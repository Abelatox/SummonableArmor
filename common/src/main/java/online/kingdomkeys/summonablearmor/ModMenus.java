package online.kingdomkeys.summonablearmor;


import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import online.kingdomkeys.summonablearmor.menu.SummonerMenu;
import online.kingdomkeys.summonablearmor.platform.services.IPlatformHelper;

import java.util.function.Supplier;

public class ModMenus {

    public static void init() {}

    public static final Supplier<MenuType<SummonerMenu>> PAULDRON = createMenu("pauldron", SummonerMenu::fromNetwork);

    public static <M extends AbstractContainerMenu> Supplier<MenuType<M>> createMenu(String name, IPlatformHelper.IMenuFactory<M> menuFactory) {
        return SummonableArmor.COMMON.registerMenu(name, menuFactory);
    }

//    @OnlyIn(Dist.CLIENT)
//    public static void registerGUIFactories(RegisterMenuScreensEvent event) {
//        event.register(ModMenus.PAULDRON.get(), SummonerScreen::new);
//    }

}
