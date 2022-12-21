package me.Danker.features;

import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.config.ModConfig;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class NoF3Coords extends Hud {

    @Exclude
    String exampleText = ModConfig.getColour(coordsColour) + "74 / 14 / -26 (141.1 / 6.7)";

    @Dropdown(
            name = "Coordinate/Angle Color",
            options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"}
    )
    public static int coordsColour = 15;

    @Override
    protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        if (example) {
            TextRenderer.drawHUDText(exampleText, x, y, scale);
            return;
        }

        if (enabled) {
            TextRenderer.drawHUDText(getText(), x, y, scale);
        }
    }

    @Override
    protected float getWidth(float scale, boolean example) {
        return RenderUtils.getWidthFromText(example ? exampleText : getText()) * scale;
    }

    @Override
    protected float getHeight(float scale, boolean example) {
        return RenderUtils.getHeightFromText(example ? exampleText : getText()) * scale;
    }

    String getText() {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;

        double xDir = (player.rotationYaw % 360 + 360) % 360;
        if (xDir > 180) xDir -= 360;
        xDir = (double) Math.round(xDir * 10d) / 10d;
        double yDir = (double) Math.round(player.rotationPitch * 10d) / 10d;

        return ModConfig.getColour(coordsColour) + (int) player.posX + " / " + (int) player.posY + " / " + (int) player.posZ + " (" + xDir + " / " + yDir + ")";
    }

}
