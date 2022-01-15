package griglog.soul.events;

import griglog.soul.SF;
import griglog.soul.Soul;
import griglog.soul.capability.SoulCap;
import griglog.soul.packets.PacketSender;
import griglog.soul.packets.SoulPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Syntax;

@Mod.EventBusSubscriber
public class CapabilityEvents {
    @SubscribeEvent
    static void playerCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(Soul.id, "soul"), new SoulCap.SoulProvider());
        }
    }
    @SubscribeEvent
    static void playerLogin(PlayerEvent.PlayerLoggedInEvent event){
        PlayerEntity player = event.getPlayer();
        SoulCap cap = SF.getSoul(player);
        SF.sendToClient((ServerPlayerEntity) player, cap);
    }

    @SubscribeEvent
    static void respawn(PlayerEvent.PlayerRespawnEvent event){
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        SF.sendToClient(player, SF.getSoul(player));
    }

    @SubscribeEvent
    static void changedDim(PlayerEvent.PlayerChangedDimensionEvent event){
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        SF.sendToClient(player, SF.getSoul(player));
    }

    @SubscribeEvent
    static void copyPlayerDataOnRespawn(PlayerEvent.Clone event){
        SoulCap soul = SF.getSoul(event.getOriginal());
        SoulCap soulNew = SF.getSoul(event.getPlayer());
        soulNew.setNbt(soul.getNbt());
    }

}
