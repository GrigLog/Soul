package griglog.soul.items;

import griglog.soul.capability.SoulCap;
import griglog.soul.capability.SoulProvider;
import griglog.soul.items.misc.CreativeTab;
import griglog.soul.items.misc.IFastItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class Zanpakuto extends SwordItem implements IFastItem {
    public Zanpakuto() {
        super(ItemTier.IRON, 3, -2.4F,
                new Properties().group(CreativeTab.instance));
        setRegistryName("zanpakuto");
    }

    public float getSlowDown(){return 1;}

    @Override
    public int getUseDuration(ItemStack stack) {
        return 20;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand handIn) {
        ItemStack itemstack = player.getHeldItem(handIn);
        SoulCap soulCap = player.getCapability(SoulProvider.SOUL_CAP, null).resolve().get();
        if (soulCap.rightClicked){
            soulCap.rightClicked = false;
            soulCap.parryTimer = 15;
            player.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
        return ActionResult.resultPass(itemstack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entity, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entity, timeLeft);
        if (entity instanceof PlayerEntity)
            releaseChecks((PlayerEntity)entity);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        super.onItemUseFinish(stack, world, entity);
        if (entity instanceof PlayerEntity)
            releaseChecks((PlayerEntity)entity);
        return stack;
    }



    private void releaseChecks(PlayerEntity player) {
        LazyOptional<SoulCap> soulOpt = player.getCapability(SoulProvider.SOUL_CAP, null);
        if (soulOpt.isPresent()) {
            SoulCap soulCap = soulOpt.resolve().get();
            if (soulCap.CATimer == 0)
                player.getCooldownTracker().setCooldown(this, 10);
        }
    }

}
