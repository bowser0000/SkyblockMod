package me.Danker.locations;

public enum Location {
    NONE(""),
    CRIMSON_ISLE("Crimson Isle"),
    CRYSTAL_HOLLOWS("Crystal Hollows"),
    DEEP_CAVERNS("Deep Caverns"),
    CATACOMBS("Catacombs"),
    DUNGEON_HUB("Dungeon Hub"),
    DWARVEN_MINES("Dwarven Mines"),
    END("The End"),
    FARMING_ISLANDS("The Farming Islands"),
    GOLD_MINE("Gold Mine"),
    HUB("Hub"),
    INSTANCED("Instanced"),
    JERRY_WORKSHOP("Jerry's Workshop"),
    PRIVATE_ISLAND("Private Island"),
    PARK("The Park"),
    SPIDERS_DEN("Spider's Den"),
    GARDEN("Garden");

    String text;

    Location(String text) {
        this.text = text;
    }

    public static Location fromTab(String text) {
        for (Location location : Location.values()) {
            if (location.text.equalsIgnoreCase(text)) return location;
        }
        return NONE;
    }

}
