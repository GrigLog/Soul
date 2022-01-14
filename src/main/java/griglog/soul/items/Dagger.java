package griglog.soul.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import griglog.soul.SF;
import griglog.soul.Soul;
import griglog.soul.capability.SoulCap;
import griglog.soul.items.misc.CreativeTab;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Dagger extends SwordItem {
    public Dagger(){
        super(ItemTier.IRON, 0, -2.4f, new Properties().group(CreativeTab.instance));  //these numbers dont mean anything
        setRegistryName("dagger");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (player.isCrouching()){
            CompoundNBT nbt = player.getHeldItem(hand).getTag();
            assert nbt != null;
            nbt.putBoolean("active", !nbt.getBoolean("active"));
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!(entity instanceof PlayerEntity))
            return;
        CompoundNBT nbt = stack.getTag();
        assert nbt != null;
        if (nbt.getBoolean("active")){
            SoulCap soul = SF.getSoul((PlayerEntity)entity);
            if (!soul.trySpendMana(0.3)){
                nbt.putBoolean("active", false);
            }
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity && !stack.getTag().getBoolean("active")){
            SoulCap soul = SF.getSoul((PlayerEntity)attacker);
            soul.addMana(3);
            SF.sendToClient((ServerPlayerEntity)attacker, soul);
        }
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlotType.MAINHAND) {
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier",
                    (stack.getTag().getBoolean("active") ? -2.4 : 0), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier",
                    (stack.getTag().getBoolean("active") ? 8 : 2), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

}
