package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class NecronNotifications {

    @SubscribeEvent(receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.isInDungeons()) return;

        if (ModConfig.necronNotifications) {
            Minecraft mc = Minecraft.getMinecraft();
            World world = mc.theWorld;

            if (message.startsWith("[BOSS] Maxor:")) {
                if (message.contains("THAT BEAM! IT HURTS! IT HURTS!!") || message.contains("YOU TRICKED ME!")) {
                    Utils.createTitle(EnumChatFormatting.RED + "MAXOR STUCK!", 2);
                }
            } else if (message.startsWith("[BOSS] Storm:")) {
                if (message.contains("Ouch, that hurt!") || message.contains("Oof")) {
                    List<EntityArmorStand> stormLabels = world.getEntities(EntityArmorStand.class, (entity -> {
                        if (!entity.hasCustomName()) return false;
                        return entity.getCustomNameTag().contains("Storm");
                    }));
                    if (stormLabels.size() == 0) {
                        Utils.createTitle(EnumChatFormatting.WHITE + "STORM STUNNED!", 2);
                    } else {
                        EntityArmorStand storm = stormLabels.get(0);
                        double x = storm.posX;
                        double z = storm.posZ;

                        BlockPos blockPos = new BlockPos(x, 168, z);

                        IBlockState blockState = world.getBlockState(blockPos);
                        Block block = blockState.getBlock();

                        if (block != Blocks.stained_hardened_clay) {
                            Utils.createTitle(EnumChatFormatting.WHITE + "STORM STUNNED!", 2);
                        } else {
                            switch (block.getDamageValue(world, blockPos)) {
                                case 4:
                                    Utils.createTitle(EnumChatFormatting.YELLOW + "YELLOW PILLAR!", 2);
                                    break;
                                case 5:
                                    Utils.createTitle(EnumChatFormatting.DARK_GREEN + "GREEN PILLAR!", 2);
                                    break;
                                case 11:
                                    Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "PURPLE PILLAR!", 2);
                                    break;
                                default:
                                    Utils.createTitle(EnumChatFormatting.WHITE + "STORM STUNNED!", 2);
                            }
                        }

                    }
                } else if (message.contains("I should have known that I stood no chance.")) {
                    Utils.createTitle(EnumChatFormatting.RED + "RED PILLAR!", 2);
                }
            } else if (message.startsWith("[BOSS] Necron:") && message.contains("ARGH!")) {
                Utils.createTitle(EnumChatFormatting.RED + "EXPLOSION OVER!", 2);
            }
        }
    }

}
