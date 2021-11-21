package me.Danker.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NetworthCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "networth";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("nw");
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " [name]";
    }

    public static String usage(ICommandSender arg0) {
        return new NetworthCommand().getCommandUsage(arg0);
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
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking networth of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
            } else {
                username = arg1[0];
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking networth of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
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

            try {
                System.out.println("Fetching networth...");

                JsonObject body = new JsonObject();
                body.add("data", profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject());
                body.get("data").getAsJsonObject().add("banking", profileResponse.get("profile").getAsJsonObject().get("banking").getAsJsonObject());

                JsonObject networth = APIHandler.getResponsePOST("https://nariah-dev.com/api/networth/categories", body, true);
                if (networth.has("success") && !networth.get("success").getAsBoolean()) {
                    String reason = profileResponse.get("cause").getAsString();
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed with reason: " + reason));
                    return;
                }

                JsonObject data = networth.get("data").getAsJsonObject();
                JsonObject categoriesObject = data.get("categories").getAsJsonObject();

                long total = data.get("networth").getAsLong();
                long purse = data.get("purse").getAsLong();
                long bank = data.get("bank").getAsLong();
                long sacks = data.get("sacks").getAsLong();

                List<String> categories = categoriesObject.entrySet().stream()
                                            .map(Map.Entry::getKey)
                                            .collect(Collectors.toCollection(ArrayList::new));
                StringBuilder subcategories = new StringBuilder();

                for (String category : categories) {
                    JsonObject categoryObject = categoriesObject.get(category).getAsJsonObject();
                    subcategories.append("\n").append(DankersSkyblockMod.TYPE_COLOUR).append(Utils.capitalizeString(category)).append(" value - ").append(formatPrice(categoryObject.get("total").getAsLong())).append("\n");

                    JsonArray topItems = categoryObject.get("top_items").getAsJsonArray();

                    for (int i = 0; i < 5 && i < topItems.size(); i++) {
                        JsonObject item = topItems.get(i).getAsJsonObject();

                        subcategories.append(EnumChatFormatting.AQUA);
                        if (item.has("count") && item.get("count").getAsInt() > 1) {
                            subcategories.append(item.get("count").getAsInt()).append("x").append(" ");
                        }
                        subcategories.append(item.get("name").getAsString());
                        if (item.has("recomb") && item.get("recomb").getAsBoolean()) {
                            subcategories.append(" ⇑");
                        }
                        subcategories.append(" ➜ ").append(EnumChatFormatting.GOLD).append(formatPrice(item.get("price").getAsLong())).append("\n");
                    }
                }

                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------\n" +
                        EnumChatFormatting.AQUA + username + "'s Networth:\n" +
                        DankersSkyblockMod.TYPE_COLOUR + "Purse: " + EnumChatFormatting.GOLD + formatPrice(purse) + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + "Bank: " + EnumChatFormatting.GOLD + formatPrice(bank) + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + "Sacks: " + EnumChatFormatting.GOLD + formatPrice(sacks) + "\n" +
                        subcategories + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + "Total: " + EnumChatFormatting.GOLD + formatPrice(total) + "\n" +
                        DankersSkyblockMod.DELIMITER_COLOUR + EnumChatFormatting.BOLD + "-------------------"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    String formatPrice(long price) {
        String[] suffixes = {"", "K", "M", "B", "T"};
        double p = price;
        int i = 0;
        while ((p / 1000) >= 1) {
            p /= 1000;
            i++;
        }
        return new DecimalFormat("#.#").format(p) + suffixes[i];
    }

}
