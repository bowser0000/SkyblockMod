package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class DHelpCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "dhelp";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName();
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		EntityPlayer player = (EntityPlayer) arg0;
		
		player.addChatMessage(new ChatComponentText("\n" + EnumChatFormatting.GOLD + " " + DankersSkyblockMod.MODID + " Version " + DankersSkyblockMod.VERSION + "\n" +
													EnumChatFormatting.AQUA + " <> = Mandatory parameter. [] = Optional parameter.\n" +
													EnumChatFormatting.GOLD + " Commands, " + EnumChatFormatting.GREEN + " Keybinds.\n" +
													EnumChatFormatting.GOLD + " /dhelp" + EnumChatFormatting.AQUA + " - Returns this message.\n" +
													EnumChatFormatting.GOLD + " /dsm" + EnumChatFormatting.AQUA + " - Opens the GUI for Danker's Skyblock Mod.\n" +
													EnumChatFormatting.GOLD + " /toggle <gparty/coords/golden/slayercount/rngesusalerts/splitfishing/chatmaddox/spiritbearalerts/aotd/lividdagger/flowerweapons/sceptremessages/midasstaffmessages/implosionmessages/healmessages/petcolors/dungeontimer/golemalerts/expertiselore/skill50display/outlinetext/caketimer/lowhealthnotify/lividsolver/stopsalvagestarred/notifyslayerslain/autoskilltracker/necronnotifications/bonzotimer/blockbreakingfarms/threemanpuzzle/oruopuzzle/blazepuzzle/creeperpuzzle/waterpuzzle/tictactoepuzzle/watchermessage/startswithterminal/selectallterminal/clickinorderterminal/blockwrongterminalclicks/itemframeonsealanterns/ultrasequencer/chronomatron/superpairs/hidetooltipsinaddons/pickblock/list>" + EnumChatFormatting.AQUA + " - Toggles features. /toggle list returns values of every toggle.\n" +
													EnumChatFormatting.GOLD + " /setkey <key>" + EnumChatFormatting.AQUA + " - Sets API key.\n" +
													EnumChatFormatting.GOLD + " /getkey" + EnumChatFormatting.AQUA + " - Returns key set with /setkey and copies it to your clipboard.\n" +
													EnumChatFormatting.GOLD + " /loot <zombie/spider/wolf/fishing/catacombs/mythological/> [winter/festival/spooky/f(1-7)/session]" + EnumChatFormatting.AQUA + " - Returns loot received from slayer quests or fishing stats. /loot fishing winter returns winter sea creatures instead.\n" +
													EnumChatFormatting.GOLD + " /display <zombie/spider/wolf/fishing/catacombs/mythological/auto/off> [winter/festival/spooky/f(1-7)/session]" + EnumChatFormatting.AQUA + " - Text display for trackers. /display fishing winter displays winter sea creatures instead. /display auto automatically displays the loot for the slayer quest you have active.\n" +
													EnumChatFormatting.GOLD + " /resetloot <zombie/spider/wolf/fishing/catacombs/mythological/confirm/cancel>" + EnumChatFormatting.AQUA + " - Resets loot for trackers. /resetloot confirm confirms the reset.\n" +
													EnumChatFormatting.GOLD + " /move <coords/display/dungeontimer/skill50/lividhp/caketimer/skilltracker/wateranswer/bonzotimer> <x> <y>" + EnumChatFormatting.AQUA + " - Moves text display to specified X and Y coordinates.\n" +
													EnumChatFormatting.GOLD + " /scale <coords/display/dungeontimer/skill50/lividhp/caketimer/skilltracker/wateranswer/bonzotimer> <scale (0.1 - 10)>" + EnumChatFormatting.AQUA + " - Scales text display to a specified multipler between 0.1x and 10x.\n" +
													EnumChatFormatting.GOLD + " /slayer [player]" + EnumChatFormatting.AQUA + " - Uses API to get slayer xp of a person. If no name is provided, it checks yours.\n" +
													EnumChatFormatting.GOLD + " /skills [player]" + EnumChatFormatting.AQUA + " - Uses API to get skill levels of a person. If no name is provided, it checks yours.\n" +
													EnumChatFormatting.GOLD + " /lobbyskills" + EnumChatFormatting.AQUA + " - Uses API to find the average skills of the lobby, as well the three players with the highest skill average.\n" +
													EnumChatFormatting.GOLD + " /guildof [player]" + EnumChatFormatting.AQUA + " - Uses API to get guild name and guild master of a person. If no name is provided, it checks yours.\n" +
													EnumChatFormatting.GOLD + " /petsof [player]" + EnumChatFormatting.AQUA + " - Uses API to get pets of a person. If no name is provided, it checks yours.\n" +
													EnumChatFormatting.GOLD + " /bank [player]" + EnumChatFormatting.AQUA + " - Uses API to get bank and purse coins of a person. If no name is provided, it checks yours.\n" +
													EnumChatFormatting.GOLD + " /armor [player]" + EnumChatFormatting.AQUA + " - Uses API to get armour of a person. If no name is provided, it checks yours.\n" +
													EnumChatFormatting.GOLD + " /dungeons [player] " + EnumChatFormatting.AQUA + " - Uses API to get dungeon levels of a person. If no name is provided, it checks yours.\n" +
													EnumChatFormatting.GOLD + " /importfishing" + EnumChatFormatting.AQUA + " - Imports your fishing stats from your latest profile to your fishing tracker using the API.\n" +
													EnumChatFormatting.GOLD + " /sbplayers" + EnumChatFormatting.AQUA + " - Uses API to find how many players are on each Skyblock island.\n" +
													EnumChatFormatting.GOLD + " /onlyslayer <zombie/spider/wolf> <1/2/3/4>" + EnumChatFormatting.AQUA + " - Stops you from starting a slayer quest other than the one specified.\n" +
													EnumChatFormatting.GOLD + " /skilltracker <start/stop/reset>" + EnumChatFormatting.AQUA + " - Text display for skill xp/hour.\n" +
													EnumChatFormatting.GREEN + " Open Maddox Menu" + EnumChatFormatting.AQUA + " - M by default.\n" +
													EnumChatFormatting.GREEN + " Start/Stop Skill Tracker" + EnumChatFormatting.AQUA + " - Numpad 5 by default.\n"));
	}

}
