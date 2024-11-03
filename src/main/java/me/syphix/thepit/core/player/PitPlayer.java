package me.syphix.thepit.core.player;

import me.syphix.thepit.core.arena.Arena;
import me.syphix.thepit.core.player.combat.Combat;
import me.syphix.thepit.core.player.stats.CombatStats;
import me.syphix.thepit.core.player.stats.MainStats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PitPlayer {

    private final Player player;
    private final MainStats mainStats;
    private final CombatStats combatStats;
    private final Combat combat;
    private Arena arena;


    public PitPlayer(Player player, MainStats mainStats, CombatStats combatStats, Combat combat) {
        this.player = player;
        this.mainStats = mainStats;
        this.combatStats = combatStats;
        this.combat = combat;

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

    public Combat combat() { return combat; }

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
            if (enderChest.getItem(i) == null || enderChest.getItem(i).getType() == Material.AIR) {
                mainStats.enderChestItems().add(i, new ItemStack(Material.AIR));
                continue;
            }

            mainStats.enderChestItems().add(i, enderChest.getItem(i));
        }
    }

    public void setEnderChestItems() {

        for (int i = 0; i <= 26; i++) {
            if (mainStats.enderChestItems().isEmpty()) break;
            if (mainStats.enderChestItems().get(i) == null || mainStats.enderChestItems().get(i).getType() == Material.AIR) {
                player.getEnderChest().setItem(i, new ItemStack(Material.AIR));
                continue;
            }
            player.getEnderChest().setItem(i, mainStats.enderChestItems().get(i));
        }

    }

}

