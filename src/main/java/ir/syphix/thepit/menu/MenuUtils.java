package ir.syphix.thepit.menu;

import ir.syphix.thepit.data.YamlDataManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuUtils {

    public static ItemStack fillerGlass(Material material) {
        ItemStack fillerGlass = new ItemStack(material);
        ItemMeta itemMeta = fillerGlass.getItemMeta();
        itemMeta.setDisplayName(" ");
        fillerGlass.setItemMeta(itemMeta);

        return fillerGlass;
    }
}
