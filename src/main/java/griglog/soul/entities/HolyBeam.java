package griglog.soul.entities;


import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
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
    public static EntityType<?> type = EntityType.Builder
            .of(HolyBeam::new, EntityClassification.MISC)
            .build("")
            .setRegistryName("holy_beam");
    private int timer = 0;
    private float power;

    private final double range = 16;
    private final double particle_gap = 0.1;
    private final float maxDamage = 15;
    public HolyBeam(PlayerEntity player, float power){
        super(Entities.holyBeam, player, player.level);
        setDeltaMovement(player.getLookAngle().scale(1));
        this.power = power;
    }


    public HolyBeam(EntityType<?> type, World world) {
        super(Entities.holyBeam, world);
    }

    @Override
    protected void defineSynchedData() {
        //this.dataManager.register(?, ?);
    }

    public void tick(){
        for (double i = 0; i <= range; i+=particle_gap) {
            super.tick();
            if (!isAlive())
                break;
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, getX(), getY(), getZ(), 0, 0, 0);
        }
        removeAfterChangingDimensions();
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        if (!this.level.isClientSide) {
            Entity entity = result.getEntity();
            Entity shooter = getOwner();
            if (shooter instanceof LivingEntity) {
                entity.hurt(DamageSource.MAGIC, maxDamage * power);
            }
        }
        removeAfterChangingDimensions();
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult result) {  //onBlockHit, why the fuck is it obfuscated?...
        super.onHitBlock(result);
        removeAfterChangingDimensions();
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {

    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {

    }

    protected float getGravity() {
        return 0;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
