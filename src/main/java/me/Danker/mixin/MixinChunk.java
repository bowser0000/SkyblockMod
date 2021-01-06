package me.Danker.mixin;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Chunk.class)
public abstract class MixinChunk {

    @Shadow
    public abstract IBlockState getBlockState(final BlockPos pos);

    @Inject(method = "setBlockState", at = @At("HEAD"), cancellable = true)
    private void onSetBlockState(BlockPos pos, IBlockState state, CallbackInfoReturnable<IBlockState> cir) {
        IBlockState old = this.getBlockState(pos);
        if (old != state) {
            if (Utils.inDungeons) {
                if (ToggleCommand.simonToggled) {
                    if (pos.getY() <= 123 && pos.getY() >= 120 && pos.getZ() >= 291 && pos.getZ() <= 294) {
                        if (pos.getX() == 310) {
                            System.out.println(String.format("Block at %s changed to %s from %s", pos, state.getBlock().getLocalizedName(), old.getBlock().getLocalizedName()));
                            if (state.getBlock() == Blocks.sea_lantern) {
                                if (!DankersSkyblockMod.simonBlockOrder.contains(pos)) {
                                    DankersSkyblockMod.simonBlockOrder.add(pos);
                                }
                            }
                        } else if (pos.getX() == 309) {
                            if (state.getBlock() == Blocks.air) {
                                System.out.println("Buttons on simon says were removed!");
                                DankersSkyblockMod.simonNumberNeeded = 0;
                            } else if (state.getBlock() == Blocks.stone_button) {
                                if (old.getBlock() == Blocks.stone_button) {
                                    if (state.getValue(BlockButtonStone.POWERED)) {
                                        System.out.println("Button on simon says was pressed");
                                        DankersSkyblockMod.simonNumberNeeded++;
                                    }
                                }
                            }
                        }
                    } else if (pos.equals(new BlockPos(309, 121, 290))) {
                        if (state.getBlock() == Blocks.stone_button) {
                            if (state.getValue(BlockButtonStone.POWERED)) {
                                System.out.println("Simon says was started");
                                DankersSkyblockMod.simonBlockOrder.clear();
                                DankersSkyblockMod.simonNumberNeeded = 0;
                            }
                        }
                    }
                }
            }
        }
    }
}
