package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class GetkeyCommand extends CommandBase implements ICommand {

	@Override
	public String getCommandName() {
		return "getkey";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName();
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
	    EntityPlayer player = (EntityPlayer)arg0;
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    StringSelection stringSelection = new StringSelection(ConfigHandler.getString("api", "APIKey"));
	    
	    if (ConfigHandler.getString("api", "APIKey").equals("")) {
	      player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "API key not set. Set your API key using /setkey."));
	    }
	    
	    clipboard.setContents(stringSelection, null);
		player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Your set API key is " + DankersSkyblockMod.SECONDARY_COLOUR + ConfigHandler.getString("api", "APIKey") + "\n" +
													DankersSkyblockMod.MAIN_COLOUR + " Your set API key has been copied to the clipboard."));

	}

}
