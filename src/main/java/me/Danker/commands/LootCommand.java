package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class LootCommand extends CommandBase {
	// Wolf
	public static int wolfSvens;
	public static int wolfTeeth;
	public static int wolfWheels;
	public static int wolfWheelsDrops;
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
	public static int spiderTAPDrops;
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
	public static int zombieFoulFleshDrops;
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
	public static int fishingMilestone;
	// Fishing Winter
	public static int frozenSteves;
	public static int frostyTheSnowmans;
	public static int grinches;
	public static int yetis;
	public static double yetiTime;
	public static int yetiSCs;
	// Fishing Festival
	public static int nurseSharks;
	public static int blueSharks;
	public static int tigerSharks;
	public static int greatWhiteSharks;
	// Spooky Fishing
	public static int scarecrows;
	public static int nightmares;
	public static int werewolfs;
	public static int phantomFishers;
	public static int grimReapers;
	
	// Mythological
	public static double mythCoins;
	public static int griffinFeathers;
	public static int crownOfGreeds;
	public static int washedUpSouvenirs;
	public static int minosHunters;
	public static int siameseLynxes;
	public static int minotaurs;
	public static int gaiaConstructs;
	public static int minosChampions;
	public static int minosInquisitors;
	
	// Catacombs Dungeons
	public static int recombobulators;
	public static int fumingPotatoBooks;
	// F1
	public static int bonzoStaffs;
	public static double f1CoinsSpent;
	public static double f1TimeSpent;
	// F2
	public static int scarfStudies;
	public static int adaptiveSwords;
	public static double f2CoinsSpent;
	public static double f2TimeSpent;
	// F3
	public static int adaptiveHelms;
	public static int adaptiveChests;
	public static int adaptiveLegs;
	public static int adaptiveBoots;
	public static double f3CoinsSpent;
	public static double f3TimeSpent;
	// F4
	public static int spiritWings;
	public static int spiritBones;
	public static int spiritBoots;
	public static int spiritSwords;
	public static int spiritBows;
	public static int epicSpiritPets;
	public static int legSpiritPets;
	public static double f4CoinsSpent;
	public static double f4TimeSpent;
	// F5
	public static int warpedStones;
	public static int shadowAssHelms;
	public static int shadowAssChests;
	public static int shadowAssLegs;
	public static int shadowAssBoots;
	public static int lastBreaths;
	public static int lividDaggers;
	public static int shadowFurys;
	public static double f5CoinsSpent;
	public static double f5TimeSpent;
	// F6
	public static int ancientRoses;
	public static int precursorEyes;
	public static int giantsSwords;
	public static int necroLordHelms;
	public static int necroLordChests;
	public static int necroLordLegs;
	public static int necroLordBoots;
	public static int necroSwords;
	public static double f6CoinsSpent;
	public static double f6TimeSpent;
	// F7
	public static int witherBloods;
	public static int witherCloaks;
	public static int implosions;
	public static int witherShields;
	public static int shadowWarps;
	public static int necronsHandles;
	public static int autoRecombs;
	public static int witherHelms;
	public static int witherChests;
	public static int witherLegs;
	public static int witherBoots;
	public static double f7CoinsSpent;
	public static double f7TimeSpent;
	
	// Single sessions (No config saves)
	// Wolf
	public static int wolfSvensSession = 0;
	public static int wolfTeethSession = 0;
	public static int wolfWheelsSession = 0;
	public static int wolfWheelsDropsSession = 0;
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
	public static int spiderTAPDropsSession = 0;
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
	public static int zombieFoulFleshDropsSession = 0;
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
	public static int fishingMilestoneSession = 0;
	// Fishing Winter
	public static int frozenStevesSession = 0;
	public static int frostyTheSnowmansSession = 0;
	public static int grinchesSession = 0;
	public static int yetisSession = 0;
	public static double yetiTimeSession = -1;
	public static int yetiSCsSession = -1;
	// Fishing Festival
	public static int nurseSharksSession = 0;
	public static int blueSharksSession = 0;
	public static int tigerSharksSession = 0;
	public static int greatWhiteSharksSession = 0;
	// Spooky Fishing
	public static int scarecrowsSession = 0;
	public static int nightmaresSession = 0;
	public static int werewolfsSession = 0;
	public static int phantomFishersSession = 0;
	public static int grimReapersSession = 0;
	
	// Mythological
	public static double mythCoinsSession = 0;
	public static int griffinFeathersSession = 0;
	public static int crownOfGreedsSession = 0;
	public static int washedUpSouvenirsSession = 0;
	public static int minosHuntersSession = 0;
	public static int siameseLynxesSession = 0;
	public static int minotaursSession = 0;
	public static int gaiaConstructsSession = 0;
	public static int minosChampionsSession = 0;
	public static int minosInquisitorsSession = 0;
	
	// Catacombs Dungeons
	public static int recombobulatorsSession = 0;
	public static int fumingPotatoBooksSession = 0;
	// F1
	public static int bonzoStaffsSession = 0;
	public static double f1CoinsSpentSession = 0;
	public static double f1TimeSpentSession = 0;
	// F2
	public static int scarfStudiesSession = 0;
	public static int adaptiveSwordsSession = 0;
	public static double f2CoinsSpentSession = 0;
	public static double f2TimeSpentSession = 0;
	// F3
	public static int adaptiveHelmsSession = 0;
	public static int adaptiveChestsSession = 0;
	public static int adaptiveLegsSession = 0;
	public static int adaptiveBootsSession = 0;
	public static double f3CoinsSpentSession = 0;
	public static double f3TimeSpentSession = 0;
	// F4
	public static int spiritWingsSession = 0;
	public static int spiritBonesSession = 0;
	public static int spiritBootsSession = 0;
	public static int spiritSwordsSession = 0;
	public static int spiritBowsSession = 0;
	public static int epicSpiritPetsSession = 0;
	public static int legSpiritPetsSession = 0;
	public static double f4CoinsSpentSession = 0;
	public static double f4TimeSpentSession = 0;
	// F5
	public static int warpedStonesSession = 0;
	public static int shadowAssHelmsSession = 0;
	public static int shadowAssChestsSession = 0;
	public static int shadowAssLegsSession = 0;
	public static int shadowAssBootsSession = 0;
	public static int lastBreathsSession = 0;
	public static int lividDaggersSession = 0;
	public static int shadowFurysSession = 0;
	public static double f5CoinsSpentSession = 0;
	public static double f5TimeSpentSession = 0;
	// F6
	public static int ancientRosesSession = 0;
	public static int precursorEyesSession = 0;
	public static int giantsSwordsSession = 0;
	public static int necroLordHelmsSession = 0;
	public static int necroLordChestsSession = 0;
	public static int necroLordLegsSession = 0;
	public static int necroLordBootsSession = 0;
	public static int necroSwordsSession = 0;
	public static double f6CoinsSpentSession = 0;
	public static double f6TimeSpentSession = 0;
	// F7
	public static int witherBloodsSession = 0;
	public static int witherCloaksSession = 0;
	public static int implosionsSession = 0;
	public static int witherShieldsSession = 0;
	public static int shadowWarpsSession = 0;
	public static int necronsHandlesSession = 0;
	public static int autoRecombsSession = 0;
	public static int witherHelmsSession = 0;
	public static int witherChestsSession = 0;
	public static int witherLegsSession = 0;
	public static int witherBootsSession = 0;
	public static double f7CoinsSpentSession = 0;
	public static double f7TimeSpentSession = 0;
	
	@Override
	public String getCommandName() {
		return "loot";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <zombie/spider/wolf/fishing/catacombs/mythological> [winter/festival/spooky/f(1-7)/session]";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "wolf", "spider", "zombie", "fishing", "catacombs", "mythological");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("fishing")) {
			return getListOfStringsMatchingLastWord(args, "winter", "festival", "spooky", "session");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("catacombs")) {
			return getListOfStringsMatchingLastWord(args, "f1", "floor1", "f2", "floor2", "f3", "floor3", "f4", "floor4", "f5", "floor5", "f6", "floor6", "f7", "floor7");
		} else if (args.length > 1) {
			return getListOfStringsMatchingLastWord(args, "session");
		}
		return null;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		double timeNow = System.currentTimeMillis() / 1000;
		String timeBetween;
		String bossesBetween;
		String drop20;
		NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
		boolean showSession = false;
		
		if (arg1[arg1.length - 1].equalsIgnoreCase("session")) showSession = true;

		switch (arg1[0].toLowerCase()) {
			case "wolf":
				if (showSession) {
					if (wolfTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(wolfTimeSession, timeNow);
					}
					if (wolfBossesSession == -1) {
						bossesBetween = "Never";
					} else {
						bossesBetween = nf.format(wolfBossesSession);
					}
					if (ToggleCommand.slayerCountTotal) {
						drop20 = nf.format(wolfWheelsSession);
					} else {
						drop20 = nf.format(wolfWheelsDropsSession) + " times";
					}
					
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + "  Sven Loot Summary (Current Session):\n" +
																EnumChatFormatting.GOLD + "    Svens Killed: " + nf.format(wolfSvensSession) + "\n" +
																EnumChatFormatting.GREEN + "    Wolf Teeth: " + nf.format(wolfTeethSession) + "\n" +
																EnumChatFormatting.BLUE + "    Hamster Wheels: " + drop20 + "\n" +
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
					timeBetween = Utils.getTimeBetween(wolfTime, timeNow);
				}
				if (wolfBosses == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(wolfBosses);
				}
				if (ToggleCommand.slayerCountTotal) {
					drop20 = nf.format(wolfWheels);
				} else {
					drop20 = nf.format(wolfWheelsDrops) + " times";
				}
				
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + "  Sven Loot Summary:\n" +
															EnumChatFormatting.GOLD + "    Svens Killed: " + nf.format(wolfSvens) + "\n" +
															EnumChatFormatting.GREEN + "    Wolf Teeth: " + nf.format(wolfTeeth) + "\n" +
															EnumChatFormatting.BLUE + "    Hamster Wheels: " + drop20 + "\n" +
															EnumChatFormatting.AQUA + "    Spirit Runes: " + wolfSpirits + "\n" + 
															EnumChatFormatting.WHITE + "    Critical VI Books: " + wolfBooks + "\n" +
															EnumChatFormatting.DARK_RED + "    Red Claw Eggs: " + wolfEggs + "\n" +
															EnumChatFormatting.GOLD + "    Couture Runes: " + wolfCoutures + "\n" +
															EnumChatFormatting.AQUA + "    Grizzly Baits: " + wolfBaits + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Overfluxes: " + wolfFluxes + "\n" +
															EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
															EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
				break;
			case "spider":
				if (showSession) {
					if (spiderTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(spiderTimeSession, timeNow);
					}
					if (spiderBossesSession == -1) {
						bossesBetween = "Never";
					} else {
						bossesBetween = nf.format(spiderBossesSession);
					}
					if (ToggleCommand.slayerCountTotal) {
						drop20 = nf.format(spiderTAPSession);
					} else {
						drop20 = nf.format(spiderTAPDropsSession) + " times";
					}
					
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Spider Loot Summary (Current Session):\n" +
																EnumChatFormatting.GOLD + "    Tarantulas Killed: " + nf.format(spiderTarantulasSession) + "\n" +
																EnumChatFormatting.GREEN + "    Tarantula Webs: " + nf.format(spiderWebsSession) + "\n" +
																EnumChatFormatting.DARK_GREEN + "    Arrow Poison: " + drop20 + "\n" +
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
					timeBetween = Utils.getTimeBetween(spiderTime, timeNow);
				}
				if (spiderBosses == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(spiderBosses);
				}
				if (ToggleCommand.slayerCountTotal) {
					drop20 = nf.format(spiderTAP);
				} else {
					drop20 = nf.format(spiderTAPDrops) + " times";
				}
				
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Spider Loot Summary:\n" +
															EnumChatFormatting.GOLD + "    Tarantulas Killed: " + nf.format(spiderTarantulas) + "\n" +
															EnumChatFormatting.GREEN + "    Tarantula Webs: " + nf.format(spiderWebs) + "\n" +
															EnumChatFormatting.DARK_GREEN + "    Arrow Poison: " + drop20 + "\n" +
															EnumChatFormatting.DARK_GRAY + "    Bite Runes: " + spiderBites + "\n" + 
															EnumChatFormatting.WHITE + "    Bane VI Books: " + spiderBooks + "\n" +
															EnumChatFormatting.AQUA + "    Spider Catalysts: " + spiderCatalysts + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Tarantula Talismans: " + spiderTalismans + "\n" +
															EnumChatFormatting.LIGHT_PURPLE + "    Fly Swatters: " + spiderSwatters + "\n" +
															EnumChatFormatting.GOLD + "    Digested Mosquitos: " + spiderMosquitos + "\n" +
															EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
															EnumChatFormatting.RED + EnumChatFormatting.BOLD + " -------------------"));
				break;
			case "zombie":
				if (showSession) {
					if (zombieTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(zombieTimeSession, timeNow);
					}
					if (zombieBossesSession == -1) {
						bossesBetween = "Never";
					} else {
						bossesBetween = nf.format(zombieBossesSession);
					}
					if (ToggleCommand.slayerCountTotal) {
						drop20 = nf.format(zombieFoulFleshSession);
					} else {
						drop20 = nf.format(zombieFoulFleshDropsSession) + " times";
					}
					
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Zombie Loot Summary (Current Session):\n" +
																EnumChatFormatting.GOLD + "    Revs Killed: " + nf.format(zombieRevsSession) + "\n" +
																EnumChatFormatting.GREEN + "    Revenant Flesh: " + nf.format(zombieRevFleshSession) + "\n" +
																EnumChatFormatting.BLUE + "    Foul Flesh: " + drop20 + "\n" +
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
					timeBetween = Utils.getTimeBetween(zombieTime, timeNow);
				}
				if (zombieBosses == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(zombieBosses);
				}
				if (ToggleCommand.slayerCountTotal) {
					drop20 = nf.format(zombieFoulFlesh);
				} else {
					drop20 = nf.format(zombieFoulFleshDrops) + " times";
				}
				
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Zombie Loot Summary:\n" +
															EnumChatFormatting.GOLD + "    Revs Killed: " + nf.format(zombieRevs) + "\n" +
															EnumChatFormatting.GREEN + "    Revenant Flesh: " + nf.format(zombieRevFlesh) + "\n" +
															EnumChatFormatting.BLUE + "    Foul Flesh: " + drop20 + "\n" +
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
				break;
			case "fishing":
				if (arg1.length > 1) {
					if (arg1[1].equalsIgnoreCase("winter")) {
						if (showSession) {
							if (yetiTimeSession == -1) {
								timeBetween = "Never";
							} else {
								timeBetween = Utils.getTimeBetween(yetiTimeSession, timeNow);
							}
							if (yetiSCsSession == -1) {
								bossesBetween = "Never";
							} else {
								bossesBetween = nf.format(yetiSCsSession);
							}
							
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "  Winter Fishing Summary (Current Session):\n" +
																		EnumChatFormatting.AQUA + "    Frozen Steves: " + nf.format(frozenStevesSession) + "\n" +
																		EnumChatFormatting.WHITE + "    Snowmans: " + nf.format(frostyTheSnowmansSession) + "\n" +
																		EnumChatFormatting.DARK_GREEN + "    Grinches: " + nf.format(grinchesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Yetis: " + nf.format(yetisSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Since Yeti: " + timeBetween + "\n" +
																		EnumChatFormatting.AQUA + "    Creatures Since Yeti: " + bossesBetween + "\n" +
																		EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						
						if (yetiTime == -1) {
							timeBetween = "Never";
						} else {
							timeBetween = Utils.getTimeBetween(yetiTime, timeNow);
						}
						if (yetiSCs == -1) {
							bossesBetween = "Never";
						} else {
							bossesBetween = nf.format(yetiSCs);
						}
						
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "  Winter Fishing Summary:\n" +
																	EnumChatFormatting.AQUA + "    Frozen Steves: " + nf.format(frozenSteves) + "\n" +
																	EnumChatFormatting.WHITE + "    Snowmans: " + nf.format(frostyTheSnowmans) + "\n" +
																	EnumChatFormatting.DARK_GREEN + "    Grinches: " + nf.format(grinches) + "\n" +
																	EnumChatFormatting.GOLD + "    Yetis: " + nf.format(yetis) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Since Yeti: " + timeBetween + "\n" +
																	EnumChatFormatting.AQUA + "    Creatures Since Yeti: " + bossesBetween + "\n" +
																	EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
						return;
					} else if (arg1[1].equalsIgnoreCase("festival")) {
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.DARK_BLUE + EnumChatFormatting.BOLD + " Fishing Festival Summary (Current Session):\n" +
																		EnumChatFormatting.LIGHT_PURPLE + "    Nurse Sharks: " + nf.format(nurseSharksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Blue Sharks: " + nf.format(blueSharksSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Tiger Sharks: " + nf.format(tigerSharksSession) + "\n" +
																		EnumChatFormatting.WHITE + "    Great White Sharks: " + nf.format(greatWhiteSharksSession) + "\n" +
																		EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.DARK_BLUE + EnumChatFormatting.BOLD + " Fishing Festival Summary:\n" +
																	EnumChatFormatting.LIGHT_PURPLE + "    Nurse Sharks: " + nf.format(nurseSharks) + "\n" +
																	EnumChatFormatting.BLUE + "    Blue Sharks: " + nf.format(blueSharks) + "\n" +
																	EnumChatFormatting.GOLD + "    Tiger Sharks: " + nf.format(tigerSharks) + "\n" +
																	EnumChatFormatting.WHITE + "    Great White Sharks: " + nf.format(greatWhiteSharks) + "\n" +
																	EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
					} else if (arg1[1].equalsIgnoreCase("spooky")) {
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + " Spooky Fishing Summary (Current Session):\n" +
																		EnumChatFormatting.BLUE + "    Scarecrows: " + nf.format(scarecrowsSession) + "\n" +
																		EnumChatFormatting.GRAY + "    Nightmares: " + nf.format(nightmaresSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Werewolves: " + nf.format(werewolfsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Phantom Fishers: " + nf.format(phantomFishersSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Grim Reapers: " + nf.format(grimReapersSession) + "\n" +
																		EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------"));
							return;
						}
						
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + " Spooky Fishing Summary:\n" +
																	EnumChatFormatting.BLUE + "    Scarecrows: " + nf.format(scarecrows) + "\n" +
																	EnumChatFormatting.GRAY + "    Nightmares: " + nf.format(nightmares) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Werewolves: " + nf.format(werewolfs) + "\n" +
																	EnumChatFormatting.GOLD + "    Phantom Fishers: " + nf.format(phantomFishers) + "\n" +
																	EnumChatFormatting.GOLD + "    Grim Reapers: " + nf.format(grimReapers) + "\n" +
																	EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------"));
					}
				}
				
				if (showSession) {
					if (empTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(empTimeSession, timeNow);
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
					timeBetween = Utils.getTimeBetween(empTime, timeNow);
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
	
				break;
			case "mythological":
				if (showSession) {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + "  Mythological Event Summary (Current Session):\n" +
																EnumChatFormatting.YELLOW + "    Coins: " + Utils.getMoneySpent(mythCoinsSession) + "\n" +
																EnumChatFormatting.WHITE + "    Griffin Feathers: " + nf.format(griffinFeathersSession) + "\n" +
																EnumChatFormatting.GOLD + "    Crown of Greeds: " + nf.format(crownOfGreedsSession) + "\n" +
																EnumChatFormatting.AQUA + "    Washed-up Souvenirs: " + nf.format(washedUpSouvenirsSession) + "\n" +
																EnumChatFormatting.RED + "    Minos Hunters: " + nf.format(minosHuntersSession) + "\n" +
																EnumChatFormatting.GRAY + "   Siamese Lynxes: " + nf.format(siameseLynxesSession) + "\n" +
																EnumChatFormatting.RED + "   Minotaurs: " + nf.format(minotaursSession) + "\n" +
																EnumChatFormatting.WHITE + "   Gaia Constructs: " + nf.format(gaiaConstructsSession) + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Minos Champions: " + nf.format(minosChampionsSession) + "\n" +
																EnumChatFormatting.GOLD + "    Minos Inquisitors: " + nf.format(minosInquisitorsSession) + "\n" +
																EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + "-------------------"));
					return;
				}
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + "  Mythological Event Summary:\n" +
															EnumChatFormatting.YELLOW + "    Coins: " + Utils.getMoneySpent(mythCoins) + "\n" +
															EnumChatFormatting.WHITE + "    Griffin Feathers: " + nf.format(griffinFeathers) + "\n" +
															EnumChatFormatting.GOLD + "    Crown of Greeds: " + nf.format(crownOfGreeds) + "\n" +
															EnumChatFormatting.AQUA + "    Washed-up Souvenirs: " + nf.format(washedUpSouvenirs) + "\n" +
															EnumChatFormatting.RED + "    Minos Hunters: " + nf.format(minosHunters) + "\n" +
															EnumChatFormatting.GRAY + "   Siamese Lynxes: " + nf.format(siameseLynxes) + "\n" +
															EnumChatFormatting.RED + "   Minotaurs: " + nf.format(minotaurs) + "\n" +
															EnumChatFormatting.WHITE + "   Gaia Constructs: " + nf.format(gaiaConstructs) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Minos Champions: " + nf.format(minosChampions) + "\n" +
															EnumChatFormatting.GOLD + "    Minos Inquisitors: " + nf.format(minosInquisitors) + "\n" +
															EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + "-------------------"));
				break;
			case "catacombs":
				if (arg1.length == 1) {
					player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: /loot catacombs <f1/f2/f3/f4/f5/f6/f7>"));
					return;
				}
				switch (arg1[1].toLowerCase()) {
					case "f1":
					case "floor1":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F1 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Bonzo's Staffs: " + nf.format(bonzoStaffsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f1CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f1TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F1 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Bonzo's Staffs: " + nf.format(bonzoStaffs) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f1CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f1TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f2":
					case "floor2":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F2 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Scarf's Studies: " + nf.format(scarfStudiesSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Blades: " + nf.format(adaptiveSwordsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f2CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f2TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F2 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Scarf's Studies: " + nf.format(scarfStudies) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Blades: " + nf.format(adaptiveSwords) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f2CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f2TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f3":
					case "floor3":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F3 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Helmets: " + nf.format(adaptiveHelmsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Chestplates: " + nf.format(adaptiveChestsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Leggings: " + nf.format(adaptiveLegsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Boots: " + nf.format(adaptiveBootsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f3CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f3TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F3 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Helmets: " + nf.format(adaptiveHelms) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Chestplates: " + nf.format(adaptiveChests) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Leggings: " + nf.format(adaptiveLegs) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Boots: " + nf.format(adaptiveBoots) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f3CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f3TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f4":
					case "floor4":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F4 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Spirit Wings: " + nf.format(spiritWingsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Spirit Bones: " + nf.format(spiritBonesSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Spirit Boots: " + nf.format(spiritBootsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Spirit Swords: " + nf.format(spiritSwordsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Spirit Bows: " + nf.format(spiritBowsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Epic Spirit Pets: " + nf.format(epicSpiritPetsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Leg Spirit Pets: " + nf.format(legSpiritPetsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f4CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f4TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F4 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Spirit Wings: " + nf.format(spiritWings) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Spirit Bones: " + nf.format(spiritBones) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Spirit Boots: " + nf.format(spiritBoots) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Spirit Swords: " + nf.format(spiritSwords) + "\n" +
																	EnumChatFormatting.GOLD + "    Spirit Bows: " + nf.format(spiritBows) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Epic Spirit Pets: " + nf.format(epicSpiritPets) + "\n" +
																	EnumChatFormatting.GOLD + "    Leg Spirit Pets: " + nf.format(legSpiritPets) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f4CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f4TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f5":
					case "floor5":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F5 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulatorsSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Warped Stones: " + nf.format(warpedStonesSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Helmets: " + nf.format(shadowAssHelmsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Chests: " + nf.format(shadowAssChestsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Legs: " + nf.format(shadowAssLegsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Boots: " + nf.format(shadowAssBootsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Last Breaths: " + nf.format(lastBreathsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Livid Daggers: " + nf.format(lividDaggersSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Shadow Furys: " + nf.format(shadowFurysSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f5CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f5TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F5 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Warped Stones: " + nf.format(warpedStones) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Helmets: " + nf.format(shadowAssHelms) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Chests: " + nf.format(shadowAssChests) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Legs: " + nf.format(shadowAssLegs) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Boots: " + nf.format(shadowAssBoots) + "\n" +
																	EnumChatFormatting.GOLD + "    Last Breaths: " + nf.format(lastBreaths) + "\n" +
																	EnumChatFormatting.GOLD + "    Livid Daggers: " + nf.format(lividDaggers) + "\n" +
																	EnumChatFormatting.GOLD + "    Shadow Furys: " + nf.format(shadowFurys) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f5CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f5TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f6":
					case "floor6":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F6 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Ancient Roses: " + nf.format(ancientRosesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Precursor Eyes: " + nf.format(precursorEyesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Giant's Swords: " + nf.format(giantsSwordsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Lord Helmets: " + nf.format(necroLordHelmsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Lord Chestplates: " + nf.format(necroLordChestsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Lord Leggings: " + nf.format(necroLordLegsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Lord Boots: " + nf.format(necroLordBootsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Swords: " + nf.format(necroSwordsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f6CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f6TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F6 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Ancient Roses: " + nf.format(ancientRoses) + "\n" +
																	EnumChatFormatting.GOLD + "    Precursor Eyes: " + nf.format(precursorEyes) + "\n" +
																	EnumChatFormatting.GOLD + "    Giant's Swords: " + nf.format(giantsSwords) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Lord Helmets: " + nf.format(necroLordHelms) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Lord Chestplates: " + nf.format(necroLordChests) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Lord Leggings: " + nf.format(necroLordLegs) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Lord Boots: " + nf.format(necroLordBoots) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Swords: " + nf.format(necroSwords) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f6CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f6TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f7":
					case "floor7":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F7 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Wither Bloods: " + nf.format(witherBloodsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Wither Cloaks: " + nf.format(witherCloaksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Implosions: " + nf.format(implosionsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Wither Shields: " + nf.format(witherShieldsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Warps: " + nf.format(shadowWarpsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Necron's Handles: " + nf.format(necronsHandlesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Auto Recombobulator: " + nf.format(autoRecombsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Wither Helmets: " + nf.format(witherHelmsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Wither Chesplates: " + nf.format(witherChestsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Wither Leggings: " + nf.format(witherLegsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Wither Boots: " + nf.format(witherBootsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f7CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f7TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F7 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Wither Bloods: " + nf.format(witherBloods) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Wither Cloaks: " + nf.format(witherCloaks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Implosions: " + nf.format(implosions) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Wither Shields: " + nf.format(witherShields) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Warps: " + nf.format(shadowWarps) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Necron's Handles: " + nf.format(necronsHandles) + "\n" +
																	EnumChatFormatting.GOLD + "    Auto Recombobulator: " + nf.format(autoRecombs) + "\n" +
																	EnumChatFormatting.GOLD + "    Wither Helmets: " + nf.format(witherHelms) + "\n" +
																	EnumChatFormatting.GOLD + "    Wither Chesplates: " + nf.format(witherChests) + "\n" +
																	EnumChatFormatting.GOLD + "    Wither Leggings: " + nf.format(witherLegs) + "\n" +
																	EnumChatFormatting.GOLD + "    Wither Boots: " + nf.format(witherBoots) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(f7CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, f7TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					default:
						player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: /loot catacombs <f1/f2/f3/f4/f5/f6/f7>"));
				}
				break;
			default:
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
		}
	}

}
