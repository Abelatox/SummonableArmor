package online.kingdomkeys.summonablearmor.integration;

import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import online.kingdomkeys.summonablearmor.SummonerItem;

public class AccessoriesCompat {
    public static ItemStack findInAccessories(Player player) {
        AccessoriesCapability capability = AccessoriesCapability.get(player);
        if (capability != null) {
            for (SlotEntryReference slot : capability.getAllEquipped()) {
                if (slot.stack().getItem() instanceof SummonerItem) {
                    return slot.stack();
                }
            }
        }
        return null;
    }
}
