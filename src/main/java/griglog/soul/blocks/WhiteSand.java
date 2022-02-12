package griglog.soul.blocks;

import griglog.soul.Soul;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.ResourceLocation;

public class WhiteSand extends SandBlock {
    public WhiteSand() {
        super(14406560, AbstractBlock.Properties
                .of(Material.SAND, MaterialColor.SAND)
                .strength(0.5F)
                .sound(SoundType.SAND));
        setRegistryName(new ResourceLocation(Soul.id, "sand"));
    }
}
