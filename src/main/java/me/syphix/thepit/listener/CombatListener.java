package me.syphix.thepit.listener;

import ir.syphix.palladiumapi.annotation.listener.ListenerHandler;
import me.syphix.thepit.core.player.PitPlayer;
import me.syphix.thepit.core.player.PitPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

@ListenerHandler
public class CombatListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

    }



    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!(event.getDamager() instanceof Player damager)) return;
        PitPlayer pitPlayer = PitPlayerManager.pitPlayer(player.getUniqueId());
        PitPlayer pitDamager = PitPlayerManager.pitPlayer(damager.getUniqueId());
    }



    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {

    }
}
