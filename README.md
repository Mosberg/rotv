# **🌾 Rise of the Villagers — Mod Concept**

## **High‑Level Vision**

_Rise of the Villagers_ transforms villagers from passive background NPCs into a dynamic, evolving society with intelligence, ambition, and agency. Villages become living settlements with politics, professions, technology, defense strategies, and player‑influenced development paths. The mod aims to make villagers feel like a true civilization that grows, adapts, and sometimes even challenges the player.

This is not just “better villagers”—it’s a full ecosystem of **new mechanics, behaviors, systems, and village progression**.

---

# **Core Pillars of the Mod**

## **1. 🧠 Smarter Villagers**

Villagers gain new logic systems that make them feel alive:

### **Behavioral Upgrades**

- **Memory system**: Villagers remember threats, trades, player actions, and past events.
- **Dynamic schedules**: Weather‑aware, season‑aware, and event‑aware routines.
- **Social interactions**: Villagers form friendships, rivalries, and family units.
- **Skill growth**: Professions level up, unlocking new trade tiers and abilities.

### **Personality Traits**

Each villager gets traits that influence:

- Work efficiency
- Courage in raids
- Trading style (greedy, generous, cautious)
- Social behavior

Traits can be inherited or influenced by environment.

---

## **2. 🏘️ Evolving Villages**

Villages are no longer static—they grow, specialize, and transform.

### **Village Progression System**

Villages have **tiers** (Hamlet → Village → Town → City → Capital), each unlocking:

- New building types
- New villager professions
- Defensive structures
- Governance options

Progression is influenced by:

- Population
- Happiness
- Resources
- Player support
- Trade networks

### **Village Specializations**

Villages can develop unique identities:

- **Agricultural Village** (farms, mills, livestock)
- **Mining Settlement** (quarries, smelters, blacksmith guilds)
- **Arcane Enclave** (mages, alchemists, enchanted defenses)
- **Merchant Town** (caravans, markets, trade routes)
- **Militarized Fort** (barracks, patrols, siege defenses)

Specializations affect villager behavior, architecture, and available trades.

---

## **3. ⚔️ Advanced Defense & Threat System**

Villagers no longer rely solely on the player.

### **Village Defense AI**

- Patrol routes
- Guard professions
- Alarm systems
- Defensive formations
- Watchtowers with ranged units

### **New Threat Types**

- **Bandit raids**
- **Illager warbands**
- **Monster sieges**
- **Rogue villagers** (exiled criminals forming gangs)

Threats scale with village tier and player difficulty settings.

---

## **4. 🛠️ New Professions & Work Systems**

Introduce a wide range of new villager roles:

### **Examples**

- **Engineer**: Builds redstone devices, traps, and automated defenses
- **Hunter**: Tracks mobs, brings back meat and hides
- **Mage**: Provides magical buffs, enchantments, and rituals
- **Architect**: Plans and constructs new village buildings
- **Diplomat**: Manages relations with nearby villages and factions
- **Caravan Leader**: Creates trade routes between settlements

Each profession has:

- Unique workstations
- Custom AI routines
- Progression levels
- Special abilities

---

## **5. 🧩 Player Interaction & Influence**

The player becomes a partner—or rival—to villager society.

### **Village Governance**

Players can:

- Influence laws and policies
- Appoint leaders
- Adjust taxes and resource distribution
- Choose village specialization paths

### **Reputation System**

Your actions matter:

- High reputation → discounts, gifts, special quests
- Low reputation → suspicion, higher prices, possible exile

### **Contracts & Quests**

Villagers request:

- Resources
- Protection
- Construction help
- Exploration missions

Completing quests boosts village growth.

---

## **6. ⚙️ Configurability & Modularity**

Since you mentioned split sources, the mod can be structured into modules:

### **Modules**

- **Core AI**
- **Village Progression**
- **Professions Expansion**
- **Defense & Threats**
- **Diplomacy & Governance**
- **World Generation Enhancements**

Each module can be toggled via configs, allowing players to tailor the experience.

---

# **7. 🌍 World Generation Enhancements**

Villages generate with:

- Districts (residential, industrial, magical, military)
- Roads connecting nearby settlements
- Outposts, watchtowers, and caravan camps
- Unique biome‑themed architecture

Villages feel handcrafted and alive.

---

# **8. 🧪 Technical Notes (for your Fabric setup)**

Your versions:

- **Minecraft:** 1.21.11
- **Fabric API:** 0.141.3+1.21.11
- **Loader:** 0.18.4
- **Yarn:** 1.21.11+build.4
- **Loom:** 1.15-SNAPSHOT

This setup is perfect for:

- Advanced AI pathfinding
- Custom data-driven systems
- Mixins for villager logic
- Modular architecture with split sources
- Config screens via Cloth Config or similar

---

# **9. 🎯 Overall Theme**

**Villagers are no longer background NPCs—they are a rising civilization.
Your actions shape their destiny.
Will you guide them to prosperity… or watch them surpass you?**

