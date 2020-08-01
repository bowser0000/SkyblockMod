package me.Danker;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;

import me.Danker.commands.ArmourCommand;
import me.Danker.commands.BankCommand;
import me.Danker.commands.DHelpCommand;
import me.Danker.commands.DisplayCommand;
import me.Danker.commands.GetkeyCommand;
import me.Danker.commands.GuildOfCommand;
import me.Danker.commands.ImportFishingCommand;
import me.Danker.commands.LootCommand;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.PetsCommand;
import me.Danker.commands.ReloadConfigCommand;
import me.Danker.commands.SetkeyCommand;
import me.Danker.commands.SkillsCommand;
import me.Danker.commands.SlayerCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.handlers.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;

@Mod(modid = TheMod.MODID, version = TheMod.VERSION, clientSideOnly = true)
public class TheMod
{
    public static final String MODID = "Danker's Skyblock Mod";
    public static final String VERSION = "1.6";
    
    static double checkItemsNow = 0;
    static double itemsChecked = 0;
    static Map<String, String> t6Enchants = new HashMap<String, String>();
    static Pattern pattern = Pattern.compile("");
    static boolean updateChecked = false;
    
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
		t6Enchants.put("Smite VI", EnumChatFormatting.GOLD + "Smite VI" + EnumChatFormatting.BLUE);
		t6Enchants.put("Smite VII", EnumChatFormatting.GOLD + "Smite VII" + EnumChatFormatting.BLUE);
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
		
