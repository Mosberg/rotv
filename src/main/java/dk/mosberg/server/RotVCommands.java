package dk.mosberg.server;

import java.util.Locale;
import java.util.UUID;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import dk.mosberg.defense.Fortification;
import dk.mosberg.defense.FortificationManager;
import dk.mosberg.quests.Quest;
import dk.mosberg.quests.QuestManager;
import dk.mosberg.village.VillagePersistentState;
import dk.mosberg.village.VillageProfile;
import dk.mosberg.villager.RotVProfession;
import dk.mosberg.villager.RotVProfessionProgression;
import dk.mosberg.villager.RotVVillagerData;
import dk.mosberg.villager.RotVVillagerDataUtil;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.UuidArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public final class RotVCommands {
    private static final SimpleCommandExceptionType NOT_VILLAGER =
            new SimpleCommandExceptionType(Text.literal("Target is not a villager"));
    private static final SimpleCommandExceptionType BAD_PROFESSION =
            new SimpleCommandExceptionType(Text.literal("Unknown profession"));

    private RotVCommands() {}

    public static void init() {
        CommandRegistrationCallback.EVENT.register(RotVCommands::register);
    }

    private static void register(CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment env) {
        dispatcher.register(CommandManager.literal("rotv")
                .then(CommandManager.literal("villager")
                        .then(CommandManager
                                .literal("info")
                                .then(CommandManager
                                        .argument("target", EntityArgumentType.entity())
                                        .executes(ctx -> infoVillager(
                                                ctx.getSource(),
                                                EntityArgumentType.getEntity(ctx, "target")))))
                        .then(CommandManager.literal("setname").then(CommandManager
                                .argument("target", EntityArgumentType.entity())
                                .then(CommandManager.argument("first", StringArgumentType.word())
                                        .then(CommandManager
                                                .argument("last", StringArgumentType.word())
                                                .executes(ctx -> setName(ctx.getSource(),
                                                        EntityArgumentType.getEntity(ctx, "target"),
                                                        StringArgumentType.getString(ctx, "first"),
                                                        StringArgumentType.getString(ctx,
                                                                "last")))))))
                        .then(CommandManager.literal("setfamily").then(CommandManager
                                .argument("target", EntityArgumentType.entity())
                                .then(CommandManager.argument("family", UuidArgumentType.uuid())
                                        .executes(ctx -> setFamily(ctx.getSource(),
                                                EntityArgumentType.getEntity(ctx, "target"),
                                                UuidArgumentType.getUuid(ctx, "family"))))))
                        .then(CommandManager.literal("setprofession").then(CommandManager
                                .argument("target", EntityArgumentType.entity())
                                .then(CommandManager
                                        .argument("profession", StringArgumentType.word())
                                        .executes(ctx -> setProfession(ctx.getSource(),
                                                EntityArgumentType.getEntity(ctx, "target"),
                                                StringArgumentType.getString(ctx, "profession"))))))
                        .then(CommandManager.literal("setlevel").then(CommandManager
                                .argument("target", EntityArgumentType.entity())
                                .then(CommandManager
                                        .argument("level", IntegerArgumentType.integer(0))
                                        .executes(ctx -> setLevel(ctx.getSource(),
                                                EntityArgumentType.getEntity(ctx, "target"),
                                                IntegerArgumentType.getInteger(ctx, "level"))))))
                        .then(CommandManager.literal("setxp").then(CommandManager
                                .argument("target", EntityArgumentType.entity())
                                .then(CommandManager.argument("xp", IntegerArgumentType.integer(0))
                                        .executes(ctx -> setXp(ctx.getSource(),
                                                EntityArgumentType.getEntity(ctx, "target"),
                                                IntegerArgumentType.getInteger(ctx, "xp")))))))
                .then(CommandManager.literal("village").then(CommandManager.literal("info")
                        .then(CommandManager.argument("villager", EntityArgumentType.entity())
                                .executes(ctx -> infoVillage(ctx.getSource(),
                                        EntityArgumentType.getEntity(ctx, "villager")))))
                        .then(CommandManager.literal("addwealth").then(CommandManager
                                .argument("villager", EntityArgumentType.entity())
                                .then(CommandManager
                                        .argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> addWealth(ctx.getSource(),
                                                EntityArgumentType.getEntity(ctx, "villager"),
                                                IntegerArgumentType.getInteger(ctx, "amount"))))))
                        .then(CommandManager.literal("addfood").then(CommandManager
                                .argument("villager", EntityArgumentType.entity())
                                .then(CommandManager
                                        .argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> addFood(ctx.getSource(),
                                                EntityArgumentType.getEntity(ctx, "villager"),
                                                IntegerArgumentType.getInteger(ctx, "amount"))))))
                        .then(CommandManager.literal("addmaterials").then(CommandManager
                                .argument("villager", EntityArgumentType.entity())
                                .then(CommandManager
                                        .argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> addMaterials(ctx.getSource(),
                                                EntityArgumentType.getEntity(ctx, "villager"),
                                                IntegerArgumentType.getInteger(ctx, "amount")))))))
                .then(CommandManager.literal("quest").then(CommandManager.literal("list")
                        .then(CommandManager.argument("villager", EntityArgumentType.entity())
                                .executes(ctx -> listQuests(ctx.getSource(),
                                        EntityArgumentType.getEntity(ctx, "villager")))))
                        .then(CommandManager.literal("activate").then(CommandManager
                                .argument("villager", EntityArgumentType.entity())
                                .then(CommandManager.argument("questId", StringArgumentType.word())
                                        .executes(ctx -> activateQuest(ctx.getSource(),
                                                EntityArgumentType.getEntity(ctx, "villager"),
                                                StringArgumentType.getString(ctx, "questId"))))))
                        .then(CommandManager.literal("progress").then(CommandManager
                                .argument("villager", EntityArgumentType.entity())
                                .then(CommandManager
                                        .argument("questType", StringArgumentType.word())
                                        .then(CommandManager
                                                .argument("amount", IntegerArgumentType.integer())
                                                .executes(ctx -> progressQuest(ctx.getSource(),
                                                        EntityArgumentType.getEntity(ctx,
                                                                "villager"),
                                                        StringArgumentType.getString(ctx,
                                                                "questType"),
                                                        IntegerArgumentType.getInteger(ctx,
                                                                "amount"))))))))
                .then(CommandManager.literal("fortification").then(CommandManager.literal("list")
                        .then(CommandManager.argument("villager", EntityArgumentType.entity())
                                .executes(ctx -> listFortifications(ctx.getSource(),
                                        EntityArgumentType.getEntity(ctx, "villager")))))
                        .then(CommandManager.literal("build").then(CommandManager
                                .argument("villager", EntityArgumentType.entity())
                                .then(CommandManager.argument("type", StringArgumentType.word())
                                        .executes(ctx -> buildFortification(ctx.getSource(),
                                                EntityArgumentType.getEntity(ctx, "villager"),
                                                StringArgumentType.getString(ctx, "type"))))))
                        .then(CommandManager.literal("repair").then(CommandManager
                                .argument("villager", EntityArgumentType.entity())
                                .then(CommandManager.argument("type", StringArgumentType.word())
                                        .executes(ctx -> repairFortification(ctx.getSource(),
                                                EntityArgumentType.getEntity(ctx, "villager"),
                                                StringArgumentType.getString(ctx, "type"))))))
                        .then(CommandManager.literal("upgrade").then(CommandManager
                                .argument("villager", EntityArgumentType.entity())
                                .then(CommandManager.argument("type", StringArgumentType.word())
                                        .executes(ctx -> upgradeFortification(ctx.getSource(),
                                                EntityArgumentType.getEntity(ctx, "villager"),
                                                StringArgumentType.getString(ctx, "type"))))))));
    }

    private static VillagerEntity requireVillager(Entity entity) throws CommandSyntaxException {
        if (!(entity instanceof VillagerEntity villager)) {
            throw NOT_VILLAGER.create();
        }
        return villager;
    }

    private static int infoVillager(ServerCommandSource source, Entity entity)
            throws CommandSyntaxException {
        VillagerEntity villager = requireVillager(entity);
        RotVVillagerData data = RotVVillagerDataUtil.getData(villager);
        RotVProfessionProgression.syncProfession(villager);
        RotVProfession profession = data.getProfession().getProfession();
        String name = data.getFirstName() + " " + data.getLastName();
        String family = data.getFamilyId() == null ? "unknown" : data.getFamilyId().toString();
        source.sendFeedback(() -> Text.literal("Villager: " + name), false);
        source.sendFeedback(() -> Text.literal("Profession: " + profession.name()), false);
        source.sendFeedback(() -> Text.literal("Level: " + data.getProfession().getLevel() + " (XP "
                + data.getProfession().getXp() + ")"), false);
        source.sendFeedback(() -> Text.literal("Family: " + family), false);
        return 1;
    }

    private static int setName(ServerCommandSource source, Entity entity, String first, String last)
            throws CommandSyntaxException {
        VillagerEntity villager = requireVillager(entity);
        RotVVillagerData data = RotVVillagerDataUtil.getData(villager);
        data.setFirstName(capitalize(first));
        data.setLastName(capitalize(last));
        if (data.getFamilyId() == null) {
            data.setFamilyId(UUID.randomUUID());
        }
        RotVVillagerDataUtil.refreshDisplayName(villager);
        source.sendFeedback(() -> Text.literal("Updated villager name."), true);
        return 1;
    }

    private static int setFamily(ServerCommandSource source, Entity entity, UUID familyId)
            throws CommandSyntaxException {
        VillagerEntity villager = requireVillager(entity);
        RotVVillagerData data = RotVVillagerDataUtil.getData(villager);
        data.setFamilyId(familyId);
        source.sendFeedback(() -> Text.literal("Updated family ID."), true);
        return 1;
    }

    private static int setProfession(ServerCommandSource source, Entity entity, String value)
            throws CommandSyntaxException {
        VillagerEntity villager = requireVillager(entity);
        RotVProfession profession = parseProfession(value);
        RotVVillagerData data = RotVVillagerDataUtil.getData(villager);
        data.getProfession().setProfession(profession, true);
        RotVProfessionProgression.applyPerks(villager);
        source.sendFeedback(() -> Text.literal("Updated profession."), true);
        return 1;
    }

    private static int setLevel(ServerCommandSource source, Entity entity, int level)
            throws CommandSyntaxException {
        VillagerEntity villager = requireVillager(entity);
        RotVVillagerData data = RotVVillagerDataUtil.getData(villager);
        data.getProfession().setLevel(level);
        RotVProfessionProgression.applyPerks(villager);
        source.sendFeedback(() -> Text.literal("Updated profession level."), true);
        return 1;
    }

    private static int setXp(ServerCommandSource source, Entity entity, int xp)
            throws CommandSyntaxException {
        VillagerEntity villager = requireVillager(entity);
        RotVVillagerData data = RotVVillagerDataUtil.getData(villager);
        data.getProfession().setXp(xp);
        source.sendFeedback(() -> Text.literal("Updated profession XP."), true);
        return 1;
    }

    private static int infoVillage(ServerCommandSource source, Entity entity)
            throws CommandSyntaxException {
        VillagerEntity villager = requireVillager(entity);
        ServerWorld world = source.getWorld();
        BlockPos anchor = RotVVillagerDataUtil.resolveVillageAnchor(villager);
        String id = world.getRegistryKey().getValue() + "@" + anchor.toShortString();
        VillagePersistentState state = VillagePersistentState.get(world);
        VillageProfile profile = state.getOrCreate(id, anchor);
        source.sendFeedback(() -> Text.literal("Village " + profile.getId()), false);
        source.sendFeedback(() -> Text.literal("Population: " + profile.getPopulation()), false);
        source.sendFeedback(() -> Text.literal("Tier: " + profile.getTier().name()), false);
        source.sendFeedback(() -> Text.literal("Food: " + profile.getFood() + " Materials: "
                + profile.getMaterials() + " Wealth: " + profile.getWealth()), false);
        return 1;
    }

    private static int addWealth(ServerCommandSource source, Entity entity, int amount)
            throws CommandSyntaxException {
        VillageProfile profile = getVillageProfile(source, entity);
        profile.setWealth(Math.max(0, profile.getWealth() + amount));
        source.sendFeedback(() -> Text.literal("Wealth updated."), true);
        return 1;
    }

    private static int addFood(ServerCommandSource source, Entity entity, int amount)
            throws CommandSyntaxException {
        VillageProfile profile = getVillageProfile(source, entity);
        profile.setFood(Math.max(0, profile.getFood() + amount));
        source.sendFeedback(() -> Text.literal("Food updated."), true);
        return 1;
    }

    private static int addMaterials(ServerCommandSource source, Entity entity, int amount)
            throws CommandSyntaxException {
        VillageProfile profile = getVillageProfile(source, entity);
        profile.setMaterials(Math.max(0, profile.getMaterials() + amount));
        source.sendFeedback(() -> Text.literal("Materials updated."), true);
        return 1;
    }

    private static VillageProfile getVillageProfile(ServerCommandSource source, Entity entity)
            throws CommandSyntaxException {
        VillagerEntity villager = requireVillager(entity);
        ServerWorld world = source.getWorld();
        BlockPos anchor = RotVVillagerDataUtil.resolveVillageAnchor(villager);
        String id = world.getRegistryKey().getValue() + "@" + anchor.toShortString();
        VillagePersistentState state = VillagePersistentState.get(world);
        VillageProfile profile = state.getOrCreate(id, anchor);
        state.markDirty();
        return profile;
    }

    private static int listQuests(ServerCommandSource source, Entity entity)
            throws CommandSyntaxException {
        VillageProfile profile = getVillageProfile(source, entity);
        java.util.List<Quest> quests = QuestManager.getActiveQuests(profile.getId());

        if (quests.isEmpty()) {
            source.sendFeedback(() -> Text.literal("No quests for this village."), false);
            return 0;
        }

        source.sendFeedback(() -> Text.literal("=== Village Quests ==="), false);
        for (Quest quest : quests) {
            String status = quest.getStatus().name();
            String progress = quest.getProgress() + "/" + quest.getObjective().target();
            source.sendFeedback(() -> Text.literal(quest.getId() + " - " + quest.getName() + " ["
                    + status + "] (" + progress + ")"), false);
            source.sendFeedback(() -> Text.literal("  " + quest.getDescription()), false);
        }
        return quests.size();
    }

    private static int activateQuest(ServerCommandSource source, Entity entity, String questId)
            throws CommandSyntaxException {
        VillageProfile profile = getVillageProfile(source, entity);
        QuestManager.activateQuest(profile.getId(), questId);
        source.sendFeedback(() -> Text.literal("Quest activated: " + questId), true);
        return 1;
    }

    private static int progressQuest(ServerCommandSource source, Entity entity, String questTypeStr,
            int amount) throws CommandSyntaxException {
        VillageProfile profile = getVillageProfile(source, entity);

        try {
            Quest.QuestType questType = Quest.QuestType.valueOf(questTypeStr.toUpperCase());
            QuestManager.progressQuest(profile.getId(), questType, amount);
            source.sendFeedback(() -> Text.literal("Quest progress: " + questType + " +" + amount),
                    true);
            return 1;
        } catch (IllegalArgumentException e) {
            source.sendFeedback(() -> Text.literal("Unknown quest type: " + questTypeStr), false);
            return 0;
        }
    }

    // ==================== Fortification Commands ====================

    private static int listFortifications(ServerCommandSource source, Entity entity)
            throws CommandSyntaxException {
        VillageProfile profile = getVillageProfile(source, entity);
        java.util.List<Fortification> fortifications =
                FortificationManager.getFortifications(profile.getId());

        if (fortifications.isEmpty()) {
            source.sendFeedback(() -> Text.literal("No fortifications in this village."), false);
            return 0;
        }

        source.sendFeedback(() -> Text.literal("=== Village Fortifications ==="), false);
        float totalDefense = FortificationManager.getTotalDefenseBonus(profile.getId());
        source.sendFeedback(
                () -> Text.literal("Total Defense Bonus: " + (totalDefense * 100) + "%"), false);

        for (Fortification fort : fortifications) {
            String status = fort.isDestroyed() ? " [DESTROYED]" : "";
            String integrity = String.format("%.0f%%", fort.getIntegrity() * 100);
            source.sendFeedback(() -> Text.literal(fort.getType().name() + " (Lvl "
                    + fort.getLevel() + ")" + status + " - Integrity: " + integrity), false);
            source.sendFeedback(() -> Text
                    .literal("  Defense: +" + (fort.getEffectiveDefenseBonus() * 100) + "%"),
                    false);
        }
        return fortifications.size();
    }

    private static int buildFortification(ServerCommandSource source, Entity entity, String typeStr)
            throws CommandSyntaxException {
        VillageProfile profile = getVillageProfile(source, entity);

        try {
            Fortification.FortificationType type =
                    Fortification.FortificationType.valueOf(typeStr.toUpperCase());

            if (FortificationManager.hasFortificationType(profile.getId(), type)) {
                source.sendFeedback(
                        () -> Text.literal("Village already has this fortification type!"), false);
                return 0;
            }

            boolean success =
                    FortificationManager.buildFortification(profile.getId(), type, profile);
            if (success) {
                source.sendFeedback(() -> Text.literal("Built " + type.name() + "!"), true);
                return 1;
            } else {
                source.sendFeedback(
                        () -> Text.literal("Not enough resources! Need " + type.getMaterialCost()
                                + " materials and " + type.getWealthCost() + " wealth."),
                        false);
                return 0;
            }
        } catch (IllegalArgumentException e) {
            source.sendFeedback(() -> Text.literal("Unknown fortification type: " + typeStr
                    + ". Valid types: WOODEN_WALL, STONE_WALL, WATCHTOWER, IRON_GATE, BARRICADE"),
                    false);
            return 0;
        }
    }

    private static int repairFortification(ServerCommandSource source, Entity entity,
            String typeStr) throws CommandSyntaxException {
        VillageProfile profile = getVillageProfile(source, entity);

        try {
            Fortification.FortificationType type =
                    Fortification.FortificationType.valueOf(typeStr.toUpperCase());

            boolean success =
                    FortificationManager.repairFortification(profile.getId(), type, profile);
            if (success) {
                source.sendFeedback(() -> Text.literal("Repaired " + type.name() + "!"), true);
                return 1;
            } else {
                int matCost = (int) (type.getMaterialCost() * 0.25);
                int wealthCost = (int) (type.getWealthCost() * 0.25);
                source.sendFeedback(() -> Text.literal("Cannot repair! Need " + matCost
                        + " materials and " + wealthCost
                        + " wealth, or fortification doesn't exist/already at full integrity."),
                        false);
                return 0;
            }
        } catch (IllegalArgumentException e) {
            source.sendFeedback(() -> Text.literal("Unknown fortification type: " + typeStr),
                    false);
            return 0;
        }
    }

    private static int upgradeFortification(ServerCommandSource source, Entity entity,
            String typeStr) throws CommandSyntaxException {
        VillageProfile profile = getVillageProfile(source, entity);

        try {
            Fortification.FortificationType type =
                    Fortification.FortificationType.valueOf(typeStr.toUpperCase());

            boolean success =
                    FortificationManager.upgradeFortification(profile.getId(), type, profile);
            if (success) {
                source.sendFeedback(() -> Text.literal("Upgraded " + type.name() + "!"), true);
                return 1;
            } else {
                int matCost = (int) (type.getMaterialCost() * 1.5);
                int wealthCost = (int) (type.getWealthCost() * 1.5);
                source.sendFeedback(
                        () -> Text.literal("Cannot upgrade! Need " + matCost + " materials and "
                                + wealthCost
                                + " wealth, or fortification doesn't exist/already at max level."),
                        false);
                return 0;
            }
        } catch (IllegalArgumentException e) {
            source.sendFeedback(() -> Text.literal("Unknown fortification type: " + typeStr),
                    false);
            return 0;
        }
    }

    // ==================== Utility Methods ====================

    private static RotVProfession parseProfession(String value) throws CommandSyntaxException {
        try {
            return RotVProfession.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw BAD_PROFESSION.create();
        }
    }

    private static String capitalize(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return trimmed;
        }
        return trimmed.substring(0, 1).toUpperCase(Locale.ROOT)
                + trimmed.substring(1).toLowerCase(Locale.ROOT);
    }
}
