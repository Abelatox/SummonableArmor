package online.kingdomkeys.summonablearmor;

import net.neoforged.fml.loading.FMLEnvironment;
import online.kingdomkeys.summonablearmor.client.ModMenus;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(SummonableArmor.MODID)
public class SummonableArmor {
    public static final String MODID = "summonablearmor";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.summonablearmor"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.armorSummoner.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.armorSummoner.get());
            }).build());

    public SummonableArmor(IEventBus modEventBus, ModContainer modContainer) {
        ModComponents.COMPONENTS.register(modEventBus);

        //ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);

        CREATIVE_MODE_TABS.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modEventBus.addListener(ModMenus::registerCapabilities);
        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(ModMenus::registerGUIFactories);
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
       // if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            //event.accept(EXAMPLE_BLOCK_ITEM);
        //}
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
