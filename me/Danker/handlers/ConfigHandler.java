package me.Danker.handlers;

import java.io.File;

import me.Danker.commands.DisplayCommand;
import me.Danker.commands.LootCommand;
import me.Danker.commands.ToggleCommand;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	public static Configuration config;
	private static String file = "config/Danker's Skyblock Mod.cfg";
	
	public static void init() {
		config = new Configuration(new File(file));
		try {
			config.load();
		} catch (Exception ex) {
			System.err.print(ex);
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
			System.err.print(ex);
		} finally {
			config.save();
		}
		return 0;
	}
	
	public static String getString(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(key)) {
				return config.get(category, key, "").getString();
			}
		} catch (Exception ex) {
			System.err.print(ex);
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
			System.err.print(ex);
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
			System.err.print(ex);
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
			System.err.print(ex);
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
			System.err.print(ex);
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
			System.err.print(ex);
		} finally {
			config.save();
		}
		return false;
	}
	
	public static void reloadConfig() {
		// Config init
		if (!hasKey("toggles", "GParty")) writeBooleanConfig("toggles", "GParty", true);
		if (!hasKey("toggles", "Coords")) writeBooleanConfig("toggles", "Coords", true);
		if (!hasKey("api", "APIKey")) writeStringConfig("api", "APIKey", "");
		
		// Wolf Loot
		if (!hasKey("wolf", "svens")) writeIntConfig("wolf", "svens", 0);
		if (!hasKey("wolf", "teeth")) writeIntConfig("wolf", "teeth", 0);
		if (!hasKey("wolf", "wheel")) writeIntConfig("wolf", "wheel", 0);
		if (!hasKey("wolf", "spirit")) writeIntConfig("wolf", "spirit", 0);
		if (!hasKey("wolf", "book")) writeIntConfig("wolf", "book", 0);
		if (!hasKey("wolf", "egg")) writeIntConfig("wolf", "egg", 0);
		if (!hasKey("wolf", "couture")) writeIntConfig("wolf", "couture", 0);
		if (!hasKey("wolf", "bait")) writeIntConfig("wolf", "bait", 0);
		if (!hasKey("wolf", "flux")) writeIntConfig("wolf", "flux", 0);
		if (!hasKey("wolf", "timeRNG")) writeIntConfig("wolf", "timeRNG", -1);
		if (!hasKey("wolf", "bossRNG")) writeIntConfig("wolf", "bossRNG", -1);
		// Spider Loot
		if (!hasKey("spider", "tarantulas")) writeIntConfig("spider", "tarantulas", 0);
		if (!hasKey("spider", "web")) writeIntConfig("spider", "web", 0);
		if (!hasKey("spider", "tap")) writeIntConfig("spider", "tap", 0);
		if (!hasKey("spider", "bite")) writeIntConfig("spider", "bite", 0);
		if (!hasKey("spider", "catalyst")) writeIntConfig("spider", "catalyst", 0);
		if (!hasKey("spider", "book")) writeIntConfig("spider", "book", 0);
		if (!hasKey("spider", "swatter")) writeIntConfig("spider", "swatter", 0);
		if (!hasKey("spider", "talisman")) writeIntConfig("spider", "talisman", 0);
		if (!hasKey("spider", "mosquito")) writeIntConfig("spider", "mosquito", 0);
		if (!hasKey("spider", "timeRNG")) writeIntConfig("spider", "timeRNG", -1);
		if (!hasKey("spider", "bossRNG")) writeIntConfig("spider", "bossRNG", -1);
		// Zombie Loot
		if (!hasKey("zombie", "revs")) writeIntConfig("zombie", "revs", 0);
		if (!hasKey("zombie", "revFlesh")) writeIntConfig("zombie", "revFlesh", 0);
		if (!hasKey("zombie", "foulFlesh")) writeIntConfig("zombie", "foulFlesh", 0);
		if (!hasKey("zombie", "pestilence")) writeIntConfig("zombie", "pestilence", 0);
		if (!hasKey("zombie", "undeadCatalyst")) writeIntConfig("zombie", "undeadCatalyst", 0);
		if (!hasKey("zombie", "book")) writeIntConfig("zombie", "book", 0);
		if (!hasKey("zombie", "beheaded")) writeIntConfig("zombie", "beheaded", 0);
		if (!hasKey("zombie", "revCatalyst")) writeIntConfig("zombie", "revCatalyst", 0);
		if (!hasKey("zombie", "snake")) writeIntConfig("zombie", "snake", 0);
		if (!hasKey("zombie", "scythe")) writeIntConfig("zombie", "scythe", 0);
		if (!hasKey("zombie", "timeRNG")) writeIntConfig("zombie", "timeRNG", -1);
		if (!hasKey("zombie", "bossRNG")) writeIntConfig("zombie", "bossRNG", -1);
		
		if (!hasKey("misc", "display")) writeStringConfig("misc", "display", "off");
		
		final ToggleCommand tf = new ToggleCommand();
		tf.gpartyToggled = getBoolean("toggles", "GParty");
		tf.coordsToggled = getBoolean("toggles", "Coords");
		
		final LootCommand lc = new LootCommand();
		// Wolf
		lc.wolfSvens = getInt("wolf", "svens");
		lc.wolfTeeth = getInt("wolf", "teeth");
		lc.wolfWheels = getInt("wolf", "wheel");
		lc.wolfSpirits = getInt("wolf", "spirit");
		lc.wolfBooks = getInt("wolf", "book");
		lc.wolfEggs = getInt("wolf", "egg");
		lc.wolfCoutures = getInt("wolf", "couture");
		lc.wolfBaits = getInt("wolf", "bait");
		lc.wolfFluxes = getInt("wolf", "flux");
		lc.wolfTime = getInt("wolf", "timeRNG");
		lc.wolfBosses = getInt("wolf", "bossRNG");
		// Spider
		lc.spiderTarantulas = getInt("spider", "tarantulas");
		lc.spiderWebs = getInt("spider", "web");
		lc.spiderTAP = getInt("spider", "tap");
		lc.spiderBites = getInt("spider", "bite");
		lc.spiderCatalysts = getInt("spider", "catalyst");
		lc.spiderBooks = getInt("spider", "book");
		lc.spiderSwatters = getInt("spider", "swatter");
		lc.spiderTalismans = getInt("spider", "talisman");
		lc.spiderMosquitos = getInt("spider", "mosquito");
		lc.spiderTime = getInt("spider", "timeRNG");
		lc.spiderBosses = getInt("spider", "bossRNG");
		// Zombie
		lc.zombieRevs = getInt("zombie", "revs");
		lc.zombieRevFlesh = getInt("zombie", "revFlesh");
		lc.zombieFoulFlesh = getInt("zombie", "foulFlesh");
		lc.zombiePestilences = getInt("zombie", "pestilence");
		lc.zombieUndeadCatas = getInt("zombie", "undeadCatalyst");
		lc.zombieBooks = getInt("zombie", "book");
		lc.zombieBeheadeds = getInt("zombie", "beheaded");
		lc.zombieRevCatas = getInt("zombie", "revCatalyst");
		lc.zombieSnakes = getInt("zombie", "snake");
		lc.zombieScythes = getInt("zombie", "scythe");
		lc.zombieTime = getInt("zombie", "timeRNG");
		lc.zombieBosses = getInt("zombie", "bossRNG");
		
		final DisplayCommand ds = new DisplayCommand();
		ds.display = getString("misc", "display");
	}
	
}
