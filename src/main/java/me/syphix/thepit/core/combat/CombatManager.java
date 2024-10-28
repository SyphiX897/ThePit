package me.syphix.thepit.core.combat;

import net.kyori.adventure.bossbar.BossBar;

import java.util.HashMap;
import java.util.UUID;

public class CombatManager {

    private final static HashMap<UUID, Long> combatPlayers = new HashMap<>();

    public static boolean exist(UUID uuid) {

        return combatPlayers.containsKey(uuid);
    }
}
