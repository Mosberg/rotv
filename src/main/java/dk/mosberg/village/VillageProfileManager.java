package dk.mosberg.village;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import dk.mosberg.config.ProgressionConfig;
import dk.mosberg.config.RotVConfig;
import dk.mosberg.economy.VillageEconomyManager;
import dk.mosberg.villager.RotVProfession;
import dk.mosberg.villager.RotVProfessionProgression;
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
        Map<VillageSpecialization, Integer> specializationCounts =
                new EnumMap<>(VillageSpecialization.class);
        for (VillagerEntity villager : members) {
            RotVVillagerData data = RotVVillagerDataUtil.getData(villager);
            if (data.getHomePos() != null) {
                beds.add(data.getHomePos());
            }
            if (data.getJobSitePos() != null) {
                workstations.add(data.getJobSitePos());
            }
            if (config.modules.professions) {
                RotVProfessionProgression.syncProfession(villager);
                RotVProfession profession = data.getProfession().getProfession();
                VillageSpecialization specialization = resolveSpecialization(profession);
                if (specialization != VillageSpecialization.NONE) {
                    specializationCounts.merge(specialization, 1, Integer::sum);
                }
            }
        }
        profile.setBeds(beds.size());
        profile.setWorkstations(workstations.size());
        profile.setTier(resolveTier(profile.getPopulation(), config.progression));
        profile.setSpecialization(
                resolveSpecialization(profile, specializationCounts, config.progression));
        float baseHappiness = Math.min(1.0f, profile.getPopulation() / 20.0f);
        float baseSecurity = Math.min(1.0f, profile.getPopulation() / 30.0f);
        VillageSpecialization specialization = profile.getSpecialization();
        float happiness =
                clamp01(baseHappiness + resolveHappinessBonus(specialization, config.progression));
        float security =
                clamp01(baseSecurity + resolveSecurityBonus(specialization, config.progression));
        profile.setHappiness(happiness);
        profile.setSecurity(security);
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

    private static VillageSpecialization resolveSpecialization(VillageProfile profile,
            Map<VillageSpecialization, Integer> counts, ProgressionConfig config) {
        if (profile.getPopulation() < config.specializationMinPopulation) {
            return VillageSpecialization.NONE;
        }
        if (counts.isEmpty()) {
            return VillageSpecialization.NONE;
        }

        VillageSpecialization best = VillageSpecialization.NONE;
        int bestCount = 0;
        for (Map.Entry<VillageSpecialization, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > bestCount) {
                best = entry.getKey();
                bestCount = entry.getValue();
            }
        }

        if (bestCount < config.specializationMinProfessionCount) {
            return VillageSpecialization.NONE;
        }
        return best;
    }

    private static VillageSpecialization resolveSpecialization(RotVProfession profession) {
        return switch (profession) {
            case HUNTER -> VillageSpecialization.AGRICULTURAL;
            case ENGINEER -> VillageSpecialization.MINING;
            case MAGE -> VillageSpecialization.ARCANE;
            case CARAVAN_LEADER, DIPLOMAT -> VillageSpecialization.MERCHANT;
            case GUARD -> VillageSpecialization.MILITARIZED;
            default -> VillageSpecialization.NONE;
        };
    }

    private static float resolveHappinessBonus(VillageSpecialization specialization,
            ProgressionConfig config) {
        return (float) switch (specialization) {
            case AGRICULTURAL -> config.agriculturalHappinessBonus;
            case MERCHANT -> config.merchantHappinessBonus;
            case ARCANE -> config.arcaneHappinessBonus;
            default -> 0.0;
        };
    }

    private static float resolveSecurityBonus(VillageSpecialization specialization,
            ProgressionConfig config) {
        return (float) switch (specialization) {
            case MINING -> config.miningSecurityBonus;
            case MILITARIZED -> config.militarizedSecurityBonus;
            default -> 0.0;
        };
    }

    private static float clamp01(float value) {
        if (value < 0.0f) {
            return 0.0f;
        }
        if (value > 1.0f) {
            return 1.0f;
        }
        return value;
    }
}
