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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SkyblockPlayersCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "sbplayers";
	}
	
	@Override
	public List<String> getCommandAliases() {
        return Collections.singletonList("skyblockplayers");
    }

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName();
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
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
			
			String playersURL = "https://api.hypixel.net/gameCounts?key=" + key;
			System.out.println("Fetching player count...");
			JsonObject playersResponse = APIHandler.getResponse(playersURL);
			if (!playersResponse.get("success").getAsBoolean()) {
				String reason = playersResponse.get("cause").getAsString();
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Failed with reason: " + reason));
				return;
			}
			
			int totalPlayers = playersResponse.get("playerCount").getAsInt();
			// Skyblock players, comments show JSON key
			int skyblockTotalPlayers = 0; // players
			int privateIsland = 0; // dynamic
			int hub = 0; // hub
			int barn = 0; // farming_1
			int mushroomDesert = 0; // farming_2
			int park = 0; // foraging_1
			int goldMine = 0; // mining_1
			int deepCaverns = 0; // mining_2
			int spidersDen = 0; // combat_1
			int blazingFortress = 0; // combat_2
			int end = 0; // combat_3
			int dungeonsHub = 0; // dungeon_hub
			int dungeons = 0; // dungeon
			int darkAuction = 0; // dark_auction
			int jerry = 0; // winter
			if (playersResponse.get("games").getAsJsonObject().get("SKYBLOCK").getAsJsonObject().has("modes")) {
				JsonObject skyblockPlayers = playersResponse.get("games").getAsJsonObject().get("SKYBLOCK").getAsJsonObject().get("modes").getAsJsonObject();
				skyblockTotalPlayers = playersResponse.get("games").getAsJsonObject().get("SKYBLOCK").getAsJsonObject().get("players").getAsInt();
				if (skyblockPlayers.has("dynamic")) {
					privateIsland = skyblockPlayers.get("dynamic").getAsInt();
				}
				if (skyblockPlayers.has("hub")) {
					hub = skyblockPlayers.get("hub").getAsInt();
				}
				if (skyblockPlayers.has("farming_1")) {
					barn = skyblockPlayers.get("farming_1").getAsInt();
				}
				if (skyblockPlayers.has("farming_2")) {
					mushroomDesert = skyblockPlayers.get("farming_2").getAsInt();
				}
				if (skyblockPlayers.has("foraging_1")) {
					park = skyblockPlayers.get("foraging_1").getAsInt();
				}
				if (skyblockPlayers.has("mining_1")) {
					goldMine = skyblockPlayers.get("mining_1").getAsInt();
				}
				if (skyblockPlayers.has("mining_2")) {
					deepCaverns = skyblockPlayers.get("mining_2").getAsInt();
				}
				if (skyblockPlayers.has("combat_1")) {
					spidersDen = skyblockPlayers.get("combat_1").getAsInt();
				}
				if (skyblockPlayers.has("combat_2")) {
					blazingFortress = skyblockPlayers.get("combat_2").getAsInt();
				}
				if (skyblockPlayers.has("combat_3")) {
					end = skyblockPlayers.get("combat_3").getAsInt();
				}
				if (skyblockPlayers.has("dungeon_hub")) {
					dungeonsHub = skyblockPlayers.get("dungeon_hub").getAsInt();
				}
				if (skyblockPlayers.has("dungeon")) {
					dungeons = skyblockPlayers.get("dungeon").getAsInt();
				}
				if (skyblockPlayers.has("dark_auction")) {
					darkAuction = skyblockPlayers.get("dark_auction").getAsInt();
				}
				if (skyblockPlayers.has("winter")) {
					jerry = skyblockPlayers.get("winter").getAsInt();
				}
			}
			
			NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Hypixel: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(totalPlayers) + "\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Skyblock: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(skyblockTotalPlayers) + " / " + Utils.getPercentage(skyblockTotalPlayers, totalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Private Island: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(privateIsland) + " / " + Utils.getPercentage(privateIsland, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Hub: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(hub) + " / " + Utils.getPercentage(hub, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Barn: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(barn) + " / " + Utils.getPercentage(barn, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Mushroom Desert: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(mushroomDesert) + " / " + Utils.getPercentage(mushroomDesert, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Park: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(park) + " / " + Utils.getPercentage(park, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Gold Mine: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(goldMine) + " / " + Utils.getPercentage(goldMine, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Deep Caverns: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(deepCaverns) + " / " + Utils.getPercentage(deepCaverns, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Spider's Den: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(spidersDen) + " / " + Utils.getPercentage(spidersDen, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Blazing Fortress: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(blazingFortress) + " / " + Utils.getPercentage(blazingFortress, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " The End: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(end) + " / " + Utils.getPercentage(end, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Dungeons Hub: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(dungeonsHub) + " / " + Utils.getPercentage(dungeonsHub, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Dungeons: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(dungeons) + " / " + Utils.getPercentage(dungeons, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Dark Auction: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(darkAuction) + " / " + Utils.getPercentage(darkAuction, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.TYPE_COLOUR + " Jerry's Workshop: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(jerry) + " / " + Utils.getPercentage(jerry, skyblockTotalPlayers) + "%\n" +
														DankersSkyblockMod.DELIMITER_COLOUR + EnumChatFormatting.BOLD + " -------------------"));
		}).start();
	}

}
