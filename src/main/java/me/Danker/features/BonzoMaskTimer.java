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

public class BonzoMaskTimer {

    public static double nextBonzoUse = 0;
    public static final ResourceLocation BONZO_ICON = new ResourceLocation("dsm", "icons/bonzo.png");

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        nextBonzoUse = 0;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.isInDungeons()) return;
        if (message.contains(":")) return;

        if (ModConfig.bonzoTimerHud.isEnabled() && message.contains("Bonzo's Mask") && message.contains("saved your life!")) {
            double usedTime = System.currentTimeMillis() / 1000;
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.thePlayer;
            ItemStack bonzoMask = player.getCurrentArmor(3);
            if (bonzoMask != null && bonzoMask.getItem() == Items.skull) {
                int cooldownSeconds = 0;
                for (String line : Utils.getItemLore(bonzoMask)) {
                    String stripped = StringUtils.stripControlCodes(line);
                    if (stripped.startsWith("Cooldown: "))
                        cooldownSeconds = Integer.parseInt(stripped.replaceAll("\\D", ""));
                }
                System.out.println("Parsed Bonzo Mask Cooldown: " + cooldownSeconds);
                if (cooldownSeconds > 0)
                    nextBonzoUse = usedTime + cooldownSeconds;
            }
        }
    }

    public static class BonzoTimerHud extends Hud {

        @Exclude
        String exampleText = ModConfig.getColour(bonzoTimerColour) + "3m30s";

        @Dropdown(
                name = "Bonzo Timer Text Color",
                options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"}
        )
        public static int bonzoTimerColour = 12;

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            Minecraft mc = Minecraft.getMinecraft();

            if (example) {
                double scaleReset = Math.pow(scale, -1);
                GL11.glScaled(scale, scale, scale);

                mc.getTextureManager().bindTexture(BONZO_ICON);
                RenderUtils.drawModalRectWithCustomSizedTexture(x / scale, y / scale, 0, 0, 16, 16, 16, 16);

                GL11.glScaled(scaleReset, scaleReset, scaleReset);

                TextRenderer.drawHUDText(exampleText, x + 20 * scale, y + 5 * scale, scale);
                return;
            }

            if (enabled && Utils.isInDungeons()) {
                ItemStack helmetSlot = mc.thePlayer.getCurrentArmor(3);
                if ((helmetSlot != null && helmetSlot.getDisplayName().contains("Bonzo's Mask")) || nextBonzoUse > 0) {
                    double scaleReset = Math.pow(scale, -1);
                    GL11.glScaled(scale, scale, scale);

                    mc.getTextureManager().bindTexture(BONZO_ICON);
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
            if (nextBonzoUse - timeNow < 0) {
                return EnumChatFormatting.GREEN + "READY";
            } else {
                return ModConfig.getColour(bonzoTimerColour) + Utils.getTimeBetween(timeNow, nextBonzoUse);
            }
        }

    }

}
