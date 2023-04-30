package me.Danker.commands;

import com.google.gson.JsonObject;
import me.Danker.config.CfgConfig;
import me.Danker.config.ModConfig;
import me.Danker.features.loot.FishingTracker;
import me.Danker.features.loot.TrophyFishTracker;
import me.Danker.handlers.APIHandler;
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

			// Check key
			String key = ModConfig.apiKey;
			if (key.equals("")) {
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "API key not set."));
				return;
			}

			player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Importing your fishing stats..."));

			// Get UUID for Hypixel API requests
			String uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");

			String latestProfile = APIHandler.getLatestProfileID(uuid, key);
			if (latestProfile == null) return;

			String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + key;
			System.out.println("Fetching profile...");
			JsonObject profileResponse = APIHandler.getResponse(profileURL, true);
			if (!profileResponse.get("success").getAsBoolean()) {
				String reason = profileResponse.get("cause").getAsString();
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Failed with reason: " + reason));
				return;
			}

			System.out.println("Fetching fishing stats...");
			JsonObject memberObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject();
			JsonObject statsObject = memberObject.get("stats").getAsJsonObject();
			JsonObject trophyObject = memberObject.get("trophy_fish").getAsJsonObject();

			FishingTracker.greatCatches = 0;
			FishingTracker.goodCatches = 0;
			if (statsObject.has("items_fished_treasure")) {
				if (statsObject.has("items_fished_large_treasure")) {
					FishingTracker.greatCatches = statsObject.get("items_fished_large_treasure").getAsInt();
					FishingTracker.goodCatches = statsObject.get("items_fished_treasure").getAsInt() - FishingTracker.greatCatches;
				} else {
					FishingTracker.goodCatches = statsObject.get("items_fished_treasure").getAsInt();
				}
			}

			FishingTracker.seaCreatures = 0;

			FishingTracker.squids = getSCFromApi(statsObject, "kills_pond_squid");
			FishingTracker.seaWalkers = getSCFromApi(statsObject, "kills_sea_walker");
			FishingTracker.nightSquids = getSCFromApi(statsObject, "kills_night_squid");
			FishingTracker.seaGuardians = getSCFromApi(statsObject, "kills_sea_guardian");
			FishingTracker.seaWitches = getSCFromApi(statsObject, "kills_sea_witch");
			FishingTracker.seaArchers = getSCFromApi(statsObject, "kills_sea_archer");
			FishingTracker.monsterOfTheDeeps = getSCFromApi(statsObject, "kills_zombie_deep") + getSCFromApi(statsObject, "kills_chicken_deep");
			FishingTracker.catfishes = getSCFromApi(statsObject, "kills_catfish");
			FishingTracker.carrotKings = getSCFromApi(statsObject, "kills_carrot_king");
			FishingTracker.seaLeeches = getSCFromApi(statsObject, "kills_sea_leech");
			FishingTracker.guardianDefenders = getSCFromApi(statsObject, "kills_guardian_defender");
			FishingTracker.deepSeaProtectors = getSCFromApi(statsObject, "kills_deep_sea_protector");
			FishingTracker.hydras = getSCFromApi(statsObject, "kills_water_hydra") / 2;
			FishingTracker.seaEmperors = getSCFromApi(statsObject, "kills_skeleton_emperor") + getSCFromApi(statsObject, "kills_guardian_emperor");
			FishingTracker.fishingMilestone = getSCFromApi(statsObject, "pet_milestone_sea_creatures_killed");
			FishingTracker.frozenSteves = getSCFromApi(statsObject, "kills_frozen_steve");
			FishingTracker.frostyTheSnowmans = getSCFromApi(statsObject, "kills_frosty_the_snowman");
			FishingTracker.grinches = getSCFromApi(statsObject, "kills_grinch");
			FishingTracker.nutcrackers = getSCFromApi(statsObject, "kills_nutcracker");
			FishingTracker.yetis = getSCFromApi(statsObject, "kills_yeti");
			FishingTracker.reindrakes = getSCFromApi(statsObject, "kills_reindrake");
			FishingTracker.nurseSharks = getSCFromApi(statsObject, "kills_nurse_shark");
			FishingTracker.blueSharks = getSCFromApi(statsObject, "kills_blue_shark");
			FishingTracker.tigerSharks = getSCFromApi(statsObject, "kills_tiger_shark");
			FishingTracker.greatWhiteSharks = getSCFromApi(statsObject, "kills_great_white_shark");
			FishingTracker.scarecrows = getSCFromApi(statsObject, "kills_scarecrow");
			FishingTracker.nightmares = getSCFromApi(statsObject, "kills_nightmare");
			FishingTracker.werewolfs = getSCFromApi(statsObject, "kills_werewolf");
			FishingTracker.phantomFishers = getSCFromApi(statsObject, "kills_phantom_fisherman");
			FishingTracker.grimReapers = getSCFromApi(statsObject, "kills_grim_reaper");
			FishingTracker.waterWorms = getSCFromApi(statsObject, "kills_water_worm");
			FishingTracker.poisonedWaterWorms = getSCFromApi(statsObject, "kills_poisoned_water_worm");
			FishingTracker.flamingWorms = getSCFromApi(statsObject, "kills_flaming_worm");
			FishingTracker.lavaBlazes = getSCFromApi(statsObject, "kills_lava_blaze");
			FishingTracker.lavaPigmen = getSCFromApi(statsObject, "kills_lava_pigman");
			FishingTracker.zombieMiners = getSCFromApi(statsObject, "kills_zombie_miner");
			FishingTracker.plhlegblasts = getSCFromApi(statsObject, "kills_plhlegblast");
			FishingTracker.magmaSlugs = getSCFromApi(statsObject, "kills_magma_slug");
			FishingTracker.moogmas = getSCFromApi(statsObject, "kills_moogma");
			FishingTracker.lavaLeeches = getSCFromApi(statsObject, "kills_lava_leech");
			FishingTracker.pyroclasticWorms = getSCFromApi(statsObject, "kills_pyroclastic_worm");
			FishingTracker.lavaFlames = getSCFromApi(statsObject, "kills_lava_flame");
			FishingTracker.fireEels = getSCFromApi(statsObject, "kills_fire_eel");
			FishingTracker.tauruses = getSCFromApi(statsObject, "kills_pig_rider");
			FishingTracker.thunders = getSCFromApi(statsObject, "kills_thunder");
			FishingTracker.lordJawbuses = getSCFromApi(statsObject, "kills_lord_jawbus");

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
