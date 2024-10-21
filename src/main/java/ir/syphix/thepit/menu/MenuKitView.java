package ir.syphix.thepit.menu;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import ir.syphix.thepit.core.kit.Kit;
import ir.syphix.thepit.data.YamlDataManager;
import ir.syphix.thepit.file.FileManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.sayandev.stickynote.bukkit.utils.ServerVersion;

public class MenuKitView extends Gui {

    public MenuKitView(Player player, Kit kit) {
        super(6, YamlDataManager.YamlDataMenus.kitViewTitle(kit));

        Inventory kitInventory = kit.inventory();

        for (int i = 0; i < 54; i++) {
            if (i < 9) {
                if (kitInventory.getItem(i) == null) continue;
                setItem((i + 27), ItemBuilder.from(kitInventory.getItem(i)).asGuiItem());
                continue;
            }

            if (i < 36) {
                if (kitInventory.getItem(i) == null) continue;
                setItem((i - 9), ItemBuilder.from(kitInventory.getItem(i)).asGuiItem());
                continue;
            }

            if (i < 41) {
                setItem(i, ItemBuilder.from(MenuUtils.fillerGlass(YamlDataManager.YamlDataMenus.kitViewFillerGlass())).asGuiItem());
                if (i == 40) {
                    if (!ServerVersion.supports(9)) {
                        setItem(i + 9, ItemBuilder.from(MenuUtils.fillerGlass(YamlDataManager.YamlDataMenus.kitViewFillerGlass())).asGuiItem());
                        continue;
                    }
                    if (kitInventory.getItem(i) == null) continue;
                    setItem(i + 9, ItemBuilder.from(kitInventory.getItem(i)).asGuiItem());
                    continue;
                }
                if (kitInventory.getItem(i) == null) continue;
                setItem(i + 9, ItemBuilder.from(kitInventory.getItem(i)).asGuiItem());
                continue;
            }

            setItem(i, ItemBuilder.from(MenuUtils.fillerGlass(YamlDataManager.YamlDataMenus.kitViewFillerGlass())).asGuiItem());
        }

        update();
        open(player);
    }

}
