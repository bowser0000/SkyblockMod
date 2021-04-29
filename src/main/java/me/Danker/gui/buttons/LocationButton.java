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
	private int longestText;
	
	public LocationButton(int x, int y, double scale, String text, String text2, Integer text2Offset) {
		super(0, x, y, text);
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.text = text;
		this.text2 = text2;
		this.text2Offset = text2Offset;

		String[] splitText;
		if (text2 == null) {
			splitText = text.split("\n");
		} else {
			splitText = text2.split("\n");
		}

		int longestText = -1;
		for (String s : splitText) {
			int stringLength = Minecraft.getMinecraft().fontRendererObj.getStringWidth(s);
			if (stringLength > longestText) {
				longestText = stringLength;
			}
		}

		this.longestText = longestText;
		this.height = (int) ((splitText.length * 9 + 3) * scale);
		this.width = (int) ((this.longestText + 3) * scale);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (text2 == null) {
			drawRect(x - 2, y - 2, x + width, y + height, 0x40D3D3D3);
		} else {
			drawRect(x - 2, y - 2, (int) (x + (longestText + text2Offset + 3) * scale), y + height, 0x40D3D3D3);
			new TextRenderer(mc, text2, (int) (x + (text2Offset * scale)), y, scale);
		}
		new TextRenderer(mc, text, x, y, scale);
	}
	
	@Override
	public void playPressSound(SoundHandler soundHandler) {
		
	}
	
}
