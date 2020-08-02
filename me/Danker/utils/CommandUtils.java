package me.Danker.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class CommandUtils {
	public static List<String> getMatchingPlayers(String arg) {
		List<String> matchingPlayers = new ArrayList<>();
		List<EntityPlayer> players = Minecraft.getMinecraft().theWorld.playerEntities;
		
		for (EntityPlayer player : players) {
			String playerName = player.getName();
			if (playerName.toLowerCase().startsWith(arg.toLowerCase())) {
				matchingPlayers.add(playerName);
			}
		}
		
		return matchingPlayers;
	}
}
