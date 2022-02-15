package griglog.soul.entities;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SkeletonEntity;


@SuppressWarnings("unchecked")
public class Entities {
    public static final EntityType<HolyArrow> holyArrow = (EntityType<HolyArrow>) HolyArrow.type;
    public static final EntityType<HolyBeam> holyBeam = (EntityType<HolyBeam>) HolyBeam.type;
    public static final EntityType<Hollow> hollow = (EntityType<Hollow>) Hollow.type;
    public static final EntityType<SkeletonEntity> rapidShooter = (EntityType<SkeletonEntity>) RapidShooterEntity.type;
    public static final EntityType<HollowRabbit> hollowRabbit = (EntityType<HollowRabbit>) HollowRabbit.type;

}
