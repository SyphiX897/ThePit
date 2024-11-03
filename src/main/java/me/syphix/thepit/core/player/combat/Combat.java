package me.syphix.thepit.core.player.combat;

import me.syphix.thepit.core.player.PitPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Combat {

    private Player lastDamager;
    private Player lastKiller;
    private final HashMap<UUID, Long> attackers = new HashMap<>();

    public Combat(Player lastDamager, Player lastKiller) {
        this.lastDamager = lastDamager;
        this.lastKiller = lastKiller;
    }

}
