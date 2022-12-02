package me.Danker.commands;

import com.google.gson.JsonObject;
import me.Danker.config.ModConfig;
import me.Danker.handlers.APIHandler;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DungeonsCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "dungeons";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " [name]";
	}

	public static String usage(ICommandSender arg0) {
		return new DungeonsCommand().getCommandUsage(arg0);
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
			
			// Check key
			String key = ModConfig.apiKey;
			if (key.equals("")) {
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "API key not set. Use /setkey."));
				return;
			}
			
			// Get UUID for Hypixel API requests
			String username;
			String uuid;
			if (arg1.length == 0) {
				username = player.getName();
				uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking dungeon stats of " + ModConfig.getColour(ModConfig.secondaryColour) + username));
			} else {
				username = arg1[0];
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking dungeon stats of " + ModConfig.getColour(ModConfig.secondaryColour) + username));
				uuid = APIHandler.getUUID(username);
			}
			
			// Find stats of latest profile
			String latestProfile = APIHandler.getLatestProfileID(uuid, key);
			if (latestProfile == null) return;
			
			String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + key;
			System.out.println("Fetching profile...");
			JsonObject profileResponse = APIHandler.getResponse(profileURL, true);
			if (!profileResponse.get("success").getAsBoolean()) {
				String reason = profileResponse.get("cause").getAsString();
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Failed with reason: " + reason));
				return;
			}

			String playerURL = "https://api.hypixel.net/player?uuid=" + uuid + "&key=" + key;
			System.out.println("Fetching player data...");
			JsonObject playerResponse = APIHandler.getResponse(playerURL, true);
			if (!playerResponse.get("success").getAsBoolean()) {
				String reason = playerResponse.get("cause").getAsString();
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Failed with reason: " + reason));
				return;
			}
			
			System.out.println("Fetching dungeon stats...");
			JsonObject dungeonsObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject();
			if (!dungeonsObject.get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().has("experience")) {
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "This player has not played dungeons."));
				return;
			}

			JsonObject catacombsObject = dungeonsObject.get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject();

			double catacombs = Utils.xpToDungeonsLevel(catacombsObject.get("experience").getAsDouble());
			double healer = MathHelper.clamp_double(Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("healer").getAsJsonObject().get("experience").getAsDouble()), 0D, 50D);
			double mage = MathHelper.clamp_double(Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("mage").getAsJsonObject().get("experience").getAsDouble()), 0D, 50D);
			double berserk = MathHelper.clamp_double(Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("berserk").getAsJsonObject().get("experience").getAsDouble()), 0D, 50D);
			double archer = MathHelper.clamp_double(Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("archer").getAsJsonObject().get("experience").getAsDouble()), 0D, 50D);
			double tank = MathHelper.clamp_double(Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("tank").getAsJsonObject().get("experience").getAsDouble()), 0D, 50D);
			double classAverage = Math.round((healer + mage + berserk + archer + tank) / 5D * 100D) / 100D;
			String selectedClass = Utils.capitalizeString(dungeonsObject.get("selected_dungeon_class").getAsString());
			int secrets = playerResponse.get("player").getAsJsonObject().get("achievements").getAsJsonObject().get("skyblock_treasure_hunter").getAsInt();

			int highestFloor = catacombsObject.get("highest_tier_completed").getAsInt();
			JsonObject completionObj = catacombsObject.get("tier_completions").getAsJsonObject();

			JsonObject catacombsMasterObject = dungeonsObject.get("dungeon_types").getAsJsonObject().get("master_catacombs").getAsJsonObject();
			boolean hasPlayedMaster = catacombsMasterObject.has("highest_tier_completed");

			int highestMasterFloor = 0;
			JsonObject completionMasterObj = null;
			if (hasPlayedMaster) {
				highestMasterFloor = catacombsMasterObject.get("highest_tier_completed").getAsInt();
				completionMasterObj = catacombsMasterObject.get("tier_completions").getAsJsonObject();
			}

			ChatComponentText classLevels = new ChatComponentText(EnumChatFormatting.GOLD + " Selected Class: " + selectedClass + "\n\n" +
																  EnumChatFormatting.RED + " Catacombs Level: " + catacombs + "\n" +
																  EnumChatFormatting.RED + " Class Average: " + classAverage + "\n\n" +
																  EnumChatFormatting.YELLOW + " Healer Level: " + healer + "\n" +
																  EnumChatFormatting.LIGHT_PURPLE + " Mage Level: " + mage + "\n" +
																  EnumChatFormatting.RED + " Berserk Level: " + berserk + "\n" +
																  EnumChatFormatting.GREEN + " Archer Level: " + archer + "\n" +
																  EnumChatFormatting.BLUE + " Tank Level: " + tank + "\n\n" +
																  EnumChatFormatting.WHITE + " Secrets Found: " + NumberFormat.getIntegerInstance(Locale.US).format(secrets) + "\n\n");

			StringBuilder completionsHoverString = new StringBuilder();
			for (int i = 0; i <= highestFloor; i++) {
				completionsHoverString
					.append(EnumChatFormatting.GOLD)
					.append(i == 0 ? "Entrance: " : "Floor " + i + ": ")
					.append(EnumChatFormatting.RESET)
					.append(completionObj.get(String.valueOf(i)).getAsInt())
					.append(i < highestFloor || hasPlayedMaster ? "\n": "");
			}
			for (int i = 1; i <= highestMasterFloor; i++) {
				if (completionMasterObj != null && completionMasterObj.has(String.valueOf(i))) {
					completionsHoverString
						.append(EnumChatFormatting.GOLD)
						.append("Master Floor ")
						.append(i)
						.append(": ")
						.append(EnumChatFormatting.RESET)
						.append(completionMasterObj.get(String.valueOf(i)).getAsInt())
						.append(i < highestMasterFloor ? "\n": "");
				}
			}

			ChatComponentText completions;
			if (hasPlayedMaster) {
				completions = new ChatComponentText(EnumChatFormatting.GOLD + " Highest Floor Completed: Master " + highestMasterFloor);
			} else {
				completions = new ChatComponentText(EnumChatFormatting.GOLD + " Highest Floor Completed: " + highestFloor);
			}
			completions.setChatStyle(completions.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(completionsHoverString.toString()))));

			player.addChatMessage(
					new ChatComponentText(ModConfig.getDelimiter())
					.appendText("\n")
					.appendSibling(classLevels)
					.appendSibling(completions)
					.appendText("\n")
					.appendSibling(new ChatComponentText(ModConfig.getDelimiter())));
		}).start();
	}
}
