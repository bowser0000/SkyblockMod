# Danker's Skyblock Mod
QOL changes that enhances your Hypixel Skyblock experience. Created to add features I couldn't find in a mod, or if the mod was paid (which is against the [Mojang TOS](https://account.mojang.com/documents/commercial_guidelines)).

## Current features
- Guild party desktop notifications (toggleable)
- Coordinate and angle display (toggleable)
- Slayer item tracker
- Slayer API command

## Commands
- /toggle [gparty/coords/list] - Toggles features. /toggle list returns values of every toggle.
- /setkey [key] - Sets API key.
- /getkey - Returns key set with /setkey.
- /loot [zombie/spider/wolf] - Returns loot received from the slayer quest.
- /display [zombie/spider/wolf/off] - Text display for slayer tracker.
- /move [coords/display] [x] [y] - Moves text display to specified X and Y coordinates.
- /slayer <name> - Uses API to get slayer xp for a person. If no name is provided, it checks yours.

### Notes
- Slayer tracker for token drops and 20% chance drops uses a 12x12x12 bounding box centered on the player to detect the drops. If you are out of the range of the item drop, it will not count on the tracker.
- API commands may take a while depending on your internet connection.
- API commands send one request to Mojang and two requests to the Hypixel API. This means doing more than 60 API commands/minute will get you rate-limited. If you have an unlimited Hypixel API key, this is changed to 600 API commands/10 minutes.
