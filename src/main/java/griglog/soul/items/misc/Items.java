package griglog.soul.items.misc;

import griglog.soul.items.Armor;
import griglog.soul.items.Badge;
import griglog.soul.items.HolyBow;
import griglog.soul.items.Zanpakuto;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

public class Items {
    public static final Item zanpakuto = new Zanpakuto();
    public static final Item holyBow = new HolyBow();
    public static final Item badge = new Badge();

    public static final Item reishiChest = new Armor(EquipmentSlotType.CHEST, "reishi_chest");
}
