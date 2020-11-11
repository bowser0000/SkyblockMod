package me.Danker.commands;

import java.util.List;

import me.Danker.TheMod;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ToggleCommand extends CommandBase implements ICommand {
	public static boolean gpartyToggled;
	public static boolean coordsToggled;
	public static boolean goldenToggled;
	public static boolean slayerCountTotal;
	public static boolean rngesusAlerts;
	public static boolean splitFishing;
	public static boolean chatMaddoxToggled;
	public static boolean trueChatMaddoxEnabled;
	public static boolean spiritBearAlerts;
	public static boolean aotdToggled;
	public static boolean lividDaggerToggled;
	public static boolean sceptreMessages;
	public static boolean petColoursToggled;
	public static boolean dungeonTimerToggled;
	public static boolean golemAlertToggled;
	public static boolean expertiseLoreToggled;
	public static boolean skill50DisplayToggled;
	public static boolean outlineTextToggled;
	public static boolean midasStaffMessages;
	public static boolean cakeTimerToggled;
	public static boolean lividSolverToggled;
	// Puzzle Solvers
	public static boolean threeManToggled;
	public static boolean oruoToggled;
	public static boolean blazeToggled;
	public static boolean creeperToggled;
	
	@Override
	public String getCommandName() {
		return "toggle";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <gparty/coords/golden/slayercount/rngesusalerts/splitfishing/chatmaddox/spiritbearalert/" + 
										  "aotd/lividdagger/sceptremessages/petcolors/dungeontimer/golemalerts/expertiselore/skill50display/" + 
										  "outlinetext/midasstaffmessages/caketimer/lividsolver/threemanpuzzle/oruopuzzle/blazepuzzle/creeperpuzzle/list>";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "gparty", "coords", "golden", "slayercount", "rngesusalerts",
														  "splitfishing", "chatmaddox", "spiritbearalerts", "aotd", "lividdagger",
														  "sceptremessages", "petcolors", "dungeontimer", "golemalerts",
														  "expertiselore", "skill50display", "outlinetext", "midasstaffmessages",
														  "caketimer", "lividsolver",  "threemanpuzzle", "oruopuzzle", "blazepuzzle",
														  "creeperpuzzle", "list");
		}
		return null;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		final ConfigHandler cf = new ConfigHandler();
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		if (arg1[0].equalsIgnoreCase("gparty")) {
			gpartyToggled = !gpartyToggled;
			cf.writeBooleanConfig("toggles", "GParty", gpartyToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Guild party notifications has been set to " + TheMod.SECONDARY_COLOUR + gpartyToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("coords")) {
			coordsToggled = !coordsToggled;
			cf.writeBooleanConfig("toggles", "Coords", coordsToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Coord/Angle display has been set to " + TheMod.SECONDARY_COLOUR + coordsToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("golden")) { 
			goldenToggled = !goldenToggled;
			cf.writeBooleanConfig("toggles", "Golden", goldenToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Golden T6 enchants has been set to " + TheMod.SECONDARY_COLOUR + goldenToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("slayercount")) {
			slayerCountTotal = !slayerCountTotal;
			cf.writeBooleanConfig("toggles", "SlayerCount", slayerCountTotal);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Counting total 20% slayer drops has been set to " + TheMod.SECONDARY_COLOUR + slayerCountTotal + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("rngesusalerts")) {
			rngesusAlerts = !rngesusAlerts;
			cf.writeBooleanConfig("toggles", "RNGesusAlerts", rngesusAlerts);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Slayer RNGesus alerts has been set to " + TheMod.SECONDARY_COLOUR + rngesusAlerts + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("splitfishing")) {
			splitFishing = !splitFishing;
			cf.writeBooleanConfig("toggles", "SplitFishing", splitFishing);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Split fishing display has been set to " + TheMod.SECONDARY_COLOUR + splitFishing + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("chatmaddox")) {
			chatMaddoxToggled = !chatMaddoxToggled;
			cf.writeBooleanConfig("toggles", "ChatMaddox", chatMaddoxToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Chat Maddox menu has been set to " + TheMod.SECONDARY_COLOUR + chatMaddoxToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("spiritbearalerts")) { 
			spiritBearAlerts = !spiritBearAlerts;
			cf.writeBooleanConfig("toggles", "SpiritBearAlerts", spiritBearAlerts);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Spirit Bear alerts have been set to " + TheMod.SECONDARY_COLOUR + spiritBearAlerts + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("aotd")) {
			aotdToggled = !aotdToggled;
			cf.writeBooleanConfig("toggles", "AOTD", aotdToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Block AOTD ability been set to " + TheMod.SECONDARY_COLOUR + aotdToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("lividdagger")) {
			lividDaggerToggled = !lividDaggerToggled;
			cf.writeBooleanConfig("toggles", "LividDagger", lividDaggerToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Block Livid Dagger ability been set to " + TheMod.SECONDARY_COLOUR + lividDaggerToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("sceptremessages")) {
			sceptreMessages = !sceptreMessages;
			cf.writeBooleanConfig("toggles", "SceptreMessages", sceptreMessages);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Spirit Sceptre messages have been set to " + TheMod.SECONDARY_COLOUR + sceptreMessages + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("petcolors") || arg1[0].equalsIgnoreCase("petcolours")) {
			petColoursToggled = !petColoursToggled;
			cf.writeBooleanConfig("toggles", "PetColors", petColoursToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Pet colours have been set to " + TheMod.SECONDARY_COLOUR + petColoursToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("dungeontimer")) {
			dungeonTimerToggled = !dungeonTimerToggled;
			cf.writeBooleanConfig("toggles", "DungeonTimer", dungeonTimerToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Dungeon timer has been set to " + TheMod.SECONDARY_COLOUR + dungeonTimerToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("golemalerts")) {
			golemAlertToggled = !golemAlertToggled;
			cf.writeBooleanConfig("toggles", "GolemAlerts", golemAlertToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Golem spawn alerts has been set to " + TheMod.SECONDARY_COLOUR + golemAlertToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("expertiselore")) {
			expertiseLoreToggled = !expertiseLoreToggled;
			cf.writeBooleanConfig("toggles", "ExpertiseLore", expertiseLoreToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Expertise in lore has been set to " + TheMod.SECONDARY_COLOUR + expertiseLoreToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("skill50display")) {
			skill50DisplayToggled = !skill50DisplayToggled;
			cf.writeBooleanConfig("toggles", "Skill50Display", skill50DisplayToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Skill 50 display has been set to " + TheMod.SECONDARY_COLOUR + skill50DisplayToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("outlinetext")) {
			outlineTextToggled = !outlineTextToggled;
			cf.writeBooleanConfig("toggles", "OutlineText", outlineTextToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Outline displayed text has been set to " + TheMod.SECONDARY_COLOUR + outlineTextToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("midasstaffmessages")) {
			midasStaffMessages = !midasStaffMessages;
			cf.writeBooleanConfig("toggles", "MidasStaffMessages", midasStaffMessages);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Midas Staff messages have been set to " + TheMod.SECONDARY_COLOUR + midasStaffMessages + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("caketimer")) { 
			cakeTimerToggled = !cakeTimerToggled;
			cf.writeBooleanConfig("toggles", "CakeTimer", cakeTimerToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Cake timer has been set to " + TheMod.SECONDARY_COLOUR + cakeTimerToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("lividsolver")) {
			lividSolverToggled = !lividSolverToggled;
			cf.writeBooleanConfig("toggles", "LividSolver", lividSolverToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Livid solver has been set to " + TheMod.SECONDARY_COLOUR + lividSolverToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("threemanpuzzle")) { 
			threeManToggled = !threeManToggled;
			cf.writeBooleanConfig("toggles", "ThreeManPuzzle", threeManToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Three man puzzle solver has been set to " + TheMod.SECONDARY_COLOUR + threeManToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("oruopuzzle")) { 
			oruoToggled = !oruoToggled;
			cf.writeBooleanConfig("toggles", "OruoPuzzle", oruoToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Oruo trivia solver has been set to " + TheMod.SECONDARY_COLOUR + oruoToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("blazepuzzle")) { 
			blazeToggled = !blazeToggled;
			cf.writeBooleanConfig("toggles", "BlazePuzzle", blazeToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Blaze puzzle solver has been set to " + TheMod.SECONDARY_COLOUR + blazeToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("creeperpuzzle")) {
			creeperToggled = !creeperToggled;
			cf.writeBooleanConfig("creeperpuzzle", "CreeperPuzzle", creeperToggled);
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Creeper puzzle solver has been set to " + TheMod.SECONDARY_COLOUR + creeperToggled + TheMod.MAIN_COLOUR + "."));
		} else if (arg1[0].equalsIgnoreCase("list")) {
			player.addChatMessage(new ChatComponentText(TheMod.TYPE_COLOUR + "Guild party notifications: " + TheMod.VALUE_COLOUR + gpartyToggled + "\n" +
														TheMod.TYPE_COLOUR + " Coord/Angle display: " + TheMod.VALUE_COLOUR + coordsToggled + "\n" +
														TheMod.TYPE_COLOUR + " Golden T6 enchants: " + TheMod.VALUE_COLOUR + goldenToggled + "\n" +
														TheMod.TYPE_COLOUR + " Counting total 20% slayer drops: " + TheMod.VALUE_COLOUR + slayerCountTotal + "\n" +
														TheMod.TYPE_COLOUR + " Slayer RNGesus alerts: " + TheMod.VALUE_COLOUR + rngesusAlerts + "\n" +
														TheMod.TYPE_COLOUR + " Split fishing display: " + TheMod.VALUE_COLOUR + splitFishing + "\n" +
														TheMod.TYPE_COLOUR + " Chat Maddox menu: " + TheMod.VALUE_COLOUR + chatMaddoxToggled + "\n" +
														TheMod.TYPE_COLOUR + " Spirit Bear alerts: " + TheMod.VALUE_COLOUR + spiritBearAlerts + "\n" +
														TheMod.TYPE_COLOUR + " Block AOTD ability: " + TheMod.VALUE_COLOUR + aotdToggled + "\n" +
														TheMod.TYPE_COLOUR + " Block Livid Dagger ability: " + TheMod.VALUE_COLOUR + lividDaggerToggled + "\n" +
														TheMod.TYPE_COLOUR + " Spirit Sceptre messages: " + TheMod.VALUE_COLOUR + sceptreMessages + "\n" +
														TheMod.TYPE_COLOUR + " Pet colours: " + TheMod.VALUE_COLOUR + petColoursToggled + "\n" +
														TheMod.TYPE_COLOUR + " Dungeon timer: " + TheMod.VALUE_COLOUR + dungeonTimerToggled + "\n" +
														TheMod.TYPE_COLOUR + " Golem spawn alerts: " + TheMod.VALUE_COLOUR + golemAlertToggled + "\n" +
														TheMod.TYPE_COLOUR + " Expertise in lore: " + TheMod.VALUE_COLOUR + expertiseLoreToggled + "\n" +
														TheMod.TYPE_COLOUR + " Skill 50 display: " + TheMod.VALUE_COLOUR + skill50DisplayToggled + "\n" +
														TheMod.TYPE_COLOUR + " Outline displayed text: " + TheMod.VALUE_COLOUR + outlineTextToggled + "\n" +
														TheMod.TYPE_COLOUR + " Midas Staff messages: " + TheMod.VALUE_COLOUR + midasStaffMessages + "\n" +
														TheMod.TYPE_COLOUR + " Cake timer: " + TheMod.VALUE_COLOUR + cakeTimerToggled + "\n" +
														TheMod.TYPE_COLOUR + " Livid solver: " + TheMod.VALUE_COLOUR + lividSolverToggled + "\n" +
														TheMod.TYPE_COLOUR + " Three man puzzle solver: " + TheMod.VALUE_COLOUR + threeManToggled + "\n" +
														TheMod.TYPE_COLOUR + " Oruo trivia solver: " + TheMod.VALUE_COLOUR + oruoToggled + "\n" +
														TheMod.TYPE_COLOUR + " Blaze puzzle solver: " + TheMod.VALUE_COLOUR + blazeToggled + "\n" +
														TheMod.TYPE_COLOUR + " Creeper puzzle solver: " + TheMod.VALUE_COLOUR + creeperToggled));
		} else {
			player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
		}
	}
}
