package me.Danker.mixin;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderBlaze.class)
public class MixinRenderBlaze {

    private static final ResourceLocation BLANK_BLAZE_TEXTURE = new ResourceLocation("dsm", "blankblaze.png");

    @Inject(method = "getEntityTexture", at = @At("RETURN"), cancellable = true)
    private void onGetEntityTexture(EntityBlaze entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (ToggleCommand.blazeToggled && Utils.inDungeons) {
            cir.setReturnValue(BLANK_BLAZE_TEXTURE);
        }
    }
}
