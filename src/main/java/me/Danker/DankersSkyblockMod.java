package me.Danker;

import me.Danker.commands.*;
import me.Danker.gui.*;
import me.Danker.handlers.*;
import me.Danker.utils.TicTacToeUtils;
import me.Danker.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.ICommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import org.apache.commons.lang3.time.StopWatch;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod(modid = DankersSkyblockMod.MODID, version = DankersSkyblockMod.VERSION, clientSideOnly = true)
public class DankersSkyblockMod {
    public static final String MODID = "Danker's Skyblock Mod";
    public static final String VERSION = "1.8.6-beta2";
    public static Map<String, String> t6Enchants = new HashMap<>();
    public static Pattern t6EnchantPattern = Pattern.compile("");
    static Pattern petPattern = Pattern.compile("\\[Lvl [\\d]{1,3}]");
    static boolean updateChecked = false;
    public static int titleTimer = -1;
    public static boolean showTitle = false;
    public static String titleText = "";
    public static int SKILL_TIME;
    public static int skillTimer = -1;
    public static boolean showSkill = false;
    public static String skillText = "";
    public static int until = 0;
    public static int lastSlot = -1;
    public static int slotIn = -1;
    static int tickAmount = 1;
    static String lastMaddoxCommand = "/cb placeholder";
    static double lastMaddoxTime = 0;
    static KeyBinding[] keyBindings = new KeyBinding[8];
    static boolean usingLabymod = false;
    static boolean usingOAM = false;
    static boolean OAMWarning = false;
    public static String guiToOpen = null;
    static boolean foundLivid = false;
    static Entity livid = null;
    public static double cakeTime;
    public static double nextBonzoUse = 0;
    public static boolean firstLaunch = false;
    static String[] harpInv = new String[54];
    public static int chestOpen = 0;
    public static long lastInteractTime;

    public static final ResourceLocation CAKE_ICON = new ResourceLocation("dsm", "icons/cake.png");
    public static final ResourceLocation BONZO_ICON = new ResourceLocation("dsm", "icons/bonzo.png");

    static String[] riddleSolutions = {"The reward is not in my chest!", "At least one of them is lying, and the reward is not in",
            "My chest doesn't have the reward. We are all telling the truth", "My chest has the reward and I'm telling the truth",
            "The reward isn't in any of our chests", "Both of them are telling the truth."};
    static Map<String, String[]> triviaSolutions = new HashMap<>();
    static String[] triviaAnswers = null;
    static Entity highestBlaze = null;
    static Entity lowestBlaze = null;
    // Among Us colours
    static final int[] CREEPER_COLOURS = {0x50EF39, 0xC51111, 0x132ED1, 0x117F2D, 0xED54BA, 0xEF7D0D, 0xF5F557, 0xD6E0F0, 0x6B2FBB, 0x39FEDC};
    static boolean drawCreeperLines = false;
    static Vec3 creeperLocation = new Vec3(0, 0, 0);
    static List<Vec3[]> creeperLines = new ArrayList<>();
    static boolean prevInWaterRoom = false;
    static boolean inWaterRoom = false;
    static String waterAnswers = null;
    static AxisAlignedBB correctTicTacToeButton = null;
    static Pattern startsWithTerminalPattern = Pattern.compile("[A-Z]{2,}");
    static Slot[] clickInOrderSlots = new Slot[36];
    static int lastChronomatronRound = 0;
    static List<String> chronomatronPattern = new ArrayList<>();
    static int chronomatronMouseClicks = 0;
    static int lastUltraSequencerClicked = 0;
    static ItemStack[] experimentTableSlots = new ItemStack[54];
    static int pickBlockBind;
    static boolean pickBlockBindSwapped = false;
    static String terminalColorNeeded;
    static int[] terminalNumberNeeded = new int[13];
    static int[] chest = new int[54];

    static double dungeonStartTime = 0;
    static double bloodOpenTime = 0;
    static double watcherClearTime = 0;
    static double bossClearTime = 0;
    static int witherDoors = 0;
    static int dungeonDeaths = 0;
    static int puzzleFails = 0;

    public int mazeId = 0;
    public int sword = 10;
    public int bow = 10;

