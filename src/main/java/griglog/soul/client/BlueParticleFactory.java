package griglog.soul.client;

import net.minecraft.client.particle.AshParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

public class BlueParticleFactory implements IParticleFactory<BasicParticleType> {
    private final IAnimatedSprite sprite;

    public BlueParticleFactory(IAnimatedSprite sprite) {
        this.sprite = sprite;
    }

    public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return new BlueParticle(worldIn, x, y, z, sprite);
    }
}
