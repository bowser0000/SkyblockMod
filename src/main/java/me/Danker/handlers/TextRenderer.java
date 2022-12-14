package me.Danker.handlers;

import me.Danker.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;

public class TextRenderer extends Gui {
	public TextRenderer(Minecraft mc, String text, double x, double y, double scale) {
		if (text == null) return;

		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 0, -1);
		GlStateManager.scale(scale, scale, 1);

		String[] split = text.split("\n");
		for (String line : split) {
			if (ModConfig.outlineText) {
				String noColorLine = StringUtils.stripControlCodes(line);
				mc.fontRendererObj.drawString(noColorLine, (int) Math.round(x / scale) - 1, (int) Math.round(y / scale), 0x000000, false);
				mc.fontRendererObj.drawString(noColorLine, (int) Math.round(x / scale) + 1, (int) Math.round(y / scale), 0x000000, false);
				mc.fontRendererObj.drawString(noColorLine, (int) Math.round(x / scale), (int) Math.round(y / scale) - 1, 0x000000, false);
				mc.fontRendererObj.drawString(noColorLine, (int) Math.round(x / scale), (int) Math.round(y / scale) + 1, 0x000000, false);
				mc.fontRendererObj.drawString(line, (int) Math.round(x / scale), (int) Math.round(y / scale), 0xFFFFFF, false);
			} else {
				mc.fontRendererObj.drawString(line, (float) (x / scale), (float) (y / scale), 0xFFFFFF, true);
			}
			y += mc.fontRendererObj.FONT_HEIGHT * scale;
		}

		GlStateManager.popMatrix();
		GlStateManager.color(1, 1, 1, 1);
	}
}
