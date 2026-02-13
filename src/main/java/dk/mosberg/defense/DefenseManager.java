package dk.mosberg.defense;

import java.util.HashMap;
import java.util.Map;
import dk.mosberg.config.RotVConfig;
import dk.mosberg.village.VillagePersistentState;
import dk.mosberg.village.VillageProfile;
import net.minecraft.server.world.ServerWorld;

/**
 * Central coordinator for all defense mechanics including guards and raids. Manages guard team
 * updates, raid spawning, and post-raid effects.
 */
public class DefenseManager {
    private static final Map<String, GuardManager> guardManagers = new HashMap<>();
    private static final Map<String, RaidManager> raidManagers = new HashMap<>();

    public static void tick(ServerWorld world, RotVConfig config) {
        if (!config.progression.enableRaids) {
            return;
        }

        String worldId = world.getRegistryKey().getValue().toString();
        GuardManager guardManager = guardManagers.computeIfAbsent(worldId, k -> new GuardManager());
        RaidManager raidManager =
                raidManagers.computeIfAbsent(worldId, k -> new RaidManager(guardManager));

        long worldTime = world.getTime();

        // Update guard teams and check for raids for all villages
        VillagePersistentState state = VillagePersistentState.get(world);
        for (VillageProfile village : state.getProfiles()) {
            guardManager.updateGuardTeam(village, world, config);

            // Check for raid spawn
            if (raidManager.shouldSpawnRaid(village, worldTime, config)) {
                raidManager.spawnRaid(village, world, config, village.getCenter());
            }
        }
    }

    /**
     * Record a raider defeat for a village (called from entity events).
     */
    public static void recordRaiderDefeated(ServerWorld world, String villageId) {
        String worldId = world.getRegistryKey().getValue().toString();
        RaidManager raidManager = raidManagers.get(worldId);
        if (raidManager != null) {
            raidManager.recordRaiderDefeated(villageId);
        }
    }

    /**
     * Get the guard team for a village.
     */
    public static GuardManager.GuardTeam getGuardTeam(ServerWorld world, String villageId) {
        String worldId = world.getRegistryKey().getValue().toString();
        GuardManager guardManager = guardManagers.get(worldId);
        return guardManager != null ? guardManager.getGuardTeam(villageId) : null;
    }

    /**
     * Check if a village is under raid.
     */
    public static boolean isUnderRaid(ServerWorld world, String villageId) {
        String worldId = world.getRegistryKey().getValue().toString();
        RaidManager raidManager = raidManagers.get(worldId);
        return raidManager != null && raidManager.isUnderRaid(villageId);
    }

    /**
     * Get raid state if active.
     */
    public static RaidManager.RaidState getActiveRaid(ServerWorld world, String villageId) {
        String worldId = world.getRegistryKey().getValue().toString();
        RaidManager raidManager = raidManagers.get(worldId);
        return raidManager != null ? raidManager.getActiveRaid(villageId) : null;
    }

    public static void clear() {
        guardManagers.clear();
        raidManagers.clear();
    }
}
