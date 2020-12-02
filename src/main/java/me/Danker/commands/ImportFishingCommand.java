package me.Danker.commands;

import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
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
			
			LootCommand.greatCatches = 0;
			LootCommand.goodCatches = 0;
			if (statsObject.has("items_fished_treasure")) {
				if (statsObject.has("items_fished_large_treasure")) {
					LootCommand.greatCatches = statsObject.get("items_fished_large_treasure").getAsInt();
					LootCommand.goodCatches = statsObject.get("items_fished_treasure").getAsInt() - LootCommand.greatCatches;
				} else {
					LootCommand.goodCatches = statsObject.get("items_fished_treasure").getAsInt();
				}
			}
			
			LootCommand.seaCreatures = 0;
			LootCommand.squids = 0;
			if (statsObject.has("kills_pond_squid")) {
				LootCommand.squids = statsObject.get("kills_pond_squid").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.squids;
			
			LootCommand.seaWalkers = 0;
			if (statsObject.has("kills_sea_walker")) {
				LootCommand.seaWalkers = statsObject.get("kills_sea_walker").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.seaWalkers;
			
			LootCommand.nightSquids = 0;
			if (statsObject.has("kills_night_squid")) {
				LootCommand.nightSquids = statsObject.get("kills_night_squid").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.nightSquids;
			
			LootCommand.seaGuardians = 0;
			if (statsObject.has("kills_sea_guardian")) {
				LootCommand.seaGuardians = statsObject.get("kills_sea_guardian").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.seaGuardians;
				
			LootCommand.seaWitches = 0;
			if (statsObject.has("kills_sea_witch")) {
				LootCommand.seaWitches = statsObject.get("kills_sea_witch").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.seaWitches;
			
			LootCommand.seaArchers = 0;
			if (statsObject.has("kills_sea_archer")) {
				LootCommand.seaArchers = statsObject.get("kills_sea_archer").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.seaArchers;
			
			LootCommand.monsterOfTheDeeps = 0;
			if (statsObject.has("kills_zombie_deep")) {
				if (statsObject.has("kills_chicken_deep")) {
					LootCommand.monsterOfTheDeeps = statsObject.get("kills_zombie_deep").getAsInt() + statsObject.get("kills_chicken_deep").getAsInt();
				} else {
					LootCommand.monsterOfTheDeeps = statsObject.get("kills_zombie_deep").getAsInt();
				}
			} else if (statsObject.has("kills_chicken_deep")) {
				LootCommand.monsterOfTheDeeps = statsObject.get("kills_chicken_deep").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.monsterOfTheDeeps;
			
			LootCommand.catfishes = 0;
			if (statsObject.has("kills_catfish")) {
				LootCommand.catfishes = statsObject.get("kills_catfish").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.catfishes;
			
			LootCommand.carrotKings = 0;
			if (statsObject.has("kills_carrot_king")) {
				LootCommand.carrotKings = statsObject.get("kills_carrot_king").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.carrotKings;
			
			LootCommand.seaLeeches = 0;
			if (statsObject.has("kills_sea_leech")) {
				LootCommand.seaLeeches = statsObject.get("kills_sea_leech").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.seaLeeches;
			
			LootCommand.guardianDefenders = 0;
			if (statsObject.has("kills_guardian_defender")) {
				LootCommand.guardianDefenders = statsObject.get("kills_guardian_defender").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.guardianDefenders;
			
			LootCommand.deepSeaProtectors = 0;
			if (statsObject.has("kills_deep_sea_protector")) {
				LootCommand.deepSeaProtectors = statsObject.get("kills_deep_sea_protector").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.deepSeaProtectors;
			
			LootCommand.hydras = 0;
			if (statsObject.has("kills_water_hydra")) {
				// Hydra splits
				LootCommand.hydras = statsObject.get("kills_water_hydra").getAsInt() / 2;
			}
			LootCommand.seaCreatures += LootCommand.hydras;
			
			LootCommand.seaEmperors = 0;
			if (statsObject.has("kills_skeleton_emperor")) {
				if (statsObject.has("kills_guardian_emperor")) {
					LootCommand.seaEmperors = statsObject.get("kills_skeleton_emperor").getAsInt() + statsObject.get("kills_guardian_emperor").getAsInt();
				} else {
					LootCommand.seaEmperors = statsObject.get("kills_skeleton_emperor").getAsInt();
				}
			} else if (statsObject.has("kills_guardian_emperor")) {
				LootCommand.seaEmperors = statsObject.get("kills_guardian_emperor").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.seaEmperors;
			
			LootCommand.fishingMilestone = 0;
			if (statsObject.has("pet_milestone_sea_creatures_killed")) {
				LootCommand.fishingMilestone = statsObject.get("pet_milestone_sea_creatures_killed").getAsInt();
			}
			
			LootCommand.frozenSteves = 0;
			if (statsObject.has("kills_frozen_steve")) {
				LootCommand.frozenSteves = statsObject.get("kills_frozen_steve").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.frozenSteves;
			
			LootCommand.frostyTheSnowmans = 0;
			if (statsObject.has("kills_frosty_the_snowman")) {
				LootCommand.frostyTheSnowmans = statsObject.get("kills_frosty_the_snowman").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.frostyTheSnowmans;
			
			LootCommand.grinches = 0;
			if (statsObject.has("kills_grinch")) {
				LootCommand.grinches = statsObject.get("kills_grinch").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.grinches;
			
			LootCommand.yetis = 0;
			if (statsObject.has("kills_yeti")) {
				LootCommand.yetis = statsObject.get("kills_yeti").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.yetis;
			
			LootCommand.nurseSharks = 0;
			if (statsObject.has("kills_nurse_shark")) {
				LootCommand.nurseSharks = statsObject.get("kills_nurse_shark").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.nurseSharks;
			
			LootCommand.blueSharks = 0;
			if (statsObject.has("kills_nurse_shark")) {
				LootCommand.blueSharks = statsObject.get("kills_blue_shark").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.blueSharks;
			
			LootCommand.tigerSharks = 0;
			if (statsObject.has("kills_nurse_shark")) {
				LootCommand.tigerSharks = statsObject.get("kills_tiger_shark").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.tigerSharks;
			
			LootCommand.greatWhiteSharks = 0;
			if (statsObject.has("kills_nurse_shark")) {
				LootCommand.greatWhiteSharks = statsObject.get("kills_great_white_shark").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.greatWhiteSharks;
			
			LootCommand.scarecrows = 0;
			if (statsObject.has("kills_scarecrow")) {
				LootCommand.scarecrows = statsObject.get("kills_scarecrow").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.scarecrows;
			
			LootCommand.nightmares = 0;
			if (statsObject.has("kills_nightmare")) {
				LootCommand.nightmares = statsObject.get("kills_nightmare").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.nightmares;
			
			LootCommand.werewolfs = 0;
			if (statsObject.has("kills_werewolf")) {
				LootCommand.werewolfs = statsObject.get("kills_werewolf").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.werewolfs;
			
			LootCommand.phantomFishers = 0;
			if (statsObject.has("kills_phantom_fisherman")) {
				LootCommand.phantomFishers = statsObject.get("kills_phantom_fisherman").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.phantomFishers;
			
			LootCommand.grimReapers = 0;
			if (statsObject.has("kills_grim_reaper")) {
				LootCommand.grimReapers = statsObject.get("kills_grim_reaper").getAsInt();
			}
			LootCommand.seaCreatures += LootCommand.grimReapers;
			
			System.out.println("Writing to config...");
			ConfigHandler.writeIntConfig("fishing", "goodCatch", LootCommand.goodCatches);
			ConfigHandler.writeIntConfig("fishing", "greatCatch", LootCommand.greatCatches);
			ConfigHandler.writeIntConfig("fishing", "seaCreature", LootCommand.seaCreatures);
			ConfigHandler.writeIntConfig("fishing", "squid", LootCommand.squids);
			ConfigHandler.writeIntConfig("fishing", "seaWalker", LootCommand.seaWalkers);
			ConfigHandler.writeIntConfig("fishing", "nightSquid", LootCommand.nightSquids);
			ConfigHandler.writeIntConfig("fishing", "seaGuardian", LootCommand.seaGuardians);
			ConfigHandler.writeIntConfig("fishing", "seaWitch", LootCommand.seaWitches);
			ConfigHandler.writeIntConfig("fishing", "seaArcher", LootCommand.seaArchers);
			ConfigHandler.writeIntConfig("fishing", "monsterOfDeep", LootCommand.monsterOfTheDeeps);
			ConfigHandler.writeIntConfig("fishing", "catfish", LootCommand.catfishes);
			ConfigHandler.writeIntConfig("fishing", "carrotKing", LootCommand.carrotKings);
			ConfigHandler.writeIntConfig("fishing", "seaLeech", LootCommand.seaLeeches);
			ConfigHandler.writeIntConfig("fishing", "guardianDefender", LootCommand.guardianDefenders);
			ConfigHandler.writeIntConfig("fishing", "deepSeaProtector", LootCommand.deepSeaProtectors);
			ConfigHandler.writeIntConfig("fishing", "hydra", LootCommand.hydras);
			ConfigHandler.writeIntConfig("fishing", "seaEmperor", LootCommand.seaEmperors);
			ConfigHandler.writeIntConfig("fishing", "milestone", LootCommand.fishingMilestone);
			ConfigHandler.writeIntConfig("fishing", "frozenSteve", LootCommand.frozenSteves);
			ConfigHandler.writeIntConfig("fishing", "snowman", LootCommand.frostyTheSnowmans);
			ConfigHandler.writeIntConfig("fishing", "grinch", LootCommand.grinches);
			ConfigHandler.writeIntConfig("fishing", "yeti", LootCommand.yetis);
			ConfigHandler.writeIntConfig("fishing", "nurseShark", LootCommand.nurseSharks);
			ConfigHandler.writeIntConfig("fishing", "blueShark", LootCommand.blueSharks);
			ConfigHandler.writeIntConfig("fishing", "tigerShark", LootCommand.tigerSharks);
			ConfigHandler.writeIntConfig("fishing", "greatWhiteShark", LootCommand.greatWhiteSharks);
			ConfigHandler.writeIntConfig("fishing", "scarecrow", LootCommand.scarecrows);
			ConfigHandler.writeIntConfig("fishing", "nightmare", LootCommand.nightmares);
			ConfigHandler.writeIntConfig("fishing", "werewolf", LootCommand.werewolfs);
			ConfigHandler.writeIntConfig("fishing", "phantomFisher", LootCommand.phantomFishers);
			ConfigHandler.writeIntConfig("fishing", "grimReaper", LootCommand.grimReapers);
			
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Fishing stats imported."));
		}).start();
	}

}
