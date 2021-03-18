package me.Danker.features.loot;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlay;
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
    public void renderPlayerInfo(RenderOverlay event) {
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.wolfSvens) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(LootTracker.wolfTeeth) + "\n" +
                            EnumChatFormatting.BLUE + drop20 + "\n" +
                            EnumChatFormatting.AQUA + LootTracker.wolfSpirits + "\n" +
                            EnumChatFormatting.WHITE + LootTracker.wolfBooks + "\n" +
                            EnumChatFormatting.DARK_RED + LootTracker.wolfEggs + "\n" +
                            EnumChatFormatting.GOLD + LootTracker.wolfCoutures + "\n" +
                            EnumChatFormatting.AQUA + LootTracker.wolfBaits + "\n" +
                            EnumChatFormatting.DARK_PURPLE + LootTracker.wolfFluxes + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "wolf_session":
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.wolfSvensSession) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(LootTracker.wolfTeethSession) + "\n" +
                            EnumChatFormatting.BLUE + drop20 + "\n" +
                            EnumChatFormatting.AQUA + LootTracker.wolfSpiritsSession + "\n" +
                            EnumChatFormatting.WHITE + LootTracker.wolfBooksSession + "\n" +
                            EnumChatFormatting.DARK_RED + LootTracker.wolfEggsSession + "\n" +
                            EnumChatFormatting.GOLD + LootTracker.wolfCouturesSession + "\n" +
                            EnumChatFormatting.AQUA + LootTracker.wolfBaitsSession + "\n" +
                            EnumChatFormatting.DARK_PURPLE + LootTracker.wolfFluxesSession + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "spider":
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.spiderTarantulas) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(LootTracker.spiderWebs) + "\n" +
                            EnumChatFormatting.DARK_GREEN + drop20 + "\n" +
                            EnumChatFormatting.DARK_GRAY + LootTracker.spiderBites + "\n" +
                            EnumChatFormatting.WHITE + LootTracker.spiderBooks + "\n" +
                            EnumChatFormatting.AQUA + LootTracker.spiderCatalysts + "\n" +
                            EnumChatFormatting.DARK_PURPLE + LootTracker.spiderTalismans + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + LootTracker.spiderSwatters + "\n" +
                            EnumChatFormatting.GOLD + LootTracker.spiderMosquitos + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "spider_session":
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.spiderTarantulasSession) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(LootTracker.spiderWebsSession) + "\n" +
                            EnumChatFormatting.DARK_GREEN + drop20 + "\n" +
                            EnumChatFormatting.DARK_GRAY + LootTracker.spiderBitesSession + "\n" +
                            EnumChatFormatting.WHITE + LootTracker.spiderBooksSession + "\n" +
                            EnumChatFormatting.AQUA + LootTracker.spiderCatalystsSession + "\n" +
                            EnumChatFormatting.DARK_PURPLE + LootTracker.spiderTalismansSession + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + LootTracker.spiderSwattersSession + "\n" +
                            EnumChatFormatting.GOLD + LootTracker.spiderMosquitosSession + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "zombie":
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

                    dropsText = EnumChatFormatting.GOLD + "Revs Killed:\n" +
                            EnumChatFormatting.GREEN + "Revenant Flesh:\n" +
                            EnumChatFormatting.BLUE + "Foul Flesh:\n" +
                            EnumChatFormatting.DARK_GREEN + "Pestilence Runes:\n" +
                            EnumChatFormatting.WHITE + "Smite VI Books:\n" +
                            EnumChatFormatting.AQUA + "Undead Catalysts:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Beheaded Horrors:\n" +
                            EnumChatFormatting.RED + "Revenant Catalysts:\n" +
                            EnumChatFormatting.DARK_GREEN + "Snake Runes:\n" +
                            EnumChatFormatting.GOLD + "Scythe Blades:\n" +
                            EnumChatFormatting.RED + "Shard of Shreddeds:\n" +
                            EnumChatFormatting.RED + "Warden Hearts:\n" +
                            EnumChatFormatting.AQUA + "Time Since RNG:\n" +
                            EnumChatFormatting.AQUA + "Bosses Since RNG:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.zombieRevs) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(LootTracker.zombieRevFlesh) + "\n" +
                            EnumChatFormatting.BLUE + drop20 + "\n" +
                            EnumChatFormatting.DARK_GREEN + LootTracker.zombiePestilences + "\n" +
                            EnumChatFormatting.WHITE + LootTracker.zombieBooks + "\n" +
                            EnumChatFormatting.AQUA + LootTracker.zombieUndeadCatas + "\n" +
                            EnumChatFormatting.DARK_PURPLE + LootTracker.zombieBeheadeds + "\n" +
                            EnumChatFormatting.RED + LootTracker.zombieRevCatas + "\n" +
                            EnumChatFormatting.DARK_GREEN + LootTracker.zombieSnakes + "\n" +
                            EnumChatFormatting.GOLD + LootTracker.zombieScythes + "\n" +
                            EnumChatFormatting.RED + LootTracker.zombieShards + "\n" +
                            EnumChatFormatting.RED + LootTracker.zombieWardenHearts + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "zombie_session":
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

                    dropsText = EnumChatFormatting.GOLD + "Revs Killed:\n" +
                            EnumChatFormatting.GREEN + "Revenant Flesh:\n" +
                            EnumChatFormatting.BLUE + "Foul Flesh:\n" +
                            EnumChatFormatting.DARK_GREEN + "Pestilence Runes:\n" +
                            EnumChatFormatting.WHITE + "Smite VI Books:\n" +
                            EnumChatFormatting.AQUA + "Undead Catalysts:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Beheaded Horrors:\n" +
                            EnumChatFormatting.RED + "Revenant Catalysts:\n" +
                            EnumChatFormatting.DARK_GREEN + "Snake Runes:\n" +
                            EnumChatFormatting.GOLD + "Scythe Blades:\n" +
                            EnumChatFormatting.RED + "Shard of Shreddeds:\n" +
                            EnumChatFormatting.RED + "Warden Hearts:\n" +
                            EnumChatFormatting.AQUA + "Time Since RNG:\n" +
                            EnumChatFormatting.AQUA + "Bosses Since RNG:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.zombieRevsSession) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(LootTracker.zombieRevFleshSession) + "\n" +
                            EnumChatFormatting.BLUE + drop20 + "\n" +
                            EnumChatFormatting.DARK_GREEN + LootTracker.zombiePestilencesSession + "\n" +
                            EnumChatFormatting.WHITE + LootTracker.zombieBooksSession + "\n" +
                            EnumChatFormatting.AQUA + LootTracker.zombieUndeadCatasSession + "\n" +
                            EnumChatFormatting.DARK_PURPLE + LootTracker.zombieBeheadedsSession + "\n" +
                            EnumChatFormatting.RED + LootTracker.zombieRevCatasSession + "\n" +
                            EnumChatFormatting.DARK_GREEN + LootTracker.zombieSnakesSession + "\n" +
                            EnumChatFormatting.GOLD + LootTracker.zombieScythes + "\n" +
                            EnumChatFormatting.RED + LootTracker.zombieShardsSession + "\n" +
                            EnumChatFormatting.RED + LootTracker.zombieWardenHeartsSession + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "fishing":
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
                    countText = EnumChatFormatting.AQUA + nf.format(LootTracker.seaCreatures) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(LootTracker.fishingMilestone) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.goodCatches) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.greatCatches) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(LootTracker.squids) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(LootTracker.seaWalkers) + "\n" +
                            EnumChatFormatting.DARK_GRAY + nf.format(LootTracker.nightSquids) + "\n" +
                            EnumChatFormatting.DARK_AQUA + nf.format(LootTracker.seaGuardians) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.seaWitches) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(LootTracker.seaArchers);
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
                    countTextTwo = EnumChatFormatting.GREEN + nf.format(LootTracker.monsterOfTheDeeps) + "\n" +
                            EnumChatFormatting.YELLOW + nf.format(LootTracker.catfishes) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.carrotKings) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(LootTracker.seaLeeches) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.guardianDefenders) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.deepSeaProtectors) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.hydras) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.seaEmperors) + "\n" +
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
                    countText = EnumChatFormatting.AQUA + nf.format(LootTracker.seaCreaturesSession) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(LootTracker.fishingMilestoneSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.goodCatchesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.greatCatchesSession) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(LootTracker.squidsSession) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(LootTracker.seaWalkersSession) + "\n" +
                            EnumChatFormatting.DARK_GRAY + nf.format(LootTracker.nightSquidsSession) + "\n" +
                            EnumChatFormatting.DARK_AQUA + nf.format(LootTracker.seaGuardiansSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.seaWitchesSession) + "\n" +
                            EnumChatFormatting.GREEN + nf.format(LootTracker.seaArchersSession);
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
                    countTextTwo = EnumChatFormatting.GREEN + nf.format(LootTracker.monsterOfTheDeepsSession) + "\n" +
                            EnumChatFormatting.YELLOW + nf.format(LootTracker.catfishesSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.carrotKingsSession) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(LootTracker.seaLeechesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.guardianDefendersSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.deepSeaProtectorsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.hydrasSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.seaEmperorsSession) + "\n" +
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
                    countText = EnumChatFormatting.AQUA + nf.format(LootTracker.seaCreatures) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(LootTracker.fishingMilestone) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.goodCatches) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.greatCatches) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(LootTracker.frozenSteves) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(LootTracker.frostyTheSnowmans) + "\n" +
                            EnumChatFormatting.DARK_GREEN + nf.format(LootTracker.grinches) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.yetis) + "\n" +
                            EnumChatFormatting.AQUA + timeBetween + "\n" +
                            EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "fishing_winter_session":
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
                    countText = EnumChatFormatting.AQUA + nf.format(LootTracker.seaCreaturesSession) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(LootTracker.fishingMilestoneSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.goodCatchesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.greatCatchesSession) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(LootTracker.frozenStevesSession) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(LootTracker.frostyTheSnowmansSession) + "\n" +
                            EnumChatFormatting.DARK_GREEN + nf.format(LootTracker.grinchesSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.yetisSession) + "\n" +
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
                    countText = EnumChatFormatting.AQUA + nf.format(LootTracker.seaCreatures) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(LootTracker.fishingMilestone) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.goodCatches) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.greatCatches) + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + nf.format(LootTracker.nurseSharks) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.blueSharks) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.tigerSharks) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(LootTracker.greatWhiteSharks);
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
                    countText = EnumChatFormatting.AQUA + nf.format(LootTracker.seaCreaturesSession) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(LootTracker.fishingMilestoneSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.goodCatchesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.greatCatchesSession) + "\n" +
                            EnumChatFormatting.LIGHT_PURPLE + nf.format(LootTracker.nurseSharksSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.blueSharksSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.tigerSharksSession) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(LootTracker.greatWhiteSharksSession);
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
                    countText = EnumChatFormatting.AQUA + nf.format(LootTracker.seaCreatures) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(LootTracker.fishingMilestone) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.goodCatches) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.greatCatches) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.scarecrows) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(LootTracker.nightmares) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.werewolfs) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.phantomFishers) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.grimReapers);
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
                    countText = EnumChatFormatting.AQUA + nf.format(LootTracker.seaCreaturesSession) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(LootTracker.fishingMilestoneSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.goodCatchesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.greatCatchesSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.scarecrowsSession) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(LootTracker.nightmaresSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.werewolfsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.phantomFishersSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.grimReapersSession);
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.mythCoins) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(LootTracker.griffinFeathers) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.crownOfGreeds) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(LootTracker.washedUpSouvenirs) + "\n" +
                            EnumChatFormatting.RED + nf.format(LootTracker.minosHunters) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(LootTracker.siameseLynxes) + "\n" +
                            EnumChatFormatting.RED + nf.format(LootTracker.minotaurs) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(LootTracker.gaiaConstructs) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.minosChampions) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.minosInquisitors);
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.mythCoinsSession) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(LootTracker.griffinFeathersSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.crownOfGreedsSession) + "\n" +
                            EnumChatFormatting.AQUA + nf.format(LootTracker.washedUpSouvenirsSession) + "\n" +
                            EnumChatFormatting.RED + nf.format(LootTracker.minosHuntersSession) + "\n" +
                            EnumChatFormatting.GRAY + nf.format(LootTracker.siameseLynxesSession) + "\n" +
                            EnumChatFormatting.RED + nf.format(LootTracker.minotaursSession) + "\n" +
                            EnumChatFormatting.WHITE + nf.format(LootTracker.gaiaConstructsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.minosChampionsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.minosInquisitorsSession);
                    break;
                case "catacombs_floor_one":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.BLUE + "Bonzo's Staffs:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.bonzoStaffs) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f1CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f1TimeSpent);
                    break;
                case "catacombs_floor_one_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.BLUE + "Bonzo's Staffs:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.bonzoStaffsSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f1CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f1TimeSpentSession);
                    break;
                case "catacombs_floor_two":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.BLUE + "Scarf's Studies:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Adaptive Blades:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.scarfStudies) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.adaptiveSwords) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f2CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f2TimeSpent);
                    break;
                case "catacombs_floor_two_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                            EnumChatFormatting.BLUE + "Scarf's Studies:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Adaptive Blades:\n" +
                            EnumChatFormatting.AQUA + "Coins Spent:\n" +
                            EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.scarfStudiesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.adaptiveSwordsSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f2CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f2TimeSpentSession);
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.adaptiveHelms) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.adaptiveChests) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.adaptiveLegs) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.adaptiveBoots) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f3CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f3TimeSpent);
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.adaptiveHelmsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.adaptiveChestsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.adaptiveLegsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.adaptiveBootsSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f3CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f3TimeSpentSession);
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.spiritWings) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.spiritBones) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.spiritBoots) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.spiritSwords) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.spiritBows) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.epicSpiritPets) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.legSpiritPets) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f4CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f4TimeSpent);
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.spiritWingsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.spiritBonesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.spiritBootsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.spiritSwordsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.spiritBowsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.epicSpiritPetsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.legSpiritPetsSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f4CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f4TimeSpentSession);
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.warpedStones) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.shadowAssHelms) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.shadowAssChests) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.shadowAssLegs) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.shadowAssBoots) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.lastBreaths) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.lividDaggers) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.shadowFurys) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f5CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f5TimeSpent);
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.warpedStonesSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.shadowAssHelmsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.shadowAssChestsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.shadowAssLegsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.shadowAssBootsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.lastBreathsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.lividDaggersSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.shadowFurysSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f5CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f5TimeSpentSession);
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.ancientRoses) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.precursorEyes) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.giantsSwords) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.necroLordHelms) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.necroLordChests) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.necroLordLegs) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.necroLordBoots) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.necroSwords) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f6CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f6TimeSpent);
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.ancientRosesSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.precursorEyesSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.giantsSwordsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.necroLordHelmsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.necroLordChestsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.necroLordLegsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.necroLordBootsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.necroSwordsSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f6CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f6TimeSpentSession);
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulators) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooks) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.witherBloods) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.witherCloaks) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.implosions) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.witherShields) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.shadowWarps) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.necronsHandles) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.autoRecombs) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.witherHelms) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.witherChests) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.witherLegs) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.witherBoots) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f7CoinsSpent) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f7TimeSpent);
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.recombobulatorsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.fumingPotatoBooksSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.witherBloodsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.witherCloaksSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.implosionsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.witherShieldsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.shadowWarpsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.necronsHandlesSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.autoRecombsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.witherHelmsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.witherChestsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.witherLegsSession) + "\n" +
                            EnumChatFormatting.GOLD + nf.format(LootTracker.witherBootsSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getMoneySpent(LootTracker.f7CoinsSpentSession) + "\n" +
                            EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootTracker.f7TimeSpentSession);
                    break;
                case "ghost_session":
                    dropsText = EnumChatFormatting.GOLD + "Bags of Cash:\n" +
                            EnumChatFormatting.BLUE + "Sorrows:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Ghosty Boots:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Voltas:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Plasmas:" ; // + \n
                           // EnumChatFormatting.AQUA + "Time Spent:" +
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.bagOfCashSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.sorrowSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.ghostlyBootsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.voltaSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.plasmaSession); //+ "\n" +
                         //   EnumChatFormatting.AQUA + nf.format(LootTracker.ghostsTimeSpentSession);
                    break;
                case "ghost":
                    dropsText = EnumChatFormatting.GOLD + "Bags of Cash:\n" +
                            EnumChatFormatting.BLUE + "Sorrows:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Ghosty Boots:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Voltas:\n" +
                            EnumChatFormatting.DARK_PURPLE + "Plasmas:" ; // + \n
                    // EnumChatFormatting.AQUA + "Time Spent:" +
                    countText = EnumChatFormatting.GOLD + nf.format(LootTracker.bagOfCashSession) + "\n" +
                            EnumChatFormatting.BLUE + nf.format(LootTracker.sorrowSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.ghostlyBootsSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.voltaSession) + "\n" +
                            EnumChatFormatting.DARK_PURPLE + nf.format(LootTracker.plasmaSession); //+ "\n" +
                    //   EnumChatFormatting.AQUA + nf.format(LootTracker.ghostsTimeSpentSession);
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
