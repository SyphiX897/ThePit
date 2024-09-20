package ir.syphix.thepit.core.player.stats;

import ir.syphix.thepit.core.mission.Mission;
import ir.syphix.thepit.core.perk.Perk;

import java.util.HashMap;
import java.util.List;

public class MainStats {

    double gold;
    int level;
    double xp;
    int prestige;
    List<Perk> unlockedPerks;
    HashMap<Integer, Perk> selectedPerks;
    List<Mission> completedMissions;
    List<String> enderChestItems;

    public MainStats(double gold, List<String> enderChestItems, List<Mission> completedMissions,
                     HashMap<Integer, Perk> selectedPerks, List<Perk> unlockedPerks, int prestige,
                     int level, double xp) {


        this.gold = gold;
        this.enderChestItems = enderChestItems;
        this.completedMissions = completedMissions;
        this.selectedPerks = selectedPerks;
        this.unlockedPerks = unlockedPerks;
        this.prestige = prestige;
        this.level = level;
        this.xp = xp;
    }
}
