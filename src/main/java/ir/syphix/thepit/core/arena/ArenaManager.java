package ir.syphix.thepit.core.arena;

import ir.syphix.thepit.annotation.AutoConstruct;
import ir.syphix.thepit.core.kit.Kit;
import ir.syphix.thepit.core.kit.KitManager;
import ir.syphix.thepit.utils.TextUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

@AutoConstruct
public class ArenaManager {

    private final static HashMap<String, Arena> arenas = new HashMap<>();
    private static ArenaManager instance;

    public static ArenaManager getInstance() {
        return instance;
    }

    public ArenaManager() {
        instance = this;
    }

    public static void create(String name) {
        Arena arena = new Arena(name);
        arenas.put(name, arena);
    }

    public static void create(String name, Location spawnLocation, Kit kit, ArenaStats arenaStats, List<Location> goldSpawnLocations) {
        Arena arena = new Arena(name, spawnLocation, kit, arenaStats, goldSpawnLocations);
        arenas.put(name, arena);
    }

    public static void join(Player player, String arenaName) {
        if (!arenas.containsKey(arenaName)) {
            TextUtils.sendMessage(player, ""); //TODO: arena is not exist
            return;
        }
        Arena arena = arenas.get(arenaName);

        switch (arena.arenaStats()) {
            case UPDATING -> {
                TextUtils.sendMessage(player, ""); //TODO: arena is updating msg
                return;
            }
            case DISABLE -> {
                TextUtils.sendMessage(player, ""); //TODO: arena is disable msg
                return;
            }
        }

        if (arena.spawnLocation() == null) {
            TextUtils.sendMessage(player, ""); //TODO: missing arena spawn location
            return;
        }

        if (arena.kit() == null) { //TODO: missing arena kit
            TextUtils.sendMessage(player, "");
            return;
        }

        player.teleport(arena.spawnLocation());
        player.clearActivePotionEffects();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(4);
        KitManager.giveKit(player, arena.kit().id());
    }


    public static void loadArenas(List<FileConfiguration> arenas) {

    }

}
