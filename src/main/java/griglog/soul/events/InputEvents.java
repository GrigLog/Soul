package griglog.soul.events;

import griglog.soul.SF;
import griglog.soul.Soul;
import griglog.soul.capability.SoulCap;
import griglog.soul.entities.HolyArrow;
import griglog.soul.items.HolyBow;
import griglog.soul.items.misc.Items;
import griglog.soul.packets.PacketSender;
import griglog.soul.packets.SoulPacket;
import griglog.soul.packets.StopHandPacket;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InputEvents {
    @SubscribeEvent
    public static void onEvent(InputEvent.MouseInputEvent event)
    {
        KeyBinding keyUse = Minecraft.getInstance().gameSettings.keyBindUseItem;
        //Soul.LOGGER.debug(event.getButton() + " " + event.getAction());
        if (event.getButton() == 1 && event.getAction() == 1) {  //right button pressed down
            //Soul.LOGGER.debug("right click client");
            SoulCap soulCap = SF.getSoul(Minecraft.getInstance().player);
            soulCap.rightClicked = true;
            PacketSender.INSTANCE.sendToServer(new SoulPacket(soulCap));

        } else if (event.getButton() == 0 && event.getAction() == 1){  //left button pressed down
            //Soul.LOGGER.debug("left click client");
            PlayerEntity player = Minecraft.getInstance().player;
            if (player == null || player.getHeldItemMainhand() == ItemStack.EMPTY)
                return;
            Item item = player.getHeldItemMainhand().getItem();
            if (item == Items.holyBow && player.isHandActive()){
                SoulCap soulCap = SF.getSoul(Minecraft.getInstance().player);
                soulCap.leftClicked = true;
                PacketSender.INSTANCE.sendToServer(new SoulPacket(soulCap));
                player.stopActiveHand();
                PacketSender.INSTANCE.sendToServer(new StopHandPacket());
            }
        }
    }
}
