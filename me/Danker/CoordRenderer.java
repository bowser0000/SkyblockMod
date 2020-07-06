package me.Danker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class CoordRenderer extends Gui {
	public CoordRenderer(Minecraft mc, String text) {
		ScaledResolution scaled = new ScaledResolution(mc);
		int height = scaled.getScaledHeight();
		drawString(mc.fontRendererObj, text, 5, height - 25, Integer.parseInt("FFFFFF", 16));
	}
}
