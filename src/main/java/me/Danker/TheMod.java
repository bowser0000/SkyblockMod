package me.Danker;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.google.gson.JsonObject;

import me.Danker.commands.ArmourCommand;
import me.Danker.commands.BankCommand;
import me.Danker.commands.BlockSlayerCommand;
import me.Danker.commands.ChatMaddoxCommand;
import me.Danker.commands.DHelpCommand;
import me.Danker.commands.DankerGuiCommand;
import me.Danker.commands.DisplayCommand;
import me.Danker.commands.DungeonsCommand;
import me.Danker.commands.GetkeyCommand;
import me.Danker.commands.GuildOfCommand;
import me.Danker.commands.ImportFishingCommand;
import me.Danker.commands.LobbySkillsCommand;
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
import me.Danker.gui.DankerGui;
import me.Danker.gui.DisplayGui;
import me.Danker.gui.EditLocationsGui;
import me.Danker.gui.OnlySlayerGui;
import me.Danker.gui.PuzzleSolversGui;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.PacketHandler;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;

@Mod(modid = TheMod.MODID, version = TheMod.VERSION, clientSideOnly = true)
public class TheMod
{
    public static final String MODID = "Danker's Skyblock Mod";
    public static final String VERSION = "1.8.1";
    
    static double checkItemsNow = 0;
    static double itemsChecked = 0;
    public static Map<String, String> t6Enchants = new HashMap<String, String>();
    public static Pattern pattern = Pattern.compile("");
    static boolean updateChecked = false;
    public static int titleTimer = -1;
    public static boolean showTitle = false;
    public static String titleText = "";
    public static int SKILL_TIME;
    public static int skillTimer = -1;
    public static boolean showSkill = false;
    public static String skillText = "";
    static int tickAmount = 1;
    public static String lastMaddoxCommand = "/cb placeholdervalue";
    static KeyBinding[] keyBindings = new KeyBinding[1];
    static int lastMouse = -1;
    static boolean usingLabymod = false;
    public static String guiToOpen = null;
    static String[] riddleSolutions = {"The reward is not in my chest!", "At least one of them is lying, and the reward is not in", 
    								   "My chest doesn't have the reward. We are all telling the truth", "My chest has the reward and I'm telling the truth",
    								   "The reward isn't in any of our chests", "Both of them are telling the truth."};
    static Map<String, String> triviaSolutions = new HashMap<String, String>();
	static Entity highestBlaze = null;
	static Entity lowestBlaze = null;
	// Among Us colours
	static int[] creeperLineColours = {0x50EF39, 0xC51111, 0x132ED1, 0x117F2D, 0xED54BA, 0xEF7D0D, 0xF5F557, 0xD6E0F0, 0x6B2FBB, 0x39FEDC};
	static boolean drawCreeperLines = false;
	static Vec3 creeperLocation = new Vec3(0, 0, 0);
	static List<Vec3[]> creeperLines = new ArrayList<Vec3[]>();
	static boolean foundLivid = false;
	static Entity livid = null;
    
    static double dungeonStartTime = 0;
    static double bloodOpenTime = 0;
    static double watcherClearTime = 0;
    static double bossClearTime = 0;
    static int witherDoors = 0;
    static int dungeonDeaths = 0;
    static int puzzleFails = 0;
    
