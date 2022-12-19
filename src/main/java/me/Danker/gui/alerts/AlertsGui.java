package me.Danker.gui.alerts;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.Alerts;
import me.Danker.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

public class AlertsGui extends GuiScreen {

    private final int page;
    private final List<GuiButton> allButtons = new ArrayList<>();

    private GuiButton goBack;
    private GuiButton backPage;
    private GuiButton nextPage;
    private GuiButton add;

    public AlertsGui(int page) {
        this.page = page;
    }

    @Override
    public void initGui() {
        super.initGui();

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int height = sr.getScaledHeight();
        int width = sr.getScaledWidth();

        goBack = new GuiButton(0, 2, height - 30, 100, 20, "Go Back");
        backPage = new GuiButton(0, width / 2 - 100, (int) (height * 0.8), 80, 20, "< Back");
        nextPage = new GuiButton(0, width / 2 + 20, (int) (height * 0.8), 80, 20, "Next >");
        add = new GuiButton(0, 0, 0, "Add Alert");

        allButtons.clear();
        allButtons.add(add);
        for (int i = 0; i < Alerts.alerts.size(); i++) {
            Alerts.Alert alert = Alerts.alerts.get(i);
            GuiButton button = new GuiButton(i, 0, 0, alert.alert + " >");
            allButtons.add(button);
        }

        reInit();
    }

    public void reInit() {
        this.buttonList.clear();

        for (int i = (page - 1) * 7, iteration = 0; iteration < 7 && i < allButtons.size(); i++, iteration++) {
            GuiButton button = allButtons.get(i);
            button.xPosition = width / 2 - 100;
            button.yPosition = (int) (height * (0.1 * (iteration + 1)));
            this.buttonList.add(button);
        }

        if (page > 1) this.buttonList.add(backPage);
        if (page < Math.ceil(allButtons.size() / 7D)) this.buttonList.add(nextPage);

        this.buttonList.add(goBack);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        String pageText = "Page: " + page + "/" + (int) Math.ceil(allButtons.size() / 7D);
        RenderUtils.drawCenteredText(pageText, width, 10, 1D);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button == goBack) {
            DankersSkyblockMod.config.openGui();
        } else if (button == nextPage) {
            mc.displayGuiScreen(new AlertsGui(page + 1));
        } else if (button == backPage) {
            mc.displayGuiScreen(new AlertsGui(page - 1));
        } else if (button == add) {
            mc.displayGuiScreen(new AlertAddGui());
        } else {
            mc.displayGuiScreen(new AlertActionGui(button.id));
        }
    }

}
