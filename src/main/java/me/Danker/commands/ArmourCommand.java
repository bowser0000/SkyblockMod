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
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class ArmourCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "armor";
	}
	
	@Override
	public List<String> getCommandAliases() {
        return Collections.singletonList("armour");
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
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "API key not set. Use /setkey."));
			}
			
			// Get UUID for Hypixel API requests
			String username;
			String uuid;
			if (arg1.length == 0) {
				username = player.getName();
				uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking armour of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
			} else {
				username = arg1[0];
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking armour of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
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
			
			String armourBase64 = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("inv_armor").getAsJsonObject().get("data").getAsString();
			InputStream armourStream = new ByteArrayInputStream(Base64.getDecoder().decode(armourBase64));
			// String armourDecodedGZIP = new String(Base64.getDecoder().decode(armourBase64));
			
			try {
				NBTTagCompound armour = CompressedStreamTools.readCompressed(armourStream);
				NBTTagList armourList = armour.getTagList("i", 10);
				
				String helmet = EnumChatFormatting.RED + "None";
				String chest = EnumChatFormatting.RED + "None";
				String legs = EnumChatFormatting.RED + "None";
				String boots = EnumChatFormatting.RED + "None";
				// Loop through armour
				for (int i = 0; i < armourList.tagCount(); i++) {
					NBTTagCompound armourPiece = armourList.getCompoundTagAt(i);
					if (armourPiece.hasNoTags()) continue;
					
					String armourPieceName = armourPiece.getCompoundTag("tag").getCompoundTag("display").getString("Name");
					// NBT is served boots -> helmet
					switch (i) {
						case 0:
							boots = armourPieceName;
							break;
						case 1:
							legs = armourPieceName;
							break;
						case 2:
							chest = armourPieceName;
							break;
						case 3:
							helmet = armourPieceName;
							break;
						default:
							System.err.println("An error has occurred.");
							break;
					}
				}
				armourStream.close();
				
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------\n" +
															EnumChatFormatting.AQUA + " " + username + "'s Armour:\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Helmet:      " + helmet + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Chestplate: " + chest + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Leggings:   " + legs + "\n" +
															DankersSkyblockMod.TYPE_COLOUR + " Boots:       " + boots + "\n" +
															DankersSkyblockMod.DELIMITER_COLOUR + " " + EnumChatFormatting.BOLD + "-------------------"));
			} catch (IOException ex) {
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "An error has occurred while reading inventory data. See logs for more info."));
				ex.printStackTrace();
			}
		}).start();
	}

}
