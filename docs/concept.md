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
