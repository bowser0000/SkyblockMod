package me.Danker.mixin;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.util.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossStatus.class)
public class MixinBossStatus {
    @Inject(method = "setBossStatus", at = @At("HEAD"), cancellable = true)
    private static void onSetBossStatus(IBossDisplayData displayData, boolean hasColorModifierIn, CallbackInfo ci) {
        if (Utils.inSkyblock) {
            if (ToggleCommand.bossBarFixToggled) {
                String unformatted = StringUtils.stripControlCodes(displayData.getDisplayName().getUnformattedText()).trim();
                if (unformatted.equals("Wither")) {
                    ci.cancel();
                    return;
                }
            }
        }
    }
}
