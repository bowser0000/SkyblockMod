package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.loot.*;
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
		return "/" + getCommandName() + " <zombie/spider/wolf/enderman/fishing/catacombs/mythological> [winter/festival/spooky/f(1-7)/mm/session]";
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
			return getListOfStringsMatchingLastWord(args, "wolf", "spider", "zombie", "enderman", "fishing", "catacombs", "mythological");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("fishing")) {
			return getListOfStringsMatchingLastWord(args, "winter", "festival", "spooky", "session");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("catacombs")) {
			return getListOfStringsMatchingLastWord(args, "f1", "floor1", "f2", "floor2", "f3", "floor3", "f4", "floor4", "f5", "floor5", "f6", "floor6", "f7", "floor7", "mm", "master");
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
					if (WolfTracker.wolfTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(WolfTracker.wolfTimeSession, timeNow);
					}
					if (WolfTracker.wolfBossesSession == -1) {
						bossesBetween = "Never";
					} else {
						bossesBetween = nf.format(WolfTracker.wolfBossesSession);
					}
					if (ToggleCommand.slayerCountTotal) {
						drop20 = nf.format(WolfTracker.wolfWheelsSession);
					} else {
						drop20 = nf.format(WolfTracker.wolfWheelsDropsSession) + " times";
					}

					player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + "  Sven Loot Summary (Current Session):\n" +
																EnumChatFormatting.GOLD + "    Svens Killed: " + nf.format(WolfTracker.wolfSvensSession) + "\n" +
																EnumChatFormatting.GREEN + "    Wolf Teeth: " + nf.format(WolfTracker.wolfTeethSession) + "\n" +
																EnumChatFormatting.BLUE + "    Hamster Wheels: " + drop20 + "\n" +
																EnumChatFormatting.AQUA + "    Spirit Runes: " + WolfTracker.wolfSpiritsSession + "\n" +
																EnumChatFormatting.WHITE + "    Critical VI Books: " + WolfTracker.wolfBooksSession + "\n" +
																EnumChatFormatting.DARK_RED + "    Red Claw Eggs: " + WolfTracker.wolfEggsSession + "\n" +
																EnumChatFormatting.GOLD + "    Couture Runes: " + WolfTracker.wolfCouturesSession + "\n" +
																EnumChatFormatting.AQUA + "    Grizzly Baits: " + WolfTracker.wolfBaitsSession + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Overfluxes: " + WolfTracker.wolfFluxesSession + "\n" +
																EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
																EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
																EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
					return;
				}

				if (WolfTracker.wolfTime == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = Utils.getTimeBetween(WolfTracker.wolfTime, timeNow);
				}
				if (WolfTracker.wolfBosses == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(WolfTracker.wolfBosses);
				}
				if (ToggleCommand.slayerCountTotal) {
					drop20 = nf.format(WolfTracker.wolfWheels);
				} else {
					drop20 = nf.format(WolfTracker.wolfWheelsDrops) + " times";
				}

				player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + "  Sven Loot Summary:\n" +
															EnumChatFormatting.GOLD + "    Svens Killed: " + nf.format(WolfTracker.wolfSvens) + "\n" +
															EnumChatFormatting.GREEN + "    Wolf Teeth: " + nf.format(WolfTracker.wolfTeeth) + "\n" +
															EnumChatFormatting.BLUE + "    Hamster Wheels: " + drop20 + "\n" +
															EnumChatFormatting.AQUA + "    Spirit Runes: " + WolfTracker.wolfSpirits + "\n" +
															EnumChatFormatting.WHITE + "    Critical VI Books: " + WolfTracker.wolfBooks + "\n" +
															EnumChatFormatting.DARK_RED + "    Red Claw Eggs: " + WolfTracker.wolfEggs + "\n" +
															EnumChatFormatting.GOLD + "    Couture Runes: " + WolfTracker.wolfCoutures + "\n" +
															EnumChatFormatting.AQUA + "    Grizzly Baits: " + WolfTracker.wolfBaits + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Overfluxes: " + WolfTracker.wolfFluxes + "\n" +
															EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
															EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
				break;
			case "spider":
				if (showSession) {
					if (SpiderTracker.spiderTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(SpiderTracker.spiderTimeSession, timeNow);
					}
					if (SpiderTracker.spiderBossesSession == -1) {
						bossesBetween = "Never";
					} else {
						bossesBetween = nf.format(SpiderTracker.spiderBossesSession);
					}
					if (ToggleCommand.slayerCountTotal) {
						drop20 = nf.format(SpiderTracker.spiderTAPSession);
					} else {
						drop20 = nf.format(SpiderTracker.spiderTAPDropsSession) + " times";
					}

					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Spider Loot Summary (Current Session):\n" +
																EnumChatFormatting.GOLD + "    Tarantulas Killed: " + nf.format(SpiderTracker.spiderTarantulasSession) + "\n" +
																EnumChatFormatting.GREEN + "    Tarantula Webs: " + nf.format(SpiderTracker.spiderWebsSession) + "\n" +
																EnumChatFormatting.DARK_GREEN + "    Arrow Poison: " + drop20 + "\n" +
																EnumChatFormatting.DARK_GRAY + "    Bite Runes: " + SpiderTracker.spiderBitesSession + "\n" +
																EnumChatFormatting.WHITE + "    Bane VI Books: " + SpiderTracker.spiderBooksSession + "\n" +
																EnumChatFormatting.AQUA + "    Spider Catalysts: " + SpiderTracker.spiderCatalystsSession + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Tarantula Talismans: " + SpiderTracker.spiderTalismansSession + "\n" +
																EnumChatFormatting.LIGHT_PURPLE + "    Fly Swatters: " + SpiderTracker.spiderSwattersSession + "\n" +
																EnumChatFormatting.GOLD + "    Digested Mosquitos: " + SpiderTracker.spiderMosquitosSession + "\n" +
																EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
																EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
																EnumChatFormatting.RED + EnumChatFormatting.BOLD + " -------------------"));
					return;
				}

				if (SpiderTracker.spiderTime == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = Utils.getTimeBetween(SpiderTracker.spiderTime, timeNow);
				}
				if (SpiderTracker.spiderBosses == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(SpiderTracker.spiderBosses);
				}
				if (ToggleCommand.slayerCountTotal) {
					drop20 = nf.format(SpiderTracker.spiderTAP);
				} else {
					drop20 = nf.format(SpiderTracker.spiderTAPDrops) + " times";
				}

				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Spider Loot Summary:\n" +
															EnumChatFormatting.GOLD + "    Tarantulas Killed: " + nf.format(SpiderTracker.spiderTarantulas) + "\n" +
															EnumChatFormatting.GREEN + "    Tarantula Webs: " + nf.format(SpiderTracker.spiderWebs) + "\n" +
															EnumChatFormatting.DARK_GREEN + "    Arrow Poison: " + drop20 + "\n" +
															EnumChatFormatting.DARK_GRAY + "    Bite Runes: " + SpiderTracker.spiderBites + "\n" +
															EnumChatFormatting.WHITE + "    Bane VI Books: " + SpiderTracker.spiderBooks + "\n" +
															EnumChatFormatting.AQUA + "    Spider Catalysts: " + SpiderTracker.spiderCatalysts + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Tarantula Talismans: " + SpiderTracker.spiderTalismans + "\n" +
															EnumChatFormatting.LIGHT_PURPLE + "    Fly Swatters: " + SpiderTracker.spiderSwatters + "\n" +
															EnumChatFormatting.GOLD + "    Digested Mosquitos: " + SpiderTracker.spiderMosquitos + "\n" +
															EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
															EnumChatFormatting.RED + EnumChatFormatting.BOLD + " -------------------"));
				break;
			case "zombie":
				if (showSession) {
					if (ZombieTracker.zombieTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(ZombieTracker.zombieTimeSession, timeNow);
					}
					if (ZombieTracker.zombieBossesSession == -1) {
						bossesBetween = "Never";
					} else {
						bossesBetween = nf.format(ZombieTracker.zombieBossesSession);
					}
					if (ToggleCommand.slayerCountTotal) {
						drop20 = nf.format(ZombieTracker.zombieFoulFleshSession);
					} else {
						drop20 = nf.format(ZombieTracker.zombieFoulFleshDropsSession) + " times";
					}

					player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Zombie Loot Summary (Current Session):\n" +
																EnumChatFormatting.GOLD + "    Revs Killed: " + nf.format(ZombieTracker.zombieRevsSession) + "\n" +
																EnumChatFormatting.GREEN + "    Revenant Flesh: " + nf.format(ZombieTracker.zombieRevFleshSession) + "\n" +
																EnumChatFormatting.GREEN + "    Revenant Viscera: " + nf.format(ZombieTracker.zombieRevVisceraSession) + "\n" +
																EnumChatFormatting.BLUE + "    Foul Flesh: " + drop20 + "\n" +
																EnumChatFormatting.DARK_GREEN + "    Pestilence Runes: " +ZombieTracker.zombiePestilencesSession + "\n" +
																EnumChatFormatting.WHITE + "    Smite VI Books: " + ZombieTracker.zombieBooksSession + "\n" +
																EnumChatFormatting.WHITE + "    Smite VII Books: " + ZombieTracker.zombieBooksT7Session + "\n" +
																EnumChatFormatting.AQUA + "    Undead Catalysts: " + ZombieTracker.zombieUndeadCatasSession + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Beheaded Horrors: " + ZombieTracker.zombieBeheadedsSession + "\n" +
																EnumChatFormatting.RED + "    Revenant Catalysts: " + ZombieTracker.zombieRevCatasSession + "\n" +
																EnumChatFormatting.DARK_GREEN + "    Snake Runes: " + ZombieTracker.zombieSnakesSession + "\n" +
																EnumChatFormatting.GOLD + "    Scythe Blades: " + ZombieTracker.zombieScythesSession + "\n" +
																EnumChatFormatting.RED + "    Shard of the Shreddeds: " + ZombieTracker.zombieShardsSession + "\n" +
																EnumChatFormatting.RED + "    Warden Hearts: " + ZombieTracker.zombieWardenHeartsSession + "\n" +
																EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
																EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
																EnumChatFormatting.GREEN + EnumChatFormatting.BOLD + " -------------------"));
					return;
				}

				if (ZombieTracker.zombieTime == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = Utils.getTimeBetween(ZombieTracker.zombieTime, timeNow);
				}
				if (ZombieTracker.zombieBosses == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(ZombieTracker.zombieBosses);
				}
				if (ToggleCommand.slayerCountTotal) {
					drop20 = nf.format(ZombieTracker.zombieFoulFlesh);
				} else {
					drop20 = nf.format(ZombieTracker.zombieFoulFleshDrops) + " times";
				}

				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Zombie Loot Summary:\n" +
															EnumChatFormatting.GOLD + "    Revs Killed: " + nf.format(ZombieTracker.zombieRevs) + "\n" +
															EnumChatFormatting.GREEN + "    Revenant Flesh: " + nf.format(ZombieTracker.zombieRevFlesh) + "\n" +
															EnumChatFormatting.GREEN + "    Revenant Viscera: " + nf.format(ZombieTracker.zombieRevViscera) + "\n" +
															EnumChatFormatting.BLUE + "    Foul Flesh: " + drop20 + "\n" +
															EnumChatFormatting.DARK_GREEN + "    Pestilence Runes: " + ZombieTracker.zombiePestilences + "\n" +
															EnumChatFormatting.WHITE + "    Smite VI Books: " + ZombieTracker.zombieBooks + "\n" +
															EnumChatFormatting.WHITE + "    Smite VII Books: " + ZombieTracker.zombieBooksT7 + "\n" +
															EnumChatFormatting.AQUA + "    Undead Catalysts: " + ZombieTracker.zombieUndeadCatas + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Beheaded Horrors: " + ZombieTracker.zombieBeheadeds + "\n" +
															EnumChatFormatting.RED + "    Revenant Catalysts: " + ZombieTracker.zombieRevCatas + "\n" +
															EnumChatFormatting.DARK_GREEN + "    Snake Runes: " + ZombieTracker.zombieSnakes + "\n" +
															EnumChatFormatting.GOLD + "    Scythe Blades: " + ZombieTracker.zombieScythes + "\n" +
															EnumChatFormatting.RED + "    Shard of the Shreddeds: " + ZombieTracker.zombieShards + "\n" +
															EnumChatFormatting.RED + "    Warden Hearts: " + ZombieTracker.zombieWardenHearts + "\n" +
															EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
															EnumChatFormatting.GREEN + EnumChatFormatting.BOLD + " -------------------"));
				break;
			case "enderman":
				if (showSession) {
					if (EndermanTracker.endermanTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(EndermanTracker.endermanTimeSession, timeNow);
					}
					if (EndermanTracker.endermanBossesSession == -1) {
						bossesBetween = "Never";
					} else {
						bossesBetween = nf.format(EndermanTracker.endermanBossesSession);
					}
					if (ToggleCommand.slayerCountTotal) {
						drop20 = nf.format(EndermanTracker.endermanTAPSession);
					} else {
						drop20 = nf.format(EndermanTracker.endermanTAPDropsSession) + " times";
					}

					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "-------------------\n" +
							EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Enderman Loot Summary (Current Session):\n" +
							EnumChatFormatting.GOLD + "    Voidglooms Killed: " + nf.format(EndermanTracker.endermanVoidgloomsSession) + "\n" +
							EnumChatFormatting.DARK_GRAY + "    Null Spheres: " + nf.format(EndermanTracker.endermanNullSpheresSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + "    Arrow Poison: " + drop20 + "\n" +
							EnumChatFormatting.LIGHT_PURPLE + "    Endersnake Runes: " + EndermanTracker.endermanEndersnakesSession + "\n" +
							EnumChatFormatting.DARK_GREEN + "    Summoning Eyes: " + EndermanTracker.endermanSummoningEyesSession + "\n" +
							EnumChatFormatting.AQUA + "    Mana Steal Books: " + EndermanTracker.endermanManaBooksSession + "\n" +
							EnumChatFormatting.BLUE + "    Transmission Tuners: " + EndermanTracker.endermanTunersSession + "\n" +
							EnumChatFormatting.YELLOW + "    Null Atoms: " + EndermanTracker.endermanAtomsSession + "\n" +
							EnumChatFormatting.AQUA + "    Espresso Machines: " + EndermanTracker.endermanEspressoMachinesSession + "\n" +
							EnumChatFormatting.WHITE + "    Smarty Pants Books: " + EndermanTracker.endermanSmartyBooksSession + "\n" +
							EnumChatFormatting.LIGHT_PURPLE + "    End Runes: " + EndermanTracker.endermanEndRunesSession + "\n" +
							EnumChatFormatting.RED + "    Blood Chalices: " + EndermanTracker.endermanChalicesSession + "\n" +
							EnumChatFormatting.RED + "    Sinful Dice: " + EndermanTracker.endermanDiceSession + "\n" +
							EnumChatFormatting.DARK_PURPLE + "    Artifact Upgrader: " + EndermanTracker.endermanArtifactsSession + "\n" +
							EnumChatFormatting.DARK_PURPLE + "    Enderman Skins: " + EndermanTracker.endermanSkinsSession + "\n" +
							EnumChatFormatting.GRAY + "    Enchant Runes: " + EndermanTracker.endermanEnchantRunesSession + "\n" +
							EnumChatFormatting.GOLD + "    Etherwarp Mergers: " + EndermanTracker.endermanMergersSession + "\n" +
							EnumChatFormatting.GOLD + "    Judgement Cores: " + EndermanTracker.endermanCoresSession + "\n" +
							EnumChatFormatting.RED + "    Ender Slayer VII Books: " + EndermanTracker.endermanEnderBooksSession + "\n" +
							EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
							EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
							EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.BOLD + " -------------------"));
					return;
				}

				if (EndermanTracker.endermanTime == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = Utils.getTimeBetween(EndermanTracker.endermanTime, timeNow);
				}
				if (EndermanTracker.endermanBosses == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(EndermanTracker.endermanBosses);
				}
				if (ToggleCommand.slayerCountTotal) {
					drop20 = nf.format(EndermanTracker.endermanTAP);
				} else {
					drop20 = nf.format(EndermanTracker.endermanTAPDrops) + " times";
				}

				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "-------------------\n" +
						EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Enderman Loot Summary:\n" +
						EnumChatFormatting.GOLD + "    Voidglooms Killed: " + nf.format(EndermanTracker.endermanVoidglooms) + "\n" +
						EnumChatFormatting.DARK_GRAY + "    Null Spheres: " + nf.format(EndermanTracker.endermanNullSpheres) + "\n" +
						EnumChatFormatting.DARK_PURPLE + "    Arrow Poison: " + drop20 + "\n" +
						EnumChatFormatting.LIGHT_PURPLE + "    Endersnake Runes: " + EndermanTracker.endermanEndersnakes + "\n" +
						EnumChatFormatting.DARK_GREEN + "    Summoning Eyes: " + EndermanTracker.endermanSummoningEyes + "\n" +
						EnumChatFormatting.AQUA + "    Mana Steal Books: " + EndermanTracker.endermanManaBooks + "\n" +
						EnumChatFormatting.BLUE + "    Transmission Tuners: " + EndermanTracker.endermanTuners + "\n" +
						EnumChatFormatting.YELLOW + "    Null Atoms: " + EndermanTracker.endermanAtoms + "\n" +
						EnumChatFormatting.AQUA + "    Espresso Machines: " + EndermanTracker.endermanEspressoMachines + "\n" +
						EnumChatFormatting.WHITE + "    Smarty Pants Books: " + EndermanTracker.endermanSmartyBooks + "\n" +
						EnumChatFormatting.LIGHT_PURPLE + "    End Runes: " + EndermanTracker.endermanEndRunes + "\n" +
						EnumChatFormatting.RED + "    Blood Chalices: " + EndermanTracker.endermanChalices + "\n" +
						EnumChatFormatting.RED + "    Sinful Dice: " + EndermanTracker.endermanDice + "\n" +
						EnumChatFormatting.DARK_PURPLE + "    Artifact Upgrader: " + EndermanTracker.endermanArtifacts + "\n" +
						EnumChatFormatting.DARK_PURPLE + "    Enderman Skins: " + EndermanTracker.endermanSkins + "\n" +
						EnumChatFormatting.GRAY + "    Enchant Runes: " + EndermanTracker.endermanEnchantRunes + "\n" +
						EnumChatFormatting.GOLD + "    Etherwarp Mergers: " + EndermanTracker.endermanMergers + "\n" +
						EnumChatFormatting.GOLD + "    Judgement Cores: " + EndermanTracker.endermanCores + "\n" +
						EnumChatFormatting.RED + "    Ender Slayer VII Books: " + EndermanTracker.endermanEnderBooks + "\n" +
						EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
						EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
						EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.BOLD + " -------------------"));
				break;
			case "fishing":
				if (arg1.length > 1) {
					if (arg1[1].equalsIgnoreCase("winter")) {
						if (showSession) {
							if (FishingTracker.yetiTimeSession == -1) {
								timeBetween = "Never";
							} else {
								timeBetween = Utils.getTimeBetween(FishingTracker.yetiTimeSession, timeNow);
							}
							if (FishingTracker.yetiSCsSession == -1) {
								bossesBetween = "Never";
							} else {
								bossesBetween = nf.format(FishingTracker.yetiSCsSession);
							}

							player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "  Winter Fishing Summary (Current Session):\n" +
																		EnumChatFormatting.AQUA + "    Frozen Steves: " + nf.format(FishingTracker.frozenStevesSession) + "\n" +
																		EnumChatFormatting.WHITE + "    Snowmans: " + nf.format(FishingTracker.frostyTheSnowmansSession) + "\n" +
																		EnumChatFormatting.DARK_GREEN + "    Grinches: " + nf.format(FishingTracker.grinchesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Yetis: " + nf.format(FishingTracker.yetisSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Since Yeti: " + timeBetween + "\n" +
																		EnumChatFormatting.AQUA + "    Creatures Since Yeti: " + bossesBetween + "\n" +
																		EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}

						if (FishingTracker.yetiTime == -1) {
							timeBetween = "Never";
						} else {
							timeBetween = Utils.getTimeBetween(FishingTracker.yetiTime, timeNow);
						}
						if (FishingTracker.yetiSCs == -1) {
							bossesBetween = "Never";
						} else {
							bossesBetween = nf.format(FishingTracker.yetiSCs);
						}

						player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "  Winter Fishing Summary:\n" +
																	EnumChatFormatting.AQUA + "    Frozen Steves: " + nf.format(FishingTracker.frozenSteves) + "\n" +
																	EnumChatFormatting.WHITE + "    Snowmans: " + nf.format(FishingTracker.frostyTheSnowmans) + "\n" +
																	EnumChatFormatting.DARK_GREEN + "    Grinches: " + nf.format(FishingTracker.grinches) + "\n" +
																	EnumChatFormatting.GOLD + "    Yetis: " + nf.format(FishingTracker.yetis) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Since Yeti: " + timeBetween + "\n" +
																	EnumChatFormatting.AQUA + "    Creatures Since Yeti: " + bossesBetween + "\n" +
																	EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
						return;
					} else if (arg1[1].equalsIgnoreCase("festival")) {
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.DARK_BLUE + EnumChatFormatting.BOLD + " Fishing Festival Summary (Current Session):\n" +
																		EnumChatFormatting.LIGHT_PURPLE + "    Nurse Sharks: " + nf.format(FishingTracker.nurseSharksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Blue Sharks: " + nf.format(FishingTracker.blueSharksSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Tiger Sharks: " + nf.format(FishingTracker.tigerSharksSession) + "\n" +
																		EnumChatFormatting.WHITE + "    Great White Sharks: " + nf.format(FishingTracker.greatWhiteSharksSession) + "\n" +
																		EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}

						player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.DARK_BLUE + EnumChatFormatting.BOLD + " Fishing Festival Summary:\n" +
																	EnumChatFormatting.LIGHT_PURPLE + "    Nurse Sharks: " + nf.format(FishingTracker.nurseSharks) + "\n" +
																	EnumChatFormatting.BLUE + "    Blue Sharks: " + nf.format(FishingTracker.blueSharks) + "\n" +
																	EnumChatFormatting.GOLD + "    Tiger Sharks: " + nf.format(FishingTracker.tigerSharks) + "\n" +
																	EnumChatFormatting.WHITE + "    Great White Sharks: " + nf.format(FishingTracker.greatWhiteSharks) + "\n" +
																	EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " -------------------"));
					} else if (arg1[1].equalsIgnoreCase("spooky")) {
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + " Spooky Fishing Summary (Current Session):\n" +
																		EnumChatFormatting.BLUE + "    Scarecrows: " + nf.format(FishingTracker.scarecrowsSession) + "\n" +
																		EnumChatFormatting.GRAY + "    Nightmares: " + nf.format(FishingTracker.nightmaresSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Werewolves: " + nf.format(FishingTracker.werewolfsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Phantom Fishers: " + nf.format(FishingTracker.phantomFishersSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Grim Reapers: " + nf.format(FishingTracker.grimReapersSession) + "\n" +
																		EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------"));
							return;
						}

						player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + " Spooky Fishing Summary:\n" +
																	EnumChatFormatting.BLUE + "    Scarecrows: " + nf.format(FishingTracker.scarecrows) + "\n" +
																	EnumChatFormatting.GRAY + "    Nightmares: " + nf.format(FishingTracker.nightmares) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Werewolves: " + nf.format(FishingTracker.werewolfs) + "\n" +
																	EnumChatFormatting.GOLD + "    Phantom Fishers: " + nf.format(FishingTracker.phantomFishers) + "\n" +
																	EnumChatFormatting.GOLD + "    Grim Reapers: " + nf.format(FishingTracker.grimReapers) + "\n" +
																	EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------"));
					}
				}

				if (showSession) {
					if (FishingTracker.empTimeSession == -1) {
						timeBetween = "Never";
					} else {
						timeBetween = Utils.getTimeBetween(FishingTracker.empTimeSession, timeNow);
					}
					if (FishingTracker.empSCsSession == -1) {
						bossesBetween = "Never";
					} else {
						bossesBetween = nf.format(FishingTracker.empSCsSession);
					}

					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + "  Fishing Summary (Current Session):\n" +
																EnumChatFormatting.AQUA + "    Sea Creatures Caught: " + nf.format(FishingTracker.seaCreaturesSession) + "\n" +
																EnumChatFormatting.GOLD + "    Good Catches: " + nf.format(FishingTracker.goodCatchesSession) + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Great Catches: " + nf.format(FishingTracker.greatCatchesSession) + "\n\n" +
																EnumChatFormatting.GRAY + "    Squids: " + nf.format(FishingTracker.squidsSession) + "\n" +
																EnumChatFormatting.GREEN + "    Sea Walkers: " + nf.format(FishingTracker.seaWalkersSession) + "\n" +
																EnumChatFormatting.DARK_GRAY + "    Night Squids: " + nf.format(FishingTracker.nightSquidsSession) + "\n" +
																EnumChatFormatting.DARK_AQUA + "    Sea Guardians: " + nf.format(FishingTracker.seaGuardiansSession) + "\n" +
																EnumChatFormatting.BLUE + "    Sea Witches: " + nf.format(FishingTracker.seaWitchesSession) + "\n" +
																EnumChatFormatting.GREEN + "    Sea Archers: " + nf.format(FishingTracker.seaArchersSession) + "\n" +
																EnumChatFormatting.GREEN + "    Monster of the Deeps: " + nf.format(FishingTracker.monsterOfTheDeepsSession) + "\n" +
																EnumChatFormatting.YELLOW + "    Catfishes: " + nf.format(FishingTracker.catfishesSession) + "\n" +
																EnumChatFormatting.GOLD + "    Carrot Kings: " + nf.format(FishingTracker.carrotKingsSession) + "\n" +
																EnumChatFormatting.GRAY + "    Sea Leeches: " + nf.format(FishingTracker.seaLeechesSession) + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Guardian Defenders: " + nf.format(FishingTracker.guardianDefendersSession) + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Deep Sea Protectors: " + nf.format(FishingTracker.deepSeaProtectorsSession) + "\n" +
																EnumChatFormatting.GOLD + "    Hydras: " + nf.format(FishingTracker.hydrasSession) + "\n" +
																EnumChatFormatting.GOLD + "    Sea Emperors: " + nf.format(FishingTracker.seaEmperorsSession) + "\n" +
																EnumChatFormatting.AQUA + "    Time Since Sea Emperor: " + timeBetween + "\n" +
																EnumChatFormatting.AQUA + "    Sea Creatures Since Sea Emperor: " + bossesBetween + "\n" +
																EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + " -------------------"));
					return;
				}

				if (FishingTracker.empTime == -1) {
					timeBetween = "Never";
				} else {
					timeBetween = Utils.getTimeBetween(FishingTracker.empTime, timeNow);
				}
				if (FishingTracker.empSCs == -1) {
					bossesBetween = "Never";
				} else {
					bossesBetween = nf.format(FishingTracker.empSCs);
				}

				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + "  Fishing Summary:\n" +
															EnumChatFormatting.AQUA + "    Sea Creatures Caught: " + nf.format(FishingTracker.seaCreatures) + "\n" +
															EnumChatFormatting.GOLD + "    Good Catches: " + nf.format(FishingTracker.goodCatches) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Great Catches: " + nf.format(FishingTracker.greatCatches) + "\n\n" +
															EnumChatFormatting.GRAY + "    Squids: " + nf.format(FishingTracker.squids) + "\n" +
															EnumChatFormatting.GREEN + "    Sea Walkers: " + nf.format(FishingTracker.seaWalkers) + "\n" +
															EnumChatFormatting.DARK_GRAY + "    Night Squids: " + nf.format(FishingTracker.nightSquids) + "\n" +
															EnumChatFormatting.DARK_AQUA + "    Sea Guardians: " + nf.format(FishingTracker.seaGuardians) + "\n" +
															EnumChatFormatting.BLUE + "    Sea Witches: " + nf.format(FishingTracker.seaWitches) + "\n" +
															EnumChatFormatting.GREEN + "    Sea Archers: " + nf.format(FishingTracker.seaArchers) + "\n" +
															EnumChatFormatting.GREEN + "    Monster of the Deeps: " + nf.format(FishingTracker.monsterOfTheDeeps) + "\n" +
															EnumChatFormatting.YELLOW + "    Catfishes: " + nf.format(FishingTracker.catfishes) + "\n" +
															EnumChatFormatting.GOLD + "    Carrot Kings: " + nf.format(FishingTracker.carrotKings) + "\n" +
															EnumChatFormatting.GRAY + "    Sea Leeches: " + nf.format(FishingTracker.seaLeeches) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Guardian Defenders: " + nf.format(FishingTracker.guardianDefenders) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Deep Sea Protectors: " + nf.format(FishingTracker.deepSeaProtectors) + "\n" +
															EnumChatFormatting.GOLD + "    Hydras: " + nf.format(FishingTracker.hydras) + "\n" +
															EnumChatFormatting.GOLD + "    Sea Emperors: " + nf.format(FishingTracker.seaEmperors) + "\n" +
															EnumChatFormatting.AQUA + "    Time Since Sea Emperor: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Sea Creatures Since Sea Emperor: " + bossesBetween + "\n" +
															EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + " -------------------"));

				break;
			case "mythological":
				if (showSession) {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + "  Mythological Event Summary (Current Session):\n" +
																EnumChatFormatting.YELLOW + "    Coins: " + Utils.getMoneySpent(MythologicalTracker.mythCoinsSession) + "\n" +
																EnumChatFormatting.WHITE + "    Griffin Feathers: " + nf.format(MythologicalTracker.griffinFeathersSession) + "\n" +
																EnumChatFormatting.GOLD + "    Crown of Greeds: " + nf.format(MythologicalTracker.crownOfGreedsSession) + "\n" +
																EnumChatFormatting.AQUA + "    Washed-up Souvenirs: " + nf.format(MythologicalTracker.washedUpSouvenirsSession) + "\n" +
																EnumChatFormatting.RED + "    Minos Hunters: " + nf.format(MythologicalTracker.minosHuntersSession) + "\n" +
																EnumChatFormatting.GRAY + "   Siamese Lynxes: " + nf.format(MythologicalTracker.siameseLynxesSession) + "\n" +
																EnumChatFormatting.RED + "   Minotaurs: " + nf.format(MythologicalTracker.minotaursSession) + "\n" +
																EnumChatFormatting.WHITE + "   Gaia Constructs: " + nf.format(MythologicalTracker.gaiaConstructsSession) + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Minos Champions: " + nf.format(MythologicalTracker.minosChampionsSession) + "\n" +
																EnumChatFormatting.GOLD + "    Minos Inquisitors: " + nf.format(MythologicalTracker.minosInquisitorsSession) + "\n" +
																EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + "-------------------"));
					return;
				}
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + "  Mythological Event Summary:\n" +
															EnumChatFormatting.YELLOW + "    Coins: " + Utils.getMoneySpent(MythologicalTracker.mythCoins) + "\n" +
															EnumChatFormatting.WHITE + "    Griffin Feathers: " + nf.format(MythologicalTracker.griffinFeathers) + "\n" +
															EnumChatFormatting.GOLD + "    Crown of Greeds: " + nf.format(MythologicalTracker.crownOfGreeds) + "\n" +
															EnumChatFormatting.AQUA + "    Washed-up Souvenirs: " + nf.format(MythologicalTracker.washedUpSouvenirs) + "\n" +
															EnumChatFormatting.RED + "    Minos Hunters: " + nf.format(MythologicalTracker.minosHunters) + "\n" +
															EnumChatFormatting.GRAY + "   Siamese Lynxes: " + nf.format(MythologicalTracker.siameseLynxes) + "\n" +
															EnumChatFormatting.RED + "   Minotaurs: " + nf.format(MythologicalTracker.minotaurs) + "\n" +
															EnumChatFormatting.WHITE + "   Gaia Constructs: " + nf.format(MythologicalTracker.gaiaConstructs) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Minos Champions: " + nf.format(MythologicalTracker.minosChampions) + "\n" +
															EnumChatFormatting.GOLD + "    Minos Inquisitors: " + nf.format(MythologicalTracker.minosInquisitors) + "\n" +
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
																		EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f1SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Bonzo's Staffs: " + nf.format(CatacombsTracker.bonzoStaffsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f1CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f1TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F1 Summary:\n" +
																	EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f1SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Bonzo's Staffs: " + nf.format(CatacombsTracker.bonzoStaffs) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f1CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f1TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f2":
					case "floor2":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F2 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f2SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Scarf's Studies: " + nf.format(CatacombsTracker.scarfStudiesSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Blades: " + nf.format(CatacombsTracker.adaptiveSwordsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f2CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f2TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F2 Summary:\n" +
																	EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f2SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Scarf's Studies: " + nf.format(CatacombsTracker.scarfStudies) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Blades: " + nf.format(CatacombsTracker.adaptiveSwords) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f2CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f2TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f3":
					case "floor3":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F3 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f3SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Helmets: " + nf.format(CatacombsTracker.adaptiveHelmsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Chestplates: " + nf.format(CatacombsTracker.adaptiveChestsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Leggings: " + nf.format(CatacombsTracker.adaptiveLegsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Boots: " + nf.format(CatacombsTracker.adaptiveBootsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f3CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f3TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F3 Summary:\n" +
																	EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f3SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Helmets: " + nf.format(CatacombsTracker.adaptiveHelms) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Chestplates: " + nf.format(CatacombsTracker.adaptiveChests) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Leggings: " + nf.format(CatacombsTracker.adaptiveLegs) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Boots: " + nf.format(CatacombsTracker.adaptiveBoots) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f3CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f3TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f4":
					case "floor4":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F4 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f4SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Spirit Wings: " + nf.format(CatacombsTracker.spiritWingsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Spirit Bones: " + nf.format(CatacombsTracker.spiritBonesSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Spirit Boots: " + nf.format(CatacombsTracker.spiritBootsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Spirit Swords: " + nf.format(CatacombsTracker.spiritSwordsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Spirit Bows: " + nf.format(CatacombsTracker.spiritBowsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Epic Spirit Pets: " + nf.format(CatacombsTracker.epicSpiritPetsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Leg Spirit Pets: " + nf.format(CatacombsTracker.legSpiritPetsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f4CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f4TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F4 Summary:\n" +
																	EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f4SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Spirit Wings: " + nf.format(CatacombsTracker.spiritWings) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Spirit Bones: " + nf.format(CatacombsTracker.spiritBones) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Spirit Boots: " + nf.format(CatacombsTracker.spiritBoots) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Spirit Swords: " + nf.format(CatacombsTracker.spiritSwords) + "\n" +
																	EnumChatFormatting.GOLD + "    Spirit Bows: " + nf.format(CatacombsTracker.spiritBows) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Epic Spirit Pets: " + nf.format(CatacombsTracker.epicSpiritPets) + "\n" +
																	EnumChatFormatting.GOLD + "    Leg Spirit Pets: " + nf.format(CatacombsTracker.legSpiritPets) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f4CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f4TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f5":
					case "floor5":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F5 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f5SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Warped Stones: " + nf.format(CatacombsTracker.warpedStonesSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Helmets: " + nf.format(CatacombsTracker.shadowAssHelmsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Chests: " + nf.format(CatacombsTracker.shadowAssChestsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Legs: " + nf.format(CatacombsTracker.shadowAssLegsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Boots: " + nf.format(CatacombsTracker.shadowAssBootsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Last Breaths: " + nf.format(CatacombsTracker.lastBreathsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Livid Daggers: " + nf.format(CatacombsTracker.lividDaggersSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Shadow Furys: " + nf.format(CatacombsTracker.shadowFurysSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f5CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f5TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F5 Summary:\n" +
																	EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f5SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Warped Stones: " + nf.format(CatacombsTracker.warpedStones) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Helmets: " + nf.format(CatacombsTracker.shadowAssHelms) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Chests: " + nf.format(CatacombsTracker.shadowAssChests) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Legs: " + nf.format(CatacombsTracker.shadowAssLegs) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Boots: " + nf.format(CatacombsTracker.shadowAssBoots) + "\n" +
																	EnumChatFormatting.GOLD + "    Last Breaths: " + nf.format(CatacombsTracker.lastBreaths) + "\n" +
																	EnumChatFormatting.GOLD + "    Livid Daggers: " + nf.format(CatacombsTracker.lividDaggers) + "\n" +
																	EnumChatFormatting.GOLD + "    Shadow Furys: " + nf.format(CatacombsTracker.shadowFurys) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f5CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f5TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f6":
					case "floor6":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F6 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f6SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Ancient Roses: " + nf.format(CatacombsTracker.ancientRosesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Precursor Eyes: " + nf.format(CatacombsTracker.precursorEyesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Giant's Swords: " + nf.format(CatacombsTracker.giantsSwordsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Lord Helmets: " + nf.format(CatacombsTracker.necroLordHelmsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Lord Chestplates: " + nf.format(CatacombsTracker.necroLordChestsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Lord Leggings: " + nf.format(CatacombsTracker.necroLordLegsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Lord Boots: " + nf.format(CatacombsTracker.necroLordBootsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necro Swords: " + nf.format(CatacombsTracker.necroSwordsSession) + "\n" +
																		EnumChatFormatting.WHITE + "    Rerolls: " + nf.format(CatacombsTracker.f6RerollsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f6CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f6TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F6 Summary:\n" +
																	EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f6SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Ancient Roses: " + nf.format(CatacombsTracker.ancientRoses) + "\n" +
																	EnumChatFormatting.GOLD + "    Precursor Eyes: " + nf.format(CatacombsTracker.precursorEyes) + "\n" +
																	EnumChatFormatting.GOLD + "    Giant's Swords: " + nf.format(CatacombsTracker.giantsSwords) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Lord Helmets: " + nf.format(CatacombsTracker.necroLordHelms) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Lord Chestplates: " + nf.format(CatacombsTracker.necroLordChests) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Lord Leggings: " + nf.format(CatacombsTracker.necroLordLegs) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Lord Boots: " + nf.format(CatacombsTracker.necroLordBoots) + "\n" +
																	EnumChatFormatting.GOLD + "    Necro Swords: " + nf.format(CatacombsTracker.necroSwords) + "\n" +
																	EnumChatFormatting.WHITE + "    Rerolls: " + nf.format(CatacombsTracker.f6Rerolls) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f6CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f6TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "f7":
					case "floor7":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F7 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f7SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Wither Bloods: " + nf.format(CatacombsTracker.witherBloodsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Wither Cloaks: " + nf.format(CatacombsTracker.witherCloaksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Implosions: " + nf.format(CatacombsTracker.implosionsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Wither Shields: " + nf.format(CatacombsTracker.witherShieldsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Warps: " + nf.format(CatacombsTracker.shadowWarpsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Necron's Handles: " + nf.format(CatacombsTracker.necronsHandlesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Auto Recombobulator: " + nf.format(CatacombsTracker.autoRecombsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Wither Helmets: " + nf.format(CatacombsTracker.witherHelmsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Wither Chesplates: " + nf.format(CatacombsTracker.witherChestsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Wither Leggings: " + nf.format(CatacombsTracker.witherLegsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Wither Boots: " + nf.format(CatacombsTracker.witherBootsSession) + "\n" +
																		EnumChatFormatting.WHITE + "    Rerolls: " + nf.format(CatacombsTracker.f7RerollsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f7CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f7TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F7 Summary:\n" +
																	EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f7SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Wither Bloods: " + nf.format(CatacombsTracker.witherBloods) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Wither Cloaks: " + nf.format(CatacombsTracker.witherCloaks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Implosions: " + nf.format(CatacombsTracker.implosions) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Wither Shields: " + nf.format(CatacombsTracker.witherShields) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Warps: " + nf.format(CatacombsTracker.shadowWarps) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Necron's Handles: " + nf.format(CatacombsTracker.necronsHandles) + "\n" +
																	EnumChatFormatting.GOLD + "    Auto Recombobulator: " + nf.format(CatacombsTracker.autoRecombs) + "\n" +
																	EnumChatFormatting.GOLD + "    Wither Helmets: " + nf.format(CatacombsTracker.witherHelms) + "\n" +
																	EnumChatFormatting.GOLD + "    Wither Chesplates: " + nf.format(CatacombsTracker.witherChests) + "\n" +
																	EnumChatFormatting.GOLD + "    Wither Leggings: " + nf.format(CatacombsTracker.witherLegs) + "\n" +
																	EnumChatFormatting.GOLD + "    Wither Boots: " + nf.format(CatacombsTracker.witherBoots) + "\n" +
																	EnumChatFormatting.WHITE + "    Rerolls: " + nf.format(CatacombsTracker.f7Rerolls) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f7CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f7TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
						break;
					case "mm":
					case "master":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs MM Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    S Runs:" + nf.format(CatacombsTracker.masterSSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Recombobulators:" + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books:" + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    1st Master Stars:" + nf.format(CatacombsTracker.firstStarsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    2nd Master Stars:" + nf.format(CatacombsTracker.secondStarsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    3rd Master Stars:" + nf.format(CatacombsTracker.thirdStarsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    4th Master Stars:" + nf.format(CatacombsTracker.fourthStarsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    5th Master Stars:" + nf.format(CatacombsTracker.fifthStarsSession) + "\n" +
																		EnumChatFormatting.WHITE + "    Rerolls:" + nf.format(CatacombsTracker.masterRerollsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent:" + nf.format(CatacombsTracker.masterCoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent:" + nf.format(CatacombsTracker.masterTimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs MM Summary:\n" +
																	EnumChatFormatting.GOLD + "    S Runs:" + nf.format(CatacombsTracker.masterS) + "\n" +
																	EnumChatFormatting.GOLD + "    Recombobulators:" + nf.format(CatacombsTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books:" + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    1st Master Stars:" + nf.format(CatacombsTracker.firstStars) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    2nd Master Stars:" + nf.format(CatacombsTracker.secondStars) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    3rd Master Stars:" + nf.format(CatacombsTracker.thirdStars) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    4th Master Stars:" + nf.format(CatacombsTracker.fourthStars) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    5th Master Stars:" + nf.format(CatacombsTracker.fifthStars) + "\n" +
																	EnumChatFormatting.WHITE + "    Rerolls:" + nf.format(CatacombsTracker.masterRerolls) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent:" + nf.format(CatacombsTracker.masterCoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent:" + nf.format(CatacombsTracker.masterTimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " -------------------"));
					default:
						player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: /loot catacombs <f1/f2/f3/f4/f5/f6/f7>"));
				}
				break;
			default:
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
		}
	}

}