    public static String MAIN_COLOUR;
    public static String SECONDARY_COLOUR;
    public static String ERROR_COLOUR;
    public static String DELIMITER_COLOUR;
    public static String TYPE_COLOUR;
    public static String VALUE_COLOUR;
    public static String SKILL_AVERAGE_COLOUR;
    public static String ANSWER_COLOUR;
    public static String SKILL_50_COLOUR;
    public static String COORDS_COLOUR;
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new PacketHandler());
		
		final ConfigHandler cf = new ConfigHandler();
		cf.reloadConfig();
		
		// For golden enchants
		t6Enchants.put("9Angler VI", "6Angler VI");
		t6Enchants.put("9Bane of Arthropods VI", "6Bane of Arthropods VI");
		t6Enchants.put("9Caster VI", "6Caster VI");
		t6Enchants.put("9Compact X", "6Compact X");
		t6Enchants.put("9Critical VI", "6Critical VI");
		t6Enchants.put("9Dragon Hunter V", "6Dragon Hunter V");
		t6Enchants.put("9Efficiency VI", "6Efficiency VI");
		t6Enchants.put("9Ender Slayer VI", "6Ender Slayer VI");
		t6Enchants.put("9Experience IV", "6Experience IV");
		t6Enchants.put("9Expertise X", "6Expertise X");
		t6Enchants.put("9Feather Falling X", "6Feather Falling X");
		t6Enchants.put("9Frail VI", "6Frail VI");
		t6Enchants.put("9Giant Killer VI", "6Giant Killer VI");
		t6Enchants.put("9Growth VI", "6Growth VI");
		t6Enchants.put("9Infinite Quiver X", "6Infinite Quiver X");
		t6Enchants.put("9Lethality VI", "6Lethality VI"); 
		t6Enchants.put("9Life Steal IV", "6Life Steal IV");
		t6Enchants.put("9Looting IV", "6Looting IV");
		t6Enchants.put("9Luck VI", "6Luck VI");
		t6Enchants.put("9Luck of the Sea VI", "6Luck of the Sea VI");
		t6Enchants.put("9Lure VI", "6Lure VI");
		t6Enchants.put("9Magnet VI", "6Magnet VI");
		t6Enchants.put("9Overload V", "6Overload V");
		t6Enchants.put("9Power VI", "6Power VI");
		t6Enchants.put("9Protection VI", "6Protection VI");
		t6Enchants.put("9Scavenger IV", "6Scavenger IV");
		t6Enchants.put("9Scavenger V", "6Scavenger V");
		t6Enchants.put("9Sharpness VI", "6Sharpness VI");
		t6Enchants.put("9Smite VI", "6Smite VI");
		t6Enchants.put("9Spiked Hook VI", "6Spiked Hook VI");
		t6Enchants.put("9Thunderlord VI", "6Thunderlord VI");
		t6Enchants.put("9Vampirism VI", "6Vampirism VI");
		
		triviaSolutions.put("What is the status of The Watcher?", "Stalker");
		triviaSolutions.put("What is the status of Bonzo?", "New Necromancer");
		triviaSolutions.put("What is the status of Scarf?", "Apprentice Necromancer");
		triviaSolutions.put("What is the status of The Professor?", "Professor");
		triviaSolutions.put("What is the status of Thorn?", "Shaman Necromancer");
		triviaSolutions.put("What is the status of Livid?", "Master Necromancer");
		triviaSolutions.put("What is the status of Sadan?", "Necromancer Lord");
		triviaSolutions.put("What is the status of Maxor?", "Young Wither");
		triviaSolutions.put("What is the status of Goldor?", "Wither Soldier");
		triviaSolutions.put("What is the status of Storm?", "Elementalist");
		triviaSolutions.put("What is the status of Necron?", "Wither Lord");
		triviaSolutions.put("How many total Fairy Souls are there?", "209 Fairy Souls");
		triviaSolutions.put("How many Fairy Souls are there in Spider's Den?", "17 Fairy Souls");
		triviaSolutions.put("How many Fairy Souls are there in The End?", "12 Fairy Souls");
		triviaSolutions.put("How many Fairy Souls are there in The Barn?", "7 Fairy Souls");
		triviaSolutions.put("How many Fairy Souls are there in Mushroom Desert?", "8 Fairy Souls");
		triviaSolutions.put("How many Fairy Souls are there in Blazing Fortress?", "19 Fairy Souls");
		triviaSolutions.put("How many Fairy Souls are there in The Park?", "11 Fairy Souls");
		triviaSolutions.put("How many Fairy Souls are there in Jerry's Workshop?", "5 Fairy Souls");
		triviaSolutions.put("How many Fairy Souls are there in Hub?", "79 Fairy Souls");
		triviaSolutions.put("How many Fairy Souls are there in The Hub?", "79 Fairy Souls");
		triviaSolutions.put("How many Fairy Souls are there in Deep Caverns?", "21 Fairy Souls");
		triviaSolutions.put("How many Fairy Souls are there in Gold Mine?", "12 Fairy Souls");
		triviaSolutions.put("How many Fairy Souls are there in Dungeon Hub?", "7 Fairy Souls");
		triviaSolutions.put("Which brother is on the Spider's Den?", "Rick");
		triviaSolutions.put("What is the name of Rick's brother?", "Pat");
		triviaSolutions.put("What is the name of the Painter in the Hub?", "Marco");
		triviaSolutions.put("What is the name of the person that upgrades pets?", "Kat");
		triviaSolutions.put("What is the name of the lady of the Nether?", "Elle");
		triviaSolutions.put("Which villager in the Village gives you a Rogue Sword?", "Jamie");
		triviaSolutions.put("How many unique minions are there?", "52 Minions");
		triviaSolutions.put("Which of these enemies does not spawn in the Spider's Den?", "Zombie Spider OR Cave Spider OR Broodfather OR Wither Skeleton OR Dashing Spooder");
		triviaSolutions.put("Which of these monsters only spawns at night?", "Zombie Villager OR Ghast");
		triviaSolutions.put("Which of these is not a dragon in The End?", "Zoomer Dragon OR Weak Dragon OR Stonk Dragon OR Holy Dragon OR Boomer Dragon");
		
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
    	ClientCommandHandler.instance.registerCommand(new BlockSlayerCommand());
    	ClientCommandHandler.instance.registerCommand(new DungeonsCommand());
    	ClientCommandHandler.instance.registerCommand(new LobbySkillsCommand());
    	ClientCommandHandler.instance.registerCommand(new DankerGuiCommand());	
    }
    
    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
    	usingLabymod = Loader.isModLoaded("labymod");
    	System.out.println("LabyMod detection: " + usingLabymod);
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
    				player.addChatMessage(new ChatComponentText(ERROR_COLOUR + MODID + " is outdated. Please update to " + latestTag + ".\n").appendSibling(update));
    			}
    		}).start();
    	}
    }
    
    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
    	foundLivid = false;
    	livid = null;
    }
    
    // It randomly broke, so I had to make it the highest priority
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
    	final ToggleCommand tc = new ToggleCommand();
    	String message = event.message.getUnformattedText();
    	
    	if (!Utils.inSkyblock) return;
    	
    	// Action Bar
    	if (event.type == 2) {
    		String[] actionBarSections = event.message.getUnformattedText().split(" {3,}");
    		for (String section : actionBarSections) {
    			if (tc.skill50DisplayToggled) {
    				if (section.contains("+") && section.contains("/") && section.contains("(")) {
    					if (section.contains("Runecrafting")) return;
    					
    					String xpGained = section.substring(section.indexOf("+"), section.indexOf("(") - 1);
    					double currentXp = Double.parseDouble(section.substring(section.indexOf("(") + 1, section.indexOf("/")).replaceAll(",", ""));
    					int limit;
    					int totalXp;
    					if (section.contains("Farming")) {
    						limit = 60;
    						totalXp = 111672425;
    					} else {
    						limit = 50;
    						totalXp = 55172425;
    					}
    					int previousXp = Utils.getPastXpEarned(Integer.parseInt(section.substring(section.indexOf("/") + 1, section.indexOf(")")).replaceAll(",", "")), limit);
    					double percentage = (double) Math.floor(((currentXp + previousXp) / totalXp) * 10000D) / 100D;
    					
    					NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
    					skillTimer = SKILL_TIME;
    					showSkill = true;
    					skillText = SKILL_50_COLOUR + xpGained + " (" + nf.format(currentXp + previousXp) + "/" + nf.format(totalXp) + ") " + percentage + "%";
    				}
    			}
    		}
    		return;
    	}
    	
    	// Replace chat messages with Maddox command
        List<IChatComponent> chatSiblings = event.message.getSiblings();
        if (ToggleCommand.trueChatMaddoxEnabled) {
            for (IChatComponent sibling : chatSiblings) {
            	if (sibling.getChatStyle().getChatClickEvent() == null) {
            		sibling.setChatStyle(sibling.getChatStyle().setChatClickEvent(new ClickEvent(Action.RUN_COMMAND, "/dmodopenmaddoxmenu")));
            	}
            }
        }
    	
        // Dungeon chat spoken by an NPC, containing :
        if (ToggleCommand.threeManToggled && Utils.inDungeons && message.contains("[NPC]")) {
        	for (String solution : riddleSolutions) {
        		if (message.contains(solution)) {
        			String npcName = message.substring(message.indexOf("]") + 2, message.indexOf(":"));
        			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ANSWER_COLOUR + "" + EnumChatFormatting.BOLD + StringUtils.stripControlCodes(npcName) + MAIN_COLOUR + " has the blessing."));
        			break;
        		}
        	}
        }
        
        if (message.contains("[BOSS] The Watcher: You have proven yourself. You may pass.")) {
        	watcherClearTime = System.currentTimeMillis() / 1000;
        }
		if (message.contains("PUZZLE FAIL! ") || message.contains("chose the wrong answer! I shall never forget this moment")) {
			puzzleFails++;
		}
        
    	if (message.contains(":")) return;
    	
        if (ToggleCommand.oruoToggled && Utils.inDungeons) {
        	for (String question : triviaSolutions.keySet()) {
        		if (message.contains(question)) {
        			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(MAIN_COLOUR + "Answer: " + ANSWER_COLOUR + EnumChatFormatting.BOLD + triviaSolutions.get(question)));
        			break;
        		}
        	}
        }
    	
		if (tc.gpartyToggled) {
			if (message.contains(" has invited all members of ")) {
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
		
		if (tc.golemAlertToggled) {
			if (message.contains("The ground begins to shake as an Endstone Protector rises from below!")) {
				Utils.createTitle(EnumChatFormatting.RED + "GOLEM SPAWNING!", 3);
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
		} else if (message.contains("RARE DROP! (Hamster Wheel)")) {
			lc.wolfWheelsDrops++;
			lc.wolfWheelsDropsSession++;
			cf.writeIntConfig("wolf", "wheelDrops", lc.wolfWheelsDrops);
		} else if (message.contains("VERY RARE DROP!  (") && message.contains(" Spirit Rune I)")) { // Removing the unicode here *should* fix rune drops not counting
			lc.wolfSpirits++;
			lc.wolfSpiritsSession++;
			cf.writeIntConfig("wolf", "spirit", lc.wolfSpirits);
		} else if (message.contains("CRAZY RARE DROP!  (Red Claw Egg)")) {
			wolfRNG = true;
			lc.wolfEggs++;
			lc.wolfEggsSession++;
			cf.writeIntConfig("wolf", "egg", lc.wolfEggs);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_RED + "RED CLAW EGG!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Couture Rune I)")) {
			wolfRNG = true;
			lc.wolfCoutures++;
			lc.wolfCouturesSession++;
			cf.writeIntConfig("wolf", "couture", lc.wolfCoutures);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "COUTURE RUNE!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (Grizzly Bait)") || message.contains("CRAZY RARE DROP! (Rename Me)")) { // How did Skyblock devs even manage to make this item Rename Me
			wolfRNG = true;
			lc.wolfBaits++;
			lc.wolfBaitsSession++;
			cf.writeIntConfig("wolf", "bait", lc.wolfBaits);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.AQUA + "GRIZZLY BAIT!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (Overflux Capacitor)")) {
			wolfRNG = true;
			lc.wolfFluxes++;
			lc.wolfFluxesSession++;
			cf.writeIntConfig("wolf", "flux", lc.wolfFluxes);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "OVERFLUX CAPACITOR!", 5);
		} else if (message.contains("Talk to Maddox to claim your Spider Slayer XP!")) { // Spider
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
		} else if (message.contains("RARE DROP! (Toxic Arrow Poison)")) {
			lc.spiderTAPDrops++;
			lc.spiderTAPDropsSession++;
			cf.writeIntConfig("spider", "tapDrops", lc.spiderTAPDrops);
		} else if (message.contains("VERY RARE DROP!  (") && message.contains(" Bite Rune I)")) {
			lc.spiderBites++;
			lc.spiderBitesSession++;
			cf.writeIntConfig("spider", "bite", lc.spiderBites);
		} else if (message.contains("VERY RARE DROP!  (Spider Catalyst)")) {
			lc.spiderCatalysts++;
			lc.spiderCatalystsSession++;
			cf.writeIntConfig("spider", "catalyst", lc.spiderCatalysts);
		} else if (message.contains("CRAZY RARE DROP!  (Fly Swatter)")) {
			spiderRNG = true;
			lc.spiderSwatters++;
			lc.spiderSwattersSession++;
			cf.writeIntConfig("spider", "swatter", lc.spiderSwatters);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.LIGHT_PURPLE + "FLY SWATTER!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (Tarantula Talisman")) {
			spiderRNG = true;
			lc.spiderTalismans++;
			lc.spiderTalismansSession++;
			cf.writeIntConfig("spider", "talisman", lc.spiderTalismans);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "TARANTULA TALISMAN!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (Digested Mosquito)")) {
			spiderRNG = true;
			lc.spiderMosquitos++;
			lc.spiderMosquitosSession++;
			cf.writeIntConfig("spider", "mosquito", lc.spiderMosquitos);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "DIGESTED MOSQUITO!", 5);
		} else if (message.contains("Talk to Maddox to claim your Zombie Slayer XP!")) { // Zombie
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
		} else if (message.contains("RARE DROP! (Foul Flesh)")) {
			lc.zombieFoulFleshDrops++;
			lc.zombieFoulFleshDropsSession++;
			cf.writeIntConfig("zombie", "foulFleshDrops", lc.zombieFoulFleshDrops);
		} else if (message.contains("VERY RARE DROP!  (Revenant Catalyst)")) {
			lc.zombieRevCatas++;
			lc.zombieRevCatasSession++;
			cf.writeIntConfig("zombie", "revCatalyst", lc.zombieRevCatas);
		} else if (message.contains("VERY RARE DROP!  (") && message.contains(" Pestilence Rune I)")) {
			lc.zombiePestilences++;
			lc.zombiePestilencesSession++;
			cf.writeIntConfig("zombie", "pestilence", lc.zombiePestilences);
		} else if (message.contains("VERY RARE DROP!  (Undead Catalyst)")) {
			lc.zombieUndeadCatas++;
			lc.zombieUndeadCatasSession++;
			cf.writeIntConfig("zombie", "undeadCatalyst", lc.zombieUndeadCatas);
		} else if (message.contains("CRAZY RARE DROP!  (Beheaded Horror)")) {
			zombieRNG = true;
			lc.zombieBeheadeds++;
			lc.zombieBeheadedsSession++;
			cf.writeIntConfig("zombie", "beheaded", lc.zombieBeheadeds);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "BEHEADED HORROR!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Snake Rune I)")) {
			zombieRNG = true;
			lc.zombieSnakes++;
			lc.zombieSnakesSession++;
			cf.writeIntConfig("zombie", "snake", lc.zombieSnakes);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_GREEN + "SNAKE RUNE!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (Scythe Blade)")) {
			zombieRNG = true;
			lc.zombieScythes++;
			lc.zombieScythesSession++;
			cf.writeIntConfig("zombie", "scythe", lc.zombieScythes);
			if (tc.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "SCYTHE BLADE!", 5);
		} else if (message.contains("GOOD CATCH!")) { // Fishing
			lc.goodCatches++;
			lc.goodCatchesSession++;
			cf.writeIntConfig("fishing", "goodCatch", lc.goodCatches);
		} else if (message.contains("GREAT CATCH!")) {
			lc.greatCatches++;
			lc.greatCatchesSession++;
			cf.writeIntConfig("fishing", "greatCatch", lc.greatCatches);
		} else if (message.contains("A Squid appeared")) {
			lc.squids++;
			lc.squidsSession++;
			cf.writeIntConfig("fishing", "squid", lc.squids);
			increaseSeaCreatures();
		} else if (message.contains("You caught a Sea Walker")) {
			lc.seaWalkers++;
			lc.seaWalkersSession++;
			cf.writeIntConfig("fishing", "seaWalker", lc.seaWalkers);
			increaseSeaCreatures();
		} else if (message.contains("Pitch darkness reveals a Night Squid")) {
			lc.nightSquids++;
			lc.nightSquidsSession++;
			cf.writeIntConfig("fishing", "nightSquid", lc.nightSquids);
			increaseSeaCreatures();
		} else if (message.contains("You stumbled upon a Sea Guardian")) {
			lc.seaGuardians++;
			lc.seaGuardiansSession++;
			cf.writeIntConfig("fishing", "seaGuardian", lc.seaGuardians);
			increaseSeaCreatures();
		} else if (message.contains("It looks like you've disrupted the Sea Witch's brewing session. Watch out, she's furious")) {
			lc.seaWitches++;
			lc.seaWitchesSession++;
			cf.writeIntConfig("fishing", "seaWitch", lc.seaWitches);
			increaseSeaCreatures();
		} else if (message.contains("You reeled in a Sea Archer")) {
			lc.seaArchers++;
			lc.seaArchersSession++;
			cf.writeIntConfig("fishing", "seaArcher", lc.seaArchers);
			increaseSeaCreatures();
		} else if (message.contains("The Monster of the Deep has emerged")) {
			lc.monsterOfTheDeeps++;
			lc.monsterOfTheDeepsSession++;
			cf.writeIntConfig("fishing", "monsterOfDeep", lc.monsterOfTheDeeps);
			increaseSeaCreatures();
		} else if (message.contains("Huh? A Catfish")) {
			lc.catfishes++;
			lc.catfishesSession++;
			cf.writeIntConfig("fishing", "catfish", lc.catfishes);
			increaseSeaCreatures();
		} else if (message.contains("Is this even a fish? It's the Carrot King")) {
			lc.carrotKings++;
			lc.carrotKingsSession++;
			cf.writeIntConfig("fishing", "carrotKing", lc.carrotKings);
			increaseSeaCreatures();
		} else if (message.contains("Gross! A Sea Leech")) {
			lc.seaLeeches++;
			lc.seaLeechesSession++;
			cf.writeIntConfig("fishing", "seaLeech", lc.seaLeeches);
			increaseSeaCreatures();
		} else if (message.contains("You've discovered a Guardian Defender of the sea")) {
			lc.guardianDefenders++;
			lc.guardianDefendersSession++;
			cf.writeIntConfig("fishing", "guardianDefender", lc.guardianDefenders);
			increaseSeaCreatures();
		} else if (message.contains("You have awoken the Deep Sea Protector, prepare for a battle")) {
			lc.deepSeaProtectors++;
			lc.deepSeaProtectorsSession++;
			cf.writeIntConfig("fishing", "deepSeaProtector", lc.deepSeaProtectors);
			increaseSeaCreatures();
		} else if (message.contains("The Water Hydra has come to test your strength")) {
			lc.hydras++;
			lc.hydrasSession++;
			cf.writeIntConfig("fishing", "hydra", lc.hydras);
			increaseSeaCreatures();
		} else if (message.contains("The Sea Emperor arises from the depths")) {
			increaseSeaCreatures();
			
			lc.seaEmperors++;
			lc.empTime = System.currentTimeMillis() / 1000;
			lc.empSCs = 0;
			lc.seaEmperorsSession++;
			lc.empTimeSession = System.currentTimeMillis() / 1000;
			lc.empSCsSession = 0;
			cf.writeIntConfig("fishing", "seaEmperor", lc.seaEmperors);
			cf.writeDoubleConfig("fishing", "empTime", lc.empTime);
			cf.writeIntConfig("fishing", "empSC", lc.empSCs);
		} else if (message.contains("Frozen Steve fell into the pond long ago")) { // Fishing Winter
			lc.frozenSteves++;
			lc.frozenStevesSession++;
			cf.writeIntConfig("fishing", "frozenSteve", lc.frozenSteves);
			increaseSeaCreatures();
		} else if (message.contains("It's a snowman! He looks harmless")) {
			lc.frostyTheSnowmans++;
			lc.frostyTheSnowmansSession++;
			cf.writeIntConfig("fishing", "snowman", lc.frostyTheSnowmans);
			increaseSeaCreatures();
		} else if (message.contains("stole Jerry's Gifts...get them back")) {
			lc.grinches++;
			lc.grinchesSession++;
			cf.writeIntConfig("fishing", "grinch", lc.grinches);
			increaseSeaCreatures();
		} else if (message.contains("What is this creature")) {
			increaseSeaCreatures();
			
			lc.yetis++;
			lc.yetiTime = System.currentTimeMillis() / 1000;
			lc.yetiSCs = 0;
			lc.yetisSession++;
			lc.yetiTimeSession = System.currentTimeMillis() / 1000;
			lc.yetiSCsSession = 0;
			cf.writeIntConfig("fishing", "yeti", lc.yetis);
			cf.writeDoubleConfig("fishing", "yetiTime", lc.yetiTime);
			cf.writeIntConfig("fishing", "yetiSC", lc.yetiSCs);
		} else if (message.contains("A tiny fin emerges from the water, you've caught a Nurse Shark")) { // Fishing Festival
			lc.nurseSharks++;
			lc.nurseSharksSession++;
			cf.writeIntConfig("fishing", "nurseShark", lc.nurseSharks);
			increaseSeaCreatures();
		} else if (message.contains("You spot a fin as blue as the water it came from, it's a Blue Shark")) {
			lc.blueSharks++;
			lc.blueSharksSession++;
			cf.writeIntConfig("fishing", "blueShark", lc.blueSharks);
			increaseSeaCreatures();
		} else if (message.contains("A striped beast bounds from the depths, the wild Tiger Shark")) {
			lc.tigerSharks++;
			lc.tigerSharksSession++;
			cf.writeIntConfig("fishing", "tigerShark", lc.tigerSharks);
			increaseSeaCreatures();
		} else if (message.contains("Hide no longer, a Great White Shark has tracked your scent and thirsts for your blood")) {
			lc.greatWhiteSharks++;
			lc.greatWhiteSharksSession++;
			cf.writeIntConfig("fishing", "greatWhiteShark", lc.greatWhiteSharks);
			increaseSeaCreatures();
		} else if (message.contains("Phew! It's only a Scarecrow")) {
			lc.scarecrows++;
			lc.scarecrowsSession++;
			cf.writeIntConfig("fishing", "scarecrow", lc.scarecrows);
			increaseSeaCreatures();
		} else if (message.contains("You hear trotting from beneath the waves, you caught a Nightmare")) {
			lc.nightmares++;
			lc.nightmaresSession++;
			cf.writeIntConfig("fishing", "nightmare", lc.nightmares);
			increaseSeaCreatures();
		} else if (message.contains("It must be a full moon, a Werewolf appears")) {
			lc.werewolfs++;
			lc.werewolfsSession++;
			cf.writeIntConfig("fishing", "werewolf", lc.werewolfs);
			increaseSeaCreatures();
		} else if (message.contains("The spirit of a long lost Phantom Fisher has come to haunt you")) {
			lc.phantomFishers++;
			lc.phantomFishersSession++;
			cf.writeIntConfig("fishing", "phantomFisher", lc.phantomFishers);
			increaseSeaCreatures();
		} else if (message.contains("This can't be! The manifestation of death himself")) {
			lc.grimReapers++;
			lc.grimReapersSession++;
			cf.writeIntConfig("fishing", "grimReaper", lc.grimReapers);
			increaseSeaCreatures();
		} else if (message.contains("Dungeon starts in 1 second.")) { // Dungeons Stuff
		    dungeonStartTime = System.currentTimeMillis() / 1000 + 1;
		    bloodOpenTime = dungeonStartTime;
		    watcherClearTime = dungeonStartTime;
		    bossClearTime = dungeonStartTime;
		    witherDoors = 0;
		    dungeonDeaths = 0;
		    puzzleFails = 0;
		} else if (message.contains("The BLOOD DOOR has been opened!")) {
			bloodOpenTime = System.currentTimeMillis() / 1000;
		} else if (message.contains(" opened a WITHER door!")) {
			witherDoors++;
		} else if (message.contains(" and became a ghost.")) {
			dungeonDeaths++;
		} else if (message.contains(" Defeated ") && message.contains(" in ")) {
			bossClearTime = System.currentTimeMillis() / 1000;
		} else if (message.contains("EXTRA STATS ")) {
			List<String> scoreboard = ScoreboardHandler.getSidebarLines();
			int timeToAdd = 0;
			for (String s : scoreboard) {
				String sCleaned = ScoreboardHandler.cleanSB(s);
				if (sCleaned.contains("The Catacombs (")) {
					// Add time to floor
					if (sCleaned.contains("F1")) {
						lc.f1TimeSpent = Math.floor(lc.f1TimeSpent + timeToAdd);
						lc.f1TimeSpentSession = Math.floor(lc.f1TimeSpentSession + timeToAdd);
						cf.writeDoubleConfig("catacombs", "floorOneTime", lc.f1TimeSpent);
					} else if (sCleaned.contains("F2")) {
						lc.f2TimeSpent = Math.floor(lc.f2TimeSpent + timeToAdd);
						lc.f2TimeSpentSession = Math.floor(lc.f2TimeSpentSession + timeToAdd);
						cf.writeDoubleConfig("catacombs", "floorTwoTime", lc.f2TimeSpent);
					} else if (sCleaned.contains("F3")) {
						lc.f3TimeSpent = Math.floor(lc.f3TimeSpent + timeToAdd);
						lc.f3TimeSpentSession = Math.floor(lc.f3TimeSpentSession + timeToAdd);
						cf.writeDoubleConfig("catacombs", "floorThreeTime", lc.f3TimeSpent);
					} else if (sCleaned.contains("F4")) {
						lc.f4TimeSpent = Math.floor(lc.f4TimeSpent + timeToAdd);
						lc.f4TimeSpentSession = Math.floor(lc.f4TimeSpentSession + timeToAdd);
						cf.writeDoubleConfig("catacombs", "floorFourTime", lc.f4TimeSpent);
					} else if (sCleaned.contains("F5")) {
						lc.f5TimeSpent = Math.floor(lc.f5TimeSpent + timeToAdd);
						lc.f5TimeSpentSession = Math.floor(lc.f5TimeSpentSession + timeToAdd);
						cf.writeDoubleConfig("catacombs", "floorFiveTime", lc.f5TimeSpent);
					} else if (sCleaned.contains("F6")) {
						lc.f6TimeSpent = Math.floor(lc.f6TimeSpent + timeToAdd);
						lc.f6TimeSpentSession = Math.floor(lc.f6TimeSpentSession + timeToAdd);
						cf.writeDoubleConfig("catacombs", "floorSixTime", lc.f6TimeSpent);
					}
				} else if (sCleaned.contains("Time Elapsed:")) {
					// Get floor time
					String time = sCleaned.substring(sCleaned.indexOf(":") + 2);
					time = time.replaceAll("\\s", "");
					int minutes = Integer.parseInt(time.substring(0, time.indexOf("m")));
					int seconds = Integer.parseInt(time.substring(time.indexOf("m") + 1, time.indexOf("s")));
					timeToAdd = (minutes * 60) + seconds;
				}
			}
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
		
		// Dungeons Trackers
		if (message.contains("    ")) {
			if (message.contains("Recombobulator 3000")) {
				lc.recombobulators++;
				lc.recombobulatorsSession++;
				cf.writeIntConfig("catacombs", "recombobulator", lc.recombobulators);
			} else if (message.contains("Fuming Potato Book")) {
				lc.fumingPotatoBooks++;
				lc.fumingPotatoBooksSession++;
				cf.writeIntConfig("catacombs", "fumingBooks", lc.fumingPotatoBooks);
			} else if (message.contains("Bonzo's Staff")) { // F1
				lc.bonzoStaffs++;
				lc.bonzoStaffsSession++;
				cf.writeIntConfig("catacombs", "bonzoStaff", lc.bonzoStaffs);
			} else if (message.contains("Scarf's Studies")) { // F2
				lc.scarfStudies++;
				lc.scarfStudiesSession++;
				cf.writeIntConfig("catacombs", "scarfStudies", lc.scarfStudies);
			} else if (message.contains("Adaptive Helmet")) { // F3
				lc.adaptiveHelms++;
				lc.adaptiveHelmsSession++;
				cf.writeIntConfig("catacombs", "adaptiveHelm", lc.adaptiveHelms);
			} else if (message.contains("Adaptive Chestplate")) {
				lc.adaptiveChests++;
				lc.adaptiveChestsSession++;
				cf.writeIntConfig("catacombs", "adaptiveChest", lc.adaptiveChests);
			} else if (message.contains("Adaptive Leggings")) {
				lc.adaptiveLegs++;
				lc.adaptiveLegsSession++;
				cf.writeIntConfig("catacombs", "adaptiveLegging", lc.adaptiveLegs);
			} else if (message.contains("Adaptive Boots")) {
				lc.adaptiveBoots++;
				lc.adaptiveBootsSession++;
				cf.writeIntConfig("catacombs", "adaptiveBoot", lc.adaptiveBoots);
			} else if (message.contains("Adaptive Blade")) {
				lc.adaptiveSwords++;
				lc.adaptiveSwordsSession++;
				cf.writeIntConfig("catacombs", "adaptiveSword", lc.adaptiveSwords);
			} else if (message.contains("Spirit Wing")) { // F4
				lc.spiritWings++;
				lc.spiritWingsSession++;
				cf.writeIntConfig("catacombs", "spiritWing", lc.spiritWings);
			} else if (message.contains("Spirit Bone")) {
				lc.spiritBones++;
				lc.spiritBonesSession++;
				cf.writeIntConfig("catacombs", "spiritBone", lc.spiritBones);
			} else if (message.contains("Spirit Boots")) {
				lc.spiritBoots++;
				lc.spiritBootsSession++;
				cf.writeIntConfig("catacombs", "spiritBoot", lc.spiritBoots);
			} else if (message.contains("[Lvl 1] Spirit")) {
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
			} else if (message.contains("Spirit Sword")) {
				lc.spiritSwords++;
				lc.spiritSwordsSession++;
				cf.writeIntConfig("catacombs", "spiritSword", lc.spiritSwords);
			} else if (message.contains("Spirit Bow")) {
				lc.spiritBows++;
				lc.spiritBowsSession++;
				cf.writeIntConfig("catacombs", "spiritBow", lc.spiritBows);
			} else if (message.contains("Warped Stone")) { // F5
				lc.warpedStones++;
				lc.warpedStonesSession++;
				cf.writeIntConfig("catacombs", "warpedStone", lc.warpedStones);
			} else if (message.contains("Shadow Assassin Helmet")) {
				lc.shadowAssHelms++;
				lc.shadowAssHelmsSession++;
				cf.writeIntConfig("catacombs", "shadowAssassinHelm", lc.shadowAssHelms);
			} else if (message.contains("Shadow Assassin Chestplate")) {
				lc.shadowAssChests++;
				lc.shadowAssChestsSession++;
				cf.writeIntConfig("catacombs", "shadowAssassinChest", lc.shadowAssChests);
			} else if (message.contains("Shadow Assassin Leggings")) {
				lc.shadowAssLegs++;
				lc.shadowAssLegsSession++;
				cf.writeIntConfig("catacombs", "shadowAssassinLegging", lc.shadowAssLegs);
			} else if (message.contains("Shadow Assassin Boots")) {
				lc.shadowAssBoots++;
				lc.shadowAssBootsSession++;
				cf.writeIntConfig("catacombs", "shadowAssassinBoot", lc.shadowAssBoots);
			} else if (message.contains("Livid Dagger")) {
				lc.lividDaggers++;
				lc.lividDaggersSession++;
				cf.writeIntConfig("catacombs", "lividDagger", lc.lividDaggers);
			} else if (message.contains("Shadow Fury")) {
				lc.shadowFurys++;
				lc.shadowFurysSession++;
				cf.writeIntConfig("catacombs", "shadowFury", lc.shadowFurys);
			} else if (message.contains("Ancient Rose")) { // F6
				lc.ancientRoses++;
				lc.ancientRosesSession++;
				cf.writeIntConfig("catacombs", "ancientRose", lc.ancientRoses);
			} else if (message.contains("Precursor Eye")) {
				lc.precursorEyes++;
				lc.precursorEyesSession++;
				cf.writeIntConfig("catacombs", "precursorEye", lc.precursorEyes);
			} else if (message.contains("Giant's Sword")) {
				lc.giantsSwords++;
				lc.giantsSwordsSession++;
				cf.writeIntConfig("catacombs", "giantsSword", lc.giantsSwords);
			} else if (message.contains("Necromancer Lord Helmet")) {
				lc.necroLordHelms++;
				lc.necroLordHelmsSession++;
				cf.writeIntConfig("catacombs", "necroLordHelm", lc.necroLordHelms);
			} else if (message.contains("Necromancer Lord Chestplate")) {
				lc.necroLordChests++;
				lc.necroLordChestsSession++;
				cf.writeIntConfig("catacombs", "necroLordChest", lc.necroLordChests);
			} else if (message.contains("Necromancer Lord Leggings")) {
				lc.necroLordLegs++;
				lc.necroLordLegsSession++;
				cf.writeIntConfig("catacombs", "necroLordLegging", lc.necroLordLegs);
			} else if (message.contains("Necromancer Lord Boots")) {
				lc.necroLordBoots++;
				lc.necroLordBootsSession++;
				cf.writeIntConfig("catacombs", "necroLordBoot", lc.necroLordBoots);
			} else if (message.contains("Necromancer Sword")) {
				lc.necroSwords++;
				lc.necroSwordsSession++;
				cf.writeIntConfig("catacombs", "necroSword", lc.necroSwords);
			}
		}
		
		// Chat Maddox
		if (message.contains("[OPEN MENU]")) {
			List<IChatComponent> listOfSiblings = event.message.getSiblings();
			for (IChatComponent sibling : listOfSiblings) {
				if (sibling.getUnformattedText().contains("[OPEN MENU]")) {
					lastMaddoxCommand = sibling.getChatStyle().getChatClickEvent().getValue();
				}
			}
			if (tc.chatMaddoxToggled) Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(MAIN_COLOUR + "Click anywhere in chat to open Maddox"));
		}
		
		// Spirit Bear alerts
		if (tc.spiritBearAlerts && message.contains("The Spirit Bear has appeared!")) {
			Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "SPIRIT BEAR", 2);
		}
		
		// Spirit Sceptre
		if (!tc.sceptreMessages && message.contains("Your Spirit Sceptre hit ")) {
			event.setCanceled(true);
		}
		// Midas Staff
		if (!tc.midasStaffMessages && message.contains("Your Molten Wave hit ")) {
			event.setCanceled(true);
		}
    }
    
    @SubscribeEvent
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
    	if (usingLabymod) return;
    	if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE && event.type != RenderGameOverlayEvent.ElementType.JUMPBAR) return;
    	renderEverything();
    }
    
    // LabyMod Support
    @SubscribeEvent
    public void renderPlayerInfoLabyMod(final RenderGameOverlayEvent event) {
    	if (!usingLabymod) return;
    	if (event.type != null) return;
    	renderEverything();
    }
    
    public void renderEverything() {
    	final ToggleCommand tc = new ToggleCommand();
    	final MoveCommand moc = new MoveCommand();
    	final DisplayCommand ds = new DisplayCommand();
    	
    	if (Minecraft.getMinecraft().currentScreen instanceof EditLocationsGui) return;
    	
    	if (tc.coordsToggled) {
    		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    		
        	double xDir = (player.rotationYaw % 360 + 360) % 360;
        	if (xDir > 180) xDir -= 360;
        	xDir = (double) Math.round(xDir * 10d) / 10d;
        	double yDir = (double) Math.round(player.rotationPitch * 10d) / 10d;
        	
        	String coordText = COORDS_COLOUR + (int) player.posX + " / " + (int) player.posY + " / " + (int) player.posZ + " (" + xDir + " / " + yDir + ")";
        	new TextRenderer(Minecraft.getMinecraft(), coordText, moc.coordsXY[0], moc.coordsXY[1], ScaleCommand.coordsScale);
    	}
    	
    	if (tc.dungeonTimerToggled && Utils.inDungeons) {
    		String dungeonTimerText = EnumChatFormatting.GRAY + "Wither Doors:\n" +
    								  EnumChatFormatting.DARK_RED + "Blood Open:\n" +
    								  EnumChatFormatting.RED + "Watcher Clear:\n" +
    								  EnumChatFormatting.BLUE + "Boss Clear:\n" +
    								  EnumChatFormatting.YELLOW + "Deaths:\n" +
    								  EnumChatFormatting.YELLOW + "Puzzle Fails:";
    		String dungeonTimers = EnumChatFormatting.GRAY + "" + witherDoors + "\n" +
								   EnumChatFormatting.DARK_RED + Utils.getTimeBetween(dungeonStartTime, bloodOpenTime) + "\n" +
								   EnumChatFormatting.RED + Utils.getTimeBetween(dungeonStartTime, watcherClearTime) + "\n" +
								   EnumChatFormatting.BLUE + Utils.getTimeBetween(dungeonStartTime, bossClearTime) + "\n" +
								   EnumChatFormatting.YELLOW + dungeonDeaths + "\n" +
								   EnumChatFormatting.YELLOW + puzzleFails;
    		new TextRenderer(Minecraft.getMinecraft(), dungeonTimerText, moc.dungeonTimerXY[0], moc.dungeonTimerXY[1], ScaleCommand.dungeonTimerScale);
    		new TextRenderer(Minecraft.getMinecraft(), dungeonTimers, (int) (moc.dungeonTimerXY[0] + (80 * ScaleCommand.dungeonTimerScale)), moc.dungeonTimerXY[1], ScaleCommand.dungeonTimerScale);
    	}
    	
    	if (tc.lividSolverToggled && foundLivid && livid != null) {
    		new TextRenderer(Minecraft.getMinecraft(), livid.getName().replace("" + EnumChatFormatting.BOLD, ""), moc.lividHpXY[0], moc.lividHpXY[1], ScaleCommand.lividHpScale);
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
    				timeBetween = Utils.getTimeBetween(lc.wolfTime, timeNow);
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
    				timeBetween = Utils.getTimeBetween(lc.wolfTimeSession, timeNow);
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
    				timeBetween = Utils.getTimeBetween(lc.spiderTime, timeNow);
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
    				timeBetween = Utils.getTimeBetween(lc.spiderTimeSession, timeNow);
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
    				timeBetween = Utils.getTimeBetween(lc.zombieTime, timeNow);
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
    				timeBetween = Utils.getTimeBetween(lc.zombieTimeSession, timeNow);
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
    				timeBetween = Utils.getTimeBetween(lc.empTime, timeNow);
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
    				new TextRenderer(Minecraft.getMinecraft(), dropsTextTwo, (int) (moc.displayXY[0] + (160 * ScaleCommand.displayScale)), moc.displayXY[1], ScaleCommand.displayScale);
        			new TextRenderer(Minecraft.getMinecraft(), countTextTwo, (int) (moc.displayXY[0] + (270 * ScaleCommand.displayScale)), moc.displayXY[1], ScaleCommand.displayScale);
    			} else {
    				dropsText += "\n" + dropsTextTwo;
    				countText += "\n" + countTextTwo;
    			}
    		} else if (ds.display.equals("fishing_session")) {
    			if (lc.empTimeSession == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = Utils.getTimeBetween(lc.empTimeSession, timeNow);
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
    				new TextRenderer(Minecraft.getMinecraft(), dropsTextTwo, (int) (moc.displayXY[0] + (160 * ScaleCommand.displayScale)), moc.displayXY[1], ScaleCommand.displayScale);
        			new TextRenderer(Minecraft.getMinecraft(), countTextTwo, (int) (moc.displayXY[0] + (270 * ScaleCommand.displayScale)), moc.displayXY[1], ScaleCommand.displayScale);
    			} else {
    				dropsText += "\n" + dropsTextTwo;
    				countText += "\n" + countTextTwo;
    			}
    		} else if (ds.display.equals("fishing_winter")) {
    			if (lc.yetiTime == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = Utils.getTimeBetween(lc.yetiTime, timeNow);
    			}
    			if (lc.yetiSCs == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = nf.format(lc.yetiSCs);
    			}
    			
    			dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
    						EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
    						EnumChatFormatting.GOLD + "Good Catches:\n" +
    						EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
	    					EnumChatFormatting.AQUA + "Frozen Steves:\n" +
							EnumChatFormatting.WHITE + "Snowmans:\n" +
							EnumChatFormatting.DARK_GREEN + "Grinches:\n" +
							EnumChatFormatting.GOLD + "Yetis:\n" +
							EnumChatFormatting.AQUA + "Time Since Yeti:\n" +
							EnumChatFormatting.AQUA + "Creatures Since Yeti:";
    			countText = EnumChatFormatting.AQUA + nf.format(lc.seaCreatures) + "\n" +
							EnumChatFormatting.AQUA + nf.format(lc.fishingMilestone) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.goodCatches) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.greatCatches) + "\n" +
	    					EnumChatFormatting.AQUA + nf.format(lc.frozenSteves) + "\n" +
							EnumChatFormatting.WHITE + nf.format(lc.frostyTheSnowmans) + "\n" +
							EnumChatFormatting.DARK_GREEN + nf.format(lc.grinches) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.yetis) + "\n" +
							EnumChatFormatting.AQUA + timeBetween + "\n" +
							EnumChatFormatting.AQUA + bossesBetween;
    		} else if (ds.display.equals("fishing_winter_session")) {
    			if (lc.yetiTimeSession == -1) {
    				timeBetween = "Never";
    			} else {
    				timeBetween = Utils.getTimeBetween(lc.yetiTimeSession, timeNow);
    			}
    			if (lc.yetiSCsSession == -1) {
    				bossesBetween = "Never";
    			} else {
    				bossesBetween = nf.format(lc.yetiSCsSession);
    			}
    			
    			dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
							EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
							EnumChatFormatting.GOLD + "Good Catches:\n" +
							EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
	    					EnumChatFormatting.AQUA + "Frozen Steves:\n" +
							EnumChatFormatting.WHITE + "Snowmans:\n" +
							EnumChatFormatting.DARK_GREEN + "Grinches:\n" +
							EnumChatFormatting.GOLD + "Yetis:\n" +
							EnumChatFormatting.AQUA + "Time Since Yeti:\n" +
							EnumChatFormatting.AQUA + "Creatures Since Yeti:";
				countText = EnumChatFormatting.AQUA + nf.format(lc.seaCreaturesSession) + "\n" +
							EnumChatFormatting.AQUA + nf.format(lc.fishingMilestoneSession) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.goodCatchesSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.greatCatchesSession) + "\n" +
	    					EnumChatFormatting.AQUA + nf.format(lc.frozenStevesSession) + "\n" +
							EnumChatFormatting.WHITE + nf.format(lc.frostyTheSnowmansSession) + "\n" +
							EnumChatFormatting.DARK_GREEN + nf.format(lc.grinchesSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.yetisSession) + "\n" +
							EnumChatFormatting.AQUA + timeBetween + "\n" +
							EnumChatFormatting.AQUA + bossesBetween;
    		} else if (ds.display.equals("fishing_festival")) {
    			dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
    						EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
    						EnumChatFormatting.GOLD + "Good Catches:\n" +
    						EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
    						EnumChatFormatting.LIGHT_PURPLE + "Nurse Sharks:\n" +
    						EnumChatFormatting.BLUE + "Blue Sharks:\n" +
    						EnumChatFormatting.GOLD + "Tiger Sharks:\n" +
    						EnumChatFormatting.WHITE + "Great White Sharks:";
    			countText = EnumChatFormatting.AQUA + nf.format(lc.seaCreatures) + "\n" +
							EnumChatFormatting.AQUA + nf.format(lc.fishingMilestone) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.goodCatches) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.greatCatches) + "\n" +
	    					EnumChatFormatting.LIGHT_PURPLE + nf.format(lc.nurseSharks) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.blueSharks) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.tigerSharks) + "\n" +
							EnumChatFormatting.WHITE + nf.format(lc.greatWhiteSharks);
     		} else if (ds.display.equals("fishing_festival_session")) {
    			dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
							EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
							EnumChatFormatting.GOLD + "Good Catches:\n" +
							EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
							EnumChatFormatting.LIGHT_PURPLE + "Nurse Sharks:\n" +
							EnumChatFormatting.BLUE + "Blue Sharks:\n" +
							EnumChatFormatting.GOLD + "Tiger Sharks:\n" +
							EnumChatFormatting.WHITE + "Great White Sharks:";
				countText = EnumChatFormatting.AQUA + nf.format(lc.seaCreaturesSession) + "\n" +
							EnumChatFormatting.AQUA + nf.format(lc.fishingMilestoneSession) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.goodCatchesSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.greatCatchesSession) + "\n" +
	    					EnumChatFormatting.LIGHT_PURPLE + nf.format(lc.nurseSharksSession) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.blueSharksSession) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.tigerSharksSession) + "\n" +
							EnumChatFormatting.WHITE + nf.format(lc.greatWhiteSharksSession);
     		} else if (ds.display.equals("fishing_spooky")) {
     			dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
     						EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
     						EnumChatFormatting.GOLD + "Good Catches:\n" +
							EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
     						EnumChatFormatting.BLUE + "Scarecrows:\n" +
							EnumChatFormatting.GRAY + "Nightmares:\n" +
     						EnumChatFormatting.DARK_PURPLE + "Werewolves:\n" +
							EnumChatFormatting.GOLD + "Phantom Fishers:\n" +
     						EnumChatFormatting.GOLD + "Grim Reapers:";
     			countText = EnumChatFormatting.AQUA + nf.format(lc.seaCreatures) + "\n" +
							EnumChatFormatting.AQUA + nf.format(lc.fishingMilestone) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.goodCatches) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.greatCatches) + "\n" +
	    					EnumChatFormatting.BLUE + nf.format(lc.scarecrows) + "\n" +
							EnumChatFormatting.GRAY + nf.format(lc.nightmares) + "\n" +
	    					EnumChatFormatting.DARK_PURPLE + nf.format(lc.werewolfs) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.phantomFishers) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.grimReapers);
     		} else if (ds.display.equals("fishing_spooky_session")) {
     			dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
	 						EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
	 						EnumChatFormatting.GOLD + "Good Catches:\n" +
							EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
	 						EnumChatFormatting.BLUE + "Scarecrows:\n" +
							EnumChatFormatting.GRAY + "Nightmares:\n" +
	 						EnumChatFormatting.DARK_PURPLE + "Werewolves:\n" +
							EnumChatFormatting.GOLD + "Phantom Fishers:\n" +
	 						EnumChatFormatting.GOLD + "Grim Reapers:";
	 			countText = EnumChatFormatting.AQUA + nf.format(lc.seaCreaturesSession) + "\n" +
							EnumChatFormatting.AQUA + nf.format(lc.fishingMilestoneSession) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.goodCatchesSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.greatCatchesSession) + "\n" +
	    					EnumChatFormatting.BLUE + nf.format(lc.scarecrowsSession) + "\n" +
							EnumChatFormatting.GRAY + nf.format(lc.nightmaresSession) + "\n" +
	    					EnumChatFormatting.DARK_PURPLE + nf.format(lc.werewolfsSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.phantomFishersSession) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.grimReapersSession);
     		} else if (ds.display.equals("catacombs_floor_one")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
    						EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
    						EnumChatFormatting.BLUE + "Bonzo's Staffs:\n" +
    						EnumChatFormatting.AQUA + "Coins Spent:\n" +
    						EnumChatFormatting.AQUA + "Time Spent:";
    			countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulators) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooks) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.bonzoStaffs) + "\n" +
							EnumChatFormatting.AQUA + Utils.getMoneySpent(lc.f1CoinsSpent) + "\n" +
							EnumChatFormatting.AQUA + Utils.getTimeBetween(0, lc.f1TimeSpent);
    		} else if (ds.display.equals("catacombs_floor_one_session")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.BLUE + "Bonzo's Staffs:\n" +
    						EnumChatFormatting.AQUA + "Coins Spent:\n" +
    						EnumChatFormatting.AQUA + "Time Spent:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulatorsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooksSession) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.bonzoStaffsSession) + "\n" +
							EnumChatFormatting.AQUA + Utils.getMoneySpent(lc.f1CoinsSpentSession) + "\n" +
							EnumChatFormatting.AQUA + Utils.getTimeBetween(0, lc.f1TimeSpentSession);
    		} else if (ds.display.equals("catacombs_floor_two")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.BLUE + "Scarf's Studies:\n" +
		    				EnumChatFormatting.AQUA + "Coins Spent:\n" +
		    				EnumChatFormatting.AQUA + "Time Spent:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulators) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooks) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.scarfStudies) + "\n" +
							EnumChatFormatting.AQUA + Utils.getMoneySpent(lc.f2CoinsSpent) + "\n" +
							EnumChatFormatting.AQUA + Utils.getTimeBetween(0, lc.f2TimeSpent);
    		} else if (ds.display.equals("catacombs_floor_two_session")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.BLUE + "Scarf's Studies:\n" +
		    				EnumChatFormatting.AQUA + "Coins Spent:\n" +
		    				EnumChatFormatting.AQUA + "Time Spent:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulatorsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooksSession) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.scarfStudiesSession) + "\n" +
							EnumChatFormatting.AQUA + Utils.getMoneySpent(lc.f2CoinsSpentSession) + "\n" +
							EnumChatFormatting.AQUA + Utils.getTimeBetween(0, lc.f2TimeSpentSession);
    		} else if (ds.display.equals("catacombs_floor_three")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Helmets:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Chestplates:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Leggings:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Boots:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Blades:\n" +
		    				EnumChatFormatting.AQUA + "Coins Spent:\n" +
		    				EnumChatFormatting.AQUA + "Time Spent:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulators) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooks) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveHelms) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveChests) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveLegs) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveBoots) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveSwords) + "\n" +
							EnumChatFormatting.AQUA + Utils.getMoneySpent(lc.f3CoinsSpent) + "\n" +
							EnumChatFormatting.AQUA + Utils.getTimeBetween(0, lc.f3TimeSpent);
    		} else if (ds.display.equals("catacombs_floor_three_session")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Helmets:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Chestplates:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Leggings:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Boots:\n" +
							EnumChatFormatting.DARK_PURPLE + "Adaptive Blades:\n" +
		    				EnumChatFormatting.AQUA + "Coins Spent:\n" +
		    				EnumChatFormatting.AQUA + "Time Spent:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulatorsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooksSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveHelmsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveChestsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveLegsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveBootsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.adaptiveSwordsSession) + "\n" +
							EnumChatFormatting.AQUA + Utils.getMoneySpent(lc.f3CoinsSpentSession) + "\n" +
							EnumChatFormatting.AQUA + Utils.getTimeBetween(0, lc.f3TimeSpentSession);
    		} else if (ds.display.equals("catacombs_floor_four")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Wings:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Bones:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Boots:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Swords:\n" +
							EnumChatFormatting.GOLD + "Spirit Bows:\n" +
							EnumChatFormatting.DARK_PURPLE + "Epic Spirit Pets:\n" +
							EnumChatFormatting.GOLD + "Leg Spirit Pets:\n" +
		    				EnumChatFormatting.AQUA + "Coins Spent:\n" +
		    				EnumChatFormatting.AQUA + "Time Spent:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulators) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooks) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritWings) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritBones) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritBoots) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritSwords) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.spiritBows) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.epicSpiritPets) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.legSpiritPets) + "\n" +
							EnumChatFormatting.AQUA + Utils.getMoneySpent(lc.f4CoinsSpent) + "\n" +
							EnumChatFormatting.AQUA + Utils.getTimeBetween(0, lc.f4TimeSpent);
    		} else if (ds.display.equals("catacombs_floor_four_session")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Wings:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Bones:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Boots:\n" +
							EnumChatFormatting.DARK_PURPLE + "Spirit Swords:\n" +
							EnumChatFormatting.GOLD + "Spirit Bows:\n" +
							EnumChatFormatting.DARK_PURPLE + "Epic Spirit Pets:\n" +
							EnumChatFormatting.GOLD + "Leg Spirit Pets:\n" +
		    				EnumChatFormatting.AQUA + "Coins Spent:\n" +
		    				EnumChatFormatting.AQUA + "Time Spent:";
				countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulatorsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooksSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritWingsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritBonesSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritBootsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.spiritSwordsSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.spiritBowsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.epicSpiritPetsSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.legSpiritPetsSession) + "\n" +
							EnumChatFormatting.AQUA + Utils.getMoneySpent(lc.f4CoinsSpentSession) + "\n" +
							EnumChatFormatting.AQUA + Utils.getTimeBetween(0, lc.f4TimeSpentSession);
    		} else if (ds.display.equals("catacombs_floor_five")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.BLUE + "Warped Stones:\n" +
							EnumChatFormatting.DARK_PURPLE + "Shadow Helmets:\n" +
							EnumChatFormatting.DARK_PURPLE + "Shadow Chestplates:\n" +
							EnumChatFormatting.DARK_PURPLE + "Shadow Leggings:\n" +
							EnumChatFormatting.DARK_PURPLE + "Shadow Boots:\n" +
							EnumChatFormatting.GOLD + "Livid Daggers:\n" +
							EnumChatFormatting.GOLD + "Shadow Furys:\n" +
							EnumChatFormatting.AQUA + "Coins Spent:\n" +
		    				EnumChatFormatting.AQUA + "Time Spent:";
    			countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulators) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooks) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.warpedStones) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.shadowAssHelms) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.shadowAssChests) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.shadowAssLegs) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.shadowAssBoots) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.lividDaggers) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.shadowFurys) + "\n" +
							EnumChatFormatting.AQUA + Utils.getMoneySpent(lc.f5CoinsSpent) + "\n" +
							EnumChatFormatting.AQUA + Utils.getTimeBetween(0, lc.f5TimeSpent);
    		} else if (ds.display.equals("catacombs_floor_five_session")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.BLUE + "Warped Stones:\n" +
							EnumChatFormatting.DARK_PURPLE + "Shadow Helmets:\n" +
							EnumChatFormatting.DARK_PURPLE + "Shadow Chestplates:\n" +
							EnumChatFormatting.DARK_PURPLE + "Shadow Leggings:\n" +
							EnumChatFormatting.DARK_PURPLE + "Shadow Boots:\n" +
							EnumChatFormatting.GOLD + "Livid Daggers:\n" +
							EnumChatFormatting.GOLD + "Shadow Furys:\n" +
							EnumChatFormatting.AQUA + "Coins Spent:\n" +
		    				EnumChatFormatting.AQUA + "Time Spent:";
    			countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulatorsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooksSession) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.warpedStonesSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.shadowAssHelmsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.shadowAssChestsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.shadowAssLegsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.shadowAssBootsSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.lividDaggersSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.shadowFurysSession) + "\n" +
							EnumChatFormatting.AQUA + Utils.getMoneySpent(lc.f5CoinsSpentSession) + "\n" +
							EnumChatFormatting.AQUA + Utils.getTimeBetween(0, lc.f5TimeSpentSession);
    		} else if (ds.display.equals("catacombs_floor_six")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.BLUE + "Ancient Roses:\n" +
							EnumChatFormatting.GOLD + "Precursor Eyes:\n" +
							EnumChatFormatting.GOLD + "Giant's Swords:\n" +
							EnumChatFormatting.GOLD + "Necro Lord Helmets:\n" +
							EnumChatFormatting.GOLD + "Necro Lord Chests:\n" +
							EnumChatFormatting.GOLD + "Necro Lord Leggings:\n" +
							EnumChatFormatting.GOLD + "Necro Lord Boots:\n" +
							EnumChatFormatting.GOLD + "Necro Swords:\n" +
							EnumChatFormatting.AQUA + "Coins Spent:\n" +
		    				EnumChatFormatting.AQUA + "Time Spent:";
    			countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulators) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooks) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.ancientRoses) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.precursorEyes) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.giantsSwords) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.necroLordHelms) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.necroLordChests) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.necroLordLegs) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.necroLordBoots) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.necroSwords) + "\n" +
							EnumChatFormatting.AQUA + Utils.getMoneySpent(lc.f6CoinsSpent) + "\n" +
							EnumChatFormatting.AQUA + Utils.getTimeBetween(0, lc.f6TimeSpent);
    		} else if (ds.display.equals("catacombs_floor_six_session")) {
    			dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
							EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
							EnumChatFormatting.BLUE + "Ancient Roses:\n" +
							EnumChatFormatting.GOLD + "Precursor Eyes:\n" +
							EnumChatFormatting.GOLD + "Giant's Swords:\n" +
							EnumChatFormatting.GOLD + "Necro Lord Helmets:\n" +
							EnumChatFormatting.GOLD + "Necro Lord Chests:\n" +
							EnumChatFormatting.GOLD + "Necro Lord Leggings:\n" +
							EnumChatFormatting.GOLD + "Necro Lord Boots:\n" +
							EnumChatFormatting.GOLD + "Necro Swords:\n" +
							EnumChatFormatting.AQUA + "Coins Spent:\n" +
		    				EnumChatFormatting.AQUA + "Time Spent:";
    			countText = EnumChatFormatting.GOLD + nf.format(lc.recombobulatorsSession) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.fumingPotatoBooksSession) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.ancientRosesSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.precursorEyesSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.giantsSwordsSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.necroLordHelmsSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.necroLordChestsSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.necroLordLegsSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.necroLordBootsSession) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.necroSwordsSession) + "\n" +
							EnumChatFormatting.AQUA + Utils.getMoneySpent(lc.f6CoinsSpentSession) + "\n" +
							EnumChatFormatting.AQUA + Utils.getTimeBetween(0, lc.f6TimeSpentSession);
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
    	if (showSkill) {
    		new TextRenderer(Minecraft.getMinecraft(), skillText, moc.skill50XY[0], moc.skill50XY[1], ScaleCommand.skill50Scale);
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
    	
    	ItemStack item = event.itemStack;
    	if (tc.goldenToggled) {
    		for (int i = 0; i < event.toolTip.size(); i++) {
    			event.toolTip.set(i, Utils.returnGoldenEnchants(event.toolTip.get(i)));
    		}
    	}
    	
    	if (tc.expertiseLoreToggled) {
    		if (item.hasTagCompound()) {
    			NBTTagCompound tags = item.getSubCompound("ExtraAttributes", false);
    			if (tags != null) {
    				if (tags.hasKey("expertise_kills")) {
    					int index = 4;
    					if (!Minecraft.getMinecraft().gameSettings.advancedItemTooltips) index -= 2;
    					
    					event.toolTip.add(event.toolTip.size() - index, "");
    					event.toolTip.add(event.toolTip.size() - index, "Expertise Kills: " + EnumChatFormatting.RED + tags.getInteger("expertise_kills"));
    					if (Utils.expertiseKillsLeft(tags.getInteger("expertise_kills")) != -1) {
    						event.toolTip.add(event.toolTip.size() - index, Utils.expertiseKillsLeft(tags.getInteger("expertise_kills")) + " kills to tier up!");
    					}
    				}
    			}
    		}
    	}
    }
    
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP player = mc.thePlayer;
		
    	// Checks every second
    	tickAmount++;
    	if (tickAmount % 20 == 0) {
    		if (player != null) {
        		Utils.checkForSkyblock();
        		Utils.checkForDungeons();
    		}
    		
    		if (DisplayCommand.auto && mc != null && mc.theWorld != null) {
    			List<String> scoreboard = ScoreboardHandler.getSidebarLines();
    			boolean found = false;
    			for (String s : scoreboard) {
    				String sCleaned = ScoreboardHandler.cleanSB(s);
    				if (sCleaned.contains("Sven Packmaster")) {
    					DisplayCommand.display = "wolf";
    					found = true;
    				} else if (sCleaned.contains("Tarantula Broodfather")) {
    					DisplayCommand.display = "spider";
    					found = true;
    				} else if (sCleaned.contains("Revenant Horror")) {
    					DisplayCommand.display = "zombie";
    					found = true;
    				} else if (sCleaned.contains("The Catacombs (")) {
    					if (sCleaned.contains("F1")) {
    						DisplayCommand.display = "catacombs_floor_one";
    					} else if (sCleaned.contains("F2")) {
    						DisplayCommand.display = "catacombs_floor_two";
    					} else if (sCleaned.contains("F3")) {
    						DisplayCommand.display = "catacombs_floor_three";
    					} else if (sCleaned.contains("F4")) {
    						DisplayCommand.display = "catacombs_floor_four";
    					} else if (sCleaned.contains("F5")) {
    						DisplayCommand.display = "catacombs_floor_five";
    					} else if (sCleaned.contains("F6")) {
    						DisplayCommand.display = "catacombs_floor_six";
    					}
    					found = true;
    				}
    			}
    			if (!found) DisplayCommand.display = "off";
    			ConfigHandler.writeStringConfig("misc", "display", DisplayCommand.display);
    		}
    		
    		if (ToggleCommand.creeperToggled && Utils.inDungeons && mc.theWorld != null) {
    	    	double x = player.posX;
    	    	double y = player.posY;
    	    	double z = player.posZ;
    			// Find creepers nearby
    			AxisAlignedBB creeperScan = new AxisAlignedBB(x - 14, y - 8, z - 13, x + 14, y + 8, z + 13); // 28x16x26 cube
    			List<EntityCreeper> creepers = mc.theWorld.getEntitiesWithinAABB(EntityCreeper.class, creeperScan);
    			// Check if creeper is nearby
    			if (creepers.size() > 0) {
    				EntityCreeper creeper = creepers.get(0);
    				// Start creeper line drawings
    				creeperLines.clear();
    				if (!drawCreeperLines) creeperLocation = new Vec3(creeper.posX, creeper.posY + 1, creeper.posZ);
    				drawCreeperLines = true;
    				// Search for nearby sea lanterns and prismarine blocks
    				BlockPos point1 = new BlockPos(creeper.posX - 14, creeper.posY - 7, creeper.posZ - 13);
    				BlockPos point2 = new BlockPos(creeper.posX + 14, creeper.posY + 10, creeper.posZ + 13);
    				Iterable<BlockPos> blocks = BlockPos.getAllInBox(point1, point2);
    				for (BlockPos blockPos : blocks) {
    					Block block = mc.theWorld.getBlockState(blockPos).getBlock();
    					if (block == Blocks.sea_lantern || block == Blocks.prismarine) {
    						// Connect block to nearest block on opposite side
    						Vec3 startBlock = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
    						BlockPos oppositeBlock = Utils.getFirstBlockPosAfterVectors(mc, startBlock, creeperLocation, 10, 20);
    						BlockPos endBlock = Utils.getNearbyBlock(mc, oppositeBlock, Blocks.sea_lantern, Blocks.prismarine);
    						if (endBlock != null && startBlock.yCoord > 68 && endBlock.getY() > 68) { // Don't create line underground
    							// Add to list for drawing
        						Vec3[] insertArray = {startBlock, new Vec3(endBlock.getX() + 0.5, endBlock.getY() + 0.5, endBlock.getZ() + 0.5)};
        						creeperLines.add(insertArray);
    						}
    					}
    				}
    			} else {
    				drawCreeperLines = false;
    			}
    		}
    		
    		if (ToggleCommand.lividSolverToggled && Utils.inDungeons && !foundLivid && mc.theWorld != null) {
    			boolean inF5 = false;
    			
    			List<String> scoreboard = ScoreboardHandler.getSidebarLines();
    			for (String s : scoreboard) {
    				String sCleaned = ScoreboardHandler.cleanSB(s);
    				if (sCleaned.contains("The Catacombs (F5)")) {
    					inF5 = true;
    					break;
    				}
    			}
    			
    			if (inF5) {
    				List<Entity> loadedLivids = new ArrayList<Entity>();
    				List<Entity> entities = mc.theWorld.getLoadedEntityList();
    				for (Entity entity : entities) {
    					String name = entity.getName();
    					if (name.contains("Livid") && name.length() > 5 && name.charAt(1) == name.charAt(5) && !loadedLivids.contains(entity)) {
    						loadedLivids.add(entity);
    					}
    				}
    				if (loadedLivids.size() > 8) {
    					livid = loadedLivids.get(0);
    					foundLivid = true;
    				}
    			}
    		}
    		
    		tickAmount = 0;
    	}
    	
    	// Checks 5 times per second
    	if (tickAmount % 4 == 0) {
    		if (ToggleCommand.blazeToggled && Utils.inDungeons && mc.theWorld != null) {
    			List<Entity> entities = mc.theWorld.getLoadedEntityList();
    			int highestHealth = 0;
    			highestBlaze = null;
    			int lowestHealth = 99999999;
    			lowestBlaze = null;

    			for (Entity entity : entities) {
    				if (entity.getName().contains("Blaze") && entity.getName().contains("/")) {
    					String blazeName = StringUtils.stripControlCodes(entity.getName());
    					try {
    						int health = Integer.parseInt(blazeName.substring(blazeName.indexOf("/") + 1, blazeName.length() - 2));
    						if (health > highestHealth) {
    							highestHealth = health;
    							highestBlaze = entity;
    						}
    						if (health < lowestHealth) {
    							lowestHealth = health;
    							lowestBlaze = entity;
    						}
    					} catch (NumberFormatException ex) {
    						System.err.println(ex);
    					}
    				}
    			}
    		}
    	}
    	
    	if (titleTimer >= 0) {
    		if (titleTimer == 0) {
    			showTitle = false;
    		}
    		titleTimer--;
    	}
    	if (skillTimer >= 0) {
    		if (skillTimer == 0) {
    			showSkill = false;
    		}
    		skillTimer--;
     	}
    }
    
    // Delay GUI by 1 tick
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
    	if (guiToOpen != null) {
    		Minecraft mc = Minecraft.getMinecraft();
        	if (guiToOpen.startsWith("dankergui")) {
        		int page = Character.getNumericValue(guiToOpen.charAt(guiToOpen.length() - 1));
        		mc.displayGuiScreen(new DankerGui(page));
        	} else if (guiToOpen.equals("displaygui")) {
        		mc.displayGuiScreen(new DisplayGui());
        	} else if (guiToOpen.equals("onlyslayergui")) {
        		mc.displayGuiScreen(new OnlySlayerGui());
        	} else if (guiToOpen.equals("editlocations")) {
        		mc.displayGuiScreen(new EditLocationsGui());
        	} else if (guiToOpen.equals("puzzlesolvers")) {
        		mc.displayGuiScreen(new PuzzleSolversGui());
        	}
        	guiToOpen = null;
    	}
    }
    
    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
    	if (ToggleCommand.blazeToggled) {
    		if (lowestBlaze != null) {
    			BlockPos stringPos = new BlockPos(lowestBlaze.posX, lowestBlaze.posY + 1, lowestBlaze.posZ);
    			Utils.draw3DString(stringPos, EnumChatFormatting.BOLD + "Smallest", 0xFF0000, event.partialTicks);
    			AxisAlignedBB aabb = new AxisAlignedBB(lowestBlaze.posX - 0.5, lowestBlaze.posY - 2, lowestBlaze.posZ - 0.5, lowestBlaze.posX + 0.5, lowestBlaze.posY, lowestBlaze.posZ + 0.5);
    			Utils.draw3DBox(aabb, 0xFF, 0x00, 0x00, 0xFF, event.partialTicks);
    		}
    		if (highestBlaze != null) {
    			BlockPos stringPos = new BlockPos(highestBlaze.posX, highestBlaze.posY + 1, highestBlaze.posZ);
    			Utils.draw3DString(stringPos, EnumChatFormatting.BOLD + "Biggest", 0x40FF40, event.partialTicks);
    			AxisAlignedBB aabb = new AxisAlignedBB(highestBlaze.posX - 0.5, highestBlaze.posY - 2, highestBlaze.posZ - 0.5, highestBlaze.posX + 0.5, highestBlaze.posY, highestBlaze.posZ + 0.5);
    			Utils.draw3DBox(aabb, 0x00, 0xFF, 0x00, 0xFF, event.partialTicks);
    		}
    	}
    	if (ToggleCommand.creeperToggled && drawCreeperLines && !creeperLines.isEmpty()) {
    		for (int i = 0; i < creeperLines.size(); i++) {
    			Utils.draw3DLine(creeperLines.get(i)[0], creeperLines.get(i)[1], creeperLineColours[i % 10], event.partialTicks);
    		}
    	}
    }
    
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
    	if (!Utils.inSkyblock || Minecraft.getMinecraft().thePlayer != event.entityPlayer) return;
    	ItemStack item = event.entityPlayer.getHeldItem();
    	if (item == null) return;
    	
    	if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
    		if (ToggleCommand.aotdToggled && item.getDisplayName().contains("Aspect of the Dragons")) {
    			event.setCanceled(true);
    		}
    		if (ToggleCommand.lividDaggerToggled && item.getDisplayName().contains("Livid Dagger")) {
    			event.setCanceled(true);
    		}
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
    public void onGuiMouseInput(GuiScreenEvent.MouseInputEvent.Pre event) {
    	if (!Utils.inSkyblock) return;
    	if (Mouse.getEventButton() == lastMouse) return;
    	lastMouse = Mouse.getEventButton();
    	if (Mouse.getEventButton() != 0 && Mouse.getEventButton() != 1) return; // Left click or right click
    	
    	if (event.gui instanceof GuiChest) {
    		Container containerChest = ((GuiChest) event.gui).inventorySlots;
    		if (containerChest instanceof ContainerChest) {
    			// a lot of declarations here, if you get scarred, my bad
        		LootCommand lc = new LootCommand();
        		ConfigHandler cf = new ConfigHandler();
    			GuiChest chest = (GuiChest) event.gui;
    			IInventory inventory = ((ContainerChest) containerChest).getLowerChestInventory();
    			Slot mouseSlot = chest.getSlotUnderMouse();
    			if (mouseSlot == null || mouseSlot.getStack() == null) return;
    			ItemStack item = mouseSlot.getStack();
    			String inventoryName = inventory.getDisplayName().getUnformattedText();
    			
    			if (inventoryName.endsWith(" Chest") && item.getDisplayName().contains("Open Reward Chest")) {
    				List<String> tooltip = item.getTooltip(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().gameSettings.advancedItemTooltips);
    				for (String lineUnclean : tooltip) {
    					String line = StringUtils.stripControlCodes(lineUnclean);
    					if (line.contains("FREE")) {
    						break;
    					} else if (line.contains(" Coins")) {
    						int coinsSpent = Integer.parseInt(line.substring(0, line.indexOf(" ")).replaceAll(",", ""));
    						
    						List<String> scoreboard = ScoreboardHandler.getSidebarLines();
    						for (String s : scoreboard) {
    							String sCleaned = ScoreboardHandler.cleanSB(s);
    							if (sCleaned.contains("The Catacombs (")) {
    								if (sCleaned.contains("F1")) {
    									lc.f1CoinsSpent += coinsSpent;
    									lc.f1CoinsSpentSession += coinsSpent;
    									cf.writeDoubleConfig("catacombs", "floorOneCoins", lc.f1CoinsSpent);
    								} else if (sCleaned.contains("F2")) {
    									lc.f2CoinsSpent += coinsSpent;
    									lc.f2CoinsSpentSession += coinsSpent;
    									cf.writeDoubleConfig("catacombs", "floorTwoCoins", lc.f2CoinsSpent);
    								} else if (sCleaned.contains("F3")) {
    									lc.f3CoinsSpent += coinsSpent;
    									lc.f3CoinsSpentSession += coinsSpent;
    									cf.writeDoubleConfig("catacombs", "floorThreeCoins", lc.f3CoinsSpent);
    								} else if (sCleaned.contains("F4")) {
    									lc.f4CoinsSpent += coinsSpent;
    									lc.f4CoinsSpentSession += coinsSpent;
    									cf.writeDoubleConfig("catacombs", "floorFourCoins", lc.f4CoinsSpent);
    								} else if (sCleaned.contains("F5")) {
    									lc.f5CoinsSpent += coinsSpent;
    									lc.f5CoinsSpentSession += coinsSpent;
    									cf.writeDoubleConfig("catacombs", "floorFiveCoins", lc.f5CoinsSpent);
    								} else if (sCleaned.contains("F6")) {
    									lc.f6CoinsSpent += coinsSpent;
    									lc.f6CoinsSpentSession += coinsSpent;
    									cf.writeDoubleConfig("catacombs", "floorSixCoins", lc.f6CoinsSpent);
    								}
    								break;
    							}
    						}
    						break;
    					}
    				}
    			} 
    			
    			if (!BlockSlayerCommand.onlySlayerName.equals(""))  {
    				if (inventoryName.equals("Slayer")) {
        				if (!item.getDisplayName().contains("Revenant Horror") && !item.getDisplayName().contains("Tarantula Broodfather") && !item.getDisplayName().contains("Sven Packmaster")) return;
        				if (!item.getDisplayName().contains(BlockSlayerCommand.onlySlayerName)) {
        					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ERROR_COLOUR + "Danker's Skyblock Mod has stopped you from starting this quest (Set to " + BlockSlayerCommand.onlySlayerName + " " + BlockSlayerCommand.onlySlayerNumber + ")"));
        					Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1, (float) 0.5);
        					event.setCanceled(true);
        				}
        			} else if (inventoryName.equals("Revenant Horror") || inventoryName.equals("Tarantula Broodfather") || inventoryName.equals("Sven Packmaster")) {
        				if (item.getDisplayName().contains("Revenant Horror") || item.getDisplayName().contains("Tarantula Broodfather") || item.getDisplayName().contains("Sven Packmaster")) {
        					// Only check number as they passed the above check
        					String slayerNumber = item.getDisplayName().substring(item.getDisplayName().lastIndexOf(" ") + 1, item.getDisplayName().length());
            				if (!slayerNumber.equals(BlockSlayerCommand.onlySlayerNumber)) {
            					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ERROR_COLOUR + "Danker's Skyblock Mod has stopped you from starting this quest (Set to " + BlockSlayerCommand.onlySlayerName + " " + BlockSlayerCommand.onlySlayerNumber + ")"));
            					Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1, (float) 0.5);
            					event.setCanceled(true);
            				}
        				}
        			}
    			}
    		}
    	}
    }
    
    @SubscribeEvent
    public void onGuiRender(GuiScreenEvent.BackgroundDrawnEvent event) {
    	if (!Utils.inSkyblock) return;
    	if (ToggleCommand.petColoursToggled && event.gui instanceof GuiChest) {
    		GuiChest inventory = (GuiChest) event.gui;
    		List<Slot> invSlots = inventory.inventorySlots.inventorySlots;
    		Pattern petPattern = Pattern.compile("\\[Lvl [\\d]{1,3}]");
    		for (Slot slot : invSlots) {
    			ItemStack item = slot.getStack();
    			if (item == null) continue;
    			String name = item.getDisplayName();
    			if (petPattern.matcher(StringUtils.stripControlCodes(name)).find()) {
    				if (name.endsWith("aHealer") || name.endsWith("aMage") || name.endsWith("aBerserk") || name.endsWith("aArcher") || name.endsWith("aTank")) continue;
    				int colour;
    				int petLevel = Integer.parseInt(item.getDisplayName().substring(item.getDisplayName().indexOf(" ") + 1, item.getDisplayName().indexOf("]")));
    				if (petLevel == 100) {
    					colour = 0xBFF2D249; // Gold
    				} else if (petLevel >= 90) {
    					colour = 0xBF9E794E; // Brown
    				} else if (petLevel >= 80) {
    					colour = 0xBF5C1F35; // idk weird magenta
    				} else if (petLevel >= 70) {
    					colour = 0xBFD64FC8; // Pink
    				} else if (petLevel >= 60) {
    					colour = 0xBF7E4FC6; // Purple
    				} else if (petLevel >= 50) {
    					colour = 0xBF008AD8; // Light Blue
    				} else if (petLevel >= 40) {
    					colour = 0xBF0EAC35; // Green
    				} else if (petLevel >= 30) {
    					colour = 0xBFFFC400; // Yellow
    				} else if (petLevel >= 20) {
    					colour = 0xBFEF5230; // Orange
    				} else if (petLevel >= 10) {
    					colour = 0xBFD62440; // Red
    				} else {
    					colour = 0xBF999999; // Gray
    				}
    				Utils.drawOnSlot(inventory.inventorySlots.inventorySlots.size(), slot.xDisplayPosition, slot.yDisplayPosition, colour);
    			}
    		}
    	}
    }
    
    @SubscribeEvent
    public void onServerConnect(ClientConnectedToServerEvent event) {
        event.manager.channel().pipeline().addBefore("packet_handler", "danker_packet_handler", new PacketHandler());
        System.out.println("Added packet handler to channel pipeline.");
    }
    
    public void increaseSeaCreatures() {
    	LootCommand lc = new LootCommand();
    	ConfigHandler cf = new ConfigHandler();
    	
    	if (lc.empSCs != -1) {
    		lc.empSCs++;
    	}
    	if (lc.empSCsSession != -1) {
    		lc.empSCsSession++;
    	}
    	// Only increment Yetis when in Jerry's Workshop
    	List<String> scoreboard = ScoreboardHandler.getSidebarLines();
    	for (String s : scoreboard) {
    		String sCleaned = ScoreboardHandler.cleanSB(s);
    		if (sCleaned.contains("Jerry's Workshop") || sCleaned.contains("Jerry Pond")) {
    			if (lc.yetiSCs != -1) {
    				lc.yetiSCs++;
    			}
    			if (lc.yetiSCsSession != -1) {
    				lc.yetiSCsSession++;
    			}
    		}
    	}
    	
		lc.seaCreatures++;
		lc.fishingMilestone++;
		lc.seaCreaturesSession++;
		lc.fishingMilestoneSession++;
		cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
		cf.writeIntConfig("fishing", "milestone", lc.fishingMilestone);
    	cf.writeIntConfig("fishing", "empSC", lc.empSCs);
    	cf.writeIntConfig("fishing", "yetiSC", lc.yetiSCs);
    	
    }
    
}
