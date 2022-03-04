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
			FishingTracker.squids = 0;
			if (statsObject.has("kills_pond_squid")) {
				FishingTracker.squids = statsObject.get("kills_pond_squid").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.squids;

			FishingTracker.seaWalkers = 0;
			if (statsObject.has("kills_sea_walker")) {
				FishingTracker.seaWalkers = statsObject.get("kills_sea_walker").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.seaWalkers;

			FishingTracker.nightSquids = 0;
			if (statsObject.has("kills_night_squid")) {
				FishingTracker.nightSquids = statsObject.get("kills_night_squid").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.nightSquids;

			FishingTracker.seaGuardians = 0;
			if (statsObject.has("kills_sea_guardian")) {
				FishingTracker.seaGuardians = statsObject.get("kills_sea_guardian").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.seaGuardians;

			FishingTracker.seaWitches = 0;
			if (statsObject.has("kills_sea_witch")) {
				FishingTracker.seaWitches = statsObject.get("kills_sea_witch").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.seaWitches;

			FishingTracker.seaArchers = 0;
			if (statsObject.has("kills_sea_archer")) {
				FishingTracker.seaArchers = statsObject.get("kills_sea_archer").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.seaArchers;

			FishingTracker.monsterOfTheDeeps = 0;
			if (statsObject.has("kills_zombie_deep")) {
				if (statsObject.has("kills_chicken_deep")) {
					FishingTracker.monsterOfTheDeeps = statsObject.get("kills_zombie_deep").getAsInt() + statsObject.get("kills_chicken_deep").getAsInt();
				} else {
					FishingTracker.monsterOfTheDeeps = statsObject.get("kills_zombie_deep").getAsInt();
				}
			} else if (statsObject.has("kills_chicken_deep")) {
				FishingTracker.monsterOfTheDeeps = statsObject.get("kills_chicken_deep").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.monsterOfTheDeeps;

			FishingTracker.catfishes = 0;
			if (statsObject.has("kills_catfish")) {
				FishingTracker.catfishes = statsObject.get("kills_catfish").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.catfishes;

			FishingTracker.carrotKings = 0;
			if (statsObject.has("kills_carrot_king")) {
				FishingTracker.carrotKings = statsObject.get("kills_carrot_king").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.carrotKings;

			FishingTracker.seaLeeches = 0;
			if (statsObject.has("kills_sea_leech")) {
				FishingTracker.seaLeeches = statsObject.get("kills_sea_leech").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.seaLeeches;

			FishingTracker.guardianDefenders = 0;
			if (statsObject.has("kills_guardian_defender")) {
				FishingTracker.guardianDefenders = statsObject.get("kills_guardian_defender").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.guardianDefenders;

			FishingTracker.deepSeaProtectors = 0;
			if (statsObject.has("kills_deep_sea_protector")) {
				FishingTracker.deepSeaProtectors = statsObject.get("kills_deep_sea_protector").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.deepSeaProtectors;

			FishingTracker.hydras = 0;
			if (statsObject.has("kills_water_hydra")) {
				// Hydra splits
				FishingTracker.hydras = statsObject.get("kills_water_hydra").getAsInt() / 2;
			}
			FishingTracker.seaCreatures += FishingTracker.hydras;

			FishingTracker.seaEmperors = 0;
			if (statsObject.has("kills_skeleton_emperor")) {
				if (statsObject.has("kills_guardian_emperor")) {
					FishingTracker.seaEmperors = statsObject.get("kills_skeleton_emperor").getAsInt() + statsObject.get("kills_guardian_emperor").getAsInt();
				} else {
					FishingTracker.seaEmperors = statsObject.get("kills_skeleton_emperor").getAsInt();
				}
			} else if (statsObject.has("kills_guardian_emperor")) {
				FishingTracker.seaEmperors = statsObject.get("kills_guardian_emperor").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.seaEmperors;

			FishingTracker.fishingMilestone = 0;
			if (statsObject.has("pet_milestone_sea_creatures_killed")) {
				FishingTracker.fishingMilestone = statsObject.get("pet_milestone_sea_creatures_killed").getAsInt();
			}

			FishingTracker.frozenSteves = 0;
			if (statsObject.has("kills_frozen_steve")) {
				FishingTracker.frozenSteves = statsObject.get("kills_frozen_steve").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.frozenSteves;

			FishingTracker.frostyTheSnowmans = 0;
			if (statsObject.has("kills_frosty_the_snowman")) {
				FishingTracker.frostyTheSnowmans = statsObject.get("kills_frosty_the_snowman").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.frostyTheSnowmans;

			FishingTracker.grinches = 0;
			if (statsObject.has("kills_grinch")) {
				FishingTracker.grinches = statsObject.get("kills_grinch").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.grinches;

			FishingTracker.yetis = 0;
			if (statsObject.has("kills_yeti")) {
				FishingTracker.yetis = statsObject.get("kills_yeti").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.yetis;

			FishingTracker.nurseSharks = 0;
			if (statsObject.has("kills_nurse_shark")) {
				FishingTracker.nurseSharks = statsObject.get("kills_nurse_shark").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.nurseSharks;

			FishingTracker.blueSharks = 0;
			if (statsObject.has("kills_nurse_shark")) {
				FishingTracker.blueSharks = statsObject.get("kills_blue_shark").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.blueSharks;

			FishingTracker.tigerSharks = 0;
			if (statsObject.has("kills_nurse_shark")) {
				FishingTracker.tigerSharks = statsObject.get("kills_tiger_shark").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.tigerSharks;

			FishingTracker.greatWhiteSharks = 0;
			if (statsObject.has("kills_nurse_shark")) {
				FishingTracker.greatWhiteSharks = statsObject.get("kills_great_white_shark").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.greatWhiteSharks;

			FishingTracker.scarecrows = 0;
			if (statsObject.has("kills_scarecrow")) {
				FishingTracker.scarecrows = statsObject.get("kills_scarecrow").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.scarecrows;

			FishingTracker.nightmares = 0;
			if (statsObject.has("kills_nightmare")) {
				FishingTracker.nightmares = statsObject.get("kills_nightmare").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.nightmares;

			FishingTracker.werewolfs = 0;
			if (statsObject.has("kills_werewolf")) {
				FishingTracker.werewolfs = statsObject.get("kills_werewolf").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.werewolfs;

			FishingTracker.phantomFishers = 0;
			if (statsObject.has("kills_phantom_fisherman")) {
				FishingTracker.phantomFishers = statsObject.get("kills_phantom_fisherman").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.phantomFishers;

			FishingTracker.grimReapers = 0;
			if (statsObject.has("kills_grim_reaper")) {
				FishingTracker.grimReapers = statsObject.get("kills_grim_reaper").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.grimReapers;

			FishingTracker.waterWorms = 0;
			if (statsObject.has("kills_water_worm")) {
				FishingTracker.waterWorms = statsObject.get("kills_water_worm").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.waterWorms;

			FishingTracker.poisonedWaterWorms = 0;
			if (statsObject.has("kills_poisoned_water_worm")) {
				FishingTracker.poisonedWaterWorms = statsObject.get("kills_poisoned_water_worm").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.poisonedWaterWorms;

			FishingTracker.flamingWorms = 0;
			if (statsObject.has("kills_flaming_worm")) {
				FishingTracker.flamingWorms = statsObject.get("kills_flaming_worm").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.flamingWorms;

			FishingTracker.lavaBlazes = 0;
			if (statsObject.has("kills_lava_blaze")) {
				FishingTracker.lavaBlazes = statsObject.get("kills_lava_blaze").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.lavaBlazes;

			FishingTracker.lavaPigmen = 0;
			if (statsObject.has("kills_lava_pigman")) {
				FishingTracker.lavaPigmen = statsObject.get("kills_lava_pigman").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.lavaPigmen;

			FishingTracker.zombieMiners = 0;
			if (statsObject.has("kills_zombie_miner")) {
				FishingTracker.zombieMiners = statsObject.get("kills_zombie_miner").getAsInt();
			}
			FishingTracker.seaCreatures += FishingTracker.zombieMiners;

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

			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Fishing stats imported."));
		}).start();
	}

}
