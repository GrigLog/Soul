package griglog.soul;

import griglog.soul.capability.SoulCap;
import griglog.soul.entities.Entities;
import griglog.soul.entities.HolyArrowRenderer;
import griglog.soul.entities.HolyBeamRenderer;
import griglog.soul.items.misc.Items;
import griglog.soul.packets.PacketSender;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("soul")
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Soul {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String id = "soul";

    public Soul() {

    }

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {  //pre-init
        CapabilityManager.INSTANCE.register(SoulCap.class, new SoulCap.SoulStorage(), () -> new SoulCap());
        PacketSender.init();
    }

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        ItemModelsProperties.registerProperty(Items.zanpakuto, new ResourceLocation(Soul.id, "blocking"),
                (stack, world, living) -> {
                    boolean res = living != null && living.isHandActive();
                    return res ? 1f : 0f;
                });
        ItemModelsProperties.registerProperty(Items.holyBow, new ResourceLocation("pull"), (stack, world, living) -> {
            if (living == null) {
                return 0.0F;
            } else {
                return living.getActiveItemStack() != stack ? 0.0F : (float) (stack.getUseDuration() - living.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.registerProperty(Items.holyBow, new ResourceLocation("pulling"),
                (stack, world, living) -> living != null && living.isHandActive() && living.getActiveItemStack() == stack ? 1.0F : 0.0F);
        //ItemModelsProperties.registerProperty();
        RenderingRegistry.registerEntityRenderingHandler(Entities.holyArrow, HolyArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Entities.holyBeam, HolyBeamRenderer::new);
    }


}
