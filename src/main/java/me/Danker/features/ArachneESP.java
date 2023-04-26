package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.locations.Location;
import me.Danker.utils.RenderUtils;
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

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        arachne = null;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        if (Utils.currentLocation != Location.SPIDERS_DEN) return;
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
            if (arachneActive && ModConfig.highlightArachne) {
                AxisAlignedBB aabb = new AxisAlignedBB(arachne.posX - 0.75, arachne.posY - 1, arachne.posZ - 0.75, arachne.posX + 0.75, arachne.posY, arachne.posZ + 0.75);
                RenderUtils.draw3DBox(aabb, ModConfig.arachneBoxColour.getRGB(), event.partialTicks);
            }
        }
    }

}

