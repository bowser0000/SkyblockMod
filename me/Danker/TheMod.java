package me.Danker;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = TheMod.MODID, version = TheMod.VERSION)
public class TheMod
{
    public static final String MODID = "Danker's Skyblock Mod";
    public static final String VERSION = "1.2";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		
		// Config init
		final ConfigHandler cf = new ConfigHandler();
		if (!cf.hasKey("toggles", "GParty")) cf.writeBooleanConfig("toggles", "GParty", true);
		if (!cf.hasKey("toggles", "Coords")) cf.writeBooleanConfig("toggles", "Coords", true);
		if (!cf.hasKey("api", "APIKey")) cf.writeStringConfig("api", "APIKey", "");
		
		final ToggleCommand tf = new ToggleCommand();
		tf.gpartyToggled = cf.getBoolean("toggles", "GParty");
		tf.coordsToggled = cf.getBoolean("toggles", "Coords");
    }
    
    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
    	ClientCommandHandler.instance.registerCommand(new ToggleCommand());
    	ClientCommandHandler.instance.registerCommand(new SetkeyCommand());
    	ClientCommandHandler.instance.registerCommand(new GetkeyCommand());
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
    	final ToggleCommand tc = new ToggleCommand();
    	final boolean isGPartyToggled = tc.getToggle("gparty");
    	String message = event.message.getUnformattedText();
    	String messagelc = message.toLowerCase();
    	
    	if (isGPartyToggled) {
    		if (messagelc.contains(" has invited all members of ")) {
    			try {
    				final SystemTray tray = SystemTray.getSystemTray();
    				final Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
    				final TrayIcon trayIcon = new TrayIcon(image, "Guild Party Notifier");
    				trayIcon.setImageAutoSize(true);
    				trayIcon.setToolTip("Guild Party Notifier");
    				tray.add(trayIcon);
    				trayIcon.displayMessage("Guild Party", message, TrayIcon.MessageType.INFO);
    				tray.remove(trayIcon);
    			} catch (Exception ex) {
    				System.err.print(ex);
    			}
    		}
    	}
    	
    }
    
    @SubscribeEvent
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
    	if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
    	final ToggleCommand tc = new ToggleCommand();
    	final boolean isCoordsToggled = tc.getToggle("coords");
    	
    	if (isCoordsToggled) {
    		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        	double xDir = (player.rotationYaw % 360 + 360) % 360;
        	if (xDir > 180) xDir -= 360;
        	xDir = (double) Math.round(xDir * 10d) / 10d;
        	double yDir = (double) Math.round(player.rotationPitch * 10d) / 10d;
        	String text = (int) player.posX + " / " + (int) player.posY + " / " + (int) player.posZ + " (" + xDir + " / " + yDir + ")";
        	new CoordRenderer(Minecraft.getMinecraft(), text);
    	}
    	
    }
}
