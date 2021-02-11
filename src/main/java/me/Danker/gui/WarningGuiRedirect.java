package me.Danker.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class WarningGuiRedirect extends GuiMainMenu {
    public GuiScreen guiToShow;
    private GuiButton close;

    protected void keyTyped(char par1, int par2){}
    public WarningGuiRedirect(GuiScreen g){
        guiToShow = g;
    }

    public void initGui(){
        super.initGui();
    }


    public void drawScreen(int par1, int par2, float par3){
        super.drawScreen(par1, par2, par3);

        mc.displayGuiScreen(guiToShow);
    }
}
