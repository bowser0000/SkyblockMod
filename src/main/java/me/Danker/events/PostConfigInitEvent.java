package me.Danker.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class PostConfigInitEvent extends Event {

    public final String configDirectory;

    public PostConfigInitEvent(String configDirectory) {
        this.configDirectory = configDirectory;
    }

}
