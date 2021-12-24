package griglog.soul.items;

import griglog.soul.items.misc.CreativeTab;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;

public class Dagger extends SwordItem {
    public Dagger(){
        super(ItemTier.IRON, 3, -2.4f, new Properties().group(CreativeTab.instance));
        setRegistryName("assassin_dagger");
    }
}
