package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.CustomMusic;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

public class CustomMusicCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "dsmmusic";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " <stop/reload/volume> [dungeonboss] [#]";
    }

    public static String usage(ICommandSender arg0) {
        return new CustomMusicCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "stop", "reload", "volume");
        } else if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args, "dungeonboss");
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        final EntityPlayer player = (EntityPlayer)arg0;

        if (arg1.length == 0) {
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
            return;
        }

        switch (arg1[0].toLowerCase()) {
            case "stop":
                if (CustomMusic.dungeonboss != null) CustomMusic.dungeonboss.stop();
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Stopped custom music."));
                break;
            case "reload":
                try {
                    CustomMusic.init(DankersSkyblockMod.configDirectory);
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Reloaded custom music."));
                } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "An error occurred while trying to reload music."));
                    e.printStackTrace();
                }
                break;
            case "volume":
                if (arg1.length < 3) {
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
                    return;
                }

                switch (arg1[1].toLowerCase()) {
                    case "dungeonboss":
                        try {
                            float volume = Float.parseFloat(arg1[2]);

                            FloatControl bounds = (FloatControl) CustomMusic.dungeonboss.getControl(FloatControl.Type.MASTER_GAIN);
                            if (volume <= bounds.getMinimum() || volume >= bounds.getMaximum()) {
                                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Volume can only be set between " + bounds.getMinimum() + " and " + bounds.getMaximum() + "."));
                                return;
                            }

                            CustomMusic.dungeonbossDecibels = volume;
                            ConfigHandler.writeIntConfig("music", "DungeonBossDecibels", (int) volume);
                            CustomMusic.init(DankersSkyblockMod.configDirectory);
                        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "An error occurred while trying to reload music."));
                            e.printStackTrace();
                        }
                        break;
                    default:
                        player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
                }
                break;
            default:
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
        }
    }

}
