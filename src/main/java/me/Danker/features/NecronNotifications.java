package me.Danker.features;

import me.Danker.commands.ToggleCommand;
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

        if (!Utils.inDungeons) return;

        if (ToggleCommand.necronNotificationsToggled && message.contains("[BOSS] Necron:")) {
            Minecraft mc = Minecraft.getMinecraft();
            World world = mc.theWorld;
            if (message.contains("You tricked me!") || message.contains("That beam, it hurts! IT HURTS!!")) {
                Utils.createTitle(EnumChatFormatting.RED + "NECRON STUCK!", 2);
            } else if (message.contains("STOP USING MY FACTORY AGAINST ME!") || message.contains("OOF") || message.contains("ANOTHER TRAP!! YOUR TRICKS ARE FUTILE!") || message.contains("SERIOUSLY? AGAIN?!") || message.contains("STOP!!!!!")) {
                List<EntityArmorStand> necronLabels = world.getEntities(EntityArmorStand.class, (entity -> {
                    if (!entity.hasCustomName()) return false;
                    if (!entity.getCustomNameTag().contains("Necron")) return false;
                    return true;
                }));
                if (necronLabels.size() == 0) {
                    Utils.createTitle(EnumChatFormatting.WHITE + "NECRON STUNNED!", 2);
                } else {
                    EntityArmorStand necron = necronLabels.get(0);
                    double x = necron.posX;
                    double z = necron.posZ;

                    BlockPos blockPos = new BlockPos(x, 168, z);

                    IBlockState blockState = world.getBlockState(blockPos);
                    Block block = blockState.getBlock();

                    if (block != Blocks.stained_hardened_clay) {
                        Utils.createTitle(EnumChatFormatting.WHITE + "NECRON STUNNED!", 2);
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
                                Utils.createTitle(EnumChatFormatting.WHITE + "NECRON STUNNED!", 2);
                        }
                    }

                }
            } else if (message.contains("I'VE HAD ENOUGH! YOU'RE NOT HITTING ME WITH ANY MORE PILLARS!")) {
                Utils.createTitle(EnumChatFormatting.RED + "RED PILLAR!", 2);
            } else if (message.contains("ARGH!")) {
                Utils.createTitle(EnumChatFormatting.RED + "EXPLOSION OVER!", 2);
            }
        }

    }

}
