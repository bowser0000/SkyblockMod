package me.Danker.mixin;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.EnumParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

    @Inject(method = "handleParticles(Lnet/minecraft/network/play/server/S2APacketParticles;)V", at = @At("HEAD"), cancellable = true)
    public void onHandleParticles(S2APacketParticles packetIn, CallbackInfo ci) {

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
                boolean bigExplosionFilter = longDistance && count == 8 && speed == 8 && xOffset == 0 && yOffset == 0 && zOffset == 0;

                //More consistent detection system needed
                if (ToggleCommand.hideImplosionParticlesToggled && bigExplosionFilter) {
                    if (Utils.inDungeons) {
                        EntityPlayer closestPlayer = Minecraft.getMinecraft().theWorld.getClosestPlayer(x, y, z, 4);

                        if (closestPlayer != null) {
                            ItemStack closestPlayerHeldItem = closestPlayer.getHeldItem();

                            if (closestPlayerHeldItem != null) {
                                if(Utils.hasRightClickAbility(closestPlayerHeldItem) && Utils.getItemLore(closestPlayerHeldItem).stream().anyMatch(line -> line.contains("Implosion") || line.contains("Wither Impact"))) {
                                    ci.cancel();
                                }
                            }
                        }
                    } else ci.cancel();
                }
            }
        }
    }
}