package me.Danker.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class ModInitEvent extends Event {

    public final String configDirectory;

    public ModInitEvent(String configDirectory) {
        this.configDirectory = configDirectory;
    }

}
