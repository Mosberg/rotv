package dk.mosberg.defense;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import dk.mosberg.config.RotVConfig;
import dk.mosberg.village.VillagePersistentState;
import dk.mosberg.village.VillageProfile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;

/**
 * Manages combat between guards (villagers) and raiders (pillagers). Applies damage reduction to
 * guards and tracks raider victories/defeats.
 */
public class CombatManager {
    private static final Map<UUID, Long> lastCombatTime = new HashMap<>();
    private static final Map<UUID, Float> guardDamageReduction = new HashMap<>();

    /**
     * Called when a living entity takes damage. Applies guard damage reduction and tracks combat.
     */
    public static void onEntityDamage(LivingEntity entity, float damage, ServerWorld world,
            RotVConfig config) {
        // If a villager (potential guard) is hit, apply damage reduction
        if (entity instanceof VillagerEntity villager) {
            float reducedDamage = applyGuardDamageReduction(villager, damage, world, config);
            if (reducedDamage < damage) {
                // Damage was reduced - record combat
                lastCombatTime.put(villager.getUuid(), world.getTime());
            }
        }
        // If a pillager (raider) is hit, check if by a guard
        else if (entity instanceof PillagerEntity raider && damage > 0) {
            recordRaiderCombat(raider, world, config);
        }
    }

    /**
     * Apply damage reduction to a villager if they're a guard in a raided village.
     */
    private static float applyGuardDamageReduction(VillagerEntity villager, float damage,
            ServerWorld world, RotVConfig config) {
        if (!config.progression.enableRaids) {
            return damage;
        }

        // Find the village this villager belongs to
        String villageId = resolveVillageId(villager, world);
        if (villageId == null || villageId.isEmpty()) {
            return damage;
        }

        // Get guard team info
        var guardTeam = DefenseManager.getGuardTeam(world, villageId);
        if (guardTeam == null || guardTeam.guardCount() <= 0) {
            return damage;
        }

        // Apply damage reduction if village is under raid
        if (DefenseManager.isUnderRaid(world, villageId)) {
            // Base damage reduction from config
            float totalReduction = (float) config.progression.guardDamageReduction;

            // Add fortification defense bonus
            float fortificationBonus = FortificationManager.getTotalDefenseBonus(villageId);
            totalReduction += fortificationBonus;

            // Cap total reduction at 75%
            totalReduction = Math.min(0.75f, totalReduction);

            float reducedDamage = damage * (1.0f - totalReduction);

            // Also apply guard armor bonus
            float armorBonus = (float) guardTeam.armorBonus();
            reducedDamage *= (1.0 - (armorBonus * 0.05)); // 5% reduction per armor point

            return Math.max(0.5f, reducedDamage); // Minimum 0.5 damage
        }

        return damage;
    }

    /**
     * Record a pillager being hit in combat.
     */
    private static void recordRaiderCombat(PillagerEntity raider, ServerWorld world,
            RotVConfig config) {
        if (!config.progression.enableRaids) {
            return;
        }

        // Check if this raider is part of an active raid
        // This would require finding which village's raid this raider belongs to
        // For now, we mark them as engaged
        lastCombatTime.put(raider.getUuid(), world.getTime());
    }

    /**
     * Called when a pillager (raider) is killed. Updates raid progress and applies bonuses.
     */
    public static void onRaiderDefeated(PillagerEntity raider, ServerWorld world,
            DamageSource source, RotVConfig config) {
        if (!config.progression.enableRaids) {
            return;
        }

        // Find which village's raid this raider belonged to and update raid progress
        VillagePersistentState state = VillagePersistentState.get(world);
        for (VillageProfile village : state.getProfiles()) {
            String villageId = village.getId();
            if (DefenseManager.isUnderRaid(world, villageId)) {
                // Record the raider defeat
                DefenseManager.recordRaiderDefeated(world, villageId);

                // Credit the attacker if it was a villager guard
                if (source.getAttacker() instanceof VillagerEntity guard) {
                    GuardPerksManager.recordKill(guard);
                }

                // Apply raid victory bonuses
                applyRaidVictoryBonus(village, world, config);
                break;
            }
        }
    }

    /**
     * Apply bonuses when a village successfully defeats raiders.
     */
    private static void applyRaidVictoryBonus(VillageProfile village, ServerWorld world,
            RotVConfig config) {
        // Bonus happiness when defending successfully
        float currentHappiness = village.getHappiness();
        float bonusHappiness = 0.05f; // 5% happiness boost per raider defeated
        village.setHappiness(Math.min(1.0f, currentHappiness + bonusHappiness));

        // Add wealth from raider loot
        int lootValue = 50 + world.getRandom().nextInt(50);
        village.setWealth(village.getWealth() + lootValue);
    }

    /**
     * Called when a raid ends (all raiders defeated or time expired). Applies post-raid penalties
     * if raid was lost.
     */
    public static void onRaidComplete(String villageId, boolean raidWon, ServerWorld world,
            RotVConfig config) {
        VillagePersistentState state = VillagePersistentState.get(world);
        VillageProfile village = state.getProfiles().stream()
                .filter(v -> v.getId().equals(villageId)).findFirst().orElse(null);

        if (village == null) {
            return;
        }

        if (!raidWon) {
            // Apply post-raid happiness penalty
            float penalty = (float) config.progression.postRaidHappinessPenalty;
            float newHappiness = village.getHappiness() - penalty;
            village.setHappiness(Math.max(0.0f, newHappiness));

            // Reduce security after failed defense
            float newSecurity = village.getSecurity() - 0.1f;
            village.setSecurity(Math.max(0.0f, newSecurity));
        }
    }

    /**
     * Resolve the village ID for a villager.
     */
    private static String resolveVillageId(VillagerEntity villager, ServerWorld world) {
        // Try to find the village from existing data
        // This would ideally be tracked per-villager when guards are assigned
        // For now, return empty string (can be enhanced later)
        return "";
    }

    /**
     * Check if an entity is in active combat.
     */
    public static boolean isInActiveCombat(LivingEntity entity, long worldTime) {
        Long lastCombat = lastCombatTime.get(entity.getUuid());
        if (lastCombat == null) {
            return false;
        }
        // Consider in combat if hit within last 10 seconds (200 ticks)
        return worldTime - lastCombat < 200;
    }

    public static void clear() {
        lastCombatTime.clear();
        guardDamageReduction.clear();
    }
}
