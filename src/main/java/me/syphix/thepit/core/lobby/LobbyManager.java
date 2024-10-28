package me.syphix.thepit.core.lobby;

import ir.syphix.palladiumapi.utils.Utils;
import me.syphix.thepit.core.action.ActionManager;
import me.syphix.thepit.core.player.PitPlayerManager;
import me.syphix.thepit.data.YamlDataManager;
import me.syphix.thepit.utils.TextUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.sayandev.stickynote.bukkit.StickyNote;


public class LobbyManager {

    private static Location lobbyLocation;

    public static Location location() {
        return lobbyLocation;
    }

    public static void location(Location location) {
        Utils.setLocationToSection(location, StickyNote.plugin().getConfig().getConfigurationSection("lobby.location"), false);
        lobbyLocation = location;
        StickyNote.plugin().saveConfig();
    }

    public static void teleport(Player player) {
        if (location() == null) {
            TextUtils.sendMessage(player, ""); //TODO: lobby location does not exist msg
            return;
        }

        player.teleport(location());
        PitPlayerManager.pitPlayer(player.getUniqueId()).combatStats().killStreak(0);
        if (YamlDataManager.YamlDataConfig.lobbyJoinActions() == null) return;
        ActionManager.handle(player, YamlDataManager.YamlDataConfig.lobbyJoinActions());
    }

}
