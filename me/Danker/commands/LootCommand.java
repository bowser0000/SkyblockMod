package me.Danker.commands;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
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
	
	// Fishing
	public static int seaCreatures;
	public static int goodCatches;
	public static int greatCatches;
	public static int squids;
	public static int seaWalkers;
	public static int nightSquids;
	public static int seaGuardians;
	public static int seaWitches;
	public static int seaArchers;
	public static int monsterOfTheDeeps;
	public static int catfishes;
	public static int carrotKings;
	public static int seaLeeches;
	public static int guardianDefenders;
	public static int deepSeaProtectors;
	public static int hydras;
	public static int seaEmperors;
	public static double empTime;
	public static int empSCs;
	// Fishing Winter
	public static int frozenSteves;
	public static int frostyTheSnowmans;
	public static int grinches;
	public static int yetis;
	
	// Single sessions (No config saves)
	// Wolf
	public static int wolfSvensSession = 0;
	public static int wolfTeethSession = 0;
	public static int wolfWheelsSession = 0;
	public static int wolfSpiritsSession = 0;
	public static int wolfBooksSession = 0;
	public static int wolfEggsSession = 0;
	public static int wolfCouturesSession = 0;
	public static int wolfBaitsSession = 0;
	public static int wolfFluxesSession = 0;
	public static double wolfTimeSession = -1;
	public static int wolfBossesSession = -1;
	// Spider
	public static int spiderTarantulasSession = 0;
	public static int spiderWebsSession = 0;
	public static int spiderTAPSession = 0;
	public static int spiderBitesSession = 0;
	public static int spiderCatalystsSession = 0;
	public static int spiderBooksSession = 0;
	public static int spiderSwattersSession = 0;
	public static int spiderTalismansSession = 0;
	public static int spiderMosquitosSession = 0;
	public static double spiderTimeSession = -1;
	public static int spiderBossesSession = -1;
	// Zombie
	public static int zombieRevsSession = 0;
	public static int zombieRevFleshSession = 0;
	public static int zombieFoulFleshSession = 0;
	public static int zombiePestilencesSession = 0;
	public static int zombieUndeadCatasSession = 0;
	public static int zombieBooksSession = 0;
	public static int zombieBeheadedsSession = 0;
	public static int zombieRevCatasSession = 0;
	public static int zombieSnakesSession = 0;
	public static int zombieScythesSession = 0;
	public static double zombieTimeSession = -1;
	public static int zombieBossesSession = -1;

	// Fishing
	public static int seaCreaturesSession = 0;
	public static int goodCatchesSession = 0;
	public static int greatCatchesSession = 0;
	public static int squidsSession = 0;
	public static int seaWalkersSession = 0;
	public static int nightSquidsSession = 0;
	public static int seaGuardiansSession = 0;
	public static int seaWitchesSession = 0;
	public static int seaArchersSession = 0;
	public static int monsterOfTheDeepsSession = 0;
	public static int catfishesSession = 0;
	public static int carrotKingsSession = 0;
	public static int seaLeechesSession = 0;
	public static int guardianDefendersSession = 0;
	public static int deepSeaProtectorsSession = 0;
	public static int hydrasSession = 0;
	public static int seaEmperorsSession = 0;
	public static double empTimeSession = -1;
	public static int empSCsSession = -1;
	// Fishing Winter
	public static int frozenStevesSession = 0;
	public static int frostyTheSnowmansSession = 0;
	public static int grinchesSession = 0;
	public static int yetisSession = 0;
	
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
		return getCommandName() + " <zombie/spider/wolf/fishing> [winter/session]";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "wolf", "spider", "zombie", "fishing");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("fishing")) {
			return getListOfStringsMatchingLastWord(args, "winter", "session");
		} else if (args.length == 2 || (args.length == 3 && args[0].equalsIgnoreCase("fishing") && args[1].equalsIgnoreCase("winter"))) { 
			return getListOfStringsMatchingLastWord(args, "session");
		}
		return null;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /loot <zombie/spider/wolf/fishing> [winter/session]"));
			return;
		}
		
		double timeNow = System.currentTimeMillis() / 1000;
		String timeBetween;
		String bossesBetween;
		NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
		boolean showSession = false;
		
		if (arg1.length > 1) {
			if (arg1[1].equalsIgnoreCase("session")) {
				showSession = true;
			} else if (arg1.length > 2) {
				if (arg1[2].equalsIgnoreCase("session")) {
					showSession = true;
				}
			}
		}
		
		if (arg1[0].equalsIgnoreCase("wolf")) {
			if (showSession) {
				if (wolfTimeSession == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = getTimeBetween(wolfTimeSession, timeNow);
				}
				if (wolfBossesSession == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(wolfBossesSession);
				}
				
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + "  Sven Loot Summary (Current Session):\n" +
															EnumChatFormatting.GOLD + "    Svens Killed: " + nf.format(wolfSvensSession) + "\n" +
															EnumChatFormatting.GREEN + "    Wolf Teeth: " + nf.format(wolfTeethSession) + "\n" +
															EnumChatFormatting.BLUE + "    Hamster Wheels: " + nf.format(wolfWheelsSession) + "\n" +
															EnumChatFormatting.AQUA + "    Spirit Runes: " + wolfSpiritsSession + "\n" + 
															EnumChatFormatting.WHITE + "    Critical VI Books: " + wolfBooksSession + "\n" +
															EnumChatFormatting.DARK_RED + "    Red Claw Eggs: " + wolfEggsSession + "\n" +
															EnumChatFormatting.GOLD + "    Couture Runes: " + wolfCouturesSession + "\n" +
															EnumChatFormatting.AQUA + "    Grizzly Baits: " + wolfBaitsSession + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Overfluxes: " + wolfFluxesSession + "\n" +
															EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
															EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
				return;
			}
			
			if (wolfTime == -1) {
				timeBetween = "Never";
			} else {
				timeBetween = getTimeBetween(wolfTime, timeNow);
			}
			if (wolfBosses == -1) {
				bossesBetween = "Never";
			} else {
				bossesBetween = nf.format(wolfBosses);
			}
			
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
														EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + "  Sven Loot Summary:\n" +
														EnumChatFormatting.GOLD + "    Svens Killed: " + nf.format(wolfSvens) + "\n" +
														EnumChatFormatting.GREEN + "    Wolf Teeth: " + nf.format(wolfTeeth) + "\n" +
														EnumChatFormatting.BLUE + "    Hamster Wheels: " + nf.format(wolfWheels) + "\n" +
														EnumChatFormatting.AQUA + "    Spirit Runes: " + wolfSpirits + "\n" + 
														EnumChatFormatting.WHITE + "    Critical VI Books: " + wolfBooks + "\n" +
														EnumChatFormatting.DARK_RED + "    Red Claw Eggs: " + wolfEggs + "\n" +
														EnumChatFormatting.GOLD + "    Couture Runes: " + wolfCoutures + "\n" +
														EnumChatFormatting.AQUA + "    Grizzly Baits: " + wolfBaits + "\n" +
														EnumChatFormatting.DARK_PURPLE + "    Overfluxes: " + wolfFluxes + "\n" +
														EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
														EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
														EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
		} else if (arg1[0].equalsIgnoreCase("spider")) {
			if (showSession) {
				if (spiderTimeSession == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = getTimeBetween(spiderTimeSession, timeNow);
				}
				if (spiderBossesSession == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(spiderBossesSession);
				}
				
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Spider Loot Summary (Current Session):\n" +
															EnumChatFormatting.GOLD + "    Tarantulas Killed: " + nf.format(spiderTarantulasSession) + "\n" +
															EnumChatFormatting.GREEN + "    Tarantula Webs: " + nf.format(spiderWebsSession) + "\n" +
															EnumChatFormatting.DARK_GREEN + "    Arrow Poison: " + nf.format(spiderTAPSession) + "\n" +
															EnumChatFormatting.DARK_GRAY + "    Bite Runes: " + spiderBitesSession + "\n" + 
															EnumChatFormatting.WHITE + "    Bane VI Books: " + spiderBooksSession + "\n" +
															EnumChatFormatting.AQUA + "    Spider Catalysts: " + spiderCatalystsSession + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Tarantula Talismans: " + spiderTalismansSession + "\n" +
															EnumChatFormatting.LIGHT_PURPLE + "    Fly Swatters: " + spiderSwattersSession + "\n" +
															EnumChatFormatting.GOLD + "    Digested Mosquitos: " + spiderMosquitosSession + "\n" +
															EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
															EnumChatFormatting.RED + EnumChatFormatting.BOLD + " -------------------"));
				return;
			}
			
			if (spiderTime == -1) {
				timeBetween = "Never";
			} else {
				timeBetween = getTimeBetween(spiderTime, timeNow);
			}
			if (spiderBosses == -1) {
				bossesBetween = "Never";
			} else {
				bossesBetween = nf.format(spiderBosses);
			}
			
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
														EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Spider Loot Summary:\n" +
														EnumChatFormatting.GOLD + "    Tarantulas Killed: " + nf.format(spiderTarantulas) + "\n" +
														EnumChatFormatting.GREEN + "    Tarantula Webs: " + nf.format(spiderWebs) + "\n" +
														EnumChatFormatting.DARK_GREEN + "    Arrow Poison: " + nf.format(spiderTAP) + "\n" +
														EnumChatFormatting.DARK_GRAY + "    Bite Runes: " + spiderBites + "\n" + 
														EnumChatFormatting.WHITE + "    Bane VI Books: " + spiderBooks + "\n" +
														EnumChatFormatting.AQUA + "    Spider Catalysts: " + spiderCatalysts + "\n" +
														EnumChatFormatting.DARK_PURPLE + "    Tarantula Talismans: " + spiderTalismans + "\n" +
														EnumChatFormatting.LIGHT_PURPLE + "    Fly Swatters: " + spiderSwatters + "\n" +
														EnumChatFormatting.GOLD + "    Digested Mosquitos: " + spiderMosquitos + "\n" +
														EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
														EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
														EnumChatFormatting.RED + EnumChatFormatting.BOLD + " -------------------"));
		} else if (arg1[0].equalsIgnoreCase("zombie")) {
			if (showSession) {
				if (zombieTimeSession == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = getTimeBetween(zombieTimeSession, timeNow);
				}
				if (zombieBossesSession == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(zombieBossesSession);
				}
				
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Zombie Loot Summary (Current Session):\n" +
															EnumChatFormatting.GOLD + "    Revs Killed: " + nf.format(zombieRevsSession) + "\n" +
															EnumChatFormatting.GREEN + "    Revenant Flesh: " + nf.format(zombieRevFleshSession) + "\n" +
															EnumChatFormatting.BLUE + "    Foul Flesh: " + nf.format(zombieFoulFleshSession) + "\n" +
															EnumChatFormatting.DARK_GREEN + "    Pestilence Runes: " + zombiePestilencesSession + "\n" + 
															EnumChatFormatting.WHITE + "    Smite VI Books: " + zombieBooksSession + "\n" +
															EnumChatFormatting.AQUA + "    Undead Catalysts: " + zombieUndeadCatasSession + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Beheaded Horrors: " + zombieBeheadedsSession + "\n" +
															EnumChatFormatting.RED + "    Revenant Catalysts: " + zombieRevCatasSession + "\n" +
															EnumChatFormatting.DARK_GREEN + "    Snake Runes: " + zombieSnakesSession + "\n" +
															EnumChatFormatting.GOLD + "    Scythe Blades: " + zombieScythesSession + "\n" +
															EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
															EnumChatFormatting.GREEN + EnumChatFormatting.BOLD + " -------------------"));
				return;
			}
			
			if (zombieTime == -1) {
				timeBetween = "Never";
			} else {
				timeBetween = getTimeBetween(zombieTime, timeNow);
			}
			if (zombieBosses == -1) {
				bossesBetween = "Never";
			} else {
				bossesBetween = nf.format(zombieBosses);
			}
			
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "-------------------\n" +
														EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Zombie Loot Summary:\n" +
														EnumChatFormatting.GOLD + "    Revs Killed: " + nf.format(zombieRevs) + "\n" +
														EnumChatFormatting.GREEN + "    Revenant Flesh: " + nf.format(zombieRevFlesh) + "\n" +
														EnumChatFormatting.BLUE + "    Foul Flesh: " + nf.format(zombieFoulFlesh) + "\n" +
														EnumChatFormatting.DARK_GREEN + "    Pestilence Runes: " + zombiePestilences + "\n" + 
														EnumChatFormatting.WHITE + "    Smite VI Books: " + zombieBooks + "\n" +
														EnumChatFormatting.AQUA + "    Undead Catalysts: " + zombieUndeadCatas + "\n" +
														EnumChatFormatting.DARK_PURPLE + "    Beheaded Horrors: " + zombieBeheadeds + "\n" +
														EnumChatFormatting.RED + "    Revenant Catalysts: " + zombieRevCatas + "\n" +
														EnumChatFormatting.DARK_GREEN + "    Snake Runes: " + zombieSnakes + "\n" +
														EnumChatFormatting.GOLD + "    Scythe Blades: " + zombieScythes + "\n" +
														EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
														EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
														EnumChatFormatting.GREEN + EnumChatFormatting.BOLD + " -------------------"));
		} else if (arg1[0].equalsIgnoreCase("fishing")) {
			if (arg1.length > 1) {
				if (arg1[1].equalsIgnoreCase("winter")) {
					if (showSession) {
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
								EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "  Winter Fishing Summary (Current Session):\n" +
								EnumChatFormatting.AQUA + "    Frozen Steves: " + nf.format(frozenStevesSession) + "\n" +
								EnumChatFormatting.WHITE + "    Snowmans: " + nf.format(frostyTheSnowmansSession) + "\n" +
								EnumChatFormatting.DARK_GREEN + "    Grinches: " + nf.format(grinchesSession) + "\n" +
								EnumChatFormatting.GOLD + "    Yetis: " + nf.format(yetisSession) + "\n" +
								EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
						return;
					}
					
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "  Winter Fishing Summary:\n" +
																EnumChatFormatting.AQUA + "    Frozen Steves: " + nf.format(frozenSteves) + "\n" +
																EnumChatFormatting.WHITE + "    Snowmans: " + nf.format(frostyTheSnowmans) + "\n" +
																EnumChatFormatting.DARK_GREEN + "    Grinches: " + nf.format(grinches) + "\n" +
																EnumChatFormatting.GOLD + "    Yetis: " + nf.format(yetis) + "\n" +
																EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
					return;
				}
			}
			
			if (showSession) {
				if (empTimeSession == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = getTimeBetween(empTimeSession, timeNow);
				}
				if (empSCsSession == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(empSCsSession);
				}
				
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + "  Fishing Summary (Current Session):\n" +
															EnumChatFormatting.AQUA + "    Sea Creatures Caught: " + nf.format(seaCreaturesSession) + "\n" +
															EnumChatFormatting.GOLD + "    Good Catches: " + nf.format(goodCatchesSession) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Great Catches: " + nf.format(greatCatchesSession) + "\n\n" +
															EnumChatFormatting.GRAY + "    Squids: " + nf.format(squidsSession) + "\n" +
															EnumChatFormatting.GREEN + "    Sea Walkers: " + nf.format(seaWalkersSession) + "\n" +
															EnumChatFormatting.DARK_GRAY + "    Night Squids: " + nf.format(nightSquidsSession) + "\n" +
															EnumChatFormatting.DARK_AQUA + "    Sea Guardians: " + nf.format(seaGuardiansSession) + "\n" +
															EnumChatFormatting.BLUE + "    Sea Witches: " + nf.format(seaWitchesSession) + "\n" +
															EnumChatFormatting.GREEN + "    Sea Archers: " + nf.format(seaArchersSession) + "\n" +
															EnumChatFormatting.GREEN + "    Monster of the Deeps: " + nf.format(monsterOfTheDeepsSession) + "\n" +
															EnumChatFormatting.YELLOW + "    Catfishes: " + nf.format(catfishesSession) + "\n" +
															EnumChatFormatting.GOLD + "    Carrot Kings: " + nf.format(carrotKingsSession) + "\n" +
															EnumChatFormatting.GRAY + "    Sea Leeches: " + nf.format(seaLeechesSession) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Guardian Defenders: " + nf.format(guardianDefendersSession) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Deep Sea Protectors: " + nf.format(deepSeaProtectorsSession) + "\n" +
															EnumChatFormatting.GOLD + "    Hydras: " + nf.format(hydrasSession) + "\n" +
															EnumChatFormatting.GOLD + "    Sea Emperors: " + nf.format(seaEmperorsSession) + "\n" +
															EnumChatFormatting.AQUA + "    Time Since Sea Emperor: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Sea Creatures Since Sea Emperor: " + bossesBetween + "\n" +
															EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + " -------------------"));
				return;
			}
			
			if (empTime == -1) {
				timeBetween = "Never";
			} else {
				timeBetween = getTimeBetween(empTime, timeNow);
			}
			if (empSCs == -1) {
				bossesBetween = "Never";
			} else {
				bossesBetween = nf.format(empSCs);
			}
			
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
														EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + "  Fishing Summary:\n" +
														EnumChatFormatting.AQUA + "    Sea Creatures Caught: " + nf.format(seaCreatures) + "\n" +
														EnumChatFormatting.GOLD + "    Good Catches: " + nf.format(goodCatches) + "\n" +
														EnumChatFormatting.DARK_PURPLE + "    Great Catches: " + nf.format(greatCatches) + "\n\n" +
														EnumChatFormatting.GRAY + "    Squids: " + nf.format(squids) + "\n" +
														EnumChatFormatting.GREEN + "    Sea Walkers: " + nf.format(seaWalkers) + "\n" +
														EnumChatFormatting.DARK_GRAY + "    Night Squids: " + nf.format(nightSquids) + "\n" +
														EnumChatFormatting.DARK_AQUA + "    Sea Guardians: " + nf.format(seaGuardians) + "\n" +
														EnumChatFormatting.BLUE + "    Sea Witches: " + nf.format(seaWitches) + "\n" +
														EnumChatFormatting.GREEN + "    Sea Archers: " + nf.format(seaArchers) + "\n" +
														EnumChatFormatting.GREEN + "    Monster of the Deeps: " + nf.format(monsterOfTheDeeps) + "\n" +
														EnumChatFormatting.YELLOW + "    Catfishes: " + nf.format(catfishes) + "\n" +
														EnumChatFormatting.GOLD + "    Carrot Kings: " + nf.format(carrotKings) + "\n" +
														EnumChatFormatting.GRAY + "    Sea Leeches: " + nf.format(seaLeeches) + "\n" +
														EnumChatFormatting.DARK_PURPLE + "    Guardian Defenders: " + nf.format(guardianDefenders) + "\n" +
														EnumChatFormatting.DARK_PURPLE + "    Deep Sea Protectors: " + nf.format(deepSeaProtectors) + "\n" +
														EnumChatFormatting.GOLD + "    Hydras: " + nf.format(hydras) + "\n" +
														EnumChatFormatting.GOLD + "    Sea Emperors: " + nf.format(seaEmperors) + "\n" +
														EnumChatFormatting.AQUA + "    Time Since Sea Emperor: " + timeBetween + "\n" +
														EnumChatFormatting.AQUA + "    Sea Creatures Since Sea Emperor: " + bossesBetween + "\n" +
														EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + " -------------------"));
		} else {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /loot <zombie/spider/wolf/fishing> [winter/session]"));
		}

	}

}
