package ir.syphix.thepit.core.perk.perks;

import ir.syphix.palladiumapi.annotation.listener.ListenerHandler;
import ir.syphix.thepit.core.perk.Perk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

@ListenerHandler
public class PerkGoldenHead implements Perk, Listener {
    @Override
    public String id() {
        return "golden_head";
    }

    @Override
    public String displayName() {
        return "Golden Head";
    }

    @Override
    public List<String> description() {
        return List.of("Mammad hosein is taghi");
    }

    @Override
    public double price() {
        return 1000;
    }


    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
    }
}
