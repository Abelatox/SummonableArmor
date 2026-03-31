package online.kingdomkeys.summonablearmor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import online.kingdomkeys.summonablearmor.FabricEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @SuppressWarnings("UnreachableCode")
    @Inject(at = @At("TAIL"), method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;")
    public void drop(ItemStack droppedItem, boolean dropAround, boolean includeThrowerName, CallbackInfoReturnable<ItemEntity> cir, @Local ItemEntity itemEntity) {
        FabricEvents.ITEM_TOSS.invoker().onItemToss(itemEntity, (Player)((Object)this));
    }

}
