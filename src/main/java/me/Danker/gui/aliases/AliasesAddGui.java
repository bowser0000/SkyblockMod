package me.Danker.gui.aliases;

import cc.polyfrost.oneconfig.libs.universal.UResolution;
import me.Danker.features.ChatAliases;
import me.Danker.handlers.TextRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.client.config.GuiCheckBox;

import java.io.IOException;

public class AliasesAddGui extends GuiScreen {

    private boolean editing;
    private ChatAliases.Alias base = null;
    private int id;

    private GuiButton cancel;

    private GuiTextField text;
    private GuiTextField alias;
    private GuiCheckBox toggled;
    private GuiCheckBox allowInCommand;
    private GuiButton add;

    public AliasesAddGui() {}

    public AliasesAddGui(ChatAliases.Alias alias, int id) {
        editing = true;
        base = alias;
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

        cancel = new GuiButton(0, 2, height - 30, 100, 20, "Cancel");

        text = new GuiTextField(0, this.fontRendererObj, width / 2 - 100, (int) (height * 0.2), 200, 20);
        alias = new GuiTextField(0, this.fontRendererObj, width / 2 - 100, (int) (height * 0.3), 200, 20);
        toggled = new GuiCheckBox(0, width / 2 - 26, (int) (height * 0.4), "Toggled", true);
        allowInCommand = new GuiCheckBox(0, width / 2 - 48, (int) (height * 0.5), "Allow in Command", true);
        add = new GuiButton(0, width / 2 - 25, (int) (height * 0.8), 50, 20, "Add");

        if (editing) {
            text.setText(base.text);
            alias.setText(base.alias);
            toggled.setIsChecked(base.toggled);
            allowInCommand.setIsChecked(base.allowInCommand);
        }

        text.setVisible(true);
        text.setEnabled(true);
        alias.setVisible(true);
        alias.setEnabled(true);
        alias.setMaxStringLength(100);

        this.buttonList.add(cancel);
        this.buttonList.add(toggled);
        this.buttonList.add(allowInCommand);
        this.buttonList.add(add);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        text.drawTextBox();
        alias.drawTextBox();

        TextRenderer.drawText("Text:", width / 2 - 135, (int) (height * 0.22), 1D);
        TextRenderer.drawText("Alias:", width / 2 - 136, (int) (height * 0.32), 1D);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button == cancel) {
            mc.displayGuiScreen(new AliasesGui(1));
        } else if (button == add) {
            ChatAliases.Alias newAlias = new ChatAliases.Alias(text.getText(), alias.getText(), toggled.isChecked(), allowInCommand.isChecked());
            if (editing) {
                ChatAliases.aliases.set(id, newAlias);
            } else {
                ChatAliases.aliases.add(newAlias);
            }
            ChatAliases.save();
            mc.displayGuiScreen(new AliasesGui(1));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        text.mouseClicked(mouseX, mouseY, mouseButton);
        alias.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        text.textboxKeyTyped(typedChar, keyCode);
        alias.textboxKeyTyped(typedChar, keyCode);
    }

}
