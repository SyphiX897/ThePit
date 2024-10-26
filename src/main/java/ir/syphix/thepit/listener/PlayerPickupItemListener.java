package ir.syphix.thepit.listener;

import de.tr7zw.nbtapi.NBT;
import ir.syphix.palladiumapi.annotation.listener.ListenerHandler;
import ir.syphix.thepit.core.economy.ThePitEconomy;
import ir.syphix.thepit.data.YamlDataManager;
import ir.syphix.thepit.utils.TextUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

@ListenerHandler
public class PlayerPickupItemListener implements Listener {

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        String tag = NBT.get(event.getItem().getItemStack(), nbt -> (String) nbt.getString("thepit:random_gold"));
        if (tag == null || tag.isBlank()) return;

        event.setCancelled(true);
        ThePitEconomy.deposit(event.getPlayer().getUniqueId(), (YamlDataManager.YamlDataConfig.randomGoldAmount() * event.getItem().getItemStack().getAmount()));
        TextUtils.sendMessage(event.getPlayer(), "5$"); //TODO: you have received gold msg
        event.getItem().remove();
    }
}
