package ir.syphix.thepit;

import ir.syphix.palladiumapi.PalladiumAPI;
import ir.syphix.palladiumapi.item.CustomItemManager;
import ir.syphix.thepit.annotation.AutoConstructProcessor;
import ir.syphix.thepit.command.ThePitCommand;
import ir.syphix.thepit.core.kit.KitManager;
import ir.syphix.thepit.data.YamlDataManager;
import ir.syphix.thepit.database.DatabaseType;
import ir.syphix.thepit.database.DatabaseUpdateTask;
import ir.syphix.thepit.database.PitDatabase;
import ir.syphix.thepit.file.FileManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.sayandev.stickynote.loader.bukkit.StickyNoteBukkitLoader;

public final class ThePit extends JavaPlugin {

    private static PitDatabase database;
    private static DatabaseUpdateTask updateTask;
    private static ThePit instance;

    public static ThePit getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        new StickyNoteBukkitLoader(this);
        PalladiumAPI.registerPlugin(this);
        AutoConstructProcessor.process();
        instance = this;
        saveDefaultConfig();
        FileManager.create();
        initializeDatabase();
        PalladiumAPI.registerListeners("ir.syphix.thepit");
        PalladiumAPI.setTextFormatType(YamlDataManager.YamlDataConfig.textFormat());
        loadItems();
        KitManager.loadKits(FileManager.KitsFile.get().getConfigurationSection("kits"));

        new ThePitCommand("thepit");
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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

}
