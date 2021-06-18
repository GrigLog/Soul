package griglog.soul.events;

import griglog.soul.SF;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.DecimalFormat;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class DamageEvents {
    static String buffer;
    static float absorbtion = 0;
    static DecimalFormat df = new DecimalFormat("#.###");

    @SubscribeEvent
    public static void entityGotHitBeforeArmor(LivingHurtEvent event) {
        LivingEntity target = event.getEntityLiving();
        if (target == null)
            return;
        buffer = "(" + df.format(event.getAmount()) + " : ";
    }

    @SubscribeEvent
    public static void entityGotHitAfterArmor(LivingDamageEvent event) {
        LivingEntity target = event.getEntityLiving();
        TextFormatting color = TextFormatting.AQUA;
        if (target instanceof PlayerEntity)
            color = TextFormatting.GOLD;
        float damage = event.getAmount();
        buffer += new DecimalFormat("#.###").format(damage) + ")";
        DamageSource ds = event.getSource();
        if (ds.isMagicDamage())
            buffer += "magic ";
        if (ds.isUnblockable())
            buffer += "unbl ";
        if (ds.isDamageAbsolute())
            buffer += "abs ";
        SF.printChat(color+buffer);
        buffer = "";
    }
}

