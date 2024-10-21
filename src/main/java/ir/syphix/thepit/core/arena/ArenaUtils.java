package ir.syphix.thepit.core.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class ArenaUtils {

    public static void setLocationToSection(Location location, ConfigurationSection section) {
        section.set("world", location.getWorld().getName());
        section.set("x", location.x());
        section.set("y", location.y());
        section.set("z", location.z());
        section.set("yaw", location.getYaw());
        section.set("pitch", location.getPitch());
    }

    public static Location getLocationFromSection(ConfigurationSection section) {
        return new Location(Bukkit.getWorld(section.getString("world")),
                section.getDouble("x"),
                section.getDouble("y"),
                section.getDouble("z"),
                (float) section.getDouble("yaw"),
                (float) section.getDouble("pitch"));
    }
}
