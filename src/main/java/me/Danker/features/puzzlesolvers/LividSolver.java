package me.Danker.features.puzzlesolvers;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlayEvent;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.handlers.TextRenderer;
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
    public static int LIVID_COLOUR;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        livid = null;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!ToggleCommand.lividSolverToggled) return;

        World world = Minecraft.getMinecraft().theWorld;
        if (world == null) return;
        if (Utils.currentFloor == Utils.DungeonFloor.F5 || Utils.currentFloor == Utils.DungeonFloor.M5) {
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

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlayEvent event) {
        if (ToggleCommand.lividSolverToggled && livid != null) {
            new TextRenderer(Minecraft.getMinecraft(), livid.getName().replace(EnumChatFormatting.BOLD.toString(), ""), MoveCommand.lividHpXY[0], MoveCommand.lividHpXY[1], ScaleCommand.lividHpScale);
        }
    }

    @SubscribeEvent
    public void onRenderEntity(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (!ToggleCommand.lividSolverToggled || livid == null) return;

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
        if (ToggleCommand.lividSolverToggled && livid != null) {
            AxisAlignedBB aabb = new AxisAlignedBB(livid.posX - 0.5, livid.posY - 2, livid.posZ - 0.5, livid.posX + 0.5, livid.posY, livid.posZ + 0.5);
            RenderUtils.draw3DBox(aabb, LIVID_COLOUR, event.partialTicks);
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

}
