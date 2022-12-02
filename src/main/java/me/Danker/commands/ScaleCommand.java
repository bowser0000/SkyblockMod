package me.Danker.commands;

import me.Danker.config.CfgConfig;
import me.Danker.config.ModConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class ScaleCommand extends CommandBase {

	public static double coordsScale;
	public static double displayScale;
	public static double dungeonTimerScale;
	public static double skill50Scale;
	public static double lividHpScale;
	public static double cakeTimerScale;
	public static double skillTrackerScale;
	public static double waterAnswerScale;
	public static double bonzoTimerScale;
	public static double golemTimerScale;
	public static double teammatesInRadiusScale;
	public static double giantHPScale;
	public static double abilityCooldownsScale;
	public static double dungeonScoreScale;
	public static double firePillarScale;
	public static double minibossTimerScale;
	public static double powderTrackerScale;
	
	@Override
	public String getCommandName() {
		return "scale";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <coords/display/dungeontimer/skill50/lividhp/caketimer/skilltracker/wateranswer/" +
										"bonzotimer/golemtimer/teammatesinradius/gianthp/abilitycooldown/dungeonscore/firepillar/" +
										"minibosstimer/powdertracker> <size (0.1 - 10)>";
	}

	public static String usage(ICommandSender arg0) {
		return new ScaleCommand().getCommandUsage(arg0);
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
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length < 2) {
			player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		double scaleAmount = Math.floor(Double.parseDouble(arg1[1]) * 100.0) / 100.0;
		if (scaleAmount < 0.1 || scaleAmount > 10.0) {
			player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Scale multipler can only be between 0.1x and 10x."));
			return;
		}
		
		switch (arg1[0].toLowerCase()) {
			case "coords":
				coordsScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "coordsScale", coordsScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Coords have been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + coordsScale + "x"));
				break;
			case "display":
				displayScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "displayScale", displayScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Display has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + displayScale + "x"));
				break;
			case "dungeontimer":
				dungeonTimerScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "dungeonTimerScale", dungeonTimerScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Dungeon timer has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + dungeonTimerScale + "x"));
				break;
			case "skill50":
				skill50Scale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "skill50Scale", skill50Scale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Skill 50 display has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + skill50Scale + "x"));
				break;
			case "lividhp":
				lividHpScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "lividHpScale", lividHpScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Livid HP has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + lividHpScale + "x"));
				break;
			case "caketimer":
				cakeTimerScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "cakeTimerScale", cakeTimerScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Cake timer has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + cakeTimerScale + "x"));
				break;
			case "skilltracker":
				skillTrackerScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "skillTrackerScale", skillTrackerScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Skill tracker has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + skillTrackerScale + "x"));
				break;
			case "wateranswer":
				waterAnswerScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "waterAnswerScale", waterAnswerScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Water solver answer has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + waterAnswerScale + "x"));
				break;
			case "bonzotimer":
				bonzoTimerScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "bonzoTimerScale", bonzoTimerScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Bonzo's Mask timer has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + bonzoTimerScale + "x"));
				break;
			case "golemtimer":
				golemTimerScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "golemTimerScale", golemTimerScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Golem timer has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + golemTimerScale + "x"));
				break;
			case "teammatesinradius":
				teammatesInRadiusScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "teammatesInRadiusScale", teammatesInRadiusScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Teammates in radius has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + teammatesInRadiusScale + "x"));
				break;
			case "gianthp":
				giantHPScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "giantHPScale", giantHPScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Giant hp has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + giantHPScale + "x"));
				break;
			case "abilitycooldowns":
				abilityCooldownsScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "abilityCooldownsScale", abilityCooldownsScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Ability cooldowns has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + abilityCooldownsScale + "x"));
				break;
			case "dungeonscore":
				dungeonScoreScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "dungeonScoreScale", dungeonScoreScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Dungeon score has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + dungeonScoreScale + "x"));
				break;
			case "firepillar":
				firePillarScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "firePillarScale", firePillarScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Fire pillar has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + firePillarScale + "x"));
				break;
			case "minibosstimer":
				minibossTimerScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "minibossTimerScale", minibossTimerScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Miniboss timer has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + minibossTimerScale + "x"));
				break;
			case "powdertracker":
				powderTrackerScale = scaleAmount;
				CfgConfig.writeDoubleConfig("scales", "powderTrackerScale", powderTrackerScale);
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Powder tracker has been scaled to " + ModConfig.getColour(ModConfig.secondaryColour) + powderTrackerScale + "x"));
				break;
			default:
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: " + getCommandUsage(arg0)));
		}	
	}

}
