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
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//key actions: 0 - release, 1 - press, 2 - hold
@Mod.EventBusSubscriber
public class InputEvents {
    @SubscribeEvent
    public static void onEvent(InputEvent.MouseInputEvent event)
    {
        //Soul.LOGGER.debug(event.getButton() + " " + event.getAction());
        if (event.getButton() == 1 && event.getAction() == 1) {  //right button pressed down
            //Soul.LOGGER.debug("right click client");
            SoulCap soulCap = SF.getSoul(Minecraft.getInstance().player);
            soulCap.rightClicked = true;
            SF.sendToServer(soulCap);

        } else if (event.getButton() == 0 && event.getAction() == 1){  //left button pressed down
            //Soul.LOGGER.debug("left click client");
            PlayerEntity player = Minecraft.getInstance().player;
            if (player == null || player.getHeldItemMainhand() == ItemStack.EMPTY)
                return;
            Item item = player.getHeldItemMainhand().getItem();
            if (item == Items.holyBow && player.isHandActive()){
                SoulCap soulCap = SF.getSoul(Minecraft.getInstance().player);
                soulCap.leftClicked = true;
                SF.sendToServer(soulCap);
                player.stopActiveHand();
                PacketSender.INSTANCE.sendToServer(new StopHandPacket());
            }
        }
    }

    //TODO: fix different speeds on ground and mid-air. Also remove gravity.
    @SubscribeEvent
    public static void onEvent(InputEvent.KeyInputEvent event){
        KeyBinding runBinding = Minecraft.getInstance().gameSettings.keyBindSprint;
        int key = event.getKey();
        int runKey = runBinding.getKey().getKeyCode();
        if (key == runKey){
            ClientPlayerEntity player = Minecraft.getInstance().player;
            SoulCap soulCap = SF.getSoul(player);
            if (event.getAction() == 1 && soulCap.dashCD == 0){
                soulCap.dashWindow = SoulCap.dashWindowMax;
                SF.sendToServer(soulCap);
            } else if (event.getAction() == 0){
                if (soulCap.dashWindow > 0) {
                    float yaw = player.rotationYaw;
                    float x = -MathHelper.sin(yaw * 0.017453292F);
                    float z = MathHelper.cos(yaw * 0.017453292F);
                    player.setMotion(new Vector3d(x, 0, z));
                    soulCap.dashCD = SoulCap.dashCDMax;
                    SF.sendToServer(soulCap);
                }
            }
        }
    }
}
