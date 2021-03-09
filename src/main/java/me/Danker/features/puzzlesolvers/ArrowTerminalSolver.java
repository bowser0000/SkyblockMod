package me.Danker.features.puzzlesolvers;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ArrowTerminalSolver {

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != event.entityPlayer) return;

        if (ToggleCommand.itemFrameOnSeaLanternsToggled && Utils.inDungeons && event.target instanceof EntityItemFrame) {
            EntityItemFrame itemFrame = (EntityItemFrame) event.target;
            ItemStack item = itemFrame.getDisplayedItem();
            if (item == null || item.getItem() != Items.arrow) return;
            BlockPos blockPos = Utils.getBlockUnderItemFrame(itemFrame);
            if (mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.sea_lantern) {
                event.setCanceled(true);
            }
        }
    }

}
