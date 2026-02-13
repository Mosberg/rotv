package dk.mosberg.server;

import java.util.Locale;
import java.util.UUID;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
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
                                                IntegerArgumentType.getInteger(ctx,
                                                        "amount"))))))));
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
