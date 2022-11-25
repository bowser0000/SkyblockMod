package me.Danker.features;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.config.ModConfig;
import me.Danker.events.RenderOverlayEvent;
import me.Danker.handlers.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoF3Coords {

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlayEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (ModConfig.coords) {
            EntityPlayer player = mc.thePlayer;

            double xDir = (player.rotationYaw % 360 + 360) % 360;
            if (xDir > 180) xDir -= 360;
            xDir = (double) Math.round(xDir * 10d) / 10d;
            double yDir = (double) Math.round(player.rotationPitch * 10d) / 10d;

            String coordText = ModConfig.getColour(ModConfig.coordsColour) + (int) player.posX + " / " + (int) player.posY + " / " + (int) player.posZ + " (" + xDir + " / " + yDir + ")";
            new TextRenderer(mc, coordText, MoveCommand.coordsXY[0], MoveCommand.coordsXY[1], ScaleCommand.coordsScale);
        }
    }

}
