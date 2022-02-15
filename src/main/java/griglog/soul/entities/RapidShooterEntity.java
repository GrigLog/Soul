package griglog.soul.entities;

import griglog.soul.Soul;
import griglog.soul.entities.ai.FastRangedBowAttackGoal;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class RapidShooterEntity extends SkeletonEntity {
    public static EntityType<?> type = EntityType.Builder
            .of(RapidShooterEntity::new, EntityClassification.MONSTER)
            .sized(0.6F, 1.99F)
            .clientTrackingRange(8)
            .build("")
            .setRegistryName(Soul.id, "rapid_shooter");

    public FastRangedBowAttackGoal<AbstractSkeletonEntity> bowGoal = new FastRangedBowAttackGoal<>(this, 1.0D, 40, 15.0F, 10);
    int arrowsToPause = 30;

    public RapidShooterEntity(EntityType<? extends SkeletonEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void reassessWeaponGoal() {
        if (this.level != null && !this.level.isClientSide) {
            this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.removeGoal(this.bowGoal);
            ItemStack itemstack = this.getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.item.BowItem));
            if (itemstack.getItem() == Items.BOW) {
                bowGoal.setMinAttackInterval(0);
                this.goalSelector.addGoal(4, this.bowGoal);
            } else {
                this.goalSelector.addGoal(4, this.meleeGoal);
            }
        }
    }

    /*@Override
    public void performRangedAttack(LivingEntity p_82196_1_, float p_82196_2_) {
        super.performRangedAttack(p_82196_1_, p_82196_2_);
        if (--arrowsToPause == 0){
            arrowsToPause
        }
        boolean pause = random.nextInt(10) == 0;
        if (pause){
            bowGoal.attackTime = 40;
            bowGoal.setMinAttackInterval(40);
        } else {
            bowGoal.attackTime = 7;
            bowGoal.setMinAttackInterval(0);
        }
    }*/
}
