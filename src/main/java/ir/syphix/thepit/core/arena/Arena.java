package ir.syphix.thepit.core.arena;

import ir.syphix.palladiumapi.utils.YamlConfig;
import ir.syphix.thepit.core.kit.Kit;
import ir.syphix.thepit.core.kit.KitManager;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.sayandev.stickynote.bukkit.StickyNote;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private final String id;
    private Location spawnLocation;
    private Kit kit;
    private ArenaStatus status;
    private List<Location> goldSpawnLocations = new ArrayList<>();
    private YamlConfig yamlConfig;

    public Arena(String id) {
        this.id = id;
        this.status = ArenaStatus.ENABLE;
    }

    public Arena(String id, Location spawnLocation, Kit kit, ArenaStatus status, List<Location> goldSpawnLocations, YamlConfig yamlConfig) {
        this.id = id;
        this.spawnLocation = spawnLocation;
        this.kit = kit;
        this.status = status;
        this.goldSpawnLocations = goldSpawnLocations;
        this.yamlConfig = yamlConfig;
    }

    public String id() {
        return id;
    }

    public Location spawnLocation() {
        return spawnLocation;
    }

    public void spawnLocation(Location location, boolean updateYamlFile) {
        spawnLocation = location;
        if (updateYamlFile) {
            if (!config().contains("spawn_location")) {
                config().createSection("spawn_location");
            }
            ArenaUtils.setLocationToSection(location, config().getConfigurationSection("spawn_location"));
            yamlConfig.saveConfig();
        }
    }

    public Kit kit() {
        return kit;
    }

    public void kit(String id, boolean updateYamlFile) {
        kit(KitManager.kit(id), updateYamlFile);
    }

    public void kit(Kit kit, boolean updateYamlFile) {
        this.kit = kit;
        if (updateYamlFile) {
            config().set("kit", kit.id());
            yamlConfig.saveConfig();
        }
    }

    public ArenaStatus status() {
        return status;
    }

    public void status(ArenaStatus status, boolean updateYamlFile) {
        this.status = status;
        if (updateYamlFile) {
            config().set("arena_status", String.valueOf(status));
            yamlConfig.saveConfig();
        }
    }

    public List<Location> goldSpawnLocations() {
        return goldSpawnLocations;
    }

    public void goldSpawnLocations(List<Location> locations, boolean updateYamlFile) {
        goldSpawnLocations = locations;
        if (updateYamlFile) {
            config().set("random_gold_locations", null);
            config().createSection("random_gold_locations");

            for (Location location : locations) {
                ArenaUtils.setLocationToSection(location, config().getConfigurationSection("random_gold_locations").createSection(String.valueOf(UUID.randomUUID())));
            }
        }
    }

    public void addGoldSpawnLocation(Location location, boolean updateYamlFile) {
        goldSpawnLocations.add(location);
        if (updateYamlFile) {
            if (!config().contains("random_gold_locations")) {
             config().createSection("random_gold_locations")   ;
            }
            ArenaUtils.setLocationToSection(location, config().getConfigurationSection("random_gold_locations").createSection(String.valueOf(UUID.randomUUID())));
        }
    }

    public YamlConfig yamlConfig() {
        return this.yamlConfig;
    }

    public FileConfiguration config() {
        return yamlConfig.getConfig();
    }


    public void yamlConfig(YamlConfig yamlConfig) {
        this.yamlConfig = yamlConfig;
    }


    public static Arena fromConfigFile(YamlConfig arenaYamlConfig) {
        FileConfiguration config = arenaYamlConfig.getConfig();
        String arenaId = config.getString("name");
        if (arenaId == null || arenaId.isBlank()) {
            StickyNote.warn("Arena name is missing in: " + arenaYamlConfig.getFileName() + ", unloading arena..."); //missing arena name in mammad.yml, unloading arena...
            return null;
        }

        if (ArenaManager.exist(arenaId)) {
            StickyNote.warn("Unable to load arena: " + arenaId + ", arena already exist!");
            return null;
        }

        Arena arena = new Arena(arenaId);
        arena.yamlConfig(arenaYamlConfig);

        if (config.contains("spawn_location")) {
            Location spawnLocation = ArenaUtils.getLocationFromSection(config.getConfigurationSection("spawn_location"));

            if (spawnLocation == null) return null;

            arena.spawnLocation(spawnLocation, false);
        }

        if (config.contains("kit")) {
            arena.kit(config.getString("kit"), false);
        }

        if (config.contains("arena_status")) {
            arena.status(ArenaStatus.valueOf(config.getString("arena_status")), false);
        }

        if (config.contains("random_gold_locations")) {
            List<Location> randomGoldLocations = new ArrayList<>();
            ConfigurationSection randomGoldLocationsSection = config.getConfigurationSection("random_gold_locations");
            if (!randomGoldLocationsSection.getKeys(false).isEmpty()) {
                for (String locationUUID : randomGoldLocationsSection.getKeys(false)) {
                    randomGoldLocations.add(ArenaUtils.getLocationFromSection(randomGoldLocationsSection.getConfigurationSection(locationUUID)));
                }
            }
            arena.goldSpawnLocations(randomGoldLocations, false);
        }
        return arena;
    }

}
