package me.syphix.thepit.listener;

import ir.syphix.palladiumapi.annotation.listener.ListenerHandler;
import me.syphix.thepit.core.arena.ArenaManager;
import me.syphix.thepit.core.lobby.LobbyManager;
import me.syphix.thepit.data.YamlDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.sayandev.stickynote.bukkit.StickyNote;

@ListenerHandler
public class PlayerJoinListener implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (YamlDataManager.YamlDataConfig.isForceArenaJoinEnable()) {
            String arenaId = YamlDataManager.YamlDataConfig.forceArenaJoinArena();
            if (!ArenaManager.exist(arenaId.toLowerCase())) {
                StickyNote.warn("Invalid arena id: " + arenaId + " in config.yml force_arena_join section.");
                return;
            }
            ArenaManager.join(event.getPlayer(), YamlDataManager.YamlDataConfig.forceArenaJoinArena());
        } else {
            if (LobbyManager.location() == null) {
                StickyNote.warn("The lobby location has not been set.");
                return;
            }
            LobbyManager.teleport(event.getPlayer());
        }
    }
}
