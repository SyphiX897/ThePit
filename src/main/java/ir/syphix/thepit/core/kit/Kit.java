package ir.syphix.thepit.core.kit;

import ir.syphix.palladiumapi.item.CustomItem;
import ir.syphix.palladiumapi.item.CustomItemManager;
import net.minecraft.util.datafix.fixes.ItemIdFix;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.units.qual.K;
import org.sayandev.stickynote.bukkit.StickyNote;
import org.sayandev.stickynote.bukkit.utils.ServerVersion;

import java.util.ArrayList;
import java.util.List;

public class Kit {

    private String id;
    private final Inventory inventory;
    private List<PotionEffect> effects = new ArrayList<>();

    public Kit(String id, Inventory inventory, List<PotionEffect> effects) {
        this.id = id;
        this.inventory = inventory;
        this.effects = effects;
    }

    public Kit(String id, Inventory inventory) {
        this.id = id;
        this.inventory = inventory;
        this.effects = new ArrayList<>();
    }

    public String id() {
        return id;
    }

    public Inventory inventory() {
        return inventory;
    }

    public List<PotionEffect> potionEffects() {
        return effects;
    }

    public void rename(String newName) {
        this.id = newName;
    }

    public static Kit fromConfig(ConfigurationSection kitSection) {
        Inventory kitInventory = Bukkit.createInventory(null, 45);

        ConfigurationSection slotsSection = kitSection.getConfigurationSection("slots");
        if (slotsSection == null) {
            throw new NullPointerException(String.format("Can't find slots section in kits.yml for kit: %s", kitSection.getName()));
        }

        for (int i = 0; i <= 40; i++) {
            if (!slotsSection.contains(String.valueOf(i))) continue;
            ConfigurationSection itemSection = slotsSection.getConfigurationSection(String.valueOf(i));

            String itemId = itemSection.getString("id");
            CustomItem customItem = CustomItemManager.getItemById(itemId);
            if (customItem == null) {
                StickyNote.warn(String.format("Invalid item: %s in %s kit", itemId, kitSection.getName()));
                continue;
            }

            kitInventory.setItem(i, customItem.getItemStack().clone());
        }

        if (!kitSection.contains("effects")) {
            return new Kit(kitSection.getName(), kitInventory);
        }

        ConfigurationSection effectsSection = kitSection.getConfigurationSection("effects");
        List<PotionEffect> potionEffects = new ArrayList<>();

        for (String effect : effectsSection.getKeys(false)) {
            ConfigurationSection effectSection = effectsSection.getConfigurationSection(effect);
            PotionEffectType effectType = PotionEffectType.getByName(effect);
            if (effectType == null) {
                StickyNote.warn(String.format("Invalid effect type: %s in %s kit", effect, kitSection.getName()));
                continue;
            }
            potionEffects.add(new PotionEffect(effectType, effectSection.getInt("duration"), effectSection.getInt("level")));
        }

        return new Kit(kitSection.getName(), kitInventory, potionEffects);
    }
}
