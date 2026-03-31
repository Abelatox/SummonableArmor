package online.kingdomkeys.summonablearmor;

import online.kingdomkeys.summonablearmor.network.PacketHandler;
import online.kingdomkeys.summonablearmor.platform.services.IPlatformHelper;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public class SummonableArmor {
    public static final String MODID = "summonablearmor";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final IPlatformHelper COMMON = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        SummonableArmor.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }


    public static final Supplier<CreativeModeTab> Creative_TAB = SummonableArmor.COMMON.registerTab("creative_tab", () -> SummonableArmor.COMMON.newTabBuilder()
            .title(Component.translatable("itemGroup.summonablearmor"))
            .icon(() -> ModItems.armorSummoner.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.armorSummoner.get());
            }).build());

    public static void init() {
        ModItems.init();
        ModComponents.init();
        ModMenus.init();
        PacketHandler.init();
    }
}
