package dk.mosberg.governance;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import dk.mosberg.village.VillagePersistentState;
import dk.mosberg.village.VillageProfile;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;

/**
 * Manages village governance: leadership, decisions, laws. Villages elect leaders who influence
 * decision-making and resource allocation.
 */
public class GovernanceManager {
    private static final Map<String, VillageLeader> leaders = new HashMap<>();
    private static final Map<String, VillageGovernance> governance = new HashMap<>();

    public record VillageLeader(UUID villagerUuid, String villageId, String title, // "Mayor",
                                                                                   // "Elder",
                                                                                   // "Chief"
            int charisma, long electionTime, int reElectionCountdown) {
        public static VillageLeader fromNbt(String villageId, NbtCompound nbt) {
            return new VillageLeader(
                    new UUID(nbt.getLong("LeaderUuidMost").orElse(0L),
                            nbt.getLong("LeaderUuidLeast").orElse(0L)),
                    villageId, nbt.getString("Title").orElse("Elder"),
                    nbt.getInt("Charisma").orElse(0),
                    nbt.getLong("ElectionTime").orElse(System.currentTimeMillis()),
                    nbt.getInt("ReElectionCountdown").orElse(24000));
        }

        public NbtCompound toNbt() {
            NbtCompound nbt = new NbtCompound();
            nbt.putLong("LeaderUuidMost", villagerUuid.getMostSignificantBits());
            nbt.putLong("LeaderUuidLeast", villagerUuid.getLeastSignificantBits());
            nbt.putString("Title", title);
            nbt.putInt("Charisma", charisma);
            nbt.putLong("ElectionTime", electionTime);
            nbt.putInt("ReElectionCountdown", reElectionCountdown);
            return nbt;
        }
    }

    public record VillageGovernance(String villageId, int legislationLevel, // 1-3: restrictive to
                                                                            // lenient
            float taxRate, // 0.05f to 0.20f (5-20%)
            boolean allowsPlayerTrade, int unrestCount // discontent: >0 leads to unrest events
    ) {
        public static VillageGovernance fromNbt(String villageId, NbtCompound nbt) {
            return new VillageGovernance(villageId, nbt.getInt("LegislationLevel").orElse(2),
                    nbt.getFloat("TaxRate").orElse(0.10f),
                    nbt.getBoolean("AllowsPlayerTrade").orElse(true),
                    nbt.getInt("UnrestCount").orElse(0));
        }

        public NbtCompound toNbt() {
            NbtCompound nbt = new NbtCompound();
            nbt.putInt("LegislationLevel", legislationLevel);
            nbt.putFloat("TaxRate", taxRate);
            nbt.putBoolean("AllowsPlayerTrade", allowsPlayerTrade);
            nbt.putInt("UnrestCount", unrestCount);
            return nbt;
        }
    }

    /**
     * Find or create a leader for a village.
     */
    public static void ensureLeadership(String villageId, ServerWorld world) {
        if (leaders.containsKey(villageId))
            return;

        VillagePersistentState villageState = VillagePersistentState.get(world);
        VillageProfile village = null;
        for (VillageProfile v : villageState.getProfiles()) {
            if (v.getId().equals(villageId)) {
                village = v;
                break;
            }
        }

        if (village == null)
            return;

        // Find villager with highest level (simplified - use population as proxy)
        int bestLevel = village.getPopulation();

        if (bestLevel > 0) {
            VillageLeader leader = new VillageLeader(new UUID(0, bestLevel), // Simplified UUID
                    villageId, determineTitleByTier(village.getTier().ordinal()), bestLevel * 10,
                    System.currentTimeMillis(), 24000 // 1 day
            );
            leaders.put(villageId, leader);
        }
    }

    /**
     * Get the current leader of a village.
     */
    public static Optional<VillageLeader> getLeader(String villageId) {
        return Optional.ofNullable(leaders.get(villageId));
    }

