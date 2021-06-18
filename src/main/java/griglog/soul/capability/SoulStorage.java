package griglog.soul.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SoulStorage implements Capability.IStorage<SoulCap> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<SoulCap> capability, SoulCap instance, Direction side) {
        return instance.getNbt();
    }

    @Override
    public void readNBT(Capability<SoulCap> capability, SoulCap instance, Direction side, INBT nbt) {
        instance.setNbt((CompoundNBT)nbt);
    }
}
