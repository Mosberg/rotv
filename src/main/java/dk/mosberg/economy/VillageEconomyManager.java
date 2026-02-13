package dk.mosberg.economy;

import java.util.List;
import dk.mosberg.config.AiConfig;
import dk.mosberg.config.EconomyConfig;
import dk.mosberg.config.RotVConfigManager;
import dk.mosberg.village.VillagePersistentState;
import dk.mosberg.village.VillageProfile;
import dk.mosberg.village.VillageProfileManager;
import dk.mosberg.village.VillageProfileManager.TierModifiers;
import dk.mosberg.village.VillageSpecialization;
import dk.mosberg.villager.RotVVillagerDataUtil;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public final class VillageEconomyManager {
    private VillageEconomyManager() {}

    public static void updateProfile(VillageProfile profile, List<VillagerEntity> members,
            EconomyConfig config, AiConfig aiConfig) {
        int population = members.size();
        int workstations = profile.getWorkstations();
        float dayScale = aiConfig.villageScanIntervalTicks <= 0 ? 0.0f
                : aiConfig.villageScanIntervalTicks / 24000.0f;

        SpecializationModifiers specMods = resolveModifiers(profile.getSpecialization(), config);
        TierModifiers tierMods = VillageProfileManager.resolveTierModifiers(profile.getTier(),
                RotVConfigManager.get().progression);
        double tierProdMultiplier = tierMods.productionMultiplier();

        int consumption = Math.round((float) (population * config.foodPerVillagerPerDay * dayScale
                * specMods.foodConsumptionMultiplier));
        int foodProduction = Math.round((float) (workstations * config.foodPerWorkstationPerDay
                * dayScale * specMods.foodProductionMultiplier * tierProdMultiplier));
        int materialProduction =
                Math.round((float) (workstations * config.materialsPerWorkstationPerDay * dayScale
                        * specMods.materialProductionMultiplier * tierProdMultiplier));

        profile.setFood(Math.max(0, profile.getFood() + foodProduction - consumption));
        profile.setMaterials(Math.max(0, profile.getMaterials() + materialProduction));
    }

    public static void applyTradeGain(ServerWorld world, VillagerEntity villager, int trades) {
        if (trades <= 0) {
            return;
        }
        BlockPos anchor = RotVVillagerDataUtil.resolveVillageAnchor(villager);
        String id = world.getRegistryKey().getValue() + "@" + anchor.toShortString();
        VillagePersistentState state = VillagePersistentState.get(world);
        VillageProfile profile = state.getOrCreate(id, anchor);
        SpecializationModifiers specMods =
                resolveModifiers(profile.getSpecialization(), RotVConfigManager.get().economy);
        TierModifiers tierMods = VillageProfileManager.resolveTierModifiers(profile.getTier(),
                RotVConfigManager.get().progression);
        int gain = Math.round((float) (trades * RotVConfigManager.get().economy.wealthPerTrade
                * specMods.wealthMultiplier * tierMods.productionMultiplier()));
        profile.setWealth(profile.getWealth() + gain);
        state.markDirty();
    }

    private static SpecializationModifiers resolveModifiers(VillageSpecialization specialization,
            EconomyConfig config) {
        return switch (specialization) {
            case AGRICULTURAL -> new SpecializationModifiers(config.agriculturalFoodMultiplier,
                    RotVConfigManager.get().progression.agriculturalFoodConsumptionPenalty, 1.0,
                    1.0);
            case MINING -> new SpecializationModifiers(1.0, 1.0, config.miningMaterialsMultiplier,
                    1.0);
            case MERCHANT -> new SpecializationModifiers(1.0, 1.0, 1.0,
                    config.merchantWealthMultiplier);
            case ARCANE -> new SpecializationModifiers(1.0, 1.0, 1.0,
                    config.arcaneWealthMultiplier);
            case MILITARIZED -> new SpecializationModifiers(1.0, config.militarizedFoodPenalty, 1.0,
                    1.0);
            case NONE -> new SpecializationModifiers(1.0, 1.0, 1.0, 1.0);
        };
    }

    private record SpecializationModifiers(double foodProductionMultiplier,
            double foodConsumptionMultiplier, double materialProductionMultiplier,
            double wealthMultiplier) {
    }
}
