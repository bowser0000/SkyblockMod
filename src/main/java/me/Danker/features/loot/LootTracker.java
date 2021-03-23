package me.Danker.features.loot;

import me.Danker.commands.ToggleCommand;
import me.Danker.events.ChestSlotClickedEvent;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class LootTracker {

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
    public static int zombieShards;
    public static int zombieWardenHearts;
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
    // Ghosts
    public static int sorrows  = 0;
    public static int bagOfCashs  = 0;
    public static int voltas  = 0;
    public static int plasmas  = 0;
    public static int ghostlyBoots = 0;
   // public static double ghostsTimeSpent = -1;



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
    public static int zombieShardsSession = 0;
    public static int zombieWardenHeartsSession = 0;
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
    // Ghosts
    public static int sorrowSession = 0;
    public static int bagOfCashSession = 0;
    public static int voltaSession = 0;
    public static int plasmaSession = 0;
    public static int ghostlyBootsSession = 0;
   // public static double ghostsSecondsSinceStarts = 0;


    static double checkItemsNow = 0;
    static double itemsChecked = 0;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());


        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        boolean wolfRNG = false;
        boolean spiderRNG = false;
        boolean zombieRNG = false;



        // Slayer tracker
        // T6 books
        if (message.contains("VERY RARE DROP!  (Enchanted Book)") || message.contains("CRAZY RARE DROP!  (Enchanted Book)")) {
            // Loop through scoreboard to see what boss you're doing
            List<String> scoreboard = ScoreboardHandler.getSidebarLines();
            for (String s : scoreboard) {
                String sCleaned = ScoreboardHandler.cleanSB(s);
                if (sCleaned.contains("Sven Packmaster")) {
                    wolfBooks++;
                    ConfigHandler.writeIntConfig("wolf", "book", wolfBooks);
                } else if (sCleaned.contains("Tarantula Broodfather")) {
                    spiderBooks++;
                    ConfigHandler.writeIntConfig("spider", "book", spiderBooks);
                } else if (sCleaned.contains("Revenant Horror")) {
                    zombieBooks++;
                    ConfigHandler.writeIntConfig("zombie", "book", zombieBooks);
                }

            }
        }

        // Wolf
        if (message.contains("   Wolf Slayer LVL ")) {
            wolfSvens++;
            wolfSvensSession++;
            if (wolfBosses != -1) {
                wolfBosses++;
            }
            if (wolfBossesSession != -1) {
                wolfBossesSession++;
            }
            ConfigHandler.writeIntConfig("wolf", "svens", wolfSvens);
            ConfigHandler.writeIntConfig("wolf", "bossRNG", wolfBosses);
        } else if (message.contains("RARE DROP! (Hamster Wheel)")) {
            wolfWheelsDrops++;
            wolfWheelsDropsSession++;
            ConfigHandler.writeIntConfig("wolf", "wheelDrops", wolfWheelsDrops);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" Spirit Rune I)")) { // Removing the unicode here *should* fix rune drops not counting
            wolfSpirits++;
            wolfSpiritsSession++;
            ConfigHandler.writeIntConfig("wolf", "spirit", wolfSpirits);
        } else if (message.contains("CRAZY RARE DROP!  (Red Claw Egg)")) {
            wolfRNG = true;
            wolfEggs++;
            wolfEggsSession++;
            ConfigHandler.writeIntConfig("wolf", "egg", wolfEggs);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_RED + "RED CLAW EGG!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Couture Rune I)")) {
            wolfRNG = true;
            wolfCoutures++;
            wolfCouturesSession++;
            ConfigHandler.writeIntConfig("wolf", "couture", wolfCoutures);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "COUTURE RUNE!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Grizzly Bait)") || message.contains("CRAZY RARE DROP! (Rename Me)")) { // How did Skyblock devs even manage to make this item Rename Me
            wolfRNG = true;
            wolfBaits++;
            wolfBaitsSession++;
            ConfigHandler.writeIntConfig("wolf", "bait", wolfBaits);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.AQUA + "GRIZZLY BAIT!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Overflux Capacitor)")) {
            wolfRNG = true;
            wolfFluxes++;
            wolfFluxesSession++;
            ConfigHandler.writeIntConfig("wolf", "flux", wolfFluxes);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "OVERFLUX CAPACITOR!", 5);
        } else if (message.contains("   Spider Slayer LVL ")) { // Spider
            spiderTarantulas++;
            spiderTarantulasSession++;
            if (spiderBosses != -1) {
                spiderBosses++;
            }
            if (spiderBossesSession != -1) {
                spiderBossesSession++;
            }
            ConfigHandler.writeIntConfig("spider", "tarantulas", spiderTarantulas);
            ConfigHandler.writeIntConfig("spider", "bossRNG", spiderBosses);
        } else if (message.contains("RARE DROP! (Toxic Arrow Poison)")) {
            spiderTAPDrops++;
            spiderTAPDropsSession++;
            ConfigHandler.writeIntConfig("spider", "tapDrops", spiderTAPDrops);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" Bite Rune I)")) {
            spiderBites++;
            spiderBitesSession++;
            ConfigHandler.writeIntConfig("spider", "bite", spiderBites);
        } else if (message.contains("VERY RARE DROP!  (Spider Catalyst)")) {
            spiderCatalysts++;
            spiderCatalystsSession++;
            ConfigHandler.writeIntConfig("spider", "catalyst", spiderCatalysts);
        } else if (message.contains("CRAZY RARE DROP!  (Fly Swatter)")) {
            spiderRNG = true;
            spiderSwatters++;
            spiderSwattersSession++;
            ConfigHandler.writeIntConfig("spider", "swatter", spiderSwatters);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.LIGHT_PURPLE + "FLY SWATTER!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Tarantula Talisman")) {
            spiderRNG = true;
            spiderTalismans++;
            spiderTalismansSession++;
            ConfigHandler.writeIntConfig("spider", "talisman", spiderTalismans);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "TARANTULA TALISMAN!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Digested Mosquito)")) {
            spiderRNG = true;
            spiderMosquitos++;
            spiderMosquitosSession++;
            ConfigHandler.writeIntConfig("spider", "mosquito", spiderMosquitos);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "DIGESTED MOSQUITO!", 5);
        } else if (message.contains("   Zombie Slayer LVL ")) { // Zombie
            zombieRevs++;
            zombieRevsSession++;
            if (zombieBosses != -1) {
                zombieBosses++;
            }
            if (zombieBossesSession != 1) {
                zombieBossesSession++;
            }
            ConfigHandler.writeIntConfig("zombie", "revs", zombieRevs);
            ConfigHandler.writeIntConfig("zombie", "bossRNG", zombieBosses);
        } else if (message.contains("RARE DROP! (Foul Flesh)")) {
            zombieFoulFleshDrops++;
            zombieFoulFleshDropsSession++;
            ConfigHandler.writeIntConfig("zombie", "foulFleshDrops", zombieFoulFleshDrops);
        } else if (message.contains("VERY RARE DROP!  (Revenant Catalyst)")) {
            zombieRevCatas++;
            zombieRevCatasSession++;
            ConfigHandler.writeIntConfig("zombie", "revCatalyst", zombieRevCatas);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" Pestilence Rune I)")) {
            zombiePestilences++;
            zombiePestilencesSession++;
            ConfigHandler.writeIntConfig("zombie", "pestilence", zombiePestilences);
        } else if (message.contains("VERY RARE DROP!  (Undead Catalyst)")) {
            zombieUndeadCatas++;
            zombieUndeadCatasSession++;
            ConfigHandler.writeIntConfig("zombie", "undeadCatalyst", zombieUndeadCatas);
        } else if (message.contains("CRAZY RARE DROP!  (Beheaded Horror)")) {
            zombieRNG = true;
            zombieBeheadeds++;
            zombieBeheadedsSession++;
            ConfigHandler.writeIntConfig("zombie", "beheaded", zombieBeheadeds);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "BEHEADED HORROR!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Snake Rune I)")) {
            zombieRNG = true;
            zombieSnakes++;
            zombieSnakesSession++;
            ConfigHandler.writeIntConfig("zombie", "snake", zombieSnakes);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_GREEN + "SNAKE RUNE!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Scythe Blade)")) {
            zombieRNG = true;
            zombieScythes++;
            zombieScythesSession++;
            ConfigHandler.writeIntConfig("zombie", "scythe", zombieScythes);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "SCYTHE BLADE!", 5);
        } else if (message.contains("CRAZY RARE DROP!  (Shard of the Shredded)")) {
            zombieRNG = true;
            zombieShards++;
            zombieShardsSession++;
            ConfigHandler.writeIntConfig("zombie", "shard", zombieShards);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "SHARD OF THE SHREDDED!", 5);
        } else if (message.contains("INSANE DROP!  (Warden Heart)")) {
            zombieRNG = true;
            zombieWardenHearts++;
            zombieWardenHeartsSession++;
            ConfigHandler.writeIntConfig("zombie", "heart", zombieWardenHearts);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "WARDEN HEART!", 5);
        }

        if (wolfRNG) {
            wolfTime = System.currentTimeMillis() / 1000;
            wolfBosses = 0;
            wolfTimeSession = System.currentTimeMillis() / 1000;
            wolfBossesSession = 0;
            ConfigHandler.writeDoubleConfig("wolf", "timeRNG", wolfTime);
            ConfigHandler.writeIntConfig("wolf", "bossRNG", 0);
        }
        if (spiderRNG) {
            spiderTime = System.currentTimeMillis() / 1000;
            spiderBosses = 0;
            spiderTimeSession = System.currentTimeMillis() / 1000;
            spiderBossesSession = 0;
            ConfigHandler.writeDoubleConfig("spider", "timeRNG", spiderTime);
            ConfigHandler.writeIntConfig("spider", "bossRNG", 0);
        }
        if (zombieRNG) {
            zombieTime = System.currentTimeMillis() / 1000;
            zombieBosses = 0;
            zombieTimeSession = System.currentTimeMillis() / 1000;
            zombieBossesSession = 0;
            ConfigHandler.writeDoubleConfig("zombie", "timeRNG", zombieTime);
            ConfigHandler.writeIntConfig("zombie", "bossRNG", 0);
        }

        // Fishing tracker
        if (message.contains("GOOD CATCH!")) {
            goodCatches++;
            goodCatchesSession++;
            ConfigHandler.writeIntConfig("fishing", "goodCatch", goodCatches);
        } else if (message.contains("GREAT CATCH!")) {
            greatCatches++;
            greatCatchesSession++;
            ConfigHandler.writeIntConfig("fishing", "greatCatch", greatCatches);
        } else if (message.contains("A Squid appeared")) {
            squids++;
            squidsSession++;
            ConfigHandler.writeIntConfig("fishing", "squid", squids);
            increaseSeaCreatures();
        } else if (message.contains("You caught a Sea Walker")) {
            seaWalkers++;
            seaWalkersSession++;
            ConfigHandler.writeIntConfig("fishing", "seaWalker", seaWalkers);
            increaseSeaCreatures();
        } else if (message.contains("Pitch darkness reveals a Night Squid")) {
            nightSquids++;
            nightSquidsSession++;
            ConfigHandler.writeIntConfig("fishing", "nightSquid", nightSquids);
            increaseSeaCreatures();
        } else if (message.contains("You stumbled upon a Sea Guardian")) {
            seaGuardians++;
            seaGuardiansSession++;
            ConfigHandler.writeIntConfig("fishing", "seaGuardian", seaGuardians);
            increaseSeaCreatures();
        } else if (message.contains("It looks like you've disrupted the Sea Witch's brewing session. Watch out, she's furious")) {
            seaWitches++;
            seaWitchesSession++;
            ConfigHandler.writeIntConfig("fishing", "seaWitch", seaWitches);
            increaseSeaCreatures();
        } else if (message.contains("You reeled in a Sea Archer")) {
            seaArchers++;
            seaArchersSession++;
            ConfigHandler.writeIntConfig("fishing", "seaArcher", seaArchers);
            increaseSeaCreatures();
        } else if (message.contains("The Monster of the Deep has emerged")) {
            monsterOfTheDeeps++;
            monsterOfTheDeepsSession++;
            ConfigHandler.writeIntConfig("fishing", "monsterOfDeep", monsterOfTheDeeps);
            increaseSeaCreatures();
        } else if (message.contains("Huh? A Catfish")) {
            catfishes++;
            catfishesSession++;
            ConfigHandler.writeIntConfig("fishing", "catfish", catfishes);
            increaseSeaCreatures();
        } else if (message.contains("Is this even a fish? It's the Carrot King")) {
            carrotKings++;
            carrotKingsSession++;
            ConfigHandler.writeIntConfig("fishing", "carrotKing", carrotKings);
            increaseSeaCreatures();
        } else if (message.contains("Gross! A Sea Leech")) {
            seaLeeches++;
            seaLeechesSession++;
            ConfigHandler.writeIntConfig("fishing", "seaLeech", seaLeeches);
            increaseSeaCreatures();
        } else if (message.contains("You've discovered a Guardian Defender of the sea")) {
            guardianDefenders++;
            guardianDefendersSession++;
            ConfigHandler.writeIntConfig("fishing", "guardianDefender", guardianDefenders);
            increaseSeaCreatures();
        } else if (message.contains("You have awoken the Deep Sea Protector, prepare for a battle")) {
            deepSeaProtectors++;
            deepSeaProtectorsSession++;
            ConfigHandler.writeIntConfig("fishing", "deepSeaProtector", deepSeaProtectors);
            increaseSeaCreatures();
        } else if (message.contains("The Water Hydra has come to test your strength")) {
            hydras++;
            hydrasSession++;
            ConfigHandler.writeIntConfig("fishing", "hydra", hydras);
            increaseSeaCreatures();
        } else if (message.contains("The Sea Emperor arises from the depths")) {
            increaseSeaCreatures();

            seaEmperors++;
            empTime = System.currentTimeMillis() / 1000;
            empSCs = 0;
            seaEmperorsSession++;
            empTimeSession = System.currentTimeMillis() / 1000;
            empSCsSession = 0;
            ConfigHandler.writeIntConfig("fishing", "seaEmperor", seaEmperors);
            ConfigHandler.writeDoubleConfig("fishing", "empTime", empTime);
            ConfigHandler.writeIntConfig("fishing", "empSC", empSCs);
        } else if (message.contains("Frozen Steve fell into the pond long ago")) { // Fishing Winter
            frozenSteves++;
            frozenStevesSession++;
            ConfigHandler.writeIntConfig("fishing", "frozenSteve", frozenSteves);
            increaseSeaCreatures();
        } else if (message.contains("It's a snowman! He looks harmless")) {
            frostyTheSnowmans++;
            frostyTheSnowmansSession++;
            ConfigHandler.writeIntConfig("fishing", "snowman", frostyTheSnowmans);
            increaseSeaCreatures();
        } else if (message.contains("stole Jerry's Gifts...get them back")) {
            grinches++;
            grinchesSession++;
            ConfigHandler.writeIntConfig("fishing", "grinch", grinches);
            increaseSeaCreatures();
        } else if (message.contains("What is this creature")) {
            yetis++;
            yetiTime = System.currentTimeMillis() / 1000;
            yetiSCs = 0;
            yetisSession++;
            yetiTimeSession = System.currentTimeMillis() / 1000;
            yetiSCsSession = 0;
            ConfigHandler.writeIntConfig("fishing", "yeti", yetis);
            ConfigHandler.writeDoubleConfig("fishing", "yetiTime", yetiTime);
            ConfigHandler.writeIntConfig("fishing", "yetiSC", yetiSCs);
            increaseSeaCreatures();
        } else if (message.contains("A tiny fin emerges from the water, you've caught a Nurse Shark")) { // Fishing Festival
            nurseSharks++;
            nurseSharksSession++;
            ConfigHandler.writeIntConfig("fishing", "nurseShark", nurseSharks);
            increaseSeaCreatures();
        } else if (message.contains("You spot a fin as blue as the water it came from, it's a Blue Shark")) {
            blueSharks++;
            blueSharksSession++;
            ConfigHandler.writeIntConfig("fishing", "blueShark", blueSharks);
            increaseSeaCreatures();
        } else if (message.contains("A striped beast bounds from the depths, the wild Tiger Shark")) {
            tigerSharks++;
            tigerSharksSession++;
            ConfigHandler.writeIntConfig("fishing", "tigerShark", tigerSharks);
            increaseSeaCreatures();
        } else if (message.contains("Hide no longer, a Great White Shark has tracked your scent and thirsts for your blood")) {
            greatWhiteSharks++;
            greatWhiteSharksSession++;
            ConfigHandler.writeIntConfig("fishing", "greatWhiteShark", greatWhiteSharks);
            increaseSeaCreatures();
        } else if (message.contains("Phew! It's only a Scarecrow")) {
            scarecrows++;
            scarecrowsSession++;
            ConfigHandler.writeIntConfig("fishing", "scarecrow", scarecrows);
            increaseSeaCreatures();
        } else if (message.contains("You hear trotting from beneath the waves, you caught a Nightmare")) {
            nightmares++;
            nightmaresSession++;
            ConfigHandler.writeIntConfig("fishing", "nightmare", nightmares);
            increaseSeaCreatures();
        } else if (message.contains("It must be a full moon, a Werewolf appears")) {
            werewolfs++;
            werewolfsSession++;
            ConfigHandler.writeIntConfig("fishing", "werewolf", werewolfs);
            increaseSeaCreatures();
        } else if (message.contains("The spirit of a long lost Phantom Fisher has come to haunt you")) {
            phantomFishers++;
            phantomFishersSession++;
            ConfigHandler.writeIntConfig("fishing", "phantomFisher", phantomFishers);
            increaseSeaCreatures();
        } else if (message.contains("This can't be! The manifestation of death himself")) {
            grimReapers++;
            grimReapersSession++;
            ConfigHandler.writeIntConfig("fishing", "grimReaper", grimReapers);
            increaseSeaCreatures();
        }

        // Dungeons tracker
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

        // Mythological Tracker
        if (message.contains("You dug out")) {
            if (message.contains(" coins!")) {
                double coinsEarned = Double.parseDouble(message.replaceAll("[^\\d]", ""));
                mythCoins += coinsEarned;
                mythCoinsSession += coinsEarned;
                ConfigHandler.writeDoubleConfig("mythological", "coins", mythCoins);
            } else if (message.contains("a Griffin Feather!")) {
                griffinFeathers++;
                griffinFeathersSession++;
                ConfigHandler.writeIntConfig("mythological", "griffinFeather", griffinFeathers);
            } else if (message.contains("a Crown of Greed!")) {
                crownOfGreeds++;
                crownOfGreedsSession++;
                ConfigHandler.writeIntConfig("mythological", "crownOfGreed", crownOfGreeds);
            } else if (message.contains("a Washed-up Souvenir!")) {
                washedUpSouvenirs++;
                washedUpSouvenirsSession++;
                ConfigHandler.writeIntConfig("mythological", "washedUpSouvenir", washedUpSouvenirs);
            } else if (message.contains("a Minos Hunter!")) {
                minosHunters++;
                minosHuntersSession++;
                ConfigHandler.writeIntConfig("mythological", "minosHunter", minosHunters);
            } else if (message.contains("Siamese Lynxes!")) {
                siameseLynxes++;
                siameseLynxesSession++;
                ConfigHandler.writeIntConfig("mythological", "siameseLynx", siameseLynxes);
            } else if (message.contains("a Minotaur!")) {
                minotaurs++;
                minotaursSession++;
                ConfigHandler.writeIntConfig("mythological", "minotaur", minotaurs);
            } else if (message.contains("a Gaia Construct!")) {
                gaiaConstructs++;
                gaiaConstructsSession++;
                ConfigHandler.writeIntConfig("mythological", "gaiaConstruct", gaiaConstructs);
            } else if (message.contains("a Minos Champion!")) {
                minosChampions++;
                minosChampionsSession++;
                ConfigHandler.writeIntConfig("mythological", "minosChampion", minosChampions);
            } else if (message.contains("a Minos Inquisitor!")) {
                minosInquisitors++;
                minosInquisitorsSession++;
                ConfigHandler.writeIntConfig("mythological", "minosInquisitor", minosInquisitors);
            }
        }


        if (message.contains("RARE DROP!")) {
                if (message.contains("Sorrow")) {
                    sorrows++;
                    sorrowSession++;
                    ConfigHandler.writeIntConfig("ghosts", "sorrow", sorrows);
                }
                if (message.contains("Volta")) {
                    voltas++;
                    voltaSession++;
                    ConfigHandler.writeIntConfig("ghosts", "volta", voltas);
                }
                if (message.contains("Plasma")) {
                    plasmas++;
                    plasmaSession++;
                    ConfigHandler.writeIntConfig("ghosts", "plasma", plasmas);
                }
                if (message.contains("Ghostly Boots")) {
                    ghostlyBoots++;
                    ghostlyBootsSession++;
                    ConfigHandler.writeIntConfig("ghosts", "ghostlyBoots", ghostlyBoots);
                }
                if (message.contains("Bag of Cash")) {
                    bagOfCashs++;
                    bagOfCashSession++;
                    ConfigHandler.writeIntConfig("ghosts", "bagOfCash", bagOfCashs);
                }
            }
    }

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        ItemStack item = event.item;

        if (event.inventoryName.endsWith(" Chest") && item != null && item.getDisplayName().contains("Open Reward Chest")) {
            List<String> tooltip = item.getTooltip(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().gameSettings.advancedItemTooltips);
            for (String lineUnclean : tooltip) {
                String line = StringUtils.stripControlCodes(lineUnclean);
                if (line.contains("FREE")) {
                    break;
                } else if (line.contains(" Coins")) {
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
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onSound(PlaySoundEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.name.equals("note.pling")) {
            // Don't check twice within 3 seconds
            checkItemsNow = System.currentTimeMillis() / 1000;
            if (checkItemsNow - itemsChecked < 3) return;

            List<String> scoreboard = ScoreboardHandler.getSidebarLines();

            for (String line : scoreboard) {
                String cleanedLine = ScoreboardHandler.cleanSB(line);
                // If Hypixel lags and scoreboard doesn't update
                if (cleanedLine.contains("Boss slain!") || cleanedLine.contains("Slay the boss!")) {
                    int itemTeeth = Utils.getItems("Wolf Tooth");
                    int itemWheels = Utils.getItems("Hamster Wheel");
                    int itemWebs = Utils.getItems("Tarantula Web");
                    int itemTAP = Utils.getItems("Toxic Arrow Poison");
                    int itemRev = Utils.getItems("Revenant Flesh");
                    int itemFoul = Utils.getItems("Foul Flesh");

                    // If no items, are detected, allow check again. Should fix items not being found
                    if (itemTeeth + itemWheels + itemWebs + itemTAP + itemRev + itemFoul > 0) {
                        itemsChecked = System.currentTimeMillis() / 1000;
                        wolfTeeth += itemTeeth;
                        wolfWheels += itemWheels;
                        spiderWebs += itemWebs;
                        spiderTAP += itemTAP;
                        zombieRevFlesh += itemRev;
                        zombieFoulFlesh += itemFoul;
                        wolfTeethSession += itemTeeth;
                        wolfWheelsSession += itemWheels;
                        spiderWebsSession += itemWebs;
                        spiderTAPSession += itemTAP;
                        zombieRevFleshSession += itemRev;
                        zombieFoulFleshSession += itemFoul;

                        ConfigHandler.writeIntConfig("wolf", "teeth", wolfTeeth);
                        ConfigHandler.writeIntConfig("wolf", "wheel", wolfWheels);
                        ConfigHandler.writeIntConfig("spider", "web", spiderWebs);
                        ConfigHandler.writeIntConfig("spider", "tap", spiderTAP);
                        ConfigHandler.writeIntConfig("zombie", "revFlesh", zombieRevFlesh);
                        ConfigHandler.writeIntConfig("zombie", "foulFlesh", zombieFoulFlesh);
                    }
                }
            }
        }
    }
    
    public void increaseSeaCreatures() {
        if (empSCs != -1) {
            empSCs++;
        }
        if (empSCsSession != -1) {
            empSCsSession++;
        }
        // Only increment Yetis when in Jerry's Workshop
        List<String> scoreboard = ScoreboardHandler.getSidebarLines();
        for (String s : scoreboard) {
            String sCleaned = ScoreboardHandler.cleanSB(s);
            if (sCleaned.contains("Jerry's Workshop") || sCleaned.contains("Jerry Pond")) {
                if (yetiSCs != -1) {
                    yetiSCs++;
                }
                if (yetiSCsSession != -1) {
                    yetiSCsSession++;
                }
            }
        }

        seaCreatures++;
        fishingMilestone++;
        seaCreaturesSession++;
        fishingMilestoneSession++;
        ConfigHandler.writeIntConfig("fishing", "seaCreature", seaCreatures);
        ConfigHandler.writeIntConfig("fishing", "milestone", fishingMilestone);
        ConfigHandler.writeIntConfig("fishing", "empSC", empSCs);
        ConfigHandler.writeIntConfig("fishing", "yetiSC", yetiSCs);
    }
    
}
