package dk.mosberg.villager;

import java.util.Optional;
import dk.mosberg.config.RotVConfigManager;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.BlockPos;
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
