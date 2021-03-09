package me.Danker.features;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlay;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class BonzoMaskTimer {

    public static double nextBonzoUse = 0;
    public static String BONZO_COLOR;
    public static final ResourceLocation BONZO_ICON = new ResourceLocation("dsm", "icons/bonzo.png");

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        nextBonzoUse = 0;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inDungeons) return;

        if (ToggleCommand.bonzoTimerToggled && message.contains("Bonzo's Mask") && message.contains("saved your life!")) {
            double usedTime = System.currentTimeMillis() / 1000;
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.thePlayer;
            ItemStack bonzoMask = player.getCurrentArmor(3);
            if (bonzoMask != null && bonzoMask.getItem() == Items.skull) {
                int cooldownSeconds = 0;
                for (String line : Utils.getItemLore(bonzoMask)) {
                    String stripped = StringUtils.stripControlCodes(line);
                    if (stripped.startsWith("Cooldown: "))
                        cooldownSeconds = Integer.parseInt(stripped.replaceAll("[^\\d]", ""));
                }
                System.out.println("Parsed Bonzo Mask Cooldown: " + cooldownSeconds);
                if (cooldownSeconds > 0)
                    nextBonzoUse = usedTime + cooldownSeconds;
            }
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event) {
        if (ToggleCommand.bonzoTimerToggled && Utils.inDungeons) {
            Minecraft mc = Minecraft.getMinecraft();
            ItemStack helmetSlot = mc.thePlayer.getCurrentArmor(3);
            if ((helmetSlot != null && helmetSlot.getDisplayName().contains("Bonzo's Mask")) || nextBonzoUse > 0) {

                double scale = ScaleCommand.bonzoTimerScale;
                double scaleReset = Math.pow(scale, -1);
                GL11.glScaled(scale, scale, scale);

                double timeNow = System.currentTimeMillis() / 1000;
                mc.getTextureManager().bindTexture(BONZO_ICON);
                Gui.drawModalRectWithCustomSizedTexture(MoveCommand.bonzoTimerXY[0], MoveCommand.bonzoTimerXY[1], 0, 0, 16, 16, 16, 16);

                String bonzoText;
                if (nextBonzoUse - timeNow < 0) {
                    bonzoText = EnumChatFormatting.GREEN + "READY";
                } else {
                    bonzoText = BONZO_COLOR + Utils.getTimeBetween(timeNow, nextBonzoUse);
                }
                new TextRenderer(mc, bonzoText, MoveCommand.bonzoTimerXY[0] + 20, MoveCommand.bonzoTimerXY[1] + 5, 1);

                GL11.glScaled(scaleReset, scaleReset, scaleReset);
            }
        }
    }

}
