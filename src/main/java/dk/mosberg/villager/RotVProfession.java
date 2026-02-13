package dk.mosberg.villager;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;

public enum RotVProfession {
    NONE, GUARD, HUNTER, ENGINEER, ARCHITECT, CARAVAN_LEADER, MAGE, DIPLOMAT;

    public static RotVProfession fromVanilla(RegistryKey<VillagerProfession> profession) {
        if (profession == null) {
            return NONE;
        }

        Identifier id = profession.getValue();
        String path = id.getPath();
        if ("none".equals(path) || "nitwit".equals(path)) {
            return NONE;
        }

        return switch (path) {
            case "farmer", "fisherman", "butcher", "shepherd", "fletcher" -> HUNTER;
            case "armorer", "toolsmith", "weaponsmith" -> ENGINEER;
            case "mason", "cartographer", "leatherworker" -> ARCHITECT;
            case "librarian", "cleric" -> MAGE;
            default -> NONE;
        };
    }
}