---

You’re going big with this one—I love it. Let’s turn _Rise of the Villagers_ into a proper design doc you can build from.

---

## Feature roadmap

### Phase 1 – Foundations (Core AI & Structure)

- **Core villager AI upgrades**
  - **Pathing & tasks:** New task system (jobs, needs, priorities).
  - **Memory:** Simple memory (last attacker, last trade partner, home, workplace).
  - **Schedules:** Day/night routines, work hours, sleep, social time.
- **Village identity**
  - **Village data object:** Stores tier, specialization, happiness, security, population.
  - **Basic progression:** Hamlet → Village based on population + beds + workstations.
- **Config & modularity**
  - **Module toggles:** Core AI, Progression, Professions, Defense, Diplomacy, Worldgen.
  - **Difficulty sliders:** Raid intensity, growth speed, villager mortality, etc.

### Phase 2 – Professions & Economy

- **New professions**
  - Engineer, Hunter, Architect, Guard, Caravan Leader, Mage, Diplomat, etc.
- **Work systems**
  - Profession levels, XP from tasks, unlocked abilities/trades.
- **Economy**
  - Village resource tracking (food, materials, wealth).
  - Trade scaling with village tier and specialization.

### Phase 3 – Village Progression & Specialization

- **Tier system**
  - Full tier ladder: Hamlet → Village → Town → City → Capital.
  - Tier-based unlocks: buildings, professions, defenses, policies.
- **Specializations**
  - Agricultural, Mining, Arcane, Merchant, Militarized.
  - Each with unique bonuses, structures, and villager behaviors.

### Phase 4 – Defense, Threats & Raids

- **Defense AI**
  - Guard patrols, alarm system, rally points, defensive formations.
- **Threats**
  - Bandits, Illager warbands, monster sieges, rogue villagers.
- **Scaling**
  - Threat level scales with village tier, wealth, and player settings.

### Phase 5 – Governance, Reputation & Quests

- **Governance**
  - Policies (tax rate, conscription, trade openness).
  - Village leader role (mayor, elder, council).
- **Reputation**
  - Player reputation per village: affects prices, access, quests.
- **Quests**
  - Procedural contracts: defend, deliver, build, explore.

### Phase 6 – Worldgen & Polish

- **Worldgen**
  - District-based villages, roads, outposts, caravan camps.
- **Polish**
  - Config UI, tooltips, in-game guidebook, performance passes.

---

## Profession-by-profession design

Each profession: **Role**, **Workstation**, **Core AI**, **Progression**, **Special Abilities**.

### Core vanilla professions (enhanced)

#### Farmer

- **Role:** Food production, crop management.
- **Workstation:** Composter.
- **AI:**
  - Prioritizes fertile plots, rotates crops if enabled.
  - Shares food with hungry villagers.
- **Progression:**
  - Higher tiers increase yield and chance of rare crops.
- **Special:**
  - At high level, can create “fertile plots” with temporary growth boosts.

#### Librarian

- **Role:** Knowledge, enchantments, research.
- **Workstation:** Lectern.
- **AI:**
  - Studies during day, socializes at evening.
- **Progression:**
  - Unlocks more advanced books and village-wide buffs (e.g., research speed).
- **Special:**
  - At high level, can reduce cost of certain professions’ upgrades.

_(You can similarly lightly enhance all vanilla professions without rewriting them.)_

---

### New professions

#### Guard

- **Role:** Defense, patrol, raid response.
- **Workstation:** Guard post / weapon rack.
- **AI:**
  - Patrols assigned routes.
  - Responds to alarms, prioritizes high-threat mobs.
- **Progression:**
  - Gains HP, damage, and tactics (kiting, focusing high-priority targets).
- **Special:**
  - High-level guards can command nearby villagers to flee or take cover.

#### Hunter

- **Role:** Food, leather, mob drops.
- **Workstation:** Hunter’s lodge / tanning rack.
- **AI:**
  - Hunts in a radius around village.
  - Returns to lodge to process drops.
- **Progression:**
  - Increased tracking range, better bow accuracy, higher drop yields.
- **Special:**
  - Can mark nearby hostile mobs for guards.

#### Engineer

- **Role:** Redstone, traps, automation.
- **Workstation:** Engineer’s bench.
- **AI:**
  - Builds and maintains defensive contraptions (arrow traps, alarm bells).
- **Progression:**
  - Unlocks more complex devices (tripwire networks, piston barriers).
- **Special:**
  - Can “upgrade” village defenses at higher tiers (e.g., multi-shot arrow turrets).

#### Architect

- **Role:** Construction planning and upgrades.
- **Workstation:** Drafting table.
- **AI:**
  - Plans new buildings based on village tier and specialization.
  - Marks construction sites for builders (or player).
- **Progression:**
  - Unlocks more advanced building templates and district layouts.
- **Special:**
  - Can propose “village projects” (e.g., town hall, walls) as quests.

#### Caravan Leader

