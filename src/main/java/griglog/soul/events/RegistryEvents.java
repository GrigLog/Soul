package griglog.soul.events;

import griglog.soul.entities.Entities;
import griglog.soul.items.misc.Items;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {
    @SubscribeEvent
    static void registerItems(final RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(Items.zanpakuto, Items.holyBow, Items.badge);
    }

    @SubscribeEvent
    static void registerEntities(final RegistryEvent.Register<EntityType<?>> event){
        event.getRegistry().registerAll(Entities.holyArrow.setRegistryName("holy_arrow"), Entities.holyBeam.setRegistryName("holy_beam"));
    }

    /*@SubscribeEvent
    static void registerParticles(ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particles.registerFactory()
    }*/
}
