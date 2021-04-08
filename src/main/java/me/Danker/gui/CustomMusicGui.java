package me.Danker.gui;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.features.CustomMusic;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class CustomMusicGui extends GuiScreen {

    private GuiButton goBack;

    private GuiButton dungeonBossMusic;
    private GuiButton bloodRoomMusic;
    private GuiButton dungeonMusic;

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

        goBack = new GuiButton(0, 2, height - 30, 100, 20, "Go Back");
        dungeonMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Custom Dungeon Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonMusic));
        bloodRoomMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Custom Blood Room Music: " + Utils.getColouredBoolean(ToggleCommand.bloodRoomMusic));
        dungeonBossMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Custom Dungeon Boss Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonBossMusic));

        this.buttonList.add(dungeonMusic);
        this.buttonList.add(bloodRoomMusic);
        this.buttonList.add(dungeonBossMusic);
        this.buttonList.add(goBack);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button == goBack) {
            DankersSkyblockMod.guiToOpen = "dankergui1";
        } else if (button == dungeonBossMusic) {
            ToggleCommand.dungeonBossMusic = !ToggleCommand.dungeonBossMusic;
            CustomMusic.dungeonboss.stop();
            ConfigHandler.writeBooleanConfig("toggles", "DungeonBossMusic", ToggleCommand.dungeonBossMusic);
            dungeonBossMusic.displayString = "Custom Dungeon Boss Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonBossMusic);
        } else if (button == bloodRoomMusic) {
            ToggleCommand.bloodRoomMusic = !ToggleCommand.bloodRoomMusic;
            CustomMusic.bloodroom.stop();
            ConfigHandler.writeBooleanConfig("toggles", "BloodRoomMusic", ToggleCommand.bloodRoomMusic);
            bloodRoomMusic.displayString = "Custom Blood Room Music: " + Utils.getColouredBoolean(ToggleCommand.bloodRoomMusic);
        } else if (button == dungeonMusic) {
            ToggleCommand.dungeonMusic = !ToggleCommand.dungeonMusic;
            CustomMusic.dungeon.stop();
            ConfigHandler.writeBooleanConfig("toggles", "DungeonMusic", ToggleCommand.dungeonMusic);
            dungeonMusic.displayString = "Custom Dungeon Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonMusic);
        }
    }

}
