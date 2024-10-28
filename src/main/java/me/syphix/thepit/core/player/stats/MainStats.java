package me.syphix.thepit.core.player.stats;

import me.syphix.thepit.core.mission.Mission;
import me.syphix.thepit.core.perk.Perk;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class MainStats {

    private double gold;
    private long level;
    private double xp;
    private long prestige;
    private List<Perk> unlockedPerks;
    private LinkedList<Perk> selectedPerks;
    private List<Mission> completedMissions;
    private LinkedList<ItemStack> enderChestItems;

    public MainStats(double gold, long level, double xp, long prestige, List<Perk> unlockedPerks, LinkedList<Perk> selectedPerks, List<Mission> completedMissions, LinkedList<ItemStack> enderChestItems) {

        this.gold = gold;
        this.level = level;
        this.xp = xp;
        this.prestige = prestige;
        this.unlockedPerks = unlockedPerks;
        this.selectedPerks = selectedPerks;
        this.completedMissions = completedMissions;
        this.enderChestItems = enderChestItems;

    }

    public double gold() {
        return gold;
    }

    public long level() {
        return level;
    }

    public double xp() {
        return xp;
    }

    public long prestige() {
        return prestige;
    }

    public List<Perk> unlockedPerks() {
        return unlockedPerks;
    }

    public LinkedList<Perk> selectedPerks() {
        return selectedPerks;
    }

    public List<Mission> completedMissions() {
        return completedMissions;
    }

    public LinkedList<ItemStack> enderChestItems() {
        return enderChestItems;
    }

    public void gold(double gold) {
        this.gold = gold;
    }

    public void level(long level) {
        this.level = level;
    }

    public void xp(double xp) {
        this.xp = xp;
    }

    public void prestige(long prestige) {
        this.prestige = prestige;
    }
}
