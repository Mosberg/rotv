package dk.mosberg.config;

public class ProfessionConfig {
    public int xpPerTrade = 10;
    public int xpPerWorkTick = 2;
    public int xpPerCombatDamage = 1;
    public int xpPerLevel = 100;
    public int maxLevel = 10;
    public int workXpIntervalTicks = 200;
    public boolean enableTradeXp = true;
    public boolean enableWorkXp = true;
    public boolean enableCombatXp = true;
    public double arcaneXpMultiplier = 1.2;
    public double militarizedGuardArmorBonus = 2.0;
    public double merchantTradeXpMultiplier = 1.15;

    public int hunterSpeedLevel1 = 3;
    public double hunterSpeedBonus1 = 0.04;
    public int hunterSpeedLevel2 = 7;
    public double hunterSpeedBonus2 = 0.08;

    public int engineerHealthLevel1 = 3;
    public double engineerHealthBonus1 = 2.0;
    public int engineerHealthLevel2 = 7;
    public double engineerHealthBonus2 = 4.0;

    public int guardArmorLevel1 = 4;
    public double guardArmorBonus1 = 2.0;
    public int guardArmorLevel2 = 8;
    public double guardArmorBonus2 = 4.0;

    public int mageRangeLevel1 = 5;
    public double mageRangeBonus1 = 4.0;
    public int mageRangeLevel2 = 9;
    public double mageRangeBonus2 = 8.0;

    public int caravanSpeedLevel1 = 5;
    public double caravanSpeedBonus1 = 0.03;

    public int architectSpeedLevel1 = 5;
    public double architectSpeedBonus1 = 0.02;
}
