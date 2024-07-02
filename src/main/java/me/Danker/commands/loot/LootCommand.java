package me.Danker.commands.loot;

import me.Danker.config.ModConfig;
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
        return "/" + getCommandName() + " <zombie/spider/wolf/enderman/blaze/vampire/fishing/catacombs/mythological> [winter/festival/spooky/ch/lava/trophy/f(1-7)/mm/session]";
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
            return getListOfStringsMatchingLastWord(args, "wolf", "spider", "zombie", "enderman", "blaze", "vampire", "fishing", "catacombs", "mythological");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("fishing")) {
            return getListOfStringsMatchingLastWord(args, "winter", "festival", "spooky", "ch", "lava", "trophy", "session");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("catacombs")) {
            return getListOfStringsMatchingLastWord(args, "f1", "floor1", "f2", "floor2", "f3", "floor3", "f4", "floor4", "f5", "floor5", "f6", "floor6", "f7", "floor7", "mm", "master");
        } else if (args.length > 1) {
            return getListOfStringsMatchingLastWord(args, "session");
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        EntityPlayer player = (EntityPlayer) arg0;
        
        if (arg1.length == 0) {
            player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: " + getCommandUsage(arg0)));
            return;
        }

        double timeNow = System.currentTimeMillis() / 1000;
        String timeBetween;
        String bossesBetween;
        String drop20;
        NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);

        boolean showSession = arg1[arg1.length - 1].equalsIgnoreCase("session");

        switch (arg1[0].toLowerCase()) {
            case "wolf":
                if (showSession) {
                    if (WolfTracker.timeSession == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(WolfTracker.timeSession, timeNow);
                    }
                    if (WolfTracker.bossesSession == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(WolfTracker.bossesSession);
                    }
                    if (LootDisplay.slayerCountTotal) {
                        drop20 = nf.format(WolfTracker.wheelsSession);
                    } else {
                        drop20 = nf.format(WolfTracker.wheelsDropsSession) + " times";
                    }

                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                                EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + "  Sven Loot Summary (Current Session):\n" +
                                                                EnumChatFormatting.GOLD + "    Svens Killed: " + nf.format(WolfTracker.svensSession) + "\n" +
                                                                EnumChatFormatting.GREEN + "    Wolf Teeth: " + nf.format(WolfTracker.teethSession) + "\n" +
                                                                EnumChatFormatting.BLUE + "    Hamster Wheels: " + drop20 + "\n" +
                                                                EnumChatFormatting.AQUA + "    Spirit Runes: " + WolfTracker.spiritsSession + "\n" +
                                                                EnumChatFormatting.WHITE + "    Critical VI Books: " + WolfTracker.booksSession + "\n" +
                                                                EnumChatFormatting.DARK_AQUA + "    Furballs: " + WolfTracker.furballsSession + "\n" +
                                                                EnumChatFormatting.DARK_RED + "    Red Claw Eggs: " + WolfTracker.eggsSession + "\n" +
                                                                EnumChatFormatting.GOLD + "    Couture Runes: " + WolfTracker.couturesSession + "\n" +
                                                                EnumChatFormatting.AQUA + "    Grizzly Baits: " + WolfTracker.baitsSession + "\n" +
                                                                EnumChatFormatting.DARK_PURPLE + "    Overfluxes: " + WolfTracker.fluxesSession + "\n" +
                                                                EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
                                                                EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
                                                                EnumChatFormatting.AQUA + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                    return;
                }

                if (WolfTracker.time == -1) {
                    timeBetween = "Never";
                } else {
                    timeBetween = Utils.getTimeBetween(WolfTracker.time, timeNow);
                }
                if (WolfTracker.bosses == -1) {
                    bossesBetween = "Never";
                } else {
                    bossesBetween = nf.format(WolfTracker.bosses);
                }
                if (LootDisplay.slayerCountTotal) {
                    drop20 = nf.format(WolfTracker.wheels);
                } else {
                    drop20 = nf.format(WolfTracker.wheelsDrops) + " times";
                }

                player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                            EnumChatFormatting.DARK_AQUA + EnumChatFormatting.BOLD + "  Sven Loot Summary:\n" +
                                                            EnumChatFormatting.GOLD + "    Svens Killed: " + nf.format(WolfTracker.svens) + "\n" +
                                                            EnumChatFormatting.GREEN + "    Wolf Teeth: " + nf.format(WolfTracker.teeth) + "\n" +
                                                            EnumChatFormatting.BLUE + "    Hamster Wheels: " + drop20 + "\n" +
                                                            EnumChatFormatting.AQUA + "    Spirit Runes: " + WolfTracker.spirits + "\n" +
                                                            EnumChatFormatting.WHITE + "    Critical VI Books: " + WolfTracker.books + "\n" +
                                                            EnumChatFormatting.DARK_AQUA + "    Furballs: " + WolfTracker.furballs + "\n" +
                                                            EnumChatFormatting.DARK_RED + "    Red Claw Eggs: " + WolfTracker.eggs + "\n" +
                                                            EnumChatFormatting.GOLD + "    Couture Runes: " + WolfTracker.coutures + "\n" +
                                                            EnumChatFormatting.AQUA + "    Grizzly Baits: " + WolfTracker.baits + "\n" +
                                                            EnumChatFormatting.DARK_PURPLE + "    Overfluxes: " + WolfTracker.fluxes + "\n" +
                                                            EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
                                                            EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
                                                            EnumChatFormatting.AQUA + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                break;
            case "spider":
                if (showSession) {
                    if (SpiderTracker.timeSession == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(SpiderTracker.timeSession, timeNow);
                    }
                    if (SpiderTracker.bossesSession == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(SpiderTracker.bossesSession);
                    }
                    if (LootDisplay.slayerCountTotal) {
                        drop20 = nf.format(SpiderTracker.TAPSession);
                    } else {
                        drop20 = nf.format(SpiderTracker.TAPDropsSession) + " times";
                    }

                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                                EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Spider Loot Summary (Current Session):\n" +
                                                                EnumChatFormatting.GOLD + "    Tarantulas Killed: " + nf.format(SpiderTracker.tarantulasSession) + "\n" +
                                                                EnumChatFormatting.GREEN + "    Tarantula Webs: " + nf.format(SpiderTracker.websSession) + "\n" +
                                                                EnumChatFormatting.DARK_GREEN + "    Arrow Poison: " + drop20 + "\n" +
                                                                EnumChatFormatting.DARK_GRAY + "    Bite Runes: " + SpiderTracker.bitesSession + "\n" +
                                                                EnumChatFormatting.WHITE + "    Bane VI Books: " + SpiderTracker.booksSession + "\n" +
                                                                EnumChatFormatting.AQUA + "    Spider Catalysts: " + SpiderTracker.catalystsSession + "\n" +
                                                                EnumChatFormatting.DARK_PURPLE + "    Tarantula Talismans: " + SpiderTracker.talismansSession + "\n" +
                                                                EnumChatFormatting.LIGHT_PURPLE + "    Fly Swatters: " + SpiderTracker.swattersSession + "\n" +
                                                                EnumChatFormatting.GOLD + "    Digested Mosquitos: " + SpiderTracker.mosquitosSession + "\n" +
                                                                EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
                                                                EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
                                                                EnumChatFormatting.RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                    return;
                }

                if (SpiderTracker.time == -1) {
                    timeBetween = "Never";
                } else {
                    timeBetween = Utils.getTimeBetween(SpiderTracker.time, timeNow);
                }
                if (SpiderTracker.bosses == -1) {
                    bossesBetween = "Never";
                } else {
                    bossesBetween = nf.format(SpiderTracker.bosses);
                }
                if (LootDisplay.slayerCountTotal) {
                    drop20 = nf.format(SpiderTracker.TAP);
                } else {
                    drop20 = nf.format(SpiderTracker.TAPDrops) + " times";
                }

                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                            EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Spider Loot Summary:\n" +
                                                            EnumChatFormatting.GOLD + "    Tarantulas Killed: " + nf.format(SpiderTracker.tarantulas) + "\n" +
                                                            EnumChatFormatting.GREEN + "    Tarantula Webs: " + nf.format(SpiderTracker.webs) + "\n" +
                                                            EnumChatFormatting.DARK_GREEN + "    Arrow Poison: " + drop20 + "\n" +
                                                            EnumChatFormatting.DARK_GRAY + "    Bite Runes: " + SpiderTracker.bites + "\n" +
                                                            EnumChatFormatting.WHITE + "    Bane VI Books: " + SpiderTracker.books + "\n" +
                                                            EnumChatFormatting.AQUA + "    Spider Catalysts: " + SpiderTracker.catalysts + "\n" +
                                                            EnumChatFormatting.DARK_PURPLE + "    Tarantula Talismans: " + SpiderTracker.talismans + "\n" +
                                                            EnumChatFormatting.LIGHT_PURPLE + "    Fly Swatters: " + SpiderTracker.swatters + "\n" +
                                                            EnumChatFormatting.GOLD + "    Digested Mosquitos: " + SpiderTracker.mosquitos + "\n" +
                                                            EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
                                                            EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
                                                            EnumChatFormatting.RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                break;
            case "zombie":
                if (showSession) {
                    if (ZombieTracker.timeSession == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(ZombieTracker.timeSession, timeNow);
                    }
                    if (ZombieTracker.bossesSession == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(ZombieTracker.bossesSession);
                    }
                    if (LootDisplay.slayerCountTotal) {
                        drop20 = nf.format(ZombieTracker.foulFleshSession);
                    } else {
                        drop20 = nf.format(ZombieTracker.foulFleshDropsSession) + " times";
                    }

                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                                EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Zombie Loot Summary (Current Session):\n" +
                                                                EnumChatFormatting.GOLD + "    Revs Killed: " + nf.format(ZombieTracker.revsSession) + "\n" +
                                                                EnumChatFormatting.GREEN + "    Revenant Flesh: " + nf.format(ZombieTracker.revFleshSession) + "\n" +
                                                                EnumChatFormatting.GREEN + "    Revenant Viscera: " + nf.format(ZombieTracker.revVisceraSession) + "\n" +
                                                                EnumChatFormatting.BLUE + "    Foul Flesh: " + drop20 + "\n" +
                                                                EnumChatFormatting.DARK_GREEN + "    Pestilence Runes: " +ZombieTracker.pestilencesSession + "\n" +
                                                                EnumChatFormatting.WHITE + "    Smite VI Books: " + ZombieTracker.booksSession + "\n" +
                                                                EnumChatFormatting.WHITE + "    Smite VII Books: " + ZombieTracker.booksT7Session + "\n" +
                                                                EnumChatFormatting.AQUA + "    Undead Catalysts: " + ZombieTracker.undeadCatasSession + "\n" +
                                                                EnumChatFormatting.DARK_PURPLE + "    Beheaded Horrors: " + ZombieTracker.beheadedsSession + "\n" +
                                                                EnumChatFormatting.RED + "    Revenant Catalysts: " + ZombieTracker.revCatasSession + "\n" +
                                                                EnumChatFormatting.DARK_GREEN + "    Snake Runes: " + ZombieTracker.snakesSession + "\n" +
                                                                EnumChatFormatting.GOLD + "    Scythe Blades: " + ZombieTracker.scythesSession + "\n" +
                                                                EnumChatFormatting.RED + "    Shard of the Shreddeds: " + ZombieTracker.shardsSession + "\n" +
                                                                EnumChatFormatting.RED + "    Warden Hearts: " + ZombieTracker.wardenHeartsSession + "\n" +
                                                                EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
                                                                EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
                                                                EnumChatFormatting.GREEN + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                    return;
                }

                if (ZombieTracker.time == -1) {
                    timeBetween = "Never";
                } else {
                    timeBetween = Utils.getTimeBetween(ZombieTracker.time, timeNow);
                }
                if (ZombieTracker.bosses == -1) {
                    bossesBetween = "Never";
                } else {
                    bossesBetween = nf.format(ZombieTracker.bosses);
                }
                if (LootDisplay.slayerCountTotal) {
                    drop20 = nf.format(ZombieTracker.foulFlesh);
                } else {
                    drop20 = nf.format(ZombieTracker.foulFleshDrops) + " times";
                }

                player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                            EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Zombie Loot Summary:\n" +
                                                            EnumChatFormatting.GOLD + "    Revs Killed: " + nf.format(ZombieTracker.revs) + "\n" +
                                                            EnumChatFormatting.GREEN + "    Revenant Flesh: " + nf.format(ZombieTracker.revFlesh) + "\n" +
                                                            EnumChatFormatting.GREEN + "    Revenant Viscera: " + nf.format(ZombieTracker.revViscera) + "\n" +
                                                            EnumChatFormatting.BLUE + "    Foul Flesh: " + drop20 + "\n" +
                                                            EnumChatFormatting.DARK_GREEN + "    Pestilence Runes: " + ZombieTracker.pestilences + "\n" +
                                                            EnumChatFormatting.WHITE + "    Smite VI Books: " + ZombieTracker.books + "\n" +
                                                            EnumChatFormatting.WHITE + "    Smite VII Books: " + ZombieTracker.booksT7 + "\n" +
                                                            EnumChatFormatting.AQUA + "    Undead Catalysts: " + ZombieTracker.undeadCatas + "\n" +
                                                            EnumChatFormatting.DARK_PURPLE + "    Beheaded Horrors: " + ZombieTracker.beheadeds + "\n" +
                                                            EnumChatFormatting.RED + "    Revenant Catalysts: " + ZombieTracker.revCatas + "\n" +
                                                            EnumChatFormatting.DARK_GREEN + "    Snake Runes: " + ZombieTracker.snakes + "\n" +
                                                            EnumChatFormatting.GOLD + "    Scythe Blades: " + ZombieTracker.scythes + "\n" +
                                                            EnumChatFormatting.RED + "    Shard of the Shreddeds: " + ZombieTracker.shards + "\n" +
                                                            EnumChatFormatting.RED + "    Warden Hearts: " + ZombieTracker.wardenHearts + "\n" +
                                                            EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
                                                            EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
                                                            EnumChatFormatting.GREEN + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                break;
            case "enderman":
                if (showSession) {
                    if (EndermanTracker.timeSession == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(EndermanTracker.timeSession, timeNow);
                    }
                    if (EndermanTracker.bossesSession == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(EndermanTracker.bossesSession);
                    }
                    if (LootDisplay.slayerCountTotal) {
                        drop20 = nf.format(EndermanTracker.TAPSession);
                    } else {
                        drop20 = nf.format(EndermanTracker.TAPDropsSession) + " times";
                    }

                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                            EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Enderman Loot Summary (Current Session):\n" +
                            EnumChatFormatting.GOLD + "    Voidglooms Killed: " + nf.format(EndermanTracker.voidgloomsSession) + "\n" +
                            EnumChatFormatting.DARK_GRAY + "    Null Spheres: " + nf.format(EndermanTracker.nullSpheresSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + "    Arrow Poison: " + drop20 + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "    Endersnake Runes: " + EndermanTracker.endersnakesSession + "\n" +
                            EnumChatFormatting.DARK_GREEN + "    Summoning Eyes: " + EndermanTracker.summoningEyesSession + "\n" +
                            EnumChatFormatting.AQUA + "    Mana Steal Books: " + EndermanTracker.manaBooksSession + "\n" +
                            EnumChatFormatting.BLUE + "    Transmission Tuners: " + EndermanTracker.tunersSession + "\n" +
                            EnumChatFormatting.YELLOW + "    Null Atoms: " + EndermanTracker.atomsSession + "\n" +
                            EnumChatFormatting.YELLOW + "    Hazmat Endermen: " + EndermanTracker.hazmatsSession + "\n" +
                            EnumChatFormatting.AQUA + "    Espresso Machines: " + EndermanTracker.espressoMachinesSession + "\n" +
                            EnumChatFormatting.WHITE + "    Smarty Pants Books: " + EndermanTracker.smartyBooksSession + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "    End Runes: " + EndermanTracker.endRunesSession + "\n" +
                            EnumChatFormatting.RED + "    Blood Chalices: " + EndermanTracker.chalicesSession + "\n" +
                            EnumChatFormatting.RED + "    Sinful Dice: " + EndermanTracker.diceSession + "\n" +
                            EnumChatFormatting.DARK_PURPLE + "    Artifact Upgrader: " + EndermanTracker.artifactsSession + "\n" +
                            EnumChatFormatting.DARK_PURPLE + "    Enderman Skins: " + EndermanTracker.skinsSession + "\n" +
                            EnumChatFormatting.GRAY + "    Enchant Runes: " + EndermanTracker.enchantRunesSession + "\n" +
                            EnumChatFormatting.GOLD + "    Etherwarp Mergers: " + EndermanTracker.mergersSession + "\n" +
                            EnumChatFormatting.GOLD + "    Judgement Cores: " + EndermanTracker.coresSession + "\n" +
                            EnumChatFormatting.RED + "    Ender Slayer VII Books: " + EndermanTracker.enderBooksSession + "\n" +
                            EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
                            EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                    return;
                }

                if (EndermanTracker.time == -1) {
                    timeBetween = "Never";
                } else {
                    timeBetween = Utils.getTimeBetween(EndermanTracker.time, timeNow);
                }
                if (EndermanTracker.bosses == -1) {
                    bossesBetween = "Never";
                } else {
                    bossesBetween = nf.format(EndermanTracker.bosses);
                }
                if (LootDisplay.slayerCountTotal) {
                    drop20 = nf.format(EndermanTracker.TAP);
                } else {
                    drop20 = nf.format(EndermanTracker.TAPDrops) + " times";
                }

                player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                        EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "  Enderman Loot Summary:\n" +
                        EnumChatFormatting.GOLD + "    Voidglooms Killed: " + nf.format(EndermanTracker.voidglooms) + "\n" +
                        EnumChatFormatting.DARK_GRAY + "    Null Spheres: " + nf.format(EndermanTracker.nullSpheres) + "\n" +
                        EnumChatFormatting.DARK_PURPLE + "    Arrow Poison: " + drop20 + "\n" +
                        EnumChatFormatting.LIGHT_PURPLE + "    Endersnake Runes: " + EndermanTracker.endersnakes + "\n" +
                        EnumChatFormatting.DARK_GREEN + "    Summoning Eyes: " + EndermanTracker.summoningEyes + "\n" +
                        EnumChatFormatting.AQUA + "    Mana Steal Books: " + EndermanTracker.manaBooks + "\n" +
                        EnumChatFormatting.BLUE + "    Transmission Tuners: " + EndermanTracker.tuners + "\n" +
                        EnumChatFormatting.YELLOW + "    Null Atoms: " + EndermanTracker.atoms + "\n" +
                        EnumChatFormatting.YELLOW + "    Hazmat Endermen: " + EndermanTracker.hazmats + "\n" +
                        EnumChatFormatting.AQUA + "    Espresso Machines: " + EndermanTracker.espressoMachines + "\n" +
                        EnumChatFormatting.WHITE + "    Smarty Pants Books: " + EndermanTracker.smartyBooks + "\n" +
                        EnumChatFormatting.LIGHT_PURPLE + "    End Runes: " + EndermanTracker.endRunes + "\n" +
                        EnumChatFormatting.RED + "    Blood Chalices: " + EndermanTracker.chalices + "\n" +
                        EnumChatFormatting.RED + "    Sinful Dice: " + EndermanTracker.dice + "\n" +
                        EnumChatFormatting.DARK_PURPLE + "    Artifact Upgrader: " + EndermanTracker.artifacts + "\n" +
                        EnumChatFormatting.DARK_PURPLE + "    Enderman Skins: " + EndermanTracker.skins + "\n" +
                        EnumChatFormatting.GRAY + "    Enchant Runes: " + EndermanTracker.enchantRunes + "\n" +
                        EnumChatFormatting.GOLD + "    Etherwarp Mergers: " + EndermanTracker.mergers + "\n" +
                        EnumChatFormatting.GOLD + "    Judgement Cores: " + EndermanTracker.cores + "\n" +
                        EnumChatFormatting.RED + "    Ender Slayer VII Books: " + EndermanTracker.enderBooks + "\n" +
                        EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
                        EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
                        EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                break;
            case "blaze":
                if (showSession) {
                    if (BlazeTracker.timeSession == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(BlazeTracker.timeSession, timeNow);
                    }
                    if (BlazeTracker.bossesSession == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(BlazeTracker.bossesSession);
                    }

                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                            EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Blaze Loot Summary (Current Session):\n" +
                            EnumChatFormatting.GOLD + "    Demonlords Killed: " + nf.format(BlazeTracker.demonlordsSession) + "\n" +
                            EnumChatFormatting.GRAY + "    Derelict Ashes: " + nf.format(BlazeTracker.derelictAshesSession) + "\n" +
                            EnumChatFormatting.YELLOW + "    Enchanted Blaze Powder: " + nf.format(BlazeTracker.blazePowderSession) + "\n" +
                            EnumChatFormatting.RED + "    Lavatear Runes: " + nf.format(BlazeTracker.lavatearRunesSession) + "\n" +
                            EnumChatFormatting.AQUA + "    Splash Potions: " + nf.format(BlazeTracker.splashPotionsSession) + "\n" +
                            EnumChatFormatting.DARK_RED + "    Magma Arrows: " + nf.format(BlazeTracker.magmaArrowsSession) + "\n" +
                            EnumChatFormatting.DARK_AQUA + "    Mana Disintegrators: " + nf.format(BlazeTracker.manaDisintegratorsSession) + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "    Scorched Books: " + nf.format(BlazeTracker.scorchedBooksSession) + "\n" +
                            EnumChatFormatting.WHITE + "    Kelvin Inverters: " + nf.format(BlazeTracker.kelvinInvertersSession) + "\n" +
                            EnumChatFormatting.BLUE + "    Blaze Rod Distillates: " + nf.format(BlazeTracker.blazeRodDistillatesSession) + "\n" +
                            EnumChatFormatting.BLUE + "    Glowstone Distillates: " + nf.format(BlazeTracker.glowstoneDistillatesSession) + "\n" +
                            EnumChatFormatting.BLUE + "    Magma Cream Distillates: " + nf.format(BlazeTracker.magmaCreamDistillatesSession) + "\n" +
                            EnumChatFormatting.BLUE + "    Nether Wart Distillates: " + nf.format(BlazeTracker.netherWartDistillatesSession) + "\n" +
                            EnumChatFormatting.BLUE + "    Gabagool Distillates: " + nf.format(BlazeTracker.gabagoolDistillatesSession) + "\n" +
                            EnumChatFormatting.RED + "    Scorched Power Crystals: " + nf.format(BlazeTracker.scorchedPowerCrystalsSession) + "\n" +
                            EnumChatFormatting.RED + "    Fire Aspect Books: " + nf.format(BlazeTracker.fireAspectBooksSession) + "\n" +
                            EnumChatFormatting.GOLD + "    Fiery Burst Runes: " + nf.format(BlazeTracker.fieryBurstRunesSession) + "\n" +
                            EnumChatFormatting.WHITE + "    Opal Gems: " + nf.format(BlazeTracker.opalGemsSession) + "\n" +
                            EnumChatFormatting.RED + "    Archfiend Dice: " + nf.format(BlazeTracker.archfiendDiceSession) + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "    Duplex Books: " + nf.format(BlazeTracker.duplexBooksSession) + "\n" +
                            EnumChatFormatting.GOLD + "    High Class Archfiend Dice: " + nf.format(BlazeTracker.highClassArchfiendDiceSession) + "\n" +
                            EnumChatFormatting.GOLD + "    Engineering Plans: " + nf.format(BlazeTracker.engineeringPlansSession) + "\n" +
                            EnumChatFormatting.GOLD + "    Subzero Inverters: " + nf.format(BlazeTracker.subzeroInvertersSession) + "\n" +
                            EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
                            EnumChatFormatting.RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                    return;
                }

                if (BlazeTracker.time == -1) {
                    timeBetween = "Never";
                } else {
                    timeBetween = Utils.getTimeBetween(BlazeTracker.time, timeNow);
                }
                if (BlazeTracker.bosses == -1) {
                    bossesBetween = "Never";
                } else {
                    bossesBetween = nf.format(BlazeTracker.bosses);
                }

                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                        EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Blaze Loot Summary:\n" +
                        EnumChatFormatting.GOLD + "    Demonlords Killed: " + nf.format(BlazeTracker.demonlords) + "\n" +
                        EnumChatFormatting.GRAY + "    Derelict Ashes: " + nf.format(BlazeTracker.derelictAshes) + "\n" +
                        EnumChatFormatting.YELLOW + "    Enchanted Blaze Powder: " + nf.format(BlazeTracker.blazePowder) + "\n" +
                        EnumChatFormatting.RED + "    Lavatear Runes: " + nf.format(BlazeTracker.lavatearRunes) + "\n" +
                        EnumChatFormatting.AQUA + "    Splash Potions: " + nf.format(BlazeTracker.splashPotions) + "\n" +
                        EnumChatFormatting.DARK_RED + "    Magma Arrows: " + nf.format(BlazeTracker.magmaArrows) + "\n" +
                        EnumChatFormatting.DARK_AQUA + "    Mana Disintegrators: " + nf.format(BlazeTracker.manaDisintegrators) + "\n" +
                        EnumChatFormatting.LIGHT_PURPLE + "    Scorched Books: " + nf.format(BlazeTracker.scorchedBooks) + "\n" +
                        EnumChatFormatting.WHITE + "    Kelvin Inverters: " + nf.format(BlazeTracker.kelvinInverters) + "\n" +
                        EnumChatFormatting.BLUE + "    Blaze Rod Distillates: " + nf.format(BlazeTracker.blazeRodDistillates) + "\n" +
                        EnumChatFormatting.BLUE + "    Glowstone Distillates: " + nf.format(BlazeTracker.glowstoneDistillates) + "\n" +
                        EnumChatFormatting.BLUE + "    Magma Cream Distillates: " + nf.format(BlazeTracker.magmaCreamDistillates) + "\n" +
                        EnumChatFormatting.BLUE + "    Nether Wart Distillates: " + nf.format(BlazeTracker.netherWartDistillates) + "\n" +
                        EnumChatFormatting.BLUE + "    Gabagool Distillates: " + nf.format(BlazeTracker.gabagoolDistillates) + "\n" +
                        EnumChatFormatting.RED + "    Scorched Power Crystals: " + nf.format(BlazeTracker.scorchedPowerCrystals) + "\n" +
                        EnumChatFormatting.RED + "    Fire Aspect Books: " + nf.format(BlazeTracker.fireAspectBooks) + "\n" +
                        EnumChatFormatting.GOLD + "    Fiery Burst Runes: " + nf.format(BlazeTracker.fieryBurstRunes) + "\n" +
                        EnumChatFormatting.WHITE + "    Opal Gems: " + nf.format(BlazeTracker.opalGems) + "\n" +
                        EnumChatFormatting.RED + "    Archfiend Dice: " + nf.format(BlazeTracker.archfiendDice) + "\n" +
                        EnumChatFormatting.LIGHT_PURPLE + "    Duplex Books: " + nf.format(BlazeTracker.duplexBooks) + "\n" +
                        EnumChatFormatting.GOLD + "    High Class Archfiend Dice: " + nf.format(BlazeTracker.highClassArchfiendDice) + "\n" +
                        EnumChatFormatting.GOLD + "    Engineering Plans: " + nf.format(BlazeTracker.engineeringPlans) + "\n" +
                        EnumChatFormatting.GOLD + "    Subzero Inverters: " + nf.format(BlazeTracker.subzeroInverters) + "\n" +
                        EnumChatFormatting.AQUA + "    Time Since RNG: " + timeBetween + "\n" +
                        EnumChatFormatting.AQUA + "    Bosses Since RNG: " + bossesBetween + "\n" +
                        EnumChatFormatting.RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                break;
            case "vampire":
                if (showSession) {
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                            EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Vampire Loot Summary (Current Session):\n" +
                            EnumChatFormatting.GOLD + "    Riftstalkers Killed: " + nf.format(VampireTracker.riftstalkersSession) + "\n" +
                            EnumChatFormatting.DARK_RED + "    Coven Seals: " + nf.format(VampireTracker.covenSealsSession) + "\n" +
                            EnumChatFormatting.DARK_AQUA + "    Quantum Bundles: " + nf.format(VampireTracker.quantumBundlesSession) + "\n" +
                            EnumChatFormatting.RED + "    Bubba Blisters: " + nf.format(VampireTracker.bubbaBlistersSession) + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "    Soultwist Runes: " + nf.format(VampireTracker.soultwistRunesSession) + "\n" +
                            EnumChatFormatting.GRAY + "    Chocolate Chips: " + nf.format(VampireTracker.chocolateChipsSession) + "\n" +
                            EnumChatFormatting.GOLD + "    Lucky Blocks: " + nf.format(VampireTracker.luckyBlocksSession) + "\n" +
                            EnumChatFormatting.RED + "    The One Bundles: " + nf.format(VampireTracker.theOneBundlesSession) + "\n" +
                            EnumChatFormatting.GREEN + "    McGrubber's Burgers: " + nf.format(VampireTracker.mcgrubbersBurgersSession) + "\n" +
                            EnumChatFormatting.WHITE + "    Vampire Parts: " + nf.format(VampireTracker.vampirePartsSession) + "\n" +
                            EnumChatFormatting.RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                    return;
                }

                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                        EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + "  Vampire Loot Summary:\n" +
                        EnumChatFormatting.GOLD + "    Riftstalkers Killed: " + nf.format(VampireTracker.riftstalkers) + "\n" +
                        EnumChatFormatting.DARK_RED + "    Coven Seals: " + nf.format(VampireTracker.covenSeals) + "\n" +
                        EnumChatFormatting.DARK_AQUA + "    Quantum Bundles: " + nf.format(VampireTracker.quantumBundles) + "\n" +
                        EnumChatFormatting.RED + "    Bubba Blisters: " + nf.format(VampireTracker.bubbaBlisters) + "\n" +
                        EnumChatFormatting.LIGHT_PURPLE + "    Soultwist Runes: " + nf.format(VampireTracker.soultwistRunes) + "\n" +
                        EnumChatFormatting.GRAY + "    Chocolate Chips: " + nf.format(VampireTracker.chocolateChips) + "\n" +
                        EnumChatFormatting.GOLD + "    Lucky Blocks: " + nf.format(VampireTracker.luckyBlocks) + "\n" +
                        EnumChatFormatting.RED + "    The One Bundles: " + nf.format(VampireTracker.theOneBundles) + "\n" +
                        EnumChatFormatting.GREEN + "    McGrubber's Burgers: " + nf.format(VampireTracker.mcgrubbersBurgers) + "\n" +
                        EnumChatFormatting.WHITE + "    Vampire Parts: " + nf.format(VampireTracker.vampireParts) + "\n" +
                        EnumChatFormatting.RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                break;
            case "fishing":
                if (arg1.length > 1) {
                    if (arg1[1].equalsIgnoreCase("winter")) {
                        if (showSession) {
                            if (FishingTracker.reindrakeTimeSession == -1) {
                                timeBetween = "Never";
                            } else {
                                timeBetween = Utils.getTimeBetween(FishingTracker.reindrakeTimeSession, timeNow);
                            }
                            if (FishingTracker.reindrakeSCsSession == -1) {
                                bossesBetween = "Never";
                            } else {
                                bossesBetween = nf.format(FishingTracker.reindrakeSCsSession);
                            }

                            player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                                        EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "  Winter Fishing Summary (Current Session):\n" +
                                                                        EnumChatFormatting.AQUA + "    Frozen Steves: " + nf.format(FishingTracker.frozenStevesSession) + "\n" +
                                                                        EnumChatFormatting.WHITE + "    Snowmans: " + nf.format(FishingTracker.frostyTheSnowmansSession) + "\n" +
                                                                        EnumChatFormatting.DARK_GREEN + "    Grinches: " + nf.format(FishingTracker.grinchesSession) + "\n" +
                                                                        EnumChatFormatting.RED + "    Nutcrackers: " + nf.format(FishingTracker.nutcrackersSession) + "\n" +
                                                                        EnumChatFormatting.GOLD + "    Yetis: " + nf.format(FishingTracker.yetisSession) + "\n" +
                                                                        EnumChatFormatting.GOLD + "    Reindrakes: " + nf.format(FishingTracker.reindrakesSession) + "\n" +
                                                                        EnumChatFormatting.AQUA + "    Time Since Reindrake: " + timeBetween + "\n" +
                                                                        EnumChatFormatting.AQUA + "    Creatures Since Reindrake: " + bossesBetween + "\n" +
                                                                        EnumChatFormatting.AQUA + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                            return;
                        }

                        if (FishingTracker.reindrakeTime == -1) {
                            timeBetween = "Never";
                        } else {
                            timeBetween = Utils.getTimeBetween(FishingTracker.reindrakeTime, timeNow);
                        }
                        if (FishingTracker.reindrakeSCs == -1) {
                            bossesBetween = "Never";
                        } else {
                            bossesBetween = nf.format(FishingTracker.reindrakeSCs);
                        }

                        player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                                    EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "  Winter Fishing Summary:\n" +
                                                                    EnumChatFormatting.AQUA + "    Frozen Steves: " + nf.format(FishingTracker.frozenSteves) + "\n" +
                                                                    EnumChatFormatting.WHITE + "    Snowmans: " + nf.format(FishingTracker.frostyTheSnowmans) + "\n" +
                                                                    EnumChatFormatting.DARK_GREEN + "    Grinches: " + nf.format(FishingTracker.grinches) + "\n" +
                                                                    EnumChatFormatting.RED + "    Nutcrackers: " + nf.format(FishingTracker.nutcrackers) + "\n" +
                                                                    EnumChatFormatting.GOLD + "    Yetis: " + nf.format(FishingTracker.yetis) + "\n" +
                                                                    EnumChatFormatting.GOLD + "    Reindrakes: " + nf.format(FishingTracker.reindrakes) + "\n" +
                                                                    EnumChatFormatting.AQUA + "    Time Since Reindrake: " + timeBetween + "\n" +
                                                                    EnumChatFormatting.AQUA + "    Creatures Since Reindrake: " + bossesBetween + "\n" +
                                                                    EnumChatFormatting.AQUA + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                        return;
                    } else if (arg1[1].equalsIgnoreCase("festival")) {
                        if (showSession) {
                            player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                                        EnumChatFormatting.DARK_BLUE + EnumChatFormatting.BOLD + " Fishing Festival Summary (Current Session):\n" +
                                                                        EnumChatFormatting.LIGHT_PURPLE + "    Nurse Sharks: " + nf.format(FishingTracker.nurseSharksSession) + "\n" +
                                                                        EnumChatFormatting.BLUE + "    Blue Sharks: " + nf.format(FishingTracker.blueSharksSession) + "\n" +
                                                                        EnumChatFormatting.GOLD + "    Tiger Sharks: " + nf.format(FishingTracker.tigerSharksSession) + "\n" +
                                                                        EnumChatFormatting.WHITE + "    Great White Sharks: " + nf.format(FishingTracker.greatWhiteSharksSession) + "\n" +
                                                                        EnumChatFormatting.AQUA + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                            return;
                        }

                        player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                                    EnumChatFormatting.DARK_BLUE + EnumChatFormatting.BOLD + " Fishing Festival Summary:\n" +
                                                                    EnumChatFormatting.LIGHT_PURPLE + "    Nurse Sharks: " + nf.format(FishingTracker.nurseSharks) + "\n" +
                                                                    EnumChatFormatting.BLUE + "    Blue Sharks: " + nf.format(FishingTracker.blueSharks) + "\n" +
                                                                    EnumChatFormatting.GOLD + "    Tiger Sharks: " + nf.format(FishingTracker.tigerSharks) + "\n" +
                                                                    EnumChatFormatting.WHITE + "    Great White Sharks: " + nf.format(FishingTracker.greatWhiteSharks) + "\n" +
                                                                    EnumChatFormatting.AQUA + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                        return;
                    } else if (arg1[1].equalsIgnoreCase("spooky")) {
                        if (showSession) {
                            player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                                        EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + " Spooky Fishing Summary (Current Session):\n" +
                                                                        EnumChatFormatting.BLUE + "    Scarecrows: " + nf.format(FishingTracker.scarecrowsSession) + "\n" +
                                                                        EnumChatFormatting.GRAY + "    Nightmares: " + nf.format(FishingTracker.nightmaresSession) + "\n" +
                                                                        EnumChatFormatting.DARK_PURPLE + "    Werewolves: " + nf.format(FishingTracker.werewolfsSession) + "\n" +
                                                                        EnumChatFormatting.GOLD + "    Phantom Fishers: " + nf.format(FishingTracker.phantomFishersSession) + "\n" +
                                                                        EnumChatFormatting.GOLD + "    Grim Reapers: " + nf.format(FishingTracker.grimReapersSession) + "\n" +
                                                                        EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                            return;
                        }

                        player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                                    EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + " Spooky Fishing Summary:\n" +
                                                                    EnumChatFormatting.BLUE + "    Scarecrows: " + nf.format(FishingTracker.scarecrows) + "\n" +
                                                                    EnumChatFormatting.GRAY + "    Nightmares: " + nf.format(FishingTracker.nightmares) + "\n" +
                                                                    EnumChatFormatting.DARK_PURPLE + "    Werewolves: " + nf.format(FishingTracker.werewolfs) + "\n" +
                                                                    EnumChatFormatting.GOLD + "    Phantom Fishers: " + nf.format(FishingTracker.phantomFishers) + "\n" +
                                                                    EnumChatFormatting.GOLD + "    Grim Reapers: " + nf.format(FishingTracker.grimReapers) + "\n" +
                                                                    EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                        return;
                    } else if (arg1[1].equalsIgnoreCase("ch")) {
                        if (showSession) {
                            player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                                        EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + " Crystal Hollows Fishing Summary (Current Session):\n" +
                                                                        EnumChatFormatting.BLUE + "    Water Worms: " + nf.format(FishingTracker.waterWormsSession) + "\n" +
                                                                        EnumChatFormatting.GREEN + "    Poisoned Water Worms: " + nf.format(FishingTracker.poisonedWaterWormsSession) + "\n" +
                                                                        EnumChatFormatting.RED + "    Flaming Worms: " + nf.format(FishingTracker.flamingWormsSession) + "\n" +
                                                                        EnumChatFormatting.LIGHT_PURPLE + "    Lava Blazes: " + nf.format(FishingTracker.lavaBlazesSession) + "\n" +
                                                                        EnumChatFormatting.LIGHT_PURPLE + "    Lava Pigmen: " + nf.format(FishingTracker.lavaPigmenSession) + "\n" +
                                                                        EnumChatFormatting.GOLD + "    Zombie Miners: " + nf.format(FishingTracker.zombieMinersSession) + "\n" +
                                                                        EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------"));

                            return;
                        }

                        player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                                                    EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + " Crystal Hollows Fishing Summary:\n" +
                                                                    EnumChatFormatting.BLUE + "    Water Worms: " + nf.format(FishingTracker.waterWorms) + "\n" +
                                                                    EnumChatFormatting.GREEN + "    Poisoned Water Worms: " + nf.format(FishingTracker.poisonedWaterWorms) + "\n" +
                                                                    EnumChatFormatting.RED + "    Flaming Worms: " + nf.format(FishingTracker.flamingWorms) + "\n" +
                                                                    EnumChatFormatting.LIGHT_PURPLE + "    Lava Blazes: " + nf.format(FishingTracker.lavaBlazes) + "\n" +
                                                                    EnumChatFormatting.LIGHT_PURPLE + "    Lava Pigmen: " + nf.format(FishingTracker.lavaPigmen) + "\n" +
                                                                    EnumChatFormatting.GOLD + "    Zombie Miners: " + nf.format(FishingTracker.zombieMiners) + "\n" +
                                                                    EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                        return;
                    } else if (arg1[1].equalsIgnoreCase("lava")) {
                        if (showSession) {
                            if (FishingTracker.jawbusTimeSession == -1) {
                                timeBetween = "Never";
                            } else {
                                timeBetween = Utils.getTimeBetween(FishingTracker.jawbusTimeSession, timeNow);
                            }
                            if (FishingTracker.jawbusSCsSession == -1) {
                                bossesBetween = "Never";
                            } else {
                                bossesBetween = nf.format(FishingTracker.jawbusSCsSession);
                            }

                            player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                    EnumChatFormatting.RED + EnumChatFormatting.BOLD + " Lava Fishing Summary (Current Session):\n" +
                                    EnumChatFormatting.BLUE + "    Plhlegblasts: " + nf.format(FishingTracker.plhlegblastsSession) + "\n" +
                                    EnumChatFormatting.DARK_RED + "    Magma Slugs: " + nf.format(FishingTracker.magmaSlugsSession) + "\n" +
                                    EnumChatFormatting.RED + "    Moogmas: " + nf.format(FishingTracker.moogmasSession) + "\n" +
                                    EnumChatFormatting.RED + "    Lava Leeches: " + nf.format(FishingTracker.lavaLeechesSession) + "\n" +
                                    EnumChatFormatting.RED + "    Pyroclastic Worms: " + nf.format(FishingTracker.pyroclasticWormsSession) + "\n" +
                                    EnumChatFormatting.DARK_RED + "    Lava Flames: " + nf.format(FishingTracker.lavaFlamesSession) + "\n" +
                                    EnumChatFormatting.RED + "    Fire Eels: " + nf.format(FishingTracker.fireEelsSession) + "\n" +
                                    EnumChatFormatting.GOLD + "    Tauruses: " + nf.format(FishingTracker.taurusesSession) + "\n" +
                                    EnumChatFormatting.LIGHT_PURPLE + "    Thunders: " + nf.format(FishingTracker.thundersSession) + "\n" +
                                    EnumChatFormatting.LIGHT_PURPLE + "    Lord Jawbuses: " + nf.format(FishingTracker.lordJawbusesSession) + "\n" +
                                    EnumChatFormatting.AQUA + "    Time Since Lord Jawbus: " + timeBetween + "\n" +
                                    EnumChatFormatting.AQUA + "    Creatures Since Lord Jawbus: " + bossesBetween + "\n" +
                                    EnumChatFormatting.AQUA + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                            return;
                        }

                        if (FishingTracker.jawbusTime == -1) {
                            timeBetween = "Never";
                        } else {
                            timeBetween = Utils.getTimeBetween(FishingTracker.jawbusTime, timeNow);
                        }
                        if (FishingTracker.jawbusSCs == -1) {
                            bossesBetween = "Never";
                        } else {
                            bossesBetween = nf.format(FishingTracker.jawbusSCs);
                        }

                        player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                EnumChatFormatting.RED + EnumChatFormatting.BOLD + " Lava Fishing Summary:\n" +
                                EnumChatFormatting.BLUE + "    Plhlegblasts: " + nf.format(FishingTracker.plhlegblasts) + "\n" +
                                EnumChatFormatting.DARK_RED + "    Magma Slugs: " + nf.format(FishingTracker.magmaSlugs) + "\n" +
                                EnumChatFormatting.RED + "    Moogmas: " + nf.format(FishingTracker.moogmas) + "\n" +
                                EnumChatFormatting.RED + "    Lava Leeches: " + nf.format(FishingTracker.lavaLeeches) + "\n" +
                                EnumChatFormatting.RED + "    Pyroclastic Worms: " + nf.format(FishingTracker.pyroclasticWorms) + "\n" +
                                EnumChatFormatting.DARK_RED + "    Lava Flames: " + nf.format(FishingTracker.lavaFlames) + "\n" +
                                EnumChatFormatting.RED + "    Fire Eels: " + nf.format(FishingTracker.fireEels) + "\n" +
                                EnumChatFormatting.GOLD + "    Tauruses: " + nf.format(FishingTracker.tauruses) + "\n" +
                                EnumChatFormatting.LIGHT_PURPLE + "    Thunders: " + nf.format(FishingTracker.thunders) + "\n" +
                                EnumChatFormatting.LIGHT_PURPLE + "    Lord Jawbuses: " + nf.format(FishingTracker.lordJawbuses) + "\n" +
                                EnumChatFormatting.AQUA + "    Time Since Lord Jawbus: " + timeBetween + "\n" +
                                EnumChatFormatting.AQUA + "    Creatures Since Lord Jawbus: " + bossesBetween + "\n" +
                                EnumChatFormatting.AQUA + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                        return;
                    } else if (arg1[1].equalsIgnoreCase("trophy")) {
                        if (showSession) {
                            player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + " Trophy Fishing Summary (Current Session):\n" +
                                    EnumChatFormatting.WHITE + "    Sulphur Skitter " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Sulphur Skitter") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Sulphur Skitter") + "\n" +
                                    EnumChatFormatting.WHITE + "    Obfuscated 1 " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Obfuscated 1") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Obfuscated 1") + "\n" +
                                    EnumChatFormatting.WHITE + "    Steaminghot Flounder " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Steaming-Hot Flounder") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Steaming-Hot Flounder") + "\n" +
                                    EnumChatFormatting.WHITE + "    Gusher " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Gusher") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Gusher") + "\n" +
                                    EnumChatFormatting.WHITE + "    Blobfish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Blobfish") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Blobfish") + "\n" +
                                    EnumChatFormatting.GREEN + "    Obfuscated 2 " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Obfuscated 2") + ")" + EnumChatFormatting.GREEN + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Obfuscated 2") + "\n" +
                                    EnumChatFormatting.GREEN + "    Slugfish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Slugfish") + ")" + EnumChatFormatting.GREEN + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Slugfish") + "\n" +
                                    EnumChatFormatting.GREEN + "    Flyfish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Flyfish") + ")" + EnumChatFormatting.GREEN + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Flyfish") + "\n" +
                                    EnumChatFormatting.BLUE + "    Obfuscated 3 " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Obfuscated 3") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Obfuscated 3") + "\n" +
                                    EnumChatFormatting.BLUE + "    Lavahorse " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Lavahorse") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Lavahorse") + "\n" +
                                    EnumChatFormatting.BLUE + "    Mana Ray " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Mana Ray") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Mana Ray") + "\n" +
                                    EnumChatFormatting.BLUE + "    Volcanic Stonefish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Volcanic Stonefish") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Volcanic Stonefish") + "\n" +
                                    EnumChatFormatting.BLUE + "    Vanille " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Vanille") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Vanille") + "\n" +
                                    EnumChatFormatting.DARK_PURPLE + "    Skeleton Fish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Skeleton Fish") + ")" + EnumChatFormatting.DARK_PURPLE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Skeleton Fish") + "\n" +
                                    EnumChatFormatting.DARK_PURPLE + "    Moldfin " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Moldfin") + ")" + EnumChatFormatting.DARK_PURPLE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Moldfin") + "\n" +
                                    EnumChatFormatting.DARK_PURPLE + "    Soul Fish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Soul Fish") + ")" + EnumChatFormatting.DARK_PURPLE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Soul Fish") + "\n" +
                                    EnumChatFormatting.DARK_PURPLE + "    Karate Fish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Karate Fish") + ")" + EnumChatFormatting.DARK_PURPLE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Karate Fish") + "\n" +
                                    EnumChatFormatting.GOLD + "    Golden Fish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fishSession, "Golden Fish") + ")" + EnumChatFormatting.GOLD + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fishSession, "Golden Fish") + "\n" +
                                    EnumChatFormatting.AQUA + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
                            return;
                        }

                        player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
                                EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + " Trophy Fishing Summary:\n" +
                                EnumChatFormatting.WHITE + "    Sulphur Skitter " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Sulphur Skitter") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Sulphur Skitter") + "\n" +
                                EnumChatFormatting.WHITE + "    Obfuscated 1 " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Obfuscated 1") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Obfuscated 1") + "\n" +
        						EnumChatFormatting.WHITE + "    Steaminghot Flounder " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Steaming-Hot Flounder") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Steaming-Hot Flounder") + "\n" +
								EnumChatFormatting.WHITE + "    Gusher " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Gusher") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Gusher") + "\n" +
								EnumChatFormatting.WHITE + "    Blobfish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Blobfish") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Blobfish") + "\n" +
								EnumChatFormatting.GREEN + "    Obfuscated 2 " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Obfuscated 2") + ")" + EnumChatFormatting.GREEN + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Obfuscated 2") + "\n" +
								EnumChatFormatting.GREEN + "    Slugfish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Slugfish") + ")" + EnumChatFormatting.GREEN + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Slugfish") + "\n" +
								EnumChatFormatting.GREEN + "    Flyfish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Flyfish") + ")" + EnumChatFormatting.GREEN + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Flyfish") + "\n" +
								EnumChatFormatting.BLUE + "    Obfuscated 3 " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Obfuscated 3") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Obfuscated 3") + "\n" +
								EnumChatFormatting.BLUE + "    Lavahorse " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Lavahorse") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Lavahorse") + "\n" +
								EnumChatFormatting.BLUE + "    Mana Ray " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Mana Ray") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Mana Ray") + "\n" +
								EnumChatFormatting.BLUE + "    Volcanic Stonefish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Volcanic Stonefish") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Volcanic Stonefish") + "\n" +
								EnumChatFormatting.BLUE + "    Vanille " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Vanille") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Vanille") + "\n" +
								EnumChatFormatting.DARK_PURPLE + "    Skeleton Fish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Skeleton Fish") + ")" + EnumChatFormatting.DARK_PURPLE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Skeleton Fish") + "\n" +
								EnumChatFormatting.DARK_PURPLE + "    Moldfin " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Moldfin") + ")" + EnumChatFormatting.DARK_PURPLE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Moldfin") + "\n" +
								EnumChatFormatting.DARK_PURPLE + "    Soul Fish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Soul Fish") + ")" + EnumChatFormatting.DARK_PURPLE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Soul Fish") + "\n" +
								EnumChatFormatting.DARK_PURPLE + "    Karate Fish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Karate Fish") + ")" + EnumChatFormatting.DARK_PURPLE + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Karate Fish") + "\n" +
								EnumChatFormatting.GOLD + "    Golden Fish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(TrophyFishTracker.fish, "Golden Fish") + ")" + EnumChatFormatting.GOLD + ": " + TrophyFishTracker.getTierCount(TrophyFishTracker.fish, "Golden Fish") + "\n" +
								EnumChatFormatting.AQUA + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
						return;
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

					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
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
																EnumChatFormatting.RED + "    Agarimoos: " + nf.format(FishingTracker.agarimoosSession) + "\n" +
																EnumChatFormatting.YELLOW + "    Catfishes: " + nf.format(FishingTracker.catfishesSession) + "\n" +
																EnumChatFormatting.GOLD + "    Carrot Kings: " + nf.format(FishingTracker.carrotKingsSession) + "\n" +
																EnumChatFormatting.GRAY + "    Sea Leeches: " + nf.format(FishingTracker.seaLeechesSession) + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Guardian Defenders: " + nf.format(FishingTracker.guardianDefendersSession) + "\n" +
																EnumChatFormatting.DARK_PURPLE + "    Deep Sea Protectors: " + nf.format(FishingTracker.deepSeaProtectorsSession) + "\n" +
																EnumChatFormatting.GOLD + "    Hydras: " + nf.format(FishingTracker.hydrasSession) + "\n" +
																EnumChatFormatting.GOLD + "    Sea Emperors: " + nf.format(FishingTracker.seaEmperorsSession) + "\n" +
																EnumChatFormatting.AQUA + "    Time Since Sea Emperor: " + timeBetween + "\n" +
																EnumChatFormatting.AQUA + "    Sea Creatures Since Sea Emperor: " + bossesBetween + "\n" +
																EnumChatFormatting.DARK_AQUA + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
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

				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
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
															EnumChatFormatting.RED + "    Agarimoos: " + nf.format(FishingTracker.agarimoos) + "\n" +
															EnumChatFormatting.YELLOW + "    Catfishes: " + nf.format(FishingTracker.catfishes) + "\n" +
															EnumChatFormatting.GOLD + "    Carrot Kings: " + nf.format(FishingTracker.carrotKings) + "\n" +
															EnumChatFormatting.GRAY + "    Sea Leeches: " + nf.format(FishingTracker.seaLeeches) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Guardian Defenders: " + nf.format(FishingTracker.guardianDefenders) + "\n" +
															EnumChatFormatting.DARK_PURPLE + "    Deep Sea Protectors: " + nf.format(FishingTracker.deepSeaProtectors) + "\n" +
															EnumChatFormatting.GOLD + "    Hydras: " + nf.format(FishingTracker.hydras) + "\n" +
															EnumChatFormatting.GOLD + "    Sea Emperors: " + nf.format(FishingTracker.seaEmperors) + "\n" +
															EnumChatFormatting.AQUA + "    Time Since Sea Emperor: " + timeBetween + "\n" +
															EnumChatFormatting.AQUA + "    Sea Creatures Since Sea Emperor: " + bossesBetween + "\n" +
															EnumChatFormatting.DARK_AQUA + EnumChatFormatting.STRIKETHROUGH + "-------------------"));

				break;
			case "mythological":
				if (showSession) {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
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
																EnumChatFormatting.GOLD + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
					return;
				}
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
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
															EnumChatFormatting.GOLD + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
				break;
			case "catacombs":
				if (arg1.length == 1) {
					player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: /loot catacombs <f1/f2/f3/f4/f5/f6/f7>"));
					return;
				}
				switch (arg1[1].toLowerCase()) {
					case "f1":
					case "floor1":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F1 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f1SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.GREEN + "    Balloon Snakes: " + nf.format(CatacombsTracker.balloonSnakesSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Bonzo's Staffs: " + nf.format(CatacombsTracker.bonzoStaffsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f1CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f1TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F1 Summary:\n" +
																	EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f1SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.GREEN + "    Balloon Snakes: " + nf.format(CatacombsTracker.balloonSnakes) + "\n" +
																	EnumChatFormatting.BLUE + "    Bonzo's Staffs: " + nf.format(CatacombsTracker.bonzoStaffs) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f1CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f1TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
						break;
					case "f2":
					case "floor2":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F2 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f2SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Scarf's Studies: " + nf.format(CatacombsTracker.scarfStudiesSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Blades: " + nf.format(CatacombsTracker.adaptiveSwordsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Adaptive Belts: " + nf.format(CatacombsTracker.adaptiveBeltsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f2CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f2TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F2 Summary:\n" +
																	EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f2SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Scarf's Studies: " + nf.format(CatacombsTracker.scarfStudies) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Blades: " + nf.format(CatacombsTracker.adaptiveSwords) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Adaptive Belts: " + nf.format(CatacombsTracker.adaptiveBelts) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f2CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f2TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
						break;
					case "f3":
					case "floor3":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
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
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
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
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
						break;
					case "f4":
					case "floor4":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
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
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
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
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
						break;
					case "f5":
					case "floor5":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F5 Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f5SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.BLUE + "    Warped Stones: " + nf.format(CatacombsTracker.warpedStonesSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Helmets: " + nf.format(CatacombsTracker.shadowAssHelmsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Chests: " + nf.format(CatacombsTracker.shadowAssChestsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Legs: " + nf.format(CatacombsTracker.shadowAssLegsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Boots: " + nf.format(CatacombsTracker.shadowAssBootsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Cloaks: " + nf.format(CatacombsTracker.shadowAssCloaksSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Last Breaths: " + nf.format(CatacombsTracker.lastBreathsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Livid Daggers: " + nf.format(CatacombsTracker.lividDaggersSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Shadow Furys: " + nf.format(CatacombsTracker.shadowFurysSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f5CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f5TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs F5 Summary:\n" +
																	EnumChatFormatting.GOLD + "    S+ Runs: " + nf.format(CatacombsTracker.f5SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Recombobulator 3000s: " + nf.format(CatacombsTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.BLUE + "    Warped Stones: " + nf.format(CatacombsTracker.warpedStones) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Helmets: " + nf.format(CatacombsTracker.shadowAssHelms) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Chests: " + nf.format(CatacombsTracker.shadowAssChests) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Legs: " + nf.format(CatacombsTracker.shadowAssLegs) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Boots: " + nf.format(CatacombsTracker.shadowAssBoots) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Shadow Assassin Cloaks: " + nf.format(CatacombsTracker.shadowAssCloaks) + "\n" +
																	EnumChatFormatting.GOLD + "    Last Breaths: " + nf.format(CatacombsTracker.lastBreaths) + "\n" +
																	EnumChatFormatting.GOLD + "    Livid Daggers: " + nf.format(CatacombsTracker.lividDaggers) + "\n" +
																	EnumChatFormatting.GOLD + "    Shadow Furys: " + nf.format(CatacombsTracker.shadowFurys) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f5CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f5TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
						break;
					case "f6":
					case "floor6":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
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
																		EnumChatFormatting.DARK_PURPLE + "    Fel Skulls: " + nf.format(CatacombsTracker.felSkullsSession) + "\n" +
																		EnumChatFormatting.WHITE + "    Rerolls: " + nf.format(CatacombsTracker.f6RerollsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f6CoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f6TimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
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
                                									EnumChatFormatting.DARK_PURPLE + "    Fel Skulls: " + nf.format(CatacombsTracker.felSkulls) + "\n" +
																	EnumChatFormatting.WHITE + "    Rerolls: " + nf.format(CatacombsTracker.f6Rerolls) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.f6CoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.f6TimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
						break;
					case "f7":
					case "floor7":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
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
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
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
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
						break;
					case "mm":
					case "master":
						if (showSession) {
							player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
																		EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs MM Summary (Current Session):\n" +
																		EnumChatFormatting.GOLD + "    Master One S Runs: " + nf.format(CatacombsTracker.m1SSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master One S+ Runs: " + nf.format(CatacombsTracker.m1SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master Two S Runs: " + nf.format(CatacombsTracker.m2SSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master Two S+ Runs: " + nf.format(CatacombsTracker.m2SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master Three S Runs: " + nf.format(CatacombsTracker.m3SSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master Three S+ Runs: " + nf.format(CatacombsTracker.m3SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master Four S Runs: " + nf.format(CatacombsTracker.m4SSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master Four S+ Runs: " + nf.format(CatacombsTracker.m4SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master Five S Runs: " + nf.format(CatacombsTracker.m5SSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master Five S+ Runs: " + nf.format(CatacombsTracker.m5SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master Six S Runs: " + nf.format(CatacombsTracker.m6SSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master Six S+ Runs: " + nf.format(CatacombsTracker.m6SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master Seven S Runs: " + nf.format(CatacombsTracker.m7SSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Master Seven S+ Runs: " + nf.format(CatacombsTracker.m7SPlusSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Recombobulators: " + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    1st Master Stars: " + nf.format(CatacombsTracker.firstStarsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    2nd Master Stars: " + nf.format(CatacombsTracker.secondStarsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    3rd Master Stars: " + nf.format(CatacombsTracker.thirdStarsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    4th Master Stars: " + nf.format(CatacombsTracker.fourthStarsSession) + "\n" +
																		EnumChatFormatting.DARK_PURPLE + "    5th Master Stars: " + nf.format(CatacombsTracker.fifthStarsSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Necron Dyes: " + nf.format(CatacombsTracker.necronDyesSession) + "\n" +
																		EnumChatFormatting.GOLD + "    Dark Claymores: " + nf.format(CatacombsTracker.darkClaymoresSession) + "\n" +
																		EnumChatFormatting.WHITE + "    Rerolls: " + nf.format(CatacombsTracker.masterRerollsSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.masterCoinsSpentSession) + "\n" +
																		EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.masterTimeSpentSession) + "\n" +
																		EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
							return;
						}
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.STRIKETHROUGH + "-------------------\n" +
																	EnumChatFormatting.RED + EnumChatFormatting.BOLD + "  Catacombs MM Summary:\n" +
																	EnumChatFormatting.GOLD + "    Master One S Runs: " + nf.format(CatacombsTracker.m1S) + "\n" +
																	EnumChatFormatting.GOLD + "    Master One S+ Runs: " + nf.format(CatacombsTracker.m1SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Master Two S Runs: " + nf.format(CatacombsTracker.m2S) + "\n" +
																	EnumChatFormatting.GOLD + "    Master Two S+ Runs: " + nf.format(CatacombsTracker.m2SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Master Three S Runs: " + nf.format(CatacombsTracker.m3S) + "\n" +
																	EnumChatFormatting.GOLD + "    Master Three S+ Runs: " + nf.format(CatacombsTracker.m3SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Master Four S Runs: " + nf.format(CatacombsTracker.m4S) + "\n" +
																	EnumChatFormatting.GOLD + "    Master Four S+ Runs: " + nf.format(CatacombsTracker.m4SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Master Five S Runs: " + nf.format(CatacombsTracker.m5S) + "\n" +
																	EnumChatFormatting.GOLD + "    Master Five S+ Runs: " + nf.format(CatacombsTracker.m5SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Master Six S Runs: " + nf.format(CatacombsTracker.m6S) + "\n" +
																	EnumChatFormatting.GOLD + "    Master Six S+ Runs: " + nf.format(CatacombsTracker.m6SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Master Seven S Runs: " + nf.format(CatacombsTracker.m7S) + "\n" +
																	EnumChatFormatting.GOLD + "    Master Seven S+ Runs: " + nf.format(CatacombsTracker.m7SPlus) + "\n" +
																	EnumChatFormatting.GOLD + "    Recombobulators: " + nf.format(CatacombsTracker.recombobulators) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    Fuming Potato Books: " + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    1st Master Stars: " + nf.format(CatacombsTracker.firstStars) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    2nd Master Stars: " + nf.format(CatacombsTracker.secondStars) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    3rd Master Stars: " + nf.format(CatacombsTracker.thirdStars) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    4th Master Stars: " + nf.format(CatacombsTracker.fourthStars) + "\n" +
																	EnumChatFormatting.DARK_PURPLE + "    5th Master Stars: " + nf.format(CatacombsTracker.fifthStars) + "\n" +
																	EnumChatFormatting.GOLD + "    Necron Dyes: " + nf.format(CatacombsTracker.necronDyes) + "\n" +
																	EnumChatFormatting.GOLD + "    Dark Claymores: " + nf.format(CatacombsTracker.darkClaymores) + "\n" +
																	EnumChatFormatting.WHITE + "    Rerolls: " + nf.format(CatacombsTracker.masterRerolls) + "\n" +
																	EnumChatFormatting.AQUA + "    Coins Spent: " + Utils.getMoneySpent(CatacombsTracker.masterCoinsSpent) + "\n" +
																	EnumChatFormatting.AQUA + "    Time Spent: " + Utils.getTimeBetween(0, CatacombsTracker.masterTimeSpent) + "\n" +
																	EnumChatFormatting.DARK_RED + EnumChatFormatting.STRIKETHROUGH + "-------------------"));
						break;
					default:
						player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: /loot catacombs <f1/f2/f3/f4/f5/f6/f7/mm>"));
				}
				break;
			default:
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: " + getCommandUsage(arg0)));
		}
	}

}
