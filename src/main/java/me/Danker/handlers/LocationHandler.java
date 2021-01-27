package me.Danker.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class LocationHandler {

    private static double lastLocationTime = 0;
    private static String location = null;
    private static int retry = 0;

    public static void sendLocraw() {
        if (Minecraft.getMinecraft().theWorld != null && location == null && (System.currentTimeMillis() - lastLocationTime) > 2500 && retry <= 5) {
            lastLocationTime = System.currentTimeMillis();
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/locraw");
            retry += 1;
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        lastLocationTime = System.currentTimeMillis();
        location = null;
        retry = 0;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if(event.message.getUnformattedText().startsWith("{")) {
            try {
                JsonObject locraw = new Gson().fromJson(event.message.getUnformattedText(), JsonObject.class);
                if(locraw.has("mode")) {
                    if(System.currentTimeMillis() - lastLocationTime < 5000) {
                        event.setCanceled(true);
                    }
                    location = locraw.get("mode").getAsString();
                    System.out.println("Got current location: " + location);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getLocation() {
        if(location != null) {
            return location;
        }
        return null;
    }
}
