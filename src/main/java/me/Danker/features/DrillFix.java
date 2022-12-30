package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.events.PacketReadEvent;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DrillFix {

    // https://github.com/BiscuitDevelopment/SkyblockAddons/blob/v1.2.0/src/main/java/codes/biscuit/skyblockaddons/mixins/MixinNetHandlerPlayClient.java
    @SubscribeEvent
    public void onPacketRead(PacketReadEvent event) {
        if (ModConfig.drillFix && Utils.inSkyblock) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayer player = mc.thePlayer;
            boolean isDown = mc.gameSettings.keyBindUseItem.isKeyDown();

            if (player == null || player.inventory == null) return;

            if (event.packet instanceof S2FPacketSetSlot) {
                S2FPacketSetSlot packet = (S2FPacketSetSlot) event.packet;
                int windowId = packet.func_149175_c();
                int slot = packet.func_149173_d();
                ItemStack item = packet.func_149174_e();

                if (item != null && windowId == 0 && slot - 36 == player.inventory.currentItem) {
                    if (item.getItem().equals(Items.prismarine_shard) && isDown) {
                        event.setCanceled(true);
                    }
                }
            } else if (event.packet instanceof S30PacketWindowItems) {
                S30PacketWindowItems packet = (S30PacketWindowItems) event.packet;
                ItemStack[] items = packet.getItemStacks();

                if (items.length == 45) {
                    int slot = player.inventory.currentItem + 36;
                    ItemStack item = items[slot];

                    if (item != null) {
                        if (item.getItem().equals(Items.prismarine_shard) && isDown) {
                            items[slot] = player.inventory.getCurrentItem();
                            event.packet = packet;
                        }
                    }
                }
            }
        }
    }

}
