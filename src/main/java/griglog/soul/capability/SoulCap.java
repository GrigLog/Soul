package griglog.soul.capability;

import net.minecraft.nbt.CompoundNBT;

public class SoulCap {
    int parryTimer = 0;
    int counterattackTimer = 0;
    boolean justParried = false;
    boolean rightClicked = false;
    boolean leftClicked = false;
    public int getParryTimer() {
        return parryTimer;
    }
    public void setParryTimer(int a) {
        parryTimer = a;
    }
    public void addParryTimer(int a) {
        parryTimer += a;
    }
    public int getCATimer() { return counterattackTimer;}
    public void setCATimer(int a) {counterattackTimer = a;}
    public void addCATimer(int a) { counterattackTimer += a;}
    public boolean isJustParried(){ return justParried;}
    public SoulCap setJustParried(boolean a){ justParried = a; return this;}
    public boolean getRightClicked() {return rightClicked;}
    public void setRightClicked(boolean a) {rightClicked = a;}
    public boolean getLeftClicked() {return leftClicked;}
    public void setLeftClicked(boolean a) {leftClicked = a;}


    public CompoundNBT getNbt(){
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("parryTimer", getParryTimer());
        tag.putInt("CATimer", getCATimer());
        tag.putBoolean("justParried", isJustParried());
        tag.putBoolean("rightClicked", getRightClicked());
        tag.putBoolean("leftClicked", getLeftClicked());
        return tag;
    }

    public SoulCap setNbt(CompoundNBT nbt){
        setParryTimer(nbt.getInt("parryTimer"));
        setCATimer(nbt.getInt("CATimer"));
        setJustParried(nbt.getBoolean("justParried"));
        setRightClicked(nbt.getBoolean("rightClicked"));
        setLeftClicked(nbt.getBoolean("leftClicked"));
        return this;
    }
}
