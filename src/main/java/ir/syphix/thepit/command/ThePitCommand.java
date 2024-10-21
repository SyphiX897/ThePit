package ir.syphix.thepit.command;

import ir.syphix.thepit.command.parser.ArenaParser;
import ir.syphix.thepit.core.arena.Arena;
import ir.syphix.thepit.core.arena.ArenaManager;
import ir.syphix.thepit.core.arena.ArenaStatus;
import ir.syphix.thepit.core.kit.Kit;
import ir.syphix.thepit.core.kit.KitManager;
import ir.syphix.thepit.menu.MenuKitView;
import ir.syphix.thepit.menu.MenuUtils;
import ir.syphix.thepit.utils.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.parser.standard.StringParser;
import org.incendo.cloud.suggestion.BlockingSuggestionProvider;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;
import org.sayandev.stickynote.bukkit.command.BukkitCommand;
import org.sayandev.stickynote.bukkit.command.BukkitSender;


public class ThePitCommand extends BukkitCommand {

    public ThePitCommand() {
        super("thepit", "tpit");


        Command.Builder<BukkitSender> debug = builder()
                .literal("debug")
                .handler(context -> {
                    TextUtils.sendMessage(context.sender().platformSender(), String.join("|", ArenaManager.arenasNameList()));
                });
        getManager().command(debug);
        //[================================| Kit section |================================]\\

        Command.Builder<BukkitSender> kitLiteral = builder() //TODO: give, delete, view
                .literal("kit")
                .permission("thepit.kit");

        Command.Builder<BukkitSender> giveKit = kitLiteral
                .literal("give")
                .permission("thepit.kit.give")
                .required("kit_id", StringParser.stringParser(), SuggestionProvider.suggestingStrings(KitManager.kitsNameList()))
                .optional("player", PlayerParser.playerParser())
                .handler(context -> {
                    Player player = context.sender().player();
                    if (player == null) {
                        TextUtils.sendMessage(context.sender().platformSender(), ""); //TODO: console not allowed message
                        return;
                    }

                    Player target = context.<Player>optional("player").orElse(player);
                    String kitId = context.get("kit_id");
                    if (!KitManager.exist(kitId)) {
                        TextUtils.sendMessage(player, ""); //TODO: Kit does not exist msg
                        return;
                    }

                    KitManager.giveKit(target, kitId);
                    //TODO: send message to sender
                });
        getManager().command(giveKit);

        Command.Builder<BukkitSender> deleteKit = kitLiteral
                .literal("delete")
                .permission("thepit.kit.delete")
                .required("kit_id", StringParser.stringParser(), SuggestionProvider.suggestingStrings(KitManager.kitsNameList()))
                .handler(context -> {
                    CommandSender sender = context.sender().platformSender();

                    String kitId = context.get("kit_id");
                    if (!KitManager.exist(kitId)) {
                        TextUtils.sendMessage(sender, ""); //TODO: Kit does not exist msg
                        return;
                    }

                    KitManager.remove(kitId);
                });
        getManager().command(deleteKit);

        Command.Builder<BukkitSender> viewKit = kitLiteral
                .literal("view")
                .permission("thepit.kit.view")
                .required("kit_id", StringParser.stringParser(), SuggestionProvider.suggestingStrings(KitManager.kitsNameList()))
                .handler(context -> {
                    Player player = context.sender().player();
                    if (player == null) {
                        TextUtils.sendMessage(context.sender().platformSender(), ""); //TODO: console not allowed message
                        return;
                    }

                    String kitId = context.get("kit_id");
                    if (!KitManager.exist(kitId)) {
                        TextUtils.sendMessage(player, ""); //TODO: Kit does not exist msg
                        return;
                    }

                    new MenuKitView(player, KitManager.kit(kitId));
                });
        getManager().command(viewKit);


        //[================================| Arena section |================================]\\

        Command.Builder<BukkitSender> arenaLiteral = builder()
                .literal("arena");

        getManager().command(getManager().commandBuilder("arena").proxies(arenaLiteral.build()));


        Command.Builder<BukkitSender> createArena = arenaLiteral
                .literal("create")
                .permission("thepit.arena.create")
                .required("arena_name", StringParser.stringParser(), SuggestionProvider.suggestingStrings("<arena-name>"))
                .handler(context -> {
                    CommandSender sender = context.sender().platformSender();

                    String arenaId = context.get("arena_name");
                    if (ArenaManager.exist(arenaId)) {
                        TextUtils.sendMessage(sender, ""); //TODO: arena is already exist msg
                        return;
                    }

                    ArenaManager.create(sender, arenaId);
                    //TODO: send message to sender
                });
        getManager().command(createArena);

        Command.Builder<BukkitSender> joinArena = arenaLiteral
                .literal("join")
                .permission("thepit.arena.join")
                .required("arena", ArenaParser.arenaParser())
                .optional("player", PlayerParser.playerParser())
                .handler(context -> {
                    Player player = context.sender().player();
                    if (player == null) {
                        TextUtils.sendMessage(context.sender().platformSender(), ""); //TODO: console not allowed message
                        return;
                    }
                    Player target = context.<Player>optional("player").orElse(player);
                    Arena arena = context.get("arena");

                    ArenaManager.join(target, arena);
                    //TODO: send message to sender
                });
        getManager().command(joinArena);

        Command.Builder<BukkitSender> statusLiteral = arenaLiteral
                .literal("status");

        Command.Builder<BukkitSender> changeStatus = statusLiteral
                .literal("change")
                .permission("thepit.arena.status.change")
                .required("arena", ArenaParser.arenaParser())
                .required("status", StringParser.stringParser(), SuggestionProvider.suggestingStrings(ArenaStatus.statusList()))
                .handler(context -> {
                    CommandSender sender = context.sender().platformSender();

                    Arena arena = context.get("arena");

                    String status = context.get("status");
                    if (status.isBlank() || !ArenaStatus.statusList().contains(status)) {
                        TextUtils.sendMessage(sender, ""); //TODO: arena status is wrong msg
                        return;
                    }

                    ArenaManager.changeStatus(arena, ArenaStatus.valueOf(status));
                    //TODO: send message to sender
                });
        getManager().command(changeStatus);

        Command.Builder<BukkitSender> checkStatus = statusLiteral
                .literal("check")
                .permission("thepit.arena.status.check")
                .required("arena_id", StringParser.stringParser(), SuggestionProvider.suggestingStrings(ArenaManager.arenas().stream().map(arenas -> arenas.id()).toList()))
                .handler(context -> {
                    CommandSender sender = context.sender().platformSender();
                    String arenaId = context.get("arena_id");
                    if (!ArenaManager.checkArena(sender, arenaId)) return;

                    Arena arena = ArenaManager.arena(arenaId);
                    String arenaStatus = String.valueOf(arena.status());

                    TextUtils.sendMessage(sender, arenaStatus); //TODO: arena status msg
                });
        getManager().command(checkStatus);

    }
}
