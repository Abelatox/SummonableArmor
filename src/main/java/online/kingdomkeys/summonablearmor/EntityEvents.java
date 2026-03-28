package online.kingdomkeys.summonablearmor;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import online.kingdomkeys.summonablearmor.client.SummonerInventory;

import java.util.UUID;

public class EntityEvents {

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        if(event.getEntity() instanceof Player player){
            for(int i=0; i < player.getInventory().items.size(); i++){
                ItemStack currItem = player.getInventory().items.get(i);
                if(currItem.getItem() == ModItems.armorSummoner){
                    Utils.clearArmor(player, currItem);
                    System.out.println("Potentially prevented duping");
                }
            }
        }
    }

    @SubscribeEvent
    public void onItemDropped(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ItemEntity) {
            ItemStack droppedItem = ((ItemEntity)event.getEntity()).getItem();
            UUID droppedID = Utils.getArmorID(droppedItem);
            if (droppedID != null && droppedItem.getItem() instanceof ArmorItem) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            for (ItemStack armour : player.getArmorSlots()) {
                if (armour.getItem() instanceof ArmorItem armorItem) {
                    if (Utils.hasArmorID(armour)) {
                        SummonerInventory pauldronInventory = (SummonerInventory) Utils.findBestSummonItem(player).getCapability(Capabilities.ItemHandler.ITEM);
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
        }
    }

    @SubscribeEvent
    public void onItemToss(ItemTossEvent event) {
        if (event.getEntity().getItem().getItem() instanceof ArmorItem armorItem) {
            ItemStack droppedItem = event.getEntity().getItem();
            if (Utils.hasArmorID(droppedItem)) {
                SummonerInventory pauldronInventory = (SummonerInventory) Utils.findBestSummonItem(event.getPlayer()).getCapability(Capabilities.ItemHandler.ITEM);
                switch (armorItem.getType()) {
                    case HELMET -> pauldronInventory.setStackInSlot(0, droppedItem);
                    case CHESTPLATE -> pauldronInventory.setStackInSlot(1, droppedItem);
                    case LEGGINGS -> pauldronInventory.setStackInSlot(2, droppedItem);
                    case BOOTS -> pauldronInventory.setStackInSlot(3, droppedItem);
                }
                event.getPlayer().level().playSound(null, event.getPlayer().position().x(), event.getPlayer().position().y(), event.getPlayer().position().z(), SoundEvents.ARMOR_EQUIP_NETHERITE, SoundSource.MASTER, 1.0f, 1.0f);
            }
        }
    }


}
