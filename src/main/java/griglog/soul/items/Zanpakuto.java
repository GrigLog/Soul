package griglog.soul.items;

import griglog.soul.SF;
import griglog.soul.capability.SoulCap;
import griglog.soul.items.misc.CreativeTab;
import griglog.soul.items.misc.IFastItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class Zanpakuto extends SwordItem implements IFastItem {
    public Zanpakuto() {
        super(ItemTier.IRON, 3, -2.4F,
                new Properties().tab(CreativeTab.instance));
        setRegistryName("zanpakuto");
    }

    public float getSlowDown(){return 1;}

    @Override
    public int getUseDuration(ItemStack stack) {
        return 20;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand handIn) {
        ItemStack itemstack = player.getItemInHand(handIn);
        SoulCap soulCap = SF.getSoul(player);
        if (soulCap.rightClicked){
            soulCap.rightClicked = false;
            soulCap.parryTimer = 15;
            player.startUsingItem(handIn);
            return ActionResult.consume(itemstack);
        }
        return ActionResult.pass(itemstack);
    }

    @Override
    //rmb is released
    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entity, int timeLeft) {
        super.releaseUsing(stack, worldIn, entity, timeLeft);
        if (entity instanceof PlayerEntity)
            onRelease((PlayerEntity)entity);
    }

    @Override
    //useDuration is over
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        super.finishUsingItem(stack, world, entity);
        if (entity instanceof PlayerEntity)
            onRelease((PlayerEntity)entity);
        return stack;
    }

    private void onRelease(PlayerEntity player) {
        SoulCap cap = SF.getSoul(player);
        if (cap.CATimer == 0)
            player.getCooldowns().addCooldown(this, 10);
    }

}
