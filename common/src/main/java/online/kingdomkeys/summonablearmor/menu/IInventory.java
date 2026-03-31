package online.kingdomkeys.summonablearmor.menu;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public interface IInventory {

    int SIZE = 4;

    default boolean itemValid(int slot, ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armorItem) {
            return switch (slot) {
                case 0 -> armorItem.getType().equals(ArmorItem.Type.HELMET);
                case 1 -> armorItem.getType().equals(ArmorItem.Type.CHESTPLATE);
                case 2 -> armorItem.getType().equals(ArmorItem.Type.LEGGINGS);
                case 3 -> armorItem.getType().equals(ArmorItem.Type.BOOTS);
                default -> false;
            };
        }
        return stack.getItem() instanceof ArmorItem || stack.isEmpty();
    }

    void setStackInSlot(int slot, ItemStack stack);
    int getSlots();
    ItemStack getStackInSlot(int slot);
    int getSlotLimit(int slot);

    ItemStack extractItem(int slot, int quantity, boolean simulate);
}
