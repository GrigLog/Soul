package griglog.soul.client.render;

import griglog.soul.Soul;
import griglog.soul.entities.HolyArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class HolyArrowRenderer extends ArrowRenderer<HolyArrow> {
    public HolyArrowRenderer(EntityRendererManager e){
        super(e);
    }

    @Override
    public ResourceLocation getEntityTexture(HolyArrow entity) {
        return new ResourceLocation(Soul.id, "textures/entity/holy_arrow.png");
    }
}
