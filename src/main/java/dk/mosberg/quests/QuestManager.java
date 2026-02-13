package dk.mosberg.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dk.mosberg.village.VillagePersistentState;
import dk.mosberg.village.VillageProfile;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

/**
 * Manages quest lifecycle: generation, progression, completion, rewards. Ties quests to village
 * events and player actions.
 */
public class QuestManager {
    private static final Map<String, List<Quest>> villageQuests = new HashMap<>();
    private static final Map<String, Long> questGenerationTimes = new HashMap<>();
    private static final long QUEST_GENERATION_INTERVAL = 6000; // 5 minutes

    /**
     * Initialize or tick quests for villages.
     */
    public static void tickQuests(ServerWorld world) {
        VillagePersistentState villageState = VillagePersistentState.get(world);

        for (VillageProfile village : villageState.getProfiles()) {
            String villageId = village.getId();
            long lastGen = questGenerationTimes.getOrDefault(villageId, 0L);

            // Generate new quests every 5 minutes
            if (System.currentTimeMillis() - lastGen > QUEST_GENERATION_INTERVAL) {
                generateQuestsForVillage(village, world);
                questGenerationTimes.put(villageId, System.currentTimeMillis());
            }

            // Tick all active quests
            List<Quest> quests = villageQuests.getOrDefault(villageId, new ArrayList<>());
            for (Quest quest : new ArrayList<>(quests)) {
                tickQuest(villageId, quest, village);
            }
        }
    }

    /**
     * Generate new quests for a village based on its tier and profile.
     */
    private static void generateQuestsForVillage(VillageProfile village, ServerWorld world) {
        String villageId = village.getId();
        List<Quest> quests = villageQuests.computeIfAbsent(villageId, k -> new ArrayList<>());

        // Don't generate if too many active quests
        int activeCount = (int) quests.stream()
                .filter(q -> q.getStatus() == Quest.QuestStatus.ACTIVE).count();

        if (activeCount >= 3)
            return;

        Random random = world.getRandom();
        int tier = village.getTier().ordinal() + 1; // Convert to 1-5 scale

        // Generate 1-2 quests based on village tier
        int toGenerate = 1 + random.nextInt(2);
        for (int i = 0; i < toGenerate; i++) {
            Quest.QuestType type = selectQuestType(tier, random);
            Quest quest = createQuestOfType(type, villageId, tier, random);
            quests.add(quest);
        }
    }

    /**
     * Create a specific quest type.
     */
    private static Quest createQuestOfType(Quest.QuestType type, String villageId, int tier,
            Random random) {
        return switch (type) {
            case GATHER_RESOURCES -> new Quest("Q_" + villageId + "_" + System.currentTimeMillis(),
                    "Gather Resources",
                    "Collect " + (30 + tier * 10) + " food or materials for the village",
                    new Quest.QuestObjective(type, 30 + tier * 10, ""));
            case DEFEND_VILLAGE -> new Quest("Q_" + villageId + "_" + System.currentTimeMillis(),
                    "Defend the Village", "Help defend against a raid (Difficulty: " + tier + ")",
                    new Quest.QuestObjective(type, tier, ""));
            case TRADE_WITH_PLAYER -> new Quest("Q_" + villageId + "_" + System.currentTimeMillis(),
                    "Open Trade", "Initiate trade with the player for emeralds",
                    new Quest.QuestObjective(type, 1, ""));
            case INCREASE_HAPPINESS -> new Quest(
                    "Q_" + villageId + "_" + System.currentTimeMillis(), "Improve Happiness",
                    "Increase village happiness by " + (15 + tier * 5),
                    new Quest.QuestObjective(type, 15 + tier * 5, ""));
            case BUILD_STRUCTURES -> new Quest("Q_" + villageId + "_" + System.currentTimeMillis(),
                    "Build Structures", "Construct " + (1 + tier) + " new buildings",
                    new Quest.QuestObjective(type, 1 + tier, ""));
            case GROW_POPULATION -> new Quest("Q_" + villageId + "_" + System.currentTimeMillis(),
                    "Grow Population", "Increase village population by " + (2 + tier),
                    new Quest.QuestObjective(type, 2 + tier, ""));
            case EARN_WEALTH -> new Quest("Q_" + villageId + "_" + System.currentTimeMillis(),
                    "Earn Riches", "Accumulate " + (100 + tier * 50) + " wealth",
                    new Quest.QuestObjective(type, 100 + tier * 50, ""));
        };
    }

