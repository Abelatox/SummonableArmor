package online.kingdomkeys.summonablearmor.client;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import online.kingdomkeys.summonablearmor.ModComponents;
import online.kingdomkeys.summonablearmor.Utils;

public class ClientEvents {
    @SubscribeEvent
    public void clientTickPre(ItemTooltipEvent event) {
        if(event.getItemStack().has(ModComponents.ARMOR_ID)){
            if(event.getItemStack().getItem() instanceof ArmorItem) {
                event.getToolTip().add(1, Component.translatable("summonablearmor.summoned").withStyle(ChatFormatting.GOLD));
                if(event.getFlags().isAdvanced()){
                    event.getToolTip().add(2, Component.literal("DEBUG:").withStyle(ChatFormatting.RED));
                    event.getToolTip().add(3, Component.literal(Utils.getArmorID(event.getItemStack())+""));
                }
            }
        }
    }

}