    /**
     * Update governance settings (tax rate, laws, etc).
     */
    public static void updateGovernance(String villageId, VillageGovernance updated) {
        governance.put(villageId, updated);
    }

    /**
     * Get governance settings for a village.
     */
    public static VillageGovernance getGovernance(String villageId) {
        return governance.computeIfAbsent(villageId,
                k -> new VillageGovernance(k, 2, 0.10f, true, 0));
    }

    /**
     * Check for unrest events (if unrest count gets too high).
     */
    public static void tickGovernance(ServerWorld world) {
        for (VillageGovernance gov : governance.values()) {
            if (gov.unrestCount > 20) {
                // Trigger unrest event (lost productivity, villager unhappiness, etc.)
                VillagePersistentState state = VillagePersistentState.get(world);
                for (VillageProfile village : state.getProfiles()) {
                    if (village.getId().equals(gov.villageId)) {
                        village.setHappiness(village.getHappiness() - 5.0f); // Unrest causes
                                                                             // unhappiness
                        break;
                    }
                }
            }
        }
    }

    /**
     * React to village events (raids, population changes) to affect governance.
     */
    public static void onRaidDefense(String villageId, boolean successful) {
        VillageGovernance current = getGovernance(villageId);
        VillageGovernance updated = successful
                ? new VillageGovernance(villageId, current.legislationLevel, current.taxRate,
                        current.allowsPlayerTrade, Math.max(0, current.unrestCount - 5))
                : new VillageGovernance(villageId, current.legislationLevel, current.taxRate,
                        current.allowsPlayerTrade, current.unrestCount + 3);
        updateGovernance(villageId, updated);
    }

    /**
     * Determine leadership title based on village tier ordinal.
     */
    private static String determineTitleByTier(int tierOrdinal) {
        return switch (tierOrdinal) {
            case 0 -> "Village Elder";
            case 1 -> "Mayor";
            case 2 -> "Lord";
            case 3 -> "Duke";
            default -> "Chieftain";
        };
    }

    // Load/save helpers for VillagePersistentState
    public static void loadFromNbt(NbtCompound nbt) {
        int leaderCount = nbt.getInt("LeaderCount").orElse(0);
        for (int i = 0; i < leaderCount; i++) {
            var leaderNbtOpt = nbt.getCompound("Leader_" + i);
            if (leaderNbtOpt.isPresent()) {
                NbtCompound leaderNbt = leaderNbtOpt.get();
                String villageId = leaderNbt.getString("VillageId").orElse("");
                if (!villageId.isEmpty()) {
                    VillageLeader leader = VillageLeader.fromNbt(villageId, leaderNbt);
                    leaders.put(leader.villageId, leader);
                }
            }
        }

        int govCount = nbt.getInt("GovernanceCount").orElse(0);
        for (int i = 0; i < govCount; i++) {
            var govNbtOpt = nbt.getCompound("Governance_" + i);
            if (govNbtOpt.isPresent()) {
                NbtCompound govNbt = govNbtOpt.get();
                String villageId = govNbt.getString("VillageId").orElse("");
                if (!villageId.isEmpty()) {
                    VillageGovernance gov = VillageGovernance.fromNbt(villageId, govNbt);
                    governance.put(villageId, gov);
                }
            }
        }
    }

    public static NbtCompound saveToNbt() {
        NbtCompound nbt = new NbtCompound();

        nbt.putInt("LeaderCount", leaders.size());
        int i = 0;
        for (VillageLeader leader : leaders.values()) {
            NbtCompound leaderNbt = leader.toNbt();
            leaderNbt.putString("VillageId", leader.villageId);
            nbt.put("Leader_" + i, leaderNbt);
            i++;
        }

        nbt.putInt("GovernanceCount", governance.size());
        i = 0;
        for (VillageGovernance gov : governance.values()) {
            NbtCompound govNbt = gov.toNbt();
            govNbt.putString("VillageId", gov.villageId);
            nbt.put("Governance_" + i, govNbt);
            i++;
        }

        return nbt;
    }
}
