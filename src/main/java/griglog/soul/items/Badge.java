package griglog.soul.items;

import griglog.soul.SF;
import griglog.soul.Soul;
import griglog.soul.capability.SoulCap;
import griglog.soul.items.misc.CreativeTab;
import griglog.soul.packets.PacketSender;
import griglog.soul.packets.SoulPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Badge extends Item {
    public Badge(){
        super(new Properties().group(CreativeTab.instance));
        setRegistryName("badge");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        SoulCap cap = SF.getSoul(player);
        if (cap.usedKeyItems.getOrDefault("badge", 0) < 1){
            cap.usedKeyItems.put("badge", 1);
            cap.maxMana += 10;
            PacketSender.INSTANCE.sendToServer(new SoulPacket(cap));
        }
        return ActionResult.resultSuccess(stack);
    }
}
