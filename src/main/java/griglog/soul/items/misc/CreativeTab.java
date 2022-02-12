package griglog.soul.items.misc;

import griglog.soul.Soul;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CreativeTab extends ItemGroup {
    public static CreativeTab instance = new CreativeTab();
    public CreativeTab(){
        super(Soul.id);
        hideTitle();
    }
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(Items.zanpakuto);
    }
}
