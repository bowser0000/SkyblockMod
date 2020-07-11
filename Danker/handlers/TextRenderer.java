package me.Danker.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class TextRenderer extends Gui {
	public TextRenderer(Minecraft mc, String text, int x, int y, int colour) {
		y -= mc.fontRendererObj.FONT_HEIGHT;
		for (String line : text.split("\n")) {
			drawString(mc.fontRendererObj, line, x, y += mc.fontRendererObj.FONT_HEIGHT, colour);
		}
	}
}
