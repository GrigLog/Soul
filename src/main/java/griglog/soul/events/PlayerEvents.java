package griglog.soul.events;

import griglog.soul.SF;
import griglog.soul.Soul;
import griglog.soul.capability.SoulCap;
import griglog.soul.items.misc.Items;
import griglog.soul.packets.PacketSender;
import griglog.soul.packets.SoulPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Syntax;
import java.text.DecimalFormat;

@Mod.EventBusSubscriber
public class PlayerEvents {
    @SubscribeEvent
    static void playerTick(TickEvent.PlayerTickEvent event){
        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = event.player;
            SoulCap cap = SF.getSoul(player);
            cap.tick();
            //Soul.LOGGER.info(SF.world(player.world) + " " + new DecimalFormat("#.##").format(cap.mana)+ " " + new DecimalFormat("#.##").format(cap.maxMana));
        }
    }

    @SubscribeEvent
    static void playerHit(LivingAttackEvent event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity)event.getEntityLiving();
        SoulCap soulCap = player.getCapability(SoulCap.SoulProvider.SOUL_CAP, null).resolve().get();
        if (player.getHeldItemMainhand().getItem() == Items.zanpakuto && player.isHandActive()) {
            SF.playSoundPlayer("sword_clash", player);
            event.setCanceled(true);
            soulCap.CATimer = 20;
            soulCap.justParried = true;
            player.stopActiveHand();
            //the event is cancelled and thus not called on client
            PacketSender.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SoulPacket(soulCap));
        }
    }

    @SubscribeEvent
    static void copyPlayerDataOnRespawn(PlayerEvent.Clone event){
        SoulCap soul = SF.getSoul(event.getOriginal());
        SoulCap soulNew = SF.getSoul(event.getPlayer());
        soulNew.setNbt(soul.getNbt());
        SF.sendToClient((ServerPlayerEntity)event.getPlayer(), soulNew);
    }
}
