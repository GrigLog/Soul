package griglog.soul.client.model;// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import griglog.soul.Soul;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.client.model.ModelLoader;

public class CustomArmorModel extends BipedModel<LivingEntity> {
	private final ModelRenderer Head;
	private final ModelRenderer Body;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftLeg;

	private final ModelRenderer helmAnchor;

	public CustomArmorModel (){
		super(1, 0, 64, 64);
		textureWidth = 64;
		textureHeight = 64;

		float s = 0.01F;

		//helm
		this.helmAnchor = new ModelRenderer(this, 0, 0);
		this.helmAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.helmAnchor.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 2, s);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		Head.setTextureOffset(0, 7).addBox(-4.0F, -7.0F, -5.0F, 8.0F, 1.0F, 1.0F, 0.0F, true);

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 0.0F, 0.0F);
		

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		RightArm.setTextureOffset(0, 0).addBox(-4.0F, 7.0F, -3.0F, 5.0F, 1.0F, 6.0F, 0.0F, true);

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);

		helmAnchor.addChild(Head);
	}


	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		/*this.bipedHead.addChild(Head);
		Head.render(matrixStack, buffer, packedLight, packedOverlay);
		Body.render(matrixStack, buffer, packedLight, packedOverlay);
		RightArm.render(matrixStack, buffer, packedLight, packedOverlay);
		LeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);*/

		setModelAttributes();
		bipedHead = helmAnchor;
		bipedRightArm = RightArm;
		super.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	/*@Override
	public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {
		bipedHead = Head;
		bipedRightArm = RightArm;
		super.render(ms, buffer, light, overlay, r, g, b, a);
	}*/

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}