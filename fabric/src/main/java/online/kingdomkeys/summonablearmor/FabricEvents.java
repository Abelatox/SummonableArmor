package online.kingdomkeys.summonablearmor;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FabricEvents {

    //Custom event to mimic Forge's EntityJoinLevelEvent
    @FunctionalInterface
    public interface EntityJoinWorld {
        boolean onEntityJoinWorld(Entity entity, Level level);
    }

    @FunctionalInterface
    public interface ItemToss {
        void onItemToss(ItemEntity entity, Player player);
    }

    public static final Event<FabricEvents.EntityJoinWorld> JOIN_WORLD = EventFactory.createArrayBacked(FabricEvents.EntityJoinWorld.class, listeners -> (entity, level) -> {
        for (EntityJoinWorld event : listeners) {
            if (event.onEntityJoinWorld(entity, level)) {
                return true;
            }
        }
        return false;
    });

    public static final Event<FabricEvents.ItemToss> ITEM_TOSS = EventFactory.createArrayBacked(FabricEvents.ItemToss.class, listeners -> (entity, player) -> {
        for (ItemToss event : listeners) {
            event.onItemToss(entity, player);
        }
    });

}