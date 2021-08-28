package me.Danker.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketReadEvent extends Event {

    public Packet packet;

    public PacketReadEvent(Packet packet) {
        this.packet = packet;
    }

}
