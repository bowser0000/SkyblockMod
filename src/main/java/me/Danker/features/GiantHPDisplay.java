package me.Danker.features;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GiantHPDisplay {

    static Pattern f6GiantPattern = Pattern.compile("(Jolly Pink Giant|L\\.A\\.S\\.R\\.|The Diamond Giant|Bigfoot).*");
    static List<Entity> giants = new ArrayList<>();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        World world = Minecraft.getMinecraft().theWorld;
        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            if (ModConfig.giantHPHud.isEnabled() && Utils.isInDungeons() && world != null) {
                giants.clear();
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                if (scoreboard.size() == 0) return;
                String firstLine = ScoreboardHandler.cleanSB(scoreboard.get(scoreboard.size() - 1));

                if (firstLine.contains("sadan")) {
                    List<Entity> entities = world.getLoadedEntityList();

                    for (Entity entity : entities) {
                        String name = StringUtils.stripControlCodes(entity.getName());
                        if (f6GiantPattern.matcher(name).find()) {
                            giants.add(entity);
                        }
                    }
                } else if (firstLine.contains("138,30") || firstLine.contains("354,66") || firstLine.contains("138,66")) {
                    List<Entity> entities = world.getLoadedEntityList();

                    for (Entity entity : entities) {
                        if (entity.getName().contains("Giant ")) {
                            giants.add(entity);
                        }
                    }
                }
            }
        }
    }

    public static class GiantHPHud extends Hud {

        @Exclude
        String exampleText = EnumChatFormatting.DARK_RED + "L.A.S.R. " + EnumChatFormatting.GREEN + "25M" + EnumChatFormatting.RED + "❤\n" +
                             EnumChatFormatting.RED + "Bigfoot " + EnumChatFormatting.GREEN + "25M" + EnumChatFormatting.RED + "❤\n" +
                             EnumChatFormatting.LIGHT_PURPLE + "Jolly Pink Giant " + EnumChatFormatting.GREEN + "25M" + EnumChatFormatting.RED + "❤\n" +
                             EnumChatFormatting.DARK_AQUA + "The Diamond Giant " + EnumChatFormatting.GREEN + "25M" + EnumChatFormatting.RED + "❤";

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            if (example) {
                TextRenderer.drawHUDText(exampleText, x, y, scale);
                return;
            }

            if (enabled && Utils.isInDungeons() && giants.size() > 0) {
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
            StringBuilder sb = new StringBuilder();

            for (Entity giant : giants) {
                if (!giant.isDead) sb.append(Utils.removeBold(giant.getDisplayName().getFormattedText())).append("\n");
            }

            return sb.toString();
        }

    }

}
