package me.Danker;

import com.google.gson.JsonObject;
import me.Danker.commands.*;
import me.Danker.commands.warp.WarpCommandHandler;
import me.Danker.config.CfgConfig;
import me.Danker.config.ModConfig;
import me.Danker.events.*;
import me.Danker.features.*;
import me.Danker.features.loot.*;
import me.Danker.features.puzzlesolvers.*;
import me.Danker.gui.WarningGui;
import me.Danker.gui.WarningGuiRedirect;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.PacketHandler;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.command.ICommand;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.event.HoverEvent;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import org.lwjgl.input.Mouse;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Mod(modid = DankersSkyblockMod.MODID, version = DankersSkyblockMod.VERSION, clientSideOnly = true)
public class DankersSkyblockMod {
    public static final String MODID = "@ID@";
    public static final String VERSION = "@VER@";
    public static ModConfig config;

    public static int titleTimer = -1;
    public static boolean showTitle = false;
    public static String titleText = "";
    public static int tickAmount = 1;
    public static int repoTickAmount = 1;
    public static boolean usingLabymod = false;
    public static boolean usingOAM = false;
    static boolean OAMWarning = false;
    public static String guiToOpen = null;
    public static boolean firstLaunch = false;
    public static String configDirectory;
    public static JsonObject data = null;
    public static WarpCommandHandler warpCommandHandler;

    public static int farmingLevel;
    public static int miningLevel;
    public static int combatLevel;
    public static int foragingLevel;
    public static int fishingLevel;
    public static int enchantingLevel;
    public static int alchemyLevel;
    public static int carpentryLevel;

