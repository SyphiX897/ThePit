package ir.syphix.thepit.utils;

import ir.syphix.thepit.data.YamlDataManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.sayandev.stickynote.bukkit.StickyNote;
import org.sayandev.stickynote.bukkit.nms.NMSUtils;

public class TextUtils {

    static MiniMessage miniMessage = MiniMessage.builder()
            .tags(
                    TagResolver.resolver(
                            TagResolver.standard()
                    )
            )
            .build();

    static BukkitAudiences bukkitAudiences = BukkitAudiences.create(StickyNote.plugin());

    public static void sendMessage(CommandSender sender, String content, TagResolver... placeholders) {
        bukkitAudiences.sender(sender).sendMessage(Component.empty().decoration(TextDecoration.ITALIC, false).append(miniMessage.deserialize(content, placeholders)));
    }

    public static String colorify(String content) {
        return ChatColor.translateAlternateColorCodes('&', content);
    }

}
