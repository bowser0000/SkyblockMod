package me.Danker.gui;

import me.Danker.commands.ToggleCommand;
import me.Danker.features.CustomMusic;
import me.Danker.gui.buttons.FeatureButton;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

public class CustomMusicGui extends GuiScreen {

    private final int page;
    private final List<GuiButton> allButtons = new ArrayList<>();

    private GuiButton goBack;
    private GuiButton backPage;
    private GuiButton nextPage;

    private FeatureButton disableHypixelMusic;
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
    private GuiButton crimsonIsleMusic;
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

        disableHypixelMusic = new FeatureButton("Disable Hypixel Music: " + Utils.getColouredBoolean(ToggleCommand.disableHypixelMusic), "Disable the noteblock music Hypixel plays in certain areas when custom music is playing.\nThis can be turned off manually in Skyblock Menu -> Settings -> Personal -> Sounds -> Play Music");
        dungeonMusic = new GuiButton(0, 0, 0, "Custom Dungeon Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonMusic));
        bloodRoomMusic = new GuiButton(0, 0, 0, "Custom Blood Room Music: " + Utils.getColouredBoolean(ToggleCommand.bloodRoomMusic));
        dungeonBossMusic = new GuiButton(0, 0, 0, "Custom Dungeon Boss Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonBossMusic));
        hubMusic = new GuiButton(0, 0, 0, "Custom Hub Music: " + Utils.getColouredBoolean(ToggleCommand.hubMusic));
        islandMusic = new GuiButton(0, 0, 0, "Custom Island Music: " + Utils.getColouredBoolean(ToggleCommand.islandMusic));
        dungeonHubMusic = new GuiButton(0, 0, 0, "Custom Dungeon Hub Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonHubMusic));
        farmingIslandsMusic = new GuiButton(0, 0, 0, "Custom Farming Islands Music: " + Utils.getColouredBoolean(ToggleCommand.farmingIslandsMusic));
        goldMineMusic = new GuiButton(0, 0, 0, "Custom Gold Mine Music: " + Utils.getColouredBoolean(ToggleCommand.goldMineMusic));
        deepCavernsMusic = new GuiButton(0, 0, 0, "Custom Deep Caverns Music: " + Utils.getColouredBoolean(ToggleCommand.deepCavernsMusic));
        dwarvenMinesMusic = new GuiButton(0, 0, 0, "Custom Dwarven Mines Music: " + Utils.getColouredBoolean(ToggleCommand.dwarvenMinesMusic));
        crystalHollowsMusic = new GuiButton(0, 0, 0, "Custom Crystal Hollows Music: " + Utils.getColouredBoolean(ToggleCommand.crystalHollowsMusic));
        spidersDenMusic = new GuiButton(0, 0, 0, "Custom Spider's Den Music: " + Utils.getColouredBoolean(ToggleCommand.spidersDenMusic));
        crimsonIsleMusic = new GuiButton(0, 0, 0, "Custom Crimson Isle Music: " + Utils.getColouredBoolean(ToggleCommand.crimsonIsleMusic));
        endMusic = new GuiButton(0, 0, 0, "Custom End Music: " + Utils.getColouredBoolean(ToggleCommand.endMusic));
        parkMusic = new GuiButton(0, 0, 0, "Custom Park Music: " + Utils.getColouredBoolean(ToggleCommand.parkMusic));

        allButtons.clear();
        allButtons.add(disableHypixelMusic);
        allButtons.add(dungeonMusic);
        allButtons.add(bloodRoomMusic);
        allButtons.add(dungeonBossMusic);
        allButtons.add(hubMusic);
        allButtons.add(islandMusic);
        allButtons.add(dungeonHubMusic);
        allButtons.add(farmingIslandsMusic);
        allButtons.add(goldMineMusic);
        allButtons.add(deepCavernsMusic);
        allButtons.add(dwarvenMinesMusic);
        allButtons.add(crystalHollowsMusic);
        allButtons.add(spidersDenMusic);
        allButtons.add(crimsonIsleMusic);
        allButtons.add(endMusic);
        allButtons.add(parkMusic);

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

        for (GuiButton button : this.buttonList) {
            if (button instanceof FeatureButton && button.isMouseOver()) {
                FeatureButton feature = (FeatureButton) button;
                drawHoveringText(feature.hoverText, mouseX - 5, mouseY);
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button == goBack) {
            mc.displayGuiScreen(new DankerGui(1, ""));
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
        } else if (button == crimsonIsleMusic) {
            ToggleCommand.crimsonIsleMusic = !ToggleCommand.crimsonIsleMusic;
            CustomMusic.crimsonIsle.stop();
            ConfigHandler.writeBooleanConfig("toggles", "BlazingFortressMusic", ToggleCommand.crimsonIsleMusic);
            crimsonIsleMusic.displayString = "Custom Crimson Isle Music: " + Utils.getColouredBoolean(ToggleCommand.crimsonIsleMusic);
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
        } else if (button == disableHypixelMusic) {
            ToggleCommand.disableHypixelMusic = !ToggleCommand.disableHypixelMusic;
            ConfigHandler.writeBooleanConfig("toggles", "DisableHypixelMusic", ToggleCommand.disableHypixelMusic);
            disableHypixelMusic.displayString = "Disable Hypixel Music: " + Utils.getColouredBoolean(ToggleCommand.disableHypixelMusic);
        }
    }

}
