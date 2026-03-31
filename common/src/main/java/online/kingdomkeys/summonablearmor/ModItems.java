package online.kingdomkeys.summonablearmor;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModItems {
    public static void init() {}

    public static final Supplier<Item>
            armorSummoner = createNewItem(Strings.armorSummoner, () -> new SummonerItem(new Item.Properties().stacksTo(1), Strings.armorSummoner));

    public static <T extends Item> Supplier<T> createNewItem(String name, Supplier<T> item) {
        return SummonableArmor.COMMON.registerItem(name, item);
    }

}
