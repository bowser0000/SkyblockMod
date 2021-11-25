package me.Danker.gui.crystalhollowwaypoints;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.features.CrystalHollowWaypoints;
import me.Danker.gui.DankerGui;
import me.Danker.gui.buttons.FeatureButton;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

public class CrystalHollowWaypointsGui extends GuiScreen {

    private final int page;
    private final List<GuiButton> allButtons = new ArrayList<>();

    private GuiButton goBack;
    private GuiButton backPage;
    private GuiButton nextPage;
    private GuiButton sendDSM;
    private GuiButton sendSBE;
    private GuiButton add;
    private FeatureButton crystalHollowWaypoints;
    private FeatureButton crystalAutoWaypoints;

    public CrystalHollowWaypointsGui(int page) {
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
        sendDSM = new GuiButton(0, 2, 10, 175, 20, "Send DSM Formatted Waypoints");
        sendSBE = new GuiButton(0, 2, 40, 175, 20, "Send SBE Formatted Waypoints");
        add = new GuiButton(0, 0, 0, "Add Waypoint");
        crystalHollowWaypoints = new FeatureButton("Crystal Hollows Waypoints: " + Utils.getColouredBoolean(ToggleCommand.crystalHollowWaypoints), "Shows waypoints to various places in the Crystal Hollows.");
        crystalAutoWaypoints = new FeatureButton("Auto Waypoints: " + Utils.getColouredBoolean(ToggleCommand.crystalAutoWaypoints), "Automatically creates waypoints when you visit a special place in the Crystal Hollows.");

        allButtons.clear();
        allButtons.add(add);
        allButtons.add(crystalHollowWaypoints);
        allButtons.add(crystalAutoWaypoints);
        for (int i = 0; i < CrystalHollowWaypoints.waypoints.size(); i++) {
            CrystalHollowWaypoints.Waypoint waypoint = CrystalHollowWaypoints.waypoints.get(i);
            GuiButton button = new GuiButton(i, 0, 0, waypoint.location + " >");
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
        this.buttonList.add(sendDSM);
        this.buttonList.add(sendSBE);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        String pageText = "Page: " + page + "/" + (int) Math.ceil(allButtons.size() / 7D);
        int pageWidth = mc.fontRendererObj.getStringWidth(pageText);
        new TextRenderer(mc, pageText, width / 2 - pageWidth / 2, 10, 1D);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (button == goBack) {
            mc.displayGuiScreen(new DankerGui(1, ""));
        } else if (button == nextPage) {
            mc.displayGuiScreen(new CrystalHollowWaypointsGui(page + 1));
        } else if (button == backPage) {
            mc.displayGuiScreen(new CrystalHollowWaypointsGui(page - 1));
        } else if (button == sendDSM) {
            if (CrystalHollowWaypoints.waypoints.size() > 0) {
                StringBuilder message = new StringBuilder();
                for (CrystalHollowWaypoints.Waypoint waypoint : CrystalHollowWaypoints.waypoints) {
                    if (message.length() > 0) message.append("\\n");
                    message.append(waypoint.getFormattedWaypoint());
                }
                message.insert(0, "$DSMCHWP:");
                player.sendChatMessage(message.toString());
            }
        } else if (button == sendSBE) {
            if (CrystalHollowWaypoints.waypoints.size() > 0) {
                StringBuilder message = new StringBuilder();
                for (CrystalHollowWaypoints.Waypoint waypoint : CrystalHollowWaypoints.waypoints) {
                    if (message.length() > 0) message.append("\\n");
                    message.append(waypoint.getFormattedWaypoint());
                }
                message.insert(0, "$SBECHWP:");
                player.sendChatMessage(message.toString());
            }
        } else if (button == add) {
            mc.displayGuiScreen(new CrystalHollowAddWaypointGui());
        } else if (button == crystalHollowWaypoints) {
            ToggleCommand.crystalHollowWaypoints = !ToggleCommand.crystalHollowWaypoints;
            ConfigHandler.writeBooleanConfig("toggles", "CrystalHollowWaypoints", ToggleCommand.crystalHollowWaypoints);
            crystalHollowWaypoints.displayString = "Crystal Hollows Waypoints: " + Utils.getColouredBoolean(ToggleCommand.crystalHollowWaypoints);
        } else if (button == crystalAutoWaypoints) {
            ToggleCommand.crystalAutoWaypoints = !ToggleCommand.crystalAutoWaypoints;
            ConfigHandler.writeBooleanConfig("toggles", "CrystalAutoWaypoints", ToggleCommand.crystalAutoWaypoints);
            crystalAutoWaypoints.displayString = "Auto Waypoints: " + Utils.getColouredBoolean(ToggleCommand.crystalAutoWaypoints);
        } else {
            mc.displayGuiScreen(new CrystalHollowWaypointActionGui(button.id));
        }
    }

}
