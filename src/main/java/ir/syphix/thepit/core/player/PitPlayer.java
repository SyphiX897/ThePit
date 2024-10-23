package ir.syphix.thepit.core.player;

import ir.syphix.thepit.core.arena.Arena;
import ir.syphix.thepit.core.player.stats.CombatStats;
import ir.syphix.thepit.core.player.stats.MainStats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.sayandev.stickynote.bukkit.StickyNote;

import java.util.UUID;

public class PitPlayer {

    private final Player player;
    private final MainStats mainStats;
    private final CombatStats combatStats;
    private Arena arena;


    public PitPlayer(Player player, MainStats mainStats, CombatStats combatStats) {
        this.player = player;
        this.mainStats = mainStats;
        this.combatStats = combatStats;

    }

    public Player player() {
        return player;
    }

    public UUID uniqueId() {
        return player.getUniqueId();
    }

    public MainStats mainStats() {
        return mainStats;
    }

    public CombatStats combatStats() {
        return combatStats;
    }

    public Arena arena() {
        return arena;
    }

    public void arena(Arena arena) {
        this.arena = arena;
    }

    public void updateEnderChest() {
        mainStats.enderChestItems().clear();
        Inventory enderChest = player.getEnderChest();

        for (int i = 0; i <= 26; i++) {
            if (enderChest.getItem(i) == null) {
                mainStats.enderChestItems().add(i, null);
                continue;
            }

            mainStats.enderChestItems().add(i, enderChest.getItem(i));
        }
    }

    public void setEnderChestItems() {
        for (int i = 0; i <= 26; i++) {
            if (mainStats.enderChestItems().isEmpty()) break;
            if (mainStats.enderChestItems().get(i) == null) {
                player.getEnderChest().setItem(i, null);
                continue;
            }
            player.getEnderChest().setItem(i, mainStats.enderChestItems().get(i));
        }
    }

}

