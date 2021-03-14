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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


import java.util.List;

/**
 * @author RabbitType99
 */

public class ArachneESP {


    static Entity arachne = null;
    static boolean arachneActive = true;
    public static final int ARACHANE_COLOUR =  0x00FF00;


    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        arachne = null;
    }

    public boolean isSpidersDen(List<String> scoreboard) {
        for (String s : scoreboard) {
            if (ScoreboardHandler.cleanSB(s).contains("Spiders Den")) {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.phase != TickEvent.Phase.START) return;

        World world = Minecraft.getMinecraft().theWorld;
        if (DankersSkyblockMod.tickAmount % 2 == 0 && ToggleCommand.highlightArachne) {
            if (world != null) {

                List<Entity> entities = world.getLoadedEntityList();
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                //if (!isSpidersDen(scoreboard)) return;
                if (!arachneActive) return;
                for (Entity e : entities) {
                    System.out.println(e.getName());
                    if (e.getName().contains("Arachne") && !e.getName().contains("Arachne's Brood")) {
                            arachne = e;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (message.contains("Something is awakening")){
            arachneActive = true;
        }
        if (message.contains("ARACHNE DOWN!")) {
            arachneActive = false;
        }
    }


    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (!Utils.inSkyblock) return;
        if (arachneActive && ToggleCommand.highlightArachne) {
            if (arachne != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(arachne.posX - 0.5, arachne.posY - 1, arachne.posZ - 0.5, arachne.posX + 0.5, arachne.posY, arachne.posZ + 0.5);
                Utils.draw3DBox(aabb, ARACHANE_COLOUR, event.partialTicks);
            }
        }
    }

}

