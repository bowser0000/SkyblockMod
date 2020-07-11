package me.Danker.commands;

import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class SetkeyCommand extends CommandBase implements ICommand {

	@Override
	public String getCommandName() {
		return "setkey";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName() + " [key]";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText("Usage: /setkey [key]"));
			return;
		}
		
		final ConfigHandler cf = new ConfigHandler();
		cf.writeStringConfig("api", "APIKey", arg1[0]);
		player.addChatMessage(new ChatComponentText("Set API key to " + arg1[0]));
	}

}
