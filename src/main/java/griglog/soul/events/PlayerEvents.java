package griglog.soul.events;

import griglog.soul.SF;
import griglog.soul.capability.SoulCap;
import griglog.soul.capability.SoulProvider;
import griglog.soul.items.misc.Items;
import griglog.soul.packets.PacketSender;
import griglog.soul.packets.SoulPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber
public class PlayerEvents {
    @SubscribeEvent
    static void playerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        SoulCap cap = player.getCapability(SoulProvider.SOUL_CAP).resolve().get();
        if (cap.getParryTimer() > 0){
            cap.setParryTimer(cap.getParryTimer() - 1);
        }
        if (cap.getCATimer() > 0){
            cap.setCATimer(cap.getCATimer() - 1);
        }
        /*if (cap.getLeftClicked()) {
            player.stopActiveHand();
            cap.setLeftClicked(false);
        }*/


    }

    @SubscribeEvent
    static void playerHit(LivingAttackEvent event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity)event.getEntityLiving();
        LazyOptional<SoulCap> soulOpt = player.getCapability(SoulProvider.SOUL_CAP, null);
        if (soulOpt.isPresent()){
            SoulCap soulCap = soulOpt.resolve().get();
            if (player.getHeldItemMainhand().getItem() == Items.zanpakuto && player.isHandActive()) {
                SF.playSoundPlayer("sword_clash", player);
                event.setCanceled(true);
                soulCap.setCATimer(20);
                player.stopActiveHand();
                //the event is cancelled and thus not called on client
                PacketSender.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SoulPacket(soulCap.setJustParried(true)));
            }
        }
    }
}
