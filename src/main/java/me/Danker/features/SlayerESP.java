package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
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
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

/**
 * @author CuzImClicks
 */

public class SlayerESP {

    static Entity zombie = null;
    static Entity spider = null;
    static Entity wolf = null;
    static Entity enderman = null;
    static Entity blaze = null;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        zombie = null;
        spider = null;
        wolf = null;
        enderman = null;
        blaze = null;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.phase != TickEvent.Phase.START) return;

        World world = Minecraft.getMinecraft().theWorld;
        if (world == null) return;
        if (zombie != null || spider != null || wolf != null || enderman != null) return;
        // keep checking if blaze is active because it switches entities

        if (DankersSkyblockMod.tickAmount % 10 == 0 && ModConfig.highlightSlayers) {
            if (Utils.isInScoreboard("Slay the boss!")) {
                List<Entity> entities = world.getLoadedEntityList();
                for (Entity e : entities) {
                    String name = e.getName();
                    if (name.contains("Revenant Horror")) {
                        zombie = e;
                        return;
                    } else if (name.contains("Tarantula Broodfather")) {
                        spider = e;
                        return;
                    } else if (name.contains("Sven Packmaster")) {
                        wolf = e;
                        return;
                    } else if (name.contains("Voidgloom Seraph")) {
                        enderman = e;
                        return;
                    } else if (name.contains("Inferno Demonlord")) {
                        blaze = e;
                        return;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (message.contains(":")) return;

        if (message.contains("NICE! SLAYER BOSS SLAIN!") || message.contains("SLAYER QUEST COMPLETE!") || message.contains("SLAYER QUEST FAILED!")) {
            zombie = null;
            spider = null;
            wolf = null;
            enderman = null;
            blaze = null;
        }

    }


    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (!Utils.inSkyblock) return;
        if (ModConfig.highlightSlayers) {
            if (zombie != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(zombie.posX - 0.5, zombie.posY - 2, zombie.posZ - 0.5, zombie.posX + 0.5, zombie.posY, zombie.posZ + 0.5);
                RenderUtils.draw3DBox(aabb, ModConfig.slayerBoxColour.getRGB(), event.partialTicks);
            } else if (spider != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(spider.posX - 0.75, spider.posY - 1, spider.posZ - 0.75, spider.posX + 0.75, spider.posY, spider.posZ + 0.75);
                RenderUtils.draw3DBox(aabb, ModConfig.slayerBoxColour.getRGB(), event.partialTicks);
            } else if (wolf != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(wolf.posX - 0.5, wolf.posY - 1, wolf.posZ - 0.5, wolf.posX + 0.5, wolf.posY, wolf.posZ + 0.5);
                RenderUtils.draw3DBox(aabb, ModConfig.slayerBoxColour.getRGB(), event.partialTicks);
            } else if (enderman != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(enderman.posX - 0.5, enderman.posY - 3, enderman.posZ - 0.5, enderman.posX + 0.5, enderman.posY, enderman.posZ + 0.5);
                RenderUtils.draw3DBox(aabb, ModConfig.slayerBoxColour.getRGB(), event.partialTicks);
            } else if (blaze != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(blaze.posX - 0.5, blaze.posY - 2, blaze.posZ - 0.5, blaze.posX + 0.5, blaze.posY, blaze.posZ + 0.5);
                RenderUtils.draw3DBox(aabb, ModConfig.slayerBoxColour.getRGB(), event.partialTicks);
            }
        }
    }

}
