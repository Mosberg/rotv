package dk.mosberg.villager;

import dk.mosberg.config.ProfessionConfig;
import dk.mosberg.config.RotVConfig;
import dk.mosberg.config.RotVConfigManager;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.Identifier;

public final class RotVProfessionProgression {
    private static final Identifier HUNTER_SPEED_ID = new Identifier("rotv", "hunter_speed");
    private static final Identifier ENGINEER_HEALTH_ID = new Identifier("rotv", "engineer_health");
    private static final Identifier GUARD_ARMOR_ID = new Identifier("rotv", "guard_armor");
    private static final Identifier MAGE_RANGE_ID = new Identifier("rotv", "mage_range");
    private static final Identifier CARAVAN_SPEED_ID = new Identifier("rotv", "caravan_speed");
    private static final Identifier ARCHITECT_SPEED_ID = new Identifier("rotv", "architect_speed");

    private RotVProfessionProgression() {}

    public static void syncProfession(VillagerEntity villager) {
        RotVConfig config = RotVConfigManager.get();
        if (!config.modules.professions) {
            return;
        }
        RotVVillagerProfessionData data = RotVVillagerDataUtil.getData(villager).getProfession();
        RotVProfession mapped = RotVProfession.fromVanilla(villager.getVillagerData().profession());
        data.setProfession(mapped, true);
    }

    public static void applyPerks(VillagerEntity villager) {
        RotVConfig config = RotVConfigManager.get();
        if (!config.modules.professions) {
            return;
        }
        RotVVillagerProfessionData data = RotVVillagerDataUtil.getData(villager).getProfession();
        int level = data.getLevel();
        RotVProfession profession = data.getProfession();
        ProfessionConfig perkConfig = config.professions;

        EntityAttributeInstance speed =
                villager.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        double hunterSpeed = resolvePerk(level, profession == RotVProfession.HUNTER,
                perkConfig.hunterSpeedLevel1, perkConfig.hunterSpeedBonus1,
                perkConfig.hunterSpeedLevel2, perkConfig.hunterSpeedBonus2);
        applyModifier(speed, HUNTER_SPEED_ID, "rotv_hunter_speed", hunterSpeed,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);

        double caravanSpeed = resolvePerk(level, profession == RotVProfession.CARAVAN_LEADER,
                perkConfig.caravanSpeedLevel1, perkConfig.caravanSpeedBonus1, 0, 0.0);
        applyModifier(speed, CARAVAN_SPEED_ID, "rotv_caravan_speed", caravanSpeed,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);

        double architectSpeed = resolvePerk(level, profession == RotVProfession.ARCHITECT,
                perkConfig.architectSpeedLevel1, perkConfig.architectSpeedBonus1, 0, 0.0);
        applyModifier(speed, ARCHITECT_SPEED_ID, "rotv_architect_speed", architectSpeed,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);

        EntityAttributeInstance health = villager.getAttributeInstance(EntityAttributes.MAX_HEALTH);
        double engineerHealth = resolvePerk(level, profession == RotVProfession.ENGINEER,
                perkConfig.engineerHealthLevel1, perkConfig.engineerHealthBonus1,
                perkConfig.engineerHealthLevel2, perkConfig.engineerHealthBonus2);
        boolean removedHealth = applyModifier(health, ENGINEER_HEALTH_ID, "rotv_engineer_health",
                engineerHealth, EntityAttributeModifier.Operation.ADD_VALUE);
        if (removedHealth && villager.getHealth() > villager.getMaxHealth()) {
            villager.setHealth(villager.getMaxHealth());
        }

        EntityAttributeInstance armor = villager.getAttributeInstance(EntityAttributes.ARMOR);
        double guardArmor = resolvePerk(level, profession == RotVProfession.GUARD,
                perkConfig.guardArmorLevel1, perkConfig.guardArmorBonus1,
                perkConfig.guardArmorLevel2, perkConfig.guardArmorBonus2);
        applyModifier(armor, GUARD_ARMOR_ID, "rotv_guard_armor", guardArmor,
                EntityAttributeModifier.Operation.ADD_VALUE);

        EntityAttributeInstance range =
                villager.getAttributeInstance(EntityAttributes.FOLLOW_RANGE);
        double mageRange = resolvePerk(level, profession == RotVProfession.MAGE,
                perkConfig.mageRangeLevel1, perkConfig.mageRangeBonus1, perkConfig.mageRangeLevel2,
                perkConfig.mageRangeBonus2);
        applyModifier(range, MAGE_RANGE_ID, "rotv_mage_range", mageRange,
                EntityAttributeModifier.Operation.ADD_VALUE);
    }

    private static double resolvePerk(int level, boolean matches, int level1, double bonus1,
            int level2, double bonus2) {
        if (!matches) {
            return 0.0;
        }
        if (level2 > 0 && level >= level2) {
            return bonus2;
        }
        if (level1 > 0 && level >= level1) {
            return bonus1;
        }
        return 0.0;
    }

    private static boolean applyModifier(EntityAttributeInstance instance, Identifier id,
            String name, double amount, EntityAttributeModifier.Operation operation) {
        if (instance == null) {
            return false;
        }
        EntityAttributeModifier existing = instance.getModifier(id);
        if (amount <= 0.0) {
            if (existing != null) {
                instance.removeModifier(existing);
                return true;
            }
            return false;
        }
        if (existing != null) {
            if (existing.value() == amount && existing.operation() == operation) {
                return false;
            }
            instance.removeModifier(existing);
        }
        instance.addPersistentModifier(new EntityAttributeModifier(id, amount, operation));
        return false;
    }

    public static void addTradeXp(VillagerEntity villager, int trades) {
        if (trades <= 0) {
            return;
        }
        RotVConfig config = RotVConfigManager.get();
        if (!config.modules.professions || !config.professions.enableTradeXp) {
            return;
        }
        syncProfession(villager);
        addXp(villager, trades * Math.max(0, config.professions.xpPerTrade), config);
    }

    public static void addWorkXp(VillagerEntity villager) {
        RotVConfig config = RotVConfigManager.get();
        if (!config.modules.professions || !config.professions.enableWorkXp) {
            return;
        }
        syncProfession(villager);
        addXp(villager, Math.max(0, config.professions.xpPerWorkTick), config);
    }

    public static void addCombatXp(VillagerEntity villager, float damageTaken) {
        RotVConfig config = RotVConfigManager.get();
        if (!config.modules.professions || !config.professions.enableCombatXp) {
            return;
        }
        syncProfession(villager);
        int amount = Math.round(Math.max(0.0f, damageTaken) * config.professions.xpPerCombatDamage);
        addXp(villager, amount, config);
    }

    private static void addXp(VillagerEntity villager, int amount, RotVConfig config) {
        if (amount <= 0) {
            return;
        }

        RotVVillagerProfessionData data = RotVVillagerDataUtil.getData(villager).getProfession();
        ProfessionConfig professionConfig = config.professions;
        int maxLevel = Math.max(1, professionConfig.maxLevel);
        int xpPerLevel = Math.max(1, professionConfig.xpPerLevel);

        int level = data.getLevel();
        if (level >= maxLevel) {
            return;
        }

        int xp = data.getXp() + amount;
        while (xp >= xpPerLevel && level < maxLevel) {
            xp -= xpPerLevel;
            level++;
        }

        data.setLevel(level);
        data.setXp(xp);
    }
}
