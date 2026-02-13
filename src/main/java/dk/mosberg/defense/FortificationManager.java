package dk.mosberg.defense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dk.mosberg.village.VillageProfile;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

/**
 * Manages fortifications for all villages. Tracks construction, damage, and defense bonuses from
 * structures.
 */
public class FortificationManager {
    private static final Map<String, List<Fortification>> villageFortifications = new HashMap<>();

    /**
     * Build a new fortification for a village.
     */
    public static boolean buildFortification(String villageId, Fortification.FortificationType type,
            VillageProfile village) {
        // Check if village can afford it
        int materialCost = type.getMaterialCost();
        int wealthCost = type.getWealthCost();

        if (village.getMaterials() < materialCost || village.getWealth() < wealthCost) {
            return false;
        }

        // Deduct costs
        village.setMaterials(village.getMaterials() - materialCost);
        village.setWealth(village.getWealth() - wealthCost);

        // Build fortification
        Fortification fortification = new Fortification(villageId, type);
        villageFortifications.computeIfAbsent(villageId, k -> new ArrayList<>()).add(fortification);

        return true;
    }

    /**
     * Get all fortifications for a village.
     */
    public static List<Fortification> getFortifications(String villageId) {
        return villageFortifications.getOrDefault(villageId, new ArrayList<>());
    }

    /**
     * Calculate total defense bonus from all fortifications.
     */
    public static float getTotalDefenseBonus(String villageId) {
        List<Fortification> fortifications = getFortifications(villageId);
        float totalBonus = 0.0f;

        for (Fortification fortification : fortifications) {
            if (!fortification.isDestroyed()) {
                totalBonus += fortification.getEffectiveDefenseBonus();
            }
        }

        return totalBonus;
    }

    /**
     * Damage all fortifications in a village during a raid.
     */
    public static void damageAllFortifications(String villageId, float damageAmount) {
        List<Fortification> fortifications = getFortifications(villageId);

        for (Fortification fortification : fortifications) {
            if (!fortification.isDestroyed()) {
                fortification.takeDamage(damageAmount);
            }
        }

        // Remove destroyed fortifications
        fortifications.removeIf(Fortification::isDestroyed);
    }

    /**
     * Repair a specific fortification.
     */
    public static boolean repairFortification(String villageId,
            Fortification.FortificationType type, VillageProfile village) {
        List<Fortification> fortifications = getFortifications(villageId);

        for (Fortification fortification : fortifications) {
            if (fortification.getType() == type && fortification.getIntegrity() < 1.0f) {
                // Repair cost is 25% of build cost
                int materialCost = type.getMaterialCost() / 4;
                int wealthCost = type.getWealthCost() / 4;

                if (village.getMaterials() >= materialCost && village.getWealth() >= wealthCost) {
                    village.setMaterials(village.getMaterials() - materialCost);
                    village.setWealth(village.getWealth() - wealthCost);
                    fortification.repair(0.25f); // Repair 25% integrity
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Upgrade a fortification to the next level.
     */
    public static boolean upgradeFortification(String villageId,
            Fortification.FortificationType type, VillageProfile village) {
        List<Fortification> fortifications = getFortifications(villageId);

        for (Fortification fortification : fortifications) {
            if (fortification.getType() == type && fortification.getLevel() < type.getMaxLevel()) {
                // Upgrade cost is 150% of build cost
                int materialCost = (type.getMaterialCost() * 3) / 2;
                int wealthCost = (type.getWealthCost() * 3) / 2;

                if (village.getMaterials() >= materialCost && village.getWealth() >= wealthCost) {
                    village.setMaterials(village.getMaterials() - materialCost);
                    village.setWealth(village.getWealth() - wealthCost);
                    fortification.upgrade();
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if a village has a specific fortification type.
     */
    public static boolean hasFortificationType(String villageId,
            Fortification.FortificationType type) {
        List<Fortification> fortifications = getFortifications(villageId);
        return fortifications.stream().anyMatch(f -> f.getType() == type && !f.isDestroyed());
    }

    /**
     * Get count of active fortifications.
     */
    public static int getActiveFortificationCount(String villageId) {
        List<Fortification> fortifications = getFortifications(villageId);
        return (int) fortifications.stream().filter(f -> !f.isDestroyed()).count();
    }

    // Load/save for persistence
    public static void loadFromNbt(NbtCompound nbt) {
        int villageCount = nbt.getInt("VillageCount").orElse(0);

        for (int i = 0; i < villageCount; i++) {
            var villageIdOpt = nbt.getString("Village_" + i);
            if (villageIdOpt.isPresent()) {
                String villageId = villageIdOpt.get();
                var fortListOpt = nbt.getList("Fortifications_" + i);

                List<Fortification> fortifications = new ArrayList<>();
                if (fortListOpt.isPresent()) {
                    NbtList fortList = fortListOpt.get();
                    for (int j = 0; j < fortList.size(); j++) {
                        var fortNbtOpt = fortList.getCompound(j);
                        if (fortNbtOpt.isPresent()) {
                            fortifications.add(Fortification.fromNbt(villageId, fortNbtOpt.get()));
                        }
                    }
                }

                villageFortifications.put(villageId, fortifications);
            }
        }
    }

    public static NbtCompound saveToNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("VillageCount", villageFortifications.size());

        int i = 0;
        for (String villageId : villageFortifications.keySet()) {
            nbt.putString("Village_" + i, villageId);

            NbtList fortList = new NbtList();
            for (Fortification fortification : villageFortifications.get(villageId)) {
                fortList.add(fortification.toNbt());
            }
            nbt.put("Fortifications_" + i, fortList);
            i++;
        }

        return nbt;
    }

    public static void clear() {
        villageFortifications.clear();
    }
}
