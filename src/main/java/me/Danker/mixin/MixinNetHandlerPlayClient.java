package me.Danker.mixin;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

    @Shadow
    private Minecraft gameController;

    @Shadow
    private WorldClient clientWorldController;

    @Inject(method = "addToSendQueue", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<INetHandlerPlayServer> packetIn, CallbackInfo ci) {
        if (packetIn instanceof C02PacketUseEntity) {
            C02PacketUseEntity packet = (C02PacketUseEntity) packetIn;
            if (ToggleCommand.itemFrameOnSeaLanternsToggled && Utils.inDungeons) {
                Entity entityHit = packet.getEntityFromWorld(gameController.theWorld);
                if (entityHit instanceof EntityItemFrame) {
                    EntityItemFrame itemFrame = (EntityItemFrame) entityHit;
                    ItemStack item = itemFrame.getDisplayedItem();
                    if (item != null && item.getItem() == Items.arrow) {
                        BlockPos blockPos = Utils.getBlockUnderItemFrame(itemFrame);
                        if (gameController.theWorld.getBlockState(blockPos).getBlock() == Blocks.sea_lantern) {
                            ci.cancel();
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "handleEntityEquipment", at = @At("HEAD"))
    private void onHandleEntityEquipment(S04PacketEntityEquipment packet, CallbackInfo ci) {
        //Spirit Boots Fix
        if (packet.getEntityID() == gameController.thePlayer.getEntityId()) {
            ObfuscationReflectionHelper.setPrivateValue(S04PacketEntityEquipment.class, packet, packet.getEquipmentSlot() + 1, "equipmentSlot", "field_149392_b");
        }
    }
    
    @Inject(method = "handleParticles(Lnet/minecraft/network/play/server/S2APacketParticles;)V", at = @At("HEAD"), cancellable = true)
    private void onHandleParticles(S2APacketParticles packet, CallbackInfo ci) {

        boolean longDistance = packet.isLongDistance();
        int count = packet.getParticleCount();
        float speed = packet.getParticleSpeed();
        float xOffset = packet.getXOffset();
        float yOffset = packet.getYOffset();
        float zOffset = packet.getZOffset();

        double x = packet.getXCoordinate();
        double y = packet.getYCoordinate();
        double z = packet.getZCoordinate();

        if (Utils.inSkyblock) {

            if (packet.getParticleType() == EnumParticleTypes.FOOTSTEP) {
                boolean burrowFilter = count == 1 && speed <= 0.1 && xOffset <= 0.1 && yOffset <= 0.1 && zOffset <= 0.1;
                if (burrowFilter) {
                    BlockPos blockUnder = new BlockPos(Math.floor(x), Math.floor(y) -1, Math.floor(z));
                    if (clientWorldController.getBlockState(blockUnder).getBlock() == Blocks.grass) {
                        DankersSkyblockMod.griffinBurrows.compute(blockUnder, (pos, times) -> times != null ? times++ : 1);
                    }
                }
            }

            if (packet.getParticleType() == EnumParticleTypes.EXPLOSION_LARGE) {
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