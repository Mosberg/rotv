package dk.mosberg.server;

import dk.mosberg.breeding.VillagerBreedingManager;
import dk.mosberg.config.RotVConfig;
import dk.mosberg.config.RotVConfigManager;
import dk.mosberg.defense.DefenseManager;
import dk.mosberg.governance.GovernanceManager;
import dk.mosberg.quests.QuestManager;
import dk.mosberg.village.VillageProfileManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public final class RotVServerHooks {
    private RotVServerHooks() {}

    public static void init() {
        RotVCommands.init();
        RotVEntityEvents.init();
        ServerTickEvents.END_SERVER_TICK.register(RotVServerHooks::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        RotVConfig config = RotVConfigManager.get();
        if (!config.modules.villageProgression) {
            return;
        }

        long time = server.getOverworld().getTime();

        if (config.ai.villageScanIntervalTicks > 0
                && time % config.ai.villageScanIntervalTicks == 0L) {
            for (ServerWorld world : server.getWorlds()) {
                VillageProfileManager.tick(world, config);
            }
        }

        if (config.progression.enableBreeding) {
            for (ServerWorld world : server.getWorlds()) {
                VillagerBreedingManager.tick(world, config);
            }
        }

        if (config.progression.enableRaids) {
            for (ServerWorld world : server.getWorlds()) {
                DefenseManager.tick(world, config);
            }
        }

        // Tick quests for Phase 5 content
        for (ServerWorld world : server.getWorlds()) {
            QuestManager.tickQuests(world);
        }

        // Tick governance systems
        for (ServerWorld world : server.getWorlds()) {
            GovernanceManager.tickGovernance(world);
        }
    }
}
