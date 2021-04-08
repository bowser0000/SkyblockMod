package me.Danker.handlers;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.features.*;
import me.Danker.features.loot.LootDisplay;
import me.Danker.features.loot.LootTracker;
import me.Danker.features.puzzlesolvers.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {
	public static Configuration config;
	private final static String file = "config/Danker's Skyblock Mod.cfg";
	
	public static void init() {
		config = new Configuration(new File(file));
		try {
			config.load();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
	}
	
	public static int getInt(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(key)) {
				return config.get(category, key, 0).getInt();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
		return 0;
	}
	
	public static double getDouble(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(key)) {
				return config.get(category, key, 0D).getDouble();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
		return 0D;
	}
	
	public static String getString(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(key)) {
				return config.get(category, key, "").getString();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
		return "";
	}
	
	public static boolean getBoolean(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(key)) {
				return config.get(category, key, false).getBoolean();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
		return true;
	}

	public static void writeIntConfig(String category, String key, int value) {
		config = new Configuration(new File(file));
		try {
			config.load();
			int set = config.get(category, key, value).getInt();
			config.getCategory(category).get(key).set(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
	}
	
	public static void writeDoubleConfig(String category, String key, double value) {
		config = new Configuration(new File(file));
		try {
			config.load();
			double set = config.get(category, key, value).getDouble();
			config.getCategory(category).get(key).set(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
	}
	
	public static void writeStringConfig(String category, String key, String value) {
		config = new Configuration(new File(file));
		try {
			config.load();
			String set = config.get(category, key, value).getString();
			config.getCategory(category).get(key).set(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
	}
	
	public static void writeBooleanConfig(String category, String key, boolean value) {
		config = new Configuration(new File(file));
		try {
			config.load();
			boolean set = config.get(category, key, value).getBoolean();
			config.getCategory(category).get(key).set(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
	}
	
	public static boolean hasKey(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (!config.hasCategory(category)) return false;
			return config.getCategory(category).containsKey(key);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
		return false;
	}
	
	public static void deleteCategory(String category) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.hasCategory(category)) {
				config.removeCategory(new ConfigCategory(category));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
	}

	public static int initInt(String category, String key, int defaultValue) {
		if (!hasKey(category, key)) {
			writeIntConfig(category, key, defaultValue);
			return defaultValue;
		} else {
			return getInt(category, key);
		}
	}

	public static double initDouble(String category, String key, double defaultValue) {
		if (!hasKey(category, key)) {
			writeDoubleConfig(category, key, defaultValue);
			return defaultValue;
		} else {
			return getDouble(category, key);
		}
	}

	public static String initString(String category, String key, String defaultValue) {
		if (!hasKey(category, key)) {
			writeStringConfig(category, key, defaultValue);
			return defaultValue;
		} else {
			return getString(category, key);
		}
	}

	public static boolean initBoolean(String category, String key, boolean defaultValue) {
		if (!hasKey(category, key)) {
			writeBooleanConfig(category, key, defaultValue);
			return defaultValue;
		} else {
			return getBoolean(category, key);
		}
	}
	
	public static void reloadConfig() {
		// Toggles
		ToggleCommand.gpartyToggled = initBoolean("toggles", "GParty", false);
		ToggleCommand.coordsToggled = initBoolean("toggles", "Coords", false);
		ToggleCommand.goldenToggled = initBoolean("toggles", "Golden", false);
		ToggleCommand.slayerCountTotal = initBoolean("toggles", "SlayerCount", true);
		ToggleCommand.rngesusAlerts = initBoolean("toggles", "RNGesusAlerts", false);
		ToggleCommand.ghostDisplay = initBoolean("toggles", "GhostDisplay", true);
		ToggleCommand.dungeonTimerToggled = initBoolean("toggles", "GhostTimer", false);
		ToggleCommand.splitFishing = initBoolean("toggles", "SplitFishing", true);
		ToggleCommand.chatMaddoxToggled = initBoolean("toggles", "ChatMaddox", false);
		ToggleCommand.spiritBearAlerts = initBoolean("toggles", "SpiritBearAlerts", false);
		ToggleCommand.petColoursToggled = initBoolean("toggles", "PetColors", false);
		ToggleCommand.golemAlertToggled = initBoolean("toggles", "GolemAlerts", false);
		ToggleCommand.expertiseLoreToggled = initBoolean("toggles", "ExpertiseLore", false);
		ToggleCommand.skill50DisplayToggled = initBoolean("toggles", "Skill50Display", false);
		ToggleCommand.outlineTextToggled = initBoolean("toggles", "OutlineText", false);
		ToggleCommand.cakeTimerToggled = initBoolean("toggles", "CakeTimer", false);
		ToggleCommand.melodyTooltips = initBoolean("toggles", "MelodyTooltips", false);
		ToggleCommand.highlightSlayers = initBoolean("toggles", "HighlightSlayers", false);
		ToggleCommand.highlightArachne = initBoolean("toggles", "HighlightArachne", false);
		// Chat Messages
		ToggleCommand.sceptreMessages = initBoolean("toggles", "SceptreMessages", true);
		ToggleCommand.midasStaffMessages = initBoolean("toggles", "MidasStaffMessages", true);
		ToggleCommand.implosionMessages = initBoolean("toggles", "ImplosionMessages", true);
		ToggleCommand.healMessages = initBoolean("toggles", "HealMessages", true);
		ToggleCommand.cooldownMessages = initBoolean("toggles", "CooldownMessages", true);
		ToggleCommand.manaMessages = initBoolean("toggles", "ManaMessages", true);
		ToggleCommand.killComboMessages = initBoolean("toggles", "KillComboMessages", true);
		// Dungeons
		ToggleCommand.dungeonTimerToggled = initBoolean("toggles", "DungeonTimer", false);
		ToggleCommand.lowHealthNotifyToggled = initBoolean("toggles", "LowHealthNotify", false);
		ToggleCommand.lividSolverToggled = initBoolean("toggles", "LividSolver", false);
		ToggleCommand.stopSalvageStarredToggled = initBoolean("toggles", "StopSalvageStarred", false);
		ToggleCommand.watcherReadyToggled = initBoolean("toggles", "WatcherReadyMessage", false);
		ToggleCommand.notifySlayerSlainToggled = initBoolean("toggles", "NotifySlayerSlain", false);
		ToggleCommand.necronNotificationsToggled = initBoolean("toggles", "NecronNotifications", false);
		ToggleCommand.bonzoTimerToggled = initBoolean("toggles", "BonzoTimer", false);
		ToggleCommand.swapToPickBlockToggled = initBoolean("toggles", "PickBlock", false);
		ToggleCommand.autoSkillTrackerToggled =  initBoolean("toggles", "AutoSkillTracker", false);
		// Puzzle Solvers
		ToggleCommand.threeManToggled = initBoolean("toggles", "ThreeManPuzzle", false);
		ToggleCommand.oruoToggled = initBoolean("toggles", "OruoPuzzle", false);
		ToggleCommand.blazeToggled = initBoolean("toggles", "BlazePuzzle", false);
		ToggleCommand.creeperToggled = initBoolean("toggles", "CreeperPuzzle", false);
		ToggleCommand.waterToggled = initBoolean("toggles", "WaterPuzzle", false);
		ToggleCommand.ticTacToeToggled = initBoolean("toggles", "TicTacToePuzzle", false);
		ToggleCommand.boulderToggled = initBoolean("toggles", "BoulderPuzzle", false);
		ToggleCommand.silverfishToggled = initBoolean("toggles", "SilverfishPuzzle", false);
		ToggleCommand.iceWalkToggled = initBoolean("toggles", "IceWalkPuzzle", false);
		ToggleCommand.startsWithToggled = initBoolean("toggles", "StartsWithTerminal", false);
		ToggleCommand.selectAllToggled = initBoolean("toggles", "SelectAllTerminal", false);
		ToggleCommand.clickInOrderToggled = initBoolean("toggles", "ClickInOrderTerminal", false);
		// Experiment Solvers
		ToggleCommand.ultrasequencerToggled = initBoolean("toggles", "UltraSequencer", false);
		ToggleCommand.chronomatronToggled = initBoolean("toggles", "Chronomatron", false);
		ToggleCommand.superpairsToggled = initBoolean("toggles", "Superpairs", false);
		ToggleCommand.hideTooltipsInExperimentAddonsToggled = initBoolean("toggles", "HideTooltipsInExperimentAddons", false);
		// Custom Music
		ToggleCommand.dungeonBossMusic = initBoolean("toggles", "DungeonBossMusic", false);
		ToggleCommand.bloodRoomMusic = initBoolean("toggles", "BloodRoomMusic", false);
		ToggleCommand.dungeonMusic = initBoolean("toggles", "DungeonMusic", false);
		// Music Volume
		CustomMusic.dungeonbossVolume = initInt("music", "DungeonBossVolume", 50);
		CustomMusic.bloodroomVolume = initInt("music", "BloodRoomVolume", 50);
		CustomMusic.dungeonVolume = initInt("music", "DungeonVolume", 50);

		// API
		if (!hasKey("api", "APIKey")) writeStringConfig("api", "APIKey", "");
		
		// Wolf
		LootTracker.wolfSvens = initInt("wolf", "svens", 0);
		LootTracker.wolfTeeth = initInt("wolf", "teeth", 0);
		LootTracker.wolfWheels = initInt("wolf", "wheel", 0);
		LootTracker.wolfWheelsDrops = initInt("wolf", "wheelDrops", 0);
		LootTracker.wolfSpirits = initInt("wolf", "spirit", 0);
		LootTracker.wolfBooks = initInt("wolf", "book", 0);
		LootTracker.wolfEggs = initInt("wolf", "egg", 0);
		LootTracker.wolfCoutures = initInt("wolf", "couture", 0);
		LootTracker.wolfBaits = initInt("wolf", "bait", 0);
		LootTracker.wolfFluxes = initInt("wolf", "flux", 0);
		LootTracker.wolfTime = initDouble("wolf", "timeRNG", -1);
		LootTracker.wolfBosses = initInt("wolf", "bossRNG", -1);
		// Spider
		LootTracker.spiderTarantulas = initInt("spider", "tarantulas", 0);
		LootTracker.spiderWebs = initInt("spider", "web", 0);
		LootTracker.spiderTAP = initInt("spider", "tap", 0);
		LootTracker.spiderTAPDrops = initInt("spider", "tapDrops", 0);
		LootTracker.spiderBites = initInt("spider", "bite", 0);
		LootTracker.spiderCatalysts = initInt("spider", "catalyst", 0);
		LootTracker.spiderBooks = initInt("spider", "book", 0);
		LootTracker.spiderSwatters = initInt("spider", "swatter", 0);
		LootTracker.spiderTalismans = initInt("spider", "talisman", 0);
		LootTracker.spiderMosquitos = initInt("spider", "mosquito", 0);
		LootTracker.spiderTime = initDouble("spider", "timeRNG", -1);
		LootTracker.spiderBosses = initInt("spider", "bossRNG", -1);
		// Zombie
		LootTracker.zombieRevs = initInt("zombie", "revs", 0);
		LootTracker.zombieRevFlesh = initInt("zombie", "revFlesh", 0);
		LootTracker.zombieFoulFlesh = initInt("zombie", "foulFlesh", 0);
		LootTracker.zombieFoulFleshDrops = initInt("zombie", "foulFleshDrops", 0);
		LootTracker.zombiePestilences = initInt("zombie", "pestilence", 0);
		LootTracker.zombieUndeadCatas = initInt("zombie", "undeadCatalyst", 0);
		LootTracker.zombieBooks = initInt("zombie", "book", 0);
		LootTracker.zombieBeheadeds = initInt("zombie", "beheaded", 0);
		LootTracker.zombieRevCatas = initInt("zombie", "revCatalyst", 0);
		LootTracker.zombieSnakes = initInt("zombie", "snake", 0);
		LootTracker.zombieScythes = initInt("zombie", "scythe", 0);
		LootTracker.zombieShards = initInt("zombie", "shard", 0);
		LootTracker.zombieWardenHearts = initInt("zombie", "heart", 0);
		LootTracker.zombieTime = initDouble("zombie", "timeRNG", -1);
		LootTracker.zombieBosses = initInt("zombie", "bossRNG", -1);
		
		// Fishing
		LootTracker.seaCreatures = initInt("fishing", "seaCreature", 0);
		LootTracker.goodCatches = initInt("fishing", "goodCatch", 0);
		LootTracker.greatCatches = initInt("fishing", "greatCatch", 0);
		LootTracker.squids = initInt("fishing", "squid", 0);
		LootTracker.seaWalkers = initInt("fishing", "seaWalker", 0);
		LootTracker.nightSquids = initInt("fishing", "nightSquid", 0);
		LootTracker.seaGuardians = initInt("fishing", "seaGuardian", 0);
		LootTracker.seaWitches = initInt("fishing", "seaWitch", 0);
		LootTracker.seaArchers = initInt("fishing", "seaArcher", 0);
		LootTracker.monsterOfTheDeeps = initInt("fishing", "monsterOfDeep", 0);
		LootTracker.catfishes = initInt("fishing", "catfish", 0);
		LootTracker.carrotKings = initInt("fishing", "carrotKing", 0);
		LootTracker.seaLeeches = initInt("fishing", "seaLeech", 0);
		LootTracker.guardianDefenders = initInt("fishing", "guardianDefender", 0);
		LootTracker.deepSeaProtectors = initInt("fishing", "deepSeaProtector", 0);
		LootTracker.hydras = initInt("fishing", "hydra", 0);
		LootTracker.seaEmperors = initInt("fishing", "seaEmperor", 0);
		LootTracker.empTime = initDouble("fishing", "empTime", -1);
		LootTracker.empSCs = initInt("fishing", "empSC", -1);
		LootTracker.fishingMilestone = initInt("fishing", "milestone", 0);
		// Fishing Winter
		LootTracker.frozenSteves = initInt("fishing", "frozenSteve", 0);
		LootTracker.frostyTheSnowmans = initInt("fishing", "snowman", 0);
		LootTracker.grinches = initInt("fishing", "grinch", 0);
		LootTracker.yetis = initInt("fishing", "yeti", 0);
		LootTracker.yetiTime = initDouble("fishing", "yetiTime", -1);
		LootTracker.yetiSCs = initInt("fishing", "yetiSC", -1);
		// Fishing Festival
		LootTracker.nurseSharks = initInt("fishing", "nurseShark", 0);
		LootTracker.blueSharks = initInt("fishing", "blueShark", 0);
		LootTracker.tigerSharks = initInt("fishing", "tigerShark", 0);
		LootTracker.greatWhiteSharks = initInt("fishing", "greatWhiteShark", 0);
		// Spooky Fishing
		LootTracker.scarecrows = initInt("fishing", "scarecrow", 0);
		LootTracker.nightmares = initInt("fishing", "nightmare", 0);
		LootTracker.werewolfs = initInt("fishing", "werewolf", 0);
		LootTracker.phantomFishers = initInt("fishing", "phantomFisher", 0);
		LootTracker.grimReapers = initInt("fishing", "grimReaper", 0);
		
		// Mythological
		LootTracker.mythCoins = initDouble("mythological", "coins", 0);
		LootTracker.griffinFeathers = initInt("mythological", "griffinFeather", 0);
		LootTracker.crownOfGreeds = initInt("mythological", "crownOfGreed", 0);
		LootTracker.washedUpSouvenirs = initInt("mythological", "washedUpSouvenir", 0);
		LootTracker.minosHunters = initInt("mythological", "minosHunter", 0);
		LootTracker.siameseLynxes = initInt("mythological", "siameseLynx", 0);
		LootTracker.minotaurs = initInt("mythological", "minotaur", 0);
		LootTracker.gaiaConstructs = initInt("mythological", "gaiaConstruct", 0);
		LootTracker.minosChampions = initInt("mythological", "minosChampion", 0);
		LootTracker.minosInquisitors = initInt("mythological", "minosInquisitor", 0);
		
		// Dungeons
		LootTracker.recombobulators =  initInt("catacombs", "recombobulator", 0);
		LootTracker.fumingPotatoBooks = initInt("catacombs", "fumingBooks", 0);
		// F1
		LootTracker.bonzoStaffs = initInt("catacombs", "bonzoStaff", 0);
		LootTracker.f1CoinsSpent = initDouble("catacombs", "floorOneCoins", 0);
		LootTracker.f1TimeSpent = initDouble("catacombs", "floorOneTime", 0);
		// F2
		LootTracker.scarfStudies = initInt("catacombs", "scarfStudies", 0);
		LootTracker.f2CoinsSpent = initDouble("catacombs", "floorTwoCoins", 0);
		LootTracker.f2TimeSpent = initDouble("catacombs", "floorTwoTime", 0);
		// F3
		LootTracker.adaptiveHelms = initInt("catacombs", "adaptiveHelm", 0);
		LootTracker.adaptiveChests = initInt("catacombs", "adaptiveChest", 0);
		LootTracker.adaptiveLegs = initInt("catacombs", "adaptiveLegging", 0);
		LootTracker.adaptiveBoots = initInt("catacombs", "adaptiveBoot", 0);
		LootTracker.adaptiveSwords = initInt("catacombs", "adaptiveSword", 0);
		LootTracker.f3CoinsSpent = initDouble("catacombs", "floorThreeCoins", 0);
		LootTracker.f3TimeSpent = initDouble("catacombs", "floorThreeTime", 0);
		// F4
		LootTracker.spiritWings = initInt("catacombs", "spiritWing", 0);
		LootTracker.spiritBones = initInt("catacombs", "spiritBone", 0);
		LootTracker.spiritBoots = initInt("catacombs", "spiritBoot", 0);
		LootTracker.spiritSwords = initInt("catacombs", "spiritSword", 0);
		LootTracker.spiritBows = initInt("catacombs", "spiritBow", 0);
		LootTracker.epicSpiritPets = initInt("catacombs", "spiritPetEpic", 0);
		LootTracker.legSpiritPets = initInt("catacombs", "spiritPetLeg", 0);
		LootTracker.f4CoinsSpent = initDouble("catacombs", "floorFourCoins", 0);
		LootTracker.f4TimeSpent = initDouble("catacombs", "floorFourTime", 0);
		// F5
		LootTracker.warpedStones = initInt("catacombs", "warpedStone", 0);
		LootTracker.shadowAssHelms = initInt("catacombs", "shadowAssassinHelm", 0);
		LootTracker.shadowAssChests = initInt("catacombs", "shadowAssassinChest", 0);
		LootTracker.shadowAssLegs = initInt("catacombs", "shadowAssassinLegging", 0);
		LootTracker.shadowAssBoots = initInt("catacombs", "shadowAssassinBoot", 0);
		LootTracker.lastBreaths = initInt("catacombs", "lastBreath", 0);
		LootTracker.lividDaggers = initInt("catacombs", "lividDagger", 0);
		LootTracker.shadowFurys = initInt("catacombs", "shadowFury", 0);
		LootTracker.f5CoinsSpent = initDouble("catacombs", "floorFiveCoins", 0);
		LootTracker.f5TimeSpent = initDouble("catacombs", "floorFiveTime", 0);
		// F6
		LootTracker.ancientRoses = initInt("catacombs", "ancientRose", 0);
		LootTracker.precursorEyes = initInt("catacombs", "precursorEye", 0);
		LootTracker.giantsSwords = initInt("catacombs", "giantsSword", 0);
		LootTracker.necroLordHelms = initInt("catacombs", "necroLordHelm", 0);
		LootTracker.necroLordChests = initInt("catacombs", "necroLordChest", 0);
		LootTracker.necroLordLegs = initInt("catacombs", "necroLordLegging", 0);
		LootTracker.necroLordBoots = initInt("catacombs", "necroLordBoot", 0);
		LootTracker.necroSwords = initInt("catacombs", "necroSword", 0);
		LootTracker.f6CoinsSpent = initDouble("catacombs", "floorSixCoins", 0);
		LootTracker.f6TimeSpent = initDouble("catacombs", "floorSixTime", 0);
		// F7
		LootTracker.witherBloods = initInt("catacombs", "witherBlood", 0);
		LootTracker.witherCloaks = initInt("catacombs", "witherCloak", 0);
		LootTracker.implosions = initInt("catacombs", "implosion", 0);
		LootTracker.witherShields = initInt("catacombs", "witherShield", 0);
		LootTracker.shadowWarps = initInt("catacombs", "shadowWarp", 0);
		LootTracker.necronsHandles = initInt("catacombs", "necronsHandle", 0);
		LootTracker.autoRecombs = initInt("catacombs", "autoRecomb", 0);
		LootTracker.witherHelms = initInt("catacombs", "witherHelm", 0);
		LootTracker.witherChests = initInt("catacombs", "witherChest", 0);
		LootTracker.witherLegs = initInt("catacombs", "witherLegging", 0);
		LootTracker.witherBoots = initInt("catacombs", "witherBoot", 0);
		LootTracker.f7CoinsSpent = initDouble("catacombs", "floorSevenCoins", 0);
		LootTracker.f7TimeSpent = initDouble("catacombs", "floorSevenTime", 0);

		// Ghost
		LootTracker.sorrows = initInt("ghosts", "sorrow", 0);
		LootTracker.voltas = initInt("ghosts", "volta", 0);
		LootTracker.plasmas = initInt("ghosts", "plasma", 0);
		LootTracker.ghostlyBoots = initInt("ghosts", "ghostlyBoots", 0);
		LootTracker.bagOfCashs = initInt("ghosts", "bagOfCash", 0);


		// Misc
		LootDisplay.display = initString("misc", "display", "off");
		LootDisplay.auto = initBoolean("misc", "autoDisplay", false);
		Skill50Display.SKILL_TIME = initInt("misc", "skill50Time", 3) * 20;
		CakeTimer.cakeTime = initDouble("misc", "cakeTime", 0);
		SkillTracker.showSkillTracker = initBoolean("misc", "showSkillTracker", false);
		DankersSkyblockMod.firstLaunch = initBoolean("misc", "firstLaunch", true);

		// Locations
		ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
		int height = scaled.getScaledHeight();
		MoveCommand.coordsXY[0] = initInt("locations", "coordsX", 5);
		MoveCommand.coordsXY[1] = initInt("locations", "coordsY", height - 25);
		MoveCommand.displayXY[0] = initInt("locations", "displayX", 80);
		MoveCommand.displayXY[1] = initInt("locations", "displayY", 5);
		MoveCommand.dungeonTimerXY[0] = initInt("locations", "dungeonTimerX", 5);
		MoveCommand.dungeonTimerXY[1] = initInt("locations", "dungeonTimerY", 5);
		MoveCommand.skill50XY[0] = initInt("locations", "skill50X", 40);
		MoveCommand.skill50XY[1] = initInt("locations", "skill50Y", 10);
		MoveCommand.lividHpXY[0] = initInt("locations", "lividHpX", 40);
		MoveCommand.lividHpXY[1] = initInt("locations", "lividHpY", 20);
		MoveCommand.cakeTimerXY[0] = initInt("locations", "cakeTimerX", 40);
		MoveCommand.cakeTimerXY[1] = initInt("locations", "cakeTimerY", 30);
		MoveCommand.skillTrackerXY[0] = initInt("locations", "skillTrackerX", 40);
		MoveCommand.skillTrackerXY[1] = initInt("locations", "skillTrackerY", 50);
		MoveCommand.waterAnswerXY[0] = initInt("locations", "waterAnswerX", 100);
		MoveCommand.waterAnswerXY[1] = initInt("locations", "waterAnswerY", 100);
		MoveCommand.bonzoTimerXY[0] = initInt("locations", "bonzoTimerX", 40);
		MoveCommand.bonzoTimerXY[1] = initInt("locations", "bonzoTimerY", 80);
		MoveCommand.golemTimerXY[0] = initInt("locations", "golemTimerX", 100);
		MoveCommand.golemTimerXY[1] = initInt("locations", "golemTimerY", 30);

		// Scales
		ScaleCommand.coordsScale = initDouble("scales", "coordsScale", 1);
		ScaleCommand.displayScale = initDouble("scales", "displayScale", 1);
		ScaleCommand.dungeonTimerScale = initDouble("scales", "dungeonTimerScale", 1);
		ScaleCommand.skill50Scale = initDouble("scales", "skill50Scale", 1);
		ScaleCommand.lividHpScale = initDouble("scales", "lividHpScale", 1);
		ScaleCommand.cakeTimerScale = initDouble("scales", "cakeTimerScale", 1);
		ScaleCommand.skillTrackerScale = initDouble("scales", "skillTrackerScale", 1);
		ScaleCommand.waterAnswerScale = initDouble("scales", "waterAnswerScale", 1);
		ScaleCommand.bonzoTimerScale = initDouble("scales", "bonzoTimerScale", 1);
		ScaleCommand.golemTimerScale = initDouble("scales", "golemTimerScale", 1);

		// Colours
		DankersSkyblockMod.MAIN_COLOUR = initString("colors", "main", EnumChatFormatting.GREEN.toString());
		DankersSkyblockMod.SECONDARY_COLOUR = initString("colors", "secondary", EnumChatFormatting.DARK_GREEN.toString());
		DankersSkyblockMod.DELIMITER_COLOUR = initString("colors", "delimiter", EnumChatFormatting.AQUA.toString() + EnumChatFormatting.STRIKETHROUGH.toString());
		DankersSkyblockMod.ERROR_COLOUR = initString("colors", "error", EnumChatFormatting.RED.toString());
		DankersSkyblockMod.TYPE_COLOUR = initString("colors", "type", EnumChatFormatting.GREEN.toString());
		DankersSkyblockMod.VALUE_COLOUR = initString("colors", "value", EnumChatFormatting.DARK_GREEN.toString());
		DankersSkyblockMod.SKILL_AVERAGE_COLOUR = initString("colors", "skillAverage", EnumChatFormatting.GOLD.toString());
		DankersSkyblockMod.ANSWER_COLOUR = initString("colors", "answer", EnumChatFormatting.DARK_GREEN.toString());
		Skill50Display.SKILL_50_COLOUR = initString("colors", "skill50Display", EnumChatFormatting.AQUA.toString());
		NoF3Coords.COORDS_COLOUR = initString("colors", "coordsDisplay", EnumChatFormatting.WHITE.toString());
		CakeTimer.CAKE_COLOUR = initString("colors", "cakeDisplay", EnumChatFormatting.GOLD.toString());
		SkillTracker.SKILL_TRACKER_COLOUR = initString("colors", "skillTracker", EnumChatFormatting.AQUA.toString());
		TriviaSolver.TRIVIA_WRONG_ANSWER_COLOUR = initString("colors", "triviaWrongAnswer", EnumChatFormatting.RED.toString());
		BonzoMaskTimer.BONZO_COLOR = initString("colors", "bonzoDisplay", EnumChatFormatting.RED.toString());
		GolemSpawningAlert.GOLEM_COLOUR = initString("colors", "golemDisplay", EnumChatFormatting.GOLD.toString());
		BlazeSolver.LOWEST_BLAZE_COLOUR = initInt("colors", "blazeLowest", 0xFF0000);
		BlazeSolver.HIGHEST_BLAZE_COLOUR = initInt("colors", "blazeHighest", 0x40FF40);
		SlayerESP.SLAYER_COLOUR = initInt("colors", "slayerColor", 0x0000FF);
		ArachneESP.ARACHANE_COLOUR = initInt("colors", "arachneColor", 0x00FF00);
		PetColours.PET_1_TO_9 = initInt("colors", "pet1To9", 0x999999); // Grey
		PetColours.PET_10_TO_19 = initInt("colors", "pet10To19", 0xD62440); // Red
		PetColours.PET_20_TO_29 = initInt("colors", "pet20To29", 0xEF5230); // Orange
		PetColours.PET_30_TO_39 = initInt("colors", "pet30To39", 0xFFC400); // Yellow
		PetColours.PET_40_TO_49 = initInt("colors", "pet40To49", 0x0EAC35); // Green
		PetColours.PET_50_TO_59 = initInt("colors", "pet50To59", 0x008AD8); // Light Blue
		PetColours.PET_60_TO_69 = initInt("colors", "pet60To69", 0x7E4FC6); // Purple
		PetColours.PET_70_TO_79 = initInt("colors", "pet70To79", 0xD64FC8); // Pink
		PetColours.PET_80_TO_89 = initInt("colors", "pet80To89", 0x5C1F35); // idk weird magenta
		PetColours.PET_90_TO_99 = initInt("colors", "pet90To99", 0x9E794E); // Brown
		PetColours.PET_100 = initInt("colors", "pet100", 0xF2D249); // Gold
		UltrasequencerSolver.ULTRASEQUENCER_NEXT = initInt("colors", "ultrasequencerNext", 0x40FF40);
		UltrasequencerSolver.ULTRASEQUENCER_NEXT_TO_NEXT = initInt("colors", "ultrasequencerNextToNext", 0x40DAE6);
		ChronomatronSolver.CHRONOMATRON_NEXT = initInt("colors", "chronomatronNext", 0x40FF40);
		ChronomatronSolver.CHRONOMATRON_NEXT_TO_NEXT = initInt("colors", "chronomatronNextToNext", 0x40DAE6);
		ClickInOrderSolver.CLICK_IN_ORDER_NEXT = initInt("colors", "clickInOrderNext", 0xFF00DD);
		ClickInOrderSolver.CLICK_IN_ORDER_NEXT_TO_NEXT = initInt("colors", "clickInOrderNextToNext", 0x0BEFE7);
		BoulderSolver.BOULDER_COLOUR = initInt("colors", "boulder", 0x197F19);
		BoulderSolver.BOULDER_ARROW_COLOUR = initInt("colors", "boulderArrow", 0x006000);
		SilverfishSolver.SILVERFISH_LINE_COLOUR = initInt("colors", "silverfishLine", 0x40FF40);
		IceWalkSolver.ICE_WALK_LINE_COLOUR = initInt("colors", "iceWalkLine", 0x40FF40);

		// Commands
		if (!hasKey("commands", "reparty")) writeBooleanConfig("commands", "reparty", false);
	}
	
}
