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

public class MoveCommand extends CommandBase {

	public static int[] coordsXY = {0, 0};
	public static int[] displayXY = {0, 0};
	public static int[] dungeonTimerXY = {0, 0};
	public static int[] skill50XY = {0, 0};
	public static int[] lividHpXY = {0, 0};
	
	@Override
	public String getCommandName() {
		return "move";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <coords/display/dungeontimer/skill50/lividhp> <x> <y>";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "coords", "display", "dungeontimer", "skill50", "lividhp");
		}
		return null;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		final ConfigHandler cf = new ConfigHandler();
		
		if (arg1.length < 2) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: " + getCommandUsage(arg0)));
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
		} else if (arg1[0].equalsIgnoreCase("dungeontimer")) {
			dungeonTimerXY[0] = Integer.parseInt(arg1[1]);
			dungeonTimerXY[1] = Integer.parseInt(arg1[2]);
			cf.writeIntConfig("locations", "dungeonTimerX", dungeonTimerXY[0]);
			cf.writeIntConfig("locations", "dungeonTimerY", dungeonTimerXY[1]);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Dungeon timer has been moved to " + EnumChatFormatting.DARK_GREEN + arg1[1] + ", " + arg1[2]));
		} else if (arg1[0].equalsIgnoreCase("skill50")) {
			skill50XY[0] = Integer.parseInt(arg1[1]);
			skill50XY[1] = Integer.parseInt(arg1[2]);
			cf.writeIntConfig("locations", "skill50X", skill50XY[0]);
			cf.writeIntConfig("locations", "skill50Y", skill50XY[1]);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Skill 50 display has been moved to " + EnumChatFormatting.DARK_GREEN + arg1[1] + ", " + arg1[2]));
		} else if (arg1[0].equalsIgnoreCase("lividhp")) { 
			lividHpXY[0] = Integer.parseInt(arg1[1]);
			lividHpXY[1] = Integer.parseInt(arg1[2]);
			cf.writeIntConfig("locations", "lividHpX", lividHpXY[0]);
			cf.writeIntConfig("locations", "lividHpY", lividHpXY[1]);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Livid HP has been moved to " + EnumChatFormatting.DARK_GREEN + arg1[1] + ", " + arg1[2]));
		} else {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: " + getCommandUsage(arg0)));
		}
	}

}
