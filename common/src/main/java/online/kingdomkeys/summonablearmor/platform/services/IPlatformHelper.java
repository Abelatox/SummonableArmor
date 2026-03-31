package online.kingdomkeys.summonablearmor.platform.services;

import net.minecraft.client.KeyMapping;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import online.kingdomkeys.summonablearmor.menu.IInventory;
import online.kingdomkeys.summonablearmor.network.Packet;

import java.util.function.Supplier;

public interface IPlatformHelper {

    String getPlatformName();

    boolean isModLoaded(String modId);
    boolean isDevelopmentEnvironment();

    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item);
    <T extends CreativeModeTab> Supplier<T> registerTab(String id, Supplier<T> tab);
    CreativeModeTab.Builder newTabBuilder();
    <T extends DataComponentType<?>> Supplier<T> registerComponent(String id, Supplier<T> component);
    <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenu(String id, IMenuFactory<T> menu);

    KeyMapping registerKeybind(String name, int defaultKey, String category);

    IInventory getInventory(ItemStack stack);

    ItemStack findInCurios(Player player);

    void sendTo(Packet packet, ServerPlayer player);
    void sendToAll(Packet packet, MinecraftServer server);
    void sendToServer(Packet packet);

    void registerS2C(Packet packet);
    void registerC2S(Packet packet);

    interface IMenuFactory<T extends AbstractContainerMenu> extends MenuType.MenuSupplier<T> {
        T create(int syncId, Inventory inventory, RegistryFriendlyByteBuf data);

        default T create(int syncId, Inventory inventory) {
            return this.create(syncId, inventory, null);
        }
    }
}