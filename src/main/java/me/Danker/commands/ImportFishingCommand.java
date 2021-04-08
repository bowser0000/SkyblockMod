package me.Danker.commands;

import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.features.loot.LootTracker;
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
			}
						
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Importing your fishing stats..."));
			
			// Get UUID for Hypixel API requests
			String uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
			
			String latestProfile = APIHandler.getLatestProfileID(uuid, key);
			if (latestProfile == null) return;
			
			String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + key;
			System.out.println("Fetching profile...");
			JsonObject profileResponse = APIHandler.getResponse(profileURL);
			if (!profileResponse.get("success").getAsBoolean()) {
				String reason = profileResponse.get("cause").getAsString();
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Failed with reason: " + reason));
				return;
			}
			
			System.out.println("Fetching fishing stats...");
			JsonObject statsObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("stats").getAsJsonObject();
			
			LootTracker.greatCatches = 0;
			LootTracker.goodCatches = 0;
			if (statsObject.has("items_fished_treasure")) {
				if (statsObject.has("items_fished_large_treasure")) {
					LootTracker.greatCatches = statsObject.get("items_fished_large_treasure").getAsInt();
					LootTracker.goodCatches = statsObject.get("items_fished_treasure").getAsInt() - LootTracker.greatCatches;
				} else {
					LootTracker.goodCatches = statsObject.get("items_fished_treasure").getAsInt();
				}
			}
			
			LootTracker.seaCreatures = 0;
			LootTracker.squids = 0;
			if (statsObject.has("kills_pond_squid")) {
				LootTracker.squids = statsObject.get("kills_pond_squid").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.squids;
			
			LootTracker.seaWalkers = 0;
			if (statsObject.has("kills_sea_walker")) {
				LootTracker.seaWalkers = statsObject.get("kills_sea_walker").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.seaWalkers;
			
			LootTracker.nightSquids = 0;
			if (statsObject.has("kills_night_squid")) {
				LootTracker.nightSquids = statsObject.get("kills_night_squid").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.nightSquids;
			
			LootTracker.seaGuardians = 0;
			if (statsObject.has("kills_sea_guardian")) {
				LootTracker.seaGuardians = statsObject.get("kills_sea_guardian").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.seaGuardians;
				
			LootTracker.seaWitches = 0;
			if (statsObject.has("kills_sea_witch")) {
				LootTracker.seaWitches = statsObject.get("kills_sea_witch").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.seaWitches;
			
			LootTracker.seaArchers = 0;
			if (statsObject.has("kills_sea_archer")) {
				LootTracker.seaArchers = statsObject.get("kills_sea_archer").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.seaArchers;
			
			LootTracker.monsterOfTheDeeps = 0;
			if (statsObject.has("kills_zombie_deep")) {
				if (statsObject.has("kills_chicken_deep")) {
					LootTracker.monsterOfTheDeeps = statsObject.get("kills_zombie_deep").getAsInt() + statsObject.get("kills_chicken_deep").getAsInt();
				} else {
					LootTracker.monsterOfTheDeeps = statsObject.get("kills_zombie_deep").getAsInt();
				}
			} else if (statsObject.has("kills_chicken_deep")) {
				LootTracker.monsterOfTheDeeps = statsObject.get("kills_chicken_deep").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.monsterOfTheDeeps;
			
			LootTracker.catfishes = 0;
			if (statsObject.has("kills_catfish")) {
				LootTracker.catfishes = statsObject.get("kills_catfish").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.catfishes;
			
			LootTracker.carrotKings = 0;
			if (statsObject.has("kills_carrot_king")) {
				LootTracker.carrotKings = statsObject.get("kills_carrot_king").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.carrotKings;
			
			LootTracker.seaLeeches = 0;
			if (statsObject.has("kills_sea_leech")) {
				LootTracker.seaLeeches = statsObject.get("kills_sea_leech").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.seaLeeches;
			
			LootTracker.guardianDefenders = 0;
			if (statsObject.has("kills_guardian_defender")) {
				LootTracker.guardianDefenders = statsObject.get("kills_guardian_defender").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.guardianDefenders;
			
			LootTracker.deepSeaProtectors = 0;
			if (statsObject.has("kills_deep_sea_protector")) {
				LootTracker.deepSeaProtectors = statsObject.get("kills_deep_sea_protector").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.deepSeaProtectors;
			
			LootTracker.hydras = 0;
			if (statsObject.has("kills_water_hydra")) {
				// Hydra splits
				LootTracker.hydras = statsObject.get("kills_water_hydra").getAsInt() / 2;
			}
			LootTracker.seaCreatures += LootTracker.hydras;
			
			LootTracker.seaEmperors = 0;
			if (statsObject.has("kills_skeleton_emperor")) {
				if (statsObject.has("kills_guardian_emperor")) {
					LootTracker.seaEmperors = statsObject.get("kills_skeleton_emperor").getAsInt() + statsObject.get("kills_guardian_emperor").getAsInt();
				} else {
					LootTracker.seaEmperors = statsObject.get("kills_skeleton_emperor").getAsInt();
				}
			} else if (statsObject.has("kills_guardian_emperor")) {
				LootTracker.seaEmperors = statsObject.get("kills_guardian_emperor").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.seaEmperors;
			
			LootTracker.fishingMilestone = 0;
			if (statsObject.has("pet_milestone_sea_creatures_killed")) {
				LootTracker.fishingMilestone = statsObject.get("pet_milestone_sea_creatures_killed").getAsInt();
			}
			
			LootTracker.frozenSteves = 0;
			if (statsObject.has("kills_frozen_steve")) {
				LootTracker.frozenSteves = statsObject.get("kills_frozen_steve").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.frozenSteves;
			
			LootTracker.frostyTheSnowmans = 0;
			if (statsObject.has("kills_frosty_the_snowman")) {
				LootTracker.frostyTheSnowmans = statsObject.get("kills_frosty_the_snowman").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.frostyTheSnowmans;
			
			LootTracker.grinches = 0;
			if (statsObject.has("kills_grinch")) {
				LootTracker.grinches = statsObject.get("kills_grinch").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.grinches;
			
			LootTracker.yetis = 0;
			if (statsObject.has("kills_yeti")) {
				LootTracker.yetis = statsObject.get("kills_yeti").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.yetis;
			
			LootTracker.nurseSharks = 0;
			if (statsObject.has("kills_nurse_shark")) {
				LootTracker.nurseSharks = statsObject.get("kills_nurse_shark").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.nurseSharks;
			
			LootTracker.blueSharks = 0;
			if (statsObject.has("kills_nurse_shark")) {
				LootTracker.blueSharks = statsObject.get("kills_blue_shark").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.blueSharks;
			
			LootTracker.tigerSharks = 0;
			if (statsObject.has("kills_nurse_shark")) {
				LootTracker.tigerSharks = statsObject.get("kills_tiger_shark").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.tigerSharks;
			
			LootTracker.greatWhiteSharks = 0;
			if (statsObject.has("kills_nurse_shark")) {
				LootTracker.greatWhiteSharks = statsObject.get("kills_great_white_shark").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.greatWhiteSharks;
			
			LootTracker.scarecrows = 0;
			if (statsObject.has("kills_scarecrow")) {
				LootTracker.scarecrows = statsObject.get("kills_scarecrow").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.scarecrows;
			
			LootTracker.nightmares = 0;
			if (statsObject.has("kills_nightmare")) {
				LootTracker.nightmares = statsObject.get("kills_nightmare").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.nightmares;
			
			LootTracker.werewolfs = 0;
			if (statsObject.has("kills_werewolf")) {
				LootTracker.werewolfs = statsObject.get("kills_werewolf").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.werewolfs;
			
			LootTracker.phantomFishers = 0;
			if (statsObject.has("kills_phantom_fisherman")) {
				LootTracker.phantomFishers = statsObject.get("kills_phantom_fisherman").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.phantomFishers;
			
			LootTracker.grimReapers = 0;
			if (statsObject.has("kills_grim_reaper")) {
				LootTracker.grimReapers = statsObject.get("kills_grim_reaper").getAsInt();
			}
			LootTracker.seaCreatures += LootTracker.grimReapers;
			
			System.out.println("Writing to config...");
			ConfigHandler.writeIntConfig("fishing", "goodCatch", LootTracker.goodCatches);
			ConfigHandler.writeIntConfig("fishing", "greatCatch", LootTracker.greatCatches);
			ConfigHandler.writeIntConfig("fishing", "seaCreature", LootTracker.seaCreatures);
			ConfigHandler.writeIntConfig("fishing", "squid", LootTracker.squids);
			ConfigHandler.writeIntConfig("fishing", "seaWalker", LootTracker.seaWalkers);
			ConfigHandler.writeIntConfig("fishing", "nightSquid", LootTracker.nightSquids);
			ConfigHandler.writeIntConfig("fishing", "seaGuardian", LootTracker.seaGuardians);
			ConfigHandler.writeIntConfig("fishing", "seaWitch", LootTracker.seaWitches);
			ConfigHandler.writeIntConfig("fishing", "seaArcher", LootTracker.seaArchers);
			ConfigHandler.writeIntConfig("fishing", "monsterOfDeep", LootTracker.monsterOfTheDeeps);
			ConfigHandler.writeIntConfig("fishing", "catfish", LootTracker.catfishes);
			ConfigHandler.writeIntConfig("fishing", "carrotKing", LootTracker.carrotKings);
			ConfigHandler.writeIntConfig("fishing", "seaLeech", LootTracker.seaLeeches);
			ConfigHandler.writeIntConfig("fishing", "guardianDefender", LootTracker.guardianDefenders);
			ConfigHandler.writeIntConfig("fishing", "deepSeaProtector", LootTracker.deepSeaProtectors);
			ConfigHandler.writeIntConfig("fishing", "hydra", LootTracker.hydras);
			ConfigHandler.writeIntConfig("fishing", "seaEmperor", LootTracker.seaEmperors);
			ConfigHandler.writeIntConfig("fishing", "milestone", LootTracker.fishingMilestone);
			ConfigHandler.writeIntConfig("fishing", "frozenSteve", LootTracker.frozenSteves);
			ConfigHandler.writeIntConfig("fishing", "snowman", LootTracker.frostyTheSnowmans);
			ConfigHandler.writeIntConfig("fishing", "grinch", LootTracker.grinches);
			ConfigHandler.writeIntConfig("fishing", "yeti", LootTracker.yetis);
			ConfigHandler.writeIntConfig("fishing", "nurseShark", LootTracker.nurseSharks);
			ConfigHandler.writeIntConfig("fishing", "blueShark", LootTracker.blueSharks);
			ConfigHandler.writeIntConfig("fishing", "tigerShark", LootTracker.tigerSharks);
			ConfigHandler.writeIntConfig("fishing", "greatWhiteShark", LootTracker.greatWhiteSharks);
			ConfigHandler.writeIntConfig("fishing", "scarecrow", LootTracker.scarecrows);
			ConfigHandler.writeIntConfig("fishing", "nightmare", LootTracker.nightmares);
			ConfigHandler.writeIntConfig("fishing", "werewolf", LootTracker.werewolfs);
			ConfigHandler.writeIntConfig("fishing", "phantomFisher", LootTracker.phantomFishers);
			ConfigHandler.writeIntConfig("fishing", "grimReaper", LootTracker.grimReapers);
			
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Fishing stats imported."));
		}).start();
	}

}
