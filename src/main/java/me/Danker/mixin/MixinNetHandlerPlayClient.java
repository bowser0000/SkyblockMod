package me.Danker.mixin;

import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

    @Shadow
    private Minecraft gameController;

    @Inject(method = "handleParticles(Lnet/minecraft/network/play/server/S2APacketParticles;)V", at = @At("HEAD"), cancellable = true)
    private void onHandleParticles(S2APacketParticles packetIn, CallbackInfo ci) {

        boolean longDistance = packetIn.isLongDistance();
        int count = packetIn.getParticleCount();
        float speed = packetIn.getParticleSpeed();
        float xOffset = packetIn.getXOffset();
        float yOffset = packetIn.getYOffset();
        float zOffset = packetIn.getZOffset();

        double x = packetIn.getXCoordinate();
        double y = packetIn.getYCoordinate();
        double z = packetIn.getZCoordinate();

        if (Utils.inSkyblock) {
            if (packetIn.getParticleType() == EnumParticleTypes.EXPLOSION_LARGE) {
                boolean bigExplosionFilter = count == 8 && speed == 8 && xOffset == 0 && yOffset == 0 && zOffset == 0;

                //More consistent detection system needed
                if (ToggleCommand.hideImplosionParticlesToggled && bigExplosionFilter) {
                        if (gameController.theWorld.isAnyPlayerWithinRangeAt(x, y, z, 11)) {
                            boolean shouldCancel = gameController.theWorld.playerEntities.stream().filter(player -> {
                                double distance = player.getDistanceSq(x, y, z);
                                double range = 11d;

                                return distance < range * range;
                            }).anyMatch(player -> {
                                ItemStack item = player.getHeldItem();

                                if (item != null) {
                                    String itemName = item.getDisplayName();

                                    boolean isHoldingImplosionItem = Utils.getItemLore(item).stream().anyMatch(line -> line.contains("Implosion") || line.contains("implode"));
                                    boolean isHoldingNecronBlade = itemName.contains("Necron's Blade") || itemName.contains("Scylla") || itemName.contains("Astrea") || itemName.contains("Hyperion") || itemName.contains("Valkyrie");

                                    return isHoldingImplosionItem || isHoldingNecronBlade;
                                }
                                return false;
                            });

                            if (shouldCancel) ci.cancel();
                        }
                    } else ci.cancel();
                }
            }
        }
    }
}