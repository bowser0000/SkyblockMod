package me.Danker.features.loot;

import me.Danker.config.CfgConfig;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class MythologicalTracker {

    public static double mythCoins;
    public static int griffinFeathers;
    public static int crownOfGreeds;
    public static int washedUpSouvenirs;
    public static int minosHunters;
    public static int siameseLynxes;
    public static int minotaurs;
    public static int gaiaConstructs;
    public static int minosChampions;
    public static int minosInquisitors;

    public static double mythCoinsSession = 0;
    public static int griffinFeathersSession = 0;
    public static int crownOfGreedsSession = 0;
    public static int washedUpSouvenirsSession = 0;
    public static int minosHuntersSession = 0;
    public static int siameseLynxesSession = 0;
    public static int minotaursSession = 0;
    public static int gaiaConstructsSession = 0;
    public static int minosChampionsSession = 0;
    public static int minosInquisitorsSession = 0;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        if (message.contains("You dug out")) {
            if (message.contains(" coins!")) {
                double coinsEarned = Double.parseDouble(message.replaceAll("\\D", ""));
                mythCoins += coinsEarned;
                mythCoinsSession += coinsEarned;
                CfgConfig.writeDoubleConfig("mythological", "coins", mythCoins);
            } else if (message.contains("a Griffin Feather!")) {
                griffinFeathers++;
                griffinFeathersSession++;
                CfgConfig.writeIntConfig("mythological", "griffinFeather", griffinFeathers);
            } else if (message.contains("a Crown of Greed!")) {
                crownOfGreeds++;
                crownOfGreedsSession++;
                CfgConfig.writeIntConfig("mythological", "crownOfGreed", crownOfGreeds);
            } else if (message.contains("a Washed-up Souvenir!")) {
                washedUpSouvenirs++;
                washedUpSouvenirsSession++;
                CfgConfig.writeIntConfig("mythological", "washedUpSouvenir", washedUpSouvenirs);
            } else if (message.contains("a Minos Hunter!")) {
                minosHunters++;
                minosHuntersSession++;
                CfgConfig.writeIntConfig("mythological", "minosHunter", minosHunters);
            } else if (message.contains("Siamese Lynxes!")) {
                siameseLynxes++;
                siameseLynxesSession++;
                CfgConfig.writeIntConfig("mythological", "siameseLynx", siameseLynxes);
            } else if (message.contains("a Minotaur!")) {
                minotaurs++;
                minotaursSession++;
                CfgConfig.writeIntConfig("mythological", "minotaur", minotaurs);
            } else if (message.contains("a Gaia Construct!")) {
                gaiaConstructs++;
                gaiaConstructsSession++;
                CfgConfig.writeIntConfig("mythological", "gaiaConstruct", gaiaConstructs);
            } else if (message.contains("a Minos Champion!")) {
                Minecraft mc = Minecraft.getMinecraft();
                List<Entity> listWorldEntity = mc.theWorld.getLoadedEntityList();
                for (Entity entity : listWorldEntity) {
                    if (entity.getName().contains("Minos Champion")) {
                        minosChampions++;
                        minosChampionsSession++;
                        CfgConfig.writeIntConfig("mythological", "minosChampion", minosChampions);
                    } else if (entity.getName().contains("Minos Inquisitor")) {
                        minosInquisitors++;
                        minosInquisitorsSession++;
                        CfgConfig.writeIntConfig("mythological", "minosInquisitor", minosInquisitors);
                    }
                }
            } else if (message.contains("a Minos Inquisitor!")) {
                minosInquisitors++;
                minosInquisitorsSession++;
                CfgConfig.writeIntConfig("mythological", "minosInquisitor", minosInquisitors);
            }
        }
    }

}
