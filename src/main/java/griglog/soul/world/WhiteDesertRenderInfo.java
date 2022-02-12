package griglog.soul.world;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import griglog.soul.Soul;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ISkyRenderHandler;

import javax.annotation.Nullable;

import static net.minecraft.client.renderer.tileentity.EndPortalTileEntityRenderer.END_SKY_LOCATION;

@OnlyIn(Dist.CLIENT)
public class WhiteDesertRenderInfo extends DimensionRenderInfo {
    public WhiteDesertRenderInfo() {
        super(99999, //cloudLevel
                true, //hasGround
                DimensionRenderInfo.FogType.END, //skyType! not fog! it doesn't matter though because I override sky rendering
                false, //forceBrightLightmap
                false);  //constantAmbientLight
        setSkyRenderHandler(new SkyRenderer());
    }
    @Override
    public Vector3d getBrightnessDependentFogColor(Vector3d fogColor, float partialTicks) {
        return fogColor;
    }

    @Override
    public boolean isFoggyAt(int p_230493_1_, int p_230493_2_) {
        return true;
    }

    @Override
    public float[] getSunriseColor(float p_230492_1_, float p_230492_2_) {
        return null;
    }

    static class SkyRenderer implements ISkyRenderHandler{
        @Override
        public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc) {
            renderEndSky(matrixStack, mc);
        }

        private void renderEndSky(MatrixStack pMatrixStack, Minecraft mc) {
            RenderSystem.disableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(false);
            Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(Soul.id, "textures/desert_sky.png"));
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();

            for(int i = 0; i < 6; ++i) {
                pMatrixStack.pushPose();
                if (i == 1) {
                    pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                }

                if (i == 2) {
                    pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
                }

                if (i == 3) {
                    pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
                }

                if (i == 4) {
                    pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
                }

                if (i == 5) {
                    pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
                }

                Matrix4f matrix4f = pMatrixStack.last().pose();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(40, 40, 40, 255).endVertex();
                bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).uv(0.0F, 16.0F).color(40, 40, 40, 255).endVertex();
                bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).uv(16.0F, 16.0F).color(40, 40, 40, 255).endVertex();
                bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).uv(16.0F, 0.0F).color(40, 40, 40, 255).endVertex();
                tessellator.end();
                pMatrixStack.popPose();
            }

            RenderSystem.depthMask(true);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
            RenderSystem.enableAlphaTest();
        }
    }
}
