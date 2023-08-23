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
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class SpiritMaskTimer {

    public static double nextspiritUse = 0;
    public static final ResourceLocation SPIRIT_ICON = new ResourceLocation("dsm", "icons/spiritmask.png");

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        nextspiritUse = 0;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains(":")) return;

        if (ModConfig.spiritTimerHud.isEnabled() && message.contains("Spirit Mask") && message.contains("Second Wind Activated! Your Spirit Mask saved your life!")) {
            double usedTime = System.currentTimeMillis() / 1000;
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.thePlayer;
            ItemStack spiritMask = player.getCurrentArmor(3);
            if (spiritMask != null && spiritMask.getItem() == Items.skull) {
                int cooldownSeconds = 0;
                for (String line : Utils.getItemLore(spiritMask)) {
                    String stripped = StringUtils.stripControlCodes(line);
                    if (stripped.startsWith("Cooldown: "))
                        cooldownSeconds = Integer.parseInt(stripped.replaceAll("\\D", ""));
                }
                System.out.println("Parsed Spirit Mask Cooldown: " + cooldownSeconds);
                if (cooldownSeconds > 0)
                    nextspiritUse = usedTime + cooldownSeconds;
            }
        }
    }

    public static class SpiritTimerHud extends Hud {

        @Exclude
        String exampleText = ModConfig.getColour(spiritTimerColour) + "30s";

        @Dropdown(
                name = "Spirit Timer Text Color",
                options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"}
        )
        public static int spiritTimerColour = 12;

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            Minecraft mc = Minecraft.getMinecraft();

            if (example) {
                double scaleReset = Math.pow(scale, -1);
                GL11.glScaled(scale, scale, scale);

                mc.getTextureManager().bindTexture(SPIRIT_ICON);
                RenderUtils.drawModalRectWithCustomSizedTexture(x / scale, y / scale, 0, 0, 16, 16, 16, 16);

                GL11.glScaled(scaleReset, scaleReset, scaleReset);

                TextRenderer.drawHUDText(exampleText, x + 20 * scale, y + 5 * scale, scale);
                return;
            }

            if (enabled) {
                ItemStack helmetSlot = mc.thePlayer.getCurrentArmor(3);
                if ((helmetSlot != null && helmetSlot.getDisplayName().contains("Spirit Mask")) || nextspiritUse > 0) {
                    double scaleReset = Math.pow(scale, -1);
                    GL11.glScaled(scale, scale, scale);

                    mc.getTextureManager().bindTexture(SPIRIT_ICON);
                    RenderUtils.drawModalRectWithCustomSizedTexture(x / scale, y / scale, 0, 0, 16, 16, 16, 16);

                    GL11.glScaled(scaleReset, scaleReset, scaleReset);

                    TextRenderer.drawHUDText(getText(), x + 20 * scale, y + 5 * scale, scale);
                }
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
            if (nextspiritUse - timeNow < 0) {
                return EnumChatFormatting.GREEN + "READY";
            } else {
                return ModConfig.getColour(spiritTimerColour) + Utils.getTimeBetween(timeNow, nextspiritUse);
            }
        }

    }

}
