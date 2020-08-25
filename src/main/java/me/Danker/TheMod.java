package me.Danker;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.lwjgl.input.Keyboard;

import com.google.gson.JsonObject;

import me.Danker.commands.ArmourCommand;
import me.Danker.commands.BankCommand;
import me.Danker.commands.ChatMaddoxCommand;
import me.Danker.commands.DHelpCommand;
import me.Danker.commands.DisplayCommand;
import me.Danker.commands.GetkeyCommand;
import me.Danker.commands.GuildOfCommand;
import me.Danker.commands.ImportFishingCommand;
import me.Danker.commands.LootCommand;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.PetsCommand;
import me.Danker.commands.ReloadConfigCommand;
import me.Danker.commands.ResetLootCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.SetkeyCommand;
import me.Danker.commands.SkillsCommand;
import me.Danker.commands.SkyblockPlayersCommand;
import me.Danker.commands.SlayerCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;

@Mod(modid = TheMod.MODID, version = TheMod.VERSION, clientSideOnly = true)
public class TheMod
{
    public static final String MODID = "Danker's Skyblock Mod";
    public static final String VERSION = "1.6.2";
    
    static double checkItemsNow = 0;
    static double itemsChecked = 0;
    public static Map<String, String> t6Enchants = new HashMap<String, String>();
    public static Pattern pattern = Pattern.compile("");
    static boolean updateChecked = false;
    public static int titleTimer = -1;
    public static boolean showTitle = false;
    public static String titleText = "";
    static int tickAmount = 1;
    public static String lastMaddoxCommand = "/cb placeholdervalue";
    static KeyBinding[] keyBindings = new KeyBinding[1];
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		
		final ConfigHandler cf = new ConfigHandler();
		cf.reloadConfig();
		
		// For golden enchants
		t6Enchants.put("Bane of Arthropods VI", EnumChatFormatting.GOLD + "Bane of Arthropods VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Critical VI", EnumChatFormatting.GOLD + "Critical VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Dragon Hunter V", EnumChatFormatting.GOLD + "Dragon Hunter V" + EnumChatFormatting.BLUE);
		t6Enchants.put("Ender Slayer VI", EnumChatFormatting.GOLD + "Ender Slayer VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Experience IV", EnumChatFormatting.GOLD + "Experience IV" + EnumChatFormatting.BLUE);
		t6Enchants.put("Giant Killer VI", EnumChatFormatting.GOLD + "Giant Killer VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Life Steal IV", EnumChatFormatting.GOLD + "Life Steal IV" + EnumChatFormatting.BLUE);
		t6Enchants.put("Looting IV", EnumChatFormatting.GOLD + "Looting IV" + EnumChatFormatting.BLUE);
		t6Enchants.put("Luck VI", EnumChatFormatting.GOLD + "Luck VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Scavenger IV", EnumChatFormatting.GOLD + "Scavenger IV" + EnumChatFormatting.BLUE);
		t6Enchants.put("Scavenger V", EnumChatFormatting.GOLD + "Scavenger V" + EnumChatFormatting.BLUE);
		t6Enchants.put("Sharpness VI", EnumChatFormatting.GOLD + "Sharpness VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Smite VII", EnumChatFormatting.GOLD + "Smite VII" + EnumChatFormatting.BLUE);
		t6Enchants.put("Smite VI", EnumChatFormatting.GOLD + "Smite VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Vampirism VI", EnumChatFormatting.GOLD + "Vampirism VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Power VI", EnumChatFormatting.GOLD + "Power VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Growth VI", EnumChatFormatting.GOLD + "Growth VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Protection VI", EnumChatFormatting.GOLD + "Protection VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Efficiency VI", EnumChatFormatting.GOLD + "Efficiency VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Angler VI", EnumChatFormatting.GOLD + "Angler VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Caster VI", EnumChatFormatting.GOLD + "Caster VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Frail VI", EnumChatFormatting.GOLD + "Frail VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Luck of the Sea VI", EnumChatFormatting.GOLD + "Luck of the Sea VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Lure VI", EnumChatFormatting.GOLD + "Lure VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Magnet VI", EnumChatFormatting.GOLD + "Magnet VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Spiked Hook VI", EnumChatFormatting.GOLD + "Spiked Hook VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Feather Falling X", EnumChatFormatting.GOLD + "Feather Falling X" + EnumChatFormatting.BLUE);
		t6Enchants.put("Infinite Quiver X", EnumChatFormatting.GOLD + "Infinite Quiver X" + EnumChatFormatting.BLUE);
		
		String patternString = "(" + String.join("|", t6Enchants.keySet()) + ")";
		pattern = Pattern.compile(patternString);
		
		keyBindings[0] = new KeyBinding("Open Maddox Menu", Keyboard.KEY_M, "Danker's Skyblock Mod");
		
