package me.Danker.gui;

import me.Danker.features.PowderTracker;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class PowderTrackerGui extends GuiScreen {

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
        if (PowderTracker.powderStopwatch.isStarted() && !PowderTracker.powderStopwatch.isSuspended()) {
            stateText = "Timer: Running";
        } else if (!PowderTracker.powderStopwatch.isStarted() || PowderTracker.powderStopwatch.isSuspended()) {
            stateText = "Timer: Paused";
        }
        if (!PowderTracker.showPowderTracker) {
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
            if (PowderTracker.powderStopwatch.isStarted() && PowderTracker.powderStopwatch.isSuspended()) {
                PowderTracker.powderStopwatch.resume();
            } else if (!PowderTracker.powderStopwatch.isStarted()) {
                PowderTracker.powderStopwatch.start();
            }
        } else if (button == stop) {
            if (PowderTracker.powderStopwatch.isStarted() && !PowderTracker.powderStopwatch.isSuspended()) {
                PowderTracker.powderStopwatch.suspend();
            }
        } else if (button == reset) {
            PowderTracker.reset();
        } else if (button == hide) {
            PowderTracker.showPowderTracker = false;
            ConfigHandler.writeBooleanConfig("misc", "showPowderTracker", false);
        } else if (button == show) {
            PowderTracker.showPowderTracker = true;
            ConfigHandler.writeBooleanConfig("misc", "showPowderTracker", true);
        }
    }

}
