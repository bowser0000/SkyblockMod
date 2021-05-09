package me.Danker.commands;

import java.util.Collections;
import java.util.List;
import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ToggleNoRotateCommand extends CommandBase implements ICommand {
    public static boolean norotatetoggled;

    public String getCommandName() {
        return "norotate";
    }

    public List<String> getCommandAliases() {
        return Collections.singletonList("norotate");
    }

    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " [name]";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        norotatetoggled = !norotatetoggled;
        EntityPlayer player = (EntityPlayer)arg0;
        ConfigHandler.writeBooleanConfig("toggles", "NoRotate", norotatetoggled);
        player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "NoRotate has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + norotatetoggled + DankersSkyblockMod.MAIN_COLOUR + "."));
    }
}
