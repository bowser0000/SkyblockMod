package me.Danker.handlers;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class TextRenderer extends Gui {
	public TextRenderer(Minecraft mc, String text, int x, int y, double scale) {
		double scaleReset = (double) Math.pow(scale, -1);
		
		GL11.glScaled(scale, scale, scale);
		y -= mc.fontRendererObj.FONT_HEIGHT;
		for (String line : text.split("\n")) {
			y += mc.fontRendererObj.FONT_HEIGHT * scale;
			drawString(mc.fontRendererObj, line, (int) Math.round(x / scale), (int) Math.round(y / scale), 0xFFFFFF);
		}
		GL11.glScaled(scaleReset, scaleReset, scaleReset);
	}
}