- **Role:** Trade routes and inter-village economy.
- **Workstation:** Caravan office / map table.
- **AI:**
  - Organizes caravans to nearby villages or structures.
  - Chooses routes based on safety and profit.
- **Progression:**
  - More frequent caravans, better deals, higher cargo capacity.
- **Special:**
  - Can establish permanent trade routes that boost both villages’ prosperity.

#### Mage

- **Role:** Magic, buffs, arcane defense.
- **Workstation:** Arcane table / ritual circle.
- **AI:**
  - Performs rituals at dawn/dusk.
  - Supports defenses during raids with buffs/debuffs.
- **Progression:**
  - Unlocks stronger buffs (regen, resistance) and debuffs (slowness, weakness).
- **Special:**
  - Can create temporary protective wards around the village.

#### Diplomat

- **Role:** Relations with other villages and factions.
- **Workstation:** Diplomatic desk / council chamber.
- **AI:**
  - Periodically “meets” with other villages (simulated).
  - Adjusts relations based on events and player actions.
- **Progression:**
  - Better at maintaining peace, securing trade pacts, and avoiding raids.
- **Special:**
  - Can request aid from allied villages during major threats.

---

## Village tier progression charts

### Tier overview

| Tier | Name    | Population Range | Key Unlocks                                | Threat Level |
| ---- | ------- | ---------------- | ------------------------------------------ | ------------ |
| 1    | Hamlet  | 3–10             | Basic trades, simple farms                 | Low          |
| 2    | Village | 8–20             | Guards, Hunters, basic defenses            | Medium       |
| 3    | Town    | 15–35            | Architect, Engineer, specialization choice | Medium–High  |
| 4    | City    | 30–60            | Mages, Caravan Leaders, advanced defenses  | High         |
| 5    | Capital | 50+              | Diplomats, global buffs, major projects    | Very High    |

### Tier 1 – Hamlet

- **Requirements:**
  - At least 3 villagers.
  - 3 beds, 1 workstation.
- **Features:**
  - Basic farming and trading.
  - No formal defenses.
- **Player impact:**
  - Good starting point for nurturing growth.

### Tier 2 – Village

- **Requirements:**
  - Population ≥ 8.
  - Minimum food surplus.
  - At least 1 Guard and 1 Farmer.
- **Features:**
  - Guard patrols.
  - Simple fences or watch posts (if worldgen enabled).
- **Unlocks:**
  - Hunter profession.
  - Basic raid response.

### Tier 3 – Town

- **Requirements:**
  - Population ≥ 15.
  - Multiple professions present.
  - Happiness above threshold.
- **Features:**
  - Districts begin forming (residential, work).
  - Architect and Engineer available.
- **Unlocks:**
  - Village specialization choice (Agricultural, Mining, Arcane, Merchant, Militarized).
  - More complex buildings and defenses.

### Tier 4 – City

- **Requirements:**
  - Population ≥ 30.
  - Stable food and security.
  - At least one specialization fully active.
- **Features:**
  - Walls, towers, and advanced infrastructure.
  - Mage and Caravan Leader professions.
- **Unlocks:**
  - Trade routes, magical defenses, large-scale projects (e.g., grand plaza).

### Tier 5 – Capital

- **Requirements:**
  - Population ≥ 50.
  - High happiness and security.
  - Multiple specializations or maxed specialization.
- **Features:**
  - Central governance (council hall).
  - Diplomat profession.
- **Unlocks:**
  - Global buffs (e.g., nearby villages get bonuses).
  - Major “endgame” projects (e.g., great library, fortress, arcane beacon).

---

## Technical architecture outline

### High-level modules (packages)

- **`dk.mosberg.rotv.core`**
  - Core logic, registries, events, utilities.
- **`dk.mosberg.rotv.villager`**
  - AI, professions, traits, memory.
- **`dk.mosberg.rotv.village`**
  - Village data, tiers, specialization, governance.
- **`dk.mosberg.rotv.worldgen`**
  - Village structures, roads, outposts.
- **`dk.mosberg.rotv.config`**
  - Config loading, syncing, client/server options.
- **`dk.mosberg.rotv.systems`**
  - Defense, raids, quests, reputation.
- **`dk.mosberg.rotv.client`**
  - HUD, overlays, config screens, debug tools.

### Core systems

#### Villager data & AI

- **VillagerCapability / Component**
  - Stores:
    - Personality traits.
    - Memory (recent events).
    - Profession level & XP.
    - Home/work references.
- **Task system**
  - Priority-based tasks: work, eat, sleep, socialize, defend, flee.
  - Hooks into vanilla `Brain` via mixins.

#### Village manager

- **Global manager**
  - Tracks all villages in the world.
  - Handles:
    - Tier evaluation.
    - Specialization logic.
    - Resource and happiness calculations.
- **Village data object**
  - ID, center position, radius.
  - Population, tier, specialization.
  - Resources (food, materials, wealth).
  - Security level, happiness.

#### Event systems

