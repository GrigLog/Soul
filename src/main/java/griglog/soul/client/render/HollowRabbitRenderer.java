package griglog.soul.client.render;

import griglog.soul.Soul;
import griglog.soul.entities.HollowRabbit;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HollowRabbitRenderer extends RabbitRenderer {
    public HollowRabbitRenderer(EntityRendererManager p_i47196_1_) {
        super(p_i47196_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(RabbitEntity e) {
        if (e.getEntityData().get(HollowRabbit.DATA_ANGRY))
            return new ResourceLocation(Soul.id, "textures/entity/hollow_rabbit_angry.png");
        else
            return new ResourceLocation(Soul.id, "textures/entity/hollow_rabbit.png");
    }
}
