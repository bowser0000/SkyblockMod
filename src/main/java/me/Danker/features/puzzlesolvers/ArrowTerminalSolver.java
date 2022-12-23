package me.Danker.features.puzzlesolvers;

import me.Danker.config.ModConfig;
import me.Danker.events.PacketWriteEvent;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ArrowTerminalSolver {

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != event.entityPlayer) return;

        if (ModConfig.itemFrameOnSeaLanterns && Utils.isInDungeons() && event.target instanceof EntityItemFrame) {
            EntityItemFrame itemFrame = (EntityItemFrame) event.target;
            ItemStack item = itemFrame.getDisplayedItem();
            if (item == null || item.getItem() != Items.arrow) return;
            BlockPos blockPos = Utils.getBlockUnderItemFrame(itemFrame);
            if (mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.sea_lantern) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onPacket(PacketWriteEvent event) {
        if (ModConfig.itemFrameOnSeaLanterns && Utils.isInDungeons() && event.packet instanceof C02PacketUseEntity) {
            Minecraft mc = Minecraft.getMinecraft();
            C02PacketUseEntity packet = (C02PacketUseEntity) event.packet;
            Entity entityHit = packet.getEntityFromWorld(mc.theWorld);
            if (entityHit instanceof EntityItemFrame) {
                EntityItemFrame itemFrame = (EntityItemFrame) entityHit;
                ItemStack item = itemFrame.getDisplayedItem();
                if (item != null && item.getItem() == Items.arrow) {
                    BlockPos blockPos = Utils.getBlockUnderItemFrame(itemFrame);
                    if (mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.sea_lantern) {
                        event.setCanceled(true);
                    }
                }
            }
        }

    }

}
