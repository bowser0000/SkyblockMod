package me.Danker.mixin;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossStatus.class)
public class MixinBossStatus {
    @Inject(method = "setBossStatus", at = @At("HEAD"), cancellable = true)
    private static void onSetBossStatus(IBossDisplayData displayData, boolean hasColorModifierIn, CallbackInfo ci) {
        if(Utils.inSkyblock) {
            if(displayData.getDisplayName().getUnformattedText().contains("Wither") && ToggleCommand.bossBarFixToggled) {
                ci.cancel();
                return;
            }
        }
    }
}
