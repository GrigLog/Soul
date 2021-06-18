package griglog.soul.packets;

import griglog.soul.Soul;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MyPacket {
    private int data;
    public MyPacket(int data) {
        this.data = data;
    }

    public static MyPacket decode(PacketBuffer buf){
        return new MyPacket(buf.readInt());
    }

    public static void encode(MyPacket inst, PacketBuffer buf){
        buf.writeInt(inst.data);
    }

    static void handle(MyPacket inst, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Soul.LOGGER.info("sync: " + inst.data);
        });
        ctx.get().setPacketHandled(true);
    }
}
