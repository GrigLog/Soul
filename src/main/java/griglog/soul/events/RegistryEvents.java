package griglog.soul.events;

import griglog.soul.Soul;
import griglog.soul.blocks.Blocks;
import griglog.soul.capability.SoulCap;
import griglog.soul.client.render.HollowRenderer;
import griglog.soul.client.render.HolyArrowRenderer;
import griglog.soul.client.render.HolyBeamRenderer;
import griglog.soul.entities.Entities;
import griglog.soul.entities.Hollow;
import griglog.soul.items.misc.Items;
import griglog.soul.packets.PacketSender;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {
    @SubscribeEvent
    static void registerBlocks(final RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(Blocks.whiteSand);
    }

    @SubscribeEvent
    static void registerItems(final RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(Items.zanpakuto, Items.holyBow, Items.badge, Items.dagger, Items.reishiChest, Items.reishiLeg, Items.whiteSandItem);
    }

    @SubscribeEvent
    static void registerEntities(final RegistryEvent.Register<EntityType<?>> event){
        event.getRegistry().registerAll(Entities.holyArrow, Entities.holyBeam, Entities.hollow);
    }

    @SubscribeEvent
    static void setEntityAttributes(EntityAttributeCreationEvent event){
        event.put(Entities.hollow, Hollow.getEntityAttributes());  //TODO: figure out how I can get rid of follow range here
    }

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(SoulCap.class, new SoulCap.SoulStorage(), () -> new SoulCap());
        PacketSender.init();
    }

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        ItemModelsProperties.register(Items.zanpakuto, new ResourceLocation(Soul.id, "blocking"),
                (stack, world, living) -> living != null && living.isUsingItem() ? 1f : 0f);
        ItemModelsProperties.register(Items.holyBow, new ResourceLocation("pull"), (stack, world, living) -> {
            if (living == null) {
                return 0.0F;
            } else {
                return living.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemModelsProperties.register(Items.holyBow, new ResourceLocation("pulling"),
                (stack, world, living) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
        ItemModelsProperties.register(Items.dagger, new ResourceLocation(Soul.id, "active"),
                (stack, world, living) -> stack.getTag() != null && stack.getTag().getBoolean("active") ? 1f : 0f);
        RenderingRegistry.registerEntityRenderingHandler(Entities.holyArrow, HolyArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Entities.holyBeam, HolyBeamRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Entities.hollow, HollowRenderer::new);
    }

}
