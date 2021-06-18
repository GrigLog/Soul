package griglog.soul.packets;

import griglog.soul.Soul;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PacketSender {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Soul.id, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int msgId = 0;

    public static void init() {
        INSTANCE.registerMessage(msgId++, StopHandPacket.class, StopHandPacket::encode, StopHandPacket::decode, StopHandPacket::handle);
        INSTANCE.registerMessage(msgId++, SoulPacket.class, SoulPacket::encode, SoulPacket::decode, SoulPacket::handle);
    }
}
