package griglog.soul.events;

import griglog.soul.Soul;
import griglog.soul.entities.Entities;
import griglog.soul.entities.HolyArrowRenderer;
import griglog.soul.entities.HolyBeamRenderer;
import griglog.soul.items.misc.Items;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value=Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event){
        ItemModelsProperties.registerProperty(Items.zanpakuto, new ResourceLocation(Soul.id, "blocking"),
                (stack, world, living) -> {
            boolean res = living != null && living.isHandActive();
            return res ? 1f : 0f;
        });
        ItemModelsProperties.registerProperty(Items.holyBow, new ResourceLocation("pull"), (stack, world, living) -> {
            if (living == null) {
                return 0.0F;
            } else {
                return living.getActiveItemStack() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.registerProperty(Items.holyBow, new ResourceLocation("pulling"),
                (stack, world, living) -> living != null && living.isHandActive() && living.getActiveItemStack() == stack ? 1.0F : 0.0F);


        RenderingRegistry.registerEntityRenderingHandler(Entities.holyArrow, HolyArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Entities.holyBeam, HolyBeamRenderer::new);
        //RenderingRegistry.registerEntityRenderingHandler(Entities.holyBeam, HolyBeamRenderer::new);
    }
}
