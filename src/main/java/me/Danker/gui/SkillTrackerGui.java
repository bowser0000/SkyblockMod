package me.Danker.gui;

import org.apache.commons.lang3.time.StopWatch;

import me.Danker.TheMod;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

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
		if (TheMod.skillStopwatch.isStarted() && !TheMod.skillStopwatch.isSuspended()) {
			stateText = "Timer: Running";
		} else if (!TheMod.skillStopwatch.isStarted() || TheMod.skillStopwatch.isSuspended()) {
			stateText = "Timer: Paused";
		}
		if (!TheMod.showSkillTracker) {
			stateText += " (Hidden)";
		}
		int stateTextWidth = mc.fontRendererObj.getStringWidth(stateText);
		new TextRenderer(mc, stateText, width / 2 - stateTextWidth / 2, 10, 1D);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (button == goBack) {
			TheMod.guiToOpen = "dankergui1";
		} else if (button == start) {
			if (TheMod.skillStopwatch.isStarted() && TheMod.skillStopwatch.isSuspended()) {
				TheMod.skillStopwatch.resume(); 
			} else if (!TheMod.skillStopwatch.isStarted()) {
				TheMod.skillStopwatch.start();
			}
		} else if (button == stop) {
			if (TheMod.skillStopwatch.isStarted() && !TheMod.skillStopwatch.isSuspended()) {
				TheMod.skillStopwatch.suspend();
			}
		} else if (button == reset) {
			TheMod.skillStopwatch = new StopWatch();
			TheMod.farmingXPGained = 0;
			TheMod.miningXPGained = 0;
			TheMod.combatXPGained = 0;
			TheMod.foragingXPGained = 0;
			TheMod.fishingXPGained = 0;
			TheMod.enchantingXPGained = 0;
			TheMod.alchemyXPGained = 0;
		} else if (button == hide) {
			TheMod.showSkillTracker = false;
			ConfigHandler.writeBooleanConfig("misc", "showSkillTracker", false);
		} else if (button == show) {
			TheMod.showSkillTracker = true;
			ConfigHandler.writeBooleanConfig("misc", "showSkillTracker", true);
		}
	}
	
}
