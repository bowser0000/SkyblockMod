package me.Danker.gui;

import net.minecraft.client.gui.*;


import java.io.IOException;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.crash.CrashReport;

public class DeathWarningGui extends GuiScreen implements GuiYesNoCallback {

    private int enableButtonsTimer;

    public void initGui() {
        this.buttonList.clear();

        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72, I18n.format("deathScreen.respawn", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, "Quit Game"));

        for (GuiButton guibutton : this.buttonList) {
            guibutton.enabled = false;
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                GuiYesNo guiyesno = new GuiYesNo(this, "Are you sure you want to respawn?", "Your inventory may get wiped if you continue!", "Respawn", "Quit Game", 0);
                this.mc.displayGuiScreen(guiyesno);
                guiyesno.setButtonDelay(20);
                break;
            case 1:
                this.mc.crashed(CrashReport.makeCrashReport(new DoubleDeath(), "Game intentionally was crashed to try to avoid an inventory wipe!"));
        }
    }

    public void confirmClicked(boolean result, int id) {
        if (result) {
            this.mc.thePlayer.respawnPlayer();
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else {
            this.mc.crashed(CrashReport.makeCrashReport(new DoubleDeath(), "Game intentionally was crashed to try to avoid an inventory wipe!"));
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        this.drawCenteredString(this.fontRendererObj,"You Died! (Vanilla Death)", this.width / 2 / 2, 30, 16777215);
        GlStateManager.popMatrix();

        this.drawCenteredString(this.fontRendererObj, "To avoid the risk of an inventory wipe, you should close your game!", this.width / 2, 100, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        super.updateScreen();
        ++this.enableButtonsTimer;

        if (this.enableButtonsTimer == 20)
        {
            for (GuiButton guibutton : this.buttonList)
            {
                guibutton.enabled = true;
            }
        }
    }

    class DoubleDeath extends Throwable {
        public DoubleDeath () {
            super("Anti-Double Death");
        }
    }

}