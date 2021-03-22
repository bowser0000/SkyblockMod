package me.Danker;

import me.Danker.commands.*;
import me.Danker.events.ChestSlotClickedEvent;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.events.RenderOverlay;
import me.Danker.features.*;
import me.Danker.features.loot.LootDisplay;
import me.Danker.features.loot.LootTracker;
import me.Danker.features.puzzlesolvers.*;
import me.Danker.gui.*;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.PacketHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
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
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Mod(modid = DankersSkyblockMod.MODID, version = DankersSkyblockMod.VERSION, clientSideOnly = true)
public class DankersSkyblockMod {
    public static final String MODID = "Danker's Skyblock Mod";
    public static final String VERSION = "1.8.6-beta4";
    public static int titleTimer = -1;
    public static boolean showTitle = false;
    public static String titleText = "";
    public static int tickAmount = 1;
    public static KeyBinding[] keyBindings = new KeyBinding[3];
    public static boolean usingLabymod = false;
    public static boolean usingOAM = false;
    static boolean OAMWarning = false;
    public static String guiToOpen = null;
    public static boolean firstLaunch = false;
    public static String configDirectory;
    
    public static String MAIN_COLOUR;
    public static String SECONDARY_COLOUR;
    public static String ERROR_COLOUR;
    public static String DELIMITER_COLOUR;
    public static String TYPE_COLOUR;
    public static String VALUE_COLOUR;
    public static String SKILL_AVERAGE_COLOUR;
    public static String ANSWER_COLOUR;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ArachneESP());
        MinecraftForge.EVENT_BUS.register(new ArrowTerminalSolver());
        MinecraftForge.EVENT_BUS.register(new AutoDisplay());
        MinecraftForge.EVENT_BUS.register(new AutoSwapToPickBlock());
        MinecraftForge.EVENT_BUS.register(new BlazeSolver());
        MinecraftForge.EVENT_BUS.register(new BlockAbilities());
        MinecraftForge.EVENT_BUS.register(new BlockPlacingFlowers());
        MinecraftForge.EVENT_BUS.register(new BlockWrongSlayer());
        MinecraftForge.EVENT_BUS.register(new BlockWrongTerminalClicks());
        MinecraftForge.EVENT_BUS.register(new BonzoMaskTimer());
        MinecraftForge.EVENT_BUS.register(new BoulderSolver());
        MinecraftForge.EVENT_BUS.register(new CakeTimer());
        MinecraftForge.EVENT_BUS.register(new ChronomatronSolver());
        MinecraftForge.EVENT_BUS.register(new ClickInOrderSolver());
        MinecraftForge.EVENT_BUS.register(new CreeperSolver());
        MinecraftForge.EVENT_BUS.register(new CustomMusic());
        MinecraftForge.EVENT_BUS.register(new DungeonTimer());
        MinecraftForge.EVENT_BUS.register(new ExpertiseLore());
        MinecraftForge.EVENT_BUS.register(new FasterMaddoxCalling());
        MinecraftForge.EVENT_BUS.register(new GoldenEnchants());
        MinecraftForge.EVENT_BUS.register(new GolemSpawningAlert());
        MinecraftForge.EVENT_BUS.register(new GpartyNotifications());
        MinecraftForge.EVENT_BUS.register(new HideTooltipsInExperiments());
        MinecraftForge.EVENT_BUS.register(new IceWalkSolver());
        MinecraftForge.EVENT_BUS.register(new LividSolver());
        MinecraftForge.EVENT_BUS.register(new LootDisplay());
        MinecraftForge.EVENT_BUS.register(new LootTracker());
        MinecraftForge.EVENT_BUS.register(new LowHealthNotifications());
        MinecraftForge.EVENT_BUS.register(new NecronNotifications());
        MinecraftForge.EVENT_BUS.register(new NoF3Coords());
        MinecraftForge.EVENT_BUS.register(new NotifySlayerSlain());
        MinecraftForge.EVENT_BUS.register(new PetColours());
        MinecraftForge.EVENT_BUS.register(new Reparty());
        MinecraftForge.EVENT_BUS.register(new SelectAllColourSolver());
        MinecraftForge.EVENT_BUS.register(new SilverfishSolver());
        MinecraftForge.EVENT_BUS.register(new Skill50Display());
        MinecraftForge.EVENT_BUS.register(new SkillTracker());
        MinecraftForge.EVENT_BUS.register(new SlayerESP());
        MinecraftForge.EVENT_BUS.register(new SpamHider());
        MinecraftForge.EVENT_BUS.register(new SpiritBearAlert());
        MinecraftForge.EVENT_BUS.register(new StartsWithSolver());
        MinecraftForge.EVENT_BUS.register(new StopSalvagingStarredItems());
        MinecraftForge.EVENT_BUS.register(new SuperpairsSolver());
        MinecraftForge.EVENT_BUS.register(new ThreeManSolver());
        MinecraftForge.EVENT_BUS.register(new TicTacToeSolver());
        MinecraftForge.EVENT_BUS.register(new TriviaSolver());
        MinecraftForge.EVENT_BUS.register(new UltrasequencerSolver());
        MinecraftForge.EVENT_BUS.register(new UpdateChecker());
        MinecraftForge.EVENT_BUS.register(new WatcherReadyAlert());
        MinecraftForge.EVENT_BUS.register(new WaterSolver());

        ConfigHandler.reloadConfig();
        GoldenEnchants.init();
        TriviaSolver.init();

        keyBindings[0] = new KeyBinding("Open Maddox Menu", Keyboard.KEY_M, "Danker's Skyblock Mod");
        keyBindings[1] = new KeyBinding("Regular Ability", Keyboard.KEY_NUMPAD4, "Danker's Skyblock Mod");
        keyBindings[2] = new KeyBinding("Start/Stop Skill Tracker", Keyboard.KEY_NUMPAD5, "Danker's Skyblock Mod");

        for (KeyBinding keyBinding : keyBindings) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        ClientCommandHandler.instance.registerCommand(new ArmourCommand());
        ClientCommandHandler.instance.registerCommand(new BankCommand());
        ClientCommandHandler.instance.registerCommand(new BlockSlayerCommand());
        ClientCommandHandler.instance.registerCommand(new CustomMusicCommand());
        ClientCommandHandler.instance.registerCommand(new DHelpCommand());
        ClientCommandHandler.instance.registerCommand(new DankerGuiCommand());
        ClientCommandHandler.instance.registerCommand(new DisplayCommand());
        ClientCommandHandler.instance.registerCommand(new DungeonsCommand());
        ClientCommandHandler.instance.registerCommand(new FairySoulsCommand());
        ClientCommandHandler.instance.registerCommand(new GetkeyCommand());
        ClientCommandHandler.instance.registerCommand(new GuildOfCommand());
        ClientCommandHandler.instance.registerCommand(new ImportFishingCommand());
        ClientCommandHandler.instance.registerCommand(new LobbyBankCommand());
        ClientCommandHandler.instance.registerCommand(new LobbySkillsCommand());
        ClientCommandHandler.instance.registerCommand(new LootCommand());
        ClientCommandHandler.instance.registerCommand(new MoveCommand());
        ClientCommandHandler.instance.registerCommand(new PetsCommand());
        ClientCommandHandler.instance.registerCommand(new ReloadConfigCommand());
        ClientCommandHandler.instance.registerCommand(new ResetLootCommand());
        ClientCommandHandler.instance.registerCommand(new ScaleCommand());
        ClientCommandHandler.instance.registerCommand(new SetkeyCommand());
        ClientCommandHandler.instance.registerCommand(new SkillTrackerCommand());
        ClientCommandHandler.instance.registerCommand(new SkillsCommand());
        ClientCommandHandler.instance.registerCommand(new SkyblockPlayersCommand());
        ClientCommandHandler.instance.registerCommand(new SlayerCommand());
        ClientCommandHandler.instance.registerCommand(new ToggleCommand());

        configDirectory = event.getModConfigurationDirectory().toString();
        CustomMusic.init(configDirectory);
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
        } else if (ConfigHandler.getBoolean("commands", "reparty")) {
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
            ConfigHandler.writeBooleanConfig("misc", "firstLaunch", false);

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
            ConfigHandler.writeStringConfig("api", "APIKey", apiKey);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Set API key to " + DankersSkyblockMod.SECONDARY_COLOUR + apiKey));
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
        if (usingLabymod && !(Minecraft.getMinecraft().ingameGUI instanceof GuiIngameForge)) return;
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE && event.type != RenderGameOverlayEvent.ElementType.JUMPBAR)
            return;
        if (Minecraft.getMinecraft().currentScreen instanceof EditLocationsGui) return;
        MinecraftForge.EVENT_BUS.post(new RenderOverlay());
    }

    // LabyMod Support
    @SubscribeEvent
    public void renderPlayerInfoLabyMod(final RenderGameOverlayEvent event) {
        if (!usingLabymod) return;
        if (event.type != null) return;
        if (Minecraft.getMinecraft().currentScreen instanceof EditLocationsGui) return;
        MinecraftForge.EVENT_BUS.post(new RenderOverlay());
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event) {
        if (showTitle) {
            Utils.drawTitle(titleText);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != Phase.START) return;

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        tickAmount++;
        if (tickAmount % 20 == 0) {
            if (player != null) {
                Utils.checkForSkyblock();
                Utils.checkForDungeons();
            }

            tickAmount = 0;
        }

        if (titleTimer >= 0) {
            if (titleTimer == 0) {
                showTitle = false;
            }
            titleTimer--;
        }
    }

    // Delay GUI by 1 tick
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (guiToOpen != null) {
            Minecraft mc = Minecraft.getMinecraft();
            if (guiToOpen.startsWith("dankergui")) {
                int page = Character.getNumericValue(guiToOpen.charAt(guiToOpen.length() - 1));
                mc.displayGuiScreen(new DankerGui(page, ""));
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
    public void onKey(KeyInputEvent event) {
        if (!Utils.inSkyblock) return;

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (keyBindings[1].isPressed()) {
            if (Utils.inDungeons) {
                player.dropOneItem(true);
            }
        }
    }

    @SubscribeEvent
    public void onGuiMouseInputPre(GuiScreenEvent.MouseInputEvent.Pre event) {
        if (!Utils.inSkyblock) return;
        if (Mouse.getEventButton() != 0 && Mouse.getEventButton() != 1 && Mouse.getEventButton() != 2)
            return; // Left click, middle click or right click
        if (!Mouse.getEventButtonState()) return;

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
                if (item == null) {
                    if (MinecraftForge.EVENT_BUS.post(new ChestSlotClickedEvent(chest, inventory, inventoryName, slot))) event.setCanceled(true);
                } else {
                    if (MinecraftForge.EVENT_BUS.post(new ChestSlotClickedEvent(chest, inventory, inventoryName, slot, item))) event.setCanceled(true);
                }
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

}
