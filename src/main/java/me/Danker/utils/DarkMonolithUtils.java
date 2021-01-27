package me.Danker.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;

import static me.Danker.utils.Utils.renderBeaconBeam;

public class DarkMonolithUtils {
    public static BlockPos findMonolith() {
        ArrayList<BlockPos> monoliths = new ArrayList<>();
        monoliths.add(new BlockPos(-15, 236, -92));
        monoliths.add(new BlockPos(49, 202, -162));
        monoliths.add(new BlockPos(56, 214, -25));
        monoliths.add(new BlockPos(1, 170, 0));
        monoliths.add(new BlockPos(150, 196, 190));
        monoliths.add(new BlockPos(-64, 206, -63));
        monoliths.add(new BlockPos(-91, 221, -53));
        monoliths.add(new BlockPos(-94, 201, -30));
        monoliths.add(new BlockPos(-9, 162, 109));
        monoliths.add(new BlockPos(1, 183, 23));
        monoliths.add(new BlockPos(61, 204, 181));
        monoliths.add(new BlockPos(77, 160, 163)); //not in wiki
        monoliths.add(new BlockPos(91, 187, 131));
        monoliths.add(new BlockPos(128, 187, 58));

        for (BlockPos blockPos : monoliths) {
            BlockPos point1 = new BlockPos(blockPos.getX() - 5, blockPos.getY() - 2, blockPos.getZ() - 5);
            BlockPos point2 = new BlockPos(blockPos.getX() + 5, blockPos.getY() + 2, blockPos.getZ() + 5);

            Iterable<BlockPos> blocks = BlockPos.getAllInBox(point1, point2);

            for (BlockPos blockCheck : blocks) {
                Block block = Minecraft.getMinecraft().theWorld.getBlockState(blockCheck).getBlock();
                if (block == Blocks.dragon_egg) {
                    return blockCheck;
                }
            }
        }
        //if not found
        //System.out.println("None found");
        return null;
    }

    public static void drawWaypoint(BlockPos pos, float partialTicks) {
        if (pos==null) return;
        Entity viewer = Minecraft.getMinecraft().getRenderViewEntity();
        double viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks;
        double viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks;
        double viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks;

        double x = pos.getX() - viewerX;
        double y = pos.getY() - viewerY;
        double z = pos.getZ() - viewerZ;

        double distSq = x*x + y*y + z*z;

        GlStateManager.disableDepth();
        GlStateManager.disableCull();
        GlStateManager.disableTexture2D();
        if (distSq > 5*5) renderBeaconBeam(x, y, z, new Color(173, 216, 230).getRGB(), 1.0f, partialTicks);
        Utils.draw3DBox(new AxisAlignedBB(pos, pos.add(1, 1, 1)), new Color(173, 216, 230).getRGB(), partialTicks);            GlStateManager.disableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
    }
}



