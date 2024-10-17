package ir.syphix.thepit.file;

import ir.syphix.palladiumapi.utils.YamlConfig;
import ir.syphix.thepit.annotation.AutoConstruct;
import org.bukkit.configuration.file.FileConfiguration;
import org.sayandev.stickynote.bukkit.StickyNote;

import java.io.File;
import java.util.Arrays;

@AutoConstruct
public class FileManager {

    private static FileManager instance;

    public static FileManager getInstance() {
        return instance;
    }

    public FileManager() {
        instance = this;
    }

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

    public static class Arena {

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

        public static void loadArenas() {
            if (arenasFolder().listFiles() == null) return;
            for (String file : Arrays.stream(arenasFolder().listFiles()).map(File::getName).toList()) {

            }
        }
    }

}
