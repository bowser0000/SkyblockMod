package me.Danker.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class SlayerLootDropEvent extends Event {

    public final String drop;
    public final int amount;

    public SlayerLootDropEvent(String drop, int amount) {
        this.drop = drop;
        this.amount = amount;
    }

}
