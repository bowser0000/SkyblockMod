package me.Danker.commands;

import com.google.gson.JsonObject;

import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ImportFishingCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "importfishing";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName();
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		// MULTI THREAD DRIFTING
		new Thread(() -> {
			APIHandler ah = new APIHandler();
			LootCommand lc = new LootCommand();
			ConfigHandler cf = new ConfigHandler();
			EntityPlayer player = (EntityPlayer) arg0;
			
			// Check key
			String key = cf.getString("api", "APIKey");
			if (key.equals("")) {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "API key not set. Use /setkey."));
			}
						
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Importing your fishing stats..."));
			
			// Get UUID for Hypixel API requests
			String username = player.getName();
			String uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
			
			String latestProfile = ah.getLatestProfileID(uuid, key);
			if (latestProfile == null) return;
			
			String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + key;
			System.out.println("Fetching profile...");
			JsonObject profileResponse = ah.getResponse(profileURL);
			if (!profileResponse.get("success").getAsBoolean()) {
				String reason = profileResponse.get("cause").getAsString();
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed with reason: " + reason));
				return;
			}
			
			System.out.println("Fetching fishing stats...");
			JsonObject statsObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("stats").getAsJsonObject();
			
			lc.greatCatches = 0;
			lc.goodCatches = 0;
			if (statsObject.has("items_fished_treasure")) {
				if (statsObject.has("items_fished_large_treasure")) {
					lc.greatCatches = statsObject.get("items_fished_large_treasure").getAsInt();
					lc.goodCatches = statsObject.get("items_fished_treasure").getAsInt() - lc.greatCatches;
				} else {
					lc.goodCatches = statsObject.get("items_fished_treasure").getAsInt();
				}
			}
			
			lc.seaCreatures = 0;
			lc.squids = 0;
			if (statsObject.has("kills_pond_squid")) {
				lc.squids = statsObject.get("kills_pond_squid").getAsInt();
			}
			lc.seaCreatures += lc.squids;
			
			lc.seaWalkers = 0;
			if (statsObject.has("kills_sea_walker")) {
				lc.seaWalkers = statsObject.get("kills_sea_walker").getAsInt();
			}
			lc.seaCreatures += lc.seaWalkers;
			
			lc.nightSquids = 0;
			if (statsObject.has("kills_night_squid")) {
				lc.nightSquids = statsObject.get("kills_night_squid").getAsInt();
			}
			lc.seaCreatures += lc.nightSquids;
			
			lc.seaGuardians = 0;
			if (statsObject.has("kills_sea_guardian")) {
				lc.seaGuardians = statsObject.get("kills_sea_guardian").getAsInt();
			}
			lc.seaCreatures += lc.seaGuardians;
				
			lc.seaWitches = 0;
			if (statsObject.has("kills_sea_witch")) {
				lc.seaWitches = statsObject.get("kills_sea_witch").getAsInt();
			}
			lc.seaCreatures += lc.seaWitches;
			
			lc.seaArchers = 0;
			if (statsObject.has("kills_sea_archer")) {
				lc.seaArchers = statsObject.get("kills_sea_archer").getAsInt();
			}
			lc.seaCreatures += lc.seaArchers;
			
			lc.monsterOfTheDeeps = 0;
			if (statsObject.has("kills_zombie_deep")) {
				if (statsObject.has("kills_chicken_deep")) {
					lc.monsterOfTheDeeps = statsObject.get("kills_zombie_deep").getAsInt() + statsObject.get("kills_chicken_deep").getAsInt();
				} else {
					lc.monsterOfTheDeeps = statsObject.get("kills_zombie_deep").getAsInt();
				}
			} else if (statsObject.has("kills_chicken_deep")) {
				lc.monsterOfTheDeeps = statsObject.get("kills_chicken_deep").getAsInt();
			}
			lc.seaCreatures += lc.monsterOfTheDeeps;
			
			lc.catfishes = 0;
			if (statsObject.has("kills_catfish")) {
				lc.catfishes = statsObject.get("kills_catfish").getAsInt();
			}
			lc.seaCreatures += lc.catfishes;
			
			lc.carrotKings = 0;
			if (statsObject.has("kills_carrot_king")) {
				lc.carrotKings = statsObject.get("kills_carrot_king").getAsInt();
			}
			lc.seaCreatures += lc.carrotKings;
			
			lc.seaLeeches = 0;
			if (statsObject.has("kills_sea_leech")) {
				lc.seaLeeches = statsObject.get("kills_sea_leech").getAsInt();
			}
			lc.seaCreatures += lc.seaLeeches;
			
			lc.guardianDefenders = 0;
			if (statsObject.has("kills_guardian_defender")) {
				lc.guardianDefenders = statsObject.get("kills_guardian_defender").getAsInt();
			}
			lc.seaCreatures += lc.guardianDefenders;
			
			lc.deepSeaProtectors = 0;
			if (statsObject.has("kills_deep_sea_protector")) {
				lc.deepSeaProtectors = statsObject.get("kills_deep_sea_protector").getAsInt();
			}
			lc.seaCreatures += lc.deepSeaProtectors;
			
			lc.hydras = 0;
			if (statsObject.has("kills_water_hydra")) {
				// Hydra splits
				lc.hydras = statsObject.get("kills_water_hydra").getAsInt() / 2;
			}
			lc.seaCreatures += lc.hydras;
			
			lc.seaEmperors = 0;
			if (statsObject.has("kills_skeleton_emperor")) {
				if (statsObject.has("kills_guardian_emperor")) {
					lc.seaEmperors = statsObject.get("kills_skeleton_emperor").getAsInt() + statsObject.get("kills_guardian_emperor").getAsInt();
				} else {
					lc.seaEmperors = statsObject.get("kills_skeleton_emperor").getAsInt();
				}
			} else if (statsObject.has("kills_guardian_emperor")) {
				lc.seaEmperors = statsObject.get("kills_guardian_emperor").getAsInt();
			}
			lc.seaCreatures += lc.seaEmperors;
			
			lc.fishingMilestone = 0;
			if (statsObject.has("pet_milestone_sea_creatures_killed")) {
				lc.fishingMilestone = statsObject.get("pet_milestone_sea_creatures_killed").getAsInt();
			}
			
			lc.frozenSteves = 0;
			if (statsObject.has("kills_frozen_steve")) {
				lc.frozenSteves = statsObject.get("kills_frozen_steve").getAsInt();
			}
			lc.seaCreatures += lc.frozenSteves;
			
			lc.frostyTheSnowmans = 0;
			if (statsObject.has("kills_frosty_the_snowman")) {
				lc.frostyTheSnowmans = statsObject.get("kills_frosty_the_snowman").getAsInt();
			}
			lc.seaCreatures += lc.frostyTheSnowmans;
			
			lc.grinches = 0;
			if (statsObject.has("kills_grinch")) {
				lc.grinches = statsObject.get("kills_grinch").getAsInt();
			}
			lc.seaCreatures += lc.grinches;
			
			lc.yetis = 0;
			if (statsObject.has("kills_yeti")) {
				lc.yetis = statsObject.get("kills_yeti").getAsInt();
			}
			lc.seaCreatures += lc.yetis;
			
			System.out.println("Writing to config...");
			cf.writeIntConfig("fishing", "goodCatch", lc.goodCatches);
			cf.writeIntConfig("fishing", "greatCatch", lc.greatCatches);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "squid", lc.squids);
			cf.writeIntConfig("fishing", "seaWalker", lc.seaWalkers);
			cf.writeIntConfig("fishing", "nightSquid", lc.nightSquids);
			cf.writeIntConfig("fishing", "seaGuardian", lc.seaGuardians);
			cf.writeIntConfig("fishing", "seaWitch", lc.seaWitches);
			cf.writeIntConfig("fishing", "seaArcher", lc.seaArchers);
			cf.writeIntConfig("fishing", "monsterOfDeep", lc.monsterOfTheDeeps);
			cf.writeIntConfig("fishing", "catfish", lc.catfishes);
			cf.writeIntConfig("fishing", "carrotKing", lc.carrotKings);
			cf.writeIntConfig("fishing", "seaLeech", lc.seaLeeches);
			cf.writeIntConfig("fishing", "guardianDefender", lc.guardianDefenders);
			cf.writeIntConfig("fishing", "deepSeaProtector", lc.deepSeaProtectors);
			cf.writeIntConfig("fishing", "hydra", lc.hydras);
			cf.writeIntConfig("fishing", "seaEmperor", lc.seaEmperors);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			cf.writeIntConfig("fishing", "frozenSteve", lc.frozenSteves);
			cf.writeIntConfig("fishing", "snowman", lc.frostyTheSnowmans);
			cf.writeIntConfig("fishing", "grinch", lc.grinches);
			cf.writeIntConfig("fishing", "yeti", lc.yetis);
			
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Fishing stats imported."));
		}).start();
	}

}
