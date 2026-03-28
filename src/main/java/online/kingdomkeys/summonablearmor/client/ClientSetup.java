package online.kingdomkeys.summonablearmor.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import online.kingdomkeys.summonablearmor.SummonableArmor;
import org.lwjgl.glfw.GLFW;

@Mod(value = SummonableArmor.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SummonableArmor.MODID, value = Dist.CLIENT)
public class ClientSetup {
    public enum Keybinds {
        SUMMON("key." + SummonableArmor.MODID + ".summon", GLFW.GLFW_KEY_H);

        public final KeyMapping keybinding;
        public final String translationKey;

        Keybinds(String name, int defaultKey) {
            keybinding = new KeyMapping(name, defaultKey, "key." + SummonableArmor.MODID);
            translationKey = name;
        }

        public KeyMapping getKeybind() {
            return keybinding;
        }

        private boolean isPressed() {
            return keybinding.consumeClick();
        }
    }

    private Keybinds getPressedKey() {
        for (Keybinds key : Keybinds.values())
            if (key.isPressed())
                return key;
        return null;
    }

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        for(Keybinds key : Keybinds.values()) {
            event.register(key.keybinding);
        }
    }

    public ClientSetup(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        SummonableArmor.LOGGER.info("HELLO FROM CLIENT SETUP");
        SummonableArmor.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        NeoForge.EVENT_BUS.register(new InputHandler());
        NeoForge.EVENT_BUS.register(new ClientEvents());

    }
}
