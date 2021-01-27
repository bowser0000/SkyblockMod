package me.Danker.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.handlers.TextRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;

public class Utils {
	
	public static boolean inSkyblock = false;
	public static boolean inDungeons = false;
	public static int[] skillXPPerLevel = {0, 50, 125, 200, 300, 500, 750, 1000, 1500, 2000, 3500, 5000, 7500, 10000, 15000, 20000, 30000, 50000,
										   75000, 100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000, 1100000,
										   1200000, 1300000, 1400000, 1500000, 1600000, 1700000, 1800000, 1900000, 2000000, 2100000, 2200000,
										   2300000, 2400000, 2500000, 2600000, 2750000, 2900000, 3100000, 3400000, 3700000, 4000000, 4300000,
										   4600000, 4900000, 5200000, 5500000, 5800000, 6100000, 6400000, 6700000, 7000000};
	static int[] dungeonsXPPerLevel = {0, 50, 75, 110, 160, 230, 330, 470, 670, 950, 1340, 1890, 2665, 3760, 5260, 7380, 10300, 14400,
									  20000, 27600, 38000, 52500, 71500, 97000, 132000, 180000, 243000, 328000, 445000, 600000, 800000,
									  1065000, 1410000, 1900000, 2500000, 3300000, 4300000, 5600000, 7200000, 9200000, 12000000, 15000000,
									  19000000, 24000000, 30000000, 38000000, 48000000, 60000000, 75000000, 93000000, 116250000};
	static int[] expertiseKills = {50, 100, 250, 500, 1000, 2500, 5500, 10000, 15000};

	static double lastAPITime= 0;
	static JsonObject lowestBINJson;
	
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

	//A portion of the following code was adapted from @Moulberry (https://github.com/Moulberry/)
	public static String getSBItemID(ItemStack stack) {
		if(stack == null) return null;
		NBTTagCompound tag = stack.getTagCompound();

		String sbItemID = null;
		if(tag != null && tag.hasKey("ExtraAttributes", 10)) {
			NBTTagCompound ea = tag.getCompoundTag("ExtraAttributes");

			if(ea.hasKey("id", 8)) {
				sbItemID = ea.getString("id").replaceAll(":", "-");
			}

			if(sbItemID.equals("PET")) {
				String petInfo = ea.getString("petInfo");
				if(petInfo.length() > 0) {
					JsonObject petInfoObject = new Gson().fromJson(petInfo, JsonObject.class);
					String type = petInfoObject.get("type").getAsString();
					String tier = petInfoObject.get("tier").getAsString();
					sbItemID += "-" + type + "-" + tier;
				}
			}
			if(sbItemID.equals("ENCHANTED_BOOK")) {
				NBTTagCompound enchantments = ea.getCompoundTag("enchantments");
				if (enchantments.getKeySet().size() <= 2) {
					ArrayList sortedEnchants = new ArrayList();
					for(String enchant : enchantments.getKeySet()) {
						sortedEnchants.add(enchant.toUpperCase() + enchantments.getInteger(enchant));
					}
					Collections.sort(sortedEnchants);
					for(Object enchant : sortedEnchants) {
						sbItemID += "-" + enchant;
					}
				} else {
					sbItemID += "-" + enchantments.getKeySet().size() + "ENCHANTS";
				}

			}
			if(sbItemID.equals("POTION")) {
				NBTTagCompound display = tag.getCompoundTag("display");
				sbItemID= display.getString("Name").replaceAll("§.", "").replaceAll(" ", "_").toUpperCase();
			}
		}

		return sbItemID;
	}

