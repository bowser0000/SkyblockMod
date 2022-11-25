package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.config.ModConfig;
import me.Danker.events.RenderOverlayEvent;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
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
            if (ModConfig.firePillar && world != null && Utils.tabLocation.equals("Crimson Isle") && Utils.isInScoreboard("Slay the boss!")) {
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

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlayEvent event) {
        if (ModConfig.firePillar && pillar != null) {
            new TextRenderer(Minecraft.getMinecraft(), Utils.removeBold(pillar.getName()), MoveCommand.firePillarXY[0], MoveCommand.firePillarXY[1], ScaleCommand.firePillarScale);
        }
    }

}
