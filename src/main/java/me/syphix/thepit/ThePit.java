package me.syphix.thepit;

import ir.syphix.palladiumapi.PalladiumAPI;
import ir.syphix.palladiumapi.core.item.CustomItemManager;
import ir.syphix.palladiumapi.utils.Utils;
import me.syphix.thepit.command.ThePitCommand;
import me.syphix.thepit.core.annotation.ActionHandlerProcessor;
import me.syphix.thepit.core.arena.ArenaManager;
import me.syphix.thepit.core.database.DatabaseUpdateTask;
import me.syphix.thepit.core.database.PitDatabase;
import me.syphix.thepit.core.kit.KitManager;
import me.syphix.thepit.core.lobby.LobbyManager;
import me.syphix.thepit.core.player.PitPlayerManager;
import me.syphix.thepit.core.task.GoldSpawnTask;
import me.syphix.thepit.data.YamlDataManager;
import me.syphix.thepit.file.FileManager;
import me.syphix.thepit.hook.PlaceHolderAPIHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.sayandev.stickynote.bukkit.StickyNote;
import org.sayandev.stickynote.loader.bukkit.StickyNoteBukkitLoader;

public final class ThePit extends JavaPlugin {

    private static ThePit instance;
    private static PitDatabase database;
    private static DatabaseUpdateTask updateTask;
    private static GoldSpawnTask goldSpawnTask;
    private static Economy economy = null;

    @Override
    public void onEnable() {
        new StickyNoteBukkitLoader(this);
        PalladiumAPI.registerPlugin(this);
        instance = this;
        saveDefaultConfig();
        if (StickyNote.hasPlugin("Vault")) {
            economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        }
        FileManager.create();
        initializeDatabase();
        PalladiumAPI.registerListeners("me.syphix.thepit");
        PalladiumAPI.setTextFormatType("legacy");
        loadItems();
        KitManager.loadKits();
        FileManager.MenusFolder.loadMenusYamlConfig();
        ArenaManager.loadArenas();
        runRandomGoldSpawnTask();
        loadLobbyLocation();
        new PlaceHolderAPIHook().register();
        ActionHandlerProcessor.process();

        new ThePitCommand();
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            database.addPlayerSync(PitPlayerManager.pitPlayer(player.getUniqueId()));
        }
    }

    private void initializeDatabase() {
        ConfigurationSection databaseSection = FileManager.DatabaseFile.get().getConfigurationSection("database");
        if (databaseSection == null) {
            getServer().getPluginManager().disablePlugin(this);
            throw new NullPointerException("Can't find database section in database.yml!");
        }
        database = new PitDatabase(YamlDataManager.YamlDataDatabase.databaseType());

        updateTask = new DatabaseUpdateTask();
        updateTask.runTaskTimer();
    }

    private void loadItems() {
        ConfigurationSection itemsSection = FileManager.ItemsFile.get().getConfigurationSection("items");
        if (itemsSection == null) {
            throw new NullPointerException("Can't find items section in items.yml!");
        }
        CustomItemManager.loadItems(itemsSection);
    }

    public void runRandomGoldSpawnTask() {
        goldSpawnTask = new GoldSpawnTask();
        goldSpawnTask.runTaskTimer();
    }

    public void loadLobbyLocation() {
        if (!getConfig().getConfigurationSection("lobby.location").getKeys(false).isEmpty()) {
            LobbyManager.location(Utils.getLocationFromSection(getConfig().getConfigurationSection("lobby.location")));
        }
    }

    public static ThePit getInstance() {
        return instance;
    }

    public static PitDatabase database() {
        return database;
    }

    public static DatabaseUpdateTask updateTask() {
        return updateTask;
    }

    public static Economy economy() { return economy; }

}
