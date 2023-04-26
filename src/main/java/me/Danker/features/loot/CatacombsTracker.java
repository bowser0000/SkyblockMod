package me.Danker.features.loot;

import me.Danker.config.CfgConfig;
import me.Danker.events.ChestSlotClickedEvent;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.locations.Location;
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
    // MM
    public static int m1S;
    public static int m1SPlus;
    public static int m2S;
    public static int m2SPlus;
    public static int m3S;
    public static int m3SPlus;
    public static int m4S;
    public static int m4SPlus;
    public static int m5S;
    public static int m5SPlus;
    public static int m6S;
    public static int m6SPlus;
    public static int m7S;
    public static int m7SPlus;
    public static int firstStars;
    public static int secondStars;
    public static int thirdStars;
    public static int fourthStars;
    public static int fifthStars;
    public static int necronDyes;
    public static int darkClaymores;
    public static int masterRerolls;
    public static double masterCoinsSpent;
    public static double masterTimeSpent;

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
    // MM
    public static int m1SSession = 0;
    public static int m1SPlusSession = 0;
    public static int m2SSession = 0;
    public static int m2SPlusSession = 0;
    public static int m3SSession = 0;
    public static int m3SPlusSession = 0;
    public static int m4SSession = 0;
    public static int m4SPlusSession = 0;
    public static int m5SSession = 0;
    public static int m5SPlusSession = 0;
    public static int m6SSession = 0;
    public static int m6SPlusSession = 0;
    public static int m7SSession = 0;
    public static int m7SPlusSession = 0;
    public static int firstStarsSession = 0;
    public static int secondStarsSession = 0;
    public static int thirdStarsSession = 0;
    public static int fourthStarsSession = 0;
    public static int fifthStarsSession = 0;
    public static int necronDyesSession = 0;
    public static int darkClaymoresSession = 0;
    public static int masterRerollsSession = 0;
    public static double masterCoinsSpentSession = 0;
    public static double masterTimeSpentSession = 0;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (Utils.currentLocation != Location.CATACOMBS && Utils.currentLocation != Location.DUNGEON_HUB) return;
        if (event.type == 2) return;

        if (message.contains("    Team Score: ")) {
            if (message.contains("(S+)")) {
                switch (Utils.currentFloor) {
                    case F1:
                        f1SPlus++;
                        f1SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "floorOneSPlus", f1SPlus);
                        break;
                    case F2:
                        f2SPlus++;
                        f2SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "floorTwoSPlus", f2SPlus);
                        break;
                    case F3:
                        f3SPlus++;
                        f3SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "floorThreeSPlus", f3SPlus);
                        break;
                    case F4:
                        f4SPlus++;
                        f4SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "floorFourSPlus", f4SPlus);
                        break;
                    case F5:
                        f5SPlus++;
                        f5SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "floorFiveSPlus", f5SPlus);
                        break;
                    case F6:
                        f6SPlus++;
                        f6SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "floorSixSPlus", f6SPlus);
                        break;
                    case F7:
                        f7SPlus++;
                        f7SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "floorSevenSPlus", f7SPlus);
                        break;
                    case M1:
                        m1S++;
                        m1SPlus++;
                        m1SSession++;
                        m1SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterOneS", m1S);
                        CfgConfig.writeIntConfig("catacombs", "masterOneSPlus", m1SPlus);
                        break;
                    case M2:
                        m2S++;
                        m2SPlus++;
                        m2SSession++;
                        m2SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterTwoS", m2S);
                        CfgConfig.writeIntConfig("catacombs", "masterTwoSPlus", m2SPlus);
                        break;
                    case M3:
                        m3S++;
                        m3SPlus++;
                        m3SSession++;
                        m3SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterThreeS", m3S);
                        CfgConfig.writeIntConfig("catacombs", "masterThreeSPlus", m3SPlus);
                        break;
                    case M4:
                        m4S++;
                        m4SPlus++;
                        m4SSession++;
                        m4SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterFourS", m4S);
                        CfgConfig.writeIntConfig("catacombs", "masterFourSPlus", m4SPlus);
                        break;
                    case M5:
                        m5S++;
                        m5SPlus++;
                        m5SSession++;
                        m5SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterFiveS", m5S);
                        CfgConfig.writeIntConfig("catacombs", "masterFiveSPlus", m5SPlus);
                        break;
                    case M6:
                        m6S++;
                        m6SPlus++;
                        m6SSession++;
                        m6SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterSixS", m6S);
                        CfgConfig.writeIntConfig("catacombs", "masterSixSPlus", m6SPlus);
                        break;
                    case M7:
                        m7S++;
                        m7SPlus++;
                        m7SSession++;
                        m7SPlusSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterSevenS", m7S);
                        CfgConfig.writeIntConfig("catacombs", "masterSevenSPlus", m7SPlus);
                        break;
                }
            } else if (message.contains("(S)")) {
                switch (Utils.currentFloor) {
                    case M1:
                        m1S++;
                        m1SSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterOneS", m1S);
                        break;
                    case M2:
                        m2S++;
                        m2SSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterTwoS", m2S);
                        break;
                    case M3:
                        m3S++;
                        m3SSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterThreeS", m3S);
                        break;
                    case M4:
                        m4S++;
                        m4SSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterFourS", m4S);
                        break;
                    case M5:
                        m5S++;
                        m5SSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterFiveS", m5S);
                        break;
                    case M6:
                        m6S++;
                        m6SSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterSixS", m6S);
                        break;
                    case M7:
                        m7S++;
                        m7SSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterSevenS", m7S);
                        break;
                }
            }
        }

        if (message.contains(":")) return;

        if (message.contains("    ")) {
            if (message.contains("Recombobulator 3000")) {
                recombobulators++;
                recombobulatorsSession++;
                CfgConfig.writeIntConfig("catacombs", "recombobulator", recombobulators);
            } else if (message.contains("Fuming Potato Book")) {
                fumingPotatoBooks++;
                fumingPotatoBooksSession++;
                CfgConfig.writeIntConfig("catacombs", "fumingBooks", fumingPotatoBooks);
            } else if (message.contains("Bonzo's Staff")) { // F1
                bonzoStaffs++;
                bonzoStaffsSession++;
                CfgConfig.writeIntConfig("catacombs", "bonzoStaff", bonzoStaffs);
            } else if (message.contains("Scarf's Studies")) { // F2
                scarfStudies++;
                scarfStudiesSession++;
                CfgConfig.writeIntConfig("catacombs", "scarfStudies", scarfStudies);
            } else if (message.contains("Adaptive Helmet")) { // F3
                adaptiveHelms++;
                adaptiveHelmsSession++;
                CfgConfig.writeIntConfig("catacombs", "adaptiveHelm", adaptiveHelms);
            } else if (message.contains("Adaptive Chestplate")) {
                adaptiveChests++;
                adaptiveChestsSession++;
                CfgConfig.writeIntConfig("catacombs", "adaptiveChest", adaptiveChests);
            } else if (message.contains("Adaptive Leggings")) {
                adaptiveLegs++;
                adaptiveLegsSession++;
                CfgConfig.writeIntConfig("catacombs", "adaptiveLegging", adaptiveLegs);
            } else if (message.contains("Adaptive Boots")) {
                adaptiveBoots++;
                adaptiveBootsSession++;
                CfgConfig.writeIntConfig("catacombs", "adaptiveBoot", adaptiveBoots);
            } else if (message.contains("Adaptive Blade")) {
                adaptiveSwords++;
                adaptiveSwordsSession++;
                CfgConfig.writeIntConfig("catacombs", "adaptiveSword", adaptiveSwords);
            } else if (message.contains("Spirit Wing")) { // F4
                spiritWings++;
                spiritWingsSession++;
                CfgConfig.writeIntConfig("catacombs", "spiritWing", spiritWings);
            } else if (message.contains("Spirit Bone")) {
                spiritBones++;
                spiritBonesSession++;
                CfgConfig.writeIntConfig("catacombs", "spiritBone", spiritBones);
            } else if (message.contains("Spirit Boots")) {
                spiritBoots++;
                spiritBootsSession++;
                CfgConfig.writeIntConfig("catacombs", "spiritBoot", spiritBoots);
            } else if (message.contains("[Lvl 1] Spirit")) {
                String formattedMessage = event.message.getFormattedText();
                // Unicode colour code messes up here, just gonna remove the symbols
                if (formattedMessage.contains("5Spirit")) {
                    epicSpiritPets++;
                    epicSpiritPetsSession++;
                    CfgConfig.writeIntConfig("catacombs", "spiritPetEpic", epicSpiritPets);
                } else if (formattedMessage.contains("6Spirit")) {
                    legSpiritPets++;
                    legSpiritPetsSession++;
                    CfgConfig.writeIntConfig("catacombs", "spiritPetLeg", legSpiritPets);
                }
            } else if (message.contains("Spirit Sword")) {
                spiritSwords++;
                spiritSwordsSession++;
                CfgConfig.writeIntConfig("catacombs", "spiritSword", spiritSwords);
            } else if (message.contains("Spirit Bow")) {
                spiritBows++;
                spiritBowsSession++;
                CfgConfig.writeIntConfig("catacombs", "spiritBow", spiritBows);
            } else if (message.contains("Warped Stone")) { // F5
                warpedStones++;
                warpedStonesSession++;
                CfgConfig.writeIntConfig("catacombs", "warpedStone", warpedStones);
            } else if (message.contains("Shadow Assassin Helmet")) {
                shadowAssHelms++;
                shadowAssHelmsSession++;
                CfgConfig.writeIntConfig("catacombs", "shadowAssassinHelm", shadowAssHelms);
            } else if (message.contains("Shadow Assassin Chestplate")) {
                shadowAssChests++;
                shadowAssChestsSession++;
                CfgConfig.writeIntConfig("catacombs", "shadowAssassinChest", shadowAssChests);
            } else if (message.contains("Shadow Assassin Leggings")) {
                shadowAssLegs++;
                shadowAssLegsSession++;
                CfgConfig.writeIntConfig("catacombs", "shadowAssassinLegging", shadowAssLegs);
            } else if (message.contains("Shadow Assassin Boots")) {
                shadowAssBoots++;
                shadowAssBootsSession++;
                CfgConfig.writeIntConfig("catacombs", "shadowAssassinBoot", shadowAssBoots);
            } else if (message.contains("Livid Dagger")) {
                lividDaggers++;
                lividDaggersSession++;
                CfgConfig.writeIntConfig("catacombs", "lividDagger", lividDaggers);
            } else if (message.contains("Shadow Fury")) {
                shadowFurys++;
                shadowFurysSession++;
                CfgConfig.writeIntConfig("catacombs", "shadowFury", shadowFurys);
            } else if (message.contains("Ancient Rose")) { // F6
                ancientRoses++;
                ancientRosesSession++;
                CfgConfig.writeIntConfig("catacombs", "ancientRose", ancientRoses);
            } else if (message.contains("Precursor Eye")) {
                precursorEyes++;
                precursorEyesSession++;
                CfgConfig.writeIntConfig("catacombs", "precursorEye", precursorEyes);
            } else if (message.contains("Giant's Sword")) {
                giantsSwords++;
                giantsSwordsSession++;
                CfgConfig.writeIntConfig("catacombs", "giantsSword", giantsSwords);
            } else if (message.contains("Necromancer Lord Helmet")) {
                necroLordHelms++;
                necroLordHelmsSession++;
                CfgConfig.writeIntConfig("catacombs", "necroLordHelm", necroLordHelms);
            } else if (message.contains("Necromancer Lord Chestplate")) {
                necroLordChests++;
                necroLordChestsSession++;
                CfgConfig.writeIntConfig("catacombs", "necroLordChest", necroLordChests);
            } else if (message.contains("Necromancer Lord Leggings")) {
                necroLordLegs++;
                necroLordLegsSession++;
                CfgConfig.writeIntConfig("catacombs", "necroLordLegging", necroLordLegs);
            } else if (message.contains("Necromancer Lord Boots")) {
                necroLordBoots++;
                necroLordBootsSession++;
                CfgConfig.writeIntConfig("catacombs", "necroLordBoot", necroLordBoots);
            } else if (message.contains("Necromancer Sword")) {
                necroSwords++;
                necroSwordsSession++;
                CfgConfig.writeIntConfig("catacombs", "necroSword", necroSwords);
            } else if (message.contains("Wither Blood")) { // F7
                witherBloods++;
                witherBloodsSession++;
                CfgConfig.writeIntConfig("catacombs", "witherBlood", witherBloods);
            } else if (message.contains("Wither Cloak")) {
                witherCloaks++;
                witherCloaksSession++;
                CfgConfig.writeIntConfig("catacombs", "witherCloak", witherCloaks);
            } else if (message.contains("Implosion")) {
                implosions++;
                implosionsSession++;
                CfgConfig.writeIntConfig("catacombs", "implosion", implosions);
            } else if (message.contains("Wither Shield")) {
                witherShields++;
                witherShieldsSession++;
                CfgConfig.writeIntConfig("catacombs", "witherShield", witherShields);
            } else if (message.contains("Shadow Warp")) {
                shadowWarps++;
                shadowWarpsSession++;
                CfgConfig.writeIntConfig("catacombs", "shadowWarp", shadowWarps);
            } else if (message.contains("Necron's Handle")) {
                necronsHandles++;
                necronsHandlesSession++;
                CfgConfig.writeIntConfig("catacombs", "necronsHandle", necronsHandles);
            } else if (message.contains("Auto Recombobulator")) {
                autoRecombs++;
                autoRecombsSession++;
                CfgConfig.writeIntConfig("catacombs", "autoRecomb", autoRecombs);
            } else if (message.contains("Wither Helmet")) {
                witherHelms++;
                witherHelmsSession++;
                CfgConfig.writeIntConfig("catacombs", "witherHelm", witherHelms);
            } else if (message.contains("Wither Chestplate")) {
                witherChests++;
                witherChestsSession++;
                CfgConfig.writeIntConfig("catacombs", "witherChest", witherChests);
            } else if (message.contains("Wither Leggings")) {
                witherLegs++;
                witherLegsSession++;
                CfgConfig.writeIntConfig("catacombs", "witherLegging", witherLegs);
            } else if (message.contains("Wither Boots")) {
                witherBoots++;
                witherBootsSession++;
                CfgConfig.writeIntConfig("catacombs", "witherBoot", witherBoots);
            } else if (message.contains("First Master Star")) {
                firstStars++;
                firstStarsSession++;
                CfgConfig.writeIntConfig("catacombs", "firstStar", firstStars);
            } else if (message.contains("Second Master Star")) {
                secondStars++;
                secondStarsSession++;
                CfgConfig.writeIntConfig("catacombs", "secondStar", secondStars);
            } else if (message.contains("Third Master Star")) {
                thirdStars++;
                thirdStarsSession++;
                CfgConfig.writeIntConfig("catacombs", "thirdStar", thirdStars);
            } else if (message.contains("Fourth Master Star")) {
                fourthStars++;
                fourthStarsSession++;
                CfgConfig.writeIntConfig("catacombs", "fourthStar", fourthStars);
            } else if (message.contains("Fifth Master Star")) {
                fifthStars++;
                fifthStarsSession++;
                CfgConfig.writeIntConfig("catacombs", "fifthStar", fifthStars);
            } else if (message.contains("Dark Claymore")) {
                darkClaymores++;
                darkClaymoresSession++;
                CfgConfig.writeIntConfig("catacombs", "darkClaymore", darkClaymores);
            } else if (message.contains("Necron Dye")) {
                necronDyes++;
                necronDyesSession++;
                CfgConfig.writeIntConfig("catacombs", "necronDye", necronDyes);
            }
        }

        if (message.contains("EXTRA STATS ")) {
            List<String> scoreboard = ScoreboardHandler.getSidebarLines();
            for (String s : scoreboard) {
                String sCleaned = ScoreboardHandler.cleanSB(s);
                if (sCleaned.contains("Time Elapsed:")) {
                    // Get floor time
                    String time = sCleaned.substring(sCleaned.indexOf(":") + 2);
                    time = time.replaceAll("\\s", "");
                    int minutes = Integer.parseInt(time.substring(0, time.indexOf("m")));
                    int seconds = Integer.parseInt(time.substring(time.indexOf("m") + 1, time.indexOf("s")));
                    int timeToAdd = (minutes * 60) + seconds;

                    // Add time to floor
                    switch (Utils.currentFloor) {
                        case F1:
                            f1TimeSpent = Math.floor(f1TimeSpent + timeToAdd);
                            f1TimeSpentSession = Math.floor(f1TimeSpentSession + timeToAdd);
                            CfgConfig.writeDoubleConfig("catacombs", "floorOneTime", f1TimeSpent);
                            break;
                        case F2:
                            f2TimeSpent = Math.floor(f2TimeSpent + timeToAdd);
                            f2TimeSpentSession = Math.floor(f2TimeSpentSession + timeToAdd);
                            CfgConfig.writeDoubleConfig("catacombs", "floorTwoTime", f2TimeSpent);
                            break;
                        case F3:
                            f3TimeSpent = Math.floor(f3TimeSpent + timeToAdd);
                            f3TimeSpentSession = Math.floor(f3TimeSpentSession + timeToAdd);
                            CfgConfig.writeDoubleConfig("catacombs", "floorThreeTime", f3TimeSpent);
                            break;
                        case F4:
                            f4TimeSpent = Math.floor(f4TimeSpent + timeToAdd);
                            f4TimeSpentSession = Math.floor(f4TimeSpentSession + timeToAdd);
                            CfgConfig.writeDoubleConfig("catacombs", "floorFourTime", f4TimeSpent);
                            break;
                        case F5:
                            f5TimeSpent = Math.floor(f5TimeSpent + timeToAdd);
                            f5TimeSpentSession = Math.floor(f5TimeSpentSession + timeToAdd);
                            CfgConfig.writeDoubleConfig("catacombs", "floorFiveTime", f5TimeSpent);
                            break;
                        case F6:
                            f6TimeSpent = Math.floor(f6TimeSpent + timeToAdd);
                            f6TimeSpentSession = Math.floor(f6TimeSpentSession + timeToAdd);
                            CfgConfig.writeDoubleConfig("catacombs", "floorSixTime", f6TimeSpent);
                            break;
                        case F7:
                            f7TimeSpent = Math.floor(f7TimeSpent + timeToAdd);
                            f7TimeSpentSession = Math.floor(f7TimeSpentSession + timeToAdd);
                            CfgConfig.writeDoubleConfig("catacombs", "floorSevenTime", f7TimeSpent);
                            break;
                        case M1:
                        case M2:
                        case M3:
                        case M4:
                        case M5:
                        case M6:
                        case M7:
                            masterTimeSpent = Math.floor(masterTimeSpent + timeToAdd);
                            masterTimeSpentSession = Math.floor(masterTimeSpentSession + timeToAdd);
                            CfgConfig.writeDoubleConfig("catacombs", "masterTime", masterTimeSpent);
                            break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        if (!Utils.isInDungeons()) return;

        ItemStack item = event.item;

        if (event.inventoryName.endsWith(" Chest") && item != null) {
            if (item.getDisplayName().contains("Open Reward Chest")) {
                List<String> tooltip = item.getTooltip(Minecraft.getMinecraft().thePlayer, false);
                for (String lineUnclean : tooltip) {
                    String line = StringUtils.stripControlCodes(lineUnclean);
                    if (line.contains(" Coins") && !line.contains("NOTE:")) {
                        int coinsSpent = Integer.parseInt(line.replaceAll("\\D", ""));

                        switch (Utils.currentFloor) {
                            case F1:
                                f1CoinsSpent += coinsSpent;
                                f1CoinsSpentSession += coinsSpent;
                                CfgConfig.writeDoubleConfig("catacombs", "floorOneCoins", f1CoinsSpent);
                                break;
                            case F2:
                                f2CoinsSpent += coinsSpent;
                                f2CoinsSpentSession += coinsSpent;
                                CfgConfig.writeDoubleConfig("catacombs", "floorTwoCoins", f2CoinsSpent);
                                break;
                            case F3:
                                f3CoinsSpent += coinsSpent;
                                f3CoinsSpentSession += coinsSpent;
                                CfgConfig.writeDoubleConfig("catacombs", "floorThreeCoins", f3CoinsSpent);
                                break;
                            case F4:
                                f4CoinsSpent += coinsSpent;
                                f4CoinsSpentSession += coinsSpent;
                                CfgConfig.writeDoubleConfig("catacombs", "floorFourCoins", f4CoinsSpent);
                                break;
                            case F5:
                                f5CoinsSpent += coinsSpent;
                                f5CoinsSpentSession += coinsSpent;
                                CfgConfig.writeDoubleConfig("catacombs", "floorFiveCoins", f5CoinsSpent);
                                break;
                            case F6:
                                f6CoinsSpent += coinsSpent;
                                f6CoinsSpentSession += coinsSpent;
                                CfgConfig.writeDoubleConfig("catacombs", "floorSixCoins", f6CoinsSpent);
                                break;
                            case F7:
                                f7CoinsSpent += coinsSpent;
                                f7CoinsSpentSession += coinsSpent;
                                CfgConfig.writeDoubleConfig("catacombs", "floorSevenCoins", f7CoinsSpent);
                                break;
                            case M1:
                            case M2:
                            case M3:
                            case M4:
                            case M5:
                            case M6:
                            case M7:
                                masterCoinsSpent += coinsSpent;
                                masterCoinsSpentSession += coinsSpent;
                                CfgConfig.writeDoubleConfig("catacombs", "masterCoins", masterCoinsSpent);
                                break;
                        }
                        break;
                    }
                }
            } else if (item.getDisplayName().contains("Reroll Chest")) {
                switch (Utils.currentFloor) {
                    case F6:
                        f6Rerolls++;
                        f6RerollsSession++;
                        CfgConfig.writeIntConfig("catacombs", "floorSixRerolls", f6Rerolls);
                        break;
                    case F7:
                        f7Rerolls++;
                        f7RerollsSession++;
                        CfgConfig.writeIntConfig("catacombs", "floorSevenRerolls", f7Rerolls);
                        break;
                    case M1:
                    case M2:
                    case M3:
                    case M4:
                    case M5:
                    case M6:
                    case M7:
                        masterRerolls++;
                        masterRerollsSession++;
                        CfgConfig.writeIntConfig("catacombs", "masterRerolls", masterRerolls);
                        break;
                }
            }
        }
    }

}
