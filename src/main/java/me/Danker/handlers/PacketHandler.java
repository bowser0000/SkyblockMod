package me.Danker.handlers;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.Danker.commands.ToggleCommand;
import me.Danker.commands.ToggleNoRotateCommand;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

import java.lang.reflect.Field;

public class PacketHandler extends ChannelDuplexHandler {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof net.minecraft.network.Packet && msg.getClass().getName().endsWith("S08PacketPlayerPosLook") && ToggleNoRotateCommand.norotatetoggled) {
            EntityPlayerSP player = (Minecraft.getMinecraft()).thePlayer;
            if (player != null) {
                ItemStack item = player.getHeldItem();
                if ((item != null && item.getDisplayName().contains("Hyperion")) || (item != null && item.getDisplayName().contains("Aspect of the End"))) {
                    S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)msg;
                    Field packetYaw = packet.getClass().getDeclaredField("field_148936_d");
                    Field packetPitch = packet.getClass().getDeclaredField("field_148937_e");
                    packetYaw.setAccessible(true);
                    packetPitch.setAccessible(true);
                    packetYaw.setFloat(packet, player.rotationYaw);
                    packetPitch.setFloat(packet, player.rotationPitch);
                    msg = packet;
                }
            }
		}
		
		super.channelRead(ctx, msg);
	}
	
	// Ignore item frames with arrows on sea lanterns
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (ToggleCommand.itemFrameOnSeaLanternsToggled && Utils.inDungeons && msg instanceof Packet && msg.getClass().getName().endsWith("C02PacketUseEntity")) {
			Minecraft mc = Minecraft.getMinecraft();
			C02PacketUseEntity packet = (C02PacketUseEntity) msg;
			Entity entityHit = packet.getEntityFromWorld(mc.theWorld);
			if (entityHit instanceof EntityItemFrame) {
				EntityItemFrame itemFrame = (EntityItemFrame) entityHit;
	    		ItemStack item = itemFrame.getDisplayedItem();
	    		if (item != null && item.getItem() == Items.arrow) {
		    		BlockPos blockPos = Utils.getBlockUnderItemFrame(itemFrame);
		    		if (mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.sea_lantern) {
		    			return;
		    		}
	    		}
			}
		}
		
		super.write(ctx, msg, promise);
	}
	
}
