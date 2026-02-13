package dk.mosberg.config;

public class ProgressionConfig {
    public int hamletMinPopulation = 3;
    public int villageMinPopulation = 8;
    public int townMinPopulation = 15;
    public int cityMinPopulation = 30;
    public int capitalMinPopulation = 50;
    public int specializationMinPopulation = 10;
    public int specializationMinProfessionCount = 3;
    public double agriculturalHappinessBonus = 0.05;
    public double merchantHappinessBonus = 0.05;
    public double arcaneHappinessBonus = 0.02;
    public double miningSecurityBonus = 0.04;
    public double militarizedSecurityBonus = 0.08;
    public double agriculturalFoodConsumptionPenalty = 0.95;

    public double villageProductionMultiplier = 1.05;
    public double villageHappinessBonus = 0.03;
    public double villageSecurityBonus = 0.02;
    public double townProductionMultiplier = 1.10;
    public double townHappinessBonus = 0.06;
    public double townSecurityBonus = 0.04;
    public double cityProductionMultiplier = 1.15;
    public double cityHappinessBonus = 0.09;
    public double citySecurityBonus = 0.06;
    public double capitalProductionMultiplier = 1.20;
    public double capitalHappinessBonus = 0.12;
    public double capitalSecurityBonus = 0.08;

    public boolean enableBreeding = true;
    public int breedingCooldownTicks = 6000;
    public int breedingFoodCost = 2;
    public double breedingHappinessThreshold = 0.6;
    public double breedingBedRatio = 1.1;

    // Guard and Defense Config
    public boolean enableRaids = true;
    public double raidSpawnChanceFactor = 0.01;
    public int minRaidersPerRaid = 2;
    public int maxRaidersPerRaid = 8;
    public int raiderHealthBase = 20;
    public double raiderDamageBase = 1.5;
    public double raiderDifficultyPerTier = 0.2;
    public int raidCooldownTicksHamlet = 72000;
    public int raidCooldownTicksVillage = 60000;
    public int raidCooldownTownAndAbove = 48000;
    public double militarizedGuardArmorBonus = 2.0;
    public double guardDamageReduction = 0.15;
    public double postRaidHappinessPenalty = 0.15;
    public int guardMinimumPopulationThreshold = 8;
}
