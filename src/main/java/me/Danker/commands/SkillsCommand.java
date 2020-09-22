package me.Danker.commands;

import java.util.List;

import com.google.gson.JsonObject;

import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class SkillsCommand extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "skills";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " [name]";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return Utils.getMatchingPlayers(args[0]);
		}
		return null;
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
					farmingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_farming").getAsDouble());
					farmingLevel = (double) Math.round(farmingLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_mining")) {
					miningLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_mining").getAsDouble());
					miningLevel = (double) Math.round(miningLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_combat")) {
					combatLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_combat").getAsDouble());
					combatLevel = (double) Math.round(combatLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_foraging")) {
					foragingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_foraging").getAsDouble());
					foragingLevel = (double) Math.round(foragingLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_fishing")) {
					fishingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_fishing").getAsDouble());
					fishingLevel = (double) Math.round(fishingLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_enchanting")) {
					enchantingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_enchanting").getAsDouble());
					enchantingLevel = (double) Math.round(enchantingLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_alchemy")) {
					alchemyLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_alchemy").getAsDouble());
					alchemyLevel = (double) Math.round(alchemyLevel * 100) / 100;
				}
				if (userObject.has("experience_skill_taming")) {
					tamingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_taming").getAsDouble());
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
														EnumChatFormatting.AQUA + " " + EnumChatFormatting.BOLD + "-------------------"));
		}).start();
	}

}
