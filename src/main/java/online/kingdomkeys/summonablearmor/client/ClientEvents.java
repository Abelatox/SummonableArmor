package online.kingdomkeys.summonablearmor.client;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import online.kingdomkeys.summonablearmor.ModComponents;

import java.awt.*;

public class ClientEvents {
    @SubscribeEvent
    public void clientTickPre(ItemTooltipEvent event) {
        if(event.getItemStack().has(ModComponents.ARMOR_ID)){
            event.getToolTip().set(1,Component.literal(ChatFormatting.GOLD+"SUMMONED"));
        }
    }

}
