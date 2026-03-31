package online.kingdomkeys.summonablearmor;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import online.kingdomkeys.summonablearmor.menu.IInventory;

import java.util.UUID;

public class EntityEvents {
    public static void onLivingDeathEvent(LivingEntity entity) {
        if(entity instanceof Player player){
            for (ItemStack armour : player.getArmorSlots()) {
                if (armour.getItem() instanceof ArmorItem armorItem) {
                    if (Utils.hasArmorID(armour)) {
                        IInventory pauldronInventory = SummonableArmor.COMMON.getInventory(Utils.findBestSummonItem(player));
                        switch (armorItem.getType()) {
                            case HELMET -> pauldronInventory.setStackInSlot(0, armour);
                            case CHESTPLATE -> pauldronInventory.setStackInSlot(1, armour);
                            case LEGGINGS -> pauldronInventory.setStackInSlot(2, armour);
                            case BOOTS -> pauldronInventory.setStackInSlot(3, armour);
                        }
                        player.level().playSound(null, player.position().x(), player.position().y(), player.position().z(), SoundEvents.ARMOR_EQUIP_NETHERITE, SoundSource.MASTER, 1.0f, 1.0f);
                    }
                }
            }

            for(int i=0; i < player.getInventory().items.size(); i++){
                ItemStack currItem = player.getInventory().items.get(i);
                if(currItem.getItem() == ModItems.armorSummoner){
                    Utils.clearArmor(player, currItem);
                    SummonableArmor.LOGGER.debug("Potentially prevented duping");
                }
            }
        }
    }

    public static boolean onItemDropped(Entity entity) {
        boolean cancelled = false;
        if (entity instanceof ItemEntity itemEntity) {
            ItemStack droppedItem = itemEntity.getItem();
            UUID droppedID = Utils.getArmorID(droppedItem);
            if (droppedID != null && droppedItem.getItem() instanceof ArmorItem) {
                cancelled = true;
            }
        }
        return cancelled;
    }

    public static void onItemToss(ItemEntity entity, Player player) {
        if (entity.getItem().getItem() instanceof ArmorItem armorItem) {
            ItemStack droppedItem = entity.getItem();
            if (Utils.hasArmorID(droppedItem)) {
                IInventory pauldronInventory = SummonableArmor.COMMON.getInventory(Utils.findBestSummonItem(player));
                switch (armorItem.getType()) {
                    case HELMET -> pauldronInventory.setStackInSlot(0, droppedItem);
                    case CHESTPLATE -> pauldronInventory.setStackInSlot(1, droppedItem);
                    case LEGGINGS -> pauldronInventory.setStackInSlot(2, droppedItem);
                    case BOOTS -> pauldronInventory.setStackInSlot(3, droppedItem);
                }
                player.level().playSound(null, player.position().x(), player.position().y(), player.position().z(), SoundEvents.ARMOR_EQUIP_NETHERITE, SoundSource.MASTER, 1.0f, 1.0f);
            }
        }
    }
}
