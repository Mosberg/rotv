package dk.mosberg.villager;

import net.minecraft.util.math.random.Random;

public final class RotVVillagerNameGenerator {
    private static final String[] FIRST_NAMES = {"Alden", "Bram", "Cora", "Darin", "Elia", "Faye",
            "Galen", "Hera", "Iris", "Joren", "Kael", "Lena", "Mira", "Nolan", "Orin", "Perrin",
            "Quinn", "Rhea", "Soren", "Talia", "Ulric", "Vera", "Wyatt", "Xara", "Yara", "Zane",

            "Aeris", "Brin", "Caelan", "Dara", "Eamon", "Freya", "Gideon", "Hale", "Isla", "Jace",
            "Kira", "Lorin", "Maela", "Neris", "Olan", "Priya", "Riven", "Selene", "Theron", "Una",
            "Vale", "Wren", "Xen", "Yorin", "Zara",

            "Arlen", "Bryn", "Celia", "Dax", "Eira", "Finn", "Gareth", "Hollis", "Inara", "Jorah",
            "Kestrel", "Lyra", "Marek", "Nadia", "Oriel", "Pax", "Rowan", "Sable", "Torin", "Ursa",
            "Vesper", "Willa", "Xylo", "Yestin", "Zarek"};


    private static final String[] LAST_NAMES = {"Ashford", "Briar", "Cobble", "Dunwell", "Elder",
            "Fairwind", "Grain", "Hollow", "Ironwood", "Juniper", "Kestrel", "Lark", "Meadow",
            "North", "Oak", "Pine", "Quarry", "Ridge", "Stone", "Thatch", "Umber", "Vale", "Willow",
            "Yarrow", "Zephyr",

            "Amberfall", "Blackthorn", "Cinderbrook", "Duskfield", "Evermere", "Frosthelm",
            "Glenford", "Highridge", "Ivydale", "Jasperwood", "Kingswell", "Longmere", "Mooncrest",
            "Nightbloom", "Oakhurst", "Proudmoor", "Quickwater", "Ravenhall", "Stormvale",
            "Timberfall", "Underleaf", "Vinehart", "Wintermere", "Yonderbrook", "Zenthorne",

            "Aldercrest", "Brightmoor", "Cloudspire", "Dunmarsh", "Evershade", "Fallowmere",
            "Goldbranch", "Hearthwick", "Ironvale", "Jadehollow", "Keenridge", "Loomfield",
            "Mossford", "Narrowbrook", "Oatfield", "Pinemarch", "Reedshore", "Silvercrest",
            "Thornwall", "Umberfell", "Vastwood", "Wheatmoor", "Yarrowfen", "Zincaster"};


    private RotVVillagerNameGenerator() {}

    public static String randomFirstName(Random random) {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    public static String randomLastName(Random random) {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }
}
