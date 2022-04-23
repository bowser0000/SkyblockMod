package me.Danker.features.loot;

import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FishingTracker {

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
    // CH Fishing
    public static int waterWorms;
    public static int poisonedWaterWorms;
    public static int flamingWorms;
    public static int lavaBlazes;
    public static int lavaPigmen;
    public static int zombieMiners;
    // Lava fishing
    public static int magmaSlugs;
    public static int moogmas;
    public static int lavaLeeches;
    public static int pyroclasticWorms;
    public static int lavaFlames;
    public static int fireEels;
    public static int tauruses;
    public static int thunders;
    public static int lordJawbuses;
    public static double jawbusTime;
    public static int jawbusSCs;

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
    // CH Fishing
    public static int waterWormsSession = 0;
    public static int poisonedWaterWormsSession = 0;
    public static int flamingWormsSession = 0;
    public static int lavaBlazesSession = 0;
    public static int lavaPigmenSession = 0;
    public static int zombieMinersSession = 0;
    // Lava fishing
    public static int magmaSlugsSession = 0;
    public static int moogmasSession = 0;
    public static int lavaLeechesSession = 0;
    public static int pyroclasticWormsSession = 0;
    public static int lavaFlamesSession = 0;
    public static int fireEelsSession = 0;
    public static int taurusesSession = 0;
    public static int thundersSession = 0;
    public static int lordJawbusesSession = 0;
    public static double jawbusTimeSession = 0;
    public static int jawbusSCsSession = 0;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

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
        } else if (message.contains("A Water Worm surfaces")) {
            waterWorms++;
            waterWormsSession++;
            ConfigHandler.writeIntConfig("fishing", "waterWorm", waterWorms);
            increaseSeaCreatures();
        } else if (message.contains("A Poisoned Water Worm surfaces")) {
            poisonedWaterWorms++;
            poisonedWaterWormsSession++;
            ConfigHandler.writeIntConfig("fishing", "poisonedWaterWorm", poisonedWaterWorms);
            increaseSeaCreatures();
        } else if (message.contains("A flaming worm surfaces from the depths")) {
            flamingWorms++;
            flamingWormsSession++;
            ConfigHandler.writeIntConfig("fishing", "flamingWorm", flamingWorms);
            increaseSeaCreatures();
        } else if (message.contains("A Lava Blaze has surfaced from the depths")) {
            lavaBlazes++;
            lavaBlazesSession++;
            ConfigHandler.writeIntConfig("fishing", "lavaBlaze", lavaBlazes);
            increaseSeaCreatures();
        } else if (message.contains("A Lava Pigman arose from the depths")) {
            lavaPigmen++;
            lavaPigmenSession++;
            ConfigHandler.writeIntConfig("fishing", "lavaPigman", lavaPigmen);
            increaseSeaCreatures();
        } else if (message.contains("A Zombie Miner surfaces")) {
            zombieMiners++;
            zombieMinersSession++;
            ConfigHandler.writeIntConfig("fishing", "zombieMiner", zombieMiners);
            increaseSeaCreatures();
        } else if (message.contains("From beneath the lava appears a Magma Slug")) {
            magmaSlugs++;
            magmaSlugsSession++;
            ConfigHandler.writeIntConfig("fishing", "magmaSlug", magmaSlugs);
            increaseSeaCreatures();
        } else if (message.contains("You hear a faint Moo from the lava... A Moogma appears")) {
            moogmas++;
            moogmasSession++;
            ConfigHandler.writeIntConfig("fishing", "moogma", moogmas);
            increaseSeaCreatures();
        } else if (message.contains("A small but fearsome Lava Leech emerges")) {
            lavaLeeches++;
            lavaLeechesSession++;
            ConfigHandler.writeIntConfig("fishing", "lavaLeech", lavaLeeches);
            increaseSeaCreatures();
        } else if (message.contains("You feel the heat radiating as a Pyroclastic Worm surfaces")) {
            pyroclasticWorms++;
            pyroclasticWormsSession++;
            ConfigHandler.writeIntConfig("fishing", "pyroclasticWorm", pyroclasticWorms);
            increaseSeaCreatures();
        } else if (message.contains("A Lava Flame flies out from beneath the lava")) {
            lavaFlames++;
            lavaFlamesSession++;
            ConfigHandler.writeIntConfig("fishing", "lavaFlame", lavaFlames);
            increaseSeaCreatures();
        } else if (message.contains("A Fire Eel slithers out from the depths")) {
            fireEels++;
            fireEelsSession++;
            ConfigHandler.writeIntConfig("fishing", "fireEel", fireEels);
            increaseSeaCreatures();
        } else if (message.contains("Taurus and his steed emerge")) {
            tauruses++;
            taurusesSession++;
            ConfigHandler.writeIntConfig("fishing", "taurus", tauruses);
            increaseSeaCreatures();
        } else if (message.contains("You hear a massive rumble as Thunder emerges")) {
            thunders++;
            thundersSession++;
            ConfigHandler.writeIntConfig("fishing", "thunder", thunders);
            increaseSeaCreatures();
        } else if (message.contains("You have angered a legendary creature... Lord Jawbus has arrived")) {
            lordJawbuses++;
            jawbusTime = System.currentTimeMillis() / 1000;
            jawbusSCs = 0;
            lordJawbusesSession++;
            jawbusTimeSession = System.currentTimeMillis() / 1000;
            jawbusSCsSession = 0;
            ConfigHandler.writeIntConfig("fishing", "lordJawbus", lordJawbuses);
            ConfigHandler.writeDoubleConfig("fishing", "jawbusTime", jawbusTime);
            ConfigHandler.writeIntConfig("fishing", "jawbusSC", jawbusSCs);
            increaseSeaCreatures();
        }
    }

    public void increaseSeaCreatures() {
        // Only increment Yetis when in Jerry's Workshop
        if (Utils.isInScoreboard("Jerry's Workshop") || Utils.isInScoreboard("Jerry Pond")) {
            if (yetiSCs != -1) {
                yetiSCs++;
            }
            if (yetiSCsSession != -1) {
                yetiSCsSession++;
            }
        } else if (Utils.tabLocation.equals("Crimson Isle")) {
            if (jawbusSCs != -1) {
                jawbusSCs++;
            }
            if (jawbusSCsSession != -1) {
                jawbusSCsSession++;
            }
        } else {
            if (empSCs != -1) {
                empSCs++;
            }
            if (empSCsSession != -1) {
                empSCsSession++;
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
        ConfigHandler.writeIntConfig("fishing", "jawbusSC", jawbusSCs);
    }

}
