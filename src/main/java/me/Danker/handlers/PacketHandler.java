package me.Danker.handlers;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S04PacketEntityEquipment;

import java.lang.reflect.Field;

public class PacketHandler extends ChannelDuplexHandler {
	
	// Spirit boots fix
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (Utils.inSkyblock && msg instanceof Packet && msg.getClass().getName().endsWith("S04PacketEntityEquipment")) { // Inventory packet name
			S04PacketEntityEquipment packet = (S04PacketEntityEquipment) msg;
			if (packet.getEntityID() == Minecraft.getMinecraft().thePlayer.getEntityId()) {
				Field slot = packet.getClass().getDeclaredField("field_149392_b"); // equipmentSlot
				slot.setAccessible(true);
				slot.setInt(packet, slot.getInt(packet) + 1);
				msg = packet;
			}
		}
		
		super.channelRead(ctx, msg);
	}
	
}
