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

public class SkillsCommand extends CommandBase {

	static double xpToLevel(double xp) {
		int level;
		double progress;
		
		if (xp < 50) {
			level = 0;
			progress = xp / 50;
		} else if (xp < 175) {
			level = 1;
			progress = (xp - 50) / 125;
		} else if (xp < 375) {
			level = 2;
			progress = (xp - 175) / 200;
		} else if (xp < 675) {
			level = 3;
			progress = (xp - 375) / 300;
		} else if (xp < 1175) {
			level = 4;
			progress = (xp - 675) / 500;
		} else if (xp < 1925) {
			level = 5;
			progress = (xp - 1175) / 750;
		} else if (xp < 2925) {
			level = 6;
			progress = (xp - 1925) / 1000;
		} else if (xp < 4425) {
			level = 7;
			progress = (xp - 2925) / 1500;
		} else if (xp < 6425) {
			level = 8;
			progress = (xp - 4425) / 2000;
		} else if (xp < 9925) {
			level = 9;
			progress = (xp - 6425) / 3500;
		} else if (xp < 14925) {
			level = 10;
			progress = (xp - 9925) / 5000;
		} else if (xp < 22425) {
			level = 11;
			progress = (xp - 14925) / 7500;
		} else if (xp < 32425) {
			level = 12;
			progress = (xp - 22425) / 10000;
		} else if (xp < 47425) {
			level = 13;
			progress = (xp - 32425) / 15000;
		} else if (xp < 67425) {
			level = 14;
			progress = (xp - 47425) / 20000;
		} else if (xp < 97425) {
			level = 15;
			progress = (xp - 67425) / 30000;
		} else if (xp < 147425) {
			level = 16;
			progress = (xp - 97425) / 50000;
		} else if (xp < 222425) {
			level = 17;
			progress = (xp - 147425) / 75000;
		} else if (xp < 322425) {
			level = 18;
			progress = (xp - 222425) / 100000;
		} else if (xp < 522425) {
			level = 19;
			progress = (xp - 322425) / 200000;
		} else if (xp < 822425) {
			level = 20;
			progress = (xp - 522425) / 300000;
		} else if (xp < 1222425) {
			level = 21;
			progress = (xp - 822425) / 400000;
		} else if (xp < 1722425) {
			level = 22;
			progress = (xp - 1222425) / 500000;
		} else if (xp < 2322425) {
			level = 23;
			progress = (xp - 1722425) / 600000;
		} else if (xp < 3022425) {
			level = 24;
			progress = (xp - 2322425) / 700000;
		} else if (xp < 3822425) {
			level = 25;
			progress = (xp - 3022425) / 800000;
		} else if (xp < 4722425) {
			level = 26;
			progress = (xp - 3822425) / 900000;
		} else if (xp < 5722425) {
			level = 27;
			progress = (xp - 4722425) / 1000000;
		} else if (xp < 6822425) {
			level = 28;
			progress = (xp - 5722425) / 1100000;
		} else if (xp < 8022425) {
			level = 29;
			progress = (xp - 6822425) / 1200000;
		} else if (xp < 9322425) {
			level = 30;
			progress = (xp - 8022425) / 1300000;
		} else if (xp < 10722425) {
			level = 31;
			progress = (xp - 9322425) / 1400000;
		} else if (xp < 12222425) {
			level = 32;
			progress = (xp - 10722425) / 1500000;
		} else if (xp < 13822425) {
			level = 33;
			progress = (xp - 12222425) / 1600000;
		} else if (xp < 15522425) {
			level = 34;
			progress = (xp - 13822425) / 1700000;
		} else if (xp < 17322425) {
			level = 35;
			progress = (xp - 15522425) / 1800000;
		} else if (xp < 19222425) {
			level = 36;
			progress = (xp - 17322425) / 1900000;
		} else if (xp < 21222425) {
			level = 37;
			progress = (xp - 19222425) / 2000000;
		} else if (xp < 23322425) {
			level = 38;
			progress = (xp - 21222425) / 2100000;
		} else if (xp < 25522425) {
			level = 39;
			progress = (xp - 23322425) / 2200000;
		} else if (xp < 27822425) {
			level = 40;
			progress = (xp - 25522425) / 2300000;
		} else if (xp < 30222425) {
			level = 41;
			progress = (xp - 27822425) / 2400000;
		} else if (xp < 32722425) {
			level = 42;
			progress = (xp - 30222425) / 2500000;
		} else if (xp < 35322425) {
			level = 43;
			progress = (xp - 32722425) / 2600000;
		} else if (xp < 38072425) {
			level = 44;
			progress = (xp - 35322425) / 2750000;
		} else if (xp < 40972425) {
			level = 45;
			progress = (xp - 38072425) / 2900000;
		} else if (xp < 44072425) {
			level = 46;
			progress = (xp - 40972425) / 3100000;
		} else if (xp < 47472425) {
			level = 47;
			progress = (xp - 44072425) / 3400000;
		} else if (xp < 51172425) {
			level = 48;
			progress = (xp - 47472425) / 3700000;
		} else if (xp < 55172425) {
			level = 49;
			progress = (xp - 51172425) / 4000000;
		} else {
			level = 50;
			progress = 0;
		}
		
		return level + progress;
	}
	
