package ir.syphix.thepit.file;

import ir.syphix.palladiumapi.utils.YamlConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.sayandev.stickynote.bukkit.StickyNote;

public class FileManager {

    public static void create() {
        DatabaseFile.add();
        MessageFile.add();
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

    public static class MessageFile {
        private static YamlConfig messageYamlConfig;

        public static void add() {
            messageYamlConfig = new YamlConfig(StickyNote.pluginDirectory(), "messages.yml");
            messageYamlConfig.saveDefaultConfig();
        }

        public static FileConfiguration get() {
            return messageYamlConfig.getConfig();
        }

        public static void reload() {
            messageYamlConfig.reloadConfig();
        }

        public static void save() {
            messageYamlConfig.saveConfig();
        }
    }

}
