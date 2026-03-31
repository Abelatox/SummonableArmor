package online.kingdomkeys.summonablearmor;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.gui.screens.MenuScreens;
import online.kingdomkeys.summonablearmor.client.ClientEvents;
import online.kingdomkeys.summonablearmor.client.ClientSetup;
import online.kingdomkeys.summonablearmor.client.SummonerScreen;

public class SummonableArmorFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientSetup.init();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientEvents.onClientTickPost();
        });
        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
            ClientEvents.tooltipEvent(stack, lines, tooltipType);
        });
        MenuScreens.register(ModMenus.PAULDRON.get(), SummonerScreen::new);
    }
}
