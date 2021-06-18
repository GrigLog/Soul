package griglog.soul.packets;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import griglog.soul.capability.SoulCap;
import griglog.soul.capability.SoulProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class StopHandPacket {
    public StopHandPacket(){

    }

    static StopHandPacket decode(PacketBuffer buf){
        return new StopHandPacket();
    }

    static void encode(StopHandPacket p, PacketBuffer buf){
    }

    static void handle(StopHandPacket p, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                player.stopActiveHand();
            } else if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER){
                ServerPlayerEntity player = ctx.get().getSender();
                player.stopActiveHand();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
