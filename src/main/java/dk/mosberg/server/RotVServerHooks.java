package dk.mosberg.server;

import dk.mosberg.config.RotVConfig;
import dk.mosberg.config.RotVConfigManager;
import dk.mosberg.village.VillageProfileManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public final class RotVServerHooks {
    private RotVServerHooks() {}

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(RotVServerHooks::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        RotVConfig config = RotVConfigManager.get();
        if (!config.modules.villageProgression) {
            return;
        }
        if (config.ai.villageScanIntervalTicks <= 0) {
            return;
        }

        long time = server.getOverworld().getTime();
        if (time % config.ai.villageScanIntervalTicks != 0L) {
            return;
        }

        for (ServerWorld world : server.getWorlds()) {
            VillageProfileManager.tick(world, config);
        }
    }
}
