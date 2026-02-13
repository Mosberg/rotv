package dk.mosberg.defense;

import net.minecraft.nbt.NbtCompound;

/**
 * Represents a defensive structure built by a village. Fortifications provide defense bonuses
 * against raids.
 */
public class Fortification {
    private final String villageId;
    private final FortificationType type;
    private int level;
    private float integrity; // 0.0 to 1.0 (damage state)
    private long builtTime;

    public enum FortificationType {
        WOODEN_WALL(1, 0.10f, 50, 20), // +10% defense, costs 50 materials, 20 wealth
        STONE_WALL(2, 0.20f, 100, 50), // +20% defense, costs 100 materials, 50 wealth
        WATCHTOWER(1, 0.15f, 75, 40), // +15% defense + early warning
        IRON_GATE(2, 0.25f, 150, 80), // +25% defense at entry points
        BARRICADE(1, 0.08f, 30, 10); // +8% defense, cheap temporary defense

        private final int maxLevel;
        private final float defenseBonus;
        private final int materialCost;
        private final int wealthCost;

        FortificationType(int maxLevel, float defenseBonus, int materialCost, int wealthCost) {
            this.maxLevel = maxLevel;
            this.defenseBonus = defenseBonus;
            this.materialCost = materialCost;
            this.wealthCost = wealthCost;
        }

        public int getMaxLevel() {
            return maxLevel;
        }

        public float getDefenseBonus() {
            return defenseBonus;
        }

        public int getMaterialCost() {
            return materialCost;
        }

        public int getWealthCost() {
            return wealthCost;
        }
    }

    public Fortification(String villageId, FortificationType type) {
        this.villageId = villageId;
        this.type = type;
        this.level = 1;
        this.integrity = 1.0f;
        this.builtTime = System.currentTimeMillis();
    }

    public static Fortification fromNbt(String villageId, NbtCompound nbt) {
        FortificationType type =
                FortificationType.valueOf(nbt.getString("Type").orElse("WOODEN_WALL"));
        Fortification fortification = new Fortification(villageId, type);
        fortification.level = nbt.getInt("Level").orElse(1);
        fortification.integrity = nbt.getFloat("Integrity").orElse(1.0f);
        fortification.builtTime = nbt.getLong("BuiltTime").orElse(System.currentTimeMillis());
        return fortification;
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Type", type.name());
        nbt.putInt("Level", level);
        nbt.putFloat("Integrity", integrity);
        nbt.putLong("BuiltTime", builtTime);
        return nbt;
    }

    /**
     * Get the effective defense bonus accounting for integrity.
     */
    public float getEffectiveDefenseBonus() {
        return type.getDefenseBonus() * level * integrity;
    }

    /**
     * Damage this fortification during a raid.
     */
    public void takeDamage(float amount) {
        integrity = Math.max(0.0f, integrity - amount);
    }

    /**
     * Repair this fortification.
     */
    public void repair(float amount) {
        integrity = Math.min(1.0f, integrity + amount);
    }

    /**
     * Upgrade this fortification to the next level.
     */
    public boolean upgrade() {
        if (level < type.getMaxLevel()) {
            level++;
            return true;
        }
        return false;
    }

    /**
     * Check if this fortification is destroyed.
     */
    public boolean isDestroyed() {
        return integrity <= 0.0f;
    }

    // Getters
    public String getVillageId() {
        return villageId;
    }

    public FortificationType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public float getIntegrity() {
        return integrity;
    }

    public long getBuiltTime() {
        return builtTime;
    }
}
