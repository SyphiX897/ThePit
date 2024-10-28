package me.syphix.thepit.file;

import ir.syphix.palladiumapi.utils.YamlConfig;
import me.syphix.thepit.core.arena.Arena;
import org.bukkit.configuration.file.FileConfiguration;
import org.sayandev.stickynote.bukkit.StickyNote;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FileManager {

    public static void create() {
        DatabaseFile.add();
        MessagesFile.add();
        ItemsFile.add();
        KitsFile.add();
    }


    public static class DatabaseFile {
        private static YamlConfig databaseYamlConfig;

        public static void add() {
            databaseYamlConfig = new YamlConfig(StickyNote.pluginDirectory(), "database.yml");
            databaseYamlConfig.saveDefaultConfig();
        }

        public static FileConfiguration get() {
            return databaseYamlConfig.getConfig();
        }

        public static void reload() {
            databaseYamlConfig.reloadConfig();
        }

        public static void save() {
            databaseYamlConfig.saveConfig();
        }
    }

    public static class MessagesFile {
        private static YamlConfig messagesYamlConfig;

        public static void add() {
            messagesYamlConfig = new YamlConfig(StickyNote.pluginDirectory(), "messages.yml");
            messagesYamlConfig.saveDefaultConfig();
        }

        public static FileConfiguration get() {
            return messagesYamlConfig.getConfig();
        }

        public static void reload() {
            messagesYamlConfig.reloadConfig();
        }

        public static void save() {
            messagesYamlConfig.saveConfig();
        }
    }

    public static class ItemsFile {
        private static YamlConfig itemsYamlConfig;

        public static void add() {
            itemsYamlConfig = new YamlConfig(StickyNote.pluginDirectory(), "items.yml");
            itemsYamlConfig.saveDefaultConfig();
        }

        public static FileConfiguration get() {
            return itemsYamlConfig.getConfig();
        }

        public static void reload() {
            itemsYamlConfig.reloadConfig();
        }

        public static void save() {
            itemsYamlConfig.saveConfig();
        }
    }

    public static class KitsFile {
        private static YamlConfig kitsYamlConfig;

        public static void add() {
            kitsYamlConfig = new YamlConfig(StickyNote.pluginDirectory(), "kits.yml");
            kitsYamlConfig.saveDefaultConfig();
        }

        public static FileConfiguration get() {
            return kitsYamlConfig.getConfig();
        }

        public static void reload() {
            kitsYamlConfig.reloadConfig();
        }

        public static void save() {
            kitsYamlConfig.saveConfig();
        }
    }

    public static class ArenasFolder {

        public static File arenasFolder() {
            File folder = new File(StickyNote.pluginDirectory(), "arenas");

            if (!folder.exists()) {
                try {
                    folder.mkdir();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            return folder;
        }

        public static List<YamlConfig> arenasYamlConfig() {
            List<YamlConfig> yamlConfigs = new ArrayList<>();
            for (String arenaFileName : Arrays.stream(arenasFolder().listFiles()).map(File::getName).toList()) {
                yamlConfigs.add(new YamlConfig(StickyNote.pluginDirectory(), "arenas/" + arenaFileName));
            }

            return yamlConfigs;
        }

        public static YamlConfig createArenaFile(Arena arena) {
            return new YamlConfig(arenasFolder(), arena.id() + ".yml");
        }
    }


    public static class MenusFolder {
        public static HashMap<String, YamlConfig> menusYamlConfig = new HashMap<>();

        public static void loadMenusYamlConfig() {
            List<String> menusNameList = List.of("kit_view");
            for (String fileName : menusNameList) {
                menusYamlConfig.put(fileName, new YamlConfig(StickyNote.pluginDirectory(), "menus/" + fileName + ".yml"));
            }
        }

        public static YamlConfig yamlConfig(String menuName) {
            return menusYamlConfig.get(menuName);
        }

        public static FileConfiguration config(String menuName) {
            return yamlConfig(menuName).getConfig();
        }

    }

}
