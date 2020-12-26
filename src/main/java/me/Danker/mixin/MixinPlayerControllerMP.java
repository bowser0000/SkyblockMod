package me.Danker.mixin;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {
    @Inject(method = "onPlayerDamageBlock", at = @At("HEAD"), cancellable = true)
    private void onPlayerDamageBlock(BlockPos pos, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP p = mc.thePlayer;
        ItemStack heldItem = p.getHeldItem();
        Block block = mc.theWorld.getBlockState(pos).getBlock();

        if (Utils.inSkyblock) {

            if (ToggleCommand.blockBreakingFarmsToggled && heldItem != null) {
                ArrayList<Item> tools = new ArrayList<>(Arrays.asList(
                        Items.wooden_hoe,
                        Items.stone_hoe,
                        Items.iron_hoe,
                        Items.golden_hoe,
                        Items.diamond_hoe,
                        Items.wooden_axe,
                        Items.stone_axe,
                        Items.iron_axe,
                        Items.golden_axe,
                        Items.diamond_axe
                ));

                ArrayList<Block> farmBlocks = new ArrayList<>(Arrays.asList(
                        Blocks.dirt,
                        Blocks.farmland,
                        Blocks.carpet,
                        Blocks.glowstone,
                        Blocks.sea_lantern,
                        Blocks.soul_sand,
                        Blocks.waterlily
                ));

                if (tools.contains(heldItem.getItem()) && farmBlocks.contains(block)) {
                    cir.cancel();
                }
            }
        }

    }
}