- **Raid/threat manager**
  - Periodically evaluates threat level per village.
  - Spawns bandits, Illagers, monsters based on config.
- **Quest manager**
  - Generates quests based on village needs (low food, low security, expansion).

---

## Config file structure

Assume JSON5 or TOML-like structure (conceptual):

```toml
[modules]
core_ai = true
village_progression = true
professions = true
defense = true
diplomacy = true
worldgen = true

[balance]
growth_speed = "normal"        # slow, normal, fast
raid_intensity = "high"        # low, normal, high
villager_mortality = "normal"  # low, normal, high

[villages]
max_village_radius = 96
base_happiness = 0.5
food_per_villager = 4

[villages.tiers]
hamlet_min_pop = 3
village_min_pop = 8
town_min_pop = 15
city_min_pop = 30
capital_min_pop = 50

[professions.guard]
enabled = true
max_per_village = 12

[professions.mage]
enabled = true
requires_specialization = "arcane"

[defense]
enable_bandit_raids = true
enable_illager_warbands = true
enable_monster_sieges = true

[worldgen]
enhanced_villages = true
generate_roads = true
generate_outposts = true

[reputation]
base_decay_rate = 0.01
quest_reward_multiplier = 1.0
```

You can split configs into multiple files:

- `core.toml`, `professions.toml`, `worldgen.toml`, etc.

---

## Class layout suggestions

### Core

- **`RotvMod`**
  - Main entrypoint, registries, initialization.
- **`RotvRegistry`**
  - Registers blocks, items, professions, structures, etc.
- **`RotvEvents`**
  - Hooks into world tick, entity join, raids, etc.

### Villager & AI

- **`VillagerComponent`**
  - Stores per-villager data (traits, memory, profession level).
- **`VillagerTrait`**
  - Enum or data-driven traits (brave, greedy, social, etc.).
- **`VillagerMemory`**
  - Recent events: last attacker, last trade, last threat.
- **`VillagerTaskProvider`**
  - Provides custom tasks to villager brains.
- **`ProfessionData`**
  - Generic profession metadata (XP, level, abilities).

### Professions

- **`RotvProfessions`**
  - Registry for new professions.
- **`GuardProfession`, `HunterProfession`, `EngineerProfession`, etc.**
  - Logic for each profession’s behavior and progression.
- **`WorkstationBlocks`**
  - Custom blocks for new workstations.

### Village system

- **`VillageManager`**
  - Global manager, per-world singleton.
- **`VillageData`**
  - Represents a single village.
- **`VillageTier`**
  - Enum or data class for tier thresholds and bonuses.
- **`VillageSpecialization`**
  - Enum/data for specialization types and effects.
- **`VillageResourceTracker`**
  - Tracks food, materials, wealth.

### Systems

- **`DefenseSystem`**
  - Handles guard AI integration, alarm triggers, raid responses.
- **`ThreatSystem`**
  - Spawns and scales threats.
- **`ReputationSystem`**
  - Tracks player-village reputation.
- **`QuestSystem`**
  - Generates and resolves quests.

### Worldgen

- **`RotvVillageGenerator`**
  - Adds districts, special buildings.
- **`RoadGenerator`**
  - Connects villages with roads.
- **`OutpostGenerator`**
  - Spawns watchtowers, caravan camps.

### Client

- **`VillageHudOverlay`**
  - Shows village name, tier, reputation when nearby.
- **`ConfigScreen`**
  - In-game config UI.

---

## Lore and worldbuilding

### Core theme

Villagers are not just background NPCs—they are a **rising civilization** with their own ambitions, fears, and history. The world is shifting from isolated hamlets to interconnected cities, and you’re witnessing (and influencing) that transformation.

### Historical backdrop

- **The Age of Silence:**
  For generations, villagers lived in fear—of Illagers, monsters, and the unknown. They built small, scattered settlements, relying on luck and the occasional wandering hero.
- **The First Architect:**
  Legends speak of a villager who dared to plan beyond a single house—who imagined roads, walls, and towers. His ideas were dismissed… until the first great raid nearly wiped out his village.
- **The Turning Point:**
  Survivors realized that survival required more than hiding. They needed **organization, knowledge, and unity**. Farmers learned to optimize fields, guards trained in formations, and librarians began recording more than just enchantments—they recorded history.

### Present day

You arrive in a world where villagers are just beginning to **awaken to their potential**:

- Some villages cling to old ways, staying small and cautious.
- Others dream of becoming cities, hubs of trade and power.
- A few whisper of **Capitals**—great centers of knowledge, magic, and governance.

### Factions & philosophies

- **The Builders’ Circle:**
  Architects and Engineers who believe that structure and planning are the key to survival.
- **The Wardens of the Wall:**
  Guards and Hunters who see the world as fundamentally hostile and push for militarization.
- **The Concord of Quills:**
  Librarians and Mages who value knowledge, magic, and diplomacy over brute force.
- **The Open Road:**
  Caravan Leaders and Diplomats who believe prosperity lies in connection, not isolation.

