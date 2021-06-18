package griglog.soul.packets;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import griglog.soul.Soul;
import griglog.soul.capability.SoulCap;
import griglog.soul.capability.SoulProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SoulPacket {
    private final SoulCap cap;
    public SoulPacket(SoulCap cap){
        this.cap = cap;
    }

    static SoulPacket decode(PacketBuffer buf){
        try {
            return new SoulPacket(new SoulCap().setNbt(JsonToNBT.getTagFromJson(buf.readString())));
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void encode(SoulPacket p, PacketBuffer buf){
        buf.writeString(p.cap.getNbt().toString());
    }

    static void handle(SoulPacket p, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                SoulCap cap = player.getCapability(SoulProvider.SOUL_CAP).resolve().get();
                cap.setNbt(p.cap.getNbt());
                if (cap.isJustParried()) {
                    player.stopActiveHand();
                    cap.setJustParried(false);
                }
            } else if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER){
                ServerPlayerEntity player = ctx.get().getSender();
                SoulCap cap = player.getCapability(SoulProvider.SOUL_CAP).resolve().get();
                cap.setNbt(p.cap.getNbt());
                if (cap.isJustParried()) {
                    cap.setJustParried(false);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
