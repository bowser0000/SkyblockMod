package me.Danker.mixin;

import me.Danker.DankersSkyblockMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.lang.reflect.Field;

@Mixin(TileEntityChestRenderer.class)
public class MixinTileEntityChestRenderer {

    @Inject(method = "renderTileEntityAt", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.color(FFFF)V", shift = At.Shift.AFTER))
    private void setChestColor(TileEntityChest te, double x, double y, double z, float partialTicks, int destroyStage, CallbackInfo ci) {
        if (te.getPos().equals(DankersSkyblockMod.riddleChest)) {
            Color colour = new Color(255, 0, 0, 255);
            GlStateManager.color((float)colour.getRed()/255, (float)colour.getGreen()/255, (float)colour.getBlue()/255);
            // disable NEU's dungeon block overlay
            if (Loader.isModLoaded("notenoughupdates")) {
                try {
                    Class neuUtilsClass = Class.forName("io.github.moulberry.notenoughupdates.util.Utils");
                    Field disableField = neuUtilsClass.getDeclaredField("disableCustomDungColours");
                    disableField.setAccessible(true);
                    disableField.set(null, true);
                } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException ignored)  {

                }
            }
        }
    }
}
