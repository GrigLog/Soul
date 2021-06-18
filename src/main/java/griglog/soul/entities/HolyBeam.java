package griglog.soul.entities;


import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;


public class HolyBeam extends ThrowableEntity {
    private int timer = 0;
    private float power;

    private final double range = 16;
    private final double particle_gap = 0.1;
    private final float maxDamage = 15;
    public HolyBeam(PlayerEntity player, float power){
        super(Entities.holyBeam, player, player.world);
        setMotion(player.getLookVec().scale(1));
        this.power = power;
    }



    public HolyBeam(EntityType<HolyBeam> type, World world) {
        super(Entities.holyBeam, world);
    }

    @Override
    protected void registerData() {
        //this.dataManager.register(?, ?);
    }

    public void tick(){
        for (double i = 0; i <= range; i+=particle_gap) {
            super.tick();
            if (!isAlive())
                break;
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, getPosX(), getPosY(), getPosZ(), 0, 0, 0);
        }
        setDead();
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        if (!this.world.isRemote) {
            Entity entity = result.getEntity();
            Entity shooter = getShooter();
            if (shooter instanceof LivingEntity) {
                entity.attackEntityFrom(DamageSource.MAGIC, maxDamage * power);
            }
        }
        setDead();
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult result) {  //onBlockHit, why the fuck is it obfuscated?...
        super.func_230299_a_(result);
        setDead();
    }

    @Override
    public void readAdditional(CompoundNBT compound) {

    }

    @Override
    public void writeAdditional(CompoundNBT compound) {

    }

    protected float getGravityVelocity() {
        return 0;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
