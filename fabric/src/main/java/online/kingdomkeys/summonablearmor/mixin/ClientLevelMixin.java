package online.kingdomkeys.summonablearmor.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import online.kingdomkeys.summonablearmor.FabricEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @SuppressWarnings("UnreachableCode")
    @Inject(at = @At("HEAD"), method = "addEntity", cancellable = true)
    private void addEntity(Entity entity, CallbackInfo info) {
        if (FabricEvents.JOIN_WORLD.invoker().onEntityJoinWorld(entity, ((ClientLevel)(Object)this))) {
            info.cancel();
        }
    }
}