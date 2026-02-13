package dk.mosberg.village;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import dk.mosberg.config.ProgressionConfig;
import dk.mosberg.config.RotVConfig;
import dk.mosberg.economy.VillageEconomyManager;
import dk.mosberg.villager.RotVVillagerData;
import dk.mosberg.villager.RotVVillagerDataUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public final class VillageProfileManager {
    private VillageProfileManager() {}

    public static void tick(ServerWorld world, RotVConfig config) {
        List<VillagerEntity> villagers =
                new ArrayList<>(world.getEntitiesByType(EntityType.VILLAGER, entity -> true));
        Map<BlockPos, List<VillagerEntity>> groups = new HashMap<>();
        for (VillagerEntity villager : villagers) {
            RotVVillagerDataUtil.updateFromBrain(villager);
            BlockPos anchor = RotVVillagerDataUtil.resolveVillageAnchor(villager);
            groups.computeIfAbsent(anchor, key -> new ArrayList<>()).add(villager);
        }

        VillagePersistentState state = VillagePersistentState.get(world);
        for (Map.Entry<BlockPos, List<VillagerEntity>> entry : groups.entrySet()) {
            BlockPos center = entry.getKey();
            List<VillagerEntity> members = entry.getValue();
            String id = world.getRegistryKey().getValue() + "@" + center.toShortString();
            VillageProfile profile = state.getOrCreate(id, center);
            updateProfile(world, profile, members, config);
            state.markDirty();
        }
    }

    private static void updateProfile(ServerWorld world, VillageProfile profile,
            List<VillagerEntity> members, RotVConfig config) {
        profile.setCenter(members.getFirst().getBlockPos());
        profile.setPopulation(members.size());
        Set<BlockPos> beds = new HashSet<>();
        Set<BlockPos> workstations = new HashSet<>();
        for (VillagerEntity villager : members) {
            RotVVillagerData data = RotVVillagerDataUtil.getData(villager);
            if (data.getHomePos() != null) {
                beds.add(data.getHomePos());
            }
            if (data.getJobSitePos() != null) {
                workstations.add(data.getJobSitePos());
            }
        }
        profile.setBeds(beds.size());
        profile.setWorkstations(workstations.size());
        profile.setTier(resolveTier(profile.getPopulation(), config.progression));
        profile.setHappiness(Math.min(1.0f, profile.getPopulation() / 20.0f));
        profile.setSecurity(Math.min(1.0f, profile.getPopulation() / 30.0f));
        VillageEconomyManager.updateProfile(profile, members, config.economy, config.ai);
        profile.setLastUpdated(world.getTime());
    }

    private static VillageTier resolveTier(int population, ProgressionConfig config) {
        if (population >= config.capitalMinPopulation) {
            return VillageTier.CAPITAL;
        }
        if (population >= config.cityMinPopulation) {
            return VillageTier.CITY;
        }
        if (population >= config.townMinPopulation) {
            return VillageTier.TOWN;
        }
        if (population >= config.villageMinPopulation) {
            return VillageTier.VILLAGE;
        }
        return VillageTier.HAMLET;
    }
}
