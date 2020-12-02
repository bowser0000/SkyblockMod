package me.Danker.gui;

import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.commons.lang3.time.StopWatch;

public class SkillTrackerGui extends GuiScreen {

	private GuiButton goBack;
	private GuiButton start;
	private GuiButton stop;
	private GuiButton reset;
	private GuiButton hide;
	private GuiButton show;
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int height = sr.getScaledHeight();
		int width = sr.getScaledWidth();
		
		goBack = new GuiButton(0, 2, height - 30, 100, 20, "Go Back");
		start = new GuiButton(0, width / 2 - 140, (int) (height * 0.45), 80, 20, "Start");
		stop = new GuiButton(0, width / 2 - 40, (int) (height * 0.45), 80, 20, "Stop");
		reset = new GuiButton(0, width / 2 + 60, (int) (height * 0.45), 80, 20, "Reset");
		hide = new GuiButton(0, width / 2 - 70, (int) (height * 0.55), 60, 20, "Hide");
		show = new GuiButton(0, width / 2 + 10, (int) (height * 0.55), 60, 20, "Show");
		
		this.buttonList.add(start);
		this.buttonList.add(stop);
		this.buttonList.add(reset);
		this.buttonList.add(hide);
		this.buttonList.add(show);
		this.buttonList.add(goBack);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		String stateText = "";
		if (DankersSkyblockMod.skillStopwatch.isStarted() && !DankersSkyblockMod.skillStopwatch.isSuspended()) {
			stateText = "Timer: Running";
		} else if (!DankersSkyblockMod.skillStopwatch.isStarted() || DankersSkyblockMod.skillStopwatch.isSuspended()) {
			stateText = "Timer: Paused";
		}
		if (!DankersSkyblockMod.showSkillTracker) {
			stateText += " (Hidden)";
		}
		int stateTextWidth = mc.fontRendererObj.getStringWidth(stateText);
		new TextRenderer(mc, stateText, width / 2 - stateTextWidth / 2, 10, 1D);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (button == goBack) {
			DankersSkyblockMod.guiToOpen = "dankergui1";
		} else if (button == start) {
			if (DankersSkyblockMod.skillStopwatch.isStarted() && DankersSkyblockMod.skillStopwatch.isSuspended()) {
				DankersSkyblockMod.skillStopwatch.resume();
			} else if (!DankersSkyblockMod.skillStopwatch.isStarted()) {
				DankersSkyblockMod.skillStopwatch.start();
			}
		} else if (button == stop) {
			if (DankersSkyblockMod.skillStopwatch.isStarted() && !DankersSkyblockMod.skillStopwatch.isSuspended()) {
				DankersSkyblockMod.skillStopwatch.suspend();
			}
		} else if (button == reset) {
			DankersSkyblockMod.skillStopwatch = new StopWatch();
			DankersSkyblockMod.farmingXPGained = 0;
			DankersSkyblockMod.miningXPGained = 0;
			DankersSkyblockMod.combatXPGained = 0;
			DankersSkyblockMod.foragingXPGained = 0;
			DankersSkyblockMod.fishingXPGained = 0;
			DankersSkyblockMod.enchantingXPGained = 0;
			DankersSkyblockMod.alchemyXPGained = 0;
		} else if (button == hide) {
			DankersSkyblockMod.showSkillTracker = false;
			ConfigHandler.writeBooleanConfig("misc", "showSkillTracker", false);
		} else if (button == show) {
			DankersSkyblockMod.showSkillTracker = true;
			ConfigHandler.writeBooleanConfig("misc", "showSkillTracker", true);
		}
	}
	
}
