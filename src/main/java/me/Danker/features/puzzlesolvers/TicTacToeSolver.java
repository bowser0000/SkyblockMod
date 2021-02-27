package me.Danker.features.puzzlesolvers;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.utils.TicTacToeUtils;
import me.Danker.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToeSolver {

    static AxisAlignedBB correctTicTacToeButton = null;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayerSP player = mc.thePlayer;
        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            if (ToggleCommand.ticTacToeToggled && Utils.inDungeons && world != null && player != null) {
                correctTicTacToeButton = null;
                AxisAlignedBB aabb = new AxisAlignedBB(player.posX - 6, player.posY - 6, player.posZ - 6, player.posX + 6, player.posY + 6, player.posZ + 6);
                List<EntityItemFrame> itemFrames = world.getEntitiesWithinAABB(EntityItemFrame.class, aabb);
                List<EntityItemFrame> itemFramesWithMaps = new ArrayList<>();
                // Find how many item frames have maps already placed
                for (EntityItemFrame itemFrame : itemFrames) {
                    ItemStack item = itemFrame.getDisplayedItem();
                    if (item == null || !(item.getItem() instanceof ItemMap)) continue;
                    MapData mapData = ((ItemMap) item.getItem()).getMapData(item, world);
                    if (mapData == null) continue;

                    itemFramesWithMaps.add(itemFrame);
                }

                // Only run when it's your turn
                if (itemFramesWithMaps.size() != 9 && itemFramesWithMaps.size() % 2 == 1) {
                    char[][] board = new char[3][3];
                    BlockPos leftmostRow = null;
                    int sign = 1;
                    char facing = 'X';
                    for (EntityItemFrame itemFrame : itemFramesWithMaps) {
                        ItemStack map = itemFrame.getDisplayedItem();
                        MapData mapData = ((ItemMap) map.getItem()).getMapData(map, world);

                        // Find position on board
                        // I mixed up row and column here and I'm too lazy to fix it
                        int row = 0;
                        int column;
                        sign = 1;

                        if (itemFrame.facingDirection == EnumFacing.SOUTH || itemFrame.facingDirection == EnumFacing.WEST) {
                            sign = -1;
                        }

                        BlockPos itemFramePos = new BlockPos(itemFrame.posX, Math.floor(itemFrame.posY), itemFrame.posZ);
                        for (int i = 2; i >= 0; i--) {
                            int realI = i * sign;
                            BlockPos blockPos = itemFramePos;
                            if (itemFrame.posX % 0.5 == 0) {
                                blockPos = itemFramePos.add(realI, 0, 0);
                            } else if (itemFrame.posZ % 0.5 == 0) {
                                blockPos = itemFramePos.add(0, 0, realI);
                                facing = 'Z';
                            }
                            Block block = world.getBlockState(blockPos).getBlock();
                            if (block == Blocks.air || block == Blocks.stone_button) {
                                leftmostRow = blockPos;
                                row = i;
                                break;
                            }
                        }

                        if (itemFrame.posY == 72.5) {
                            column = 0;
                        } else if (itemFrame.posY == 71.5) {
                            column = 1;
                        } else if (itemFrame.posY == 70.5) {
                            column = 2;
                        } else {
                            continue;
                        }

                        // Get colour
                        // Middle pixel = 64*128 + 64 = 8256
                        int colourInt = mapData.colors[8256] & 255;
                        if (colourInt == 114) {
                            board[column][row] = 'X';
                        } else if (colourInt == 33) {
                            board[column][row] = 'O';
                        }
                    }
                    System.out.println("Board: " + Arrays.deepToString(board));

                    // Draw best move
                    int bestMove = TicTacToeUtils.getBestMove(board) - 1;
                    System.out.println("Best move slot: " + bestMove);
                    if (leftmostRow != null) {
                        double drawX = facing == 'X' ? leftmostRow.getX() - sign * (bestMove % 3) : leftmostRow.getX();
                        double drawY = 72 - Math.floor(bestMove / 3);
                        double drawZ = facing == 'Z' ? leftmostRow.getZ() - sign * (bestMove % 3) : leftmostRow.getZ();

                        correctTicTacToeButton = new AxisAlignedBB(drawX, drawY, drawZ, drawX + 1, drawY + 1, drawZ + 1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (ToggleCommand.ticTacToeToggled && correctTicTacToeButton != null) {
            Utils.draw3DBox(correctTicTacToeButton, 0x40FF40, event.partialTicks);
        }
    }

}
