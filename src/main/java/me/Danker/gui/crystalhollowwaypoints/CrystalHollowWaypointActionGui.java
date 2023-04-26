package me.Danker.gui.crystalhollowwaypoints;

import cc.polyfrost.oneconfig.libs.universal.UResolution;
import me.Danker.features.CrystalHollowWaypoints;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class CrystalHollowWaypointActionGui extends GuiScreen {

    private int id;

    private GuiButton goBack;
    private GuiButton toggle;
    private GuiButton sendNormal;
    private GuiButton sendDSM;
    private GuiButton sendSBE;
    private GuiButton delete;

    public CrystalHollowWaypointActionGui(int id) {
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

        CrystalHollowWaypoints.Waypoint waypoint = CrystalHollowWaypoints.waypoints.get(id);

        goBack = new GuiButton(0, 2, height - 30, 100, 20, "Go Back");
        toggle = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Set Visibility: " + Utils.getColouredBoolean(waypoint.toggled));
        sendNormal = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Send Location And Coordinates");
        sendDSM = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Send DSM Formatted Waypoint");
        sendSBE = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Send SBE Formatted Waypoint");
        delete = new GuiButton(0, width / 2 - 100, (int) (height * 0.8), EnumChatFormatting.RED + "Delete Waypoint");

        this.buttonList.add(toggle);
        this.buttonList.add(sendNormal);
        this.buttonList.add(sendDSM);
        this.buttonList.add(sendSBE);
        this.buttonList.add(delete);
        this.buttonList.add(goBack);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        CrystalHollowWaypoints.Waypoint waypoint = CrystalHollowWaypoints.waypoints.get(id);

        RenderUtils.drawCenteredText(waypoint.location + " @ " + waypoint.getPos(), width, 10, 1D);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        CrystalHollowWaypoints.Waypoint waypoint = CrystalHollowWaypoints.waypoints.get(id);
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (button == goBack) {
            mc.displayGuiScreen(new CrystalHollowWaypointsGui(1));
        } else if (button == toggle) {
            waypoint.toggle();
            toggle.displayString = "Set Visibility: " + Utils.getColouredBoolean(waypoint.toggled);
        } else if (button == sendNormal) {
            player.sendChatMessage(waypoint.location + " @ " + waypoint.getPos());
        } else if (button == sendDSM) {
            player.sendChatMessage("$DSMCHWP:" + waypoint.getFormattedWaypoint());
        } else if (button == sendSBE) {
            player.sendChatMessage("$SBECHWP:" + waypoint.getFormattedWaypoint());
        } else if (button == delete) {
            CrystalHollowWaypoints.waypoints.remove(id);
            mc.displayGuiScreen(new CrystalHollowWaypointsGui(1));
            return;
        }
        CrystalHollowWaypoints.waypoints.set(id, waypoint);
    }

}
