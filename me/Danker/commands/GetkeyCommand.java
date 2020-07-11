package me.Danker.commands;

import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class GetkeyCommand extends CommandBase implements ICommand {

	@Override
	public String getCommandName() {
		return "getkey";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName();
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		final ConfigHandler cf = new ConfigHandler();
		
		if (cf.getString("api", "APIKey").equals("")) {
			player.addChatMessage(new ChatComponentText("API key not set. Set your API key using /setkey."));
		} else {
			player.addChatMessage(new ChatComponentText("Your set API key is " + cf.getString("api", "APIKey")));
		}
	}

}
