package me.Danker.gui.aliases;

import cc.polyfrost.oneconfig.libs.universal.UResolution;
import me.Danker.features.ChatAliases;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class AliasesActionGui extends GuiScreen {

    private final int id;

    private GuiButton goBack;
    private GuiButton toggle;
    private GuiButton allowInCommand;
    private GuiButton edit;
    private GuiButton delete;

    public AliasesActionGui(int id) {
        this.id = id;
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

        ChatAliases.Alias alias = ChatAliases.aliases.get(id);

        goBack = new GuiButton(0, 2, height - 30, 100, 20, "Go Back");
        toggle = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Enabled: " + Utils.getColouredBoolean(alias.toggled));
        allowInCommand = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Allow in Command: " + Utils.getColouredBoolean(alias.allowInCommand));
        edit = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Edit >");
        delete = new GuiButton(0, width / 2 - 100, (int) (height * 0.8), EnumChatFormatting.RED + "Delete Alias");

        this.buttonList.add(toggle);
        this.buttonList.add(allowInCommand);
        this.buttonList.add(edit);
        this.buttonList.add(delete);
        this.buttonList.add(goBack);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        ChatAliases.Alias alias = ChatAliases.aliases.get(id);

        RenderUtils.drawCenteredText(alias.text, width, 10, 1D);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        ChatAliases.Alias alias = ChatAliases.aliases.get(id);
        if (button == goBack) {
            mc.displayGuiScreen(new AliasesGui(1));
        } else if (button == toggle) {
            alias.toggle();
            toggle.displayString = "Enabled: " + Utils.getColouredBoolean(alias.toggled);
        } else if (button == allowInCommand) {
            alias.toggleInCommand();
            allowInCommand.displayString = "Allow in Command: " + Utils.getColouredBoolean(alias.allowInCommand);
        } else if (button == edit) {
            mc.displayGuiScreen(new AliasesAddGui(alias, id));
        } else if (button == delete) {
            ChatAliases.aliases.remove(id);
            ChatAliases.save();
            mc.displayGuiScreen(new AliasesGui(1));
            return;
        }
        ChatAliases.aliases.set(id, alias);
        ChatAliases.save();
    }

}
