package dk.mosberg.villager;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;

public enum RotVProfession {
    NONE, GUARD, HUNTER, ENGINEER, ARCHITECT, CARAVAN_LEADER, MAGE, DIPLOMAT;

    public static RotVProfession fromVanilla(RegistryEntry<VillagerProfession> profession) {
        if (profession == null) {
            return NONE;
        }
        Identifier id = profession.getKey().map(RegistryKey::getValue).orElse(null);
        if (id == null) {
            return NONE;
        }
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
