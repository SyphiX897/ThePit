package me.syphix.thepit.hook;

import me.syphix.thepit.core.player.PitPlayer;
import me.syphix.thepit.core.player.PitPlayerManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceHolderAPIHook extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "";
    }

    @Override
    public @NotNull String getAuthor() {
        return "";
    }

    @Override
    public @NotNull String getVersion() {
        return "";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        PitPlayer pitPlayer = PitPlayerManager.pitPlayer(player.getUniqueId());
        if (pitPlayer == null) return "";
        switch (params.toLowerCase()) {
            case "bounty" -> {
                return toString(pitPlayer.combatStats().bounty());
            }
            case "kills" -> {
                return toString(pitPlayer.combatStats().kills());
            }
            case "deaths" -> {
                return toString(pitPlayer.combatStats().deaths());
            }
            case "assists" -> {
                return toString(pitPlayer.combatStats().assists());
            }
            case "kill_streak" -> {
                return toString(pitPlayer.combatStats().killStreak());
            }
            case "highest_kill_streak" -> {
                return toString(pitPlayer.combatStats().highestKillSteak());
            }
            case "sword_hits" -> {
                return toString(pitPlayer.combatStats().swordHits());
            }
            case "arrow_hits" -> {
                return toString(pitPlayer.combatStats().arrowHits());
            }
            case "arrow_shoots" -> {
                return toString(pitPlayer.combatStats().arrowShoots());
            }
            case "damage_dealt" -> {
                return toString(pitPlayer.combatStats().damageDealt());
            }
            case "melee_damage_dealt" -> {
                return toString(pitPlayer.combatStats().meleeDamageDealt());
            }
            case "arrow_damage_dealt" -> {
                return toString(pitPlayer.combatStats().arrowDamageDealt());
            }
            case "damage_taken" -> {
                return toString(pitPlayer.combatStats().damageTaken());
            }
            case "melee_damage_taken" -> {
                return toString(pitPlayer.combatStats().meleeDamageTaken());
            }
            case "arrow_damage_taken" -> {
                return toString(pitPlayer.combatStats().arrowDamageTaken());
            }
            case "block_placed" -> {
                return toString(pitPlayer.combatStats().blockPlaced());
            }
            case "lava_bucket_placed" -> {
                return toString(pitPlayer.combatStats().lavaBucketPlaced());
            }
            case "gold" -> {
                return toString(pitPlayer.mainStats().gold());
            }
            case "level" -> {
                return toString(pitPlayer.mainStats().level());
            }
            case "xp" -> {
                return toString(pitPlayer.mainStats().xp());
            }
            case "prestige" -> {
                return toString(pitPlayer.mainStats().prestige());
            }
        }
        return null;
    }

    public String toString(Object object) {
        return String.valueOf(object);
    }

}
