package me.Danker.features.loot;

import me.Danker.events.ChestSlotClickedEvent;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class CatacombsTracker {

    // Catacombs Dungeons
    public static int recombobulators;
    public static int fumingPotatoBooks;
    // F1
    public static int f1SPlus;
    public static int bonzoStaffs;
    public static double f1CoinsSpent;
    public static double f1TimeSpent;
    // F2
    public static int f2SPlus;
    public static int scarfStudies;
    public static int adaptiveSwords;
    public static double f2CoinsSpent;
    public static double f2TimeSpent;
    // F3
    public static int f3SPlus;
    public static int adaptiveHelms;
    public static int adaptiveChests;
    public static int adaptiveLegs;
    public static int adaptiveBoots;
    public static double f3CoinsSpent;
    public static double f3TimeSpent;
    // F4
    public static int f4SPlus;
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
    public static int f5SPlus;
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
    public static int f6SPlus;
    public static int ancientRoses;
    public static int precursorEyes;
    public static int giantsSwords;
    public static int necroLordHelms;
    public static int necroLordChests;
    public static int necroLordLegs;
    public static int necroLordBoots;
    public static int necroSwords;
    public static int f6Rerolls;
    public static double f6CoinsSpent;
    public static double f6TimeSpent;
    // F7
    public static int f7SPlus;
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
    public static int f7Rerolls;
    public static double f7CoinsSpent;
    public static double f7TimeSpent;

    // Catacombs Dungeons
    public static int recombobulatorsSession = 0;
    public static int fumingPotatoBooksSession = 0;
    // F1
    public static int f1SPlusSession = 0;
    public static int bonzoStaffsSession = 0;
    public static double f1CoinsSpentSession = 0;
    public static double f1TimeSpentSession = 0;
    // F2
    public static int f2SPlusSession = 0;
    public static int scarfStudiesSession = 0;
    public static int adaptiveSwordsSession = 0;
    public static double f2CoinsSpentSession = 0;
    public static double f2TimeSpentSession = 0;
    // F3
    public static int f3SPlusSession = 0;
    public static int adaptiveHelmsSession = 0;
    public static int adaptiveChestsSession = 0;
    public static int adaptiveLegsSession = 0;
    public static int adaptiveBootsSession = 0;
    public static double f3CoinsSpentSession = 0;
    public static double f3TimeSpentSession = 0;
    // F4
    public static int f4SPlusSession = 0;
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
    public static int f5SPlusSession = 0;
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
    public static int f6SPlusSession = 0;
    public static int ancientRosesSession = 0;
    public static int precursorEyesSession = 0;
    public static int giantsSwordsSession = 0;
    public static int necroLordHelmsSession = 0;
    public static int necroLordChestsSession = 0;
    public static int necroLordLegsSession = 0;
    public static int necroLordBootsSession = 0;
    public static int necroSwordsSession = 0;
    public static int f6RerollsSession = 0;
    public static double f6CoinsSpentSession = 0;
    public static double f6TimeSpentSession = 0;
    // F7
    public static int f7SPlusSession = 0;
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
    public static int f7RerollsSession = 0;
    public static double f7CoinsSpentSession = 0;
    public static double f7TimeSpentSession = 0;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;

        if (message.contains("    Team Score: ") && message.contains("(S+)")) {
            List<String> scoreboard = ScoreboardHandler.getSidebarLines();
            for (String s : scoreboard) {
                String sCleaned = ScoreboardHandler.cleanSB(s);
                if (sCleaned.contains("The Catacombs (")) {
                    if (sCleaned.contains("F1")) {
                        f1SPlus++;
                        f1SPlusSession++;
                        ConfigHandler.writeIntConfig("catacombs", "floorOneSPlus", f1SPlus);
                    } else if (sCleaned.contains("F2")) {
                        f2SPlus++;
                        f2SPlusSession++;
                        ConfigHandler.writeIntConfig("catacombs", "floorTwoSPlus", f2SPlus);
                    } else if (sCleaned.contains("F3")) {
                        f3SPlus++;
                        f3SPlusSession++;
                        ConfigHandler.writeIntConfig("catacombs", "floorThreeSPlus", f3SPlus);
                    } else if (sCleaned.contains("F4")) {
                        f4SPlus++;
                        f4SPlusSession++;
                        ConfigHandler.writeIntConfig("catacombs", "floorFourSPlus", f4SPlus);
                    } else if (sCleaned.contains("F5")) {
                        f5SPlus++;
                        f5SPlusSession++;
                        ConfigHandler.writeIntConfig("catacombs", "floorFiveSPlus", f5SPlus);
                    } else if (sCleaned.contains("F6")) {
                        f6SPlus++;
                        f6SPlusSession++;
                        ConfigHandler.writeIntConfig("catacombs", "floorSixSPlus", f6SPlus);
                    } else if (sCleaned.contains("F7")) {
                        f7SPlus++;
                        f7SPlusSession++;
                        ConfigHandler.writeIntConfig("catacombs", "floorSevenSPlus", f7SPlus);
                    }
                }
            }
        }

        if (message.contains(":")) return;

        if (message.contains("    ")) {
            if (message.contains("Recombobulator 3000")) {
                recombobulators++;
                recombobulatorsSession++;
                ConfigHandler.writeIntConfig("catacombs", "recombobulator", recombobulators);
            } else if (message.contains("Fuming Potato Book")) {
                fumingPotatoBooks++;
                fumingPotatoBooksSession++;
                ConfigHandler.writeIntConfig("catacombs", "fumingBooks", fumingPotatoBooks);
            } else if (message.contains("Bonzo's Staff")) { // F1
                bonzoStaffs++;
                bonzoStaffsSession++;
                ConfigHandler.writeIntConfig("catacombs", "bonzoStaff", bonzoStaffs);
            } else if (message.contains("Scarf's Studies")) { // F2
                scarfStudies++;
                scarfStudiesSession++;
                ConfigHandler.writeIntConfig("catacombs", "scarfStudies", scarfStudies);
            } else if (message.contains("Adaptive Helmet")) { // F3
                adaptiveHelms++;
                adaptiveHelmsSession++;
                ConfigHandler.writeIntConfig("catacombs", "adaptiveHelm", adaptiveHelms);
            } else if (message.contains("Adaptive Chestplate")) {
                adaptiveChests++;
                adaptiveChestsSession++;
                ConfigHandler.writeIntConfig("catacombs", "adaptiveChest", adaptiveChests);
            } else if (message.contains("Adaptive Leggings")) {
                adaptiveLegs++;
                adaptiveLegsSession++;
                ConfigHandler.writeIntConfig("catacombs", "adaptiveLegging", adaptiveLegs);
            } else if (message.contains("Adaptive Boots")) {
                adaptiveBoots++;
                adaptiveBootsSession++;
                ConfigHandler.writeIntConfig("catacombs", "adaptiveBoot", adaptiveBoots);
            } else if (message.contains("Adaptive Blade")) {
                adaptiveSwords++;
                adaptiveSwordsSession++;
                ConfigHandler.writeIntConfig("catacombs", "adaptiveSword", adaptiveSwords);
            } else if (message.contains("Spirit Wing")) { // F4
                spiritWings++;
                spiritWingsSession++;
                ConfigHandler.writeIntConfig("catacombs", "spiritWing", spiritWings);
            } else if (message.contains("Spirit Bone")) {
                spiritBones++;
                spiritBonesSession++;
                ConfigHandler.writeIntConfig("catacombs", "spiritBone", spiritBones);
            } else if (message.contains("Spirit Boots")) {
                spiritBoots++;
                spiritBootsSession++;
                ConfigHandler.writeIntConfig("catacombs", "spiritBoot", spiritBoots);
            } else if (message.contains("[Lvl 1] Spirit")) {
                String formattedMessage = event.message.getFormattedText();
                // Unicode colour code messes up here, just gonna remove the symbols
                if (formattedMessage.contains("5Spirit")) {
                    epicSpiritPets++;
                    epicSpiritPetsSession++;
                    ConfigHandler.writeIntConfig("catacombs", "spiritPetEpic", epicSpiritPets);
                } else if (formattedMessage.contains("6Spirit")) {
                    legSpiritPets++;
                    legSpiritPetsSession++;
                    ConfigHandler.writeIntConfig("catacombs", "spiritPetLeg", legSpiritPets);
                }
            } else if (message.contains("Spirit Sword")) {
                spiritSwords++;
                spiritSwordsSession++;
                ConfigHandler.writeIntConfig("catacombs", "spiritSword", spiritSwords);
            } else if (message.contains("Spirit Bow")) {
                spiritBows++;
                spiritBowsSession++;
                ConfigHandler.writeIntConfig("catacombs", "spiritBow", spiritBows);
            } else if (message.contains("Warped Stone")) { // F5
                warpedStones++;
                warpedStonesSession++;
                ConfigHandler.writeIntConfig("catacombs", "warpedStone", warpedStones);
            } else if (message.contains("Shadow Assassin Helmet")) {
                shadowAssHelms++;
                shadowAssHelmsSession++;
                ConfigHandler.writeIntConfig("catacombs", "shadowAssassinHelm", shadowAssHelms);
            } else if (message.contains("Shadow Assassin Chestplate")) {
                shadowAssChests++;
                shadowAssChestsSession++;
                ConfigHandler.writeIntConfig("catacombs", "shadowAssassinChest", shadowAssChests);
            } else if (message.contains("Shadow Assassin Leggings")) {
                shadowAssLegs++;
                shadowAssLegsSession++;
                ConfigHandler.writeIntConfig("catacombs", "shadowAssassinLegging", shadowAssLegs);
            } else if (message.contains("Shadow Assassin Boots")) {
                shadowAssBoots++;
                shadowAssBootsSession++;
                ConfigHandler.writeIntConfig("catacombs", "shadowAssassinBoot", shadowAssBoots);
            } else if (message.contains("Livid Dagger")) {
                lividDaggers++;
                lividDaggersSession++;
                ConfigHandler.writeIntConfig("catacombs", "lividDagger", lividDaggers);
            } else if (message.contains("Shadow Fury")) {
                shadowFurys++;
                shadowFurysSession++;
                ConfigHandler.writeIntConfig("catacombs", "shadowFury", shadowFurys);
            } else if (message.contains("Ancient Rose")) { // F6
                ancientRoses++;
                ancientRosesSession++;
                ConfigHandler.writeIntConfig("catacombs", "ancientRose", ancientRoses);
            } else if (message.contains("Precursor Eye")) {
                precursorEyes++;
                precursorEyesSession++;
                ConfigHandler.writeIntConfig("catacombs", "precursorEye", precursorEyes);
            } else if (message.contains("Giant's Sword")) {
                giantsSwords++;
                giantsSwordsSession++;
                ConfigHandler.writeIntConfig("catacombs", "giantsSword", giantsSwords);
            } else if (message.contains("Necromancer Lord Helmet")) {
                necroLordHelms++;
                necroLordHelmsSession++;
                ConfigHandler.writeIntConfig("catacombs", "necroLordHelm", necroLordHelms);
            } else if (message.contains("Necromancer Lord Chestplate")) {
                necroLordChests++;
                necroLordChestsSession++;
                ConfigHandler.writeIntConfig("catacombs", "necroLordChest", necroLordChests);
            } else if (message.contains("Necromancer Lord Leggings")) {
                necroLordLegs++;
                necroLordLegsSession++;
                ConfigHandler.writeIntConfig("catacombs", "necroLordLegging", necroLordLegs);
            } else if (message.contains("Necromancer Lord Boots")) {
                necroLordBoots++;
                necroLordBootsSession++;
                ConfigHandler.writeIntConfig("catacombs", "necroLordBoot", necroLordBoots);
            } else if (message.contains("Necromancer Sword")) {
                necroSwords++;
                necroSwordsSession++;
                ConfigHandler.writeIntConfig("catacombs", "necroSword", necroSwords);
            } else if (message.contains("Wither Blood")) { // F7
                witherBloods++;
                witherBloodsSession++;
                ConfigHandler.writeIntConfig("catacombs", "witherBlood", witherBloods);
            } else if (message.contains("Wither Cloak")) {
                witherCloaks++;
                witherCloaksSession++;
                ConfigHandler.writeIntConfig("catacombs", "witherCloak", witherCloaks);
            } else if (message.contains("Implosion")) {
                implosions++;
                implosionsSession++;
                ConfigHandler.writeIntConfig("catacombs", "implosion", implosions);
            } else if (message.contains("Wither Shield")) {
                witherShields++;
                witherShieldsSession++;
                ConfigHandler.writeIntConfig("catacombs", "witherShield", witherShields);
            } else if (message.contains("Shadow Warp")) {
                shadowWarps++;
                shadowWarpsSession++;
                ConfigHandler.writeIntConfig("catacombs", "shadowWarp", shadowWarps);
            } else if (message.contains("Necron's Handle")) {
                necronsHandles++;
                necronsHandlesSession++;
                ConfigHandler.writeIntConfig("catacombs", "necronsHandle", necronsHandles);
            } else if (message.contains("Auto Recombobulator")) {
                autoRecombs++;
                autoRecombsSession++;
                ConfigHandler.writeIntConfig("catacombs", "autoRecomb", autoRecombs);
            } else if (message.contains("Wither Helmet")) {
                witherHelms++;
                witherHelmsSession++;
                ConfigHandler.writeIntConfig("catacombs", "witherHelm", witherHelms);
            } else if (message.contains("Wither Chestplate")) {
                witherChests++;
                witherChestsSession++;
                ConfigHandler.writeIntConfig("catacombs", "witherChest", witherChests);
            } else if (message.contains("Wither Leggings")) {
                witherLegs++;
                witherLegsSession++;
                ConfigHandler.writeIntConfig("catacombs", "witherLegging", witherLegs);
            } else if (message.contains("Wither Boots")) {
                witherBoots++;
                witherBootsSession++;
                ConfigHandler.writeIntConfig("catacombs", "witherBoot", witherBoots);
            }
        }

        if (message.contains("EXTRA STATS ")) {
            List<String> scoreboard = ScoreboardHandler.getSidebarLines();
            int timeToAdd = 0;
            for (String s : scoreboard) {
                String sCleaned = ScoreboardHandler.cleanSB(s);
                if (sCleaned.contains("The Catacombs (")) {
                    // Add time to floor
                    if (sCleaned.contains("F1")) {
                        f1TimeSpent = Math.floor(f1TimeSpent + timeToAdd);
                        f1TimeSpentSession = Math.floor(f1TimeSpentSession + timeToAdd);
                        ConfigHandler.writeDoubleConfig("catacombs", "floorOneTime", f1TimeSpent);
                    } else if (sCleaned.contains("F2")) {
                        f2TimeSpent = Math.floor(f2TimeSpent + timeToAdd);
                        f2TimeSpentSession = Math.floor(f2TimeSpentSession + timeToAdd);
                        ConfigHandler.writeDoubleConfig("catacombs", "floorTwoTime", f2TimeSpent);
                    } else if (sCleaned.contains("F3")) {
                        f3TimeSpent = Math.floor(f3TimeSpent + timeToAdd);
                        f3TimeSpentSession = Math.floor(f3TimeSpentSession + timeToAdd);
                        ConfigHandler.writeDoubleConfig("catacombs", "floorThreeTime", f3TimeSpent);
                    } else if (sCleaned.contains("F4")) {
                        f4TimeSpent = Math.floor(f4TimeSpent + timeToAdd);
                        f4TimeSpentSession = Math.floor(f4TimeSpentSession + timeToAdd);
                        ConfigHandler.writeDoubleConfig("catacombs", "floorFourTime", f4TimeSpent);
                    } else if (sCleaned.contains("F5")) {
                        f5TimeSpent = Math.floor(f5TimeSpent + timeToAdd);
                        f5TimeSpentSession = Math.floor(f5TimeSpentSession + timeToAdd);
                        ConfigHandler.writeDoubleConfig("catacombs", "floorFiveTime", f5TimeSpent);
                    } else if (sCleaned.contains("F6")) {
                        f6TimeSpent = Math.floor(f6TimeSpent + timeToAdd);
                        f6TimeSpentSession = Math.floor(f6TimeSpentSession + timeToAdd);
                        ConfigHandler.writeDoubleConfig("catacombs", "floorSixTime", f6TimeSpent);
                    } else if (sCleaned.contains("F7")) {
                        f7TimeSpent = Math.floor(f7TimeSpent + timeToAdd);
                        f7TimeSpentSession = Math.floor(f7TimeSpentSession + timeToAdd);
                        ConfigHandler.writeDoubleConfig("catacombs", "floorSevenTime", f7TimeSpent);
                    }
                } else if (sCleaned.contains("Time Elapsed:")) {
                    // Get floor time
                    String time = sCleaned.substring(sCleaned.indexOf(":") + 2);
                    time = time.replaceAll("\\s", "");
                    int minutes = Integer.parseInt(time.substring(0, time.indexOf("m")));
                    int seconds = Integer.parseInt(time.substring(time.indexOf("m") + 1, time.indexOf("s")));
                    timeToAdd = (minutes * 60) + seconds;
                }
            }
        }
    }

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        ItemStack item = event.item;

        if (event.inventoryName.endsWith(" Chest") && item != null) {
            if (item.getDisplayName().contains("Open Reward Chest")) {
                List<String> tooltip = item.getTooltip(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().gameSettings.advancedItemTooltips);
                for (String lineUnclean : tooltip) {
                    String line = StringUtils.stripControlCodes(lineUnclean);
                    if (line.contains("FREE")) {
                        break;
                    } else if (line.contains(" Coins") && !line.contains("NOTE:")) {
                        int coinsSpent = Integer.parseInt(line.substring(0, line.indexOf(" ")).replaceAll(",", ""));

                        List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                        for (String s : scoreboard) {
                            String sCleaned = ScoreboardHandler.cleanSB(s);
                            if (sCleaned.contains("The Catacombs (")) {
                                if (sCleaned.contains("F1")) {
                                    f1CoinsSpent += coinsSpent;
                                    f1CoinsSpentSession += coinsSpent;
                                    ConfigHandler.writeDoubleConfig("catacombs", "floorOneCoins", f1CoinsSpent);
                                } else if (sCleaned.contains("F2")) {
                                    f2CoinsSpent += coinsSpent;
                                    f2CoinsSpentSession += coinsSpent;
                                    ConfigHandler.writeDoubleConfig("catacombs", "floorTwoCoins", f2CoinsSpent);
                                } else if (sCleaned.contains("F3")) {
                                    f3CoinsSpent += coinsSpent;
                                    f3CoinsSpentSession += coinsSpent;
                                    ConfigHandler.writeDoubleConfig("catacombs", "floorThreeCoins", f3CoinsSpent);
                                } else if (sCleaned.contains("F4")) {
                                    f4CoinsSpent += coinsSpent;
                                    f4CoinsSpentSession += coinsSpent;
                                    ConfigHandler.writeDoubleConfig("catacombs", "floorFourCoins", f4CoinsSpent);
                                } else if (sCleaned.contains("F5")) {
                                    f5CoinsSpent += coinsSpent;
                                    f5CoinsSpentSession += coinsSpent;
                                    ConfigHandler.writeDoubleConfig("catacombs", "floorFiveCoins", f5CoinsSpent);
                                } else if (sCleaned.contains("F6")) {
                                    f6CoinsSpent += coinsSpent;
                                    f6CoinsSpentSession += coinsSpent;
                                    ConfigHandler.writeDoubleConfig("catacombs", "floorSixCoins", f6CoinsSpent);
                                } else if (sCleaned.contains("F7")) {
                                    f7CoinsSpent += coinsSpent;
                                    f7CoinsSpentSession += coinsSpent;
                                    ConfigHandler.writeDoubleConfig("catacombs", "floorSevenCoins", f7CoinsSpent);
                                }
                            }
                            break;
                        }
                    }
                }
            } else if (item.getDisplayName().contains("Reroll Chest")) {
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                for (String s : scoreboard) {
                    String sCleaned = ScoreboardHandler.cleanSB(s);
                    if (sCleaned.contains("The Catacombs (")) {
                        if (sCleaned.contains("F6")) {
                            f6Rerolls++;
                            f6RerollsSession++;
                            ConfigHandler.writeDoubleConfig("catacombs", "floorSixRerolls", f6Rerolls);
                        } else if (sCleaned.contains("F7")) {
                            f7Rerolls++;
                            f7RerollsSession++;
                            ConfigHandler.writeDoubleConfig("catacombs", "floorSevenRerolls", f7Rerolls);
                        }
                        break;
                    }
                }
            }
        }
    }

}
