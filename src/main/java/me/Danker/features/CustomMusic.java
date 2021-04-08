package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
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
import java.util.List;

public class CustomMusic {

    static boolean cancelNotes;
    static boolean prevInDungeonBossRoom = false;
    public static boolean inDungeonBossRoom = false;
    public static Song dungeonboss;
    public static int dungeonbossVolume;
    public static Song bloodroom;
    public static int bloodroomVolume;
    public static Song dungeon;
    public static int dungeonVolume;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        reset();
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        World world = mc.theWorld;
        if (DankersSkyblockMod.tickAmount % 10 == 0) {
            if (ToggleCommand.dungeonBossMusic && Utils.inDungeons && world != null && player != null) {
                prevInDungeonBossRoom = inDungeonBossRoom;
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
                        firstLine.contains("necron")) { // F7

                        inDungeonBossRoom = true;
                        if (!prevInDungeonBossRoom) {
                            dungeonboss.start();
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (ToggleCommand.dungeonMusic && Utils.inDungeons) {
            if (message.contains("[NPC] Mort: Here, I found this map when I first entered the dungeon.")) {
                dungeon.start();
            }
        }

        if (message.contains(":")) return;

        if (Utils.inDungeons) {
            if (message.contains("EXTRA STATS ")) {
                dungeonboss.stop();
                bloodroom.stop();
                dungeon.stop();
            } else if (ToggleCommand.bloodRoomMusic && message.contains("The BLOOD DOOR has been opened!")) {
                bloodroom.start();
            }
        }
    }

    @SubscribeEvent
    public void onSound(PlaySoundEvent event) {
        if (cancelNotes && event.name.startsWith("note.")) {
            event.result = null;
        }
    }

    public static void init(String configDirectory) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (configDirectory == null) return;
        File directory = new File(configDirectory + "/dsmmusic");
        if (!directory.exists()) directory.mkdir();

        reset();

        File dungeonBossFile = new File(directory + "/dungeonboss.wav");
        System.out.println("dungeonboss.wav exists?: " + dungeonBossFile.exists());
        dungeonboss = new Song(dungeonBossFile, dungeonbossVolume);

        File bloodRoomFile = new File(directory + "/bloodroom.wav");
        System.out.println("bloodroom.wav exists?: " + bloodRoomFile.exists());
        bloodroom = new Song(bloodRoomFile, bloodroomVolume);

        File dungeonFile = new File(directory + "/dungeon.wav");
        System.out.println("dungeon.wav exists?: " + dungeonFile.exists());
        dungeon = new Song(dungeonFile, dungeonVolume);
    }

    public static void reset() {
        if (dungeonboss != null) dungeonboss.stop();
        if (bloodroom != null) bloodroom.stop();
        if (dungeon != null) dungeon.stop();
    }

    public static class Song {

        public Clip music;

        public Song(File file, int volume) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
            if (file.exists()) {
                music = AudioSystem.getClip();
                AudioInputStream ais = AudioSystem.getAudioInputStream(file);
                music.open(ais);

                setVolume(volume);
            }
        }

        public void start() {
            reset();
            if (music != null) {
                cancelNotes = true;
                music.setMicrosecondPosition(0);
                music.start();
                music.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }

        public void stop() {
            cancelNotes = false;
            if (music != null) music.stop();
        }

        public boolean setVolume(int volume) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (music == null) return false;
            if (volume <= 0 || volume > 100) {
                if (player != null) player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Volume can only be set between 0% and 100%."));
                return false;
            }

            float decibels = (float) (20 * Math.log(volume / 100.0));
            FloatControl control = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
            if (decibels <= control.getMinimum() || decibels >= control.getMaximum()) {
                return false;
            }

            control.setValue(decibels);
            return true;
        }

    }

}
