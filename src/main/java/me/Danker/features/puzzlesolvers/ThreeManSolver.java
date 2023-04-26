package me.Danker.features.puzzlesolvers;

import com.google.gson.JsonArray;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ThreeManSolver {

    // Hard coded solutions if api call fails
    static String[] riddleSolutions = {"The reward is not in my chest!", "At least one of them is lying, and the reward is not in",
            "My chest doesn't have the reward. We are all telling the truth", "My chest has the reward and I'm telling the truth",
            "The reward isn't in any of our chests", "Both of them are telling the truth."};
    static BlockPos riddleChest = null;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        riddleChest = null;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.isInDungeons()) return;

        if (ModConfig.threeMan && message.startsWith("[NPC]")) {
            if (DankersSkyblockMod.data != null && DankersSkyblockMod.data.has("threeman")) {
                JsonArray riddleSolutions = DankersSkyblockMod.data.get("threeman").getAsJsonArray();

                for (int i = 0; i < riddleSolutions.size(); i++) {
                    String solution = riddleSolutions.get(i).getAsString();
                    if (message.contains(solution)) {
                        answer(message);
                        break;
                    }
                }
            } else {
                for (String solution : riddleSolutions) {
                    if (message.contains(solution)) {
                        answer(message);
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (ModConfig.threeMan && riddleChest != null) {
            RenderUtils.drawFilled3DBox(new AxisAlignedBB(riddleChest.getX() - 0.05, riddleChest.getY(), riddleChest.getZ() - 0.05, riddleChest.getX() + 1.05, riddleChest.getY() + 1, riddleChest.getZ() + 1.05), 0x197F19, true, true, event.partialTicks);
        }
    }

    public static void answer(String message) {
        Minecraft mc = Minecraft.getMinecraft();
        String npcName = message.substring(message.indexOf("]") + 2, message.indexOf(":"));
        mc.thePlayer.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.threeManAnswerColour) + EnumChatFormatting.BOLD + StringUtils.stripControlCodes(npcName) + ModConfig.getColour(ModConfig.mainColour) + " has the blessing."));

        if (riddleChest == null) {
            List<Entity> entities = mc.theWorld.getLoadedEntityList();
            for (Entity entity : entities) {
                if (entity == null || !entity.hasCustomName()) continue;
                if (entity.getCustomNameTag().contains(npcName)) {
                    BlockPos npcLocation = new BlockPos(entity.posX, 69, entity.posZ);
                    if (mc.theWorld.getBlockState(npcLocation.north()).getBlock() == Blocks.chest) {
                        riddleChest = npcLocation.north();
                    } else if (mc.theWorld.getBlockState(npcLocation.east()).getBlock() == Blocks.chest) {
                        riddleChest = npcLocation.east();
                    } else if (mc.theWorld.getBlockState(npcLocation.south()).getBlock() == Blocks.chest) {
                        riddleChest = npcLocation.south();
                    } else if (mc.theWorld.getBlockState(npcLocation.west()).getBlock() == Blocks.chest) {
                        riddleChest = npcLocation.west();
                    } else {
                        System.out.print("Could not find correct riddle chest.");
                    }
                    break;
                }
            }
        }
    }

}
