package me.Danker;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
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
    public static final String VERSION = "1.3";
    
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
		
		// Wolf Loot
		if (!cf.hasKey("wolf", "svens")) cf.writeIntConfig("wolf", "svens", 0);
		if (!cf.hasKey("wolf", "spirit")) cf.writeIntConfig("wolf", "spirit", 0);
		if (!cf.hasKey("wolf", "book")) cf.writeIntConfig("wolf", "book", 0);
		if (!cf.hasKey("wolf", "egg")) cf.writeIntConfig("wolf", "egg", 0);
		if (!cf.hasKey("wolf", "couture")) cf.writeIntConfig("wolf", "couture", 0);
		if (!cf.hasKey("wolf", "bait")) cf.writeIntConfig("wolf", "bait", 0);
		if (!cf.hasKey("wolf", "flux")) cf.writeIntConfig("wolf", "flux", 0);
		if (!cf.hasKey("wolf", "timeRNG")) cf.writeIntConfig("wolf", "timeRNG", -1);
		if (!cf.hasKey("wolf", "bossRNG")) cf.writeIntConfig("wolf", "bossRNG", -1);
		// Spider Loot
		if (!cf.hasKey("spider", "tarantulas")) cf.writeIntConfig("spider", "tarantulas", 0);
		if (!cf.hasKey("spider", "bite")) cf.writeIntConfig("spider", "bite", 0);
		if (!cf.hasKey("spider", "catalyst")) cf.writeIntConfig("spider", "catalyst", 0);
		if (!cf.hasKey("spider", "book")) cf.writeIntConfig("spider", "book", 0);
		if (!cf.hasKey("spider", "swatter")) cf.writeIntConfig("spider", "swatter", 0);
		if (!cf.hasKey("spider", "talisman")) cf.writeIntConfig("spider", "talisman", 0);
		if (!cf.hasKey("spider", "mosquito")) cf.writeIntConfig("spider", "mosquito", 0);
		if (!cf.hasKey("spider", "timeRNG")) cf.writeIntConfig("spider", "timeRNG", -1);
		if (!cf.hasKey("spider", "bossRNG")) cf.writeIntConfig("spider", "bossRNG", -1);
		// Zombie Loot
		if (!cf.hasKey("zombie", "revs")) cf.writeIntConfig("zombie", "revs", 0);
		if (!cf.hasKey("zombie", "pestilence")) cf.writeIntConfig("zombie", "pestilence", 0);
		if (!cf.hasKey("zombie", "undeadCatalyst")) cf.writeIntConfig("zombie", "undeadCatalyst", 0);
		if (!cf.hasKey("zombie", "book")) cf.writeIntConfig("zombie", "book", 0);
		if (!cf.hasKey("zombie", "beheaded")) cf.writeIntConfig("zombie", "beheaded", 0);
		if (!cf.hasKey("zombie", "revCatalyst")) cf.writeIntConfig("zombie", "revCatalyst", 0);
		if (!cf.hasKey("zombie", "snake")) cf.writeIntConfig("zombie", "snake", 0);
		if (!cf.hasKey("zombie", "scythe")) cf.writeIntConfig("zombie", "scythe", 0);
		if (!cf.hasKey("zombie", "timeRNG")) cf.writeIntConfig("zombie", "timeRNG", -1);
		if (!cf.hasKey("zombie", "bossRNG")) cf.writeIntConfig("zombie", "bossRNG", -1);
		
		final ToggleCommand tf = new ToggleCommand();
		tf.gpartyToggled = cf.getBoolean("toggles", "GParty");
		tf.coordsToggled = cf.getBoolean("toggles", "Coords");
		
		final LootCommand lc = new LootCommand();
		// Wolf
		lc.wolfSvens = cf.getInt("wolf", "svens");
		lc.wolfSpirits = cf.getInt("wolf", "spirit");
		lc.wolfBooks = cf.getInt("wolf", "book");
		lc.wolfEggs = cf.getInt("wolf", "egg");
		lc.wolfCoutures = cf.getInt("wolf", "couture");
		lc.wolfBaits = cf.getInt("wolf", "bait");
		lc.wolfFluxes = cf.getInt("wolf", "flux");
		lc.wolfTime = cf.getInt("wolf", "timeRNG");
		lc.wolfBosses = cf.getInt("wolf", "bossRNG");
		// Spider
		lc.spiderTarantulas = cf.getInt("spider", "tarantulas");
		lc.spiderBites = cf.getInt("spider", "bite");
		lc.spiderCatalysts = cf.getInt("spider", "catalyst");
		lc.spiderBooks = cf.getInt("spider", "book");
		lc.spiderSwatters = cf.getInt("spider", "swatter");
		lc.spiderTalismans = cf.getInt("spider", "talisman");
		lc.spiderMosquitos = cf.getInt("spider", "mosquito");
		lc.spiderTime = cf.getInt("spider", "timeRNG");
		lc.spiderBosses = cf.getInt("spider", "bossRNG");
		// Zombie
		lc.zombieRevs = cf.getInt("zombie", "revs");
		lc.zombiePestilences = cf.getInt("zombie", "pestilence");
		lc.zombieUndeadCatas = cf.getInt("zombie", "undeadCatalyst");
		lc.zombieBooks = cf.getInt("zombie", "book");
		lc.zombieBeheadeds = cf.getInt("zombie", "beheaded");
		lc.zombieRevCatas = cf.getInt("zombie", "revCatalyst");
		lc.zombieSnakes = cf.getInt("zombie", "snake");
		lc.zombieScythes = cf.getInt("zombie", "scythe");
		lc.zombieTime = cf.getInt("zombie", "timeRNG");
		lc.zombieBosses = cf.getInt("zombie", "bossRNG");
		
    }
    
    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
    	ClientCommandHandler.instance.registerCommand(new ToggleCommand());
    	ClientCommandHandler.instance.registerCommand(new SetkeyCommand());
    	ClientCommandHandler.instance.registerCommand(new GetkeyCommand());
    	ClientCommandHandler.instance.registerCommand(new LootCommand());
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
    	final ToggleCommand tc = new ToggleCommand();
    	final boolean isGPartyToggled = tc.getToggle("gparty");
    	String message = event.message.getUnformattedText();
    	
    	if (!message.contains(":")) {
    		if (isGPartyToggled) {
        		if (message.contains(" has invited all members of ")) {
        			System.out.println(message);
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
        	
    		// Time is stored as a 32-bit int in seconds, so if Skyblock
    		// survives until 2038, I'll just update it then
    		final LootCommand lc = new LootCommand();
    		final ConfigHandler cf = new ConfigHandler();
    		boolean wolfRNG = false;
    		boolean spiderRNG = false;
    		boolean zombieRNG = false;
    		// T6 books
    		if (message.contains("VERY RARE DROP! (Enchanted Book)")) {
    			// Loop through scoreboard to see what boss you're doing
    			List<String> scoreboard = ScoreboardHandler.getSidebarLines();
    			for (String s : scoreboard) {
    				String sCleaned = ScoreboardHandler.cleanSB(s);
    				if (sCleaned.contains("Sven Packmaster")) {
    					lc.wolfBooks++;
    					cf.writeIntConfig("wolf", "book", lc.wolfBooks);
    				} else if (sCleaned.contains("Tarantula Broodfather")) {
    					lc.spiderBooks++;
    					cf.writeIntConfig("spider", "book", lc.spiderBooks);
    				} else if (sCleaned.contains("Revenant Horror")) {
    					lc.zombieBooks++;
    					cf.writeIntConfig("zombie", "book", lc.zombieBooks);
    				}
    			}
    		}

    		// Wolf
    		if (message.contains("Talk to Maddox to claim your Wolf Slayer XP!")) {
    			lc.wolfSvens++;
    			cf.writeIntConfig("wolf", "svens", lc.wolfSvens);
    		}
    		if (message.contains("VERY RARE DROP! (◆ Spirit Rune ")) {
    			lc.wolfSpirits++;
    			cf.writeIntConfig("wolf", "spirit", lc.wolfSpirits);
    		}
    		if (message.contains("CRAZY RARE DROP! (Red Claw Egg)")) {
    			wolfRNG = true;
    			lc.wolfEggs++;
    			cf.writeIntConfig("wolf", "egg", lc.wolfEggs);
    		}
    		if (message.contains("CRAZY RARE DROP! (◆ Couture Rune")) {
    			wolfRNG = true;
    			lc.wolfCoutures++;
    			cf.writeIntConfig("wolf", "couture", lc.wolfCoutures);
    		}
    		// How did Skyblock devs even manage to make this item Rename Me
    		if (message.contains("CRAZY RARE DROP! (Grizzly Bait)") || message.contains("CRAZY RARE DROP! (Rename Me)")) {
    			wolfRNG = true;
    			lc.wolfBaits++;
    			cf.writeIntConfig("wolf", "bait", lc.wolfBaits);
    		}
    		if (message.contains("CRAZY RARE DROP! (Overflux Capacitor)")) {
    			wolfRNG = true;
    			lc.wolfFluxes++;
    			cf.writeIntConfig("wolf", "flux", lc.wolfFluxes);
    		}

    		// Spider
    		if (message.contains("Talk to Maddox to claim your Spider Slayer XP!")) {
    			lc.spiderTarantulas++;
    			cf.writeIntConfig("spider", "tarantulas", lc.spiderTarantulas);
    		}

    		if (message.contains("VERY RARE DROP! (◆ Bite Rune")) {
    			lc.spiderBites++;
    			cf.writeIntConfig("spider", "bite", lc.spiderBites);
    		}
    		if (message.contains("VERY RARE DROP! (Spider Catalyst)")) {
    			lc.spiderCatalysts++;
    			cf.writeIntConfig("spider", "catalyst", lc.spiderCatalysts);
    		}
    		// T3 Spider Book Drop
    		if (message.contains("CRAZY RARE DROP! (Enchanted Book)")) {
    			lc.spiderBooks++;
    			cf.writeIntConfig("spider", "book", lc.spiderBooks);
    		}
    		if (message.contains("CRAZY RARE DROP! (Fly Swatter)")) {
    			spiderRNG = true;
    			lc.spiderSwatters++;
    			cf.writeIntConfig("spider", "swatter", lc.spiderSwatters);
    		}
    		if (message.contains("CRAZY RARE DROP! (Tarantula Talisman")) {
    			spiderRNG = true;
    			lc.spiderTalismans++;
    			cf.writeIntConfig("spider", "talisman", lc.spiderTalismans);
    		}
    		if (message.contains("CRAZY RARE DROP! (Digested Mosquito)")) {
    			spiderRNG = true;
    			lc.spiderMosquitos++;
    			cf.writeIntConfig("spider", "mosquito", lc.spiderMosquitos);
    		}

    		// Zombie
    		if (message.contains("Talk to Maddox to claim your Zombie Slayer XP!")) {
    			lc.zombieRevs++;
    			cf.writeIntConfig("zombie", "revs", lc.zombieRevs);
    		}
    		// I couldn't find a pic of someone getting this drop, so I'm assuming this works
    		if (message.contains("VERY RARE DROP! (Revenant Catalyst)")) {
    			lc.zombieRevCatas++;
    			cf.writeIntConfig("zombie", "revCatalyst", lc.zombieRevCatas);
    		}
    		if (message.contains("VERY RARE DROP! (◆ Pestilence Rune")) {
    			lc.zombiePestilences++;
    			cf.writeIntConfig("zombie", "pestilence", lc.zombiePestilences);
    		}
    		if (message.contains("VERY RARE DROP! (Undead Catalyst)")) {
    			lc.zombieUndeadCatas++;
    			cf.writeIntConfig("zombie", "undeadCatalyst", lc.zombieUndeadCatas);
    		}
    		if (message.contains("CRAZY RARE DROP! (Beheaded Horror)")) {
    			zombieRNG = true;
    			lc.zombieBeheadeds++;
    			cf.writeIntConfig("zombie", "beheaded", lc.zombieBeheadeds);
    		}

    		if (message.contains("CRAZY RARE DROP! (◆ Snake Rune")) {
    			zombieRNG = true;
    			lc.zombieSnakes++;
    			cf.writeIntConfig("zombie", "snake", lc.zombieSnakes);
    		}
    		if (message.contains("CRAZY RARE DROP! (Scythe Blade)")) {
    			zombieRNG = true;
    			lc.zombieScythes++;
    			cf.writeIntConfig("zombie", "scythe", lc.zombieScythes);
    		}

    		if (wolfRNG) {
    			lc.wolfTime = (int) System.currentTimeMillis() / 1000;
    			lc.wolfBosses = 0;
    			cf.writeIntConfig("wolf", "timeRNG", lc.wolfTime);
    			cf.writeIntConfig("wolf", "bossRNG", 0);
    		} else {
    			if (lc.wolfBosses != -1) {
    				lc.wolfBosses++;
    			}
    			cf.writeIntConfig("wolf", "bossRNG", lc.wolfBosses);
    		}
    		if (spiderRNG) {
    			lc.spiderTime = (int) System.currentTimeMillis() / 1000;
    			lc.spiderBosses = 0;
    			cf.writeIntConfig("spider", "timeRNG", lc.spiderTime);
    			cf.writeIntConfig("spider", "bossRNG", 0);
    		} else {
    			if (lc.spiderBosses != -1) {
    				lc.spiderBosses++;
    			}
    			cf.writeIntConfig("spider", "bossRNG", lc.spiderBosses);
    		}
    		if (zombieRNG) {
    			lc.zombieTime = (int) System.currentTimeMillis() / 1000;
    			lc.zombieBosses = 0;
    			cf.writeIntConfig("zombie", "timeRNG", lc.zombieTime);
    			cf.writeIntConfig("zombie", "bossRNG", 0);
    		} else {
    			if (lc.zombieBosses != -1) {
    				lc.zombieBosses++;
    			}
    			cf.writeIntConfig("wolf", "bossRNG", lc.zombieBosses);
    		}
    	}	
    }
    
    @SubscribeEvent
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
    	if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
    	final ToggleCommand tc = new ToggleCommand();
    	final boolean isCoordsToggled = tc.getToggle("coords");
    	
    	if (isCoordsToggled) {
    		ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
    		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    		
        	double xDir = (player.rotationYaw % 360 + 360) % 360;
        	if (xDir > 180) xDir -= 360;
        	xDir = (double) Math.round(xDir * 10d) / 10d;
        	double yDir = (double) Math.round(player.rotationPitch * 10d) / 10d;
        	
        	String coordText = (int) player.posX + " / " + (int) player.posY + " / " + (int) player.posZ + " (" + xDir + " / " + yDir + ")";
        	int height = scaled.getScaledHeight();
        	new TextRenderer(Minecraft.getMinecraft(), coordText, 5, height - 25, Integer.parseInt("FFFFFF", 16));
    	}
    }
    
}
