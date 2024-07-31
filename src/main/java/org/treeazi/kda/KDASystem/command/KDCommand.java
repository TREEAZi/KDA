package org.treeazi.kda.KDASystem.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

import net.minecraft.server.level.ServerPlayer;
import org.treeazi.kda.KDASystem.KDASystem;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;

public class KDCommand{
    public KDCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("KD").then(Commands.argument("target", EntityArgument.players()).executes((commandContext) -> {
            return sendKDMessage(commandContext.getSource(), KDASystem.KDToString(EntityArgument.getPlayer(commandContext, "target")));})));

        dispatcher.register(Commands.literal("allKD").executes((commandContext) -> {
            return sendKDMessage(commandContext.getSource(), KDASystem.allKDToString());}));

        dispatcher.register(Commands.literal("clearKD").then(Commands.argument("target", EntityArgument.players()).requires(commandContext -> {
            return commandContext.hasPermission(2);
        }).executes(commandContext -> {
            return clearPlayerKD(commandContext.getSource(), EntityArgument.getPlayer(commandContext, "target"));
        })));
    }

    public static int sendKDMessage(CommandSourceStack commandSourceStack, String str) throws CommandSyntaxException {
        commandSourceStack.sendSystemMessage(Component.literal(str));
        return 1;
    }

    public static int clearPlayerKD(CommandSourceStack commandSourceStack, ServerPlayer pPlayer) throws CommandSyntaxException {
        if (KDASystem.clearKD(pPlayer))
            commandSourceStack.sendSystemMessage(Component.literal("KD已清除"));
        else
            commandSourceStack.sendSystemMessage(Component.literal("KD未清除,可能玩家不存在"));
        return 1;
    }
}
//public class KDCommand implements Command<CommandSourceStack> {
//    public static final Command<CommandSourceStack> INSTANCE = new KDCommand();
//    @Override
//    public int run(CommandContext<CommandSourceStack> commandContext) throws CommandSyntaxException {
//        commandContext.getSource().sendSystemMessage(Component.literal(KDASystem.KDToString()));
//        return 0;
//    }
//}
