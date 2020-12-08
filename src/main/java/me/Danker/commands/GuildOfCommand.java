package me.Danker.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
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

import java.util.List;

public class GuildOfCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "guildof";
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
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking guild of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
			} else {
				username = arg1[0];
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking guild of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
				uuid = APIHandler.getUUID(username);
			}
			
			// Find guild ID
			System.out.println("Fetching guild...");
			String guildURL = "https://api.hypixel.net/guild?player=" + uuid + "&key=" + key;
			JsonObject guildResponse = APIHandler.getResponse(guildURL);
			if (!guildResponse.get("success").getAsBoolean()) {
				String reason = guildResponse.get("cause").getAsString();
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Failed with reason: " + reason));
				return;
			}
			
			System.out.println("Fetching guild stats and members...");
			
			String guildName = "N/A";
			String guildMaster = "N/A";
			int players = 0;
			// Check if player is in guild
			if (!guildResponse.get("guild").isJsonNull()) {
				guildName = guildResponse.get("guild").getAsJsonObject().get("name").getAsString();
				
				// Loop through members to find guildmaster
				JsonArray guildMembers = guildResponse.get("guild").getAsJsonObject().get("members").getAsJsonArray();
				players = guildMembers.size();
				for (JsonElement member : guildMembers) {
					JsonObject memberObject = member.getAsJsonObject();
					String memberRank = memberObject.get("rank").getAsString();
					
					if (memberRank.equals("GUILDMASTER") || memberRank.equals("Guild Master")) {
						// Get username from UUID
						String gmUUID = memberObject.get("uuid").getAsString();
						String gmNameURL = "https://api.mojang.com/user/profiles/" + gmUUID + "/names";
						JsonArray gmNameResponse = APIHandler.getArrayResponse(gmNameURL);
						
						guildMaster = gmNameResponse.get(gmNameResponse.size() - 1).getAsJsonObject().get("name").getAsString();
						break;
					}
				}
			}
			
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------\n" +
														EnumChatFormatting.AQUA + " " + username + "'s Guild:\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Guild: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + guildName + "\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Guildmaster: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + guildMaster + "\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Members: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + players + "\n" +
														DankersSkyblockMod.DELIMITER_COLOUR + " " + EnumChatFormatting.BOLD + "-------------------"));
		}).start();
	}

}
