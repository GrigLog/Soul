package griglog.soul.client.render;

import griglog.soul.Soul;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;

public class RapidShooterRenderer extends SkeletonRenderer {
    public RapidShooterRenderer(EntityRendererManager p_i46143_1_) {
        super(p_i46143_1_);
    }
    public ResourceLocation getTextureLocation(AbstractSkeletonEntity pEntity) {
        return new ResourceLocation(Soul.id, "textures/entity/rapid_shooter.png");
    }
}
