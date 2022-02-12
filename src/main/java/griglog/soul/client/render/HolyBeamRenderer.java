package griglog.soul.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import griglog.soul.entities.HolyBeam;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class HolyBeamRenderer extends EntityRenderer<HolyBeam> {
    public HolyBeamRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getTextureLocation(HolyBeam entity) {
        return null;
    }

    @Override
    public void render(HolyBeam entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
