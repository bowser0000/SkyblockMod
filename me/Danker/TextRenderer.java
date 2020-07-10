package me.Danker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class TextRenderer extends Gui {
	public TextRenderer(Minecraft mc, String text, int x, int y, int colour) {
		drawString(mc.fontRendererObj, text, x, y, colour);
	}
}
