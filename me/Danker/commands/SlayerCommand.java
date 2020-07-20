package me.Danker.commands;

import java.text.NumberFormat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class SlayerCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "slayer";
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
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Checking slayer of " + EnumChatFormatting.DARK_GREEN + username));
			} else {
				username = arg1[0];
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Checking slayer of " + EnumChatFormatting.DARK_GREEN + username));
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
			
			System.out.println("Fetching slayer stats...");
			JsonObject slayersObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("slayer_bosses").getAsJsonObject();
			// Zombie
			int zombieXP = 0;
			if (slayersObject.get("zombie").getAsJsonObject().has("xp")) {
				zombieXP = slayersObject.get("zombie").getAsJsonObject().get("xp").getAsInt();
			}
			// Spider
			int spiderXP = 0;
			if (slayersObject.get("spider").getAsJsonObject().has("xp")) {
				spiderXP = slayersObject.get("spider").getAsJsonObject().get("xp").getAsInt();
			}
			// Wolf
			int wolfXP = 0;
			if (slayersObject.get("wolf").getAsJsonObject().has("xp")) {
				wolfXP = slayersObject.get("wolf").getAsJsonObject().get("xp").getAsInt();
			}
			
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
														EnumChatFormatting.AQUA + " " + username + "'s Total XP: " + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + NumberFormat.getIntegerInstance().format(zombieXP + spiderXP + wolfXP) + "\n" +
														EnumChatFormatting.AQUA + " Zombie XP: " + EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + NumberFormat.getIntegerInstance().format(zombieXP) + "\n" +
														EnumChatFormatting.AQUA + " Spider XP: " + EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + NumberFormat.getIntegerInstance().format(spiderXP) + "\n" +
														EnumChatFormatting.AQUA + " Wolf XP: " + EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + NumberFormat.getIntegerInstance().format(wolfXP) + "\n" +
														EnumChatFormatting.AQUA + " " + EnumChatFormatting.BOLD + "-------------------"));
			
		}).start();
	}

}
