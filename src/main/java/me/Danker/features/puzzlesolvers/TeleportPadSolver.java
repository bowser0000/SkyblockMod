package me.Danker.features.puzzlesolvers;

import me.Danker.DankersSkyblockMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class TeleportPadSolver {

    static List<BlockPos> usedPads = new ArrayList<>();
    static BlockPos finalPad = null;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        usedPads.clear();
        finalPad = null;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        World world = mc.theWorld;

        if (DankersSkyblockMod.tickAmount % 20 == 0) {

        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {

    }

}
