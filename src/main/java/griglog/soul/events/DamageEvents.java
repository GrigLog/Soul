package griglog.soul.events;

import griglog.soul.SF;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.DecimalFormat;
import java.util.List;

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
        TextFormatting color = TextFormatting.GOLD;
        if (target instanceof PlayerEntity)
            color = TextFormatting.DARK_RED;
        else {
            boolean playerNear = false;
            List<Entity> entitiesNear = target.level.getEntities(null,
                    new AxisAlignedBB(target.blockPosition().offset(-10, -10, -10), target.blockPosition().offset(10, 10, 10)));
            for (Entity e : entitiesNear){
                if (e instanceof PlayerEntity) {
                    playerNear = true;
                    break;
                }
            }
            if (!playerNear)
                return;
        }
        float damage = event.getAmount();
        buffer += new DecimalFormat("#.###").format(damage) + ")";
        DamageSource ds = event.getSource();
        if (ds.isMagic())
            buffer += "magic ";
        if (ds.isBypassArmor())
            buffer += "unbl ";
        if (ds.isBypassMagic())
            buffer += "abs ";
        SF.printChat(color+buffer);
        buffer = "";
    }
}

