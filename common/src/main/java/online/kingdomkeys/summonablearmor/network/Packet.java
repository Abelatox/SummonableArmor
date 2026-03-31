package online.kingdomkeys.summonablearmor.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public interface Packet extends CustomPacketPayload {
    void handle(Player player);
    default Packet reply(Player player) {
        return null;
    }
    <T extends CustomPacketPayload> StreamCodec<? super RegistryFriendlyByteBuf, T> getCodec();
}
