package online.kingdomkeys.summonablearmor.platform;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import online.kingdomkeys.summonablearmor.ItemHandler;
import online.kingdomkeys.summonablearmor.SummonableArmor;
import online.kingdomkeys.summonablearmor.integration.AccessoriesCompat;
import online.kingdomkeys.summonablearmor.menu.IInventory;
import online.kingdomkeys.summonablearmor.network.Packet;
import online.kingdomkeys.summonablearmor.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return registerSupplier(BuiltInRegistries.ITEM, id, item);
    }

    @Override
    public <T extends CreativeModeTab> Supplier<T> registerTab(String id, Supplier<T> tab) {
        return registerSupplier(BuiltInRegistries.CREATIVE_MODE_TAB, id, tab);
    }

    @Override
    public CreativeModeTab.Builder newTabBuilder() {
        return FabricItemGroup.builder();
    }

    @Override
    public <T extends DataComponentType<?>> Supplier<T> registerComponent(String id, Supplier<T> component) {
        return registerSupplier(BuiltInRegistries.DATA_COMPONENT_TYPE, id, component);
    }

    @Override
    public <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenu(String id, IMenuFactory<T> menu) {
        return registerSupplier(BuiltInRegistries.MENU, id, () -> new MenuType<>(menu, FeatureFlags.DEFAULT_FLAGS));
    }

    @Override
    public KeyMapping registerKeybind(String name, int defaultKey, String category) {
        return KeyBindingHelper.registerKeyBinding(new KeyMapping(name, InputConstants.Type.KEYSYM, defaultKey, category));
    }

    @Override
    public IInventory getInventory(ItemStack stack) {
        return new ItemHandler(stack);
    }

    @Override
    public ItemStack findInCurios(Player player) {
        return AccessoriesCompat.findInAccessories(player);
    }

    @Override
    public void sendToServer(Packet packet) {
        ClientPlayNetworking.send(packet);
    }

    @Override
    public void registerS2C(Packet packet) {
        PayloadTypeRegistry.playS2C().register(packet.type(), packet.getCodec());
        ClientPlayNetworking.registerGlobalReceiver(packet.type(), (payload, context) -> ((Packet)payload).handle(context.player()));
    }

    @Override
    public void registerC2S(Packet packet) {
        PayloadTypeRegistry.playC2S().register(packet.type(), packet.getCodec());
        ServerPlayNetworking.registerGlobalReceiver(packet.type(), (payload, context) -> ((Packet)payload).handle(context.player()));
    }

    @Override
    public void sendToAll(Packet packet, MinecraftServer server) {
        for (ServerPlayer player : PlayerLookup.all(server)) {
            ServerPlayNetworking.send(player, packet);
        }
    }

    @Override
    public void sendTo(Packet packet, ServerPlayer player) {
        ServerPlayNetworking.send(player, packet);
    }

    private static <T, R extends Registry<? super T>> Supplier<T> registerSupplier(R registry, String id, Supplier<T> object) {
        final T registeredObject = Registry.register((Registry<T>)registry, ResourceLocation.fromNamespaceAndPath(SummonableArmor.MODID, id), object.get());

        return () -> registeredObject;
    }
}
