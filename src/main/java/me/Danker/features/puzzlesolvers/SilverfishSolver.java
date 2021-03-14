package me.Danker.features.puzzlesolvers;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.utils.SilverfishUtils;
import me.Danker.utils.Utils;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SilverfishSolver {

    static boolean prevInSilverfishRoom = false;
    static boolean inSilverfishRoom = false;
    static BlockPos chest = null;
    static EnumFacing silverfishRoomDirection = null;
    static List<SilverfishUtils.Point> route = new ArrayList<>();
    public static int SILVERFISH_LINE_COLOUR;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        reset();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayerSP player = mc.thePlayer;
        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            if (ToggleCommand.silverfishToggled && Utils.inDungeons && world != null && player != null) {
                new Thread(() -> {
                    boolean foundRoom = false;
                    prevInSilverfishRoom = inSilverfishRoom;
                    double x = player.posX;
                    double z = player.posZ;
                    // Find creepers nearby
                    AxisAlignedBB entityScan = new AxisAlignedBB(x - 25, 67, z - 25, x + 25, 68, z + 25); // 50x1x50
                    List<EntitySilverfish> silverfishes = world.getEntitiesWithinAABB(EntitySilverfish.class, entityScan);
                    List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, entityScan);
                    if (silverfishes.size() > 0 && items.size() > 0) {
                        double silverfishX = silverfishes.get(0).posX;
                        double silverfishZ = silverfishes.get(0).posZ;
                        for (EntityItem item : items) {
                            if (Item.getIdFromItem(item.getEntityItem().getItem()) == 46 && Math.abs(item.posX - silverfishX) < 1 && Math.abs(item.posZ - silverfishZ) < 1) {
                                Iterable<BlockPos> blocks = BlockPos.getAllInBox(new BlockPos(player.posX - 27, 67, player.posZ - 27), new BlockPos(player.posX + 27, 67, player.posZ + 27));
                                for (BlockPos blockPos : blocks) {
                                    if (world.getBlockState(blockPos).getBlock() == Blocks.chest && world.getBlockState(blockPos.add(0, 2, 0)).getBlock() == Blocks.hopper) {
                                        foundRoom = true;
                                        inSilverfishRoom = true;
                                        if (!prevInSilverfishRoom) {
                                            chest = blockPos;
                                            BlockPos silverfishBlock = new BlockPos(silverfishX, 67, silverfishZ);
                                            TileEntity hopper = world.getTileEntity(blockPos.add(0, 2, 0));
                                            silverfishRoomDirection = BlockHopper.getFacing(hopper.getBlockMetadata());
                                            char[][] board = new char[19][19];
                                            SilverfishUtils.Point silverfishPoint = null;

                                            switch (silverfishRoomDirection) {
                                                case NORTH:
                                                    for (int row = chest.getZ() + 3, xIteration = 0; xIteration < 19; row++, xIteration++) {
                                                        for (int column = chest.getX() - 9, yIteration = 0; yIteration < 19; column++, yIteration++) {
                                                            if (world.getBlockState(new BlockPos(column, 67, row)).getBlock() != Blocks.air) {
                                                                board[xIteration][yIteration] = 'X';
                                                            } else if (silverfishBlock.equals(new BlockPos(column, 67, row))) {
                                                                silverfishPoint = new SilverfishUtils.Point(xIteration, yIteration);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case EAST:
                                                    for (int row = chest.getX() - 3, xIteration = 0; xIteration < 19; row--, xIteration++) {
                                                        for (int column = chest.getZ() - 9, yIteration = 0; yIteration < 19; column++, yIteration++) {
                                                            if (world.getBlockState(new BlockPos(row, 67, column)).getBlock() != Blocks.air) {
                                                                board[xIteration][yIteration] = 'X';
                                                            } else if (silverfishBlock.equals(new BlockPos(row, 67, column))) {
                                                                silverfishPoint = new SilverfishUtils.Point(xIteration, yIteration);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case SOUTH:
                                                    for (int row = chest.getZ() - 3, xIteration = 0; xIteration < 19; row--, xIteration++) {
                                                        for (int column = chest.getX() + 9, yIteration = 0; yIteration < 19; column--, yIteration++) {
                                                            if (world.getBlockState(new BlockPos(column, 67, row)).getBlock() != Blocks.air) {
                                                                board[xIteration][yIteration] = 'X';
                                                            } else if (silverfishBlock.equals(new BlockPos(column, 67, row))) {
                                                                silverfishPoint = new SilverfishUtils.Point(xIteration, yIteration);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case WEST:
                                                    for (int row = chest.getX() + 3, xIteration = 0; xIteration < 19; row++, xIteration++) {
                                                        for (int column = chest.getZ() + 9, yIteration = 0; yIteration < 19; column--, yIteration++) {
                                                            if (world.getBlockState(new BlockPos(row, 67, column)).getBlock() != Blocks.air) {
                                                                board[xIteration][yIteration] = 'X';
                                                            } else if (silverfishBlock.equals(new BlockPos(row, 67, column))) {
                                                                board[xIteration][yIteration] = 'S';
                                                                silverfishPoint = new SilverfishUtils.Point(xIteration, yIteration);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    return;
                                            }
                                            System.out.println(Arrays.deepToString(board));

                                            List<SilverfishUtils.Point> endPoints = new ArrayList<>();
                                            for (int column = 0; column < 19; column++) {
                                                if (board[0][column] != 'X') endPoints.add(new SilverfishUtils.Point(0, column));
                                            }

                                            route = SilverfishUtils.solve(board, silverfishPoint, endPoints);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!foundRoom) reset();
                }).start();
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (ToggleCommand.silverfishToggled && Utils.inDungeons && route != null && route.size() > 0 && chest != null) {
            for (int i = 0; i < route.size() - 1; i++) {
                Vec3 pos1 = null;
                Vec3 pos2 = null;
                switch (silverfishRoomDirection) {
                    case NORTH:
                        pos1 = new Vec3(chest.getX() - 8.5 + route.get(i).column, 67.5, chest.getZ() + 3.5 + route.get(i).row);
                        pos2 = new Vec3(chest.getX() - 8.5 + route.get(i + 1).column, 67.5, chest.getZ() + 3.5 + route.get(i + 1).row);
                        break;
                    case EAST:
                        pos1 = new Vec3(chest.getX() - 2.5 - route.get(i).row, 67.5, chest.getZ() - 8.5 + route.get(i).column);
                        pos2 = new Vec3(chest.getX() - 2.5 - route.get(i + 1).row, 67.5, chest.getZ() - 8.5 + route.get(i + 1).column);
                        break;
                    case SOUTH:
                        pos1 = new Vec3(chest.getX() + 9.5 - route.get(i).column, 67.5, chest.getZ() - 2.5 - route.get(i).row);
                        pos2 = new Vec3(chest.getX() + 9.5 - route.get(i + 1).column, 67.5, chest.getZ() - 2.5 - route.get(i + 1).row);
                        break;
                    case WEST:
                        pos1 = new Vec3(chest.getX() + 3.5 + route.get(i).row, 67.5, chest.getZ() + 9.5 - route.get(i).column);
                        pos2 = new Vec3(chest.getX() + 3.5 + route.get(i + 1).row, 67.5, chest.getZ() + 9.5 - route.get(i + 1).column);
                        break;
                    default:
                        return;
                }
                Utils.draw3DLine(pos1, pos2, SILVERFISH_LINE_COLOUR, 5, true, event.partialTicks);
            }
        }
    }

    static void reset() {
        inSilverfishRoom = false;
        chest = null;
        silverfishRoomDirection = null;
        route.clear();
    }

}
