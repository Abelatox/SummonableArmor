package online.kingdomkeys.summonablearmor.client;

import net.minecraft.client.KeyMapping;
import online.kingdomkeys.summonablearmor.SummonableArmor;
import org.lwjgl.glfw.GLFW;

public class ClientSetup {
    public static void init() {}
    public static final KeyMapping SUMMON = SummonableArmor.COMMON.registerKeybind("key." + SummonableArmor.MODID + ".summon", GLFW.GLFW_KEY_H, "key." + SummonableArmor.MODID);
}
