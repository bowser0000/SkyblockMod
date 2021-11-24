package me.Danker.gui;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.features.BlockWrongSlayer;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class OnlySlayerGui extends GuiScreen {

    private int onlyNumberInt = 4;
    private String onlyName = "Revenant Horror";

    private GuiButton goBack;
    private GuiButton off;
    private GuiButton zombie;
    private GuiButton spider;
    private GuiButton wolf;
    private GuiButton enderman;
    private GuiButton one;
    private GuiButton two;
    private GuiButton three;
    private GuiButton four;
    private GuiButton five;

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

        onlyName = BlockWrongSlayer.onlySlayerName;
        switch (BlockWrongSlayer.onlySlayerNumber) {
            case "I":
                onlyNumberInt = 1;
                break;
            case "II":
                onlyNumberInt = 2;
                break;
            case "III":
                onlyNumberInt = 3;
                break;
            case "IV":
            default:
                onlyNumberInt = 4;
                break;
            case "V":
                onlyNumberInt = 5;
                break;
        }

        goBack = new GuiButton(0, 2, height - 30, 100, 20, "Go Back");
        off = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Off");
        zombie = new GuiButton(0, width / 2 - 230, (int) (height * 0.4), 100, 20, "Zombie");
        spider = new GuiButton(0, width / 2 - 110, (int) (height * 0.4), 100, 20, "Spider");
        wolf = new GuiButton(0, width / 2 + 10, (int) (height * 0.4), 100, 20, "Wolf");
        enderman = new GuiButton(0, width / 2 + 130, (int) (height * 0.4), 100, 20, "Enderman");
        one = new GuiButton(0, width / 2 - 190, (int) (height * 0.6), 60, 20, "I");
        two = new GuiButton(0, width / 2 - 110, (int) (height * 0.6), 60, 20, "II");
        three = new GuiButton(0, width / 2 - 30, (int) (height * 0.6), 60, 20, "III");
        four = new GuiButton(0, width / 2 + 50, (int) (height * 0.6), 60, 20, "IV");
        five = new GuiButton(0, width / 2 + 130, (int) (height * 0.6), 60, 20, "V");

        this.buttonList.add(off);
        this.buttonList.add(zombie);
        this.buttonList.add(spider);
        this.buttonList.add(wolf);
        this.buttonList.add(enderman);
        this.buttonList.add(one);
        this.buttonList.add(two);
        this.buttonList.add(three);
        this.buttonList.add(four);
        this.buttonList.add(five);
        this.buttonList.add(goBack);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        Minecraft mc = Minecraft.getMinecraft();

        String displayText;
        if (BlockWrongSlayer.onlySlayerName.equals("")) {
            displayText = "Only Allow Slayer: Off";
        } else {
            displayText = "Only Allow Slayer: " + BlockWrongSlayer.onlySlayerName + " " + BlockWrongSlayer.onlySlayerNumber;
        }
        int displayWidth = mc.fontRendererObj.getStringWidth(displayText);
        new TextRenderer(mc, displayText, width / 2 - displayWidth / 2, 10, 1D);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button == goBack) {
            DankersSkyblockMod.guiToOpen = "dankergui1";
            return;
        } else if (button == off) {
            BlockWrongSlayer.onlySlayerName = "";
            BlockWrongSlayer.onlySlayerNumber = "";
            ConfigHandler.writeStringConfig("toggles", "BlockSlayer", "");
            return;
        } else if (button == zombie) {
            onlyName = "Revenant Horror";
        } else if (button == spider) {
            onlyName = "Tarantula Broodfather";
        } else if (button == wolf) {
            onlyName = "Sven Packmaster";
        } else if (button == enderman) {
            onlyName = "Voidgloom Seraph";
        } else if (button == one) {
            onlyNumberInt = 1;
        } else if (button == two) {
            onlyNumberInt = 2;
        } else if (button == three) {
            onlyNumberInt = 3;
        } else if (button == four) {
            onlyNumberInt = 4;
        } else if (button == five) {
            onlyNumberInt = 5;
        }

        String onlyNumber;
        switch (onlyNumberInt) {
            case 1:
                onlyNumber = "I";
                break;
            case 2:
                onlyNumber = "II";
                break;
            case 3:
                onlyNumber = "III";
                break;
            case 4:
                onlyNumber = "IV";
                break;
            case 5:
                onlyNumber = "V";
                break;
            default:
                onlyNumber = "IV";
        }

        BlockWrongSlayer.onlySlayerName = onlyName;
        BlockWrongSlayer.onlySlayerNumber = onlyNumber;
        ConfigHandler.writeStringConfig("toggles", "BlockSlayer", BlockWrongSlayer.onlySlayerName + " " + BlockWrongSlayer.onlySlayerNumber);
    }

}
