package me.Danker.handlers;

import me.Danker.commands.ToggleCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class TextRenderer extends Gui {
	public TextRenderer(Minecraft mc, String text, double x, double y, double scale) {
		double scaleReset = Math.pow(scale, -1);
		
		GL11.glScaled(scale, scale, scale);
		for (String line : text.split("\n")) {
			if (ToggleCommand.outlineTextToggled) {
				String noColorLine = StringUtils.stripControlCodes(line);
				mc.fontRendererObj.drawString(noColorLine, (float) (x / scale - 1), (float) (y / scale), 0x000000, false);
				mc.fontRendererObj.drawString(noColorLine, (float) (x / scale + 1), (float) (y / scale), 0x000000, false);
				mc.fontRendererObj.drawString(noColorLine, (float) (x / scale), (float) (y / scale - 1), 0x000000, false);
				mc.fontRendererObj.drawString(noColorLine, (float) (x / scale), (float) (y / scale + 1), 0x000000, false);
				mc.fontRendererObj.drawString(line, (float) (x / scale), (float) (y / scale), 0xFFFFFF, false);
			} else {
				mc.fontRendererObj.drawString(line, (float) (x / scale), (float) (y / scale), 0xFFFFFF, true);
			}
			y += mc.fontRendererObj.FONT_HEIGHT * scale;
		}
		GL11.glScaled(scaleReset, scaleReset, scaleReset);
		GlStateManager.color(1, 1, 1, 1);
	}
}
