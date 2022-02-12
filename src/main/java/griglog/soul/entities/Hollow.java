package griglog.soul.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class Hollow extends MonsterEntity {
    public PlayerEntity targetPlayer;
    protected Hollow(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 30.0F));
    }

    public static AttributeModifierMap getEntityAttributes(){
        return MonsterEntity.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .build();
    }

}
