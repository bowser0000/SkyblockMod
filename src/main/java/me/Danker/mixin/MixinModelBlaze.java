package me.Danker.mixin;

import me.Danker.DankersSkyblockMod;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(ModelBlaze.class)
public class MixinModelBlaze {
    @Inject(method = "render", at = @At(value = "HEAD"))
    private void onRender(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale, CallbackInfo ci) {
       if (entityIn.isEntityEqual(DankersSkyblockMod.lowestBlaze)) {
           Color colour = Color.decode(String.valueOf(DankersSkyblockMod.LOWEST_BLAZE_COLOUR));
           GlStateManager.color((float)colour.getRed()/255, (float)colour.getGreen()/255, (float)colour.getBlue()/255);
       }
       if (entityIn.isEntityEqual(DankersSkyblockMod.highestBlaze)) {
           Color colour = Color.decode(String.valueOf(DankersSkyblockMod.HIGHEST_BLAZE_COLOUR));
           GlStateManager.color((float)colour.getRed()/255, (float)colour.getGreen()/255, (float)colour.getBlue()/255);
       }
    }
}
