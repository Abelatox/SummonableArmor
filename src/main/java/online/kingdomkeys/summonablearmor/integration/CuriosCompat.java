package online.kingdomkeys.summonablearmor.integration;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import online.kingdomkeys.summonablearmor.SummonerItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;

public class CuriosCompat {
    public static ItemStack findInCurios(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .map(inv -> {
                    Optional<ICurioStacksHandler> handler = inv.getStacksHandler("summonablearmor");
                    if (handler != null) {
                        IDynamicStackHandler stacks = handler.get().getStacks();
                        for (int i = 0; i < stacks.getSlots(); i++) {
                            ItemStack stack = stacks.getStackInSlot(i);
                            if (stack.getItem() instanceof SummonerItem) {
                                return stack;
                            }
                        }
                    }
                    return null;
                }).orElse(null);
    }
}