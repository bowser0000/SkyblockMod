package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

/**
 * @author RabbitType99
 */

public class ArachneESP {

    static Entity arachne = null;
    static boolean arachneActive = true;
    public static int ARACHANE_COLOUR;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        arachne = null;
    }

    public boolean inSpidersDen(List<String> scoreboard) {
        for (String s : scoreboard) {
            if (ScoreboardHandler.cleanSB(s).contains("Spiders Den")) {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        if (!inSpidersDen(ScoreboardHandler.getSidebarLines())) return;
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (message.contains("Something is awakening")){
            arachneActive = true;
            World world = Minecraft.getMinecraft().theWorld;
            List<Entity> entities = world.getLoadedEntityList();
            for (Entity e : entities) {
                if (e.getName().contains("Arachne") && !e.getName().contains("Arachne's Brood")) {
                    arachne = e;
                }
            }
        }
        if (message.contains("ARACHNE DOWN!")) {
            arachneActive = false;
            arachne = null;
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (!Utils.inSkyblock) return;
        if (arachne != null) {
            if (arachneActive && ToggleCommand.highlightArachne) {
                AxisAlignedBB aabb = new AxisAlignedBB(arachne.posX - 0.75, arachne.posY - 1, arachne.posZ - 0.75, arachne.posX + 0.75, arachne.posY, arachne.posZ + 0.75);
                Utils.draw3DBox(aabb, ARACHANE_COLOUR, event.partialTicks);
            }
        }
    }

}

