package online.kingdomkeys.summonablearmor.platform;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.network.PacketDistributor;
import online.kingdomkeys.summonablearmor.SummonableArmorNeoForge;
import online.kingdomkeys.summonablearmor.menu.IInventory;
import online.kingdomkeys.summonablearmor.integration.CuriosCompat;
import online.kingdomkeys.summonablearmor.network.Packet;
import online.kingdomkeys.summonablearmor.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.function.Supplier;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public String getEnvironmentName() {
        return IPlatformHelper.super.getEnvironmentName();
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return SummonableArmorNeoForge.ITEMS.register(id, item);
    }

    @Override
    public <T extends CreativeModeTab> Supplier<T> registerTab(String id, Supplier<T> tab) {
        return SummonableArmorNeoForge.CREATIVE_MODE_TABS.register(id, tab);
    }

    @Override
    public CreativeModeTab.Builder newTabBuilder() {
        return CreativeModeTab.builder().withTabsBefore(CreativeModeTabs.COMBAT);
    }

    @Override
    public <T extends DataComponentType<?>> Supplier<T> registerComponent(String id, Supplier<T> component) {
        return SummonableArmorNeoForge.COMPONENTS.register(id, component);
    }

    @Override
    public <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenu(String id, IMenuFactory<T> menu) {
        return SummonableArmorNeoForge.MENUS.register(id, () -> new MenuType<>(menu, FeatureFlags.DEFAULT_FLAGS));
    }

    @Override
    public ItemStack findInCurios(Player player) {
        return CuriosCompat.findInCurios(player);
    }

    @Override
    public void sendTo(Packet packet, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, packet);
    }

    @Override
    public void sendToAll(Packet packet, MinecraftServer server) {
        PacketDistributor.sendToAllPlayers(packet);
    }

    @Override
    public void sendToServer(Packet packet) {
        PacketDistributor.sendToServer(packet);
    }

    @Override
    public void registerS2C(Packet packet) {
        SummonableArmorNeoForge.s2cPacketsToRegister.add(packet);
    }

    @Override
    public void registerC2S(Packet packet) {
        SummonableArmorNeoForge.c2sPacketsToRegister.add(packet);
    }

    @Override
    public KeyMapping registerKeybind(String name, int defaultKey, String category) {
        return new KeyMapping(name, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, defaultKey, category);
    }

    @Override
    public IInventory getInventory(ItemStack stack) {
        return (IInventory) stack.getCapability(Capabilities.ItemHandler.ITEM);
    }
}