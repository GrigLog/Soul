package griglog.soul;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class SF {
    public static void printChat(String s) {
        NewChatGui chat = Minecraft.getInstance().ingameGUI.getChatGUI();
        StringTextComponent msg = new StringTextComponent(s);
        chat.printChatMessage(msg);
    }

    public static void playSoundPlayer(String fileName, PlayerEntity player){
        SoundEvent event = new SoundEvent(new ResourceLocation("soul", fileName));
        player.world.playSound(null, player.getPosition(), event, SoundCategory.PLAYERS, 1, 1);
    }

    public static String world(World world){return world.isRemote ? "client" : "server";}
}
