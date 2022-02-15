package griglog.soul.entities;

import griglog.soul.Soul;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class HollowRabbit extends RabbitEntity {
    public static EntityType<?> type = EntityType.Builder
            .of(HollowRabbit::new, EntityClassification.CREATURE)
            .sized(0.4F, 0.5F)
            .clientTrackingRange(8)
            .build("")
            .setRegistryName(Soul.id, "hollow_rabbit");
    public static final DataParameter<Boolean> DATA_ANGRY = EntityDataManager.defineId(HollowRabbit.class, DataSerializers.BOOLEAN);

    public HollowRabbit(EntityType<? extends RabbitEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_ANGRY, false);
    }

    @Override
    protected void registerGoals() {  //TODO: faster jump speed when angry
        this.goalSelector.addGoal(1, new SwimGoal(this));
        //this.goalSelector.addGoal(1, new RabbitEntity.PanicGoal(this, 2.2D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(Items.CARROT, Items.GOLDEN_CARROT, Blocks.DANDELION), false));
        //his.goalSelector.addGoal(4, new RabbitEntity.AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 2.2D, 2.2D));
        //this.goalSelector.addGoal(4, new RabbitEntity.AvoidEntityGoal<>(this, WolfEntity.class, 10.0F, 2.2D, 2.2D));
        //this.goalSelector.addGoal(4, new RabbitEntity.AvoidEntityGoal<>(this, MonsterEntity.class, 4.0F, 2.2D, 2.2D));
        //this.goalSelector.addGoal(5, new RabbitEntity.RaidFarmGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 10.0F));

        goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false));
        targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, //
                0, //randomInterval
                false, //mustSee
                false, //mustReach
                this::attackPredicate));
    }


    @Nonnull
    public static AttributeModifierMap.MutableAttribute createMobAttributes() {
        return RabbitEntity.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.ATTACK_DAMAGE, 1);
    }

    private boolean attackPredicate(LivingEntity target){
        boolean angry = (target.getHealth() <= 6 && Math.abs(target.getY() - this.getY()) <= 4.0D);
        entityData.set(DATA_ANGRY, angry);
        return angry;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        entityData.set(DATA_ANGRY, tag.getBoolean("angry"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("angry", entityData.get(DATA_ANGRY));
    }
}