	public static int getLowestBin(String sbItemID) {
		if (System.currentTimeMillis() - lastAPITime >= 60000) {
			//If json hasn't been updated for over a minute since last chest opened, get a new copy from server
			lowestBINJson = APIHandler.getResponse("http://dsm.quantizr.repl.co/lowestbin.json");
		}
		try {
			int lowestBin = lowestBINJson.get(sbItemID).getAsInt();
			//Reset timer after an item value is successfully queried, otherwise immediately allow for new query
			lastAPITime = System.currentTimeMillis();
			return lowestBin;
		} catch (Exception e) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(sbItemID + " not found in API, chest profit will not be accurate"));
			return 0;
		}
	}

    public static String returnGoldenEnchants(String line) {
    	Matcher matcher = DankersSkyblockMod.t6EnchantPattern.matcher(line);
    	StringBuffer out = new StringBuffer();
    	
    	while (matcher.find()) {
    		matcher.appendReplacement(out, DankersSkyblockMod.t6Enchants.get(matcher.group(1)));
    	}
    	matcher.appendTail(out);
    	
    	return out.toString();
    }
	
	public static List<String> getMatchingPlayers(String arg) {
		List<String> matchingPlayers = new ArrayList<>();
		Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
		
		for (NetworkPlayerInfo player : players) {
			String playerName = player.getGameProfile().getName();
			if (playerName.startsWith("!")) continue; // New tablist
			if (playerName.toLowerCase().startsWith(arg.toLowerCase())) {
				matchingPlayers.add(playerName);
			}
		}
		
		return matchingPlayers;
	}
	
	public static void createTitle(String text, int seconds) {
		Minecraft.getMinecraft().thePlayer.playSound("random.orb", 1, (float) 0.5);
		DankersSkyblockMod.titleTimer = seconds * 20;
		DankersSkyblockMod.showTitle = true;
		DankersSkyblockMod.titleText = text;
	}
	
	public static void drawTitle(String text) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledResolution = new ScaledResolution(mc);
		
		int height = scaledResolution.getScaledHeight();
		int width = scaledResolution.getScaledWidth();
		int drawHeight = 0;
		String[] splitText = text.split("\n");
		for (String title : splitText) {
			int textLength = mc.fontRendererObj.getStringWidth(title);

			double scale = 4;
			if (textLength * scale > (width * 0.9F)) {
				scale = (width * 0.9F) / (float) textLength;
			}

			int titleX = (int) ((width / 2) - (textLength * scale / 2));
			int titleY = (int) ((height * 0.45) / scale) + (int) (drawHeight * scale);
			new TextRenderer(mc, title, titleX, titleY, scale);
			drawHeight += mc.fontRendererObj.FONT_HEIGHT;
		}
	}

	public static boolean isOnHypixel () {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
			return mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
		}
		return false;
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

	public static void checkForDungeons() {
		if (inSkyblock) {
			List<String> scoreboard = ScoreboardHandler.getSidebarLines();
			for (String s : scoreboard) {
				String sCleaned = ScoreboardHandler.cleanSB(s);
				if (sCleaned.contains("The Catacombs")) {
					inDungeons = true;
					return;
				}
			}
		}
		inDungeons = false;
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
		
		String timeFormatted;
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
	
	public static double xpToSkillLevel(double xp, int limit) {
		for (int i = 0, xpAdded = 0; i < limit + 1; i++) {
			xpAdded += skillXPPerLevel[i];
			if (xp < xpAdded) {
				return (i - 1) + (xp - (xpAdded - skillXPPerLevel[i])) / skillXPPerLevel[i];
			}
		}
		return limit;
	}
	
	public static double xpToDungeonsLevel(double xp) {
		for (int i = 0, xpAdded = 0; i < dungeonsXPPerLevel.length; i++) {
			xpAdded += dungeonsXPPerLevel[i];
			if (xp < xpAdded) {
				double level =  (i - 1) + (xp - (xpAdded - dungeonsXPPerLevel[i])) / dungeonsXPPerLevel[i];
				return (double) Math.round(level * 100) / 100;
			}
		}
		return 50D;
	}
	
	public static int expertiseKillsLeft(int kills) {
		for (int i = 0; i < expertiseKills.length; i++) {
			if (kills < expertiseKills[i]) {
				return expertiseKills[i] - kills;
			}
		}
		return -1;
	}
	
	public static int getPastXpEarned(int currentLevelXp, int limit) {
		if (currentLevelXp == 0) {
			int xpAdded = 0;
			for (int i = 1; i <= limit; i++) {
				xpAdded += skillXPPerLevel[i];
			}
			return xpAdded;
		}
		for (int i = 1, xpAdded = 0; i <= limit; i++) {
			xpAdded += skillXPPerLevel[i - 1];
			if (currentLevelXp == skillXPPerLevel[i]) return xpAdded;
		}
		return 0;
	}
	
	public static String getColouredBoolean(boolean bool) {
		return bool ? EnumChatFormatting.GREEN + "On" : EnumChatFormatting.RED + "Off";
	}

	//Taken from SkyblockAddons
	public static List<String> getItemLore(ItemStack itemStack) {
		final int NBT_INTEGER = 3;
		final int NBT_STRING = 8;
		final int NBT_LIST = 9;
		final int NBT_COMPOUND = 10;

		if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("display", NBT_COMPOUND)) {
			NBTTagCompound display = itemStack.getTagCompound().getCompoundTag("display");

			if (display.hasKey("Lore", NBT_LIST)) {
				NBTTagList lore = display.getTagList("Lore", NBT_STRING);

				List<String> loreAsList = new ArrayList<>();
				for (int lineNumber = 0; lineNumber < lore.tagCount(); lineNumber++) {
					loreAsList.add(lore.getStringTagAt(lineNumber));
				}

				return Collections.unmodifiableList(loreAsList);
			}
		}

		return Collections.emptyList();
	}

	public static boolean hasRightClickAbility(ItemStack itemStack) {
		return Utils.getItemLore(itemStack).stream().anyMatch(line->{
			String stripped = StringUtils.stripControlCodes(line);
			return stripped.startsWith("Item Ability:") && stripped.endsWith("RIGHT CLICK");
		});
	}

	//Taken from NEU https://github.com/Moulberry/NotEnoughUpdates/
	private static final ResourceLocation beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");

	public static void renderBeaconBeam(double x, double y, double z, int rgb, float alphaMult, float partialTicks) {
		int height = 300;
		int bottomOffset = 0;
		int topOffset = bottomOffset + height;

		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();

		Minecraft.getMinecraft().getTextureManager().bindTexture(beaconBeam);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableCull();
		GlStateManager.enableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

		double time = Minecraft.getMinecraft().theWorld.getTotalWorldTime() + (double)partialTicks;
		double d1 = MathHelper.func_181162_h(-time * 0.2D - (double)MathHelper.floor_double(-time * 0.1D));

		float r = ((rgb >> 16) & 0xFF) / 255f;
		float g = ((rgb >> 8) & 0xFF) / 255f;
		float b = (rgb & 0xFF) / 255f;
		double d2 = time * 0.025D * -1.5D;
		double d4 = 0.5D + Math.cos(d2 + 2.356194490192345D) * 0.2D;
		double d5 = 0.5D + Math.sin(d2 + 2.356194490192345D) * 0.2D;
		double d6 = 0.5D + Math.cos(d2 + (Math.PI / 4D)) * 0.2D;
		double d7 = 0.5D + Math.sin(d2 + (Math.PI / 4D)) * 0.2D;
		double d8 = 0.5D + Math.cos(d2 + 3.9269908169872414D) * 0.2D;
		double d9 = 0.5D + Math.sin(d2 + 3.9269908169872414D) * 0.2D;
		double d10 = 0.5D + Math.cos(d2 + 5.497787143782138D) * 0.2D;
		double d11 = 0.5D + Math.sin(d2 + 5.497787143782138D) * 0.2D;
		double d14 = -1.0D + d1;
		double d15 = (double)(height) * 2.5D + d14;
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		worldrenderer.pos(x + d4, y + topOffset, z + d5).tex(1.0D, d15).color(r, g, b, 1.0F*alphaMult).endVertex();
		worldrenderer.pos(x + d4, y + bottomOffset, z + d5).tex(1.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d6, y + bottomOffset, z + d7).tex(0.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d6, y + topOffset, z + d7).tex(0.0D, d15).color(r, g, b, 1.0F*alphaMult).endVertex();
		worldrenderer.pos(x + d10, y + topOffset, z + d11).tex(1.0D, d15).color(r, g, b, 1.0F*alphaMult).endVertex();
		worldrenderer.pos(x + d10, y + bottomOffset, z + d11).tex(1.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d8, y + bottomOffset, z + d9).tex(0.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d8, y + topOffset, z + d9).tex(0.0D, d15).color(r, g, b, 1.0F*alphaMult).endVertex();
		worldrenderer.pos(x + d6, y + topOffset, z + d7).tex(1.0D, d15).color(r, g, b, 1.0F*alphaMult).endVertex();
		worldrenderer.pos(x + d6, y + bottomOffset, z + d7).tex(1.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d10, y + bottomOffset, z + d11).tex(0.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d10, y + topOffset, z + d11).tex(0.0D, d15).color(r, g, b, 1.0F*alphaMult).endVertex();
		worldrenderer.pos(x + d8, y + topOffset, z + d9).tex(1.0D, d15).color(r, g, b, 1.0F*alphaMult).endVertex();
		worldrenderer.pos(x + d8, y + bottomOffset, z + d9).tex(1.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d4, y + bottomOffset, z + d5).tex(0.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d4, y + topOffset, z + d5).tex(0.0D, d15).color(r, g, b, 1.0F*alphaMult).endVertex();
		tessellator.draw();

		GlStateManager.disableCull();
		double d12 = -1.0D + d1;
		double d13 = height + d12;

		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		worldrenderer.pos(x + 0.2D, y + topOffset, z + 0.2D).tex(1.0D, d13).color(r, g, b, 0.25F*alphaMult).endVertex();
		worldrenderer.pos(x + 0.2D, y + bottomOffset, z + 0.2D).tex(1.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.8D, y + bottomOffset, z + 0.2D).tex(0.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.8D, y + topOffset, z + 0.2D).tex(0.0D, d13).color(r, g, b, 0.25F*alphaMult).endVertex();
		worldrenderer.pos(x + 0.8D, y + topOffset, z + 0.8D).tex(1.0D, d13).color(r, g, b, 0.25F*alphaMult).endVertex();
		worldrenderer.pos(x + 0.8D, y + bottomOffset, z + 0.8D).tex(1.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.2D, y + bottomOffset, z + 0.8D).tex(0.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.2D, y + topOffset, z + 0.8D).tex(0.0D, d13).color(r, g, b, 0.25F*alphaMult).endVertex();
		worldrenderer.pos(x + 0.8D, y + topOffset, z + 0.2D).tex(1.0D, d13).color(r, g, b, 0.25F*alphaMult).endVertex();
		worldrenderer.pos(x + 0.8D, y + bottomOffset, z + 0.2D).tex(1.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.8D, y + bottomOffset, z + 0.8D).tex(0.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.8D, y + topOffset, z + 0.8D).tex(0.0D, d13).color(r, g, b, 0.25F*alphaMult).endVertex();
		worldrenderer.pos(x + 0.2D, y + topOffset, z + 0.8D).tex(1.0D, d13).color(r, g, b, 0.25F*alphaMult).endVertex();
		worldrenderer.pos(x + 0.2D, y + bottomOffset, z + 0.8D).tex(1.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.2D, y + bottomOffset, z + 0.2D).tex(0.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.2D, y + topOffset, z + 0.2D).tex(0.0D, d13).color(r, g, b, 0.25F*alphaMult).endVertex();
		tessellator.draw();
	}

	public static void draw3DLine(Vec3 pos1, Vec3 pos2, int colourInt, float partialTicks) {
		Entity render = Minecraft.getMinecraft().getRenderViewEntity();
		WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
		Color colour = new Color(colourInt);
		
		double realX = render.lastTickPosX + (render.posX - render.lastTickPosX) * partialTicks;
		double realY = render.lastTickPosY + (render.posY - render.lastTickPosY) * partialTicks;
		double realZ = render.lastTickPosZ + (render.posZ - render.lastTickPosZ) * partialTicks;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(-realX, -realY, -realZ);
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glLineWidth(2);
		GlStateManager.color(colour.getRed() / 255f, colour.getGreen() / 255f, colour.getBlue() / 255f, colour.getAlpha() / 255f);
		worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
		
		worldRenderer.pos(pos1.xCoord, pos1.yCoord, pos1.zCoord).endVertex();
		worldRenderer.pos(pos2.xCoord, pos2.yCoord, pos2.zCoord).endVertex();
		Tessellator.getInstance().draw();

		GlStateManager.translate(realX, realY, realZ);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}
	
	public static void draw3DString(BlockPos pos, String text, int colour, float partialTicks) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		double x = (pos.getX() - player.lastTickPosX) + ((pos.getX() - player.posX) - (pos.getX() - player.lastTickPosX)) * partialTicks;
		double y = (pos.getY() - player.lastTickPosY) + ((pos.getY() - player.posY) - (pos.getY() - player.lastTickPosY)) * partialTicks;
		double z = (pos.getZ() - player.lastTickPosZ) + ((pos.getZ() - player.posZ) - (pos.getZ() - player.lastTickPosZ)) * partialTicks;
		RenderManager renderManager = mc.getRenderManager();
		
		float f = 1.6F;
		float f1 = 0.016666668F * f;
		int width = mc.fontRendererObj.getStringWidth(text) / 2;
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GL11.glNormal3f(0f, 1f, 0f);
		GlStateManager.rotate(-renderManager.playerViewY, 0f, 1f, 0f);
		GlStateManager.rotate(renderManager.playerViewX, 1f, 0f, 0f);
		GlStateManager.scale(-f1, -f1, -f1);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		mc.fontRendererObj.drawString(text, -width, 0, colour);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
	
	// Yoinked from ForgeHax
	public static void draw3DBox(AxisAlignedBB aabb, int colourInt, float partialTicks) {
		Entity render = Minecraft.getMinecraft().getRenderViewEntity();
		WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
		Color colour = new Color(colourInt);
		
		double realX = render.lastTickPosX + (render.posX - render.lastTickPosX) * partialTicks;
		double realY = render.lastTickPosY + (render.posY - render.lastTickPosY) * partialTicks;
		double realZ = render.lastTickPosZ + (render.posZ - render.lastTickPosZ) * partialTicks;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(-realX, -realY, -realZ);
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glLineWidth(2);
		GlStateManager.color(colour.getRed() / 255f, colour.getGreen() / 255f, colour.getBlue() / 255f, colour.getAlpha() / 255f);
		worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
		
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
		Tessellator.getInstance().draw();
		worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
		Tessellator.getInstance().draw();
		worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
		Tessellator.getInstance().draw();
		
		GlStateManager.translate(realX, realY, realZ);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}

	public static void renderItem(ItemStack item, float x, float y, float z) {

		GlStateManager.enableRescaleNormal();
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableDepth();

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(item, 0, 0);
		GlStateManager.popMatrix();

		GlStateManager.disableDepth();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
	}
	
	public static BlockPos getFirstBlockPosAfterVectors(Minecraft mc, Vec3 pos1, Vec3 pos2, int strength, int distance) {
		double x = pos2.xCoord - pos1.xCoord;
		double y = pos2.yCoord - pos1.yCoord;
		double z = pos2.zCoord - pos1.zCoord;
		
		for (int i = strength; i < distance * strength; i++) { // Start at least 1 strength away
			double newX = pos1.xCoord + ((x / strength) * i);
			double newY = pos1.yCoord + ((y / strength) * i);
			double newZ = pos1.zCoord + ((z / strength) * i);
			
			BlockPos newBlock = new BlockPos(newX, newY, newZ);
			if (mc.theWorld.getBlockState(newBlock).getBlock() != Blocks.air) {
				return newBlock;
			}
		}
		
		return null;
	}
	
	public static BlockPos getNearbyBlock(Minecraft mc, BlockPos pos, Block... blockTypes) {
		if (pos == null) return null;
		BlockPos pos1 = new BlockPos(pos.getX() - 2, pos.getY() - 3, pos.getZ() - 2);
		BlockPos pos2 = new BlockPos(pos.getX() + 2, pos.getY() + 3, pos.getZ() + 2);
		
		BlockPos closestBlock = null;
		double closestBlockDistance = 99;
		Iterable<BlockPos> blocks = BlockPos.getAllInBox(pos1, pos2);
		
		for (BlockPos block : blocks) {
			for (Block blockType : blockTypes) {
				if (mc.theWorld.getBlockState(block).getBlock() == blockType && block.distanceSq(pos) < closestBlockDistance) {
					closestBlock = block;
					closestBlockDistance = block.distanceSq(pos);
				}
			}
		}
		
		return closestBlock;
	}
	
	public static BlockPos getBlockUnderItemFrame(EntityItemFrame itemFrame) {
		switch (itemFrame.facingDirection) {
			case NORTH:
				return new BlockPos(itemFrame.posX, itemFrame.posY, itemFrame.posZ + 1);
			case EAST:
				return new BlockPos(itemFrame.posX - 1, itemFrame.posY, itemFrame.posZ - 0.5);
			case SOUTH:
				return new BlockPos(itemFrame.posX, itemFrame.posY, itemFrame.posZ - 1);
			case WEST:
				return new BlockPos(itemFrame.posX + 1, itemFrame.posY, itemFrame.posZ - 0.5);
			default:
				return null;
		}
	}
	
}
