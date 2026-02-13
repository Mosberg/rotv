package dk.mosberg.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dk.mosberg.config.RotVConfigManager;
import dk.mosberg.villager.RotVVillagerAccess;
import dk.mosberg.villager.RotVVillagerData;
import dk.mosberg.villager.RotVVillagerDataUtil;
import dk.mosberg.villager.RotVVillagerPersonality;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin implements RotVVillagerAccess {
    @Unique
    private final RotVVillagerData rotv$data = new RotVVillagerData();

    @Override
    public RotVVillagerData rotv$getData() {
        return rotv$data;
    }

    @Inject(method = "writeCustomData", at = @At("HEAD"))
    private void rotv$writeCustomData(WriteView view, CallbackInfo ci) {
        NbtCompound tag = new NbtCompound();
        rotv$data.writeToNbt(tag);
        view.put("rotv_data", NbtCompound.CODEC, tag);
    }

    @Inject(method = "readCustomData", at = @At("HEAD"))
    private void rotv$readCustomData(ReadView view, CallbackInfo ci) {
        view.read("rotv_data", NbtCompound.CODEC).ifPresent(rotv$data::readFromNbt);
    }

    @Inject(method = "interactMob", at = @At("HEAD"))
    private void rotv$onInteract(PlayerEntity player, Hand hand,
            CallbackInfoReturnable<ActionResult> cir) {
        if (!RotVConfigManager.get().modules.coreAi) {
            return;
        }
        rotv$data.setLastTradePlayerUuid(player.getUuid());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void rotv$onTick(CallbackInfo ci) {
        VillagerEntity villager = (VillagerEntity) (Object) this;
        if (rotv$data.getPersonality() == null) {
            rotv$data.setPersonality(RotVVillagerPersonality.random(villager.getRandom()));
        }
        if (!RotVConfigManager.get().modules.coreAi) {
            return;
        }
        if (RotVConfigManager.get().ai.villagerUpdateIntervalTicks <= 0) {
            return;
        }
        if (villager.getEntityWorld().getTime()
                % RotVConfigManager.get().ai.villagerUpdateIntervalTicks != 0L) {
            return;
        }
        RotVVillagerDataUtil.updateFromBrain(villager);
    }
}
