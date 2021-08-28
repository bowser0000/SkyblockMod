package me.Danker.features;

import me.Danker.events.PacketReadEvent;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class SpiritBootsFix {

    static Field slot = ReflectionHelper.findField(S04PacketEntityEquipment.class, "equipmentSlot", "field_149392_b");

    @SubscribeEvent
    public void onPacketRead(PacketReadEvent event) throws IllegalAccessException {
        if (Utils.inSkyblock && event.packet instanceof S04PacketEntityEquipment) {
            S04PacketEntityEquipment packet = (S04PacketEntityEquipment) event.packet;
            if (packet.getEntityID() == Minecraft.getMinecraft().thePlayer.getEntityId()) {
                slot.setAccessible(true);
                slot.setInt(packet, slot.getInt(packet) + 1);
                event.packet = packet;
            }
        }
    }

}
