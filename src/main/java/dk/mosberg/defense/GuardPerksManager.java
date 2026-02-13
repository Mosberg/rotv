package dk.mosberg.defense;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import dk.mosberg.villager.RotVProfessionProgression;
import dk.mosberg.villager.RotVVillagerData;
import dk.mosberg.villager.RotVVillagerDataUtil;
import net.minecraft.entity.passive.VillagerEntity;

/**
 * Manages guard-specific perks and bonuses. Guards get XP bonuses, armor enhancements, and special
 * abilities.
 */
public class GuardPerksManager {
    private static final Map<UUID, GuardPerk> guardPerks = new HashMap<>();

    public record GuardPerk(UUID villagers, String villageId, int killCount, float armorBonus,
            long assignmentTime) {
        public GuardPerk withKill() {
            return new GuardPerk(villagers, villageId, killCount + 1, armorBonus, assignmentTime);
        }

        /**
         * Get experience bonus based on kill count (scales up to +50% XP)
         */
        public float getXpMultiplier() {
            return 1.0f + Math.min(0.5f, killCount * 0.05f);
        }
    }

    /**
     * Assign guard status to a villager.
     */
    public static void assignGuard(VillagerEntity villager, String villageId, double armorBonus) {
        guardPerks.put(villager.getUuid(), new GuardPerk(villager.getUuid(), villageId, 0,
                (float) armorBonus, villager.getEntityWorld().getTime()));
    }

    /**
     * Check if a villager is a guard.
     */
    public static boolean isGuard(VillagerEntity villager) {
        return guardPerks.containsKey(villager.getUuid());
    }

    /**
     * Get the guard perk info for a villager.
     */
    public static GuardPerk getGuardPerk(VillagerEntity villager) {
        return guardPerks.get(villager.getUuid());
    }

    /**
     * Record a kill by a guard (when they kill a raider).
     */
    public static void recordKill(VillagerEntity villager) {
        GuardPerk perk = guardPerks.get(villager.getUuid());
        if (perk != null) {
            GuardPerk updated = perk.withKill();
            guardPerks.put(villager.getUuid(), updated);

            // Add XP for guard kill
            float xpBonus = 50.0f * updated.getXpMultiplier();
            RotVProfessionProgression.addCombatXp(villager, (int) xpBonus);

            // Apply happiness to villager for successful defense
            RotVVillagerData data = RotVVillagerDataUtil.getData(villager);
            // Mark as recently in combat (for happiness calculation elsewhere)
        }
    }

    /**
     * Apply armor bonus to a guard based on their perk level.
     */
    public static float getArmorBonus(VillagerEntity villager) {
        GuardPerk perk = guardPerks.get(villager.getUuid());
        if (perk == null) {
            return 0.0f;
        }

        // Base armor + bonus per kill (0.2 armor per kill, max 2.0)
        float baseArmor = perk.armorBonus;
        float killBonus = Math.min(2.0f, perk.killCount * 0.2f);
        return baseArmor + killBonus;
    }

    /**
     * Remove guard status when village is no longer under threat.
     */
    public static void removeGuard(UUID villagerUuid) {
        guardPerks.remove(villagerUuid);
    }

    /**
     * Remove all guards (when mod unloads or world unloads).
     */
    public static void clear() {
        guardPerks.clear();
    }
}
