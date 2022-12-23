package me.Danker.features.puzzlesolvers;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class WaterSolver {

    static boolean prevInWaterRoom = false;
    static boolean inWaterRoom = false;
    static String waterAnswers = null;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        World world = mc.theWorld;
        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            if (ModConfig.waterSolverHud.isEnabled() && Utils.isInDungeons() && world != null && player != null) {
                // multi thread block checking
                new Thread(() -> {
                    prevInWaterRoom = inWaterRoom;
                    inWaterRoom = false;
                    boolean foundPiston = false;
                    boolean done = false;
                    for (int x = (int) (player.posX - 13); x <= player.posX + 13; x++) {
                        for (int z = (int) (player.posZ - 13); z <= player.posZ + 13; z++) {
                            BlockPos blockPos = new BlockPos(x, 54, z);
                            if (world.getBlockState(blockPos).getBlock() == Blocks.sticky_piston) {
                                foundPiston = true;
                                break;
                            }
                        }
                        if (foundPiston) break;
                    }

                    if (foundPiston) {
                        for (int x = (int) (player.posX - 25); x <= player.posX + 25; x++) {
                            for (int z = (int) (player.posZ - 25); z <= player.posZ + 25; z++) {
                                BlockPos blockPos = new BlockPos(x, 82, z);
                                if (world.getBlockState(blockPos).getBlock() == Blocks.piston_head) {
                                    inWaterRoom = true;
                                    if (!prevInWaterRoom) {
                                        boolean foundGold = false;
                                        boolean foundClay = false;
                                        boolean foundEmerald = false;
                                        boolean foundQuartz = false;
                                        boolean foundDiamond = false;

                                        // Detect first blocks near water stream
                                        BlockPos scan1 = new BlockPos(x + 1, 78, z + 1);
                                        BlockPos scan2 = new BlockPos(x - 1, 77, z - 1);
                                        Iterable<BlockPos> blocks = BlockPos.getAllInBox(scan1, scan2);
                                        for (BlockPos puzzleBlockPos : blocks) {
                                            Block block = world.getBlockState(puzzleBlockPos).getBlock();
                                            if (block == Blocks.gold_block) {
                                                foundGold = true;
                                            } else if (block == Blocks.hardened_clay) {
                                                foundClay = true;
                                            } else if (block == Blocks.emerald_block) {
                                                foundEmerald = true;
                                            } else if (block == Blocks.quartz_block) {
                                                foundQuartz = true;
                                            } else if (block == Blocks.diamond_block) {
                                                foundDiamond = true;
                                            }
                                        }

                                        int variant = 0;
                                        if (foundGold && foundClay) {
                                            variant = 1;
                                        } else if (foundEmerald && foundQuartz) {
                                            variant = 2;
                                        } else if (foundQuartz && foundDiamond) {
                                            variant = 3;
                                        } else if (foundGold && foundQuartz) {
                                            variant = 4;
                                        }

                                        // Return solution
                                        String purple;
                                        String orange;
                                        String blue;
                                        String green;
                                        String red;
                                        switch (variant) {
                                            case 1:
                                                purple = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.AQUA + "Diamond, " + EnumChatFormatting.RED + "Clay";
                                                orange = EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.DARK_GRAY + "Coal, " + EnumChatFormatting.GREEN + "Emerald";
                                                blue = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";
                                                green = EnumChatFormatting.GREEN + "Emerald";
                                                red = EnumChatFormatting.GRAY + "None";
                                                break;
                                            case 2:
                                                purple = EnumChatFormatting.DARK_GRAY + "Coal";
                                                orange = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";
                                                blue = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.AQUA + "Diamond, " + EnumChatFormatting.GREEN + "Emerald";
                                                green = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.GREEN + "Emerald";
                                                red = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.DARK_GRAY + "Coal, " + EnumChatFormatting.GREEN + "Emerald";
                                                break;
                                            case 3:
                                                purple = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.AQUA + "Diamond";
                                                orange = EnumChatFormatting.GREEN + "Emerald";
                                                blue = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.AQUA + "Diamond";
                                                green = EnumChatFormatting.GRAY + "None";
                                                red = EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald";
                                                break;
                                            case 4:
                                                purple = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";
                                                orange = EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.DARK_GRAY + "Coal";
                                                blue = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.DARK_GRAY + "Coal, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";
                                                green = EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald";
                                                red = EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.AQUA + "Diamond, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";
                                                break;
                                            default:
                                                purple = orange = blue = green = red = ModConfig.getColour(ModConfig.errorColour) + "Error detecting water puzzle variant.";
                                                break;
                                        }
                                        waterAnswers = ModConfig.getColour(ModConfig.mainColour) + "The following levers must be down:\n" +
                                                EnumChatFormatting.DARK_PURPLE + "Purple: " + purple + "\n" +
                                                EnumChatFormatting.GOLD + "Orange: " + orange + "\n" +
                                                EnumChatFormatting.BLUE + "Blue: " + blue + "\n" +
                                                EnumChatFormatting.GREEN + "Green: " + green + "\n" +
                                                EnumChatFormatting.RED + "Red: " + red;
                                        done = true;
                                        break;
                                    }
                                }
                            }
                            if (done) break;
                        }
                    } else {
                        waterAnswers = null;
                    }
                }).start();
            }
        }
    }

    public static class WaterSolverHud extends Hud {

        @Exclude
        String exampleText = ModConfig.getColour(ModConfig.mainColour) + "The following levers must be down:\n" +
                             EnumChatFormatting.DARK_PURPLE + "Purple: " + EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay\n" +
                             EnumChatFormatting.GOLD + "Orange: " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.DARK_GRAY + "Coal\n" +
                             EnumChatFormatting.BLUE + "Blue: " + EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.DARK_GRAY + "Coal, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay\n" +
                             EnumChatFormatting.GREEN + "Green: " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald\n" +
                             EnumChatFormatting.RED + "Red: " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.AQUA + "Diamond, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            if (example) {
                TextRenderer.drawHUDText(exampleText, x, y, scale);
                return;
            }

            if (enabled && Utils.isInDungeons() && waterAnswers != null) {
                TextRenderer.drawHUDText(waterAnswers, x, y, scale);
            }
        }

        @Override
        protected float getWidth(float scale, boolean example) {
            return RenderUtils.getWidthFromText(example ? exampleText : waterAnswers) * scale;
        }

        @Override
        protected float getHeight(float scale, boolean example) {
            return RenderUtils.getHeightFromText(example ? exampleText : waterAnswers) * scale;
        }

    }

}
