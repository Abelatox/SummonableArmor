package online.kingdomkeys.summonablearmor.client;


import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;
import online.kingdomkeys.summonablearmor.ModItems;
import online.kingdomkeys.summonablearmor.SummonableArmor;

import java.util.function.Supplier;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, SummonableArmor.MODID);

    public static final Supplier<MenuType<PauldronMenu>> PAULDRON = createMenu("pauldron", PauldronMenu::fromNetwork);

    public static <M extends AbstractContainerMenu> Supplier<MenuType<M>> createMenu(String name, IContainerFactory<M> container) {
        return MENUS.register(name, () -> new MenuType<>(container, FeatureFlags.DEFAULT_FLAGS));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerGUIFactories(RegisterMenuScreensEvent event) {
        event.register(ModMenus.PAULDRON.get(), PauldronScreen::new);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.ItemHandler.ITEM, (object, context) -> new SummonerInventory(object), ModItems.terra_Shoulder.get());
    }

}
