package dk.mosberg.breeding;

import java.util.ArrayList;
import java.util.List;
import dk.mosberg.config.ProgressionConfig;
import dk.mosberg.config.RotVConfig;
import dk.mosberg.village.VillagePersistentState;
import dk.mosberg.village.VillageProfile;
import dk.mosberg.villager.RotVVillagerDataUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public final class VillagerBreedingManager {
    private VillagerBreedingManager() {}

    public static void tick(ServerWorld world, RotVConfig config) {
        ProgressionConfig progConfig = config.progression;
        if (!progConfig.enableBreeding) {
            return;
        }

        List<VillagerEntity> villagers =
                new ArrayList<>(world.getEntitiesByType(EntityType.VILLAGER, entity -> true));
        if (villagers.size() < 2) {
            return;
        }

        VillagePersistentState state = VillagePersistentState.get(world);

        for (VillagerEntity villager : villagers) {
            if (!shouldBreed(villager, world.getTime(), progConfig)) {
                continue;
            }

            BlockPos anchor = RotVVillagerDataUtil.resolveVillageAnchor(villager);
            String villageId = world.getRegistryKey().getValue() + "@" + anchor.toShortString();
            VillageProfile profile = state.getOrCreate(villageId, anchor);

            if (profile.getFood() < progConfig.breedingFoodCost) {
                continue;
            }

            double villagerCount = villagers.stream()
                    .filter(v -> RotVVillagerDataUtil.resolveVillageAnchor(v).equals(anchor))
                    .count();
            int requiredBeds = (int) Math.ceil(villagerCount * progConfig.breedingBedRatio);

            if (profile.getBeds() < requiredBeds) {
                continue;
            }

            spawnBaby(villager, world, profile, state, progConfig);
        }
    }

    private static boolean shouldBreed(VillagerEntity villager, long worldTime,
            ProgressionConfig config) {
        var data = RotVVillagerDataUtil.getData(villager);
        long lastBreed = data.getLastBreedTime();
        long timeSinceBreed = worldTime - lastBreed;

        return timeSinceBreed >= config.breedingCooldownTicks;
    }

    private static void spawnBaby(VillagerEntity parent, ServerWorld world, VillageProfile profile,
            VillagePersistentState state, ProgressionConfig config) {
        VillagerEntity baby = new VillagerEntity(EntityType.VILLAGER, world);
        if (baby == null) {
            return;
        }

        BlockPos spawnPos = parent.getBlockPos().add(world.getRandom().nextInt(9) - 4, 1,
                world.getRandom().nextInt(9) - 4);
        baby.refreshPositionAndAngles(spawnPos, 0.0f, 0.0f);

        world.spawnEntity(baby);

        RotVVillagerDataUtil.ensureInitialized(baby);

        profile.setFood(Math.max(0, profile.getFood() - config.breedingFoodCost));
        state.markDirty();

        var parentData = RotVVillagerDataUtil.getData(parent);
        parentData.setLastBreedTime(world.getTime());
    }
}
