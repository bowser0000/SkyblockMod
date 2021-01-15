package me.Danker.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class GriffinBurrowUtils {

    public static ArrayList<Burrow> burrows = new ArrayList<>();
    public static ArrayList<BlockPos> dugBurrows = new ArrayList<>();
    public static BlockPos lastDugBurrow = null;

    private static Minecraft mc = Minecraft.getMinecraft();

    public static void refreshBurrows() {

        new Thread(() -> {
            System.out.println("Finding burrows");
            String uuid = mc.thePlayer.getGameProfile().getId().toString().replaceAll("[\\-]", "");
            String apiKey = ConfigHandler.getString("api", "APIKey");
            if (apiKey.length() == 0) {
                mc.thePlayer.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "API key not set. Use /setkey."));
                return;
            }

            String latestProfile = APIHandler.getLatestProfileID(uuid, apiKey);
            if (latestProfile == null) return;

            JsonObject profileResponse = APIHandler.getResponse("https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + apiKey);
            if (!profileResponse.get("success").getAsBoolean()) {
                String reason = profileResponse.get("cause").getAsString();
                mc.thePlayer.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Failed getting burrows with reason: " + reason));
                return;
            }

            JsonObject playerObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject();

            if (!playerObject.has("griffin")) {
                mc.thePlayer.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Failed getting burrows with reason: No griffin object."));
                return;
            }

            JsonArray burrowArray = playerObject.get("griffin").getAsJsonObject().get("burrows").getAsJsonArray();

            ArrayList<Burrow> receivedBurrows = new ArrayList<>();
            burrowArray.forEach(jsonElement -> {
                JsonObject burrowObject = jsonElement.getAsJsonObject();
                int x = burrowObject.get("x").getAsInt();
                int y = burrowObject.get("y").getAsInt();
                int z = burrowObject.get("z").getAsInt();
                int type = burrowObject.get("type").getAsInt();
                int tier = burrowObject.get("tier").getAsInt();
                int chain = burrowObject.get("chain").getAsInt();
                Burrow burrow = new Burrow(x, y, z, type, tier, chain);
                receivedBurrows.add(burrow);
            });

            dugBurrows.removeIf(dug -> !receivedBurrows.stream().anyMatch(burrow -> burrow.getBlockPos().equals(dug)));
            receivedBurrows.removeIf(burrow -> dugBurrows.contains(burrow.getBlockPos()));

            burrows.clear();
            burrows.addAll(receivedBurrows);
            mc.thePlayer.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Loaded " + DankersSkyblockMod.SECONDARY_COLOUR + receivedBurrows.size() + DankersSkyblockMod.MAIN_COLOUR + " burrows!"));

        }).start();
    }

    public static class Burrow {
        public int x, y, z, type, tier, chain;

        public Burrow(int x, int y, int z, int type, int tier, int chain) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.type = type;
            this.tier = tier;
            this.chain = chain;
        }

        public BlockPos getBlockPos() {
            return new BlockPos(x, y, z);
        }

        public void drawWaypoint(float partialTicks) {
            BlockPos pos = this.getBlockPos();
            GlStateManager.disableDepth();
            Utils.draw3DLine(new Vec3(pos.add(0.5, 0, 0.5)), new Vec3(pos.add(0.5, 200, 0.5)), new Color(255, 0, 0).getRGB(), partialTicks);
            Utils.draw3DBox(new AxisAlignedBB(pos, pos.add(1, 1, 1)), new Color(255, 0, 0).getRGB(), partialTicks);
            GlStateManager.enableDepth();
        }

    }

}
