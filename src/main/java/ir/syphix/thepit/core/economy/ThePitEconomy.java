package ir.syphix.thepit.core.economy;

import ir.syphix.thepit.ThePit;
import ir.syphix.thepit.core.player.PitPlayer;
import ir.syphix.thepit.core.player.PitPlayerManager;
import ir.syphix.thepit.data.YamlDataManager;
import org.bukkit.Bukkit;

import java.util.UUID;

public class ThePitEconomy {
    public static EconomyType economyType = YamlDataManager.YamlDataConfig.economyType();

    public static void withdraw(UUID uuid, double amount) {
        if (economyType == EconomyType.THEPIT) {
            PitPlayer pitPlayer = PitPlayerManager.pitPlayer(uuid);
            double newBalance = pitPlayer.mainStats().gold() - amount;
            pitPlayer.mainStats().setGold(newBalance);
        } else {
            ThePit.economy().withdrawPlayer(Bukkit.getPlayer(uuid), amount);
        }
    }

    public static void deposit(UUID uuid, double amount) {
        if (economyType == EconomyType.THEPIT) {
            PitPlayer pitPlayer = PitPlayerManager.pitPlayer(uuid);
            double newBalance = pitPlayer.mainStats().gold() + amount;
            pitPlayer.mainStats().setGold(newBalance);
        } else {
            ThePit.economy().depositPlayer(Bukkit.getPlayer(uuid), amount);
        }
    }

    public static double balance(UUID uuid) {
        if (economyType == EconomyType.THEPIT) {
            return PitPlayerManager.pitPlayer(uuid).mainStats().gold();
        } else {
            return ThePit.economy().getBalance(Bukkit.getPlayer(uuid));
        }
    }
}
