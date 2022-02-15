package griglog.soul.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.network.NetworkHooks;

public class HolyArrow extends AbstractArrowEntity {
    public static EntityType<?> type = EntityType.Builder
            .of(HolyArrow::new, EntityClassification.MISC)
            .build("").setRegistryName("holy_arrow");
    public HolyArrow(World world, PlayerEntity player){
        super(Entities.holyArrow, player, world);
    }

    public HolyArrow (EntityType<?> type, World world){
        super(Entities.holyArrow, world);
    }

    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void playerTouch(PlayerEntity entityIn) {} //no pickup


    @Override
    protected ItemStack getPickupItem() {
        return null;
    }
}