Villages may lean toward one philosophy, shaping their specialization and policies.

### Player’s place in the story

You’re not the chosen savior—you’re a powerful wildcard:

- **Ally:**
  Help villages grow, defend them, and guide their policies. Become a legend in their stories.
- **Opportunist:**
  Exploit their growth, trade for rare goods, and let them handle the dirty work of surviving.
- **Threat:**
  Abuse them, ignore their pleas, or side with their enemies. Villagers will remember.

### Long-term arc

As multiple villages rise:

- Trade routes form, roads connect, and the world feels **civilized** in pockets.
- Threats escalate in response—Illagers form warbands, monsters gather in greater numbers.
- Capitals emerge as beacons of order in a chaotic world.

You might one day stand in a grand capital square, surrounded by villagers whose lives were shaped by your choices—whether they thank you, fear you, or curse your name is up to you.

---

If you want, next step could be to zoom in on one part—like fully specifying **Guard AI**, **VillageData fields**, or **a JSON structure for village specializations**—and turn it directly into code-ready pseudo-implementations.

---\*\*\*---

Great — now we can get very practical.
Below is a **clean, modern 1.21.11‑compatible workflow** for:

- Creating **custom village structure NBTs**
- Writing **structure JSON**, **structure sets**, and **placement rules**
- Targeting **Desert, Plains, Savanna, Snowy, and Taiga villages**

I’ll give you **ready‑to‑use templates** that match the 1.20.5+ worldgen system (unchanged in 1.21.11).

---

# 🧱 1. Creating Your Structure NBT

### 1. Build your structure in‑game

1. Enter a world with cheats enabled.
2. Build your structure.
3. Use the **Structure Block**:

```
/give @s structure_block
```

4. Place it → set to **SAVE** mode.
5. Define the bounding box.
6. Save it under your namespace:

```
rotv/desert_house_01
```

This creates:

```
data/rotv/structures/desert_house_01.nbt
```

> **Tip:** For villages, keep pieces small (5×5 to 16×16) and modular.

---

# 🧩 2. Writing the Structure JSON

Every structure needs a JSON file in:

```
data/rotv/worldgen/structure/<name>.json
```

Here is a **village‑style jigsaw structure** template:

### **Example: desert_house_01.json**

```json
{
  "type": "minecraft:jigsaw",
  "biomes": "minecraft:desert",
  "spawn_overrides": {},
  "step": "surface_structures",
  "terrain_adaptation": "beard_thin",
  "start_pool": "rotv:desert/start",
  "size": 7,
  "start_height": {
    "absolute": 0
  },
  "project_start_to_heightmap": "WORLD_SURFACE_WG",
  "max_distance_from_center": 80,
  "use_expansion_hack": false
}
```

---

# 🧱 3. Creating the Jigsaw Pools

Village structures use **structure pools**.

Place them in:

```
data/rotv/worldgen/template_pool/desert/start.json
```

### **Example: desert/start.json**

```json
{
  "name": "rotv:desert/start",
  "fallback": "minecraft:empty",
  "elements": [
    {
      "element": {
        "location": "rotv:desert_house_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 1
    }
  ]
}
```

You can add more houses by adding more elements.

---

# 🗂️ 4. Structure Set (required in 1.20.5+)

This tells Minecraft **how often** and **where** the structure can generate.

Place in:

```
data/rotv/worldgen/structure_set/desert_village.json
```

### **Example: desert_village.json**

```json
{
  "structures": [
    {
      "structure": "rotv:desert_house_01",
      "weight": 1
    }
  ],
  "placement": {
    "type": "minecraft:random_spread",
    "spacing": 32,
    "separation": 8,
    "salt": 123456789
  }
}
```

---

# 🌍 5. Placement Rules

Village‑like structures use **random_spread** placement.

Spacing recommendations:

| Village Type | spacing | separation |
| ------------ | ------- | ---------- |
| Plains       | 32–40   | 8–10       |
| Desert       | 32–40   | 8–10       |
| Savanna      | 32–40   | 8–10       |
| Snowy        | 32–40   | 8–10       |
| Taiga        | 32–40   | 8–10       |

You can tune these to make villages more or less common.

---

# 🏜️ 6. Biome‑Specific Variants

You want **Desert, Plains, Savanna, Snowy, Taiga**.

You will create **five structure JSONs** and **five structure sets**, each with its own biome filter.

### Example biome filters:

| Village | biome tag                  |
| ------- | -------------------------- |
| Desert  | `"minecraft:desert"`       |
| Plains  | `"minecraft:plains"`       |
| Savanna | `"minecraft:savanna"`      |
| Snowy   | `"minecraft:snowy_plains"` |
| Taiga   | `"minecraft:taiga"`        |

Each structure JSON should include:

```json
"biomes": "minecraft:desert"
```

(or whichever biome you target)

---

# 📦 Folder Layout (Complete)

