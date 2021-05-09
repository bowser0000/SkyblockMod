package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class SwapCommand extends CommandBase implements ICommand {
    public static int swapDelay;

    public String getCommandName() {
        return "swapdelay";
    }

    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " delayMS, 0 disables swap";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        EntityPlayer player = (EntityPlayer)arg0;
        if (arg1.length == 0) {
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
            return;
        }
        swapDelay = Integer.parseInt(arg1[0]);
        ConfigHandler.writeIntConfig("macro", "swap", swapDelay);
        player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Swap delay set to " + Integer.parseInt(arg1[0])));
    }
}
