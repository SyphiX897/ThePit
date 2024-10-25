package ir.syphix.thepit.core.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.sayandev.stickynote.bukkit.StickyNote;

import java.io.File;
import java.util.Arrays;

public class ArenaUtils {

    public static void setLocationToSection(Location location, ConfigurationSection section, boolean isGoldSpawn) {
        section.set("world", location.getWorld().getName());
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        if (isGoldSpawn) return;
        section.set("yaw", location.getYaw());
        section.set("pitch", location.getPitch());
    }

    public static Location getLocationFromSection(ConfigurationSection section) {
        String worldName = section.getString("world");

        if (Bukkit.getWorld(worldName) == null) {
            if (!Arrays.stream(Bukkit.getWorldContainer().listFiles()).map(File::getName).toList().contains(worldName)) {
                StickyNote.warn("Invalid world name: " + worldName + ", unloading arena...");
                return null;
            } else {
                Bukkit.createWorld(new WorldCreator(worldName));
            }
        }

        return new Location(Bukkit.getWorld(section.getString("world")),
                section.getDouble("x"),
                section.getDouble("y"),
                section.getDouble("z"),
                (float) section.getDouble("yaw"),
                (float) section.getDouble("pitch"));
    }
}
