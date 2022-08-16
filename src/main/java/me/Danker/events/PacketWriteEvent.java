package me.Danker.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketWriteEvent extends Event {

    public Packet packet;

    public PacketWriteEvent(Packet packet) {
        this.packet = packet;
    }

}
