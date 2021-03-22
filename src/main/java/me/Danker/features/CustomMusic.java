package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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

    static boolean prevInDungeonBossRoom = false;
    static boolean inDungeonBossRoom = false;
    public static Clip dungeonboss;

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
                        if (!prevInDungeonBossRoom && dungeonboss != null) {
                            start();
                        }
                    } else {
                        reset();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inDungeons) return;
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains("EXTRA STATS ")) {
            if (dungeonboss != null) dungeonboss.stop();
        }
    }

    @SubscribeEvent
    public void onSound(PlaySoundEvent event) {
        if (ToggleCommand.dungeonBossMusic && Utils.inDungeons && inDungeonBossRoom) {
            if (event.isCancelable() && event.name.startsWith("note.")) event.setCanceled(true);
        }
    }

    public static void init(String configDirectory) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (configDirectory == null) return;
        File directory = new File(configDirectory + "/dsmmusic");
        if (!directory.exists()) directory.mkdir();

        reset();

        dungeonboss = AudioSystem.getClip();
        File dungeonBossFile = new File(configDirectory + "/dsmmusic/dungeonboss.wav");
        System.out.println("dungeonboss.wav exists?: " + dungeonBossFile.exists());
        if (dungeonBossFile.exists()) {
            AudioInputStream ais = AudioSystem.getAudioInputStream(dungeonBossFile);
            dungeonboss.open(ais);

            FloatControl volume = (FloatControl) dungeonboss.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20F);
        }
    }

    static void reset() {
        inDungeonBossRoom = false;
        if (dungeonboss != null) dungeonboss.stop();
    }

    public static void start() {
        if (dungeonboss != null && inDungeonBossRoom) {
            dungeonboss.setMicrosecondPosition(0);
            dungeonboss.start();
            dungeonboss.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

}
