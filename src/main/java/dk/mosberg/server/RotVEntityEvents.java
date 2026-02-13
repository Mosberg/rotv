package dk.mosberg.server;

import dk.mosberg.config.RotVConfigManager;
import dk.mosberg.defense.CombatManager;
import dk.mosberg.villager.RotVProfessionProgression;
import dk.mosberg.villager.RotVVillagerData;
import dk.mosberg.villager.RotVVillagerDataUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;

public final class RotVEntityEvents {
    private RotVEntityEvents() {}

    public static void init() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register(RotVEntityEvents::onAfterDamage);
        ServerLivingEntityEvents.AFTER_DEATH.register(RotVEntityEvents::onAfterDeath);
    }

    private static void onAfterDamage(LivingEntity entity, DamageSource source,
            float baseDamageTaken, float damageTaken, boolean blocked) {
        if (entity.getEntityWorld().isClient()) {
            return;
        }
        if (!RotVConfigManager.get().modules.coreAi) {
            return;
        }
        if (damageTaken <= 0.0f) {
            return;
        }

        // Track combat between guards and raiders
        if (entity.getEntityWorld() instanceof ServerWorld serverWorld) {
            CombatManager.onEntityDamage(entity, damageTaken, serverWorld, RotVConfigManager.get());
        }

        Entity attacker = source.getAttacker();
        if (attacker instanceof VillagerEntity villagerAttacker) {
            RotVProfessionProgression.addCombatXp(villagerAttacker, damageTaken);
        }

        if (!(entity instanceof VillagerEntity villager)) {
            return;
        }

        if (!(attacker instanceof LivingEntity livingAttacker)) {
            return;
        }

        RotVVillagerData data = RotVVillagerDataUtil.getData(villager);
        data.setLastAttackerUuid(livingAttacker.getUuid());
        data.setLastThreatTime(entity.getEntityWorld().getTime());
    }

    private static void onAfterDeath(LivingEntity entity, DamageSource source) {
        if (entity.getEntityWorld().isClient()) {
            return;
        }
        if (!RotVConfigManager.get().progression.enableRaids) {
            return;
        }

        // Track raider defeats
        if (entity instanceof PillagerEntity raider
                && entity.getEntityWorld() instanceof ServerWorld serverWorld) {
            CombatManager.onRaiderDefeated(raider, serverWorld, source, RotVConfigManager.get());
        }
    }
}
