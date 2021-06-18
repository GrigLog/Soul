package griglog.soul.items;

import griglog.soul.SF;
import griglog.soul.Soul;
import griglog.soul.capability.SoulCap;
import griglog.soul.capability.SoulProvider;
import griglog.soul.entities.HolyArrow;
import griglog.soul.entities.HolyBeam;
import griglog.soul.items.misc.CreativeTab;
import griglog.soul.items.misc.IFastItem;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HolyBow extends BowItem implements IFastItem {
    boolean beam_release = false;
    static final float speedMult = 1.1f;
    public HolyBow() {
        super(new Item.Properties().group(CreativeTab.instance));
        setRegistryName("holy_bow");
    }

    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        SoulCap soulCap = player.getCapability(SoulProvider.SOUL_CAP, null).resolve().get();
        //Soul.LOGGER.info("onItemRightClick " + soulCap.getRightClicked());
        if (soulCap.getRightClicked()){
            soulCap.setRightClicked(false);

            boolean flag = canFire(itemstack, player);  //custom check

            ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, world, player, hand, flag);
            if (ret != null) return ret;

            if (!player.abilities.isCreativeMode && !flag) {
                return ActionResult.resultFail(itemstack);
            } else {
                player.setActiveHand(hand);
                return ActionResult.resultConsume(itemstack);
            }
        }
        return ActionResult.resultPass(itemstack);
    }



    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) entity;
        //Soul.LOGGER.info(SF.world(world) + " " + ticksUsed + " " + getUseDuration(stack));
        if (beam_release){  //we just fired a special, no arrows as a follow-up!
            beam_release = false;
            return;
        }
        if (getTicksUsed(stack, player) >= 3 && canFire(stack, player)) {
            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, player, i, true);
            float speed = getArrowVelocity(i);

            HolyArrow arrow = new HolyArrow(world, player);
            arrow.setDirectionAndMovement(player, player.rotationPitch, player.rotationYaw, 0.0F, speed * 3, 1.0F);
            if (speed == speedMult)
                arrow.setIsCritical(true);

            //TODO: bonus damage from enchantments

            if (!world.isRemote) {
                world.addEntity(arrow);
                SF.playSoundPlayer("arrow_shoot", player);
            }
        }
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (!(entity instanceof PlayerEntity))
            return true;
        PlayerEntity player = (PlayerEntity)entity;
        if (!player.world.isRemote && player.isHandActive()){
            //Soul.LOGGER.info(SF.world(player.world) + " " + ticksUsed + " " + getUseDuration(stack));
            player.world.addEntity(new HolyBeam(player, Math.min(getTicksUsed(stack, player), 20) / 20f));
        }
        beam_release = true;
        player.stopActiveHand();
        return true;
    }

    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity)
    {
        return true;  //impossible to hit enemies with left-click
    }

    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player)
    {
        return true;  //impossible to break blocks
    }

    private int getTicksUsed(ItemStack stack, PlayerEntity player){
        return getUseDuration(stack) - player.getItemInUseCount();
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 40;
    }

    private boolean canFire(ItemStack itemstack, PlayerEntity player) {
        return true;
    }

    public static float getArrowVelocity(int charge){
        return BowItem.getArrowVelocity(charge) * speedMult;
    }

    public float getSlowDown() { return 0.8f; }
}