    static String lastSkill = "Farming";
    public static boolean showSkillTracker;
    public static StopWatch skillStopwatch = new StopWatch();
	static double farmingXP = 0;
	public static double farmingXPGained = 0;
	static double miningXP = 0;
	public static double miningXPGained = 0;
	static double combatXP = 0;
	public static double combatXPGained = 0;
	static double foragingXP = 0;
	public static double foragingXPGained = 0;
	static double fishingXP = 0;
	public static double fishingXPGained = 0;
	static double enchantingXP = 0;
	public static double enchantingXPGained = 0;
	static double alchemyXP = 0;
	public static double alchemyXPGained = 0;
	static double xpLeft = 0;
	static double timeSinceGained = 0;

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
    public static String CAKE_COLOUR;
    public static String SKILL_TRACKER_COLOUR;
    public static String TRIVIA_WRONG_ANSWER_COLOUR;
    public static String BONZO_COLOR;
    public static int LOWEST_BLAZE_COLOUR;
    public static int HIGHEST_BLAZE_COLOUR;
    public static int PET_1_TO_9;
    public static int PET_10_TO_19;
    public static int PET_20_TO_29;
    public static int PET_30_TO_39;
    public static int PET_40_TO_49;
    public static int PET_50_TO_59;
    public static int PET_60_TO_69;
    public static int PET_70_TO_79;
    public static int PET_80_TO_89;
    public static int PET_90_TO_99;
    public static int PET_100;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new PacketHandler());

        ConfigHandler.reloadConfig();

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

        triviaSolutions.put("What is the status of The Watcher?", new String[]{"Stalker"});
        triviaSolutions.put("What is the status of Bonzo?", new String[]{"New Necromancer"});
        triviaSolutions.put("What is the status of Scarf?", new String[]{"Apprentice Necromancer"});
        triviaSolutions.put("What is the status of The Professor?", new String[]{"Professor"});
        triviaSolutions.put("What is the status of Thorn?", new String[]{"Shaman Necromancer"});
        triviaSolutions.put("What is the status of Livid?", new String[]{"Master Necromancer"});
        triviaSolutions.put("What is the status of Sadan?", new String[]{"Necromancer Lord"});
        triviaSolutions.put("What is the status of Maxor?", new String[]{"Young Wither"});
        triviaSolutions.put("What is the status of Goldor?", new String[]{"Wither Soldier"});
        triviaSolutions.put("What is the status of Storm?", new String[]{"Elementalist"});
        triviaSolutions.put("What is the status of Necron?", new String[]{"Wither Lord"});
        triviaSolutions.put("How many total Fairy Souls are there?", new String[]{"220 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Spider's Den?", new String[]{"17 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in The End?", new String[]{"12 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in The Barn?", new String[]{"7 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Mushroom Desert?", new String[]{"8 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Blazing Fortress?", new String[]{"19 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in The Park?", new String[]{"11 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Jerry's Workshop?", new String[]{"5 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Hub?", new String[]{"79 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in The Hub?", new String[]{"79 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Deep Caverns?", new String[]{"21 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Gold Mine?", new String[]{"12 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Dungeon Hub?", new String[]{"7 Fairy Souls"});
        triviaSolutions.put("Which brother is on the Spider's Den?", new String[]{"Rick"});
        triviaSolutions.put("What is the name of Rick's brother?", new String[]{"Pat"});
        triviaSolutions.put("What is the name of the Painter in the Hub?", new String[]{"Marco"});
        triviaSolutions.put("What is the name of the person that upgrades pets?", new String[]{"Kat"});
        triviaSolutions.put("What is the name of the lady of the Nether?", new String[]{"Elle"});
        triviaSolutions.put("Which villager in the Village gives you a Rogue Sword?", new String[]{"Jamie"});
        triviaSolutions.put("How many unique minions are there?", new String[]{"53 Minions"});
        triviaSolutions.put("Which of these enemies does not spawn in the Spider's Den?", new String[]{"Zombie Spider", "Cave Spider", "Wither Skeleton",
                "Dashing Spooder", "Broodfather", "Night Spider"});
        triviaSolutions.put("Which of these monsters only spawns at night?", new String[]{"Zombie Villager", "Ghast"});
        triviaSolutions.put("Which of these is not a dragon in The End?", new String[]{"Zoomer Dragon", "Weak Dragon", "Stonk Dragon", "Holy Dragon", "Boomer Dragon",
                "Booger Dragon", "Older Dragon", "Elder Dragon", "Stable Dragon", "Professor Dragon"});

        String patternString = "(" + String.join("|", t6Enchants.keySet()) + ")";
        t6EnchantPattern = Pattern.compile(patternString);

        keyBindings[0] = new KeyBinding("Open Maddox Menu", Keyboard.KEY_M, "Danker's Skyblock Mod");
        keyBindings[1] = new KeyBinding("Regular Ability", Keyboard.KEY_NUMPAD4, "Danker's Skyblock Mod");
        keyBindings[2] = new KeyBinding("Start/Stop Skill Tracker", Keyboard.KEY_NUMPAD5, "Danker's Skyblock Mod");
        keyBindings[3] = new KeyBinding("Bone Macro", Keyboard.KEY_B, "Danker's Skyblock Mod");
        keyBindings[4] = new KeyBinding("Reset Fake ID", Keyboard.KEY_P, "Danker's Skyblock Mod");
        keyBindings[5] = new KeyBinding("Right Click Spam", Keyboard.KEY_X, "Danker's Skyblock Mod");
        keyBindings[6] = new KeyBinding("Hyperion Bind", Keyboard.KEY_I, "Danker's Skyblock Mod");
        keyBindings[7] = new KeyBinding("Ghost Block Bind", Keyboard.KEY_G, "Danker's Skyblock Mod");

        for (KeyBinding keyBinding : keyBindings) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
    	ClientCommandHandler.instance.registerCommand(new ToggleCommand());
    	ClientCommandHandler.instance.registerCommand(new SetkeyCommand());
    	ClientCommandHandler.instance.registerCommand(new GetkeyCommand());
        ClientCommandHandler.instance.registerCommand(new DelayCommand());
        ClientCommandHandler.instance.registerCommand(new SimonCommand());
        ClientCommandHandler.instance.registerCommand(new SwapCommand());
        ClientCommandHandler.instance.registerCommand(new ToggleNoRotateCommand());
        ClientCommandHandler.instance.registerCommand(new SleepCommand());
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
    	ClientCommandHandler.instance.registerCommand(new SkyblockPlayersCommand());
    	ClientCommandHandler.instance.registerCommand(new BlockSlayerCommand());
    	ClientCommandHandler.instance.registerCommand(new DungeonsCommand());
    	ClientCommandHandler.instance.registerCommand(new LobbySkillsCommand());
    	ClientCommandHandler.instance.registerCommand(new DankerGuiCommand());
		ClientCommandHandler.instance.registerCommand(new SkillTrackerCommand());
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
		Package[] packages = Package.getPackages();
		for(Package p : packages){
			if(p.getName().startsWith("com.spiderfrog.gadgets") || p.getName().startsWith("com.spiderfrog.oldanimations")){
				usingOAM = true;
			}
		}
		System.out.println("OAM detection: " + usingOAM);

    	usingLabymod = Loader.isModLoaded("labymod");
    	System.out.println("LabyMod detection: " + usingLabymod);

        if(!ClientCommandHandler.instance.getCommands().containsKey("reparty")) {
            ClientCommandHandler.instance.registerCommand(new RepartyCommand());
        } else if (ConfigHandler.getBoolean("commands", "reparty")) {
            for(Map.Entry<String, ICommand> entry : ClientCommandHandler.instance.getCommands().entrySet()) {
                if (entry.getKey().equals("reparty") || entry.getKey().equals("rp")) {
                    entry.setValue(new RepartyCommand());
                }
            }
        }

    }

    @SubscribeEvent
	public void onGuiOpenEvent(GuiOpenEvent event){
		if(event.gui instanceof GuiMainMenu && usingOAM && !OAMWarning){
			if(!(event.gui instanceof WarningGui)){
				event.gui = new WarningGuiRedirect(new WarningGui());
				OAMWarning = true;
			}
		}
	}

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        foundLivid = false;
        livid = null;
        nextBonzoUse = 0;
        mazeId = 0;
    }

    // It randomly broke, so I had to make it the highest priority
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
    	String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.startsWith("Your new API key is ") && Utils.isOnHypixel()) {
            String apiKey = event.message.getSiblings().get(0).getChatStyle().getChatClickEvent().getValue();
            ConfigHandler.writeStringConfig("api", "APIKey", apiKey);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Set API key to " + DankersSkyblockMod.SECONDARY_COLOUR + apiKey));
        }

		// Reparty command
        // Getting party
        if (RepartyCommand.gettingParty) {
            if (message.contains("-----")) {
                switch(RepartyCommand.Delimiter) {
                    case 0:
                        System.out.println("Get Party Delimiter Cancelled");
                        RepartyCommand.Delimiter++;
                        event.setCanceled(true);
                        return;
                    case 1:
                        System.out.println("Done querying party");
                        RepartyCommand.gettingParty = false;
                        RepartyCommand.Delimiter = 0;
                        event.setCanceled(true);
                        return;
                }
            }else if (message.startsWith("Party M") || message.startsWith("Party Leader")){
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

                Pattern party_start_pattern = Pattern.compile("^Party Members \\((\\d+)\\)$");
                Pattern leader_pattern = Pattern.compile("^Party Leader: (?:\\[.+?] )?(\\w+) ●$");
                Pattern members_pattern = Pattern.compile(" (?:\\[.+?] )?(\\w+) ●");
                Matcher party_start = party_start_pattern.matcher(message);
                Matcher leader = leader_pattern.matcher(message);
                Matcher members = members_pattern.matcher(message);

                if (party_start.matches() && Integer.parseInt(party_start.group(1)) == 1) {
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "You cannot reparty yourself."));
                    RepartyCommand.partyThread.interrupt();
                } else if (leader.matches() && !(leader.group(1).equals(player.getName()))) {
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "You are not party leader."));
                    RepartyCommand.partyThread.interrupt();
                } else {
                    while (members.find()) {
                        String partyMember = members.group(1);
                        if (!partyMember.equals(player.getName())) {
                            RepartyCommand.party.add(partyMember);
                            System.out.println(partyMember);
                        }
                    }
                }
                event.setCanceled(true);
                return;
            }
        }
        // Disbanding party
        if (RepartyCommand.disbanding) {
            if (message.contains("-----")) {
                switch (RepartyCommand.Delimiter) {
                    case 0:
                        System.out.println("Disband Delimiter Cancelled");
                        RepartyCommand.Delimiter++;
                        event.setCanceled(true);
                        return;
                    case 1:
                        System.out.println("Done disbanding");
                        RepartyCommand.disbanding = false;
                        RepartyCommand.Delimiter = 0;
                        event.setCanceled(true);
                        return;
                }
            } else if (message.endsWith("has disbanded the party!")) {
                event.setCanceled(true);
                return;
            }
        }
        // Inviting
        if (RepartyCommand.inviting) {
            if (message.contains("-----")) {
                switch (RepartyCommand.Delimiter) {
                    case 1:
                        event.setCanceled(true);
                        RepartyCommand.Delimiter = 0;
                        System.out.println("Player Invited!");
                        RepartyCommand.inviting = false;
                        return;
                    case 0:
                        RepartyCommand.Delimiter++;
                        event.setCanceled(true);
                        return;
                }
            } else if (message.endsWith(" to the party! They have 60 seconds to accept.")) {
                Pattern invitePattern = Pattern.compile("(?:(?:\\[.+?] )?(?:\\w+) invited )(?:\\[.+?] )?(\\w+)");
                Matcher invitee = invitePattern.matcher(message);
                if (invitee.find()) {
                    System.out.println("" + invitee.group(1) + ": " + RepartyCommand.repartyFailList.remove(invitee.group(1)));
                }
                event.setCanceled(true);
                return;
            } else if (message.contains("Couldn't find a player") || message.contains("You cannot invite that player")) {
                event.setCanceled(true);
                return;
            }
        }
        // Fail Inviting
        if (RepartyCommand.failInviting) {
            if (message.contains("-----")) {
                switch (RepartyCommand.Delimiter) {
                    case 1:
                        event.setCanceled(true);
                        RepartyCommand.Delimiter = 0;
                        System.out.println("Player Invited!");
                        RepartyCommand.inviting = false;
                        return;
                    case 0:
                        RepartyCommand.Delimiter++;
                        event.setCanceled(true);
                        return;
                }
            } else if (message.endsWith(" to the party! They have 60 seconds to accept.")) {
                Pattern invitePattern = Pattern.compile("(?:(?:\\[.+?] )?(?:\\w+) invited )(?:\\[.+?] )?(\\w+)");
                Matcher invitee = invitePattern.matcher(message);
                if (invitee.find()) {
                    System.out.println("" + invitee.group(1) + ": " + RepartyCommand.repartyFailList.remove(invitee.group(1)));
                }
                event.setCanceled(true);
                return;
            } else if (message.contains("Couldn't find a player") || message.contains("You cannot invite that player")) {
                event.setCanceled(true);
                return;
            }
        }

    	if (!Utils.inSkyblock) return;

    	// Action Bar
    	if (event.type == 2) {
			EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
			String[] actionBarSections = event.message.getUnformattedText().split(" {3,}");

			for (String section : actionBarSections) {
    			if (section.contains("+") && section.contains("/") && section.contains("(")) {
    				if (!section.contains("Runecrafting") && !section.contains("Carpentry")) {
						if (ToggleCommand.autoSkillTrackerToggled && System.currentTimeMillis() / 1000 - timeSinceGained <= 2) {
							if (skillStopwatch.isStarted() && skillStopwatch.isSuspended()) {
								skillStopwatch.resume();
							} else if (!skillStopwatch.isStarted()) {
								skillStopwatch.start();
							}
						}
						timeSinceGained = System.currentTimeMillis() / 1000;

						int limit = section.contains("Farming") || section.contains("Enchanting") ? 60 : 50;
						double currentXP = Double.parseDouble(section.substring(section.indexOf("(") + 1, section.indexOf("/")).replace(",", ""));
    					int xpToLevelUp = Integer.parseInt(section.substring(section.indexOf("/") + 1, section.indexOf(")")).replaceAll(",", ""));
    					xpLeft = xpToLevelUp - currentXP;
    					int previousXP = Utils.getPastXpEarned(xpToLevelUp, limit);
						double totalXP = currentXP + previousXP;

    					String skill = section.substring(section.indexOf(" ") + 1, section.lastIndexOf(" "));
    					switch (skill) {
	    					case "Farming":
	    						lastSkill = "Farming";
	    						if (farmingXP != 0) {
	    							if (skillStopwatch.isStarted() && !skillStopwatch.isSuspended()) farmingXPGained += totalXP - farmingXP;
	    						}
								farmingXP = totalXP;
	    						break;
	    					case "Mining":
	    						lastSkill = "Mining";
	    						if (miningXP != 0) {
	    							if (skillStopwatch.isStarted() && !skillStopwatch.isSuspended()) miningXPGained += totalXP - miningXP;
	    						}
								miningXP = totalXP;
	    						break;
	    					case "Combat":
	    						lastSkill = "Combat";
	    						if (combatXP != 0) {
	    							if (skillStopwatch.isStarted() && !skillStopwatch.isSuspended()) combatXPGained += totalXP - combatXP;
	    						}
								combatXP = totalXP;
	    						break;
	    					case "Foraging":
	    						lastSkill = "Foraging";
	    						if (foragingXP != 0) {
	    							if (skillStopwatch.isStarted() && !skillStopwatch.isSuspended()) foragingXPGained += totalXP - foragingXP;
	    						}
								foragingXP = totalXP;
	    						break;
	    					case "Fishing":
	    						lastSkill = "Fishing";
	    						if (fishingXP != 0) {
	    							if (skillStopwatch.isStarted() && !skillStopwatch.isSuspended()) fishingXPGained += totalXP - fishingXP;
	    						}
								fishingXP = totalXP;
	    						break;
	    					case "Enchanting":
	    						lastSkill = "Enchanting";
	    						if (enchantingXP != 0) {
	    							if (skillStopwatch.isStarted() && !skillStopwatch.isSuspended()) enchantingXPGained += totalXP - enchantingXP;
	    						}
								enchantingXP = totalXP;
	    						break;
	    					case "Alchemy":
	    						lastSkill = "Alchemy";
	    						if (alchemyXP != 0) {
	    							if (skillStopwatch.isStarted() && !skillStopwatch.isSuspended()) alchemyXPGained += totalXP - alchemyXP;
	    						}
								alchemyXP = totalXP;
	    						break;
	    					default:
	    						System.err.println("Unknown skill.");
    					}
    				}

    				if (ToggleCommand.skill50DisplayToggled && !section.contains("Runecrafting")) {
    					String xpGained = section.substring(section.indexOf("+"), section.indexOf("(") - 1);
    					double currentXp = Double.parseDouble(section.substring(section.indexOf("(") + 1, section.indexOf("/")).replace(",", ""));
    					int limit;
    					int totalXp;
    					if (section.contains("Farming") || section.contains("Enchanting")) {
    						limit = 60;
    						totalXp = 111672425;
    					} else {
    						limit = 50;
    						totalXp = 55172425;
    					}
    					int previousXp = Utils.getPastXpEarned(Integer.parseInt(section.substring(section.indexOf("/") + 1, section.indexOf(")")).replaceAll(",", "")), limit);
    					double percentage = Math.floor(((currentXp + previousXp) / totalXp) * 10000D) / 100D;

    					NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
    					skillTimer = SKILL_TIME;
    					showSkill = true;
    					skillText = SKILL_50_COLOUR + xpGained + " (" + nf.format(currentXp + previousXp) + "/" + nf.format(totalXp) + ") " + percentage + "%";
    				}
    			}
    		}
    		return;
    	}

        if (ToggleCommand.bonzoTimerToggled && Utils.inDungeons && message.contains("Bonzo's Mask") && message.contains("saved your life!")) {
            double usedTime = System.currentTimeMillis() / 1000;
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.thePlayer;
            ItemStack bonzoMask = player.getCurrentArmor(3);
            if (bonzoMask != null && bonzoMask.getItem() == Items.skull) {
                int cooldownSeconds = 0;
                for (String line : Utils.getItemLore(bonzoMask)) {
                    String stripped = StringUtils.stripControlCodes(line);
                    if (stripped.startsWith("Cooldown: "))
                        cooldownSeconds = Integer.parseInt(stripped.replaceAll("[^\\d]", ""));
                }
                System.out.println("Parsed Bonzo Mask Cooldown: " + cooldownSeconds);
                if (cooldownSeconds > 0)
                    nextBonzoUse = usedTime + cooldownSeconds;
            }
        }

        // Dungeon chat spoken by an NPC, containing :
        if (ToggleCommand.threeManToggled && Utils.inDungeons && message.contains("[NPC]")) {
            for (String solution : riddleSolutions) {
                if (message.contains(solution)) {
                    String npcName = message.substring(message.indexOf("]") + 2, message.indexOf(":"));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ANSWER_COLOUR + EnumChatFormatting.BOLD + StringUtils.stripControlCodes(npcName) + MAIN_COLOUR + " has the blessing."));
                    break;
                }
            }
        }

        if (ToggleCommand.necronNotificationsToggled && Utils.inDungeons && message.contains("[BOSS] Necron:")) {
            Minecraft mc = Minecraft.getMinecraft();
            World world = mc.theWorld;
            if (message.contains("You tricked me!") || message.contains("That beam, it hurts! IT HURTS!!")) {
                Utils.createTitle(EnumChatFormatting.RED + "NECRON STUCK!", 2);
            } else if (message.contains("STOP USING MY FACTORY AGAINST ME!") || message.contains("OOF") || message.contains("ANOTHER TRAP!! YOUR TRICKS ARE FUTILE!") || message.contains("SERIOUSLY? AGAIN?!") || message.contains("STOP!!!!!")) {
                List<EntityArmorStand> necronLabels = world.getEntities(EntityArmorStand.class, (entity -> {
                    if (!entity.hasCustomName()) return false;
                    if (!entity.getCustomNameTag().contains("Necron")) return false;
                    return true;
                }));
                if (necronLabels.size() == 0) {
                    Utils.createTitle(EnumChatFormatting.WHITE + "NECRON STUNNED!", 2);
                } else {
                    EntityArmorStand necron = necronLabels.get(0);
                    double x = necron.posX;
                    double z = necron.posZ;

                    BlockPos blockPos = new BlockPos(x, 168, z);

                    IBlockState blockState = world.getBlockState(blockPos);
                    Block block = blockState.getBlock();

                    if (block != Blocks.stained_hardened_clay) {
                        Utils.createTitle(EnumChatFormatting.WHITE + "NECRON STUNNED!", 2);
                    } else {
                        switch (block.getDamageValue(world, blockPos)) {
                            case 4:
                                Utils.createTitle(EnumChatFormatting.YELLOW + "YELLOW PILLAR!", 2);
                                break;
                            case 5:
                                Utils.createTitle(EnumChatFormatting.GREEN + "LIME PILLAR!", 2);
                                break;
                            case 11:
                                Utils.createTitle(EnumChatFormatting.BLUE + "BLUE PILLAR!", 2);
                                break;
                            default:
                                Utils.createTitle(EnumChatFormatting.WHITE + "NECRON STUNNED!", 2);
                        }
                    }

                }
            } else if (message.contains("I'VE HAD ENOUGH! YOU'RE NOT HITTING ME WITH ANY MORE PILLARS!")) {
                Utils.createTitle(EnumChatFormatting.RED + "RED PILLAR!", 2);
            } else if (message.contains("ARGH!")) {
                Utils.createTitle(EnumChatFormatting.RED + "EXPLOSION OVER!", 2);
            }
        }

        if (message.contains("[BOSS] The Watcher: You have proven yourself. You may pass.")) {
            watcherClearTime = System.currentTimeMillis() / 1000;
        }
        if (message.contains("[BOSS] The Watcher: That will be enough for now.")) {
            if (ToggleCommand.watcherReadyToggled) Utils.createTitle(EnumChatFormatting.RED + "WATCHER READY", 2);
        }
        if (message.contains("PUZZLE FAIL! ") || message.contains("chose the wrong answer! I shall never forget this moment")) {
            puzzleFails++;
        }

        if (message.contains(":")) return;

        // Spirit Sceptre
        if (!ToggleCommand.sceptreMessages && message.contains("Your Spirit Sceptre hit ")) {
            event.setCanceled(true);
            return;
        }
        // Midas Staff
        if (!ToggleCommand.midasStaffMessages && message.contains("Your Molten Wave hit ")) {
            event.setCanceled(true);
            return;
        }
        // Heals
        if (!ToggleCommand.healMessages && message.contains(" health!") && (message.contains("You healed ") || message.contains(" healed you for "))) {
            event.setCanceled(true);
            return;
        }
        // Ability Cooldown
        if (!ToggleCommand.cooldownMessages && message.contains("This ability is on cooldown for ")) {
          event.setCanceled(true);
          return;
        }
        // Out of mana messages
        if (!ToggleCommand.manaMessages && message.contains("You do not have enough mana to do this!")) {
          event.setCanceled(true);
          return;
        }
        // Implosion
        if (!ToggleCommand.implosionMessages) {
            if (message.contains("Your Implosion hit ") || message.contains("There are blocks in the way")) {
                event.setCanceled(true);
                return;
            }
        }

        if (ToggleCommand.oruoToggled && Utils.inDungeons) {
        	if (message.contains("What SkyBlock year is it?")) {
                double currentTime = System.currentTimeMillis() /1000L;

                double diff = Math.floor(currentTime - 1560276000);

                int year = (int) (diff / 446400 + 1);
                triviaAnswers = new String[]{"Year " + year};
            } else {
                for (String question : triviaSolutions.keySet()) {
                    if (message.contains(question)) {
                        triviaAnswers = triviaSolutions.get(question);
                        break;
                    }
                }
            }

        	// Set wrong answers to red and remove click events
        	if (triviaAnswers != null && (message.contains("ⓐ") || message.contains("ⓑ") || message.contains("ⓒ"))) {
        		boolean isSolution = false;
        		for (String solution : triviaAnswers) {
        			if (message.contains(solution)) {
        				isSolution = true;
        				break;
					}
        		}
        		if (!isSolution) {
        			char letter = message.charAt(5);
        			String option = message.substring(6);
        			event.message = new ChatComponentText("     " + EnumChatFormatting.GOLD + letter + TRIVIA_WRONG_ANSWER_COLOUR + option);
        			return;
        		}
        	}
        }

		if (ToggleCommand.gpartyToggled) {
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
					ex.printStackTrace();
				}
			}
		}

		if (ToggleCommand.golemAlertToggled) {
			if (message.contains("The ground begins to shake as an Endstone Protector rises from below!")) {
				Utils.createTitle(EnumChatFormatting.RED + "GOLEM SPAWNING!", 3);
			}
		}

		if (message.contains("Yum! You gain +") && message.contains(" for 48 hours!")) {
			cakeTime = System.currentTimeMillis() / 1000 + 172800; // Add 48 hours
			ConfigHandler.writeDoubleConfig("misc", "cakeTime", cakeTime);
		}

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
					LootCommand.wolfBooks++;
					ConfigHandler.writeIntConfig("wolf", "book", LootCommand.wolfBooks);
				} else if (sCleaned.contains("Tarantula Broodfather")) {
					LootCommand.spiderBooks++;
					ConfigHandler.writeIntConfig("spider", "book", LootCommand.spiderBooks);
				} else if (sCleaned.contains("Revenant Horror")) {
					LootCommand.zombieBooks++;
					ConfigHandler.writeIntConfig("zombie", "book", LootCommand.zombieBooks);
				}
			}
		}

		// Wolf
		if (message.contains("Talk to Maddox to claim your Wolf Slayer XP!")) {
			LootCommand.wolfSvens++;
			LootCommand.wolfSvensSession++;
			if (LootCommand.wolfBosses != -1) {
				LootCommand.wolfBosses++;
			}
			if (LootCommand.wolfBossesSession != -1) {
				LootCommand.wolfBossesSession++;
			}
			ConfigHandler.writeIntConfig("wolf", "svens", LootCommand.wolfSvens);
			ConfigHandler.writeIntConfig("wolf", "bossRNG", LootCommand.wolfBosses);
		} else if (message.contains("RARE DROP! (Hamster Wheel)")) {
			LootCommand.wolfWheelsDrops++;
			LootCommand.wolfWheelsDropsSession++;
			ConfigHandler.writeIntConfig("wolf", "wheelDrops", LootCommand.wolfWheelsDrops);
		} else if (message.contains("VERY RARE DROP!  (") && message.contains(" Spirit Rune I)")) { // Removing the unicode here *should* fix rune drops not counting
			LootCommand.wolfSpirits++;
			LootCommand.wolfSpiritsSession++;
			ConfigHandler.writeIntConfig("wolf", "spirit", LootCommand.wolfSpirits);
		} else if (message.contains("CRAZY RARE DROP!  (Red Claw Egg)")) {
			wolfRNG = true;
			LootCommand.wolfEggs++;
			LootCommand.wolfEggsSession++;
			ConfigHandler.writeIntConfig("wolf", "egg", LootCommand.wolfEggs);
			if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_RED + "RED CLAW EGG!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Couture Rune I)")) {
			wolfRNG = true;
			LootCommand.wolfCoutures++;
			LootCommand.wolfCouturesSession++;
			ConfigHandler.writeIntConfig("wolf", "couture", LootCommand.wolfCoutures);
			if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "COUTURE RUNE!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (Grizzly Bait)") || message.contains("CRAZY RARE DROP! (Rename Me)")) { // How did Skyblock devs even manage to make this item Rename Me
			wolfRNG = true;
			LootCommand.wolfBaits++;
			LootCommand.wolfBaitsSession++;
			ConfigHandler.writeIntConfig("wolf", "bait", LootCommand.wolfBaits);
			if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.AQUA + "GRIZZLY BAIT!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (Overflux Capacitor)")) {
			wolfRNG = true;
			LootCommand.wolfFluxes++;
			LootCommand.wolfFluxesSession++;
			ConfigHandler.writeIntConfig("wolf", "flux", LootCommand.wolfFluxes);
			if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "OVERFLUX CAPACITOR!", 5);
		} else if (message.contains("Talk to Maddox to claim your Spider Slayer XP!")) { // Spider
			LootCommand.spiderTarantulas++;
			LootCommand.spiderTarantulasSession++;
			if (LootCommand.spiderBosses != -1) {
				LootCommand.spiderBosses++;
			}
			if (LootCommand.spiderBossesSession != -1) {
				LootCommand.spiderBossesSession++;
			}
			ConfigHandler.writeIntConfig("spider", "tarantulas", LootCommand.spiderTarantulas);
			ConfigHandler.writeIntConfig("spider", "bossRNG", LootCommand.spiderBosses);
		} else if (message.contains("RARE DROP! (Toxic Arrow Poison)")) {
			LootCommand.spiderTAPDrops++;
			LootCommand.spiderTAPDropsSession++;
			ConfigHandler.writeIntConfig("spider", "tapDrops", LootCommand.spiderTAPDrops);
		} else if (message.contains("VERY RARE DROP!  (") && message.contains(" Bite Rune I)")) {
			LootCommand.spiderBites++;
			LootCommand.spiderBitesSession++;
			ConfigHandler.writeIntConfig("spider", "bite", LootCommand.spiderBites);
		} else if (message.contains("VERY RARE DROP!  (Spider Catalyst)")) {
			LootCommand.spiderCatalysts++;
			LootCommand.spiderCatalystsSession++;
			ConfigHandler.writeIntConfig("spider", "catalyst", LootCommand.spiderCatalysts);
		} else if (message.contains("CRAZY RARE DROP!  (Fly Swatter)")) {
			spiderRNG = true;
			LootCommand.spiderSwatters++;
			LootCommand.spiderSwattersSession++;
			ConfigHandler.writeIntConfig("spider", "swatter", LootCommand.spiderSwatters);
			if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.LIGHT_PURPLE + "FLY SWATTER!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (Tarantula Talisman")) {
			spiderRNG = true;
			LootCommand.spiderTalismans++;
			LootCommand.spiderTalismansSession++;
			ConfigHandler.writeIntConfig("spider", "talisman", LootCommand.spiderTalismans);
			if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "TARANTULA TALISMAN!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (Digested Mosquito)")) {
			spiderRNG = true;
			LootCommand.spiderMosquitos++;
			LootCommand.spiderMosquitosSession++;
			ConfigHandler.writeIntConfig("spider", "mosquito", LootCommand.spiderMosquitos);
			if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "DIGESTED MOSQUITO!", 5);
		} else if (message.contains("Talk to Maddox to claim your Zombie Slayer XP!")) { // Zombie
			LootCommand.zombieRevs++;
			LootCommand.zombieRevsSession++;
			if (LootCommand.zombieBosses != -1) {
				LootCommand.zombieBosses++;
			}
			if (LootCommand.zombieBossesSession != 1) {
				LootCommand.zombieBossesSession++;
			}
			ConfigHandler.writeIntConfig("zombie", "revs", LootCommand.zombieRevs);
			ConfigHandler.writeIntConfig("zombie", "bossRNG", LootCommand.zombieBosses);
		} else if (message.contains("RARE DROP! (Foul Flesh)")) {
			LootCommand.zombieFoulFleshDrops++;
			LootCommand.zombieFoulFleshDropsSession++;
			ConfigHandler.writeIntConfig("zombie", "foulFleshDrops", LootCommand.zombieFoulFleshDrops);
		} else if (message.contains("VERY RARE DROP!  (Revenant Catalyst)")) {
			LootCommand.zombieRevCatas++;
			LootCommand.zombieRevCatasSession++;
			ConfigHandler.writeIntConfig("zombie", "revCatalyst", LootCommand.zombieRevCatas);
		} else if (message.contains("VERY RARE DROP!  (") && message.contains(" Pestilence Rune I)")) {
			LootCommand.zombiePestilences++;
			LootCommand.zombiePestilencesSession++;
			ConfigHandler.writeIntConfig("zombie", "pestilence", LootCommand.zombiePestilences);
		} else if (message.contains("VERY RARE DROP!  (Undead Catalyst)")) {
			LootCommand.zombieUndeadCatas++;
			LootCommand.zombieUndeadCatasSession++;
			ConfigHandler.writeIntConfig("zombie", "undeadCatalyst", LootCommand.zombieUndeadCatas);
		} else if (message.contains("CRAZY RARE DROP!  (Beheaded Horror)")) {
			zombieRNG = true;
			LootCommand.zombieBeheadeds++;
			LootCommand.zombieBeheadedsSession++;
			ConfigHandler.writeIntConfig("zombie", "beheaded", LootCommand.zombieBeheadeds);
			if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "BEHEADED HORROR!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Snake Rune I)")) {
			zombieRNG = true;
			LootCommand.zombieSnakes++;
			LootCommand.zombieSnakesSession++;
			ConfigHandler.writeIntConfig("zombie", "snake", LootCommand.zombieSnakes);
			if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_GREEN + "SNAKE RUNE!", 3);
		} else if (message.contains("CRAZY RARE DROP!  (Scythe Blade)")) {
			zombieRNG = true;
			LootCommand.zombieScythes++;
			LootCommand.zombieScythesSession++;
			ConfigHandler.writeIntConfig("zombie", "scythe", LootCommand.zombieScythes);
			if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "SCYTHE BLADE!", 5);
		} else if (message.contains("GOOD CATCH!")) { // Fishing
			LootCommand.goodCatches++;
			LootCommand.goodCatchesSession++;
			ConfigHandler.writeIntConfig("fishing", "goodCatch", LootCommand.goodCatches);
		} else if (message.contains("GREAT CATCH!")) {
			LootCommand.greatCatches++;
			LootCommand.greatCatchesSession++;
			ConfigHandler.writeIntConfig("fishing", "greatCatch", LootCommand.greatCatches);
		} else if (message.contains("A Squid appeared")) {
			LootCommand.squids++;
			LootCommand.squidsSession++;
			ConfigHandler.writeIntConfig("fishing", "squid", LootCommand.squids);
			increaseSeaCreatures();
		} else if (message.contains("You caught a Sea Walker")) {
			LootCommand.seaWalkers++;
			LootCommand.seaWalkersSession++;
			ConfigHandler.writeIntConfig("fishing", "seaWalker", LootCommand.seaWalkers);
			increaseSeaCreatures();
		} else if (message.contains("Pitch darkness reveals a Night Squid")) {
			LootCommand.nightSquids++;
			LootCommand.nightSquidsSession++;
			ConfigHandler.writeIntConfig("fishing", "nightSquid", LootCommand.nightSquids);
			increaseSeaCreatures();
		} else if (message.contains("You stumbled upon a Sea Guardian")) {
			LootCommand.seaGuardians++;
			LootCommand.seaGuardiansSession++;
			ConfigHandler.writeIntConfig("fishing", "seaGuardian", LootCommand.seaGuardians);
			increaseSeaCreatures();
		} else if (message.contains("It looks like you've disrupted the Sea Witch's brewing session. Watch out, she's furious")) {
			LootCommand.seaWitches++;
			LootCommand.seaWitchesSession++;
			ConfigHandler.writeIntConfig("fishing", "seaWitch", LootCommand.seaWitches);
			increaseSeaCreatures();
		} else if (message.contains("You reeled in a Sea Archer")) {
			LootCommand.seaArchers++;
			LootCommand.seaArchersSession++;
			ConfigHandler.writeIntConfig("fishing", "seaArcher", LootCommand.seaArchers);
			increaseSeaCreatures();
		} else if (message.contains("The Monster of the Deep has emerged")) {
			LootCommand.monsterOfTheDeeps++;
			LootCommand.monsterOfTheDeepsSession++;
			ConfigHandler.writeIntConfig("fishing", "monsterOfDeep", LootCommand.monsterOfTheDeeps);
			increaseSeaCreatures();
		} else if (message.contains("Huh? A Catfish")) {
			LootCommand.catfishes++;
			LootCommand.catfishesSession++;
			ConfigHandler.writeIntConfig("fishing", "catfish", LootCommand.catfishes);
			increaseSeaCreatures();
		} else if (message.contains("Is this even a fish? It's the Carrot King")) {
			LootCommand.carrotKings++;
			LootCommand.carrotKingsSession++;
			ConfigHandler.writeIntConfig("fishing", "carrotKing", LootCommand.carrotKings);
			increaseSeaCreatures();
		} else if (message.contains("Gross! A Sea Leech")) {
			LootCommand.seaLeeches++;
			LootCommand.seaLeechesSession++;
			ConfigHandler.writeIntConfig("fishing", "seaLeech", LootCommand.seaLeeches);
			increaseSeaCreatures();
		} else if (message.contains("You've discovered a Guardian Defender of the sea")) {
			LootCommand.guardianDefenders++;
			LootCommand.guardianDefendersSession++;
			ConfigHandler.writeIntConfig("fishing", "guardianDefender", LootCommand.guardianDefenders);
			increaseSeaCreatures();
		} else if (message.contains("You have awoken the Deep Sea Protector, prepare for a battle")) {
			LootCommand.deepSeaProtectors++;
			LootCommand.deepSeaProtectorsSession++;
			ConfigHandler.writeIntConfig("fishing", "deepSeaProtector", LootCommand.deepSeaProtectors);
			increaseSeaCreatures();
		} else if (message.contains("The Water Hydra has come to test your strength")) {
			LootCommand.hydras++;
			LootCommand.hydrasSession++;
			ConfigHandler.writeIntConfig("fishing", "hydra", LootCommand.hydras);
			increaseSeaCreatures();
		} else if (message.contains("The Sea Emperor arises from the depths")) {
			increaseSeaCreatures();

			LootCommand.seaEmperors++;
			LootCommand.empTime = System.currentTimeMillis() / 1000;
			LootCommand.empSCs = 0;
			LootCommand.seaEmperorsSession++;
			LootCommand.empTimeSession = System.currentTimeMillis() / 1000;
			LootCommand.empSCsSession = 0;
			ConfigHandler.writeIntConfig("fishing", "seaEmperor", LootCommand.seaEmperors);
			ConfigHandler.writeDoubleConfig("fishing", "empTime", LootCommand.empTime);
			ConfigHandler.writeIntConfig("fishing", "empSC", LootCommand.empSCs);
		} else if (message.contains("Frozen Steve fell into the pond long ago")) { // Fishing Winter
			LootCommand.frozenSteves++;
			LootCommand.frozenStevesSession++;
			ConfigHandler.writeIntConfig("fishing", "frozenSteve", LootCommand.frozenSteves);
			increaseSeaCreatures();
		} else if (message.contains("It's a snowman! He looks harmless")) {
			LootCommand.frostyTheSnowmans++;
			LootCommand.frostyTheSnowmansSession++;
			ConfigHandler.writeIntConfig("fishing", "snowman", LootCommand.frostyTheSnowmans);
			increaseSeaCreatures();
		} else if (message.contains("stole Jerry's Gifts...get them back")) {
			LootCommand.grinches++;
			LootCommand.grinchesSession++;
			ConfigHandler.writeIntConfig("fishing", "grinch", LootCommand.grinches);
			increaseSeaCreatures();
		} else if (message.contains("What is this creature")) {
			LootCommand.yetis++;
			LootCommand.yetiTime = System.currentTimeMillis() / 1000;
			LootCommand.yetiSCs = 0;
			LootCommand.yetisSession++;
			LootCommand.yetiTimeSession = System.currentTimeMillis() / 1000;
			LootCommand.yetiSCsSession = 0;
			ConfigHandler.writeIntConfig("fishing", "yeti", LootCommand.yetis);
			ConfigHandler.writeDoubleConfig("fishing", "yetiTime", LootCommand.yetiTime);
			ConfigHandler.writeIntConfig("fishing", "yetiSC", LootCommand.yetiSCs);
			increaseSeaCreatures();
		} else if (message.contains("A tiny fin emerges from the water, you've caught a Nurse Shark")) { // Fishing Festival
			LootCommand.nurseSharks++;
			LootCommand.nurseSharksSession++;
			ConfigHandler.writeIntConfig("fishing", "nurseShark", LootCommand.nurseSharks);
			increaseSeaCreatures();
		} else if (message.contains("You spot a fin as blue as the water it came from, it's a Blue Shark")) {
			LootCommand.blueSharks++;
			LootCommand.blueSharksSession++;
			ConfigHandler.writeIntConfig("fishing", "blueShark", LootCommand.blueSharks);
			increaseSeaCreatures();
		} else if (message.contains("A striped beast bounds from the depths, the wild Tiger Shark")) {
			LootCommand.tigerSharks++;
			LootCommand.tigerSharksSession++;
			ConfigHandler.writeIntConfig("fishing", "tigerShark", LootCommand.tigerSharks);
			increaseSeaCreatures();
		} else if (message.contains("Hide no longer, a Great White Shark has tracked your scent and thirsts for your blood")) {
			LootCommand.greatWhiteSharks++;
			LootCommand.greatWhiteSharksSession++;
			ConfigHandler.writeIntConfig("fishing", "greatWhiteShark", LootCommand.greatWhiteSharks);
			increaseSeaCreatures();
		} else if (message.contains("Phew! It's only a Scarecrow")) {
			LootCommand.scarecrows++;
			LootCommand.scarecrowsSession++;
			ConfigHandler.writeIntConfig("fishing", "scarecrow", LootCommand.scarecrows);
			increaseSeaCreatures();
		} else if (message.contains("You hear trotting from beneath the waves, you caught a Nightmare")) {
			LootCommand.nightmares++;
			LootCommand.nightmaresSession++;
			ConfigHandler.writeIntConfig("fishing", "nightmare", LootCommand.nightmares);
			increaseSeaCreatures();
		} else if (message.contains("It must be a full moon, a Werewolf appears")) {
			LootCommand.werewolfs++;
			LootCommand.werewolfsSession++;
			ConfigHandler.writeIntConfig("fishing", "werewolf", LootCommand.werewolfs);
			increaseSeaCreatures();
		} else if (message.contains("The spirit of a long lost Phantom Fisher has come to haunt you")) {
			LootCommand.phantomFishers++;
			LootCommand.phantomFishersSession++;
			ConfigHandler.writeIntConfig("fishing", "phantomFisher", LootCommand.phantomFishers);
			increaseSeaCreatures();
		} else if (message.contains("This can't be! The manifestation of death himself")) {
			LootCommand.grimReapers++;
			LootCommand.grimReapersSession++;
			ConfigHandler.writeIntConfig("fishing", "grimReaper", LootCommand.grimReapers);
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
						LootCommand.f1TimeSpent = Math.floor(LootCommand.f1TimeSpent + timeToAdd);
						LootCommand.f1TimeSpentSession = Math.floor(LootCommand.f1TimeSpentSession + timeToAdd);
						ConfigHandler.writeDoubleConfig("catacombs", "floorOneTime", LootCommand.f1TimeSpent);
					} else if (sCleaned.contains("F2")) {
						LootCommand.f2TimeSpent = Math.floor(LootCommand.f2TimeSpent + timeToAdd);
						LootCommand.f2TimeSpentSession = Math.floor(LootCommand.f2TimeSpentSession + timeToAdd);
						ConfigHandler.writeDoubleConfig("catacombs", "floorTwoTime", LootCommand.f2TimeSpent);
					} else if (sCleaned.contains("F3")) {
						LootCommand.f3TimeSpent = Math.floor(LootCommand.f3TimeSpent + timeToAdd);
						LootCommand.f3TimeSpentSession = Math.floor(LootCommand.f3TimeSpentSession + timeToAdd);
						ConfigHandler.writeDoubleConfig("catacombs", "floorThreeTime", LootCommand.f3TimeSpent);
					} else if (sCleaned.contains("F4")) {
						LootCommand.f4TimeSpent = Math.floor(LootCommand.f4TimeSpent + timeToAdd);
						LootCommand.f4TimeSpentSession = Math.floor(LootCommand.f4TimeSpentSession + timeToAdd);
						ConfigHandler.writeDoubleConfig("catacombs", "floorFourTime", LootCommand.f4TimeSpent);
					} else if (sCleaned.contains("F5")) {
						LootCommand.f5TimeSpent = Math.floor(LootCommand.f5TimeSpent + timeToAdd);
						LootCommand.f5TimeSpentSession = Math.floor(LootCommand.f5TimeSpentSession + timeToAdd);
						ConfigHandler.writeDoubleConfig("catacombs", "floorFiveTime", LootCommand.f5TimeSpent);
					} else if (sCleaned.contains("F6")) {
						LootCommand.f6TimeSpent = Math.floor(LootCommand.f6TimeSpent + timeToAdd);
						LootCommand.f6TimeSpentSession = Math.floor(LootCommand.f6TimeSpentSession + timeToAdd);
						ConfigHandler.writeDoubleConfig("catacombs", "floorSixTime", LootCommand.f6TimeSpent);
					} else if (sCleaned.contains("F7")) {
						LootCommand.f7TimeSpent = Math.floor(LootCommand.f7TimeSpent + timeToAdd);
						LootCommand.f7TimeSpentSession = Math.floor(LootCommand.f7TimeSpentSession + timeToAdd);
						ConfigHandler.writeDoubleConfig("catacombs", "floorSevenTime", LootCommand.f7TimeSpent);
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
			LootCommand.wolfTime = System.currentTimeMillis() / 1000;
			LootCommand.wolfBosses = 0;
			LootCommand.wolfTimeSession = System.currentTimeMillis() / 1000;
			LootCommand.wolfBossesSession = 0;
			ConfigHandler.writeDoubleConfig("wolf", "timeRNG", LootCommand.wolfTime);
			ConfigHandler.writeIntConfig("wolf", "bossRNG", 0);
		}
		if (spiderRNG) {
			LootCommand.spiderTime = System.currentTimeMillis() / 1000;
			LootCommand.spiderBosses = 0;
			LootCommand.spiderTimeSession = System.currentTimeMillis() / 1000;
			LootCommand.spiderBossesSession = 0;
			ConfigHandler.writeDoubleConfig("spider", "timeRNG", LootCommand.spiderTime);
			ConfigHandler.writeIntConfig("spider", "bossRNG", 0);
		}
		if (zombieRNG) {
			LootCommand.zombieTime = System.currentTimeMillis() / 1000;
			LootCommand.zombieBosses = 0;
			LootCommand.zombieTimeSession = System.currentTimeMillis() / 1000;
			LootCommand.zombieBossesSession = 0;
			ConfigHandler.writeDoubleConfig("zombie", "timeRNG", LootCommand.zombieTime);
			ConfigHandler.writeIntConfig("zombie", "bossRNG", 0);
		}

		// Mythological Tracker
		if (message.contains("You dug out")) {
			if (message.contains(" coins!")) {
				double coinsEarned = Double.parseDouble(message.replaceAll("[^\\d]", ""));
				LootCommand.mythCoins += coinsEarned;
				LootCommand.mythCoinsSession += coinsEarned;
				ConfigHandler.writeDoubleConfig("mythological", "coins", LootCommand.mythCoins);
			} else if (message.contains("a Griffin Feather!")) {
				LootCommand.griffinFeathers++;
				LootCommand.griffinFeathersSession++;
				ConfigHandler.writeIntConfig("mythological", "griffinFeather", LootCommand.griffinFeathers);
			} else if (message.contains("a Crown of Greed!")) {
				LootCommand.crownOfGreeds++;
				LootCommand.crownOfGreedsSession++;
				ConfigHandler.writeIntConfig("mythological", "crownOfGreed", LootCommand.crownOfGreeds);
			} else if (message.contains("a Washed-up Souvenir!")) {
				LootCommand.washedUpSouvenirs++;
				LootCommand.washedUpSouvenirsSession++;
				ConfigHandler.writeIntConfig("mythological", "washedUpSouvenir", LootCommand.washedUpSouvenirs);
			} else if (message.contains("a Minos Hunter!")) {
				LootCommand.minosHunters++;
				LootCommand.minosHuntersSession++;
				ConfigHandler.writeIntConfig("mythological", "minosHunter", LootCommand.minosHunters);
			} else if (message.contains("Siamese Lynxes!")) {
				LootCommand.siameseLynxes++;
				LootCommand.siameseLynxesSession++;
				ConfigHandler.writeIntConfig("mythological", "siameseLynx", LootCommand.siameseLynxes);
			} else if (message.contains("a Minotaur!")) {
				LootCommand.minotaurs++;
				LootCommand.minotaursSession++;
				ConfigHandler.writeIntConfig("mythological", "minotaur", LootCommand.minotaurs);
			} else if (message.contains("a Gaia Construct!")) {
				LootCommand.gaiaConstructs++;
				LootCommand.gaiaConstructsSession++;
				ConfigHandler.writeIntConfig("mythological", "gaiaConstruct", LootCommand.gaiaConstructs);
			} else if (message.contains("a Minos Champion!")) {
				LootCommand.minosChampions++;
				LootCommand.minosChampionsSession++;
				ConfigHandler.writeIntConfig("mythological", "minosChampion", LootCommand.minosChampions);
			} else if (message.contains("a Minos Inquisitor!")) {
				LootCommand.minosInquisitors++;
				LootCommand.minosInquisitorsSession++;
				ConfigHandler.writeIntConfig("mythological", "minosInquisitor", LootCommand.minosInquisitors);
			}
		}

		// Dungeons Trackers
		if (message.contains("    ")) {
			if (message.contains("Recombobulator 3000")) {
				LootCommand.recombobulators++;
				LootCommand.recombobulatorsSession++;
				ConfigHandler.writeIntConfig("catacombs", "recombobulator", LootCommand.recombobulators);
			} else if (message.contains("Fuming Potato Book")) {
				LootCommand.fumingPotatoBooks++;
				LootCommand.fumingPotatoBooksSession++;
				ConfigHandler.writeIntConfig("catacombs", "fumingBooks", LootCommand.fumingPotatoBooks);
			} else if (message.contains("Bonzo's Staff")) { // F1
				LootCommand.bonzoStaffs++;
				LootCommand.bonzoStaffsSession++;
				ConfigHandler.writeIntConfig("catacombs", "bonzoStaff", LootCommand.bonzoStaffs);
			} else if (message.contains("Scarf's Studies")) { // F2
				LootCommand.scarfStudies++;
				LootCommand.scarfStudiesSession++;
				ConfigHandler.writeIntConfig("catacombs", "scarfStudies", LootCommand.scarfStudies);
			} else if (message.contains("Adaptive Helmet")) { // F3
				LootCommand.adaptiveHelms++;
				LootCommand.adaptiveHelmsSession++;
				ConfigHandler.writeIntConfig("catacombs", "adaptiveHelm", LootCommand.adaptiveHelms);
			} else if (message.contains("Adaptive Chestplate")) {
				LootCommand.adaptiveChests++;
				LootCommand.adaptiveChestsSession++;
				ConfigHandler.writeIntConfig("catacombs", "adaptiveChest", LootCommand.adaptiveChests);
			} else if (message.contains("Adaptive Leggings")) {
				LootCommand.adaptiveLegs++;
				LootCommand.adaptiveLegsSession++;
				ConfigHandler.writeIntConfig("catacombs", "adaptiveLegging", LootCommand.adaptiveLegs);
			} else if (message.contains("Adaptive Boots")) {
				LootCommand.adaptiveBoots++;
				LootCommand.adaptiveBootsSession++;
				ConfigHandler.writeIntConfig("catacombs", "adaptiveBoot", LootCommand.adaptiveBoots);
			} else if (message.contains("Adaptive Blade")) {
				LootCommand.adaptiveSwords++;
				LootCommand.adaptiveSwordsSession++;
				ConfigHandler.writeIntConfig("catacombs", "adaptiveSword", LootCommand.adaptiveSwords);
			} else if (message.contains("Spirit Wing")) { // F4
				LootCommand.spiritWings++;
				LootCommand.spiritWingsSession++;
				ConfigHandler.writeIntConfig("catacombs", "spiritWing", LootCommand.spiritWings);
			} else if (message.contains("Spirit Bone")) {
				LootCommand.spiritBones++;
				LootCommand.spiritBonesSession++;
				ConfigHandler.writeIntConfig("catacombs", "spiritBone", LootCommand.spiritBones);
			} else if (message.contains("Spirit Boots")) {
				LootCommand.spiritBoots++;
				LootCommand.spiritBootsSession++;
				ConfigHandler.writeIntConfig("catacombs", "spiritBoot", LootCommand.spiritBoots);
			} else if (message.contains("[Lvl 1] Spirit")) {
				String formattedMessage = event.message.getFormattedText();
				// Unicode colour code messes up here, just gonna remove the symbols
				if (formattedMessage.contains("5Spirit")) {
					LootCommand.epicSpiritPets++;
					LootCommand.epicSpiritPetsSession++;
					ConfigHandler.writeIntConfig("catacombs", "spiritPetEpic", LootCommand.epicSpiritPets);
				} else if (formattedMessage.contains("6Spirit")) {
					LootCommand.legSpiritPets++;
					LootCommand.legSpiritPetsSession++;
					ConfigHandler.writeIntConfig("catacombs", "spiritPetLeg", LootCommand.legSpiritPets);
				}
			} else if (message.contains("Spirit Sword")) {
				LootCommand.spiritSwords++;
				LootCommand.spiritSwordsSession++;
				ConfigHandler.writeIntConfig("catacombs", "spiritSword", LootCommand.spiritSwords);
			} else if (message.contains("Spirit Bow")) {
				LootCommand.spiritBows++;
				LootCommand.spiritBowsSession++;
				ConfigHandler.writeIntConfig("catacombs", "spiritBow", LootCommand.spiritBows);
			} else if (message.contains("Warped Stone")) { // F5
				LootCommand.warpedStones++;
				LootCommand.warpedStonesSession++;
				ConfigHandler.writeIntConfig("catacombs", "warpedStone", LootCommand.warpedStones);
			} else if (message.contains("Shadow Assassin Helmet")) {
				LootCommand.shadowAssHelms++;
				LootCommand.shadowAssHelmsSession++;
				ConfigHandler.writeIntConfig("catacombs", "shadowAssassinHelm", LootCommand.shadowAssHelms);
			} else if (message.contains("Shadow Assassin Chestplate")) {
				LootCommand.shadowAssChests++;
				LootCommand.shadowAssChestsSession++;
				ConfigHandler.writeIntConfig("catacombs", "shadowAssassinChest", LootCommand.shadowAssChests);
			} else if (message.contains("Shadow Assassin Leggings")) {
				LootCommand.shadowAssLegs++;
				LootCommand.shadowAssLegsSession++;
				ConfigHandler.writeIntConfig("catacombs", "shadowAssassinLegging", LootCommand.shadowAssLegs);
			} else if (message.contains("Shadow Assassin Boots")) {
				LootCommand.shadowAssBoots++;
				LootCommand.shadowAssBootsSession++;
				ConfigHandler.writeIntConfig("catacombs", "shadowAssassinBoot", LootCommand.shadowAssBoots);
			} else if (message.contains("Livid Dagger")) {
				LootCommand.lividDaggers++;
				LootCommand.lividDaggersSession++;
				ConfigHandler.writeIntConfig("catacombs", "lividDagger", LootCommand.lividDaggers);
			} else if (message.contains("Shadow Fury")) {
				LootCommand.shadowFurys++;
				LootCommand.shadowFurysSession++;
				ConfigHandler.writeIntConfig("catacombs", "shadowFury", LootCommand.shadowFurys);
			} else if (message.contains("Ancient Rose")) { // F6
				LootCommand.ancientRoses++;
				LootCommand.ancientRosesSession++;
				ConfigHandler.writeIntConfig("catacombs", "ancientRose", LootCommand.ancientRoses);
			} else if (message.contains("Precursor Eye")) {
				LootCommand.precursorEyes++;
				LootCommand.precursorEyesSession++;
				ConfigHandler.writeIntConfig("catacombs", "precursorEye", LootCommand.precursorEyes);
			} else if (message.contains("Giant's Sword")) {
				LootCommand.giantsSwords++;
				LootCommand.giantsSwordsSession++;
				ConfigHandler.writeIntConfig("catacombs", "giantsSword", LootCommand.giantsSwords);
			} else if (message.contains("Necromancer Lord Helmet")) {
				LootCommand.necroLordHelms++;
				LootCommand.necroLordHelmsSession++;
				ConfigHandler.writeIntConfig("catacombs", "necroLordHelm", LootCommand.necroLordHelms);
			} else if (message.contains("Necromancer Lord Chestplate")) {
				LootCommand.necroLordChests++;
				LootCommand.necroLordChestsSession++;
				ConfigHandler.writeIntConfig("catacombs", "necroLordChest", LootCommand.necroLordChests);
			} else if (message.contains("Necromancer Lord Leggings")) {
				LootCommand.necroLordLegs++;
				LootCommand.necroLordLegsSession++;
				ConfigHandler.writeIntConfig("catacombs", "necroLordLegging", LootCommand.necroLordLegs);
			} else if (message.contains("Necromancer Lord Boots")) {
				LootCommand.necroLordBoots++;
				LootCommand.necroLordBootsSession++;
				ConfigHandler.writeIntConfig("catacombs", "necroLordBoot", LootCommand.necroLordBoots);
			} else if (message.contains("Necromancer Sword")) {
				LootCommand.necroSwords++;
				LootCommand.necroSwordsSession++;
				ConfigHandler.writeIntConfig("catacombs", "necroSword", LootCommand.necroSwords);
			} else if (message.contains("Wither Blood")) { // F7
				LootCommand.witherBloods++;
				LootCommand.witherBloodsSession++;
				ConfigHandler.writeIntConfig("catacombs", "witherBlood", LootCommand.witherBloods);
			} else if (message.contains("Wither Cloak")) {
				LootCommand.witherCloaks++;
				LootCommand.witherCloaksSession++;
				ConfigHandler.writeIntConfig("catacombs", "witherCloak", LootCommand.witherCloaks);
			} else if (message.contains("Implosion")) {
				LootCommand.implosions++;
				LootCommand.implosionsSession++;
				ConfigHandler.writeIntConfig("catacombs", "implosion", LootCommand.implosions);
			} else if (message.contains("Wither Shield")) {
				LootCommand.witherShields++;
				LootCommand.witherShieldsSession++;
				ConfigHandler.writeIntConfig("catacombs", "witherShield", LootCommand.witherShields);
			} else if (message.contains("Shadow Warp")) {
				LootCommand.shadowWarps++;
				LootCommand.shadowWarpsSession++;
				ConfigHandler.writeIntConfig("catacombs", "shadowWarp", LootCommand.shadowWarps);
			} else if (message.contains("Necron's Handle")) {
				LootCommand.necronsHandles++;
				LootCommand.necronsHandlesSession++;
				ConfigHandler.writeIntConfig("catacombs", "necronsHandle", LootCommand.necronsHandles);
			} else if (message.contains("Auto Recombobulator")) {
				LootCommand.autoRecombs++;
				LootCommand.autoRecombsSession++;
				ConfigHandler.writeIntConfig("catacombs", "autoRecomb", LootCommand.autoRecombs);
			} else if (message.contains("Wither Helmet")) {
				LootCommand.witherHelms++;
				LootCommand.witherHelmsSession++;
				ConfigHandler.writeIntConfig("catacombs", "witherHelm", LootCommand.witherHelms);
			} else if (message.contains("Wither Chestplate")) {
				LootCommand.witherChests++;
				LootCommand.witherChestsSession++;
				ConfigHandler.writeIntConfig("catacombs", "witherChest", LootCommand.witherChests);
			} else if (message.contains("Wither Leggings")) {
				LootCommand.witherLegs++;
				LootCommand.witherLegsSession++;
				ConfigHandler.writeIntConfig("catacombs", "witherLegging", LootCommand.witherLegs);
			} else if (message.contains("Wither Boots")) {
				LootCommand.witherBoots++;
				LootCommand.witherBootsSession++;
				ConfigHandler.writeIntConfig("catacombs", "witherBoot", LootCommand.witherBoots);
			}
		}

		// Chat Maddox
		if (message.contains("[OPEN MENU]")) {
			List<IChatComponent> listOfSiblings = event.message.getSiblings();
			for (IChatComponent sibling : listOfSiblings) {
				if (sibling.getUnformattedText().contains("[OPEN MENU]")) {
					lastMaddoxCommand = sibling.getChatStyle().getChatClickEvent().getValue();
					lastMaddoxTime = System.currentTimeMillis() / 1000;
				}
			}
			if (ToggleCommand.chatMaddoxToggled) Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(MAIN_COLOUR + "Open chat then click anywhere on-screen to open Maddox"));
		}

		// Spirit Bear alerts
		if (ToggleCommand.spiritBearAlerts && message.contains("The Spirit Bear has appeared!")) {
			Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "SPIRIT BEAR", 2);
		}
    }

    @SubscribeEvent
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
        if (usingLabymod && !(Minecraft.getMinecraft().ingameGUI instanceof GuiIngameForge)) return;
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE && event.type != RenderGameOverlayEvent.ElementType.JUMPBAR)
            return;
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
        if (Minecraft.getMinecraft().currentScreen instanceof EditLocationsGui) return;

        Minecraft mc = Minecraft.getMinecraft();

        if (ToggleCommand.coordsToggled) {
            EntityPlayer player = mc.thePlayer;

            double xDir = (player.rotationYaw % 360 + 360) % 360;
            if (xDir > 180) xDir -= 360;
            xDir = (double) Math.round(xDir * 10d) / 10d;
            double yDir = (double) Math.round(player.rotationPitch * 10d) / 10d;

            String coordText = COORDS_COLOUR + (int) player.posX + " / " + (int) player.posY + " / " + (int) player.posZ + " (" + xDir + " / " + yDir + ")";
            new TextRenderer(mc, coordText, MoveCommand.coordsXY[0], MoveCommand.coordsXY[1], ScaleCommand.coordsScale);
        }

        if (ToggleCommand.dungeonTimerToggled && Utils.inDungeons) {
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
            new TextRenderer(mc, dungeonTimerText, MoveCommand.dungeonTimerXY[0], MoveCommand.dungeonTimerXY[1], ScaleCommand.dungeonTimerScale);
            new TextRenderer(mc, dungeonTimers, (int) (MoveCommand.dungeonTimerXY[0] + (80 * ScaleCommand.dungeonTimerScale)), MoveCommand.dungeonTimerXY[1], ScaleCommand.dungeonTimerScale);
        }

        if (ToggleCommand.lividSolverToggled && foundLivid && livid != null) {
            new TextRenderer(mc, livid.getName().replace("" + EnumChatFormatting.BOLD, ""), MoveCommand.lividHpXY[0], MoveCommand.lividHpXY[1], ScaleCommand.lividHpScale);
        }

        if (ToggleCommand.cakeTimerToggled) {
            double scale = ScaleCommand.cakeTimerScale;
            double scaleReset = Math.pow(scale, -1);
            GL11.glScaled(scale, scale, scale);

            double timeNow = System.currentTimeMillis() / 1000;
            mc.getTextureManager().bindTexture(CAKE_ICON);
            Gui.drawModalRectWithCustomSizedTexture(MoveCommand.cakeTimerXY[0], MoveCommand.cakeTimerXY[1], 0, 0, 16, 16, 16, 16);

            String cakeText;
            if (cakeTime - timeNow < 0) {
                cakeText = EnumChatFormatting.RED + "NONE";
            } else {
                cakeText = CAKE_COLOUR + Utils.getTimeBetween(timeNow, cakeTime);
            }
            new TextRenderer(mc, cakeText, MoveCommand.cakeTimerXY[0] + 20, MoveCommand.cakeTimerXY[1] + 5, 1);

            GL11.glScaled(scaleReset, scaleReset, scaleReset);
        }

        if (ToggleCommand.bonzoTimerToggled && Utils.inDungeons) {

            ItemStack helmetSlot = mc.thePlayer.getCurrentArmor(3);
            if ((helmetSlot != null && helmetSlot.getDisplayName().contains("Bonzo's Mask")) || nextBonzoUse > 0) {

                double scale = ScaleCommand.bonzoTimerScale;
                double scaleReset = Math.pow(scale, -1);
                GL11.glScaled(scale, scale, scale);

                double timeNow = System.currentTimeMillis() / 1000;
                mc.getTextureManager().bindTexture(BONZO_ICON);
                Gui.drawModalRectWithCustomSizedTexture(MoveCommand.bonzoTimerXY[0], MoveCommand.bonzoTimerXY[1], 0, 0, 16, 16, 16, 16);

                String bonzoText;
                if (nextBonzoUse - timeNow < 0) {
                    bonzoText = EnumChatFormatting.GREEN + "READY";
                } else {
                    bonzoText = BONZO_COLOR + Utils.getTimeBetween(timeNow, nextBonzoUse);
                }
                new TextRenderer(mc, bonzoText, MoveCommand.bonzoTimerXY[0] + 20, MoveCommand.bonzoTimerXY[1] + 5, 1);

                GL11.glScaled(scaleReset, scaleReset, scaleReset);
            }
        }

        if (showSkillTracker) {
            int xpPerHour;
            double xpToShow = 0;
            switch (lastSkill) {
                case "Farming":
                    xpToShow = farmingXPGained;
                    break;
                case "Mining":
                    xpToShow = miningXPGained;
                    break;
                case "Combat":
                    xpToShow = combatXPGained;
                    break;
                case "Foraging":
                    xpToShow = foragingXPGained;
                    break;
                case "Fishing":
                    xpToShow = fishingXPGained;
                    break;
                case "Enchanting":
                    xpToShow = enchantingXPGained;
                    break;
                case "Alchemy":
                    xpToShow = alchemyXPGained;
                    break;
                default:
                    System.err.println("Unknown skill in rendering.");
            }
            xpPerHour = (int) Math.round(xpToShow / ((skillStopwatch.getTime() + 1) / 3600000d));
            String skillTrackerText = SKILL_TRACKER_COLOUR + lastSkill + " XP Earned: " + NumberFormat.getNumberInstance(Locale.US).format(xpToShow) + "\n" +
                                      SKILL_TRACKER_COLOUR + "Time Elapsed: " + Utils.getTimeBetween(0, skillStopwatch.getTime() / 1000d) + "\n" +
                                      SKILL_TRACKER_COLOUR + "XP Per Hour: " + NumberFormat.getIntegerInstance(Locale.US).format(xpPerHour);
            if (xpLeft >= 0) {
                String time = xpPerHour == 0 ? "Never" : Utils.getTimeBetween(0, xpLeft / (xpPerHour / 3600D));
                skillTrackerText += "\n" + SKILL_TRACKER_COLOUR + "Time Until Next Level: " + time;
            }
            if (!skillStopwatch.isStarted() || skillStopwatch.isSuspended()) {
                skillTrackerText += "\n" + EnumChatFormatting.RED + "PAUSED";
            }

            new TextRenderer(mc, skillTrackerText, MoveCommand.skillTrackerXY[0], MoveCommand.skillTrackerXY[1], ScaleCommand.skillTrackerScale);
        }

        if (ToggleCommand.waterToggled && Utils.inDungeons && waterAnswers != null) {
            new TextRenderer(mc, waterAnswers, MoveCommand.waterAnswerXY[0], MoveCommand.waterAnswerXY[1], ScaleCommand.waterAnswerScale);
        }

        if (!DisplayCommand.display.equals("off")) {
            String dropsText = "";
            String countText = "";
            String dropsTextTwo;
            String countTextTwo;
            String timeBetween;
            String bossesBetween;
            String drop20;
            double timeNow = System.currentTimeMillis() / 1000;
            NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);

            switch (DisplayCommand.display) {
                case "wolf":
                    if (LootCommand.wolfTime == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(LootCommand.wolfTime, timeNow);
                    }
                    if (LootCommand.wolfBosses == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(LootCommand.wolfBosses);
                    }
                    if (ToggleCommand.slayerCountTotal) {
                        drop20 = nf.format(LootCommand.wolfWheels);
                    } else {
                        drop20 = nf.format(LootCommand.wolfWheelsDrops) + " times";
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.wolfSvens) + "\n" +
                                EnumChatFormatting.GREEN + nf.format(LootCommand.wolfTeeth) + "\n" +
                                EnumChatFormatting.BLUE + drop20 + "\n" +
                                EnumChatFormatting.AQUA + LootCommand.wolfSpirits + "\n" +
                                EnumChatFormatting.WHITE + LootCommand.wolfBooks + "\n" +
                                EnumChatFormatting.DARK_RED + LootCommand.wolfEggs + "\n" +
                                EnumChatFormatting.GOLD + LootCommand.wolfCoutures + "\n" +
                                EnumChatFormatting.AQUA + LootCommand.wolfBaits + "\n" +
                                EnumChatFormatting.DARK_PURPLE + LootCommand.wolfFluxes + "\n" +
                                EnumChatFormatting.AQUA + timeBetween + "\n" +
                                EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "wolf_session":
                    if (LootCommand.wolfTimeSession == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(LootCommand.wolfTimeSession, timeNow);
                    }
                    if (LootCommand.wolfBossesSession == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(LootCommand.wolfBossesSession);
                    }
                    if (ToggleCommand.slayerCountTotal) {
                        drop20 = nf.format(LootCommand.wolfWheelsSession);
                    } else {
                        drop20 = nf.format(LootCommand.wolfWheelsDropsSession) + " times";
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.wolfSvensSession) + "\n" +
                                EnumChatFormatting.GREEN + nf.format(LootCommand.wolfTeethSession) + "\n" +
                                EnumChatFormatting.BLUE + drop20 + "\n" +
                                EnumChatFormatting.AQUA + LootCommand.wolfSpiritsSession + "\n" +
                                EnumChatFormatting.WHITE + LootCommand.wolfBooksSession + "\n" +
                                EnumChatFormatting.DARK_RED + LootCommand.wolfEggsSession + "\n" +
                                EnumChatFormatting.GOLD + LootCommand.wolfCouturesSession + "\n" +
                                EnumChatFormatting.AQUA + LootCommand.wolfBaitsSession + "\n" +
                                EnumChatFormatting.DARK_PURPLE + LootCommand.wolfFluxesSession + "\n" +
                                EnumChatFormatting.AQUA + timeBetween + "\n" +
                                EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "spider":
                    if (LootCommand.spiderTime == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(LootCommand.spiderTime, timeNow);
                    }
                    if (LootCommand.spiderBosses == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(LootCommand.spiderBosses);
                    }
                    if (ToggleCommand.slayerCountTotal) {
                        drop20 = nf.format(LootCommand.spiderTAP);
                    } else {
                        drop20 = nf.format(LootCommand.spiderTAPDrops) + " times";
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.spiderTarantulas) + "\n" +
                                EnumChatFormatting.GREEN + nf.format(LootCommand.spiderWebs) + "\n" +
                                EnumChatFormatting.DARK_GREEN + drop20 + "\n" +
                                EnumChatFormatting.DARK_GRAY + LootCommand.spiderBites + "\n" +
                                EnumChatFormatting.WHITE + LootCommand.spiderBooks + "\n" +
                                EnumChatFormatting.AQUA + LootCommand.spiderCatalysts + "\n" +
                                EnumChatFormatting.DARK_PURPLE + LootCommand.spiderTalismans + "\n" +
                                EnumChatFormatting.LIGHT_PURPLE + LootCommand.spiderSwatters + "\n" +
                                EnumChatFormatting.GOLD + LootCommand.spiderMosquitos + "\n" +
                                EnumChatFormatting.AQUA + timeBetween + "\n" +
                                EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "spider_session":
                    if (LootCommand.spiderTimeSession == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(LootCommand.spiderTimeSession, timeNow);
                    }
                    if (LootCommand.spiderBossesSession == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(LootCommand.spiderBossesSession);
                    }
                    if (ToggleCommand.slayerCountTotal) {
                        drop20 = nf.format(LootCommand.spiderTAPSession);
                    } else {
                        drop20 = nf.format(LootCommand.spiderTAPDropsSession) + " times";
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.spiderTarantulasSession) + "\n" +
                                EnumChatFormatting.GREEN + nf.format(LootCommand.spiderWebsSession) + "\n" +
                                EnumChatFormatting.DARK_GREEN + drop20 + "\n" +
                                EnumChatFormatting.DARK_GRAY + LootCommand.spiderBitesSession + "\n" +
                                EnumChatFormatting.WHITE + LootCommand.spiderBooksSession + "\n" +
                                EnumChatFormatting.AQUA + LootCommand.spiderCatalystsSession + "\n" +
                                EnumChatFormatting.DARK_PURPLE + LootCommand.spiderTalismansSession + "\n" +
                                EnumChatFormatting.LIGHT_PURPLE + LootCommand.spiderSwattersSession + "\n" +
                                EnumChatFormatting.GOLD + LootCommand.spiderMosquitosSession + "\n" +
                                EnumChatFormatting.AQUA + timeBetween + "\n" +
                                EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "zombie":
                    if (LootCommand.zombieTime == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(LootCommand.zombieTime, timeNow);
                    }
                    if (LootCommand.zombieBosses == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(LootCommand.zombieBosses);
                    }
                    if (ToggleCommand.slayerCountTotal) {
                        drop20 = nf.format(LootCommand.zombieFoulFlesh);
                    } else {
                        drop20 = nf.format(LootCommand.zombieFoulFleshDrops) + " times";
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.zombieRevs) + "\n" +
                                EnumChatFormatting.GREEN + nf.format(LootCommand.zombieRevFlesh) + "\n" +
                                EnumChatFormatting.BLUE + drop20 + "\n" +
                                EnumChatFormatting.DARK_GREEN + LootCommand.zombiePestilences + "\n" +
                                EnumChatFormatting.WHITE + LootCommand.zombieBooks + "\n" +
                                EnumChatFormatting.AQUA + LootCommand.zombieUndeadCatas + "\n" +
                                EnumChatFormatting.DARK_PURPLE + LootCommand.zombieBeheadeds + "\n" +
                                EnumChatFormatting.RED + LootCommand.zombieRevCatas + "\n" +
                                EnumChatFormatting.DARK_GREEN + LootCommand.zombieSnakes + "\n" +
                                EnumChatFormatting.GOLD + LootCommand.zombieScythes + "\n" +
                                EnumChatFormatting.AQUA + timeBetween + "\n" +
                                EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "zombie_session":
                    if (LootCommand.zombieTimeSession == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(LootCommand.zombieTimeSession, timeNow);
                    }
                    if (LootCommand.zombieBossesSession == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(LootCommand.zombieBossesSession);
                    }
                    if (ToggleCommand.slayerCountTotal) {
                        drop20 = nf.format(LootCommand.zombieFoulFleshSession);
                    } else {
                        drop20 = nf.format(LootCommand.zombieFoulFleshDropsSession) + " times";
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.zombieRevsSession) + "\n" +
                                EnumChatFormatting.GREEN + nf.format(LootCommand.zombieRevFleshSession) + "\n" +
                                EnumChatFormatting.BLUE + drop20 + "\n" +
                                EnumChatFormatting.DARK_GREEN + LootCommand.zombiePestilencesSession + "\n" +
                                EnumChatFormatting.WHITE + LootCommand.zombieBooksSession + "\n" +
                                EnumChatFormatting.AQUA + LootCommand.zombieUndeadCatasSession + "\n" +
                                EnumChatFormatting.DARK_PURPLE + LootCommand.zombieBeheadedsSession + "\n" +
                                EnumChatFormatting.RED + LootCommand.zombieRevCatasSession + "\n" +
                                EnumChatFormatting.DARK_GREEN + LootCommand.zombieSnakesSession + "\n" +
                                EnumChatFormatting.GOLD + LootCommand.zombieScythes + "\n" +
                                EnumChatFormatting.AQUA + timeBetween + "\n" +
                                EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "fishing":
                    if (LootCommand.empTime == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(LootCommand.empTime, timeNow);
                    }
                    if (LootCommand.empSCs == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(LootCommand.empSCs);
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
                    countText = EnumChatFormatting.AQUA + nf.format(LootCommand.seaCreatures) + "\n" +
                                EnumChatFormatting.AQUA + nf.format(LootCommand.fishingMilestone) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.goodCatches) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.greatCatches) + "\n" +
                                EnumChatFormatting.GRAY + nf.format(LootCommand.squids) + "\n" +
                                EnumChatFormatting.GREEN + nf.format(LootCommand.seaWalkers) + "\n" +
                                EnumChatFormatting.DARK_GRAY + nf.format(LootCommand.nightSquids) + "\n" +
                                EnumChatFormatting.DARK_AQUA + nf.format(LootCommand.seaGuardians) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.seaWitches) + "\n" +
                                EnumChatFormatting.GREEN + nf.format(LootCommand.seaArchers);
                    // Seperated to save vertical space
                    dropsTextTwo = EnumChatFormatting.GREEN + "Monster of Deeps:\n" +
                                   EnumChatFormatting.YELLOW + "Catfishes:\n" +
                                   EnumChatFormatting.GOLD + "Carrot Kings:\n" +
                                   EnumChatFormatting.GRAY + "Sea Leeches:\n" +
                                   EnumChatFormatting.DARK_PURPLE + "Guardian Defenders:\n" +
                                   EnumChatFormatting.DARK_PURPLE + "Deep Sea Protectors:\n" +
                                   EnumChatFormatting.GOLD + "Hydras:\n" +
                                   EnumChatFormatting.GOLD + "Sea Emperors:\n" +
                                   EnumChatFormatting.AQUA + "Time Since Emp:\n" +
                                   EnumChatFormatting.AQUA + "Creatures Since Emp:";
                    countTextTwo = EnumChatFormatting.GREEN + nf.format(LootCommand.monsterOfTheDeeps) + "\n" +
                                   EnumChatFormatting.YELLOW + nf.format(LootCommand.catfishes) + "\n" +
                                   EnumChatFormatting.GOLD + nf.format(LootCommand.carrotKings) + "\n" +
                                   EnumChatFormatting.GRAY + nf.format(LootCommand.seaLeeches) + "\n" +
                                   EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.guardianDefenders) + "\n" +
                                   EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.deepSeaProtectors) + "\n" +
                                   EnumChatFormatting.GOLD + nf.format(LootCommand.hydras) + "\n" +
                                   EnumChatFormatting.GOLD + nf.format(LootCommand.seaEmperors) + "\n" +
                                   EnumChatFormatting.AQUA + timeBetween + "\n" +
                                   EnumChatFormatting.AQUA + bossesBetween;

                    if (ToggleCommand.splitFishing) {
                        new TextRenderer(mc, dropsTextTwo, (int) (MoveCommand.displayXY[0] + (160 * ScaleCommand.displayScale)), MoveCommand.displayXY[1], ScaleCommand.displayScale);
                        new TextRenderer(mc, countTextTwo, (int) (MoveCommand.displayXY[0] + (270 * ScaleCommand.displayScale)), MoveCommand.displayXY[1], ScaleCommand.displayScale);
                    } else {
                        dropsText += "\n" + dropsTextTwo;
                        countText += "\n" + countTextTwo;
                    }
                    break;
                case "fishing_session":
                    if (LootCommand.empTimeSession == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(LootCommand.empTimeSession, timeNow);
                    }
                    if (LootCommand.empSCsSession == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(LootCommand.empSCsSession);
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
                    countText = EnumChatFormatting.AQUA + nf.format(LootCommand.seaCreaturesSession) + "\n" +
                                EnumChatFormatting.AQUA + nf.format(LootCommand.fishingMilestoneSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.goodCatchesSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.greatCatchesSession) + "\n" +
                                EnumChatFormatting.GRAY + nf.format(LootCommand.squidsSession) + "\n" +
                                EnumChatFormatting.GREEN + nf.format(LootCommand.seaWalkersSession) + "\n" +
                                EnumChatFormatting.DARK_GRAY + nf.format(LootCommand.nightSquidsSession) + "\n" +
                                EnumChatFormatting.DARK_AQUA + nf.format(LootCommand.seaGuardiansSession) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.seaWitchesSession) + "\n" +
                                EnumChatFormatting.GREEN + nf.format(LootCommand.seaArchersSession);
                    // Seperated to save vertical space
                    dropsTextTwo = EnumChatFormatting.GREEN + "Monster of Deeps:\n" +
                                   EnumChatFormatting.YELLOW + "Catfishes:\n" +
                                   EnumChatFormatting.GOLD + "Carrot Kings:\n" +
                                   EnumChatFormatting.GRAY + "Sea Leeches:\n" +
                                   EnumChatFormatting.DARK_PURPLE + "Guardian Defenders:\n" +
                                   EnumChatFormatting.DARK_PURPLE + "Deep Sea Protectors:\n" +
                                   EnumChatFormatting.GOLD + "Hydras:\n" +
                                   EnumChatFormatting.GOLD + "Sea Emperors:\n" +
                                   EnumChatFormatting.AQUA + "Time Since Emp:\n" +
                                   EnumChatFormatting.AQUA + "Creatures Since Emp:";
                    countTextTwo = EnumChatFormatting.GREEN + nf.format(LootCommand.monsterOfTheDeepsSession) + "\n" +
                                   EnumChatFormatting.YELLOW + nf.format(LootCommand.catfishesSession) + "\n" +
                                   EnumChatFormatting.GOLD + nf.format(LootCommand.carrotKingsSession) + "\n" +
                                   EnumChatFormatting.GRAY + nf.format(LootCommand.seaLeechesSession) + "\n" +
                                   EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.guardianDefendersSession) + "\n" +
                                   EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.deepSeaProtectorsSession) + "\n" +
                                   EnumChatFormatting.GOLD + nf.format(LootCommand.hydrasSession) + "\n" +
                                   EnumChatFormatting.GOLD + nf.format(LootCommand.seaEmperorsSession) + "\n" +
                                   EnumChatFormatting.AQUA + timeBetween + "\n" +
                                   EnumChatFormatting.AQUA + bossesBetween;

                    if (ToggleCommand.splitFishing) {
                        new TextRenderer(mc, dropsTextTwo, (int) (MoveCommand.displayXY[0] + (160 * ScaleCommand.displayScale)), MoveCommand.displayXY[1], ScaleCommand.displayScale);
                        new TextRenderer(mc, countTextTwo, (int) (MoveCommand.displayXY[0] + (270 * ScaleCommand.displayScale)), MoveCommand.displayXY[1], ScaleCommand.displayScale);
                    } else {
                        dropsText += "\n" + dropsTextTwo;
                        countText += "\n" + countTextTwo;
                    }
                    break;
                case "fishing_winter":
                    if (LootCommand.yetiTime == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(LootCommand.yetiTime, timeNow);
                    }
                    if (LootCommand.yetiSCs == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(LootCommand.yetiSCs);
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
                    countText = EnumChatFormatting.AQUA + nf.format(LootCommand.seaCreatures) + "\n" +
                                EnumChatFormatting.AQUA + nf.format(LootCommand.fishingMilestone) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.goodCatches) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.greatCatches) + "\n" +
                                EnumChatFormatting.AQUA + nf.format(LootCommand.frozenSteves) + "\n" +
                                EnumChatFormatting.WHITE + nf.format(LootCommand.frostyTheSnowmans) + "\n" +
                                EnumChatFormatting.DARK_GREEN + nf.format(LootCommand.grinches) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.yetis) + "\n" +
                                EnumChatFormatting.AQUA + timeBetween + "\n" +
                                EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "fishing_winter_session":
                    if (LootCommand.yetiTimeSession == -1) {
                        timeBetween = "Never";
                    } else {
                        timeBetween = Utils.getTimeBetween(LootCommand.yetiTimeSession, timeNow);
                    }
                    if (LootCommand.yetiSCsSession == -1) {
                        bossesBetween = "Never";
                    } else {
                        bossesBetween = nf.format(LootCommand.yetiSCsSession);
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
                    countText = EnumChatFormatting.AQUA + nf.format(LootCommand.seaCreaturesSession) + "\n" +
                                EnumChatFormatting.AQUA + nf.format(LootCommand.fishingMilestoneSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.goodCatchesSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.greatCatchesSession) + "\n" +
                                EnumChatFormatting.AQUA + nf.format(LootCommand.frozenStevesSession) + "\n" +
                                EnumChatFormatting.WHITE + nf.format(LootCommand.frostyTheSnowmansSession) + "\n" +
                                EnumChatFormatting.DARK_GREEN + nf.format(LootCommand.grinchesSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.yetisSession) + "\n" +
                                EnumChatFormatting.AQUA + timeBetween + "\n" +
                                EnumChatFormatting.AQUA + bossesBetween;
                    break;
                case "fishing_festival":
                    dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
                                EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
                                EnumChatFormatting.GOLD + "Good Catches:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
                                EnumChatFormatting.LIGHT_PURPLE + "Nurse Sharks:\n" +
                                EnumChatFormatting.BLUE + "Blue Sharks:\n" +
                                EnumChatFormatting.GOLD + "Tiger Sharks:\n" +
                                EnumChatFormatting.WHITE + "Great White Sharks:";
                    countText = EnumChatFormatting.AQUA + nf.format(LootCommand.seaCreatures) + "\n" +
                                EnumChatFormatting.AQUA + nf.format(LootCommand.fishingMilestone) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.goodCatches) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.greatCatches) + "\n" +
                                EnumChatFormatting.LIGHT_PURPLE + nf.format(LootCommand.nurseSharks) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.blueSharks) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.tigerSharks) + "\n" +
                                EnumChatFormatting.WHITE + nf.format(LootCommand.greatWhiteSharks);
                    break;
                case "fishing_festival_session":
                    dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
                                EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
                                EnumChatFormatting.GOLD + "Good Catches:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
                                EnumChatFormatting.LIGHT_PURPLE + "Nurse Sharks:\n" +
                                EnumChatFormatting.BLUE + "Blue Sharks:\n" +
                                EnumChatFormatting.GOLD + "Tiger Sharks:\n" +
                                EnumChatFormatting.WHITE + "Great White Sharks:";
                    countText = EnumChatFormatting.AQUA + nf.format(LootCommand.seaCreaturesSession) + "\n" +
                                EnumChatFormatting.AQUA + nf.format(LootCommand.fishingMilestoneSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.goodCatchesSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.greatCatchesSession) + "\n" +
                                EnumChatFormatting.LIGHT_PURPLE + nf.format(LootCommand.nurseSharksSession) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.blueSharksSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.tigerSharksSession) + "\n" +
                                EnumChatFormatting.WHITE + nf.format(LootCommand.greatWhiteSharksSession);
                    break;
                case "fishing_spooky":
                    dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
                                EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
                                EnumChatFormatting.GOLD + "Good Catches:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
                                EnumChatFormatting.BLUE + "Scarecrows:\n" +
                                EnumChatFormatting.GRAY + "Nightmares:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Werewolves:\n" +
                                EnumChatFormatting.GOLD + "Phantom Fishers:\n" +
                                EnumChatFormatting.GOLD + "Grim Reapers:";
                    countText = EnumChatFormatting.AQUA + nf.format(LootCommand.seaCreatures) + "\n" +
                                EnumChatFormatting.AQUA + nf.format(LootCommand.fishingMilestone) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.goodCatches) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.greatCatches) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.scarecrows) + "\n" +
                                EnumChatFormatting.GRAY + nf.format(LootCommand.nightmares) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.werewolfs) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.phantomFishers) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.grimReapers);
                    break;
                case "fishing_spooky_session":
                    dropsText = EnumChatFormatting.AQUA + "Creatures Caught:\n" +
                                EnumChatFormatting.AQUA + "Fishing Milestone:\n" +
                                EnumChatFormatting.GOLD + "Good Catches:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Great Catches:\n" +
                                EnumChatFormatting.BLUE + "Scarecrows:\n" +
                                EnumChatFormatting.GRAY + "Nightmares:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Werewolves:\n" +
                                EnumChatFormatting.GOLD + "Phantom Fishers:\n" +
                                EnumChatFormatting.GOLD + "Grim Reapers:";
                    countText = EnumChatFormatting.AQUA + nf.format(LootCommand.seaCreaturesSession) + "\n" +
                                EnumChatFormatting.AQUA + nf.format(LootCommand.fishingMilestoneSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.goodCatchesSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.greatCatchesSession) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.scarecrowsSession) + "\n" +
                                EnumChatFormatting.GRAY + nf.format(LootCommand.nightmaresSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.werewolfsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.phantomFishersSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.grimReapersSession);
                    break;
                case "mythological":
                    dropsText = EnumChatFormatting.GOLD + "Coins:\n" +
                                EnumChatFormatting.WHITE + "Griffin Feathers:\n" +
                                EnumChatFormatting.GOLD + "Crown of Greeds:\n" +
                                EnumChatFormatting.AQUA + "Washed up Souvenirs:\n" +
                                EnumChatFormatting.RED + "Minos Hunters:\n" +
                                EnumChatFormatting.GRAY + "Siamese Lynxes:\n" +
                                EnumChatFormatting.RED + "Minotaurs:\n" +
                                EnumChatFormatting.WHITE + "Gaia Constructs:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Minos Champions:\n" +
                                EnumChatFormatting.GOLD + "Minos Inquisitors:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.mythCoins) + "\n" +
                                EnumChatFormatting.WHITE + nf.format(LootCommand.griffinFeathers) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.crownOfGreeds) + "\n" +
                                EnumChatFormatting.AQUA + nf.format(LootCommand.washedUpSouvenirs) + "\n" +
                                EnumChatFormatting.RED + nf.format(LootCommand.minosHunters) + "\n" +
                                EnumChatFormatting.GRAY + nf.format(LootCommand.siameseLynxes) + "\n" +
                                EnumChatFormatting.RED + nf.format(LootCommand.minotaurs) + "\n" +
                                EnumChatFormatting.WHITE + nf.format(LootCommand.gaiaConstructs) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.minosChampions) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.minosInquisitors);
                    break;
                case "mythological_session":
                    dropsText = EnumChatFormatting.GOLD + "Coins:\n" +
                                EnumChatFormatting.WHITE + "Griffin Feathers:\n" +
                                EnumChatFormatting.GOLD + "Crown of Greeds:\n" +
                                EnumChatFormatting.AQUA + "Washed up Souvenirs:\n" +
                                EnumChatFormatting.RED + "Minos Hunters:\n" +
                                EnumChatFormatting.GRAY + "Siamese Lynxes:\n" +
                                EnumChatFormatting.RED + "Minotaurs:\n" +
                                EnumChatFormatting.WHITE + "Gaia Constructs:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Minos Champions:\n" +
                                EnumChatFormatting.GOLD + "Minos Inquisitors:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.mythCoinsSession) + "\n" +
                                EnumChatFormatting.WHITE + nf.format(LootCommand.griffinFeathersSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.crownOfGreedsSession) + "\n" +
                                EnumChatFormatting.AQUA + nf.format(LootCommand.washedUpSouvenirsSession) + "\n" +
                                EnumChatFormatting.RED + nf.format(LootCommand.minosHuntersSession) + "\n" +
                                EnumChatFormatting.GRAY + nf.format(LootCommand.siameseLynxesSession) + "\n" +
                                EnumChatFormatting.RED + nf.format(LootCommand.minotaursSession) + "\n" +
                                EnumChatFormatting.WHITE + nf.format(LootCommand.gaiaConstructsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.minosChampionsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.minosInquisitorsSession);
                    break;
                case "catacombs_floor_one":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                                EnumChatFormatting.BLUE + "Bonzo's Staffs:\n" +
                                EnumChatFormatting.AQUA + "Coins Spent:\n" +
                                EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulators) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooks) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.bonzoStaffs) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f1CoinsSpent) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f1TimeSpent);
                    break;
                case "catacombs_floor_one_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                                EnumChatFormatting.BLUE + "Bonzo's Staffs:\n" +
                                EnumChatFormatting.AQUA + "Coins Spent:\n" +
                                EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulatorsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooksSession) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.bonzoStaffsSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f1CoinsSpentSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f1TimeSpentSession);
                    break;
                case "catacombs_floor_two":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                                EnumChatFormatting.BLUE + "Scarf's Studies:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Adaptive Blades:\n" +
                                EnumChatFormatting.AQUA + "Coins Spent:\n" +
                                EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulators) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooks) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.scarfStudies) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.adaptiveSwords) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f2CoinsSpent) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f2TimeSpent);
                    break;
                case "catacombs_floor_two_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                                EnumChatFormatting.BLUE + "Scarf's Studies:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Adaptive Blades:\n" +
                                EnumChatFormatting.AQUA + "Coins Spent:\n" +
                                EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulatorsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooksSession) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.scarfStudiesSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.adaptiveSwordsSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f2CoinsSpentSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f2TimeSpentSession);
                    break;
                case "catacombs_floor_three":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Adaptive Helmets:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Adaptive Chestplates:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Adaptive Leggings:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Adaptive Boots:\n" +
                                EnumChatFormatting.AQUA + "Coins Spent:\n" +
                                EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulators) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooks) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.adaptiveHelms) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.adaptiveChests) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.adaptiveLegs) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.adaptiveBoots) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f3CoinsSpent) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f3TimeSpent);
                    break;
                case "catacombs_floor_three_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Adaptive Helmets:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Adaptive Chestplates:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Adaptive Leggings:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Adaptive Boots:\n" +
                                EnumChatFormatting.AQUA + "Coins Spent:\n" +
                                EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulatorsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooksSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.adaptiveHelmsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.adaptiveChestsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.adaptiveLegsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.adaptiveBootsSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f3CoinsSpentSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f3TimeSpentSession);
                    break;
                case "catacombs_floor_four":
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulators) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooks) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.spiritWings) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.spiritBones) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.spiritBoots) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.spiritSwords) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.spiritBows) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.epicSpiritPets) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.legSpiritPets) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f4CoinsSpent) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f4TimeSpent);
                    break;
                case "catacombs_floor_four_session":
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulatorsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooksSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.spiritWingsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.spiritBonesSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.spiritBootsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.spiritSwordsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.spiritBowsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.epicSpiritPetsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.legSpiritPetsSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f4CoinsSpentSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f4TimeSpentSession);
                    break;
                case "catacombs_floor_five":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                                EnumChatFormatting.BLUE + "Warped Stones:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Shadow Helmets:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Shadow Chestplates:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Shadow Leggings:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Shadow Boots:\n" +
                                EnumChatFormatting.GOLD + "Last Breaths:\n" +
                                EnumChatFormatting.GOLD + "Livid Daggers:\n" +
                                EnumChatFormatting.GOLD + "Shadow Furys:\n" +
                                EnumChatFormatting.AQUA + "Coins Spent:\n" +
                                EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulators) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooks) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.warpedStones) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.shadowAssHelms) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.shadowAssChests) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.shadowAssLegs) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.shadowAssBoots) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.lastBreaths) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.lividDaggers) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.shadowFurys) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f5CoinsSpent) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f5TimeSpent);
                    break;
                case "catacombs_floor_five_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                                EnumChatFormatting.BLUE + "Warped Stones:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Shadow Helmets:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Shadow Chestplates:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Shadow Leggings:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Shadow Boots:\n" +
                                EnumChatFormatting.GOLD + "Last Breaths:\n" +
                                EnumChatFormatting.GOLD + "Livid Daggers:\n" +
                                EnumChatFormatting.GOLD + "Shadow Furys:\n" +
                                EnumChatFormatting.AQUA + "Coins Spent:\n" +
                                EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulatorsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooksSession) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.warpedStonesSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.shadowAssHelmsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.shadowAssChestsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.shadowAssLegsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.shadowAssBootsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.lastBreathsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.lividDaggersSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.shadowFurysSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f5CoinsSpentSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f5TimeSpentSession);
                    break;
                case "catacombs_floor_six":
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulators) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooks) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.ancientRoses) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.precursorEyes) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.giantsSwords) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.necroLordHelms) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.necroLordChests) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.necroLordLegs) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.necroLordBoots) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.necroSwords) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f6CoinsSpent) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f6TimeSpent);
                    break;
                case "catacombs_floor_six_session":
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
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulatorsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooksSession) + "\n" +
                                EnumChatFormatting.BLUE + nf.format(LootCommand.ancientRosesSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.precursorEyesSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.giantsSwordsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.necroLordHelmsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.necroLordChestsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.necroLordLegsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.necroLordBootsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.necroSwordsSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f6CoinsSpentSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f6TimeSpentSession);
                    break;
                case "catacombs_floor_seven":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Wither Bloods:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Wither Cloaks:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Implosions:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Wither Shields:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Shadow Warps:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Necron's Handles:\n" +
                                EnumChatFormatting.GOLD + "Auto Recombobs:\n" +
                                EnumChatFormatting.GOLD + "Wither Helmets:\n" +
                                EnumChatFormatting.GOLD + "Wither Chests:\n" +
                                EnumChatFormatting.GOLD + "Wither Leggings:\n" +
                                EnumChatFormatting.GOLD + "Wither Boots:\n" +
                                EnumChatFormatting.AQUA + "Coins Spent:\n" +
                                EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulators) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooks) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.witherBloods) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.witherCloaks) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.implosions) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.witherShields) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.shadowWarps) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.necronsHandles) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.autoRecombs) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.witherHelms) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.witherChests) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.witherLegs) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.witherBoots) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f7CoinsSpent) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f7TimeSpent);
                    break;
                case "catacombs_floor_seven_session":
                    dropsText = EnumChatFormatting.GOLD + "Recombobulators:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Fuming Potato Books:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Wither Bloods:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Wither Cloaks:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Implosions:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Wither Shields:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Shadow Warps:\n" +
                                EnumChatFormatting.DARK_PURPLE + "Necron's Handles:\n" +
                                EnumChatFormatting.GOLD + "Auto Recombobulators:\n" +
                                EnumChatFormatting.GOLD + "Wither Helmets:\n" +
                                EnumChatFormatting.GOLD + "Wither Chests:\n" +
                                EnumChatFormatting.GOLD + "Wither Leggings:\n" +
                                EnumChatFormatting.GOLD + "Wither Boots:\n" +
                                EnumChatFormatting.AQUA + "Coins Spent:\n" +
                                EnumChatFormatting.AQUA + "Time Spent:";
                    countText = EnumChatFormatting.GOLD + nf.format(LootCommand.recombobulatorsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.fumingPotatoBooksSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.witherBloodsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.witherCloaksSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.implosionsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.witherShieldsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.shadowWarpsSession) + "\n" +
                                EnumChatFormatting.DARK_PURPLE + nf.format(LootCommand.necronsHandlesSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.autoRecombsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.witherHelmsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.witherChestsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.witherLegsSession) + "\n" +
                                EnumChatFormatting.GOLD + nf.format(LootCommand.witherBootsSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getMoneySpent(LootCommand.f7CoinsSpentSession) + "\n" +
                                EnumChatFormatting.AQUA + Utils.getTimeBetween(0, LootCommand.f7TimeSpentSession);
                    break;
                default:
                    System.out.println("Display was an unknown value, turning off.");
                    DisplayCommand.display = "off";
                    ConfigHandler.writeStringConfig("misc", "display", "off");
            }
            new TextRenderer(mc, dropsText, MoveCommand.displayXY[0], MoveCommand.displayXY[1], ScaleCommand.displayScale);
            new TextRenderer(mc, countText, (int) (MoveCommand.displayXY[0] + (110 * ScaleCommand.displayScale)), MoveCommand.displayXY[1], ScaleCommand.displayScale);
        }

        if (showTitle) {
            Utils.drawTitle(titleText);
        }
        if (showSkill) {
            new TextRenderer(mc, skillText, MoveCommand.skill50XY[0], MoveCommand.skill50XY[1], ScaleCommand.skill50Scale);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onTooltipLow(ItemTooltipEvent event) {
        if (event.toolTip == null) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        if (mc.currentScreen instanceof GuiChest) {
            ContainerChest chest = (ContainerChest) player.openContainer;
            IInventory inv = chest.getLowerChestInventory();
            String chestName = inv.getDisplayName().getUnformattedText();

            if (ToggleCommand.hideTooltipsInExperimentAddonsToggled && (chestName.startsWith("Ultrasequencer (") || chestName.startsWith("Chronomatron ("))) {
                event.toolTip.clear();
            }

            if (ToggleCommand.clickInOrderToggled && chestName.equals("Click in order!")) {
                event.toolTip.clear();
            }

        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayerSP player = mc.thePlayer;

        if (mc.currentScreen == null && System.currentTimeMillis() - lastInteractTime >= 250L) {
            slotIn = -1;
            lastSlot = -1;
            mazeId = 0;
        }
        if (keyBindings[6].isKeyDown())
            for (int i = 0; i <= 8; i++) {
                ItemStack item = player.inventory.getStackInSlot(i);
                if ((item != null && item.getDisplayName().contains("Hyperion")) || (item != null && item.getDisplayName().contains("Aspect of the End"))) {
                    player.inventory.currentItem = i;
                    mc.playerController.sendUseItem(mc.thePlayer, world, player.inventory.getStackInSlot(i));
                    break;
                }
            }
        if (keyBindings[7].isKeyDown()) {
            if (mc.objectMouseOver.getBlockPos() == null) return;
            Block block = (Minecraft.getMinecraft()).theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
            ArrayList<Block> interactables = new ArrayList<>(Arrays.asList(new Block[] {
                    Blocks.acacia_door, Blocks.anvil, Blocks.beacon, Blocks.bed, Blocks.birch_door, Blocks.brewing_stand, Blocks.command_block, Blocks.crafting_table, Blocks.chest, Blocks.dark_oak_door,
                    Blocks.daylight_detector, Blocks.daylight_detector_inverted, Blocks.dispenser, Blocks.dropper, Blocks.enchanting_table, Blocks.ender_chest, Blocks.furnace, Blocks.hopper, Blocks.jungle_door, Blocks.lever,
                    Blocks.noteblock, Blocks.powered_comparator, Blocks.unpowered_comparator, Blocks.powered_repeater, Blocks.unpowered_repeater, Blocks.standing_sign, Blocks.wall_sign, Blocks.trapdoor, Blocks.trapped_chest, Blocks.wooden_button,
                    Blocks.stone_button, Blocks.oak_door, Blocks.skull }));
            if (!interactables.contains(block)) {
                world.setBlockToAir(mc.objectMouseOver.getBlockPos());
            }
        }

        // Checks every second
        tickAmount++;
        if (tickAmount % 20 == 0) {
            if (player != null) {
                Utils.checkForSkyblock();
                Utils.checkForDungeons();
            }

            if (DisplayCommand.auto && world != null && player != null) {
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
                        } else if (sCleaned.contains("F7")) {
                            DisplayCommand.display = "catacombs_floor_seven";
                        }
                        found = true;
                    }
                }
                for (int i = 0; i < 8; i++) {
                    ItemStack hotbarItem = player.inventory.getStackInSlot(i);
                    if (hotbarItem == null) continue;
                    if (hotbarItem.getDisplayName().contains("Ancestral Spade")) {
                        DisplayCommand.display = "mythological";
                        found = true;
                    }
                }
                if (!found) DisplayCommand.display = "off";
                ConfigHandler.writeStringConfig("misc", "display", DisplayCommand.display);
            }

            if (ToggleCommand.creeperToggled && Utils.inDungeons && world != null && player != null) {
                double x = player.posX;
                double y = player.posY;
                double z = player.posZ;
                // Find creepers nearby
                AxisAlignedBB creeperScan = new AxisAlignedBB(x - 14, y - 8, z - 13, x + 14, y + 8, z + 13); // 28x16x26 cube
                List<EntityCreeper> creepers = world.getEntitiesWithinAABB(EntityCreeper.class, creeperScan);
                // Check if creeper is nearby
                if (creepers.size() > 0 && !creepers.get(0).isInvisible()) { // Don't show Wither Cloak creepers
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
                        Block block = world.getBlockState(blockPos).getBlock();
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

            if (ToggleCommand.waterToggled && Utils.inDungeons && world != null && player != null) {
                // multi thread block checking
                new Thread(() -> {
                    prevInWaterRoom = inWaterRoom;
                    inWaterRoom = false;
                    boolean foundPiston = false;
                    boolean done = false;
                    for (int x = (int) (player.posX - 13); x <= player.posX + 13; x++) {
                        for (int z = (int) (player.posZ - 13); z <= player.posZ + 13; z++) {
                            BlockPos blockPos = new BlockPos(x, 54, z);
                            if (world.getBlockState(blockPos).getBlock() == Blocks.sticky_piston) {
                                foundPiston = true;
                                break;
                            }
                        }
                        if (foundPiston) break;
                    }

                    if (foundPiston) {
                        for (int x = (int) (player.posX - 25); x <= player.posX + 25; x++) {
                            for (int z = (int) (player.posZ - 25); z <= player.posZ + 25; z++) {
                                BlockPos blockPos = new BlockPos(x, 82, z);
                                if (world.getBlockState(blockPos).getBlock() == Blocks.piston_head) {
                                    inWaterRoom = true;
                                    if (!prevInWaterRoom) {
                                        boolean foundGold = false;
                                        boolean foundClay = false;
                                        boolean foundEmerald = false;
                                        boolean foundQuartz = false;
                                        boolean foundDiamond = false;

                                        // Detect first blocks near water stream
                                        BlockPos scan1 = new BlockPos(x + 1, 78, z + 1);
                                        BlockPos scan2 = new BlockPos(x - 1, 77, z - 1);
                                        Iterable<BlockPos> blocks = BlockPos.getAllInBox(scan1, scan2);
                                        for (BlockPos puzzleBlockPos : blocks) {
                                            Block block = world.getBlockState(puzzleBlockPos).getBlock();
                                            if (block == Blocks.gold_block) {
                                                foundGold = true;
                                            } else if (block == Blocks.hardened_clay) {
                                                foundClay = true;
                                            } else if (block == Blocks.emerald_block) {
                                                foundEmerald = true;
                                            } else if (block == Blocks.quartz_block) {
                                                foundQuartz = true;
                                            } else if (block == Blocks.diamond_block) {
                                                foundDiamond = true;
                                            }
                                        }

                                        int variant = 0;
                                        if (foundGold && foundClay) {
                                            variant = 1;
                                        } else if (foundEmerald && foundQuartz) {
                                            variant = 2;
                                        } else if (foundQuartz && foundDiamond) {
                                            variant = 3;
                                        } else if (foundGold && foundQuartz) {
                                            variant = 4;
                                        }

                                        // Return solution
                                        String purple;
                                        String orange;
                                        String blue;
                                        String green;
                                        String red;
                                        switch (variant) {
                                            case 1:
                                                purple = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.AQUA + "Diamond, " + EnumChatFormatting.RED + "Clay";
                                                orange = EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.DARK_GRAY + "Coal, " + EnumChatFormatting.GREEN + "Emerald";
                                                blue = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";
                                                green = EnumChatFormatting.GREEN + "Emerald";
                                                red = EnumChatFormatting.GRAY + "None";
                                                break;
                                            case 2:
                                                purple = EnumChatFormatting.DARK_GRAY + "Coal";
                                                orange = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";
                                                blue = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.AQUA + "Diamond, " + EnumChatFormatting.GREEN + "Emerald";
                                                green = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.GREEN + "Emerald";
                                                red = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.DARK_GRAY + "Coal, " + EnumChatFormatting.GREEN + "Emerald";
                                                break;
                                            case 3:
                                                purple = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.AQUA + "Diamond";
                                                orange = EnumChatFormatting.GREEN + "Emerald";
                                                blue = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.AQUA + "Diamond";
                                                green = EnumChatFormatting.GRAY + "None";
                                                red = EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald";
                                                break;
                                            case 4:
                                                purple = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";
                                                orange = EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.DARK_GRAY + "Coal";
                                                blue = EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.DARK_GRAY + "Coal, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";
                                                green = EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald";
                                                red = EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.AQUA + "Diamond, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";
                                                break;
                                            default:
                                                purple = orange = blue = green = red = ERROR_COLOUR + "Error detecting water puzzle variant.";
                                                break;
                                        }
                                        waterAnswers = MAIN_COLOUR + "The following levers must be down:\n" +
                                                       EnumChatFormatting.DARK_PURPLE + "Purple: " + purple + "\n" +
                                                       EnumChatFormatting.GOLD + "Orange: " + orange + "\n" +
                                                       EnumChatFormatting.BLUE + "Blue: " + blue + "\n" +
                                                       EnumChatFormatting.GREEN + "Green: " + green + "\n" +
                                                       EnumChatFormatting.RED + "Red: " + red;
                                        done = true;
                                        break;
                                    }
                                }
                            }
                            if (done) break;
                        }
                    } else {
                        waterAnswers = null;
                    }
                }).start();
            }

            if (ToggleCommand.lividSolverToggled && Utils.inDungeons && !foundLivid && world != null) {
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
                    List<Entity> loadedLivids = new ArrayList<>();
                    List<Entity> entities = world.getLoadedEntityList();
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

            if (ToggleCommand.ticTacToeToggled && Utils.inDungeons && world != null && player != null) {
                correctTicTacToeButton = null;
                AxisAlignedBB aabb = new AxisAlignedBB(player.posX - 6, player.posY - 6, player.posZ - 6, player.posX + 6, player.posY + 6, player.posZ + 6);
                List<EntityItemFrame> itemFrames = world.getEntitiesWithinAABB(EntityItemFrame.class, aabb);
                List<EntityItemFrame> itemFramesWithMaps = new ArrayList<>();
                // Find how many item frames have maps already placed
                for (EntityItemFrame itemFrame : itemFrames) {
                    ItemStack item = itemFrame.getDisplayedItem();
                    if (item == null || !(item.getItem() instanceof ItemMap)) continue;
                    MapData mapData = ((ItemMap) item.getItem()).getMapData(item, world);
                    if (mapData == null) continue;

                    itemFramesWithMaps.add(itemFrame);
                }

                // Only run when it's your turn
                if (itemFramesWithMaps.size() != 9 && itemFramesWithMaps.size() % 2 == 1) {
                    char[][] board = new char[3][3];
                    BlockPos leftmostRow = null;
                    int sign = 1;
                    char facing = 'X';
                    for (EntityItemFrame itemFrame : itemFramesWithMaps) {
                        ItemStack map = itemFrame.getDisplayedItem();
                        MapData mapData = ((ItemMap) map.getItem()).getMapData(map, world);

                        // Find position on board
                        int row = 0;
                        int column;
                        sign = 1;

                        if (itemFrame.facingDirection == EnumFacing.SOUTH || itemFrame.facingDirection == EnumFacing.WEST) {
                            sign = -1;
                        }

                        BlockPos itemFramePos = new BlockPos(itemFrame.posX, Math.floor(itemFrame.posY), itemFrame.posZ);
                        for (int i = 2; i >= 0; i--) {
                            int realI = i * sign;
                            BlockPos blockPos = itemFramePos;
                            if (itemFrame.posX % 0.5 == 0) {
                                blockPos = itemFramePos.add(realI, 0, 0);
                            } else if (itemFrame.posZ % 0.5 == 0) {
                                blockPos = itemFramePos.add(0, 0, realI);
                                facing = 'Z';
                            }
                            Block block = world.getBlockState(blockPos).getBlock();
                            if (block == Blocks.air || block == Blocks.stone_button) {
                                leftmostRow = blockPos;
                                row = i;
                                break;
                            }
                        }

                        if (itemFrame.posY == 72.5) {
                            column = 0;
                        } else if (itemFrame.posY == 71.5) {
                            column = 1;
                        } else if (itemFrame.posY == 70.5) {
                            column = 2;
                        } else {
                            continue;
                        }

                        // Get colour
                        // Middle pixel = 64*128 + 64 = 8256
                        int colourInt = mapData.colors[8256] & 255;
                        if (colourInt == 114) {
                            board[column][row] = 'X';
                        } else if (colourInt == 33) {
                            board[column][row] = 'O';
                        }
                    }
                    System.out.println("Board: " + Arrays.deepToString(board));

                    // Draw best move
                    int bestMove = TicTacToeUtils.getBestMove(board) - 1;
                    System.out.println("Best move slot: " + bestMove);
                    if (leftmostRow != null) {
                        double drawX = facing == 'X' ? leftmostRow.getX() - sign * (bestMove % 3) : leftmostRow.getX();
                        double drawY = 72 - Math.floor(bestMove / 3);
                        double drawZ = facing == 'Z' ? leftmostRow.getZ() - sign * (bestMove % 3) : leftmostRow.getZ();

                        correctTicTacToeButton = new AxisAlignedBB(drawX, drawY, drawZ, drawX + 1, drawY + 1, drawZ + 1);
                    }
                }
            }

            tickAmount = 0;
        }

        // Checks 5 times per second
        if (tickAmount % 4 == 0) {
            if (ToggleCommand.blazeToggled && Utils.inDungeons && world != null) {
                List<Entity> entities = world.getLoadedEntityList();
                int highestHealth = 0;
                highestBlaze = null;
                int lowestHealth = 99999999;
                lowestBlaze = null;

                for (Entity entity : entities) {
                    if (entity.getName().contains("Blaze") && entity.getName().contains("/")) {
                        String blazeName = StringUtils.stripControlCodes(entity.getName());
                        try {
                            int health = Integer.parseInt(blazeName.substring(blazeName.indexOf("/") + 1, blazeName.length() - 1));
                            if (health > highestHealth) {
                                highestHealth = health;
                                highestBlaze = entity;
                            }
                            if (health < lowestHealth) {
                                lowestHealth = health;
                                lowestBlaze = entity;
                            }
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }

        // Checks 10 times per second
        if (tickAmount % 2 == 0) {
            if (ToggleCommand.lowHealthNotifyToggled && Utils.inDungeons && world != null) {
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                for (String score : scoreboard) {
                    if (score.endsWith("❤") && score.matches(".*§c\\d.*")) {
                        String name = score.substring(score.indexOf(" ") + 1);
                        Utils.createTitle(EnumChatFormatting.RED + "LOW HEALTH!\n" + name, 1);
                        break;
                    }
                }
            }
        }

        // Runs 20 times per second
        if (mc.currentScreen instanceof GuiChest) {
            if (player == null) return;
            ContainerChest chest = (ContainerChest) player.openContainer;
            List<Slot> invSlots = ((GuiChest) mc.currentScreen).inventorySlots.inventorySlots;
            String chestName = chest.getLowerChestInventory().getDisplayName().getUnformattedText().trim();

            if (ToggleCommand.ultrasequencerToggled && chestName.startsWith("Ultrasequencer (")) {
                if (invSlots.get(49).getStack() != null && invSlots.get(49).getStack().getDisplayName().equals("§aRemember the pattern!")) {
                    for (int i = 9; i <= 44; i++) {
                        if (invSlots.get(i) == null || invSlots.get(i).getStack() == null) continue;
                        String itemName = StringUtils.stripControlCodes(invSlots.get(i).getStack().getDisplayName());
                        if (itemName.matches("\\d+")) {
                            int number = Integer.parseInt(itemName);
                            clickInOrderSlots[number - 1] = invSlots.get(i);
                        }
                    }
                }
            }

            if (chestName.equals("Click in order!") && until == 100000) {
                for (int i = 10; i <= 25; i++) {
                    if (i != 17 && i != 18) {
                        Container container = mc.thePlayer.openContainer;
                        IInventory chestInventory = ((ContainerChest) container).getLowerChestInventory();
                        ItemStack click = chestInventory.getStackInSlot(i);
                        if (click != null) {
                            if (click.getItemDamage() != 14) break;
                            if ((chestInventory.getStackInSlot(i)).stackSize == 1 && terminalNumberNeeded[0] == 0) {
                                terminalNumberNeeded[0] = i;
                            }
                            if (terminalNumberNeeded[0] != 0 || terminalNumberNeeded[1] != 0) {
                                for (int j = 1; j < terminalNumberNeeded.length; j++) {
                                    ItemStack prevClick = chestInventory.getStackInSlot(terminalNumberNeeded[j - 1]);
                                    if (prevClick != null) {
                                        if (prevClick.getItemDamage() != 14)
                                            break;
                                        if (terminalNumberNeeded[j] == 0 && click.stackSize - (chestInventory.getStackInSlot(terminalNumberNeeded[j - 1])).stackSize == 1) {
                                            terminalNumberNeeded[j] = i;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (ToggleCommand.superpairsToggled && chestName.startsWith("Superpairs (")) {
                for (int i = 0; i < 53; i++) {
                    ItemStack itemStack = invSlots.get(i).getStack();
                    if (itemStack == null) continue;
                    String itemName = itemStack.getDisplayName();
                    if (Item.getIdFromItem(itemStack.getItem()) == 95 || Item.getIdFromItem(itemStack.getItem()) == 160) continue;
                    if (itemName.contains("Instant Find") || itemName.contains("Gained +")) continue;
                    if (itemName.contains("Enchanted Book")) {
                        itemName = itemStack.getTooltip(mc.thePlayer, false).get(3);
                    }
                    if (itemStack.stackSize > 1) {
                        itemName = itemStack.stackSize + " " + itemName;
                    }
                    if (experimentTableSlots[i] != null) continue;
                    experimentTableSlots[i] = itemStack.copy().setStackDisplayName(itemName);
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
            } else {
                switch (guiToOpen) {
                    case "displaygui":
                        mc.displayGuiScreen(new DisplayGui());
                        break;
                    case "onlyslayergui":
                        mc.displayGuiScreen(new OnlySlayerGui());
                        break;
                    case "editlocations":
                        mc.displayGuiScreen(new EditLocationsGui());
                        break;
                    case "puzzlesolvers":
                        mc.displayGuiScreen(new PuzzleSolversGui(1));
                        break;
                    case "experimentsolvers":
                        mc.displayGuiScreen(new ExperimentsGui());
                        break;
                    case "skilltracker":
                        mc.displayGuiScreen(new SkillTrackerGui());
                        break;
                }
            }
            guiToOpen = null;
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (ToggleCommand.blazeToggled) {
            if (lowestBlaze != null) {
                BlockPos stringPos = new BlockPos(lowestBlaze.posX, lowestBlaze.posY + 1, lowestBlaze.posZ);
                Utils.draw3DString(stringPos, EnumChatFormatting.BOLD + "Smallest", LOWEST_BLAZE_COLOUR, event.partialTicks);
                AxisAlignedBB aabb = new AxisAlignedBB(lowestBlaze.posX - 0.5, lowestBlaze.posY - 2, lowestBlaze.posZ - 0.5, lowestBlaze.posX + 0.5, lowestBlaze.posY, lowestBlaze.posZ + 0.5);
                Utils.draw3DBox(aabb, LOWEST_BLAZE_COLOUR, event.partialTicks);
            }
            if (highestBlaze != null) {
                BlockPos stringPos = new BlockPos(highestBlaze.posX, highestBlaze.posY + 1, highestBlaze.posZ);
                Utils.draw3DString(stringPos, EnumChatFormatting.BOLD + "Biggest", HIGHEST_BLAZE_COLOUR, event.partialTicks);
                AxisAlignedBB aabb = new AxisAlignedBB(highestBlaze.posX - 0.5, highestBlaze.posY - 2, highestBlaze.posZ - 0.5, highestBlaze.posX + 0.5, highestBlaze.posY, highestBlaze.posZ + 0.5);
                Utils.draw3DBox(aabb, HIGHEST_BLAZE_COLOUR, event.partialTicks);
            }
        }
        if (ToggleCommand.creeperToggled && drawCreeperLines && !creeperLines.isEmpty()) {
            for (int i = 0; i < creeperLines.size(); i++) {
                Utils.draw3DLine(creeperLines.get(i)[0], creeperLines.get(i)[1], CREEPER_COLOURS[i % 10], event.partialTicks);
            }
        }
        if (ToggleCommand.ticTacToeToggled && correctTicTacToeButton != null) {
            Utils.draw3DBox(correctTicTacToeButton, 0x40FF40, event.partialTicks);
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (Minecraft.getMinecraft().thePlayer != event.entityPlayer) return;
        ItemStack item = event.entityPlayer.getHeldItem();
        if (item == null) return;

        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {

            Block block = Minecraft.getMinecraft().theWorld.getBlockState(event.pos).getBlock();

            if (ToggleCommand.blockBreakingFarmsToggled) {
                ArrayList<Item> tools = new ArrayList<>(Arrays.asList(
                        Items.wooden_hoe,
                        Items.stone_hoe,
                        Items.iron_hoe,
                        Items.golden_hoe,
                        Items.diamond_hoe,
                        Items.wooden_axe,
                        Items.stone_axe,
                        Items.iron_axe,
                        Items.golden_axe,
                        Items.diamond_axe
                ));

                ArrayList<Block> farmBlocks = new ArrayList<>(Arrays.asList(
                        Blocks.dirt,
                        Blocks.farmland,
                        Blocks.carpet,
                        Blocks.glowstone,
                        Blocks.sea_lantern
                ));

                if (tools.contains(item.getItem()) && farmBlocks.contains(block)) {
                    event.setCanceled(true);
                }

            }
        }

        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            Minecraft mc = Minecraft.getMinecraft();
            if (ToggleCommand.aotdToggled && item.getDisplayName().contains("Aspect of the Dragons")) {
                event.setCanceled(true);
            }
            if (ToggleCommand.lividDaggerToggled && item.getDisplayName().contains("Livid Dagger")) {
                event.setCanceled(true);
            }
            if (ToggleCommand.notifySlayerSlainToggled) {
                if (ScoreboardHandler.getSidebarLines().stream().anyMatch(x -> ScoreboardHandler.cleanSB(x).contains("Boss slain!"))) {
                    if (ScoreboardHandler.getSidebarLines().stream().anyMatch(x -> {
                        String line = ScoreboardHandler.cleanSB(x);
                        return Arrays.stream(new String[]{"Howling Cave", "Ruins", "Graveyard", "Coal Mine", "Spider's Den"}).anyMatch(line::contains);
                    })) {
                        if (Utils.hasRightClickAbility(item)) {
                            List<String> lore = Utils.getItemLore(item);

                            int abilityLine = -1;
                            for (int i = 0; i < lore.size(); i++) {
                                String line = StringUtils.stripControlCodes(lore.get(i));
                                if (line.startsWith("Item Ability:")) abilityLine = i;
                                if (abilityLine != -1 && i > abilityLine) {
                                    if (line.toLowerCase().contains("damage")) {
                                        Utils.createTitle(EnumChatFormatting.RED + "Boss slain!", 2);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            Minecraft mc = Minecraft.getMinecraft();
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(event.pos).getBlock();

            ArrayList<Block> interactables = new ArrayList<>(Arrays.asList(
                    Blocks.acacia_door,
                    Blocks.anvil,
                    Blocks.beacon,
                    Blocks.bed,
                    Blocks.birch_door,
                    Blocks.brewing_stand,
                    Blocks.command_block,
                    Blocks.crafting_table,
                    Blocks.chest,
                    Blocks.dark_oak_door,
                    Blocks.daylight_detector,
                    Blocks.daylight_detector_inverted,
                    Blocks.dispenser,
                    Blocks.dropper,
                    Blocks.enchanting_table,
                    Blocks.ender_chest,
                    Blocks.furnace,
                    Blocks.hopper,
                    Blocks.jungle_door,
                    Blocks.lever,
                    Blocks.noteblock,
                    Blocks.powered_comparator,
                    Blocks.unpowered_comparator,
                    Blocks.powered_repeater,
                    Blocks.unpowered_repeater,
                    Blocks.standing_sign,
                    Blocks.wall_sign,
                    Blocks.trapdoor,
                    Blocks.trapped_chest,
                    Blocks.wooden_button,
                    Blocks.stone_button,
                    Blocks.oak_door,
                    Blocks.skull
            ));
            ArrayList<Block> flowerPlaceable = new ArrayList<>(Arrays.asList(
                    Blocks.grass,
                    Blocks.dirt,
                    Blocks.flower_pot,
                    Blocks.tallgrass,
                    Blocks.double_plant
            ));
            if (Utils.inDungeons) {
                interactables.add(Blocks.coal_block);
                interactables.add(Blocks.stained_hardened_clay);
            }
            if (flowerPlaceable.contains(block)) {
                if (ToggleCommand.flowerWeaponsToggled && item.getDisplayName().contains("Flower of Truth")) {
                    event.setCanceled(true);
                }
                if (ToggleCommand.flowerWeaponsToggled && item.getDisplayName().contains("Spirit Sceptre")) {
                    event.setCanceled(true);
                }
            }
            if (!interactables.contains(block)) {
                if (ToggleCommand.aotdToggled && item.getDisplayName().contains("Aspect of the Dragons")) {
                    event.setCanceled(true);
                }
                if (ToggleCommand.lividDaggerToggled && item.getDisplayName().contains("Livid Dagger")) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != event.entityPlayer) return;

        if (ToggleCommand.itemFrameOnSeaLanternsToggled && Utils.inDungeons && event.target instanceof EntityItemFrame) {
            EntityItemFrame itemFrame = (EntityItemFrame) event.target;
            ItemStack item = itemFrame.getDisplayedItem();
            if (item == null || item.getItem() != Items.arrow) return;
            BlockPos blockPos = Utils.getBlockUnderItemFrame(itemFrame);
            if (mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.sea_lantern) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onKey(KeyInputEvent event) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;

        if (keyBindings[0].isPressed()) {
            player.sendChatMessage(lastMaddoxCommand);
        }
        if (keyBindings[1].isPressed()) {
            if (Utils.inDungeons) {
                player.dropOneItem(true);
            }
        }
        if (keyBindings[2].isPressed()) {
            if (skillStopwatch.isStarted() && skillStopwatch.isSuspended()) {
                skillStopwatch.resume();
                player.addChatMessage(new ChatComponentText(MAIN_COLOUR + "Skill tracker started."));
            } else if (!skillStopwatch.isStarted()) {
                skillStopwatch.start();
                player.addChatMessage(new ChatComponentText(MAIN_COLOUR + "Skill tracker started."));
            } else if (skillStopwatch.isStarted() && !skillStopwatch.isSuspended()) {
                skillStopwatch.suspend();
                player.addChatMessage(new ChatComponentText(MAIN_COLOUR + "Skill tracker paused."));
            }
        }
        if (keyBindings[3].isPressed()) {
            int[] order = new int[9];
            for (int i = 0; i <= 8; i++) {
                ItemStack item = player.inventory.getStackInSlot(i);
                if (item != null && item.getDisplayName().contains("Bonemerang"))
                    order[i] = 1;
                if ((item != null && item.getDisplayName().contains("Giant's Sword")) || (item != null && item.getDisplayName().contains("Emerald")))
                    this.sword = i;
                if (item != null && item.getDisplayName().contains("Bow"))
                    this.bow = i;
            }
            new Thread(() -> {
                for (int i = 0; i <= 8; i++) {
                    if (order[i] != 0) {
                        player.inventory.currentItem = i;
                        mc.playerController.sendUseItem(mc.thePlayer, world, player.inventory.getStackInSlot(i));
                        try {
                           Thread.sleep(DelayCommand.boneDelay);
                        } catch (InterruptedException e) {
                           e.printStackTrace();
                        }
                    }
                }
                if (this.sword != 10 && this.bow != 10 && SwapCommand.swapDelay != 0) {
                  player.inventory.currentItem = this.sword;
                  try {
                    Thread.sleep(SwapCommand.swapDelay);
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                  player.inventory.currentItem = this.bow;
                }
            }).start();
        }
        if (keyBindings[4].isPressed()) {
          mazeId = 0;
          slotIn = -1;
        }
        if (keyBindings[5].isPressed()) {
            for (int i = 0; i <= SimonCommand.simonAmount; i++) {
                try {
                    Method method = mc.getClass().getDeclaredMethod("func_147121_ag", new Class[0]);
                    method.setAccessible(true);
                    method.invoke(mc, new Object[0]);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiMouseInputPre(GuiScreenEvent.MouseInputEvent.Pre event) {
        if (Mouse.getEventButton() != 0 && Mouse.getEventButton() != 1 && Mouse.getEventButton() != 2)
            return; // Left click, middle click or right click
        if (!Mouse.getEventButtonState()) return;

        if (event.gui instanceof GuiChest) {
            Container containerChest = ((GuiChest) event.gui).inventorySlots;
            if (containerChest instanceof ContainerChest) {
                // a lot of declarations here, if you get scarred, my bad
                GuiChest chest = (GuiChest) event.gui;
                IInventory inventory = ((ContainerChest) containerChest).getLowerChestInventory();
                Slot mouseSlot = chest.getSlotUnderMouse();
                if (mouseSlot == null) return;
                ItemStack item = mouseSlot.getStack();
                String inventoryName = inventory.getDisplayName().getUnformattedText();

                if (ToggleCommand.stopSalvageStarredToggled && inventoryName.startsWith("Salvage")) {
                    if (item == null) return;
                    boolean inSalvageGui = false;
                    if (item.getDisplayName().contains("Salvage") || item.getDisplayName().contains("Essence")) {
                        ItemStack salvageItem = inventory.getStackInSlot(13);
                        if (salvageItem == null) return;
                        item = salvageItem;
                        inSalvageGui = true;
                    }
                    if (item.getDisplayName().contains("✪") && (mouseSlot.slotNumber > 53 || inSalvageGui)) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ERROR_COLOUR + "Danker's Skyblock Mod has stopped you from salvaging that item!"));
                        event.setCanceled(true);
                        return;
                    }
                }

                if (inventoryName.endsWith(" Chest") && item != null && item.getDisplayName().contains("Open Reward Chest")) {
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
                                        LootCommand.f1CoinsSpent += coinsSpent;
                                        LootCommand.f1CoinsSpentSession += coinsSpent;
                                        ConfigHandler.writeDoubleConfig("catacombs", "floorOneCoins", LootCommand.f1CoinsSpent);
                                    } else if (sCleaned.contains("F2")) {
                                        LootCommand.f2CoinsSpent += coinsSpent;
                                        LootCommand.f2CoinsSpentSession += coinsSpent;
                                        ConfigHandler.writeDoubleConfig("catacombs", "floorTwoCoins", LootCommand.f2CoinsSpent);
                                    } else if (sCleaned.contains("F3")) {
                                        LootCommand.f3CoinsSpent += coinsSpent;
                                        LootCommand.f3CoinsSpentSession += coinsSpent;
                                        ConfigHandler.writeDoubleConfig("catacombs", "floorThreeCoins", LootCommand.f3CoinsSpent);
                                    } else if (sCleaned.contains("F4")) {
                                        LootCommand.f4CoinsSpent += coinsSpent;
                                        LootCommand.f4CoinsSpentSession += coinsSpent;
                                        ConfigHandler.writeDoubleConfig("catacombs", "floorFourCoins", LootCommand.f4CoinsSpent);
                                    } else if (sCleaned.contains("F5")) {
                                        LootCommand.f5CoinsSpent += coinsSpent;
                                        LootCommand.f5CoinsSpentSession += coinsSpent;
                                        ConfigHandler.writeDoubleConfig("catacombs", "floorFiveCoins", LootCommand.f5CoinsSpent);
                                    } else if (sCleaned.contains("F6")) {
                                        LootCommand.f6CoinsSpent += coinsSpent;
                                        LootCommand.f6CoinsSpentSession += coinsSpent;
                                        ConfigHandler.writeDoubleConfig("catacombs", "floorSixCoins", LootCommand.f6CoinsSpent);
                                    } else if (sCleaned.contains("F7")) {
                                        LootCommand.f7CoinsSpent += coinsSpent;
                                        LootCommand.f7CoinsSpentSession += coinsSpent;
                                        ConfigHandler.writeDoubleConfig("catacombs", "floorSevenCoins", LootCommand.f7CoinsSpent);
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }

                if (ToggleCommand.chronomatronToggled && inventoryName.startsWith("Chronomatron (")) {
                    if (item == null) {
                        if (event.isCancelable() && !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
                            event.setCanceled(true);
                        return;
                    }
                    if (inventory.getStackInSlot(49).getDisplayName().startsWith("§7Timer: §a") && (item.getItem() == Item.getItemFromBlock(Blocks.stained_glass) || item.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay))) {
                        if (chronomatronPattern.size() > chronomatronMouseClicks && !item.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks))) {
                            if (event.isCancelable() && !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
                                event.setCanceled(true);
                            return;
                        }
                        chronomatronMouseClicks++;
                    } else if (inventory.getStackInSlot(49).getDisplayName().startsWith("§aRemember the pattern!")) {
                        if (event.isCancelable()) event.setCanceled(true);
                        return;
                    }
                }

                if (ToggleCommand.ultrasequencerToggled && inventoryName.startsWith("Ultrasequencer (")) {
                    if (item == null) {
                        if (event.isCancelable() && !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
                            event.setCanceled(true);
                        return;
                    }
                    if (inventory.getStackInSlot(49).getDisplayName().equals("§aRemember the pattern!")) {
                        if (event.isCancelable()) event.setCanceled(true);
                        return;
                    } else if (inventory.getStackInSlot(49).getDisplayName().startsWith("§7Timer: §a")) {
                        if (clickInOrderSlots[lastUltraSequencerClicked] != null && mouseSlot.getSlotIndex() != clickInOrderSlots[lastUltraSequencerClicked].getSlotIndex()) {
                            if (event.isCancelable() && !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
                                event.setCanceled(true);
                            return;
                        }
                    }
                }

                if (ToggleCommand.blockWrongTerminalClicksToggled && Utils.inDungeons) {
                    boolean shouldCancel = false;

                    if (item == null) return;

                    //most of these are extra but who cares

                    switch (inventoryName) {
                        case "Correct all the panes!":
                            shouldCancel = !StringUtils.stripControlCodes(item.getDisplayName()).startsWith("Off");
                            break;
                        case "Navigate the maze!":
                            if (item.getItem() != Item.getItemFromBlock(Blocks.stained_glass_pane)) {
                                shouldCancel = true;
                                break;
                            }

                            if (item.getItemDamage() != 0) {
                                shouldCancel = true;
                                break;
                            }

                            boolean isValid = false;

                            int slotIndex = mouseSlot.getSlotIndex();

                            if (slotIndex % 9 != 8 && slotIndex != 53) {
                                ItemStack itemStack = inventory.getStackInSlot(slotIndex + 1);
                                if (itemStack != null && itemStack.getItemDamage() == 5) isValid = true;
                            }

                            if (!isValid && slotIndex % 9 != 0 && slotIndex != 0) {
                                ItemStack itemStack = inventory.getStackInSlot(slotIndex - 1);
                                if (itemStack != null && itemStack.getItemDamage() == 5) isValid = true;
                            }

                            if (!isValid && slotIndex <= 44) {
                                ItemStack itemStack = inventory.getStackInSlot(slotIndex + 9);
                                if (itemStack != null && itemStack.getItemDamage() == 5) isValid = true;
                            }

                            if (!isValid && slotIndex >= 9) {
                                ItemStack itemStack = inventory.getStackInSlot(slotIndex - 9);
                                if (itemStack != null && itemStack.getItemDamage() == 5) isValid = true;
                            }

                            shouldCancel = !isValid;

                            break;
                        case "Click in order!":

                            if (mouseSlot.getSlotIndex() > 35) {
                                break;
                            }

                            if ((item.getItem() != Item.getItemFromBlock(Blocks.stained_glass_pane))) {
                                shouldCancel = true;
                                break;
                            }
                            if (item.getItemDamage() != 14) {
                                shouldCancel = true;
                                break;
                            }
                            int needed = terminalNumberNeeded[0];
                            if (needed == 0) break;
                            shouldCancel = needed != -1 && item.stackSize != needed;
                            break;
                    }

                    if (!shouldCancel) {
                        if (inventoryName.startsWith("What starts with:")) {
                            char letter = inventoryName.charAt(inventoryName.indexOf("'") + 1);
                            shouldCancel = !(StringUtils.stripControlCodes(item.getDisplayName()).charAt(0) == letter);
                        } else if (inventoryName.startsWith("Select all the")) {
                            if (terminalColorNeeded == null) return;
                            String itemName = StringUtils.stripControlCodes(item.getDisplayName()).toUpperCase();
                            shouldCancel = !(itemName.contains(terminalColorNeeded) || (terminalColorNeeded.equals("SILVER") && itemName.contains("LIGHT GRAY")) || (terminalColorNeeded.equals("WHITE") && itemName.equals("WOOL")));
                        }
                    }

                    event.setCanceled(shouldCancel && !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));
                }

                if (!BlockSlayerCommand.onlySlayerName.equals("") && item != null) {
                    if (inventoryName.equals("Slayer")) {
                        if (!item.getDisplayName().contains("Revenant Horror") && !item.getDisplayName().contains("Tarantula Broodfather") && !item.getDisplayName().contains("Sven Packmaster"))
                            return;
                        if (!item.getDisplayName().contains(BlockSlayerCommand.onlySlayerName)) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ERROR_COLOUR + "Danker's Skyblock Mod has stopped you from starting this quest (Set to " + BlockSlayerCommand.onlySlayerName + " " + BlockSlayerCommand.onlySlayerNumber + ")"));
                            event.setCanceled(true);
                        }
                    } else if (inventoryName.equals("Revenant Horror") || inventoryName.equals("Tarantula Broodfather") || inventoryName.equals("Sven Packmaster")) {
                        if (item.getDisplayName().contains("Revenant Horror") || item.getDisplayName().contains("Tarantula Broodfather") || item.getDisplayName().contains("Sven Packmaster")) {
                            // Only check number as they passed the above check
                            String slayerNumber = item.getDisplayName().substring(item.getDisplayName().lastIndexOf(" ") + 1);
                            if (!slayerNumber.equals(BlockSlayerCommand.onlySlayerNumber)) {
                                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ERROR_COLOUR + "Danker's Skyblock Mod has stopped you from starting this quest (Set to " + BlockSlayerCommand.onlySlayerName + " " + BlockSlayerCommand.onlySlayerNumber + ")"));
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onMouseInputPost(GuiScreenEvent.MouseInputEvent.Post event) {
        if (Mouse.getEventButton() == 0 && event.gui instanceof GuiChat) {
            if (ToggleCommand.chatMaddoxToggled && System.currentTimeMillis() / 1000 - lastMaddoxTime < 10) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(lastMaddoxCommand);
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        GameSettings gameSettings = mc.gameSettings;
        if (event.gui instanceof GuiChest) {
            Container containerChest = ((GuiChest) event.gui).inventorySlots;
            if (containerChest instanceof ContainerChest) {
                GuiChest chest = (GuiChest) event.gui;
                IInventory inventory = ((ContainerChest) containerChest).getLowerChestInventory();
                String inventoryName = inventory.getDisplayName().getUnformattedText();

                if (ToggleCommand.swapToPickBlockToggled) {
                    if (inventoryName.startsWith("Chronomatron (") || inventoryName.startsWith("Superpairs (") || inventoryName.startsWith("Ultrasequencer (") || inventoryName.startsWith("What starts with:") || inventoryName.startsWith("Select all the") || inventoryName.startsWith("Navigate the maze!") || inventoryName.startsWith("Correct all the panes!") || inventoryName.startsWith("Click in order!") || inventoryName.startsWith("Harp -")) {
                        if (!pickBlockBindSwapped) {
                            pickBlockBind = gameSettings.keyBindPickBlock.getKeyCode();
                            gameSettings.keyBindPickBlock.setKeyCode(-100);
                            pickBlockBindSwapped = true;
                        }
                    } else {
                        if (pickBlockBindSwapped) {
                            gameSettings.keyBindPickBlock.setKeyCode(pickBlockBind);
                            pickBlockBindSwapped = false;
                        }
                    }
                }
            }
        } else {
            if (pickBlockBindSwapped) {
                gameSettings.keyBindPickBlock.setKeyCode(pickBlockBind);
                pickBlockBindSwapped = false;
            }
        }

        if (ToggleCommand.autoSkillTrackerToggled) {
			if (skillStopwatch.isStarted() && !skillStopwatch.isSuspended()) {
				skillStopwatch.suspend();
			}
		}

        clickInOrderSlots = new Slot[36];
        lastChronomatronRound = 0;
        chronomatronPattern.clear();
        chronomatronMouseClicks = 0;
        experimentTableSlots = new ItemStack[54];
        terminalColorNeeded = null;
    }

    @SubscribeEvent
    public void onGuiRender(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (event.gui instanceof GuiChest) {
            GuiChest inventory = (GuiChest) event.gui;
            Container containerChest = inventory.inventorySlots;
            if (containerChest instanceof ContainerChest) {
                Minecraft mc = Minecraft.getMinecraft();
                ScaledResolution sr = new ScaledResolution(mc);
                int guiLeft = (sr.getScaledWidth() - 176) / 2;
                int guiTop = (sr.getScaledHeight() - 222) / 2;

                List<Slot> invSlots = inventory.inventorySlots.inventorySlots;
                String displayName = ((ContainerChest) containerChest).getLowerChestInventory().getDisplayName().getUnformattedText().trim();
                int chestSize = inventory.inventorySlots.inventorySlots.size();

                if (ToggleCommand.petColoursToggled) {
                    for (Slot slot : invSlots) {
                        ItemStack item = slot.getStack();
                        if (item == null) continue;
                        String name = item.getDisplayName();
                        if (petPattern.matcher(StringUtils.stripControlCodes(name)).find()) {
                            if (name.endsWith("aHealer") || name.endsWith("aMage") || name.endsWith("aBerserk") || name.endsWith("aArcher") || name.endsWith("aTank"))
                                continue;
                            int colour;
                            int petLevel = Integer.parseInt(item.getDisplayName().substring(item.getDisplayName().indexOf(" ") + 1, item.getDisplayName().indexOf("]")));
                            if (petLevel == 100) {
                                colour = PET_100;
                            } else if (petLevel >= 90) {
                                colour = PET_90_TO_99;
                            } else if (petLevel >= 80) {
                                colour = PET_80_TO_89;
                            } else if (petLevel >= 70) {
                                colour = PET_70_TO_79;
                            } else if (petLevel >= 60) {
                                colour = PET_60_TO_69;
                            } else if (petLevel >= 50) {
                                colour = PET_50_TO_59;
                            } else if (petLevel >= 40) {
                                colour = PET_40_TO_49;
                            } else if (petLevel >= 30) {
                                colour = PET_30_TO_39;
                            } else if (petLevel >= 20) {
                                colour = PET_20_TO_29;
                            } else if (petLevel >= 10) {
                                colour = PET_10_TO_19;
                            } else {
                                colour = PET_1_TO_9;
                            }
                            Utils.drawOnSlot(chestSize, slot.xDisplayPosition, slot.yDisplayPosition, colour + 0xBF000000);
                        }
                    }
                }
                if (ToggleCommand.clickInOrderToggled && Utils.inDungeons && displayName.equals("Chest") && tickAmount != until) {
                    if (chestOpen == 0) {
                        until = tickAmount;
                        chestOpen = 1;
                        return;
                    }
                    mc.thePlayer.closeScreen();
                    chestOpen = 0;
                }
                if (displayName.contains("Harp") && ToggleCommand.startsWithToggled) {
                    String[] currentInv = new String[54];
                    Container playerContainer = mc.thePlayer.openContainer;
                    IInventory chestInventory = ((ContainerChest)playerContainer).getLowerChestInventory();
                    for (int i = 37; i <= 43; i++) {
                        ItemStack itemStack = chestInventory.getStackInSlot(i);
                        if (itemStack != null) {
                            if (itemStack.getUnlocalizedName().toLowerCase().contains("quartz")) {
                                for (int j = 0; j <= 53; j++) {
                                    ItemStack name = chestInventory.getStackInSlot(j);
                                    if (name != null)
                                        currentInv[j] = name.toString();
                                }
                                if (!Arrays.toString(currentInv).equals(Arrays.toString(harpInv))) {
                                    mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, i, 2, 0, mc.thePlayer);
                                    mc.thePlayer.addChatMessage(new ChatComponentText("clicked"));
                                    until = tickAmount;
                                    harpInv = currentInv;
                                }
                            }
                        }
                    }
                }
                if (displayName.equals("Navigate the maze!") && invSlots.size() == 90 && ToggleCommand.startsWithToggled && System.currentTimeMillis() - lastInteractTime >= SleepCommand.waitAmount) {
                    if (mazeId <= mc.thePlayer.openContainer.windowId) {
                        mazeId = mc.thePlayer.openContainer.windowId;
                    }
                    Container playerContainer = mc.thePlayer.openContainer;
                    IInventory chestInventory = ((ContainerChest)playerContainer).getLowerChestInventory();
                    if (slotIn == -1) {
                        System.out.println("checking chest");
                        for (int i = 0; i <= 53; i++) {
                            ItemStack itemStack = chestInventory.getStackInSlot(i);
                            if (itemStack != null) {
                                int type = itemStack.getItemDamage();
                                if (type == 0)
                                    chest[i] = 1;
                                if (type == 5) {
                                    slotIn = i;
                                    chest[i] = 2;
                                }
                            }
                        }
                    }
                    int firstCheck = slotIn + 1;
                    int secondCheck = slotIn + 9;
                    int thirdCheck = slotIn - 1;
                    int fourthCheck = slotIn - 9;
                    System.out.println("attempt " + slotIn);
                    if (firstCheck % 9 != 0 && firstCheck <= 53 && chest[firstCheck] == 1) {
                        chest[firstCheck] = 0;
                        slotIn = firstCheck;
                        System.out.println("1");
                        mc.playerController.windowClick(mazeId, firstCheck, 0, 0, mc.thePlayer);
                        mazeId++;
                        lastInteractTime = System.currentTimeMillis();
                    } else if (secondCheck <= 53 && chest[secondCheck] == 1) {
                        chest[secondCheck] = 0;
                        slotIn = secondCheck;
                        System.out.println("2");
                        mc.playerController.windowClick(mazeId, secondCheck, 0, 0, mc.thePlayer);
                        mazeId++;
                        lastInteractTime = System.currentTimeMillis();
                    } else if (thirdCheck % 9 != 8 && thirdCheck >= 0 && thirdCheck <= 53 && chest[thirdCheck] == 1) {
                        chest[thirdCheck] = 0;
                        slotIn = thirdCheck;
                        System.out.println("3");
                        mc.playerController.windowClick(mazeId, thirdCheck, 0, 0, mc.thePlayer);
                        mazeId++;
                        lastInteractTime = System.currentTimeMillis();
                    } else if (fourthCheck >= 0 && (Minecraft.getMinecraft()).currentScreen != null && chest[fourthCheck] == 1) {
                        chest[fourthCheck] = 0;
                        slotIn = fourthCheck;
                        System.out.println("4");
                        mc.playerController.windowClick(mazeId, fourthCheck, 0, 0, mc.thePlayer);
                        mazeId++;
                        lastInteractTime = System.currentTimeMillis();
                    }
                }

                if (ToggleCommand.startsWithToggled && System.currentTimeMillis() - lastInteractTime >= SleepCommand.waitAmount && displayName.startsWith("What starts with:")) {
                    char letter = displayName.charAt(displayName.indexOf("'") + 1);
                    if (mazeId <= mc.thePlayer.openContainer.windowId)
                        mazeId = mc.thePlayer.openContainer.windowId;
                    for (Slot startsWith : invSlots) {
                        if (startsWith.slotNumber <= lastSlot ||
                                startsWith.getSlotIndex() <= lastSlot)
                            continue;
                        ItemStack item = startsWith.getStack();
                        if (item == null ||
                                item.isItemEnchanted())
                            continue;
                        if (StringUtils.stripControlCodes(item.getDisplayName()).charAt(0) == letter) {
                            mc.playerController.windowClick(mazeId, startsWith.slotNumber, 0, 0, mc.thePlayer);
                            mazeId++;
                            lastSlot = startsWith.getSlotIndex();
                            lastInteractTime = System.currentTimeMillis();
                            break;
                        }
                        if (mazeId - 15 > mc.thePlayer.openContainer.windowId)
                            break;
                    }
                }

                if (ToggleCommand.startsWithToggled && System.currentTimeMillis() - lastInteractTime >= SleepCommand.waitAmount && displayName.equals("Correct all the panes!")) {
                    for (Slot startsWith : invSlots) {
                        if (startsWith.getSlotIndex() > 53 ||
                                startsWith.getSlotIndex() <= lastSlot)
                            continue;
                        ItemStack item = startsWith.getStack();
                        if (item == null ||
                                item.isItemEnchanted())
                            continue;
                        if (item.getDisplayName().contains("Off") && (Minecraft.getMinecraft()).currentScreen != null) {
                            if (mazeId <= mc.thePlayer.openContainer.windowId)
                                mazeId = mc.thePlayer.openContainer.windowId;
                            mc.playerController.windowClick(mazeId, startsWith.slotNumber, 0, 0, mc.thePlayer);
                            mazeId++;
                            lastSlot = startsWith.getSlotIndex();
                            lastInteractTime = System.currentTimeMillis();
                            break;
                        }
                        if (mazeId - 15 > mc.thePlayer.openContainer.windowId)
                            break;
                    }
                }

                if (ToggleCommand.startsWithToggled && System.currentTimeMillis() - lastInteractTime >= SleepCommand.waitAmount && displayName.startsWith("Select all the")) {
                    String colour = displayName.split(" ")[3];
                    for (Slot slot : invSlots) {
                        if (slot.getSlotIndex() > 53 || slot.getSlotIndex() <= lastSlot) continue;
                        ItemStack item = slot.getStack();
                        if (item == null || item.isItemEnchanted()) continue;
                        String itemName = StringUtils.stripControlCodes(item.getDisplayName()).toUpperCase();
                        if (item.getDisplayName().toUpperCase().contains(colour) || (colour.equals("SILVER") && itemName.contains("LIGHT GRAY")) || (colour.equals("WHITE") && itemName.equals("WOOL")) || (colour.equals("BLACK") && itemName.contains("INK")) || (colour.equals("BROWN") && itemName.contains("COCOA")) || (colour.equals("BLUE") && itemName.contains("LAPIS")) || (colour.equals("WHITE") && itemName.contains("BONE"))) {
                            if (mazeId <= mc.thePlayer.openContainer.windowId)
                                mazeId = mc.thePlayer.openContainer.windowId;
                            lastInteractTime = System.currentTimeMillis();
                            mc.playerController.windowClick(mazeId, slot.slotNumber, 2, 0, mc.thePlayer);
                            lastSlot = slot.getSlotIndex();
                            mazeId++;
                            break;
                        }
                        if (mazeId - 15 > mc.thePlayer.openContainer.windowId)
                            break;
                    }
                }
                if (displayName.equals("Click in order!") && System.currentTimeMillis() - lastInteractTime >= SleepCommand.waitAmount && ToggleCommand.startsWithToggled) {
                    Container playerContainer = mc.thePlayer.openContainer;
                    IInventory chestInventory = ((ContainerChest)playerContainer).getLowerChestInventory();
                    if (mazeId <= mc.thePlayer.openContainer.windowId)
                        mazeId = mc.thePlayer.openContainer.windowId;
                    int[] order = new int[14];
                    int i;
                    for (i = 10; i <= 25; i++) {
                        if (i != 17 && i != 18) {
                            ItemStack click = chestInventory.getStackInSlot(i);
                            if (click == null)
                                break;
                            order[(chestInventory.getStackInSlot(i)).stackSize - 1] = i;
                        }
                    }
                    for (i = 0; i < order.length &&
                            order[i] != 0; i++) {
                        if (i > lastSlot) {
                            ItemStack check = chestInventory.getStackInSlot(order[i]);
                            if (order[i] != 0 && check != null && check.getItemDamage() == 14) {
                                mc.playerController.windowClick(mazeId, order[i], 2, 0, mc.thePlayer);
                                mazeId++;
                                lastSlot = i;
                                lastInteractTime = System.currentTimeMillis();
                                break;
                            }
                            if (mazeId - 15 > mc.thePlayer.openContainer.windowId)
                                break;
                        }
                    }
                }

                if (ToggleCommand.ultrasequencerToggled && displayName.startsWith("Ultrasequencer (")) {
                    EntityPlayerSP player = mc.thePlayer;
                    if (invSlots.size() > 48 && invSlots.get(49).getStack() != null && player.inventory.getItemStack() == null) {
                        if (invSlots.get(49).getStack().getDisplayName().startsWith("§7Timer: §a")) {
                            lastUltraSequencerClicked = 0;
                            for (Slot slot : clickInOrderSlots) {
                                if (slot != null && slot.getStack() != null && StringUtils.stripControlCodes(slot.getStack().getDisplayName()).matches("\\d+")) {
                                    int number = Integer.parseInt(StringUtils.stripControlCodes(slot.getStack().getDisplayName()));
                                    if (number > lastUltraSequencerClicked) {
                                        lastUltraSequencerClicked = number;
                                    }
                                }
                            }
                            if (clickInOrderSlots[lastUltraSequencerClicked] != null && player.inventory.getItemStack() == null && tickAmount % 2 == 0 && lastUltraSequencerClicked != 0 && until == lastUltraSequencerClicked) {
                                Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked];
                                new TextRenderer(mc, String.valueOf(mc.thePlayer.openContainer.windowId), 50, 50, 1.0D);
                                mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, nextSlot.slotNumber, 0, 0, mc.thePlayer);
                                Utils.drawOnSlot(chestSize, nextSlot.xDisplayPosition, nextSlot.yDisplayPosition, -448725184);
                                until = lastUltraSequencerClicked + 1;
                                tickAmount = 0;
                            }
                            if (clickInOrderSlots[lastUltraSequencerClicked] != null && player.inventory.getItemStack() == null && tickAmount == 18 && lastUltraSequencerClicked < 1) {
                                Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked];
                                new TextRenderer(mc, String.valueOf(mc.thePlayer.openContainer.windowId), 50, 50, 1.0D);
                                mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, nextSlot.slotNumber, 0, 0, mc.thePlayer);
                                Utils.drawOnSlot(chestSize, nextSlot.xDisplayPosition, nextSlot.yDisplayPosition, -448725184);
                                tickAmount = 0;
                                until = 1;
                            }
                            if (lastUltraSequencerClicked + 1 < clickInOrderSlots.length && clickInOrderSlots[lastUltraSequencerClicked + 1] != null) {
                                Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked + 1];
                                Utils.drawOnSlot(chestSize, nextSlot.xDisplayPosition, nextSlot.yDisplayPosition, -683615514);
                            }
                        }
                    }
                }

                if (ToggleCommand.chronomatronToggled && displayName.startsWith("Chronomatron (")) {
                    EntityPlayerSP player = mc.thePlayer;
                    if (player.inventory.getItemStack() == null && invSlots.size() > 48 && invSlots.get(49).getStack() != null) {
                        if (invSlots.get(49).getStack().getDisplayName().startsWith("§7Timer: §a") && invSlots.get(4).getStack() != null) {
                            int round = invSlots.get(4).getStack().stackSize;
                            int timerSeconds = Integer.parseInt(StringUtils.stripControlCodes(invSlots.get(49).getStack().getDisplayName()).replaceAll("[^\\d]", ""));
                            if (round != lastChronomatronRound && timerSeconds == round + 2) {
                                lastChronomatronRound = round;
                                for (int i = 10; i <= 43; i++) {
                                    ItemStack stack = invSlots.get(i).getStack();
                                    if (stack == null) continue;
                                    if (stack.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay)) {
                                        chronomatronPattern.add(stack.getDisplayName());
                                        break;
                                    }
                                }
                            }
                            if (chronomatronMouseClicks < chronomatronPattern.size() && player.inventory.getItemStack() == null) {
                                for (int i = 10; i <= 43; i++) {
                                    ItemStack glass = (invSlots.get(i)).getStack();
                                    if (glass != null) {
                                        if (player.inventory.getItemStack() == null) {
                                            if (tickAmount % 5 == 0) {
                                                Slot glassSlot = invSlots.get(i);
                                                if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks))) {
                                                    Utils.drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, -448725184);
                                                    mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, glassSlot.slotNumber, 0, 0, mc.thePlayer);
                                                    chronomatronMouseClicks++;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (invSlots.get(49).getStack().getDisplayName().equals("§aRemember the pattern!")) {
                            chronomatronMouseClicks = 0;
                        }
                    }
                    new TextRenderer(mc, String.join("\n", chronomatronPattern), (int) (guiLeft * 0.8), 10, 1);
                }

                if (ToggleCommand.superpairsToggled && displayName.contains("Superpairs (")) {
                    new TextRenderer(mc, String.valueOf(mc.thePlayer.openContainer.windowId), 50, 50, 1.0D);
                    HashMap<String, HashSet<Integer>> matches = new HashMap<>();
                    for (int i = 0; i < 53; i++) {
                        ItemStack itemStack = experimentTableSlots[i];
                        if (itemStack == null) continue;
                        Slot slot = invSlots.get(i);
                        int x = guiLeft + slot.xDisplayPosition;
                        int y = guiTop + slot.yDisplayPosition;
                        if (chestSize != 90) y += (6 - (chestSize - 36) / 9) * 9;

                        //Utils.renderItem(itemStack, x, y, -100);

                        String itemName = itemStack.getDisplayName();
                        String keyName = itemName + itemStack.getUnlocalizedName();
                        matches.computeIfAbsent(keyName, k -> new HashSet<>());
                        matches.get(keyName).add(i);
                    }

                    Color[] colors = {
                            new Color(255, 0, 0, 100),
                            new Color(0, 0, 255, 100),
                            new Color(100, 179, 113, 100),
                            new Color(255, 114, 255, 100),
                            new Color(255, 199, 87, 100),
                            new Color(119, 105, 198, 100),
                            new Color(135, 199, 112, 100),
                            new Color(240, 37, 240, 100),
                            new Color(178, 132, 190, 100),
                            new Color(63, 135, 163, 100),
                            new Color(146, 74, 10, 100),
                            new Color(255, 255, 255, 100),
                            new Color(217, 252, 140, 100),
                            new Color(255, 82, 82, 100)
                    };

                    Iterator<Color> colorIterator = Arrays.stream(colors).iterator();

                    matches.forEach((itemName, slotSet) -> {
                        if (slotSet.size() < 2) return;
                        ArrayList<Slot> slots = new ArrayList<>();
                        slotSet.forEach(slotNum -> slots.add(invSlots.get(slotNum)));
                        Color color = colorIterator.next();
                        slots.forEach(slot -> {
                            Utils.drawOnSlot(chestSize, slot.xDisplayPosition, slot.yDisplayPosition, color.getRGB());
                        });
                    });
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
        if (LootCommand.empSCs != -1) {
            LootCommand.empSCs++;
        }
        if (LootCommand.empSCsSession != -1) {
            LootCommand.empSCsSession++;
        }
        // Only increment Yetis when in Jerry's Workshop
        List<String> scoreboard = ScoreboardHandler.getSidebarLines();
        for (String s : scoreboard) {
            String sCleaned = ScoreboardHandler.cleanSB(s);
            if (sCleaned.contains("Jerry's Workshop") || sCleaned.contains("Jerry Pond")) {
                if (LootCommand.yetiSCs != -1) {
                    LootCommand.yetiSCs++;
                }
                if (LootCommand.yetiSCsSession != -1) {
                    LootCommand.yetiSCsSession++;
                }
            }
        }

        LootCommand.seaCreatures++;
        LootCommand.fishingMilestone++;
        LootCommand.seaCreaturesSession++;
        LootCommand.fishingMilestoneSession++;
        ConfigHandler.writeIntConfig("fishing", "seaCreature", LootCommand.seaCreatures);
        ConfigHandler.writeIntConfig("fishing", "milestone", LootCommand.fishingMilestone);
        ConfigHandler.writeIntConfig("fishing", "empSC", LootCommand.empSCs);
        ConfigHandler.writeIntConfig("fishing", "yetiSC", LootCommand.yetiSCs);

    }

}
