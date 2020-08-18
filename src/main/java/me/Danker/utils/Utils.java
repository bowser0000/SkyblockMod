package me.Danker.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import me.Danker.TheMod;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.handlers.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StringUtils;

public class Utils {
	
	public static boolean inSkyblock = false;
	
    public static int getItems(String item) {
    	Minecraft mc = Minecraft.getMinecraft();
    	EntityPlayer player = mc.thePlayer;
    	
    	double x = player.posX;
    	double y = player.posY;
    	double z = player.posZ;
    	AxisAlignedBB scan = new AxisAlignedBB(x - 6, y - 6, z - 6, x + 6, y + 6, z + 6);
    	List<EntityItem> items = mc.theWorld.getEntitiesWithinAABB(EntityItem.class, scan);
    	
    	for (EntityItem i : items) {
    		String itemName = StringUtils.stripControlCodes(i.getEntityItem().getDisplayName());
    		if (itemName.equals(item)) return i.getEntityItem().stackSize;
    	}
    	// No items found
    	return 0;
    }
    
    public static String returnGoldenEnchants(String line) {
    	Matcher matcher = TheMod.pattern.matcher(line);
    	StringBuffer out = new StringBuffer();
    	
    	while (matcher.find()) {
    		matcher.appendReplacement(out, TheMod.t6Enchants.get(matcher.group(1)));
    	}
    	matcher.appendTail(out);
    	
    	return out.toString();
    }
	
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
	
	public static void createTitle(String text, int seconds) {
		Minecraft.getMinecraft().thePlayer.playSound("random.orb", 1, (float) 0.5);
		TheMod.titleTimer = seconds * 20;
		TheMod.showTitle = true;
		TheMod.titleText = text;
	}
	
	public static void drawTitle(String text) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledResolution = new ScaledResolution(mc);
		
		int height = scaledResolution.getScaledHeight();
		int width = scaledResolution.getScaledWidth();
		int textLength = mc.fontRendererObj.getStringWidth(text);
		
		double scale = 4;
		if (textLength * scale > (width * 0.9F)) {
			scale = (width * 0.9F) / (float) textLength;
		}
		
		int titleX = (int) ((width / 2) - (textLength * scale / 2));
		int titleY = (int) ((height * 0.45) / scale);
		new TextRenderer(mc, text, titleX, titleY, scale);
	}
	
	public static void checkForSkyblock() {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
			ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
			if (scoreboardObj != null) {
				String scObjName = ScoreboardHandler.cleanSB(scoreboardObj.getDisplayName());
				if (scObjName.contains("SKYBLOCK")) {
					inSkyblock = true;
					return;
				}
			}
		}
		inSkyblock = false;
	}
	
	public static String capitalizeString(String string) {
		String[] words = string.split("_");
		
		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
		}
		
		return String.join(" ", words);
	}
	
}
