package me.Danker.commands;

import java.util.ArrayList;
import java.util.List;

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

public class PetsCommand extends CommandBase {

	static int petXpToLevel(double xp, String rarity) {
		int[] xpPerLevel = {100, 110, 120, 130, 145, 160, 175, 190, 210, 230, 250, 275, 300, 330, 360, 400, 440, 490, 540, 600, 660, 730,
							800, 880, 860, 1050, 1150, 1260, 1380, 1510, 1650, 1800, 1960, 2130, 2310, 2500, 2700, 2920, 3160, 3420, 3700,
							4000, 4350, 4750, 5200, 5700, 6300, 7000, 7800, 8700, 9700, 10800, 12000, 13300, 14700, 16200, 17800, 19500,
							21300, 23200, 25200, 27400, 29800, 32400, 35200, 38200, 41400, 44800, 48400, 52200, 56200, 60400, 64800, 69400,
							74200, 79200, 84700, 90700, 97200, 104200, 111700, 119700, 128200, 137200, 146700, 156700, 167700, 179700, 192700,
							206700, 221700, 237700, 254700, 272700, 291700, 311700, 333700, 357700, 383700, 411700, 441700, 476700, 516700,
							561700, 611700, 666700, 726700, 791700, 861700, 936700, 1016700, 1101700, 1191700, 1286700, 1386700, 1496700,
							1616700, 1746700, 1886700};
		
		int levelOffset = 0;
		if (rarity.equals("UNCOMMON")) {
			levelOffset = 6;
		} else if (rarity.equals("RARE")) {
			levelOffset = 11;
		} else if (rarity.equals("EPIC")) {
			levelOffset = 16;
		} else if (rarity.equals("LEGENDARY")) {
			levelOffset = 20;
		}
		
		for (int i = levelOffset, xpAdded = 0; i < levelOffset + 99; i++) {
			xpAdded += xpPerLevel[i];
			if (xp < xpAdded) {
				return i + 1 - levelOffset;
			}
		}
		return 100;
	}
	
	static String capitalize(String string) {
		String capitalized = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
		return capitalized;
	}
	
	@Override
	public String getCommandName() {
		return "pets";
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
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Checking pets of " + EnumChatFormatting.DARK_GREEN + username));
			} else {
				username = arg1[0];
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Checking pets of " + EnumChatFormatting.DARK_GREEN + username));
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
			
			System.out.println("Fetching pets...");
			JsonArray petsArray = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("pets").getAsJsonArray();
			if (petsArray.size() == 0) {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + username + " has no pets."));
				return;
			}
			
			System.out.println("Looping through pets...");
			
			List<JsonObject> commonPets = new ArrayList<JsonObject>();
			List<JsonObject> uncommonPets = new ArrayList<JsonObject>();
			List<JsonObject> rarePets = new ArrayList<JsonObject>();
			List<JsonObject> epicPets = new ArrayList<JsonObject>();
			List<JsonObject> legendaryPets = new ArrayList<JsonObject>();
			
			for (JsonElement petElement : petsArray) {
				JsonObject pet = petElement.getAsJsonObject();
				String rarity = pet.get("tier").getAsString();
				
				if (rarity.equals("COMMON")) {
					commonPets.add(pet);
				} else if (rarity.equals("UNCOMMON")) {
					uncommonPets.add(pet);
				} else if (rarity.equals("RARE")) {
					rarePets.add(pet);
				} else if (rarity.equals("EPIC")) {
					epicPets.add(pet);
				} else if (rarity.equals("LEGENDARY")) {
					legendaryPets.add(pet);
				}
			}
			
			String finalMessage = EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "-------------------\n" +
					  			  EnumChatFormatting.AQUA + " " + username + "'s Pets:\n";
			
			// Loop through pet rarities
			for (JsonObject legPet : legendaryPets) {
				String petName = capitalize(legPet.get("type").getAsString());
				int level = petXpToLevel(legPet.get("exp").getAsDouble(), "LEGENDARY");
				
				String messageToAdd = "";
				if (legPet.get("active").getAsBoolean()) {
					messageToAdd = EnumChatFormatting.GOLD + " " + EnumChatFormatting.BOLD + ">>> Legendary " + petName + " (" + level + ") <<<";
				} else {
					messageToAdd = EnumChatFormatting.GOLD + " Legendary " + petName + " (" + level + ")";
				}
				
				finalMessage += messageToAdd + "\n";
			}
			
			for (JsonObject epicPet: epicPets) {
				String petName = capitalize(epicPet.get("type").getAsString());
				int level = petXpToLevel(epicPet.get("exp").getAsDouble(), "EPIC");
				
				String messageToAdd = "";
				if (epicPet.get("active").getAsBoolean()) {
					messageToAdd = EnumChatFormatting.DARK_PURPLE + " " + EnumChatFormatting.BOLD + ">>> Epic " + petName + " (" + level + ") <<<";
				} else {
					messageToAdd = EnumChatFormatting.DARK_PURPLE + " Epic " + petName + " (" + level + ")";
				}
				
				finalMessage += messageToAdd + "\n";
			}
			
			for (JsonObject rarePet: rarePets) {
				String petName = capitalize(rarePet.get("type").getAsString());
				int level = petXpToLevel(rarePet.get("exp").getAsDouble(), "RARE");
				
				String messageToAdd = "";
				if (rarePet.get("active").getAsBoolean()) {
					messageToAdd = EnumChatFormatting.BLUE + " " + EnumChatFormatting.BOLD + ">>> Rare " + petName + " (" + level + ") <<<";
				} else {
					messageToAdd = EnumChatFormatting.BLUE + " Rare " + petName + " (" + level + ")";
				}
				
				finalMessage += messageToAdd + "\n";
			}
			
			for (JsonObject uncommonPet: uncommonPets) {
				String petName = capitalize(uncommonPet.get("type").getAsString());
				int level = petXpToLevel(uncommonPet.get("exp").getAsDouble(), "UNCOMMON");
				
				String messageToAdd = "";
				if (uncommonPet.get("active").getAsBoolean()) {
					messageToAdd = EnumChatFormatting.GREEN + " " + EnumChatFormatting.BOLD + ">>> Uncommon " + petName + " (" + level + ") <<<";
				} else {
					messageToAdd = EnumChatFormatting.GREEN + " Uncommon " + petName + " (" + level + ")";
				}
				
				finalMessage += messageToAdd + "\n";
			}
			
			for (JsonObject commonPet: commonPets) {
				String petName = capitalize(commonPet.get("type").getAsString());
				int level = petXpToLevel(commonPet.get("exp").getAsDouble(), "COMMON");
				
				String messageToAdd = "";
				if (commonPet.get("active").getAsBoolean()) {
					messageToAdd = EnumChatFormatting.BOLD + ">>> Common " + petName + " (" + level + ") <<<";
				} else {
					messageToAdd = " Common " + petName + " (" + level + ")";
				}
				
				finalMessage += messageToAdd + "\n";
			}
			
			finalMessage += EnumChatFormatting.AQUA + " " + EnumChatFormatting.BOLD + "-------------------";
			player.addChatMessage(new ChatComponentText(finalMessage));
				
		}).start();
	}

}
