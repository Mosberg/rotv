package dk.mosberg.villager;

import java.util.UUID;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class RotVVillagerData {
    private static final String ROOT_KEY = "RotV";

    private UUID lastAttackerUuid;
    private UUID lastTradePlayerUuid;
    private long lastThreatTime;
    private RotVScheduleState scheduleState = RotVScheduleState.REST;
    private RotVVillagerPersonality personality = new RotVVillagerPersonality();
    private BlockPos homePos;
    private BlockPos jobSitePos;

    public void readFromNbt(NbtCompound nbt) {
        nbt.getCompound(ROOT_KEY).ifPresent(rotv -> {
            lastAttackerUuid = rotv.getString("LastAttacker").map(UUID::fromString).orElse(null);
            lastTradePlayerUuid =
                    rotv.getString("LastTradePlayer").map(UUID::fromString).orElse(null);
            lastThreatTime = rotv.getLong("LastThreatTime").orElse(0L);

            String scheduleValue = rotv.getString("ScheduleState").orElse(null);
            if (scheduleValue != null) {
                try {
                    scheduleState = RotVScheduleState.valueOf(scheduleValue);
                } catch (IllegalArgumentException ignored) {
                    scheduleState = RotVScheduleState.REST;
                }
            }

            rotv.getCompound("Personality").ifPresent(personality::readFromNbt);
            rotv.getCompound("HomePos").ifPresent(tag -> homePos = readBlockPos(tag));
            rotv.getCompound("JobSitePos").ifPresent(tag -> jobSitePos = readBlockPos(tag));
        });
    }

    public void writeToNbt(NbtCompound nbt) {
        NbtCompound rotv = new NbtCompound();
        if (lastAttackerUuid != null) {
            rotv.putString("LastAttacker", lastAttackerUuid.toString());
        }
        if (lastTradePlayerUuid != null) {
            rotv.putString("LastTradePlayer", lastTradePlayerUuid.toString());
        }
        rotv.putLong("LastThreatTime", lastThreatTime);
        rotv.putString("ScheduleState", scheduleState.name());
        NbtCompound personalityTag = new NbtCompound();
        personality.writeToNbt(personalityTag);
        rotv.put("Personality", personalityTag);
        if (homePos != null) {
            rotv.put("HomePos", writeBlockPos(homePos));
        }
        if (jobSitePos != null) {
            rotv.put("JobSitePos", writeBlockPos(jobSitePos));
        }
        nbt.put(ROOT_KEY, rotv);
    }

    private static BlockPos readBlockPos(NbtCompound nbt) {
        return new BlockPos(nbt.getInt("X").orElse(0), nbt.getInt("Y").orElse(0),
                nbt.getInt("Z").orElse(0));
    }

    private static NbtCompound writeBlockPos(BlockPos pos) {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("X", pos.getX());
        nbt.putInt("Y", pos.getY());
        nbt.putInt("Z", pos.getZ());
        return nbt;
    }

    public UUID getLastAttackerUuid() {
        return lastAttackerUuid;
    }

    public void setLastAttackerUuid(UUID lastAttackerUuid) {
        this.lastAttackerUuid = lastAttackerUuid;
    }

    public UUID getLastTradePlayerUuid() {
        return lastTradePlayerUuid;
    }

    public void setLastTradePlayerUuid(UUID lastTradePlayerUuid) {
        this.lastTradePlayerUuid = lastTradePlayerUuid;
    }

    public long getLastThreatTime() {
        return lastThreatTime;
    }

    public void setLastThreatTime(long lastThreatTime) {
        this.lastThreatTime = lastThreatTime;
    }

    public RotVScheduleState getScheduleState() {
        return scheduleState;
    }

    public void setScheduleState(RotVScheduleState scheduleState) {
        this.scheduleState = scheduleState;
    }

    public RotVVillagerPersonality getPersonality() {
        return personality;
    }

    public void setPersonality(RotVVillagerPersonality personality) {
        this.personality = personality;
    }

    public BlockPos getHomePos() {
        return homePos;
    }

    public void setHomePos(BlockPos homePos) {
        this.homePos = homePos;
    }

    public BlockPos getJobSitePos() {
        return jobSitePos;
    }

    public void setJobSitePos(BlockPos jobSitePos) {
        this.jobSitePos = jobSitePos;
    }
}
