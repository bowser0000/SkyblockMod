package me.Danker.commands.loot;

import com.google.gson.JsonObject;
import me.Danker.config.CfgConfig;
import me.Danker.config.ModConfig;
import me.Danker.features.loot.FishingTracker;
import me.Danker.features.loot.TrophyFishTracker;
import me.Danker.handlers.HypixelAPIHandler;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ImportFishingCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "importfishing";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName();
	}

	public static String usage(ICommandSender arg0) {
		return new ImportFishingCommand().getCommandUsage(arg0);
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		// MULTI THREAD DRIFTING
		new Thread(() -> {
			EntityPlayer player = (EntityPlayer) arg0;

			player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Importing your fishing stats using Polyfrost's API..."));

			// Get UUID for Hypixel API requests
			String uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");

			JsonObject profileResponse = HypixelAPIHandler.getLatestProfile(uuid);
			if (profileResponse == null) return;

			System.out.println("Fetching fishing stats...");
			JsonObject memberObject = Utils.getObjectFromPath(profileResponse, "members." + uuid);
			JsonObject itemsFishedObject = Utils.getObjectFromPath(memberObject, "player_stats.items_fished");
			JsonObject killsObject = Utils.getObjectFromPath(memberObject, "player_stats.kills");
			JsonObject trophyObject = memberObject.getAsJsonObject("trophy_fish");

			FishingTracker.greatCatches = 0;
			FishingTracker.goodCatches = 0;
			if (itemsFishedObject.has("treasure")) {
				if (itemsFishedObject.has("large_treasure")) {
					FishingTracker.greatCatches = itemsFishedObject.get("large_treasure").getAsInt();
					FishingTracker.goodCatches = itemsFishedObject.get("treasure").getAsInt() - FishingTracker.greatCatches;
				} else {
					FishingTracker.goodCatches = itemsFishedObject.get("treasure").getAsInt();
				}
			}

			FishingTracker.seaCreatures = 0;
			FishingTracker.fishingMilestone = Utils.getObjectFromPath(memberObject, "player_stats.pets.milestone").get("sea_creatures_killed").getAsInt();;

			FishingTracker.squids = getSCFromApi(killsObject, "pond_squid");
			FishingTracker.seaWalkers = getSCFromApi(killsObject, "sea_walker");
			FishingTracker.nightSquids = getSCFromApi(killsObject, "night_squid");
			FishingTracker.seaGuardians = getSCFromApi(killsObject, "sea_guardian");
			FishingTracker.seaWitches = getSCFromApi(killsObject, "sea_witch");
			FishingTracker.seaArchers = getSCFromApi(killsObject, "sea_archer");
			FishingTracker.monsterOfTheDeeps = getSCFromApi(killsObject, "zombie_deep") + getSCFromApi(killsObject, "chicken_deep");
			FishingTracker.agarimoos = getSCFromApi(killsObject, "agarimoo");
			FishingTracker.catfishes = getSCFromApi(killsObject, "catfish");
			FishingTracker.carrotKings = getSCFromApi(killsObject, "carrot_king");
			FishingTracker.seaLeeches = getSCFromApi(killsObject, "sea_leech");
			FishingTracker.guardianDefenders = getSCFromApi(killsObject, "guardian_defender");
			FishingTracker.deepSeaProtectors = getSCFromApi(killsObject, "deep_sea_protector");
			FishingTracker.hydras = getSCFromApi(killsObject, "water_hydra") / 2;
			FishingTracker.seaEmperors = getSCFromApi(killsObject, "skeleton_emperor") + getSCFromApi(killsObject, "guardian_emperor");
			FishingTracker.frozenSteves = getSCFromApi(killsObject, "frozen_steve");
			FishingTracker.frostyTheSnowmans = getSCFromApi(killsObject, "frosty_the_snowman");
			FishingTracker.grinches = getSCFromApi(killsObject, "grinch");
			FishingTracker.nutcrackers = getSCFromApi(killsObject, "nutcracker");
			FishingTracker.yetis = getSCFromApi(killsObject, "yeti");
			FishingTracker.reindrakes = getSCFromApi(killsObject, "reindrake_100");
			FishingTracker.nurseSharks = getSCFromApi(killsObject, "nurse_shark");
			FishingTracker.blueSharks = getSCFromApi(killsObject, "blue_shark");
			FishingTracker.tigerSharks = getSCFromApi(killsObject, "tiger_shark");
			FishingTracker.greatWhiteSharks = getSCFromApi(killsObject, "great_white_shark");
			FishingTracker.scarecrows = getSCFromApi(killsObject, "scarecrow");
			FishingTracker.nightmares = getSCFromApi(killsObject, "nightmare");
			FishingTracker.werewolfs = getSCFromApi(killsObject, "werewolf");
			FishingTracker.phantomFishers = getSCFromApi(killsObject, "phantom_fisherman");
			FishingTracker.grimReapers = getSCFromApi(killsObject, "grim_reaper");
			FishingTracker.waterWorms = getSCFromApi(killsObject, "water_worm");
			FishingTracker.poisonedWaterWorms = getSCFromApi(killsObject, "poisoned_water_worm");
			FishingTracker.flamingWorms = getSCFromApi(killsObject, "flaming_worm");
			FishingTracker.lavaBlazes = getSCFromApi(killsObject, "lava_blaze");
			FishingTracker.lavaPigmen = getSCFromApi(killsObject, "lava_pigman");
			FishingTracker.zombieMiners = getSCFromApi(killsObject, "zombie_miner");
			FishingTracker.plhlegblasts = getSCFromApi(killsObject, "pond_squid_300");
			FishingTracker.magmaSlugs = getSCFromApi(killsObject, "magma_slug");
			FishingTracker.moogmas = getSCFromApi(killsObject, "moogma");
			FishingTracker.lavaLeeches = getSCFromApi(killsObject, "lava_leech");
			FishingTracker.pyroclasticWorms = getSCFromApi(killsObject, "pyroclastic_worm");
			FishingTracker.lavaFlames = getSCFromApi(killsObject, "lava_flame");
			FishingTracker.fireEels = getSCFromApi(killsObject, "fire_eel");
			FishingTracker.tauruses = getSCFromApi(killsObject, "pig_rider");
			FishingTracker.thunders = getSCFromApi(killsObject, "thunder");
			FishingTracker.lordJawbuses = getSCFromApi(killsObject, "lord_jawbus");

			System.out.println("Writing SC to config...");
			CfgConfig.writeIntConfig("fishing", "goodCatch", FishingTracker.goodCatches);
			CfgConfig.writeIntConfig("fishing", "greatCatch", FishingTracker.greatCatches);
			CfgConfig.writeIntConfig("fishing", "seaCreature", FishingTracker.seaCreatures);
			CfgConfig.writeIntConfig("fishing", "squid", FishingTracker.squids);
			CfgConfig.writeIntConfig("fishing", "seaWalker", FishingTracker.seaWalkers);
			CfgConfig.writeIntConfig("fishing", "nightSquid", FishingTracker.nightSquids);
			CfgConfig.writeIntConfig("fishing", "seaGuardian", FishingTracker.seaGuardians);
			CfgConfig.writeIntConfig("fishing", "seaWitch", FishingTracker.seaWitches);
			CfgConfig.writeIntConfig("fishing", "seaArcher", FishingTracker.seaArchers);
			CfgConfig.writeIntConfig("fishing", "monsterOfDeep", FishingTracker.monsterOfTheDeeps);
			CfgConfig.writeIntConfig("fishing", "agarimoo", FishingTracker.agarimoos);
			CfgConfig.writeIntConfig("fishing", "catfish", FishingTracker.catfishes);
			CfgConfig.writeIntConfig("fishing", "carrotKing", FishingTracker.carrotKings);
			CfgConfig.writeIntConfig("fishing", "seaLeech", FishingTracker.seaLeeches);
			CfgConfig.writeIntConfig("fishing", "guardianDefender", FishingTracker.guardianDefenders);
			CfgConfig.writeIntConfig("fishing", "deepSeaProtector", FishingTracker.deepSeaProtectors);
			CfgConfig.writeIntConfig("fishing", "hydra", FishingTracker.hydras);
			CfgConfig.writeIntConfig("fishing", "seaEmperor", FishingTracker.seaEmperors);
			CfgConfig.writeIntConfig("fishing", "milestone", FishingTracker.fishingMilestone);
			CfgConfig.writeIntConfig("fishing", "frozenSteve", FishingTracker.frozenSteves);
			CfgConfig.writeIntConfig("fishing", "snowman", FishingTracker.frostyTheSnowmans);
			CfgConfig.writeIntConfig("fishing", "grinch", FishingTracker.grinches);
			CfgConfig.writeIntConfig("fishing", "yeti", FishingTracker.yetis);
			CfgConfig.writeIntConfig("fishing", "reindrake", FishingTracker.reindrakes);
			CfgConfig.writeIntConfig("fishing", "nurseShark", FishingTracker.nurseSharks);
			CfgConfig.writeIntConfig("fishing", "blueShark", FishingTracker.blueSharks);
			CfgConfig.writeIntConfig("fishing", "tigerShark", FishingTracker.tigerSharks);
			CfgConfig.writeIntConfig("fishing", "greatWhiteShark", FishingTracker.greatWhiteSharks);
			CfgConfig.writeIntConfig("fishing", "scarecrow", FishingTracker.scarecrows);
			CfgConfig.writeIntConfig("fishing", "nightmare", FishingTracker.nightmares);
			CfgConfig.writeIntConfig("fishing", "werewolf", FishingTracker.werewolfs);
			CfgConfig.writeIntConfig("fishing", "phantomFisher", FishingTracker.phantomFishers);
			CfgConfig.writeIntConfig("fishing", "grimReaper", FishingTracker.grimReapers);
			CfgConfig.writeIntConfig("fishing", "waterWorm", FishingTracker.waterWorms);
			CfgConfig.writeIntConfig("fishing", "poisonedWaterWorm", FishingTracker.poisonedWaterWorms);
			CfgConfig.writeIntConfig("fishing", "flamingWorm", FishingTracker.flamingWorms);
			CfgConfig.writeIntConfig("fishing", "lavaBlaze", FishingTracker.lavaBlazes);
			CfgConfig.writeIntConfig("fishing", "lavaPigman", FishingTracker.lavaPigmen);
			CfgConfig.writeIntConfig("fishing", "zombieMiner", FishingTracker.zombieMiners);
			CfgConfig.writeIntConfig("fishing", "plhlegblast", FishingTracker.plhlegblasts);
			CfgConfig.writeIntConfig("fishing", "magmaSlug", FishingTracker.magmaSlugs);
			CfgConfig.writeIntConfig("fishing", "moogma", FishingTracker.moogmas);
			CfgConfig.writeIntConfig("fishing", "lavaLeech", FishingTracker.lavaLeeches);
			CfgConfig.writeIntConfig("fishing", "pyroclasticWorm", FishingTracker.pyroclasticWorms);
			CfgConfig.writeIntConfig("fishing", "lavaFlame", FishingTracker.lavaFlames);
			CfgConfig.writeIntConfig("fishing", "fireEel", FishingTracker.fireEels);
			CfgConfig.writeIntConfig("fishing", "taurus", FishingTracker.tauruses);
			CfgConfig.writeIntConfig("fishing", "thunder", FishingTracker.thunders);
			CfgConfig.writeIntConfig("fishing", "lordJawbus", FishingTracker.lordJawbuses);

			TrophyFishTracker.fish = TrophyFishTracker.createEmpty();
			TrophyFishTracker.fish.add("Sulphur Skitter", Utils.getTrophyFromAPI(trophyObject, "sulphur_skitter"));
			TrophyFishTracker.fish.add("Obfuscated 1", Utils.getTrophyFromAPI(trophyObject, "obfuscated_fish_1"));
			TrophyFishTracker.fish.add("Steaming-Hot Flounder", Utils.getTrophyFromAPI(trophyObject, "steaming_hot_flounder"));
			TrophyFishTracker.fish.add("Obfuscated 2", Utils.getTrophyFromAPI(trophyObject, "obfuscated_fish_2"));
			TrophyFishTracker.fish.add("Gusher", Utils.getTrophyFromAPI(trophyObject, "gusher"));
			TrophyFishTracker.fish.add("Blobfish", Utils.getTrophyFromAPI(trophyObject, "blobfish"));
			TrophyFishTracker.fish.add("Slugfish", Utils.getTrophyFromAPI(trophyObject, "slugfish"));
			TrophyFishTracker.fish.add("Obfuscated 3", Utils.getTrophyFromAPI(trophyObject, "obfuscated_fish_3"));
			TrophyFishTracker.fish.add("Flyfish", Utils.getTrophyFromAPI(trophyObject, "flyfish"));
			TrophyFishTracker.fish.add("Lavahorse", Utils.getTrophyFromAPI(trophyObject, "lava_horse"));
			TrophyFishTracker.fish.add("Mana Ray", Utils.getTrophyFromAPI(trophyObject, "mana_ray"));
			TrophyFishTracker.fish.add("Volcanic Stonefish", Utils.getTrophyFromAPI(trophyObject, "volcanic_stonefish"));
			TrophyFishTracker.fish.add("Vanille", Utils.getTrophyFromAPI(trophyObject, "vanille"));
			TrophyFishTracker.fish.add("Skeleton Fish", Utils.getTrophyFromAPI(trophyObject, "skeleton_fish"));
			TrophyFishTracker.fish.add("Moldfin", Utils.getTrophyFromAPI(trophyObject, "moldfin"));
			TrophyFishTracker.fish.add("Soul Fish", Utils.getTrophyFromAPI(trophyObject, "soul_fish"));
			TrophyFishTracker.fish.add("Karate Fish", Utils.getTrophyFromAPI(trophyObject, "karate_fish"));
			TrophyFishTracker.fish.add("Golden Fish", Utils.getTrophyFromAPI(trophyObject, "golden_fish"));
			TrophyFishTracker.save();

			player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Fishing stats imported."));
		}).start();
	}

	static int getSCFromApi(JsonObject obj, String key) {
		int sc = 0;
		if (obj.has(key)) sc = obj.get(key).getAsInt();
		FishingTracker.seaCreatures += sc;
		return sc;
	}

}
