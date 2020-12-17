package me.Danker.gui;

import akka.event.Logging;
import me.Danker.handlers.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;

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

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int height = sr.getScaledHeight();
        int width = sr.getScaledWidth();

        close = new GuiButton(0, width / 2 - 100, (int) (height * 0.6), "Close");

        this.buttonList.add(close);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        String text0 = "§cWARNING!§0";
        int text0Width = mc.fontRendererObj.getStringWidth(text0);
        new TextRenderer(mc, text0, (int)(width * 0.45) - text0Width / 2, (int) (height * 0.1), 2 );
        String text1 = "You are using SpiderFrog's Old Animations mod.";
        int text1Width = mc.fontRendererObj.getStringWidth(text1);
        new TextRenderer(mc, text1, width / 2 - text1Width / 2, (int) (height * 0.3), 1D );
        String text2 = "This mod breaks Danker's Skyblock Mod.";
        int text2Width = mc.fontRendererObj.getStringWidth(text2);
        new TextRenderer(mc, text2, width / 2 - text2Width / 2, (int) (height * 0.4), 1D);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button){
        if(button == close) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }
}
