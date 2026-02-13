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

        ConfigCategory professions = builder.getOrCreateCategory(Text.literal("Professions"));
        professions.addEntry(entryBuilder
                .startBooleanToggle(Text.literal("Enable trade XP"),
                        config.professions.enableTradeXp)
                .setSaveConsumer(value -> config.professions.enableTradeXp = value).build());
        professions.addEntry(entryBuilder
                .startBooleanToggle(Text.literal("Enable work XP"), config.professions.enableWorkXp)
                .setSaveConsumer(value -> config.professions.enableWorkXp = value).build());
        professions.addEntry(entryBuilder
                .startBooleanToggle(Text.literal("Enable combat XP"),
                        config.professions.enableCombatXp)
                .setSaveConsumer(value -> config.professions.enableCombatXp = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("XP per trade"), config.professions.xpPerTrade)
                .setSaveConsumer(value -> config.professions.xpPerTrade = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("XP per work tick"), config.professions.xpPerWorkTick)
                .setSaveConsumer(value -> config.professions.xpPerWorkTick = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("XP per combat damage"),
                        config.professions.xpPerCombatDamage)
                .setSaveConsumer(value -> config.professions.xpPerCombatDamage = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("XP per level"), config.professions.xpPerLevel)
                .setSaveConsumer(value -> config.professions.xpPerLevel = value).build());
        professions.addEntry(
                entryBuilder.startIntField(Text.literal("Max level"), config.professions.maxLevel)
                        .setSaveConsumer(value -> config.professions.maxLevel = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("Work XP interval (ticks)"),
                        config.professions.workXpIntervalTicks)
                .setSaveConsumer(value -> config.professions.workXpIntervalTicks = value).build());

        professions.addEntry(entryBuilder
                .startIntField(Text.literal("Hunter speed level 1"),
                        config.professions.hunterSpeedLevel1)
                .setSaveConsumer(value -> config.professions.hunterSpeedLevel1 = value).build());
        professions.addEntry(entryBuilder
                .startDoubleField(Text.literal("Hunter speed bonus 1"),
                        config.professions.hunterSpeedBonus1)
                .setSaveConsumer(value -> config.professions.hunterSpeedBonus1 = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("Hunter speed level 2"),
                        config.professions.hunterSpeedLevel2)
                .setSaveConsumer(value -> config.professions.hunterSpeedLevel2 = value).build());
        professions.addEntry(entryBuilder
                .startDoubleField(Text.literal("Hunter speed bonus 2"),
                        config.professions.hunterSpeedBonus2)
                .setSaveConsumer(value -> config.professions.hunterSpeedBonus2 = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("Engineer health level 1"),
                        config.professions.engineerHealthLevel1)
                .setSaveConsumer(value -> config.professions.engineerHealthLevel1 = value).build());
        professions.addEntry(entryBuilder
                .startDoubleField(Text.literal("Engineer health bonus 1"),
                        config.professions.engineerHealthBonus1)
                .setSaveConsumer(value -> config.professions.engineerHealthBonus1 = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("Engineer health level 2"),
                        config.professions.engineerHealthLevel2)
                .setSaveConsumer(value -> config.professions.engineerHealthLevel2 = value).build());
        professions.addEntry(entryBuilder
                .startDoubleField(Text.literal("Engineer health bonus 2"),
                        config.professions.engineerHealthBonus2)
                .setSaveConsumer(value -> config.professions.engineerHealthBonus2 = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("Guard armor level 1"),
                        config.professions.guardArmorLevel1)
                .setSaveConsumer(value -> config.professions.guardArmorLevel1 = value).build());
        professions.addEntry(entryBuilder
                .startDoubleField(Text.literal("Guard armor bonus 1"),
                        config.professions.guardArmorBonus1)
                .setSaveConsumer(value -> config.professions.guardArmorBonus1 = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("Guard armor level 2"),
                        config.professions.guardArmorLevel2)
                .setSaveConsumer(value -> config.professions.guardArmorLevel2 = value).build());
        professions.addEntry(entryBuilder
                .startDoubleField(Text.literal("Guard armor bonus 2"),
                        config.professions.guardArmorBonus2)
                .setSaveConsumer(value -> config.professions.guardArmorBonus2 = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("Mage range level 1"),
                        config.professions.mageRangeLevel1)
                .setSaveConsumer(value -> config.professions.mageRangeLevel1 = value).build());
        professions.addEntry(entryBuilder
                .startDoubleField(Text.literal("Mage range bonus 1"),
                        config.professions.mageRangeBonus1)
                .setSaveConsumer(value -> config.professions.mageRangeBonus1 = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("Mage range level 2"),
                        config.professions.mageRangeLevel2)
                .setSaveConsumer(value -> config.professions.mageRangeLevel2 = value).build());
        professions.addEntry(entryBuilder
                .startDoubleField(Text.literal("Mage range bonus 2"),
                        config.professions.mageRangeBonus2)
                .setSaveConsumer(value -> config.professions.mageRangeBonus2 = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("Caravan speed level"),
                        config.professions.caravanSpeedLevel1)
                .setSaveConsumer(value -> config.professions.caravanSpeedLevel1 = value).build());
        professions.addEntry(entryBuilder
                .startDoubleField(Text.literal("Caravan speed bonus"),
                        config.professions.caravanSpeedBonus1)
                .setSaveConsumer(value -> config.professions.caravanSpeedBonus1 = value).build());
        professions.addEntry(entryBuilder
                .startIntField(Text.literal("Architect speed level"),
                        config.professions.architectSpeedLevel1)
                .setSaveConsumer(value -> config.professions.architectSpeedLevel1 = value).build());
        professions.addEntry(entryBuilder
                .startDoubleField(Text.literal("Architect speed bonus"),
                        config.professions.architectSpeedBonus1)
                .setSaveConsumer(value -> config.professions.architectSpeedBonus1 = value).build());

        ConfigCategory economy = builder.getOrCreateCategory(Text.literal("Economy"));
        economy.addEntry(entryBuilder
                .startIntField(Text.literal("Food per villager per day"),
                        config.economy.foodPerVillagerPerDay)
                .setSaveConsumer(value -> config.economy.foodPerVillagerPerDay = value).build());
        economy.addEntry(entryBuilder
                .startIntField(Text.literal("Food per workstation per day"),
                        config.economy.foodPerWorkstationPerDay)
                .setSaveConsumer(value -> config.economy.foodPerWorkstationPerDay = value).build());
        economy.addEntry(entryBuilder
                .startIntField(Text.literal("Materials per workstation per day"),
                        config.economy.materialsPerWorkstationPerDay)
                .setSaveConsumer(value -> config.economy.materialsPerWorkstationPerDay = value)
                .build());
        economy.addEntry(entryBuilder
                .startIntField(Text.literal("Wealth per trade"), config.economy.wealthPerTrade)
                .setSaveConsumer(value -> config.economy.wealthPerTrade = value).build());

        ConfigCategory names = builder.getOrCreateCategory(Text.literal("Names"));
        names.addEntry(entryBuilder
                .startBooleanToggle(Text.literal("Enable full names"), config.names.enableFullNames)
                .setSaveConsumer(value -> config.names.enableFullNames = value).build());
        names.addEntry(entryBuilder
                .startBooleanToggle(Text.literal("Show nameplates"), config.names.showNameplate)
                .setSaveConsumer(value -> config.names.showNameplate = value).build());
        names.addEntry(entryBuilder
                .startBooleanToggle(Text.literal("Override custom names"),
                        config.names.allowOverrideCustomName)
                .setSaveConsumer(value -> config.names.allowOverrideCustomName = value).build());

        return builder.build();
    }
}
