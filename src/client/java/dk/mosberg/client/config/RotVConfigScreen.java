package dk.mosberg.client.config;

import dk.mosberg.config.RotVConfig;
import dk.mosberg.config.RotVConfigManager;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class RotVConfigScreen {
    private RotVConfigScreen() {}

    public static Screen create(Screen parent) {
        RotVConfig config = RotVConfigManager.get();
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent)
                .setTitle(Text.literal("Rise of the Villagers"))
                .setSavingRunnable(RotVConfigManager::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory modules = builder.getOrCreateCategory(Text.literal("Modules"));
        modules.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Core AI"), config.modules.coreAi)
                        .setSaveConsumer(value -> config.modules.coreAi = value).build());
        modules.addEntry(entryBuilder
                .startBooleanToggle(Text.literal("Village progression"),
                        config.modules.villageProgression)
                .setSaveConsumer(value -> config.modules.villageProgression = value).build());
        modules.addEntry(entryBuilder
                .startBooleanToggle(Text.literal("Professions"), config.modules.professions)
                .setSaveConsumer(value -> config.modules.professions = value).build());
        modules.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Defense"), config.modules.defense)
                        .setSaveConsumer(value -> config.modules.defense = value).build());
        modules.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Diplomacy"), config.modules.diplomacy)
                        .setSaveConsumer(value -> config.modules.diplomacy = value).build());
        modules.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Worldgen"), config.modules.worldgen)
                        .setSaveConsumer(value -> config.modules.worldgen = value).build());

        ConfigCategory difficulty = builder.getOrCreateCategory(Text.literal("Difficulty"));
        difficulty.addEntry(entryBuilder
                .startFloatField(Text.literal("Raid intensity"), config.difficulty.raidIntensity)
                .setSaveConsumer(value -> config.difficulty.raidIntensity = value).build());
        difficulty.addEntry(entryBuilder
                .startFloatField(Text.literal("Growth speed"), config.difficulty.growthSpeed)
                .setSaveConsumer(value -> config.difficulty.growthSpeed = value).build());
        difficulty.addEntry(entryBuilder
                .startFloatField(Text.literal("Villager mortality"),
                        config.difficulty.villagerMortality)
                .setSaveConsumer(value -> config.difficulty.villagerMortality = value).build());

        ConfigCategory progression = builder.getOrCreateCategory(Text.literal("Progression"));
        progression.addEntry(entryBuilder
                .startIntField(Text.literal("Hamlet min population"),
                        config.progression.hamletMinPopulation)
                .setSaveConsumer(value -> config.progression.hamletMinPopulation = value).build());
        progression.addEntry(entryBuilder
                .startIntField(Text.literal("Village min population"),
                        config.progression.villageMinPopulation)
                .setSaveConsumer(value -> config.progression.villageMinPopulation = value).build());
        progression.addEntry(entryBuilder
                .startIntField(Text.literal("Town min population"),
                        config.progression.townMinPopulation)
                .setSaveConsumer(value -> config.progression.townMinPopulation = value).build());
        progression.addEntry(entryBuilder
                .startIntField(Text.literal("City min population"),
                        config.progression.cityMinPopulation)
                .setSaveConsumer(value -> config.progression.cityMinPopulation = value).build());
        progression.addEntry(entryBuilder
                .startIntField(Text.literal("Capital min population"),
                        config.progression.capitalMinPopulation)
                .setSaveConsumer(value -> config.progression.capitalMinPopulation = value).build());

        ConfigCategory ai = builder.getOrCreateCategory(Text.literal("AI"));
        ai.addEntry(entryBuilder
                .startIntField(Text.literal("Village scan interval (ticks)"),
                        config.ai.villageScanIntervalTicks)
                .setSaveConsumer(value -> config.ai.villageScanIntervalTicks = value).build());
        ai.addEntry(entryBuilder
                .startIntField(Text.literal("Villager update interval (ticks)"),
                        config.ai.villagerUpdateIntervalTicks)
                .setSaveConsumer(value -> config.ai.villagerUpdateIntervalTicks = value).build());

        return builder.build();
    }
}
