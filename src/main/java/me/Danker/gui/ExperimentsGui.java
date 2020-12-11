package me.Danker.gui;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ExperimentsGui extends GuiScreen {

    private GuiButton goBack;
    private GuiButton ultrasequencer;
    private GuiButton chronomatron;
    private GuiButton superpairs;
    private GuiButton hideTooltips;

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
        ultrasequencer = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Ultrasequencer Solver: " + Utils.getColouredBoolean(ToggleCommand.ultrasequencerToggled));
        chronomatron = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Chronomatron Solver: " + Utils.getColouredBoolean(ToggleCommand.chronomatronToggled));
        superpairs = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Superpairs Solver: " + Utils.getColouredBoolean(ToggleCommand.superpairsToggled));
        hideTooltips = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Hide Tooltips in Addons: " + Utils.getColouredBoolean(ToggleCommand.hideTooltipsInExperimentAddonsToggled));

        this.buttonList.add(goBack);
        this.buttonList.add(ultrasequencer);
        this.buttonList.add(chronomatron);
        this.buttonList.add(superpairs);
        this.buttonList.add(hideTooltips);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button == goBack) {
            DankersSkyblockMod.guiToOpen = "dankergui1";
        } else if (button == ultrasequencer) {
            ToggleCommand.ultrasequencerToggled = !ToggleCommand.ultrasequencerToggled;
            ConfigHandler.writeBooleanConfig("toggles", "UltraSequencer", ToggleCommand.ultrasequencerToggled);
            ultrasequencer.displayString = "Ultrasequencer Solver: " + Utils.getColouredBoolean(ToggleCommand.ultrasequencerToggled);
        } else if (button == chronomatron) {
            ToggleCommand.chronomatronToggled = !ToggleCommand.chronomatronToggled;
            ConfigHandler.writeBooleanConfig("toggles", "Chronomatron", ToggleCommand.chronomatronToggled);
            chronomatron.displayString = "Chronomatron Solver: " + Utils.getColouredBoolean(ToggleCommand.chronomatronToggled);
        } else if (button == superpairs) {
            ToggleCommand.superpairsToggled = !ToggleCommand.superpairsToggled;
            ConfigHandler.writeBooleanConfig("toggles", "Superpairs", ToggleCommand.superpairsToggled);
            superpairs.displayString = "Superpairs Solver: " + Utils.getColouredBoolean(ToggleCommand.superpairsToggled);
        } else if (button == hideTooltips) {
            ToggleCommand.hideTooltipsInExperimentAddonsToggled = !ToggleCommand.hideTooltipsInExperimentAddonsToggled;
            ConfigHandler.writeBooleanConfig("toggles", "HideTooltipsInExperimentAddons", ToggleCommand.hideTooltipsInExperimentAddonsToggled);
            hideTooltips.displayString = "Hide Tooltips in Addons: " + Utils.getColouredBoolean(ToggleCommand.hideTooltipsInExperimentAddonsToggled);
        }
    }

}
