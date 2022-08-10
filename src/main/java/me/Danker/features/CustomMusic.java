package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.ModInitEvent;
import me.Danker.events.PostConfigInitEvent;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomMusic {

    static boolean cancelNotes;

    public static Song dungeonboss;
    public static int dungeonbossVolume;
    public static Song bloodroom;
    public static int bloodroomVolume;
    public static Song dungeon;
    public static int dungeonVolume;
    public static Song phase2;
    public static int phase2Volume;
    public static Song phase3;
    public static int phase3Volume;
    public static Song phase4;
    public static int phase4Volume;
    public static Song phase5;
    public static int phase5Volume;
    public static Song hub;
    public static int hubVolume;
    public static Song island;
    public static int islandVolume;
    public static Song dungeonHub;
    public static int dungeonHubVolume;
    public static Song farmingIslands;
    public static int farmingIslandsVolume;
    public static Song goldMine;
    public static int goldMineVolume;
    public static Song deepCaverns;
    public static int deepCavernsVolume;
    public static Song dwarvenMines;
    public static int dwarvenMinesVolume;
    public static Song crystalHollows;
    public static int crystalHollowsVolume;
    public static Song spidersDen;
    public static int spidersDenVolume;
    public static Song crimsonIsle;
    public static int crimsonIsleVolume;
    public static Song end;
    public static int endVolume;
    public static Song park;
    public static int parkVolume;

    static int curPhase = 0;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        reset();
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onTick(TickEvent.ClientTickEvent event) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        World world = mc.theWorld;
        if (DankersSkyblockMod.tickAmount % 10 == 0) {
            if (world != null && player != null) {
                if (Utils.inDungeons) {
                    List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                    if (scoreboard.size() > 2) {
                        String firstLine = ScoreboardHandler.cleanSB(scoreboard.get(scoreboard.size() - 1));
                        String secondLine = ScoreboardHandler.cleanSB(scoreboard.get(scoreboard.size() - 2));
                        if (firstLine.contains("30,30") || // F1
                            firstLine.contains("30,125") || // F2
                            firstLine.contains("30,225") || // F3
                            secondLine.contains("- Healthy") || // F3
                            firstLine.contains("30,344") || // F4
                            firstLine.contains("livid") || // F5
                            firstLine.contains("sadan") || // F6
                            firstLine.contains("maxor") || // F7
                            firstLine.contains("f7")) {

                            if (ToggleCommand.dungeonBossMusic) {
                                switch (curPhase) {
                                    case -1:
                                        break;
                                    case 2:
                                        phase2.start();
                                        break;
                                    case 3:
                                        phase3.start();
                                        break;
                                    case 4:
                                        phase4.start();
                                        break;
                                    case 5:
                                        phase5.start();
                                        break;
                                    default:
                                        dungeonboss.start();
                                }
                            }
                        }
                    }
                } else {
                    switch (Utils.tabLocation) {
                        case "Hub":
                            if (ToggleCommand.hubMusic) hub.start();
                            break;
                        case "Private Island":
                            if (ToggleCommand.islandMusic) island.start();
                            break;
                        case "Dungeon Hub":
                            if (ToggleCommand.dungeonHubMusic) dungeonHub.start();
                            break;
                        case "The Farming Islands":
                            if (ToggleCommand.farmingIslandsMusic) farmingIslands.start();
                            break;
                        case "Gold Mine":
                            if (ToggleCommand.goldMineMusic) goldMine.start();
                            break;
                        case "Deep Caverns":
                            if (ToggleCommand.deepCavernsMusic) deepCaverns.start();
                            break;
                        case "Dwarven Mines":
                            if (ToggleCommand.dwarvenMinesMusic) dwarvenMines.start();
                            break;
                        case "Crystal Hollows":
                            if (ToggleCommand.crystalHollowsMusic) crystalHollows.start();
                            break;
                        case "Spider's Den":
                            if (ToggleCommand.spidersDenMusic) spidersDen.start();
                            break;
                        case "Crimson Isle":
                            if (ToggleCommand.crimsonIsleMusic) crimsonIsle.start();
                            break;
                        case "The End":
                            if (ToggleCommand.endMusic) end.start();
                            break;
                        case "The Park":
                            if (ToggleCommand.parkMusic) park.start();
                            break;
                    }
                }
            }
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (ToggleCommand.dungeonMusic && Utils.inDungeons) {
            if (message.contains("[NPC] Mort: Here, I found this map when I first entered the dungeon.")) {
                dungeon.start();
            }
        }

        if (Utils.inDungeons) {
            if (ToggleCommand.dungeonBossMusic) {
                if (phase2.hasSongs() && message.startsWith("[BOSS] Storm: Pathetic Maxor")) {
                    phase2.start();
                    curPhase = 2;
                } else if (phase3.hasSongs() && message.startsWith("[BOSS] Goldor: Who dares trespass into my domain?")) {
                    phase3.start();
                    curPhase = 3;
                } else if (phase4.hasSongs() && message.startsWith("[BOSS] Necron: You went further than any human before")) {
                    phase4.start();
                    curPhase = 4;
                } else if (phase5.hasSongs() && message.startsWith("[BOSS] ") && message.endsWith("You.. again?")) {
                    phase5.start();
                    curPhase = 5;
                }
            }

            if (message.contains(":")) return;

            if (message.contains("EXTRA STATS ")) {
                curPhase = -1; // force no play
                dungeonboss.stop();
                bloodroom.stop();
                dungeon.stop();
                phase2.stop();
                phase3.stop();
                phase4.stop();
                phase5.stop();
            } else if (message.contains("The BLOOD DOOR has been opened!")) {
                dungeon.stop();
                if (ToggleCommand.bloodRoomMusic) bloodroom.start();
            }
        }
    }

    @SubscribeEvent
    public void onSound(PlaySoundEvent event) {
        if (cancelNotes && event.name.startsWith("note.")) {
            event.result = null;
        }
    }

    @SubscribeEvent
    public void postConfigInit(PostConfigInitEvent event) {
        init(event.configDirectory);
    }

    public static void init(String configDirectory) {
        if (configDirectory == null) return;
        File directory = new File(configDirectory + "/dsmmusic");
        if (!directory.exists()) directory.mkdir();

        reset();

        dungeonboss = new Song(directory, "dungeonboss", dungeonbossVolume);
        bloodroom = new Song(directory, "bloodroom", bloodroomVolume);
        dungeon = new Song(directory, "dungeon", dungeonVolume);
        phase2 = new Song(directory, "phasetwo", phase2Volume);
        phase3 = new Song(directory, "phasethree", phase3Volume);
        phase4 = new Song(directory, "phasefour", phase4Volume);
        phase5 = new Song(directory, "phasefive", phase5Volume);
        hub = new Song(directory, "hub", hubVolume);
        island = new Song(directory, "island", islandVolume);
        dungeonHub = new Song(directory, "dungeonhub", dungeonHubVolume);
        farmingIslands = new Song(directory, "farmingislands", farmingIslandsVolume);
        goldMine = new Song(directory, "goldmine", goldMineVolume);
        deepCaverns = new Song(directory, "deepcaverns", deepCavernsVolume);
        dwarvenMines = new Song(directory, "dwarvenmines", dwarvenMinesVolume);
        crystalHollows = new Song(directory, "crystalhollows", crystalHollowsVolume);
        spidersDen = new Song(directory, "spidersden", spidersDenVolume);
        crimsonIsle = new Song(directory, "crimsonisle", crimsonIsleVolume);
        end = new Song(directory, "end", endVolume);
        park = new Song(directory, "park", parkVolume);
    }

    public static void reset() {
        if (dungeonboss != null) dungeonboss.stop();
        if (bloodroom != null) bloodroom.stop();
        if (dungeon != null) dungeon.stop();
        if (phase2 != null) phase2.stop();
        if (phase3 != null) phase3.stop();
        if (phase4 != null) phase4.stop();
        if (phase5 != null) phase5.stop();
        if (hub != null) hub.stop();
        if (island != null) island.stop();
        if (dungeonHub != null) dungeonHub.stop();
        if (farmingIslands != null) farmingIslands.stop();
        if (goldMine != null) goldMine.stop();
        if (deepCaverns != null) deepCaverns.stop();
        if (dwarvenMines != null) dwarvenMines.stop();
        if (crystalHollows != null) crystalHollows.stop();
        if (spidersDen != null) spidersDen.stop();
        if (crimsonIsle != null) crimsonIsle.stop();
        if (end != null) end.stop();
        if (park != null) park.stop();
        curPhase = 0;
    }

    public static class Song {

        public Clip music;
        private final List<File> playlist = new ArrayList<>();
        private int volume;

        public Song(File directory, String songName, int volume) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory() && file.getName().matches(songName + "\\d*(?:\\.wav)?\\.wav")) { // .wav.wav moment
                        playlist.add(file);
                        System.out.println("Added " + file.getName() + " to " + songName + " playlist.");
                    }
                }
            }

            this.volume = volume;
        }

        public void start() throws UnsupportedAudioFileException, LineUnavailableException, IOException {

            try {
                if (music == null) music = AudioSystem.getClip();
                if (!music.isRunning()) {
                    reset();
                    shuffle();
                    setVolume(volume);
                    cancelNotes = true;
                    music.setMicrosecondPosition(0);
                    music.start();
                }
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();

                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                if (player != null) {
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Attempted to play non .wav file. Please use a .wav converter instead of renaming the file."));
                }
            }
        }

        public void stop() {
            cancelNotes = false;
            if (music != null) {
                music.stop();
                if (music.isOpen()) music.close();
            }
        }

        public void shuffle() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
            if (playlist.size() > 0) {
                File file = playlist.get(new Random().nextInt(playlist.size()));
                music = AudioSystem.getClip();
                AudioInputStream ais = AudioSystem.getAudioInputStream(file);
                music.open(ais);
            }
        }

        public boolean setVolume(int volume) {
            this.volume = volume;
            if (playlist.size() < 1) return false;
            if (volume <= 0 || volume > 100) {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                if (player != null) player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Volume can only be set between 0% and 100%."));
                return false;
            }

            if (music != null) {
                float decibels = (float) (20 * Math.log(volume / 100.0));
                FloatControl control = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
                if (decibels <= control.getMinimum() || decibels >= control.getMaximum()) return false;
                control.setValue(decibels);
            }

            return true;
        }

        public boolean hasSongs() {
            return playlist.size() > 0;
        }

    }

}
