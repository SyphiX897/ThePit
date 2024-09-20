package ir.syphix.thepit;

import com.mysql.cj.exceptions.WrongArgumentException;
import ir.syphix.palladiumapi.PalladiumAPI;
import ir.syphix.thepit.command.ThePitCommand;
import ir.syphix.thepit.database.DatabaseType;
import ir.syphix.thepit.database.PitDatabase;
import ir.syphix.thepit.file.FileManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.sayandev.stickynote.bukkit.StickyNote;
import org.sayandev.stickynote.loader.bukkit.StickyNoteBukkitLoader;

import java.security.InvalidKeyException;

public final class ThePit extends JavaPlugin {

    private static PitDatabase database;

    @Override
    public void onEnable() {
        new StickyNoteBukkitLoader(this);
        PalladiumAPI.registerPlugin(this);
        PalladiumAPI.registerListeners("ir.syphix.thepit");
        FileManager.create();
        initializeDatabase();

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

        String databaseType = databaseSection.getString("type");
        if (databaseType.equalsIgnoreCase("sqlite")) {
            database = new PitDatabase(DatabaseType.SQLITE);

        } else if (databaseType.equalsIgnoreCase("mysql")) {
            database = new PitDatabase(DatabaseType.MYSQL);

        } else {
            throw new WrongArgumentException("Invalid database type!");
        }
    }


}
