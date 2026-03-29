package online.kingdomkeys.summonablearmor.client;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import online.kingdomkeys.summonablearmor.ModComponents;

public class ClientEvents {
    @SubscribeEvent
    public void clientTickPre(ItemTooltipEvent event) {
        if(event.getItemStack() != null && event.getItemStack().has(ModComponents.ARMOR_ID)){
            if(event.getItemStack().getItem() instanceof ArmorItem) {
                event.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + "SUMMONED"));
            }
        }
    }

}
