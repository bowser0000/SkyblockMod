package me.Danker.utils;

import me.Danker.features.CrystalHollowWaypoints;
import me.Danker.handlers.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderUtils {

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

    public static void drawTextOnSlot(int size, int xSlotPos, int ySlotPos, String text) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sr = new ScaledResolution(mc);
        int guiLeft = (sr.getScaledWidth() - 176) / 2;
        int guiTop = (sr.getScaledHeight() - 222) / 2;
        int x = guiLeft + xSlotPos;
        int y = guiTop + ySlotPos;
        // Move down when chest isn't 6 rows
        if (size != 90) y += (6 - (size - 36) / 9) * 9;

        int width = mc.fontRendererObj.getStringWidth(text);
        GL11.glTranslated(0, 0, 1);
        mc.fontRendererObj.drawString(text, x + 8 - width / 2, y + 5, 0xFFFFFF, true);
        GL11.glTranslated(0, 0, -1);
    }

    public static void draw3DLine(Vec3 pos1, Vec3 pos2, int colourInt, int lineWidth, boolean depth, float partialTicks) {
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
        GL11.glLineWidth(lineWidth);
        if (!depth) {
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GlStateManager.depthMask(false);
        }
        GlStateManager.color(colour.getRed() / 255f, colour.getGreen() / 255f, colour.getBlue() / 255f, colour.getAlpha() / 255f);
        worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);

        worldRenderer.pos(pos1.xCoord, pos1.yCoord, pos1.zCoord).endVertex();
        worldRenderer.pos(pos2.xCoord, pos2.yCoord, pos2.zCoord).endVertex();
        Tessellator.getInstance().draw();

        GlStateManager.translate(realX, realY, realZ);
        if (!depth) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GlStateManager.depthMask(true);
        }
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

    public static void draw3DString(double x, double y, double z, String text, int colour, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        double realX = (x - player.lastTickPosX) + ((x - player.posX) - (x - player.lastTickPosX)) * partialTicks;
        double realY = (y - player.lastTickPosY) + ((y - player.posY) - (y - player.lastTickPosY)) * partialTicks;
        double realZ = (z - player.lastTickPosZ) + ((z - player.posZ) - (z - player.lastTickPosZ)) * partialTicks;
        RenderManager renderManager = mc.getRenderManager();

        float f = 1.6F;
        float f1 = 0.016666668F * f;
        int width = mc.fontRendererObj.getStringWidth(text) / 2;
        GlStateManager.pushMatrix();
        GlStateManager.translate(realX, realY, realZ);
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

    // I couldnt get waypoint strings to work so in the end I just copied from NEU
    // If anyone sees this please help
	/*public static void draw3DWaypointString(CrystalHollowWaypoints.Waypoint waypoint, float partialTicks) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		BlockPos pos = waypoint.pos;
		double x = (pos.getX() - player.lastTickPosX) + ((pos.getX() - player.posX) - (pos.getX() - player.lastTickPosX)) * partialTicks;
		double y = (pos.getY() - player.lastTickPosY) + ((pos.getY() - player.posY) - (pos.getY() - player.lastTickPosY)) * partialTicks;
		double z = (pos.getZ() - player.lastTickPosZ) + ((pos.getZ() - player.posZ) - (pos.getZ() - player.lastTickPosZ)) * partialTicks;

		double distance = player.getDistance(x, y, z);
		if (distance > 12) {
			x *= 12 / distance;
			y *= 12 / distance;
			z *= 12 / distance;
		}

		RenderManager renderManager = mc.getRenderManager();
		Entity viewer = Minecraft.getMinecraft().getRenderViewEntity();

		float f = 1.6F;
		float f1 = 0.016666668F * f;
		int width = mc.fontRendererObj.getStringWidth(waypoint.location) / 2;
		int width2 = mc.fontRendererObj.getStringWidth(waypoint.getDistance(player)) / 2;
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GL11.glNormal3f(0f, 1f, 0f);
		GlStateManager.rotate(-renderManager.playerViewY, 0f, 1f, 0f);
		GlStateManager.rotate(renderManager.playerViewX, 1f, 0f, 0f);
		GlStateManager.scale(-f1, -f1, -f1);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GlStateManager.depthMask(false);

		mc.fontRendererObj.drawString(waypoint.location, -width, 0, 0x55FFFF);

		GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0, -1, 0);
		GlStateManager.rotate(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(renderManager.playerViewY, 0.0F, 1.0F, 0.0F);

		mc.fontRendererObj.drawString(waypoint.getDistance(player), -width2, 0, 0xFFFF55);

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}*/

    // https://github.com/Moulberry/NotEnoughUpdates/blob/master/src/main/java/io/github/moulberry/notenoughupdates/miscfeatures/DwarvenMinesWaypoints.java#L261
    public static void draw3DWaypointString(CrystalHollowWaypoints.Waypoint waypoint, float partialTicks) {
        GlStateManager.alphaFunc(516, 0.1F);

        GlStateManager.pushMatrix();

        Entity viewer = Minecraft.getMinecraft().getRenderViewEntity();
        double viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks;
        double viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks;
        double viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks;

        double x = waypoint.pos.getX()-viewerX+0.5f;
        double y = waypoint.pos.getY()-viewerY-viewer.getEyeHeight();
        double z = waypoint.pos.getZ()-viewerZ+0.5f;

        double distSq = x*x + y*y + z*z;
        double dist = Math.sqrt(distSq);
        if(distSq > 144) {
            x *= 12/dist;
            y *= 12/dist;
            z *= 12/dist;
        }
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0, viewer.getEyeHeight(), 0);

        renderNametag(EnumChatFormatting.AQUA + waypoint.location);

        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0, -0.25f, 0);
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);

        renderNametag(EnumChatFormatting.YELLOW + waypoint.getDistance(Minecraft.getMinecraft().thePlayer));

        GlStateManager.popMatrix();

        GlStateManager.disableLighting();
    }

    // https://github.com/Moulberry/NotEnoughUpdates/blob/master/src/main/java/io/github/moulberry/notenoughupdates/miscfeatures/DwarvenMinesWaypoints.java#L300
    public static void renderNametag(String str) {
        FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
        float f = 1.6F;
        float f1 = 0.016666668F * f;
        GlStateManager.pushMatrix();
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-f1, -f1, f1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        int i = 0;

        int j = fontrenderer.getStringWidth(str) / 2;
        GlStateManager.disableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(-j - 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(-j - 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(j + 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(j + 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127);
        GlStateManager.depthMask(true);

        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);

        GlStateManager.enableDepth();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    public static void draw3DBox(AxisAlignedBB aabb, int colourInt, float partialTicks) {
        Entity render = Minecraft.getMinecraft().getRenderViewEntity();
        Color colour = new Color(colourInt, true);

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

        RenderGlobal.drawOutlinedBoundingBox(aabb, colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getAlpha());

        GlStateManager.translate(realX, realY, realZ);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    public static void drawFilled3DBox(AxisAlignedBB aabb, int colourInt, boolean translucent, boolean depth, float partialTicks) {
        Entity render = Minecraft.getMinecraft().getRenderViewEntity();
        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        Color colour = new Color(colourInt);

        double realX = render.lastTickPosX + (render.posX - render.lastTickPosX) * partialTicks;
        double realY = render.lastTickPosY + (render.posY - render.lastTickPosY) * partialTicks;
        double realZ = render.lastTickPosZ + (render.posZ - render.lastTickPosZ) * partialTicks;

        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.translate(-realX, -realY, -realZ);
        GlStateManager.disableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.tryBlendFuncSeparate(770, translucent ? 1 : 771, 1, 0);
        if (!depth) {
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GlStateManager.depthMask(false);
        }
        GlStateManager.color(colour.getRed() / 255f, colour.getGreen() / 255f, colour.getBlue() / 255f, colour.getAlpha() / 255f);
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        // Bottom
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        // Top
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        // West
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        // East
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        // North
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        // South
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        Tessellator.getInstance().draw();

        GlStateManager.translate(realX, realY, realZ);
        if (!depth) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GlStateManager.depthMask(true);
        }
        GlStateManager.enableCull();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    public static void renderItem(ItemStack stack, int x, int y, double scale) {
        renderItem(stack, x, y, 0, scale);
    }

    public static void renderItem(ItemStack stack, int x, int y, int z, double scale) {
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(scale, scale, scale);

        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, 0, 0);

        GlStateManager.popMatrix();

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }

    public static void drawCenteredText(String text, int screenWidth, int height, double scale) {
        Minecraft mc = Minecraft.getMinecraft();
        int textWidth = mc.fontRendererObj.getStringWidth(text);
        new TextRenderer(mc, text, screenWidth / 2 - textWidth / 2, height, scale);
    }

    public static void drawModalRectWithCustomSizedTexture(double x, double y, float u, float v, int width, int height, float textureWidth, float textureHeight)
    {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0D).tex(u * f, (v + (float)height) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0D).tex((u + (float)width) * f, (v + (float)height) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0D).tex((u + (float)width) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

}
