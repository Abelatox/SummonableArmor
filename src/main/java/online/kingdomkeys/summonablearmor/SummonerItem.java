package online.kingdomkeys.summonablearmor;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import online.kingdomkeys.summonablearmor.client.SummonerMenu;
import online.kingdomkeys.summonablearmor.client.SummonerInventory;

import java.util.List;
import java.util.UUID;

public class SummonerItem extends Item {
    String textureName;

    public SummonerItem(Properties properties, String textureName) {
        super(properties);
        this.textureName = textureName;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Utils.clearArmor(player, stack);
        if (!level.isClientSide) {
            MenuProvider container = new SimpleMenuProvider((w, p, pl) -> new SummonerMenu(w, p, stack), stack.getHoverName());
            player.openMenu(container, buf -> {
                buf.writeBoolean(hand == InteractionHand.MAIN_HAND);
            });
        }
        return super.use(level, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!stack.has(ModComponents.ARMOR_ID)) {
            stack.set(ModComponents.ARMOR_ID, UUID.randomUUID());
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.getRarity() == Rarity.EPIC;
    }

    public String getTextureName() {
        return textureName;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flagIn) {
        SummonerInventory pauldronInventory = (SummonerInventory) stack.getCapability(Capabilities.ItemHandler.ITEM);
        if(pauldronInventory == null)
            return;

        if (!pauldronInventory.getStackInSlot(0).isEmpty()) {
            tooltip.add(Component.literal(Component.translatable(SummonableArmor.MODID+".helmet").getString() + ": " + pauldronInventory.getStackInSlot(0).getHoverName().getString()));
        }
        if (!pauldronInventory.getStackInSlot(1).isEmpty()) {
            tooltip.add(Component.literal(Component.translatable(SummonableArmor.MODID+".chestplate").getString() + ": " + pauldronInventory.getStackInSlot(1).getHoverName().getString()));
        }
        if (!pauldronInventory.getStackInSlot(2).isEmpty()) {
            tooltip.add(Component.literal(Component.translatable(SummonableArmor.MODID+".leggings").getString() + ": " + pauldronInventory.getStackInSlot(2).getHoverName().getString()));
        }
        if (!pauldronInventory.getStackInSlot(3).isEmpty()) {
            tooltip.add(Component.literal(Component.translatable(SummonableArmor.MODID+".boots").getString() + ": " + pauldronInventory.getStackInSlot(3).getHoverName().getString()));
        }
        if (flagIn.isAdvanced()) {
            if (stack.has(ModComponents.ARMOR_ID)) {
                tooltip.add(Component.translatable(ChatFormatting.RED + "DEBUG:"));
                tooltip.add(Component.translatable(ChatFormatting.WHITE + stack.get(ModComponents.ARMOR_ID).toString()));
            }
        }
    }

    @Override
    public boolean canGrindstoneRepair(ItemStack stack) {
        return isFoil(stack);
    }

}