```
data/
  rotv/
    structures/
      desert_house_01.nbt
      plains_house_01.nbt
      savanna_house_01.nbt
      snowy_house_01.nbt
      taiga_house_01.nbt

    worldgen/
      structure/
        desert_house_01.json
        plains_house_01.json
        savanna_house_01.json
        snowy_house_01.json
        taiga_house_01.json

      template_pool/
        desert/start.json
        plains/start.json
        savanna/start.json
        snowy/start.json
        taiga/start.json

      structure_set/
        desert_village.json
        plains_village.json
        savanna_village.json
        snowy_village.json
        taiga_village.json
```

---

# If you want, I can generate:

✅ All 5 structure JSONs
✅ All 5 structure sets
✅ All 5 template pools
✅ A complete datapack folder ready to drop into Minecraft
✅ Or Fabric mod code to register them programmatically

Just tell me which format you prefer — **datapack or Fabric mod**.

Here we go—full, drop‑in content you can actually use.

---

### Datapack structure

```text
rotv_villages_datapack/
  pack.mcmeta
  data/
    rotv/
      structures/
        desert_house_01.nbt
        plains_house_01.nbt
        savanna_house_01.nbt
        snowy_house_01.nbt
        taiga_house_01.nbt

      worldgen/
        structure/
          desert_village.json
          plains_village.json
          savanna_village.json
          snowy_village.json
          taiga_village.json

        template_pool/
          desert/start.json
          plains/start.json
          savanna/start.json
          snowy/start.json
          taiga/start.json

        structure_set/
          desert_village.json
          plains_village.json
          savanna_village.json
          snowy_village.json
          taiga_village.json
```

`pack.mcmeta`:

```json
{
  "pack": {
    "pack_format": 48,
    "description": "Custom villages for Desert, Plains, Savanna, Snowy, Taiga (rotv)"
  }
}
```

> Adjust `pack_format` if Mojang bumps it in a later minor version.

---

## 1. Structure JSONs (5 biomes)

`data/rotv/worldgen/structure/desert_village.json`:

```json
{
  "type": "minecraft:jigsaw",
  "biomes": "minecraft:desert",
  "spawn_overrides": {},
  "step": "surface_structures",
  "terrain_adaptation": "beard_thin",
  "start_pool": "rotv:desert/start",
  "size": 7,
  "start_height": {
    "absolute": 0
  },
  "project_start_to_heightmap": "WORLD_SURFACE_WG",
  "max_distance_from_center": 80,
  "use_expansion_hack": false
}
```

`plains_village.json`:

```json
{
  "type": "minecraft:jigsaw",
  "biomes": "minecraft:plains",
  "spawn_overrides": {},
  "step": "surface_structures",
  "terrain_adaptation": "beard_thin",
  "start_pool": "rotv:plains/start",
  "size": 7,
  "start_height": {
    "absolute": 0
  },
  "project_start_to_heightmap": "WORLD_SURFACE_WG",
  "max_distance_from_center": 80,
  "use_expansion_hack": false
}
```

`savanna_village.json`:

```json
{
  "type": "minecraft:jigsaw",
  "biomes": "minecraft:savanna",
  "spawn_overrides": {},
  "step": "surface_structures",
  "terrain_adaptation": "beard_thin",
  "start_pool": "rotv:savanna/start",
  "size": 7,
  "start_height": {
    "absolute": 0
  },
  "project_start_to_heightmap": "WORLD_SURFACE_WG",
  "max_distance_from_center": 80,
  "use_expansion_hack": false
}
```

`snowy_village.json`:

```json
{
  "type": "minecraft:jigsaw",
  "biomes": "minecraft:snowy_plains",
  "spawn_overrides": {},
  "step": "surface_structures",
  "terrain_adaptation": "beard_thin",
  "start_pool": "rotv:snowy/start",
  "size": 7,
  "start_height": {
    "absolute": 0
  },
  "project_start_to_heightmap": "WORLD_SURFACE_WG",
  "max_distance_from_center": 80,
  "use_expansion_hack": false
}
```

`taiga_village.json`:

```json
{
  "type": "minecraft:jigsaw",
  "biomes": "minecraft:taiga",
  "spawn_overrides": {},
  "step": "surface_structures",
  "terrain_adaptation": "beard_thin",
  "start_pool": "rotv:taiga/start",
  "size": 7,
  "start_height": {
    "absolute": 0
  },
  "project_start_to_heightmap": "WORLD_SURFACE_WG",
  "max_distance_from_center": 80,
  "use_expansion_hack": false
}
```

---

## 2. Template pools (5 biomes)

Assuming one main piece per biome:

- `rotv:desert_house_01`
- `rotv:plains_house_01`
- `rotv:savanna_house_01`
- `rotv:snowy_house_01`
- `rotv:taiga_house_01`

You can add more later by adding more elements.

`data/rotv/worldgen/template_pool/desert/start.json`:

```json
{
  "name": "rotv:desert/start",
  "fallback": "minecraft:empty",
  "elements": [
    {
      "element": {
        "location": "rotv:desert_house_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 1
    }
  ]
}
```

