package me.Danker.features;

import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.config.ModConfig;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class GolemSpawningAlert {

    static double golemTime = 0;
    public static final ResourceLocation GOLEM_ICON = new ResourceLocation("dsm", "icons/golem.png");

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (message.contains(":")) return;

        if (ModConfig.golemAlerts) {
            if (message.contains("The ground begins to shake as an Endstone Protector rises from below!")) {
                golemTime = System.currentTimeMillis() / 1000 + 20;
                Utils.createTitle(EnumChatFormatting.RED + "GOLEM SPAWNING!", 3);
            }
        }
    }

    public static class GolemTimerHud extends Hud {

        @Exclude
        String exampleText = ModConfig.getColour(golemAlertColour) + "20s";

        @Dropdown(
                name = "Golem Timer Text Color",
                options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"}
        )
        public static int golemAlertColour = 6;

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            Minecraft mc = Minecraft.getMinecraft();

            if (example) {
                double scaleReset = Math.pow(scale, -1);
                GL11.glScaled(scale, scale, scale);

                mc.getTextureManager().bindTexture(GOLEM_ICON);
                RenderUtils.drawModalRectWithCustomSizedTexture(x / scale, y / scale, 0, 0, 16, 16, 16, 16);

                GL11.glScaled(scaleReset, scaleReset, scaleReset);

                TextRenderer.drawHUDText(exampleText, x + 20 * scale, y + 5 * scale, scale);
                return;
            }

            if (enabled && Utils.inSkyblock && golemTime > System.currentTimeMillis() / 1000) {
                double scaleReset = Math.pow(scale, -1);
                GL11.glScaled(scale, scale, scale);

                mc.getTextureManager().bindTexture(GOLEM_ICON);
                RenderUtils.drawModalRectWithCustomSizedTexture(x / scale, y / scale, 0, 0, 16, 16, 16, 16);

                GL11.glScaled(scaleReset, scaleReset, scaleReset);

                TextRenderer.drawHUDText(getText(), x + 20 * scale, y + 5 * scale, scale);
            }
        }

        @Override
        protected float getWidth(float scale, boolean example) {
            return (RenderUtils.getWidthFromText(example ? exampleText : getText()) + 20 * scale) * scale;
        }

        @Override
        protected float getHeight(float scale, boolean example) {
            return (RenderUtils.getHeightFromText(example ? exampleText : getText()) + 5 * scale) * scale;
        }

        String getText() {
            double timeNow = System.currentTimeMillis() / 1000;
            return ModConfig.getColour(golemAlertColour) + Utils.getTimeBetween(timeNow, golemTime);
        }

    }

}