	@Override
	public String getCommandName() {
		return "skills";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName() + " <name>";
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
			ConfigHandler cf = new ConfigHandler();
			EntityPlayer player = (EntityPlayer) arg0;
			
			// Check key
			String key = cf.getString("api", "APIKey");
			if (key.equals("")) {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "API key not set. Use /setkey."));
			}
			
			// Get UUID for Hypixel API requests
			String username;
			String uuid;
			if (arg1.length == 0) {
				username = player.getName();
				uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Checking skills of " + EnumChatFormatting.DARK_GREEN + username));
			} else {
				username = arg1[0];
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Checking skills of " + EnumChatFormatting.DARK_GREEN + username));
				uuid = ah.getUUID(username);
			}
			
			// Find stats of latest profile
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
			
			System.out.println("Fetching skills...");
			JsonObject userObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject();
			
			double farmingLevel = 0;
			double miningLevel = 0;
			double combatLevel = 0;
			double foragingLevel = 0;
			double fishingLevel = 0;
			double enchantingLevel = 0;
			double alchemyLevel = 0;
			double tamingLevel = 0;
			
			if (userObject.has("experience_skill_farming") || userObject.has("experience_skill_mining") || userObject.has("experience_skill_combat") || userObject.has("experience_skill_foraging") || userObject.has("experience_skill_fishing") || userObject.has("experience_skill_enchanting") || userObject.has("experience_skill_alchemy")) {
				if (userObject.has("experience_skill_farming")) {
					farmingLevel = xpToLevel(userObject.get("experience_skill_farming").getAsDouble());
					farmingLevel = (double) Math.round(farmingLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_mining")) {
					miningLevel = xpToLevel(userObject.get("experience_skill_mining").getAsDouble());
					miningLevel = (double) Math.round(miningLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_combat")) {
					combatLevel = xpToLevel(userObject.get("experience_skill_combat").getAsDouble());
					combatLevel = (double) Math.round(combatLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_foraging")) {
					foragingLevel = xpToLevel(userObject.get("experience_skill_foraging").getAsDouble());
					foragingLevel = (double) Math.round(foragingLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_fishing")) {
					fishingLevel = xpToLevel(userObject.get("experience_skill_fishing").getAsDouble());
					fishingLevel = (double) Math.round(fishingLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_enchanting")) {
					enchantingLevel = xpToLevel(userObject.get("experience_skill_enchanting").getAsDouble());
					enchantingLevel = (double) Math.round(enchantingLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_alchemy")) {
					alchemyLevel = xpToLevel(userObject.get("experience_skill_alchemy").getAsDouble());
					alchemyLevel = (double) Math.round(alchemyLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_taming")) {
					tamingLevel = xpToLevel(userObject.get("experience_skill_taming").getAsDouble());
					tamingLevel = (double) Math.round(tamingLevel * 100) / 100;
				}
			} else {
				// Get skills from achievement API, will be floored
				
				String playerURL = "https://api.hypixel.net/player?uuid=" + uuid + "&key=" + key;
				System.out.println("Fetching skills from achievement API");
				JsonObject playerObject = ah.getResponse(playerURL);
				
				if (!playerObject.get("success").getAsBoolean()) {
					String reason = profileResponse.get("cause").getAsString();
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed with reason: " + reason));
					return;
				}
				
				JsonObject achievementObject = playerObject.get("player").getAsJsonObject().get("achievements").getAsJsonObject();
				farmingLevel = achievementObject.get("skyblock_harvester").getAsInt();
				miningLevel = achievementObject.get("skyblock_excavator").getAsInt();
				combatLevel = achievementObject.get("skyblock_combat").getAsInt();
				foragingLevel = achievementObject.get("skyblock_gatherer").getAsInt();
				fishingLevel = achievementObject.get("skyblock_angler").getAsInt();
				enchantingLevel = achievementObject.get("skyblock_augmentation").getAsInt();
				alchemyLevel = achievementObject.get("skyblock_concoctor").getAsInt();
				tamingLevel = achievementObject.get("skyblock_domesticator").getAsInt();
			}

			double skillAvg = (farmingLevel + miningLevel + combatLevel + foragingLevel + fishingLevel + enchantingLevel + alchemyLevel + tamingLevel) / 8;
			skillAvg = (double) Math.round(skillAvg * 100) / 100;
			double trueAvg = (Math.floor(farmingLevel) + Math.floor(miningLevel) + Math.floor(combatLevel) + Math.floor(foragingLevel) + Math.floor(fishingLevel) + Math.floor(enchantingLevel) + Math.floor(alchemyLevel) + Math.floor(tamingLevel)) / 8;
			
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
														EnumChatFormatting.AQUA + " " + username + "'s Skills:\n" +
														EnumChatFormatting.GREEN + " Farming: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + farmingLevel + "\n" +
														EnumChatFormatting.GREEN + " Mining: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + miningLevel + "\n" +
														EnumChatFormatting.GREEN + " Combat: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + combatLevel + "\n" +
														EnumChatFormatting.GREEN + " Foraging: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + foragingLevel + "\n" +
														EnumChatFormatting.GREEN + " Fishing: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + fishingLevel + "\n" +
														EnumChatFormatting.GREEN + " Enchanting: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + enchantingLevel + "\n" +
														EnumChatFormatting.GREEN + " Alchemy: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + alchemyLevel + "\n" +
														EnumChatFormatting.GREEN + " Taming: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + tamingLevel + "\n" +
														EnumChatFormatting.AQUA + " Average Skill Level: " + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + skillAvg + "\n" +
														EnumChatFormatting.AQUA + " True Average Skill Level: " + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + trueAvg + "\n" +
														EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------"));
		}).start();
	}

}
