package me.syphix.thepit.core.arena;

import ir.syphix.palladiumapi.utils.Utils;
import ir.syphix.palladiumapi.utils.YamlConfig;
import me.syphix.thepit.core.action.ActionManager;
import me.syphix.thepit.core.kit.Kit;
import me.syphix.thepit.core.kit.KitManager;
import me.syphix.thepit.core.player.PitPlayer;
import me.syphix.thepit.core.player.PitPlayerManager;
import me.syphix.thepit.data.YamlDataManager;
import me.syphix.thepit.event.ArenaJoinEvent;
import me.syphix.thepit.file.FileManager;
import me.syphix.thepit.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class ArenaManager {

    private final static HashMap<String, Arena> arenas = new HashMap<>();
    private final static HashMap<String, FileConfiguration> arenasFile = new HashMap<>();

    private ArenaManager() {
    }

    public static void create(CommandSender sender, String id) {
        if (arenas.containsKey(id.toLowerCase())) {
            if (sender != null) {
                TextUtils.sendMessage(sender, ""); //TODO: arena already exist msg
            }
            return;
        }
        Arena arena = new Arena(id.toLowerCase());
        arena.yamlConfig(createArenaFile(arena));
        arena.status(ArenaStatus.DISABLE, true);
        arenas.put(id.toLowerCase(), arena);
    }

    public static void create(Player player, String id, Location spawnLocation, Kit kit, ArenaStatus arenaStatus, List<Location> goldSpawnLocations, YamlConfig yamlConfig) {
        if (arenas.containsKey(id.toLowerCase())) {
            if (player != null) {
                TextUtils.sendMessage(player, ""); //TODO: arena already exist msg
            }
            return;
        }
        Arena arena = new Arena(id.toLowerCase(), spawnLocation, kit, arenaStatus, goldSpawnLocations, yamlConfig);
        arena.yamlConfig(createArenaFile(arena));
        arena.status(ArenaStatus.ENABLE, true);
        arenas.put(id.toLowerCase(), arena);
        //TODO:
    }

    public static YamlConfig createArenaFile(Arena arena) {
        YamlConfig arenaYamlConfig = FileManager.ArenasFolder.createArenaFile(arena);
        FileConfiguration arenaFile = arenaYamlConfig.getConfig();
        arenaFile.set("name", arena.id());

        if (arena.spawnLocation() != null) {
            ConfigurationSection spawnSection = arenaFile.createSection("spawn_location");

            Utils.setLocationToSection(arena.spawnLocation(), spawnSection, false);
        }

        if (arena.kit() != null) {
            arenaFile.set("kit", arena.kit().id());
        }

        if (arena.status() != null) {
            arenaFile.set("arena_status", String.valueOf(arena.status()));
        }

        if (!arena.goldSpawnLocations().isEmpty()) {
            ConfigurationSection spawnSection = arenaFile.createSection("random_gold_locations");

            for (Location location : arena.goldSpawnLocations()) {
                Utils.setLocationToSection(location, spawnSection.createSection(String.valueOf(UUID.randomUUID())), true);
            }
        }

        arenaYamlConfig.saveConfig();
        return arenaYamlConfig;
    }

    public static void join(Player player, String id) {
        if (!arenas.containsKey(id)) {
            TextUtils.sendMessage(player, "1"); //TODO: arena does not exist msg
            return;
        }
        Arena arena = arenas.get(id);
        join(player, arena);
    }

    public static void join(Player player, Arena arena) {
        switch (arena.status()) {
            case UPDATING -> {
                TextUtils.sendMessage(player, "2"); //TODO: arena is updating msg
                return;
            }
            case DISABLE -> {
                TextUtils.sendMessage(player, "3"); //TODO: arena is disable msg
                return;
            }
        }

        if (arena.spawnLocation() == null) {
            TextUtils.sendMessage(player, "4"); //TODO: missing arena spawn location
            return;
        }

        if (arena.kit() == null) { //TODO: missing arena kit
            TextUtils.sendMessage(player, "5");
            return;
        }

        PitPlayer pitPlayer = PitPlayerManager.pitPlayer(player.getUniqueId());

        if (pitPlayer.arena() == arena) {
            TextUtils.sendMessage(player, "6"); //TODO: you are already joined this arena msg
            return;
        }

        ArenaJoinEvent arenaJoinEvent = new ArenaJoinEvent(pitPlayer, arena);
        Bukkit.getServer().getPluginManager().callEvent(arenaJoinEvent);
        if (arenaJoinEvent.isCancelled()) return;

        player.teleport(arena.spawnLocation().clone());
        ActionManager.handle(player, YamlDataManager.YamlDataConfig.arenaJoinActions());

        KitManager.giveKit(player, arena.kit().id());
        arena.addPlayer(pitPlayer);
        pitPlayer.arena(arena);
        pitPlayer.combatStats().killStreak(0);
    }

    public static Arena arena(String name) {
        return arenas.get(name);
    }

    public static Collection<Arena> arenas() {
        return arenas.values();
    }

    public static void add(Arena arena) {
        arenas.put(arena.id(), arena);
    }

    public static void remove(String id) {
        arenas.remove(id);
    } //TODO: remove arena from cache and remove arena file

    public static boolean exist(String id) {
        return arenas.get(id) != null;
    }

    public static List<String> arenasNameList() {
        return arenas().stream().map(Arena::id).toList();
    }

    public static void changeStatus(String id, ArenaStatus arenaStatus) {
        Arena arena = arenas.get(id);
        changeStatus(arena, arenaStatus);
    }

    public static void changeStatus(Arena arena, ArenaStatus arenaStatus) {
        arena.status(arenaStatus, true);
    }

    public static void loadArenas() {
        for (YamlConfig arenaYamlConfig : FileManager.ArenasFolder.arenasYamlConfig()) {
            Arena arena = Arena.fromConfigFile(arenaYamlConfig);
            if (arena == null) continue;
            arenas.put(arena.id(), arena);
        }
    }


} 
