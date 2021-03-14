package me.Danker.features.puzzlesolvers;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.utils.IceWalkUtils;
import me.Danker.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
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

public class IceWalkSolver {

    static boolean prevInIceWalkRoom = false;
    static boolean inIceWalkRoom = false;
    static BlockPos chest = null;
    static EnumFacing silverfishRoomDirection = null;
    static List<IceWalkUtils.Point> threeByThreeRoute = new ArrayList<>();
    static List<IceWalkUtils.Point> fiveByFiveRoute = new ArrayList<>();
    static List<IceWalkUtils.Point> sevenBySevenRoute = new ArrayList<>();
    public static int ICE_WALK_LINE_COLOUR;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        reset();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        World world = mc.theWorld;
        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            if (ToggleCommand.iceWalkToggled && Utils.inDungeons && world != null && player != null) {
                // multi thread block checking
                new Thread(() -> {
                    boolean foundRoom = false;
                    prevInIceWalkRoom = inIceWalkRoom;
                    Iterable<BlockPos> blocks = BlockPos.getAllInBox(new BlockPos(player.posX - 27, 75, player.posZ - 27), new BlockPos(player.posX + 27, 75, player.posZ + 27));
                    for (BlockPos blockPos : blocks) {
                        Block block = world.getBlockState(blockPos).getBlock();
                        if (block == Blocks.chest) {
                            char[][] threeByThreeBoard = new char[4][3];
                            char[][] fiveByFiveBoard = new char[6][5];
                            char[][] sevenBySevenBoard = new char[8][7];

                            if (world.getBlockState(blockPos.add(0, 7, 2)).getBlock() == Blocks.iron_bars && world.getBlockState(blockPos.add(0, -3, 2)).getBlock() == Blocks.stone_brick_stairs) {
                                silverfishRoomDirection = EnumFacing.NORTH;
                            } else if (world.getBlockState(blockPos.add(-2, 7, 0)).getBlock() == Blocks.iron_bars && world.getBlockState(blockPos.add(-2, -3, 0)).getBlock() == Blocks.stone_brick_stairs) {
                                silverfishRoomDirection = EnumFacing.EAST;
                            } else if (world.getBlockState(blockPos.add(0, 7, -2)).getBlock() == Blocks.iron_bars && world.getBlockState(blockPos.add(0, -3, -2)).getBlock() == Blocks.stone_brick_stairs) {
                                silverfishRoomDirection = EnumFacing.SOUTH;
                            } else if (world.getBlockState(blockPos.add(2, 7, 0)).getBlock() == Blocks.iron_bars && world.getBlockState(blockPos.add(2, -3, 0)).getBlock() == Blocks.stone_brick_stairs) {
                                silverfishRoomDirection = EnumFacing.WEST;
                            } else {
                                return;
                            }

                            foundRoom = true;
                            inIceWalkRoom = true;
                            if (!prevInIceWalkRoom) {
                                chest = blockPos;
                                switch (silverfishRoomDirection) {
                                    case NORTH:
                                        for (int row = chest.getZ() + 3, xIteration = 0; xIteration < 8; row++, xIteration++) {
                                            for (int column = chest.getX() - 3, yIteration = 0; yIteration < 7; column++, yIteration++) {
                                                if (world.getBlockState(new BlockPos(column, 72, row)).getBlock() != Blocks.air) {
                                                    sevenBySevenBoard[xIteration][yIteration] = 'X';
                                                }
                                            }
                                        }
                                        for (int row = chest.getZ() + 12, xIteration = 0; xIteration < 6; row++, xIteration++) {
                                            for (int column = chest.getX() - 2, yIteration = 0; yIteration < 5; column++, yIteration++) {
                                                if (world.getBlockState(new BlockPos(column, 71, row)).getBlock() != Blocks.air) {
                                                    fiveByFiveBoard[xIteration][yIteration] = 'X';
                                                }
                                            }
                                        }
                                        for (int row = chest.getZ() + 19, xIteration = 0; xIteration < 4; row++, xIteration++) {
                                            for (int column = chest.getX() - 1, yIteration = 0; yIteration < 3; column++, yIteration++) {
                                                if (world.getBlockState(new BlockPos(column, 70, row)).getBlock() != Blocks.air) {
                                                    threeByThreeBoard[xIteration][yIteration] = 'X';
                                                }
                                            }
                                        }
                                        break;
                                    case EAST:
                                        for (int row = chest.getX() - 3, xIteration = 0; xIteration < 8; row--, xIteration++) {
                                            for (int column = chest.getZ() - 3, yIteration = 0; yIteration < 7; column++, yIteration++) {
                                                if (world.getBlockState(new BlockPos(row, 72, column)).getBlock() != Blocks.air) {
                                                    sevenBySevenBoard[xIteration][yIteration] = 'X';
                                                }
                                            }
                                        }
                                        for (int row = chest.getX() - 12, xIteration = 0; xIteration < 6; row--, xIteration++) {
                                            for (int column = chest.getZ() - 2, yIteration = 0; yIteration < 5; column++, yIteration++) {
                                                if (world.getBlockState(new BlockPos(row, 71, column)).getBlock() != Blocks.air) {
                                                    fiveByFiveBoard[xIteration][yIteration] = 'X';
                                                }
                                            }
                                        }
                                        for (int row = chest.getX() - 19, xIteration = 0; xIteration < 4; row--, xIteration++) {
                                            for (int column = chest.getZ() - 1, yIteration = 0; yIteration < 3; column++, yIteration++) {
                                                if (world.getBlockState(new BlockPos(row, 70, column)).getBlock() != Blocks.air) {
                                                    threeByThreeBoard[xIteration][yIteration] = 'X';
                                                }
                                            }
                                        }
                                        break;
                                    case SOUTH:
                                        for (int row = chest.getZ() - 3, xIteration = 0; xIteration < 8; row--, xIteration++) {
                                            for (int column = chest.getX() + 3, yIteration = 0; yIteration < 7; column--, yIteration++) {
                                                if (world.getBlockState(new BlockPos(column, 72, row)).getBlock() != Blocks.air) {
                                                    sevenBySevenBoard[xIteration][yIteration] = 'X';
                                                }
                                            }
                                        }
                                        for (int row = chest.getZ() - 12, xIteration = 0; xIteration < 6; row--, xIteration++) {
                                            for (int column = chest.getX() + 2, yIteration = 0; yIteration < 5; column--, yIteration++) {
                                                if (world.getBlockState(new BlockPos(column, 71, row)).getBlock() != Blocks.air) {
                                                    fiveByFiveBoard[xIteration][yIteration] = 'X';
                                                }
                                            }
                                        }
                                        for (int row = chest.getZ() - 19, xIteration = 0; xIteration < 4; row--, xIteration++) {
                                            for (int column = chest.getX() + 1, yIteration = 0; yIteration < 3; column--, yIteration++) {
                                                if (world.getBlockState(new BlockPos(column, 70, row)).getBlock() != Blocks.air) {
                                                    threeByThreeBoard[xIteration][yIteration] = 'X';
                                                }
                                            }
                                        }
                                        break;
                                    case WEST:
                                        for (int row = chest.getX() + 3, xIteration = 0; xIteration < 8; row++, xIteration++) {
                                            for (int column = chest.getZ() + 3, yIteration = 0; yIteration < 7; column--, yIteration++) {
                                                if (world.getBlockState(new BlockPos(row, 72, column)).getBlock() != Blocks.air) {
                                                    sevenBySevenBoard[xIteration][yIteration] = 'X';
                                                }
                                            }
                                        }
                                        for (int row = chest.getX() + 12, xIteration = 0; xIteration < 6; row++, xIteration++) {
                                            for (int column = chest.getZ() + 2, yIteration = 0; yIteration < 5; column--, yIteration++) {
                                                if (world.getBlockState(new BlockPos(row, 71, column)).getBlock() != Blocks.air) {
                                                    fiveByFiveBoard[xIteration][yIteration] = 'X';
                                                }
                                            }
                                        }
                                        for (int row = chest.getX() + 19, xIteration = 0; xIteration < 4; row++, xIteration++) {
                                            for (int column = chest.getZ() + 1, yIteration = 0; yIteration < 3; column--, yIteration++) {
                                                if (world.getBlockState(new BlockPos(row, 70, column)).getBlock() != Blocks.air) {
                                                    threeByThreeBoard[xIteration][yIteration] = 'X';
                                                }
                                            }
                                        }
                                        break;
                                }
                                System.out.println(Arrays.deepToString(threeByThreeBoard));
                                System.out.println(Arrays.deepToString(fiveByFiveBoard));
                                System.out.println(Arrays.deepToString(sevenBySevenBoard));

                                threeByThreeRoute = IceWalkUtils.solve(threeByThreeBoard);
                                fiveByFiveRoute = IceWalkUtils.solve(fiveByFiveBoard);
                                sevenBySevenRoute = IceWalkUtils.solve(sevenBySevenBoard);
                            }
                        }
                    }
                    if (!foundRoom) {
                        inIceWalkRoom = false;
                        reset();
                    }
                }).start();
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (ToggleCommand.iceWalkToggled && Utils.inDungeons && chest != null) {
            if (threeByThreeRoute != null && threeByThreeRoute.size() > 1) {
                for (int i = 0; i < threeByThreeRoute.size() - 1; i++) {
                    Vec3 pos1;
                    Vec3 pos2;
                    switch (silverfishRoomDirection) {
                        case NORTH:
                            pos1 = new Vec3(chest.getX() - 0.5 + threeByThreeRoute.get(i).column, 70.5, chest.getZ() + 19.5 + threeByThreeRoute.get(i).row);
                            pos2 = new Vec3(chest.getX() - 0.5 + threeByThreeRoute.get(i + 1).column, 70.5, chest.getZ() + 19.5 + threeByThreeRoute.get(i + 1).row);
                            break;
                        case EAST:
                            pos1 = new Vec3(chest.getX() - 18.5 - threeByThreeRoute.get(i).row, 70.5, chest.getZ() - 0.5 + threeByThreeRoute.get(i).column);
                            pos2 = new Vec3(chest.getX() - 18.5 - threeByThreeRoute.get(i + 1).row, 70.5, chest.getZ() - 0.5 + threeByThreeRoute.get(i + 1).column);
                            break;
                        case SOUTH:
                            pos1 = new Vec3(chest.getX() + 1.5 - threeByThreeRoute.get(i).column, 70.5, chest.getZ() - 18.5 - threeByThreeRoute.get(i).row);
                            pos2 = new Vec3(chest.getX() + 1.5 - threeByThreeRoute.get(i + 1).column, 70.5, chest.getZ() - 18.5 - threeByThreeRoute.get(i + 1).row);
                            break;
                        case WEST:
                            pos1 = new Vec3(chest.getX() + 19.5 + threeByThreeRoute.get(i).row, 70.5, chest.getZ() + 1.5 - threeByThreeRoute.get(i).column);
                            pos2 = new Vec3(chest.getX() + 19.5 + threeByThreeRoute.get(i + 1).row, 70.5, chest.getZ() + 1.5 - threeByThreeRoute.get(i + 1).column);
                            break;
                        default:
                            return;
                    }
                    Utils.draw3DLine(pos1, pos2, ICE_WALK_LINE_COLOUR, 5, true, event.partialTicks);
                }
            }

            if (fiveByFiveRoute != null && fiveByFiveRoute.size() > 1) {
                for (int i = 0; i < fiveByFiveRoute.size() - 1; i++) {
                    Vec3 pos1;
                    Vec3 pos2;
                    switch (silverfishRoomDirection) {
                        case NORTH:
                            pos1 = new Vec3(chest.getX() - 1.5 + fiveByFiveRoute.get(i).column, 71.5, chest.getZ() + 12.5 + fiveByFiveRoute.get(i).row);
                            pos2 = new Vec3(chest.getX() - 1.5 + fiveByFiveRoute.get(i + 1).column, 71.5, chest.getZ() + 12.5 + fiveByFiveRoute.get(i + 1).row);
                            break;
                        case EAST:
                            pos1 = new Vec3(chest.getX() - 11.5 - fiveByFiveRoute.get(i).row, 71.5, chest.getZ() - 1.5 + fiveByFiveRoute.get(i).column);
                            pos2 = new Vec3(chest.getX() - 11.5 - fiveByFiveRoute.get(i + 1).row, 71.5, chest.getZ() - 1.5 + fiveByFiveRoute.get(i + 1).column);
                            break;
                        case SOUTH:
                            pos1 = new Vec3(chest.getX() + 2.5 - fiveByFiveRoute.get(i).column, 71.5, chest.getZ() - 11.5 - fiveByFiveRoute.get(i).row);
                            pos2 = new Vec3(chest.getX() + 2.5 - fiveByFiveRoute.get(i + 1).column, 71.5, chest.getZ() - 11.5 - fiveByFiveRoute.get(i + 1).row);
                            break;
                        case WEST:
                            pos1 = new Vec3(chest.getX() + 12.5 + fiveByFiveRoute.get(i).row, 71.5, chest.getZ() + 2.5 - fiveByFiveRoute.get(i).column);
                            pos2 = new Vec3(chest.getX() + 12.5 + fiveByFiveRoute.get(i + 1).row, 71.5, chest.getZ() + 2.5 - fiveByFiveRoute.get(i + 1).column);
                            break;
                        default:
                            return;
                    }
                    Utils.draw3DLine(pos1, pos2, ICE_WALK_LINE_COLOUR, 5, true, event.partialTicks);
                }
            }

            if (sevenBySevenRoute != null && sevenBySevenRoute.size() > 1) {
                for (int i = 0; i < sevenBySevenRoute.size() - 1; i++) {
                    Vec3 pos1;
                    Vec3 pos2;
                    switch (silverfishRoomDirection) {
                        case NORTH:
                            pos1 = new Vec3(chest.getX() - 2.5 + sevenBySevenRoute.get(i).column, 72.5, chest.getZ() + 3.5 + sevenBySevenRoute.get(i).row);
                            pos2 = new Vec3(chest.getX() - 2.5 + sevenBySevenRoute.get(i + 1).column, 72.5, chest.getZ() + 3.5 + sevenBySevenRoute.get(i + 1).row);
                            break;
                        case EAST:
                            pos1 = new Vec3(chest.getX() - 2.5 - sevenBySevenRoute.get(i).row, 72.5, chest.getZ() - 2.5 + sevenBySevenRoute.get(i).column);
                            pos2 = new Vec3(chest.getX() - 2.5 - sevenBySevenRoute.get(i + 1).row, 72.5, chest.getZ() - 2.5 + sevenBySevenRoute.get(i + 1).column);
                            break;
                        case SOUTH:
                            pos1 = new Vec3(chest.getX() + 3.5 - sevenBySevenRoute.get(i).column, 72.5, chest.getZ() - 2.5 - sevenBySevenRoute.get(i).row);
                            pos2 = new Vec3(chest.getX() + 3.5 - sevenBySevenRoute.get(i + 1).column, 72.5, chest.getZ() - 2.5 - sevenBySevenRoute.get(i + 1).row);
                            break;
                        case WEST:
                            pos1 = new Vec3(chest.getX() + 3.5 + sevenBySevenRoute.get(i).row, 72.5, chest.getZ() + 3.5 - sevenBySevenRoute.get(i).column);
                            pos2 = new Vec3(chest.getX() + 3.5 + sevenBySevenRoute.get(i + 1).row, 72.5, chest.getZ() + 3.5 - sevenBySevenRoute.get(i + 1).column);
                            break;
                        default:
                            return;
                    }
                    Utils.draw3DLine(pos1, pos2, ICE_WALK_LINE_COLOUR, 5, true, event.partialTicks);
                }
            }
        }
    }

    static void reset() {
        silverfishRoomDirection = null;
        chest = null;
        threeByThreeRoute.clear();
        fiveByFiveRoute.clear();
        sevenBySevenRoute.clear();
    }

}
