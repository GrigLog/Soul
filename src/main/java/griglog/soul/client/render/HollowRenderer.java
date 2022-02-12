package griglog.soul.client.render;

import griglog.soul.Soul;
import griglog.soul.client.model.HollowModel;
import griglog.soul.entities.Hollow;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class HollowRenderer extends MobRenderer<Hollow, HollowModel> {
    public HollowRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new HollowModel(), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(Hollow entity) {
        return new ResourceLocation(Soul.id, "textures/entity/test_hollow.png");
    }
}
