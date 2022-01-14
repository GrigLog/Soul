package griglog.soul.items.misc;

import griglog.soul.items.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

public class Items {
    public static final Item zanpakuto = new Zanpakuto();
    public static final Item holyBow = new HolyBow();
    public static final Item badge = new Badge();
    public static final Item dagger = new Dagger();

    public static final Item reishiChest = new Armor(EquipmentSlotType.CHEST);
    public static final Item reishiLeg = new Armor(EquipmentSlotType.LEGS);
}
