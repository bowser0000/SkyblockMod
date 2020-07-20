package me.Danker;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.text.NumberFormat;
import java.util.List;

import me.Danker.commands.DHelpCommand;
import me.Danker.commands.DisplayCommand;
import me.Danker.commands.GetkeyCommand;
import me.Danker.commands.GuildOfCommand;
import me.Danker.commands.LootCommand;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.ReloadConfigCommand;
import me.Danker.commands.SetkeyCommand;
import me.Danker.commands.SkillsCommand;
import me.Danker.commands.SlayerCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.handlers.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = TheMod.MODID, version = TheMod.VERSION, clientSideOnly = true)
public class TheMod
{
    public static final String MODID = "Danker's Skyblock Mod";
    public static final String VERSION = "1.5.3";
    
    static int checkItemsNow = 0;
    static int itemsChecked = 0;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		
		final ConfigHandler cf = new ConfigHandler();
		cf.reloadConfig();
    }
    
    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
    	ClientCommandHandler.instance.registerCommand(new ToggleCommand());
    	ClientCommandHandler.instance.registerCommand(new SetkeyCommand());
    	ClientCommandHandler.instance.registerCommand(new GetkeyCommand());
    	ClientCommandHandler.instance.registerCommand(new LootCommand());
    	ClientCommandHandler.instance.registerCommand(new ReloadConfigCommand());
    	ClientCommandHandler.instance.registerCommand(new DisplayCommand());
    	ClientCommandHandler.instance.registerCommand(new MoveCommand());
    	ClientCommandHandler.instance.registerCommand(new SlayerCommand());
    	ClientCommandHandler.instance.registerCommand(new SkillsCommand());
    	ClientCommandHandler.instance.registerCommand(new GuildOfCommand());
    	ClientCommandHandler.instance.registerCommand(new DHelpCommand());
    }
    
    // It randomly broke, so I had to make it the highest priority
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
    	final ToggleCommand tc = new ToggleCommand();
    	String message = event.message.getUnformattedText();
    	
    	if (!message.contains(":")) {
    		if (tc.gpartyToggled) {
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
        	
    		final LootCommand lc = new LootCommand();
    		final ConfigHandler cf = new ConfigHandler();
    		boolean wolfRNG = false;
    		boolean spiderRNG = false;
    		boolean zombieRNG = false;
    		// T6 books
    		if (message.contains("VERY RARE DROP!  (Enchanted Book)")) {
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
    			if (lc.wolfBosses != -1) {
    				lc.wolfBosses++;
    			}
    			cf.writeIntConfig("wolf", "svens", lc.wolfSvens);
    			cf.writeIntConfig("wolf", "bossRNG", lc.wolfBosses);
    		}
    		// Removing the unicode here *should* fix rune drops not counting
    		if (message.contains("VERY RARE DROP!  (") && message.contains(" Spirit Rune I)")) {
    			lc.wolfSpirits++;
    			cf.writeIntConfig("wolf", "spirit", lc.wolfSpirits);
    		}
    		if (message.contains("CRAZY RARE DROP!  (Red Claw Egg)")) {
    			wolfRNG = true;
    			lc.wolfEggs++;
    			cf.writeIntConfig("wolf", "egg", lc.wolfEggs);
    		}
    		if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Couture Rune I)")) {
    			wolfRNG = true;
    			lc.wolfCoutures++;
    			cf.writeIntConfig("wolf", "couture", lc.wolfCoutures);
    		}
    		// How did Skyblock devs even manage to make this item Rename Me
    		if (message.contains("CRAZY RARE DROP!  (Grizzly Bait)") || message.contains("CRAZY RARE DROP! (Rename Me)")) {
    			wolfRNG = true;
    			lc.wolfBaits++;
    			cf.writeIntConfig("wolf", "bait", lc.wolfBaits);
    		}
    		if (message.contains("CRAZY RARE DROP!  (Overflux Capacitor)")) {
    			wolfRNG = true;
    			lc.wolfFluxes++;
    			cf.writeIntConfig("wolf", "flux", lc.wolfFluxes);
    		}

    		// Spider
    		if (message.contains("Talk to Maddox to claim your Spider Slayer XP!")) {
    			lc.spiderTarantulas++;
    			if (lc.spiderBosses != -1) {
    				lc.spiderBosses++;
    			}
    			cf.writeIntConfig("spider", "tarantulas", lc.spiderTarantulas);
    			cf.writeIntConfig("spider", "bossRNG", lc.spiderBosses);
    		}
    		if (message.contains("VERY RARE DROP!  (") && message.contains(" Bite Rune I)")) {
    			lc.spiderBites++;
    			cf.writeIntConfig("spider", "bite", lc.spiderBites);
    		}
    		if (message.contains("VERY RARE DROP!  (Spider Catalyst)")) {
    			lc.spiderCatalysts++;
    			cf.writeIntConfig("spider", "catalyst", lc.spiderCatalysts);
    		}
    		// T3 Spider Book Drop
    		if (message.contains("CRAZY RARE DROP!  (Enchanted Book)")) {
    			lc.spiderBooks++;
    			cf.writeIntConfig("spider", "book", lc.spiderBooks);
    		}
    		if (message.contains("CRAZY RARE DROP!  (Fly Swatter)")) {
    			spiderRNG = true;
    			lc.spiderSwatters++;
    			cf.writeIntConfig("spider", "swatter", lc.spiderSwatters);
    		}
    		if (message.contains("CRAZY RARE DROP!  (Tarantula Talisman")) {
    			spiderRNG = true;
    			lc.spiderTalismans++;
    			cf.writeIntConfig("spider", "talisman", lc.spiderTalismans);
    		}
    		if (message.contains("CRAZY RARE DROP!  (Digested Mosquito)")) {
    			spiderRNG = true;
    			lc.spiderMosquitos++;
    			cf.writeIntConfig("spider", "mosquito", lc.spiderMosquitos);
    		}

    		// Zombie
    		if (message.contains("Talk to Maddox to claim your Zombie Slayer XP!")) {
    			lc.zombieRevs++;
    			if (lc.zombieBosses != -1) {
    				lc.zombieBosses++;
    			}
    			cf.writeIntConfig("zombie", "revs", lc.zombieRevs);
    			cf.writeIntConfig("wolf", "bossRNG", lc.zombieBosses);
    		}
    		// I couldn't find a pic of someone getting this drop, so I'm assuming this works
    		if (message.contains("VERY RARE DROP!  (Revenant Catalyst)")) {
    			lc.zombieRevCatas++;
    			cf.writeIntConfig("zombie", "revCatalyst", lc.zombieRevCatas);
    		}
    		if (message.contains("VERY RARE DROP!  (") && message.contains(" Pestilence Rune I)")) {
    			lc.zombiePestilences++;
    			cf.writeIntConfig("zombie", "pestilence", lc.zombiePestilences);
    		}
    		if (message.contains("VERY RARE DROP!  (Undead Catalyst)")) {
    			lc.zombieUndeadCatas++;
    			cf.writeIntConfig("zombie", "undeadCatalyst", lc.zombieUndeadCatas);
    		}
    		if (message.contains("CRAZY RARE DROP!  (Beheaded Horror)")) {
    			zombieRNG = true;
    			lc.zombieBeheadeds++;
    			cf.writeIntConfig("zombie", "beheaded", lc.zombieBeheadeds);
    		}
    		if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Snake Rune I)")) {
    			zombieRNG = true;
    			lc.zombieSnakes++;
    			cf.writeIntConfig("zombie", "snake", lc.zombieSnakes);
    		}
    		if (message.contains("CRAZY RARE DROP!  (Scythe Blade)")) {
    			zombieRNG = true;
    			lc.zombieScythes++;
    			cf.writeIntConfig("zombie", "scythe", lc.zombieScythes);
    		}

    		// Time is stored in seconds, so if Skyblock
    		// survives until 2038, I'll just update it then
    		if (wolfRNG) {
    			lc.wolfTime = (int) System.currentTimeMillis() / 1000;
    			lc.wolfBosses = 0;
    			cf.writeIntConfig("wolf", "timeRNG", lc.wolfTime);
    			cf.writeIntConfig("wolf", "bossRNG", 0);
    		}
    		if (spiderRNG) {
    			lc.spiderTime = (int) System.currentTimeMillis() / 1000;
    			lc.spiderBosses = 0;
    			cf.writeIntConfig("spider", "timeRNG", lc.spiderTime);
    			cf.writeIntConfig("spider", "bossRNG", 0);
    		}
    		if (zombieRNG) {
    			lc.zombieTime = (int) System.currentTimeMillis() / 1000;
    			lc.zombieBosses = 0;
    			cf.writeIntConfig("zombie", "timeRNG", lc.zombieTime);
    			cf.writeIntConfig("zombie", "bossRNG", 0);
    		}
    	}	
    }
    
    @SubscribeEvent
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
    	if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
    	final ToggleCommand tc = new ToggleCommand();
    	final MoveCommand moc = new MoveCommand();
    	
    	if (tc.coordsToggled) {
    		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    		
        	double xDir = (player.rotationYaw % 360 + 360) % 360;
        	if (xDir > 180) xDir -= 360;
        	xDir = (double) Math.round(xDir * 10d) / 10d;
        	double yDir = (double) Math.round(player.rotationPitch * 10d) / 10d;
        	
        	String coordText = (int) player.posX + " / " + (int) player.posY + " / " + (int) player.posZ + " (" + xDir + " / " + yDir + ")";
        	new TextRenderer(Minecraft.getMinecraft(), coordText, moc.coordsXY[0], moc.coordsXY[1], Integer.parseInt("FFFFFF", 16));
    	}
    	
    	final DisplayCommand ds = new DisplayCommand();
    	final String displayToggle = ds.display;
    	
    	if (!displayToggle.equals("off")) {
    		final LootCommand lc = new LootCommand();
    		String dropsText = "";
    		String countText = "";
    		String timeBetween = "Never";
    		String bossesBetween = "Never";
    		int timeNow = (int) System.currentTimeMillis() / 1000;
    		
    		if (displayToggle.equals("wolf")) {
    			if (lc.wolfTime == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = lc.getTimeBetween(lc.wolfTime, timeNow);
    			}
    			if (lc.wolfBosses == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = NumberFormat.getIntegerInstance().format(lc.wolfBosses);
    			}
    			
    			dropsText = EnumChatFormatting.GOLD + "Svens Killed:\n" +
							EnumChatFormatting.GREEN + "Wolf Teeth:\n" +
							EnumChatFormatting.BLUE + "Hamster Wheels:\n" +
							EnumChatFormatting.AQUA + "Spirit Runes:\n" + 
							EnumChatFormatting.WHITE + "Critical VI Books:\n" +
							EnumChatFormatting.DARK_RED + "Red Claw Eggs:\n" +
							EnumChatFormatting.GOLD + "Couture Runes:\n" +
							EnumChatFormatting.AQUA + "Grizzly Baits:\n" +
							EnumChatFormatting.DARK_PURPLE + "Overfluxes:\n" +
							EnumChatFormatting.AQUA + "Time Since RNG:\n" +
							EnumChatFormatting.AQUA + "Bosses Since RNG:\n";
    			countText = EnumChatFormatting.GOLD + "" + NumberFormat.getIntegerInstance().format(lc.wolfSvens) + "\n" +
							EnumChatFormatting.GREEN + NumberFormat.getIntegerInstance().format(lc.wolfTeeth) + "\n" +
							EnumChatFormatting.BLUE + NumberFormat.getIntegerInstance().format(lc.wolfWheels) + "\n" +
							EnumChatFormatting.AQUA + lc.wolfSpirits + "\n" + 
							EnumChatFormatting.WHITE + lc.wolfBooks + "\n" +
							EnumChatFormatting.DARK_RED + lc.wolfEggs + "\n" +
							EnumChatFormatting.GOLD + lc.wolfCoutures + "\n" +
							EnumChatFormatting.AQUA + lc.wolfBaits + "\n" +
							EnumChatFormatting.DARK_PURPLE + lc.wolfFluxes + "\n" +
							EnumChatFormatting.AQUA + timeBetween + "\n" +
							EnumChatFormatting.AQUA + bossesBetween + "\n";
    		} else if (displayToggle.equals("spider")) {
    			if (lc.spiderTime == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = lc.getTimeBetween(lc.spiderTime, timeNow);
    			}
    			if (lc.spiderBosses == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = NumberFormat.getIntegerInstance().format(lc.spiderBosses);
    			}
    			
    			dropsText = EnumChatFormatting.GOLD + "Tarantulas Killed:\n" +
							EnumChatFormatting.GREEN + "Tarantula Webs:\n" +
							EnumChatFormatting.DARK_GREEN + "Arrow Poison:\n" +
							EnumChatFormatting.DARK_GRAY + "Bite Runes:\n" + 
							EnumChatFormatting.WHITE + "Bane VI Books:\n" +
							EnumChatFormatting.AQUA + "Spider Catalysts:\n" +
							EnumChatFormatting.DARK_PURPLE + "Tarantula Talismans:\n" +
							EnumChatFormatting.LIGHT_PURPLE + "Fly Swatters:\n" +
							EnumChatFormatting.GOLD + "Digested Mosquitos:\n" +
							EnumChatFormatting.AQUA + "Time Since RNG:\n" +
							EnumChatFormatting.AQUA + "Bosses Since RNG:\n";
    			countText = EnumChatFormatting.GOLD + "" + NumberFormat.getIntegerInstance().format(lc.spiderTarantulas) + "\n" +
							EnumChatFormatting.GREEN + NumberFormat.getIntegerInstance().format(lc.spiderWebs) + "\n" +
							EnumChatFormatting.DARK_GREEN + NumberFormat.getIntegerInstance().format(lc.spiderTAP) + "\n" +
							EnumChatFormatting.DARK_GRAY + lc.spiderBites + "\n" + 
							EnumChatFormatting.WHITE + lc.spiderBooks + "\n" +
							EnumChatFormatting.AQUA + lc.spiderCatalysts + "\n" +
							EnumChatFormatting.DARK_PURPLE + lc.spiderTalismans + "\n" +
							EnumChatFormatting.LIGHT_PURPLE + lc.spiderSwatters + "\n" +
							EnumChatFormatting.GOLD + lc.spiderMosquitos + "\n" +
							EnumChatFormatting.AQUA + timeBetween + "\n" +
							EnumChatFormatting.AQUA + bossesBetween + "\n";
    		} else {
    			if (lc.zombieTime == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = lc.getTimeBetween(lc.zombieTime, timeNow);
    			}
    			if (lc.zombieBosses == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = NumberFormat.getIntegerInstance().format(lc.zombieBosses);
    			}
    			
    			dropsText = EnumChatFormatting.GOLD + "Revs Killed:\n" +
							EnumChatFormatting.GREEN + "Revenant Flesh:\n" +
							EnumChatFormatting.BLUE + "Foul Flesh:\n" +
							EnumChatFormatting.DARK_GREEN + "Pestilence Runes:\n" + 
							EnumChatFormatting.WHITE + "Smite VI Books:\n" +
							EnumChatFormatting.AQUA + "Undead Catalysts:\n" +
							EnumChatFormatting.DARK_PURPLE + "Beheaded Horrors:\n" +
							EnumChatFormatting.RED + "Revenant Catalysts:\n" +
							EnumChatFormatting.DARK_GREEN + "Snake Runes:\n" +
							EnumChatFormatting.GOLD + "Scythe Blades:\n" +
							EnumChatFormatting.AQUA + "Time Since RNG:\n" +
							EnumChatFormatting.AQUA + "Bosses Since RNG:\n";
    			countText = EnumChatFormatting.GOLD + "" + NumberFormat.getIntegerInstance().format(lc.zombieRevs) + "\n" +
							EnumChatFormatting.GREEN + NumberFormat.getIntegerInstance().format(lc.zombieRevFlesh) + "\n" +
							EnumChatFormatting.BLUE + NumberFormat.getIntegerInstance().format(lc.zombieFoulFlesh) + "\n" +
							EnumChatFormatting.DARK_GREEN + lc.zombiePestilences + "\n" + 
							EnumChatFormatting.WHITE + lc.zombieBooks + "\n" +
							EnumChatFormatting.AQUA + lc.zombieUndeadCatas + "\n" +
							EnumChatFormatting.DARK_PURPLE + lc.zombieBeheadeds + "\n" +
							EnumChatFormatting.RED + lc.zombieRevCatas + "\n" +
							EnumChatFormatting.DARK_GREEN + lc.zombieSnakes + "\n" +
							EnumChatFormatting.GOLD + lc.zombieScythes + "\n" +
							EnumChatFormatting.AQUA + timeBetween + "\n" +
							EnumChatFormatting.AQUA + bossesBetween + "\n";
    		}
    		new TextRenderer(Minecraft.getMinecraft(), dropsText, moc.displayXY[0], moc.displayXY[1], Integer.parseInt("FFFFFF", 16));
    		new TextRenderer(Minecraft.getMinecraft(), countText, moc.displayXY[0] + 110, moc.displayXY[1], Integer.parseInt("FFFFFF", 16));
    	}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onSound(final PlaySoundEvent event) {
    	if (event.name.equals("note.pling")) {
    		// Don't check twice within 3 seconds 
    		checkItemsNow = (int) System.currentTimeMillis() / 1000;
    		if (checkItemsNow - itemsChecked < 3) return;
    		
    		final ScoreboardHandler sc = new ScoreboardHandler();
    		List<String> scoreboard = sc.getSidebarLines();
    		
    		for (String line : scoreboard) {
    			String cleanedLine = sc.cleanSB(line);
    			// If Hypixel lags and scoreboard doesn't update
    			if (cleanedLine.contains("Boss slain!") || cleanedLine.contains("Slay the boss!")) {
    				final LootCommand lc = new LootCommand();
    				final ConfigHandler cf = new ConfigHandler();
    				
    				int itemTeeth = getItems("Wolf Tooth");
        			int itemWheels = getItems("Hamster Wheel");
        			int itemWebs = getItems("Tarantula Web");
        			int itemTAP = getItems("Toxic Arrow Poison");
        			int itemRev = getItems("Revenant Flesh");
        			int itemFoul = getItems("Foul Flesh");
        			
        			// If no items, are detected, allow check again. Should fix items not being found
        			if (itemTeeth + itemWheels + itemWebs + itemTAP + itemRev + itemFoul > 0) {
        				itemsChecked = (int) System.currentTimeMillis() / 1000;
            			lc.wolfTeeth += itemTeeth;
            			lc.wolfWheels += itemWheels;
            			lc.spiderWebs += itemWebs;
            			lc.spiderTAP += itemTAP;
            			lc.zombieRevFlesh += itemRev;
            			lc.zombieFoulFlesh += itemFoul;
            			
            			cf.writeIntConfig("wolf", "teeth", lc.wolfTeeth);
            			cf.writeIntConfig("wolf", "wheel", lc.wolfWheels);
            			cf.writeIntConfig("spider", "web", lc.spiderWebs);
            			cf.writeIntConfig("spider", "tap", lc.spiderTAP);
            			cf.writeIntConfig("zombie", "revFlesh", lc.zombieRevFlesh);
            			cf.writeIntConfig("zombie", "foulFlesh", lc.zombieFoulFlesh);
        			}
    			}
    		}
    	}
    }
    
    public int getItems(String item) {
    	Minecraft mc = Minecraft.getMinecraft();
    	EntityPlayer player = mc.thePlayer;
    	
    	double x = player.posX;
    	double y = player.posY;
    	double z = player.posZ;
    	AxisAlignedBB scan = new AxisAlignedBB(x - 6, y - 6, z - 6, x + 6, y + 6, z + 6);
    	List<EntityItem> items = mc.theWorld.getEntitiesWithinAABB(EntityItem.class, scan);
    	
    	for (EntityItem i : items) {
    		String itemName = StringUtils.stripControlCodes(i.getEntityItem().getDisplayName());
    		if (itemName.equals(item)) return i.getEntityItem().stackSize;
    	}
    	// No items found
    	return 0;
    }
    
}
