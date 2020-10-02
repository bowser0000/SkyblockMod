package me.Danker.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class LobbySkillsCommand extends CommandBase {
	
	Thread mainThread = null;
	
	@Override
	public String getCommandName() {
		return "lobbyskills";
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
		EntityPlayer playerSP = (EntityPlayer) arg0;
		ConfigHandler cf = new ConfigHandler();
		APIHandler ah = new APIHandler();
		boolean someErrored = false;
		Map<String, Double> unsortedSAList = new HashMap<String, Double>();
		ArrayList<Double> lobbySkills = new ArrayList<Double>();
		// Check key
		String key = cf.getString("api", "APIKey");
		if (key.equals("")) {
			playerSP.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "API key not set. Use /setkey."));
			return;
		}

		mainThread = new Thread(() -> {
			try {
				// Create deep copy of players to prevent passing reference and ConcurrentModificationException
				Collection<NetworkPlayerInfo> players = new ArrayList<NetworkPlayerInfo>(Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap());
				playerSP.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Checking skill average of lobby. Estimated time: " + (int) (players.size() * 1.2 + 1) + " seconds."));
				// Send request every .6 seconds, leaving room for another 20 requests per minute
				
				for (final NetworkPlayerInfo player : players) {
					// Manually get latest profile to use reduced requests on extra achievement API
					System.out.println("Now parsing " + player.getGameProfile().getName());
					String UUID = player.getGameProfile().getId().toString().replaceAll("-", "");
					long biggestLastSave = 0;
					int profileIndex = -1;
					Thread.sleep(600);
					JsonObject profileResponse = ah.getResponse("https://api.hypixel.net/skyblock/profiles?uuid=" + UUID + "&key=" + key);
					if (!profileResponse.get("success").getAsBoolean()) {
						String reason = profileResponse.get("cause").getAsString();
						System.out.println("User " + player.getGameProfile().getName() + " failed with reason: " + reason);
						continue;
					}
					if (profileResponse.get("profiles").isJsonNull()) continue;
					
					JsonArray profiles = profileResponse.get("profiles").getAsJsonArray();
					for (int i = 0; i < profiles.size(); i++) {
						JsonObject profile = profiles.get(i).getAsJsonObject();
						if (!profile.get("members").getAsJsonObject().get(UUID).getAsJsonObject().has("last_save")) continue;
						if (profile.get("members").getAsJsonObject().get(UUID).getAsJsonObject().get("last_save").getAsLong() > biggestLastSave) {
							biggestLastSave = profile.get("members").getAsJsonObject().get(UUID).getAsJsonObject().get("last_save").getAsLong();
							profileIndex = i;
						}
					}
					if (profileIndex == -1 || biggestLastSave == 0) continue;
					JsonObject latestProfile = profiles.get(profileIndex).getAsJsonObject().get("members").getAsJsonObject().get(UUID).getAsJsonObject();
					
					// Get SA
					double farmingLevel = 0;
					double miningLevel = 0;
					double combatLevel = 0;
					double foragingLevel = 0;
					double fishingLevel = 0;
					double enchantingLevel = 0;
					double alchemyLevel = 0;
					double tamingLevel = 0;
					
					if (latestProfile.has("experience_skill_farming") || latestProfile.has("experience_skill_mining") || latestProfile.has("experience_skill_combat") || latestProfile.has("experience_skill_foraging") || latestProfile.has("experience_skill_fishing") || latestProfile.has("experience_skill_enchanting") || latestProfile.has("experience_skill_alchemy")) {
						if (latestProfile.has("experience_skill_farming")) {
							farmingLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_farming").getAsDouble());
							farmingLevel = (double) Math.round(farmingLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_mining")) {
							miningLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_mining").getAsDouble());
							miningLevel = (double) Math.round(miningLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_combat")) {
							combatLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_combat").getAsDouble());
							combatLevel = (double) Math.round(combatLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_foraging")) {
							foragingLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_foraging").getAsDouble());
							foragingLevel = (double) Math.round(foragingLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_fishing")) {
							fishingLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_fishing").getAsDouble());
							fishingLevel = (double) Math.round(fishingLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_enchanting")) {
							enchantingLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_enchanting").getAsDouble());
							enchantingLevel = (double) Math.round(enchantingLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_alchemy")) {
							alchemyLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_alchemy").getAsDouble());
							alchemyLevel = (double) Math.round(alchemyLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_taming")) {
							tamingLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_taming").getAsDouble());
							tamingLevel = (double) Math.round(tamingLevel * 100) / 100;
						}
					} else {
						Thread.sleep(600); // Sleep for another request
						System.out.println("Fetching skills from achievement API");
						JsonObject playerObject = ah.getResponse("https://api.hypixel.net/player?uuid=" + UUID + "&key=" + key);
						
						if (!playerObject.get("success").getAsBoolean()) {
							String reason = profileResponse.get("cause").getAsString();
							System.out.println("User " + player.getGameProfile().getName() + " failed with reason: " + reason);
							continue;
						}
						
						JsonObject achievementObject = playerObject.get("player").getAsJsonObject().get("achievements").getAsJsonObject();
						if (achievementObject.has("skyblock_harvester")) {
							farmingLevel = achievementObject.get("skyblock_harvester").getAsInt();
						}
						if (achievementObject.has("skyblock_excavator")) {
							miningLevel = achievementObject.get("skyblock_excavator").getAsInt();
						}
						if (achievementObject.has("skyblock_combat")) {
							combatLevel = achievementObject.get("skyblock_combat").getAsInt();
						}
						if (achievementObject.has("skyblock_gatherer")) {
							foragingLevel = achievementObject.get("skyblock_gatherer").getAsInt();
						}
						if (achievementObject.has("skyblock_angler")) {
							fishingLevel = achievementObject.get("skyblock_angler").getAsInt();
						}
						if (achievementObject.has("skyblock_augmentation")) {
							enchantingLevel = achievementObject.get("skyblock_augmentation").getAsInt();
						}
						if (achievementObject.has("skyblock_concoctor")) {
							alchemyLevel = achievementObject.get("skyblock_concoctor").getAsInt();
						}
						if (achievementObject.has("skyblock_domesticator")) {
							tamingLevel = achievementObject.get("skyblock_domesticator").getAsInt();
						}
					}
					
					double skillAvg = (farmingLevel + miningLevel + combatLevel + foragingLevel + fishingLevel + enchantingLevel + alchemyLevel + tamingLevel) / 8;
					skillAvg = (double) Math.round(skillAvg * 100) / 100;
					unsortedSAList.put(player.getGameProfile().getName(), skillAvg); // Put SA in HashMap
					lobbySkills.add(skillAvg); // Add SA to lobby skills
				}
				
				// I have no idea how this works, or even what :: does but this sorts the skill averages
				Map<String, Double> sortedSAList = unsortedSAList.entrySet().stream()
												   .sorted(Entry.<String, Double>comparingByValue().reversed())
												   .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
														   (e1, e2) -> e1, LinkedHashMap::new));
				
				String[] sortedSAListKeys = sortedSAList.keySet().toArray(new String[sortedSAList.keySet().size()]);
				String top3 = "";
				for (int i = 0; i < 3 && i < sortedSAListKeys.length; i++) {
					top3 += "\n " + EnumChatFormatting.AQUA + sortedSAListKeys[i] + ": " + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + sortedSAList.get(sortedSAListKeys[i]);
				}
				
				// Get lobby sa
				double lobbySA = 0;
				for (int i = 0; i < lobbySkills.size(); i++) {
					lobbySA += lobbySkills.get(i);
				}
				lobbySA = (double) Math.round((lobbySA / lobbySkills.size()) * 100) / 100;
				
				// Finally say skill lobby avg and highest SA users
				playerSP.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															  EnumChatFormatting.GREEN + " Lobby Skill Average: " + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + lobbySA + "\n" +
															  EnumChatFormatting.GREEN + " Highest Skill Averages:" + top3 + "\n" +
															  EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + " -------------------"));
			} catch (InterruptedException ex) {
				System.out.println("Current skill average list: " + unsortedSAList.toString());
				Thread.currentThread().interrupt();
				System.out.println("Interrupted /lobbyskills thread.");
			}

		});
		mainThread.start();
	}

}
