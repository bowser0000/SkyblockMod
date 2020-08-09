package me.Danker.commands;

import java.util.List;

import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class DisplayCommand extends CommandBase {
	public static String display;

	@Override
	public String getCommandName() {
		return "display";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName() + " <zombie/spider/wolf/fishing/off> [winter/session]";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "wolf", "spider", "zombie", "fishing", "off");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("fishing")) {
			return getListOfStringsMatchingLastWord(args, "winter", "session");
		} else if (args.length == 2 || (args.length == 3 && args[0].equalsIgnoreCase("fishing") && args[1].equalsIgnoreCase("winter"))) { 
			return getListOfStringsMatchingLastWord(args, "session");
		}
		return null;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /display <zombie/spider/wolf/fishing/off> [winter/session]"));
			return;
		}
		
		final ConfigHandler cf = new ConfigHandler();
		boolean showSession = false;
		
		if (arg1.length > 1) {
			if (arg1[1].equalsIgnoreCase("session")) {
				showSession = true;
			} else if (arg1.length > 2) {
				if (arg1[2].equalsIgnoreCase("session")) {
					showSession = true;
				}
			}
		}
		
		if (arg1[0].equalsIgnoreCase("wolf")) {
			if (showSession) {
				display = "wolf_session";
			} else {
				display = "wolf";
			}
		} else if (arg1[0].equalsIgnoreCase("spider")) {
			if (showSession) {
				display = "spider_session";
			} else {
				display = "spider";
			}
		} else if (arg1[0].equalsIgnoreCase("zombie")) {
			if (showSession) {
				display = "zombie_session";
			} else {
				display = "zombie";
			}
		} else if (arg1[0].equalsIgnoreCase("fishing")) {
			if (arg1.length > 1 && arg1[1].equalsIgnoreCase("winter")) {
				if (showSession) {
					display = "fishing_winter_session";
				} else {
					display = "fishing_winter";
				}
			} else {
				if (showSession) {
					display = "fishing_session";
				} else {
					display = "fishing";
				}
			} 
		} else if (arg1[0].equalsIgnoreCase("off")) {
			display = "off";
		} else {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /display <zombie/spider/wolf/fishing/off> [winter/session]"));
			return;
		}
		player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Display set to " + EnumChatFormatting.DARK_GREEN + display + EnumChatFormatting.GREEN + "."));
		cf.writeStringConfig("misc", "display", display);
	}

}
