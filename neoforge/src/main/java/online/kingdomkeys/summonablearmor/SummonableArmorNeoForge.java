package online.kingdomkeys.summonablearmor;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import online.kingdomkeys.summonablearmor.network.Packet;

import java.util.ArrayList;
import java.util.List;

import static online.kingdomkeys.summonablearmor.SummonableArmor.MODID;

@Mod(MODID)
public class SummonableArmorNeoForge {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MODID);

    public SummonableArmorNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        SummonableArmor.init();
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        COMPONENTS.register(modEventBus);
        MENUS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::registerPackets);
        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(SummonableArmorNeoForgeClient::registerGUIFactories);
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        EntityEvents.onLivingDeathEvent(event.getEntity());
    }

    @SubscribeEvent
    public void onItemDropped(EntityJoinLevelEvent event) {
        event.setCanceled(EntityEvents.onItemDropped(event.getEntity()));
    }

    @SubscribeEvent
    public void onItemToss(ItemTossEvent event) {
        EntityEvents.onItemToss(event.getEntity(), event.getPlayer());
    }

    public static PayloadRegistrar registrar;

    public static final List<Packet> s2cPacketsToRegister = new ArrayList<>();
    public static final List<Packet> c2sPacketsToRegister = new ArrayList<>();

    public void registerPackets(RegisterPayloadHandlersEvent event) {
        registrar = event.registrar(MODID);

        s2cPacketsToRegister.forEach(packet -> {
            registrar.playToClient(packet.type(), packet.getCodec(), (customPacketPayload, iPayloadContext) -> handlePacket(packet, iPayloadContext));
        });
        s2cPacketsToRegister.clear();

        c2sPacketsToRegister.forEach(packet -> {
            registrar.playToServer(packet.type(), packet.getCodec(), (customPacketPayload, iPayloadContext) -> handlePacket(packet, iPayloadContext));
        });
        c2sPacketsToRegister.clear();
    }

    public static <T extends Packet> void handlePacket(final T data, final IPayloadContext context) {
        context.enqueueWork(() -> data.handle(context.player())).exceptionally(e -> {
            SummonableArmor.LOGGER.warn("Packet \"{}\" handling failed, something is likely broken", data.type());
            return null;
        });
        Packet reply = data.reply(context.player());
        if (reply != null) {
            context.reply(reply);
        }
    }


    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.ItemHandler.ITEM, (object, context) -> new SummonableInventory(object), ModItems.armorSummoner.get());
    }
}
