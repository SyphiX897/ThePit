package ir.syphix.thepit.hook;

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
//        if (params.equalsIgnoreCase("tag")) {
//            TagPlayer tagPlayer = Tags.cache.get(player.getUniqueId());
//
//            if (tagPlayer == null) return "";
//            if (tagPlayer.getSelectedTagId().equalsIgnoreCase("")) return "";
//            return TextUtils.colorify(tagPlayer.getSelectedTag().getSuffix());
//        }
        return null;
    }
}
