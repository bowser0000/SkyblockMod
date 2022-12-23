package me.Danker.features.puzzlesolvers;

import cc.polyfrost.oneconfig.config.annotations.Color;
import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.locations.DungeonFloor;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class LividSolver {

    static BlockPos pos = new BlockPos(5, 108, 25);
    static Entity livid = null;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        livid = null;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!ModConfig.lividSolverHud.isEnabled()) return;

        if (DankersSkyblockMod.tickAmount % 10 == 0) {
            World world = Minecraft.getMinecraft().theWorld;
            if (world == null) return;
            if (Utils.currentFloor == DungeonFloor.F5 || Utils.currentFloor == DungeonFloor.M5) {
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                if (scoreboard.size() == 0) return;
                String firstLine = ScoreboardHandler.cleanSB(scoreboard.get(scoreboard.size() - 1));

                if (firstLine.contains("livid")) {
                    if (world.getBlockState(pos).getBlock() == Blocks.wool) {
                        List<Entity> entities = world.getLoadedEntityList();
                        int colour = world.getBlockState(pos).getBlock().getDamageValue(world, pos);
                        String find = getTextFromValue(colour);

                        for (Entity entity : entities) {
                            if (!(entity instanceof EntityArmorStand) || !entity.hasCustomName()) continue;
                            String name = entity.getCustomNameTag();

                            if (name.contains(find)) {
                                livid = entity;
                                return;
                            }
                        }
                        livid = null;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderEntity(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (!ModConfig.lividSolverHud.isEnabled() || livid == null) return;

        Entity entity = event.entity;
        if (entity instanceof EntityArmorStand && entity.hasCustomName()) {
            String name = entity.getCustomNameTag();
            if (!entity.isEntityEqual(livid) && name.contains("Livid")) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (ModConfig.lividSolverHud.isEnabled() && livid != null) {
            AxisAlignedBB aabb = new AxisAlignedBB(livid.posX - 0.5, livid.posY - 2, livid.posZ - 0.5, livid.posX + 0.5, livid.posY, livid.posZ + 0.5);
            RenderUtils.draw3DBox(aabb, LividSolverHud.lividColour.getRGB(), event.partialTicks);
        }
    }

    static String getTextFromValue(int value) {
        String colour = "Failed";
        switch (value) {
            case 0:
                colour = EnumChatFormatting.WHITE.toString();
                break;
            case 2:
                colour = EnumChatFormatting.LIGHT_PURPLE.toString();
                break;
            case 4:
                colour = EnumChatFormatting.YELLOW.toString();
                break;
            case 5:
                colour = EnumChatFormatting.GREEN.toString();
                break;
            case 7:
                colour = EnumChatFormatting.GRAY.toString();
                break;
            case 10:
                colour = EnumChatFormatting.DARK_PURPLE.toString();
                break;
            case 11:
                colour = EnumChatFormatting.BLUE.toString();
                break;
            case 13:
                colour = EnumChatFormatting.DARK_GREEN.toString();
                break;
            case 14:
                colour = EnumChatFormatting.RED.toString();
                break;
        }
        return colour + EnumChatFormatting.BOLD + "Livid";
    }

    public static class LividSolverHud extends Hud {

        @Exclude
        String exampleText = EnumChatFormatting.WHITE + "﴾ Livid " + EnumChatFormatting.YELLOW + "6.9M" + EnumChatFormatting.RED + "❤ " + EnumChatFormatting.WHITE + "﴿";

        @Color(
                name = "Correct Livid Box Color",
                category = "Puzzle Solvers",
                subcategory = "Dungeons"
        )
        public static OneColor lividColour = new OneColor(0, 0, 255);

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            if (example) {
                TextRenderer.drawHUDText(exampleText, x, y, scale);
                return;
            }

            if (ModConfig.lividSolverHud.isEnabled() && livid != null) {
                TextRenderer.drawHUDText(getText(), x, y, scale);
            }
        }

        @Override
        protected float getWidth(float scale, boolean example) {
            return RenderUtils.getWidthFromText(example ? exampleText : getText()) * scale;
        }

        @Override
        protected float getHeight(float scale, boolean example) {
            return RenderUtils.getHeightFromText(example ? exampleText : getText()) * scale;
        }

        String getText() {
            if (livid != null) return livid.getName().replace(EnumChatFormatting.BOLD.toString(), "");
            return "";
        }

    }

}
