package ir.syphix.thepit.item;

import ir.syphix.palladiumapi.annotation.item.ItemHandler;
import ir.syphix.palladiumapi.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@ItemHandler
public class RandomGold extends CustomItem {
    public RandomGold() {
        super("random_gold", new ItemStack(Material.GOLD_INGOT));
    }
}
