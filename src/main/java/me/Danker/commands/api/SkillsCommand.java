package me.Danker.commands.api;

import com.google.gson.JsonObject;
import me.Danker.config.ModConfig;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.HypixelAPIHandler;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SkillsCommand extends CommandBase {

	static NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);

	@Override
	public String getCommandName() {
		return "skill";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " [name]";
	}

	public static String usage(ICommandSender arg0) {
		return new SkillsCommand().getCommandUsage(arg0);
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
			EntityPlayer player = (EntityPlayer) arg0;
			
			// Get UUID for Hypixel API requests
			String username;
			String uuid;
			if (arg1.length == 0) {
				username = player.getName();
				uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
			} else {
				username = arg1[0];
				uuid = APIHandler.getUUID(username);
			}
			player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking skills of " + ModConfig.getColour(ModConfig.secondaryColour) + username + ModConfig.getColour(ModConfig.mainColour) + " using Polyfrost's API."));
			
			// Find stats of latest profile
			JsonObject profileResponse = HypixelAPIHandler.getLatestProfile(uuid);
			if (profileResponse == null) return;
			
			System.out.println("Fetching skills...");
			JsonObject experienceObj = Utils.getObjectFromPath(profileResponse, "members." + uuid + ".player_data.experience");

			ChatComponentText farmingLevelText = new ChatComponentText(ModConfig.getColour(ModConfig.typeColour) + " Farming: ");
			ChatComponentText miningLevelText = new ChatComponentText(ModConfig.getColour(ModConfig.typeColour) + " Mining: ");
			ChatComponentText combatLevelText = new ChatComponentText(ModConfig.getColour(ModConfig.typeColour) + " Combat: ");
			ChatComponentText foragingLevelText = new ChatComponentText(ModConfig.getColour(ModConfig.typeColour) + " Foraging: ");
			ChatComponentText fishingLevelText = new ChatComponentText(ModConfig.getColour(ModConfig.typeColour) + " Fishing: ");
			ChatComponentText enchantingLevelText = new ChatComponentText(ModConfig.getColour(ModConfig.typeColour) + " Enchanting: ");
			ChatComponentText alchemyLevelText = new ChatComponentText(ModConfig.getColour(ModConfig.typeColour) + " Alchemy: ");
			ChatComponentText tamingLevelText = new ChatComponentText(ModConfig.getColour(ModConfig.typeColour) + " Taming: ");
			ChatComponentText carpentryLevelText = new ChatComponentText(ModConfig.getColour(ModConfig.typeColour) + " Carpentry: ");
			ChatComponentText newLine = new ChatComponentText("\n");

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
					double rawFarmingXP = experienceObj.get("SKILL_FARMING").getAsDouble();
					farmingLevel = Utils.xpToSkillLevel(rawFarmingXP, 60);
					farmingLevel = (double) Math.round(farmingLevel * 100) / 100;
					farmingLevelText.setChatStyle(appendFormatted(farmingLevelText, "XP", rawFarmingXP));
					farmingLevelText.setChatStyle(appendFormatted(farmingLevelText, "Overflow XP", getOverflowXP(rawFarmingXP, 60)));
				}
				if (experienceObj.has("SKILL_MINING")) {
					double rawMiningXP = experienceObj.get("SKILL_MINING").getAsDouble();
					miningLevel = Utils.xpToSkillLevel(rawMiningXP, 60);
					miningLevel = (double) Math.round(miningLevel * 100) / 100;
					miningLevelText.setChatStyle(appendFormatted(miningLevelText, "XP", rawMiningXP));
					miningLevelText.setChatStyle(appendFormatted(miningLevelText, "Overflow XP", getOverflowXP(rawMiningXP, 60)));
				}
				if (experienceObj.has("SKILL_COMBAT")) {
					double rawCombatXP = experienceObj.get("SKILL_COMBAT").getAsDouble();
					combatLevel = Utils.xpToSkillLevel(rawCombatXP, 60);
					combatLevel = (double) Math.round(combatLevel * 100) / 100;
					combatLevelText.setChatStyle(appendFormatted(combatLevelText, "XP", rawCombatXP));
					combatLevelText.setChatStyle(appendFormatted(combatLevelText, "Overflow XP", getOverflowXP(rawCombatXP, 60)));
				}
				if (experienceObj.has("SKILL_FORAGING")) {
					double rawForagingXP = experienceObj.get("SKILL_FORAGING").getAsDouble();
					foragingLevel = Utils.xpToSkillLevel(rawForagingXP, 50);
					foragingLevel = (double) Math.round(foragingLevel * 100) / 100;
					foragingLevelText.setChatStyle(appendFormatted(foragingLevelText, "XP", rawForagingXP));
					foragingLevelText.setChatStyle(appendFormatted(foragingLevelText, "Overflow XP", getOverflowXP(rawForagingXP, 50)));
				}
				if (experienceObj.has("SKILL_FISHING")) {
					double rawFishingXP = experienceObj.get("SKILL_FISHING").getAsDouble();
					fishingLevel = Utils.xpToSkillLevel(rawFishingXP, 50);
					fishingLevel = (double) Math.round(fishingLevel * 100) / 100;
					fishingLevelText.setChatStyle(appendFormatted(fishingLevelText, "XP", rawFishingXP));
					fishingLevelText.setChatStyle(appendFormatted(fishingLevelText, "Overflow XP", getOverflowXP(rawFishingXP, 50)));
				}
				if (experienceObj.has("SKILL_ENCHANTING")) {
					double rawEnchantingXP = experienceObj.get("SKILL_ENCHANTING").getAsDouble();
					enchantingLevel = Utils.xpToSkillLevel(rawEnchantingXP, 60);
					enchantingLevel = (double) Math.round(enchantingLevel * 100) / 100;
					enchantingLevelText.setChatStyle(appendFormatted(enchantingLevelText, "XP", rawEnchantingXP));
					enchantingLevelText.setChatStyle(appendFormatted(enchantingLevelText, "Overflow XP", getOverflowXP(rawEnchantingXP, 60)));
				}
				if (experienceObj.has("SKILL_ALCHEMY")) {
					double rawAlchemyXP = experienceObj.get("SKILL_ALCHEMY").getAsDouble();
					alchemyLevel = Utils.xpToSkillLevel(rawAlchemyXP, 50);
					alchemyLevel = (double) Math.round(alchemyLevel * 100) / 100;
					alchemyLevelText.setChatStyle(appendFormatted(alchemyLevelText, "XP", rawAlchemyXP));
					alchemyLevelText.setChatStyle(appendFormatted(alchemyLevelText, "Overflow XP", getOverflowXP(rawAlchemyXP, 50)));
				}
				if (experienceObj.has("SKILL_TAMING")) {
					double rawTamingXP = experienceObj.get("SKILL_TAMING").getAsDouble();
					tamingLevel = Utils.xpToSkillLevel(rawTamingXP, 50);
					tamingLevel = (double) Math.round(tamingLevel * 100) / 100;
					tamingLevelText.setChatStyle(appendFormatted(tamingLevelText, "XP", rawTamingXP));
					tamingLevelText.setChatStyle(appendFormatted(tamingLevelText, "Overflow XP", getOverflowXP(rawTamingXP, 50)));
				}
				if (experienceObj.has("SKILL_CARPENTRY")) {
					double rawCarpentryXP = experienceObj.get("SKILL_CARPENTRY").getAsDouble();
					carpentryLevel = Utils.xpToSkillLevel(rawCarpentryXP, 50);
					carpentryLevel = (double) Math.round(carpentryLevel * 100) / 100;
					carpentryLevelText.setChatStyle(appendFormatted(carpentryLevelText, "XP", rawCarpentryXP));
					carpentryLevelText.setChatStyle(appendFormatted(carpentryLevelText, "Overflow XP", getOverflowXP(rawCarpentryXP, 50)));
				}
			} else {
				// Get skills from achievement API, will be floored

				System.out.println("Fetching skills from achievement API");
				JsonObject playerObject = HypixelAPIHandler.getJsonObjectAuth(HypixelAPIHandler.URL + "player/" + uuid);

				if (playerObject == null) {
					player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Could not connect to API."));
					return;
				}
				if (!playerObject.get("success").getAsBoolean()) {
					String reason = profileResponse.get("cause").getAsString();
					player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Failed with reason: " + reason));
					return;
				}

				JsonObject achievementObject = Utils.getObjectFromPath(playerObject, "player.achievements");
				if (achievementObject.has("skyblock_harvester")) {
					farmingLevel = achievementObject.get("skyblock_harvester").getAsInt();
					farmingLevelText.setChatStyle(appendFormatted(farmingLevelText, "XP", Utils.skillLevelToXp((int) farmingLevel)));
				}
				if (achievementObject.has("skyblock_excavator")) {
					miningLevel = achievementObject.get("skyblock_excavator").getAsInt();
					miningLevelText.setChatStyle(appendFormatted(miningLevelText, "XP", Utils.skillLevelToXp((int) miningLevel)));
				}
				if (achievementObject.has("skyblock_combat")) {
					combatLevel = achievementObject.get("skyblock_combat").getAsInt();
					combatLevelText.setChatStyle(appendFormatted(combatLevelText, "XP", Utils.skillLevelToXp((int) combatLevel)));
				}
				if (achievementObject.has("skyblock_gatherer")) {
					foragingLevel = Math.min(achievementObject.get("skyblock_gatherer").getAsInt(), 50);
					foragingLevelText.setChatStyle(appendFormatted(foragingLevelText, "XP", Utils.skillLevelToXp((int) foragingLevel)));
				}
				if (achievementObject.has("skyblock_angler")) {
					fishingLevel = Math.min(achievementObject.get("skyblock_angler").getAsInt(), 50);
					fishingLevelText.setChatStyle(appendFormatted(fishingLevelText, "XP", Utils.skillLevelToXp((int) fishingLevel)));
				}
				if (achievementObject.has("skyblock_augmentation")) {
					enchantingLevel = achievementObject.get("skyblock_augmentation").getAsInt();
					enchantingLevelText.setChatStyle(appendFormatted(enchantingLevelText, "XP", Utils.skillLevelToXp((int) enchantingLevel)));
				}
				if (achievementObject.has("skyblock_concoctor")) {
					alchemyLevel = Math.min(achievementObject.get("skyblock_concoctor").getAsInt(), 50);
					alchemyLevelText.setChatStyle(appendFormatted(alchemyLevelText, "XP", Utils.skillLevelToXp((int) alchemyLevel)));
				}
				if (achievementObject.has("skyblock_domesticator")) {
					tamingLevel = Math.min(achievementObject.get("skyblock_domesticator").getAsInt(), 50);
					tamingLevelText.setChatStyle(appendFormatted(tamingLevelText, "XP", Utils.skillLevelToXp((int) tamingLevel)));
				}
			}

			farmingLevelText.appendSibling(new ChatComponentText(ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + farmingLevel));
			miningLevelText.appendSibling(new ChatComponentText(ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + miningLevel));
			combatLevelText.appendSibling(new ChatComponentText(ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + combatLevel));
			foragingLevelText.appendSibling(new ChatComponentText(ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + foragingLevel));
			fishingLevelText.appendSibling(new ChatComponentText(ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + fishingLevel));
			enchantingLevelText.appendSibling(new ChatComponentText(ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + enchantingLevel));
			alchemyLevelText.appendSibling(new ChatComponentText(ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + alchemyLevel));
			tamingLevelText.appendSibling(new ChatComponentText(ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + tamingLevel));
			carpentryLevelText.appendSibling(new ChatComponentText(ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + carpentryLevel));

			double skillAvg = (farmingLevel + miningLevel + combatLevel + foragingLevel + fishingLevel + enchantingLevel + alchemyLevel + tamingLevel + carpentryLevel) / 9;
			skillAvg = (double) Math.round(skillAvg * 100) / 100;
			double trueAvg = (Math.floor(farmingLevel) + Math.floor(miningLevel) + Math.floor(combatLevel) + Math.floor(foragingLevel) + Math.floor(fishingLevel) + Math.floor(enchantingLevel) + Math.floor(alchemyLevel) + Math.floor(tamingLevel) + Math.floor(carpentryLevel)) / 9;
			trueAvg = (double) Math.round(trueAvg * 100) / 100;

			player.addChatMessage(new ChatComponentText(ModConfig.getDelimiter() + "\n" +
														EnumChatFormatting.AQUA + " " + username + "'s Skills:\n")
														.appendSibling(farmingLevelText).appendSibling(newLine)
														.appendSibling(miningLevelText).appendSibling(newLine)
														.appendSibling(combatLevelText).appendSibling(newLine)
														.appendSibling(foragingLevelText).appendSibling(newLine)
														.appendSibling(fishingLevelText).appendSibling(newLine)
														.appendSibling(enchantingLevelText).appendSibling(newLine)
														.appendSibling(alchemyLevelText).appendSibling(newLine)
														.appendSibling(tamingLevelText).appendSibling(newLine)
														.appendSibling(carpentryLevelText).appendSibling(newLine)
														.appendSibling(new ChatComponentText(
														EnumChatFormatting.AQUA + " Average Skill Level: " + ModConfig.getColour(ModConfig.skillAverageColour) + EnumChatFormatting.BOLD + skillAvg + "\n" +
														EnumChatFormatting.AQUA + " True Average Skill Level: " + ModConfig.getColour(ModConfig.skillAverageColour) + EnumChatFormatting.BOLD + trueAvg + "\n" +
														ModConfig.getDelimiter())));
		}).start();
	}

	static ChatStyle appendHover(ChatComponentText component, String text) {
		String original = "";
		if (component.getChatStyle().getChatHoverEvent() != null) original = component.getChatStyle().getChatHoverEvent().getValue().getFormattedText();
		if (original.length() > 0) original += "\n";
		return component.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(original + text)));
	}

	static ChatStyle appendFormatted(ChatComponentText component, String category, double number) {
		return appendHover(component, ModConfig.getColour(ModConfig.typeColour) + category + ": " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + nf.format(number));
	}

	static double getOverflowXP(double xp, int limit) {
		if (limit == 50) return Math.max(0D, xp - 55172425D);
		return Math.max(0D, xp - 111672425D);
	}

}
