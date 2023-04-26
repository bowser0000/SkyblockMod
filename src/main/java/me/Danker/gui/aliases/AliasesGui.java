package me.Danker.gui.aliases;

import cc.polyfrost.oneconfig.libs.universal.UResolution;
import me.Danker.DankersSkyblockMod;
import me.Danker.features.ChatAliases;
import me.Danker.utils.RenderUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class AliasesGui extends GuiScreen {

    private final int page;
    private final List<GuiButton> allButtons = new ArrayList<>();

    private GuiButton goBack;
    private GuiButton backPage;
    private GuiButton nextPage;
    private GuiButton add;

    public AliasesGui(int page) {
        this.page = page;
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

        goBack = new GuiButton(0, 2, height - 30, 100, 20, "Go Back");
        backPage = new GuiButton(0, width / 2 - 100, (int) (height * 0.8), 80, 20, "< Back");
        nextPage = new GuiButton(0, width / 2 + 20, (int) (height * 0.8), 80, 20, "Next >");
        add = new GuiButton(0, 0, 0, "Add Alias");

        allButtons.clear();
        allButtons.add(add);
        for (int i = 0; i < ChatAliases.aliases.size(); i++) {
            ChatAliases.Alias alias = ChatAliases.aliases.get(i);
            GuiButton button = new GuiButton(i, 0, 0, alias.text + " >");
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
            mc.displayGuiScreen(new AliasesGui(page + 1));
        } else if (button == backPage) {
            mc.displayGuiScreen(new AliasesGui(page - 1));
        } else if (button == add) {
            mc.displayGuiScreen(new AliasesAddGui());
        } else {
            mc.displayGuiScreen(new AliasesActionGui(button.id));
        }
    }

}
