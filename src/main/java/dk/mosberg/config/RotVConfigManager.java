package dk.mosberg.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.mosberg.RotV;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class RotVConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "rotv.json";
    private static RotVConfig config = new RotVConfig();

    private RotVConfigManager() {}

    public static void init() {
        load();
    }

    public static RotVConfig get() {
        return config;
    }

    public static void load() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
        if (!Files.exists(configPath)) {
            save();
            return;
        }

        try (Reader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
            RotVConfig loaded = GSON.fromJson(reader, RotVConfig.class);
            if (loaded != null) {
                config = loaded;
            }
        } catch (IOException ex) {
            RotV.LOGGER.warn("Failed to read config, using defaults", ex);
        }
    }

    public static void save() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
        try {
            Files.createDirectories(configPath.getParent());
        } catch (IOException ex) {
            RotV.LOGGER.warn("Failed to create config directory", ex);
        }

        try (Writer writer = Files.newBufferedWriter(configPath, StandardCharsets.UTF_8)) {
            GSON.toJson(config, writer);
        } catch (IOException ex) {
            RotV.LOGGER.warn("Failed to write config", ex);
        }
    }
}
