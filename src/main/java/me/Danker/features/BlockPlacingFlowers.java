package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockPlacingFlowers {

    ArrayList<Block> flowerPlaceable = new ArrayList<>(Arrays.asList(
            Blocks.grass,
            Blocks.dirt,
            Blocks.flower_pot,
            Blocks.tallgrass,
            Blocks.double_plant
    ));

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (!Utils.inSkyblock || Minecraft.getMinecraft().thePlayer != event.entityPlayer) return;
        ItemStack item = event.entityPlayer.getHeldItem();
        if (item == null) return;

        if (ModConfig.flowerWeapons && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(event.pos).getBlock();

            if (flowerPlaceable.contains(block)) {
                if (item.getDisplayName().contains("Flower of Truth")) {
                    event.setCanceled(true);
                }
                if (item.getDisplayName().contains("Spirit Sceptre")) {
                    event.setCanceled(true);
                }
            }
        }
    }

}
