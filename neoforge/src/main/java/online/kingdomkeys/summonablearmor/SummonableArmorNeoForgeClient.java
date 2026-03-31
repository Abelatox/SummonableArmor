package online.kingdomkeys.summonablearmor;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import online.kingdomkeys.summonablearmor.client.ClientEvents;
import online.kingdomkeys.summonablearmor.client.ClientSetup;
import online.kingdomkeys.summonablearmor.client.SummonerScreen;

@Mod(value = SummonableArmor.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SummonableArmor.MODID, value = Dist.CLIENT)
public class SummonableArmorNeoForgeClient {

    public SummonableArmorNeoForgeClient(ModContainer container) {}

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {}

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(ClientSetup.SUMMON);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        ClientEvents.onClientTickPost();
    }

    @SubscribeEvent
    public static void toolTip(ItemTooltipEvent event) {
        ClientEvents.tooltipEvent(event.getItemStack(), event.getToolTip(), event.getFlags());
    }

    public static void registerGUIFactories(RegisterMenuScreensEvent event) {
        event.register(ModMenus.PAULDRON.get(), SummonerScreen::new);
    }

}