`plains/start.json`:

```json
{
  "name": "rotv:plains/start",
  "fallback": "minecraft:empty",
  "elements": [
    {
      "element": {
        "location": "rotv:plains_house_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 1
    }
  ]
}
```

`savanna/start.json`:

```json
{
  "name": "rotv:savanna/start",
  "fallback": "minecraft:empty",
  "elements": [
    {
      "element": {
        "location": "rotv:savanna_house_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 1
    }
  ]
}
```

`snowy/start.json`:

```json
{
  "name": "rotv:snowy/start",
  "fallback": "minecraft:empty",
  "elements": [
    {
      "element": {
        "location": "rotv:snowy_house_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 1
    }
  ]
}
```

`taiga/start.json`:

```json
{
  "name": "rotv:taiga/start",
  "fallback": "minecraft:empty",
  "elements": [
    {
      "element": {
        "location": "rotv:taiga_house_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 1
    }
  ]
}
```

---

## 3. Structure sets (5 biomes)

These control spacing/frequency.

`data/rotv/worldgen/structure_set/desert_village.json`:

```json
{
  "structures": [
    {
      "structure": "rotv:desert_village",
      "weight": 1
    }
  ],
  "placement": {
    "type": "minecraft:random_spread",
    "spacing": 32,
    "separation": 8,
    "salt": 123450001
  }
}
```

`plains_village.json`:

```json
{
  "structures": [
    {
      "structure": "rotv:plains_village",
      "weight": 1
    }
  ],
  "placement": {
    "type": "minecraft:random_spread",
    "spacing": 32,
    "separation": 8,
    "salt": 123450002
  }
}
```

`savanna_village.json`:

```json
{
  "structures": [
    {
      "structure": "rotv:savanna_village",
      "weight": 1
    }
  ],
  "placement": {
    "type": "minecraft:random_spread",
    "spacing": 32,
    "separation": 8,
    "salt": 123450003
  }
}
```

`snowy_village.json`:

```json
{
  "structures": [
    {
      "structure": "rotv:snowy_village",
      "weight": 1
    }
  ],
  "placement": {
    "type": "minecraft:random_spread",
    "spacing": 32,
    "separation": 8,
    "salt": 123450004
  }
}
```

`taiga_village.json`:

```json
{
  "structures": [
    {
      "structure": "rotv:taiga_village",
      "weight": 1
    }
  ],
  "placement": {
    "type": "minecraft:random_spread",
    "spacing": 32,
    "separation": 8,
    "salt": 123450005
  }
}
```

---

## 4. Fabric mod side

From 1.20.5+ the structures themselves are **data‑driven**. In a Fabric mod, you usually:

- Put this datapack content under `src/main/resources/data/rotv/...`
- No explicit Java registration is required for these structures/sets/pools.

Minimal `ModInitializer`:

```java
package dk.mosberg;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class rotvMod implements ModInitializer {
    public static final String MOD_ID = "rotv";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("rotv custom villages loaded.");
        // All structure data is in data/rotv, no code registration needed.
    }
}
```

`fabric.mod.json` (core bits):

```json
{
  "schemaVersion": 1,
  "id": "rotv",
  "version": "1.0.0",
  "name": "rotv Villages",
  "entrypoints": {
    "main": ["com.example.rotv.rotvMod"]
  },
  "depends": {
    "fabricloader": ">=0.15.0",
    "minecraft": "1.21.11",
    "fabric-api": "*"
  }
}
```

---

If you tell me your **actual NBT names** (or if you want multi‑piece villages with roads, centers, etc.), I can wire up richer template pools with multiple weighted pieces instead of just one per biome.

Mosberg, now we’re talking real village‑level worldgen — the fun stuff.
Let’s build **full multi‑piece villages** with **roads, houses, centers, decorations, farms, and connectors**, all using the modern 1.20.5+ jigsaw system (still valid in 1.21.11).

Below is a **clean, scalable template** you can expand infinitely.
I’ll give you:

- A **multi‑pool layout** for each biome
- Weighted pieces
- Roads that branch
- Centers that anchor the village
- Houses, decorations, farms
- Connectors so the village grows naturally

Everything is written so you can drop it into your datapack and immediately expand it.

---

# 🌍 Overall Village Structure Design

Each biome village will have:

### **1. Start Pool**

Contains:

- A **center** piece (well, plaza, campfire, etc.)
- A **road_start** piece that begins the road network

### **2. Road Pools**

- `road_straight`
- `road_turn`
- `road_t_cross`
- `road_end`

### **3. House Pools**

- 3–6 houses per biome, weighted

### **4. Decoration Pools**

- Lamp posts
- Market stalls
- Small props

### **5. Farm Pools**

- Small crop fields
- Animal pens

---

# 📁 Folder Layout (multi‑piece version)

