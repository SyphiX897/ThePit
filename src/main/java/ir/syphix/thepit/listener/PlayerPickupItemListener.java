package ir.syphix.thepit.listener;

import de.tr7zw.nbtapi.NBT;
import ir.syphix.palladiumapi.PalladiumAPI;
import ir.syphix.palladiumapi.annotation.listener.ListenerHandler;
import ir.syphix.palladiumapi.item.CustomItemManager;
import ir.syphix.thepit.core.economy.ThePitEconomy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.sayandev.stickynote.bukkit.StickyNote;

@ListenerHandler
public class PlayerPickupItemListener implements Listener {

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        String tag = NBT.get(event.getItem().getItemStack(), nbt -> (String) nbt.getString("thepit:random_gold"));
        event.getPlayer().getInventory().addItem(CustomItemManager.getItemById("random_gold").getItemStack());
        if (tag == null || tag.isBlank()) return;

        event.setCancelled(true);
        event.getItem().remove();
        ThePitEconomy.deposit(event.getPlayer().getUniqueId(), 5);
    }
}
