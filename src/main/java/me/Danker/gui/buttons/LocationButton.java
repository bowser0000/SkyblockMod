package me.Danker.gui.buttons;

import me.Danker.handlers.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

public class LocationButton extends GuiButton {

	private int x;
	private int y;
	private double scale;
	private String text;
	private String text2;
	private Integer text2Offset;
	
	public LocationButton(int buttonId, int x, int y, double width, double height, double scale, String text, String text2, Integer text2Offset) {
		super(buttonId, x, y, text);
		this.x = x;
		this.y = y;
		this.width = (int) width;
		this.height = (int) height;
		this.scale = scale;
		this.text = text;
		this.text2 = text2;
		this.text2Offset = text2Offset;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		String[] splitText;
		if (text2 == null) {
			splitText = text.split("\n");
		} else {
			splitText = text2.split("\n");
		}

		int longestText = -1;
		for (String s : splitText) {
			int stringLength = mc.fontRendererObj.getStringWidth(s);
			if (stringLength > longestText) {
				longestText = stringLength;
			}
		}
		
		if (text2 == null) {
			drawRect(x - 2, y - 2, (int) (x + longestText * scale + 3), (int) (y + (splitText.length * 9 + 3) * scale), 0x40D3D3D3);
		} else {
			drawRect(x - 2, y - 2, (int) (x + (longestText + text2Offset) * scale + 3), (int) (y + (splitText.length * 9 + 3) * scale), 0x40D3D3D3);
			new TextRenderer(mc, text2, (int) (x + (text2Offset * scale)), y, scale);
		}
		new TextRenderer(mc, text, x, y, scale);
	}
	
	@Override
	public void playPressSound(SoundHandler soundHandler) {
		
	}
	
}
