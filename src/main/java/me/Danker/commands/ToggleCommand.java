package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.CustomMusic;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class ToggleCommand extends CommandBase implements ICommand {
	// i hate this file so much
	// Some of these end with toggled and some don't, I don't want to go back and fix them all for consistency
	public static boolean gpartyToggled;
	public static boolean coordsToggled;
	public static boolean goldenToggled;
	public static boolean slayerCountTotal;
	public static boolean rngesusAlerts;
	public static boolean ghostDisplay;
	public static boolean splitFishing;
	public static boolean chatMaddoxToggled;
	public static boolean spiritBearAlerts;
	public static boolean petColoursToggled;
	public static boolean dungeonTimerToggled;
	public static boolean ghostTimerToggled;
	public static boolean golemAlertToggled;
	public static boolean expertiseLoreToggled;
	public static boolean skill50DisplayToggled;
	public static boolean outlineTextToggled;
	public static boolean cakeTimerToggled;
	public static boolean highlightSlayers;
	public static boolean highlightArachne;
	public static boolean highlightSkeletonMasters;
	public static boolean teammatesInRadius;
	public static boolean giantHP;
	public static boolean hidePetCandy;
	public static boolean customColouredNames;
	public static boolean endOfFarmAlert;
	public static boolean gemstoneLore;
	public static boolean crystalHollowWaypoints;
	public static boolean crystalAutoWaypoints;
	public static boolean autoAcceptReparty;
	public static boolean abilityCooldowns;
	public static boolean alerts;
	// Chat Messages
	public static boolean sceptreMessages;
	public static boolean midasStaffMessages;
	public static boolean implosionMessages;
	public static boolean healMessages;
	public static boolean cooldownMessages;
	public static boolean manaMessages;
	public static boolean killComboMessages;
	public static boolean lowHealthNotifyToggled;
	public static boolean lividSolverToggled;
	public static boolean stopSalvageStarredToggled;
	public static boolean watcherReadyToggled;
	public static boolean swapToPickBlockToggled;
	public static boolean flowerWeaponsToggled;
	public static boolean notifySlayerSlainToggled;
	public static boolean necronNotificationsToggled;
	public static boolean bonzoTimerToggled;
	public static boolean autoSkillTrackerToggled;
	// Puzzle Solvers
	public static boolean threeManToggled;
	public static boolean oruoToggled;
	public static boolean blazeToggled;
	public static boolean creeperToggled;
	public static boolean creeperLinesToggled;
	public static boolean waterToggled;
	public static boolean ticTacToeToggled;
	public static boolean boulderToggled;
	public static boolean silverfishToggled;
	public static boolean iceWalkToggled;
	// Terminal Helpers
	public static boolean startsWithToggled;
	public static boolean selectAllToggled;
	public static boolean clickInOrderToggled;
	public static boolean blockWrongTerminalClicksToggled;
	public static boolean itemFrameOnSeaLanternsToggled;
	// Experiments
	public static boolean ultrasequencerToggled;
	public static boolean chronomatronToggled;
	public static boolean superpairsToggled;
	public static boolean hideTooltipsInExperimentAddonsToggled;
	public static boolean melodyTooltips;
	// Custom Music
	public static boolean dungeonBossMusic;
	public static boolean bloodRoomMusic;
	public static boolean dungeonMusic;
	public static boolean hubMusic;
	public static boolean islandMusic;
	public static boolean dungeonHubMusic;
	public static boolean farmingIslandsMusic;
	public static boolean goldMineMusic;
	public static boolean deepCavernsMusic;
	public static boolean dwarvenMinesMusic;
	public static boolean crystalHollowsMusic;
	public static boolean spidersDenMusic;
	public static boolean blazingFortressMusic;
	public static boolean endMusic;
	public static boolean parkMusic;

	@Override
	public String getCommandName() {
		return "toggle";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <too many to list>";
	}

	public static String usage(ICommandSender arg0) {
		return new ToggleCommand().getCommandUsage(arg0);
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "gparty", "coords", "golden", "slayercount", "rngesusalerts", "highlightArachne",
														  "splitfishing", "chatmaddox", "spiritbearalerts", "flowerweapons",
														  "sceptremessages", "petcolors", "dungeontimer", "golemalerts",
														  "expertiselore", "skill50display", "outlinetext", "midasstaffmessages",
														  "implosionmessages", "healmessages", "cooldownmessages", "manamessages",
														  "killcombomessages", "caketimer", "lowhealthnotify", "autoskilltracker", "lividsolver",
														  "stopsalvagestarred", "notifyslayerslain", "necronnotifications",
														  "bonzotimer", "threemanpuzzle", "oruopuzzle", "blazepuzzle",
														  "creeperpuzzle", "creeperlines", "waterpuzzle", "tictactoepuzzle", "boulderpuzzle",
														  "silverfishpuzzle", "icewalkpuzzle", "watchermessage", "startswithterminal",
														  "selectallterminal", "clickinorderterminal", "blockwrongterminalclicks",
														  "ultrasequencer", "chronomatron", "superpairs", "itemframeonsealanterns",
														  "hidetooltipsinaddons", "pickblock", "melodytooltips", "highlightslayers",
														  "highlightskeletonmasters", "dungeonbossmusic", "bloodroommusic", "dungeonmusic",
														  "teammatesinradius", "gianthp", "hidepetcandy", "customcolorednames", "endoffarmalert",
														  "gemstonelore", "crystalhollowwaypoints", "crystalautowaypoints", "autoacceptreparty",
														  "itemcooldowns", "hubmusic", "islandmusic", "dungeonhubmusic", "farmingislandsmusic", "goldminemusic",
														  "deepcavernsmusic", "dwarvenminesmusic", "crystalhollowsmusic", "spidersdenmusic", "blazingfortressmusic",
														  "endmusic", "parkmusic", "alerts", "list");
		}
		return null;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		switch (arg1[0].toLowerCase()) {
			case "gparty":
				gpartyToggled = !gpartyToggled;
				ConfigHandler.writeBooleanConfig("toggles", "GParty", gpartyToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Guild party notifications has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + gpartyToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "coords":
				coordsToggled = !coordsToggled;
				ConfigHandler.writeBooleanConfig("toggles", "Coords", coordsToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Coord/Angle display has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + coordsToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "golden": 
				goldenToggled = !goldenToggled;
				ConfigHandler.writeBooleanConfig("toggles", "Golden", goldenToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Golden T6 enchants has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + goldenToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "slayercount":
				slayerCountTotal = !slayerCountTotal;
				ConfigHandler.writeBooleanConfig("toggles", "SlayerCount", slayerCountTotal);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Counting total 20% slayer drops has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + slayerCountTotal + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "rngesusalerts":
				rngesusAlerts = !rngesusAlerts;
				ConfigHandler.writeBooleanConfig("toggles", "RNGesusAlerts", rngesusAlerts);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Slayer RNGesus alerts has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + rngesusAlerts + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "ghostDisplay":
				ghostDisplay = !ghostDisplay;
				ConfigHandler.writeBooleanConfig("toggles", "GhostDisplay", ghostDisplay);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Ghost Display has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + ghostDisplay + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "splitfishing":
				splitFishing = !splitFishing;
				ConfigHandler.writeBooleanConfig("toggles", "SplitFishing", splitFishing);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Split fishing display has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + splitFishing + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "chatmaddox":
				chatMaddoxToggled = !chatMaddoxToggled;
				ConfigHandler.writeBooleanConfig("toggles", "ChatMaddox", chatMaddoxToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Click screen to open Maddox menu has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + chatMaddoxToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "spiritbearalerts":
				spiritBearAlerts = !spiritBearAlerts;
				ConfigHandler.writeBooleanConfig("toggles", "SpiritBearAlerts", spiritBearAlerts);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Spirit Bear alerts have been set to " + DankersSkyblockMod.SECONDARY_COLOUR + spiritBearAlerts + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "sceptremessages":
				sceptreMessages = !sceptreMessages;
				ConfigHandler.writeBooleanConfig("toggles", "SceptreMessages", sceptreMessages);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Spirit Sceptre messages have been set to " + DankersSkyblockMod.SECONDARY_COLOUR + sceptreMessages + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "flowerweapons":
				flowerWeaponsToggled = !flowerWeaponsToggled;
				ConfigHandler.writeBooleanConfig("toggles", "FlowerWeapons", flowerWeaponsToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Prevent Placing FoT/Spirit Sceptre been set to " + DankersSkyblockMod.SECONDARY_COLOUR + flowerWeaponsToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "midasstaffmessages":
				midasStaffMessages = !midasStaffMessages;
				ConfigHandler.writeBooleanConfig("toggles", "MidasStaffMessages", midasStaffMessages);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Midas Staff messages have been set to " + DankersSkyblockMod.SECONDARY_COLOUR + midasStaffMessages + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "implosionmessages":
				implosionMessages = !implosionMessages;
				ConfigHandler.writeBooleanConfig("toggles", "ImplosionMessages", implosionMessages);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Implosion messages have been set to " + DankersSkyblockMod.SECONDARY_COLOUR + implosionMessages + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "healmessages":
				healMessages = !healMessages;
				ConfigHandler.writeBooleanConfig("toggles", "HealMessages", healMessages);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Heal messages have been set to " + DankersSkyblockMod.SECONDARY_COLOUR + healMessages + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "cooldownmessages":
				cooldownMessages = !cooldownMessages;
				ConfigHandler.writeBooleanConfig("toggles", "CooldownMessages", cooldownMessages);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Ability cooldown messages has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + cooldownMessages + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "manamessages":
				manaMessages = !manaMessages;
				ConfigHandler.writeBooleanConfig("toggles", "ManaMessages", manaMessages);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Out of mana messages has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + manaMessages + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "killcombomessages":
				killComboMessages = !killComboMessages;
				ConfigHandler.writeBooleanConfig("toggles", "KillComboMessages", killComboMessages);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Kill combo messages has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + killComboMessages + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "petcolors":
			case "petcolours":
				petColoursToggled = !petColoursToggled;
				ConfigHandler.writeBooleanConfig("toggles", "PetColors", petColoursToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Pet colours have been set to " + DankersSkyblockMod.SECONDARY_COLOUR + petColoursToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "dungeontimer":
				dungeonTimerToggled = !dungeonTimerToggled;
				ConfigHandler.writeBooleanConfig("toggles", "DungeonTimer", dungeonTimerToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Dungeon timer has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + dungeonTimerToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			/*case "ghostTimer":
				ghostTimerToggled = !ghostTimerToggled;
				ConfigHandler.writeBooleanConfig("toggles", "GhostTimer", ghostTimerToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Ghost timer has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + ghostTimerToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break; */
			case "golemalerts":
				golemAlertToggled = !golemAlertToggled;
				ConfigHandler.writeBooleanConfig("toggles", "GolemAlerts", golemAlertToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Golem spawn alerts has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + golemAlertToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "expertiselore":
				expertiseLoreToggled = !expertiseLoreToggled;
				ConfigHandler.writeBooleanConfig("toggles", "ExpertiseLore", expertiseLoreToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Expertise in lore has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + expertiseLoreToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "skill50display":
				skill50DisplayToggled = !skill50DisplayToggled;
				ConfigHandler.writeBooleanConfig("toggles", "Skill50Display", skill50DisplayToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill 50 display has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + skill50DisplayToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "outlinetext":
				outlineTextToggled = !outlineTextToggled;
				ConfigHandler.writeBooleanConfig("toggles", "OutlineText", outlineTextToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Outline displayed text has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + outlineTextToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "caketimer":
				cakeTimerToggled = !cakeTimerToggled;
				ConfigHandler.writeBooleanConfig("toggles", "CakeTimer", cakeTimerToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Cake timer has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + cakeTimerToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "lowhealthnotify":
				lowHealthNotifyToggled = !lowHealthNotifyToggled;
				ConfigHandler.writeBooleanConfig("toggles", "LowHealthNotify", lowHealthNotifyToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Low health notify has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + lowHealthNotifyToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "lividsolver":
				lividSolverToggled = !lividSolverToggled;
				ConfigHandler.writeBooleanConfig("toggles", "LividSolver", lividSolverToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Livid solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + lividSolverToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "stopsalvagestarred":
				stopSalvageStarredToggled = !stopSalvageStarredToggled;
				ConfigHandler.writeBooleanConfig("toggles", "StopSalvageStarred", stopSalvageStarredToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Stop salvaging starred items has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + stopSalvageStarredToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "notifyslayerslain":
				notifySlayerSlainToggled = !notifySlayerSlainToggled;
				ConfigHandler.writeBooleanConfig("toggles", "NotifySlayerSlain", notifySlayerSlainToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Notify when slayer slain has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + notifySlayerSlainToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "necronnotifications":
				necronNotificationsToggled = !necronNotificationsToggled;
				ConfigHandler.writeBooleanConfig("toggles", "NecronNotifications", necronNotificationsToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Necron phase notifications has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + necronNotificationsToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "bonzotimer":
				bonzoTimerToggled = !bonzoTimerToggled;
				ConfigHandler.writeBooleanConfig("toggles", "BonzoTimer", bonzoTimerToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Bonzo's Mask timer has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + bonzoTimerToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "autoskilltracker":
				autoSkillTrackerToggled = !autoSkillTrackerToggled;
				ConfigHandler.writeBooleanConfig("toggles", "AutoSkillTracker", autoSkillTrackerToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Auto start/stop skill tracker has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + autoSkillTrackerToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "threemanpuzzle":
				threeManToggled = !threeManToggled;
				ConfigHandler.writeBooleanConfig("toggles", "ThreeManPuzzle", threeManToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Three man puzzle solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + threeManToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "oruopuzzle":
				oruoToggled = !oruoToggled;
				ConfigHandler.writeBooleanConfig("toggles", "OruoPuzzle", oruoToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Oruo trivia solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + oruoToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "blazepuzzle":
				blazeToggled = !blazeToggled;
				ConfigHandler.writeBooleanConfig("toggles", "BlazePuzzle", blazeToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Blaze puzzle solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + blazeToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "creeperpuzzle":
				creeperToggled = !creeperToggled;
				ConfigHandler.writeBooleanConfig("toggles", "CreeperPuzzle", creeperToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Creeper puzzle solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + creeperToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "creeperlines":
				creeperLinesToggled = !creeperLinesToggled;
				ConfigHandler.writeBooleanConfig("toggles", "CreeperLines", creeperLinesToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Creeper lines has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + creeperLinesToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "waterpuzzle":
				waterToggled = !waterToggled;
				ConfigHandler.writeBooleanConfig("toggles", "WaterPuzzle", waterToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Water puzzle solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + waterToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "tictactoepuzzle":
				ticTacToeToggled = !ticTacToeToggled;
				ConfigHandler.writeBooleanConfig("toggles", "TicTacToePuzzle", ticTacToeToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Tic tac toe puzzle solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + ticTacToeToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "boulderpuzzle":
				boulderToggled = !boulderToggled;
				ConfigHandler.writeBooleanConfig("toggles", "BoulderPuzzle", boulderToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Boulder puzzle solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + boulderToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "silverfishpuzzle":
				silverfishToggled = !silverfishToggled;
				ConfigHandler.writeBooleanConfig("toggles", "SilverfishPuzzle", silverfishToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Silverfish puzzle solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + silverfishToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "icewalkpuzzle":
				iceWalkToggled = !iceWalkToggled;
				ConfigHandler.writeBooleanConfig("toggles", "IceWalkPuzzle", iceWalkToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Ice walk puzzle solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + iceWalkToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "watchermessage":
				watcherReadyToggled = !watcherReadyToggled;
				ConfigHandler.writeBooleanConfig("toggles", "WatcherReadyMessage", watcherReadyToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Display Watcher ready message has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + watcherReadyToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;	
			case "startswithterminal":
				startsWithToggled = !startsWithToggled;
				ConfigHandler.writeBooleanConfig("toggles", "StartsWithTerminal", startsWithToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Starts with letter terminal solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + startsWithToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "selectallterminal":
				selectAllToggled = !selectAllToggled;
				ConfigHandler.writeBooleanConfig("toggles", "SelectAllTerminal", selectAllToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Select all color items terminal solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + selectAllToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "clickinorderterminal":
				clickInOrderToggled = !clickInOrderToggled;
				ConfigHandler.writeBooleanConfig("toggles", "ClickInOrderTerminal", clickInOrderToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Click in order terminal helper has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + selectAllToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "blockwrongterminalclicks":
				blockWrongTerminalClicksToggled = !blockWrongTerminalClicksToggled;
				ConfigHandler.writeBooleanConfig("toggles", "BlockWrongTerminalClicks", blockWrongTerminalClicksToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Block wrong clicks on terminals has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + blockWrongTerminalClicksToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "itemframeonsealanterns":
				itemFrameOnSeaLanternsToggled = !itemFrameOnSeaLanternsToggled;
				ConfigHandler.writeBooleanConfig("toggles", "IgnoreItemFrameOnSeaLanterns", itemFrameOnSeaLanternsToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Ignore item frames on sea lanterns has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + itemFrameOnSeaLanternsToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "ultrasequencer":
				ultrasequencerToggled = !ultrasequencerToggled;
				ConfigHandler.writeBooleanConfig("toggles", "UltraSequencer", ultrasequencerToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Ultrasequencer solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + ultrasequencerToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "chronomatron":
				chronomatronToggled = !chronomatronToggled;
				ConfigHandler.writeBooleanConfig("toggles", "Chronomatron", chronomatronToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Chronomatron solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + chronomatronToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "superpairs":
				superpairsToggled = !superpairsToggled;
				ConfigHandler.writeBooleanConfig("toggles", "Superpairs", superpairsToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Superpairs solver has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + superpairsToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "hidetooltipsinaddons":
				hideTooltipsInExperimentAddonsToggled = !hideTooltipsInExperimentAddonsToggled;
				ConfigHandler.writeBooleanConfig("toggles", "HideTooltipsInExperimentAddons", hideTooltipsInExperimentAddonsToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Hide tooltips in ultrasequencer and chronomatron has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + hideTooltipsInExperimentAddonsToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "pickblock":
				swapToPickBlockToggled = !swapToPickBlockToggled;
				ConfigHandler.writeBooleanConfig("toggles", "PickBlock", swapToPickBlockToggled);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Auto-swap to pick block has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + swapToPickBlockToggled + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "melodytooltips":
				melodyTooltips = !melodyTooltips;
				ConfigHandler.writeBooleanConfig("toggles", "MelodyTooltips", melodyTooltips);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Hide tooltips in Melody's Harp has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + melodyTooltips + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "highlightslayers":
				highlightSlayers = !highlightSlayers;
				ConfigHandler.writeBooleanConfig("toggles", "HighlightSlayers", highlightSlayers);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Highlight Slayer Bosses has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + highlightSlayers + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "highlightarachne":
				highlightArachne = !highlightArachne;
				ConfigHandler.writeBooleanConfig("toggles", "HighlightArachne", highlightArachne);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Highlight Arachne has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + highlightArachne + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "highlightskeletonmasters":
				highlightSkeletonMasters = !highlightSkeletonMasters;
				ConfigHandler.writeBooleanConfig("toggles", "HighlightSkeletonMasters", highlightSkeletonMasters);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Highlight Skeleton Masters has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + highlightSkeletonMasters + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "teammatesinradius":
				teammatesInRadius = !teammatesInRadius;
				ConfigHandler.writeBooleanConfig("toggles", "TeammatesInRadius", teammatesInRadius);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Teammates in radius has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + teammatesInRadius + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "gianthp":
				giantHP = !giantHP;
				ConfigHandler.writeBooleanConfig("toggles", "GiantHP", giantHP);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Giant HP display has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + giantHP + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "hidepetcandy":
				hidePetCandy = !hidePetCandy;
				ConfigHandler.writeBooleanConfig("toggles", "HidePetCandy", hidePetCandy);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Hide pet candy has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + hidePetCandy + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "customcolorednames":
			case "customcolourednames":
				customColouredNames = !customColouredNames;
				ConfigHandler.writeBooleanConfig("toggles", "CustomColouredNames", customColouredNames);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom name colors has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + customColouredNames + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "endoffarmalert":
				endOfFarmAlert = !endOfFarmAlert;
				ConfigHandler.writeBooleanConfig("toggles", "EndOfFarmAlert", endOfFarmAlert);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "End of farm alert has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + endOfFarmAlert + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "gemstonelore":
				gemstoneLore = !gemstoneLore;
				ConfigHandler.writeBooleanConfig("toggles", "GemstoneLore", gemstoneLore);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Gemstone in lore has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + gemstoneLore + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "crystalhollowwaypoints":
				crystalHollowWaypoints = !crystalHollowWaypoints;
				ConfigHandler.writeBooleanConfig("toggles", "CrystalHollowWaypoints", crystalHollowWaypoints);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Crystal Hollows waypoints has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + crystalHollowWaypoints + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "crystalautowaypoints":
				crystalAutoWaypoints = !crystalAutoWaypoints;
				ConfigHandler.writeBooleanConfig("toggles", "CrystalAutoWaypoints", crystalAutoWaypoints);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Auto Crystal Hollows waypoints has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + crystalAutoWaypoints + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "autoacceptreparty":
				autoAcceptReparty = !autoAcceptReparty;
				ConfigHandler.writeBooleanConfig("toggles", "AutoAcceptReparty", autoAcceptReparty);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Auto accept reparty has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + autoAcceptReparty + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "abilitycooldowns":
				abilityCooldowns = !abilityCooldowns;
				ConfigHandler.writeBooleanConfig("toggles", "AbilityCooldowns", abilityCooldowns);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Ability cooldowns display as been set to " + DankersSkyblockMod.SECONDARY_COLOUR + abilityCooldowns + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "dungeonbossmusic":
				dungeonBossMusic = !dungeonBossMusic;
				CustomMusic.dungeonboss.stop();
				ConfigHandler.writeBooleanConfig("toggles", "DungeonBossMusic", dungeonBossMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom dungeon boss music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + dungeonBossMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "bloodroommusic":
				bloodRoomMusic = !bloodRoomMusic;
				CustomMusic.bloodroom.stop();
				ConfigHandler.writeBooleanConfig("toggles", "BloodRoomMusic", bloodRoomMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom blood room music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + bloodRoomMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "dungeonmusic":
				dungeonMusic = !dungeonMusic;
				CustomMusic.dungeon.stop();
				ConfigHandler.writeBooleanConfig("toggles", "DungeonMusic", dungeonMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom dungeon music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + dungeonMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "hubmusic":
				hubMusic = !hubMusic;
				CustomMusic.hub.stop();
				ConfigHandler.writeBooleanConfig("toggles", "HubMusic", hubMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom hub music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + hubMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "islandmusic":
				islandMusic = !islandMusic;
				CustomMusic.island.stop();
				ConfigHandler.writeBooleanConfig("toggles", "IslandMusic", islandMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom island music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + islandMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "dungeonhubmusic":
				dungeonHubMusic = !dungeonHubMusic;
				CustomMusic.dungeonHub.stop();
				ConfigHandler.writeBooleanConfig("toggles", "DungeonHubMusic", dungeonHubMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom dungeon hub music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + dungeonHubMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "farmingislandsmusic":
				farmingIslandsMusic = !farmingIslandsMusic;
				CustomMusic.farmingIslands.stop();
				ConfigHandler.writeBooleanConfig("toggles", "FarmingIslandsMusic", farmingIslandsMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom farming islands music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + farmingIslandsMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "goldminemusic":
				goldMineMusic = !goldMineMusic;
				CustomMusic.goldMine.stop();
				ConfigHandler.writeBooleanConfig("toggles", "GoldMineMusic", goldMineMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom gold mine music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + goldMineMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "deepcavernsmusic":
				deepCavernsMusic = !deepCavernsMusic;
				CustomMusic.deepCaverns.stop();
				ConfigHandler.writeBooleanConfig("toggles", "DeepCavernsMusic", deepCavernsMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom deep caverns music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + deepCavernsMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "dwarvenminesmusic":
				dwarvenMinesMusic = !dwarvenMinesMusic;
				CustomMusic.dwarvenMines.stop();
				ConfigHandler.writeBooleanConfig("toggles", "DwarvenMinesMusic", dwarvenMinesMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom dwarven mines music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + dwarvenMinesMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "crystalhollowsmusic":
				crystalHollowsMusic = !crystalHollowsMusic;
				CustomMusic.crystalHollows.stop();
				ConfigHandler.writeBooleanConfig("toggles", "CrystalHollowsMusic", crystalHollowsMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom crystal hollows music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + crystalHollowsMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "spidersdenmusic":
				spidersDenMusic = !spidersDenMusic;
				CustomMusic.spidersDen.stop();
				ConfigHandler.writeBooleanConfig("toggles", "SpidersDenMusic", spidersDenMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom spider's den music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + spidersDenMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "blazingfortressmusic":
				blazingFortressMusic = !blazingFortressMusic;
				CustomMusic.blazingFortress.stop();
				ConfigHandler.writeBooleanConfig("toggles", "BlazingFortressMusic", blazingFortressMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom blazing fortress music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + blazingFortressMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "endmusic":
				endMusic = !endMusic;
				CustomMusic.end.stop();
				ConfigHandler.writeBooleanConfig("toggles", "EndMusic", endMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom end music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + endMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "parkmusic":
				parkMusic = !parkMusic;
				CustomMusic.park.stop();
				ConfigHandler.writeBooleanConfig("toggles", "DungeonMusic", parkMusic);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Custom park music has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + parkMusic + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "alert":
				alerts = !alerts;
				ConfigHandler.writeBooleanConfig("toggles", "Alerts", alerts);
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Alerts has been set to " + DankersSkyblockMod.SECONDARY_COLOUR + alerts + DankersSkyblockMod.MAIN_COLOUR + "."));
				break;
			case "list":
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.TYPE_COLOUR + "Guild party notifications: " + DankersSkyblockMod.VALUE_COLOUR + gpartyToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Coord/Angle display: " + DankersSkyblockMod.VALUE_COLOUR + coordsToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Golden T6 enchants: " + DankersSkyblockMod.VALUE_COLOUR + goldenToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Counting total 20% slayer drops: " + DankersSkyblockMod.VALUE_COLOUR + slayerCountTotal + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Slayer RNGesus alerts: " + DankersSkyblockMod.VALUE_COLOUR + rngesusAlerts + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Split fishing display: " + DankersSkyblockMod.VALUE_COLOUR + splitFishing + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Chat Maddox menu: " + DankersSkyblockMod.VALUE_COLOUR + chatMaddoxToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Spirit Bear alerts: " + DankersSkyblockMod.VALUE_COLOUR + spiritBearAlerts + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Prevent Placing FoT/Spirit Sceptre: " + DankersSkyblockMod.VALUE_COLOUR + flowerWeaponsToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Spirit Sceptre messages: " + DankersSkyblockMod.VALUE_COLOUR + sceptreMessages + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Midas Staff messages: " + DankersSkyblockMod.VALUE_COLOUR + midasStaffMessages + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Implosion messages: " + DankersSkyblockMod.VALUE_COLOUR + implosionMessages + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Heal messages: " + DankersSkyblockMod.VALUE_COLOUR + healMessages + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Ability cooldown messages: " + DankersSkyblockMod.VALUE_COLOUR + cooldownMessages + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Out of mana messages: " + DankersSkyblockMod.VALUE_COLOUR + manaMessages + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Pet colours: " + DankersSkyblockMod.VALUE_COLOUR + petColoursToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Dungeon timer: " + DankersSkyblockMod.VALUE_COLOUR + dungeonTimerToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Golem spawn alerts: " + DankersSkyblockMod.VALUE_COLOUR + golemAlertToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Expertise in lore: " + DankersSkyblockMod.VALUE_COLOUR + expertiseLoreToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Skill 50 display: " + DankersSkyblockMod.VALUE_COLOUR + skill50DisplayToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Outline displayed text: " + DankersSkyblockMod.VALUE_COLOUR + outlineTextToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Cake timer: " + DankersSkyblockMod.VALUE_COLOUR + cakeTimerToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Low health notify: " + DankersSkyblockMod.VALUE_COLOUR + lowHealthNotifyToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Auto start/stop skill tracker: " + DankersSkyblockMod.VALUE_COLOUR + autoSkillTrackerToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Livid solver: " + DankersSkyblockMod.VALUE_COLOUR + lividSolverToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Three man puzzle solver: " + DankersSkyblockMod.VALUE_COLOUR + threeManToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Oruo trivia solver: " + DankersSkyblockMod.VALUE_COLOUR + oruoToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Blaze puzzle solver: " + DankersSkyblockMod.VALUE_COLOUR + blazeToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Creeper puzzle solver: " + DankersSkyblockMod.VALUE_COLOUR + creeperToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Creeper lines: " + DankersSkyblockMod.VALUE_COLOUR + creeperLinesToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Water puzzle solver: " + DankersSkyblockMod.VALUE_COLOUR + waterToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Tic tac toe puzzle solver: " + DankersSkyblockMod.VALUE_COLOUR + ticTacToeToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Boulder puzzle solver: " + DankersSkyblockMod.VALUE_COLOUR + boulderToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Silverfish puzzle solver: " + DankersSkyblockMod.VALUE_COLOUR + silverfishToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Ice walk puzzle solver: " + DankersSkyblockMod.VALUE_COLOUR + iceWalkToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Watcher ready message: " + DankersSkyblockMod.VALUE_COLOUR + watcherReadyToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Starts with letter terminal solver: " + DankersSkyblockMod.VALUE_COLOUR + startsWithToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Select all color items terminal solver: " + DankersSkyblockMod.VALUE_COLOUR + selectAllToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Ignore item frames on sea lanterns: " + DankersSkyblockMod.VALUE_COLOUR + itemFrameOnSeaLanternsToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Ultra sequencer solver: " + DankersSkyblockMod.VALUE_COLOUR + ultrasequencerToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Chronomatron solver: " + DankersSkyblockMod.VALUE_COLOUR + chronomatronToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Superpairs solver: " + DankersSkyblockMod.VALUE_COLOUR + superpairsToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Hide tooltips in experiment addons: " + DankersSkyblockMod.VALUE_COLOUR + hideTooltipsInExperimentAddonsToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Auto-swap to pick block: " + DankersSkyblockMod.VALUE_COLOUR + swapToPickBlockToggled + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Hide tooltips in Melody's Harp: " + DankersSkyblockMod.VALUE_COLOUR + melodyTooltips + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Highlight Slayer Bosses: " + DankersSkyblockMod.VALUE_COLOUR + highlightSlayers + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Highlight Arachne Boss: " + DankersSkyblockMod.VALUE_COLOUR + highlightArachne + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Highlight Skeleton Masters: " + DankersSkyblockMod.VALUE_COLOUR + highlightSkeletonMasters + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Teammates in radius: " + DankersSkyblockMod.VALUE_COLOUR + teammatesInRadius + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Giant HP display: " + DankersSkyblockMod.VALUE_COLOUR + giantHP + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Hide pet candy: " + DankersSkyblockMod.VALUE_COLOUR + hidePetCandy + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom name colors: " + DankersSkyblockMod.VALUE_COLOUR + customColouredNames + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " End of farm alert: " + DankersSkyblockMod.VALUE_COLOUR + endOfFarmAlert + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Gemstone in lore: " + DankersSkyblockMod.VALUE_COLOUR + gemstoneLore + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Crystal Hollows waypoints: " + DankersSkyblockMod.VALUE_COLOUR + crystalHollowWaypoints + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Auto Crystal Hollows waypoints: " + DankersSkyblockMod.VALUE_COLOUR + crystalAutoWaypoints + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Auto accept reparty: " + DankersSkyblockMod.VALUE_COLOUR + autoAcceptReparty + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Ability cooldown display: " + DankersSkyblockMod.VALUE_COLOUR + abilityCooldowns + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom dungeon boss music: " + DankersSkyblockMod.VALUE_COLOUR + dungeonBossMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom blood room music: " + DankersSkyblockMod.VALUE_COLOUR + bloodRoomMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom dungeon music: " + DankersSkyblockMod.VALUE_COLOUR + dungeonMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom hub music: " + DankersSkyblockMod.VALUE_COLOUR + hubMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom island music: " + DankersSkyblockMod.VALUE_COLOUR + islandMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom dungeon hub music: " + DankersSkyblockMod.VALUE_COLOUR + dungeonHubMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom farming islands music: " + DankersSkyblockMod.VALUE_COLOUR + farmingIslandsMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom gold mine music: " + DankersSkyblockMod.VALUE_COLOUR + goldMineMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom deep caverns music: " + DankersSkyblockMod.VALUE_COLOUR + deepCavernsMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom dwarven mines music: " + DankersSkyblockMod.VALUE_COLOUR + dwarvenMinesMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom crystal hollows music: " + DankersSkyblockMod.VALUE_COLOUR + crystalHollowsMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom spider's den music: " + DankersSkyblockMod.VALUE_COLOUR + spidersDenMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom blazing fortress music: " + DankersSkyblockMod.VALUE_COLOUR + blazingFortressMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom end music: " + DankersSkyblockMod.VALUE_COLOUR + endMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Custom park music: " + DankersSkyblockMod.VALUE_COLOUR + parkMusic + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Alerts: " + DankersSkyblockMod.VALUE_COLOUR + alerts
				));
				break;
			default:
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
		}
	}
}
