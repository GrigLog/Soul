package griglog.soul;

import griglog.soul.capability.SoulCap;
import griglog.soul.client.render.HollowRenderer;
import griglog.soul.entities.Entities;
import griglog.soul.client.render.HolyArrowRenderer;
import griglog.soul.client.render.HolyBeamRenderer;
import griglog.soul.items.misc.Items;
import griglog.soul.packets.PacketSender;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
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

    public Soul() {}

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    static void clientLoad(FMLClientSetupEvent event) {
        /*DimensionRenderInfo customEffect = new DimensionRenderInfo(99999, true, DimensionRenderInfo.FogType.NORMAL, false, false) {
            @Override
            public Vector3d getBrightnessDependentFogColor(Vector3d fogColor, float partialTicks) {
                return fogColor;
            }

            @Override
            public boolean isFoggyAt(int posX, int posY) {
                return true;
            }
        };
        DeferredWorkQueue.runLater(() -> {
            try {
                Object2ObjectMap<ResourceLocation, DimensionRenderInfo> effectsRegistry = (Object2ObjectMap<ResourceLocation, DimensionRenderInfo>) ObfuscationReflectionHelper
                        .getPrivateValue(DimensionRenderInfo.class, null, "EFFECTS");
                effectsRegistry.put(new ResourceLocation("theabyss:the_abyss"), customEffect);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        RenderTypeLookup.setRenderLayer(portal, RenderType.getTranslucent());*/
    }

}
