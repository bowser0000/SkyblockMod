package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
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
    public static final int SLAYER_COLOUR = 0xFF0000;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        zombie = null;
        spider = null;
        wolf = null;
    }
    
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        //if (!Utils.inSkyblock) return;
        if (event.phase != TickEvent.Phase.START) return;
        
        World world = Minecraft.getMinecraft().theWorld;
        if (DankersSkyblockMod.tickAmount % 2 == 0) {
            if (world != null) {
                List<Entity> entities = world.getLoadedEntityList();
                for (Entity e : entities) {
                    System.out.println(e.getName());
                    if (e.getName().contains("Revenant Horror")) {
                        zombie = e;
                    } else if (e.getName().contains("Tarantula Broodfather")) {
                        spider = e;
                    } else if (e.getName().contains("Sven Packmaster")) {
                        wolf = e;
                    }

                }
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        //if (!Utils.inSkyblock) return;
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (message.contains("SLAYER QUEST COMPLETE!")) {
            slayerActive = false;
        }

    }
    @SubscribeEvent
    public void onAttackingEntity(AttackEntityEvent event) {
        if (event.target instanceof EntityZombie || event.target instanceof EntitySpider || event.target instanceof EntityWolf) {
            List<String> scoreboard = ScoreboardHandler.getSidebarLines();

            for (String line : scoreboard) {
                String cleanedLine = ScoreboardHandler.cleanSB(line);
                if (cleanedLine.contains("Slay the boss!")) {
                    slayerActive = true;
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        //if (!Utils.inSkyblock) return;
        if (slayerActive) {
            if (zombie != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(zombie.posX - 0.5, zombie.posY - 2, zombie.posZ - 0.5, zombie.posX + 0.5, zombie.posY, zombie.posZ + 0.5);
                Utils.draw3DBox(aabb, SLAYER_COLOUR, event.partialTicks);
            }
            if (spider != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(spider.posX - 0.5, spider.posY - 1, spider.posZ - 0.5, spider.posX + 0.5, spider.posY, spider.posZ + 0.5);
                Utils.draw3DBox(aabb, SLAYER_COLOUR, event.partialTicks);
            }
            if (wolf != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(wolf.posX - 0.5, wolf.posY - 1, wolf.posZ - 0.5, wolf.posX + 0.5, wolf.posY, wolf.posZ + 0.5);
                Utils.draw3DBox(aabb, SLAYER_COLOUR, event.partialTicks);

            }
        }
    }

}
