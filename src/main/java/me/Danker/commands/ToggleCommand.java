package me.Danker.commands;

import java.util.List;

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
	public static boolean spiritBearAlerts;
	public static boolean aotdToggled;
	public static boolean sceptreMessages;
	public static boolean petColoursToggled;
	public static boolean dungeonTimerToggled;
	public static boolean golemAlertToggled;
	public static boolean expertiseLoreToggled;
	
	@Override
	public String getCommandName() {
		return "toggle";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <gparty/coords/golden/slayercount/rngesusalerts/splitfishing/chatmaddox/spiritbearalert/aotd/sceptremessages/petcolors/dungeontimer/golemalerts/expertiselore/list>";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "gparty", "coords", "golden", "slayercount", "rngesusalerts", "splitfishing", "chatmaddox", "spiritbearalerts", "aotd", "sceptremessages", "petcolors", "dungeontimer", "golemalerts", "expertiselore", "list");
		}
		return null;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		final ConfigHandler cf = new ConfigHandler();
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		if (arg1[0].equalsIgnoreCase("gparty")) {
			gpartyToggled = !gpartyToggled;
			cf.writeBooleanConfig("toggles", "GParty", gpartyToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Guild party notifications has been set to " + EnumChatFormatting.DARK_GREEN + gpartyToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("coords")) {
			coordsToggled = !coordsToggled;
			cf.writeBooleanConfig("toggles", "Coords", coordsToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Coord/Angle display has been set to " + EnumChatFormatting.DARK_GREEN + coordsToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("golden")) { 
			goldenToggled = !goldenToggled;
			cf.writeBooleanConfig("toggles", "Golden", goldenToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Golden T6 enchants has been set to " + EnumChatFormatting.DARK_GREEN + goldenToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("slayercount")) {
			slayerCountTotal = !slayerCountTotal;
			cf.writeBooleanConfig("toggles", "SlayerCount", slayerCountTotal);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Counting total 20% slayer drops has been set to " + EnumChatFormatting.DARK_GREEN + slayerCountTotal + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("rngesusalerts")) {
			rngesusAlerts = !rngesusAlerts;
			cf.writeBooleanConfig("toggles", "RNGesusAlerts", rngesusAlerts);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Slayer RNGesus alerts has been set to " + EnumChatFormatting.DARK_GREEN + rngesusAlerts + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("splitfishing")) {
			splitFishing = !splitFishing;
			cf.writeBooleanConfig("toggles", "SplitFishing", splitFishing);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Split fishing display has been set to " + EnumChatFormatting.DARK_GREEN + splitFishing + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("chatmaddox")) {
			chatMaddoxToggled = !chatMaddoxToggled;
			cf.writeBooleanConfig("toggles", "ChatMaddox", chatMaddoxToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Chat Maddox menu has been set to " + EnumChatFormatting.DARK_GREEN + chatMaddoxToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("spiritbearalerts")) { 
			spiritBearAlerts = !spiritBearAlerts;
			cf.writeBooleanConfig("toggles", "SpiritBearAlerts", spiritBearAlerts);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Spirit Bear alerts have been set to " + EnumChatFormatting.DARK_GREEN + spiritBearAlerts + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("aotd")) {
			aotdToggled = !aotdToggled;
			cf.writeBooleanConfig("toggles", "AOTD", aotdToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Block AOTD ability been set to " + EnumChatFormatting.DARK_GREEN + aotdToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("sceptremessages")) {
			sceptreMessages = !sceptreMessages;
			cf.writeBooleanConfig("toggles", "SceptreMessages", sceptreMessages);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Spirit Sceptre messages have been set to " + EnumChatFormatting.DARK_GREEN + sceptreMessages + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("petcolors") || arg1[0].equalsIgnoreCase("petcolours")) {
			petColoursToggled = !petColoursToggled;
			cf.writeBooleanConfig("toggles", "PetColors", petColoursToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Pet colours have been set to " + EnumChatFormatting.DARK_GREEN + petColoursToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("dungeontimer")) {
			dungeonTimerToggled = !dungeonTimerToggled;
			cf.writeBooleanConfig("toggles", "DungeonTimer", dungeonTimerToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Dungeon timer has been set to " + EnumChatFormatting.DARK_GREEN + dungeonTimerToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("golemalerts")) {
			golemAlertToggled = !golemAlertToggled;
			cf.writeBooleanConfig("toggles", "GolemAlerts", golemAlertToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Golem spawn alerts has been set to " + EnumChatFormatting.DARK_GREEN + golemAlertToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("expertiselore")) {
			expertiseLoreToggled = !expertiseLoreToggled;
			cf.writeBooleanConfig("toggles", "ExpertiseLore", expertiseLoreToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Expertise in lore has been set to " + EnumChatFormatting.DARK_GREEN + expertiseLoreToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("list")) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Guild party notifications: " + EnumChatFormatting.DARK_GREEN + gpartyToggled + "\n" +
														EnumChatFormatting.GREEN + " Coord/Angle display: " + EnumChatFormatting.DARK_GREEN + coordsToggled + "\n" +
														EnumChatFormatting.GREEN + " Golden T6 enchants: " + EnumChatFormatting.DARK_GREEN + goldenToggled + "\n" +
														EnumChatFormatting.GREEN + " Counting total 20% slayer drops: " + EnumChatFormatting.DARK_GREEN + slayerCountTotal + "\n" +
														EnumChatFormatting.GREEN + " Slayer RNGesus alerts: " + EnumChatFormatting.DARK_GREEN + rngesusAlerts + "\n" +
														EnumChatFormatting.GREEN + " Split fishing display: " + EnumChatFormatting.DARK_GREEN + splitFishing + "\n" +
														EnumChatFormatting.GREEN + " Chat Maddox menu: " + EnumChatFormatting.DARK_GREEN + chatMaddoxToggled + "\n" +
														EnumChatFormatting.GREEN + " Spirit Bear alerts: " + EnumChatFormatting.DARK_GREEN + spiritBearAlerts + "\n" +
														EnumChatFormatting.GREEN + " Block AOTD ability: " + EnumChatFormatting.DARK_GREEN + aotdToggled + "\n" +
														EnumChatFormatting.GREEN + " Spirit Sceptre messages: " + EnumChatFormatting.DARK_GREEN + sceptreMessages + "\n" +
														EnumChatFormatting.GREEN + " Pet colours: " + EnumChatFormatting.DARK_GREEN + petColoursToggled + "\n" +
														EnumChatFormatting.GREEN + " Dungeon timer: " + EnumChatFormatting.DARK_GREEN + dungeonTimerToggled + "\n" +
														EnumChatFormatting.GREEN + " Golem spawn alerts: " + EnumChatFormatting.DARK_GREEN + golemAlertToggled + "\n" +
														EnumChatFormatting.GREEN + " Expertise in lore: " + EnumChatFormatting.DARK_GREEN + expertiseLoreToggled));
		} else {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: " + getCommandUsage(arg0)));
		}
	}
}
