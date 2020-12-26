package me.Danker.commands;

import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class DungeonsCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "dungeons";
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
			EntityPlayer player = (EntityPlayer) arg0;
			
			// Check key
			String key = ConfigHandler.getString("api", "APIKey");
			if (key.equals("")) {
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "API key not set. Use /setkey."));
			}
			
			// Get UUID for Hypixel API requests
			String username;
			String uuid;
			if (arg1.length == 0) {
				username = player.getName();
				uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking dungeon stats of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
			} else {
				username = arg1[0];
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking dungeon stats of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
				uuid = APIHandler.getUUID(username);
			}
			
			// Find stats of latest profile
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

			String playerURL = "https://api.hypixel.net/player?uuid=" + uuid + "&key=" + key;
			System.out.println("Fetching player data...");
			JsonObject playerResponse = APIHandler.getResponse(playerURL);
			if(!playerResponse.get("success").getAsBoolean()){
				String reason = playerResponse.get("cause").getAsString();
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "This player has not played on Hypixel."));
			}
			
			System.out.println("Fetching dungeon stats...");
			JsonObject dungeonsObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject();
			if (!dungeonsObject.get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().has("experience")) {
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "This player has not played dungeons."));
				return;
			}

			JsonObject catacombsObject = dungeonsObject.get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject();
			double catacombs = Utils.xpToDungeonsLevel(catacombsObject.get("experience").getAsDouble());
			double healer = Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("healer").getAsJsonObject().get("experience").getAsDouble());
			double mage = Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("mage").getAsJsonObject().get("experience").getAsDouble());
			double berserk = Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("berserk").getAsJsonObject().get("experience").getAsDouble());
			double archer = Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("archer").getAsJsonObject().get("experience").getAsDouble());
			double tank = Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("tank").getAsJsonObject().get("experience").getAsDouble());
			String selectedClass = Utils.capitalizeString(dungeonsObject.get("selected_dungeon_class").getAsString());
			int secrets = playerResponse.get("player").getAsJsonObject().get("achievements").getAsJsonObject().get("skyblock_treasure_hunter").getAsInt();

			int highestFloor = catacombsObject.get("highest_tier_completed").getAsInt();
			JsonObject completionObj = catacombsObject.get("tier_completions").getAsJsonObject();

			String delimiter = DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------";

			ChatComponentText classLevels = new ChatComponentText(
					EnumChatFormatting.GOLD + " Selected Class: " + selectedClass + "\n\n" +
						 EnumChatFormatting.RED + " Catacombs Level: " + catacombs + "\n" +
						 EnumChatFormatting.YELLOW + " Healer Level: " + healer + "\n" +
						 EnumChatFormatting.LIGHT_PURPLE + " Mage Level: " + mage + "\n" +
						 EnumChatFormatting.RED + " Berserk Level: " + berserk + "\n" +
						 EnumChatFormatting.GREEN + " Archer Level: " + archer + "\n" +
						 EnumChatFormatting.BLUE + " Tank Level: " + tank + "\n\n" +
						 EnumChatFormatting.WHITE + " Secrets Found: " + secrets + "\n\n");

			StringBuilder completionsHoverString = new StringBuilder();

			for (int i = 0; i <= highestFloor; i++) {
				completionsHoverString
						.append(EnumChatFormatting.GOLD)
						.append(i == 0 ? "Entrance: " : "Floor " + i + ": ")
						.append(EnumChatFormatting.RESET)
						.append(completionObj.get(String.valueOf(i)).getAsInt())
						.append(i < highestFloor ? "\n": "");
			}

			ChatComponentText completions = new ChatComponentText(EnumChatFormatting.GOLD + " Highest Floor Completed: " + highestFloor);

			completions.setChatStyle(completions.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(completionsHoverString.toString()))));


			player.addChatMessage(
					new ChatComponentText(delimiter)
					.appendText("\n")
					.appendSibling(classLevels)
					.appendSibling(completions)
					.appendText("\n")
					.appendSibling(new ChatComponentText(delimiter))
				);
		}).start();
	}
}
