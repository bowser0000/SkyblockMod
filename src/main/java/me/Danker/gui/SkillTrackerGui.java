package me.Danker.gui;

import me.Danker.features.SkillTracker;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.RenderUtils;
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
		if (SkillTracker.skillStopwatch.isStarted() && !SkillTracker.skillStopwatch.isSuspended()) {
			stateText = "Timer: Running";
		} else if (!SkillTracker.skillStopwatch.isStarted() || SkillTracker.skillStopwatch.isSuspended()) {
			stateText = "Timer: Paused";
		}
		if (!SkillTracker.showSkillTracker) {
			stateText += " (Hidden)";
		}
		RenderUtils.drawCenteredText(stateText, width, 10, 1D);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (button == goBack) {
			mc.displayGuiScreen(new DankerGui(1, ""));
		} else if (button == start) {
			if (SkillTracker.skillStopwatch.isStarted() && SkillTracker.skillStopwatch.isSuspended()) {
				SkillTracker.skillStopwatch.resume();
			} else if (!SkillTracker.skillStopwatch.isStarted()) {
				SkillTracker.skillStopwatch.start();
			}
		} else if (button == stop) {
			if (SkillTracker.skillStopwatch.isStarted() && !SkillTracker.skillStopwatch.isSuspended()) {
				SkillTracker.skillStopwatch.suspend();
			}
		} else if (button == reset) {
			SkillTracker.skillStopwatch = new StopWatch();
			SkillTracker.farmingXPGained = 0;
			SkillTracker.miningXPGained = 0;
			SkillTracker.combatXPGained = 0;
			SkillTracker.foragingXPGained = 0;
			SkillTracker.fishingXPGained = 0;
			SkillTracker.enchantingXPGained = 0;
			SkillTracker.alchemyXPGained = 0;
		} else if (button == hide) {
			SkillTracker.showSkillTracker = false;
			ConfigHandler.writeBooleanConfig("misc", "showSkillTracker", false);
		} else if (button == show) {
			SkillTracker.showSkillTracker = true;
			ConfigHandler.writeBooleanConfig("misc", "showSkillTracker", true);
		}
	}
	
}
