package online.kingdomkeys.summonablearmor.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import online.kingdomkeys.summonablearmor.ModComponents;
import online.kingdomkeys.summonablearmor.SummonableArmor;
import online.kingdomkeys.summonablearmor.Utils;
import online.kingdomkeys.summonablearmor.network.CSSummonArmor;

import java.util.List;

public class ClientEvents {

    public static void onClientTickPost() {
        if(Minecraft.getInstance().screen == null) {
            while (ClientSetup.SUMMON.consumeClick()) {
                SummonableArmor.COMMON.sendToServer(new CSSummonArmor());
            }
        }
    }

    public static void tooltipEvent(ItemStack stack, List<Component> tooltip, TooltipFlag flags) {
        if(stack.has(ModComponents.ARMOR_ID.get())){
            if(stack.getItem() instanceof ArmorItem) {
                tooltip.add(1, Component.translatable("summonablearmor.summoned").withStyle(ChatFormatting.GOLD));
                if(flags.isAdvanced()){
                    tooltip.add(2, Component.literal("DEBUG:").withStyle(ChatFormatting.RED));
                    tooltip.add(3, Component.literal(Utils.getArmorID(stack)+""));
                }
            }
        }
    }
}
