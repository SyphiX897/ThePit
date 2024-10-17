package ir.syphix.thepit.utils;

import ir.syphix.thepit.data.YamlDataManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.sayandev.stickynote.bukkit.StickyNote;

public class TextUtils {

    static MiniMessage miniMessage = MiniMessage.builder()
            .tags(
                    TagResolver.resolver(
                            TagResolver.standard()
                    )
            )
            .build();

    static BukkitAudiences bukkitAudiences = BukkitAudiences.create(StickyNote.plugin());

    public static void sendMessage(CommandSender sender, String message) {
        if (!YamlDataManager.YamlDataConfig.textFormat().equalsIgnoreCase("minimessage")) {
            sender.sendMessage(ir.syphix.palladiumapi.utils.TextUtils.colorify(message));
        } else {
            bukkitAudiences.sender(sender).sendMessage(miniMessage.deserialize(message));
        }
    }
}
