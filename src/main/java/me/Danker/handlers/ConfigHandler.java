package me.Danker.handlers;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.DisplayCommand;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.features.*;
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
	
	public static void reloadConfig() {
		// Config init
		if (!hasKey("toggles", "GParty")) writeBooleanConfig("toggles", "GParty", false);
		if (!hasKey("toggles", "Coords")) writeBooleanConfig("toggles", "Coords", false);
		if (!hasKey("toggles", "Golden")) writeBooleanConfig("toggles", "Golden", false);
		if (!hasKey("toggles", "SlayerCount")) writeBooleanConfig("toggles", "SlayerCount", true);
		if (!hasKey("toggles", "RNGesusAlerts")) writeBooleanConfig("toggles", "RNGesusAlerts", true);
		if (!hasKey("toggles", "SplitFishing")) writeBooleanConfig("toggles", "SplitFishing", true);
		if (!hasKey("toggles", "ChatMaddox")) writeBooleanConfig("toggles", "ChatMaddox", true);
		if (!hasKey("toggles", "SpiritBearAlerts")) writeBooleanConfig("toggles", "SpiritBearAlerts", true);
		if (!hasKey("toggles", "AOTD")) writeBooleanConfig("toggles", "AOTD", false);
		if (!hasKey("toggles", "LividDagger")) writeBooleanConfig("toggles", "LividDagger", false);
		if (!hasKey("toggles", "MidasStaffMessages")) writeBooleanConfig("toggles", "MidasStaffMessages", true);
		if (!hasKey("toggles", "ImplosionMessages")) writeBooleanConfig("toggles", "ImplosionMessages", true);
		if (!hasKey("toggles", "HealMessages")) writeBooleanConfig("toggles", "HealMessages", true);
		if (!hasKey("toggles", "PetColors")) writeBooleanConfig("toggles", "PetColors", false);
		if (!hasKey("toggles", "BlockSlayer")) writeStringConfig("toggles", "BlockSlayer", "");
		if (!hasKey("toggles", "GolemAlerts")) writeBooleanConfig("toggles", "GolemAlerts", false);
		if (!hasKey("toggles", "ExpertiseLore")) writeBooleanConfig("toggles", "ExpertiseLore", true);
		if (!hasKey("toggles", "Skill50Display")) writeBooleanConfig("toggles", "Skill50Display", false);
		if (!hasKey("toggles", "OutlineText")) writeBooleanConfig("toggles", "OutlineText", false);
		if (!hasKey("toggles", "CakeTimer")) writeBooleanConfig("toggles", "CakeTimer", false);
		// Chat Messages
		if (!hasKey("toggles", "SceptreMessages")) writeBooleanConfig("toggles", "SceptreMessages", true);
		if (!hasKey("toggles", "MidasStaffMessages")) writeBooleanConfig("toggles", "MidasStaffMessages", true);
		if (!hasKey("toggles", "ImplosionMessages")) writeBooleanConfig("toggles", "ImplosionMessages", true);
		if (!hasKey("toggles", "HealMessages")) writeBooleanConfig("toggles", "HealMessages", true);
		if (!hasKey("toggles", "CooldownMessages")) writeBooleanConfig("toggles","CooldownMessages",true);
		if (!hasKey("toggles", "ManaMessages")) writeBooleanConfig("toggles","ManaMessages",true);
		// Dungeons
		if (!hasKey("toggles", "DungeonTimer")) writeBooleanConfig("toggles", "DungeonTimer", false);
		if (!hasKey("toggles", "LowHealthNotify")) writeBooleanConfig("toggles", "LowHealthNotify", false);
		if (!hasKey("toggles", "LividSolver")) writeBooleanConfig("toggles", "LividSolver", false);
		if (!hasKey("toggles", "StopSalvageStarred")) writeBooleanConfig("toggles", "StopSalvageStarred", false);
		if (!hasKey("toggles", "WatcherReadyMessage")) writeBooleanConfig("toggles", "WatcherReadyMessage", false);
		if (!hasKey("toggles", "PickBlock")) writeBooleanConfig("toggles", "PickBlock", false);
		if (!hasKey("toggles", "FlowerWeapons")) writeBooleanConfig("toggles", "FlowerWeapons", false);
		if (!hasKey("toggles", "NotifySlayerSlain")) writeBooleanConfig("toggles", "NotifySlayerSlain", false);
		if (!hasKey("toggles", "NecronNotifications")) writeBooleanConfig("toggles", "NecronNotifications", false);
		if (!hasKey("toggles", "BonzoTimer")) writeBooleanConfig("toggles", "BonzoTimer", false);
		if (!hasKey("toggles", "AutoSkillTracker")) writeBooleanConfig("toggles", "AutoSkillTracker", false);
		// Puzzle Solvers
		if (!hasKey("toggles", "ThreeManPuzzle")) writeBooleanConfig("toggles", "ThreeManPuzzle", false);
		if (!hasKey("toggles", "OruoPuzzle")) writeBooleanConfig("toggles", "OruoPuzzle", false);
		if (!hasKey("toggles", "BlazePuzzle")) writeBooleanConfig("toggles", "BlazePuzzle", false);
		if (!hasKey("toggles", "CreeperPuzzle")) writeBooleanConfig("toggles", "CreeperPuzzle", false);
		if (!hasKey("toggles", "WaterPuzzle")) writeBooleanConfig("toggles", "WaterPuzzle", false);
		if (!hasKey("toggles", "TicTacToePuzzle")) writeBooleanConfig("toggles", "TicTacToePuzzle", false);
		if (!hasKey("toggles", "StartsWithTerminal")) writeBooleanConfig("toggles", "StartsWithTerminal", false);
		if (!hasKey("toggles", "SelectAllTerminal")) writeBooleanConfig("toggles", "SelectAllTerminal", false);
		if (!hasKey("toggles", "ClickInOrderTerminal")) writeBooleanConfig("toggles", "ClickInOrderTerminal", false);
		if (!hasKey("toggles", "BlockWrongTerminalClicks")) writeBooleanConfig("toggles", "BlockWrongTerminalClicks", false);
		if (!hasKey("toggles", "IgnoreItemFrameOnSeaLanterns")) writeBooleanConfig("toggles", "IgnoreItemFrameOnSeaLanterns", false);
		// Experiment Solvers
		if (!hasKey("toggles", "UltraSequencer")) writeBooleanConfig("toggles", "UltraSequencer", false);
		if (!hasKey("toggles", "Chronomatron")) writeBooleanConfig("toggles", "Chronomatron", false);
		if (!hasKey("toggles", "Superpairs")) writeBooleanConfig("toggles", "Superpairs", false);
		if (!hasKey("toggles", "HideTooltipsInExperimentAddons")) writeBooleanConfig("toggles", "HideTooltipsInExperimentAddons", false);

		if (!hasKey("api", "APIKey")) writeStringConfig("api", "APIKey", "");
		
		// Wolf Loot
		if (!hasKey("wolf", "svens")) writeIntConfig("wolf", "svens", 0);
		if (!hasKey("wolf", "teeth")) writeIntConfig("wolf", "teeth", 0);
		if (!hasKey("wolf", "wheel")) writeIntConfig("wolf", "wheel", 0);
		if (!hasKey("wolf", "wheelDrops")) writeIntConfig("wolf", "wheelDrops", 0);
		if (!hasKey("wolf", "spirit")) writeIntConfig("wolf", "spirit", 0);
		if (!hasKey("wolf", "book")) writeIntConfig("wolf", "book", 0);
		if (!hasKey("wolf", "egg")) writeIntConfig("wolf", "egg", 0);
		if (!hasKey("wolf", "couture")) writeIntConfig("wolf", "couture", 0);
		if (!hasKey("wolf", "bait")) writeIntConfig("wolf", "bait", 0);
		if (!hasKey("wolf", "flux")) writeIntConfig("wolf", "flux", 0);
		if (!hasKey("wolf", "timeRNG")) writeDoubleConfig("wolf", "timeRNG", -1);
		if (!hasKey("wolf", "bossRNG")) writeIntConfig("wolf", "bossRNG", -1);
		// Spider Loot
		if (!hasKey("spider", "tarantulas")) writeIntConfig("spider", "tarantulas", 0);
		if (!hasKey("spider", "web")) writeIntConfig("spider", "web", 0);
		if (!hasKey("spider", "tap")) writeIntConfig("spider", "tap", 0);
		if (!hasKey("spider", "tapDrops")) writeIntConfig("spider", "tapDrops", 0);
		if (!hasKey("spider", "bite")) writeIntConfig("spider", "bite", 0);
		if (!hasKey("spider", "catalyst")) writeIntConfig("spider", "catalyst", 0);
		if (!hasKey("spider", "book")) writeIntConfig("spider", "book", 0);
		if (!hasKey("spider", "swatter")) writeIntConfig("spider", "swatter", 0);
		if (!hasKey("spider", "talisman")) writeIntConfig("spider", "talisman", 0);
		if (!hasKey("spider", "mosquito")) writeIntConfig("spider", "mosquito", 0);
		if (!hasKey("spider", "timeRNG")) writeDoubleConfig("spider", "timeRNG", -1);
		if (!hasKey("spider", "bossRNG")) writeIntConfig("spider", "bossRNG", -1);
		// Zombie Loot
		if (!hasKey("zombie", "revs")) writeIntConfig("zombie", "revs", 0);
		if (!hasKey("zombie", "revFlesh")) writeIntConfig("zombie", "revFlesh", 0);
		if (!hasKey("zombie", "foulFlesh")) writeIntConfig("zombie", "foulFlesh", 0);
		if (!hasKey("zombie", "foulFleshDrops")) writeIntConfig("zombie", "foulFleshDrops", 0);
		if (!hasKey("zombie", "pestilence")) writeIntConfig("zombie", "pestilence", 0);
		if (!hasKey("zombie", "undeadCatalyst")) writeIntConfig("zombie", "undeadCatalyst", 0);
		if (!hasKey("zombie", "book")) writeIntConfig("zombie", "book", 0);
		if (!hasKey("zombie", "beheaded")) writeIntConfig("zombie", "beheaded", 0);
		if (!hasKey("zombie", "revCatalyst")) writeIntConfig("zombie", "revCatalyst", 0);
		if (!hasKey("zombie", "snake")) writeIntConfig("zombie", "snake", 0);
		if (!hasKey("zombie", "scythe")) writeIntConfig("zombie", "scythe", 0);
		if (!hasKey("zombie", "timeRNG")) writeDoubleConfig("zombie", "timeRNG", -1);
		if (!hasKey("zombie", "bossRNG")) writeIntConfig("zombie", "bossRNG", -1);
		
		// Fishing
		if (!hasKey("fishing", "seaCreature")) writeIntConfig("fishing", "seaCreature", 0);
		if (!hasKey("fishing", "goodCatch")) writeIntConfig("fishing", "goodCatch", 0);
		if (!hasKey("fishing", "greatCatch")) writeIntConfig("fishing", "greatCatch", 0);
		if (!hasKey("fishing", "squid")) writeIntConfig("fishing", "squid", 0);
		if (!hasKey("fishing", "seaWalker")) writeIntConfig("fishing", "seaWalker", 0);
		if (!hasKey("fishing", "nightSquid")) writeIntConfig("fishing", "nightSquid", 0);
		if (!hasKey("fishing", "seaGuardian")) writeIntConfig("fishing", "seaGuardian", 0);
		if (!hasKey("fishing", "seaWitch")) writeIntConfig("fishing", "seaWitch", 0);
		if (!hasKey("fishing", "seaArcher")) writeIntConfig("fishing", "seaArcher", 0);
		if (!hasKey("fishing", "monsterOfDeep")) writeIntConfig("fishing", "monsterOfDeep", 0);
		if (!hasKey("fishing", "catfish")) writeIntConfig("fishing", "catfish", 0);
		if (!hasKey("fishing", "carrotKing")) writeIntConfig("fishing", "carrotKing", 0);
		if (!hasKey("fishing", "seaLeech")) writeIntConfig("fishing", "seaLeech", 0);
		if (!hasKey("fishing", "guardianDefender")) writeIntConfig("fishing", "guardianDefender", 0);
		if (!hasKey("fishing", "deepSeaProtector")) writeIntConfig("fishing", "deepSeaProtector", 0);
		if (!hasKey("fishing", "hydra")) writeIntConfig("fishing", "hydra", 0);
		if (!hasKey("fishing", "seaEmperor")) writeIntConfig("fishing", "seaEmperor", 0);
		if (!hasKey("fishing", "empTime")) writeDoubleConfig("fishing", "empTime", -1);
		if (!hasKey("fishing", "empSC")) writeIntConfig("fishing", "empSC", -1);
		if (!hasKey("fishing", "milestone")) writeIntConfig("fishing", "milestone", 0);
		// Fishing Winter
		if (!hasKey("fishing", "frozenSteve")) writeIntConfig("fishing", "frozenSteve", 0);
		if (!hasKey("fishing", "snowman")) writeIntConfig("fishing", "snowman", 0);
		if (!hasKey("fishing", "grinch")) writeIntConfig("fishing", "grinch", 0);
		if (!hasKey("fishing", "yeti")) writeIntConfig("fishing", "yeti", 0);
		if (!hasKey("fishing", "yetiTime")) writeDoubleConfig("fishing", "yetiTime", -1);
		if (!hasKey("fishing", "yetiSC")) writeIntConfig("fishing", "yetiSC", -1);
		// Fishing Festival
		if (!hasKey("fishing", "nurseShark")) writeIntConfig("fishing", "nurseShark", 0);
		if (!hasKey("fishing", "blueShark")) writeIntConfig("fishing", "blueShark", 0);
		if (!hasKey("fishing", "tigerShark")) writeIntConfig("fishing", "tigerShark", 0);
		if (!hasKey("fishing", "greatWhiteShark")) writeIntConfig("fishing", "greatWhiteShark", 0);
		// Spooky Fishing
		if (!hasKey("fishing", "scarecrow")) writeIntConfig("fishing", "scarecrow", 0);
		if (!hasKey("fishing", "nightmare")) writeIntConfig("fishing", "nightmare", 0);
		if (!hasKey("fishing", "werewolf")) writeIntConfig("fishing", "werewolf", 0);
		if (!hasKey("fishing", "phantomFisher")) writeIntConfig("fishing", "phantomFisher", 0);
		if (!hasKey("fishing", "grimReaper")) writeIntConfig("fishing", "grimReaper", 0);
		
		// Mythological
		if (!hasKey("mythological", "coins")) writeDoubleConfig("mythological", "coins", 0);
		if (!hasKey("mythological", "griffinFeather")) writeIntConfig("mythological", "griffinFeather", 0);
		if (!hasKey("mythological", "crownOfGreed")) writeIntConfig("mythological", "crownOfGreed", 0);
		if (!hasKey("mythological", "washedUpSouvenir")) writeIntConfig("mythological", "washedUpSouvenir", 0);
		if (!hasKey("mythological", "minosHunter")) writeIntConfig("mythological", "minosHunter", 0);
		if (!hasKey("mythological", "siameseLynx")) writeIntConfig("mythological", "siameseLynx", 0);
		if (!hasKey("mythological", "minotaur")) writeIntConfig("mythological", "minotaur", 0);
		if (!hasKey("mythological", "gaiaConstruct")) writeIntConfig("mythological", "gaiaConstruct", 0);
		if (!hasKey("mythological", "minosChampion")) writeIntConfig("mythological", "minosChampion", 0);
		if (!hasKey("mythological", "minosInquisitor")) writeIntConfig("mythological", "minosInquisitor", 0);
		
		// Dungeons
		if (!hasKey("catacombs", "recombobulator")) writeIntConfig("catacombs", "recombobulator", 0);
		if (!hasKey("catacombs", "fumingBooks")) writeIntConfig("catacombs", "fumingBooks", 0);
		// F1
		if (!hasKey("catacombs", "bonzoStaff")) writeIntConfig("catacombs", "bonzoStaff", 0);
		if (!hasKey("catacombs", "floorOneCoins")) writeDoubleConfig("catacombs", "floorOneCoins", 0);
		if (!hasKey("catacombs", "floorOneTime")) writeDoubleConfig("catacombs", "floorOneTime", 0);
		// F2
		if (!hasKey("catacombs", "scarfStudies")) writeIntConfig("catacombs", "scarfStudies", 0);
		if (!hasKey("catacombs", "floorTwoCoins")) writeDoubleConfig("catacombs", "floorTwoCoins", 0);
		if (!hasKey("catacombs", "floorTwoTime")) writeDoubleConfig("catacombs", "floorTwoTime", 0);
		// F3
		if (!hasKey("catacombs", "adaptiveHelm")) writeIntConfig("catacombs", "adaptiveHelm", 0);
		if (!hasKey("catacombs", "adaptiveChest")) writeIntConfig("catacombs", "adaptiveChest", 0);
		if (!hasKey("catacombs", "adaptiveLegging")) writeIntConfig("catacombs", "adaptiveLegging", 0);
		if (!hasKey("catacombs", "adaptiveBoot")) writeIntConfig("catacombs", "adaptiveBoot", 0);
		if (!hasKey("catacombs", "adaptiveSword")) writeIntConfig("catacombs", "adaptiveSword", 0);
		if (!hasKey("catacombs", "floorThreeCoins")) writeDoubleConfig("catacombs", "floorThreeCoins", 0);
		if (!hasKey("catacombs", "floorThreeTime")) writeDoubleConfig("catacombs", "floorThreeTime", 0);
		// F4
		if (!hasKey("catacombs", "spiritWing")) writeIntConfig("catacombs", "spiritWing", 0);
		if (!hasKey("catacombs", "spiritBone")) writeIntConfig("catacombs", "spiritBone", 0);
		if (!hasKey("catacombs", "spiritBoot")) writeIntConfig("catacombs", "spiritBoot", 0);
		if (!hasKey("catacombs", "spiritSword")) writeIntConfig("catacombs", "spiritSword", 0);
		if (!hasKey("catacombs", "spiritBow")) writeIntConfig("catacombs", "spiritBow", 0);
		if (!hasKey("catacombs", "spiritPetEpic")) writeIntConfig("catacombs", "spiritPetEpic", 0);
		if (!hasKey("catacombs", "spiritPetLeg")) writeIntConfig("catacombs", "spiritPetLeg", 0);
		if (!hasKey("catacombs", "floorFourCoins")) writeDoubleConfig("catacombs", "floorFourCoins", 0);
		if (!hasKey("catacombs", "floorFourTime")) writeDoubleConfig("catacombs", "floorFourTime", 0);
		// F5
		if (!hasKey("catacombs", "warpedStone")) writeIntConfig("catacombs", "warpedStone", 0);
		if (!hasKey("catacombs", "shadowAssassinHelm")) writeIntConfig("catacombs", "shadowAssassinHelm", 0);
		if (!hasKey("catacombs", "shadowAssassinChest")) writeIntConfig("catacombs", "shadowAssassinChest", 0);
		if (!hasKey("catacombs", "shadowAssassinLegging")) writeIntConfig("catacombs", "shadowAssassinLegging", 0);
		if (!hasKey("catacombs", "shadowAssassinBoot")) writeIntConfig("catacombs", "shadowAssassinBoot", 0);
		if (!hasKey("catacombs", "lastBreath")) writeIntConfig("catacombs", "lastBreath", 0);
		if (!hasKey("catacombs", "lividDagger")) writeIntConfig("catacombs", "lividDagger", 0);
		if (!hasKey("catacombs", "shadowFury")) writeIntConfig("catacombs", "shadowFury", 0);
		if (!hasKey("catacombs", "floorFiveCoins")) writeDoubleConfig("catacombs", "floorFiveCoins", 0);
		if (!hasKey("catacombs", "floorFiveTime")) writeDoubleConfig("catacombs", "floorFiveTime", 0);
		// F6
		if (!hasKey("catacombs", "ancientRose")) writeIntConfig("catacombs", "ancientRose", 0);
		if (!hasKey("catacombs", "precursorEye")) writeIntConfig("catacombs", "precursorEye", 0);
		if (!hasKey("catacombs", "giantsSword")) writeIntConfig("catacombs", "giantsSword", 0);
		if (!hasKey("catacombs", "necroLordHelm")) writeIntConfig("catacombs", "necroHelm", 0);
		if (!hasKey("catacombs", "necroLordChest")) writeIntConfig("catacombs", "necroChest", 0);
		if (!hasKey("catacombs", "necroLordLegging")) writeIntConfig("catacombs", "necroLegging", 0);
		if (!hasKey("catacombs", "necroLordBoot")) writeIntConfig("catacombs", "necroBoot", 0);
		if (!hasKey("catacombs", "necroSword")) writeIntConfig("catacombs", "necroSword", 0);
		if (!hasKey("catacombs", "floorSixCoins")) writeDoubleConfig("catacombs", "floorSixCoins", 0);
		if (!hasKey("catacombs", "floorSixTime")) writeDoubleConfig("catacombs", "floorSixTime", 0);
		// F7
		if (!hasKey("catacombs", "witherBlood")) writeIntConfig("catacombs", "witherBlood", 0);
		if (!hasKey("catacombs", "witherCloak")) writeIntConfig("catacombs", "witherCloak", 0);
		if (!hasKey("catacombs", "implosion")) writeIntConfig("catacombs", "implosion", 0);
		if (!hasKey("catacombs", "witherShield")) writeIntConfig("catacombs", "witherShield", 0);
		if (!hasKey("catacombs", "shadowWarp")) writeIntConfig("catacombs", "shadowWarp", 0);
		if (!hasKey("catacombs", "necronsHandle")) writeIntConfig("catacombs", "necronsHandle", 0);
		if (!hasKey("catacombs", "autoRecomb")) writeIntConfig("catacombs", "autoRecomb", 0);
		if (!hasKey("catacombs", "witherHelm")) writeIntConfig("catacombs", "witherHelm", 0);
		if (!hasKey("catacombs", "witherChest")) writeIntConfig("catacombs", "witherChest", 0);
		if (!hasKey("catacombs", "witherLegging")) writeIntConfig("catacombs", "witherLegging", 0);
		if (!hasKey("catacombs", "witherBoot")) writeIntConfig("catacombs", "witherBoot", 0);
		if (!hasKey("catacombs", "floorSevenCoins")) writeDoubleConfig("catacombs", "floorSevenCoins", 0);
		if (!hasKey("catacombs", "floorSevenTime")) writeDoubleConfig("catacombs", "floorSevenTime", 0);
		
		if (!hasKey("misc", "display")) writeStringConfig("misc", "display", "off");
		if (!hasKey("misc", "autoDisplay")) writeBooleanConfig("misc", "autoDisplay", false);
		if (!hasKey("misc", "skill50Time")) writeIntConfig("misc", "skill50Time", 3);
		if (!hasKey("misc", "cakeTime")) writeDoubleConfig("misc", "cakeTime", 0);
		if (!hasKey("misc", "showSkillTracker")) writeBooleanConfig("misc", "showSkillTracker", false);
		if (!hasKey("misc", "firstLaunch")) writeBooleanConfig("misc", "firstLaunch", true);

		ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
		int height = scaled.getScaledHeight();
		if (!hasKey("locations", "coordsX")) writeIntConfig("locations", "coordsX", 5);
		if (!hasKey("locations", "coordsY")) writeIntConfig("locations", "coordsY", height - 25);
		if (!hasKey("locations", "displayX")) writeIntConfig("locations", "displayX", 80);
		if (!hasKey("locations", "displayY")) writeIntConfig("locations", "displayY", 5);
		if (!hasKey("locations", "dungeonTimerX")) writeIntConfig("locations", "dungeonTimerX", 5);
		if (!hasKey("locations", "dungeonTimerY")) writeIntConfig("locations", "dungeonTimerY", 5);
		if (!hasKey("locations", "skill50X")) writeIntConfig("locations", "skill50X", 40);
		if (!hasKey("locations", "skill50Y")) writeIntConfig("locations", "skill50Y", 10);
		if (!hasKey("locations", "lividHpX")) writeIntConfig("locations", "lividHpX", 40);
		if (!hasKey("locations", "lividHpY")) writeIntConfig("locations", "lividHpY", 20);
		if (!hasKey("locations", "cakeTimerX")) writeIntConfig("locations", "cakeTimerX", 40);
		if (!hasKey("locations", "cakeTimerY")) writeIntConfig("locations", "cakeTimerY", 30);
		if (!hasKey("locations", "skillTrackerX")) writeIntConfig("locations", "skillTrackerX", 40);
		if (!hasKey("locations", "skillTrackerY")) writeIntConfig("locations", "skillTrackerY", 50);
		if (!hasKey("locations", "waterAnswerX")) writeIntConfig("locations", "waterAnswerX", 100);
		if (!hasKey("locations", "waterAnswerY")) writeIntConfig("locations", "waterAnswerY", 100);
		if (!hasKey("locations", "bonzoTimerX")) writeIntConfig("locations", "bonzoTimerX", 40);
		if (!hasKey("locations", "bonzoTimerY")) writeIntConfig("locations", "bonzoTimerY", 80);
		if (!hasKey("scales", "coordsScale")) writeDoubleConfig("scales", "coordsScale", 1);
		if (!hasKey("scales", "displayScale")) writeDoubleConfig("scales", "displayScale", 1);
		if (!hasKey("scales", "dungeonTimerScale")) writeDoubleConfig("scales", "dungeonTimerScale", 1);
		if (!hasKey("scales", "skill50Scale")) writeDoubleConfig("scales", "skill50Scale", 1);
		if (!hasKey("scales", "lividHpScale")) writeDoubleConfig("scales", "lividHpScale", 1);
		if (!hasKey("scales", "cakeTimerScale")) writeDoubleConfig("scales", "cakeTimerScale", 1);
		if (!hasKey("scales", "skillTrackerScale")) writeDoubleConfig("scales", "skillTrackerScale", 1);
		if (!hasKey("scales", "waterAnswerScale")) writeDoubleConfig("scales", "waterAnswerScale", 1);
		if (!hasKey("scales", "bonzoTimerScale")) writeDoubleConfig("scales", "bonzoTimerScale", 1);

		if (!hasKey("colors", "main")) writeStringConfig("colors", "main", EnumChatFormatting.GREEN.toString());
		if (!hasKey("colors", "secondary")) writeStringConfig("colors", "secondary", EnumChatFormatting.DARK_GREEN.toString());
		if (!hasKey("colors", "delimiter")) writeStringConfig("colors", "delimiter", EnumChatFormatting.AQUA.toString() + EnumChatFormatting.STRIKETHROUGH.toString());
		if (!hasKey("colors", "error")) writeStringConfig("colors", "error", EnumChatFormatting.RED.toString());
		if (!hasKey("colors", "type")) writeStringConfig("colors", "type", EnumChatFormatting.GREEN.toString());
		if (!hasKey("colors", "value")) writeStringConfig("colors", "value", EnumChatFormatting.DARK_GREEN.toString());
		if (!hasKey("colors", "skillAverage")) writeStringConfig("colors", "skillAverage", EnumChatFormatting.GOLD.toString());
		if (!hasKey("colors", "answer")) writeStringConfig("colors", "answer", EnumChatFormatting.DARK_GREEN.toString());
		if (!hasKey("colors", "skill50Display")) writeStringConfig("colors", "skill50Display", EnumChatFormatting.AQUA.toString());
		if (!hasKey("colors", "coordsDisplay")) writeStringConfig("colors", "coordsDisplay", EnumChatFormatting.WHITE.toString());
		if (!hasKey("colors", "cakeDisplay")) writeStringConfig("colors", "cakeDisplay", EnumChatFormatting.GOLD.toString());
		if (!hasKey("colors", "skillTracker")) writeStringConfig("colors", "skillTracker", EnumChatFormatting.AQUA.toString());
		if (!hasKey("colors", "triviaWrongAnswer")) writeStringConfig("colors", "triviaWrongAnswer", EnumChatFormatting.RED.toString());
		if (!hasKey("colors", "bonzoDisplay")) writeStringConfig("colors", "bonzoDisplay", EnumChatFormatting.RED.toString());
		if (!hasKey("colors", "blazeLowest")) writeIntConfig("colors", "blazeLowest", 0xFF0000);
		if (!hasKey("colors", "blazeHighest")) writeIntConfig("colors", "blazeHighest", 0x40FF40);
		if (!hasKey("colors", "pet1To9")) writeIntConfig("colors", "pet1To9", 0x999999); // Gray
		if (!hasKey("colors", "pet10To19")) writeIntConfig("colors", "pet10To19", 0xD62440); // Red
		if (!hasKey("colors", "pet20To29")) writeIntConfig("colors", "pet20To29", 0xEF5230); // Orange
		if (!hasKey("colors", "pet30To39")) writeIntConfig("colors", "pet30To39", 0xFFC400); // Yellow
		if (!hasKey("colors", "pet40To49")) writeIntConfig("colors", "pet40To49", 0x0EAC35); // Green
		if (!hasKey("colors", "pet50To59")) writeIntConfig("colors", "pet50To59", 0x008AD8); // Light Blue
		if (!hasKey("colors", "pet60To69")) writeIntConfig("colors", "pet60To69", 0x7E4FC6); // Purple
		if (!hasKey("colors", "pet70To79")) writeIntConfig("colors", "pet70To79", 0xD64FC8); // Pink
		if (!hasKey("colors", "pet80To89")) writeIntConfig("colors", "pet80To89", 0x5C1F35); // idk weird magenta
		if (!hasKey("colors", "pet90To99")) writeIntConfig("colors", "pet90To99", 0x9E794E); // Brown
		if (!hasKey("colors", "pet100")) writeIntConfig("colors", "pet100", 0xF2D249); // Gold
		if (!hasKey("colors", "ultrasequencerNext")) writeIntConfig("colors", "ultrasequencerNext", 0x40FF40);
		if (!hasKey("colors", "ultrasequencerNextToNext")) writeIntConfig("colors", "ultrasequencerNextToNext", 0x40DAE6);
		if (!hasKey("colors", "chronomatronNext")) writeIntConfig("colors", "chronomatronNext", 0x40FF40);
		if (!hasKey("colors", "chronomatronNextToNext")) writeIntConfig("colors", "chronomatronNextToNext", 0x40DAE6);
		if (!hasKey("colors", "clickInOrderNext")) writeIntConfig("colors", "clickInOrderNext", 0xFF00DD);
		if (!hasKey("colors", "clickInOrderNextToNext")) writeIntConfig("colors", "clickInOrderNextToNext", 0x0BEFE7);

		// Commands
		if (!hasKey("commands", "reparty")) writeBooleanConfig("commands", "reparty", false);
		
		ToggleCommand.gpartyToggled = getBoolean("toggles", "GParty");
		ToggleCommand.coordsToggled = getBoolean("toggles", "Coords");
		ToggleCommand.goldenToggled = getBoolean("toggles", "Golden");
		ToggleCommand.slayerCountTotal = getBoolean("toggles", "SlayerCount");
		ToggleCommand.rngesusAlerts = getBoolean("toggles", "RNGesusAlerts");
		ToggleCommand.splitFishing = getBoolean("toggles", "SplitFishing");
		ToggleCommand.chatMaddoxToggled = getBoolean("toggles", "ChatMaddox");
		ToggleCommand.spiritBearAlerts = getBoolean("toggles", "SpiritBearAlerts");
		ToggleCommand.aotdToggled = getBoolean("toggles", "AOTD");
		ToggleCommand.lividDaggerToggled = getBoolean("toggles", "LividDagger");
		ToggleCommand.petColoursToggled = getBoolean("toggles", "PetColors");
		ToggleCommand.golemAlertToggled = getBoolean("toggles", "GolemAlerts");
		ToggleCommand.expertiseLoreToggled = getBoolean("toggles", "ExpertiseLore");
		ToggleCommand.skill50DisplayToggled = getBoolean("toggles", "Skill50Display");
		ToggleCommand.outlineTextToggled = getBoolean("toggles", "OutlineText");
		ToggleCommand.cakeTimerToggled = getBoolean("toggles", "CakeTimer");
		// Chat Messages
		ToggleCommand.sceptreMessages = getBoolean("toggles", "SceptreMessages");
		ToggleCommand.midasStaffMessages = getBoolean("toggles", "MidasStaffMessages");
		ToggleCommand.implosionMessages = getBoolean("toggles", "ImplosionMessages");
		ToggleCommand.healMessages = getBoolean("toggles", "HealMessages");
		ToggleCommand.cooldownMessages = getBoolean("toggles", "CooldownMessages");
		ToggleCommand.manaMessages = getBoolean("toggles", "ManaMessages");
		//Dungeons
		ToggleCommand.dungeonTimerToggled = getBoolean("toggles", "DungeonTimer");
		ToggleCommand.lowHealthNotifyToggled = getBoolean("toggles", "LowHealthNotify");
		ToggleCommand.lividSolverToggled = getBoolean("toggles", "LividSolver");
		ToggleCommand.stopSalvageStarredToggled = getBoolean("toggles", "StopSalvageStarred");
		ToggleCommand.watcherReadyToggled = getBoolean("toggles", "WatcherReadyMessage");
		ToggleCommand.notifySlayerSlainToggled = getBoolean("toggles", "NotifySlayerSlain");
		ToggleCommand.necronNotificationsToggled = getBoolean("toggles", "NecronNotifications");
		ToggleCommand.bonzoTimerToggled = getBoolean("toggles", "BonzoTimer");
		ToggleCommand.swapToPickBlockToggled = getBoolean("toggles", "PickBlock");
		ToggleCommand.flowerWeaponsToggled = getBoolean("toggles", "FlowerWeapons");
		ToggleCommand.autoSkillTrackerToggled =  getBoolean("toggles", "AutoSkillTracker");
		// Puzzle Solvers
		ToggleCommand.threeManToggled = getBoolean("toggles", "ThreeManPuzzle");
		ToggleCommand.oruoToggled = getBoolean("toggles", "OruoPuzzle");
		ToggleCommand.blazeToggled = getBoolean("toggles", "BlazePuzzle");
		ToggleCommand.creeperToggled = getBoolean("toggles", "CreeperPuzzle");
		ToggleCommand.waterToggled = getBoolean("toggles", "WaterPuzzle");
		ToggleCommand.ticTacToeToggled = getBoolean("toggles", "TicTacToePuzzle");
		ToggleCommand.startsWithToggled = getBoolean("toggles", "StartsWithTerminal");
		ToggleCommand.selectAllToggled = getBoolean("toggles", "SelectAllTerminal");
		ToggleCommand.clickInOrderToggled = getBoolean("toggles", "ClickInOrderTerminal");
		ToggleCommand.blockWrongTerminalClicksToggled = getBoolean("toggles", "BlockWrongTerminalClicks");
		ToggleCommand.itemFrameOnSeaLanternsToggled = getBoolean("toggles", "IgnoreItemFrameOnSeaLanterns");
		// Experiment Solvers
		ToggleCommand.ultrasequencerToggled = getBoolean("toggles", "UltraSequencer");
		ToggleCommand.chronomatronToggled = getBoolean("toggles", "Chronomatron");
		ToggleCommand.superpairsToggled = getBoolean("toggles", "Superpairs");
		ToggleCommand.hideTooltipsInExperimentAddonsToggled = getBoolean("toggles", "HideTooltipsInExperimentAddons");

		String onlySlayer = getString("toggles", "BlockSlayer");
		if (!onlySlayer.equals("")) {
			BlockWrongSlayer.onlySlayerName = onlySlayer.substring(0, onlySlayer.lastIndexOf(" "));
			BlockWrongSlayer.onlySlayerNumber = onlySlayer.substring(onlySlayer.lastIndexOf(" ") + 1);
		}
		
		// Wolf
		LootTracker.wolfSvens = getInt("wolf", "svens");
		LootTracker.wolfTeeth = getInt("wolf", "teeth");
		LootTracker.wolfWheels = getInt("wolf", "wheel");
		LootTracker.wolfWheelsDrops = getInt("wolf", "wheelDrops");
		LootTracker.wolfSpirits = getInt("wolf", "spirit");
		LootTracker.wolfBooks = getInt("wolf", "book");
		LootTracker.wolfEggs = getInt("wolf", "egg");
		LootTracker.wolfCoutures = getInt("wolf", "couture");
		LootTracker.wolfBaits = getInt("wolf", "bait");
		LootTracker.wolfFluxes = getInt("wolf", "flux");
		LootTracker.wolfTime = getDouble("wolf", "timeRNG");
		LootTracker.wolfBosses = getInt("wolf", "bossRNG");
		// Spider
		LootTracker.spiderTarantulas = getInt("spider", "tarantulas");
		LootTracker.spiderWebs = getInt("spider", "web");
		LootTracker.spiderTAP = getInt("spider", "tap");
		LootTracker.spiderTAPDrops = getInt("spider", "tapDrops");
		LootTracker.spiderBites = getInt("spider", "bite");
		LootTracker.spiderCatalysts = getInt("spider", "catalyst");
		LootTracker.spiderBooks = getInt("spider", "book");
		LootTracker.spiderSwatters = getInt("spider", "swatter");
		LootTracker.spiderTalismans = getInt("spider", "talisman");
		LootTracker.spiderMosquitos = getInt("spider", "mosquito");
		LootTracker.spiderTime = getDouble("spider", "timeRNG");
		LootTracker.spiderBosses = getInt("spider", "bossRNG");
		// Zombie
		LootTracker.zombieRevs = getInt("zombie", "revs");
		LootTracker.zombieRevFlesh = getInt("zombie", "revFlesh");
		LootTracker.zombieFoulFlesh = getInt("zombie", "foulFlesh");
		LootTracker.zombieFoulFleshDrops = getInt("zombie", "foulFleshDrops");
		LootTracker.zombiePestilences = getInt("zombie", "pestilence");
		LootTracker.zombieUndeadCatas = getInt("zombie", "undeadCatalyst");
		LootTracker.zombieBooks = getInt("zombie", "book");
		LootTracker.zombieBeheadeds = getInt("zombie", "beheaded");
		LootTracker.zombieRevCatas = getInt("zombie", "revCatalyst");
		LootTracker.zombieSnakes = getInt("zombie", "snake");
		LootTracker.zombieScythes = getInt("zombie", "scythe");
		LootTracker.zombieTime = getDouble("zombie", "timeRNG");
		LootTracker.zombieBosses = getInt("zombie", "bossRNG");
		
		// Fishing
		LootTracker.seaCreatures = getInt("fishing", "seaCreature");
		LootTracker.goodCatches = getInt("fishing", "goodCatch");
		LootTracker.greatCatches = getInt("fishing", "greatCatch");
		LootTracker.squids = getInt("fishing", "squid");
		LootTracker.seaWalkers = getInt("fishing", "seaWalker");
		LootTracker.nightSquids = getInt("fishing", "nightSquid");
		LootTracker.seaGuardians = getInt("fishing", "seaGuardian");
		LootTracker.seaWitches = getInt("fishing", "seaWitch");
		LootTracker.seaArchers = getInt("fishing", "seaArcher");
		LootTracker.monsterOfTheDeeps = getInt("fishing", "monsterOfDeep");
		LootTracker.catfishes = getInt("fishing", "catfish");
		LootTracker.carrotKings = getInt("fishing", "carrotKing");
		LootTracker.seaLeeches = getInt("fishing", "seaLeech");
		LootTracker.guardianDefenders = getInt("fishing", "guardianDefender");
		LootTracker.deepSeaProtectors = getInt("fishing", "deepSeaProtector");
		LootTracker.hydras = getInt("fishing", "hydra");
		LootTracker.seaEmperors = getInt("fishing", "seaEmperor");
		LootTracker.empTime = getDouble("fishing", "empTime");
		LootTracker.empSCs = getInt("fishing", "empSC");
		LootTracker.fishingMilestone = getInt("fishing", "milestone");
		// Fishing Winter
		LootTracker.frozenSteves = getInt("fishing", "frozenSteve");
		LootTracker.frostyTheSnowmans = getInt("fishing", "snowman");
		LootTracker.grinches = getInt("fishing", "grinch");
		LootTracker.yetis = getInt("fishing", "yeti");
		LootTracker.yetiTime = getDouble("fishing", "yetiTime");
		LootTracker.yetiSCs = getInt("fishing", "yetiSC");
		// Fishing Festival
		LootTracker.nurseSharks = getInt("fishing", "nurseShark");
		LootTracker.blueSharks = getInt("fishing", "blueShark");
		LootTracker.tigerSharks = getInt("fishing", "tigerShark");
		LootTracker.greatWhiteSharks = getInt("fishing", "greatWhiteShark");
		// Spooky Fishing
		LootTracker.scarecrows = getInt("fishing", "scarecrow");
		LootTracker.nightmares = getInt("fishing", "nightmare");
		LootTracker.werewolfs = getInt("fishing", "werewolf");
		LootTracker.phantomFishers = getInt("fishing", "phantomFisher");
		LootTracker.grimReapers = getInt("fishing", "grimReaper");
		
		// Mythological
		LootTracker.mythCoins = getDouble("mythological", "coins");
		LootTracker.griffinFeathers = getInt("mythological", "griffinFeather");
		LootTracker.crownOfGreeds = getInt("mythological", "crownOfGreed");
		LootTracker.washedUpSouvenirs = getInt("mythological", "washedUpSouvenir");
		LootTracker.minosHunters = getInt("mythological", "minosHunter");
		LootTracker.siameseLynxes = getInt("mythological", "siameseLynx");
		LootTracker.minotaurs = getInt("mythological", "minotaur");
		LootTracker.gaiaConstructs = getInt("mythological", "gaiaConstruct");
		LootTracker.minosChampions = getInt("mythological", "minosChampion");
		LootTracker.minosInquisitors = getInt("mythological", "minosInquisitor");
		
		// Dungeons
		LootTracker.recombobulators =  getInt("catacombs", "recombobulator");
		LootTracker.fumingPotatoBooks = getInt("catacombs", "fumingBooks");
		// F1
		LootTracker.bonzoStaffs = getInt("catacombs", "bonzoStaff");
		LootTracker.f1CoinsSpent = getDouble("catacombs", "floorOneCoins");
		LootTracker.f1TimeSpent = getDouble("catacombs", "floorOneTime");
		// F2
		LootTracker.scarfStudies = getInt("catacombs", "scarfStudies");
		LootTracker.f2CoinsSpent = getDouble("catacombs", "floorTwoCoins");
		LootTracker.f2TimeSpent = getDouble("catacombs", "floorTwoTime");
		// F3
		LootTracker.adaptiveHelms = getInt("catacombs", "adaptiveHelm");
		LootTracker.adaptiveChests = getInt("catacombs", "adaptiveChest");
		LootTracker.adaptiveLegs = getInt("catacombs", "adaptiveLegging");
		LootTracker.adaptiveBoots = getInt("catacombs", "adaptiveBoot");
		LootTracker.adaptiveSwords = getInt("catacombs", "adaptiveSword");
		LootTracker.f3CoinsSpent = getDouble("catacombs", "floorThreeCoins");
		LootTracker.f3TimeSpent = getDouble("catacombs", "floorThreeTime");
		// F4
		LootTracker.spiritWings = getInt("catacombs", "spiritWing");
		LootTracker.spiritBones = getInt("catacombs", "spiritBone");
		LootTracker.spiritBoots = getInt("catacombs", "spiritBoot");
		LootTracker.spiritSwords = getInt("catacombs", "spiritSword");
		LootTracker.spiritBows = getInt("catacombs", "spiritBow");
		LootTracker.epicSpiritPets = getInt("catacombs", "spiritPetEpic");
		LootTracker.legSpiritPets = getInt("catacombs", "spiritPetLeg");
		LootTracker.f4CoinsSpent = getDouble("catacombs", "floorFourCoins");
		LootTracker.f4TimeSpent = getDouble("catacombs", "floorFourTime");
		// F5
		LootTracker.warpedStones = getInt("catacombs", "warpedStone");
		LootTracker.shadowAssHelms = getInt("catacombs", "shadowAssassinHelm");
		LootTracker.shadowAssChests = getInt("catacombs", "shadowAssassinChest");
		LootTracker.shadowAssLegs = getInt("catacombs", "shadowAssassinLegging");
		LootTracker.shadowAssBoots = getInt("catacombs", "shadowAssassinBoot");
		LootTracker.lastBreaths = getInt("catacombs", "lastBreath");
		LootTracker.lividDaggers = getInt("catacombs", "lividDagger");
		LootTracker.shadowFurys = getInt("catacombs", "shadowFury");
		LootTracker.f5CoinsSpent = getDouble("catacombs", "floorFiveCoins");
		LootTracker.f5TimeSpent = getDouble("catacombs", "floorFiveTime");
		// F6
		LootTracker.ancientRoses = getInt("catacombs", "ancientRose");
		LootTracker.precursorEyes = getInt("catacombs", "precursorEye");
		LootTracker.giantsSwords = getInt("catacombs", "giantsSword");
		LootTracker.necroLordHelms = getInt("catacombs", "necroLordHelm");
		LootTracker.necroLordChests = getInt("catacombs", "necroLordChest");
		LootTracker.necroLordLegs = getInt("catacombs", "necroLordLegging");
		LootTracker.necroLordBoots = getInt("catacombs", "necroLordBoot");
		LootTracker.necroSwords = getInt("catacombs", "necroSword");
		LootTracker.f6CoinsSpent = getDouble("catacombs", "floorSixCoins");
		LootTracker.f6TimeSpent = getDouble("catacombs", "floorSixTime");
		// F7
		LootTracker.witherBloods = getInt("catacombs", "witherBlood");
		LootTracker.witherCloaks = getInt("catacombs", "witherCloak");
		LootTracker.implosions = getInt("catacombs", "implosion");
		LootTracker.witherShields = getInt("catacombs", "witherShield");
		LootTracker.shadowWarps = getInt("catacombs", "shadowWarp");
		LootTracker.necronsHandles = getInt("catacombs", "necronsHandle");
		LootTracker.autoRecombs = getInt("catacombs", "autoRecomb");
		LootTracker.witherHelms = getInt("catacombs", "witherHelm");
		LootTracker.witherChests = getInt("catacombs", "witherChest");
		LootTracker.witherLegs = getInt("catacombs", "witherLegging");
		LootTracker.witherBoots = getInt("catacombs", "witherBoot");
		LootTracker.f7CoinsSpent = getDouble("catacombs", "floorSevenCoins");
		LootTracker.f7TimeSpent = getDouble("catacombs", "floorSevenTime");
		
		// Misc
		DisplayCommand.display = getString("misc", "display");
		DisplayCommand.auto = getBoolean("misc", "autoDisplay");
		Skill50Display.SKILL_TIME = getInt("misc", "skill50Time") * 20;
		CakeTimer.cakeTime = getDouble("misc", "cakeTime");
		SkillTracker.showSkillTracker = getBoolean("misc", "showSkillTracker");
		DankersSkyblockMod.firstLaunch = getBoolean("misc", "firstLaunch");

		MoveCommand.coordsXY[0] = getInt("locations", "coordsX");
		MoveCommand.coordsXY[1] = getInt("locations", "coordsY");
		MoveCommand.displayXY[0] = getInt("locations", "displayX");
		MoveCommand.displayXY[1] = getInt("locations", "displayY");
		MoveCommand.dungeonTimerXY[0] = getInt("locations", "dungeonTimerX");
		MoveCommand.dungeonTimerXY[1] = getInt("locations", "dungeonTimerY");
		MoveCommand.skill50XY[0] = getInt("locations", "skill50X");
		MoveCommand.skill50XY[1] = getInt("locations", "skill50Y");
		MoveCommand.lividHpXY[0] = getInt("locations", "lividHpX");
		MoveCommand.lividHpXY[1] = getInt("locations", "lividHpY");
		MoveCommand.cakeTimerXY[0] = getInt("locations", "cakeTimerX");
		MoveCommand.cakeTimerXY[1] = getInt("locations", "cakeTimerY");
		MoveCommand.skillTrackerXY[0] = getInt("locations", "skillTrackerX");
		MoveCommand.skillTrackerXY[1] = getInt("locations", "skillTrackerY");
		MoveCommand.waterAnswerXY[0] = getInt("locations", "waterAnswerX");
		MoveCommand.waterAnswerXY[1] = getInt("locations", "waterAnswerY");
		MoveCommand.bonzoTimerXY[0] = getInt("locations", "bonzoTimerX");
		MoveCommand.bonzoTimerXY[1] = getInt("locations", "bonzoTimerY");


		ScaleCommand.coordsScale = getDouble("scales", "coordsScale");
		ScaleCommand.displayScale = getDouble("scales", "displayScale");
		ScaleCommand.dungeonTimerScale = getDouble("scales", "dungeonTimerScale");
		ScaleCommand.skill50Scale = getDouble("scales", "skill50Scale");
		ScaleCommand.lividHpScale = getDouble("scales", "lividHpScale");
		ScaleCommand.cakeTimerScale = getDouble("scales", "cakeTimerScale");
		ScaleCommand.skillTrackerScale = getDouble("scales", "skillTrackerScale");
		ScaleCommand.waterAnswerScale = getDouble("scales", "waterAnswerScale");
		ScaleCommand.bonzoTimerScale = getDouble("scales", "bonzoTimerScale");

		DankersSkyblockMod.MAIN_COLOUR = getString("colors", "main");
		DankersSkyblockMod.SECONDARY_COLOUR = getString("colors", "secondary");
		DankersSkyblockMod.DELIMITER_COLOUR = getString("colors", "delimiter");
		DankersSkyblockMod.ERROR_COLOUR = getString("colors", "error");
		DankersSkyblockMod.TYPE_COLOUR = getString("colors", "type");
		DankersSkyblockMod.VALUE_COLOUR = getString("colors", "value");
		DankersSkyblockMod.SKILL_AVERAGE_COLOUR = getString("colors", "skillAverage");
		DankersSkyblockMod.ANSWER_COLOUR = getString("colors", "answer");
		Skill50Display.SKILL_50_COLOUR = getString("colors", "skill50Display");
		NoF3Coords.COORDS_COLOUR = getString("colors", "coordsDisplay");
		CakeTimer.CAKE_COLOUR = getString("colors", "cakeDisplay");
		SkillTracker.SKILL_TRACKER_COLOUR = getString("colors", "skillTracker");
		TriviaSolver.TRIVIA_WRONG_ANSWER_COLOUR = getString("colors", "triviaWrongAnswer");
		BonzoMaskTimer.BONZO_COLOR = getString("colors", "bonzoDisplay");
		BlazeSolver.LOWEST_BLAZE_COLOUR = getInt("colors", "blazeLowest");
		BlazeSolver.HIGHEST_BLAZE_COLOUR = getInt("colors", "blazeHighest");
		PetColours.PET_1_TO_9 = getInt("colors", "pet1To9");
		PetColours.PET_10_TO_19 = getInt("colors", "pet10To19");
		PetColours.PET_20_TO_29 = getInt("colors", "pet20To29");
		PetColours.PET_30_TO_39 = getInt("colors", "pet30To39");
		PetColours.PET_40_TO_49 = getInt("colors", "pet40To49");
		PetColours.PET_50_TO_59 = getInt("colors", "pet50To59");
		PetColours.PET_60_TO_69 = getInt("colors", "pet60To69");
		PetColours.PET_70_TO_79 = getInt("colors", "pet70To79");
		PetColours.PET_80_TO_89 = getInt("colors", "pet80To89");
		PetColours.PET_90_TO_99 = getInt("colors", "pet90To99");
		PetColours.PET_100 = getInt("colors", "pet100");
		UltrasequencerSolver.ULTRASEQUENCER_NEXT = getInt("colors", "ultrasequencerNext");
		UltrasequencerSolver.ULTRASEQUENCER_NEXT_TO_NEXT = getInt("colors", "ultrasequencerNextToNext");
		ChronomatronSolver.CHRONOMATRON_NEXT = getInt("colors", "chronomatronNext");
		ChronomatronSolver.CHRONOMATRON_NEXT_TO_NEXT = getInt("colors", "chronomatronNextToNext");
		ClickInOrderSolver.CLICK_IN_ORDER_NEXT = getInt("colors", "clickInOrderNext");
		ClickInOrderSolver.CLICK_IN_ORDER_NEXT_TO_NEXT = getInt("colors", "clickInOrderNextToNext");
	}
	
}
