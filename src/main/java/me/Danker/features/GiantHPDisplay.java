package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlay;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
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
            if (ToggleCommand.giantHP && Utils.inDungeons && world != null) {
                giants.clear();
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
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

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event) {
        if (ToggleCommand.giantHP && Utils.inDungeons && giants.size() > 0) {
            StringBuilder sb = new StringBuilder();

            for (Entity giant : giants) {
                if (!giant.isDead) sb.append(Utils.removeBold(giant.getDisplayName().getFormattedText())).append("\n");
            }

            new TextRenderer(Minecraft.getMinecraft(), sb.toString(), 100, 100, 1);
        }
    }

}
