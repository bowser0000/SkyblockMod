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

public class BlockAbilities {

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (!Utils.inSkyblock || Minecraft.getMinecraft().thePlayer != event.entityPlayer) return;
        ItemStack item = event.entityPlayer.getHeldItem();
        if (item == null) return;

        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            if (ToggleCommand.aotdToggled && item.getDisplayName().contains("Aspect of the Dragons")) {
                event.setCanceled(true);
            }
            if (ToggleCommand.lividDaggerToggled && item.getDisplayName().contains("Livid Dagger")) {
                event.setCanceled(true);
            }
        } else if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(event.pos).getBlock();

            ArrayList<Block> interactables = new ArrayList<>(Arrays.asList(
                    Blocks.acacia_door,
                    Blocks.anvil,
                    Blocks.beacon,
                    Blocks.bed,
                    Blocks.birch_door,
                    Blocks.brewing_stand,
                    Blocks.command_block,
                    Blocks.crafting_table,
                    Blocks.chest,
                    Blocks.dark_oak_door,
                    Blocks.daylight_detector,
                    Blocks.daylight_detector_inverted,
                    Blocks.dispenser,
                    Blocks.dropper,
                    Blocks.enchanting_table,
                    Blocks.ender_chest,
                    Blocks.furnace,
                    Blocks.hopper,
                    Blocks.jungle_door,
                    Blocks.lever,
                    Blocks.noteblock,
                    Blocks.powered_comparator,
                    Blocks.unpowered_comparator,
                    Blocks.powered_repeater,
                    Blocks.unpowered_repeater,
                    Blocks.standing_sign,
                    Blocks.wall_sign,
                    Blocks.trapdoor,
                    Blocks.trapped_chest,
                    Blocks.wooden_button,
                    Blocks.stone_button,
                    Blocks.oak_door,
                    Blocks.skull
            ));
            if (Utils.inDungeons) {
                interactables.add(Blocks.coal_block);
                interactables.add(Blocks.stained_hardened_clay);
            }

            if (!interactables.contains(block)) {
                if (ToggleCommand.aotdToggled && item.getDisplayName().contains("Aspect of the Dragons")) {
                    event.setCanceled(true);
                }
                if (ToggleCommand.lividDaggerToggled && item.getDisplayName().contains("Livid Dagger")) {
                    event.setCanceled(true);
                }
            }
        }
    }

}
