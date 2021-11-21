package me.Danker.commands;

import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HOTMCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "hotmof";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " [name]";
    }

    public static String usage(ICommandSender arg0) {
        return new HOTMCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return Utils.getMatchingPlayers(args[0]);
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        // MULTI THREAD DRIFTING
        new Thread(() -> {
            EntityPlayer player = (EntityPlayer) arg0;

            // Check key
            String key = ConfigHandler.getString("api", "APIKey");
            if (key.equals("")) {
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "API key not set. Use /setkey."));
                return;
            }

            // Get UUID for Hypixel API requests
            String username;
            String uuid;
            if (arg1.length == 0) {
                username = player.getName();
                uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking HotM of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
            } else {
                username = arg1[0];
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking HotM of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
                uuid = APIHandler.getUUID(username);
            }

            // Find stats of latest profile
            String latestProfile = APIHandler.getLatestProfileID(uuid, key);
            if (latestProfile == null) return;

            String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + key;
            System.out.println("Fetching profile...");
            JsonObject profileResponse = APIHandler.getResponse(profileURL, true);
            if (!profileResponse.get("success").getAsBoolean()) {
                String reason = profileResponse.get("cause").getAsString();
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed with reason: " + reason));
                return;
            }

            System.out.println("Fetching mining stats...");
            JsonObject miningCore = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("mining_core").getAsJsonObject();
            int mithril = miningCore.get("powder_mithril").getAsInt() + miningCore.get("powder_spent_mithril").getAsInt();
            int gemstone = miningCore.get("powder_gemstone").getAsInt() + miningCore.get("powder_spent_gemstone").getAsInt();
            String ability = Node.valueOf(miningCore.get("selected_pickaxe_ability").getAsString()).name;

            ChatComponentText tree = new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "[CLICK]");
            tree.setChatStyle(tree.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hotmtree " + username + " " + latestProfile)));

            NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------\n" +
                                                        EnumChatFormatting.AQUA + username + "'s HotM:\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + "Mithril Powder: " + EnumChatFormatting.DARK_GREEN + nf.format(mithril) + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + "Gemstone Powder: " + EnumChatFormatting.LIGHT_PURPLE + nf.format(gemstone) + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + "Pickaxe Ability: " + DankersSkyblockMod.VALUE_COLOUR + ability + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + "HotM Tree: ").appendSibling(tree)
                                                        .appendSibling(new ChatComponentText("\n" + DankersSkyblockMod.DELIMITER_COLOUR + EnumChatFormatting.BOLD + "-------------------")));
        }).start();
    }

    enum Node {
        mining_speed_boost("Mining Speed Boost"),
        pickobulus("*Pickobulus"),
        vein_seeker("Vein Seeker"),
        maniac_miner("*Maniac Miner");

        public String name;

        Node(String name) {
            this.name = name;
        }
    }

}
