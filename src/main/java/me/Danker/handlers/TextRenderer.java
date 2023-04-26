package me.Danker.handlers;

import me.Danker.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;

public class TextRenderer extends Gui {

	static Minecraft mc = Minecraft.getMinecraft();
	static FontRenderer fr = mc.fontRendererObj;

	public static void drawText(String text, double x, double y, double scale) {
		if (text == null) return;

		GlStateManager.pushMatrix();
		GlStateManager.scale(scale, scale, 1);

		String[] split = text.split("\n");
		for (String line : split) {
			if (ModConfig.outlineText) {
				String noColorLine = StringUtils.stripControlCodes(line);
				fr.drawString(noColorLine, (int) Math.round(x / scale) - 1, (int) Math.round(y / scale), 0x000000, false);
				fr.drawString(noColorLine, (int) Math.round(x / scale) + 1, (int) Math.round(y / scale), 0x000000, false);
				fr.drawString(noColorLine, (int) Math.round(x / scale), (int) Math.round(y / scale) - 1, 0x000000, false);
				fr.drawString(noColorLine, (int) Math.round(x / scale), (int) Math.round(y / scale) + 1, 0x000000, false);
				fr.drawString(line, (int) Math.round(x / scale), (int) Math.round(y / scale), 0xFFFFFF, false);
			} else {
				fr.drawString(line, (float) (x / scale), (float) (y / scale), 0xFFFFFF, true);
			}
			y += fr.FONT_HEIGHT * scale;
		}

		GlStateManager.popMatrix();
		GlStateManager.color(1, 1, 1, 1);
	}

	public static void drawHUDText(String text, double x, double y, double scale) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 0, -1);
		drawText(text, x, y, scale);
		GlStateManager.popMatrix();
	}

}
