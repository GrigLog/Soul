package griglog.soul.entities;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import griglog.soul.entities.HolyArrow;


@SuppressWarnings("unchecked")
public class Entities {
    public static final EntityType<HolyArrow> holyArrow = (EntityType<HolyArrow>) EntityType.Builder
            .<HolyArrow>create(HolyArrow::new, EntityClassification.MISC)
            .build("").setRegistryName("holy_arrow");
    public static final EntityType<HolyBeam> holyBeam = (EntityType<HolyBeam>) EntityType.Builder
            .<HolyBeam>create(HolyBeam::new, EntityClassification.MISC)
            .build("").setRegistryName("holy_beam");
    public static final EntityType<Hollow> hollow = (EntityType<Hollow>) EntityType.Builder
            .create(Hollow::new, EntityClassification.CREATURE)
            .build("").setRegistryName("hollow");

}
