package me.Danker.features.loot;

import me.Danker.config.CfgConfig;
import me.Danker.events.PacketReadEvent;
import me.Danker.events.SlayerLootDropEvent;
import me.Danker.utils.Utils;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LootTracker {

    public static long itemsChecked = 0;
    // RARE DROP! (61x Twilight Arrow Poison) (+140% Magic Find!)
    // VERY RARE DROP!  (19x Nether Wart Distillate) (+160% Magic Find!)
    static Pattern twentyPattern = Pattern.compile("(VERY )?RARE DROP!  ?\\((?<amount>\\d+)x (?<drop>.+?)\\)( \\(.*\\))?");
    // CRAZY RARE DROP!  (Judgement Core) (+203% Magic Find!)
    static Pattern dropPattern = Pattern.compile("((VERY|CRAZY|INSANE) )?RARE DROP!  ?\\((?<drop>.+?)\\)( \\(.*\\))?");

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains(":")) return;

        Matcher twentyMatcher = twentyPattern.matcher(message);
        if (twentyMatcher.matches()) {
            MinecraftForge.EVENT_BUS.post(new SlayerLootDropEvent(twentyMatcher.group("drop"), Integer.parseInt(twentyMatcher.group("amount"))));
        } else {
            Matcher dropMatcher = dropPattern.matcher(message);
            if (dropMatcher.matches()) {
                MinecraftForge.EVENT_BUS.post(new SlayerLootDropEvent(dropMatcher.group("drop"), 1));
            }
        }
    }

    @SubscribeEvent
    public void onPacketRead(PacketReadEvent event) {
        if (!Utils.inSkyblock) return;

        if (event.packet instanceof S29PacketSoundEffect) {
            S29PacketSoundEffect packet = (S29PacketSoundEffect) event.packet;

            if (packet.getSoundName().equals("note.pling")) {
                if (System.currentTimeMillis() / 1000 - itemsChecked < 3) return;

                if (Utils.isInScoreboard("Boss slain!") || Utils.isInScoreboard("Slay the boss!")) {
                    int itemTeeth = Utils.getItems("Wolf Tooth");
                    int itemWebs = Utils.getItems("Tarantula Web");
                    int itemRev = Utils.getItems("Revenant Flesh");
                    int itemNullSphere = Utils.getItems("Null Sphere");
                    int itemDerelictAshe = Utils.getItems("Derelict Ashe");

                    // If no items, are detected, allow check again. Should fix items not being found
                    if (itemTeeth + itemWebs + itemRev + itemNullSphere + itemDerelictAshe > 0) {
                        itemsChecked = System.currentTimeMillis() / 1000;
                        WolfTracker.teeth += itemTeeth;
                        SpiderTracker.webs += itemWebs;
                        ZombieTracker.revFlesh += itemRev;
                        EndermanTracker.nullSpheres += itemNullSphere;
                        BlazeTracker.derelictAshes += itemDerelictAshe;
                        WolfTracker.teethSession += itemTeeth;
                        SpiderTracker.websSession += itemWebs;
                        ZombieTracker.revFleshSession += itemRev;
                        EndermanTracker.nullSpheresSession += itemNullSphere;
                        BlazeTracker.derelictAshesSession += itemDerelictAshe;

                        CfgConfig.writeIntConfig("wolf", "teeth", WolfTracker.teeth);
                        CfgConfig.writeIntConfig("spider", "web", SpiderTracker.webs);
                        CfgConfig.writeIntConfig("zombie", "revFlesh", ZombieTracker.revFlesh);
                        CfgConfig.writeIntConfig("enderman", "nullSpheres", EndermanTracker.nullSpheres);
                        CfgConfig.writeIntConfig("blaze", "derelictAshe", BlazeTracker.derelictAshes);
                    }
                }
            }
        }
    }

    public static int getAmountfromMessage(String message) {
        Matcher matcher = dropPattern.matcher(message);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group("amount"));
        }
        return 1;
    }
    
}
