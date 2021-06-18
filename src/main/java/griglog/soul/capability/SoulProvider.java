package griglog.soul.capability;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SoulProvider implements ICapabilityProvider {
    @CapabilityInject(SoulCap.class)
    public static Capability<SoulCap> SOUL_CAP;
    private LazyOptional<SoulCap> instance = LazyOptional.of(() -> new SoulCap());

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == SOUL_CAP ? instance.cast() : LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return cap == SOUL_CAP ? instance.cast() : LazyOptional.empty();
    }
}
