package ir.syphix.thepit.core.arena;

import ir.syphix.palladiumapi.utils.YamlConfig;
import ir.syphix.thepit.core.kit.Kit;
import ir.syphix.thepit.core.kit.KitManager;
import ir.syphix.thepit.core.player.PitPlayer;
import ir.syphix.thepit.core.player.PitPlayerManager;
import ir.syphix.thepit.data.YamlDataManager;
import ir.syphix.thepit.event.ArenaJoinEvent;
import ir.syphix.thepit.file.FileManager;
import ir.syphix.thepit.utils.TextUtils;
import ir.syphix.thepit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class ArenaManager {

    private final static HashMap<String, Arena> arenas = new HashMap<>();
    private final static HashMap<String, FileConfiguration> arenasFile = new HashMap<>();

    private ArenaManager() {
    }

    public static void create(CommandSender sender, String id) {
        if (arenas.containsKey(id)) {
            if (sender != null) {
                TextUtils.sendMessage(sender, ""); //TODO: arena is already exist msg
            }
            return;
        }
        Arena arena = new Arena(id); //TODO: create arena file and set data in it
        arena.yamlConfig(createArenaFile(arena));
        arena.status(ArenaStatus.DISABLE, true);
        arenas.put(id, arena);
    }

    public static void create(Player player, String id, Location spawnLocation, Kit kit, ArenaStatus arenaStatus, List<Location> goldSpawnLocations, YamlConfig yamlConfig) {
        if (arenas.containsKey(id)) {
            if (player != null) {
                TextUtils.sendMessage(player, ""); //TODO: arena already exist msg
            }
            return;
        }
        Arena arena = new Arena(id, spawnLocation, kit, arenaStatus, goldSpawnLocations, yamlConfig);
        arena.yamlConfig(createArenaFile(arena));
        arena.status(ArenaStatus.ENABLE, true);
        arenas.put(id, arena);
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

        ArenaJoinEvent arenaJoinEvent = new ArenaJoinEvent(pitPlayer, arena);
        Bukkit.getServer().getPluginManager().callEvent(arenaJoinEvent);
        if (arenaJoinEvent.isCancelled()) return;

        if (YamlDataManager.YamlDataConfig.arenaClearEffects()) {
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
        }
        if (YamlDataManager.YamlDataConfig.arenaClearInventory()) {
            player.getInventory().clear();
        }

        player.setHealth(YamlDataManager.YamlDataConfig.arenaHealth());
        player.setFoodLevel(YamlDataManager.YamlDataConfig.arenaFoodLevel());
        player.setSaturation(YamlDataManager.YamlDataConfig.arenaSaturation());
        KitManager.giveKit(player, arena.kit().id());
        player.teleport(arena.spawnLocation().clone());
        player.getInventory().setHeldItemSlot(YamlDataManager.YamlDataConfig.heldItemSlot());
        arena.addPlayer(pitPlayer);
        pitPlayer.arena(arena);
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
