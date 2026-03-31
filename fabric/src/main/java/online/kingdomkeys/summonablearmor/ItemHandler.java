package online.kingdomkeys.summonablearmor;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import online.kingdomkeys.summonablearmor.menu.IInventory;

public class ItemHandler implements IInventory {

    protected final DataComponentType<ItemContainerContents> component;
    protected final ItemStack parent;

    public ItemHandler(ItemStack parent) {
        this.component = ModComponents.INVENTORY.get();
        this.parent = parent;
    }


    @Override
    public int getSlots() {
        return SIZE;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        ItemContainerContents contents = this.getContents();
        return this.getStackFromContents(contents, slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.validateSlotIndex(slot);
        if (!this.isItemValid(slot, stack)) {
            throw new RuntimeException("Inavlid stack " + stack + " for slot (" + slot + ")");
        } else {
            ItemContainerContents contents = this.getContents();
            ItemStack existing = this.getStackFromContents(contents, slot);
            if (!ItemStack.matches(stack, existing)) {
                this.updateContents(contents, stack, slot);
            }
        }
    }

    public ItemStack insertItem(int slot, ItemStack toInsert, boolean simulate) {
        this.validateSlotIndex(slot);
        if (toInsert.isEmpty()) {
            return ItemStack.EMPTY;
        } else if (!this.isItemValid(slot, toInsert)) {
            return toInsert;
        } else {
            ItemContainerContents contents = this.getContents();
            ItemStack existing = this.getStackFromContents(contents, slot);
            int insertLimit = Math.min(this.getSlotLimit(slot), toInsert.getMaxStackSize());
            if (!existing.isEmpty()) {
                if (!ItemStack.isSameItemSameComponents(toInsert, existing)) {
                    return toInsert;
                }

                insertLimit -= existing.getCount();
            }

            if (insertLimit <= 0) {
                return toInsert;
            } else {
                int inserted = Math.min(insertLimit, toInsert.getCount());
                if (!simulate) {
                    this.updateContents(contents, toInsert.copyWithCount(existing.getCount() + inserted), slot);
                }

                return toInsert.copyWithCount(toInsert.getCount() - inserted);
            }
        }
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        this.validateSlotIndex(slot);
        if (amount == 0) {
            return ItemStack.EMPTY;
        } else {
            ItemContainerContents contents = this.getContents();
            ItemStack existing = this.getStackFromContents(contents, slot);
            if (existing.isEmpty()) {
                return ItemStack.EMPTY;
            } else {
                int toExtract = Math.min(amount, existing.getCount());
                if (!simulate) {
                    this.updateContents(contents, existing.copyWithCount(existing.getCount() - toExtract), slot);
                }

                return existing.copyWithCount(toExtract);
            }
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    public boolean isItemValid(int slot, ItemStack stack) {
        return itemValid(slot, stack);
    }

    protected void onContentsChanged(int slot, ItemStack oldStack, ItemStack newStack) {}

    protected ItemContainerContents getContents() {
        return this.parent.getOrDefault(this.component, ItemContainerContents.EMPTY);
    }

    protected ItemStack getStackFromContents(ItemContainerContents contents, int slot) {
        this.validateSlotIndex(slot);
        return Utils.getSlots(contents) <= slot ? ItemStack.EMPTY : Utils.getStackInSlot(contents, slot);
    }

    protected void updateContents(ItemContainerContents contents, ItemStack stack, int slot) {
        this.validateSlotIndex(slot);
        NonNullList<ItemStack> list = NonNullList.withSize(Math.max(Utils.getSlots(contents), this.getSlots()), ItemStack.EMPTY);
        contents.copyInto(list);
        ItemStack oldStack = list.get(slot);
        list.set(slot, stack);
        this.parent.set(this.component, ItemContainerContents.fromItems(list));
        this.onContentsChanged(slot, oldStack, stack);
    }

    protected final void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= this.getSlots()) {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + this.getSlots() + ")");
        }
    }
}
