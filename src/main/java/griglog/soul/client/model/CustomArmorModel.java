package griglog.soul.client.model;


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
	public CustomArmorModel() {
		super(1);
		textureWidth = 64;
		textureHeight = 64;

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		Head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.setTextureOffset(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		RightArm.setTextureOffset(16, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		LeftArm.setTextureOffset(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		RightLeg.setTextureOffset(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		LeftLeg.setTextureOffset(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		//i have no idea when this shit is called lol. That's why I didn't place copyModelAngles()'s here,
		//by the time render() executes angles are set to 0's again.
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Head.copyModelAngles(bipedHead);
		Body.copyModelAngles(bipedBody);
		RightArm.copyModelAngles(bipedRightArm);
		LeftArm.copyModelAngles(bipedLeftArm);
		LeftLeg.copyModelAngles(bipedLeftLeg);
		RightLeg.copyModelAngles(bipedRightLeg);

		//super.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		//i would recommend to never ever call super.render() because it fucks up everything with its transformations
		if (bipedBody.showModel){
			Body.render(matrixStack, buffer, packedLight, packedOverlay);
			RightArm.render(matrixStack, buffer, packedLight, packedOverlay);
			LeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
			RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
			LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		}
	}
}