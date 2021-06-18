package griglog.soul.capability;

import net.minecraft.nbt.CompoundNBT;

public class SoulCap {
    public int parryTimer = 0;
    public int CATimer = 0;
    public boolean justParried = false;
    public boolean rightClicked = false;
    public boolean leftClicked = false;
    public double mana = 0;
    public double maxMana = 10;

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
        return this;
    }
}
