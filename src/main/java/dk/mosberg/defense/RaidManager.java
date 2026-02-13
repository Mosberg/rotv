package dk.mosberg.defense;

import java.util.HashMap;
import java.util.Map;
import dk.mosberg.config.RotVConfig;
import dk.mosberg.village.VillageProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

/**
 * Manages raid events and raider spawning for villages. Raid frequency and difficulty scales with
 * village population and tier.
 */
public class RaidManager {
    private final Map<String, RaidState> activeRaids = new HashMap<>();
    private final GuardManager guardManager;

    public record RaidState(String villageId, int raiderCount, int raidersDefeated, long startTime,
            double difficulty) {
        public RaidState withRaiderDefeated() {
            return new RaidState(villageId, raiderCount, raidersDefeated + 1, startTime,
                    difficulty);
        }

        public boolean isComplete() {
            return raidersDefeated >= raiderCount;
        }
    }

    public RaidManager(GuardManager guardManager) {
        this.guardManager = guardManager;
    }

    /**
     * Check if a raid should spawn for a village. Probability increases with village size and tier.
     */
    public boolean shouldSpawnRaid(VillageProfile village, long worldTime, RotVConfig config) {
        if (!config.progression.enableRaids) {
            return false;
        }

        if (!guardManager.canRaidNow(village, worldTime, config)) {
            return false;
        }

        if (activeRaids.containsKey(village.getId())) {
            return false;
        }

        double populationFactor =
                village.getPopulation() * config.progression.raidSpawnChanceFactor;
        double tierFactor = village.getTier().ordinal() * 0.01;
        double totalChance = populationFactor + tierFactor;

        return Math.random() < totalChance;
    }

    /**
     * Spawn a raid for a village.
     */
    public void spawnRaid(VillageProfile village, ServerWorld world, RotVConfig config,
            BlockPos anchor) {
        int minRaiders = config.progression.minRaidersPerRaid;
        int maxRaiders = config.progression.maxRaidersPerRaid;
        int raiderCount = minRaiders + world.getRandom().nextInt(maxRaiders - minRaiders + 1);

        double baseDifficulty =
                1.0 + (village.getTier().ordinal() * config.progression.raiderDifficultyPerTier);
        activeRaids.put(village.getId(),
                new RaidState(village.getId(), raiderCount, 0, world.getTime(), baseDifficulty));

        // Spawn pillagers around village anchor
        for (int i = 0; i < raiderCount; i++) {
            spawnPillager(world, anchor, config, baseDifficulty);
        }

        guardManager.recordRaid(village.getId(), world.getTime());
    }

    /**
     * Spawn a single pillager raider with difficulty scaling.
     */
    private void spawnPillager(ServerWorld world, BlockPos anchor, RotVConfig config,
            double difficulty) {
        int offsetX = -16 + world.getRandom().nextInt(32);
        int offsetZ = -16 + world.getRandom().nextInt(32);
        BlockPos spawnPos = anchor.add(offsetX, 1, offsetZ);

        PillagerEntity pillager = new PillagerEntity(EntityType.PILLAGER, world);
        pillager.setPosition(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);

        // Scale health based on difficulty
        double healthMultiplier = Math.min(difficulty, 3.0);
        pillager.setHealth((float) (config.progression.raiderHealthBase * healthMultiplier));

        world.spawnEntity(pillager);
    }

    /**
     * Record a raider defeat and check if raid is complete.
     */
    public void recordRaiderDefeated(String villageId) {
        RaidState raid = activeRaids.get(villageId);
        if (raid != null) {
            RaidState updated = raid.withRaiderDefeated();
            if (updated.isComplete()) {
                activeRaids.remove(villageId);
            } else {
                activeRaids.put(villageId, updated);
            }
        }
    }

    /**
     * Get the active raid state for a village.
     */
    public RaidState getActiveRaid(String villageId) {
        return activeRaids.get(villageId);
    }

    /**
     * Check if a village is currently under raid.
     */
    public boolean isUnderRaid(String villageId) {
        return activeRaids.containsKey(villageId);
    }

    public void clear() {
        activeRaids.clear();
    }
}
