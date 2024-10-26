package ir.syphix.thepit;

import ir.syphix.palladiumapi.PalladiumAPI;
import ir.syphix.palladiumapi.core.item.CustomItemManager;
import ir.syphix.thepit.command.ThePitCommand;
import ir.syphix.thepit.core.arena.ArenaManager;
import ir.syphix.thepit.core.database.DatabaseUpdateTask;
import ir.syphix.thepit.core.database.PitDatabase;
import ir.syphix.thepit.core.kit.KitManager;
import ir.syphix.thepit.core.player.PitPlayerManager;
import ir.syphix.thepit.core.task.GoldSpawnTask;
import ir.syphix.thepit.data.YamlDataManager;
import ir.syphix.thepit.file.FileManager;
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

    public static ThePit getInstance() {
        return instance;
    }

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
        PalladiumAPI.registerListeners("ir.syphix.thepit");
        PalladiumAPI.setTextFormatType("legacy");
        loadItems();
        KitManager.loadKits();
        FileManager.MenusFolder.loadMenusYamlConfig();
        ArenaManager.loadArenas();

        new ThePitCommand();

        goldSpawnTask = new GoldSpawnTask();
        goldSpawnTask.runTaskTimer();
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

    public static PitDatabase database() {
        return database;
    }

    public static DatabaseUpdateTask updateTask() {
        return updateTask;
    }

    public static Economy economy() { return economy; }

}
