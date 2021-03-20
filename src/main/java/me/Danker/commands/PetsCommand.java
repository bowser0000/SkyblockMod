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

import java.util.ArrayList;
import java.util.List;

public class PetsCommand extends CommandBase {

	static int petXpToLevel(double xp, String rarity) {
		int[] xpPerLevel = {100, 110, 120, 130, 145, 160, 175, 190, 210, 230, 250, 275, 300, 330, 360, 400, 440, 490, 540, 600, 660, 730,
				800, 880, 960, 1050, 1150, 1260, 1380, 1510, 1650, 1800, 1960, 2130, 2310, 2500, 2700, 2920, 3160, 3420, 3700,
				4000, 4350, 4750, 5200, 5700, 6300, 7000, 7800, 8700, 9700, 10800, 12000, 13300, 14700, 16200, 17800, 19500,
				21300, 23200, 25200, 27400, 29800, 32400, 35200, 38200, 41400, 44800, 48400, 52200, 56200, 60400, 64800, 69400,
				74200, 79200, 84700, 90700, 97200, 104200, 111700, 119700, 128200, 137200, 146700, 156700, 167700, 179700, 192700,
				206700, 221700, 237700, 254700, 272700, 291700, 311700, 333700, 357700, 383700, 411700, 441700, 476700, 516700,
				561700, 611700, 666700, 726700, 791700, 861700, 936700, 1016700, 1101700, 1191700, 1286700, 1386700, 1496700,
				1616700, 1746700, 1886700};

		int levelOffset = 0;
		switch (rarity) {
			case "UNCOMMON":
				levelOffset = 6;
				break;
			case "RARE":
				levelOffset = 11;
				break;
			case "EPIC":
				levelOffset = 16;
				break;
			case "LEGENDARY":
				levelOffset = 20;
				break;
			case "MYTHIC":
				levelOffset = 20;
				break;
		}

		for (int i = levelOffset, xpAdded = 0; i < levelOffset + 99; i++) {
			xpAdded += xpPerLevel[i];
			if (xp < xpAdded) {
				return i + 1 - levelOffset;
			}
		}
		return 100;
	}

	enum Rarity {
		COMMON,
		UNCOMMON,
		RARE,
		EPIC,
		LEGENDARY,
		MYTHIC;

		public Rarity nextRarity() {
			if (this.ordinal() == Rarity.values().length) return this;
			return Rarity.values()[this.ordinal() + 1];
		}

		public EnumChatFormatting getChatColor() {
			if (this == Rarity.COMMON) return EnumChatFormatting.WHITE;
			if (this == Rarity.UNCOMMON) return EnumChatFormatting.GREEN;
			if (this == Rarity.RARE) return EnumChatFormatting.BLUE;
			if (this == Rarity.EPIC) return EnumChatFormatting.DARK_PURPLE;
			if (this == Rarity.LEGENDARY) return EnumChatFormatting.GOLD;
			if (this == Rarity.MYTHIC) return EnumChatFormatting.LIGHT_PURPLE;
			return null;
		}
	}

	class Pet {

		public Rarity rarity;
		public double xp;
		public boolean active;
		public boolean rarityBoosted = false;
		public String name;

		Pet(JsonObject pet) {
			Rarity rarity = Rarity.valueOf(pet.get("tier").getAsString());
			if (!pet.get("heldItem").isJsonNull()) {
				String petItemID = pet.get("heldItem").getAsString();
				switch (petItemID) {
					case "PET_ITEM_VAMPIRE_FANG":
					case "PET_ITEM_TOY_JERRY":
					case "PET_ITEM_TIER_BOOST":
						rarityBoosted = true;
						rarity = rarity.nextRarity();
				}
			}
			this.active = pet.get("active").getAsBoolean();
			this.name = Utils.capitalizeString(pet.get("type").getAsString());;
			this.rarity = rarity;
			this.xp = pet.get("exp").getAsDouble();
		}

		public String getStringToAdd() {
			int level = petXpToLevel(this.xp, this.rarity.name());

			String messageToAdd = rarity.getChatColor() + " " + (this.active ? EnumChatFormatting.BOLD + ">>> " : "") + Utils.capitalizeString(this.rarity.name()) + (this.rarityBoosted ? " ⇑" : "")  + " " + this.name + " (" + level + ")" + (this.active ? " <<<" : "");

			return messageToAdd + "\n";
		}

	}

	@Override
	public String getCommandName() {
		return "petsof";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " [name]";
	}

	public static String usage(ICommandSender arg0) {
		return new PetsCommand().getCommandUsage(arg0);
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
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking pets of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
			} else {
				username = arg1[0];
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking pets of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
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

			System.out.println("Fetching pets...");
			JsonArray petsArray = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("pets").getAsJsonArray();
			if (petsArray.size() == 0) {
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + username + " has no pets."));
				return;
			}

			System.out.println("Looping through pets...");
			// Push each pet into list
			List<Pet> pets = new ArrayList<>();
			for (JsonElement petElement : petsArray) {
				pets.add(new Pet(petElement.getAsJsonObject()));
			}

			// Sort pets by exp and rarity
			pets.sort((pet1, pet2) -> {
				int rarity = pet1.rarity.compareTo(pet2.rarity);
				int xp = Double.compare(pet1.xp, pet2.xp);
				if (rarity != 0) return -rarity;
				return -xp;
			});

			String finalMessage = DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------\n" +
					EnumChatFormatting.AQUA + " " + username + "'s Pets (" + pets.size() + "):\n";

			// Loop through pets
			for(Pet pet : pets) {
				finalMessage += pet.getStringToAdd();
			}

			finalMessage += DankersSkyblockMod.DELIMITER_COLOUR + " " + EnumChatFormatting.BOLD + "-------------------";
			player.addChatMessage(new ChatComponentText(finalMessage));

		}).start();
	}

}