package griglog.soul.client;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.world.ClientWorld;

public class BlueParticle extends RisingParticle {
    protected BlueParticle(ClientWorld world, double x, double y, double z, IAnimatedSprite spriteWithAge) {
        super(world, x, y, z, 0.1F, -0.1F, 0.1F, 0, 0, 0, 1, spriteWithAge, 0.5F, 20, -0.004D, false);
    }
}
