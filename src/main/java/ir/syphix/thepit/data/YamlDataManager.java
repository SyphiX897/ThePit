package ir.syphix.thepit.data;

import ir.syphix.thepit.annotation.AutoConstruct;
import ir.syphix.thepit.database.DatabaseType;
import ir.syphix.thepit.file.FileManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.sayandev.stickynote.bukkit.StickyNote;
import org.sayandev.stickynote.core.database.Database;

@AutoConstruct
public class YamlDataManager extends FileManager {

    private static YamlDataManager instance;

    public static YamlDataManager getInstance() {
        return instance;
    }

    public YamlDataManager() {
        instance = this;
    }

    //TODO: inner class for each yaml file

    public static class YamlDataConfig {
        private static final FileConfiguration config = StickyNote.plugin().getConfig();

        public static String textFormat() {
            return config.getString("text_format");
        }

        public static double startingGold() {
            return config.getDouble("starting_gold");
        }
    }

    public static class YamlDataDatabase {
        private static final ConfigurationSection databaseSection = DatabaseFile.get().getConfigurationSection("database");

        public static DatabaseType databaseType() {
            if (databaseSection == null) {
                throw new NullPointerException("Can't find database section in database.yml!");
            }

            switch (databaseSection.getString("type").toLowerCase()) {
                case ("mysql") -> {
                    return DatabaseType.MYSQL;
                }
                case ("sqlite") -> {
                    return DatabaseType.SQLITE;
                }
                default -> {
                    throw new IllegalArgumentException("Database type is not valid!");
                }
            }
        }

        public static class MysqlData {
            public static String address() {
                return databaseSection.getString("address");
            }
            public static Integer port() {
                return databaseSection.getInt("port");
            }
            public static String database() {
                return databaseSection.getString("database");
            }
            public static String username() {
                return databaseSection.getString("username");
            }
            public static String password() {
                return databaseSection.getString("password");
            }
            public static Boolean ssl() {
                return databaseSection.getBoolean("ssl");
            }
            public static Integer poolingSize() {
                return databaseSection.getInt("pooling_size");
            }
        }
    }

    public static class YamlDataMessages {

    }



}
