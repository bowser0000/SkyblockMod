package me.Danker.commands;

import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.handlers.APIHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class InventoryCommand extends CommandBase {

    public static GuiChest chest = null;

    @Override
    public String getCommandName() {
        return "inventory";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("inv");
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " [name]";
    }

    public static String usage(ICommandSender arg0) {
        return new InventoryCommand().getCommandUsage(arg0);
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
            Minecraft mc = Minecraft.getMinecraft();

            // Check key
            String key = ModConfig.apiKey;
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
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking inventory of " + ModConfig.getColour(ModConfig.secondaryColour) + username));
            } else {
                username = arg1[0];
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking inventory of " + ModConfig.getColour(ModConfig.secondaryColour) + username));
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

            System.out.println("Fetching inventory...");
            JsonObject userObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject();

            IInventory inventory = new InventoryBasic(username + "'s Inventory:", true, 54);

            String armourBase64 = userObject.get("inv_armor").getAsJsonObject().get("data").getAsString();
            InputStream armourStream = new ByteArrayInputStream(Base64.getDecoder().decode(armourBase64));

            try {
                // Armour
                NBTTagCompound armour = CompressedStreamTools.readCompressed(armourStream);
                NBTTagList armourList = armour.getTagList("i", 10);

                for (int i = 0; i < armourList.tagCount(); i++) {
                    NBTTagCompound item = armourList.getCompoundTagAt(i);
                    if (item.hasNoTags()) continue;
                    inventory.setInventorySlotContents(7 - i * 2, ItemStack.loadItemStackFromNBT(item));
                }

                ItemStack glass = new ItemStack(Blocks.stained_glass_pane, 1, 15);
                glass.setStackDisplayName("");
                for (int i = 0; i < 18; i++) {
                    if (i < 8 && i % 2 == 1) continue;
                    inventory.setInventorySlotContents(i, glass);
                }

                // Inventory
                if (userObject.has("inv_contents")) {
                    String invBase64 = userObject.get("inv_contents").getAsJsonObject().get("data").getAsString();
                    InputStream invStream = new ByteArrayInputStream(Base64.getDecoder().decode(invBase64));

                    NBTTagCompound inv = CompressedStreamTools.readCompressed(invStream);
                    NBTTagList invList = inv.getTagList("i", 10);

                    for (int i = 0; i < invList.tagCount(); i++) {
                        NBTTagCompound item = invList.getCompoundTagAt(i);
                        if (item.hasNoTags()) continue;
                        inventory.setInventorySlotContents(i < 9 ? i + 45 : i + 9, ItemStack.loadItemStackFromNBT(item));
                    }
                } else {
                    ItemStack notEnabled = new ItemStack(Blocks.barrier, 1, 0);
                    notEnabled.setStackDisplayName(EnumChatFormatting.RED + "Inventory API not enabled.");
                    for (int i = 18; i < 54; i++) {
                        inventory.setInventorySlotContents(i, notEnabled);
                    }
                }
            } catch (IOException ex) {
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "An error has occurred while reading inventory data. See logs for more info."));
                ex.printStackTrace();
            }

            chest = new GuiChest(player.inventory, inventory);
            DankersSkyblockMod.guiToOpen = "inventory";
        }).start();
    }

}