    /**
     * Progress towards a quest objective.
     */
    public static void progressQuest(String villageId, Quest.QuestType type, int amount) {
        List<Quest> quests = villageQuests.getOrDefault(villageId, new ArrayList<>());
        for (Quest quest : quests) {
            if (quest.getStatus() == Quest.QuestStatus.ACTIVE
                    && quest.getObjective().type() == type) {
                quest.incrementProgress(amount);
            }
        }
    }

    /**
     * Tick a single quest (check expiration, auto-complete if objective reached).
     */
    private static void tickQuest(String villageId, Quest quest, VillageProfile village) {
        if (quest.getStatus() == Quest.QuestStatus.AVAILABLE) {
            // Available quests stay available until player accepts
            return;
        }

        if (quest.getStatus() == Quest.QuestStatus.ACTIVE) {
            if (quest.isComplete()) {
                completeQuest(villageId, quest, village);
            }
        }

        if (quest.getStatus() == Quest.QuestStatus.EXPIRED) {
            // Remove expired quests after some time
            villageQuests.get(villageId).remove(quest);
        }
    }

    /**
     * Complete a quest and distribute rewards.
     */
    public static void completeQuest(String villageId, Quest quest, VillageProfile village) {
        List<Quest> quests = villageQuests.getOrDefault(villageId, new ArrayList<>());
        int questIndex = quests.indexOf(quest);

        if (questIndex == -1)
            return;

        // Create new quest instance with completed status
        List<QuestReward> rewards = new ArrayList<>();

        int tier = village.getTier().ordinal() + 1; // Convert VillageTier to 1-5
        switch (quest.getObjective().type()) {
            case GATHER_RESOURCES -> {
                rewards.add(new QuestReward(QuestReward.RewardType.MATERIALS, 10 + tier * 5));
                rewards.add(new QuestReward(QuestReward.RewardType.WEALTH, 20 + tier * 10));
            }
            case DEFEND_VILLAGE -> {
                rewards.add(new QuestReward(QuestReward.RewardType.HAPPINESS,
                        (int) (0.5f * tier * 100))); // 50 represents 0.5 happiness
                rewards.add(new QuestReward(QuestReward.RewardType.WEALTH, 50 + tier * 30));
            }
            case TRADE_WITH_PLAYER -> {
                rewards.add(new QuestReward(QuestReward.RewardType.WEALTH, 30 + tier * 15));
                rewards.add(new QuestReward(QuestReward.RewardType.HAPPINESS, 30)); // 30 represents
                                                                                    // 0.3 happiness
            }
            case INCREASE_HAPPINESS -> {
                rewards.add(new QuestReward(QuestReward.RewardType.HAPPINESS, 100)); // 100
                                                                                     // represents
                                                                                     // 1.0
                                                                                     // happiness
                rewards.add(new QuestReward(QuestReward.RewardType.WEALTH, 15 + tier * 10));
            }
            case BUILD_STRUCTURES -> {
                rewards.add(new QuestReward(QuestReward.RewardType.MATERIALS, 20 + tier * 10));
                rewards.add(new QuestReward(QuestReward.RewardType.WEALTH, 25 + tier * 15));
            }
            case GROW_POPULATION -> {
                rewards.add(new QuestReward(QuestReward.RewardType.HAPPINESS, 70)); // 70 represents
                                                                                    // 0.7 happiness
                rewards.add(new QuestReward(QuestReward.RewardType.WEALTH, 40 + tier * 20));
            }
            case EARN_WEALTH -> {
                rewards.add(new QuestReward(QuestReward.RewardType.WEALTH, 100 + tier * 50));
                rewards.add(new QuestReward(QuestReward.RewardType.XP_BOOST, 10));
            }
        }

        // Apply rewards to village
        for (QuestReward reward : rewards) {
            applyReward(village, reward);
        }

        // Mark quest as completed and remove from active quests
        quests.remove(questIndex);
    }

