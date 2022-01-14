package griglog.soul.events;

import griglog.soul.entities.Entities;
import griglog.soul.entities.Hollow;
import griglog.soul.items.misc.Items;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {
    @SubscribeEvent
    static void registerItems(final RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(Items.zanpakuto, Items.holyBow, Items.badge, Items.dagger, Items.reishiChest, Items.reishiLeg);
    }

    @SubscribeEvent
    static void registerEntities(final RegistryEvent.Register<EntityType<?>> event){
        event.getRegistry().registerAll(Entities.holyArrow, Entities.holyBeam, Entities.hollow);
    }

    @SubscribeEvent
    static void setEntityAttributes(EntityAttributeCreationEvent event){
        event.put(Entities.hollow, Hollow.getAttributes());  //TODO: figure out how I can get rid of follow range here

        /*event.put(Entities.hollow, AttributeModifierMap.createMutableAttribute()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10d)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 1)
                .create());*/
    }

    /*@SubscribeEvent
    static void registerParticles(ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particles.registerFactory()
    }*/
}
