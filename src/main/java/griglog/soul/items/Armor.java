package griglog.soul.items;

import griglog.soul.client.model.CustomArmorModel;
import griglog.soul.items.misc.CreativeTab;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Armor extends ArmorItem {
    public Armor(EquipmentSlotType slot) {
        super(new Reishi(), slot, new Properties().group(CreativeTab.instance));
        setRegistryName("reishi_" + slot.getName());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "soul:textures/armor/custom_armor.png";
    }



    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return (A) new CustomArmorModel();
    }

    public static class Reishi implements IArmorMaterial {
        @Override
        public int getDurability(EquipmentSlotType slotIn) {
            return 100;
        }

        @Override
        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
            return 1;
        }

        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public SoundEvent getSoundEvent() {
            return null;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return null;
        }

        @Override
        public String getName() {
            return "reishi";
        }

        @Override
        public float getToughness() {
            return 2;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    }
}
