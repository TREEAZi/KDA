package org.treeazi.kda.KDASystem.event;

import com.mojang.brigadier.CommandDispatcher;
import org.treeazi.kda.KDASystem.command.KDCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import static org.treeazi.kda.BattleKDAMain.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class CommandEvent {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        KDCommand.register(dispatcher);
        ConfigCommand.register(dispatcher);
    }
}
