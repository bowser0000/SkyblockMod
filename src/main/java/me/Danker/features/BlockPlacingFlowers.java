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

    ArrayList<String> flowerWeapons  = new ArrayList<>(Arrays.asList(
            "Flower of Truth",
            "Spirit Sceptre",
            "Bouquet of Lies",
            "Fire Freeze Staff"
    ));

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (!Utils.inSkyblock || Minecraft.getMinecraft().thePlayer != event.entityPlayer) return;
        ItemStack item = event.entityPlayer.getHeldItem();
        if (item == null) return;

        if (ModConfig.flowerWeapons && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(event.pos).getBlock();
            Block blockUnder = Minecraft.getMinecraft().theWorld.getBlockState(event.pos.down().offset(event.face)).getBlock();

            if (flowerPlaceable.contains(block) || flowerPlaceable.contains(blockUnder)) {
                for (String weapon : flowerWeapons) {
                    if(item.getDisplayName().contains(weapon)){
                        event.setCanceled(true);
                        break;
                    }
                }
            }
        }
    }

}
