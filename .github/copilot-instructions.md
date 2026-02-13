# Copilot Instructions

## Project overview

- Fabric mod for Minecraft 1.21.11 using Loom with split source sets (common + client).
- Java 21 is required (see Gradle config); build uses Gradle wrapper.

## Source layout

- Common code: src/main/java/dk/mosberg
- Client-only code: src/client/java/dk/mosberg/client
- Assets and mod metadata: src/main/resources (including assets/rotv)

## Entrypoints and wiring

- Entrypoints are defined in gradle.properties and templated into fabric.mod.json during processResources.
- Main entrypoint: dk.mosberg.RotV (implements ModInitializer).
- Client entrypoint: dk.mosberg.client.RotVClient (implements ClientModInitializer).
- Mod Menu entrypoint exists (dk.mosberg.client.modmenu.RotVModMenu), currently empty.
- Use RotV.LOGGER for logging (mod id is "rotv").

## Build and test

- Gradle wrapper is the canonical workflow; Fabric Loom handles remap/build tasks.
- JUnit 5 is configured via Gradle test task (useJUnitPlatform).

## Conventions to follow

- Keep client-only logic under src/client to respect splitEnvironmentSourceSets.
- Update gradle.properties values if changing mod metadata; fabric.mod.json is generated from those properties.
- Keep mod id consistent with RotV.MOD_ID and gradle.properties mod_id.
