package ir.syphix.thepit.database;

import ir.syphix.palladiumapi.annotation.listener.ListenerHandler;
import ir.syphix.thepit.ThePit;
import ir.syphix.thepit.core.player.PitPlayer;
import ir.syphix.thepit.core.player.PitPlayerManager;
import ir.syphix.thepit.core.player.stats.CombatStats;
import ir.syphix.thepit.core.player.stats.MainStats;
import ir.syphix.thepit.data.YamlDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.sayandev.stickynote.bukkit.StickyNote;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

@ListenerHandler
public class DatabaseListeners implements Listener {


    @EventHandler
    public void addPlayerToCache(PlayerJoinEvent event) {
        StickyNote.runSync(() -> {
            UUID uuid = event.getPlayer().getUniqueId();
            PitPlayer pitPlayer;

            if (ThePit.database().hasPlayer(uuid)) {
                pitPlayer = ThePit.database().getPlayer(uuid);
            } else {
                MainStats mainStats = new MainStats(YamlDataManager.YamlDataConfig.startingGold(), 0, 0, 0,
                        new ArrayList<>(), new LinkedList<>(), new ArrayList<>(), new LinkedList<>());

                CombatStats combatStats = new CombatStats(0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0);

                pitPlayer = new PitPlayer(event.getPlayer(), mainStats, combatStats);
                ThePit.database().addPlayerAsync(pitPlayer);
            }

            pitPlayer.setEnderChestItems();
            PitPlayerManager.add(pitPlayer);
        }, 10);
    }

    @EventHandler
    public void removePlayerFromCache(PlayerQuitEvent event) {
        StickyNote.warn("1");
        UUID uuid = event.getPlayer().getUniqueId();
        if (!PitPlayerManager.exist(uuid)) return;
        StickyNote.warn("2");
        PitPlayer pitPlayer = PitPlayerManager.pitPlayer(uuid);

        pitPlayer.updateEnderChest();
        ThePit.database().addPlayerSync(pitPlayer);
        PitPlayerManager.remove(uuid);
    }
}
