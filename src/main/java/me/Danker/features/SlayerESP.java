package me.Danker.features;

import me.Danker.DankersSkyblockMod;
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
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

/**
 * @author CuzImClicks
 */

public class SlayerESP {

    static Entity zombie = null;
    static Entity spider = null;
    static Entity wolf = null;
    static boolean slayerActive = false;
    static boolean slayerStarted = false;
    public static int SLAYER_COLOUR;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        zombie = null;
        spider = null;
        wolf = null;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.phase != TickEvent.Phase.START) return;

        World world = Minecraft.getMinecraft().theWorld;
        if (world == null) return;
        if (!slayerStarted) return;
        if (zombie != null || spider != null || wolf != null) {
            return;
        }
        slayerActive = true;
        if (DankersSkyblockMod.tickAmount % 10 == 0 && ToggleCommand.highlightSlayers) {
            for (String line : ScoreboardHandler.getSidebarLines()) {
                String cleanedLine = ScoreboardHandler.cleanSB(line);
                if (cleanedLine.contains("Slay the boss!")) {
                    slayerActive = true;
                    List<Entity> entities = world.getLoadedEntityList();
                    for (Entity e : entities) {
                        System.out.println(e.getName());
                        if (e.getName().contains("Revenant Horror")) {
                            zombie = e;
                            return;
                        } else if (e.getName().contains("Tarantula Broodfather")) {
                            spider = e;
                            return;
                        } else if (e.getName().contains("Sven Packmaster")) {
                            wolf = e;
                            return;
                        }

                    }
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (message.contains("SLAYER QUEST STARTED!")) {
            slayerStarted = true;
        }
        if (message.contains("NICE! SLAYER BOSS SLAIN!") || message.contains("SLAYER QUEST COMPLETE!") || message.contains("SLAYER QUEST FAILED!")) {
            slayerActive = false;
            slayerStarted = false;
            zombie = null;
            spider = null;
            wolf = null;
        }

    }


    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (!Utils.inSkyblock) return;
        if (!slayerStarted) return;
        if (slayerActive && ToggleCommand.highlightSlayers) {
            if (zombie != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(zombie.posX - 0.5, zombie.posY - 2, zombie.posZ - 0.5, zombie.posX + 0.5, zombie.posY, zombie.posZ + 0.5);
                Utils.draw3DBox(aabb, SLAYER_COLOUR, event.partialTicks);
                return;
            }
            if (spider != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(spider.posX - 0.75, spider.posY - 1, spider.posZ - 0.75, spider.posX + 0.75, spider.posY, spider.posZ + 0.75);
                Utils.draw3DBox(aabb, SLAYER_COLOUR, event.partialTicks);
                return;
            }
            if (wolf != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(wolf.posX - 0.5, wolf.posY - 1, wolf.posZ - 0.5, wolf.posX + 0.5, wolf.posY, wolf.posZ + 0.5);
                Utils.draw3DBox(aabb, SLAYER_COLOUR, event.partialTicks);
                return;
            }
        }
    }

}
