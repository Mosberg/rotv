package dk.mosberg.governance;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.nbt.NbtCompound;

/**
 * Tracks relations between a village and players/entities. Affects trade prices, NPC attitudes, and
 * quest availability.
 */
public class VillageRelations {
    private final String villageId;
    private final Map<UUID, Float> playerReputation = new HashMap<>();
    private int diplomacyLevel = 1; // 1-5 scale
    private boolean isFriendly = true;
    private long lastDiplomaticEvent = 0;

    public VillageRelations(String villageId) {
        this.villageId = villageId;
    }

    public static VillageRelations fromNbt(String villageId, NbtCompound nbt) {
        VillageRelations relations = new VillageRelations(villageId);
        relations.diplomacyLevel = nbt.getInt("DiplomacyLevel").orElse(1);
        relations.isFriendly = nbt.getBoolean("IsFriendly").orElse(true);
        relations.lastDiplomaticEvent = nbt.getLong("LastDiplomaticEvent").orElse(0L);

        // Load player reputations if stored
        int playerCount = nbt.getInt("PlayerCount").orElse(0);
        for (int i = 0; i < playerCount; i++) {
            String key = nbt.getString("Player_" + i).orElse("");
            float rep = nbt.getFloat("Rep_" + i).orElse(0.0f);
            if (!key.isEmpty()) {
                relations.playerReputation.put(UUID.fromString(key), rep);
            }
        }

        return relations;
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("DiplomacyLevel", diplomacyLevel);
        nbt.putBoolean("IsFriendly", isFriendly);
        nbt.putLong("LastDiplomaticEvent", lastDiplomaticEvent);

        // Store player reputations
        nbt.putInt("PlayerCount", playerReputation.size());
        int i = 0;
        for (Map.Entry<UUID, Float> entry : playerReputation.entrySet()) {
            nbt.putString("Player_" + i, entry.getKey().toString());
            nbt.putFloat("Rep_" + i, entry.getValue());
            i++;
        }

        return nbt;
    }

    /**
     * Modify player reputation with this village. -100 to +100 scale (negative = hostile, positive
     * = friendly)
     */
    public void modifyReputation(UUID player, float change) {
        float currentRep = playerReputation.getOrDefault(player, 0.0f);
        float newRep = Math.max(-100.0f, Math.min(100.0f, currentRep + change));
        playerReputation.put(player, newRep);

        // Update overall village attitude based on average player reputation
        updateVillageFriendliness();
    }

    /**
     * Get player reputation with this village.
     */
    public float getReputation(UUID player) {
        return playerReputation.getOrDefault(player, 0.0f);
    }

    /**
     * Check if player is viewed favorably by village.
     */
    public boolean isFriendlyWith(UUID player) {
        return getReputation(player) > 30.0f;
    }

    /**
     * Increase diplomacy level (improves trade prices, quest rewards).
     */
    public void increaseDiplomacy() {
        diplomacyLevel = Math.min(5, diplomacyLevel + 1);
        lastDiplomaticEvent = System.currentTimeMillis();
    }

    /**
     * Decrease diplomacy level (increases prices, NPC hostility).
     */
    public void decreaseDiplomacy() {
        diplomacyLevel = Math.max(1, diplomacyLevel - 1);
        lastDiplomaticEvent = System.currentTimeMillis();
    }

    /**
     * Get trade price multiplier based on diplomacy and reputation.
     */
    public float getTradePriceMultiplier(UUID player) {
        float repMultiplier = 1.0f - (getReputation(player) / 500.0f); // -20% to +20%
        float diplomacyMultiplier = (6 - diplomacyLevel) / 5.0f; // 20% to 100% (lower level =
                                                                 // higher price)
        return repMultiplier * diplomacyMultiplier;
    }

    /**
     * Update overall village friendliness based on average player reputation.
     */
    private void updateVillageFriendliness() {
        if (playerReputation.isEmpty()) {
            isFriendly = true;
            return;
        }

        float avgRep = (float) playerReputation.values().stream().mapToDouble(Float::doubleValue)
                .average().orElse(0.0);

        isFriendly = avgRep > 0.0f;
    }

    // Getters
    public String getVillageId() {
        return villageId;
    }

    public int getDiplomacyLevel() {
        return diplomacyLevel;
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public long getLastDiplomaticEvent() {
        return lastDiplomaticEvent;
    }
}
