package me.Danker.commands;

import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class MoveCommand extends CommandBase {

	public static int[] coordsXY = {0, 0};
	public static int[] displayXY = {0, 0};
	
	@Override
	public String getCommandName() {
		return "move";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName() + " <coords/display> <x> <y>";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		final ConfigHandler cf = new ConfigHandler();
		
		if (arg1.length < 2) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /move <coords/display> <x> <y>"));
			return;
		}
		
		if (arg1[0].equalsIgnoreCase("coords")) {
			coordsXY[0] = Integer.parseInt(arg1[1]);
			coordsXY[1] = Integer.parseInt(arg1[2]);
			cf.writeIntConfig("locations", "coordsX", coordsXY[0]);
			cf.writeIntConfig("locations", "coordsY", coordsXY[1]);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Coords have been moved to " + EnumChatFormatting.DARK_GREEN + arg1[1] + ", " + arg1[2]));
		} else if (arg1[0].equalsIgnoreCase("display")) {
			displayXY[0] = Integer.parseInt(arg1[1]);
			displayXY[1] = Integer.parseInt(arg1[2]);
			cf.writeIntConfig("locations", "displayX", displayXY[0]);
			cf.writeIntConfig("locations", "displayY", displayXY[1]);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Tracker display has been moved to " + EnumChatFormatting.DARK_GREEN + arg1[1] + ", " + arg1[2]));
		} else {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /move <coords/display> <x> <y>"));
		}
	}

}
