package me.Danker.gui.alerts;

import me.Danker.features.Alerts;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

public class AlertActionGui extends GuiScreen {

    private final int id;

    private GuiButton goBack;
    private GuiButton toggle;
    private GuiButton toggleDesktop;
    private GuiButton edit;
    private GuiButton delete;

    public AlertActionGui(int id) {
        this.id = id;
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

        Alerts.Alert alert = Alerts.alerts.get(id);

        goBack = new GuiButton(0, 2, height - 30, 100, 20, "Go Back");
        toggle = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Enabled: " + Utils.getColouredBoolean(alert.toggled));
        toggleDesktop = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Desktop Notification: " + Utils.getColouredBoolean(alert.desktop));
        edit = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Edit >");
        delete = new GuiButton(0, width / 2 - 100, (int) (height * 0.8), EnumChatFormatting.RED + "Delete Alert");

        this.buttonList.add(toggle);
        this.buttonList.add(toggleDesktop);
        this.buttonList.add(edit);
        this.buttonList.add(delete);
        this.buttonList.add(goBack);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        Alerts.Alert alert = Alerts.alerts.get(id);

        RenderUtils.drawCenteredText(alert.alert, width, 10, 1D);
        String alertText = alert.alert;
        int textWidth = mc.fontRendererObj.getStringWidth(alertText);
        new TextRenderer(mc, alertText, width / 2 - textWidth / 2, 10, 1D);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        Alerts.Alert alert = Alerts.alerts.get(id);
        if (button == goBack) {
            mc.displayGuiScreen(new AlertsGui(1));
        } else if (button == toggle) {
            alert.toggle();
            toggle.displayString = "Enabled: " + Utils.getColouredBoolean(alert.toggled);
        } else if (button == toggleDesktop) {
            alert.toggleDesktop();
            toggleDesktop.displayString = "Desktop Notification: " + Utils.getColouredBoolean(alert.desktop);
        } else if (button == edit) {
            mc.displayGuiScreen(new AlertAddGui(alert, id));
        } else if (button == delete) {
            Alerts.alerts.remove(id);
            Alerts.save();
            mc.displayGuiScreen(new AlertsGui(1));
            return;
        }
        Alerts.alerts.set(id, alert);
        Alerts.save();
    }

}
