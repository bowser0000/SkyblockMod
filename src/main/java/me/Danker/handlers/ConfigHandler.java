package me.Danker.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import me.Danker.DankersSkyblockMod;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.features.*;
import me.Danker.features.loot.*;
import me.Danker.features.puzzlesolvers.*;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
		try {
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
		try {
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
		try {
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
		try {
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
		try {
			int set = config.get(category, key, value).getInt();
			config.getCategory(category).get(key).set(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
	}
	
	public static void writeDoubleConfig(String category, String key, double value) {
		try {
			double set = config.get(category, key, value).getDouble();
			config.getCategory(category).get(key).set(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
	}
	
	public static void writeStringConfig(String category, String key, String value) {
		try {
			String set = config.get(category, key, value).getString();
			config.getCategory(category).get(key).set(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
	}
	
	public static void writeBooleanConfig(String category, String key, boolean value) {
		try {
			boolean set = config.get(category, key, value).getBoolean();
			config.getCategory(category).get(key).set(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			config.save();
		}
	}
	
	public static boolean hasKey(String category, String key) {
		try {
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
		try {
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
		init();

		// Toggles
		ToggleCommand.gpartyToggled = initBoolean("toggles", "GParty", false);
		ToggleCommand.coordsToggled = initBoolean("toggles", "Coords", false);
		ToggleCommand.goldenToggled = initBoolean("toggles", "Golden", false);
		ToggleCommand.slayerCountTotal = initBoolean("toggles", "SlayerCount", true);
		ToggleCommand.masterSPlusDisplay = initBoolean("toggles", "MasterSPlusDisplay", true);
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
		ToggleCommand.highlightSkeletonMasters = initBoolean("toggles", "HighlightSkeletonMasters", false);
		ToggleCommand.teammatesInRadius = initBoolean("toggles", "TeammatesInRadius", false);
		ToggleCommand.giantHP = initBoolean("toggles", "GiantHP", false);
		ToggleCommand.hidePetCandy = initBoolean("toggles", "HidePetCandy", false);
		ToggleCommand.customColouredNames = initBoolean("toggles", "CustomColouredNames", true); // enabled by default
		ToggleCommand.endOfFarmAlert = initBoolean("toggles", "EndOfFarmAlert", false);
		ToggleCommand.gemstoneLore = initBoolean("toggles", "GemstoneLore", false);
		ToggleCommand.crystalHollowWaypoints = initBoolean("toggles", "CrystalHollowWaypoints", false);
		ToggleCommand.crystalAutoWaypoints = initBoolean("toggles", "CrystalAutoWaypoints", true); // enabled by default
		ToggleCommand.crystalAutoPlayerWaypoints = initBoolean("toggles", "CrystalAutoPlayerWaypoints", false);
		ToggleCommand.autoAcceptReparty = initBoolean("toggles", "AutoAcceptReparty", false);
		ToggleCommand.abilityCooldowns = initBoolean("toggles", "AbilityCooldowns", false);
		ToggleCommand.dungeonTimerToggled = initBoolean("toggles", "DungeonTimer", false);
		ToggleCommand.lowHealthNotifyToggled = initBoolean("toggles", "LowHealthNotify", false);
		ToggleCommand.lividSolverToggled = initBoolean("toggles", "LividSolver", false);
		ToggleCommand.stopSalvageStarredToggled = initBoolean("toggles", "StopSalvageStarred", false);
		ToggleCommand.watcherReadyToggled = initBoolean("toggles", "WatcherReadyMessage", false);
		ToggleCommand.notifySlayerSlainToggled = initBoolean("toggles", "NotifySlayerSlain", false);
		ToggleCommand.necronNotificationsToggled = initBoolean("toggles", "NecronNotifications", false);
		ToggleCommand.bonzoTimerToggled = initBoolean("toggles", "BonzoTimer", false);
		ToggleCommand.swapToPickBlockToggled = initBoolean("toggles", "PickBlock", false);
		ToggleCommand.flowerWeaponsToggled = initBoolean("toggles", "FlowerWeapons", false);
		ToggleCommand.autoSkillTrackerToggled = initBoolean("toggles", "AutoSkillTracker", false);
		ToggleCommand.alerts = initBoolean("toggles", "Alerts", false);
		ToggleCommand.dungeonScore = initBoolean("toggles", "DungeonScore", false);
		ToggleCommand.hideArmour = initBoolean("toggles", "HideArmour", false);
		ToggleCommand.autoJoinSkyblock = initBoolean("toggles", "AutoJoinSkyblock", false);
		ToggleCommand.firePillar = initBoolean("toggles", "FirePillar", false);
		// Chat Messages
		ToggleCommand.sceptreMessages = initBoolean("toggles", "SceptreMessages", true);
		ToggleCommand.midasStaffMessages = initBoolean("toggles", "MidasStaffMessages", true);
		ToggleCommand.implosionMessages = initBoolean("toggles", "ImplosionMessages", true);
		ToggleCommand.healMessages = initBoolean("toggles", "HealMessages", true);
		ToggleCommand.cooldownMessages = initBoolean("toggles", "CooldownMessages", true);
		ToggleCommand.manaMessages = initBoolean("toggles", "ManaMessages", true);
		ToggleCommand.killComboMessages = initBoolean("toggles", "KillComboMessages", true);
		// Puzzle Solvers
		ToggleCommand.threeManToggled = initBoolean("toggles", "ThreeManPuzzle", false);
		ToggleCommand.oruoToggled = initBoolean("toggles", "OruoPuzzle", false);
		ToggleCommand.blazeToggled = initBoolean("toggles", "BlazePuzzle", false);
		ToggleCommand.creeperToggled = initBoolean("toggles", "CreeperPuzzle", false);
		ToggleCommand.creeperLinesToggled = initBoolean("toggles", "CreeperLines", true);
		ToggleCommand.waterToggled = initBoolean("toggles", "WaterPuzzle", false);
		ToggleCommand.ticTacToeToggled = initBoolean("toggles", "TicTacToePuzzle", false);
		ToggleCommand.boulderToggled = initBoolean("toggles", "BoulderPuzzle", false);
		ToggleCommand.silverfishToggled = initBoolean("toggles", "SilverfishPuzzle", false);
		ToggleCommand.iceWalkToggled = initBoolean("toggles", "IceWalkPuzzle", false);
		ToggleCommand.startsWithToggled = initBoolean("toggles", "StartsWithTerminal", false);
		ToggleCommand.selectAllToggled = initBoolean("toggles", "SelectAllTerminal", false);
		ToggleCommand.clickInOrderToggled = initBoolean("toggles", "ClickInOrderTerminal", false);
		ToggleCommand.sameColourToggled = initBoolean("toggles", "SameColourTerminal", false);
		ToggleCommand.blockWrongTerminalClicksToggled = initBoolean("toggles", "BlockWrongTerminalClicks", false);
		ToggleCommand.itemFrameOnSeaLanternsToggled = initBoolean("toggles", "IgnoreItemFrameOnSeaLanterns", false);
		// Experiment Solvers
		ToggleCommand.ultrasequencerToggled = initBoolean("toggles", "UltraSequencer", false);
		ToggleCommand.chronomatronToggled = initBoolean("toggles", "Chronomatron", false);
		ToggleCommand.superpairsToggled = initBoolean("toggles", "Superpairs", false);
		ToggleCommand.hideTooltipsInExperimentAddonsToggled = initBoolean("toggles", "HideTooltipsInExperimentAddons", false);
		// Custom Music
		ToggleCommand.dungeonBossMusic = initBoolean("toggles", "DungeonBossMusic", false);
		ToggleCommand.bloodRoomMusic = initBoolean("toggles", "BloodRoomMusic", false);
		ToggleCommand.dungeonMusic = initBoolean("toggles", "DungeonMusic", false);
		ToggleCommand.hubMusic = initBoolean("toggles", "HubMusic", false);
		ToggleCommand.islandMusic = initBoolean("toggles", "IslandMusic", false);
		ToggleCommand.dungeonHubMusic = initBoolean("toggles", "DungeonHubMusic", false);
		ToggleCommand.farmingIslandsMusic = initBoolean("toggles", "FarmingIslandsMusic", false);
		ToggleCommand.goldMineMusic = initBoolean("toggles", "GoldMineMusic", false);
		ToggleCommand.deepCavernsMusic = initBoolean("toggles", "DeepCavernsMusic", false);
		ToggleCommand.dwarvenMinesMusic = initBoolean("toggles", "DwarvenMinesMusic", false);
		ToggleCommand.crystalHollowsMusic = initBoolean("toggles", "CrystalHollowsMusic", false);
		ToggleCommand.spidersDenMusic = initBoolean("toggles", "SpidersDenMusic", false);
		ToggleCommand.crimsonIsleMusic = initBoolean("toggles", "CrimsonIsleMusic", false);
		ToggleCommand.endMusic = initBoolean("toggles", "Music", false);
		ToggleCommand.parkMusic = initBoolean("toggles", "Music", false);
		// Music Volume
		CustomMusic.dungeonbossVolume = initInt("music", "DungeonBossVolume", 50);
		CustomMusic.bloodroomVolume = initInt("music", "BloodRoomVolume", 50);
		CustomMusic.dungeonVolume = initInt("music", "DungeonVolume", 50);
		CustomMusic.hubVolume = initInt("music", "HubVolume", 50);
		CustomMusic.islandVolume = initInt("music", "IslandVolume", 50);
		CustomMusic.dungeonHubVolume = initInt("music", "DungeonHubVolume", 50);
		CustomMusic.farmingIslandsVolume = initInt("music", "FarmingIslandsVolume", 50);
		CustomMusic.goldMineVolume = initInt("music", "GoldMineVolume", 50);
		CustomMusic.deepCavernsVolume = initInt("music", "DeepCavernsVolume", 50);
		CustomMusic.dwarvenMinesVolume = initInt("music", "DwarvenMinesVolume", 50);
		CustomMusic.crystalHollowsVolume = initInt("music", "CrystalHollowsVolume", 50);
		CustomMusic.spidersDenVolume = initInt("music", "SpidersDenVolume", 50);
		CustomMusic.crimsonIsleVolume = initInt("music", "CrimsonIsleVolume", 50);
		CustomMusic.endVolume = initInt("music", "EndVolume", 50);
		CustomMusic.parkVolume = initInt("music", "ParkVolume", 50);

		// API
		if (!hasKey("api", "APIKey")) writeStringConfig("api", "APIKey", "");

		// Block Wrong Slayer
		if (!hasKey("toggles", "BlockSlayer")) writeStringConfig("toggles", "BlockSlayer", "");
		String onlySlayer = getString("toggles", "BlockSlayer");
		if (!onlySlayer.equals("")) {
			BlockWrongSlayer.onlySlayerName = onlySlayer.substring(0, onlySlayer.lastIndexOf(" "));
			BlockWrongSlayer.onlySlayerNumber = onlySlayer.substring(onlySlayer.lastIndexOf(" ") + 1);
		}
		
		// Wolf
		WolfTracker.svens = initInt("wolf", "svens", 0);
		WolfTracker.teeth = initInt("wolf", "teeth", 0);
		WolfTracker.wheels = initInt("wolf", "wheel", 0);
		WolfTracker.wheelsDrops = initInt("wolf", "wheelDrops", 0);
		WolfTracker.spirits = initInt("wolf", "spirit", 0);
		WolfTracker.books = initInt("wolf", "book", 0);
		WolfTracker.furballs = initInt("wolf", "furball", 0);
		WolfTracker.eggs = initInt("wolf", "egg", 0);
		WolfTracker.coutures = initInt("wolf", "couture", 0);
		WolfTracker.baits = initInt("wolf", "bait", 0);
		WolfTracker.fluxes = initInt("wolf", "flux", 0);
		WolfTracker.time = initDouble("wolf", "timeRNG", -1);
		WolfTracker.bosses = initInt("wolf", "bossRNG", -1);
		// Spider
		SpiderTracker.tarantulas = initInt("spider", "tarantulas", 0);
		SpiderTracker.webs = initInt("spider", "web", 0);
		SpiderTracker.TAP = initInt("spider", "tap", 0);
		SpiderTracker.TAPDrops = initInt("spider", "tapDrops", 0);
		SpiderTracker.bites = initInt("spider", "bite", 0);
		SpiderTracker.catalysts = initInt("spider", "catalyst", 0);
		SpiderTracker.books = initInt("spider", "book", 0);
		SpiderTracker.swatters = initInt("spider", "swatter", 0);
		SpiderTracker.talismans = initInt("spider", "talisman", 0);
		SpiderTracker.mosquitos = initInt("spider", "mosquito", 0);
		SpiderTracker.time = initDouble("spider", "timeRNG", -1);
		SpiderTracker.bosses = initInt("spider", "bossRNG", -1);
		// Zombie
		ZombieTracker.revs = initInt("zombie", "revs", 0);
		ZombieTracker.revFlesh = initInt("zombie", "revFlesh", 0);
		ZombieTracker.revViscera = initInt("zombie", "revViscera", 0);
		ZombieTracker.foulFlesh = initInt("zombie", "foulFlesh", 0);
		ZombieTracker.foulFleshDrops = initInt("zombie", "foulFleshDrops", 0);
		ZombieTracker.pestilences = initInt("zombie", "pestilence", 0);
		ZombieTracker.undeadCatas = initInt("zombie", "undeadCatalyst", 0);
		ZombieTracker.books = initInt("zombie", "book", 0);
		ZombieTracker.beheadeds = initInt("zombie", "beheaded", 0);
		ZombieTracker.revCatas = initInt("zombie", "revCatalyst", 0);
		ZombieTracker.snakes = initInt("zombie", "snake", 0);
		ZombieTracker.scythes = initInt("zombie", "scythe", 0);
		ZombieTracker.shards = initInt("zombie", "shard", 0);
		ZombieTracker.wardenHearts = initInt("zombie", "heart", 0);
		ZombieTracker.time = initDouble("zombie", "timeRNG", -1);
		ZombieTracker.bosses = initInt("zombie", "bossRNG", -1);
		// Enderman
		EndermanTracker.voidglooms = initInt("enderman", "voidglooms", 0);
		EndermanTracker.nullSpheres = initInt("enderman", "nullSpheres", 0);
		EndermanTracker.TAP = initInt("enderman", "tap", 0);
		EndermanTracker.TAPDrops = initInt("enderman", "tapDrops", 0);
		EndermanTracker.endersnakes = initInt("enderman", "endersnakes", 0);
		EndermanTracker.summoningEyes = initInt("enderman", "summoningEyes", 0);
		EndermanTracker.manaBooks = initInt("enderman", "manaBooks", 0);
		EndermanTracker.tuners = initInt("enderman", "tuners", 0);
		EndermanTracker.atoms = initInt("enderman", "atoms", 0);
		EndermanTracker.hazmats = initInt("enderman", "hazmats", 0);
		EndermanTracker.espressoMachines = initInt("enderman", "espressoMachines", 0);
		EndermanTracker.smartyBooks = initInt("enderman", "smartyBooks", 0);
		EndermanTracker.endRunes = initInt("enderman", "endRunes", 0);
		EndermanTracker.chalices = initInt("enderman", "chalices", 0);
		EndermanTracker.dice = initInt("enderman", "dice", 0);
		EndermanTracker.artifacts = initInt("enderman", "artifacts", 0);
		EndermanTracker.skins = initInt("enderman", "skins", 0);
		EndermanTracker.mergers = initInt("enderman", "mergers", 0);
		EndermanTracker.cores = initInt("enderman", "cores", 0);
		EndermanTracker.enchantRunes = initInt("enderman", "enchantRunes", 0);
		EndermanTracker.enderBooks = initInt("enderman", "enderBooks", 0);
		EndermanTracker.time = initDouble("enderman", "timeRNG", -1);
		EndermanTracker.bosses = initInt("enderman", "bossRNG", -1);
		// Blaze
		BlazeTracker.demonlords = initInt("blaze", "demonlords", 0);
		BlazeTracker.derelictAshes = initInt("blaze", "derelictAshes", 0);
		BlazeTracker.lavatearRunes = initInt("blaze", "lavatearRunes", 0);
		BlazeTracker.splashPotions = initInt("blaze", "splashPotions", 0);
		BlazeTracker.magmaArrows = initInt("blaze", "magmaArrows", 0);
		BlazeTracker.manaDisintegrators = initInt("blaze", "manaDisintegrators", 0);
		BlazeTracker.scorchedBooks = initInt("blaze", "scorchedBooks", 0);
		BlazeTracker.kelvinInverters = initInt("blaze", "kelvinInverters", 0);
		BlazeTracker.blazeRodDistillates = initInt("blaze", "blazeRodDistillates", 0);
		BlazeTracker.glowstoneDistillates = initInt("blaze", "glowstoneDistillates", 0);
		BlazeTracker.magmaCreamDistillates = initInt("blaze", "magmaCreamDistillates", 0);
		BlazeTracker.netherWartDistillates = initInt("blaze", "netherWartDistillates", 0);
		BlazeTracker.gabagoolDistillates = initInt("blaze", "gabagoolDistillates", 0);
		BlazeTracker.scorchedPowerCrystals = initInt("blaze", "scorchedPowerCrystals", 0);
		BlazeTracker.fireAspectBooks = initInt("blaze", "fireAspectBooks", 0);
		BlazeTracker.fieryBurstRunes = initInt("blaze", "fieryBurstRunes", 0);
		BlazeTracker.opalGems = initInt("blaze", "opalGems", 0);
		BlazeTracker.archfiendDice = initInt("blaze", "archfiendDice", 0);
		BlazeTracker.duplexBooks = initInt("blaze", "duplexBooks", 0);
		BlazeTracker.highClassArchfiendDice = initInt("blaze", "highClassArchfiendDice", 0);
		BlazeTracker.engineeringPlans = initInt("blaze", "engineeringPlans", 0);
		BlazeTracker.subzeroInverters = initInt("blaze", "subzeroInverters", 0);
		BlazeTracker.time = initDouble("blaze", "timeRNG", -1);
		BlazeTracker.bosses = initInt("blaze", "bossRNG", -1);

		// Fishing
		FishingTracker.seaCreatures = initInt("fishing", "seaCreature", 0);
		FishingTracker.goodCatches = initInt("fishing", "goodCatch", 0);
		FishingTracker.greatCatches = initInt("fishing", "greatCatch", 0);
		FishingTracker.squids = initInt("fishing", "squid", 0);
		FishingTracker.seaWalkers = initInt("fishing", "seaWalker", 0);
		FishingTracker.nightSquids = initInt("fishing", "nightSquid", 0);
		FishingTracker.seaGuardians = initInt("fishing", "seaGuardian", 0);
		FishingTracker.seaWitches = initInt("fishing", "seaWitch", 0);
		FishingTracker.seaArchers = initInt("fishing", "seaArcher", 0);
		FishingTracker.monsterOfTheDeeps = initInt("fishing", "monsterOfDeep", 0);
		FishingTracker.catfishes = initInt("fishing", "catfish", 0);
		FishingTracker.carrotKings = initInt("fishing", "carrotKing", 0);
		FishingTracker.seaLeeches = initInt("fishing", "seaLeech", 0);
		FishingTracker.guardianDefenders = initInt("fishing", "guardianDefender", 0);
		FishingTracker.deepSeaProtectors = initInt("fishing", "deepSeaProtector", 0);
		FishingTracker.hydras = initInt("fishing", "hydra", 0);
		FishingTracker.seaEmperors = initInt("fishing", "seaEmperor", 0);
		FishingTracker.empTime = initDouble("fishing", "empTime", -1);
		FishingTracker.empSCs = initInt("fishing", "empSC", -1);
		FishingTracker.fishingMilestone = initInt("fishing", "milestone", 0);
		// Fishing Winter
		FishingTracker.frozenSteves = initInt("fishing", "frozenSteve", 0);
		FishingTracker.frostyTheSnowmans = initInt("fishing", "snowman", 0);
		FishingTracker.grinches = initInt("fishing", "grinch", 0);
		FishingTracker.yetis = initInt("fishing", "yeti", 0);
		FishingTracker.yetiTime = initDouble("fishing", "yetiTime", -1);
		FishingTracker.yetiSCs = initInt("fishing", "yetiSC", -1);
		// Fishing Festival
		FishingTracker.nurseSharks = initInt("fishing", "nurseShark", 0);
		FishingTracker.blueSharks = initInt("fishing", "blueShark", 0);
		FishingTracker.tigerSharks = initInt("fishing", "tigerShark", 0);
		FishingTracker.greatWhiteSharks = initInt("fishing", "greatWhiteShark", 0);
		// Spooky Fishing
		FishingTracker.scarecrows = initInt("fishing", "scarecrow", 0);
		FishingTracker.nightmares = initInt("fishing", "nightmare", 0);
		FishingTracker.werewolfs = initInt("fishing", "werewolf", 0);
		FishingTracker.phantomFishers = initInt("fishing", "phantomFisher", 0);
		FishingTracker.grimReapers = initInt("fishing", "grimReaper", 0);
		// CH Fishing
		FishingTracker.waterWorms = initInt("fishing", "waterWorm", 0);
		FishingTracker.poisonedWaterWorms = initInt("fishing", "poisonedWaterWorm", 0);
		FishingTracker.flamingWorms = initInt("fishing", "flamingWorm", 0);
		FishingTracker.lavaBlazes = initInt("fishing", "lavaBlaze", 0);
		FishingTracker.lavaPigmen = initInt("fishing", "lavaPigman", 0);
		FishingTracker.zombieMiners = initInt("fishing", "zombieMiner", 0);
		// Lava Fishing
		FishingTracker.magmaSlugs = initInt("fishing", "magmaSlug", 0);
		FishingTracker.moogmas = initInt("fishing", "moogma", 0);
		FishingTracker.lavaLeeches = initInt("fishing", "lavaLeech", 0);
		FishingTracker.pyroclasticWorms = initInt("fishing", "pyroclasticWorm", 0);
		FishingTracker.lavaFlames = initInt("fishing", "lavaFlame", 0);
		FishingTracker.fireEels = initInt("fishing", "fireEel", 0);
		FishingTracker.tauruses = initInt("fishing", "taurus", 0);
		FishingTracker.thunders = initInt("fishing", "thunder", 0);
		FishingTracker.lordJawbuses = initInt("fishing", "lordJawbus", 0);
		FishingTracker.jawbusTime = initDouble("fishing", "jawbusTime", -1);
		FishingTracker.jawbusSCs = initInt("fishing", "jawbusSC", -1);
		
		// Mythological
		MythologicalTracker.mythCoins = initDouble("mythological", "coins", 0);
		MythologicalTracker.griffinFeathers = initInt("mythological", "griffinFeather", 0);
		MythologicalTracker.crownOfGreeds = initInt("mythological", "crownOfGreed", 0);
		MythologicalTracker.washedUpSouvenirs = initInt("mythological", "washedUpSouvenir", 0);
		MythologicalTracker.minosHunters = initInt("mythological", "minosHunter", 0);
		MythologicalTracker.siameseLynxes = initInt("mythological", "siameseLynx", 0);
		MythologicalTracker.minotaurs = initInt("mythological", "minotaur", 0);
		MythologicalTracker.gaiaConstructs = initInt("mythological", "gaiaConstruct", 0);
		MythologicalTracker.minosChampions = initInt("mythological", "minosChampion", 0);
		MythologicalTracker.minosInquisitors = initInt("mythological", "minosInquisitor", 0);
		
		// Dungeons
		CatacombsTracker.recombobulators =  initInt("catacombs", "recombobulator", 0);
		CatacombsTracker.fumingPotatoBooks = initInt("catacombs", "fumingBooks", 0);
		// F1
		CatacombsTracker.f1SPlus = initInt("catacombs", "floorOneSPlus", 0);
		CatacombsTracker.bonzoStaffs = initInt("catacombs", "bonzoStaff", 0);
		CatacombsTracker.f1CoinsSpent = initDouble("catacombs", "floorOneCoins", 0);
		CatacombsTracker.f1TimeSpent = initDouble("catacombs", "floorOneTime", 0);
		// F2
		CatacombsTracker.f2SPlus = initInt("catacombs", "floorTwoSPlus", 0);
		CatacombsTracker.scarfStudies = initInt("catacombs", "scarfStudies", 0);
		CatacombsTracker.f2CoinsSpent = initDouble("catacombs", "floorTwoCoins", 0);
		CatacombsTracker.f2TimeSpent = initDouble("catacombs", "floorTwoTime", 0);
		// F3
		CatacombsTracker.f3SPlus = initInt("catacombs", "floorThreeSPlus", 0);
		CatacombsTracker.adaptiveHelms = initInt("catacombs", "adaptiveHelm", 0);
		CatacombsTracker.adaptiveChests = initInt("catacombs", "adaptiveChest", 0);
		CatacombsTracker.adaptiveLegs = initInt("catacombs", "adaptiveLegging", 0);
		CatacombsTracker.adaptiveBoots = initInt("catacombs", "adaptiveBoot", 0);
		CatacombsTracker.adaptiveSwords = initInt("catacombs", "adaptiveSword", 0);
		CatacombsTracker.f3CoinsSpent = initDouble("catacombs", "floorThreeCoins", 0);
		CatacombsTracker.f3TimeSpent = initDouble("catacombs", "floorThreeTime", 0);
		// F4
		CatacombsTracker.f4SPlus = initInt("catacombs", "floorFourSPlus", 0);
		CatacombsTracker.spiritWings = initInt("catacombs", "spiritWing", 0);
		CatacombsTracker.spiritBones = initInt("catacombs", "spiritBone", 0);
		CatacombsTracker.spiritBoots = initInt("catacombs", "spiritBoot", 0);
		CatacombsTracker.spiritSwords = initInt("catacombs", "spiritSword", 0);
		CatacombsTracker.spiritBows = initInt("catacombs", "spiritBow", 0);
		CatacombsTracker.epicSpiritPets = initInt("catacombs", "spiritPetEpic", 0);
		CatacombsTracker.legSpiritPets = initInt("catacombs", "spiritPetLeg", 0);
		CatacombsTracker.f4CoinsSpent = initDouble("catacombs", "floorFourCoins", 0);
		CatacombsTracker.f4TimeSpent = initDouble("catacombs", "floorFourTime", 0);
		// F5
		CatacombsTracker.f5SPlus = initInt("catacombs", "floorFiveSPlus", 0);
		CatacombsTracker.warpedStones = initInt("catacombs", "warpedStone", 0);
		CatacombsTracker.shadowAssHelms = initInt("catacombs", "shadowAssassinHelm", 0);
		CatacombsTracker.shadowAssChests = initInt("catacombs", "shadowAssassinChest", 0);
		CatacombsTracker.shadowAssLegs = initInt("catacombs", "shadowAssassinLegging", 0);
		CatacombsTracker.shadowAssBoots = initInt("catacombs", "shadowAssassinBoot", 0);
		CatacombsTracker.lastBreaths = initInt("catacombs", "lastBreath", 0);
		CatacombsTracker.lividDaggers = initInt("catacombs", "lividDagger", 0);
		CatacombsTracker.shadowFurys = initInt("catacombs", "shadowFury", 0);
		CatacombsTracker.f5CoinsSpent = initDouble("catacombs", "floorFiveCoins", 0);
		CatacombsTracker.f5TimeSpent = initDouble("catacombs", "floorFiveTime", 0);
		// F6
		CatacombsTracker.f6SPlus = initInt("catacombs", "floorSixSPlus", 0);
		CatacombsTracker.ancientRoses = initInt("catacombs", "ancientRose", 0);
		CatacombsTracker.precursorEyes = initInt("catacombs", "precursorEye", 0);
		CatacombsTracker.giantsSwords = initInt("catacombs", "giantsSword", 0);
		CatacombsTracker.necroLordHelms = initInt("catacombs", "necroLordHelm", 0);
		CatacombsTracker.necroLordChests = initInt("catacombs", "necroLordChest", 0);
		CatacombsTracker.necroLordLegs = initInt("catacombs", "necroLordLegging", 0);
		CatacombsTracker.necroLordBoots = initInt("catacombs", "necroLordBoot", 0);
		CatacombsTracker.necroSwords = initInt("catacombs", "necroSword", 0);
		CatacombsTracker.f6Rerolls = initInt("catacombs", "floorSixRerolls", 0);
		CatacombsTracker.f6CoinsSpent = initDouble("catacombs", "floorSixCoins", 0);
		CatacombsTracker.f6TimeSpent = initDouble("catacombs", "floorSixTime", 0);
		// F7
		CatacombsTracker.f7SPlus = initInt("catacombs", "floorSevenSPlus", 0);
		CatacombsTracker.witherBloods = initInt("catacombs", "witherBlood", 0);
		CatacombsTracker.witherCloaks = initInt("catacombs", "witherCloak", 0);
		CatacombsTracker.implosions = initInt("catacombs", "implosion", 0);
		CatacombsTracker.witherShields = initInt("catacombs", "witherShield", 0);
		CatacombsTracker.shadowWarps = initInt("catacombs", "shadowWarp", 0);
		CatacombsTracker.necronsHandles = initInt("catacombs", "necronsHandle", 0);
		CatacombsTracker.autoRecombs = initInt("catacombs", "autoRecomb", 0);
		CatacombsTracker.witherHelms = initInt("catacombs", "witherHelm", 0);
		CatacombsTracker.witherChests = initInt("catacombs", "witherChest", 0);
		CatacombsTracker.witherLegs = initInt("catacombs", "witherLegging", 0);
		CatacombsTracker.witherBoots = initInt("catacombs", "witherBoot", 0);
		CatacombsTracker.f7Rerolls = initInt("catacombs", "floorSevenRerolls", 0);
		CatacombsTracker.f7CoinsSpent = initDouble("catacombs", "floorSevenCoins", 0);
		CatacombsTracker.f7TimeSpent = initDouble("catacombs", "floorSevenTime", 0);
		// MM
		CatacombsTracker.m1S = initInt("catacombs", "masterOneS", 0);
		CatacombsTracker.m1SPlus = initInt("catacombs", "masterOneSPlus", 0);
		CatacombsTracker.m2S = initInt("catacombs", "masterTwoS", 0);
		CatacombsTracker.m2SPlus = initInt("catacombs", "masterTwoSPlus", 0);
		CatacombsTracker.m3S = initInt("catacombs", "masterThreeS", 0);
		CatacombsTracker.m3SPlus = initInt("catacombs", "masterThreeSPlus", 0);
		CatacombsTracker.m4S = initInt("catacombs", "masterFourS", 0);
		CatacombsTracker.m4SPlus = initInt("catacombs", "masterFourSPlus", 0);
		CatacombsTracker.m5S = initInt("catacombs", "masterFiveS", 0);
		CatacombsTracker.m5SPlus = initInt("catacombs", "masterFiveSPlus", 0);
		CatacombsTracker.m6S = initInt("catacombs", "masterSixS", 0);
		CatacombsTracker.m6SPlus = initInt("catacombs", "masterSixSPlus", 0);
		CatacombsTracker.m7S = initInt("catacombs", "masterSevenS", 0);
		CatacombsTracker.m7SPlus = initInt("catacombs", "masterSevenSPlus", 0);
		CatacombsTracker.firstStars = initInt("catacombs", "firstStar", 0);
		CatacombsTracker.secondStars = initInt("catacombs", "secondStar", 0);
		CatacombsTracker.thirdStars = initInt("catacombs", "thirdStar", 0);
		CatacombsTracker.fourthStars = initInt("catacombs", "fourthStar", 0);
		CatacombsTracker.fifthStars = initInt("catacombs", "fifthStar", 0);
		CatacombsTracker.necronDyes = initInt("catacombs", "necronDye", 0);
		CatacombsTracker.darkClaymores = initInt("catacombs", "darkClaymore", 0);
		CatacombsTracker.masterRerolls = initInt("catacombs", "masterRerolls", 0);
		CatacombsTracker.masterCoinsSpent = initDouble("catacombs", "masterCoins", 0);
		CatacombsTracker.masterTimeSpent = initDouble("catacombs", "masterTime", 0);

		// Ghost
		GhostTracker.sorrows = initInt("ghosts", "sorrow", 0);
		GhostTracker.voltas = initInt("ghosts", "volta", 0);
		GhostTracker.plasmas = initInt("ghosts", "plasma", 0);
		GhostTracker.ghostlyBoots = initInt("ghosts", "ghostlyBoots", 0);
		GhostTracker.bagOfCashs = initInt("ghosts", "bagOfCash", 0);

		// Misc
		LootDisplay.display = initString("misc", "display", "off");
		LootDisplay.auto = initBoolean("misc", "autoDisplay", false);
		Skill50Display.SKILL_TIME = initInt("misc", "skill50Time", 3) * 20;
		CakeTimer.cakeTime = initDouble("misc", "cakeTime", 0);
		SkillTracker.showSkillTracker = initBoolean("misc", "showSkillTracker", false);
		DankersSkyblockMod.firstLaunch = initBoolean("misc", "firstLaunch", true);
		EndOfFarmAlert.min = initDouble("misc", "farmMin", -78.5);
		EndOfFarmAlert.max = initDouble("misc", "farmMax", 79.5);
		Utils.TITLE_SOUND = initString("misc", "titleSound", "random.orb");

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
		MoveCommand.teammatesInRadiusXY[0] = initInt("locations", "teammatesInRadiusX", 80);
		MoveCommand.teammatesInRadiusXY[1] = initInt("locations", "teammatesInRadiusY", 100);
		MoveCommand.giantHPXY[0] = initInt("locations", "giantHPX", 80);
		MoveCommand.giantHPXY[1] = initInt("locations", "giantHPY", 150);
		MoveCommand.abilityCooldownsXY[0] = initInt("locations", "abilityCooldownsX", 120);
		MoveCommand.abilityCooldownsXY[1] = initInt("locations", "abilityCooldownsY", 150);
		MoveCommand.dungeonScoreXY[0] = initInt("locations", "dungeonScoreX", 150);
		MoveCommand.dungeonScoreXY[1] = initInt("locations", "dungeonScoreY", 150);
		MoveCommand.firePillarXY[0] = initInt("locations", "firePillarX", 200);
		MoveCommand.firePillarXY[1] = initInt("locations", "firePillarY", 200);

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
		ScaleCommand.teammatesInRadiusScale = initDouble("scales", "teammatesInRadiusScale", 1);
		ScaleCommand.giantHPScale = initDouble("scales", "giantHPScale", 1);
		ScaleCommand.abilityCooldownsScale = initDouble("scales", "abilityCooldownsScale", 1);
		ScaleCommand.dungeonScoreScale = initDouble("scales", "dungeonScoreScale", 1);
		ScaleCommand.firePillarScale = initDouble("scales", "firePillarScale", 1);

		// Skills
		DankersSkyblockMod.farmingLevel = initInt("skills", "farming", -1);
		DankersSkyblockMod.miningLevel = initInt("skills", "mining", -1);
		DankersSkyblockMod.combatLevel = initInt("skills", "combat", -1);
		DankersSkyblockMod.foragingLevel = initInt("skills", "foraging", -1);
		DankersSkyblockMod.fishingLevel = initInt("skills", "fishing", -1);
		DankersSkyblockMod.enchantingLevel = initInt("skills", "enchanting", -1);
		DankersSkyblockMod.alchemyLevel = initInt("skills", "alchemy", -1);
		DankersSkyblockMod.carpentryLevel = initInt("skills", "carpentry", -1);

		// Colours
		DankersSkyblockMod.MAIN_COLOUR = initString("colors", "main", EnumChatFormatting.GREEN.toString());
		DankersSkyblockMod.SECONDARY_COLOUR = initString("colors", "secondary", EnumChatFormatting.DARK_GREEN.toString());
		DankersSkyblockMod.DELIMITER_COLOUR = initString("colors", "delimiter", EnumChatFormatting.AQUA.toString() + EnumChatFormatting.STRIKETHROUGH);
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
		HighlightSkeletonMasters.SKELETON_MASTER_COLOUR = initInt("colors", "skeletonMaster", 0xFF0000);
		PetColours.PET_1_TO_9 = initInt("colors", "pet1To9", 0x999999); // Grey
		PetColours.PET_10_TO_19 = initInt("colors", "pet10To19", 0xD62440); // Red
		PetColours.PET_20_TO_29 = initInt("colors", "pet20To29", 0xEF5230); // Orange
		PetColours.PET_30_TO_39 = initInt("colors", "pet30To39", 0x113CF2); // Dark Blue
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

		// JSON
		Gson gson = new Gson();

		try {
			if (!(new File(Alerts.configFile).exists())) {
				FileWriter file = new FileWriter(Alerts.configFile);
				file.write(new JsonArray().toString());
				file.close();
			}
			Alerts.Alert[] alerts = gson.fromJson(new FileReader(Alerts.configFile), Alerts.Alert[].class);
			if (alerts != null) Alerts.alerts = new ArrayList<>(Arrays.asList(alerts));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}
