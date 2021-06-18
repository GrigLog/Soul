package griglog.soul;

import griglog.soul.capability.SoulCap;
import griglog.soul.capability.SoulStorage;
import griglog.soul.packets.PacketSender;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
    public static void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("pre-init");
        CapabilityManager.INSTANCE.register(SoulCap.class, new SoulStorage(), () -> new SoulCap());
        PacketSender.init();
    }


}
