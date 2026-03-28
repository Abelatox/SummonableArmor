package online.kingdomkeys.summonablearmor;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SummonableArmor.MODID);

    public static final Supplier<Item>
            armorSummoner = createNewItem(Strings.armorSummoner, () -> new SummonerItem(new Item.Properties().stacksTo(1), Strings.armorSummoner));

    public static <T extends Item> Supplier<T> createNewItem(String name, Supplier<? extends T> item) {
        return ITEMS.register(name, item);
    }

}
