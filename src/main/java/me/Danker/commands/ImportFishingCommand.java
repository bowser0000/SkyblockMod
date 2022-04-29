package me.Danker.commands;

import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.features.loot.FishingTracker;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
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
			String key = ConfigHandler.getString("api", "APIKey");
			if (key.equals("")) {
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "API key not set. Use /setkey."));
				return;
			}

			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Importing your fishing stats..."));

			// Get UUID for Hypixel API requests
			String uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");

			String latestProfile = APIHandler.getLatestProfileID(uuid, key);
			if (latestProfile == null) return;

			String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + key;
			System.out.println("Fetching profile...");
			JsonObject profileResponse = APIHandler.getResponse(profileURL, true);
			if (!profileResponse.get("success").getAsBoolean()) {
				String reason = profileResponse.get("cause").getAsString();
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Failed with reason: " + reason));
				return;
			}

			System.out.println("Fetching fishing stats...");
			JsonObject statsObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("stats").getAsJsonObject();

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
			FishingTracker.yetis = getSCFromApi(statsObject, "kills_yeti");
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

			System.out.println("Writing to config...");
			ConfigHandler.writeIntConfig("fishing", "goodCatch", FishingTracker.goodCatches);
			ConfigHandler.writeIntConfig("fishing", "greatCatch", FishingTracker.greatCatches);
			ConfigHandler.writeIntConfig("fishing", "seaCreature", FishingTracker.seaCreatures);
			ConfigHandler.writeIntConfig("fishing", "squid", FishingTracker.squids);
			ConfigHandler.writeIntConfig("fishing", "seaWalker", FishingTracker.seaWalkers);
			ConfigHandler.writeIntConfig("fishing", "nightSquid", FishingTracker.nightSquids);
			ConfigHandler.writeIntConfig("fishing", "seaGuardian", FishingTracker.seaGuardians);
			ConfigHandler.writeIntConfig("fishing", "seaWitch", FishingTracker.seaWitches);
			ConfigHandler.writeIntConfig("fishing", "seaArcher", FishingTracker.seaArchers);
			ConfigHandler.writeIntConfig("fishing", "monsterOfDeep", FishingTracker.monsterOfTheDeeps);
			ConfigHandler.writeIntConfig("fishing", "catfish", FishingTracker.catfishes);
			ConfigHandler.writeIntConfig("fishing", "carrotKing", FishingTracker.carrotKings);
			ConfigHandler.writeIntConfig("fishing", "seaLeech", FishingTracker.seaLeeches);
			ConfigHandler.writeIntConfig("fishing", "guardianDefender", FishingTracker.guardianDefenders);
			ConfigHandler.writeIntConfig("fishing", "deepSeaProtector", FishingTracker.deepSeaProtectors);
			ConfigHandler.writeIntConfig("fishing", "hydra", FishingTracker.hydras);
			ConfigHandler.writeIntConfig("fishing", "seaEmperor", FishingTracker.seaEmperors);
			ConfigHandler.writeIntConfig("fishing", "milestone", FishingTracker.fishingMilestone);
			ConfigHandler.writeIntConfig("fishing", "frozenSteve", FishingTracker.frozenSteves);
			ConfigHandler.writeIntConfig("fishing", "snowman", FishingTracker.frostyTheSnowmans);
			ConfigHandler.writeIntConfig("fishing", "grinch", FishingTracker.grinches);
			ConfigHandler.writeIntConfig("fishing", "yeti", FishingTracker.yetis);
			ConfigHandler.writeIntConfig("fishing", "nurseShark", FishingTracker.nurseSharks);
			ConfigHandler.writeIntConfig("fishing", "blueShark", FishingTracker.blueSharks);
			ConfigHandler.writeIntConfig("fishing", "tigerShark", FishingTracker.tigerSharks);
			ConfigHandler.writeIntConfig("fishing", "greatWhiteShark", FishingTracker.greatWhiteSharks);
			ConfigHandler.writeIntConfig("fishing", "scarecrow", FishingTracker.scarecrows);
			ConfigHandler.writeIntConfig("fishing", "nightmare", FishingTracker.nightmares);
			ConfigHandler.writeIntConfig("fishing", "werewolf", FishingTracker.werewolfs);
			ConfigHandler.writeIntConfig("fishing", "phantomFisher", FishingTracker.phantomFishers);
			ConfigHandler.writeIntConfig("fishing", "grimReaper", FishingTracker.grimReapers);
			ConfigHandler.writeIntConfig("fishing", "waterWorm", FishingTracker.waterWorms);
			ConfigHandler.writeIntConfig("fishing", "poisonedWaterWorm", FishingTracker.poisonedWaterWorms);
			ConfigHandler.writeIntConfig("fishing", "flamingWorm", FishingTracker.flamingWorms);
			ConfigHandler.writeIntConfig("fishing", "lavaBlaze", FishingTracker.lavaBlazes);
			ConfigHandler.writeIntConfig("fishing", "lavaPigman", FishingTracker.lavaPigmen);
			ConfigHandler.writeIntConfig("fishing", "zombieMiner", FishingTracker.zombieMiners);
			ConfigHandler.writeIntConfig("fishing", "plhlegblast", FishingTracker.plhlegblasts);
			ConfigHandler.writeIntConfig("fishing", "magmaSlug", FishingTracker.magmaSlugs);
			ConfigHandler.writeIntConfig("fishing", "moogma", FishingTracker.moogmas);
			ConfigHandler.writeIntConfig("fishing", "lavaLeech", FishingTracker.lavaLeeches);
			ConfigHandler.writeIntConfig("fishing", "pyroclasticWorm", FishingTracker.pyroclasticWorms);
			ConfigHandler.writeIntConfig("fishing", "lavaFlame", FishingTracker.lavaFlames);
			ConfigHandler.writeIntConfig("fishing", "fireEel", FishingTracker.fireEels);
			ConfigHandler.writeIntConfig("fishing", "taurus", FishingTracker.tauruses);
			ConfigHandler.writeIntConfig("fishing", "thunder", FishingTracker.thunders);
			ConfigHandler.writeIntConfig("fishing", "lordJawbus", FishingTracker.lordJawbuses);

			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Fishing stats imported."));
		}).start();
	}

	static int getSCFromApi(JsonObject obj, String key) {
		int sc = 0;
		if (obj.has(key)) sc = obj.get(key).getAsInt();
		FishingTracker.seaCreatures += sc;
		return sc;
	}

}
