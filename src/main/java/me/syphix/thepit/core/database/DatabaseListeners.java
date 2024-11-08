package me.syphix.thepit.core.database;

import ir.syphix.palladiumapi.annotation.listener.ListenerHandler;
import me.syphix.thepit.ThePit;
import me.syphix.thepit.core.arena.Arena;
import me.syphix.thepit.core.arena.ArenaManager;
import me.syphix.thepit.core.economy.EconomyType;
import me.syphix.thepit.core.economy.ThePitEconomy;
import me.syphix.thepit.core.player.PitPlayer;
import me.syphix.thepit.core.player.PitPlayerManager;
import me.syphix.thepit.core.player.combat.Combat;
import me.syphix.thepit.core.player.stats.CombatStats;
import me.syphix.thepit.core.player.stats.MainStats;
import me.syphix.thepit.data.YamlDataManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

@ListenerHandler
public class DatabaseListeners implements Listener {


    @EventHandler(priority = EventPriority.LOW)
    public void addPlayerToCache(PlayerJoinEvent event) {
//        StickyNote.runSync(() -> {
        UUID uuid = event.getPlayer().getUniqueId();
        PitPlayer pitPlayer;

        if (ThePit.database().hasPlayer(uuid)) {
            pitPlayer = ThePit.database().getPlayer(uuid);
        } else {
            if (ThePitEconomy.economyType == EconomyType.VAULT) {
                ThePit.economy().depositPlayer(Bukkit.getPlayer(uuid), YamlDataManager.YamlDataConfig.startingBalance());
            }
            MainStats mainStats = new MainStats(YamlDataManager.YamlDataConfig.startingBalance(), 0, 0, 0,
                    new ArrayList<>(), new LinkedList<>(), new ArrayList<>(), new LinkedList<>());

            CombatStats combatStats = new CombatStats(0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0);

            pitPlayer = new PitPlayer(event.getPlayer(), mainStats, combatStats, new Combat(null, null));
            ThePit.database().addPlayerAsync(pitPlayer);
        }

        pitPlayer.setEnderChestItems();
        PitPlayerManager.add(pitPlayer);
//        }, 10);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void removePlayerFromCache(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (!PitPlayerManager.exist(uuid)) return;
        PitPlayer pitPlayer = PitPlayerManager.pitPlayer(uuid);

        pitPlayer.updateEnderChest();
        ThePit.database().addPlayerAsync(pitPlayer);
        for (Arena arena : ArenaManager.arenas()) {
            if (!arena.players().contains(pitPlayer)) continue;
            arena.removePlayer(pitPlayer);
        }
        PitPlayerManager.remove(uuid);
    }
}
