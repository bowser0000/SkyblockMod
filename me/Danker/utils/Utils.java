package me.Danker.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import me.Danker.TheMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StringUtils;

public class Utils {
	
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
	
}
