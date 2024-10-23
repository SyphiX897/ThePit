package ir.syphix.thepit.core.kit;

import ir.syphix.thepit.file.FileManager;
import ir.syphix.thepit.utils.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.sayandev.stickynote.bukkit.utils.ServerVersion;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class KitManager {

    private static final HashMap<String, Kit> kits = new HashMap<>();

    private KitManager() {
    }

    public static void loadKits() {
        ConfigurationSection kitsSection = FileManager.KitsFile.get().getConfigurationSection("kits");
        for (String kit : kitsSection.getKeys(false)) {
            ConfigurationSection kitSection = kitsSection.getConfigurationSection(kit);
            kits.put(kitSection.getName(), Kit.fromConfigFile(kitSection));
        }
    }

    public static Kit kit(String id) {
        return kits.get(id);
    }

    public static List<Kit> kits() {
        return kits.values().stream().toList();
    }

    public static void add(Kit kit) {
        kits.put(kit.id(), kit);
    }

    public static void remove(String id) {
        kits.remove(id);
        FileManager.KitsFile.get().getConfigurationSection("kits").set(id, null);
        FileManager.KitsFile.save();
    }

    public static boolean exist(String id) {
        return kits.get(id) != null;
    }

    public static List<String> kitsNameList() {
        return KitManager.kits().stream().map(Kit::id).toList();
    }

    public static void giveKit(Player player, String kitId) {
        if (!KitManager.exist(kitId)) {
            TextUtils.sendMessage(player, ""); //TODO: kit does not exist message
            return;
        }
        Kit kit = kit(kitId);

        for (int i = 0; i <= 40; i++) {
            if (kit.inventory().getItem(i) == null) continue;
            switch (i) {
                case 36 -> player.getInventory().setBoots(kit.inventory().getItem(i));
                case 37 -> player.getInventory().setLeggings(kit.inventory().getItem(i));
                case 38 -> player.getInventory().setChestplate(kit.inventory().getItem(i));
                case 39 -> player.getInventory().setHelmet(kit.inventory().getItem(i));
                case 40 -> {
                    if (!ServerVersion.supports(9)) break;
                    player.getInventory().setItemInOffHand(kit.inventory().getItem(i));
                }
            }

            player.getInventory().setItem(i, kit.inventory().getItem(i));
        }

        if (kit.potionEffects().isEmpty()) return;

        player.addPotionEffects(kit.potionEffects());
    }

}
