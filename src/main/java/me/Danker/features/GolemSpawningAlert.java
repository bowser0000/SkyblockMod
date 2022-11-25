package me.Danker.features;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.config.ModConfig;
import me.Danker.events.RenderOverlayEvent;
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

    double golemTime = 0;
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

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlayEvent event) {
        if (ModConfig.golemAlerts && Utils.inSkyblock && golemTime > System.currentTimeMillis() / 1000) {
            Minecraft mc = Minecraft.getMinecraft();
            double scale = ScaleCommand.golemTimerScale;
            double scaleReset = Math.pow(scale, -1);
            GL11.glScaled(scale, scale, scale);

            double timeNow = System.currentTimeMillis() / 1000;
            mc.getTextureManager().bindTexture(GOLEM_ICON);

            RenderUtils.drawModalRectWithCustomSizedTexture(MoveCommand.golemTimerXY[0] / scale, MoveCommand.golemTimerXY[1] / scale, 0, 0, 16, 16, 16, 16);
            GL11.glScaled(scaleReset, scaleReset, scaleReset);

            String golemText = ModConfig.getColour(ModConfig.golemAlertColour) + Utils.getTimeBetween(timeNow, golemTime);
            new TextRenderer(mc, golemText, MoveCommand.golemTimerXY[0] + 20 * scale, MoveCommand.golemTimerXY[1] + 5 * scale, scale);
        }
    }

}
