package me.syphix.thepit.data;

import me.syphix.thepit.core.database.DatabaseType;
import me.syphix.thepit.core.economy.EconomyType;
import me.syphix.thepit.core.kit.Kit;
import me.syphix.thepit.file.FileManager;
import me.syphix.thepit.utils.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.sayandev.stickynote.bukkit.StickyNote;

import java.util.List;

public class YamlDataManager extends FileManager {

    //TODO: inner class for each yaml file

    public static class YamlDataConfig {
        private static final FileConfiguration config = StickyNote.plugin().getConfig();
        private static final ConfigurationSection arenaSection = config.getConfigurationSection("arena");

        public static EconomyType economyType() {
            return EconomyType.valueOf(config.getString("economy.type").toUpperCase());
        }

        public static double startingBalance() {
            return config.getDouble("economy.starting_balance");
        }

        public static String randomGoldCustomItemId() {
            return arenaSection.getString("random_gold_spawn.item");
        }

        public static double randomGoldAmount() {
            return arenaSection.getDouble("random_gold_spawn.amount");
        }

        public static int randomGoldPeriod() {
            return arenaSection.getInt("random_gold_spawn.period");
        }

        public static boolean randomGoldAllLocation() {
            return arenaSection.getBoolean("random_gold_spawn.all_locations");
        }

        public static boolean randomGoldAllowStack() {
            return arenaSection.getBoolean("random_gold_spawn.allow_stack");
        }

        public static boolean isRandomGoldHologramEnable() {
            return arenaSection.getBoolean("random_gold_spawn.hologram.enabled");
        }

        public static String randomGoldCustomName() {
            return arenaSection.getString("random_gold_spawn.hologram.text");
        }

        public static List<?> arenaJoinActions() {
            return arenaSection.getList("on_join.actions");
        }

        public static boolean isForceArenaJoinEnable() {
            return config.getBoolean("force_arena_join.enabled");
        }

        public static String forceArenaJoinArena() {
            return config.getString("force_arena_join.arena");
        }

        public static List<?> lobbyJoinActions() {
            return config.getList("lobby.on_join.actions");
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
        public static int port() {
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
        public static boolean ssl() {
            return mysqlSection.getBoolean("ssl");
        }
        public static int poolingSize() {
            return mysqlSection.getInt("pooling_size");
        }

    }

    public static class YamlDataMenus {
        public static String kitViewTitle(Kit kit) {
            return TextUtils.colorify(MenusFolder.config("kit_view").getString("title").replace("<kit>", TextUtils.capitalize(kit.id())));
        }

        public static String kitViewFillerGlass() {

            return MenusFolder.config("kit_view").getString("filler_glass");
        }

    }



}
