package me.syphix.thepit.command;

import ir.syphix.palladiumapi.core.item.CustomItem;
import ir.syphix.palladiumapi.core.item.CustomItemManager;
import me.syphix.thepit.command.parser.ArenaParser;
import me.syphix.thepit.core.arena.Arena;
import me.syphix.thepit.core.arena.ArenaManager;
import me.syphix.thepit.core.arena.ArenaStatus;
import me.syphix.thepit.core.economy.ThePitEconomy;
import me.syphix.thepit.core.kit.KitManager;
import me.syphix.thepit.core.lobby.LobbyManager;
import me.syphix.thepit.menu.MenuKitView;
import me.syphix.thepit.utils.TextUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.parser.flag.CommandFlag;
import org.incendo.cloud.parser.standard.StringParser;
import org.incendo.cloud.suggestion.SuggestionProvider;
import org.sayandev.stickynote.bukkit.command.BukkitCommand;
import org.sayandev.stickynote.bukkit.command.BukkitSender;


public class ThePitCommand extends BukkitCommand {

    public ThePitCommand() {
        super("thepit", "tpit");

        //[================================| Give section |================================]\\

        Command.Builder<BukkitSender> giveLiteral = builder()
                .literal("give");

        Command.Builder<BukkitSender> giveItem = giveLiteral
                .literal("item")
                .permission("thepit.give.items")
                .required("item_id", StringParser.stringParser(), SuggestionProvider.suggestingStrings(CustomItemManager.items().stream().map(CustomItem::getId).toList()))
                .optional("player", PlayerParser.playerParser())
                .handler(context -> {
                    Player player = context.sender().player();
                    if (isPlayerNull(player, context.sender().platformSender())) return;

                    Player target = context.<Player>optional("player").orElse(player);
                    String itemId = context.get("item_id");
                    if (CustomItemManager.getItemById(itemId) == null) {
                        TextUtils.sendMessage(player, ""); //TODO: item does not exist msg
                        return;
                    }

                    if (target.getInventory().firstEmpty() == -1) {
                        TextUtils.sendMessage(player, ""); //TODO: inventory is full msg
                        return;
                    }

                    target.getInventory().addItem(CustomItemManager.getItemById(itemId).getItemStack());
                    //TODO: send message to sender

                });
        getManager().command(giveItem);



        //[================================| Lobby section |================================]\\

        Command.Builder<BukkitSender> lobbyLiteral = builder()
                .literal("lobby");

        Command.Builder<BukkitSender> setLobbyLocation = lobbyLiteral
                .literal("set")
                .permission("thepit.lobby.set")
                .handler(context -> {

                    Player player = context.sender().player();
                    if (isPlayerNull(player, context.sender().platformSender())) return;

                    LobbyManager.location(player.getLocation());
                    //TODO: send message to sender
                });
        getManager().command(setLobbyLocation);

        Command.Builder<BukkitSender> tpLobby = lobbyLiteral
                .literal("tp")
                .permission("thepit.lobby.tp")
                .optional("player", PlayerParser.playerParser())
                .handler(context -> {
                    Player player = context.sender().player();
                    if (isPlayerNull(player, context.sender().platformSender())) return;
                    Player target = context.<Player>optional("player").orElse(player);


                    LobbyManager.teleport(target);
                    //TODO: send message to sender
                });
        getManager().command(tpLobby);



        //[================================| Kit section |================================]\\

        Command.Builder<BukkitSender> kitLiteral = builder() //TODO: give, delete, view
                .literal("kit");

        Command.Builder<BukkitSender> giveKit = kitLiteral
                .literal("give")
                .permission("thepit.kit.give")
                .required("kit_id", StringParser.stringParser(), SuggestionProvider.suggestingStrings(KitManager.kitsNameList()))
                .optional("player", PlayerParser.playerParser())
                .handler(context -> {
                    Player player = context.sender().player();
                    if (isPlayerNull(player, context.sender().platformSender())) return;

                    Player target = context.<Player>optional("player").orElse(player);
                    String kitId = context.get("kit_id");
                    if (isKitNull(kitId, player)) return;

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
                    if (isKitNull(kitId, sender)) return;

                    KitManager.remove(kitId);
                });
        getManager().command(deleteKit);

        Command.Builder<BukkitSender> viewKit = kitLiteral
                .literal("view")
                .permission("thepit.kit.view")
                .required("kit_id", StringParser.stringParser(), SuggestionProvider.suggestingStrings(KitManager.kitsNameList()))
                .handler(context -> {
                    Player player = context.sender().player();
                    if (isPlayerNull(player, context.sender().platformSender())) return;

                    String kitId = context.get("kit_id");
                    if (isKitNull(kitId, player)) return;

                    new MenuKitView(player, KitManager.kit(kitId));
                });
        getManager().command(viewKit);


        //[================================| Arena section |================================]\\

        Command.Builder<BukkitSender> arenaLiteral = builder()
                .literal("arena");
        getManager().command(arenaLiteral);


        Command.Builder<BukkitSender> createArena = arenaLiteral
                .literal("create")
                .permission("thepit.arena.create")
                .required("arena_id", StringParser.stringParser(), SuggestionProvider.suggestingStrings("<Arena-Name>"))
                .handler(context -> {
                    CommandSender sender = context.sender().platformSender();

                    String arenaId = context.get("arena_id");
                    if (ArenaManager.exist(arenaId)) {
                        TextUtils.sendMessage(sender, ""); //TODO: arena is already exist msg
                        return;
                    }

                    ArenaManager.create(sender, arenaId);
                    //TODO: send message to sender
                });
        getManager().command(createArena);


        Command.Builder<BukkitSender> tpArena = arenaLiteral
                .literal("tp")
                .permission("thepit.arena.tp")
                .required("arena", ArenaParser.arenaParser())
                .optional("player", PlayerParser.playerParser())
                .flag(CommandFlag.builder("sendMessage"))
                .handler(context -> {
                    Player player = context.sender().player();
                    if (isPlayerNull(player, context.sender().platformSender())) return;
                    Player target = context.<Player>optional("player").orElse(player);

                    Arena arena = context.get("arena");

                    if (arena.spawnLocation() == null) {
                        TextUtils.sendMessage(player, "4"); //TODO: missing arena spawn location
                        return;
                    }

                    target.teleport(arena.spawnLocation().clone());

                });
        getManager().command(tpArena);


        Command.Builder<BukkitSender> setLiteral = arenaLiteral //TODO: randomgold, arenaspawn, kit
                .literal("set");

        Command.Builder<BukkitSender> spawnLocation = setLiteral
                .literal("spawnLocation")
                .permission("thepit.arena.setspawn")
                .required("arena", ArenaParser.arenaParser())
                .handler(context -> {
                    Player player = context.sender().player();
                    if (isPlayerNull(player, context.sender().platformSender())) return;

                    Arena arena = context.get("arena");
                    arena.spawnLocation(player.getLocation().clone(), true);
                    //TODO: send message to sender
                });
        getManager().command(spawnLocation);

        Command.Builder<BukkitSender> kit = setLiteral
                .literal("kit")
                .permission("thepit.arena.setkit")
                .required("arena", ArenaParser.arenaParser())
                .required("kit_id", StringParser.stringParser(), SuggestionProvider.suggestingStrings(KitManager.kitsNameList()))
                .handler(context -> {
                    CommandSender sender = context.sender().platformSender();
                    Arena arena = context.get("arena");
                    String kitId = context.get("kit_id");
                    if (isKitNull(kitId, sender)) return;

                    arena.kit(kitId, true);
                    //TODO: send message to sender
                });
        getManager().command(kit);

        Command.Builder<BukkitSender> goldLocation = setLiteral
                .literal("randomGoldLocation")
                .permission("thepit.arena.setgoldlocation")
                .required("arena", ArenaParser.arenaParser())
                .handler(context -> {
                    Player player = context.sender().player();
                    if (isPlayerNull(player, context.sender().platformSender())) return;

                    Arena arena = context.get("arena");
                    arena.addGoldSpawnLocation(player.getLocation().clone(), true);
                    //TODO: send message to sender
                });
        getManager().command(goldLocation);



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
                .required("arena", ArenaParser.arenaParser())
                .handler(context -> {
                    CommandSender sender = context.sender().platformSender();
                    Arena arena = context.get("arena");
                    String arenaStatus = String.valueOf(arena.status());

                    TextUtils.sendMessage(sender, arenaStatus); //TODO: arena status msg
                });
        getManager().command(checkStatus);

        Command.Builder<BukkitSender> joinArena = arenaLiteral
                .literal("join")
                .permission("thepit.arena.join")
                .required("arena", ArenaParser.arenaParser())
                .optional("player", PlayerParser.playerParser())
                .handler(context -> {
                    Player player = context.sender().player();
                    if (isPlayerNull(player, context.sender().platformSender())) return;

                    Player target = context.<Player>optional("player").orElse(player);
                    Arena arena = context.get("arena");

                    ArenaManager.join(target, arena);
                    //TODO: send message to sender
                });
        getManager().command(joinArena);



        //[================================| Miscellaneous section |================================]\\

        Command.Builder<BukkitSender> balance = builder()
                .literal("balance")
                .permission("thepit.balance")
                .optional("player", PlayerParser.playerParser())
                .handler(context -> {
                    Player player = context.sender().player();
                    if (isPlayerNull(player, context.sender().platformSender())) return;

                    if (!player.hasPermission("thepit.balance.other")) {
                        double playerBalance = ThePitEconomy.balance(player.getUniqueId());

                        TextUtils.sendMessage(player, playerBalance + "", Placeholder.component("balance", TextUtils.toComponent(String.valueOf(playerBalance))));
                        return;
                    }
                    Player target = context.<Player>optional("player").orElse(player);
                    double targetBalance = ThePitEconomy.balance(target.getUniqueId());

                    TextUtils.sendMessage(player, targetBalance + "", Placeholder.component("balance", TextUtils.toComponent(String.valueOf(targetBalance))));
                });
        getManager().command(balance);
        getManager().command(getManager().commandBuilder("balance").proxies(balance.build()));
        getManager().command(getManager().commandBuilder("gold").proxies(balance.build()));


        Command.Builder<BukkitSender> reload = builder()
                .literal("reload")
                .permission("thepit.reload")
                .handler(context -> {

                });
        getManager().command(reload);

    }



    //[================================| ================== |================================]\\

    private boolean isPlayerNull(Player player, CommandSender sender) {
        if (player == null) {
            TextUtils.sendMessage(sender, ""); //TODO: console not allowed message
            return true;
        }
        return false;
    }

    private boolean isKitNull(String kitId, CommandSender sender) {
        if (!KitManager.exist(kitId)) {
            TextUtils.sendMessage(sender, ""); //TODO: Kit does not exist msg
            return true;
        }
        return false;
    }
}
