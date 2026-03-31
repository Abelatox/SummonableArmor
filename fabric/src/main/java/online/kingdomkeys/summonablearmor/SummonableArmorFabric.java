package online.kingdomkeys.summonablearmor;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

public class SummonableArmorFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        SummonableArmor.init();
        FabricEvents.JOIN_WORLD.register((entity, level) -> EntityEvents.onItemDropped(entity));
        FabricEvents.ITEM_TOSS.register(EntityEvents::onItemToss);
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> EntityEvents.onLivingDeathEvent(entity));
    }
}
