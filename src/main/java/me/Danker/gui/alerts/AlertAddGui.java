package me.Danker.gui.alerts;

import me.Danker.features.Alerts;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.client.config.GuiCheckBox;

import java.io.IOException;

public class AlertAddGui extends GuiScreen {

    private boolean editing;
    private Alerts.Alert base = null;
    private int id;

    private GuiButton cancel;

    private String mode = "Contains";
    private String location = "Skyblock";
    private GuiButton startsWith;
    private GuiButton contains;
    private GuiButton endsWith;
    private GuiButton regex;
    private GuiButton everywhere;
    private GuiButton skyblock;
    private GuiButton dungeons;
    private GuiTextField message;
    private GuiTextField alert;
    private GuiCheckBox desktop;
    private GuiCheckBox toggled;
    private GuiButton add;

    public AlertAddGui() {}

    public AlertAddGui(Alerts.Alert alert, int id) {
        editing = true;
        base = alert;
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

        cancel = new GuiButton(0, 2, height - 30, 100, 20, "Cancel");

        startsWith = new GuiButton(0, width / 2 - 260, (int) (height * 0.2), 120, 20, "Starts With");
        contains = new GuiButton(0, width / 2 - 130, (int) (height * 0.2), 120, 20, "Contains");
        endsWith = new GuiButton(0, width / 2 + 10, (int) (height * 0.2), 120, 20, "Ends With");
        regex = new GuiButton(0, width / 2 + 140, (int) (height * 0.2), 120, 20, "Regex");
        everywhere = new GuiButton(0, width / 2 - 200, (int) (height * 0.3), 120, 20, "Everywhere");
        skyblock = new GuiButton(0, width / 2 - 60, (int) (height * 0.3), 120, 20, "Skyblock");
        dungeons = new GuiButton(0, width / 2 + 80, (int) (height * 0.3), 120, 20, "Dungeons");
        message = new GuiTextField(0, this.fontRendererObj, width / 2 - 100, (int) (height * 0.4), 200, 20);
        alert = new GuiTextField(0, this.fontRendererObj, width / 2 - 100, (int) (height * 0.5), 200, 20);
        desktop = new GuiCheckBox(0, width / 2 - 58, (int) (height * 0.6), "Desktop Notification", false);
        toggled = new GuiCheckBox(0, width / 2 - 26, (int) (height * 0.65), "Toggled", true);
        add = new GuiButton(0, width / 2 - 25, (int) (height * 0.8), 50, 20, "Add");

        if (editing) {
            mode = base.mode;
            location = base.location;
            message.setText(base.message);
            alert.setText(base.alert);
            desktop.setIsChecked(base.desktop);
            toggled.setIsChecked(base.toggled);
        }

        message.setVisible(true);
        message.setEnabled(true);
        message.setMaxStringLength(100);
        alert.setVisible(true);
        alert.setEnabled(true);
        alert.setMaxStringLength(100);

        this.buttonList.add(cancel);
        this.buttonList.add(startsWith);
        this.buttonList.add(contains);
        this.buttonList.add(endsWith);
        this.buttonList.add(regex);
        this.buttonList.add(everywhere);
        this.buttonList.add(skyblock);
        this.buttonList.add(dungeons);
        this.buttonList.add(desktop);
        this.buttonList.add(toggled);
        this.buttonList.add(add);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        message.drawTextBox();
        alert.drawTextBox();

        RenderUtils.drawCenteredText("Mode: " + mode, width, (int) (height * 0.1), 1D);
        RenderUtils.drawCenteredText("Location: " + location, width, (int) (height * 0.15), 1D);
        new TextRenderer(mc, "Trigger:", width / 2 - 145, (int) (height * 0.42), 1D);
        new TextRenderer(mc, "Alert Text:", width / 2 - 158, (int) (height * 0.52), 1D);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button == cancel) {
            mc.displayGuiScreen(new AlertsGui(1));
        } else if (button == startsWith) {
            mode = "Starts With";
        } else if (button == contains) {
            mode = "Contains";
        } else if (button == endsWith) {
            mode = "Ends With";
        } else if (button == regex) {
            mode = "Regex";
        } else if (button == everywhere) {
            location = "Everywhere";
        } else if (button == skyblock) {
            location = "Skyblock";
        } else if (button == dungeons) {
            location = "Dungeons";
        } else if (button == add) {
            Alerts.Alert newAlert = new Alerts.Alert(mode, location, message.getText(), alert.getText(), desktop.isChecked(), toggled.isChecked());
            if (editing) {
                Alerts.alerts.set(id, newAlert);
            } else {
                Alerts.alerts.add(newAlert);
            }
            Alerts.save();
            mc.displayGuiScreen(new AlertsGui(1));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        message.mouseClicked(mouseX, mouseY, mouseButton);
        alert.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        message.textboxKeyTyped(typedChar, keyCode);
        alert.textboxKeyTyped(typedChar, keyCode);
    }

}
