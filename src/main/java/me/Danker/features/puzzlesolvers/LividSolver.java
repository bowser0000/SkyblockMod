package me.Danker.features.puzzlesolvers;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlay;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class LividSolver {

    static boolean foundLivid = false;
    static Entity livid = null;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        foundLivid = false;
        livid = null;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        World world = Minecraft.getMinecraft().theWorld;
        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            if (ToggleCommand.lividSolverToggled && Utils.inDungeons && !foundLivid && world != null) {
                boolean inF5 = false;

                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                for (String s : scoreboard) {
                    String sCleaned = ScoreboardHandler.cleanSB(s);
                    if (sCleaned.contains("The Catacombs (F5)")) {
                        inF5 = true;
                        break;
                    }
                }

                if (inF5) {
                    List<Entity> loadedLivids = new ArrayList<>();
                    List<Entity> entities = world.getLoadedEntityList();
                    for (Entity entity : entities) {
                        String name = entity.getName();
                        if (name.contains("Livid") && name.length() > 5 && name.charAt(1) == name.charAt(5) && !loadedLivids.contains(entity)) {
                            loadedLivids.add(entity);
                        }
                    }
                    if (loadedLivids.size() > 8) {
                        livid = loadedLivids.get(0);
                        foundLivid = true;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event) {
        if (ToggleCommand.lividSolverToggled && foundLivid && livid != null) {
            new TextRenderer(Minecraft.getMinecraft(), livid.getName().replace("" + EnumChatFormatting.BOLD, ""), MoveCommand.lividHpXY[0], MoveCommand.lividHpXY[1], ScaleCommand.lividHpScale);
        }
    }

    @SubscribeEvent
    public void onRenderEntity(RenderLivingEvent.Pre event) {
        Entity entity = event.entity;
        String name = entity.getName();
        if (entity instanceof EntityArmorStand) {
            if (ToggleCommand.lividSolverToggled && !entity.isEntityEqual(livid) && name.contains("Livid") && name.length() > 5 && name.charAt(1) == name.charAt(5)) {
                event.setCanceled(true);
            }
        }
    }

}
