package me.Danker.gui;

import cc.polyfrost.oneconfig.libs.universal.UResolution;
import me.Danker.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class WarningGui extends GuiScreen {
    private GuiButton close;

    public WarningGui(){

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        super.initGui();

        int height = UResolution.getScaledHeight();
        int width = UResolution.getScaledWidth();

        close = new GuiButton(0, width / 2 - 100, (int) (height * 0.6), "Close");

        this.buttonList.add(close);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        RenderUtils.drawCenteredText(EnumChatFormatting.RED + "WARNING!", (int) (width * 0.9), (int) (height * 0.1), 2D);
        RenderUtils.drawCenteredText("You are using SpiderFrog's Old Animations mod.", width, (int) (height * 0.3), 1D);
        RenderUtils.drawCenteredText("This mod breaks Danker's Skyblock Mod.", width, (int) (height * 0.4), 1D);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button){
        if(button == close) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }
}
