package ir.syphix.thepit.core.task;

import de.tr7zw.nbtapi.NBT;
import ir.syphix.palladiumapi.core.item.CustomItem;
import ir.syphix.palladiumapi.core.item.CustomItemManager;
import ir.syphix.palladiumapi.utils.Utils;
import ir.syphix.thepit.core.arena.Arena;
import ir.syphix.thepit.core.arena.ArenaManager;
import ir.syphix.thepit.core.arena.ArenaStatus;
import ir.syphix.thepit.data.YamlDataManager;
import ir.syphix.thepit.utils.TextUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.sayandev.stickynote.bukkit.StickyNote;

import java.util.List;
import java.util.Random;

public class GoldSpawnTask extends BukkitRunnable {

    private final ItemStack gold = gold();

    @Override
    public void run() {
        for (Arena arena : ArenaManager.arenas()) {
            if (arena.status() == ArenaStatus.DISABLE || arena.status() == ArenaStatus.UPDATING) continue;
            Random random = new Random();
            if (YamlDataManager.YamlDataConfig.randomGoldAllLocation()) {
                for (Location location : arena.goldSpawnLocations()) {
                    spawnGold(location.clone());
                }
            } else {
                if (arena.goldSpawnLocations().isEmpty()) continue;
                int randomLocationIndex = random.nextInt(arena.goldSpawnLocations().size());
                spawnGold(arena.goldSpawnLocations().get(randomLocationIndex).clone());
            }
        }
    }

    public void runTaskTimer() {
        runTaskTimer(StickyNote.plugin(), YamlDataManager.YamlDataConfig.randomGoldPeriod() - 15, YamlDataManager.YamlDataConfig.randomGoldPeriod() - 15);
    }

    public void spawnGold(Location location) {
        if (!YamlDataManager.YamlDataConfig.randomGoldAllowStack()) {
            for (Entity entity : Utils.getNearbyEntities(location, 1D)) {
                if (isCustomGold(entity)) return;
            }
        }

        Item item = location.getWorld().dropItemNaturally(new Location(location.getWorld(), location.getX(), 10000, location.getZ()), gold);

        StickyNote.runSync(() -> {
            item.teleport(Utils.simplifyToCenter(location.clone().add(0, 0.1, 0)));
        }, 15);
        StickyNote.runSync(() -> {
            item.teleport(Utils.simplifyToCenter(location.clone().add(0, 0.1, 0)));
        }, 1);

        if (YamlDataManager.YamlDataConfig.isRandomGoldHologramEnable()) {
            item.setCustomName(TextUtils.colorify(YamlDataManager.YamlDataConfig.randomGoldCustomName()));
            item.setCustomNameVisible(true);
        }
    }

    public ItemStack gold() {
        CustomItem customItem = CustomItemManager.getItemById(YamlDataManager.YamlDataConfig.randomGoldCustomItemId());
        customItem.addTag("string", List.of("thepit:random_gold", "random_gold"));

        return customItem.getItemStackClone();
    }

    public boolean isCustomGold(Entity entity) {
        if (entity.getType() != EntityType.DROPPED_ITEM) return false;
        Item item = (Item) entity;
        String tag = NBT.get(item.getItemStack(), nbt -> (String) nbt.getString("thepit:random_gold"));

        return tag != null && !tag.isBlank();
    }

}
