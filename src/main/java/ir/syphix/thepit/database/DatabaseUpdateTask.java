package ir.syphix.thepit.database;

import ir.syphix.thepit.ThePit;
import ir.syphix.thepit.core.player.PitPlayer;
import ir.syphix.thepit.core.player.PitPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.sayandev.stickynote.bukkit.StickyNote;

public class DatabaseUpdateTask extends BukkitRunnable {


    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PitPlayer pitPlayer = PitPlayerManager.pitPlayer(player.getUniqueId());
            if (pitPlayer == null) continue;

            pitPlayer.updateEnderChest();
            ThePit.database().addPlayerAsync(pitPlayer);
        }
    }

    //5m delay and period
    public void runTaskTimer() {
        this.runTaskTimer(StickyNote.plugin(), 6000, 6000);
    }
}
