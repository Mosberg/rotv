package dk.mosberg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dk.mosberg.config.RotVConfigManager;
import dk.mosberg.server.RotVServerHooks;
import net.fabricmc.api.ModInitializer;

public class RotV implements ModInitializer {
    public static final String MOD_ID = "rotv";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        RotVConfigManager.init();
        RotVServerHooks.init();
        LOGGER.info("Rise of the Villagers initialized");
    }
}
