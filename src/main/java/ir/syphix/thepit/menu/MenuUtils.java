package ir.syphix.thepit.menu;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import ir.syphix.thepit.data.YamlDataManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuUtils {

    public static GuiItem fillerGlass(Material material) {
        ItemStack fillerGlass = new ItemStack(material);
        ItemMeta itemMeta = fillerGlass.getItemMeta();
        itemMeta.setDisplayName(" ");
        fillerGlass.setItemMeta(itemMeta);

        GuiItem guiItem = ItemBuilder.from(fillerGlass).asGuiItem();
        guiItem.setAction(event -> event.setCancelled(true));

        return guiItem;
    }
}
