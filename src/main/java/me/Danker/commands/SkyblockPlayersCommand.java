package me.Danker.commands;

import com.google.gson.JsonObject;
import me.Danker.config.ModConfig;
import me.Danker.handlers.APIHandler;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

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

	public static String usage(ICommandSender arg0) {
		return new SkyblockPlayersCommand().getCommandUsage(arg0);
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
			String key = ModConfig.apiKey;
			if (key.equals("")) {
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "API key not set. Use /setkey."));
				return;
			}
			
			String playersURL = "https://api.hypixel.net/gameCounts?key=" + key;
			System.out.println("Fetching player count...");
			JsonObject playersResponse = APIHandler.getResponse(playersURL, true);
			if (!playersResponse.get("success").getAsBoolean()) {
				String reason = playersResponse.get("cause").getAsString();
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Failed with reason: " + reason));
				return;
			}
			
			int totalPlayers = playersResponse.get("playerCount").getAsInt();
			// Skyblock players, comments show JSON key
			int skyblockTotalPlayers = 0; // players
			int privateIsland = 0; // dynamic
			int hub = 0; // hub
			int farmingIslands = 0; // farming_1
			int park = 0; // foraging_1
			int goldMine = 0; // mining_1
			int deepCaverns = 0; // mining_2
			int dwarvenMines = 0; // mining_3
			int crystalHollows = 0; // crystal_hollows
			int spidersDen = 0; // combat_1
			int crimsonIsle = 0; // crimson_isle
			int kuudra = 0; // instanced
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
					farmingIslands = skyblockPlayers.get("farming_1").getAsInt();
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
				if (skyblockPlayers.has("mining_3")) {
					dwarvenMines = skyblockPlayers.get("mining_3").getAsInt();
				}
				if (skyblockPlayers.has("crystal_hollows")) {
					crystalHollows = skyblockPlayers.get("crystal_hollows").getAsInt();
				}
				if (skyblockPlayers.has("combat_1")) {
					spidersDen = skyblockPlayers.get("combat_1").getAsInt();
				}
				if (skyblockPlayers.has("crimson_isle")) {
					crimsonIsle = skyblockPlayers.get("crimson_isle").getAsInt();
				}
				if (skyblockPlayers.has("instanced")) {
					kuudra = skyblockPlayers.get("instanced").getAsInt();
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
			player.addChatMessage(new ChatComponentText(ModConfig.getDelimiter() + "\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Hypixel: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(totalPlayers) + "\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Skyblock: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(skyblockTotalPlayers) + " / " + Utils.getPercentage(skyblockTotalPlayers, totalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Private Island: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(privateIsland) + " / " + Utils.getPercentage(privateIsland, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Hub: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(hub) + " / " + Utils.getPercentage(hub, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Farming Islands: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(farmingIslands) + " / " + Utils.getPercentage(farmingIslands, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Park: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(park) + " / " + Utils.getPercentage(park, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Gold Mine: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(goldMine) + " / " + Utils.getPercentage(goldMine, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Deep Caverns: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(deepCaverns) + " / " + Utils.getPercentage(deepCaverns, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Dwarven Mines: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(dwarvenMines) + " / " + Utils.getPercentage(dwarvenMines, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Crystal Hollows: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(crystalHollows) + " / " + Utils.getPercentage(crystalHollows, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Spider's Den: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(spidersDen) + " / " + Utils.getPercentage(spidersDen, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Crimson Isle: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(crimsonIsle) + " / " + Utils.getPercentage(crimsonIsle, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Kuudra: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(kuudra) + " / " + Utils.getPercentage(kuudra, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " The End: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(end) + " / " + Utils.getPercentage(end, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Dungeons Hub: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(dungeonsHub) + " / " + Utils.getPercentage(dungeonsHub, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Dungeons: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(dungeons) + " / " + Utils.getPercentage(dungeons, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Dark Auction: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(darkAuction) + " / " + Utils.getPercentage(darkAuction, skyblockTotalPlayers) + "%\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Jerry's Workshop: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(jerry) + " / " + Utils.getPercentage(jerry, skyblockTotalPlayers) + "%\n" +
														ModConfig.getDelimiter()));
		}).start();
	}

}
