package me.syphix.thepit.menu;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.syphix.thepit.core.kit.Kit;
import me.syphix.thepit.data.YamlDataManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.sayandev.stickynote.bukkit.utils.ServerVersion;

public class MenuKitView extends Gui {

    public MenuKitView(Player player, Kit kit) {
        super(6, YamlDataManager.YamlDataMenus.kitViewTitle(kit));

        Inventory kitInventory = kit.inventory();

        for (int i = 0; i < 54; i++) {
            if (i < 9) {
                if (kitInventory.getItem(i) == null) continue;
                setItem((i + 27), canceledGuiItem(kitInventory.getItem(i)));
                continue;
            }

            if (i < 36) {
                if (kitInventory.getItem(i) == null) continue;
                setItem((i - 9), canceledGuiItem(kitInventory.getItem(i)));
                continue;
            }

            if (i < 41) {
                setItem(i, MenuUtils.fillerGlass(YamlDataManager.YamlDataMenus.kitViewFillerGlass()));
                if (i == 40) {
                    if (!ServerVersion.supports(9)) {
                        setItem(i + 9, MenuUtils.fillerGlass(YamlDataManager.YamlDataMenus.kitViewFillerGlass()));
                        continue;
                    }
                    if (kitInventory.getItem(i) == null) continue;
                    setItem(i + 9, canceledGuiItem(kitInventory.getItem(i)));
                    continue;
                }
                if (kitInventory.getItem(i) == null) continue;
                setItem(i + 9, canceledGuiItem(kitInventory.getItem(i)));
                continue;
            }

            if (i < 45 || i > 49) {
                setItem(i, MenuUtils.fillerGlass(YamlDataManager.YamlDataMenus.kitViewFillerGlass()));
            }
        }

        update();
        open(player);
    }

    private GuiItem canceledGuiItem(ItemStack itemStack) {
        GuiItem guiItem = ItemBuilder.from(itemStack).asGuiItem();
        guiItem.setAction(event -> event.setCancelled(true));

        return guiItem;
    }

}
