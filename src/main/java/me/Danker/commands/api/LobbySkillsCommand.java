package me.Danker.commands.api;

import com.google.gson.JsonObject;
import me.Danker.config.ModConfig;
import me.Danker.handlers.HypixelAPIHandler;
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
	
	public static Thread mainThread = null;
	
	@Override
	public String getCommandName() {
		return "lobbyskills";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName();
	}

	public static String usage(ICommandSender arg0) {
		return new LobbySkillsCommand().getCommandUsage(arg0);
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

		mainThread = new Thread(() -> {
			try {
				// Create deep copy of players to prevent passing reference and ConcurrentModificationException
				Collection<NetworkPlayerInfo> players = new ArrayList<>(Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap());
				playerSP.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking skill average of lobby using Polyfrost's API. Estimated time: " + (int) (Utils.getMatchingPlayers("").size() * 1.2 + 1) + " seconds."));
				// Send request every .6 seconds, leaving room for another 20 requests per minute
				
				for (NetworkPlayerInfo player : players) {
					if (player.getGameProfile().getName().startsWith("!")) continue;

					Thread.sleep(600);

					String UUID = player.getGameProfile().getId().toString().replaceAll("-", "");
					JsonObject profileResponse = HypixelAPIHandler.getLatestProfile(UUID);
					if (profileResponse == null) continue;

					JsonObject experienceObj = Utils.getObjectFromPath(profileResponse, "members." + UUID + ".player_data.experience");
					
					// Get SA
					double farmingLevel = 0;
					double miningLevel = 0;
					double combatLevel = 0;
					double foragingLevel = 0;
					double fishingLevel = 0;
					double enchantingLevel = 0;
					double alchemyLevel = 0;
					double tamingLevel = 0;
					double carpentryLevel = 0;
					
					if (experienceObj.has("SKILL_FARMING") || experienceObj.has("SKILL_MINING") || experienceObj.has("SKILL_COMBAT") || experienceObj.has("SKILL_FORAGING") || experienceObj.has("SKILL_FISHING") || experienceObj.has("SKILL_ENCHANTING") || experienceObj.has("SKILL_ALCHEMY")) {
						if (experienceObj.has("SKILL_FARMING")) {
							farmingLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_FARMING").getAsDouble(), 60);
							farmingLevel = (double) Math.round(farmingLevel * 100) / 100;
						}
						if (experienceObj.has("SKILL_MINING")) {
							miningLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_MINING").getAsDouble(), 60);
							miningLevel = (double) Math.round(miningLevel * 100) / 100;
						}
						if (experienceObj.has("SKILL_COMBAT")) {
							combatLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_COMBAT").getAsDouble(), 60);
							combatLevel = (double) Math.round(combatLevel * 100) / 100;
						}
						if (experienceObj.has("SKILL_FORAGING")) {
							foragingLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_FORAGING").getAsDouble(), 50);
							foragingLevel = (double) Math.round(foragingLevel * 100) / 100;
						}
						if (experienceObj.has("SKILL_FISHING")) {
							fishingLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_FISHING").getAsDouble(), 50);
							fishingLevel = (double) Math.round(fishingLevel * 100) / 100;
						}
						if (experienceObj.has("SKILL_ENCHANTING")) {
							enchantingLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_ENCHANTING").getAsDouble(), 60);
							enchantingLevel = (double) Math.round(enchantingLevel * 100) / 100;
						}
						if (experienceObj.has("SKILL_ALCHEMY")) {
							alchemyLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_ALCHEMY").getAsDouble(), 50);
							alchemyLevel = (double) Math.round(alchemyLevel * 100) / 100;
						}
						if (experienceObj.has("SKILL_TAMING")) {
							tamingLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_TAMING").getAsDouble(), 50);
							tamingLevel = (double) Math.round(tamingLevel * 100) / 100;
						}
						if (experienceObj.has("SKILL_CARPENTRY")) {
							carpentryLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_CARPENTRY").getAsDouble(), 50);
							carpentryLevel = (double) Math.round(carpentryLevel * 100) / 100;
						}
					} else {
						Thread.sleep(600); // Sleep for another request
						System.out.println("Fetching skills from achievement API");
						JsonObject playerObject = HypixelAPIHandler.getJsonObjectAuth(HypixelAPIHandler.URL + "player/" + UUID);

						if (playerObject == null) {
							System.out.println("Connection failed for user " + player.getGameProfile().getName());
							return;
						}
						if (!playerObject.get("success").getAsBoolean()) {
							String reason = profileResponse.get("cause").getAsString();
							System.out.println("User " + player.getGameProfile().getName() + " failed with reason: " + reason);
							continue;
						}
						
						JsonObject achievementObject = Utils.getObjectFromPath(playerObject, "player.achievements");
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
					
					double skillAvg = (farmingLevel + miningLevel + combatLevel + foragingLevel + fishingLevel + enchantingLevel + alchemyLevel + tamingLevel + carpentryLevel) / 9;
					skillAvg = (double) Math.round(skillAvg * 100) / 100;
					unsortedSAList.put(player.getGameProfile().getName(), skillAvg); // Put SA in HashMap
					lobbySkills.add(skillAvg); // Add SA to lobby skills
				}
				
				// Sort skill average
				Map<String, Double> sortedSAList = unsortedSAList.entrySet().stream()
												   .sorted(Entry.<String, Double>comparingByValue().reversed())
												   .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
														   (e1, e2) -> e1, LinkedHashMap::new));
				
				String[] sortedSAListKeys = sortedSAList.keySet().toArray(new String[0]);
				String top3 = "";
				for (int i = 0; i < 3 && i < sortedSAListKeys.length; i++) {
					top3 += "\n " + EnumChatFormatting.AQUA + sortedSAListKeys[i] + ": " + ModConfig.getColour(ModConfig.skillAverageColour) + EnumChatFormatting.BOLD + sortedSAList.get(sortedSAListKeys[i]);
				}
				
				// Get lobby sa
				double lobbySA = 0;
				for (Double playerSkills : lobbySkills) {
					lobbySA += playerSkills;
				}
				lobbySA = (double) Math.round((lobbySA / lobbySkills.size()) * 100) / 100;
				
				// Finally say skill lobby avg and highest SA users
				playerSP.addChatMessage(new ChatComponentText(ModConfig.getDelimiter() + "\n" +
															  ModConfig.getColour(ModConfig.typeColour) + " Lobby Skill Average: " + ModConfig.getColour(ModConfig.skillAverageColour) + EnumChatFormatting.BOLD + lobbySA + "\n" +
															  ModConfig.getColour(ModConfig.typeColour) + " Highest Skill Averages:" + top3 + "\n" +
															  ModConfig.getDelimiter()));
			} catch (InterruptedException ex) {
				System.out.println("Current skill average list: " + unsortedSAList);
				Thread.currentThread().interrupt();
				System.out.println("Interrupted /lobbyskills thread.");
			}

		});
		mainThread.start();
	}

}
