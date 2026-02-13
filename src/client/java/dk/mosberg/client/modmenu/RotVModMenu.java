package dk.mosberg.client.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dk.mosberg.client.config.RotVConfigScreen;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class RotVModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            if (!FabricLoader.getInstance().isModLoaded("cloth-config")) {
                if (parent != null) {
                    return parent;
                }
                return new Screen(Text.literal("Rise of the Villagers")) {};
            }
            return RotVConfigScreen.create(parent);
        };
    }
}
