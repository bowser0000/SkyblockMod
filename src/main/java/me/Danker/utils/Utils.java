package me.Danker.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.lwjgl.opengl.GL11;

import me.Danker.TheMod;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.handlers.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StringUtils;

public class Utils {
	
	public static boolean inSkyblock = false;
	static int[] skillXPPerLevel = {0, 50, 125, 200, 300, 500, 750, 1000, 1500, 2000, 3500, 5000, 7500, 10000, 15000, 20000, 30000, 50000,
									75000, 100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000, 1100000,
									1200000, 1300000, 1400000, 1500000, 1600000, 1700000, 1800000, 1900000, 2000000, 2100000, 2200000,
									2300000, 2400000, 2500000, 2600000, 2750000, 2900000, 3100000, 3400000, 3700000, 4000000};
	
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
	
	public static double getPercentage(int num1, int num2) {
		if (num2 == 0) return 0D;
		double result = ((double) num1 * 100D) / (double) num2;
		result = Math.round(result * 100D) / 100D;
		return result;
	}
	
	public static void drawOnSlot(int size, int xSlotPos, int ySlotPos, int colour) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int guiLeft = (sr.getScaledWidth() - 176) / 2;
		int guiTop = (sr.getScaledHeight() - 222) / 2;
		int x = guiLeft + xSlotPos;
		int y = guiTop + ySlotPos;
		// Move down when chest isn't 6 rows
		if (size != 90) y += (6 - (size - 36) / 9) * 9;
		
		GL11.glTranslated(0, 0, 1);
		Gui.drawRect(x, y, x + 16, y + 16, colour);
		GL11.glTranslated(0, 0, -1);
	}
	
	public static String getTimeBetween(double timeOne, double timeTwo) {
		double secondsBetween = Math.floor(timeTwo - timeOne);
		
		String timeFormatted = "";
		int days;
		int hours;
		int minutes;
		int seconds;
		
		if (secondsBetween > 86400) {
			// More than 1d, display #d#h
			days = (int) (secondsBetween / 86400);
			hours = (int) (secondsBetween % 86400 / 3600);
			timeFormatted = days + "d" + hours + "h";
		} else if (secondsBetween > 3600) {
			// More than 1h, display #h#m
			hours = (int) (secondsBetween / 3600);
			minutes = (int) (secondsBetween % 3600 / 60);
			timeFormatted = hours + "h" + minutes + "m";
		} else {
			// Display #m#s
			minutes = (int) (secondsBetween / 60);
			seconds = (int) (secondsBetween % 60);
			timeFormatted = minutes + "m" + seconds + "s";
		}
		
		return timeFormatted;
	}
	
	public static String getMoneySpent(double coins) {
		double coinsSpentMillions = coins / 1000000D;
		coinsSpentMillions = Math.floor(coinsSpentMillions * 100D) / 100D;
		return coinsSpentMillions + "M";
	}
	
	public static double xpToSkillLevel(double xp) {
		for (int i = 0, xpAdded = 0; i < skillXPPerLevel.length; i++) {
			xpAdded += skillXPPerLevel[i];
			if (xp < xpAdded) {
				return (i - 1) + (xp - (xpAdded - skillXPPerLevel[i])) / skillXPPerLevel[i];
			}
		}
		return 50D;
	}
	
}
