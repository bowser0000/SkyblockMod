package me.Danker.features.loot;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlayEvent;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.NumberFormat;
import java.util.Locale;

public class LootDisplay {

    public static String display;
    public static boolean auto;

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlayEvent event) {
        if (!display.equals("off")) {
            Minecraft mc = Minecraft.getMinecraft();
            String dropsText = "";
            String countText = "";
            String dropsTextTwo;
            String countTextTwo;
            String timeBetween;
            String bossesBetween;
            String drop20;
            double timeNow = System.currentTimeMillis() / 1000;
            NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);

            switch (display) {
                case "wolf":
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

                    dropsText = EnumChatFormatting.GOLD + "Svens Killed:\n" +
                            EnumChatFormatting.GREEN + "Wolf Teeth:\n" +
                            EnumChatFormatting.BLUE + "Hamster Wheels:\n" +
                            EnumChatFormatting.AQUA + "Spirit Runes:\n" +
                            EnumChatFormatting.WHITE + "Critical VI Books:\n" +
                            EnumChatFormatting.DARK_RED + "Red Claw Eggs:\n" +
                            EnumChatFormatting.GOLD + "Couture Runes:\n" +
                            EnumChatFormatting.AQUA + "Grizzly Baits:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Overfluxes:\n" +
                            EnumChatFormatting.AQUA + "Time Since RNG:\n" +
                            EnumChatFormatting.AQUA + "Bosses Since RNG:";
                    countText = EnumChatFormatting.GOLD + nf.format(WolfTracker.wolfSvens) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(WolfTracker.wolfTeeth) + "\n" +
                            EnumChatFormatting.BLUE + drop20 + "\n" +
                            EnumChatFormatting.AQUA + WolfTracker.wolfSpirits + "\n" +
                            EnumChatFormatting.WHITE + WolfTracker.wolfBooks + "\n" +
                            EnumChatFormatting.DARK_RED + WolfTracker.wolfEggs + "\n" +
                            EnumChatFormatting.GOLD + WolfTracker.wolfCoutures + "\n" +
                            EnumChatFormatting.AQUA + WolfTracker.wolfBaits + "\n" +
                            EnumChatFormatting.DARK_PURPLE + WolfTracker.wolfFluxes + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "wolf_session":
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

                    dropsText = EnumChatFormatting.GOLD + "Svens Killed:\n" +
                            EnumChatFormatting.GREEN + "Wolf Teeth:\n" +
                            EnumChatFormatting.BLUE + "Hamster Wheels:\n" +
                            EnumChatFormatting.AQUA + "Spirit Runes:\n" +
                            EnumChatFormatting.WHITE + "Critical VI Books:\n" +
                            EnumChatFormatting.DARK_RED + "Red Claw Eggs:\n" +
                            EnumChatFormatting.GOLD + "Couture Runes:\n" +
                            EnumChatFormatting.AQUA + "Grizzly Baits:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Overfluxes:\n" +
                            EnumChatFormatting.AQUA + "Time Since RNG:\n" +
                            EnumChatFormatting.AQUA + "Bosses Since RNG:";
                    countText = EnumChatFormatting.GOLD + nf.format(WolfTracker.wolfSvensSession) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(WolfTracker.wolfTeethSession) + "\n" +
                            EnumChatFormatting.BLUE + drop20 + "\n" +
                            EnumChatFormatting.AQUA + WolfTracker.wolfSpiritsSession + "\n" +
                            EnumChatFormatting.WHITE + WolfTracker.wolfBooksSession + "\n" +
                            EnumChatFormatting.DARK_RED + WolfTracker.wolfEggsSession + "\n" +
                            EnumChatFormatting.GOLD + WolfTracker.wolfCouturesSession + "\n" +
                            EnumChatFormatting.AQUA + WolfTracker.wolfBaitsSession + "\n" +
                            EnumChatFormatting.DARK_PURPLE + WolfTracker.wolfFluxesSession + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "spider":
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

                    dropsText = EnumChatFormatting.GOLD + "Tarantulas Killed:\n" +
                            EnumChatFormatting.GREEN + "Tarantula Webs:\n" +
                            EnumChatFormatting.DARK_GREEN + "Arrow Poison:\n" +
                            EnumChatFormatting.DARK_GRAY + "Bite Runes:\n" +
                            EnumChatFormatting.WHITE + "Bane VI Books:\n" +
                            EnumChatFormatting.AQUA + "Spider Catalysts:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Tarantula Talismans:\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "Fly Swatters:\n" +
                            EnumChatFormatting.GOLD + "Digested Mosquitos:\n" +
                            EnumChatFormatting.AQUA + "Time Since RNG:\n" +
                            EnumChatFormatting.AQUA + "Bosses Since RNG:";
                    countText = EnumChatFormatting.GOLD + nf.format(SpiderTracker.spiderTarantulas) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(SpiderTracker.spiderWebs) + "\n" +
                            EnumChatFormatting.DARK_GREEN + drop20 + "\n" +
                            EnumChatFormatting.DARK_GRAY + SpiderTracker.spiderBites + "\n" +
                            EnumChatFormatting.WHITE + SpiderTracker.spiderBooks + "\n" +
                            EnumChatFormatting.AQUA + SpiderTracker.spiderCatalysts + "\n" +
                            EnumChatFormatting.DARK_PURPLE + SpiderTracker.spiderTalismans + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + SpiderTracker.spiderSwatters + "\n" +
                            EnumChatFormatting.GOLD + SpiderTracker.spiderMosquitos + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "spider_session":
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

                    dropsText = EnumChatFormatting.GOLD + "Tarantulas Killed:\n" +
                            EnumChatFormatting.GREEN + "Tarantula Webs:\n" +
                            EnumChatFormatting.DARK_GREEN + "Arrow Poison:\n" +
                            EnumChatFormatting.DARK_GRAY + "Bite Runes:\n" +
                            EnumChatFormatting.WHITE + "Bane VI Books:\n" +
                            EnumChatFormatting.AQUA + "Spider Catalysts:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Tarantula Talismans:\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "Fly Swatters:\n" +
                            EnumChatFormatting.GOLD + "Digested Mosquitos:\n" +
                            EnumChatFormatting.AQUA + "Time Since RNG:\n" +
                            EnumChatFormatting.AQUA + "Bosses Since RNG:";
                    countText = EnumChatFormatting.GOLD + nf.format(SpiderTracker.spiderTarantulasSession) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(SpiderTracker.spiderWebsSession) + "\n" +
                            EnumChatFormatting.DARK_GREEN + drop20 + "\n" +
                            EnumChatFormatting.DARK_GRAY + SpiderTracker.spiderBitesSession + "\n" +
                            EnumChatFormatting.WHITE + SpiderTracker.spiderBooksSession + "\n" +
                            EnumChatFormatting.AQUA + SpiderTracker.spiderCatalystsSession + "\n" +
                            EnumChatFormatting.DARK_PURPLE + SpiderTracker.spiderTalismansSession + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + SpiderTracker.spiderSwattersSession + "\n" +
                            EnumChatFormatting.GOLD + SpiderTracker.spiderMosquitosSession + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "zombie":
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

                    dropsText = EnumChatFormatting.GOLD + "Revs Killed:\n" +
                            EnumChatFormatting.GREEN + "Revenant Flesh:\n" +
                            EnumChatFormatting.GREEN + "Revenant Viscera:\n" +
                            EnumChatFormatting.BLUE + "Foul Flesh:\n" +
                            EnumChatFormatting.DARK_GREEN + "Pestilence Runes:\n" +
                            EnumChatFormatting.WHITE + "Smite VI Books:\n" +
                            EnumChatFormatting.WHITE + "Smite VII Books:\n" +
                            EnumChatFormatting.AQUA + "Undead Catalysts:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Beheaded Horrors:\n" +
                            EnumChatFormatting.RED + "Revenant Catalysts:\n" +
                            EnumChatFormatting.DARK_GREEN + "Snake Runes:\n" +
                            EnumChatFormatting.GOLD + "Scythe Blades:\n" +
                            EnumChatFormatting.RED + "Shard of Shreddeds:\n" +
                            EnumChatFormatting.RED + "Warden Hearts:\n" +
                            EnumChatFormatting.AQUA + "Time Since RNG:\n" +
                            EnumChatFormatting.AQUA + "Bosses Since RNG:";
                    countText = EnumChatFormatting.GOLD + nf.format(ZombieTracker.zombieRevs) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(ZombieTracker.zombieRevFlesh) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(ZombieTracker.zombieRevViscera) + "\n" +
                            EnumChatFormatting.BLUE + drop20 + "\n" +
                            EnumChatFormatting.DARK_GREEN + ZombieTracker.zombiePestilences + "\n" +
                            EnumChatFormatting.WHITE + ZombieTracker.zombieBooks + "\n" +
                            EnumChatFormatting.WHITE + ZombieTracker.zombieBooksT7 + "\n" +
                            EnumChatFormatting.AQUA + ZombieTracker.zombieUndeadCatas + "\n" +
                            EnumChatFormatting.DARK_PURPLE + ZombieTracker.zombieBeheadeds + "\n" +
                            EnumChatFormatting.RED + ZombieTracker.zombieRevCatas + "\n" +
                            EnumChatFormatting.DARK_GREEN + ZombieTracker.zombieSnakes + "\n" +
                            EnumChatFormatting.GOLD + ZombieTracker.zombieScythes + "\n" +
                            EnumChatFormatting.RED + ZombieTracker.zombieShards + "\n" +
                            EnumChatFormatting.RED + ZombieTracker.zombieWardenHearts + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "zombie_session":
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

                    dropsText = EnumChatFormatting.GOLD + "Revs Killed:\n" +
                            EnumChatFormatting.GREEN + "Revenant Flesh:\n" +
                            EnumChatFormatting.GREEN + "Revenant Viscera:\n" +
                            EnumChatFormatting.BLUE + "Foul Flesh:\n" +
                            EnumChatFormatting.DARK_GREEN + "Pestilence Runes:\n" +
                            EnumChatFormatting.WHITE + "Smite VI Books:\n" +
                            EnumChatFormatting.WHITE + "Smite VII Books:\n" +
                            EnumChatFormatting.AQUA + "Undead Catalysts:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Beheaded Horrors:\n" +
                            EnumChatFormatting.RED + "Revenant Catalysts:\n" +
                            EnumChatFormatting.DARK_GREEN + "Snake Runes:\n" +
                            EnumChatFormatting.GOLD + "Scythe Blades:\n" +
                            EnumChatFormatting.RED + "Shard of Shreddeds:\n" +
                            EnumChatFormatting.RED + "Warden Hearts:\n" +
                            EnumChatFormatting.AQUA + "Time Since RNG:\n" +
                            EnumChatFormatting.AQUA + "Bosses Since RNG:";
                    countText = EnumChatFormatting.GOLD + nf.format(ZombieTracker.zombieRevsSession) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(ZombieTracker.zombieRevFleshSession) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(ZombieTracker.zombieRevVisceraSession) + "\n" +
                            EnumChatFormatting.BLUE + drop20 + "\n" +
                            EnumChatFormatting.DARK_GREEN + ZombieTracker.zombiePestilencesSession + "\n" +
                            EnumChatFormatting.WHITE + ZombieTracker.zombieBooksSession + "\n" +
                            EnumChatFormatting.WHITE + ZombieTracker.zombieBooksT7Session + "\n" +
                            EnumChatFormatting.AQUA + ZombieTracker.zombieUndeadCatasSession + "\n" +
                            EnumChatFormatting.DARK_PURPLE + ZombieTracker.zombieBeheadedsSession + "\n" +
                            EnumChatFormatting.RED + ZombieTracker.zombieRevCatasSession + "\n" +
                            EnumChatFormatting.DARK_GREEN + ZombieTracker.zombieSnakesSession + "\n" +
                            EnumChatFormatting.GOLD + ZombieTracker.zombieScythes + "\n" +
                            EnumChatFormatting.RED + ZombieTracker.zombieShardsSession + "\n" +
                            EnumChatFormatting.RED + ZombieTracker.zombieWardenHeartsSession + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "enderman":
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
                    
                    dropsText = EnumChatFormatting.GOLD + "Voidglooms Killed:\n" +
                            EnumChatFormatting.DARK_GRAY + "Null Spheres:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Arrow Poison:\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "Endersnake Runes:\n" +
                            EnumChatFormatting.DARK_GREEN + "Summoning Eyes:\n" +
                            EnumChatFormatting.AQUA + "Mana Steal Books:\n" +
                            EnumChatFormatting.BLUE + "Transmission Tuners:\n" +
                            EnumChatFormatting.YELLOW + "Null Atoms:\n" +
                            EnumChatFormatting.AQUA + "Espresso Machines:\n" +
                            EnumChatFormatting.WHITE + "Smarty Pants Books:\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "End Runes:\n" +
                            EnumChatFormatting.RED + "Blood Chalices:\n" +
                            EnumChatFormatting.RED + "Sinful Dice:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Artifact Upgrader:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Enderman Skins:\n" +
                            EnumChatFormatting.GRAY + "Enchant Runes:\n" +
                            EnumChatFormatting.GOLD + "Etherwarp Mergers:\n" +
                            EnumChatFormatting.GOLD + "Judgement Cores:\n" +
                            EnumChatFormatting.RED + "Ender Slayer Books:\n" +
                            EnumChatFormatting.AQUA + "Time Since RNG:\n" +
                            EnumChatFormatting.AQUA + "Bosses Since RNG:";
                    countText = EnumChatFormatting.GOLD + nf.format(EndermanTracker.endermanVoidglooms) + "\n" +
                            EnumChatFormatting.DARK_GRAY + nf.format(EndermanTracker.endermanNullSpheres) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + drop20 + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + EndermanTracker.endermanEndersnakes + "\n" +
                            EnumChatFormatting.DARK_GREEN + EndermanTracker.endermanSummoningEyes + "\n" +
                            EnumChatFormatting.AQUA + EndermanTracker.endermanManaBooks + "\n" +
                            EnumChatFormatting.BLUE + EndermanTracker.endermanTuners + "\n" +
                            EnumChatFormatting.YELLOW + EndermanTracker.endermanAtoms + "\n" +
                            EnumChatFormatting.AQUA + EndermanTracker.endermanEspressoMachines + "\n" +
                            EnumChatFormatting.WHITE + EndermanTracker.endermanSmartyBooks + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + EndermanTracker.endermanEndRunes + "\n" +
                            EnumChatFormatting.RED + EndermanTracker.endermanChalices + "\n" +
                            EnumChatFormatting.RED + EndermanTracker.endermanDice + "\n" +
                            EnumChatFormatting.DARK_PURPLE + EndermanTracker.endermanArtifacts + "\n" +
                            EnumChatFormatting.DARK_PURPLE + EndermanTracker.endermanSkins + "\n" +
                            EnumChatFormatting.GRAY + EndermanTracker.endermanEnchantRunes + "\n" +
                            EnumChatFormatting.GOLD + EndermanTracker.endermanMergers + "\n" +
                            EnumChatFormatting.GOLD + EndermanTracker.endermanCores + "\n" +
                            EnumChatFormatting.RED + EndermanTracker.endermanEnderBooks + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "enderman_session":
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

                    dropsText = EnumChatFormatting.GOLD + "Voidglooms Killed:\n" +
                            EnumChatFormatting.DARK_GRAY + "Null Spheres:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Arrow Poison:\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "Endersnake Runes:\n" +
                            EnumChatFormatting.DARK_GREEN + "Summoning Eyes:\n" +
                            EnumChatFormatting.AQUA + "Mana Steal Books:\n" +
                            EnumChatFormatting.BLUE + "Transmission Tuners:\n" +
                            EnumChatFormatting.YELLOW + "Null Atoms:\n" +
                            EnumChatFormatting.AQUA + "Espresso Machines:\n" +
                            EnumChatFormatting.WHITE + "Smarty Pants Books:\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "End Runes:\n" +
                            EnumChatFormatting.RED + "Blood Chalices:\n" +
                            EnumChatFormatting.RED + "Sinful Dice:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Artifact Upgrader:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Enderman Skins:\n" +
                            EnumChatFormatting.GRAY + "Enchant Runes:\n" +
                            EnumChatFormatting.GOLD + "Etherwarp Mergers:\n" +
                            EnumChatFormatting.GOLD + "Judgement Cores:\n" +
                            EnumChatFormatting.RED + "Ender Slayer Books:\n" +
                            EnumChatFormatting.AQUA + "Time Since RNG:\n" +
                            EnumChatFormatting.AQUA + "Bosses Since RNG:";
                    countText = EnumChatFormatting.GOLD + nf.format(EndermanTracker.endermanVoidgloomsSession) + "\n" +
                            EnumChatFormatting.DARK_GRAY + nf.format(EndermanTracker.endermanNullSpheresSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + drop20 + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + EndermanTracker.endermanEndersnakesSession + "\n" +
                            EnumChatFormatting.DARK_GREEN + EndermanTracker.endermanSummoningEyesSession + "\n" +
                            EnumChatFormatting.AQUA + EndermanTracker.endermanManaBooksSession + "\n" +
                            EnumChatFormatting.BLUE + EndermanTracker.endermanTunersSession + "\n" +
                            EnumChatFormatting.YELLOW + EndermanTracker.endermanAtomsSession + "\n" +
                            EnumChatFormatting.AQUA + EndermanTracker.endermanEspressoMachinesSession + "\n" +
                            EnumChatFormatting.WHITE + EndermanTracker.endermanSmartyBooksSession + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + EndermanTracker.endermanEndRunesSession + "\n" +
                            EnumChatFormatting.RED + EndermanTracker.endermanChalicesSession + "\n" +
                            EnumChatFormatting.RED + EndermanTracker.endermanDiceSession + "\n" +
                            EnumChatFormatting.DARK_PURPLE + EndermanTracker.endermanArtifactsSession + "\n" +
                            EnumChatFormatting.DARK_PURPLE + EndermanTracker.endermanSkinsSession + "\n" +
                            EnumChatFormatting.GRAY + EndermanTracker.endermanEnchantRunesSession + "\n" +
                            EnumChatFormatting.GOLD + EndermanTracker.endermanMergersSession + "\n" +
                            EnumChatFormatting.GOLD + EndermanTracker.endermanCoresSession + "\n" +
                            EnumChatFormatting.RED + EndermanTracker.endermanEnderBooksSession + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "fishing":
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

                    dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
                            EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
                            EnumChatFormatting.GOLD + "Good Catches:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
                            EnumChatFormatting.GRAY + "Squids:\n" +
                            EnumChatFormatting.GREEN + "Sea Walkers:\n" +
                            EnumChatFormatting.DARK_GRAY + "Night Squids:\n" +
                            EnumChatFormatting.DARK_AQUA + "Sea Guardians:\n" +
                            EnumChatFormatting.BLUE + "Sea Witches:\n" +
                            EnumChatFormatting.GREEN + "Sea Archers:";
                    countText = EnumChatFormatting.AQUA + nf.format(FishingTracker.seaCreatures) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(FishingTracker.fishingMilestone) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.goodCatches) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.greatCatches) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(FishingTracker.squids) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(FishingTracker.seaWalkers) + "\n" +
                            EnumChatFormatting.DARK_GRAY + nf.format(FishingTracker.nightSquids) + "\n" +
                            EnumChatFormatting.DARK_AQUA + nf.format(FishingTracker.seaGuardians) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(FishingTracker.seaWitches) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(FishingTracker.seaArchers);
                    // Seperated to save vertical space
                    dropsTextTwo = EnumChatFormatting.GREEN + "Monster of Deeps:\n" +
                            EnumChatFormatting.YELLOW + "Catfishes:\n" +
                            EnumChatFormatting.GOLD + "Carrot Kings:\n" +
                            EnumChatFormatting.GRAY + "Sea Leeches:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Guardian Defenders:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Deep Sea Protectors:\n" +
                            EnumChatFormatting.GOLD + "Hydras:\n" +
                            EnumChatFormatting.GOLD + "Sea Emperors:\n" +
                            EnumChatFormatting.AQUA + "Time Since Emp:\n" +
                            EnumChatFormatting.AQUA + "Creatures Since Emp:";
                    countTextTwo = EnumChatFormatting.GREEN + nf.format(FishingTracker.monsterOfTheDeeps) + "\n" +
                            EnumChatFormatting.YELLOW + nf.format(FishingTracker.catfishes) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.carrotKings) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(FishingTracker.seaLeeches) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.guardianDefenders) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.deepSeaProtectors) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.hydras) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.seaEmperors) + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;

                    if (ToggleCommand.splitFishing) {
                        new TextRenderer(mc, dropsTextTwo, (int) (MoveCommand.displayXY[0] + (160 * ScaleCommand.displayScale)), MoveCommand.displayXY[1], ScaleCommand.displayScale);
                        new TextRenderer(mc, countTextTwo, (int) (MoveCommand.displayXY[0] + (270 * ScaleCommand.displayScale)), MoveCommand.displayXY[1], ScaleCommand.displayScale);
                    } else {
                        dropsText += "\n" + dropsTextTwo;
                        countText += "\n" + countTextTwo;
                    }
                    break;
                case "fishing_session":
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

                    dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
                            EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
                            EnumChatFormatting.GOLD + "Good Catches:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
                            EnumChatFormatting.GRAY + "Squids:\n" +
                            EnumChatFormatting.GREEN + "Sea Walkers:\n" +
                            EnumChatFormatting.DARK_GRAY + "Night Squids:\n" +
                            EnumChatFormatting.DARK_AQUA + "Sea Guardians:\n" +
                            EnumChatFormatting.BLUE + "Sea Witches:\n" +
                            EnumChatFormatting.GREEN + "Sea Archers:";
                    countText = EnumChatFormatting.AQUA + nf.format(FishingTracker.seaCreaturesSession) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(FishingTracker.fishingMilestoneSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.goodCatchesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.greatCatchesSession) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(FishingTracker.squidsSession) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(FishingTracker.seaWalkersSession) + "\n" +
                            EnumChatFormatting.DARK_GRAY + nf.format(FishingTracker.nightSquidsSession) + "\n" +
                            EnumChatFormatting.DARK_AQUA + nf.format(FishingTracker.seaGuardiansSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(FishingTracker.seaWitchesSession) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(FishingTracker.seaArchersSession);
                    // Seperated to save vertical space
                    dropsTextTwo = EnumChatFormatting.GREEN + "Monster of Deeps:\n" +
                            EnumChatFormatting.YELLOW + "Catfishes:\n" +
                            EnumChatFormatting.GOLD + "Carrot Kings:\n" +
                            EnumChatFormatting.GRAY + "Sea Leeches:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Guardian Defenders:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Deep Sea Protectors:\n" +
                            EnumChatFormatting.GOLD + "Hydras:\n" +
                            EnumChatFormatting.GOLD + "Sea Emperors:\n" +
                            EnumChatFormatting.AQUA + "Time Since Emp:\n" +
                            EnumChatFormatting.AQUA + "Creatures Since Emp:";
                    countTextTwo = EnumChatFormatting.GREEN + nf.format(FishingTracker.monsterOfTheDeepsSession) + "\n" +
                            EnumChatFormatting.YELLOW + nf.format(FishingTracker.catfishesSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.carrotKingsSession) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(FishingTracker.seaLeechesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.guardianDefendersSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.deepSeaProtectorsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.hydrasSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.seaEmperorsSession) + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;

                    if (ToggleCommand.splitFishing) {
                        new TextRenderer(mc, dropsTextTwo, (int) (MoveCommand.displayXY[0] + (160 * ScaleCommand.displayScale)), MoveCommand.displayXY[1], ScaleCommand.displayScale);
                        new TextRenderer(mc, countTextTwo, (int) (MoveCommand.displayXY[0] + (270 * ScaleCommand.displayScale)), MoveCommand.displayXY[1], ScaleCommand.displayScale);
                    } else {
                        dropsText += "\n" + dropsTextTwo;
                        countText += "\n" + countTextTwo;
                    }
                    break;
                case "fishing_winter":
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

                    dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
                            EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
                            EnumChatFormatting.GOLD + "Good Catches:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
                            EnumChatFormatting.AQUA + "Frozen Steves:\n" +
                            EnumChatFormatting.WHITE + "Snowmans:\n" +
                            EnumChatFormatting.DARK_GREEN + "Grinches:\n" +
                            EnumChatFormatting.GOLD + "Yetis:\n" +
                            EnumChatFormatting.AQUA + "Time Since Yeti:\n" +
                            EnumChatFormatting.AQUA + "Creatures Since Yeti:";
                    countText = EnumChatFormatting.AQUA + nf.format(FishingTracker.seaCreatures) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(FishingTracker.fishingMilestone) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.goodCatches) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.greatCatches) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(FishingTracker.frozenSteves) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(FishingTracker.frostyTheSnowmans) + "\n" +
                            EnumChatFormatting.DARK_GREEN + nf.format(FishingTracker.grinches) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.yetis) + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "fishing_winter_session":
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

                    dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
                            EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
                            EnumChatFormatting.GOLD + "Good Catches:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
                            EnumChatFormatting.AQUA + "Frozen Steves:\n" +
                            EnumChatFormatting.WHITE + "Snowmans:\n" +
                            EnumChatFormatting.DARK_GREEN + "Grinches:\n" +
                            EnumChatFormatting.GOLD + "Yetis:\n" +
                            EnumChatFormatting.AQUA + "Time Since Yeti:\n" +
                            EnumChatFormatting.AQUA + "Creatures Since Yeti:";
                    countText = EnumChatFormatting.AQUA + nf.format(FishingTracker.seaCreaturesSession) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(FishingTracker.fishingMilestoneSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.goodCatchesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.greatCatchesSession) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(FishingTracker.frozenStevesSession) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(FishingTracker.frostyTheSnowmansSession) + "\n" +
                            EnumChatFormatting.DARK_GREEN + nf.format(FishingTracker.grinchesSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.yetisSession) + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "fishing_festival":
                    dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
                            EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
                            EnumChatFormatting.GOLD + "Good Catches:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "Nurse Sharks:\n" +
                            EnumChatFormatting.BLUE + "Blue Sharks:\n" +
                            EnumChatFormatting.GOLD + "Tiger Sharks:\n" +
                            EnumChatFormatting.WHITE + "Great White Sharks:";
                    countText = EnumChatFormatting.AQUA + nf.format(FishingTracker.seaCreatures) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(FishingTracker.fishingMilestone) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.goodCatches) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.greatCatches) + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + nf.format(FishingTracker.nurseSharks) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(FishingTracker.blueSharks) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.tigerSharks) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(FishingTracker.greatWhiteSharks);
                    break;
                case "fishing_festival_session":
                    dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
                            EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
                            EnumChatFormatting.GOLD + "Good Catches:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
                            EnumChatFormatting.LIGHT_PURPLE + "Nurse Sharks:\n" +
                            EnumChatFormatting.BLUE + "Blue Sharks:\n" +
                            EnumChatFormatting.GOLD + "Tiger Sharks:\n" +
                            EnumChatFormatting.WHITE + "Great White Sharks:";
                    countText = EnumChatFormatting.AQUA + nf.format(FishingTracker.seaCreaturesSession) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(FishingTracker.fishingMilestoneSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.goodCatchesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.greatCatchesSession) + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + nf.format(FishingTracker.nurseSharksSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(FishingTracker.blueSharksSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.tigerSharksSession) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(FishingTracker.greatWhiteSharksSession);
                    break;
                case "fishing_spooky":
                    dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
                            EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
                            EnumChatFormatting.GOLD + "Good Catches:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
                            EnumChatFormatting.BLUE + "Scarecrows:\n" +
                            EnumChatFormatting.GRAY + "Nightmares:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Werewolves:\n" +
                            EnumChatFormatting.GOLD + "Phantom Fishers:\n" +
                            EnumChatFormatting.GOLD + "Grim Reapers:";
                    countText = EnumChatFormatting.AQUA + nf.format(FishingTracker.seaCreatures) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(FishingTracker.fishingMilestone) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.goodCatches) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.greatCatches) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(FishingTracker.scarecrows) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(FishingTracker.nightmares) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.werewolfs) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.phantomFishers) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.grimReapers);
                    break;
                case "fishing_spooky_session":
                    dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
                            EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
                            EnumChatFormatting.GOLD + "Good Catches:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
                            EnumChatFormatting.BLUE + "Scarecrows:\n" +
                            EnumChatFormatting.GRAY + "Nightmares:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Werewolves:\n" +
                            EnumChatFormatting.GOLD + "Phantom Fishers:\n" +
                            EnumChatFormatting.GOLD + "Grim Reapers:";
                    countText = EnumChatFormatting.AQUA + nf.format(FishingTracker.seaCreaturesSession) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(FishingTracker.fishingMilestoneSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.goodCatchesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.greatCatchesSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(FishingTracker.scarecrowsSession) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(FishingTracker.nightmaresSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(FishingTracker.werewolfsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.phantomFishersSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(FishingTracker.grimReapersSession);
                    break;
                case "mythological":
                    dropsText = EnumChatFormatting.GOLD + "Coins:\n" +
                            EnumChatFormatting.WHITE + "Griffin Feathers:\n" +
                            EnumChatFormatting.GOLD + "Crown of Greeds:\n" +
                            EnumChatFormatting.AQUA + "Washed up Souvenirs:\n" +
                            EnumChatFormatting.RED + "Minos Hunters:\n" +
                            EnumChatFormatting.GRAY + "Siamese Lynxes:\n" +
                            EnumChatFormatting.RED + "Minotaurs:\n" +
                            EnumChatFormatting.WHITE + "Gaia Constructs:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Minos Champions:\n" +
                            EnumChatFormatting.GOLD + "Minos Inquisitors:";
                    countText = EnumChatFormatting.GOLD + nf.format(MythologicalTracker.mythCoins) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(MythologicalTracker.griffinFeathers) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(MythologicalTracker.crownOfGreeds) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(MythologicalTracker.washedUpSouvenirs) + "\n" +
                            EnumChatFormatting.RED + nf.format(MythologicalTracker.minosHunters) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(MythologicalTracker.siameseLynxes) + "\n" +
                            EnumChatFormatting.RED + nf.format(MythologicalTracker.minotaurs) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(MythologicalTracker.gaiaConstructs) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(MythologicalTracker.minosChampions) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(MythologicalTracker.minosInquisitors);
                    break;
                case "mythological_session":
                    dropsText = EnumChatFormatting.GOLD + "Coins:\n" +
                            EnumChatFormatting.WHITE + "Griffin Feathers:\n" +
                            EnumChatFormatting.GOLD + "Crown of Greeds:\n" +
                            EnumChatFormatting.AQUA + "Washed up Souvenirs:\n" +
                            EnumChatFormatting.RED + "Minos Hunters:\n" +
                            EnumChatFormatting.GRAY + "Siamese Lynxes:\n" +
                            EnumChatFormatting.RED + "Minotaurs:\n" +
                            EnumChatFormatting.WHITE + "Gaia Constructs:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Minos Champions:\n" +
                            EnumChatFormatting.GOLD + "Minos Inquisitors:";
                    countText = EnumChatFormatting.GOLD + nf.format(MythologicalTracker.mythCoinsSession) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(MythologicalTracker.griffinFeathersSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(MythologicalTracker.crownOfGreedsSession) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(MythologicalTracker.washedUpSouvenirsSession) + "\n" +
                            EnumChatFormatting.RED + nf.format(MythologicalTracker.minosHuntersSession) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(MythologicalTracker.siameseLynxesSession) + "\n" +
                            EnumChatFormatting.RED + nf.format(MythologicalTracker.minotaursSession) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(MythologicalTracker.gaiaConstructsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(MythologicalTracker.minosChampionsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(MythologicalTracker.minosInquisitorsSession);
                    break;
                case "catacombs_floor_one":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.BLUE + "Bonzo's Staffs:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(CatacombsTracker.bonzoStaffs) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f1CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f1TimeSpent);
                    break;
                case "catacombs_floor_one_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.BLUE + "Bonzo's Staffs:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(CatacombsTracker.bonzoStaffsSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f1CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f1TimeSpentSession);
                    break;
                case "catacombs_floor_two":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.BLUE + "Scarf's Studies:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Adaptive Blades:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(CatacombsTracker.scarfStudies) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.adaptiveSwords) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f2CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f2TimeSpent);
                    break;
                case "catacombs_floor_two_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.BLUE + "Scarf's Studies:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Adaptive Blades:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(CatacombsTracker.scarfStudiesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.adaptiveSwordsSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f2CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f2TimeSpentSession);
                    break;
                case "catacombs_floor_three":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Adaptive Helmets:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Adaptive Chestplates:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Adaptive Leggings:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Adaptive Boots:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.adaptiveHelms) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.adaptiveChests) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.adaptiveLegs) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.adaptiveBoots) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f3CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f3TimeSpent);
                    break;
                case "catacombs_floor_three_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Adaptive Helmets:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Adaptive Chestplates:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Adaptive Leggings:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Adaptive Boots:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.adaptiveHelmsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.adaptiveChestsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.adaptiveLegsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.adaptiveBootsSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f3CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f3TimeSpentSession);
                    break;
                case "catacombs_floor_four":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Spirit Wings:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Spirit Bones:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Spirit Boots:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Spirit Swords:\n" +
                            EnumChatFormatting.GOLD + "Spirit Bows:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Epic Spirit Pets:\n" +
                            EnumChatFormatting.GOLD + "Leg Spirit Pets:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.spiritWings) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.spiritBones) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.spiritBoots) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.spiritSwords) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.spiritBows) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.epicSpiritPets) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.legSpiritPets) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f4CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f4TimeSpent);
                    break;
                case "catacombs_floor_four_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Spirit Wings:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Spirit Bones:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Spirit Boots:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Spirit Swords:\n" +
                            EnumChatFormatting.GOLD + "Spirit Bows:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Epic Spirit Pets:\n" +
                            EnumChatFormatting.GOLD + "Leg Spirit Pets:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.spiritWingsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.spiritBonesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.spiritBootsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.spiritSwordsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.spiritBowsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.epicSpiritPetsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.legSpiritPetsSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f4CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f4TimeSpentSession);
                    break;
                case "catacombs_floor_five":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.BLUE + "Warped Stones:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Shadow Helmets:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Shadow Chestplates:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Shadow Leggings:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Shadow Boots:\n" +
                            EnumChatFormatting.GOLD + "Last Breaths:\n" +
                            EnumChatFormatting.GOLD + "Livid Daggers:\n" +
                            EnumChatFormatting.GOLD + "Shadow Furys:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(CatacombsTracker.warpedStones) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.shadowAssHelms) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.shadowAssChests) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.shadowAssLegs) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.shadowAssBoots) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.lastBreaths) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.lividDaggers) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.shadowFurys) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f5CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f5TimeSpent);
                    break;
                case "catacombs_floor_five_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.BLUE + "Warped Stones:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Shadow Helmets:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Shadow Chestplates:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Shadow Leggings:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Shadow Boots:\n" +
                            EnumChatFormatting.GOLD + "Last Breaths:\n" +
                            EnumChatFormatting.GOLD + "Livid Daggers:\n" +
                            EnumChatFormatting.GOLD + "Shadow Furys:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(CatacombsTracker.warpedStonesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.shadowAssHelmsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.shadowAssChestsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.shadowAssLegsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.shadowAssBootsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.lastBreathsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.lividDaggersSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.shadowFurysSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f5CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f5TimeSpentSession);
                    break;
                case "catacombs_floor_six":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.BLUE + "Ancient Roses:\n" +
                            EnumChatFormatting.GOLD + "Precursor Eyes:\n" +
                            EnumChatFormatting.GOLD + "Giant's Swords:\n" +
                            EnumChatFormatting.GOLD + "Necro Lord Helmets:\n" +
                            EnumChatFormatting.GOLD + "Necro Lord Chests:\n" +
                            EnumChatFormatting.GOLD + "Necro Lord Leggings:\n" +
                            EnumChatFormatting.GOLD + "Necro Lord Boots:\n" +
                            EnumChatFormatting.GOLD + "Necro Swords:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(CatacombsTracker.ancientRoses) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.precursorEyes) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.giantsSwords) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.necroLordHelms) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.necroLordChests) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.necroLordLegs) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.necroLordBoots) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.necroSwords) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f6CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f6TimeSpent);
                    break;
                case "catacombs_floor_six_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.BLUE + "Ancient Roses:\n" +
                            EnumChatFormatting.GOLD + "Precursor Eyes:\n" +
                            EnumChatFormatting.GOLD + "Giant's Swords:\n" +
                            EnumChatFormatting.GOLD + "Necro Lord Helmets:\n" +
                            EnumChatFormatting.GOLD + "Necro Lord Chests:\n" +
                            EnumChatFormatting.GOLD + "Necro Lord Leggings:\n" +
                            EnumChatFormatting.GOLD + "Necro Lord Boots:\n" +
                            EnumChatFormatting.GOLD + "Necro Swords:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(CatacombsTracker.ancientRosesSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.precursorEyesSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.giantsSwordsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.necroLordHelmsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.necroLordChestsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.necroLordLegsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.necroLordBootsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.necroSwordsSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f6CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f6TimeSpentSession);
                    break;
                case "catacombs_floor_seven":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Wither Bloods:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Wither Cloaks:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Implosions:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Wither Shields:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Shadow Warps:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Necron's Handles:\n" +
                            EnumChatFormatting.GOLD + "Auto Recombobs:\n" +
                            EnumChatFormatting.GOLD + "Wither Helmets:\n" +
                            EnumChatFormatting.GOLD + "Wither Chests:\n" +
                            EnumChatFormatting.GOLD + "Wither Leggings:\n" +
                            EnumChatFormatting.GOLD + "Wither Boots:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.witherBloods) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.witherCloaks) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.implosions) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.witherShields) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.shadowWarps) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.necronsHandles) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.autoRecombs) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.witherHelms) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.witherChests) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.witherLegs) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.witherBoots) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f7CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f7TimeSpent);
                    break;
                case "catacombs_floor_seven_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Wither Bloods:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Wither Cloaks:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Implosions:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Wither Shields:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Shadow Warps:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Necron's Handles:\n" +
                            EnumChatFormatting.GOLD + "Auto Recombobulators:\n" +
                            EnumChatFormatting.GOLD + "Wither Helmets:\n" +
                            EnumChatFormatting.GOLD + "Wither Chests:\n" +
                            EnumChatFormatting.GOLD + "Wither Leggings:\n" +
                            EnumChatFormatting.GOLD + "Wither Boots:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(CatacombsTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.witherBloodsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.witherCloaksSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.implosionsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.witherShieldsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.shadowWarpsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(CatacombsTracker.necronsHandlesSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.autoRecombsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.witherHelmsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.witherChestsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.witherLegsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(CatacombsTracker.witherBootsSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(CatacombsTracker.f7CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, CatacombsTracker.f7TimeSpentSession);
                    break;
                case "ghost_session":
                    dropsText = EnumChatFormatting.GOLD + "Bags of Cash:\n" +
                            EnumChatFormatting.BLUE + "Sorrows:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Ghosty Boots:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Voltas:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Plasmas:" ; // + \n
                           // EnumChatFormatting.AQUA + "Time Spent:" +
                    countText = EnumChatFormatting.GOLD + nf.format(GhostTracker.bagOfCashSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(GhostTracker.sorrowSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(GhostTracker.ghostlyBootsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(GhostTracker.voltaSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(GhostTracker.plasmaSession); //+ "\n" +
                         //   EnumChatFormatting.AQUA + nf.format(GhostTracker.ghostsTimeSpentSession);
                    break;
                case "ghost":
                    dropsText = EnumChatFormatting.GOLD + "Bags of Cash:\n" +
                            EnumChatFormatting.BLUE + "Sorrows:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Ghosty Boots:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Voltas:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Plasmas:" ; // + \n
                    // EnumChatFormatting.AQUA + "Time Spent:" +
                    countText = EnumChatFormatting.GOLD + nf.format(GhostTracker.bagOfCashSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(GhostTracker.sorrowSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(GhostTracker.ghostlyBootsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(GhostTracker.voltaSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(GhostTracker.plasmaSession); //+ "\n" +
                    //   EnumChatFormatting.AQUA + nf.format(GhostTracker.ghostsTimeSpentSession);
                    break;


                default:
                    System.out.println("Display was an unknown value, turning off.");
                    display = "off";
                    ConfigHandler.writeStringConfig("misc", "display", "off");
            }
            new TextRenderer(mc, dropsText, MoveCommand.displayXY[0], MoveCommand.displayXY[1], ScaleCommand.displayScale);
            new TextRenderer(mc, countText, (int) (MoveCommand.displayXY[0] + (110 * ScaleCommand.displayScale)), MoveCommand.displayXY[1], ScaleCommand.displayScale);
        }
    }

}
