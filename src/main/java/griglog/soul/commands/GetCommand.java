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
public class GetCommand {
    @SubscribeEvent
    public static void register(RegisterCommandsEvent event){
        event.getDispatcher().register(Commands.literal("soul_get")
                .requires((context) -> context.hasPermissionLevel(4))
                .executes(GetCommand::execute));
    }

    public static int execute(CommandContext<CommandSource> context){
        ServerPlayerEntity player = (ServerPlayerEntity)context.getSource().getEntity();
        if (player == null){
            context.getSource().sendErrorMessage(new StringTextComponent("ServerPlayer is null!"));
            return 0;
        }
        SoulCap cap = SF.getSoul(player);
        String text = cap.getNbt().getString();
        context.getSource().sendFeedback(new StringTextComponent(text), true);
        return 0;
    }
}