    /**
     * Apply a quest reward to the village.
     */
    private static void applyReward(VillageProfile village, QuestReward reward) {
        switch (reward.type()) {
            case FOOD -> {
                // Add food to village resources
                village.setFood(village.getFood() + reward.amount());
                village.setHappiness(village.getHappiness() + 0.1f);
            }
            case MATERIALS -> {
                // Materials used for structures, increase security slightly
                village.setMaterials(village.getMaterials() + reward.amount());
                village.setHappiness(village.getHappiness() + 0.2f);
            }
            case WEALTH -> {
                // Increase village wealth directly
                village.setWealth(village.getWealth() + reward.amount());
                village.setHappiness(village.getHappiness() + (reward.amount() * 0.01f));
            }
            case HAPPINESS -> {
                // Convert from percentage representation (e.g., 30 = 0.3 happiness)
                float happinessBonus = reward.amount() / 100.0f;
                village.setHappiness(village.getHappiness() + happinessBonus);
            }
            case XP_BOOST -> {
                // Boost all villager happiness slightly
                village.setHappiness(village.getHappiness() + 0.1f);
            }
        }
    }

    /**
     * Get active quests for a village.
     */
    public static List<Quest> getActiveQuests(String villageId) {
        return villageQuests.getOrDefault(villageId, new ArrayList<>());
    }

    /**
     * Activate an available quest.
     */
    public static void activateQuest(String villageId, String questId) {
        List<Quest> quests = villageQuests.getOrDefault(villageId, new ArrayList<>());
        for (Quest quest : quests) {
            if (quest.getId().equals(questId) && quest.getStatus() == Quest.QuestStatus.AVAILABLE) {
                quest.setStatus(Quest.QuestStatus.ACTIVE);
                quest.setCreatedTime(System.currentTimeMillis());
                break;
            }
        }
    }

    /**
     * Select a quest type based on village tier and randomness.
     */
    private static Quest.QuestType selectQuestType(int tier, Random random) {
        Quest.QuestType[] types = Quest.QuestType.values();
        return types[random.nextInt(types.length)];
    }

    // Load/save for persistence
    public static void loadFromNbt(NbtCompound nbt) {
        int villageCount = nbt.getInt("VillageCount").orElse(0);
        for (int i = 0; i < villageCount; i++) {
            var villageIdOpt = nbt.getString("Village_" + i);
            if (villageIdOpt.isPresent()) {
                String villageId = villageIdOpt.get();
                var questListOpt = nbt.getList("Quests_" + i);

                List<Quest> quests = new ArrayList<>();
                if (questListOpt.isPresent()) {
                    NbtList questList = questListOpt.get();
                    for (int j = 0; j < questList.size(); j++) {
                        var questNbtOpt = questList.getCompound(j);
                        if (questNbtOpt.isPresent()) {
                            quests.add(Quest.fromNbt(questNbtOpt.get()));
                        }
                    }
                }

                villageQuests.put(villageId, quests);
            }
        }
    }

    public static NbtCompound saveToNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("VillageCount", villageQuests.size());

        int i = 0;
        for (String villageId : villageQuests.keySet()) {
            nbt.putString("Village_" + i, villageId);

            NbtList questList = new NbtList();
            for (Quest quest : villageQuests.get(villageId)) {
                questList.add(quest.toNbt());
            }
            nbt.put("Quests_" + i, questList);
            i++;
        }

        return nbt;
    }
}
