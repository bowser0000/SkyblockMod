package me.Danker.features;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class TetherDisplay {

    static List<String> playersInRadius = new ArrayList<>();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        World world = mc.theWorld;
        if (DankersSkyblockMod.tickAmount % 10 == 0) {
            if (ModConfig.tetherHud.isEnabled() && Utils.isInDungeons() && player != null && world != null) {
                playersInRadius.clear();
                List<EntityPlayer> teammates = world.getEntitiesWithinAABB(EntityOtherPlayerMP.class, new AxisAlignedBB(player.posX - 30, player.posY - 30, player.posZ - 30, player.posX + 30, player.posY + 30, player.posZ + 30));

                for (EntityPlayer teammate : teammates) {
                    if (Utils.isRealPlayer(teammate) && !teammate.isInvisible() && player.getDistanceToEntity(teammate) <= 30F) {
                        playersInRadius.add(teammate.getDisplayName().getSiblings().get(0).getFormattedText());
                    }
                }
            }
        }
    }

    public static class TetherDisplayHud extends Hud {

        @Exclude
        String exampleText = EnumChatFormatting.AQUA + "Teammates In Radius:\n" +
                             EnumChatFormatting.GREEN + "NoticeMehSenpai\n" +
                             EnumChatFormatting.GREEN + "DeathStreeks\n" +
                             EnumChatFormatting.GREEN + "Not_A_Neko\n" +
                             EnumChatFormatting.GREEN + "Minikloon";

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            if (example) {
                TextRenderer.drawHUDText(exampleText, x, y, scale);
                return;
            }

            if (enabled && Utils.isInDungeons()) {
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
            String teammates;
            if (playersInRadius.size() > 0) {
                teammates = String.join("\n", playersInRadius);
            } else {
                teammates = EnumChatFormatting.RED + "NONE";
            }

            return EnumChatFormatting.AQUA + "Teammates In Radius:\n" + teammates;
        }

    }

}
