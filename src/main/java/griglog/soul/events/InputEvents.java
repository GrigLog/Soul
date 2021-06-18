package griglog.soul.events;

import griglog.soul.Soul;
import griglog.soul.capability.SoulCap;
import griglog.soul.capability.SoulProvider;
import griglog.soul.items.HolyBow;
import griglog.soul.items.misc.Items;
import griglog.soul.packets.PacketSender;
import griglog.soul.packets.SoulPacket;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InputEvents {
    @SubscribeEvent
    public static void onEvent(InputEvent.MouseInputEvent event)
    {
        KeyBinding keyUse = Minecraft.getInstance().gameSettings.keyBindUseItem;
        //Soul.LOGGER.info(event.getButton() + " " + event.getAction());
        if (event.getButton() == 1 && event.getAction() == 1) {  //right button pressed down
            SoulCap soulCap = Minecraft.getInstance().player.getCapability(SoulProvider.SOUL_CAP, null).resolve().get();
            soulCap.rightClicked = true;
            PacketSender.INSTANCE.sendToServer(new SoulPacket(soulCap));
        } else if (event.getButton() == 0 && event.getAction() == 1){  //left button pressed down
            PlayerEntity player = Minecraft.getInstance().player;
            if (player == null || player.getHeldItemMainhand() == ItemStack.EMPTY)
                return;
            Item item = player.getHeldItemMainhand().getItem();
            if (item == Items.holyBow && player.isHandActive()){
                player.stopActiveHand();
                SoulCap soulCap = Minecraft.getInstance().player.getCapability(SoulProvider.SOUL_CAP, null).resolve().get();
                soulCap.leftClicked = true;
                PacketSender.INSTANCE.sendToServer(new SoulPacket(soulCap));
            }
        }
    }
}
