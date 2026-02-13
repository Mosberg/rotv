package dk.mosberg.quests;

/**
 * Represents a reward for completing a quest.
 */
public record QuestReward(RewardType type, int amount) {
    public enum RewardType {
        FOOD, MATERIALS, WEALTH, HAPPINESS, XP_BOOST
    }

    /**
     * Get a readable description of the reward.
     */
    public String getDescription() {
        return switch (type) {
            case FOOD -> amount + " food";
            case MATERIALS -> amount + " materials";
            case WEALTH -> amount + " wealth";
            case HAPPINESS -> "+" + String.format("%.1f", amount / 100.0f) + " happiness";
            case XP_BOOST -> amount + "% XP boost";
        };
    }
}
