package griglog.soul.capability;

import griglog.soul.Soul;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public class SoulCap {
    public int parryTimer = 0;
    public int CATimer = 0;
    public boolean justParried = false;
    public boolean rightClicked = false;
    public boolean leftClicked = false;
    public double mana = 0;
    public double maxMana = 10;
    public HashMap<String, Integer> usedKeyItems = new HashMap<>();

    public boolean trySpendMana(double a) {
        if (mana >= a){
            mana -= a;
            return true;
        }
        return false;
    }
    public void addMana(double a){
        mana += a;
        if (mana > maxMana)
            mana = maxMana;
    }


    public CompoundNBT getNbt(){
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("parryTimer", parryTimer);
        tag.putInt("CATimer", CATimer);
        tag.putBoolean("justParried", justParried);
        tag.putBoolean("rightClicked", rightClicked);
        tag.putBoolean("leftClicked", leftClicked);
        tag.putDouble("mana", mana);
        tag.putDouble("maxMana", maxMana);
        CompoundNBT keyItems = new CompoundNBT();
        for (String s : usedKeyItems.keySet()){
            keyItems.putInt(s, usedKeyItems.get(s));
        }
        tag.put("usedKeyItems", keyItems);
        return tag;
    }

    public SoulCap setNbt(CompoundNBT nbt){
        parryTimer = nbt.getInt("parryTimer");
        CATimer=  nbt.getInt("CATimer");
        justParried = nbt.getBoolean("justParried");
        rightClicked = nbt.getBoolean("rightClicked");
        leftClicked =  nbt.getBoolean("leftClicked");
        mana = nbt.getDouble("mana");
        maxMana = nbt.getDouble("maxMana");
        CompoundNBT keyItems = nbt.getCompound("usedKeyItems");
        for (String s : keyItems.keySet()){
            usedKeyItems.put(s, keyItems.getInt(s));
        }
        return this;
    }

    public static class SoulProvider implements ICapabilitySerializable<INBT> {
        @CapabilityInject(SoulCap.class)
        public static Capability<SoulCap> SOUL_CAP;
        private final LazyOptional<SoulCap> instance = LazyOptional.of(() -> new SoulCap());

        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return cap == SOUL_CAP ? instance.cast() : LazyOptional.empty();
        }

        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
            return cap == SOUL_CAP ? instance.cast() : LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT() {
            return SOUL_CAP.getStorage().writeNBT(SOUL_CAP, this.instance.resolve().get(), null);
        }

        @Override
        public void deserializeNBT(INBT nbt) {
            SOUL_CAP.getStorage().readNBT(SOUL_CAP, this.instance.resolve().get(), null, nbt);
        }
    }

    public static class SoulStorage implements Capability.IStorage<SoulCap> {
        @Override
        public INBT writeNBT(Capability<SoulCap> capability, SoulCap soulCap, Direction side) {
            return soulCap.getNbt();
        }

        @Override
        public void readNBT(Capability<SoulCap> capability, SoulCap soulCap, Direction side, INBT nbt) {
            soulCap.setNbt((CompoundNBT)nbt);
        }
    }
}
