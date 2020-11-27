package me.Danker.gui;

import me.Danker.TheMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class PuzzleSolversGui extends GuiScreen {

	private GuiButton goBack;
	private GuiButton riddle;
	private GuiButton trivia;
	private GuiButton blaze;
	private GuiButton onlyShowCorrectBlaze;
	private GuiButton creeper;
	private GuiButton water;
	
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
		riddle = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Riddle Solver: " + Utils.getColouredBoolean(ToggleCommand.threeManToggled));
		trivia = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Trivia Solver: " + Utils.getColouredBoolean(ToggleCommand.oruoToggled));
		blaze = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Blaze Solver: " + Utils.getColouredBoolean(ToggleCommand.blazeToggled));
		onlyShowCorrectBlaze = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Only Show Correct Blaze Hitbox: " + Utils.getColouredBoolean(ToggleCommand.onlyShowCorrectBlazeToggled));
		creeper = new GuiButton(0, width / 2 - 100, (int) (height * 0.5), "Creeper Solver: " + Utils.getColouredBoolean(ToggleCommand.creeperToggled));
		water = new GuiButton(0, width / 2 - 100, (int) (height * 0.6), "Water Solver: " + Utils.getColouredBoolean(ToggleCommand.waterToggled));
		
		this.buttonList.add(goBack);
		this.buttonList.add(riddle);
		this.buttonList.add(trivia);
		this.buttonList.add(blaze);
		this.buttonList.add(onlyShowCorrectBlaze);
		this.buttonList.add(creeper);
		this.buttonList.add(water);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (button == goBack) {
			TheMod.guiToOpen = "dankergui1";
		} else if (button == riddle) {
			ToggleCommand.threeManToggled = !ToggleCommand.threeManToggled;
			ConfigHandler.writeBooleanConfig("toggles", "ThreeManPuzzle", ToggleCommand.threeManToggled);
			riddle.displayString = "Riddle Solver: " + Utils.getColouredBoolean(ToggleCommand.threeManToggled);
		} else if (button == trivia) {
			ToggleCommand.oruoToggled = !ToggleCommand.oruoToggled;
			ConfigHandler.writeBooleanConfig("toggles", "OruoPuzzle", ToggleCommand.oruoToggled);
			trivia.displayString = "Trivia Solver: " + Utils.getColouredBoolean(ToggleCommand.oruoToggled);
		} else if (button == blaze) {
			ToggleCommand.blazeToggled = !ToggleCommand.blazeToggled;
			ConfigHandler.writeBooleanConfig("toggles", "BlazePuzzle", ToggleCommand.blazeToggled);
			blaze.displayString = "Blaze Solver: " + Utils.getColouredBoolean(ToggleCommand.blazeToggled);
		} else if (button == onlyShowCorrectBlaze) {
			ToggleCommand.onlyShowCorrectBlazeToggled = !ToggleCommand.onlyShowCorrectBlazeToggled;
			ConfigHandler.writeBooleanConfig("toggles", "OnlyShowCorrectBlaze", ToggleCommand.onlyShowCorrectBlazeToggled);
			onlyShowCorrectBlaze.displayString = "Only Show Correct Blaze Hitbox: " + Utils.getColouredBoolean(ToggleCommand.onlyShowCorrectBlazeToggled);
		} else if (button == creeper) {
			ToggleCommand.creeperToggled = !ToggleCommand.creeperToggled;
			ConfigHandler.writeBooleanConfig("toggles", "CreeperPuzzle", ToggleCommand.creeperToggled);
			creeper.displayString = "Creeper Solver: " + Utils.getColouredBoolean(ToggleCommand.creeperToggled);
		} else if (button == water) {
			ToggleCommand.waterToggled = !ToggleCommand.waterToggled;
			ConfigHandler.writeBooleanConfig("toggles", "WaterPuzzle", ToggleCommand.waterToggled);
			water.displayString = "Water Solver: " + Utils.getColouredBoolean(ToggleCommand.waterToggled);
		}
	}
	
}
