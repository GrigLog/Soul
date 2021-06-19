package griglog.soul.events;

import griglog.soul.SF;
import griglog.soul.capability.SoulCap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(value=Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    static void renderOverlay(RenderGameOverlayEvent event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            SoulCap soulCap = SF.getSoul(Minecraft.getInstance().player);
            FontRenderer fr = Minecraft.getInstance().fontRenderer;
            int heightHalf = Minecraft.getInstance().getMainWindow().getHeight() / 2;  //no fucking idea why I have to divide by 2
            int widthHalf = Minecraft.getInstance().getMainWindow().getWidth() / 2;
            /*StringTextComponent tc = new StringTextComponent("mana");
            tc.mergeStyle(TextFormatting.BOLD).mergeStyle(TextFormatting.DARK_BLUE);*/
            fr.drawString(event.getMatrixStack(), "Reiatsu:" + (long)soulCap.mana + "/" + (long)soulCap.maxMana, widthHalf * 0.2f, heightHalf * 0.8f, 0x1f75fe);
        }
    }
}
