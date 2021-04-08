package me.Danker.features;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlay;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class GolemSpawningAlert {

    double golemTime = 0;
    public static final ResourceLocation GOLEM_ICON = new ResourceLocation("dsm", "icons/golem.png");
    public static String GOLEM_COLOUR;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (message.contains(":")) return;

        if (ToggleCommand.golemAlertToggled) {
            if (message.contains("The ground begins to shake as an Endstone Protector rises from below!")) {
                golemTime = System.currentTimeMillis() / 1000 + 20;
                Utils.createTitle(EnumChatFormatting.RED + "GOLEM SPAWNING!", 3);
            }
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event) {
        if (ToggleCommand.golemAlertToggled && Utils.inSkyblock && golemTime > System.currentTimeMillis() / 1000) {
            Minecraft mc = Minecraft.getMinecraft();
            double scale = ScaleCommand.golemTimerScale;
            double scaleReset = Math.pow(scale, -1);
            GL11.glScaled(scale, scale, scale);

            double timeNow = System.currentTimeMillis() / 1000;
            mc.getTextureManager().bindTexture(GOLEM_ICON);
            Gui.drawModalRectWithCustomSizedTexture(MoveCommand.golemTimerXY[0], MoveCommand.golemTimerXY[1], 0, 0, 16, 16, 16, 16);

            String golemText = GOLEM_COLOUR + Utils.getTimeBetween(timeNow, golemTime);
            new TextRenderer(mc, golemText, MoveCommand.golemTimerXY[0] + 20, MoveCommand.golemTimerXY[1] + 5, 1);

            GL11.glScaled(scaleReset, scaleReset, scaleReset);
        }
    }

}
