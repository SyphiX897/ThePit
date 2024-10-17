package ir.syphix.thepit.core.kit;

import ir.syphix.thepit.annotation.AutoConstruct;
import ir.syphix.thepit.utils.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.sayandev.stickynote.bukkit.utils.ServerVersion;

import java.util.HashMap;
import java.util.List;

@AutoConstruct
public class KitManager {

    private static final HashMap<String, Kit> kits = new HashMap<>();
    private static KitManager instance;

    public static KitManager getInstance() {
        return instance;
    }

    public KitManager() {
        instance = this;
    }

    public static void loadKits(ConfigurationSection kitsSection) {
        for (String kit : kitsSection.getKeys(false)) {
            ConfigurationSection kitSection = kitsSection.getConfigurationSection(kit);
            kits.put(kitSection.getName(), Kit.fromConfig(kitSection));
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
    }

    public static boolean exist(String id) {
        return kits.get(id) != null;
    }

    public static void giveKit(Player player, String kitId) {
        if (!KitManager.exist(kitId)) {
            TextUtils.sendMessage(player, ""); //TODO: kit is not exist message
            return;
        }
        Kit kit = kit(kitId);

        for (int i = 0; i <= 40; i++) {
            if (kit.inventory().getItem(i) == null) continue;
            switch (i) {
                case 36 -> player.getInventory().setHelmet(kit.inventory().getItem(i));
                case 37 -> player.getInventory().setLeggings(kit.inventory().getItem(i));
                case 38 -> player.getInventory().setChestplate(kit.inventory().getItem(i));
                case 39 -> player.getInventory().setBoots(kit.inventory().getItem(i));
                case 40 -> {
                    if (ServerVersion.supports(9)) break;
                    player.getInventory().setItemInOffHand(kit.inventory().getItem(i));
                }
            }

            player.getInventory().setItem(i, kit.inventory().getItem(i));
        }

        if (kit.potionEffects().isEmpty()) return;

        player.addPotionEffects(kit.potionEffects());
    }

}
