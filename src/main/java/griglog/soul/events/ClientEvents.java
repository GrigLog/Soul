package griglog.soul.events;

import griglog.soul.Soul;
import griglog.soul.capability.SoulCap;
import griglog.soul.capability.SoulProvider;
import griglog.soul.entities.Entities;
import griglog.soul.entities.HolyArrowRenderer;
import griglog.soul.entities.HolyBeamRenderer;
import griglog.soul.items.misc.Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;

@Mod.EventBusSubscriber(value=Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    static void renderOverlay(RenderGameOverlayEvent event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            SoulCap soulCap = Minecraft.getInstance().player.getCapability(SoulProvider.SOUL_CAP, null).resolve().get();
            FontRenderer fr = Minecraft.getInstance().fontRenderer;
            int heightHalf = Minecraft.getInstance().getMainWindow().getHeight() / 2;  //no fucking idea why I have to divide by 2
            int widthHalf = Minecraft.getInstance().getMainWindow().getWidth() / 2;
            /*StringTextComponent tc = new StringTextComponent("mana");
            tc.mergeStyle(TextFormatting.BOLD).mergeStyle(TextFormatting.DARK_BLUE);*/
            fr.drawString(event.getMatrixStack(), "Reiatsu:" + (long)soulCap.mana + "/" + (long)soulCap.maxMana, widthHalf * 0.2f, heightHalf * 0.8f, 0x1f75fe);
        }
    }
}
