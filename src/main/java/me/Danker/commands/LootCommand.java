package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.loot.LootTracker;
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

	@Override
	public String getCommandName() {
		return "loot";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <zombie/spider/wolf/fishing/catacombs/mythological> [winter/festival/spooky/f(1-7)/session]";
	}

	public static String usage(ICommandSender arg0) {
		return new LootCommand().getCommandUsage(arg0);
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
					if (LootTracker.wolfTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(LootTracker.wolfTimeSession, timeNow);
					}
					if (LootTracker.wolfBossesSession == -1) {
						bossesBetween = "Never";
					} else {
						bossesBetween = nf.format(LootTracker.wolfBossesSession);
					}
					if (ToggleCommand.slayerCountTotal) {
						drop20 = nf.format(LootTracker.wolfWheelsSession);
					} else {
						drop20 = nf.format(LootTracker.wolfWheelsDropsSession) + " times";
					}

					player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + "  Sven Loot Summary (Current Session):\n" +
																EnumChatFormatting.GOLD + "    Svens Killed: " + nf.format(LootTracker.wolfSvensSession) + "\n" +
																EnumChatFormatting.GREEN + "    Wolf Teeth: " + nf.format(LootTracker.wolfTeethSession) + "\n" +
																EnumChatFormatting.BLUE + "    Hamster Wheels: " + drop20 + "\n" +
																EnumChatFormatting.AQUA + "    Spirit Runes: " + LootTracker.wolfSpiritsSession + "\n" +
																EnumChatFormatting.WHITE + "    Critical VI Books: " + LootTracker.wolfBooksSession + "\n" +
																EnumChatFormatting.DARK_RED + "    Red Claw Eggs: " + LootTracker.wolfEggsSession + "\n" +
																EnumChatFormatting.GOLD + "    Couture Runes: " + LootTracker.wolfCouturesSession + "\n" +
																EnumChatFormatting.AQUA + "    Grizzly Baits: " + LootTracker.wolfBaitsSession + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Overfluxes: " + LootTracker.wolfFluxesSession + "\n" +
																EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
																EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
																EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
					return;
				}

				if (LootTracker.wolfTime == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = Utils.getTimeBetween(LootTracker.wolfTime, timeNow);
				}
				if (LootTracker.wolfBosses == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(LootTracker.wolfBosses);
				}
				if (ToggleCommand.slayerCountTotal) {
					drop20 = nf.format(LootTracker.wolfWheels);
				} else {
					drop20 = nf.format(LootTracker.wolfWheelsDrops) + " times";
				}

				player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + "  Sven Loot Summary:\n" +
															EnumChatFormatting.GOLD + "    Svens Killed: " + nf.format(LootTracker.wolfSvens) + "\n" +
															EnumChatFormatting.GREEN + "    Wolf Teeth: " + nf.format(LootTracker.wolfTeeth) + "\n" +
															EnumChatFormatting.BLUE + "    Hamster Wheels: " + drop20 + "\n" +
															EnumChatFormatting.AQUA + "    Spirit Runes: " + LootTracker.wolfSpirits + "\n" +
															EnumChatFormatting.WHITE + "    Critical VI Books: " + LootTracker.wolfBooks + "\n" +
															EnumChatFormatting.DARK_RED + "    Red Claw Eggs: " + LootTracker.wolfEggs + "\n" +
															EnumChatFormatting.GOLD + "    Couture Runes: " + LootTracker.wolfCoutures + "\n" +
															EnumChatFormatting.AQUA + "    Grizzly Baits: " + LootTracker.wolfBaits + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Overfluxes: " + LootTracker.wolfFluxes + "\n" +
															EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
															EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
				break;
			case "spider":
				if (showSession) {
					if (LootTracker.spiderTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(LootTracker.spiderTimeSession, timeNow);
					}
					if (LootTracker.spiderBossesSession == -1) {
						bossesBetween = "Never";
					} else {
						bossesBetween = nf.format(LootTracker.spiderBossesSession);
					}
					if (ToggleCommand.slayerCountTotal) {
						drop20 = nf.format(LootTracker.spiderTAPSession);
					} else {
						drop20 = nf.format(LootTracker.spiderTAPDropsSession) + " times";
					}

					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Spider Loot Summary (Current Session):\n" +
																EnumChatFormatting.GOLD + "    Tarantulas Killed: " + nf.format(LootTracker.spiderTarantulasSession) + "\n" +
																EnumChatFormatting.GREEN + "    Tarantula Webs: " + nf.format(LootTracker.spiderWebsSession) + "\n" +
																EnumChatFormatting.DARK_GREEN + "    Arrow Poison: " + drop20 + "\n" +
																EnumChatFormatting.DARK_GRAY + "    Bite Runes: " + LootTracker.spiderBitesSession + "\n" +
																EnumChatFormatting.WHITE + "    Bane VI Books: " + LootTracker.spiderBooksSession + "\n" +
																EnumChatFormatting.AQUA + "    Spider Catalysts: " + LootTracker.spiderCatalystsSession + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Tarantula Talismans: " + LootTracker.spiderTalismansSession + "\n" +
																EnumChatFormatting.LIGHT_PURPLE + "    Fly Swatters: " + LootTracker.spiderSwattersSession + "\n" +
																EnumChatFormatting.GOLD + "    Digested Mosquitos: " + LootTracker.spiderMosquitosSession + "\n" +
																EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
																EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
																EnumChatFormatting.RED + EnumChatFormatting.BOLD + " -------------------"));
					return;
				}

				if (LootTracker.spiderTime == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = Utils.getTimeBetween(LootTracker.spiderTime, timeNow);
				}
				if (LootTracker.spiderBosses == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(LootTracker.spiderBosses);
				}
				if (ToggleCommand.slayerCountTotal) {
					drop20 = nf.format(LootTracker.spiderTAP);
				} else {
					drop20 = nf.format(LootTracker.spiderTAPDrops) + " times";
				}

				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Spider Loot Summary:\n" +
															EnumChatFormatting.GOLD + "    Tarantulas Killed: " + nf.format(LootTracker.spiderTarantulas) + "\n" +
															EnumChatFormatting.GREEN + "    Tarantula Webs: " + nf.format(LootTracker.spiderWebs) + "\n" +
															EnumChatFormatting.DARK_GREEN + "    Arrow Poison: " + drop20 + "\n" +
															EnumChatFormatting.DARK_GRAY + "    Bite Runes: " + LootTracker.spiderBites + "\n" +
															EnumChatFormatting.WHITE + "    Bane VI Books: " + LootTracker.spiderBooks + "\n" +
															EnumChatFormatting.AQUA + "    Spider Catalysts: " + LootTracker.spiderCatalysts + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Tarantula Talismans: " + LootTracker.spiderTalismans + "\n" +
															EnumChatFormatting.LIGHT_PURPLE + "    Fly Swatters: " + LootTracker.spiderSwatters + "\n" +
															EnumChatFormatting.GOLD + "    Digested Mosquitos: " + LootTracker.spiderMosquitos + "\n" +
															EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
															EnumChatFormatting.RED + EnumChatFormatting.BOLD + " -------------------"));
				break;
			case "zombie":
				if (showSession) {
					if (LootTracker.zombieTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(LootTracker.zombieTimeSession, timeNow);
					}
					if (LootTracker.zombieBossesSession == -1) {
						bossesBetween = "Never";
					} else {
						bossesBetween = nf.format(LootTracker.zombieBossesSession);
					}
					if (ToggleCommand.slayerCountTotal) {
						drop20 = nf.format(LootTracker.zombieFoulFleshSession);
					} else {
						drop20 = nf.format(LootTracker.zombieFoulFleshDropsSession) + " times";
					}

					player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Zombie Loot Summary (Current Session):\n" +
																EnumChatFormatting.GOLD + "    Revs Killed: " + nf.format(LootTracker.zombieRevsSession) + "\n" +
																EnumChatFormatting.GREEN + "    Revenant Flesh: " + nf.format(LootTracker.zombieRevFleshSession) + "\n" +
																EnumChatFormatting.BLUE + "    Foul Flesh: " + drop20 + "\n" +
																EnumChatFormatting.DARK_GREEN + "    Pestilence Runes: " +LootTracker.zombiePestilencesSession + "\n" +
																EnumChatFormatting.WHITE + "    Smite VI Books: " + LootTracker.zombieBooksSession + "\n" +
																EnumChatFormatting.AQUA + "    Undead Catalysts: " + LootTracker.zombieUndeadCatasSession + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Beheaded Horrors: " + LootTracker.zombieBeheadedsSession + "\n" +
																EnumChatFormatting.RED + "    Revenant Catalysts: " + LootTracker.zombieRevCatasSession + "\n" +
																EnumChatFormatting.DARK_GREEN + "    Snake Runes: " + LootTracker.zombieSnakesSession + "\n" +
																EnumChatFormatting.GOLD + "    Scythe Blades: " + LootTracker.zombieScythesSession + "\n" +
																EnumChatFormatting.RED + "    Shard of the Shreddeds: " + LootTracker.zombieShardsSession + "\n" +
																EnumChatFormatting.RED + "    Warden Hearts: " + LootTracker.zombieWardenHeartsSession + "\n" +
																EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
																EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
																EnumChatFormatting.GREEN + EnumChatFormatting.BOLD + " -------------------"));
					return;
				}

				if (LootTracker.zombieTime == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = Utils.getTimeBetween(LootTracker.zombieTime, timeNow);
				}
				if (LootTracker.zombieBosses == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(LootTracker.zombieBosses);
				}
				if (ToggleCommand.slayerCountTotal) {
					drop20 = nf.format(LootTracker.zombieFoulFlesh);
				} else {
					drop20 = nf.format(LootTracker.zombieFoulFleshDrops) + " times";
				}

				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Zombie Loot Summary:\n" +
															EnumChatFormatting.GOLD + "    Revs Killed: " + nf.format(LootTracker.zombieRevs) + "\n" +
															EnumChatFormatting.GREEN + "    Revenant Flesh: " + nf.format(LootTracker.zombieRevFlesh) + "\n" +
															EnumChatFormatting.BLUE + "    Foul Flesh: " + drop20 + "\n" +
															EnumChatFormatting.DARK_GREEN + "    Pestilence Runes: " + LootTracker.zombiePestilences + "\n" +
															EnumChatFormatting.WHITE + "    Smite VI Books: " + LootTracker.zombieBooks + "\n" +
															EnumChatFormatting.AQUA + "    Undead Catalysts: " + LootTracker.zombieUndeadCatas + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Beheaded Horrors: " + LootTracker.zombieBeheadeds + "\n" +
															EnumChatFormatting.RED + "    Revenant Catalysts: " + LootTracker.zombieRevCatas + "\n" +
															EnumChatFormatting.DARK_GREEN + "    Snake Runes: " + LootTracker.zombieSnakes + "\n" +
															EnumChatFormatting.GOLD + "    Scythe Blades: " + LootTracker.zombieScythes + "\n" +
															EnumChatFormatting.RED + "    Shard of the Shreddeds: " + LootTracker.zombieShards + "\n" +
															EnumChatFormatting.RED + "    Warden Hearts: " + LootTracker.zombieWardenHearts + "\n" +
															EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
															EnumChatFormatting.GREEN + EnumChatFormatting.BOLD + " -------------------"));
				break;
			case "fishing":
				if (arg1.length > 1) {
					if (arg1[1].equalsIgnoreCase("winter")) {
						if (showSession) {
							if (LootTracker.yetiTimeSession == -1) {
								timeBetween = "Never";
							} else {
								timeBetween = Utils.getTimeBetween(LootTracker.yetiTimeSession, timeNow);
							}
							if (LootTracker.yetiSCsSession == -1) {
								bossesBetween = "Never";
							} else {
								bossesBetween = nf.format(LootTracker.yetiSCsSession);
							}

							player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "  Winter Fishing Summary (Current Session):\n" +
																		EnumChatFormatting.AQUA + "    Frozen Steves: " + nf.format(LootTracker.frozenStevesSession) + "\n" +
																		EnumChatFormatting.WHITE + "    Snowmans: " + nf.format(LootTracker.frostyTheSnowmansSession) + "\n" +
																		EnumChatFormatting.DARK_GREEN + "    Grinches: " + nf.format(LootTracker.grinchesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Yetis: " + nf.format(LootTracker.yetisSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Since Yeti: " + timeBetween + "\n" +
																		EnumChatFormatting.AQUA + "    Creatures Since Yeti: " + bossesBetween + "\n" +
																		EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}

						if (LootTracker.yetiTime == -1) {
							timeBetween = "Never";
						} else {
							timeBetween = Utils.getTimeBetween(LootTracker.yetiTime, timeNow);
						}
						if (LootTracker.yetiSCs == -1) {
							bossesBetween = "Never";
						} else {
							bossesBetween = nf.format(LootTracker.yetiSCs);
						}

						player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "  Winter Fishing Summary:\n" +
																	EnumChatFormatting.AQUA + "    Frozen Steves: " + nf.format(LootTracker.frozenSteves) + "\n" +
																	EnumChatFormatting.WHITE + "    Snowmans: " + nf.format(LootTracker.frostyTheSnowmans) + "\n" +
																	EnumChatFormatting.DARK_GREEN + "    Grinches: " + nf.format(LootTracker.grinches) + "\n" +
																	EnumChatFormatting.GOLD + "    Yetis: " + nf.format(LootTracker.yetis) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Since Yeti: " + timeBetween + "\n" +
																	EnumChatFormatting.AQUA + "    Creatures Since Yeti: " + bossesBetween + "\n" +
																	EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
						return;
					} else if (arg1[1].equalsIgnoreCase("festival")) {
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.DARK_BLUE + EnumChatFormatting.BOLD + " Fishing Festival Summary (Current Session):\n" +
																		EnumChatFormatting.LIGHT_PURPLE + "    Nurse Sharks: " + nf.format(LootTracker.nurseSharksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Blue Sharks: " + nf.format(LootTracker.blueSharksSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Tiger Sharks: " + nf.format(LootTracker.tigerSharksSession) + "\n" +
																		EnumChatFormatting.WHITE + "    Great White Sharks: " + nf.format(LootTracker.greatWhiteSharksSession) + "\n" +
																		EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}

						player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.DARK_BLUE + EnumChatFormatting.BOLD + " Fishing Festival Summary:\n" +
																	EnumChatFormatting.LIGHT_PURPLE + "    Nurse Sharks: " + nf.format(LootTracker.nurseSharks) + "\n" +
																	EnumChatFormatting.BLUE + "    Blue Sharks: " + nf.format(LootTracker.blueSharks) + "\n" +
																	EnumChatFormatting.GOLD + "    Tiger Sharks: " + nf.format(LootTracker.tigerSharks) + "\n" +
																	EnumChatFormatting.WHITE + "    Great White Sharks: " + nf.format(LootTracker.greatWhiteSharks) + "\n" +
																	EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
					} else if (arg1[1].equalsIgnoreCase("spooky")) {
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + " Spooky Fishing Summary (Current Session):\n" +
																		EnumChatFormatting.BLUE + "    Scarecrows: " + nf.format(LootTracker.scarecrowsSession) + "\n" +
																		EnumChatFormatting.GRAY + "    Nightmares: " + nf.format(LootTracker.nightmaresSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Werewolves: " + nf.format(LootTracker.werewolfsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Phantom Fishers: " + nf.format(LootTracker.phantomFishersSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Grim Reapers: " + nf.format(LootTracker.grimReapersSession) + "\n" +
																		EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------"));
							return;
						}

						player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + " Spooky Fishing Summary:\n" +
																	EnumChatFormatting.BLUE + "    Scarecrows: " + nf.format(LootTracker.scarecrows) + "\n" +
																	EnumChatFormatting.GRAY + "    Nightmares: " + nf.format(LootTracker.nightmares) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Werewolves: " + nf.format(LootTracker.werewolfs) + "\n" +
																	EnumChatFormatting.GOLD + "    Phantom Fishers: " + nf.format(LootTracker.phantomFishers) + "\n" +
																	EnumChatFormatting.GOLD + "    Grim Reapers: " + nf.format(LootTracker.grimReapers) + "\n" +
																	EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------"));
					}
				}

				if (showSession) {
					if (LootTracker.empTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(LootTracker.empTimeSession, timeNow);
					}
					if (LootTracker.empSCsSession == -1) {
						bossesBetween = "Never";
					} else {
						bossesBetween = nf.format(LootTracker.empSCsSession);
					}

					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + "  Fishing Summary (Current Session):\n" +
																EnumChatFormatting.AQUA + "    Sea Creatures Caught: " + nf.format(LootTracker.seaCreaturesSession) + "\n" +
																EnumChatFormatting.GOLD + "    Good Catches: " + nf.format(LootTracker.goodCatchesSession) + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Great Catches: " + nf.format(LootTracker.greatCatchesSession) + "\n\n" +
																EnumChatFormatting.GRAY + "    Squids: " + nf.format(LootTracker.squidsSession) + "\n" +
																EnumChatFormatting.GREEN + "    Sea Walkers: " + nf.format(LootTracker.seaWalkersSession) + "\n" +
																EnumChatFormatting.DARK_GRAY + "    Night Squids: " + nf.format(LootTracker.nightSquidsSession) + "\n" +
																EnumChatFormatting.DARK_AQUA + "    Sea Guardians: " + nf.format(LootTracker.seaGuardiansSession) + "\n" +
																EnumChatFormatting.BLUE + "    Sea Witches: " + nf.format(LootTracker.seaWitchesSession) + "\n" +
																EnumChatFormatting.GREEN + "    Sea Archers: " + nf.format(LootTracker.seaArchersSession) + "\n" +
																EnumChatFormatting.GREEN + "    Monster of the Deeps: " + nf.format(LootTracker.monsterOfTheDeepsSession) + "\n" +
																EnumChatFormatting.YELLOW + "    Catfishes: " + nf.format(LootTracker.catfishesSession) + "\n" +
																EnumChatFormatting.GOLD + "    Carrot Kings: " + nf.format(LootTracker.carrotKingsSession) + "\n" +
																EnumChatFormatting.GRAY + "    Sea Leeches: " + nf.format(LootTracker.seaLeechesSession) + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Guardian Defenders: " + nf.format(LootTracker.guardianDefendersSession) + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Deep Sea Protectors: " + nf.format(LootTracker.deepSeaProtectorsSession) + "\n" +
																EnumChatFormatting.GOLD + "    Hydras: " + nf.format(LootTracker.hydrasSession) + "\n" +
																EnumChatFormatting.GOLD + "    Sea Emperors: " + nf.format(LootTracker.seaEmperorsSession) + "\n" +
																EnumChatFormatting.AQUA + "    Time Since Sea Emperor: " + timeBetween + "\n" +
																EnumChatFormatting.AQUA + "    Sea Creatures Since Sea Emperor: " + bossesBetween + "\n" +
																EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + " -------------------"));
					return;
				}

				if (LootTracker.empTime == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = Utils.getTimeBetween(LootTracker.empTime, timeNow);
				}
				if (LootTracker.empSCs == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(LootTracker.empSCs);
				}

				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + "  Fishing Summary:\n" +
															EnumChatFormatting.AQUA + "    Sea Creatures Caught: " + nf.format(LootTracker.seaCreatures) + "\n" +
															EnumChatFormatting.GOLD + "    Good Catches: " + nf.format(LootTracker.goodCatches) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Great Catches: " + nf.format(LootTracker.greatCatches) + "\n\n" +
															EnumChatFormatting.GRAY + "    Squids: " + nf.format(LootTracker.squids) + "\n" +
															EnumChatFormatting.GREEN + "    Sea Walkers: " + nf.format(LootTracker.seaWalkers) + "\n" +
															EnumChatFormatting.DARK_GRAY + "    Night Squids: " + nf.format(LootTracker.nightSquids) + "\n" +
															EnumChatFormatting.DARK_AQUA + "    Sea Guardians: " + nf.format(LootTracker.seaGuardians) + "\n" +
															EnumChatFormatting.BLUE + "    Sea Witches: " + nf.format(LootTracker.seaWitches) + "\n" +
															EnumChatFormatting.GREEN + "    Sea Archers: " + nf.format(LootTracker.seaArchers) + "\n" +
															EnumChatFormatting.GREEN + "    Monster of the Deeps: " + nf.format(LootTracker.monsterOfTheDeeps) + "\n" +
															EnumChatFormatting.YELLOW + "    Catfishes: " + nf.format(LootTracker.catfishes) + "\n" +
															EnumChatFormatting.GOLD + "    Carrot Kings: " + nf.format(LootTracker.carrotKings) + "\n" +
															EnumChatFormatting.GRAY + "    Sea Leeches: " + nf.format(LootTracker.seaLeeches) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Guardian Defenders: " + nf.format(LootTracker.guardianDefenders) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Deep Sea Protectors: " + nf.format(LootTracker.deepSeaProtectors) + "\n" +
															EnumChatFormatting.GOLD + "    Hydras: " + nf.format(LootTracker.hydras) + "\n" +
															EnumChatFormatting.GOLD + "    Sea Emperors: " + nf.format(LootTracker.seaEmperors) + "\n" +
															EnumChatFormatting.AQUA + "    Time Since Sea Emperor: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Sea Creatures Since Sea Emperor: " + bossesBetween + "\n" +
															EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + " -------------------"));

				break;
			case "mythological":
				if (showSession) {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + "  Mythological Event Summary (Current Session):\n" +
																EnumChatFormatting.YELLOW + "    Coins: " + Utils.getMoneySpent(LootTracker.mythCoinsSession) + "\n" +
																EnumChatFormatting.WHITE + "    Griffin Feathers: " + nf.format(LootTracker.griffinFeathersSession) + "\n" +
																EnumChatFormatting.GOLD + "    Crown of Greeds: " + nf.format(LootTracker.crownOfGreedsSession) + "\n" +
																EnumChatFormatting.AQUA + "    Washed-up Souvenirs: " + nf.format(LootTracker.washedUpSouvenirsSession) + "\n" +
																EnumChatFormatting.RED + "    Minos Hunters: " + nf.format(LootTracker.minosHuntersSession) + "\n" +
																EnumChatFormatting.GRAY + "   Siamese Lynxes: " + nf.format(LootTracker.siameseLynxesSession) + "\n" +
																EnumChatFormatting.RED + "   Minotaurs: " + nf.format(LootTracker.minotaursSession) + "\n" +
																EnumChatFormatting.WHITE + "   Gaia Constructs: " + nf.format(LootTracker.gaiaConstructsSession) + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Minos Champions: " + nf.format(LootTracker.minosChampionsSession) + "\n" +
																EnumChatFormatting.GOLD + "    Minos Inquisitors: " + nf.format(LootTracker.minosInquisitorsSession) + "\n" +
																EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + "-------------------"));
					return;
				}
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + "  Mythological Event Summary:\n" +
															EnumChatFormatting.YELLOW + "    Coins: " + Utils.getMoneySpent(LootTracker.mythCoins) + "\n" +
															EnumChatFormatting.WHITE + "    Griffin Feathers: " + nf.format(LootTracker.griffinFeathers) + "\n" +
															EnumChatFormatting.GOLD + "    Crown of Greeds: " + nf.format(LootTracker.crownOfGreeds) + "\n" +
															EnumChatFormatting.AQUA + "    Washed-up Souvenirs: " + nf.format(LootTracker.washedUpSouvenirs) + "\n" +
															EnumChatFormatting.RED + "    Minos Hunters: " + nf.format(LootTracker.minosHunters) + "\n" +
															EnumChatFormatting.GRAY + "   Siamese Lynxes: " + nf.format(LootTracker.siameseLynxes) + "\n" +
															EnumChatFormatting.RED + "   Minotaurs: " + nf.format(LootTracker.minotaurs) + "\n" +
															EnumChatFormatting.WHITE + "   Gaia Constructs: " + nf.format(LootTracker.gaiaConstructs) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Minos Champions: " + nf.format(LootTracker.minosChampions) + "\n" +
															EnumChatFormatting.GOLD + "    Minos Inquisitors: " + nf.format(LootTracker.minosInquisitors) + "\n" +
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
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Bonzo's Staffs: " + nf.format(LootTracker.bonzoStaffsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f1CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f1TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F1 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Bonzo's Staffs: " + nf.format(LootTracker.bonzoStaffs) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f1CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f1TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f2":
					case "floor2":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F2 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Scarf's Studies: " + nf.format(LootTracker.scarfStudiesSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Blades: " + nf.format(LootTracker.adaptiveSwordsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f2CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f2TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F2 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Scarf's Studies: " + nf.format(LootTracker.scarfStudies) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Blades: " + nf.format(LootTracker.adaptiveSwords) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f2CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f2TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f3":
					case "floor3":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F3 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Helmets: " + nf.format(LootTracker.adaptiveHelmsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Chestplates: " + nf.format(LootTracker.adaptiveChestsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Leggings: " + nf.format(LootTracker.adaptiveLegsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Boots: " + nf.format(LootTracker.adaptiveBootsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f3CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f3TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F3 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Helmets: " + nf.format(LootTracker.adaptiveHelms) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Chestplates: " + nf.format(LootTracker.adaptiveChests) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Leggings: " + nf.format(LootTracker.adaptiveLegs) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Boots: " + nf.format(LootTracker.adaptiveBoots) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f3CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f3TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f4":
					case "floor4":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F4 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Spirit Wings: " + nf.format(LootTracker.spiritWingsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Spirit Bones: " + nf.format(LootTracker.spiritBonesSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Spirit Boots: " + nf.format(LootTracker.spiritBootsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Spirit Swords: " + nf.format(LootTracker.spiritSwordsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Spirit Bows: " + nf.format(LootTracker.spiritBowsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Epic Spirit Pets: " + nf.format(LootTracker.epicSpiritPetsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Leg Spirit Pets: " + nf.format(LootTracker.legSpiritPetsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f4CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f4TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F4 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Spirit Wings: " + nf.format(LootTracker.spiritWings) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Spirit Bones: " + nf.format(LootTracker.spiritBones) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Spirit Boots: " + nf.format(LootTracker.spiritBoots) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Spirit Swords: " + nf.format(LootTracker.spiritSwords) + "\n" +
																	EnumChatFormatting.GOLD + "    Spirit Bows: " + nf.format(LootTracker.spiritBows) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Epic Spirit Pets: " + nf.format(LootTracker.epicSpiritPets) + "\n" +
																	EnumChatFormatting.GOLD + "    Leg Spirit Pets: " + nf.format(LootTracker.legSpiritPets) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f4CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f4TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f5":
					case "floor5":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F5 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Warped Stones: " + nf.format(LootTracker.warpedStonesSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Helmets: " + nf.format(LootTracker.shadowAssHelmsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Chests: " + nf.format(LootTracker.shadowAssChestsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Legs: " + nf.format(LootTracker.shadowAssLegsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Boots: " + nf.format(LootTracker.shadowAssBootsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Last Breaths: " + nf.format(LootTracker.lastBreathsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Livid Daggers: " + nf.format(LootTracker.lividDaggersSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Shadow Furys: " + nf.format(LootTracker.shadowFurysSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f5CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f5TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F5 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Warped Stones: " + nf.format(LootTracker.warpedStones) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Helmets: " + nf.format(LootTracker.shadowAssHelms) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Chests: " + nf.format(LootTracker.shadowAssChests) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Legs: " + nf.format(LootTracker.shadowAssLegs) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Boots: " + nf.format(LootTracker.shadowAssBoots) + "\n" +
																	EnumChatFormatting.GOLD + "    Last Breaths: " + nf.format(LootTracker.lastBreaths) + "\n" +
																	EnumChatFormatting.GOLD + "    Livid Daggers: " + nf.format(LootTracker.lividDaggers) + "\n" +
																	EnumChatFormatting.GOLD + "    Shadow Furys: " + nf.format(LootTracker.shadowFurys) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f5CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f5TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f6":
					case "floor6":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F6 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Ancient Roses: " + nf.format(LootTracker.ancientRosesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Precursor Eyes: " + nf.format(LootTracker.precursorEyesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Giant's Swords: " + nf.format(LootTracker.giantsSwordsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Lord Helmets: " + nf.format(LootTracker.necroLordHelmsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Lord Chestplates: " + nf.format(LootTracker.necroLordChestsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Lord Leggings: " + nf.format(LootTracker.necroLordLegsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Lord Boots: " + nf.format(LootTracker.necroLordBootsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Swords: " + nf.format(LootTracker.necroSwordsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f6CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f6TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F6 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Ancient Roses: " + nf.format(LootTracker.ancientRoses) + "\n" +
																	EnumChatFormatting.GOLD + "    Precursor Eyes: " + nf.format(LootTracker.precursorEyes) + "\n" +
																	EnumChatFormatting.GOLD + "    Giant's Swords: " + nf.format(LootTracker.giantsSwords) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Lord Helmets: " + nf.format(LootTracker.necroLordHelms) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Lord Chestplates: " + nf.format(LootTracker.necroLordChests) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Lord Leggings: " + nf.format(LootTracker.necroLordLegs) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Lord Boots: " + nf.format(LootTracker.necroLordBoots) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Swords: " + nf.format(LootTracker.necroSwords) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f6CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f6TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f7":
					case "floor7":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F7 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Wither Bloods: " + nf.format(LootTracker.witherBloodsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Wither Cloaks: " + nf.format(LootTracker.witherCloaksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Implosions: " + nf.format(LootTracker.implosionsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Wither Shields: " + nf.format(LootTracker.witherShieldsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Warps: " + nf.format(LootTracker.shadowWarpsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Necron's Handles: " + nf.format(LootTracker.necronsHandlesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Auto Recombobulator: " + nf.format(LootTracker.autoRecombsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Wither Helmets: " + nf.format(LootTracker.witherHelmsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Wither Chesplates: " + nf.format(LootTracker.witherChestsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Wither Leggings: " + nf.format(LootTracker.witherLegsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Wither Boots: " + nf.format(LootTracker.witherBootsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f7CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f7TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F7 Summary:\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(LootTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Wither Bloods: " + nf.format(LootTracker.witherBloods) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Wither Cloaks: " + nf.format(LootTracker.witherCloaks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Implosions: " + nf.format(LootTracker.implosions) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Wither Shields: " + nf.format(LootTracker.witherShields) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Warps: " + nf.format(LootTracker.shadowWarps) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Necron's Handles: " + nf.format(LootTracker.necronsHandles) + "\n" +
																	EnumChatFormatting.GOLD + "    Auto Recombobulator: " + nf.format(LootTracker.autoRecombs) + "\n" +
																	EnumChatFormatting.GOLD + "    Wither Helmets: " + nf.format(LootTracker.witherHelms) + "\n" +
																	EnumChatFormatting.GOLD + "    Wither Chesplates: " + nf.format(LootTracker.witherChests) + "\n" +
																	EnumChatFormatting.GOLD + "    Wither Leggings: " + nf.format(LootTracker.witherLegs) + "\n" +
																	EnumChatFormatting.GOLD + "    Wither Boots: " + nf.format(LootTracker.witherBoots) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(LootTracker.f7CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, LootTracker.f7TimeSpent) + "\n" +
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
