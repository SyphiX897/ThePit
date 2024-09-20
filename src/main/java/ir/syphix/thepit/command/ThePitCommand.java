package ir.syphix.thepit.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.incendo.cloud.Command;
import org.jetbrains.annotations.NotNull;
import org.sayandev.stickynote.bukkit.StickyNote;
import org.sayandev.stickynote.bukkit.command.BukkitCommand;
import org.sayandev.stickynote.bukkit.command.BukkitSender;
import org.sayandev.stickynote.bukkit.nms.NMSUtils;

public class ThePitCommand extends BukkitCommand {

    public ThePitCommand(String name, String... aliases) {
        super(name, aliases);


        Command.Builder<BukkitSender> test = builder()
                .literal("mammad")
                .handler(context -> {
                    Player player = context.sender().player();

                    ItemStack itemStack = player.getInventory().getItemInMainHand();

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
