package me.Danker.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
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

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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
		Map<String, Double> unsortedSAList = new HashMap<>();
		ArrayList<Double> lobbySkills = new ArrayList<>();
		// Check key
		String key = ConfigHandler.getString("api", "APIKey");
		if (key.equals("")) {
			playerSP.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "API key not set. Use /setkey."));
			return;
		}

		mainThread = new Thread(() -> {
			try {
				// Create deep copy of players to prevent passing reference and ConcurrentModificationException
				Collection<NetworkPlayerInfo> players = new ArrayList<>(Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap());
				playerSP.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking skill average of lobby. Estimated time: " + (int) (players.size() * 1.2 + 1) + " seconds."));
				// Send request every .6 seconds, leaving room for another 20 requests per minute
				
				for (final NetworkPlayerInfo player : players) {
					if (player.getGameProfile().getName().startsWith("!")) continue;
					// Manually get latest profile to use reduced requests on extra achievement API
					String UUID = player.getGameProfile().getId().toString().replaceAll("-", "");
					long biggestLastSave = 0;
					int profileIndex = -1;
					Thread.sleep(600);
					JsonObject profileResponse = APIHandler.getResponse("https://api.hypixel.net/skyblock/profiles?uuid=" + UUID + "&key=" + key);
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
							farmingLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_farming").getAsDouble(), 60);
							farmingLevel = (double) Math.round(farmingLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_mining")) {
							miningLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_mining").getAsDouble(), 50);
							miningLevel = (double) Math.round(miningLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_combat")) {
							combatLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_combat").getAsDouble(), 50);
							combatLevel = (double) Math.round(combatLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_foraging")) {
							foragingLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_foraging").getAsDouble(), 50);
							foragingLevel = (double) Math.round(foragingLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_fishing")) {
							fishingLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_fishing").getAsDouble(), 50);
							fishingLevel = (double) Math.round(fishingLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_enchanting")) {
							enchantingLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_enchanting").getAsDouble(), 60);
							enchantingLevel = (double) Math.round(enchantingLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_alchemy")) {
							alchemyLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_alchemy").getAsDouble(), 50);
							alchemyLevel = (double) Math.round(alchemyLevel * 100) / 100;
						}
						if (latestProfile.has("experience_skill_taming")) {
							tamingLevel = Utils.xpToSkillLevel(latestProfile.get("experience_skill_taming").getAsDouble(), 50);
							tamingLevel = (double) Math.round(tamingLevel * 100) / 100;
						}
					} else {
						Thread.sleep(600); // Sleep for another request
						System.out.println("Fetching skills from achievement API");
						JsonObject playerObject = APIHandler.getResponse("https://api.hypixel.net/player?uuid=" + UUID + "&key=" + key);
						
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
							miningLevel = Math.min(achievementObject.get("skyblock_excavator").getAsInt(), 50);
						}
						if (achievementObject.has("skyblock_combat")) {
							combatLevel = Math.min(achievementObject.get("skyblock_combat").getAsInt(), 50);
						}
						if (achievementObject.has("skyblock_gatherer")) {
							foragingLevel = Math.min(achievementObject.get("skyblock_gatherer").getAsInt(), 50);
						}
						if (achievementObject.has("skyblock_angler")) {
							fishingLevel = Math.min(achievementObject.get("skyblock_angler").getAsInt(), 50);
						}
						if (achievementObject.has("skyblock_augmentation")) {
							enchantingLevel = achievementObject.get("skyblock_augmentation").getAsInt();
						}
						if (achievementObject.has("skyblock_concoctor")) {
							alchemyLevel = Math.min(achievementObject.get("skyblock_concoctor").getAsInt(), 50);
						}
						if (achievementObject.has("skyblock_domesticator")) {
							tamingLevel = Math.min(achievementObject.get("skyblock_domesticator").getAsInt(), 50);
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
				
				String[] sortedSAListKeys = sortedSAList.keySet().toArray(new String[0]);
				String top3 = "";
				for (int i = 0; i < 3 && i < sortedSAListKeys.length; i++) {
					top3 += "\n " + EnumChatFormatting.AQUA + sortedSAListKeys[i] + ": " + DankersSkyblockMod.SKILL_AVERAGE_COLOUR + EnumChatFormatting.BOLD + sortedSAList.get(sortedSAListKeys[i]);
				}
				
				// Get lobby sa
				double lobbySA = 0;
				for (Double playerSkills : lobbySkills) {
					lobbySA += playerSkills;
				}
				lobbySA = (double) Math.round((lobbySA / lobbySkills.size()) * 100) / 100;
				
				// Finally say skill lobby avg and highest SA users
				playerSP.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															  DankersSkyblockMod.TYPE_COLOUR + " Lobby Skill Average: " + DankersSkyblockMod.SKILL_AVERAGE_COLOUR + EnumChatFormatting.BOLD + lobbySA + "\n" +
															  DankersSkyblockMod.TYPE_COLOUR + " Highest Skill Averages:" + top3 + "\n" +
															  DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + " -------------------"));
			} catch (InterruptedException ex) {
				System.out.println("Current skill average list: " + unsortedSAList.toString());
				Thread.currentThread().interrupt();
				System.out.println("Interrupted /lobbyskills thread.");
			}

		});
		mainThread.start();
	}

}