    @EventHandler
    public void init(FMLInitializationEvent event) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new AbilityCooldowns());
        MinecraftForge.EVENT_BUS.register(new Alerts());
        MinecraftForge.EVENT_BUS.register(new AnnounceVanquishers());
        MinecraftForge.EVENT_BUS.register(new ArachneESP());
        MinecraftForge.EVENT_BUS.register(new ArrowTerminalSolver());
        MinecraftForge.EVENT_BUS.register(new AutoAcceptReparty());
        MinecraftForge.EVENT_BUS.register(new AutoDisplay());
        MinecraftForge.EVENT_BUS.register(new AutoJoinSkyblock());
        MinecraftForge.EVENT_BUS.register(new AutoSwapToPickBlock());
        MinecraftForge.EVENT_BUS.register(new BazaarTimeToFill());
        MinecraftForge.EVENT_BUS.register(new BlazeSolver());
        MinecraftForge.EVENT_BUS.register(new BlockPlacingFlowers());
        MinecraftForge.EVENT_BUS.register(new BlockWrongSlayer());
        MinecraftForge.EVENT_BUS.register(new BlockWrongTerminalClicks());
        MinecraftForge.EVENT_BUS.register(new BonzoMaskTimer());
        MinecraftForge.EVENT_BUS.register(new BoulderSolver());
        MinecraftForge.EVENT_BUS.register(new CakeTimer());
        MinecraftForge.EVENT_BUS.register(new ChatAliases());
        MinecraftForge.EVENT_BUS.register(new ChronomatronSolver());
        MinecraftForge.EVENT_BUS.register(new ClickInOrderSolver());
        MinecraftForge.EVENT_BUS.register(new ColouredNames());
        MinecraftForge.EVENT_BUS.register(new CreeperSolver());
        MinecraftForge.EVENT_BUS.register(new CrimsonMinibossTimer());
        MinecraftForge.EVENT_BUS.register(new CrystalHollowWaypoints());
        MinecraftForge.EVENT_BUS.register(new CustomMusic());
        MinecraftForge.EVENT_BUS.register(new DisableMovement());
        MinecraftForge.EVENT_BUS.register(new DrillFix());
        MinecraftForge.EVENT_BUS.register(new DungeonScore());
        MinecraftForge.EVENT_BUS.register(new DungeonTimer());
        MinecraftForge.EVENT_BUS.register(new EndOfFarmAlert());
        MinecraftForge.EVENT_BUS.register(new ExpertiseLore());
        MinecraftForge.EVENT_BUS.register(new FasterMaddoxCalling());
        MinecraftForge.EVENT_BUS.register(new FilletMagmafish());
        MinecraftForge.EVENT_BUS.register(new FirePillarDisplay());
        MinecraftForge.EVENT_BUS.register(new FishingSpawnAlerts());
        MinecraftForge.EVENT_BUS.register(new GemstonesLore());
        MinecraftForge.EVENT_BUS.register(new GiantHPDisplay());
        MinecraftForge.EVENT_BUS.register(new GoldenEnchants());
        MinecraftForge.EVENT_BUS.register(new GolemSpawningAlert());
        MinecraftForge.EVENT_BUS.register(new GpartyNotifications());
        MinecraftForge.EVENT_BUS.register(new HidePetCandy());
        MinecraftForge.EVENT_BUS.register(new HidePlayerArmour());
        MinecraftForge.EVENT_BUS.register(new HideTooltipsInExperiments());
        MinecraftForge.EVENT_BUS.register(new HighlightCommissions());
        MinecraftForge.EVENT_BUS.register(new HighlightFilledOrders());
        MinecraftForge.EVENT_BUS.register(new HighlightSkeletonMasters());
        MinecraftForge.EVENT_BUS.register(new IceWalkSolver());
        MinecraftForge.EVENT_BUS.register(new KuudraNotifications());
        MinecraftForge.EVENT_BUS.register(new LividSolver());
        MinecraftForge.EVENT_BUS.register(new LowHealthNotifications());
        MinecraftForge.EVENT_BUS.register(new MeterTracker());
        MinecraftForge.EVENT_BUS.register(new MinionLastCollected());
        MinecraftForge.EVENT_BUS.register(new NecronNotifications());
        MinecraftForge.EVENT_BUS.register(new NoF3Coords());
        MinecraftForge.EVENT_BUS.register(new NotifySlayerSlain());
        MinecraftForge.EVENT_BUS.register(new PetColours());
        MinecraftForge.EVENT_BUS.register(new PowderTracker());
        MinecraftForge.EVENT_BUS.register(new Reparty());
        MinecraftForge.EVENT_BUS.register(new SameColourSolver());
        MinecraftForge.EVENT_BUS.register(new SelectAllColourSolver());
        MinecraftForge.EVENT_BUS.register(new SilverfishSolver());
        MinecraftForge.EVENT_BUS.register(new Skill50Display());
        MinecraftForge.EVENT_BUS.register(new SkillTracker());
        MinecraftForge.EVENT_BUS.register(new SlayerESP());
        MinecraftForge.EVENT_BUS.register(new SpamHider());
        MinecraftForge.EVENT_BUS.register(new SpiritBearAlert());
        MinecraftForge.EVENT_BUS.register(new SpiritBootsFix());
        MinecraftForge.EVENT_BUS.register(new StartsWithSolver());
        MinecraftForge.EVENT_BUS.register(new StopSalvagingStarredItems());
        MinecraftForge.EVENT_BUS.register(new SuperpairsSolver());
        MinecraftForge.EVENT_BUS.register(new TetherDisplay());
        MinecraftForge.EVENT_BUS.register(new ThreeManSolver());
        MinecraftForge.EVENT_BUS.register(new TicTacToeSolver());
        MinecraftForge.EVENT_BUS.register(new TriviaSolver());
        MinecraftForge.EVENT_BUS.register(new UltrasequencerSolver());
        MinecraftForge.EVENT_BUS.register(new UpdateChecker());
        MinecraftForge.EVENT_BUS.register(new WatcherReadyAlert());
        MinecraftForge.EVENT_BUS.register(new WaterSolver());

        MinecraftForge.EVENT_BUS.register(new LootDisplay());
        MinecraftForge.EVENT_BUS.register(new LootTracker());
        MinecraftForge.EVENT_BUS.register(new BlazeTracker());
        MinecraftForge.EVENT_BUS.register(new CatacombsTracker());
        MinecraftForge.EVENT_BUS.register(new EndermanTracker());
        MinecraftForge.EVENT_BUS.register(new FishingTracker());
        MinecraftForge.EVENT_BUS.register(new GhostTracker());
        MinecraftForge.EVENT_BUS.register(new MythologicalTracker());
        MinecraftForge.EVENT_BUS.register(new SpiderTracker());
        MinecraftForge.EVENT_BUS.register(new TrophyFishTracker());
        MinecraftForge.EVENT_BUS.register(new WolfTracker());
        MinecraftForge.EVENT_BUS.register(new ZombieTracker());
        
        MinecraftForge.EVENT_BUS.post(new ModInitEvent(configDirectory));
        config = new ModConfig();
        ConfigHandler.reloadConfig();
        MinecraftForge.EVENT_BUS.post(new PostConfigInitEvent(configDirectory));

        new Thread(Utils::refreshRepo).start();
    }

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new ArmourCommand());
        ClientCommandHandler.instance.registerCommand(new BankCommand());
        ClientCommandHandler.instance.registerCommand(new CrystalHollowWaypointCommand());
        ClientCommandHandler.instance.registerCommand(new DankerGuiCommand());
        ClientCommandHandler.instance.registerCommand(new DHelpCommand());
        ClientCommandHandler.instance.registerCommand(new DisplayCommand());
        ClientCommandHandler.instance.registerCommand(new DungeonsCommand());
        ClientCommandHandler.instance.registerCommand(new FairySoulsCommand());
        ClientCommandHandler.instance.registerCommand(new GuildOfCommand());
        ClientCommandHandler.instance.registerCommand(new HOTMCommand());
        ClientCommandHandler.instance.registerCommand(new HOTMTreeCommand());
        ClientCommandHandler.instance.registerCommand(new ImportFishingCommand());
        ClientCommandHandler.instance.registerCommand(new InventoryCommand());
        ClientCommandHandler.instance.registerCommand(new LobbyBankCommand());
        ClientCommandHandler.instance.registerCommand(new LobbySkillsCommand());
        ClientCommandHandler.instance.registerCommand(new LootCommand());
        ClientCommandHandler.instance.registerCommand(new PetsCommand());
        ClientCommandHandler.instance.registerCommand(new PlayerCommand());
        ClientCommandHandler.instance.registerCommand(new PowderTrackerCommand());
        ClientCommandHandler.instance.registerCommand(new ReloadConfigCommand());
        ClientCommandHandler.instance.registerCommand(new ReloadRepoCommand());
        ClientCommandHandler.instance.registerCommand(new ResetLootCommand());
        ClientCommandHandler.instance.registerCommand(new SkillsCommand());
        ClientCommandHandler.instance.registerCommand(new SkillTrackerCommand());
        ClientCommandHandler.instance.registerCommand(new SkyblockPlayersCommand());
        ClientCommandHandler.instance.registerCommand(new SlayerCommand());
        ClientCommandHandler.instance.registerCommand(new StopLobbyCommand());
        ClientCommandHandler.instance.registerCommand(new TrophyFishCommand());
        ClientCommandHandler.instance.registerCommand(new WeightCommand());

        warpCommandHandler = new WarpCommandHandler();

        configDirectory = event.getModConfigurationDirectory().toString();
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
		Package[] packages = Package.getPackages();
		for (Package p : packages){
			if (p.getName().startsWith("com.spiderfrog.gadgets") || p.getName().startsWith("com.spiderfrog.oldanimations")){
				usingOAM = true;
				break;
			}
		}
		System.out.println("OAM detection: " + usingOAM);

    	usingLabymod = Loader.isModLoaded("labymod");
    	System.out.println("LabyMod detection: " + usingLabymod);
    	
        if (!ClientCommandHandler.instance.getCommands().containsKey("reparty")) {
            ClientCommandHandler.instance.registerCommand(new RepartyCommand());
        } else if (CfgConfig.getBoolean("commands", "reparty")) {
            for (Map.Entry<String, ICommand> entry : ClientCommandHandler.instance.getCommands().entrySet()) {
                if (entry.getKey().equals("reparty") || entry.getKey().equals("rp")) {
                    entry.setValue(new RepartyCommand());
                }
            }
        }

    }

    @SubscribeEvent
	public void onGuiOpenEvent(GuiOpenEvent event) {
		if (event.gui instanceof GuiMainMenu && usingOAM && !OAMWarning) {
		    event.gui = new WarningGuiRedirect(new WarningGui());
		    OAMWarning = true;
		}
	}

    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent event) {
        if (firstLaunch) {
            firstLaunch = false;
            CfgConfig.writeBooleanConfig("misc", "firstLaunch", false);

            IChatComponent chatComponent = new ChatComponentText(
                    EnumChatFormatting.GOLD + "Thank you for downloading Danker's Skyblock Mod.\n" +
                         "To get started, run the command " + EnumChatFormatting.GOLD + "/dsm" + EnumChatFormatting.RESET + " to view all the mod features."
            );
            chatComponent.setChatStyle(chatComponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Click to open the DSM menu."))).setChatClickEvent(new ClickEvent(Action.RUN_COMMAND, "/dsm")));

            new Thread(() -> {
                while (true) {
                    if (Minecraft.getMinecraft().thePlayer == null) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Minecraft.getMinecraft().thePlayer.addChatMessage(chatComponent);
                    break;
                }
            }).start();
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.startsWith("Your new API key is ") && Utils.isOnHypixel()) {
            String apiKey = event.message.getSiblings().get(0).getChatStyle().getChatClickEvent().getValue();
            ModConfig.apiKey = apiKey;
            config.save();
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Set API key to " + ModConfig.getColour(ModConfig.secondaryColour) + apiKey));
        } else if (Utils.inSkyblock && !message.contains(":") && message.contains("  SKILL LEVEL UP ")) {
            // Handle skill level ups
            String skill = message.substring(message.indexOf("UP") + 3, message.lastIndexOf(" "));
            int level = Utils.getIntFromString(message.substring(message.indexOf("➜") + 1), true);

            switch (skill) {
                case "Farming":
                    DankersSkyblockMod.farmingLevel = level;
                    break;
                case "Mining":
                    DankersSkyblockMod.miningLevel = level;
                    break;
                case "Combat":
                    DankersSkyblockMod.combatLevel = level;
                    break;
                case "Foraging":
                    DankersSkyblockMod.foragingLevel = level;
                    break;
                case "Fishing":
                    DankersSkyblockMod.fishingLevel = level;
                    break;
                case "Enchanting":
                    DankersSkyblockMod.enchantingLevel = level;
                    break;
                case "Alchemy":
                    DankersSkyblockMod.alchemyLevel = level;
                    break;
                case "Carpentry":
                    DankersSkyblockMod.carpentryLevel = level;
                    break;
                default:
                    System.err.println("Unknown skill leveled up.");
            }

            CfgConfig.writeIntConfig("skills", skill.toLowerCase(Locale.US), level);
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
        if (usingLabymod && !(Minecraft.getMinecraft().ingameGUI instanceof GuiIngameForge)) return;
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE && event.type != RenderGameOverlayEvent.ElementType.JUMPBAR)
            return;
        MinecraftForge.EVENT_BUS.post(new RenderOverlayEvent());
    }

    // LabyMod Support
    @SubscribeEvent
    public void renderPlayerInfoLabyMod(final RenderGameOverlayEvent event) {
        if (!usingLabymod) return;
        if (event.type != null) return;
        MinecraftForge.EVENT_BUS.post(new RenderOverlayEvent());
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlayEvent event) {
        if (showTitle) {
            RenderUtils.drawTitle(titleText);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        tickAmount++;
        if (tickAmount % 20 == 0) {
            repoTickAmount++;
            if (player != null) {
                Utils.checkForSkyblock();
                Utils.checkTabLocation();
                Utils.checkForDungeonFloor();
            }

            tickAmount = 0;
        }

        if (repoTickAmount % 3601 == 0) {
            // I didn't want to change everything so I just made a new tick variable
            new Thread(Utils::refreshRepo).start();
            repoTickAmount = 1;
        }

        if (titleTimer >= 0) {
            if (titleTimer == 0) {
                showTitle = false;
            }
            titleTimer--;
        }

        // New skill level detection
        if (mc.currentScreen instanceof GuiChest && tickAmount % 5 == 0 && player != null) {
            ContainerChest chest = (ContainerChest) player.openContainer;
            String chestName = chest.getLowerChestInventory().getDisplayName().getUnformattedText().trim();

            if (chestName.equals("Your Skills")) {
                List<Slot> invSlots = ((GuiChest) mc.currentScreen).inventorySlots.inventorySlots;

                farmingLevel = Utils.initializeSkill(invSlots.get(19).getStack(), "farming");
                miningLevel = Utils.initializeSkill(invSlots.get(20).getStack(), "mining");
                combatLevel = Utils.initializeSkill(invSlots.get(21).getStack(), "combat");
                foragingLevel = Utils.initializeSkill(invSlots.get(22).getStack(), "foraging");
                fishingLevel = Utils.initializeSkill(invSlots.get(23).getStack(), "fishing");
                enchantingLevel = Utils.initializeSkill(invSlots.get(24).getStack(), "enchanting");
                alchemyLevel = Utils.initializeSkill(invSlots.get(25).getStack(), "alchemy");
                carpentryLevel = Utils.initializeSkill(invSlots.get(29).getStack(), "carpentry");

                System.out.println("Updated skill levels.");
            }
        }
    }

    // Delay GUI by 1 tick
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (guiToOpen != null) {
            Minecraft mc = Minecraft.getMinecraft();
            switch (guiToOpen) {
                case "inventory":
                    mc.displayGuiScreen(InventoryCommand.chest);
                    break;
                case "hotminventory":
                    mc.displayGuiScreen(HOTMTreeCommand.chest);
                    break;
            }
            guiToOpen = null;
        }
    }

    @SubscribeEvent
    public void onGuiMouseInputPre(GuiScreenEvent.MouseInputEvent.Pre event) {
        if (Mouse.getEventButton() != 0 && Mouse.getEventButton() != 1 && Mouse.getEventButton() != 2)
            return; // Left click, middle click or right click
        if (!Mouse.getEventButtonState()) return;

        if (event.gui == InventoryCommand.chest || event.gui == HOTMTreeCommand.chest) {
            event.setCanceled(true);
            return;
        }

        if (!Utils.inSkyblock) return;

        if (event.gui instanceof GuiChest) {
            Container containerChest = ((GuiChest) event.gui).inventorySlots;
            if (containerChest instanceof ContainerChest) {
                // a lot of declarations here, if you get scarred, my bad
                GuiChest chest = (GuiChest) event.gui;
                IInventory inventory = ((ContainerChest) containerChest).getLowerChestInventory();
                Slot slot = chest.getSlotUnderMouse();
                if (slot == null) return;
                ItemStack item = slot.getStack();
                String inventoryName = inventory.getDisplayName().getUnformattedText();

                if (MinecraftForge.EVENT_BUS.post(new ChestSlotClickedEvent(chest, inventory, inventoryName, slot, item))) event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onGuiRender(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.gui instanceof GuiChest) {
            GuiChest inventory = (GuiChest) event.gui;
            Container containerChest = inventory.inventorySlots;
            if (containerChest instanceof ContainerChest) {
                List<Slot> invSlots = inventory.inventorySlots.inventorySlots;
                String displayName = ((ContainerChest) containerChest).getLowerChestInventory().getDisplayName().getUnformattedText().trim();
                int chestSize = inventory.inventorySlots.inventorySlots.size();

                MinecraftForge.EVENT_BUS.post(new GuiChestBackgroundDrawnEvent(inventory, displayName, chestSize, invSlots));
            }
        }
    }

    @SubscribeEvent
    public void onServerConnect(ClientConnectedToServerEvent event) {
        event.manager.channel().pipeline().addBefore("packet_handler", "danker_packet_handler", new PacketHandler());
        System.out.println("Added packet handler to channel pipeline.");
    }

    // misc feature ig

    public static void onAbilityKey() {
        if (!Utils.isInDungeons()) return;
        Minecraft.getMinecraft().thePlayer.dropOneItem(true);
    }

}
