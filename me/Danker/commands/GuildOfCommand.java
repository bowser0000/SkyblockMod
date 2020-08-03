package me.Danker.commands;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

public class GuildOfCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "guildof";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName() + " [name]";
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
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Checking guild of " + EnumChatFormatting.DARK_GREEN + username));
			} else {
				username = arg1[0];
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Checking guild of " + EnumChatFormatting.DARK_GREEN + username));
				uuid = ah.getUUID(username);
			}
			
			// Find guild ID
			System.out.println("Fetching guild...");
			String guildURL = "https://api.hypixel.net/guild?player=" + uuid + "&key=" + key;
			JsonObject guildResponse = ah.getResponse(guildURL);
			if (!guildResponse.get("success").getAsBoolean()) {
				String reason = guildResponse.get("cause").getAsString();
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed with reason: " + reason));
				return;
			}
			
			System.out.println("Fetching guild stats and members...");
			
			String guildName = "N/A";
			String guildMaster = "N/A";
			// Check if player is in guild
			if (!guildResponse.get("guild").isJsonNull()) {
				guildName = guildResponse.get("guild").getAsJsonObject().get("name").getAsString();
				
				// Loop through members to find guildmaster
				JsonArray guildMembers = guildResponse.get("guild").getAsJsonObject().get("members").getAsJsonArray();
				for (JsonElement member : guildMembers) {
					JsonObject memberObject = member.getAsJsonObject();
					String memberRank = memberObject.get("rank").getAsString();
					
					if (memberRank.equals("GUILDMASTER") || memberRank.equals("Guild Master")) {
						// Get username from UUID
						String gmUUID = memberObject.get("uuid").getAsString();
						String gmNameURL = "https://api.mojang.com/user/profiles/" + gmUUID + "/names";
						JsonArray gmNameResponse = ah.getArrayResponse(gmNameURL);
						
						guildMaster = gmNameResponse.get(gmNameResponse.size() - 1).getAsJsonObject().get("name").getAsString();
						break;
					}
				}
			}
			
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
														EnumChatFormatting.AQUA + " " + username + "'s Guild:\n" +
														EnumChatFormatting.GREEN + " Guild: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + guildName + "\n" +
														EnumChatFormatting.GREEN + " Guildmaster: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + guildMaster + "\n" +
														EnumChatFormatting.AQUA + " " + EnumChatFormatting.BOLD + "-------------------"));
		}).start();
	}

}
