package dk.mosberg.client;

import net.fabricmc.api.ClientModInitializer;
import dk.mosberg.client.hud.RotVVillagerHudOverlay;

public class RotVClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        RotVVillagerHudOverlay.init();
    }
}
