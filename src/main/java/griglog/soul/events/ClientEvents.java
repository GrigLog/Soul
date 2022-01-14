package griglog.soul.events;

import griglog.soul.SF;
import griglog.soul.Soul;
import griglog.soul.capability.SoulCap;
import griglog.soul.client.render.HollowRenderer;
import griglog.soul.entities.Entities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(value=Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    static void renderOverlay(RenderGameOverlayEvent event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            PlayerEntity player = Minecraft.getInstance().player;
            if (Minecraft.getInstance().player == null)
                return; //this happens when you have died and didnt click "respawn" yet
            SoulCap soulCap = SF.getSoul(Minecraft.getInstance().player);
            if (soulCap == null)
                return;
            FontRenderer fr = Minecraft.getInstance().fontRenderer;
            int heightHalf = Minecraft.getInstance().getMainWindow().getHeight() / 2;  //no fucking idea why I have to divide by 2
            int widthHalf = Minecraft.getInstance().getMainWindow().getWidth() / 2;
            /*StringTextComponent tc = new StringTextComponent("mana");
            tc.mergeStyle(TextFormatting.BOLD).mergeStyle(TextFormatting.DARK_BLUE);*/
            fr.drawString(event.getMatrixStack(), "Reiatsu:" + (long)soulCap.mana + "/" + (long)soulCap.maxMana, widthHalf * 0.2f, heightHalf * 0.8f, 0x1f75fe);
        }
    }
}
