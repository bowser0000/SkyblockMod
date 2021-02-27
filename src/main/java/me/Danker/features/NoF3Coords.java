package me.Danker.features;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlay;
import me.Danker.handlers.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoF3Coords {

    public static String COORDS_COLOUR;

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (ToggleCommand.coordsToggled) {
            EntityPlayer player = mc.thePlayer;

            double xDir = (player.rotationYaw % 360 + 360) % 360;
            if (xDir > 180) xDir -= 360;
            xDir = (double) Math.round(xDir * 10d) / 10d;
            double yDir = (double) Math.round(player.rotationPitch * 10d) / 10d;

            String coordText = COORDS_COLOUR + (int) player.posX + " / " + (int) player.posY + " / " + (int) player.posZ + " (" + xDir + " / " + yDir + ")";
            new TextRenderer(mc, coordText, MoveCommand.coordsXY[0], MoveCommand.coordsXY[1], ScaleCommand.coordsScale);
        }
    }

}
