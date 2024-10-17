package ir.syphix.thepit.command;

import ir.syphix.thepit.core.kit.Kit;
import ir.syphix.thepit.core.kit.KitManager;
import ir.syphix.thepit.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.parser.standard.StringParser;
import org.incendo.cloud.suggestion.SuggestionProvider;
import org.jetbrains.annotations.NotNull;
import org.sayandev.stickynote.bukkit.StickyNote;
import org.sayandev.stickynote.bukkit.command.BukkitCommand;
import org.sayandev.stickynote.bukkit.command.BukkitSender;
import org.sayandev.stickynote.bukkit.nms.NMSUtils;

public class ThePitCommand extends BukkitCommand {

    public ThePitCommand(String name, String... aliases) {
        super(name, aliases);

        Command.Builder<BukkitSender> kitLiteral = builder()
                .literal("kit")
                .permission("thepit.kit");

        Command.Builder<BukkitSender> giveKit = kitLiteral
                .literal("give")
                .permission("thepit.kit.give")
                .required("kit_id", StringParser.stringParser(), SuggestionProvider.suggestingStrings(KitManager.kits().stream().map(Kit::id).toList()))
                .optional("player", PlayerParser.playerParser())
                .handler(context -> {
                    Player player = context.sender().player();
                    if (player == null) {
                        TextUtils.sendMessage(context.sender().platformSender(), ""); //TODO: console not allowed message
                        return;
                    }

                    Player target = context.<Player>optional("player").orElse(player);
                    String kitId = context.get("kit_id");
                    KitManager.giveKit(target, kitId);

                });
        getManager().command(giveKit);

        Command.Builder<BukkitSender> arenaLiteral = builder()
                .literal("arena")
                .permission("thepit.arena");


        Command.Builder<BukkitSender> createArena = arenaLiteral
                .literal("create")
                .permission("thepit.arena.create")
                .required("arena_name", StringParser.stringParser())
                .handler(context -> {

                });




















        Command.Builder<BukkitSender> test = builder()
                .literal("mammad")
                .handler(context -> {
                    Player player = context.sender().player();

                    ItemStack itemStack = player.getInventory().getItemInHand();

                    String itemStackString = NMSUtils.INSTANCE.getItemStackNBTJson(itemStack);

                    player.sendMessage(itemStackString);

                    StickyNote.runSync(() -> {

                        ItemStack itemStack1 = NMSUtils.INSTANCE.getItemStackFromNBTJson(itemStackString);
                        itemStack1.setAmount(64);

                        player.getInventory().addItem(itemStack1);

                    }, 100);


                });
        getManager().command(test);
    }
}
