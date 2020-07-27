package me.Danker.commands;

import java.text.NumberFormat;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class LootCommand extends CommandBase {
	// Wolf
	public static int wolfSvens;
	public static int wolfTeeth;
	public static int wolfWheels;
	public static int wolfSpirits;
	public static int wolfBooks;
	public static int wolfEggs;
	public static int wolfCoutures;
	public static int wolfBaits;
	public static int wolfFluxes;
	public static double wolfTime;
	public static int wolfBosses;
	// Spider
	public static int spiderTarantulas;
	public static int spiderWebs;
	public static int spiderTAP;
	public static int spiderBites;
	public static int spiderCatalysts;
	public static int spiderBooks;
	public static int spiderSwatters;
	public static int spiderTalismans;
	public static int spiderMosquitos;
	public static double spiderTime;
	public static int spiderBosses;
	// Zombie
	public static int zombieRevs;
	public static int zombieRevFlesh;
	public static int zombieFoulFlesh;
	public static int zombiePestilences;
	public static int zombieUndeadCatas;
	public static int zombieBooks;
	public static int zombieBeheadeds;
	public static int zombieRevCatas;
	public static int zombieSnakes;
	public static int zombieScythes;
	public static double zombieTime;
	public static int zombieBosses;
	
	public String getTimeBetween(double timeOne, double timeTwo) {
		double secondsBetween = Math.floor(timeTwo - timeOne);
		
		String timeFormatted = "";
		int days;
		int hours;
		int minutes;
		int seconds;
		
		if (secondsBetween > 86400) {
			// More than 1d, display #d#h
			days = (int) (secondsBetween / 86400);
			hours = (int) (secondsBetween % 86400 / 3600);
			timeFormatted = days + "d" + hours + "h";
		} else if (secondsBetween > 3600) {
			// More than 1h, display #h#m
			hours = (int) (secondsBetween / 3600);
			minutes = (int) (secondsBetween % 3600 / 60);
			timeFormatted = hours + "h" + minutes + "m";
		} else {
			// Display #m#s
			minutes = (int) (secondsBetween / 60);
			seconds = (int) (secondsBetween % 60);
			timeFormatted = minutes + "m" + seconds + "s";
		}
		
		return timeFormatted;
	}
	
	@Override
	public String getCommandName() {
		return "loot";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName() + " <zombie/spider/wolf>";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /loot <zombie/spider/wolf>"));
			return;
		}
		
		double timeNow = System.currentTimeMillis() / 1000;
		String timeBetween;
		String bossesBetween;
		if (arg1[0].equalsIgnoreCase("wolf")) {
			if (wolfTime == -1) {
				timeBetween = "Never";
			} else {
				timeBetween = getTimeBetween(wolfTime, timeNow);
			}
			if (wolfBosses == -1) {
				bossesBetween = "Never";
			} else {
				bossesBetween = NumberFormat.getIntegerInstance().format(wolfBosses);
			}
			
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
														EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.BOLD + "  Sven Loot Summary:\n" +
														EnumChatFormatting.GOLD + "    Svens Killed: " + NumberFormat.getIntegerInstance().format(wolfSvens) + "\n" +
														EnumChatFormatting.GREEN + "    Wolf Teeth: " + NumberFormat.getIntegerInstance().format(wolfTeeth) + "\n" +
														EnumChatFormatting.BLUE + "    Hamster Wheels: " + NumberFormat.getIntegerInstance().format(wolfWheels) + "\n" +
														EnumChatFormatting.AQUA + "    Spirit Runes: " + wolfSpirits + "\n" + 
														EnumChatFormatting.WHITE + "    Critical VI Books: " + wolfBooks + "\n" +
														EnumChatFormatting.DARK_RED + "    Red Claw Eggs: " + wolfEggs + "\n" +
														EnumChatFormatting.GOLD + "    Couture Runes: " + wolfCoutures + "\n" +
														EnumChatFormatting.AQUA + "    Grizzly Baits: " + wolfBaits + "\n" +
														EnumChatFormatting.DARK_PURPLE + "    Overfluxes: " + wolfFluxes + "\n" +
														EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
														EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
														EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + " -------------------"));
		} else if (arg1[0].equalsIgnoreCase("spider")) {
			if (spiderTime == -1) {
				timeBetween = "Never";
			} else {
				timeBetween = getTimeBetween(spiderTime, timeNow);
			}
			if (spiderBosses == -1) {
				bossesBetween = "Never";
			} else {
				bossesBetween = NumberFormat.getIntegerInstance().format(spiderBosses);
			}
			
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
														EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "  Spider Loot Summary:\n" +
														EnumChatFormatting.GOLD + "    Tarantulas Killed: " + NumberFormat.getIntegerInstance().format(spiderTarantulas) + "\n" +
														EnumChatFormatting.GREEN + "    Tarantula Webs: " + NumberFormat.getIntegerInstance().format(spiderWebs) + "\n" +
														EnumChatFormatting.DARK_GREEN + "    Arrow Poison: " + NumberFormat.getIntegerInstance().format(spiderTAP) + "\n" +
														EnumChatFormatting.DARK_GRAY + "    Bite Runes: " + spiderBites + "\n" + 
														EnumChatFormatting.WHITE + "    Bane VI Books: " + spiderBooks + "\n" +
														EnumChatFormatting.AQUA + "    Spider Catalysts: " + spiderCatalysts + "\n" +
														EnumChatFormatting.DARK_PURPLE + "    Tarantula Talismans: " + spiderTalismans + "\n" +
														EnumChatFormatting.LIGHT_PURPLE + "    Fly Swatters: " + spiderSwatters + "\n" +
														EnumChatFormatting.GOLD + "    Digested Mosquitos: " + spiderMosquitos + "\n" +
														EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
														EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
														EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + " -------------------"));
		} else if (arg1[0].equalsIgnoreCase("zombie")) {
			if (zombieTime == -1) {
				timeBetween = "Never";
			} else {
				timeBetween = getTimeBetween(zombieTime, timeNow);
			}
			if (zombieBosses == -1) {
				bossesBetween = "Never";
			} else {
				bossesBetween = NumberFormat.getIntegerInstance().format(zombieBosses);
			}
			
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "-------------------\n" +
														EnumChatFormatting.DARK_GREEN + "" + EnumChatFormatting.BOLD + "  Zombie Loot Summary:\n" +
														EnumChatFormatting.GOLD + "    Revs Killed: " + NumberFormat.getIntegerInstance().format(zombieRevs) + "\n" +
														EnumChatFormatting.GREEN + "    Revenant Flesh: " + NumberFormat.getIntegerInstance().format(zombieRevFlesh) + "\n" +
														EnumChatFormatting.BLUE + "    Foul Flesh: " + NumberFormat.getIntegerInstance().format(zombieFoulFlesh) + "\n" +
														EnumChatFormatting.DARK_GREEN + "    Pestilence Runes: " + zombiePestilences + "\n" + 
														EnumChatFormatting.WHITE + "    Smite VI Books: " + zombieBooks + "\n" +
														EnumChatFormatting.AQUA + "    Undead Catalysts: " + zombieUndeadCatas + "\n" +
														EnumChatFormatting.DARK_PURPLE + "    Beheaded Horrors: " + zombieBeheadeds + "\n" +
														EnumChatFormatting.RED + "    Revenant Catalysts: " + zombieRevCatas + "\n" +
														EnumChatFormatting.DARK_GREEN + "    Snake Runes: " + zombieSnakes + "\n" +
														EnumChatFormatting.GOLD + "    Scythe Blades: " + zombieScythes + "\n" +
														EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
														EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
														EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + " -------------------"));
		} else {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /loot <zombie/spider/wolf>"));
		}

	}

}
