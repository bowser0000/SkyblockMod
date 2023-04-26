package me.Danker.features;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.handlers.TextRenderer;
import me.Danker.locations.Location;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class FirePillarDisplay {

    static Entity pillar = null;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        World world = Minecraft.getMinecraft().theWorld;
        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            pillar = null;
            if (ModConfig.firePillarHud.isEnabled() && world != null && Utils.currentLocation == Location.CRIMSON_ISLE && Utils.isInScoreboard("Slay the boss!")) {
                List<Entity> entities = world.getLoadedEntityList();

                for (Entity entity : entities) {
                    String name = StringUtils.stripControlCodes(entity.getName());
                    if (name.endsWith(" hits") && name.charAt(1) == 's') {
                        pillar = entity;
                        break;
                    }
                }
            }
        }
    }

    public static class FirePillarHud extends Hud {

        @Exclude
        String exampleText = EnumChatFormatting.GOLD + "3s " + EnumChatFormatting.RED + "8 hits";

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            if (example) {
                TextRenderer.drawHUDText(exampleText, x, y, scale);
                return;
            }

            if (enabled && pillar != null) {
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
            if (pillar != null) return Utils.removeBold(pillar.getName());
            return "";
        }

    }

}
