package ir.syphix.thepit.core.arena;

import ir.syphix.thepit.api.ThePitAPI;
import ir.syphix.thepit.core.kit.Kit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private String name;
    private Location spawnLocation;
    private Kit kit;
    private ArenaStats arenaStats;
    private List<Location> goldSpawnLocations = new ArrayList<>();

    public Arena(String name) {
        this.name = name;
        this.arenaStats = ArenaStats.ENABLE;
    }

    public Arena(String name, Location spawnLocation, Kit kit, ArenaStats arenaStats, List<Location> goldSpawnLocations) {
        this.name = name;
        this.spawnLocation = spawnLocation;
        this.kit = kit;
        this.arenaStats = arenaStats;
        this.goldSpawnLocations = goldSpawnLocations;
    }

    public String name() {
        return name;
    }

    public Location spawnLocation() {
        return spawnLocation;
    }

    public Kit kit() {
        return kit;
    }

    public ArenaStats arenaStats() {
        return arenaStats;
    }

    public List<Location> goldSpawnLocations() {
        return goldSpawnLocations;
    }
}
