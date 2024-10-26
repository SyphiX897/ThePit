package ir.syphix.thepit.core.lobby;

import ir.syphix.thepit.utils.Utils;
import org.bukkit.Location;
import org.sayandev.stickynote.bukkit.StickyNote;


public class LobbyManager {

    public static Location location() {
        return Utils.getLocationFromSection(StickyNote.plugin().getConfig().getConfigurationSection("lobby_location"));
    }

    public static void location(Location location) {
        Utils.setLocationToSection(location, StickyNote.plugin().getConfig().getConfigurationSection("lobby_location"), false);
    }


}
