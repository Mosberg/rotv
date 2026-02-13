package dk.mosberg.quests;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.NbtCompound;

/**
 * Represents a quest that a village can perform. Quests provide rewards and have specific
 * objectives.
 */
public class Quest {
    private final String id;
    private final String name;
    private final String description;
    private final QuestObjective objective;
    private final List<QuestReward> rewards;
    private QuestStatus status;
    private int progress;
    private long createdTime;
    private long completedTime;

    public enum QuestStatus {
        AVAILABLE, ACTIVE, COMPLETED, FAILED, EXPIRED
    }

    public record QuestObjective(QuestType type, int target, String metadata // Additional
                                                                             // quest-specific data
    ) {
    }

    public enum QuestType {
        GATHER_RESOURCES, // Collect X food/materials
        DEFEND_VILLAGE, // Survive X raids
        TRADE_WITH_PLAYER, // Trade X times with player
        INCREASE_HAPPINESS, // Reach happiness threshold
        BUILD_STRUCTURES, // Build workstations
        GROW_POPULATION, // Reach population target
        EARN_WEALTH // Gain X wealth
    }

    public Quest(String id, String name, String description, QuestObjective objective) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.objective = objective;
        this.rewards = new ArrayList<>();
        this.status = QuestStatus.AVAILABLE;
        this.progress = 0;
        this.createdTime = 0;
        this.completedTime = 0;
    }

    public static Quest fromNbt(NbtCompound nbt) {
        String id = nbt.getString("Id").orElse("");
        String name = nbt.getString("Name").orElse("Unnamed Quest");
        String description = nbt.getString("Description").orElse("");

        var objNbtOpt = nbt.getCompound("Objective");
        QuestType type = QuestType.GATHER_RESOURCES;
        int target = 0;
        String metadata = "";

        if (objNbtOpt.isPresent()) {
            NbtCompound objNbt = objNbtOpt.get();
            type = QuestType.valueOf(objNbt.getString("Type").orElse("GATHER_RESOURCES"));
            target = objNbt.getInt("Target").orElse(0);
            metadata = objNbt.getString("Metadata").orElse("");
        }

        QuestObjective objective = new QuestObjective(type, target, metadata);

        Quest quest = new Quest(id, name, description, objective);
        quest.status = QuestStatus.valueOf(nbt.getString("Status").orElse("AVAILABLE"));
        quest.progress = nbt.getInt("Progress").orElse(0);
        quest.createdTime = nbt.getLong("CreatedTime").orElse(0L);
        quest.completedTime = nbt.getLong("CompletedTime").orElse(0L);

        return quest;
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Id", id);
        nbt.putString("Name", name);
        nbt.putString("Description", description);

        NbtCompound objNbt = new NbtCompound();
        objNbt.putString("Type", objective.type.name());
        objNbt.putInt("Target", objective.target);
        objNbt.putString("Metadata", objective.metadata);
        nbt.put("Objective", objNbt);

        nbt.putString("Status", status.name());
        nbt.putInt("Progress", progress);
        nbt.putLong("CreatedTime", createdTime);
        nbt.putLong("CompletedTime", completedTime);

        return nbt;
    }

    // ===== Getters =====
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public QuestObjective getObjective() {
        return objective;
    }

    public List<QuestReward> getRewards() {
        return rewards;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public int getProgress() {
        return progress;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public long getCompletedTime() {
        return completedTime;
    }

    // ===== Setters =====
    public void setStatus(QuestStatus status) {
        this.status = status;
    }

    public void setProgress(int progress) {
        this.progress = Math.max(0, Math.min(progress, objective.target));
    }

    public void setCreatedTime(long time) {
        this.createdTime = time;
    }

    public void setCompletedTime(long time) {
        this.completedTime = time;
    }

    // ===== Helpers =====
    public boolean isComplete() {
        return progress >= objective.target;
    }

    public float getProgressPercent() {
        return (float) progress / (float) objective.target;
    }

    public void addReward(QuestReward reward) {
        rewards.add(reward);
    }

    public void incrementProgress() {
        progress = Math.min(progress + 1, objective.target);
    }

    public void incrementProgress(int amount) {
        progress = Math.min(progress + amount, objective.target);
    }
}