		String patternString = "(" + String.join("|", t6Enchants.keySet()) + ")";
		pattern = Pattern.compile(patternString);
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
		// I couldn't find a pic of someone getting this drop, so I'm assuming this
		// works
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
			lc.wolfTime = System.currentTimeMillis() / 1000;
			lc.wolfBosses = 0;
			cf.writeDoubleConfig("wolf", "timeRNG", lc.wolfTime);
			cf.writeIntConfig("wolf", "bossRNG", 0);
		}
		if (spiderRNG) {
			lc.spiderTime = System.currentTimeMillis() / 1000;
			lc.spiderBosses = 0;
			cf.writeDoubleConfig("spider", "timeRNG", lc.spiderTime);
			cf.writeIntConfig("spider", "bossRNG", 0);
		}
		if (zombieRNG) {
			lc.zombieTime = System.currentTimeMillis() / 1000;
			lc.zombieBosses = 0;
			cf.writeDoubleConfig("zombie", "timeRNG", lc.zombieTime);
			cf.writeIntConfig("zombie", "bossRNG", 0);
		}
		
		// Fishing
		if (message.contains("GOOD CATCH!")) {
			lc.goodCatches++;
			cf.writeIntConfig("fishing", "goodCatch", lc.goodCatches);
		}
		if (message.contains("GREAT CATCH!")) {
			lc.greatCatches++;
			cf.writeIntConfig("fishing", "greatCatch", lc.greatCatches);
		}
		if (message.contains("You caught a lowly Squid")) {
			lc.squids++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "squid", lc.squids);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("From the depths of the waters, you've reeled in a Sea Walker")) {
			lc.seaWalkers++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "seaWalker", lc.seaWalkers);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("Pitch darkness reveals you've caught a")) {
			lc.nightSquids++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "nightSquid", lc.nightSquids);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("You've stumbled upon a patrolling Sea Guardian")) {
			lc.seaGuardians++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "seaGuardian", lc.seaGuardians);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("It looks like you've disrupted Sea Witch's brewing session")) {
			lc.seaWitches++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "seaWitch", lc.seaWitches);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("From the depths of the waters, you've reeled in a Sea Archer")) {
			lc.seaArchers++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "seaArcher", lc.seaArchers);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("The Monster of the Deep emerges from the dark depths")) {
			lc.monsterOfTheDeeps++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "monsterOfDeep", lc.monsterOfTheDeeps);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("You have found a Catfish, don't let it steal your catches")) {
			lc.catfishes++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "catfish", lc.catfishes);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("Is this even a fish? It's the Carrot King")) {
			lc.carrotKings++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "carrotKing", lc.carrotKings);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("Gross! A Sea Leech")) {
			lc.seaLeeches++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "seaLeech", lc.seaLeeches);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("You've discovered a Guardian Defender of the sea")) {
			lc.guardianDefenders++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "guardianDefender", lc.guardianDefenders);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("You have awoken the Deep Sea Protector, prepare for a battle")) {
			lc.deepSeaProtectors++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "deepSeaProtector", lc.deepSeaProtectors);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("The Water Hydra has come to test your strength")) {
			lc.hydras++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "hydra", lc.hydras);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			increaseEmpSC();
		}
		if (message.contains("The Sea Emperor arises from the depths")) {
			lc.seaEmperors++;
			lc.seaCreatures++;
			lc.empTime = System.currentTimeMillis() / 1000;
			lc.empSCs = 0;
			cf.writeIntConfig("fishing", "seaEmperor", lc.seaEmperors);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
			cf.writeDoubleConfig("fishing", "empTime", lc.empTime);
			cf.writeIntConfig("fishing", "empSC", lc.empSCs);
		}
		// Fishing Winter
		if (message.contains("Frozen Steve fell into the pond long ago")) {
			lc.frozenSteves++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "frozenSteve", lc.frozenSteves);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
		}
		if (message.contains("It's a snowman! He looks harmless")) {
			lc.frostyTheSnowmans++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "snowman", lc.frostyTheSnowmans);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
		}
		if (message.contains("stole Jerry's Gifts...get them back")) {
			lc.grinches++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "grinch", lc.grinches);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
		}
		if (message.contains("What is this creature")) {
			lc.yetis++;
			lc.seaCreatures++;
			cf.writeIntConfig("fishing", "yeti", lc.yetis);
			cf.writeIntConfig("fishing", "seaCreature", lc.seaCreatures);
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
    	
    	if (!ds.display.equals("off")) {
    		final LootCommand lc = new LootCommand();
    		String dropsText = "";
    		String countText = "";
    		String timeBetween = "Never";
    		String bossesBetween = "Never";
    		double timeNow = System.currentTimeMillis() / 1000;
    		NumberFormat nf = NumberFormat.getIntegerInstance();
    		
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
							EnumChatFormatting.BLUE + nf.format(lc.wolfWheels) + "\n" +
							EnumChatFormatting.AQUA + lc.wolfSpirits + "\n" + 
							EnumChatFormatting.WHITE + lc.wolfBooks + "\n" +
							EnumChatFormatting.DARK_RED + lc.wolfEggs + "\n" +
							EnumChatFormatting.GOLD + lc.wolfCoutures + "\n" +
							EnumChatFormatting.AQUA + lc.wolfBaits + "\n" +
							EnumChatFormatting.DARK_PURPLE + lc.wolfFluxes + "\n" +
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
							EnumChatFormatting.DARK_GREEN + nf.format(lc.spiderTAP) + "\n" +
							EnumChatFormatting.DARK_GRAY + lc.spiderBites + "\n" + 
							EnumChatFormatting.WHITE + lc.spiderBooks + "\n" +
							EnumChatFormatting.AQUA + lc.spiderCatalysts + "\n" +
							EnumChatFormatting.DARK_PURPLE + lc.spiderTalismans + "\n" +
							EnumChatFormatting.LIGHT_PURPLE + lc.spiderSwatters + "\n" +
							EnumChatFormatting.GOLD + lc.spiderMosquitos + "\n" +
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
							EnumChatFormatting.BLUE + nf.format(lc.zombieFoulFlesh) + "\n" +
							EnumChatFormatting.DARK_GREEN + lc.zombiePestilences + "\n" + 
							EnumChatFormatting.WHITE + lc.zombieBooks + "\n" +
							EnumChatFormatting.AQUA + lc.zombieUndeadCatas + "\n" +
							EnumChatFormatting.DARK_PURPLE + lc.zombieBeheadeds + "\n" +
							EnumChatFormatting.RED + lc.zombieRevCatas + "\n" +
							EnumChatFormatting.DARK_GREEN + lc.zombieSnakes + "\n" +
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
	    					EnumChatFormatting.GOLD + "Good Catches:\n" +
							EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
							EnumChatFormatting.GRAY + "Squids:\n" +
							EnumChatFormatting.GREEN + "Sea Walkers:\n" +
							EnumChatFormatting.DARK_GRAY + "Night Squids:\n" +
							EnumChatFormatting.DARK_AQUA + "Sea Guardians:\n" +
							EnumChatFormatting.BLUE + "Sea Witches:\n" +
							EnumChatFormatting.GREEN + "Sea Archers:\n" +
							EnumChatFormatting.GREEN + "Monster of Deeps:";
    			countText = EnumChatFormatting.AQUA + nf.format(lc.seaCreatures) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.goodCatches) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.greatCatches) + "\n" +
							EnumChatFormatting.GRAY + nf.format(lc.squids) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.seaWalkers) + "\n" +
							EnumChatFormatting.DARK_GRAY + nf.format(lc.nightSquids) + "\n" +
							EnumChatFormatting.DARK_AQUA + nf.format(lc.seaGuardians) + "\n" +
							EnumChatFormatting.BLUE + nf.format(lc.seaWitches) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.seaArchers) + "\n" +
							EnumChatFormatting.GREEN + nf.format(lc.monsterOfTheDeeps);
    			// Seperated to save vertical space
    			String dropsTextTwo = EnumChatFormatting.YELLOW + "Catfishes:\n" +
									  EnumChatFormatting.GOLD + "Carrot Kings:\n" +
									  EnumChatFormatting.GRAY + "Sea Leeches:\n" +
									  EnumChatFormatting.DARK_PURPLE + "Guardian Defenders:\n" +
									  EnumChatFormatting.DARK_PURPLE + "Deep Sea Protectors:\n" +
									  EnumChatFormatting.GOLD + "Hydras:\n" +
									  EnumChatFormatting.GOLD + "Sea Emperors:\n" +
									  EnumChatFormatting.AQUA + "Time Since Emp:\n" +
									  EnumChatFormatting.AQUA + "Creatures Since Emp:";
    			String countTextTwo = EnumChatFormatting.YELLOW + nf.format(lc.catfishes) + "\n" +
									  EnumChatFormatting.GOLD + nf.format(lc.carrotKings) + "\n" +
									  EnumChatFormatting.GRAY + nf.format(lc.seaLeeches) + "\n" +
									  EnumChatFormatting.DARK_PURPLE + nf.format(lc.guardianDefenders) + "\n" +
									  EnumChatFormatting.DARK_PURPLE + nf.format(lc.deepSeaProtectors) + "\n" +
									  EnumChatFormatting.GOLD + nf.format(lc.hydras) + "\n" +
									  EnumChatFormatting.GOLD + nf.format(lc.seaEmperors) + "\n" +
									  EnumChatFormatting.AQUA + timeBetween + "\n" +
									  EnumChatFormatting.AQUA + bossesBetween;
    			
    			new TextRenderer(Minecraft.getMinecraft(), dropsTextTwo, moc.displayXY[0] + 145, moc.displayXY[1], Integer.parseInt("FFFFFF", 16));
    			new TextRenderer(Minecraft.getMinecraft(), countTextTwo, moc.displayXY[0] + 255, moc.displayXY[1], Integer.parseInt("FFFFFF", 16));
    			
    		} else if (ds.display.equals("fishingwinter")) {
    			dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
    						EnumChatFormatting.GOLD + "Good Catches:\n" +
    						EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
	    					EnumChatFormatting.AQUA + "Frozen Steves:\n" +
							EnumChatFormatting.WHITE + "Snowmans:\n" +
							EnumChatFormatting.DARK_GREEN + "Grinches:\n" +
							EnumChatFormatting.GOLD + "Yetis:";
    			countText = EnumChatFormatting.AQUA + nf.format(lc.seaCreatures) + "\n" +
	    					EnumChatFormatting.GOLD + nf.format(lc.goodCatches) + "\n" +
							EnumChatFormatting.DARK_PURPLE + nf.format(lc.greatCatches) + "\n" +
	    					EnumChatFormatting.AQUA + nf.format(lc.frozenSteves) + "\n" +
							EnumChatFormatting.WHITE + nf.format(lc.frostyTheSnowmans) + "\n" +
							EnumChatFormatting.DARK_GREEN + nf.format(lc.grinches) + "\n" +
							EnumChatFormatting.GOLD + nf.format(lc.yetis);
    		}
    		new TextRenderer(Minecraft.getMinecraft(), dropsText, moc.displayXY[0], moc.displayXY[1], Integer.parseInt("FFFFFF", 16));
    		new TextRenderer(Minecraft.getMinecraft(), countText, moc.displayXY[0] + 110, moc.displayXY[1], Integer.parseInt("FFFFFF", 16));
    	}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onSound(final PlaySoundEvent event) {
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
    				
    				int itemTeeth = getItems("Wolf Tooth");
        			int itemWheels = getItems("Hamster Wheel");
        			int itemWebs = getItems("Tarantula Web");
        			int itemTAP = getItems("Toxic Arrow Poison");
        			int itemRev = getItems("Revenant Flesh");
        			int itemFoul = getItems("Foul Flesh");
        			
        			// If no items, are detected, allow check again. Should fix items not being found
        			if (itemTeeth + itemWheels + itemWebs + itemTAP + itemRev + itemFoul > 0) {
        				itemsChecked = System.currentTimeMillis() / 1000;
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
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTooltip(ItemTooltipEvent event) {
    	final ToggleCommand tc = new ToggleCommand();
    	
    	if (event.toolTip == null) return;
    	if (tc.goldenToggled) {
    		for (int i = 0; i < event.toolTip.size(); i++) {
    			event.toolTip.set(i, returnGoldenEnchants(event.toolTip.get(i)));
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
    
    public String returnGoldenEnchants(String line) {
    	Matcher matcher = pattern.matcher(line);
    	StringBuffer out = new StringBuffer();
    	
    	while (matcher.find()) {
    		matcher.appendReplacement(out, t6Enchants.get(matcher.group(1)));
    	}
    	matcher.appendTail(out);
    	
    	return out.toString();
    }
    
    public void increaseEmpSC() {
    	LootCommand lc = new LootCommand();
    	ConfigHandler cf = new ConfigHandler();
    	if (lc.empSCs == -1) return;
    	
    	lc.empSCs++;
    	cf.writeIntConfig("fishing", "empSC", lc.empSCs);
    }
    
}
