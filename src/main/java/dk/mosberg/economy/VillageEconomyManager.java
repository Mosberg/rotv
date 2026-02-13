package dk.mosberg.economy;

import java.util.List;
import dk.mosberg.config.AiConfig;
import dk.mosberg.config.EconomyConfig;
import dk.mosberg.config.RotVConfigManager;
import dk.mosberg.village.VillagePersistentState;
import dk.mosberg.village.VillageProfile;
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

        int consumption = Math.round(population * config.foodPerVillagerPerDay * dayScale);
        int foodProduction = Math.round(workstations * config.foodPerWorkstationPerDay * dayScale);
        int materialProduction =
                Math.round(workstations * config.materialsPerWorkstationPerDay * dayScale);

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
        profile.setWealth(
                profile.getWealth() + trades * RotVConfigManager.get().economy.wealthPerTrade);
        state.markDirty();
    }
}
