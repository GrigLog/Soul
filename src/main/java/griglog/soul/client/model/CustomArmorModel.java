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
		texWidth = 64;
		texHeight = 64;

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, 0.0F, 0.0F);
		Head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, 0.0F, 0.0F);
		Body.texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(-5.0F, 2.0F, 0.0F);
		RightArm.texOffs(16, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(5.0F, 2.0F, 0.0F);
		LeftArm.texOffs(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setPos(-1.9F, 12.0F, 0.0F);
		RightLeg.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		RightLeg.texOffs(42, 52).addBox(-3.0F, 2.0F, -3.0F, 5.0F, 6.0F, 6.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setPos(1.9F, 12.0F, 0.0F);
		LeftLeg.texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		LeftLeg.texOffs(42, 52).addBox(-2.0F, 2.0F, -3.0F, 5.0F, 6.0F, 6.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		//I have no idea when this shit is never called lol. That's why I didn't place copyModelAngles()'s here.
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Head.copyFrom(head);
		Body.copyFrom(body);
		RightArm.copyFrom(rightArm);
		LeftArm.copyFrom(leftArm);
		LeftLeg.copyFrom(leftLeg);
		RightLeg.copyFrom(rightLeg);

		//super.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		//i would recommend to never ever call super.render() because it fucks up everything with its transformations
		if (body.visible){
			Body.render(matrixStack, buffer, packedLight, packedOverlay);
			RightArm.render(matrixStack, buffer, packedLight, packedOverlay);
			LeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
			RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
			LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		}
	}
}