package griglog.soul.commands;

import com.mojang.brigadier.context.CommandContext;
import griglog.soul.SF;
import griglog.soul.capability.SoulCap;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ResetCommand {
    @SubscribeEvent
    public static void register(RegisterCommandsEvent event){
        event.getDispatcher().register(Commands.literal("soul_reset")
                .requires((context) -> context.hasPermission(4))
                .executes(ResetCommand::execute));
    }
    //TODO: fix re-entering required
    public static int execute(CommandContext<CommandSource> context){
        ServerPlayerEntity player = (ServerPlayerEntity)context.getSource().getEntity();
        if (player == null){
            context.getSource().sendFailure(new StringTextComponent("ServerPlayer is null!"));
            return 0;
        }
        SoulCap cap = SF.getSoul(player);
        cap.maxMana = 10;
        cap.usedKeyItems.clear();
        context.getSource().sendSuccess(new StringTextComponent("All upgrades forgotten. Please re-enter.").withStyle(TextFormatting.GREEN), true);
        return 0;
    }
}
