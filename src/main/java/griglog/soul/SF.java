package griglog.soul;

import griglog.soul.capability.SoulCap;
import griglog.soul.packets.PacketSender;
import griglog.soul.packets.SoulPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class SF {
    public static void printChat(String s) {
        NewChatGui chat = Minecraft.getInstance().gui.getChat();
        StringTextComponent msg = new StringTextComponent(s);
        chat.addMessage(msg);
    }

    public static void playSoundPlayer(String fileName, PlayerEntity player){
        SoundEvent event = new SoundEvent(new ResourceLocation("soul", fileName));
        player.level.playSound(null, player.blockPosition(), event, SoundCategory.PLAYERS, 1, 1);
    }

    public static String world(World world){return world.isClientSide ? "client" : "server";}

    public static SoulCap getSoul(PlayerEntity player){
        return player.getCapability(SoulCap.SoulProvider.SOUL_CAP, null).resolve().orElse(null);
    }

    public static void sendToClient(ServerPlayerEntity player, SoulCap cap){
        PacketSender.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SoulPacket(cap));
    }

    public static void sendToServer(SoulCap cap){
        PacketSender.INSTANCE.sendToServer(new SoulPacket(cap));
    }
}
