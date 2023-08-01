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
			JsonObject userObject = profileResponse.get("members").getAsJsonObject().get(uuid).getAsJsonObject();

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

			if (userObject.has("experience_skill_farming") || userObject.has("experience_skill_mining") || userObject.has("experience_skill_combat") || userObject.has("experience_skill_foraging") || userObject.has("experience_skill_fishing") || userObject.has("experience_skill_enchanting") || userObject.has("experience_skill_alchemy")) {
				if (userObject.has("experience_skill_farming")) {
					farmingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_farming").getAsDouble(), 60);
					farmingLevel = (double) Math.round(farmingLevel * 100) / 100;
					farmingLevelText.setChatStyle(appendFormatted(farmingLevelText, "XP", userObject.get("experience_skill_farming").getAsDouble()));
					farmingLevelText.setChatStyle(appendFormatted(farmingLevelText, "Overflow XP", getOverflowXP(userObject.get("experience_skill_farming").getAsDouble(), 60)));
				}
				if (userObject.has("experience_skill_mining")) {
					miningLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_mining").getAsDouble(), 60);
					miningLevel = (double) Math.round(miningLevel * 100) / 100;
					miningLevelText.setChatStyle(appendFormatted(miningLevelText, "XP", userObject.get("experience_skill_mining").getAsDouble()));
					miningLevelText.setChatStyle(appendFormatted(miningLevelText, "Overflow XP", getOverflowXP(userObject.get("experience_skill_mining").getAsDouble(), 60)));
				}
				if (userObject.has("experience_skill_combat")) {
					combatLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_combat").getAsDouble(), 60);
					combatLevel = (double) Math.round(combatLevel * 100) / 100;
					combatLevelText.setChatStyle(appendFormatted(combatLevelText, "XP", userObject.get("experience_skill_combat").getAsDouble()));
					combatLevelText.setChatStyle(appendFormatted(combatLevelText, "Overflow XP", getOverflowXP(userObject.get("experience_skill_combat").getAsDouble(), 60)));
				}
				if (userObject.has("experience_skill_foraging")) {
					foragingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_foraging").getAsDouble(), 50);
					foragingLevel = (double) Math.round(foragingLevel * 100) / 100;
					foragingLevelText.setChatStyle(appendFormatted(foragingLevelText, "XP", userObject.get("experience_skill_foraging").getAsDouble()));
					foragingLevelText.setChatStyle(appendFormatted(foragingLevelText, "Overflow XP", getOverflowXP(userObject.get("experience_skill_foraging").getAsDouble(), 50)));
				}
				if (userObject.has("experience_skill_fishing")) {
					fishingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_fishing").getAsDouble(), 50);
					fishingLevel = (double) Math.round(fishingLevel * 100) / 100;
					fishingLevelText.setChatStyle(appendFormatted(fishingLevelText, "XP", userObject.get("experience_skill_fishing").getAsDouble()));
					fishingLevelText.setChatStyle(appendFormatted(fishingLevelText, "Overflow XP", getOverflowXP(userObject.get("experience_skill_fishing").getAsDouble(), 50)));
				}
				if (userObject.has("experience_skill_enchanting")) {
					enchantingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_enchanting").getAsDouble(), 60);
					enchantingLevel = (double) Math.round(enchantingLevel * 100) / 100;
					enchantingLevelText.setChatStyle(appendFormatted(enchantingLevelText, "XP", userObject.get("experience_skill_enchanting").getAsDouble()));
					enchantingLevelText.setChatStyle(appendFormatted(enchantingLevelText, "Overflow XP", getOverflowXP(userObject.get("experience_skill_enchanting").getAsDouble(), 60)));
				}
				if (userObject.has("experience_skill_alchemy")) {
					alchemyLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_alchemy").getAsDouble(), 50);
					alchemyLevel = (double) Math.round(alchemyLevel * 100) / 100;
					alchemyLevelText.setChatStyle(appendFormatted(alchemyLevelText, "XP", userObject.get("experience_skill_alchemy").getAsDouble()));
					alchemyLevelText.setChatStyle(appendFormatted(alchemyLevelText, "Overflow XP", getOverflowXP(userObject.get("experience_skill_alchemy").getAsDouble(), 50)));
				}
				if (userObject.has("experience_skill_taming")) {
					tamingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_taming").getAsDouble(), 50);
					tamingLevel = (double) Math.round(tamingLevel * 100) / 100;
					tamingLevelText.setChatStyle(appendFormatted(tamingLevelText, "XP", userObject.get("experience_skill_taming").getAsDouble()));
					tamingLevelText.setChatStyle(appendFormatted(tamingLevelText, "Overflow XP", getOverflowXP(userObject.get("experience_skill_taming").getAsDouble(), 50)));
				}
				if (userObject.has("experience_skill_carpentry")) {
					carpentryLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_carpentry").getAsDouble(), 50);
					carpentryLevel = (double) Math.round(carpentryLevel * 100) / 100;
					carpentryLevelText.setChatStyle(appendFormatted(carpentryLevelText, "XP", userObject.get("experience_skill_carpentry").getAsDouble()));
					carpentryLevelText.setChatStyle(appendFormatted(carpentryLevelText, "Overflow XP", getOverflowXP(userObject.get("experience_skill_carpentry").getAsDouble(), 50)));
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
				
				JsonObject achievementObject = playerObject.get("player").getAsJsonObject().get("achievements").getAsJsonObject();
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
