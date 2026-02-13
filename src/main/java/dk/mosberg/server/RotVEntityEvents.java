package dk.mosberg.server;

import dk.mosberg.config.RotVConfigManager;
import dk.mosberg.villager.RotVProfessionProgression;
import dk.mosberg.villager.RotVVillagerData;
import dk.mosberg.villager.RotVVillagerDataUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;

public final class RotVEntityEvents {
    private RotVEntityEvents() {}

    public static void init() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register(RotVEntityEvents::onAfterDamage);
    }

    private static void onAfterDamage(LivingEntity entity, DamageSource source,
            float baseDamageTaken, float damageTaken, boolean blocked) {
        if (entity.getWorld().isClient) {
            return;
        }
        if (!RotVConfigManager.get().modules.coreAi) {
            return;
        }
        if (damageTaken <= 0.0f) {
            return;
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
        data.setLastThreatTime(entity.getWorld().getTime());
    }
}
