# Implemented Features

## Core Systems

- Config loading and in-game config screen via Cloth Config.
- Module toggles for core AI, village progression, professions, defense, diplomacy, worldgen.

## Villager Data

- Persistent villager data (personality, schedule state, home/job site, last trade/attacker).
- Personality traits with random initialization.
- Full names with family IDs and shared last names based on nearby villagers.

## AI and Memory

- Schedule state derived from time and weather.
- Last attacker memory updates on damage events.
- Last trade player memory updates on interaction.

## Professions and Progression

- RotV profession categories mapped from vanilla professions.
- Profession XP from trades, work ticks, and combat damage.
- Profession levels with configurable XP and max level.
- Profession-specific perks (hunter speed, engineer health, guard armor, mage range, caravan/architect speed) with config tuning.
- Specialization-based profession perks:
  - ARCANE: +20% XP gain (all professions)
  - MERCHANT: +15% Trade XP (Caravan Leader, Diplomat)
  - MILITARIZED: +2.0 armor (Guards)

## Village Progression

- Village profile tracking (population, beds, workstations, tier, happiness, security).
- Tier resolution from population thresholds.
- Automatic village specialization based on dominant profession mix.
- Specialization-based village-wide modifiers:
  - AGRICULTURAL: +25% food production, -5% food consumption
  - MINING: +25% materials, +4% security
  - MERCHANT: +25% wealth gain
  - ARCANE: +10% wealth gain
  - MILITARIZED: -10% food consumption, +8% security (penalty/bonus trade-off)

## Economy

- Village resource tracking (food, materials, wealth).
- Production/consumption tick based on scan interval.
- Wealth gain from trades.
- Specialization bonuses for food, materials, and wealth.

## UI and Tooling

- Villager HUD tooltip when looking at villagers (name, profession, level, specialization perks).
- Config UI for profession tuning, economy, progression modifiers, and naming options.

## Commands

- /rotv villager info, setname, setfamily, setprofession, setlevel, setxp.
- /rotv village info, addwealth, addfood, addmaterials.
