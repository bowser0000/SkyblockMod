package me.Danker.gui.crystalhollowwaypoints;

import me.Danker.features.CrystalHollowWaypoints;
import me.Danker.handlers.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class CrystalHollowAddWaypointGui extends GuiScreen {

    private GuiButton cancel;

    private GuiTextField name;
    private GuiButton curPos;
    private GuiTextField x;
    private GuiTextField y;
    private GuiTextField z;
    private GuiButton add;

    private int xPos = -1;
    private int yPos = -1;
    private int zPos = -1;

    public CrystalHollowAddWaypointGui() {}

    public CrystalHollowAddWaypointGui(int x, int y, int z) {
        xPos = x;
        yPos = y;
        zPos = z;
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

        cancel = new GuiButton(0, 2, height - 30, 100, 20, "Cancel");

        name = new GuiTextField(0, this.fontRendererObj, width / 2 - 100, (int) (height * 0.1), 200, 20);
        curPos = new GuiButton(0, width / 2 - 50, (int) (height * 0.25), 100, 20, "Current Position");
        x = new GuiTextField(0, this.fontRendererObj, width / 2 - 85, (int) (height * 0.4), 50, 20);
        y = new GuiTextField(0, this.fontRendererObj, width / 2 - 25, (int) (height * 0.4), 50, 20);
        z = new GuiTextField(0, this.fontRendererObj, width / 2 + 35, (int) (height * 0.4), 50, 20);
        add = new GuiButton(0, width / 2 - 25, (int) (height * 0.6), 50, 20, "Add");

        name.setVisible(true);
        name.setEnabled(true);
        x.setVisible(true);
        x.setEnabled(true);
        if (xPos != -1) x.setText(xPos + "");
        y.setVisible(true);
        y.setEnabled(true);
        if (yPos != -1) y.setText(yPos + "");
        z.setVisible(true);
        z.setEnabled(true);
        if (zPos != -1) z.setText(zPos + "");

        this.buttonList.add(cancel);
        this.buttonList.add(curPos);
        this.buttonList.add(add);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        name.drawTextBox();
        x.drawTextBox();
        y.drawTextBox();
        z.drawTextBox();

        new TextRenderer(mc, "X:", width / 2 - 85, (int) (height * 0.35), 1D);
        new TextRenderer(mc, "Y:", width / 2 - 25, (int) (height * 0.35), 1D);
        new TextRenderer(mc, "Z:", width / 2 + 35, (int) (height * 0.35), 1D);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if (button == cancel) {
            mc.displayGuiScreen(new CrystalHollowWaypointsGui(1));
        } else if (button == curPos) {
            x.setText(Integer.toString(player.getPosition().getX()));
            y.setText(Integer.toString(player.getPosition().getY()));
            z.setText(Integer.toString(player.getPosition().getZ()));
        } else if (button == add) {
            String loc = name.getText().length() == 0 ? Integer.toString(CrystalHollowWaypoints.waypoints.size()) : name.getText();
            int xPos = x.getText().matches("[-]?\\d+") ? Integer.parseInt(x.getText()) : player.getPosition().getX();
            int yPos = y.getText().matches("[-]?\\d+") ? Integer.parseInt(y.getText()) : player.getPosition().getY();
            int zPos = z.getText().matches("[-]?\\d+") ? Integer.parseInt(z.getText()) : player.getPosition().getZ();

            BlockPos pos = new BlockPos(xPos, yPos, zPos);
            CrystalHollowWaypoints.waypoints.add(new CrystalHollowWaypoints.Waypoint(loc, pos));

            mc.displayGuiScreen(new CrystalHollowWaypointsGui(1));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        name.mouseClicked(mouseX, mouseY, mouseButton);
        x.mouseClicked(mouseX, mouseY, mouseButton);
        y.mouseClicked(mouseX, mouseY, mouseButton);
        z.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        name.textboxKeyTyped(typedChar, keyCode);
        x.textboxKeyTyped(typedChar, keyCode);
        y.textboxKeyTyped(typedChar, keyCode);
        z.textboxKeyTyped(typedChar, keyCode);
    }

}
