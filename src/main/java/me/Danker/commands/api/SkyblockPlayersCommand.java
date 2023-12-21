package me.Danker.commands.api;

import com.google.gson.JsonObject;
import me.Danker.config.ModConfig;
import me.Danker.handlers.HypixelAPIHandler;
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

	static NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);

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
			
			System.out.println("Fetching player count...");
			JsonObject playersResponse = HypixelAPIHandler.getJsonObjectAuth(HypixelAPIHandler.URL + "gameCounts");

			if (playersResponse == null) {
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Could not connect to API."));
				return;
			}
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
			int garden = 0; // garden
			int park = 0; // foraging_1
			int goldMine = 0; // mining_1
			int deepCaverns = 0; // mining_2
			int dwarvenMines = 0; // mining_3
			int crystalHollows = 0; // crystal_hollows
			int spidersDen = 0; // combat_1
			int crimsonIsle = 0; // crimson_isle
			int kuudra = 0; // kuudra
			int end = 0; // combat_3
			int dungeonsHub = 0; // dungeon_hub
			int dungeons = 0; // dungeon
			int darkAuction = 0; // dark_auction
			int jerry = 0; // winter
			int rift = 0; // rift

			if (Utils.getObjectFromPath(playersResponse, "games.SKYBLOCK").has("modes")) {
				JsonObject skyblockPlayers = Utils.getObjectFromPath(playersResponse, "games.SKYBLOCK.modes");
				skyblockTotalPlayers = Utils.getObjectFromPath(playersResponse, "games.SKYBLOCK").get("players").getAsInt();

				privateIsland = getPlayerCount("dynamic", skyblockPlayers);
				hub = getPlayerCount("hub", skyblockPlayers);
				farmingIslands = getPlayerCount("farming_1", skyblockPlayers);
				garden = getPlayerCount("garden", skyblockPlayers);
				park = getPlayerCount("foraging_1", skyblockPlayers);
				goldMine = getPlayerCount("mining_1", skyblockPlayers);
				deepCaverns = getPlayerCount("mining_2", skyblockPlayers);
				dwarvenMines = getPlayerCount("mining_3", skyblockPlayers);
				crystalHollows = getPlayerCount("crystal_hollows", skyblockPlayers);
				spidersDen = getPlayerCount("combat_1", skyblockPlayers);
				crimsonIsle = getPlayerCount("crimson_isle", skyblockPlayers);
				kuudra = getPlayerCount("kuudra", skyblockPlayers);
				end = getPlayerCount("combat_3", skyblockPlayers);
				dungeonsHub = getPlayerCount("dungeon_hub", skyblockPlayers);
				dungeons = getPlayerCount("dungeon", skyblockPlayers);
				darkAuction = getPlayerCount("dark_auction", skyblockPlayers);
				jerry = getPlayerCount("winter", skyblockPlayers);
				rift = getPlayerCount("rift", skyblockPlayers);
			}

			player.addChatMessage(new ChatComponentText(ModConfig.getDelimiter() + "\n" +
														ModConfig.getColour(ModConfig.typeColour) + " Hypixel: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(totalPlayers) + "\n" +
														getOutput("Skyblock", skyblockTotalPlayers, totalPlayers) +
														getOutput("Private Island", privateIsland, skyblockTotalPlayers) +
														getOutput("Hub", hub, skyblockTotalPlayers) +
														getOutput("Dark Auction", darkAuction, skyblockTotalPlayers) +
														getOutput("The Rift", rift, skyblockTotalPlayers) +
														getOutput("Farming Islands", farmingIslands, skyblockTotalPlayers) +
														getOutput("Garden", garden, skyblockTotalPlayers) +
														getOutput("Park", park, skyblockTotalPlayers) +
														getOutput("Gold Mine", goldMine, skyblockTotalPlayers) +
														getOutput("Deep Caverns", deepCaverns, skyblockTotalPlayers) +
														getOutput("Dwarven Mines", dwarvenMines, skyblockTotalPlayers) +
														getOutput("Crystal Hollows", crystalHollows, skyblockTotalPlayers) +
														getOutput("Spider's Den", spidersDen, skyblockTotalPlayers) +
														getOutput("Crimson Isle", crimsonIsle, skyblockTotalPlayers) +
														getOutput("Kuudra", kuudra, skyblockTotalPlayers) +
														getOutput("The End", end, skyblockTotalPlayers) +
														getOutput("Dungeons Hub", dungeonsHub, skyblockTotalPlayers) +
														getOutput("Dungeons", dungeons, skyblockTotalPlayers) +
														getOutput("Jerry's Workshop", jerry, skyblockTotalPlayers) +
														ModConfig.getDelimiter()));
		}).start();
	}

	static int getPlayerCount(String location, JsonObject obj) {
		if (obj.has(location)) return obj.get(location).getAsInt();
		return 0;
	}

	static String getOutput(String name, int amount, int total) {
		return ModConfig.getColour(ModConfig.typeColour) + " " + name + ": " + ModConfig.getColour(ModConfig.valueColour) + nf.format(amount) + " / " + Utils.getPercentage(amount, total) + "%\n";
	}

}
