package dk.mosberg.villager;

import java.util.Optional;
import java.util.UUID;
import dk.mosberg.config.RotVConfigManager;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;

public final class RotVVillagerDataUtil {
    private RotVVillagerDataUtil() {}

    public static RotVVillagerData getData(VillagerEntity villager) {
        return ((RotVVillagerAccess) villager).rotv$getData();
    }

    public static void ensureInitialized(VillagerEntity villager) {
        RotVVillagerData data = getData(villager);
        if (data.getPersonality() == null) {
            data.setPersonality(RotVVillagerPersonality.random(villager.getRandom()));
        }
        if (data.getFirstName() == null || data.getFirstName().isEmpty()) {
            data.setFirstName(RotVVillagerNameGenerator.randomFirstName(villager.getRandom()));
        }
        if (data.getLastName() == null || data.getLastName().isEmpty()) {
            FamilyInfo family = findFamily(villager);
            if (family != null) {
                data.setFamilyId(family.id);
                data.setLastName(family.lastName);
            } else {
                data.setFamilyId(UUID.randomUUID());
                data.setLastName(RotVVillagerNameGenerator.randomLastName(villager.getRandom()));
            }
        } else if (data.getFamilyId() == null) {
            data.setFamilyId(UUID.randomUUID());
        }

        applyDisplayName(villager, data);
    }

    public static void refreshDisplayName(VillagerEntity villager) {
        RotVVillagerData data = getData(villager);
        applyDisplayName(villager, data);
    }

    private static void applyDisplayName(VillagerEntity villager, RotVVillagerData data) {
        if (!RotVConfigManager.get().names.enableFullNames) {
            return;
        }
        if (data.getFirstName() == null || data.getLastName() == null) {
            return;
        }
        String fullName = data.getFirstName() + " " + data.getLastName();
        Text nameText = Text.literal(fullName);
        if (villager.hasCustomName()) {
            if (!RotVConfigManager.get().names.allowOverrideCustomName
                    && !villager.getCustomName().getString().equals(fullName)) {
                return;
            }
        }
        villager.setCustomName(nameText);
        villager.setCustomNameVisible(RotVConfigManager.get().names.showNameplate);
    }

    private static FamilyInfo findFamily(VillagerEntity villager) {
        RotVVillagerData selfData = getData(villager);
        BlockPos selfHome = selfData.getHomePos();
        Box box = villager.getBoundingBox().expand(24.0);
        for (VillagerEntity nearby : villager.getEntityWorld()
                .getEntitiesByClass(VillagerEntity.class, box, other -> other != villager)) {
            RotVVillagerData data = getData(nearby);
            if (data.getLastName() == null || data.getLastName().isEmpty()) {
                continue;
            }
            if (selfHome != null && data.getHomePos() != null && selfHome.equals(data.getHomePos())
                    && data.getFamilyId() != null) {
                return new FamilyInfo(data.getFamilyId(), data.getLastName());
            }
        }
        for (VillagerEntity nearby : villager.getEntityWorld()
                .getEntitiesByClass(VillagerEntity.class, box, other -> other != villager)) {
            RotVVillagerData data = getData(nearby);
            if (data.getLastName() == null || data.getLastName().isEmpty()) {
                continue;
            }
            UUID familyId = data.getFamilyId();
            if (familyId != null) {
                return new FamilyInfo(familyId, data.getLastName());
            }
        }
        return null;
    }

    private record FamilyInfo(UUID id, String lastName) {
    }

    public static void updateFromBrain(VillagerEntity villager) {
        if (!RotVConfigManager.get().modules.coreAi) {
            return;
        }

        RotVVillagerData data = getData(villager);
        Optional<GlobalPos> home = villager.getBrain().getOptionalMemory(MemoryModuleType.HOME);
        home.ifPresent(globalPos -> data.setHomePos(globalPos.pos()));
        Optional<GlobalPos> jobSite =
                villager.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE);
        jobSite.ifPresent(globalPos -> data.setJobSitePos(globalPos.pos()));

        World world = villager.getEntityWorld();
        long timeOfDay = world.getTimeOfDay() % 24000L;
        RotVScheduleState state;
        if (world.isThundering() || world.isRaining()) {
            state = RotVScheduleState.INDOORS;
        } else if (timeOfDay < 1000L) {
            state = RotVScheduleState.REST;
        } else if (timeOfDay < 9000L) {
            state = RotVScheduleState.WORK;
        } else if (timeOfDay < 11000L) {
            state = RotVScheduleState.SOCIAL;
        } else {
            state = RotVScheduleState.REST;
        }
        data.setScheduleState(state);
    }

    public static BlockPos resolveVillageAnchor(VillagerEntity villager) {
        Optional<GlobalPos> meeting =
                villager.getBrain().getOptionalMemory(MemoryModuleType.MEETING_POINT);
        if (meeting.isPresent()) {
            return meeting.get().pos();
        }
        RotVVillagerData data = getData(villager);
        if (data.getHomePos() != null) {
            return data.getHomePos();
        }
        return villager.getBlockPos();
    }
}
