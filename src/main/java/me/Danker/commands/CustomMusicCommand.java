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
        return "/" + getCommandName() + " <stop/reload/volume> [dungeonboss/bloodroom/dungeon] [1-100]";
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
            return getListOfStringsMatchingLastWord(args, "dungeonboss", "bloodroom", "dungeon");
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
                CustomMusic.reset();
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

                int volume = Integer.parseInt(arg1[2]);
                boolean success;

                switch (arg1[1].toLowerCase()) {
                    case "dungeonboss":
                        success = CustomMusic.dungeonboss.setVolume(volume);
                        if (!success) {
                            return;
                        }

                        CustomMusic.dungeonbossVolume = volume;
                        ConfigHandler.writeIntConfig("music", "DungeonBossVolume", volume);
                        break;
                    case "bloodroom":
                        success = CustomMusic.bloodroom.setVolume(volume);
                        if (!success) {
                            return;
                        }

                        CustomMusic.bloodroomVolume = volume;
                        ConfigHandler.writeIntConfig("music", "BloodRoomVolume", volume);
                        break;
                    case "dungeon":
                        success = CustomMusic.dungeon.setVolume(volume);
                        if (!success) {
                            return;
                        }

                        CustomMusic.dungeonVolume = volume;
                        ConfigHandler.writeIntConfig("music", "DungeonVolume", volume);
                        break;
                    default:
                        player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
                        return;
                }

                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.SECONDARY_COLOUR + arg1[1] + DankersSkyblockMod.MAIN_COLOUR + " was set to " + DankersSkyblockMod.SECONDARY_COLOUR + volume + "%"));
                break;
            default:
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
        }
    }

}
