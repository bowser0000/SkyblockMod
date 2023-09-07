package me.Danker.config;


import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.Color;
import cc.polyfrost.oneconfig.config.annotations.Number;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.config.data.InfoType;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.data.OptionSize;
import cc.polyfrost.oneconfig.config.migration.CfgMigrator;
import cc.polyfrost.oneconfig.config.migration.CfgName;
import cc.polyfrost.oneconfig.libs.universal.UKeyboard;
import cc.polyfrost.oneconfig.utils.Notifications;
import me.Danker.DankersSkyblockMod;
import me.Danker.features.*;
import me.Danker.features.loot.LootDisplay;
import me.Danker.features.puzzlesolvers.LividSolver;
import me.Danker.features.puzzlesolvers.WaterSolver;
import me.Danker.gui.alerts.AlertsGui;
import me.Danker.gui.aliases.AliasesGui;
import me.Danker.gui.crystalhollowwaypoints.CrystalHollowWaypointsGui;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class ModConfig extends Config {

    @Exclude Minecraft mc = Minecraft.getMinecraft();
    @Exclude static String[] displays = {"Off", "Zombie Slayer", "Spider Slayer", "Wolf Slayer", "Enderman Slayer", "Blaze Slayer", "Vampire Slayer", "Fishing", "Winter Fishing", "Fishing Festival", "Spooky Fishing", "Crystal Hollows Fishing", "Lava Fishing", "Trophy Fishing", "Floor 1", "Floor 2", "Floor 3", "Floor 4", "Floor 5", "Floor 6", "Floor 7", "Master Mode", "Mythological", "Ghost"};

    public ModConfig() {
        super(new Mod("Danker's Skyblock Mod", ModType.SKYBLOCK, "/assets/dsm/icons/icon.png", new CfgMigrator("./config/Danker's Skyblock Mod.cfg")), "dsmconfig.json");
        initialize();

        registerKeyBind(maddoxKey, FasterMaddoxCalling::onKey);
        registerKeyBind(abilityKey, DankersSkyblockMod::onAbilityKey);
        registerKeyBind(waypointKey, CrystalHollowWaypoints::onKey);
        registerKeyBind(skillTrackerKey, SkillTracker::onKey);
        registerKeyBind(powderTrackerKey, PowderTracker::onKey);
        registerKeyBind(disableMouse, DisableMovement::onDisableMouse);
        registerKeyBind(disableWS, DisableMovement::onDisableWS);
        registerKeyBind(disableAD, DisableMovement::onDisableAD);

        addDependency("customNametags", "customColouredNames");
        addDependency("creeperLines", "creeper");
        addDependency("lowestBlazeColour", "blaze");
        addDependency("highestBlazeColour", "blaze");
        addDependency("slayerBoxColour", "highlightSlayers");
        addDependency("arachneBoxColour", "highlightArachne");
        addDependency("skeletonMasterBoxColour", "highlightSkeletonMasters");
        addDependency("slayerName", "blockSlayer");
        addDependency("slayerNumber", "blockSlayer");
        addDependency("ultrasequencerNextColour", "ultrasequencer");
        addDependency("ultrasequencerNextToNextColour", "ultrasequencer");
        addDependency("chronomatronNextColour", "chronomatron");
        addDependency("chronomatronNextToNextColour", "chronomatron");
        addDependency("clickInOrderNextColour", "clickInOrder");
        addDependency("clickInOrderNextToNextColour", "clickInOrder");
        addDependency("farmX", "endOfFarmAlert");
        addDependency("farmMinX", "endOfFarmAlert");
        addDependency("farmMinX", "farmX");
        addDependency("farmMaxX", "endOfFarmAlert");
        addDependency("farmMaxX", "farmX");
        addDependency("farmZ", "endOfFarmAlert");
        addDependency("farmMinZ", "endOfFarmAlert");
        addDependency("farmMinZ", "farmZ");
        addDependency("farmMaxZ", "endOfFarmAlert");
        addDependency("farmMaxZ", "farmZ");
        addDependency("boulderColour", "boulder");
        addDependency("boulderArrowColour", "boulder");
        addDependency("silverfishLineColour", "silverfish");
        addDependency("iceWalkLineColour", "iceWalk");
        addDependency("commissionColour", "highlightCommissions");
        addDependency("lastCollectedColour", "minionLastCollected");
        addDependency("ticTacToeColour", "ticTacToe");
        addDependency("startsWithColour", "startsWith");
        addDependency("selectAllColour", "selectAll");
        addDependency("autoWaypoints", "crystalHollowWaypoints");
        addDependency("autoPlayerWaypoints", "crystalHollowWaypoints");
        addDependency("autoImportWaypoints", "crystalHollowWaypoints");
        addDependency("permaWaypoints", "crystalHollowWaypoints");
        addDependency("permaWaypoints", "autoImportWaypoints");
        addDependency("copyWaypoints", "crystalHollowWaypoints");
        addDependency("importWaypoints", "crystalHollowWaypoints");
        addDependency("triviaWrongAnswerColour", "oruo");
        addDependency("threeManAnswerColour", "threeMan");
        addDependency("highlightOrdersColour", "highlightOrders");
        addDependency("hidePlayerArmourOnly", "hideArmour");
        addDependency("autoImportWaypoints", "autoWaypoints");
        addDependency("debugPacketsIn", "debug");
        addDependency("debugPacketsOut", "debug");
        addDependency("debugChat", "debug");
        addDependency("thunderAlert", "fishingAlert");
        addDependency("jawbusAlert", "fishingAlert");
        addDependency("gwAlert", "fishingAlert");
    }

    public static String getColour(int index) {
        return "§" + Integer.toHexString(index);
    }

    public static String getDelimiter() {
        return getColour(delimiterColour) + EnumChatFormatting.STRIKETHROUGH + "-----------------------------";
    }

    public static String getDisplay() {
        return displays[LootDisplay.display];
    }

    public static int toDisplay(String value) {
        return Arrays.asList(displays).indexOf(value);
    }

    /* categories:
    general
        api
        general
        slayers
        hide tooltips
        custom name colors
    puzzle solvers
        dungeons
        terminals
        experiments
    display
        options
        general
        dungeons
        misc.
    trackers
        loot tracker
        skill tracker
        powder tracker
    highlights
        general
        hitboxes
    alerts
        options
        custom alerts
        general
        dungeons
        farming
        fishing
    aliases
        custom aliases
    waypoints
        crystal hollows
    messages
        general
    music
        options
        music
    colors
        text
        pet highlight colors
    keybinds
        general
        dungeons
        waypoints
        trackers
        farming
    debug
    */

    // General

    @CfgName(
            name = "APIKey",
            category = "api"
    )
    @Text(
            name = "API Key",
            description = "API key used for commands.",
            secure = true,
            category = "General",
            subcategory = "API"
    )
    @HypixelKey
    public static String apiKey = "";

    @Button(
            name = "Generate New API Key",
            text = "Click",
            category = "General",
            subcategory = "API"
    )
    Runnable newKey = () -> mc.thePlayer.sendChatMessage("/api new");

    @CfgName(
            name = "ExpertiseLore",
            category = "toggles"
    )
    @Switch(
            name = "Expertise Kills In Lore",
            description = "Adds expertise kills to fishing rod tooltip.",
            category = "General",
            subcategory = "General"
    )
    public static boolean expertiseLore = false;

    @CfgName(
            name = "HidePetCandy",
            category = "toggles"
    )
    @Switch(
            name = "Hide Pet Candy",
            description = "Hide pet candy in pet tooltips.",
            category = "General",
            subcategory = "General"
    )
    public static boolean hidePetCandy = false;

    @CfgName(
            name = "GemstoneLore",
            category = "toggles"
    )
    @Switch(
            name = "Applied Gemstones in Lore",
            description = "Adds applied gemstones to item tooltip.",
            category = "General",
            subcategory = "General"
    )
    public static boolean gemstoneLore = false;

    @CfgName(
            name = "StopSalvageStarred",
            category = "toggles"
    )
    @Switch(
            name = "Stop Salvaging Starred Items",
            description = "Blocks salvaging starred items.",
            category = "General",
            subcategory = "General"
    )
    public static boolean stopSalvageStarred = false;

    @CfgName(
            name = "PickBlock",
            category = "toggles"
    )
    @Switch(
            name = "Auto-Swap to Pick Block",
            description = "Automatically changes left clicks to middle clicks.\nHelpful when lagging.",
            category = "General",
            subcategory = "General"
    )
    public static boolean swapToPickBlock = false;

    @CfgName(
            name = "FlowerWeapons",
            category = "toggles"
    )
    @Switch(
            name = "Prevent Placing FoT/Spirit Sceptre",
            description = "Blocks placing Flower of Truth or Spirit Sceptre.",
            category = "General",
            subcategory = "General"
    )
    public static boolean flowerWeapons = false;

    @CfgName(
            name = "HideArmour",
            category = "toggles"
    )
    @Switch(
            name = "Hide Player Armor",
            description = "Makes player armour invisible, showing their skin.",
            category = "General",
            subcategory = "General"
    )
    public static boolean hideArmour = false;

    @Switch(
            name = "Don't hide NPC armor",
            description = "If you have Hide Player Armor enabled, don't hide the armor of NPCs. 99% accurate",
            category = "General",
            subcategory = "General"
    )
    public static boolean hidePlayerArmourOnly = false;

    @CfgName(
            name = "AutoJoinSkyblock",
            category = "toggles"
    )
    @Switch(
            name = "Automatically Join Skyblock",
            description = "Automatically join Skyblock when you join Hypixel.\nYou have an addiction.",
            category = "General",
            subcategory = "General"
    )
    public static boolean autoJoinSkyblock = false;

    @CfgName(
            name = "AnnounceVanqs",
            category = "toggles"
    )
    @Switch(
            name = "Announce Vanquishers",
            description = "Announces when and at what coordinates your Vanquisher spawns in your selected chat.",
            category = "General",
            subcategory = "General"
    )
    public static boolean announceVanqs = false;

    @CfgName(
            name = "GParty",
            category = "toggles"
    )
    @Switch(
            name = "Guild Party Notifications",
            description = "Creates desktop notification on guild party.",
            category = "General",
            subcategory = "General"
    )
    public static boolean gparty = false;

    @CfgName(
            name = "Golden",
            category = "toggles"
    )
    @Switch(
            name = "Golden T10/T6/T4 Enchantments",
            description = "Turns expensive enchants golden in tooltips.",
            category = "General",
            subcategory = "General"
    )
    public static boolean golden = false;

    @Switch(
            name = "Fix Drill Animation Reset",
            description = "Fixes drill animation resetting when the fuel updates.",
            category = "General",
            subcategory = "General"
    )
    public static boolean drillFix = false;

    @CfgName(
            name = "AutoAcceptReparty",
            category = "toggles"
    )
    @Switch(
            name = "Auto Accept Reparty",
            description = "Automatically rejoins parties when disbanded and invited.",
            category = "General",
            subcategory = "Dungeons"
    )
    public static boolean autoAcceptReparty = false;

    @Switch(
            name = "RNG Meter Tracker",
            description = "Prints RNG meter info at the end of a dungeon run.\nOpen your RNG meter menu to sync selected drops.",
            category = "General",
            subcategory = "Dungeons"
    )
    public static boolean meterTracker = false;

    @CfgName(
            name = "ChatMaddox",
            category = "toggles"
    )
    @Switch(
            name = "Click On-Screen to Open Maddox",
            description = "Open chat then click anywhere after calling Maddox to open the menu.",
            category = "General",
            subcategory = "Slayers"
    )
    public static boolean chatMaddox = false;

    @Switch(
            name = "Only Allow Certain Slayer",
            description = "Blocks clicks on incorrect slayers.",
            size = OptionSize.DUAL,
            category = "General",
            subcategory = "Slayers"
    )
    public static boolean blockSlayer = false;

    @Dropdown(
            name = "Slayer Boss",
            options = {
                    "Revenant Horror",
                    "Tarantula Broodfather",
                    "Sven Packmaster",
                    "Voidgloom Seraph",
                    "Inferno Demonlord"
            },
            category = "General",
            subcategory = "Slayers"
    )
    public static int slayerName = 0;

    @Dropdown(
            name = "Slayer Tier",
            options = {
                    "I",
                    "II",
                    "III",
                    "IV",
                    "V"
            },
            category = "General",
            subcategory = "Slayers"
    )
    public static int slayerNumber = 4;

    @CfgName(
            name = "MelodyTooltips",
            category = "toggles"
    )
    @Switch(
            name = "Hide Tooltips in Melody's Harp",
            description = "Hides tooltips in Melody's Harp.",
            category = "General",
            subcategory = "Hide Tooltips"
    )
    public static boolean melodyTooltips = false;

    @Switch(
            name = "Hide Tooltips During Hacking",
            description = "Hides tooltips during hacking in the rift.",
            category = "General",
            subcategory = "Hide Tooltips"
    )
    public static boolean hackingTooltips = false;

    @CfgName(
            name = "HideTooltipsInExperimentAddons",
            category = "toggles"
    )
    @Switch(
            name = "Hide Tooltips in Addons",
            description = "Hides tooltips in experiment addons.",
            category = "General",
            subcategory = "Hide Tooltips"
    )
    public static boolean hideTooltipsInExperimentAddons = false;

    @CfgName(
            name = "CustomColouredNames",
            category = "toggles"
    )
    @Switch(
            name = "Custom Name Colors",
            description = "Replaces some player's usernames with a custom color.",
            category = "General",
            subcategory = "Custom Name Colors"
    )
    public static boolean customColouredNames = false;

    @CfgName(
            name = "CustomNametags",
            category = "toggles"
    )
    @Switch(
            name = "Custom Color on Nametags",
            description = "Displays custom name colors on nametags. Disabling will increase performance with custom colors.",
            category = "General",
            subcategory = "Custom Name Colors"
    )
    public static boolean customNametags = true;

    // Puzzle Solvers

    @HUD(
            name = "Find Correct Livid",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static LividSolver.LividSolverHud lividSolverHud = new LividSolver.LividSolverHud();

    @CfgName(
            name = "ThreeManPuzzle",
            category = "toggles"
    )
    @Switch(
            name = "Riddle Solver",
            description = "Solves the three man puzzle in dungeons.",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static boolean threeMan = false;

    @Dropdown(
            name = "Riddle Puzzle Answer Text Color",
            description = "Color for the solution to the three man dungeon puzzle.",
            options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"},
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static int threeManAnswerColour = 2;

    @CfgName(
            name = "OruoPuzzle",
            category = "toggles"
    )
    @Switch(
            name = "Trivia Solver",
            description = "Solves Oruo's trivia in dungeons",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static boolean oruo = false;

    @Dropdown(
            name = "Trivia Wrong Answer Text Color",
            description = "Color for the wrong answers in Oruo's trivia dungeon puzzle.",
            options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"},
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static int triviaWrongAnswerColour = 12;

    @CfgName(
            name = "BlazePuzzle",
            category = "toggles"
    )
    @Switch(
            name = "Blaze Solver",
            description = "Solves the shoot in order blaze puzzle in dungeons.",
            size = OptionSize.DUAL,
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static boolean blaze = false;

    @Color(
            name = "Lowest Blaze Color",
            description = "Color for the blaze with the lowest HP in the blaze dungeon puzzle.",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static OneColor lowestBlazeColour = new OneColor(255, 0, 0);

    @Color(
            name = "Highest Blaze Color",
            description = "Color for the blaze with the highest HP in the blaze dungeon puzzle.",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static OneColor highestBlazeColour = new OneColor(64, 255, 64);

    @CfgName(
            name = "CreeperPuzzle",
            category = "toggles"
    )
    @Switch(
            name = "Creeper Solver",
            description = "Solves the creeper beams puzzle in dungeons.",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static boolean creeper = false;

    @CfgName(
            name = "CreeperLines",
            category = "toggles"
    )
    @Switch(
            name = "Lines In Creeper Solver",
            description = "Adds lines connecting the matching blocks in the creeper puzzle solver.",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static boolean creeperLines = true;

    @CfgName(
            name = "TicTacToePuzzle",
            category = "toggles"
    )
    @Switch(
            name = "Tic Tac Toe Solver",
            description = "Solves the tic tac toe puzzle in dungeons.",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static boolean ticTacToe = false;

    @Color(
            name = "Correct Location Box Color",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static OneColor ticTacToeColour = new OneColor(64, 255, 64);

    @HUD(
            name = "Water Solver",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static WaterSolver.WaterSolverHud waterSolverHud = new WaterSolver.WaterSolverHud();

    @CfgName(
            name = "BoulderPuzzle",
            category = "toggles"
    )
    @Switch(
            name = "Boulder Solver",
            description = "Solves the boulder puzzle in dungeons.",
            size = OptionSize.DUAL,
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static boolean boulder = false;

    @Color(
            name = "Boulder to Push Color",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static OneColor boulderColour = new OneColor(25, 127, 25);

    @Color(
            name = "Boulder Direction Arrow Color",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static OneColor boulderArrowColour = new OneColor(0, 96, 0);

    @CfgName(
            name = "SilverfishPuzzle",
            category = "toggles"
    )
    @Switch(
            name = "Silverfish Solver",
            description = "Solves the silverfish puzzle in dungeons.",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static boolean silverfish = false;

    @Color(
            name = "Silverfish Line Color",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static OneColor silverfishLineColour = new OneColor(64, 255, 64);

    @CfgName(
            name = "IceWalkPuzzle",
            category = "toggles"
    )
    @Switch(
            name = "Ice Walk Solver",
            description = "Solves the ice walk puzzle in dungeons.",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static boolean iceWalk = false;

    @Color(
            name = "Ice Walk Line Color",
            category = "Puzzle Solvers",
            subcategory = "Dungeons"
    )
    public static OneColor iceWalkLineColour = new OneColor(64, 255, 64);

    @CfgName(
            name = "StartsWithTerminal",
            category = "toggles"
    )
    @Switch(
            name = "Starts With Letter Terminal Solver",
            description = "Solves the starts with letter terminal in F7.",
            category = "Puzzle Solvers",
            subcategory = "Terminals"
    )
    public static boolean startsWith = false;

    @Color(
            name = "Matching Item Color",
            category = "Puzzle Solvers",
            subcategory = "Terminals"
    )
    public static OneColor startsWithColour = new OneColor(64, 255, 64, 191);

    @CfgName(
            name = "SelectAllTerminal",
            category = "toggles"
    )
    @Switch(
            name = "Select All Color Terminal Solver",
            description = "Solves the select all matching color termianl in F7.",
            category = "Puzzle Solvers",
            subcategory = "Terminals"
    )
    public static boolean selectAll = false;

    @Color(
            name = "Matching Item Color",
            category = "Puzzle Solvers",
            subcategory = "Terminals"
    )
    public static OneColor selectAllColour = new OneColor(64, 255, 64, 191);

    @CfgName(
            name = "ClickInOrderTerminal",
            category = "toggles"
    )
    @Switch(
            name = "Click in Order Terminal Helper",
            description = "Solves the click in order terminal in F7.",
            size = OptionSize.DUAL,
            category = "Puzzle Solvers",
            subcategory = "Terminals"
    )
    public static boolean clickInOrder = false;

    @Color(
            name = "Next Slot to Click",
            category = "Puzzle Solvers",
            subcategory = "Terminals"
    )
    public static OneColor clickInOrderNextColour = new OneColor(255, 0, 221);

    @Color(
            name = "Next to Next Slot to Click",
            category = "Puzzle Solvers",
            subcategory = "Terminals"
    )
    public static OneColor clickInOrderNextToNextColour = new OneColor(11, 239, 231);

    @CfgName(
            name = "SameColourTerminal",
            category = "toggles"
    )
    @Switch(
            name = "Same Colour Terminal Solver",
            description = "Solves the make all same color terminal in F7.",
            category = "Puzzle Solvers",
            subcategory = "Terminals"
    )
    public static boolean sameColour = false;

    @CfgName(
            name = "BlockWrongTerminalClicks",
            category = "toggles"
    )
    @Switch(
            name = "Block Wrong Clicks on Terminals",
            description = "Blocks incorrect clicks in terminals.",
            category = "Puzzle Solvers",
            subcategory = "Terminals"
    )
    public static boolean blockWrongTerminalClicks = false;

    @CfgName(
            name = "IgnoreItemFrameOnSeaLanterns",
            category = "toggles"
    )
    @Switch(
            name = "Ignore Arrows On Sea Lanterns",
            description = "Prevents moving correct arrows in the F7 phase 3 arrow device.",
            category = "Puzzle Solvers",
            subcategory = "Terminals"
    )
    public static boolean itemFrameOnSeaLanterns = false;

    @CfgName(
            name = "UltraSequencer",
            category = "toggles"
    )
    @Switch(
            name = "Ultrasequencer Solver",
            description = "Solves the Ultrasequencer experiment.",
            size = OptionSize.DUAL,
            category = "Puzzle Solvers",
            subcategory = "Experiments"
    )
    public static boolean ultrasequencer = false;

    @Color(
            name = "Next Slot to Click",
            category = "Puzzle Solvers",
            subcategory = "Experiments"
    )
    public static OneColor ultrasequencerNextColour = new OneColor(64, 255, 64, 229);

    @Color(
            name = "Next to Next Slot to Click",
            category = "Puzzle Solvers",
            subcategory = "Experiments"
    )
    public static OneColor ultrasequencerNextToNextColour = new OneColor(64, 218, 230, 215);

    @CfgName(
            name = "Chronomatron",
            category = "toggles"
    )
    @Switch(
            name = "Chronomatron Solver",
            description = "Solves the Chronomatron experiment.",
            size = OptionSize.DUAL,
            category = "Puzzle Solvers",
            subcategory = "Experiments"
    )
    public static boolean chronomatron = false;

    @Color(
            name = "Next Slot to Click",
            category = "Puzzle Solvers",
            subcategory = "Experiments"
    )
    public static OneColor chronomatronNextColour = new OneColor(64, 255, 64, 229);

    @Color(
            name = "Next to Next Slot to Click",
            category = "Puzzle Solvers",
            subcategory = "Experiments"
    )
    public static OneColor chronomatronNextToNextColour = new OneColor(64, 218, 230, 190);

    @CfgName(
            name = "Superpairs",
            category = "toggles"
    )
    @Switch(
            name = "Superpairs Solver",
            description = "Solves the Superpairs experiment.",
            category = "Puzzle Solvers",
            subcategory = "Experiments"
    )
    public static boolean superpairs = false;

    // Display

    @CfgName(
            name = "OutlineText",
            category = "toggles"
    )
    @Switch(
            name = "Outline Displayed Text",
            description = "Adds bold outline to on-screen text.",
            category = "Display",
            subcategory = "Options"
    )
    public static boolean outlineText = false;

    @HUD(
            name = "Coordinate/Angle Display",
            category = "Display",
            subcategory = "General"
    )
    public static NoF3Coords coordsHud = new NoF3Coords();

    @HUD(
            name = "Display Progress to Max Skill Level",
            category = "Display",
            subcategory = "General"
    )
    public static Skill50Display.MaxSkillHud maxSkillHud = new Skill50Display.MaxSkillHud();

    @HUD(
            name = "Cake Timer",
            category = "Display",
            subcategory = "General"
    )
    public static CakeTimer.CakeTimerHud cakeTimerHud = new CakeTimer.CakeTimerHud();

    @HUD(
            name = "Ability Cooldowns",
            category = "Display",
            subcategory = "General"
    )
    public static AbilityCooldowns.AbilityCooldownHud abilityCooldownHud = new AbilityCooldowns.AbilityCooldownHud();

    @HUD(
            name = "Fire Pillar Display",
            category = "Display",
            subcategory = "General"
    )
    public static FirePillarDisplay.FirePillarHud firePillarHud = new FirePillarDisplay.FirePillarHud();

    @HUD(
            name = "Crimson Isle Miniboss Timer",
            category = "Display",
            subcategory = "General"
    )
    public static CrimsonMinibossTimer.MinibossTimerHud minibossTimerHud = new CrimsonMinibossTimer.MinibossTimerHud();

    @HUD(
            name = "Golem Spawn Timer",
            category = "Display",
            subcategory = "General"
    )
    public static GolemSpawningAlert.GolemTimerHud golemTimerHud = new GolemSpawningAlert.GolemTimerHud();

    @HUD(
            name = "Bonzo's Mask Timer",
            category = "Display",
            subcategory = "Dungeons"
    )
    public static BonzoMaskTimer.BonzoTimerHud bonzoTimerHud = new BonzoMaskTimer.BonzoTimerHud();

    @HUD(
            name = "Display Players in 30 Block Radius",
            category = "Display",
            subcategory = "Dungeons"
    )
    public static TetherDisplay.TetherDisplayHud tetherHud = new TetherDisplay.TetherDisplayHud();

    @HUD(
            name = "Display Giant HP",
            category = "Display",
            subcategory = "Dungeons"
    )
    public static GiantHPDisplay.GiantHPHud giantHPHud = new GiantHPDisplay.GiantHPHud();

    @HUD(
            name = "Display Dungeon Timers",
            category = "Display",
            subcategory = "Dungeons"
    )
    public static DungeonTimer.DungeonTimerHud dungeonTimerHud = new DungeonTimer.DungeonTimerHud();

    @HUD(
            name = "Dungeon Score Display",
            category = "Display",
            subcategory = "Dungeons"
    )
    public static DungeonScore.DungeonScoreHud dungeonScoreHud = new DungeonScore.DungeonScoreHud();

    @CfgName(
            name = "MinionLastCollected",
            category = "toggles"
    )
    @Switch(
            name = "Show When Minion Last Collected",
            description = "Displays when a minion was last collected over the minion.",
            category = "Display",
            subcategory = "Misc."
    )
    public static boolean minionLastCollected = false;

    @Color(
            name = "Minion Last Collected Color",
            category = "Display",
            subcategory = "Misc."
    )
    public static OneColor lastCollectedColour = new OneColor(85, 255, 255);

    @CfgName(
            name = "ShowTotalMagmafish",
            category = "toggles"
    )
    @Switch(
            name = "Show Total Fillet Magmafish",
            description = "Show total Magmafish you would get if you filleted all the trophy fish in your inventory.",
            category = "Display",
            subcategory = "Misc."
    )
    public static boolean showTotalMagmafish = false;

    @CfgName(
            name = "BazaarTimeToFill",
            category = "toggles"
    )
    @Switch(
            name = "Show Time to Fill Bazaar Order",
            description = "Shows an estimated amount of time it would take for a bazaar order to be filled.\nAssumes you are not over/undercut.\nOnly works when moving through menus. Doesn't work when clicking items as shortcut.",
            category = "Display",
            subcategory = "Misc."
    )
    public static boolean bazaarTimeToFill = false;

    // Trackers

    @HUD(
            name = "Loot Display",
            category = "Trackers"
    )
    public static LootDisplay lootDisplayHud = new LootDisplay();

    @HUD(
            name = "Skill Tracker",
            category = "Trackers"
    )
    public static SkillTracker.SkillTrackerHud skillTrackerHud = new SkillTracker.SkillTrackerHud();

    @HUD(
            name = "Powder Tracker",
            category = "Trackers"
    )
    public static PowderTracker.PowderTrackerHud powderTrackerHud = new PowderTracker.PowderTrackerHud();

    // Highlights

    @CfgName(
            name = "PetColors",
            category = "toggles"
    )
    @Switch(
            name = "Color Pet Backgrounds",
            description = "Colors pets based on their level.",
            category = "Highlights",
            subcategory = "General"
    )
    public static boolean petColours = false;

    @Info(
            text = "Pet highlight colors are in the colors category.",
            type = InfoType.INFO,
            category = "Highlights",
            subcategory = "General"
    )
    public static boolean ignored1;

    @CfgName(
            name = "HighlightCommissions",
            category = "toggles"
    )
    @Switch(
            name = "Highlight Completed Commissions",
            description = "Show which commissions are completed.",
            category = "Highlights",
            subcategory = "General"
    )
    public static boolean highlightCommissions = false;

    @Color(
            name = "Commission Highlight Color",
            category = "Highlights",
            subcategory = "General"
    )
    public static OneColor commissionColour = new OneColor(81, 255, 81, 215);

   @Switch(
            name = "Highlight Filled Orders",
            description = "Show which bazaar orders have been filled.",
            category = "Highlights",
            subcategory = "General"
    )
    public static boolean highlightOrders = false;

    @Color(
            name = "Filled Order Highlight Color",
            category = "Highlights",
            subcategory = "General"
    )
    public static OneColor highlightOrdersColour = new OneColor(81, 255, 81, 215);

    @CfgName(
            name = "HighlightSlayers",
            category = "toggles"
    )
    @Switch(
            name = "Highlight Slayer Boss",
            description = "Draws a box around Slayer bosses.",
            category = "Highlights",
            subcategory = "Hitboxes"
    )
    public static boolean highlightSlayers = false;

    @Color(
            name = "Slayer Box Color",
            category = "Highlights",
            subcategory = "Hitboxes"
    )
    public static OneColor slayerBoxColour = new OneColor(0, 0, 255);

    @CfgName(
            name = "HighlightArachne",
            category = "toggles"
    )
    @Switch(
            name = "Highlight Arachne",
            description = "Draws a box around Arachne.",
            category = "Highlights",
            subcategory = "Hitboxes"
    )
    public static boolean highlightArachne = false;

    @Color(
            name = "Arachne Box Color",
            category = "Highlights",
            subcategory = "Hitboxes"
    )
    public static OneColor arachneBoxColour = new OneColor(0, 255, 0);

    @CfgName(
            name = "HighlightSkeletonMasters",
            category = "toggles"
    )
    @Switch(
            name = "Highlight Skeleton Masters",
            description = "Draws a box around Skeleton Masters.",
            category = "Highlights",
            subcategory = "Hitboxes"
    )
    public static boolean highlightSkeletonMasters = false;

    @Color(
            name = "Skeleton Master Box Color",
            category = "Highlights",
            subcategory = "Hitboxes"
    )
    public static OneColor skeletonMasterBoxColour = new OneColor(255, 0, 0);

    // Alerts

    @CfgName(
            name = "titleSound",
            category = "misc"
    )
    @Text(
            name = "Alert Noise",
            description = "The Minecraft sound that plays when an alert is made.",
            category = "Alerts",
            subcategory = "Options"
    )
    public static String alertNoise = "random.orb";

    @CfgName(
            name = "Alerts",
            category = "toggles"
    )
    @Switch(
            name = "Custom Alerts",
            description = "Sends custom alert when a message is recieved",
            category = "Alerts",
            subcategory = "Custom Alerts"
    )
    public static boolean alerts = false;

    @Button(
            name = "Custom Alerts",
            text = "Click",
            category = "Alerts",
            subcategory = "Custom Alerts"
    )
    Runnable alertsButton = () -> mc.displayGuiScreen(new AlertsGui(1));

    @CfgName(
            name = "GolemAlerts",
            category = "toggles"
    )
    @Switch(
            name = "Golem Spawn Alert And Timer",
            description = "Creates alert with 20s countdown when golem is spawning.",
            category = "Alerts",
            subcategory = "General"
    )
    public static boolean golemAlerts = false;

    @CfgName(
            name = "RNGesusAlerts",
            category = "toggles"
    )
    @Switch(
            name = "Slayer RNGesus Alerts",
            description = "Alerts when an RNGesus item is dropped.",
            category = "Alerts",
            subcategory = "General"
    )
    public static boolean rngesusAlerts = false;

    @CfgName(
            name = "NotifySlayerSlain",
            category = "toggles"
    )
    @Switch(
            name = "Notify when Slayer Slain",
            description = "Alerts when slayer boss has been slain.",
            category = "Alerts",
            subcategory = "General"
    )
    public static boolean notifySlayerSlain = false;

    @CfgName(
            name = "KuudraNotifications",
            category = "toggles"
    )
    @Switch(
            name = "Kuudra Notifications",
            description = "Alerts when to cloak for dropships, when Kuudra is stunned and when Ballista is fully charged.",
            category = "Alerts",
            subcategory = "General"
    )
    public static boolean kuudraNotifications = false;

    @CfgName(
            name = "SpiritBearAlerts",
            category = "toggles"
    )
    @Switch(
            name = "Spirit Bear Spawn Alerts",
            description = "Alert when Spirit Bear spawns.",
            category = "Alerts",
            subcategory = "Dungeons"
    )
    public static boolean spiritBearAlerts = false;

    @CfgName(
            name = "LowHealthNotify",
            category = "toggles"
    )
    @Switch(
            name = "Low Health Notifications",
            description = "Alerts when dungeon teammate has low health.",
            category = "Alerts",
            subcategory = "Dungeons"
    )
    public static boolean lowHealthNotify = false;

    @CfgName(
            name = "WatcherReadyMessage",
            category = "toggles"
    )
    @Switch(
            name = "Display Watcher Ready Message",
            description = "Alerts when Watcher finishes spawning mobs.",
            category = "Alerts",
            subcategory = "Dungeons"
    )
    public static boolean watcherReady = false;

    @CfgName(
            name = "NecronNotifications",
            category = "toggles"
    )
    @Switch(
            name = "Floor 7 Phase Notifications",
            description = "Creates alert on different phases of the floor 7 fight.",
            category = "Alerts",
            subcategory = "Dungeons"
    )
    public static boolean necronNotifications = false;

    @CfgName(
            name = "EndOfFarmAlert",
            category = "toggles"
    )
    @Switch(
            name = "Alert When Reaching End of Farm",
            description = "Alerts when you go past certain coords.",
            size = OptionSize.DUAL,
            category = "Alerts",
            subcategory = "Farming"
    )
    public static boolean endOfFarmAlert = false;

    @Switch(
            name = "Alert Based on X Coordinates",
            description = "Alerts when you go past X coords.",
            size = OptionSize.DUAL,
            category = "Alerts",
            subcategory = "Farming"
    )
    public static boolean farmX = true;

    @CfgName(
            name = "farmMin",
            category = "misc"
    )
    @Number(
            name = "Minimum X Coords",
            description = "When your coords go below this number, the alert will sound.",
            min = -300F, max = 300F,
            category = "Alerts",
            subcategory = "Farming"
    )
    public static float farmMinX = -220F;

    @CfgName(
            name = "farmMax",
            category = "misc"
    )
    @Number(
            name = "Maximum X Coords",
            description = "When your coords go above this number, the alert will sound.",
            min = -300F, max = 300F,
            category = "Alerts",
            subcategory = "Farming"
    )
    public static float farmMaxX = 220F;

    @Switch(
            name = "Alert Based on Z Coordinates",
            description = "Alerts when you go past Z coords.",
            size = OptionSize.DUAL,
            category = "Alerts",
            subcategory = "Farming"
    )
    public static boolean farmZ = true;

    @CfgName(
            name = "farmMin",
            category = "misc"
    )
    @Number(
            name = "Minimum Z Coords",
            description = "When your coords go below this number, the alert will sound.",
            min = -300F, max = 300F,
            category = "Alerts",
            subcategory = "Farming"
    )
    public static float farmMinZ = -220F;

    @CfgName(
            name = "farmMax",
            category = "misc"
    )
    @Number(
            name = "Maximum Z Coords",
            description = "When your coords go above this number, the alert will sound.",
            min = -300F, max = 300F,
            category = "Alerts",
            subcategory = "Farming"
    )
    public static float farmMaxZ = 220F;

    @CfgName(
            name = "FishingAlert",
            category = "toggles"
    )
    @Switch(
            name = "Fishing Spawn Alerts",
            description = "Alerts when a mob is fished up nearby.",
            category = "Alerts",
            subcategory = "Fishing"
    )
    public static boolean fishingAlert = false;

    @Switch(
            name = "Thunder Spawn Alert",
            description = "Alert when a Thunder is fished up nearby.",
            category = "Alerts",
            subcategory = "Fishing"
    )
    public static boolean thunderAlert = true;

    @Switch(
            name = "Jawbus Spawn Alert",
            description = "Alert when a Jawbus is fished up nearby.",
            category = "Alerts",
            subcategory = "Fishing"
    )
    public static boolean jawbusAlert = true;

    @Switch(
            name = "Great White Shark Spawn Alert",
            description = "Alert when a Great White Shark is fished up nearby.",
            category = "Alerts",
            subcategory = "Fishing"
    )
    public static boolean gwAlert = true;

    // Aliases

    @CfgName(
            name = "Aliases",
            category = "toggles"
    )
    @Switch(
            name = "Custom Aliases",
            description = "Replaces text in chat with an alias.",
            category = "Aliases",
            subcategory = "Custom Aliases"
    )
    public static boolean aliases = false;

    @Button(
            name = "Custom Aliases",
            text = "Click",
            category = "Aliases",
            subcategory = "Custom Aliases"
    )
    Runnable aliasesButton = () -> mc.displayGuiScreen(new AliasesGui(1));

    // Waypoints

    @CfgName(
            name = "CrystalHollowWaypoints",
            category = "toggles"
    )
    @Switch(
            name = "Crystal Hollows Waypoints",
            description = "Shows waypoints to various places in the Crystal Hollows.",
            category = "Waypoints",
            subcategory = "Crystal Hollows"
    )
    public static boolean crystalHollowWaypoints = false;

    @Button(
            name = "Crystal Hollow Waypoints",
            text = "Click",
            category = "Waypoints",
            subcategory = "Crystal Hollows"
    )
    Runnable crystalHollowWaypointsButton = () -> mc.displayGuiScreen(new CrystalHollowWaypointsGui(1));

    @CfgName(
            name = "CrystalAutoWaypoints",
            category = "toggles"
    )
    @Switch(
            name = "Auto Waypoints",
            description = "Automatically creates waypoints when you visit a special place in the Crystal Hollows.",
            category = "Waypoints",
            subcategory = "Crystal Hollows"
    )
    public static boolean autoWaypoints = true;

    @CfgName(
            name = "CrystalAutoPlayerWaypoints",
            category = "toggles"
    )
    @Switch(
            name = "Auto Add Player Waypoints",
            description = "Automatically adds waypoints sent from players.",
            category = "Waypoints",
            subcategory = "Crystal Hollows"
    )
    public static boolean autoPlayerWaypoints = false;

    @Switch(
            name = "Auto Import Waypoints",
            description = "Automatically import DSM formatted waypoints from the text field when you join a Crystal Hollows lobby.",
            category = "Waypoints",
            subcategory = "Crystal Hollows"
    )
    public static boolean autoImportWaypoints = true;

    @Text(
            name = "Import These Waypoints",
            multiline = true,
            size = OptionSize.DUAL,
            category = "Waypoints",
            subcategory = "Crystal Hollows"
    )
    public static String permaWaypoints = "Crystal Nucleus@-512,110,512";

    @Button(
            name = "Copy Waypoints to Clipboard",
            text = "Copy",
            description = "Copies current waypoints to your clipboard in a DSM format.",
            category = "Waypoints",
            subcategory = "Crystal Hollows"
    )
    Runnable copyWaypoints = CrystalHollowWaypoints::copyToClipboard;

    @Button(
            name = "Import Waypoints from Clipboard",
            text = "Import",
            description = "Imports Skytils, DSM/SBE, or Soopy waypoints from your clipboard.",
            category = "Waypoints",
            subcategory = "Crystal Hollows"
    )
    Runnable importWaypoints = CrystalHollowWaypoints::importWaypoints;

    @Button(
            name = "Optimize Waypoints",
            text = "Click",
            description = "Optimizes the waypoint path for the minimum cycle distance.",
            category = "Waypoints",
            subcategory = "Crystal Hollows"
    )
    Runnable optimizeCoords = () -> new Thread(CrystalHollowWaypoints::optimize).start();

    @Info(
            text = "Run multiple times for best results.",
            type = InfoType.INFO,
            category = "Waypoints",
            subcategory = "Crystal Hollows"
    )
    public static boolean ignored2;

    @Number(
            name = "Alpha",
            description = "Controls pheromone importance.",
            min = 1, max = 100,
            category = "Waypoints",
            subcategory = "Crystal Hollows"
    )
    public static int coordAlpha = 10;

    @Number(
            name = "Beta",
            description = "Controls distance importance.",
            min = 1, max = 500,
            category = "Waypoints",
            subcategory = "Crystal Hollows"
    )
    public static int coordBeta = 50;

    // Messages

    @CfgName(
            name = "SceptreMessages",
            category = "toggles"
    )
    @Switch(
            name = "Spirit Sceptre Messages",
            description = "Turn off to hide Spirit Sceptre messages.",
            category = "Messages",
            subcategory = "General"
    )
    public static boolean sceptreMessages = true;

    @CfgName(
            name = "MidasStaffMessages",
            category = "toggles"
    )
    @Switch(
            name = "Midas Staff Messages",
            description = "Turn off to hide Midas Staff messages.",
            category = "Messages",
            subcategory = "General"
    )
    public static boolean midasStaffMessages = true;

    @CfgName(
            name = "ImplosionMessages",
            category = "toggles"
    )
    @Switch(
            name = "Implosion Messages",
            description = "Turn off to hide Implosion messages.",
            category = "Messages",
            subcategory = "General"
    )
    public static boolean implosionMessages = true;

    @CfgName(
            name = "HealMessages",
            category = "toggles"
    )
    @Switch(
            name = "Heal Messages",
            description = "Turn off to hide healing messages.",
            category = "Messages",
            subcategory = "General"
    )
    public static boolean healMessages = true;

    @CfgName(
            name = "CooldownMessages",
            category = "toggles"
    )
    @Switch(
            name = "Cooldown Messages",
            description = "Turn off to hide cooldown messages.",
            category = "Messages",
            subcategory = "General"
    )
    public static boolean cooldownMessages = true;

    @CfgName(
            name = "ManaMessages",
            category = "toggles"
    )
    @Switch(
            name = "Mana Messages",
            description = "Turn off to hide out of mana messages.",
            category = "Messages",
            subcategory = "General"
    )
    public static boolean manaMessages = true;

    @CfgName(
            name = "KillComboMessages",
            category = "toggles"
    )
    @Switch(
            name = "Kill Combo Messages",
            description = "Turn off to hide kill combo messages.",
            category = "Messages",
            subcategory = "General"
    )
    public static boolean killComboMessages = true;

    // Music

    @Button(
            name = "Instructions",
            description = "Click to learn how to use custom music.",
            text = "Click",
            category = "Music"
    )
    Runnable musicInstructions = () -> {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/bowser0000/SkyblockMod/blob/master/README.md#custom-music"));
        } catch (IOException | URISyntaxException ex) {
            Notifications.INSTANCE.send("Error", "Error opening web page.");
            ex.printStackTrace();
        }
    };

    @Button(
            name = "Reload Custom Music",
            text = "Click",
            category = "Music",
            subcategory = "Options"
    )
    Runnable reloadMusic = () -> {
        CustomMusic.init(DankersSkyblockMod.configDirectory);
        Notifications.INSTANCE.send("Success", "Reloaded custom music.");
    };

    @CfgName(
            name = "DisableHypixelMusic",
            category = "toggles"
    )
    @Switch(
            name = "Disable Hypixel Music",
            description = "Disable the noteblock music Hypixel plays in certain areas when custom music is playing.\nThis can be turned off manually in Skyblock Menu -> Settings -> Personal -> Sounds -> Play Music",
            category = "Music",
            subcategory = "Options"
    )
    public static boolean disableHypixelMusic = true;

    @CfgName(
            name = "DungeonBossMusic",
            category = "toggles"
    )
    @Switch(
            name = "Dungeon Boss Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean dungeonBossMusic = false;

    @CfgName(
            name = "DungeonBossVolume",
            category = "music"
    )
    @Slider(
            name = "Dungeon Boss Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int dungeonBossVolume = 50;

    @CfgName(
            name = "Phase1Volume",
            category = "music"
    )
    @Slider(
            name = "F7 Phase 1 Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int phase1Volume = 50;

    @CfgName(
            name = "Phase2Volume",
            category = "music"
    )
    @Slider(
            name = "F7 Phase 2 Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int phase2Volume = 50;

    @CfgName(
            name = "Phase3Volume",
            category = "music"
    )
    @Slider(
            name = "F7 Phase 3 Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int phase3Volume = 50;

    @CfgName(
            name = "Phase4Volume",
            category = "music"
    )
    @Slider(
            name = "F7 Phase 4 Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int phase4Volume = 50;

    @CfgName(
            name = "Phase5Volume",
            category = "music"
    )
    @Slider(
            name = "F7 Phase 5 Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int phase5Volume = 50;

    @CfgName(
            name = "BloodRoomMusic",
            category = "toggles"
    )
    @Switch(
            name = "Blood Room Open Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean bloodRoomMusic = false;

    @CfgName(
            name = "BloodRoomVolume",
            category = "music"
    )
    @Slider(
            name = "Blood Room Open Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int bloodRoomVolume = 50;

    @CfgName(
            name = "DungeonMusic",
            category = "toggles"
    )
    @Switch(
            name = "Dungeon Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean dungeonMusic = false;

    @CfgName(
            name = "DungeonVolume",
            category = "music"
    )
    @Slider(
            name = "Dungeon Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int dungeonVolume = 50;

    @CfgName(
            name = "HubMusic",
            category = "toggles"
    )
    @Switch(
            name = "Hub Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean hubMusic = false;

    @CfgName(
            name = "HubVolume",
            category = "music"
    )
    @Slider(
            name = "Hub Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int hubVolume = 50;

    @CfgName(
            name = "IslandMusic",
            category = "toggles"
    )
    @Switch(
            name = "Private Island Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean islandMusic = false;

    @CfgName(
            name = "IslandVolume",
            category = "music"
    )
    @Slider(
            name = "Private Island Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int islandVolume = 50;

    @CfgName(
            name = "DungeonHubMusic",
            category = "toggles"
    )
    @Switch(
            name = "Dungeon Hub Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean dungeonHubMusic = false;

    @CfgName(
            name = "DungeonHubVolume",
            category = "music"
    )
    @Slider(
            name = "Dungeon Hub Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int dungeonHubVolume = 50;

    @CfgName(
            name = "FarmingIslandsMusic",
            category = "toggles"
    )
    @Switch(
            name = "Farming Islands Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean farmingIslandsMusic = false;

    @CfgName(
            name = "FarmingIslandsVolume",
            category = "music"
    )
    @Slider(
            name = "Farming Islands Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int farmingIslandsVolume = 50;

    @Switch(
            name = "Garden Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean gardenMusic = false;

    @Slider(
            name = "Garden Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int gardenVolume = 50;

    @CfgName(
            name = "GoldMineMusic",
            category = "toggles"
    )
    @Switch(
            name = "Gold Mine Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean goldMineMusic = false;

    @CfgName(
            name = "GoldMineVolume",
            category = "music"
    )
    @Slider(
            name = "Gold Mine Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int goldMineVolume = 50;

    @CfgName(
            name = "DeepCavernsMusic",
            category = "toggles"
    )
    @Switch(
            name = "Deep Caverns Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean deepCavernsMusic = false;

    @CfgName(
            name = "DeepCavernsVolume",
            category = "music"
    )
    @Slider(
            name = "Deep Caverns Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int deepCavernsVolume = 50;

    @CfgName(
            name = "DwarvenMinesMusic",
            category = "toggles"
    )
    @Switch(
            name = "Dwarven Mines Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean dwarvenMinesMusic = false;

    @CfgName(
            name = "DwarvenMinesVolume",
            category = "music"
    )
    @Slider(
            name = "Dwarven Mines Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int dwarvenMinesVolume = 50;

    @CfgName(
            name = "CrystalHollowsMusic",
            category = "toggles"
    )
    @Switch(
            name = "Crystal Hollows Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean crystalHollowsMusic = false;

    @CfgName(
            name = "CrystalHollowsVolume",
            category = "music"
    )
    @Slider(
            name = "Crystal Hollows Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int crystalHollowsVolume = 50;

    @CfgName(
            name = "SpidersDenMusic",
            category = "toggles"
    )
    @Switch(
            name = "Spider's Den Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean spidersDenMusic = false;

    @CfgName(
            name = "SpidersDenVolume",
            category = "music"
    )
    @Slider(
            name = "Spider's Den Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int spidersDenVolume = 50;

    @CfgName(
            name = "CrimsonIsleMusic",
            category = "toggles"
    )
    @Switch(
            name = "Crimson Isle Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean crimsonIsleMusic = false;

    @CfgName(
            name = "CrimsonIsleVolume",
            category = "music"
    )
    @Slider(
            name = "Crimson Isle Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int crimsonIsleVolume = 50;

    @Switch(
            name = "Kuudra Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean kuudraMusic = false;

    @Slider(
            name = "Kuudra Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int kuudraVolume = 50;

    @CfgName(
            name = "EndMusic",
            category = "toggles"
    )
    @Switch(
            name = "End Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean endMusic = false;

    @CfgName(
            name = "EndVolume",
            category = "music"
    )
    @Slider(
            name = "End Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int endVolume = 50;

    @CfgName(
            name = "ParkMusic",
            category = "toggles"
    )
    @Switch(
            name = "Park Music",
            category = "Music",
            subcategory = "Music"
    )
    public static boolean parkMusic = false;

    @CfgName(
            name = "ParkVolume",
            category = "music"
    )
    @Slider(
            name = "Park Music Volume",
            min = 0, max = 100,
            category = "Music",
            subcategory = "Music"
    )
    public static int parkVolume = 50;

    // Colors
    /* List of colours, copy paste
    {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"}
    */

    @Dropdown(
            name = "Main Text Color",
            options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"},
            category = "Colors",
            subcategory = "Text"
    )
    public static int mainColour = 10;

    @Dropdown(
            name = "Secondary Text Color",
            options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"},
            category = "Colors",
            subcategory = "Text"
    )
    public static int secondaryColour = 2;

    @Dropdown(
            name = "Delimiter Text Color",
            description = "Color of the line shown above and below command outputs. Strike-through is automatically added.",
            options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"},
            category = "Colors",
            subcategory = "Text"
    )
    public static int delimiterColour = 11;

    @Dropdown(
            name = "Error Text Color",
            options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"},
            category = "Colors",
            subcategory = "Text"
    )
    public static int errorColour = 12;

    @Dropdown(
            name = "Type Text Color",
            description = "Used in API commands to color a stat.",
            options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"},
            category = "Colors",
            subcategory = "Text"
    )
    public static int typeColour = 10;

    @Dropdown(
            name = "Value Text Color",
            description = "Used in API commands to color a stat value.",
            options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"},
            category = "Colors",
            subcategory = "Text"
    )
    public static int valueColour = 2;

    @Dropdown(
            name = "Skill Average Text Color",
            description = "Used in API commands to color the skill average number.",
            options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"},
            category = "Colors",
            subcategory = "Text"
    )
    public static int skillAverageColour = 6;

    @Color(
            name = "Pet Levels 1 to 9 Color",
            category = "Colors",
            subcategory = "Pet Highlight Colors"
    )
    public static OneColor pet1To9Colour = new OneColor(153, 153, 153, 191); // Grey

    @Color(
            name = "Pet Levels 10 to 19 Color",
            category = "Colors",
            subcategory = "Pet Highlight Colors"
    )
    public static OneColor pet10To19Colour = new OneColor(214, 36, 64, 191); // Red

    @Color(
            name = "Pet Levels 20 to 29 Color",
            category = "Colors",
            subcategory = "Pet Highlight Colors"
    )
    public static OneColor pet20To29Colour = new OneColor(239, 82, 48, 191); // Orange

    @Color(
            name = "Pet Levels 30 to 39 Color",
            category = "Colors",
            subcategory = "Pet Highlight Colors"
    )
    public static OneColor pet30To39Colour = new OneColor(17, 60, 242, 191); // Dark Blue

    @Color(
            name = "Pet Levels 40 to 49 Color",
            category = "Colors",
            subcategory = "Pet Highlight Colors"
    )
    public static OneColor pet40To49Colour = new OneColor(14, 172, 53, 191); // Green

    @Color(
            name = "Pet Levels 50 to 59 Color",
            category = "Colors",
            subcategory = "Pet Highlight Colors"
    )
    public static OneColor pet50To59Colour = new OneColor(0, 138, 216, 191); // Light Blue

    @Color(
            name = "Pet Levels 60 to 69 Color",
            category = "Colors",
            subcategory = "Pet Highlight Colors"
    )
    public static OneColor pet60To69Colour = new OneColor(126, 79, 198, 191); // Purple

    @Color(
            name = "Pet Levels 70 to 79 Color",
            category = "Colors",
            subcategory = "Pet Highlight Colors"
    )
    public static OneColor pet70To79Colour = new OneColor(214, 79, 200, 191); // Pink

    @Color(
            name = "Pet Levels 80 to 89 Color",
            category = "Colors",
            subcategory = "Pet Highlight Colors"
    )
    public static OneColor pet80To89Colour = new OneColor(92, 31, 53, 191); // idk weird magenta

    @Color(
            name = "Pet Levels 90 to 99 Color",
            category = "Colors",
            subcategory = "Pet Highlight Colors"
    )
    public static OneColor pet90To99Colour = new OneColor(158, 121, 78, 191); // Brown

    @Color(
            name = "Pet Level 100 Color",
            category = "Colors",
            subcategory = "Pet Highlight Colors"
    )
    public static OneColor pet100Colour = new OneColor(242, 210, 73, 191); // Gold

    // Keybinds

    @KeyBind(
            name = "Open Maddox Menu",
            category = "Keybinds",
            subcategory = "General"
    )
    public static OneKeyBind maddoxKey = new OneKeyBind(UKeyboard.KEY_M);

    @KeyBind(
            name = "Regular Ability",
            category = "Keybinds",
            subcategory = "Dungeons"
    )
    public static OneKeyBind abilityKey = new OneKeyBind(UKeyboard.KEY_NUMPAD4);

    @KeyBind(
            name = "Create Waypoint",
            category = "Keybinds",
            subcategory = "Waypoints"
    )
    public static OneKeyBind waypointKey = new OneKeyBind(UKeyboard.KEY_NUMPAD6);

    @KeyBind(
            name = "Start/Stop Skill Tracker",
            category = "Keybinds",
            subcategory = "Trackers"
    )
    public static OneKeyBind skillTrackerKey = new OneKeyBind(UKeyboard.KEY_NUMPAD5);

    @KeyBind(
            name = "Start/Stop Powder Tracker",
            category = "Keybinds",
            subcategory = "Trackers"
    )
    public static OneKeyBind powderTrackerKey = new OneKeyBind(UKeyboard.KEY_NUMPAD8);

    @KeyBind(
            name = "Disable Mouse Movement",
            category = "Keybinds",
            subcategory = "Farming"
    )
    public static OneKeyBind disableMouse = new OneKeyBind();

    @Info(
            text = "If this gets stuck, change your sensitivity in controls.",
            type = InfoType.INFO,
            category = "Keybinds",
            subcategory = "Farming"
    )
    public static boolean ignored3;

    @KeyBind(
            name = "Disable Moving Forwards/Back",
            category = "Keybinds",
            subcategory = "Farming"
    )
    public static OneKeyBind disableWS = new OneKeyBind();

    @KeyBind(
            name = "Disable Moving Left/Right",
            category = "Keybinds",
            subcategory = "Farming"
    )
    public static OneKeyBind disableAD = new OneKeyBind();

    @Switch(
            name = "Debug Messages",
            description = "Enable debug messages.",
            size = OptionSize.DUAL,
            category = "Debug",
            subcategory = "Debugging"
    )
    public static boolean debug = false;

    @Switch(
            name = "Packets Received",
            description = "Show packets being received.",
            category = "Debug",
            subcategory = "Debugging"
    )
    public static boolean debugPacketsIn = false;

    @Switch(
            name = "Packets Sent",
            description = "Show packets being sent.",
            category = "Debug",
            subcategory = "Debugging"
    )
    public static boolean debugPacketsOut = false;

    @Switch(
            name = "Messages Sent",
            description = "Show messages/commands being sent.",
            category = "Debug",
            subcategory = "Debugging"
    )
    public static boolean debugChat = false;

    @Switch(
            name = "Fix /locraw Spam",
            description = "Prevents /locraw from being sent in succession, fixing commands being sent too fast.",
            category = "Debug",
            subcategory = "Fixes"
    )
    public static boolean fixLocraw = false;

    @Switch(
            name = "Fix S04PacketEntityEquipment",
            description = "Fixes S04PacketEntityEquipment packet incorrectly setting armor on Forge clients.",
            category = "Debug",
            subcategory = "Fixes"
    )
    public static boolean spiritBootsFix = true;

}
