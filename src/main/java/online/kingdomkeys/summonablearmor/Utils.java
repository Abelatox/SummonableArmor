package online.kingdomkeys.summonablearmor;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.capabilities.Capabilities;
import online.kingdomkeys.summonablearmor.client.SummonerInventory;
import online.kingdomkeys.summonablearmor.integration.CuriosCompat;
import java.util.UUID;

public class Utils {

    public static ItemStack findBestSummonItem(Player player) {
        //If curios is loaded
        ItemStack best = null;
        if (ModList.get().isLoaded("curios")) {
            try {
                best = CuriosCompat.findInCurios(player);
            } catch (Throwable ignored) {}
        }

        if(best != null) //If curios had one return it
            return best;

        // If not in Curios fallback to inventory
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() instanceof SummonerItem) {
                return stack;
            }
        }

        return null;
    }

    public static int getFreeSlotsForPlayer(Player player) {
        int free = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (ItemStack.matches(ItemStack.EMPTY, stack)) {
                free++;
            }
        }
        return free;
    }

    public static void swapStack(Inventory inv, int stack1, int stack2) {
        ItemStack tempStack = inv.getItem(stack2);
        inv.setItem(stack2, inv.getItem(stack1));
        inv.setItem(stack1, tempStack);
    }

    public static void armourTick(ItemStack stack, Entity entity, Level level, int slot) {
        if (entity instanceof Player player && !level.isClientSide) {
            ItemStack armorItem = Utils.findBestSummonItem(player);
            UUID armorUUID = armorItem.getItem() != null ? Utils.getArmorID(armorItem) : null;

            if (Utils.hasArmorID(stack)) {
                if (Utils.getArmorID(stack).equals(armorUUID)) { //If UUID is the same check slots
                    //If the armor item is ticking outside an armor slot
                    if (!(player.getInventory().getItem(36) == stack || player.getInventory().getItem(37) == stack || player.getInventory().getItem(38) == stack || player.getInventory().getItem(39) == stack)) {
                        Utils.desummonArmour(player, stack, slot, true, true);
                    }
                } else {//If UUID is different remove
                    Utils.desummonArmour(player, stack, slot, false, true);
                }
            }
        }
    }

    public static void desummonArmour(Player player, ItemStack stack, int slot, boolean sameUUID, boolean playSound) {
        if (sameUUID) {
            SummonerInventory summonerInventory = (SummonerInventory) Utils.findBestSummonItem(player).getCapability(Capabilities.ItemHandler.ITEM);
            if (stack.getItem() instanceof ArmorItem armorItem) {
                stack.remove(ModComponents.ARMOR_ID);
                summonerInventory.setStackInSlot(armorItem.getType().ordinal(), stack);
            }
        }
        player.getInventory().setItem(slot, ItemStack.EMPTY);
        if (playSound) {
            player.level().playSound(null, player.position().x(),player.position().y(),player.position().z(), SoundEvents.ARMOR_EQUIP_NETHERITE, SoundSource.MASTER, 1f, 1.0f);
        }
    }

    public static boolean hasArmorID(ItemStack stack) {
        if (stack.getItem() instanceof SummonerItem || stack.getItem() instanceof ArmorItem) {
            return stack.has(ModComponents.ARMOR_ID);
        }
        return false;
    }

    public static UUID getArmorID(ItemStack stack) {
        if (hasArmorID(stack)) {
            return stack.get(ModComponents.ARMOR_ID);
        }
        return null;
    }

}