package ir.syphix.thepit.core.player;

import ir.syphix.thepit.core.player.stats.CombatStats;
import ir.syphix.thepit.core.player.stats.MainStats;
import org.bukkit.entity.Player;

public class PitPlayer {

    Player player;
    MainStats mainStats;
    CombatStats combatStats;


    public PitPlayer(Player player, MainStats mainStats, CombatStats combatStats) {
        this.player = player;
        this.mainStats = mainStats;
        this.combatStats = combatStats;

    }


    //TODO:PERK SYSTEM ALGORITHM

}

