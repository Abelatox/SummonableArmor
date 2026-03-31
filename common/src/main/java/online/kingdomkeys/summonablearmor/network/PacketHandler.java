package online.kingdomkeys.summonablearmor.network;

import online.kingdomkeys.summonablearmor.SummonableArmor;

public class PacketHandler {
    public static void init() {
        SummonableArmor.COMMON.registerC2S(new CSSummonArmor());
    }
}
