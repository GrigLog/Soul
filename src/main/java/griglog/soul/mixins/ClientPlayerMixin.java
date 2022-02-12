package griglog.soul.mixins;

import com.mojang.authlib.GameProfile;
import griglog.soul.Soul;
import griglog.soul.items.misc.IFastItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.Pose;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerMixin extends AbstractClientPlayerEntity {
    public ClientPlayerMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    public void aiStep() {
        ++this.sprintTime;
        if (this.sprintTriggerTime > 0) {
            --this.sprintTriggerTime;
        }

        this.handleNetherPortalClient();
        boolean flag = this.input.jumping;
        boolean flag1 = this.input.shiftKeyDown;
        boolean flag2 = this.hasEnoughImpulseToStartSprinting();
        this.crouching = !this.abilities.flying && !this.isSwimming() && this.canEnterPose(Pose.CROUCHING) && (this.isShiftKeyDown() || !this.isSleeping() && !this.canEnterPose(Pose.STANDING));
        this.input.tick(this.isMovingSlowly());
        net.minecraftforge.client.ForgeHooksClient.onInputUpdate(this, this.input);
        this.minecraft.getTutorial().onInput(this.input);
        if (this.isUsingItem() && !this.isPassenger()) {
            float mult = 0.2f; //vanilla value
            if(this.getMainHandItem().getItem() instanceof IFastItem){
                mult = ((IFastItem)this.getMainHandItem().getItem()).getSlowDown();
            }
            this.input.leftImpulse *= mult;
            this.input.forwardImpulse *= mult;
            this.sprintTriggerTime = 0;
        }

        boolean flag3 = false;
        if (this.autoJumpTime > 0) {
            --this.autoJumpTime;
            flag3 = true;
            this.input.jumping = true;
        }

        if (!this.noPhysics) {
            this.moveTowardsClosestSpace(this.getX() - (double)this.getBbWidth() * 0.35D, this.getZ() + (double)this.getBbWidth() * 0.35D);
            this.moveTowardsClosestSpace(this.getX() - (double)this.getBbWidth() * 0.35D, this.getZ() - (double)this.getBbWidth() * 0.35D);
            this.moveTowardsClosestSpace(this.getX() + (double)this.getBbWidth() * 0.35D, this.getZ() - (double)this.getBbWidth() * 0.35D);
            this.moveTowardsClosestSpace(this.getX() + (double)this.getBbWidth() * 0.35D, this.getZ() + (double)this.getBbWidth() * 0.35D);
        }

        if (flag1) {
            this.sprintTriggerTime = 0;
        }

        boolean flag4 = (float)this.getFoodData().getFoodLevel() > 6.0F || this.abilities.mayfly;
        if ((this.onGround || this.isUnderWater()) && !flag1 && !flag2 && this.hasEnoughImpulseToStartSprinting() && !this.isSprinting() && flag4 && !this.isUsingItem() && !this.hasEffect(Effects.BLINDNESS)) {
            if (this.sprintTriggerTime <= 0 && !this.minecraft.options.keySprint.isDown()) {
                this.sprintTriggerTime = 7;
            } else {
                this.setSprinting(true);
            }
        }

        if (!this.isSprinting() && (!this.isInWater() || this.isUnderWater()) && this.hasEnoughImpulseToStartSprinting() && flag4 && !this.isUsingItem() && !this.hasEffect(Effects.BLINDNESS) && this.minecraft.options.keySprint.isDown()) {
            this.setSprinting(true);
        }

        if (this.isSprinting()) {
            boolean flag5 = !this.input.hasForwardImpulse() || !flag4;
            boolean flag6 = flag5 || this.horizontalCollision || this.isInWater() && !this.isUnderWater();
            if (this.isSwimming()) {
                if (!this.onGround && !this.input.shiftKeyDown && flag5 || !this.isInWater()) {
                    this.setSprinting(false);
                }
            } else if (flag6) {
                this.setSprinting(false);
            }
        }

        boolean flag7 = false;
        if (this.abilities.mayfly) {
            if (this.minecraft.gameMode.isAlwaysFlying()) {
                if (!this.abilities.flying) {
                    this.abilities.flying = true;
                    flag7 = true;
                    this.onUpdateAbilities();
                }
            } else if (!flag && this.input.jumping && !flag3) {
                if (this.jumpTriggerTime == 0) {
                    this.jumpTriggerTime = 7;
                } else if (!this.isSwimming()) {
                    this.abilities.flying = !this.abilities.flying;
                    flag7 = true;
                    this.onUpdateAbilities();
                    this.jumpTriggerTime = 0;
                }
            }
        }

        if (this.input.jumping && !flag7 && !flag && !this.abilities.flying && !this.isPassenger() && !this.onClimbable()) {
            ItemStack itemstack = this.getItemBySlot(EquipmentSlotType.CHEST);
            if (itemstack.canElytraFly(this) && this.tryToStartFallFlying()) {
                this.connection.send(new CEntityActionPacket(this, CEntityActionPacket.Action.START_FALL_FLYING));
            }
        }

        this.wasFallFlying = this.isFallFlying();
        if (this.isInWater() && this.input.shiftKeyDown && this.isAffectedByFluids()) {
            this.goDownInWater();
        }

        if (this.isEyeInFluid(FluidTags.WATER)) {
            int i = this.isSpectator() ? 10 : 1;
            this.waterVisionTime = MathHelper.clamp(this.waterVisionTime + i, 0, 600);
        } else if (this.waterVisionTime > 0) {
            this.isEyeInFluid(FluidTags.WATER);
            this.waterVisionTime = MathHelper.clamp(this.waterVisionTime - 10, 0, 600);
        }

        if (this.abilities.flying && this.isControlledCamera()) {
            int j = 0;
            if (this.input.shiftKeyDown) {
                --j;
            }

            if (this.input.jumping) {
                ++j;
            }

            if (j != 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, (double)((float)j * this.abilities.getFlyingSpeed() * 3.0F), 0.0D));
            }
        }

        if (this.isRidingJumpable()) {
            IJumpingMount ijumpingmount = (IJumpingMount)this.getVehicle();
            if (this.jumpRidingTicks < 0) {
                ++this.jumpRidingTicks;
                if (this.jumpRidingTicks == 0) {
                    this.jumpRidingScale = 0.0F;
                }
            }

            if (flag && !this.input.jumping) {
                this.jumpRidingTicks = -10;
                ijumpingmount.onPlayerJump(MathHelper.floor(this.getJumpRidingScale() * 100.0F));
                this.sendRidingJump();
            } else if (!flag && this.input.jumping) {
                this.jumpRidingTicks = 0;
                this.jumpRidingScale = 0.0F;
            } else if (flag) {
                ++this.jumpRidingTicks;
                if (this.jumpRidingTicks < 10) {
                    this.jumpRidingScale = (float)this.jumpRidingTicks * 0.1F;
                } else {
                    this.jumpRidingScale = 0.8F + 2.0F / (float)(this.jumpRidingTicks - 9) * 0.1F;
                }
            }
        } else {
            this.jumpRidingScale = 0.0F;
        }

        super.aiStep();
        if (this.onGround && this.abilities.flying && !this.minecraft.gameMode.isAlwaysFlying()) {
            this.abilities.flying = false;
            this.onUpdateAbilities();
        }

    }
    @Shadow
    protected int sprintTriggerTime;
    @Shadow
    private float jumpRidingScale;
    @Shadow
    private int jumpRidingTicks;
    @Shadow
    public MovementInput input;
    @Shadow
    private boolean crouching;
    @Final
    @Shadow
    protected Minecraft minecraft;
    @Shadow
    public int sprintTime;
    @Shadow
    private int autoJumpTime;
    @Final
    @Shadow
    public ClientPlayNetHandler connection;
    @Shadow
    private boolean wasFallFlying;
    @Shadow
    private int waterVisionTime;

    @Shadow
    protected abstract boolean isControlledCamera();
    @Shadow
    protected abstract void sendRidingJump();
    @Shadow
    public abstract float getJumpRidingScale();
    @Shadow
    public abstract boolean isRidingJumpable();
    @Shadow
    public abstract boolean isMovingSlowly();
    @Shadow
    protected abstract boolean hasEnoughImpulseToStartSprinting();
    @Shadow
    protected abstract void handleNetherPortalClient();
    @Shadow
    protected abstract void moveTowardsClosestSpace(double pX, double pZ);

}
