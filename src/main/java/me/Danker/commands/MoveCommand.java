package me.Danker.commands;

import me.Danker.config.ModConfig;
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
	public static int[] golemTimerXY = {0 ,0};
	public static int[] teammatesInRadiusXY = {0, 0};
	public static int[] giantHPXY = {0, 0};
	public static int[] abilityCooldownsXY = {0, 0};
	public static int[] dungeonScoreXY = {0, 0};
	public static int[] firePillarXY = {0, 0};
	public static int[] minibossTimerXY = {0, 0};
	public static int[] powderTrackerXY = {0, 0};

	@Override
	public String getCommandName() {
		return "move";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <coords/display/dungeontimer/skill50/lividhp/caketimer/skilltracker/" +
										"wateranswer/bonzotimer/golemtimer/teammatesinradius/gianthp/" +
										"abilitycooldowns/dungeonscore/firepillar/minibosstimer/powdertracker> <x> <y>";
	}

	public static String usage(ICommandSender arg0) {
		return new MoveCommand().getCommandUsage(arg0);
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "coords", "display", "dungeontimer", "skill50", "lividhp", "caketimer",
														  "skilltracker", "wateranswer", "bonzotimer", "golemtimer", "teammatesinradius",
														  "gianthp", "abilitycooldowns", "dungeonscore", "firepillar", "minibosstimer",
														  "powdertracker");
		}
		return null;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		
		if (arg1.length < 2) {
			player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		switch (arg1[0].toLowerCase()) {
			case "coords":
				coordsXY[0] = Integer.parseInt(arg1[1]);
				coordsXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "coordsX", coordsXY[0]);
				ConfigHandler.writeIntConfig("locations", "coordsY", coordsXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Coords have been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "display":
				displayXY[0] = Integer.parseInt(arg1[1]);
				displayXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "displayX", displayXY[0]);
				ConfigHandler.writeIntConfig("locations", "displayY", displayXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Tracker display has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "dungeontimer":
				dungeonTimerXY[0] = Integer.parseInt(arg1[1]);
				dungeonTimerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "dungeonTimerX", dungeonTimerXY[0]);
				ConfigHandler.writeIntConfig("locations", "dungeonTimerY", dungeonTimerXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Dungeon timer has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "skill50":
				skill50XY[0] = Integer.parseInt(arg1[1]);
				skill50XY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "skill50X", skill50XY[0]);
				ConfigHandler.writeIntConfig("locations", "skill50Y", skill50XY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Skill 50 display has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "lividhp":
				lividHpXY[0] = Integer.parseInt(arg1[1]);
				lividHpXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "lividHpX", lividHpXY[0]);
				ConfigHandler.writeIntConfig("locations", "lividHpY", lividHpXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Livid HP has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "caketimer":
				cakeTimerXY[0] = Integer.parseInt(arg1[1]);
				cakeTimerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "cakeTimerX", cakeTimerXY[0]);
				ConfigHandler.writeIntConfig("locations", "cakeTimerY", cakeTimerXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Cake timer has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "skilltracker":
				skillTrackerXY[0] = Integer.parseInt(arg1[1]);
				skillTrackerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "skillTrackerX", skillTrackerXY[0]);
				ConfigHandler.writeIntConfig("locations", "skillTrackerY", skillTrackerXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Skill tracker has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "wateranswer":
				waterAnswerXY[0] = Integer.parseInt(arg1[1]);
				waterAnswerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "waterAnswerX", waterAnswerXY[0]);
				ConfigHandler.writeIntConfig("locations", "waterAnswerY", waterAnswerXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Water solver answer has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "bonzotimer":
				bonzoTimerXY[0] = Integer.parseInt(arg1[1]);
				bonzoTimerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "bonzoTimerX", bonzoTimerXY[0]);
				ConfigHandler.writeIntConfig("locations", "bonzoTimerX", bonzoTimerXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Bonzo's Mask timer has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "golemtimer":
				golemTimerXY[0] = Integer.parseInt(arg1[1]);
				golemTimerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "golemTimerX", golemTimerXY[0]);
				ConfigHandler.writeIntConfig("locations", "golemTimerY", golemTimerXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Golem timer has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "teammatesinradius":
				teammatesInRadiusXY[0] = Integer.parseInt(arg1[1]);
				teammatesInRadiusXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "teammatesInRadiusX", teammatesInRadiusXY[0]);
				ConfigHandler.writeIntConfig("locations", "teammatesInRadiusY", teammatesInRadiusXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Teammates in radius has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "gianthp":
				giantHPXY[0] = Integer.parseInt(arg1[1]);
				giantHPXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "giantHPX", giantHPXY[0]);
				ConfigHandler.writeIntConfig("locations", "giantHPY", giantHPXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Giant HP has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "abilitycooldowns":
				abilityCooldownsXY[0] = Integer.parseInt(arg1[1]);
				abilityCooldownsXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "abilityCooldownsX", abilityCooldownsXY[0]);
				ConfigHandler.writeIntConfig("locations", "abilityCooldownsY", abilityCooldownsXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Ability cooldowns has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "dungeonscore":
				dungeonScoreXY[0] = Integer.parseInt(arg1[1]);
				dungeonScoreXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "dungeonScoreX", dungeonScoreXY[0]);
				ConfigHandler.writeIntConfig("locations", "dungeonScoreY", dungeonScoreXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Dungeon score has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "firepillar":
				firePillarXY[0] = Integer.parseInt(arg1[1]);
				firePillarXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "firePillarX", firePillarXY[0]);
				ConfigHandler.writeIntConfig("locations", "firePillarY", firePillarXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Fire pillar has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "minibosstimer":
				minibossTimerXY[0] = Integer.parseInt(arg1[1]);
				minibossTimerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "minibossTimerX", minibossTimerXY[0]);
				ConfigHandler.writeIntConfig("locations", "minibossTimerY", minibossTimerXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Miniboss timer has been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			case "powdertracker":
				powderTrackerXY[0] = Integer.parseInt(arg1[1]);
				powderTrackerXY[1] = Integer.parseInt(arg1[2]);
				ConfigHandler.writeIntConfig("locations", "powderTrackerX", powderTrackerXY[0]);
				ConfigHandler.writeIntConfig("locations", "powderTrackerY", powderTrackerXY[1]);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Powder tracker been moved to " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[1] + ", " + arg1[2]));
				break;
			default:
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: " + getCommandUsage(arg0)));
		}
	}

}
