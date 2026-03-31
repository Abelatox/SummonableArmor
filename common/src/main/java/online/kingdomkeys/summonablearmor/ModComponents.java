package online.kingdomkeys.summonablearmor;

import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.UUID;
import java.util.function.Supplier;

public class ModComponents {
    public static void init() {}

    public static final Supplier<DataComponentType<ItemContainerContents>> INVENTORY = SummonableArmor.COMMON.registerComponent("inventory", () -> new DataComponentType.Builder<ItemContainerContents>().persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).cacheEncoding().build());
    public static final Supplier<DataComponentType<UUID>> ARMOR_ID = SummonableArmor.COMMON.registerComponent("armor_id", () -> new DataComponentType.Builder<UUID>().persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC).cacheEncoding().build());
}
