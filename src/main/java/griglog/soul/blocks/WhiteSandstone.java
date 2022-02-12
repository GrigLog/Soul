package griglog.soul.blocks;


import griglog.soul.Soul;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SandBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class WhiteSandstone extends Block {
    public WhiteSandstone() {
        super(AbstractBlock.Properties
                .of(Material.STONE, MaterialColor.SAND)
                .requiresCorrectToolForDrops()
                .strength(0.8F));
        setRegistryName(Soul.id, "white_sandstone");
    }
}
