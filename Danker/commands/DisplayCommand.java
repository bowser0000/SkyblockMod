package me.Danker.commands;

import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class DisplayCommand extends CommandBase {
	public static String display;

	@Override
	public String getCommandName() {
		return "display";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName() + " [zombie/spider/wolf/off]";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText("Usage: /display [zombie/spider/wolf/off]"));
			return;
		}
		
		final ConfigHandler cf = new ConfigHandler();
		
		if (arg1[0].equalsIgnoreCase("wolf")) {
			display = "wolf";
		} else if (arg1[0].equalsIgnoreCase("spider")) {
			display = "spider";
		} else if (arg1[0].equalsIgnoreCase("zombie")) {
			display = "zombie";
		} else if (arg1[0].equalsIgnoreCase("off")) {
			display = "off";
		} else {
			player.addChatMessage(new ChatComponentText("Usage: /display [zombie/spider/wolf/off]"));
			return;
		}
		player.addChatMessage(new ChatComponentText("Display set to " + display + "."));
		cf.writeStringConfig("misc", "display", display);
	}

}