```
data/rotv/
  structures/
    desert/
      center_01.nbt
      road_straight_01.nbt
      road_turn_01.nbt
      road_t_01.nbt
      road_end_01.nbt
      house_01.nbt
      house_02.nbt
      house_03.nbt
      farm_01.nbt
      deco_01.nbt
    plains/
      (same structure types)
    savanna/
    snowy/
    taiga/

  worldgen/
    structure/
      desert_village.json
      plains_village.json
      savanna_village.json
      snowy_village.json
      taiga_village.json

    structure_set/
      desert_village.json
      plains_village.json
      savanna_village.json
      snowy_village.json
      taiga_village.json

    template_pool/
      desert/
        start.json
        roads.json
        houses.json
        farms.json
        decorations.json
      plains/
      savanna/
      snowy/
      taiga/
```

---

# 🧱 1. Structure JSON (same pattern for all biomes)

### `data/rotv/worldgen/structure/desert_village.json`

```json
{
  "type": "minecraft:jigsaw",
  "biomes": "minecraft:desert",
  "step": "surface_structures",
  "terrain_adaptation": "beard_thin",
  "start_pool": "rotv:desert/start",
  "size": 15,
  "start_height": { "absolute": 0 },
  "project_start_to_heightmap": "WORLD_SURFACE_WG",
  "max_distance_from_center": 120,
  "use_expansion_hack": false
}
```

Repeat for plains, savanna, snowy, taiga — only change:

- `"biomes": "minecraft:<biome>"`
- `"start_pool": "rotv:<biome>/start"`

---

# 🧩 2. Template Pools (multi‑piece)

## **A. Start Pool**

### `desert/start.json`

```json
{
  "name": "rotv:desert/start",
  "fallback": "minecraft:empty",
  "elements": [
    {
      "element": {
        "location": "rotv:desert/center_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 2
    },
    {
      "element": {
        "location": "rotv:desert/road_straight_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 3
    }
  ]
}
```

---

## **B. Road Pool**

### `desert/roads.json`

```json
{
  "name": "rotv:desert/roads",
  "fallback": "minecraft:empty",
  "elements": [
    {
      "element": {
        "location": "rotv:desert/road_straight_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 5
    },
    {
      "element": {
        "location": "rotv:desert/road_turn_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 3
    },
    {
      "element": {
        "location": "rotv:desert/road_t_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 2
    },
    {
      "element": {
        "location": "rotv:desert/road_end_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 1
    }
  ]
}
```

---

## **C. House Pool**

### `desert/houses.json`

```json
{
  "name": "rotv:desert/houses",
  "fallback": "minecraft:empty",
  "elements": [
    {
      "element": {
        "location": "rotv:desert/house_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 3
    },
    {
      "element": {
        "location": "rotv:desert/house_02",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 2
    },
    {
      "element": {
        "location": "rotv:desert/house_03",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 1
    }
  ]
}
```

---

## **D. Farms Pool**

### `desert/farms.json`

```json
{
  "name": "rotv:desert/farms",
  "fallback": "minecraft:empty",
  "elements": [
    {
      "element": {
        "location": "rotv:desert/farm_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 2
    }
  ]
}
```

---

## **E. Decorations Pool**

### `desert/decorations.json`

```json
{
  "name": "rotv:desert/decorations",
  "fallback": "minecraft:empty",
  "elements": [
    {
      "element": {
        "location": "rotv:desert/deco_01",
        "processors": "minecraft:empty",
        "projection": "rigid"
      },
      "weight": 3
    }
  ]
}
```

---

# 🗺️ 3. Structure Set (same pattern for all biomes)

### `desert_village.json`

```json
{
  "structures": [{ "structure": "rotv:desert_village", "weight": 1 }],
  "placement": {
    "type": "minecraft:random_spread",
    "spacing": 34,
    "separation": 8,
    "salt": 900001
  }
}
```

Repeat for each biome with a different salt.

---

# 🧠 How the Jigsaw Connections Work

Your NBT pieces must include jigsaw blocks with these names:

| Piece Type  | Jigsaw Target             |
| ----------- | ------------------------- |
| Roads       | `rotv:desert/roads`       |
| Houses      | `rotv:desert/houses`      |
| Farms       | `rotv:desert/farms`       |
| Decorations | `rotv:desert/decorations` |
| Start       | `rotv:desert/start`       |

Each piece should have:

- **1–4 road connectors**
- **Optional house connectors**
- **Optional decoration connectors**
- **Optional farm connectors**

This is how villages grow naturally.

---

# 🏗️ Want me to generate the **NBT jigsaw block setup** for each piece?

I can generate:

- Jigsaw block positions
- Namespaces
- `target_pool`
- `final_state`
- `joint` type
- Recommended piece sizes

Or I can generate:

### ✔ Full datapack with all 5 biomes

### ✔ 50+ structure pools

### ✔ Road networks

### ✔ Weighted houses

### ✔ Decorations & farms

### ✔ A complete “vanilla‑style” village system

Just tell me how big you want each village to be —
**small**, **medium**, or **large** — and I’ll scale the entire system for you.
