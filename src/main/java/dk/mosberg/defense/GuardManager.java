package dk.mosberg.defense;

import java.util.HashMap;
import java.util.Map;
import dk.mosberg.config.RotVConfig;
import dk.mosberg.village.VillageProfile;
import net.minecraft.server.world.ServerWorld;

/**
 * Manages guard assignments and guard-related mechanics for villages. Guards provide defense
 * against raids and receive bonuses in MILITARIZED villages.
 */
public class GuardManager {
    private final Map<String, GuardTeam> teams = new HashMap<>();

    /**
     * Represents a village's guard team with assigned guards and stats.
     */
    public record GuardTeam(String villageId, int guardCount, double armorBonus, long lastRaidTime,
            boolean isActive) {
        public GuardTeam updateLastRaid(long time) {
            return new GuardTeam(villageId, guardCount, armorBonus, time, true);
        }
    }

    /**
     * Assign guards based on village tier and population. Higher tiers and MILITARIZED
     * specializations get more guards.
     */
    public void updateGuardTeam(VillageProfile village, ServerWorld world, RotVConfig config) {
        if (village.getPopulation() < config.progression.guardMinimumPopulationThreshold) {
            teams.remove(village.getId());
            return;
        }

        int baseGuards = Math.max(1, village.getPopulation() / 5);
        int tierBonus = village.getTier().ordinal();
        int totalGuards = baseGuards + tierBonus;

        double armorBonus = 0.0;
        if (village.getSpecialization().name().equals("MILITARIZED")) {
            armorBonus = config.professions.militarizedGuardArmorBonus;
        }

        GuardTeam existing = teams.get(village.getId());
        long lastRaidTime = existing != null ? existing.lastRaidTime : 0;
        teams.put(village.getId(),
                new GuardTeam(village.getId(), totalGuards, armorBonus, lastRaidTime, true));
    }

    /**
     * Get the guard team for a village.
     */
    public GuardTeam getGuardTeam(String villageId) {
        return teams.getOrDefault(villageId, new GuardTeam(villageId, 0, 0.0, 0, false));
    }

    /**
     * Record a raid and update team status.
     */
    public void recordRaid(String villageId, long currentTime) {
        GuardTeam team = teams.get(villageId);
        if (team != null) {
            teams.put(villageId, team.updateLastRaid(currentTime));
        }
    }

    private long getCooldownTicks(int tierLevel, RotVConfig config) {
        return switch (tierLevel) {
            case 0 -> config.progression.raidCooldownTicksHamlet;
            case 1 -> config.progression.raidCooldownTicksVillage;
            default -> config.progression.raidCooldownTownAndAbove;
        };
    }

    /**
     * Check if raid cooldown has elapsed for a village.
     */
    public boolean canRaidNow(VillageProfile village, long currentTime, RotVConfig config) {
        GuardTeam team = getGuardTeam(village.getId());
        long cooldown = getCooldownTicks(village.getTier().ordinal(), config);
        return currentTime - team.lastRaidTime > cooldown;
    }

    public void clear() {
        teams.clear();
    }
}
