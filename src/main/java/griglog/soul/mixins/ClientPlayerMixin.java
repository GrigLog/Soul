package griglog.soul.mixins;

import com.mojang.authlib.GameProfile;
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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerMixin extends AbstractClientPlayerEntity {


    public ClientPlayerMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    public void livingTick() {  //allows items without slowing down on usage
        ++this.sprintingTicksLeft;
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }

        this.handlePortalTeleportation();
        boolean flag = this.movementInput.jump;
        boolean flag1 = this.movementInput.sneaking;
        boolean flag2 = this.isUsingSwimmingAnimation();
        this.isCrouching = !this.abilities.isFlying && !this.isSwimming() && this.isPoseClear(Pose.CROUCHING) && (this.isSneaking() || !this.isSleeping() && !this.isPoseClear(Pose.STANDING));
        this.movementInput.tickMovement(this.isForcedDown());
        net.minecraftforge.client.ForgeHooksClient.onInputUpdate(this, this.movementInput);
        this.mc.getTutorial().handleMovement(this.movementInput);
        if (this.isHandActive() && !this.isPassenger()) {
            float mult = 0.2f; //vanilla value
            if(this.getHeldItemMainhand().getItem() instanceof IFastItem){
                mult = ((IFastItem)this.getHeldItemMainhand().getItem()).getSlowDown();
            }
            this.movementInput.moveStrafe *= mult;
            this.movementInput.moveForward *= mult;
            this.sprintToggleTimer = 0;
        }

        boolean flag3 = false;
        if (this.autoJumpTime > 0) {
            --this.autoJumpTime;
            flag3 = true;
            this.movementInput.jump = true;
        }

        if (!this.noClip) {
            this.setPlayerOffsetMotion(this.getPosX() - (double)this.getWidth() * 0.35D, this.getPosZ() + (double)this.getWidth() * 0.35D);
            this.setPlayerOffsetMotion(this.getPosX() - (double)this.getWidth() * 0.35D, this.getPosZ() - (double)this.getWidth() * 0.35D);
            this.setPlayerOffsetMotion(this.getPosX() + (double)this.getWidth() * 0.35D, this.getPosZ() - (double)this.getWidth() * 0.35D);
            this.setPlayerOffsetMotion(this.getPosX() + (double)this.getWidth() * 0.35D, this.getPosZ() + (double)this.getWidth() * 0.35D);
        }

        if (flag1) {
            this.sprintToggleTimer = 0;
        }

        boolean flag4 = (float)this.getFoodStats().getFoodLevel() > 6.0F || this.abilities.allowFlying;
        if ((this.onGround || this.canSwim()) && !flag1 && !flag2 && this.isUsingSwimmingAnimation() && !this.isSprinting() && flag4 && !this.isHandActive() && !this.isPotionActive(Effects.BLINDNESS)) {
            if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                this.sprintToggleTimer = 7;
            } else {
                this.setSprinting(true);
            }
        }

        if (!this.isSprinting() && (!this.isInWater() || this.canSwim()) && this.isUsingSwimmingAnimation() && flag4 && !this.isHandActive() && !this.isPotionActive(Effects.BLINDNESS) && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.setSprinting(true);
        }

        if (this.isSprinting()) {
            boolean flag5 = !this.movementInput.isMovingForward() || !flag4;
            boolean flag6 = flag5 || this.collidedHorizontally || this.isInWater() && !this.canSwim();
            if (this.isSwimming()) {
                if (!this.onGround && !this.movementInput.sneaking && flag5 || !this.isInWater()) {
                    this.setSprinting(false);
                }
            } else if (flag6) {
                this.setSprinting(false);
            }
        }

        boolean flag7 = false;
        if (this.abilities.allowFlying) {
            if (this.mc.playerController.isSpectatorMode()) {
                if (!this.abilities.isFlying) {
                    this.abilities.isFlying = true;
                    flag7 = true;
                    this.sendPlayerAbilities();
                }
            } else if (!flag && this.movementInput.jump && !flag3) {
                if (this.flyToggleTimer == 0) {
                    this.flyToggleTimer = 7;
                } else if (!this.isSwimming()) {
                    this.abilities.isFlying = !this.abilities.isFlying;
                    flag7 = true;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
        }

        if (this.movementInput.jump && !flag7 && !flag && !this.abilities.isFlying && !this.isPassenger() && !this.isOnLadder()) {
            ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (itemstack.canElytraFly(this) && this.tryToStartFallFlying()) {
                this.connection.sendPacket(new CEntityActionPacket(this, CEntityActionPacket.Action.START_FALL_FLYING));
            }
        }

        this.wasFallFlying = this.isElytraFlying();
        if (this.isInWater() && this.movementInput.sneaking && this.func_241208_cS_()) {
            this.handleFluidSneak();
        }

        if (this.areEyesInFluid(FluidTags.WATER)) {
            int i = this.isSpectator() ? 10 : 1;
            this.counterInWater = MathHelper.clamp(this.counterInWater + i, 0, 600);
        } else if (this.counterInWater > 0) {
            this.areEyesInFluid(FluidTags.WATER);
            this.counterInWater = MathHelper.clamp(this.counterInWater - 10, 0, 600);
        }

        if (this.abilities.isFlying && this.isCurrentViewEntity()) {
            int j = 0;
            if (this.movementInput.sneaking) {
                --j;
            }

            if (this.movementInput.jump) {
                ++j;
            }

            if (j != 0) {
                this.setMotion(this.getMotion().add(0.0D, (double)((float)j * this.abilities.getFlySpeed() * 3.0F), 0.0D));
            }
        }

        if (this.isRidingHorse()) {
            IJumpingMount ijumpingmount = (IJumpingMount)this.getRidingEntity();
            if (this.horseJumpPowerCounter < 0) {
                ++this.horseJumpPowerCounter;
                if (this.horseJumpPowerCounter == 0) {
                    this.horseJumpPower = 0.0F;
                }
            }

            if (flag && !this.movementInput.jump) {
                this.horseJumpPowerCounter = -10;
                ijumpingmount.setJumpPower(MathHelper.floor(this.getHorseJumpPower() * 100.0F));
                this.sendHorseJump();
            } else if (!flag && this.movementInput.jump) {
                this.horseJumpPowerCounter = 0;
                this.horseJumpPower = 0.0F;
            } else if (flag) {
                ++this.horseJumpPowerCounter;
                if (this.horseJumpPowerCounter < 10) {
                    this.horseJumpPower = (float)this.horseJumpPowerCounter * 0.1F;
                } else {
                    this.horseJumpPower = 0.8F + 2.0F / (float)(this.horseJumpPowerCounter - 9) * 0.1F;
                }
            }
        } else {
            this.horseJumpPower = 0.0F;
        }

        super.livingTick();
        if (this.onGround && this.abilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
            this.abilities.isFlying = false;
            this.sendPlayerAbilities();
        }

    }

    @Shadow
    public abstract boolean isForcedDown();
    @Shadow
    protected abstract void handlePortalTeleportation();
    @Shadow
    protected abstract void setPlayerOffsetMotion(double v, double v1);
    @Shadow
    protected abstract void sendHorseJump();
    @Shadow
    public abstract float getHorseJumpPower();
    @Shadow
    protected abstract boolean isCurrentViewEntity();
    @Shadow
    protected abstract boolean isUsingSwimmingAnimation();
    @Shadow
    public abstract boolean isRidingHorse();
    @Final
    @Shadow
    public ClientPlayNetHandler connection;
    @Shadow
    public MovementInput movementInput;
    @Final
    @Shadow
    protected Minecraft mc;
    protected int sprintToggleTimer;
    @Shadow
    public int sprintingTicksLeft;
    @Shadow
    private int horseJumpPowerCounter;
    @Shadow
    private float horseJumpPower;
    @Shadow
    private boolean autoJumpEnabled;
    @Shadow
    private int autoJumpTime;
    @Shadow
    private boolean wasFallFlying;
    @Shadow
    private boolean isCrouching;
    @Shadow
    private int counterInWater;
    @Shadow
    private boolean showDeathScreen;
}
