package ir.syphix.thepit.data;

import ir.syphix.thepit.core.database.DatabaseType;
import ir.syphix.thepit.core.economy.EconomyType;
import ir.syphix.thepit.core.kit.Kit;
import ir.syphix.thepit.file.FileManager;
import ir.syphix.thepit.utils.TextUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.sayandev.stickynote.bukkit.StickyNote;

public class YamlDataManager extends FileManager {

    //TODO: inner class for each yaml file

    public static class YamlDataConfig {
        private static final FileConfiguration config = StickyNote.plugin().getConfig();
        private static final ConfigurationSection arenaSection = config.getConfigurationSection("arena");

        public static EconomyType economyType() {
            return EconomyType.valueOf(config.getString("economy.type"));
        }

        public static Double startingBalance() {
            return config.getDouble("economy.starting_balance");
        }

        public static Integer heldItemSlot() {
            return arenaSection.getInt("on_join.held_item_slot");
        }

        public static Boolean arenaClearInventory() {
            return arenaSection.getBoolean("on_join.clear_inventory");
        }

        public static Boolean arenaClearEffects() {
            return arenaSection.getBoolean("on_join.clear_effects");
        }

        public static Double arenaHealth() {
            return arenaSection.getDouble("on_join.health");
        }

        public static Integer arenaFoodLevel() {
            return arenaSection.getInt("on_join.food_level");
        }

        public static Integer arenaSaturation() {
            return arenaSection.getInt("on_join.saturation");
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


        private static final ConfigurationSection mysqlSection = databaseSection.getConfigurationSection("mysql");

        public static String address() {
            return mysqlSection.getString("address");
        }
        public static Integer port() {
            return mysqlSection.getInt("port");
        }
        public static String database() {
            return mysqlSection.getString("database");
        }
        public static String username() {
            return mysqlSection.getString("username");
        }
        public static String password() {
            return mysqlSection.getString("password");
        }
        public static Boolean ssl() {
            return mysqlSection.getBoolean("ssl");
        }
        public static Integer poolingSize() {
            return mysqlSection.getInt("pooling_size");
        }

    }

    public static class YamlDataMenus {
        public static String kitViewTitle(Kit kit) {
            return TextUtils.colorify(MenusFolder.config("kit_view").getString("title").replace("<kit>", TextUtils.capitalize(kit.id())));
        }

        public static Material kitViewFillerGlass() {
            return Material.valueOf(MenusFolder.config("kit_view").getString("filler_glass"));
        }
    }



}
