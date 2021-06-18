package griglog.soul.entities;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import griglog.soul.entities.HolyArrow;


public class Entities {
    public static final EntityType<HolyArrow> holyArrow = EntityType.Builder
            .<HolyArrow>create(HolyArrow::new, EntityClassification.MISC)
            .build("");
    public static final EntityType<HolyBeam> holyBeam = EntityType.Builder
            .<HolyBeam>create(HolyBeam::new, EntityClassification.MISC)
            .build("");
}
