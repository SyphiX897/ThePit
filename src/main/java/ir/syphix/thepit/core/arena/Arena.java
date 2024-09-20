package ir.syphix.thepit.core.arena;

import javax.xml.stream.Location;

public class Arena {

    String name;
    Location spawnLocation;

    public Arena(String name, Location spawnLocation) {
        this.name = name;
        this.spawnLocation = spawnLocation;

    }
}
