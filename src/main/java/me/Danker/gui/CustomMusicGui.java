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

    private int page;

    private GuiButton goBack;
    private GuiButton backPage;
    private GuiButton nextPage;

    private GuiButton dungeonBossMusic;
    private GuiButton bloodRoomMusic;
    private GuiButton dungeonMusic;
    private GuiButton hubMusic;
    private GuiButton islandMusic;
    private GuiButton dungeonHubMusic;
    private GuiButton farmingIslandsMusic;
    private GuiButton goldMineMusic;
    private GuiButton deepCavernsMusic;
    private GuiButton dwarvenMinesMusic;
    private GuiButton crystalHollowsMusic;
    private GuiButton spidersDenMusic;
    private GuiButton blazingFortressMusic;
    private GuiButton endMusic;
    private GuiButton parkMusic;

    public CustomMusicGui(int page) {
        this.page = page;
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

        goBack = new GuiButton(0, 2, height - 30, 100, 20, "Go Back");
        backPage = new GuiButton(0, width / 2 - 100, (int) (height * 0.8), 80, 20, "< Back");
        nextPage = new GuiButton(0, width / 2 + 20, (int) (height * 0.8), 80, 20, "Next >");

        dungeonMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Custom Dungeon Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonMusic));
        bloodRoomMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Custom Blood Room Music: " + Utils.getColouredBoolean(ToggleCommand.bloodRoomMusic));
        dungeonBossMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Custom Dungeon Boss Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonBossMusic));
        hubMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Custom Hub Music: " + Utils.getColouredBoolean(ToggleCommand.hubMusic));
        islandMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.5), "Custom Island Music: " + Utils.getColouredBoolean(ToggleCommand.islandMusic));
        dungeonHubMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.6), "Custom Dungeon Hub Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonHubMusic));
        farmingIslandsMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.7), "Custom Farming Islands Music: " + Utils.getColouredBoolean(ToggleCommand.farmingIslandsMusic));
        goldMineMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Custom Gold Mine Music: " + Utils.getColouredBoolean(ToggleCommand.goldMineMusic));
        deepCavernsMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Custom Deep Caverns Music: " + Utils.getColouredBoolean(ToggleCommand.deepCavernsMusic));
        dwarvenMinesMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Custom Dwarven Mines Music: " + Utils.getColouredBoolean(ToggleCommand.dwarvenMinesMusic));
        crystalHollowsMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Custom Crystal Hollows Music: " + Utils.getColouredBoolean(ToggleCommand.crystalHollowsMusic));
        spidersDenMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.5), "Custom Spider's Den Music: " + Utils.getColouredBoolean(ToggleCommand.spidersDenMusic));
        blazingFortressMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.6), "Custom Blazing Fortress Music: " + Utils.getColouredBoolean(ToggleCommand.blazingFortressMusic));
        endMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.7), "Custom End Music: " + Utils.getColouredBoolean(ToggleCommand.endMusic));
        parkMusic = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Custom Park Music: " + Utils.getColouredBoolean(ToggleCommand.parkMusic));

        switch (page) {
            case 1:
                this.buttonList.add(dungeonMusic);
                this.buttonList.add(bloodRoomMusic);
                this.buttonList.add(dungeonBossMusic);
                this.buttonList.add(hubMusic);
                this.buttonList.add(islandMusic);
                this.buttonList.add(dungeonHubMusic);
                this.buttonList.add(farmingIslandsMusic);
                this.buttonList.add(nextPage);
                break;
            case 2:
                this.buttonList.add(goldMineMusic);
                this.buttonList.add(deepCavernsMusic);
                this.buttonList.add(dwarvenMinesMusic);
                this.buttonList.add(crystalHollowsMusic);
                this.buttonList.add(spidersDenMusic);
                this.buttonList.add(blazingFortressMusic);
                this.buttonList.add(endMusic);
                this.buttonList.add(nextPage);
                this.buttonList.add(backPage);
                break;
            case 3:
                this.buttonList.add(parkMusic);
                this.buttonList.add(backPage);
                break;
        }

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
        } else if (button == backPage) {
            Minecraft.getMinecraft().displayGuiScreen(new CustomMusicGui(page - 1));
        } else if (button == nextPage) {
            Minecraft.getMinecraft().displayGuiScreen(new CustomMusicGui(page + 1));
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
        } else if (button == hubMusic) {
            ToggleCommand.hubMusic = !ToggleCommand.hubMusic;
            CustomMusic.hub.stop();
            ConfigHandler.writeBooleanConfig("toggles", "HubMusic", ToggleCommand.hubMusic);
            hubMusic.displayString = "Custom Hub Music: " + Utils.getColouredBoolean(ToggleCommand.hubMusic);
        } else if (button == islandMusic) {
            ToggleCommand.islandMusic = !ToggleCommand.islandMusic;
            CustomMusic.island.stop();
            ConfigHandler.writeBooleanConfig("toggles", "IslandMusic", ToggleCommand.islandMusic);
            islandMusic.displayString = "Custom Island Music: " + Utils.getColouredBoolean(ToggleCommand.islandMusic);
        } else if (button == dungeonHubMusic) {
            ToggleCommand.dungeonHubMusic = !ToggleCommand.dungeonHubMusic;
            CustomMusic.dungeonHub.stop();
            ConfigHandler.writeBooleanConfig("toggles", "DungeonHubMusic", ToggleCommand.dungeonHubMusic);
            dungeonHubMusic.displayString = "Custom Dungeon Hub Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonHubMusic);
        } else if (button == farmingIslandsMusic) {
            ToggleCommand.farmingIslandsMusic = !ToggleCommand.farmingIslandsMusic;
            CustomMusic.farmingIslands.stop();
            ConfigHandler.writeBooleanConfig("toggles", "FarmingIslandsMusic", ToggleCommand.farmingIslandsMusic);
            farmingIslandsMusic.displayString = "Custom Farming Islands Music: " + Utils.getColouredBoolean(ToggleCommand.farmingIslandsMusic);
        } else if (button == goldMineMusic) {
            ToggleCommand.goldMineMusic = !ToggleCommand.goldMineMusic;
            CustomMusic.goldMine.stop();
            ConfigHandler.writeBooleanConfig("toggles", "GoldMineMusic", ToggleCommand.goldMineMusic);
            goldMineMusic.displayString = "Custom Gold Mine Music: " + Utils.getColouredBoolean(ToggleCommand.goldMineMusic);
        } else if (button == deepCavernsMusic) {
            ToggleCommand.deepCavernsMusic = !ToggleCommand.deepCavernsMusic;
            CustomMusic.deepCaverns.stop();
            ConfigHandler.writeBooleanConfig("toggles", "DeepCavernsMusic", ToggleCommand.deepCavernsMusic);
            deepCavernsMusic.displayString = "Custom Deep Caverns Music: " + Utils.getColouredBoolean(ToggleCommand.deepCavernsMusic);
        } else if (button == dwarvenMinesMusic) {
            ToggleCommand.dwarvenMinesMusic = !ToggleCommand.dwarvenMinesMusic;
            CustomMusic.dwarvenMines.stop();
            ConfigHandler.writeBooleanConfig("toggles", "DwarvenMinesMusic", ToggleCommand.dwarvenMinesMusic);
            dwarvenMinesMusic.displayString = "Custom Dwarven Mines Music: " + Utils.getColouredBoolean(ToggleCommand.dwarvenMinesMusic);
        } else if (button == crystalHollowsMusic) {
            ToggleCommand.crystalHollowsMusic = !ToggleCommand.crystalHollowsMusic;
            CustomMusic.crystalHollows.stop();
            ConfigHandler.writeBooleanConfig("toggles", "CrystalHollowsMusic", ToggleCommand.crystalHollowsMusic);
            crystalHollowsMusic.displayString = "Custom Crystal Hollows Music: " + Utils.getColouredBoolean(ToggleCommand.crystalHollowsMusic);
        } else if (button == spidersDenMusic) {
            ToggleCommand.spidersDenMusic = !ToggleCommand.spidersDenMusic;
            CustomMusic.spidersDen.stop();
            ConfigHandler.writeBooleanConfig("toggles", "SpidersDenMusic", ToggleCommand.spidersDenMusic);
            spidersDenMusic.displayString = "Custom Spider's Den Music: " + Utils.getColouredBoolean(ToggleCommand.spidersDenMusic);
        } else if (button == blazingFortressMusic) {
            ToggleCommand.blazingFortressMusic = !ToggleCommand.blazingFortressMusic;
            CustomMusic.blazingFortress.stop();
            ConfigHandler.writeBooleanConfig("toggles", "BlazingFortressMusic", ToggleCommand.blazingFortressMusic);
            blazingFortressMusic.displayString = "Custom Blazing Fortress Music: " + Utils.getColouredBoolean(ToggleCommand.blazingFortressMusic);
        } else if (button == endMusic) {
            ToggleCommand.endMusic = !ToggleCommand.endMusic;
            CustomMusic.end.stop();
            ConfigHandler.writeBooleanConfig("toggles", "EndMusic", ToggleCommand.endMusic);
            endMusic.displayString = "Custom End Music: " + Utils.getColouredBoolean(ToggleCommand.endMusic);
        } else if (button == parkMusic) {
            ToggleCommand.parkMusic = !ToggleCommand.parkMusic;
            CustomMusic.park.stop();
            ConfigHandler.writeBooleanConfig("toggles", "ParkMusic", ToggleCommand.parkMusic);
            parkMusic.displayString = "Custom Park Music: " + Utils.getColouredBoolean(ToggleCommand.parkMusic);
        }
    }

}
