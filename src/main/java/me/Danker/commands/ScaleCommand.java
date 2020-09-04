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

public class ScaleCommand extends CommandBase {

	public static double coordsScale;
	public static double displayScale;
	public static double dungeonTimerScale;
	
	@Override
	public String getCommandName() {
		return "scale";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName() + " <coords/display/dungeontimer> <size (0.1 - 10)>";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "coords", "display", "dungeontimer");
		}
		return null;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length < 2) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /scale <coords/display/dungeontimer> <size (0.1 - 10)>"));
			return;
		}
		
		double scaleAmount = (double) Math.floor(Double.parseDouble(arg1[1]) * 10.0) / 10.0;
		if (scaleAmount < 0.1 || scaleAmount > 10.0) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Scale multipler can only be between 0.1x and 10x."));
			return;
		}
		
		if (arg1[0].equalsIgnoreCase("coords")) {
			coordsScale = scaleAmount;
			ConfigHandler.writeDoubleConfig("scales", "coordsScale", coordsScale);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Coords have been scaled to " + EnumChatFormatting.DARK_GREEN + coordsScale + "x"));
		} else if (arg1[0].equalsIgnoreCase("display")) {
			displayScale = scaleAmount;
			ConfigHandler.writeDoubleConfig("scales", "displayScale", displayScale);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Display has been scaled to " + EnumChatFormatting.DARK_GREEN + displayScale + "x"));
		} else if (arg1[0].equalsIgnoreCase("dungeontimer")) {
			dungeonTimerScale = scaleAmount;
			ConfigHandler.writeDoubleConfig("scales", "dungeonTimerScale", dungeonTimerScale);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Dungeon timer has been scaled to " + EnumChatFormatting.DARK_GREEN + dungeonTimerScale + "x"));
		} else {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /scale <coords/display/dungeontimer> <size (0.1 - 10)>"));
		}
	}

}
