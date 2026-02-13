package dk.mosberg.client.hud;

import java.util.ArrayList;
import java.util.List;
import dk.mosberg.config.RotVConfigManager;
import dk.mosberg.village.VillagePersistentState;
import dk.mosberg.village.VillageProfile;
import dk.mosberg.village.VillageSpecialization;
import dk.mosberg.villager.RotVProfession;
import dk.mosberg.villager.RotVProfessionProgression;
import dk.mosberg.villager.RotVVillagerData;
import dk.mosberg.villager.RotVVillagerDataUtil;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;

public final class RotVVillagerHudOverlay {
    private RotVVillagerHudOverlay() {}

    public static void init() {
        HudElementRegistry.addLast(Identifier.of("rotv", "villager_overlay"),
                RotVVillagerHudOverlay::render);
    }

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        if (!RotVConfigManager.get().names.showHudTooltip) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) {
            return;
        }
        if (!(client.crosshairTarget instanceof EntityHitResult hit)) {
            return;
        }
        if (!(hit.getEntity() instanceof VillagerEntity villager)) {
            return;
        }
        RotVVillagerDataUtil.ensureInitialized(villager);
        RotVVillagerData data = RotVVillagerDataUtil.getData(villager);
        RotVProfessionProgression.syncProfession(villager);
        RotVProfession profession = data.getProfession().getProfession();
        VillageSpecialization specialization = resolveVillageSpecialization(villager);
        List<Text> lines = new ArrayList<>();
        String first = data.getFirstName();
        String last = data.getLastName();
        if (first != null && last != null) {
            lines.add(Text.literal(first + " " + last));
        }
        lines.add(Text.literal("Profession: " + profession.name()));
        if (data.getProfession().getLevel() > 0) {
            lines.add(Text.literal("Level: " + data.getProfession().getLevel()));
        }
        if (specialization != VillageSpecialization.NONE) {
            lines.add(Text.literal(""));
            String perkText = buildSpecializationPerkInfo(profession, specialization);
            if (!perkText.isEmpty()) {
                lines.add(Text.literal("§6" + perkText));
            }
        }
        TextRenderer renderer = client.textRenderer;
        int x = client.getWindow().getScaledWidth() / 2 + 8;
        int y = client.getWindow().getScaledHeight() / 2 + 8;
        for (Text line : lines) {
            context.drawTextWithShadow(renderer, line, x, y, 0xFFFFFF);
            y += renderer.fontHeight + 2;
        }
    }

    private static VillageSpecialization resolveVillageSpecialization(VillagerEntity villager) {
        if (!(villager.getEntityWorld() instanceof ServerWorld serverWorld)) {
            return VillageSpecialization.NONE;
        }
        BlockPos anchor = RotVVillagerDataUtil.resolveVillageAnchor(villager);
        String id = serverWorld.getRegistryKey().getValue() + "@" + anchor.toShortString();
        VillageProfile profile = VillagePersistentState.get(serverWorld).getOrCreate(id, anchor);
        return profile.getSpecialization();
    }

    private static String buildSpecializationPerkInfo(RotVProfession profession,
            VillageSpecialization specialization) {
        return switch (specialization) {
            case AGRICULTURAL -> "AGRICULTURAL: +Food production";
            case MINING -> "MINING: +Materials, +Security";
            case MERCHANT -> {
                if (profession == RotVProfession.CARAVAN_LEADER
                        || profession == RotVProfession.DIPLOMAT) {
                    yield "MERCHANT: +Trade XP, +Wealth";
                } else {
                    yield "MERCHANT: +Wealth";
                }
            }
            case ARCANE -> "ARCANE: +XP gain, +Wealth";
            case MILITARIZED -> "MILITARIZED: +Armor (Guards), +Food penalty";
            default -> "";
        };
    }
}
