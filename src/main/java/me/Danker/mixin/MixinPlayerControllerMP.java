package me.Danker.mixin;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.utils.GriffinBurrowUtils;
import me.Danker.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {

    @Final
    @Shadow
    private Minecraft mc;

    private long lastNotifyBreakTime = 0;

    @Inject(method = "onPlayerDamageBlock", at = @At("HEAD"), cancellable = true)
    private void onPlayerDamageBlock(BlockPos pos, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
        EntityPlayerSP p = mc.thePlayer;
        ItemStack heldItem = p.getHeldItem();
        Block block = mc.theWorld.getBlockState(pos).getBlock();

        if (Utils.inSkyblock) {

            if (ToggleCommand.burrowWaypointsToggled && heldItem.getDisplayName().contains("Ancestral Spade") && block == Blocks.grass) {
                if (GriffinBurrowUtils.burrows.stream().anyMatch(burrow -> burrow.getBlockPos().equals(pos))) {
                    GriffinBurrowUtils.lastDugBurrow = pos;
                }
            }

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
                        Blocks.waterlily,
                        Blocks.standing_sign,
                        Blocks.wall_sign,
                        Blocks.wooden_slab,
                        Blocks.double_wooden_slab,
                        Blocks.oak_fence,
                        Blocks.dark_oak_fence,
                        Blocks.birch_fence,
                        Blocks.spruce_fence,
                        Blocks.acacia_fence,
                        Blocks.jungle_fence,
                        Blocks.oak_fence_gate,
                        Blocks.acacia_fence_gate,
                        Blocks.birch_fence_gate,
                        Blocks.jungle_fence_gate,
                        Blocks.spruce_fence_gate,
                        Blocks.dark_oak_fence_gate
                ));

                if (tools.contains(heldItem.getItem()) && farmBlocks.contains(block)) {
                    cir.cancel();
                    if (System.currentTimeMillis() - lastNotifyBreakTime > 10000) {
                        lastNotifyBreakTime = System.currentTimeMillis();
                        p.playSound("note.bass", 1, 0.5f);
                        ChatComponentText notif = new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Danker's Skyblock Mod has prevented you from breaking that block!");
                        notif.setChatStyle(notif.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.GOLD + "Click to toggle"))).setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/toggle blockbreakingfarms")));
                        p.addChatMessage(notif);
                    }
                }
            }
        }

    }
}
