package online.kingdomkeys.summonablearmor;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.items.ComponentItemHandler;
import online.kingdomkeys.summonablearmor.menu.IInventory;

public class SummonableInventory extends ComponentItemHandler implements IInventory {

    public SummonableInventory(MutableDataComponentHolder parent) {
        super(parent, ModComponents.INVENTORY.get(), SIZE);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return itemValid(slot, stack);
    }


}
