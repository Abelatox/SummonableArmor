package online.kingdomkeys.summonablearmor.menu;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class PauldronSlot extends Slot {

    private static Container emptyContainer = new SimpleContainer(0);
    protected final int index;
    private final IInventory inventory;

    public PauldronSlot(IInventory inventory, int slot, int x, int y) {
        super(emptyContainer, slot, x, y);
        this.inventory = inventory;
        this.index = slot;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.isEmpty() ? false : this.inventory.itemValid(this.index, stack);
    }

    @Override
    public ItemStack getItem() {
        return this.inventory.getStackInSlot(this.index);
    }

    @Override
    public void set(ItemStack stack) {
        this.inventory.setStackInSlot(this.index, stack);
        this.setChanged();
    }

    @Override
    public void onQuickCraft(ItemStack oldStack, ItemStack newStack) {}

    @Override
    public int getMaxStackSize() {
        return this.inventory.getSlotLimit(this.index);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return Math.min(stack.getMaxStackSize(), this.inventory.getSlotLimit(this.index));
    }

    @Override
    public boolean mayPickup(Player player) {
        return !this.inventory.extractItem(this.index, 1, true).isEmpty();
    }

    @Override
    public ItemStack remove(int amount) {
        return this.inventory.extractItem(this.index, amount, false);
    }

    static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{InventoryMenu.EMPTY_ARMOR_SLOT_HELMET, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE, InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS, InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS};

    @Override
    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        return Pair.of(InventoryMenu.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS[index]);
    }
}
