package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class MoveCommand extends CommandBase {

	public static int[] coordsXY = {0, 0};
	public static int[] displayXY = {0, 0};
	public static int[] dungeonTimerXY = {0, 0};
	public static int[] skill50XY = {0, 0};
	public static int[] lividHpXY = {0, 0};
	public static int[] cakeTimerXY = {0, 0};
	public static int[] skillTrackerXY = {0, 0};
	public static int[] waterAnswerXY = {0, 0};
	public static int[] bonzoTimerXY = {0, 0};

	@Override
	public String getCommandName() {
		return "move";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <coords/display/dungeontimer/skill50/lividhp/caketimer/skilltracker/wateranswer/bonzotimer> <x> <y>";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "coords", "display", "dungeontimer", "skill50", "lividhp", "caketimer", "skilltracker", "wateranswer", "bonzotimer");
		}
		return null;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		
		if (arg1.length < 2) {
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		switch (arg1[0].toLowerCase()) {
			case "coords":
				coordsXY[0] = Integer.parseInt(arg1[1]);
				coordsXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "coordsX", coordsXY[0]);
				ConfigHandler.writeIntConfig("locations", "coordsY", coordsXY[1]);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Coords have been moved to " + DankersSkyblockMod.SECONDARY_COLOUR + arg1[1] + ", " + arg1[2]));
				break;
			case "display":
				displayXY[0] = Integer.parseInt(arg1[1]);
				displayXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "displayX", displayXY[0]);
				ConfigHandler.writeIntConfig("locations", "displayY", displayXY[1]);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Tracker display has been moved to " + DankersSkyblockMod.SECONDARY_COLOUR + arg1[1] + ", " + arg1[2]));
				break;
			case "dungeontimer":
				dungeonTimerXY[0] = Integer.parseInt(arg1[1]);
				dungeonTimerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "dungeonTimerX", dungeonTimerXY[0]);
				ConfigHandler.writeIntConfig("locations", "dungeonTimerY", dungeonTimerXY[1]);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Dungeon timer has been moved to " + DankersSkyblockMod.SECONDARY_COLOUR + arg1[1] + ", " + arg1[2]));
				break;
			case "skill50":
				skill50XY[0] = Integer.parseInt(arg1[1]);
				skill50XY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "skill50X", skill50XY[0]);
				ConfigHandler.writeIntConfig("locations", "skill50Y", skill50XY[1]);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill 50 display has been moved to " + DankersSkyblockMod.SECONDARY_COLOUR + arg1[1] + ", " + arg1[2]));
				break;
			case "lividhp":
				lividHpXY[0] = Integer.parseInt(arg1[1]);
				lividHpXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "lividHpX", lividHpXY[0]);
				ConfigHandler.writeIntConfig("locations", "lividHpY", lividHpXY[1]);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Livid HP has been moved to " + DankersSkyblockMod.SECONDARY_COLOUR + arg1[1] + ", " + arg1[2]));
				break;
			case "caketimer":
				cakeTimerXY[0] = Integer.parseInt(arg1[1]);
				cakeTimerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "cakeTimerX", cakeTimerXY[0]);
				ConfigHandler.writeIntConfig("locations", "cakeTimerY", cakeTimerXY[1]);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Cake timer has been moved to " + DankersSkyblockMod.SECONDARY_COLOUR + arg1[1] + ", " + arg1[2]));
				break;
			case "skilltracker":
				skillTrackerXY[0] = Integer.parseInt(arg1[1]);
				skillTrackerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "skillTrackerX", skillTrackerXY[0]);
				ConfigHandler.writeIntConfig("locations", "skillTrackerY", skillTrackerXY[1]);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker has been moved to " + DankersSkyblockMod.SECONDARY_COLOUR + arg1[1] + ", " + arg1[2]));
				break;
			case "wateranswer":
				waterAnswerXY[0] = Integer.parseInt(arg1[1]);
				waterAnswerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "waterAnswerX", waterAnswerXY[0]);
				ConfigHandler.writeIntConfig("locations", "waterAnswerY", waterAnswerXY[1]);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Water solver answer has been moved to " + DankersSkyblockMod.SECONDARY_COLOUR + arg1[1] + ", " + arg1[2]));
				break;
			case "bonzotimer":
				bonzoTimerXY[0] = Integer.parseInt(arg1[1]);
				bonzoTimerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "bonzoTimerX", bonzoTimerXY[0]);
				ConfigHandler.writeIntConfig("locations", "bonzoTimerX", bonzoTimerXY[1]);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Bonzo's Mask timer has been moved to " + DankersSkyblockMod.SECONDARY_COLOUR + arg1[1] + ", " + arg1[2]));
				break;
			default:
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
		}
	}

}