		for (int i = 0; i < keyBindings.length; i++) {
			ClientRegistry.registerKeyBinding(keyBindings[i]);
		}
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
    	ClientCommandHandler.instance.registerCommand(new PetsCommand());
    	ClientCommandHandler.instance.registerCommand(new BankCommand());
    	ClientCommandHandler.instance.registerCommand(new ArmourCommand());
    	ClientCommandHandler.instance.registerCommand(new ImportFishingCommand());
    	ClientCommandHandler.instance.registerCommand(new ResetLootCommand());
    	ClientCommandHandler.instance.registerCommand(new ScaleCommand());
    	ClientCommandHandler.instance.registerCommand(new ChatMaddoxCommand());
    	ClientCommandHandler.instance.registerCommand(new SkyblockPlayersCommand());
    }
    
    // Update checker
    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent event) {
    	if (!updateChecked) {
			updateChecked = true;
			
    		// MULTI THREAD DRIFTING
    		new Thread(() -> {
    			APIHandler ah = new APIHandler();
    			EntityPlayer player = Minecraft.getMinecraft().thePlayer;	
    			
    			System.err.println("Checking for updates...");
    			JsonObject latestRelease = ah.getResponse("https://api.github.com/repos/bowser0000/SkyblockMod/releases/latest");
    			
    			String latestTag = latestRelease.get("tag_name").getAsString();
    			DefaultArtifactVersion currentVersion = new DefaultArtifactVersion(VERSION);
    			DefaultArtifactVersion latestVersion = new DefaultArtifactVersion(latestTag.substring(1));
    			
    			if (currentVersion.compareTo(latestVersion) < 0) {
    				String releaseURL = latestRelease.get("html_url").getAsString();
    				
    				ChatComponentText update = new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "  [UPDATE]  ");
    				update.setChatStyle(update.getChatStyle().setChatClickEvent(new ClickEvent(Action.OPEN_URL, releaseURL)));
    					
    				try {
						Thread.sleep(2000);
					} catch (InterruptedException ex) {
						System.err.println(ex);
					}
    				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + MODID + " is outdated. Please update to " + latestTag + ".\n").appendSibling(update));
    			}
    		}).start();
    	}
    }
    
    // It randomly broke, so I had to make it the highest priority
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
    	final ToggleCommand tc = new ToggleCommand();
    	String message = event.message.getUnformattedText();
    	
    	if (event.type == 2) return;
    	if (!Utils.inSkyblock) return;
    	
    	// Replace chat messages with Maddox command
        List<IChatComponent> chatSiblings = event.message.getSiblings();
        for (IChatComponent sibling : chatSiblings) {
        	if (sibling.getChatStyle().getChatClickEvent() == null) {
        		sibling.setChatStyle(sibling.getChatStyle().setChatClickEvent(new ClickEvent(Action.RUN_COMMAND, "/dmodopenmaddoxmenu")));
        	}
        }
    	
    	if (message.contains(":")) return;
    	
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
		if (message.contains("VERY RARE DROP!  (Enchanted Book)") || message.contains("CRAZY RARE DROP!  (Enchanted Book)")) {
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
			lc.wolfSvensSession++;
			if (lc.wolfBosses != -1) {
				lc.wolfBosses++;
			}
			if (lc.wolfBossesSession != -1) {
				lc.wolfBossesSession++;
			}
			cf.writeIntConfig("wolf", "svens", lc.wolfSvens);
			cf.writeIntConfig("wolf", "bossRNG", lc.wolfBosses);
		}
		if (message.contains("RARE DROP! (Hamster Wheel)")) {
			lc.wolfWheelsDrops++;
			lc.wolfWheelsDropsSession++;
			cf.writeIntConfig("wolf", "wheelDrops", lc.wolfWheelsDrops);
		}
		// Removing the unicode here *should* fix rune drops not counting
		if (message.contains("VERY RARE DROP!  (") && message.contains(" Spirit Rune I)")) {
			lc.wolfSpirits++;
			lc.wolfSpiritsSession++;
			cf.writeIntConfig("wolf", "spirit", lc.wolfSpirits);
		}
		if (message.contains("CRAZY RARE DROP!  (Red Claw Egg)")) {
			wolfRNG = true;
			lc.wolfEggs++;
			lc.wolfEggsSession++;
			cf.writeIntConfig("wolf", "egg", lc.wolfEggs);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_RED + "RED CLAW EGG!", 3);
		}
		if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Couture Rune I)")) {
			wolfRNG = true;
			lc.wolfCoutures++;
			lc.wolfCouturesSession++;
			cf.writeIntConfig("wolf", "couture", lc.wolfCoutures);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "COUTURE RUNE!", 3);
		}
		// How did Skyblock devs even manage to make this item Rename Me
		if (message.contains("CRAZY RARE DROP!  (Grizzly Bait)") || message.contains("CRAZY RARE DROP! (Rename Me)")) {
			wolfRNG = true;
			lc.wolfBaits++;
			lc.wolfBaitsSession++;
			cf.writeIntConfig("wolf", "bait", lc.wolfBaits);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.AQUA + "GRIZZLY BAIT!", 3);
		}
		if (message.contains("CRAZY RARE DROP!  (Overflux Capacitor)")) {
			wolfRNG = true;
			lc.wolfFluxes++;
			lc.wolfFluxesSession++;
			cf.writeIntConfig("wolf", "flux", lc.wolfFluxes);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "OVERFLUX CAPACITOR!", 5);
		}

		// Spider
		if (message.contains("Talk to Maddox to claim your Spider Slayer XP!")) {
			lc.spiderTarantulas++;
			lc.spiderTarantulasSession++;
			if (lc.spiderBosses != -1) {
				lc.spiderBosses++;
			}
			if (lc.spiderBossesSession != -1) {
				lc.spiderBossesSession++;
			}
			cf.writeIntConfig("spider", "tarantulas", lc.spiderTarantulas);
			cf.writeIntConfig("spider", "bossRNG", lc.spiderBosses);
		}
		if (message.contains("RARE DROP! (Toxic Arrow Poison)")) {
			lc.spiderTAPDrops++;
			lc.spiderTAPDropsSession++;
			cf.writeIntConfig("spider", "tapDrops", lc.spiderTAPDrops);
		}
		if (message.contains("VERY RARE DROP!  (") && message.contains(" Bite Rune I)")) {
			lc.spiderBites++;
			lc.spiderBitesSession++;
			cf.writeIntConfig("spider", "bite", lc.spiderBites);
		}
		if (message.contains("VERY RARE DROP!  (Spider Catalyst)")) {
			lc.spiderCatalysts++;
			lc.spiderCatalystsSession++;
			cf.writeIntConfig("spider", "catalyst", lc.spiderCatalysts);
		}
		if (message.contains("CRAZY RARE DROP!  (Fly Swatter)")) {
			spiderRNG = true;
			lc.spiderSwatters++;
			lc.spiderSwattersSession++;
			cf.writeIntConfig("spider", "swatter", lc.spiderSwatters);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.LIGHT_PURPLE + "FLY SWATTER!", 3);
		}
		if (message.contains("CRAZY RARE DROP!  (Tarantula Talisman")) {
			spiderRNG = true;
			lc.spiderTalismans++;
			lc.spiderTalismansSession++;
			cf.writeIntConfig("spider", "talisman", lc.spiderTalismans);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "TARANTULA TALISMAN!", 3);
		}
		if (message.contains("CRAZY RARE DROP!  (Digested Mosquito)")) {
			spiderRNG = true;
			lc.spiderMosquitos++;
			lc.spiderMosquitosSession++;
			cf.writeIntConfig("spider", "mosquito", lc.spiderMosquitos);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "DIGESTED MOSQUITO!", 5);
		}

		// Zombie
		if (message.contains("Talk to Maddox to claim your Zombie Slayer XP!")) {
			lc.zombieRevs++;
			lc.zombieRevsSession++;
			if (lc.zombieBosses != -1) {
				lc.zombieBosses++;
			}
			if (lc.zombieBossesSession != 1) {
				lc.zombieBossesSession++;
			}
			cf.writeIntConfig("zombie", "revs", lc.zombieRevs);
			cf.writeIntConfig("zombie", "bossRNG", lc.zombieBosses);
		}
		if (message.contains("RARE DROP! (Foul Flesh)")) {
			lc.zombieFoulFleshDrops++;
			lc.zombieFoulFleshDropsSession++;
			cf.writeIntConfig("zombie", "foulFleshDrops", lc.zombieFoulFleshDrops);
		}
		if (message.contains("VERY RARE DROP!  (Revenant Catalyst)")) {
			lc.zombieRevCatas++;
			lc.zombieRevCatasSession++;
			cf.writeIntConfig("zombie", "revCatalyst", lc.zombieRevCatas);
		}
		if (message.contains("VERY RARE DROP!  (") && message.contains(" Pestilence Rune I)")) {
			lc.zombiePestilences++;
			lc.zombiePestilencesSession++;
			cf.writeIntConfig("zombie", "pestilence", lc.zombiePestilences);
		}
		if (message.contains("VERY RARE DROP!  (Undead Catalyst)")) {
			lc.zombieUndeadCatas++;
			lc.zombieUndeadCatasSession++;
			cf.writeIntConfig("zombie", "undeadCatalyst", lc.zombieUndeadCatas);
		}
		if (message.contains("CRAZY RARE DROP!  (Beheaded Horror)")) {
			zombieRNG = true;
			lc.zombieBeheadeds++;
			lc.zombieBeheadedsSession++;
			cf.writeIntConfig("zombie", "beheaded", lc.zombieBeheadeds);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "BEHEADED HORROR!", 3);
		}
		if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Snake Rune I)")) {
			zombieRNG = true;
			lc.zombieSnakes++;
			lc.zombieSnakesSession++;
			cf.writeIntConfig("zombie", "snake", lc.zombieSnakes);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_GREEN + "SNAKE RUNE!", 3);
		}
		if (message.contains("CRAZY RARE DROP!  (Scythe Blade)")) {
			zombieRNG = true;
			lc.zombieScythes++;
			lc.zombieScythesSession++;
			cf.writeIntConfig("zombie", "scythe", lc.zombieScythes);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "SCYTHE BLADE!", 5);
		}

		if (wolfRNG) {
			lc.wolfTime = System.currentTimeMillis() / 1000;
			lc.wolfBosses = 0;
			lc.wolfTimeSession = System.currentTimeMillis() / 1000;
			lc.wolfBossesSession = 0;
			cf.writeDoubleConfig("wolf", "timeRNG", lc.wolfTime);
			cf.writeIntConfig("wolf", "bossRNG", 0);
		}
		if (spiderRNG) {
			lc.spiderTime = System.currentTimeMillis() / 1000;
			lc.spiderBosses = 0;
			lc.spiderTimeSession = System.currentTimeMillis() / 1000;
			lc.spiderBossesSession = 0;
			cf.writeDoubleConfig("spider", "timeRNG", lc.spiderTime);
			cf.writeIntConfig("spider", "bossRNG", 0);
		}
		if (zombieRNG) {
			lc.zombieTime = System.currentTimeMillis() / 1000;
			lc.zombieBosses = 0;
			lc.zombieTimeSession = System.currentTimeMillis() / 1000;
			lc.zombieBossesSession = 0;
			cf.writeDoubleConfig("zombie", "timeRNG", lc.zombieTime);
			cf.writeIntConfig("zombie", "bossRNG", 0);
		}
		
		// Fishing
		if (message.contains("GOOD CATCH!")) {
			lc.goodCatches++;
			lc.goodCatchesSession++;
			cf.writeIntConfig("fishing", "goodCatch", lc.goodCatches);
		}
		if (message.contains("GREAT CATCH!")) {
			lc.greatCatches++;
			lc.greatCatchesSession++;
			cf.writeIntConfig("fishing", "greatCatch", lc.greatCatches);
		}
		if (message.contains("You caught a lowly Squid")) {
			lc.squids++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.squidsSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "squid", lc.squids);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("From the depths of the waters, you've reeled in a Sea Walker")) {
			lc.seaWalkers++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.seaWalkersSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "seaWalker", lc.seaWalkers);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("Pitch darkness reveals you've caught a")) {
			lc.nightSquids++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.nightSquidsSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "nightSquid", lc.nightSquids);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("You've stumbled upon a patrolling Sea Guardian")) {
			lc.seaGuardians++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.seaGuardiansSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "seaGuardian", lc.seaGuardians);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("It looks like you've disrupted the Sea Witch's brewing session. Watch out, she's furious")) {
			lc.seaWitches++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.seaWitchesSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "seaWitch", lc.seaWitches);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("From the depths of the waters, you've reeled in a Sea Archer")) {
			lc.seaArchers++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.seaArchersSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "seaArcher", lc.seaArchers);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("The Monster of the Deep emerges from the dark depths")) {
			lc.monsterOfTheDeeps++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.monsterOfTheDeepsSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "monsterOfDeep", lc.monsterOfTheDeeps);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("You have found a Catfish, don't let it steal your catches")) {
			lc.catfishes++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.catfishesSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "catfish", lc.catfishes);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("Is this even a fish? It's the Carrot King")) {
			lc.carrotKings++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.carrotKingsSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "carrotKing", lc.carrotKings);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("Gross! A Sea Leech")) {
			lc.seaLeeches++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.seaLeechesSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "seaLeech", lc.seaLeeches);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("You've discovered a Guardian Defender of the sea")) {
			lc.guardianDefenders++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.guardianDefendersSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "guardianDefender", lc.guardianDefenders);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("You have awoken the Deep Sea Protector, prepare for a battle")) {
			lc.deepSeaProtectors++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.deepSeaProtectorsSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "deepSeaProtector", lc.deepSeaProtectors);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("The Water Hydra has come to test your strength")) {
			lc.hydras++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.hydrasSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "hydra", lc.hydras);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			increaseEmpSC();
		}
		if (message.contains("The Sea Emperor arises from the depths")) {
			lc.seaEmperors++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.empTime = System.currentTimeMillis() / 1000;
			lc.empSCs = 0;
			lc.seaEmperorsSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			lc.empTimeSession = System.currentTimeMillis() / 1000;
			lc.empSCsSession = 0;
			cf.writeIntConfig("fishing", "seaEmperor", lc.seaEmperors);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
			cf.writeDoubleConfig("fishing", "empTime", lc.empTime);
			cf.writeIntConfig("fishing", "empSC", lc.empSCs);
		}
		// Fishing Winter
		if (message.contains("Frozen Steve fell into the pond long ago")) {
			lc.frozenSteves++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.frozenStevesSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "frozenSteve", lc.frozenSteves);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
		}
		if (message.contains("It's a snowman! He looks harmless")) {
			lc.frostyTheSnowmans++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.frostyTheSnowmansSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "snowman", lc.frostyTheSnowmans);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
		}
		if (message.contains("stole Jerry's Gifts...get them back")) {
			lc.grinches++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.grinchesSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "grinch", lc.grinches);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
		}
		if (message.contains("What is this creature")) {
			lc.yetis++;
			lc.seaCreatures++;
			lc.fishingMilestone++;
			lc.yetisSession++;
			lc.seaCreaturesSession++;
			lc.fishingMilestoneSession++;
			cf.writeIntConfig("fishing", "yeti", lc.yetis);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
		}
		
		// Catacombs Dungeons
		if (message.contains("    RARE REWARD! Recombobulator 3000")) {
			lc.recombobulators++;
			lc.recombobulatorsSession++;
			cf.writeIntConfig("catacombs", "recombobulator", lc.recombobulators);
		}
		if (message.contains("    RARE REWARD! Fuming Potato Book")) {
			
		}
		// F1
		if (message.contains("    RARE REWARD! Bonzo's Staff")) {
			lc.bonzoStaffs++;
			lc.bonzoStaffsSession++;
			cf.writeIntConfig("catacombs", "bonzoStaff", lc.bonzoStaffs);
		}
		// F2
		if (message.contains("    RARE REWARD! Scarf's Studies")) {
			lc.scarfStudies++;
			lc.scarfStudiesSession++;
			cf.writeIntConfig("catacombs", "scarfStudies", lc.scarfStudies);
		}
		// F3
		if (message.contains("    RARE REWARD! Adaptive Helmet")) {
			lc.adaptiveHelms++;
			lc.adaptiveHelmsSession++;
			cf.writeIntConfig("catacombs", "adaptiveHelm", lc.adaptiveHelms);
		}
		if (message.contains("    RARE REWARD! Adaptive Chestplate")) {
			lc.adaptiveChests++;
			lc.adaptiveChestsSession++;
			cf.writeIntConfig("catacombs", "adaptiveChest", lc.adaptiveChests);
		}
		if (message.contains("    RARE REWARD! Adaptive Leggings")) {
			lc.adaptiveLegs++;
			lc.adaptiveLegsSession++;
			cf.writeIntConfig("catacombs", "adaptiveLegging", lc.adaptiveLegs);
		}
		if (message.contains("    RARE REWARD! Adaptive Boots")) {
			lc.adaptiveBoots++;
			lc.adaptiveBootsSession++;
			cf.writeIntConfig("catacombs", "adaptiveBoot", lc.adaptiveBoots);
		}
		if (message.contains("    RARE REWARD! Adaptive Blade")) {
			lc.adaptiveSwords++;
			lc.adaptiveSwordsSession++;
			cf.writeIntConfig("catacombs", "adaptiveSword", lc.adaptiveSwords);
		}
		// F4
		if (message.contains("    Spirit Wing")) {
			lc.spiritWings++;
			lc.spiritWingsSession++;
			cf.writeIntConfig("catacombs", "spiritWing", lc.spiritWings);
		}
		// TODO
		// Fix strings for Spirit Bone, Spirit Boots
		if (message.contains("    ") && message.contains("Spirit Bone")) {
			lc.spiritBones++;
			lc.spiritBonesSession++;
			cf.writeIntConfig("catacombs", "spiritBone", lc.spiritBones);
		}
		if (message.contains("    ") && message.contains("Spirit Boots")) {
			lc.spiritBoots++;
			lc.spiritBootsSession++;
			cf.writeIntConfig("catacombs", "spiritBoot", lc.spiritBoots);
		}
		if (message.contains("    [Lvl 1] Spirit")) {
			String formattedMessage = event.message.getFormattedText();
			// Unicode colour code messes up here, just gonna remove the symbols
			if (formattedMessage.contains("5Spirit")) {
				lc.epicSpiritPets++;
				lc.epicSpiritPetsSession++;
				cf.writeIntConfig("catacombs", "spiritPetEpic", lc.epicSpiritPets);
			} else if (formattedMessage.contains("6Spirit")) {
				lc.legSpiritPets++;
				lc.legSpiritPetsSession++;
				cf.writeIntConfig("catacombs", "spiritPetLeg", lc.legSpiritPets);
			} 
		}
		if (message.contains("    Spirit Sword")) {
			lc.spiritSwords++;
			lc.spiritSwordsSession++;
			cf.writeIntConfig("catacombs", "spiritSword", lc.spiritSwords);
		}
		if (message.contains("    Spirit Bow")) {
			lc.spiritBows++;
			lc.spiritBowsSession++;
			cf.writeIntConfig("catacombs", "spiritBow", lc.spiritBows);
		}
		
		// Chat Maddox
		if (message.contains("[OPEN MENU]")) {
			List<IChatComponent> listOfSiblings = event.message.getSiblings();
			for (IChatComponent sibling : listOfSiblings) {
				if (sibling.getUnformattedText().contains("[OPEN MENU]")) {
					lastMaddoxCommand = sibling.getChatStyle().getChatClickEvent().getValue();
				}
			}
			if (tc.chatMaddoxToggled) Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Click anywhere in chat to open Maddox"));
		}
		
		// Spirit Bear alerts
		if (tc.spiritBearAlerts && message.contains("The Spirit Bear has appeared!")) {
			Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "SPIRIT BEAR", 2);
		}
		
		// Spirit Sceptre
		if (!tc.sceptreMessages && message.contains("Your Bat Staff hit ")) {
			event.setCanceled(true);
		}
    }
    
    @SubscribeEvent
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
    	if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
    	final ToggleCommand tc = new ToggleCommand();
    	final MoveCommand moc = new MoveCommand();
    	final DisplayCommand ds = new DisplayCommand();
    	
    	if (tc.coordsToggled) {
    		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    		
        	double xDir = (player.rotationYaw % 360 + 360) % 360;
        	if (xDir > 180) xDir -= 360;
        	xDir = (double) Math.round(xDir * 10d) / 10d;
        	double yDir = (double) Math.round(player.rotationPitch * 10d) / 10d;
        	
        	String coordText = (int) player.posX + " / " + (int) player.posY + " / " + (int) player.posZ + " (" + xDir + " / " + yDir + ")";
        	new TextRenderer(Minecraft.getMinecraft(), coordText, moc.coordsXY[0], moc.coordsXY[1], ScaleCommand.coordsScale);
    	}
    	
    	if (!ds.display.equals("off")) {
    		final LootCommand lc = new LootCommand();
    		String dropsText = "";
    		String countText = "";
    		String timeBetween = "Never";
    		String bossesBetween = "Never";
    		String drop20;
    		double timeNow = System.currentTimeMillis() / 1000;
    		NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
    		
    		if (ds.display.equals("wolf")) {
    			if (lc.wolfTime == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = lc.getTimeBetween(lc.wolfTime, timeNow);
    			}
    			if (lc.wolfBosses == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = nf.format(lc.wolfBosses);
    			}
    			if (tc.slayerCountTotal) {
    				drop20 = nf.format(lc.wolfWheels);
    			} else {
    				drop20 = nf.format(lc.wolfWheelsDrops) + " times";
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
							EnumChatFormatting.AQUA + "Bosses Since RNG:";
    			countText = EnumChatFormatting.GOLD + nf.format(lc.wolfSvens) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.wolfTeeth) + "\n" +
							EnumChatFormatting.BLUE + drop20 + "\n" +
							EnumChatFormatting.AQUA + lc.wolfSpirits + "\n" + 
							EnumChatFormatting.WHITE + lc.wolfBooks + "\n" +
							EnumChatFormatting.DARK_RED + lc.wolfEggs + "\n" +
							EnumChatFormatting.GOLD + lc.wolfCoutures + "\n" +
							EnumChatFormatting.AQUA + lc.wolfBaits + "\n" +
							EnumChatFormatting.DARK_PURPLE + lc.wolfFluxes + "\n" +
							EnumChatFormatting.AQUA + timeBetween + "\n" +
							EnumChatFormatting.AQUA + bossesBetween;
    		} else if (ds.display.equals("wolf_session")) {
    			if (lc.wolfTimeSession == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = lc.getTimeBetween(lc.wolfTimeSession, timeNow);
    			}
    			if (lc.wolfBossesSession == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = nf.format(lc.wolfBossesSession);
    			}
    			if (tc.slayerCountTotal) {
    				drop20 = nf.format(lc.wolfWheelsSession);
    			} else {
    				drop20 = nf.format(lc.wolfWheelsDropsSession) + " times";
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
							EnumChatFormatting.AQUA + "Bosses Since RNG:";
    			countText = EnumChatFormatting.GOLD + nf.format(lc.wolfSvensSession) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.wolfTeethSession) + "\n" +
							EnumChatFormatting.BLUE + drop20 + "\n" +
							EnumChatFormatting.AQUA + lc.wolfSpiritsSession + "\n" + 
							EnumChatFormatting.WHITE + lc.wolfBooksSession + "\n" +
							EnumChatFormatting.DARK_RED + lc.wolfEggsSession + "\n" +
							EnumChatFormatting.GOLD + lc.wolfCouturesSession + "\n" +
							EnumChatFormatting.AQUA + lc.wolfBaitsSession + "\n" +
							EnumChatFormatting.DARK_PURPLE + lc.wolfFluxesSession + "\n" +
							EnumChatFormatting.AQUA + timeBetween + "\n" +
						EnumChatFormatting.AQUA + bossesBetween;
    		} else if (ds.display.equals("spider")) {
    			if (lc.spiderTime == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = lc.getTimeBetween(lc.spiderTime, timeNow);
    			}
    			if (lc.spiderBosses == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = nf.format(lc.spiderBosses);
    			}
    			if (tc.slayerCountTotal) {
    				drop20 = nf.format(lc.spiderTAP);
    			} else {
    				drop20 = nf.format(lc.spiderTAPDrops) + " times";
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
							EnumChatFormatting.AQUA + "Bosses Since RNG:";
    			countText = EnumChatFormatting.GOLD + nf.format(lc.spiderTarantulas) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.spiderWebs) + "\n" +
							EnumChatFormatting.DARK_GREEN + drop20 + "\n" +
							EnumChatFormatting.DARK_GRAY + lc.spiderBites + "\n" + 
							EnumChatFormatting.WHITE + lc.spiderBooks + "\n" +
							EnumChatFormatting.AQUA + lc.spiderCatalysts + "\n" +
							EnumChatFormatting.DARK_PURPLE + lc.spiderTalismans + "\n" +
							EnumChatFormatting.LIGHT_PURPLE + lc.spiderSwatters + "\n" +
							EnumChatFormatting.GOLD + lc.spiderMosquitos + "\n" +
							EnumChatFormatting.AQUA + timeBetween + "\n" +
							EnumChatFormatting.AQUA + bossesBetween;
    		} else if (ds.display.equals("spider_session")) {
    			if (lc.spiderTimeSession == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = lc.getTimeBetween(lc.spiderTimeSession, timeNow);
    			}
    			if (lc.spiderBossesSession == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = nf.format(lc.spiderBossesSession);
    			}
    			if (tc.slayerCountTotal) {
    				drop20 = nf.format(lc.spiderTAPSession);
    			} else {
    				drop20 = nf.format(lc.spiderTAPDropsSession) + " times";
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
							EnumChatFormatting.AQUA + "Bosses Since RNG:";
    			countText = EnumChatFormatting.GOLD + nf.format(lc.spiderTarantulasSession) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.spiderWebsSession) + "\n" +
							EnumChatFormatting.DARK_GREEN + drop20 + "\n" +
							EnumChatFormatting.DARK_GRAY + lc.spiderBitesSession + "\n" + 
							EnumChatFormatting.WHITE + lc.spiderBooksSession + "\n" +
							EnumChatFormatting.AQUA + lc.spiderCatalystsSession + "\n" +
							EnumChatFormatting.DARK_PURPLE + lc.spiderTalismansSession + "\n" +
							EnumChatFormatting.LIGHT_PURPLE + lc.spiderSwattersSession + "\n" +
							EnumChatFormatting.GOLD + lc.spiderMosquitosSession + "\n" +
							EnumChatFormatting.AQUA + timeBetween + "\n" +
							EnumChatFormatting.AQUA + bossesBetween;
    		} else if (ds.display.equals("zombie")) {
    			if (lc.zombieTime == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = lc.getTimeBetween(lc.zombieTime, timeNow);
    			}
    			if (lc.zombieBosses == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = nf.format(lc.zombieBosses);
    			}
    			if (tc.slayerCountTotal) {
    				drop20 = nf.format(lc.zombieFoulFlesh);
    			} else {
    				drop20 = nf.format(lc.zombieFoulFleshDrops) + " times";
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
							EnumChatFormatting.AQUA + "Bosses Since RNG:";
    			countText = EnumChatFormatting.GOLD + nf.format(lc.zombieRevs) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.zombieRevFlesh) + "\n" +
							EnumChatFormatting.BLUE + drop20 + "\n" +
							EnumChatFormatting.DARK_GREEN + lc.zombiePestilences + "\n" + 
							EnumChatFormatting.WHITE + lc.zombieBooks + "\n" +
							EnumChatFormatting.AQUA + lc.zombieUndeadCatas + "\n" +
							EnumChatFormatting.DARK_PURPLE + lc.zombieBeheadeds + "\n" +
							EnumChatFormatting.RED + lc.zombieRevCatas + "\n" +
							EnumChatFormatting.DARK_GREEN + lc.zombieSnakes + "\n" +
							EnumChatFormatting.GOLD + lc.zombieScythes + "\n" +
							EnumChatFormatting.AQUA + timeBetween + "\n" +
							EnumChatFormatting.AQUA + bossesBetween;
    		} else if (ds.display.equals("zombie_session")) {
    			if (lc.zombieTimeSession == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = lc.getTimeBetween(lc.zombieTimeSession, timeNow);
    			}
    			if (lc.zombieBossesSession == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = nf.format(lc.zombieBossesSession);
    			}
    			if (tc.slayerCountTotal) {
    				drop20 = nf.format(lc.zombieFoulFleshSession);
    			} else {
    				drop20 = nf.format(lc.zombieFoulFleshDropsSession) + " times";
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
							EnumChatFormatting.AQUA + "Bosses Since RNG:";
    			countText = EnumChatFormatting.GOLD + nf.format(lc.zombieRevsSession) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.zombieRevFleshSession) + "\n" +
							EnumChatFormatting.BLUE + drop20 + "\n" +
							EnumChatFormatting.DARK_GREEN + lc.zombiePestilencesSession + "\n" + 
							EnumChatFormatting.WHITE + lc.zombieBooksSession + "\n" +
							EnumChatFormatting.AQUA + lc.zombieUndeadCatasSession + "\n" +
							EnumChatFormatting.DARK_PURPLE + lc.zombieBeheadedsSession + "\n" +
							EnumChatFormatting.RED + lc.zombieRevCatasSession + "\n" +
							EnumChatFormatting.DARK_GREEN + lc.zombieSnakesSession + "\n" +
							EnumChatFormatting.GOLD + lc.zombieScythes + "\n" +
							EnumChatFormatting.AQUA + timeBetween + "\n" +
							EnumChatFormatting.AQUA + bossesBetween;
    		} else if (ds.display.equals("fishing")) {
    			if (lc.empTime == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = lc.getTimeBetween(lc.empTime, timeNow);
    			}
    			if (lc.empSCs == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = nf.format(lc.empSCs);
    			}
    			
    			dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
    						EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
	    					EnumChatFormatting.GOLD + "Good Catches:\n" +
							EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
							EnumChatFormatting.GRAY + "Squids:\n" +
							EnumChatFormatting.GREEN + "Sea Walkers:\n" +
							EnumChatFormatting.DARK_GRAY + "Night Squids:\n" +
							EnumChatFormatting.DARK_AQUA + "Sea Guardians:\n" +
							EnumChatFormatting.BLUE + "Sea Witches:\n" +
							EnumChatFormatting.GREEN + "Sea Archers:";
    			countText = EnumChatFormatting.AQUA + nf.format(lc.seaCreatures) + "\n" +
							EnumChatFormatting.AQUA + nf.format(lc.fishingMilestone) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.goodCatches) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.greatCatches) + "\n" +
							EnumChatFormatting.GRAY + nf.format(lc.squids) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.seaWalkers) + "\n" +
							EnumChatFormatting.DARK_GRAY + nf.format(lc.nightSquids) + "\n" +
							EnumChatFormatting.DARK_AQUA + nf.format(lc.seaGuardians) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.seaWitches) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.seaArchers);
    			// Seperated to save vertical space
    			String dropsTextTwo = EnumChatFormatting.GREEN + "Monster of Deeps:\n" +
    								  EnumChatFormatting.YELLOW + "Catfishes:\n" +
									  EnumChatFormatting.GOLD + "Carrot Kings:\n" +
									  EnumChatFormatting.GRAY + "Sea Leeches:\n" +
									  EnumChatFormatting.DARK_PURPLE + "Guardian Defenders:\n" +
									  EnumChatFormatting.DARK_PURPLE + "Deep Sea Protectors:\n" +
									  EnumChatFormatting.GOLD + "Hydras:\n" +
									  EnumChatFormatting.GOLD + "Sea Emperors:\n" +
									  EnumChatFormatting.AQUA + "Time Since Emp:\n" +
									  EnumChatFormatting.AQUA + "Creatures Since Emp:";
    			String countTextTwo = EnumChatFormatting.GREEN + nf.format(lc.monsterOfTheDeeps) + "\n" +
    								  EnumChatFormatting.YELLOW + nf.format(lc.catfishes) + "\n" +
									  EnumChatFormatting.GOLD + nf.format(lc.carrotKings) + "\n" +
									  EnumChatFormatting.GRAY + nf.format(lc.seaLeeches) + "\n" +
									  EnumChatFormatting.DARK_PURPLE + nf.format(lc.guardianDefenders) + "\n" +
									  EnumChatFormatting.DARK_PURPLE + nf.format(lc.deepSeaProtectors) + "\n" +
									  EnumChatFormatting.GOLD + nf.format(lc.hydras) + "\n" +
									  EnumChatFormatting.GOLD + nf.format(lc.seaEmperors) + "\n" +
									  EnumChatFormatting.AQUA + timeBetween + "\n" +
									  EnumChatFormatting.AQUA + bossesBetween;
    			
    			if (tc.splitFishing) {
    				new TextRenderer(Minecraft.getMinecraft(), dropsTextTwo, (int) (moc.displayXY[0] + (145 * ScaleCommand.displayScale)), moc.displayXY[1], ScaleCommand.displayScale);
        			new TextRenderer(Minecraft.getMinecraft(), countTextTwo, (int) (moc.displayXY[0] + (255 * ScaleCommand.displayScale)), moc.displayXY[1], ScaleCommand.displayScale);
    			} else {
    				dropsText += "\n" + dropsTextTwo;
    				countText += "\n" + countTextTwo;
    			}
    		} else if (ds.display.equals("fishing_session")) {
    			if (lc.empTimeSession == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = lc.getTimeBetween(lc.empTimeSession, timeNow);
    			}
    			if (lc.empSCsSession == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = nf.format(lc.empSCsSession);
    			}
    			
    			dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
    						EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
	    					EnumChatFormatting.GOLD + "Good Catches:\n" +
							EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
							EnumChatFormatting.GRAY + "Squids:\n" +
							EnumChatFormatting.GREEN + "Sea Walkers:\n" +
							EnumChatFormatting.DARK_GRAY + "Night Squids:\n" +
							EnumChatFormatting.DARK_AQUA + "Sea Guardians:\n" +
							EnumChatFormatting.BLUE + "Sea Witches:\n" +
							EnumChatFormatting.GREEN + "Sea Archers:";
    			countText = EnumChatFormatting.AQUA + nf.format(lc.seaCreaturesSession) + "\n" +
							EnumChatFormatting.AQUA + nf.format(lc.fishingMilestoneSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.goodCatchesSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.greatCatchesSession) + "\n" +
							EnumChatFormatting.GRAY + nf.format(lc.squidsSession) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.seaWalkersSession) + "\n" +
							EnumChatFormatting.DARK_GRAY + nf.format(lc.nightSquidsSession) + "\n" +
							EnumChatFormatting.DARK_AQUA + nf.format(lc.seaGuardiansSession) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.seaWitchesSession) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.seaArchersSession);
    			// Seperated to save vertical space
    			String dropsTextTwo = EnumChatFormatting.GREEN + "Monster of Deeps:\n" +
    								  EnumChatFormatting.YELLOW + "Catfishes:\n" +
									  EnumChatFormatting.GOLD + "Carrot Kings:\n" +
									  EnumChatFormatting.GRAY + "Sea Leeches:\n" +
									  EnumChatFormatting.DARK_PURPLE + "Guardian Defenders:\n" +
									  EnumChatFormatting.DARK_PURPLE + "Deep Sea Protectors:\n" +
									  EnumChatFormatting.GOLD + "Hydras:\n" +
									  EnumChatFormatting.GOLD + "Sea Emperors:\n" +
									  EnumChatFormatting.AQUA + "Time Since Emp:\n" +
									  EnumChatFormatting.AQUA + "Creatures Since Emp:";
    			String countTextTwo = EnumChatFormatting.GREEN + nf.format(lc.monsterOfTheDeepsSession) + "\n" +
    								  EnumChatFormatting.YELLOW + nf.format(lc.catfishesSession) + "\n" +
									  EnumChatFormatting.GOLD + nf.format(lc.carrotKingsSession) + "\n" +
									  EnumChatFormatting.GRAY + nf.format(lc.seaLeechesSession) + "\n" +
									  EnumChatFormatting.DARK_PURPLE + nf.format(lc.guardianDefendersSession) + "\n" +
									  EnumChatFormatting.DARK_PURPLE + nf.format(lc.deepSeaProtectorsSession) + "\n" +
									  EnumChatFormatting.GOLD + nf.format(lc.hydrasSession) + "\n" +
									  EnumChatFormatting.GOLD + nf.format(lc.seaEmperorsSession) + "\n" +
									  EnumChatFormatting.AQUA + timeBetween + "\n" +
									  EnumChatFormatting.AQUA + bossesBetween;
    			
    			if (tc.splitFishing) {
    				new TextRenderer(Minecraft.getMinecraft(), dropsTextTwo, (int) (moc.displayXY[0] + (145 * ScaleCommand.displayScale)), moc.displayXY[1], ScaleCommand.displayScale);
        			new TextRenderer(Minecraft.getMinecraft(), countTextTwo, (int) (moc.displayXY[0] + (255 * ScaleCommand.displayScale)), moc.displayXY[1], ScaleCommand.displayScale);
    			} else {
    				dropsText += "\n" + dropsTextTwo;
    				countText += "\n" + countTextTwo;
    			}
    		} else if (ds.display.equals("fishing_winter")) {
    			dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
    						EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
    						EnumChatFormatting.GOLD + "Good Catches:\n" +
    						EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
	    					EnumChatFormatting.AQUA + "Frozen Steves:\n" +
							EnumChatFormatting.WHITE + "Snowmans:\n" +
							EnumChatFormatting.DARK_GREEN + "Grinches:\n" +
							EnumChatFormatting.GOLD + "Yetis:";
    			countText = EnumChatFormatting.AQUA + nf.format(lc.seaCreatures) + "\n" +
							EnumChatFormatting.AQUA + nf.format(lc.fishingMilestone) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.goodCatches) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.greatCatches) + "\n" +
	    					EnumChatFormatting.AQUA + nf.format(lc.frozenSteves) + "\n" +
							EnumChatFormatting.WHITE + nf.format(lc.frostyTheSnowmans) + "\n" +
							EnumChatFormatting.DARK_GREEN + nf.format(lc.grinches) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.yetis);
    		} else if (ds.display.equals("fishing_winter_session")) {
    			dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
    						EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
							EnumChatFormatting.GOLD + "Good Catches:\n" +
							EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
	    					EnumChatFormatting.AQUA + "Frozen Steves:\n" +
							EnumChatFormatting.WHITE + "Snowmans:\n" +
							EnumChatFormatting.DARK_GREEN + "Grinches:\n" +
							EnumChatFormatting.GOLD + "Yetis:";
				countText = EnumChatFormatting.AQUA + nf.format(lc.seaCreaturesSession) + "\n" +
							EnumChatFormatting.AQUA + nf.format(lc.fishingMilestoneSession) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.goodCatchesSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.greatCatchesSession) + "\n" +
	    					EnumChatFormatting.AQUA + nf.format(lc.frozenStevesSession) + "\n" +
							EnumChatFormatting.WHITE + nf.format(lc.frostyTheSnowmansSession) + "\n" +
							EnumChatFormatting.DARK_GREEN + nf.format(lc.grinchesSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.yetisSession);
    		} else if (ds.display.equals("catacombs_floor_one")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
    						EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
    						EnumChatFormatting.BLUE + "Bonzo's Staffs:";
    			countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulators) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooks) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.bonzoStaffs);
    		} else if (ds.display.equals("catacombs_floor_one_session")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.BLUE + "Bonzo's Staffs:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulatorsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooksSession) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.bonzoStaffsSession);
    		} else if (ds.display.equals("catacombs_floor_two")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.BLUE + "Scarf's Studies:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulators) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooks) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.scarfStudies);
    		} else if (ds.display.equals("catacombs_floor_two_session")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.BLUE + "Scarf's Studies:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulatorsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooksSession) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.scarfStudiesSession);
    		} else if (ds.display.equals("catacombs_floor_three")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Helmets:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Chestplates:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Leggings:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Boots:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Blades:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulators) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooks) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveHelms) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveChests) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveLegs) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveBoots) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveSwords);	
    		} else if (ds.display.equals("catacombs_floor_three_session")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Helmets:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Chestplates:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Leggings:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Boots:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Blades:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulatorsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooksSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveHelmsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveChestsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveLegsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveBootsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveSwordsSession);
    		} else if (ds.display.equals("catacombs_floor_four")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Wings:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Bones:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Boots:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Swords:\n" +
							EnumChatFormatting.GOLD + "Spirit Bows:\n" +
							EnumChatFormatting.DARK_PURPLE + "Epic Spirit Pets:\n" +
							EnumChatFormatting.GOLD + "Leg Spirit Pets:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulators) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooks) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritWings) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritBones) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritBoots) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritSwords) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.spiritBows) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.epicSpiritPets) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.legSpiritPets);
    		} else if (ds.display.equals("catacombs_floor_four_session")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Wings:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Bones:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Boots:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Swords:\n" +
							EnumChatFormatting.GOLD + "Spirit Bows:\n" +
							EnumChatFormatting.DARK_PURPLE + "Epic Spirit Pets:\n" +
							EnumChatFormatting.GOLD + "Leg Spirit Pets:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulatorsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooksSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritWingsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritBonesSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritBootsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritSwordsSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.spiritBowsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.epicSpiritPetsSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.legSpiritPetsSession);
    		} else {
    			ConfigHandler cf = new ConfigHandler();
    			
    			System.out.println("Display was an unknown value, turning off.");
    			ds.display = "off";
    			cf.writeStringConfig("misc", "display", "off");
    		}
    		new TextRenderer(Minecraft.getMinecraft(), dropsText, moc.displayXY[0], moc.displayXY[1], ScaleCommand.displayScale);
    		new TextRenderer(Minecraft.getMinecraft(), countText, (int) (moc.displayXY[0] + (110 * ScaleCommand.displayScale)), moc.displayXY[1], ScaleCommand.displayScale);
    	}
    	
    	if (showTitle) {
    		Utils.drawTitle(titleText);
    	}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onSound(final PlaySoundEvent event) {
    	if (!Utils.inSkyblock) return;
    	if (event.name.equals("note.pling")) {
    		// Don't check twice within 3 seconds 
    		checkItemsNow = System.currentTimeMillis() / 1000;
    		if (checkItemsNow - itemsChecked < 3) return;
    		
    		final ScoreboardHandler sc = new ScoreboardHandler();
    		List<String> scoreboard = sc.getSidebarLines();
    		
    		for (String line : scoreboard) {
    			String cleanedLine = sc.cleanSB(line);
    			// If Hypixel lags and scoreboard doesn't update
    			if (cleanedLine.contains("Boss slain!") || cleanedLine.contains("Slay the boss!")) {
    				final LootCommand lc = new LootCommand();
    				final ConfigHandler cf = new ConfigHandler();
    				
    				int itemTeeth = Utils.getItems("Wolf Tooth");
        			int itemWheels = Utils.getItems("Hamster Wheel");
        			int itemWebs = Utils.getItems("Tarantula Web");
        			int itemTAP = Utils.getItems("Toxic Arrow Poison");
        			int itemRev = Utils.getItems("Revenant Flesh");
        			int itemFoul = Utils.getItems("Foul Flesh");
        			
        			// If no items, are detected, allow check again. Should fix items not being found
        			if (itemTeeth + itemWheels + itemWebs + itemTAP + itemRev + itemFoul > 0) {
        				itemsChecked = System.currentTimeMillis() / 1000;
            			lc.wolfTeeth += itemTeeth;
            			lc.wolfWheels += itemWheels;
            			lc.spiderWebs += itemWebs;
            			lc.spiderTAP += itemTAP;
            			lc.zombieRevFlesh += itemRev;
            			lc.zombieFoulFlesh += itemFoul;
            			lc.wolfTeethSession += itemTeeth;
            			lc.wolfWheelsSession += itemWheels;
            			lc.spiderWebsSession += itemWebs;
            			lc.spiderTAPSession += itemTAP;
            			lc.zombieRevFleshSession += itemRev;
            			lc.zombieFoulFleshSession += itemFoul;
            			
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
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTooltip(ItemTooltipEvent event) {
    	if (!Utils.inSkyblock) return;
    	final ToggleCommand tc = new ToggleCommand();
    	
    	if (event.toolTip == null) return;
    	if (tc.goldenToggled) {
    		for (int i = 0; i < event.toolTip.size(); i++) {
    			event.toolTip.set(i, Utils.returnGoldenEnchants(event.toolTip.get(i)));
    		}
    	}
    }
    
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
    	// Check if player is in Skyblock every second
    	tickAmount++;
    	if (tickAmount % 20 == 0) {
    		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
    		if (player != null) {
        		Utils.checkForSkyblock();
    		}
    		tickAmount = 1;
    	}
    	
    	if (titleTimer >= 0) {
    		if (titleTimer == 0) {
    			showTitle = false;
    		}
    		titleTimer--;
    	}
    }
    
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
    	if (!Utils.inSkyblock || Minecraft.getMinecraft().thePlayer != event.entityPlayer) return;
    	ItemStack item = event.entityPlayer.getHeldItem();
    	if (item == null) return;
    	
    	if (ToggleCommand.aotdToggled && item.getDisplayName().contains("Aspect of the Dragons") && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
    		event.setCanceled(true);
    	}
    }
    
    @SubscribeEvent
    public void onKey(KeyInputEvent event) {
    	if (!Utils.inSkyblock) return;
    	if (keyBindings[0].isPressed()) {
    		Minecraft.getMinecraft().thePlayer.sendChatMessage(lastMaddoxCommand);
    	}
    }
    
    @SubscribeEvent
    public void onGuiRender(GuiScreenEvent.BackgroundDrawnEvent event) {
    	if (!Utils.inSkyblock) return;
    	if (ToggleCommand.petColoursToggled && event.gui instanceof GuiChest) {
    		GuiChest inventory = (GuiChest) event.gui;
    		List<Slot> invSlots = inventory.inventorySlots.inventorySlots;
    		for (Slot slot : invSlots) {
    			ItemStack item = slot.getStack();
    			if (item == null) continue;
    			if (item.getDisplayName().contains("[Lvl ")) {
    				int colour;
    				int petLevel = Integer.parseInt(item.getDisplayName().substring(item.getDisplayName().indexOf(" ") + 1, item.getDisplayName().indexOf("]")));
    				if (petLevel == 100) {
    					colour = 0xBFF2D249; // Gold
    				} else if (petLevel >= 90) {
    					colour = 0xBFE06C65; // Red
    				} else if (petLevel >= 80) {
    					colour = 0xBF5F91C0; // Blue
    				} else if (petLevel >= 70) {
    					colour = 0xBF84CA85; // Green
    				} else if (petLevel >= 60) {
    					colour = 0xBFF6C100; // Goldish
    				} else if (petLevel >= 50) {
    					colour = 0xBFA575D2; // Purple
    				} else if (petLevel >= 40) {
    					colour = 0xBFFFA252; // Orange
    				} else if (petLevel >= 30) {
    					colour = 0xBF845EF7; // Bluish purple
    				} else if (petLevel >= 20) {
    					colour = 0xBFD6336C; // Magenta
    				} else if (petLevel >= 10) {
    					colour = 0xBF58C9A3; // Teal
    				} else {
    					colour = 0xBFFEBBD1; // Pink
    				}
    				Utils.drawOnSlot(inventory.inventorySlots.inventorySlots.size(), slot.xDisplayPosition, slot.yDisplayPosition, colour);
    			}
    		}
    	}
    }
    
    public void increaseEmpSC() {
    	LootCommand lc = new LootCommand();
    	ConfigHandler cf = new ConfigHandler();
    	
    	if (lc.empSCs != -1) {
    		lc.empSCs++;
    	}
    	if (lc.empSCsSession != -1) {
    		lc.empSCsSession++;
    	}
    	
    	cf.writeIntConfig("fishing", "empSC", lc.empSCs);
    }
    
}
