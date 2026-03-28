package online.kingdomkeys.summonablearmor.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import online.kingdomkeys.summonablearmor.SummonableArmor;

@EventBusSubscriber()
public class PacketHandler {

    private static PayloadRegistrar registrar;

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        registrar = event.registrar(SummonableArmor.MODID);

        server(CSSummonArmor.TYPE, CSSummonArmor.STREAM_CODEC);
    }

    private static <T extends Packet> void client(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader) {
        registrar.playToClient(type, reader, PacketHandler::handlePacket);
    }

    private static <T extends Packet> void server(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader) {
        registrar.playToServer(type, reader, PacketHandler::handlePacket);
    }

    private static <T extends Packet> void bidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader) {
        registrar.playBidirectional(type, reader, PacketHandler::handlePacket);
    }

    public static void sendTo(Packet packet, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, packet);
    }

    public static void sendToAll(Packet packet) {
        PacketDistributor.sendToAllPlayers(packet);
    }

    public static void sendToServer(Packet packet) {
        PacketDistributor.sendToServer(packet);
    }

    public static <T extends Packet> void handlePacket(final T data, final IPayloadContext context) {
        context.enqueueWork(() -> data.handle(context)).exceptionally(e -> {
            SummonableArmor.LOGGER.warn("Packet \"{}\" handling failed, something is likely broken", data.type());
            return null;
        });
        Packet reply = data.reply(context);
        if (reply != null) {
            context.reply(reply);
        }
    }
}
