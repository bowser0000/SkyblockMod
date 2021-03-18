package me.Danker.features;

import me.Danker.commands.ToggleCommand;
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

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (!Utils.inSkyblock || Minecraft.getMinecraft().thePlayer != event.entityPlayer) return;
        ItemStack item = event.entityPlayer.getHeldItem();
        if (item == null) return;

        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(event.pos).getBlock();

            ArrayList<Block> flowerPlaceable = new ArrayList<>(Arrays.asList(
                    Blocks.grass,
                    Blocks.dirt,
                    Blocks.flower_pot,
                    Blocks.tallgrass,
                    Blocks.double_plant
            ));
            if (flowerPlaceable.contains(block)) {
                if (ToggleCommand.flowerWeaponsToggled && item.getDisplayName().contains("Flower of Truth")) {
                    event.setCanceled(true);
                }
                if (ToggleCommand.flowerWeaponsToggled && item.getDisplayName().contains("Spirit Sceptre")) {
                    event.setCanceled(true);
                }
            }
        }
    }

}
