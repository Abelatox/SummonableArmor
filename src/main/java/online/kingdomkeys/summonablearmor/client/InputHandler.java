package online.kingdomkeys.summonablearmor.client;

import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import online.kingdomkeys.summonablearmor.network.CSSummonArmor;
import online.kingdomkeys.summonablearmor.network.PacketHandler;

public class InputHandler {
    Minecraft mc;

    public static boolean forceSense = false;

    public InputHandler(){
        mc = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.Key event) {
        if(mc.screen == null) {
            if (event.getAction() == 1) { //We only want to run it once the key has been pressed, not released
                if (event.getKey() == ClientSetup.Keybinds.SUMMON.getKeybind().getKey().getValue()) {
                    PacketHandler.sendToServer(new CSSummonArmor());
                }
            }
        }
    }
}
