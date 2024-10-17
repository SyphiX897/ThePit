package ir.syphix.thepit.database;

import ir.syphix.thepit.core.mission.Mission;
import ir.syphix.thepit.core.mission.MissionManager;
import ir.syphix.thepit.core.perk.Perk;
import ir.syphix.thepit.core.perk.PerkManager;
import ir.syphix.thepit.core.player.PitPlayer;
import ir.syphix.thepit.core.player.stats.CombatStats;
import ir.syphix.thepit.core.player.stats.MainStats;
import ir.syphix.thepit.data.YamlDataManager;
import ir.syphix.thepit.file.FileManager;
import ir.syphix.thepit.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.sayandev.stickynote.bukkit.StickyNote;
import org.sayandev.stickynote.core.database.Database;
import org.sayandev.stickynote.core.database.Query;
import org.sayandev.stickynote.core.database.mysql.MySQLCredentials;
import org.sayandev.stickynote.core.database.mysql.MySQLDatabase;
import org.sayandev.stickynote.core.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.UUID;
import java.util.regex.Pattern;

public class PitDatabase extends YamlDataManager.YamlDataDatabase {

    public static String SPLITTER_REGEX = Pattern.quote("<\"<splitter>\">");
    public static String SPLITTER = "<\"<splitter>\">";

    Database database;
    DatabaseType type;


    public PitDatabase(DatabaseType type) {
        this.type = type;
        String driverClass = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  //old mysql driver
            driverClass = "com.mysql.cj.jdbc.Driver";
        } catch (Exception exception) {
            try {
                Class.forName("com.mysql.jdbc.Driver"); //new mysql driver
                driverClass = "com.mysql.jdbc.Driver";
            } catch (Exception exception2) {
                exception2.printStackTrace();
            }
        }

        switch (type) {
            case MYSQL -> {
                database = new MySQLDatabase(
                        MySQLCredentials.mySQLCredentials(
                                MysqlData.address(),
                                MysqlData.port(),
                                MysqlData.database(),
                                MysqlData.ssl(),
                                MysqlData.username(),
                                MysqlData.password()
                        ),
                        MysqlData.poolingSize(),
                        false, driverClass, 0L, 5000L, 10, 1800000L, true
                );
            }

            case SQLITE -> database = new SQLiteDatabase(new File(StickyNote.pluginDirectory(), "storage.db"), StickyNote.getLogger());
        }

        connect();
        database.queueQuery(Query.query("CREATE TABLE IF NOT EXISTS thepit_info (UUID VARCHAR(64), gold DOUBLE, level BIGINT," +
                " xp DOUBLE, prestige BIGINT, unlocked_perks TEXT, selected_perks TEXT, completed_missions TEXT, ender_chest_items MEDIUMTEXT," +
                " bounty DOUBLE, kills BIGINT, deaths BIGINT, assists BIGINT, highest_kill_streak BIGINT, sword_hits BIGINT," +
                " arrow_hits BIGINT, arrow_shoots BIGINT, damage_dealt DOUBLE, melee_damage_dealt DOUBLE, arrow_damage_dealt DOUBLE," +
                " damage_taken DOUBLE, melee_damage_taken DOUBLE, arrow_damage_taken DOUBLE, block_placed BIGINT, lava_bucket_placed BIGINT, " +
                " PRIMARY KEY (UUID));"));

    }

    public boolean hasPlayer(UUID uniqueId) {
        ResultSet result = database.runQuery(Query.query("SELECT * FROM thepit_info WHERE UUID = ?;").setStatementValue(1, String.valueOf(uniqueId))).getResult();
        if (result == null) {
            return false;
        }

        try {
            boolean hasNext = result.next();
            result.close();
            return hasNext;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addPlayerAsync(PitPlayer pitPlayer) {
        if (hasPlayer(pitPlayer.uniqueId())) {
            updatePlayerAsync(pitPlayer);
            return;
        }

        String ignore = type == DatabaseType.MYSQL ? "IGNORE " : "";
        database.queueQuery(Query.query(String.format("INSERT %sINTO thepit_info (UUID, gold, level, xp, prestige, unlocked_perks, selected_perks, completed_missions," +
                " ender_chest_items, bounty, kills, deaths, assists, highest_kill_streak, sword_hits, arrow_hits, arrow_shoots, damage_dealt, melee_damage_dealt," +
                " arrow_damage_dealt, damage_taken, melee_damage_taken, arrow_damage_taken, block_placed, lava_bucket_placed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", ignore))
                .setStatementValue(1, String.valueOf(pitPlayer.uniqueId()))
                .setStatementValue(2, pitPlayer.mainStats().gold())
                .setStatementValue(3, pitPlayer.mainStats().level())
                .setStatementValue(4, pitPlayer.mainStats().xp())
                .setStatementValue(5, pitPlayer.mainStats().prestige())
                .setStatementValue(6, pitPlayer.mainStats().unlockedPerks().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().unlockedPerks().stream().map(Perk::id).toList()))
                .setStatementValue(7, pitPlayer.mainStats().selectedPerks().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().selectedPerks().stream().map(Perk::id).toList()))
                .setStatementValue(8, pitPlayer.mainStats().completedMissions().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().completedMissions().stream().map(Mission::id).toList()))
                .setStatementValue(9, pitPlayer.mainStats().enderChestItems().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().enderChestItems().stream().map(ItemStackUtils::toString).toList()))
                .setStatementValue(10, pitPlayer.combatStats().bounty())
                .setStatementValue(11, pitPlayer.combatStats().kills())
                .setStatementValue(12, pitPlayer.combatStats().deaths())
                .setStatementValue(13, pitPlayer.combatStats().assists())
                .setStatementValue(14, pitPlayer.combatStats().highestKillSteak())
                .setStatementValue(15, pitPlayer.combatStats().swordHits())
                .setStatementValue(16, pitPlayer.combatStats().arrowHits())
                .setStatementValue(17, pitPlayer.combatStats().arrowShoots())
                .setStatementValue(18, pitPlayer.combatStats().damageDealt())
                .setStatementValue(19, pitPlayer.combatStats().meleeDamageDealt())
                .setStatementValue(20, pitPlayer.combatStats().arrowDamageDealt())
                .setStatementValue(21, pitPlayer.combatStats().damageTaken())
                .setStatementValue(22, pitPlayer.combatStats().meleeDamageTaken())
                .setStatementValue(23, pitPlayer.combatStats().arrowDamageTaken())
                .setStatementValue(24, pitPlayer.combatStats().blockPlaced())
                .setStatementValue(25, pitPlayer.combatStats().lavaBucketPlaced())
        );

    }


    public void addPlayerSync(PitPlayer pitPlayer) {
        if (hasPlayer(pitPlayer.uniqueId())) {
            updatePlayerSync(pitPlayer);
            return;
        }

        String ignore = type == DatabaseType.MYSQL ? "IGNORE " : "";
        database.runQuery(Query.query(String.format("INSERT %sINTO thepit_info (UUID, gold, level, xp, prestige, unlocked_perks, selected_perks, completed_missions," +
                        " ender_chest_items, bounty, kills, deaths, assists, highest_kill_streak, sword_hits, arrow_hits, arrow_shoots, damage_dealt, melee_damage_dealt," +
                        " arrow_damage_dealt, damage_taken, melee_damage_taken, arrow_damage_taken, block_placed, lava_bucket_placed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", ignore))
                .setStatementValue(1, String.valueOf(pitPlayer.uniqueId()))
                .setStatementValue(2, pitPlayer.mainStats().gold())
                .setStatementValue(3, pitPlayer.mainStats().level())
                .setStatementValue(4, pitPlayer.mainStats().xp())
                .setStatementValue(5, pitPlayer.mainStats().prestige())
                .setStatementValue(6, pitPlayer.mainStats().unlockedPerks().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().unlockedPerks().stream().map(Perk::id).toList()))
                .setStatementValue(7, pitPlayer.mainStats().selectedPerks().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().selectedPerks().stream().map(Perk::id).toList()))
                .setStatementValue(8, pitPlayer.mainStats().completedMissions().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().completedMissions().stream().map(Mission::id).toList()))
                .setStatementValue(9, pitPlayer.mainStats().enderChestItems().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().enderChestItems().stream().map(ItemStackUtils::toString).toList()))
                .setStatementValue(10, pitPlayer.combatStats().bounty())
                .setStatementValue(11, pitPlayer.combatStats().kills())
                .setStatementValue(12, pitPlayer.combatStats().deaths())
                .setStatementValue(13, pitPlayer.combatStats().assists())
                .setStatementValue(14, pitPlayer.combatStats().highestKillSteak())
                .setStatementValue(15, pitPlayer.combatStats().swordHits())
                .setStatementValue(16, pitPlayer.combatStats().arrowHits())
                .setStatementValue(17, pitPlayer.combatStats().arrowShoots())
                .setStatementValue(18, pitPlayer.combatStats().damageDealt())
                .setStatementValue(19, pitPlayer.combatStats().meleeDamageDealt())
                .setStatementValue(20, pitPlayer.combatStats().arrowDamageDealt())
                .setStatementValue(21, pitPlayer.combatStats().damageTaken())
                .setStatementValue(22, pitPlayer.combatStats().meleeDamageTaken())
                .setStatementValue(23, pitPlayer.combatStats().arrowDamageTaken())
                .setStatementValue(24, pitPlayer.combatStats().blockPlaced())
                .setStatementValue(25, pitPlayer.combatStats().lavaBucketPlaced())
        );

    }

    public void updatePlayerAsync(PitPlayer pitPlayer) {
        database.queueQuery(Query.query("UPDATE thepit_info SET gold = ?, level = ?, xp = ?, prestige = ?, unlocked_perks = ?, selected_perks = ?, completed_missions = ?, " +
                "ender_chest_items = ?, bounty = ?, kills = ?, deaths = ?, assists = ?, highest_kill_streak = ?, sword_hits = ?, arrow_hits = ?, arrow_shoots = ?, damage_dealt = ?, " +
                "melee_damage_dealt = ?, arrow_damage_dealt = ?, damage_taken = ?, melee_damage_taken = ?, arrow_damage_taken = ?, block_placed = ?, lava_bucket_placed = ? WHERE UUID = ?;")
                .setStatementValue(1, pitPlayer.mainStats().gold())
                .setStatementValue(2, pitPlayer.mainStats().level())
                .setStatementValue(3, pitPlayer.mainStats().xp())
                .setStatementValue(4, pitPlayer.mainStats().prestige())
                .setStatementValue(5, pitPlayer.mainStats().unlockedPerks().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().unlockedPerks().stream().map(Perk::id).toList()))
                .setStatementValue(6, pitPlayer.mainStats().selectedPerks().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().selectedPerks().stream().map(Perk::id).toList()))
                .setStatementValue(7, pitPlayer.mainStats().completedMissions().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().completedMissions().stream().map(Mission::id).toList()))
                .setStatementValue(8, pitPlayer.mainStats().enderChestItems().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().enderChestItems().stream().map(ItemStackUtils::toString).toList()))
                .setStatementValue(9, pitPlayer.combatStats().bounty())
                .setStatementValue(10, pitPlayer.combatStats().kills())
                .setStatementValue(11, pitPlayer.combatStats().deaths())
                .setStatementValue(12, pitPlayer.combatStats().assists())
                .setStatementValue(13, pitPlayer.combatStats().highestKillSteak())
                .setStatementValue(14, pitPlayer.combatStats().swordHits())
                .setStatementValue(15, pitPlayer.combatStats().arrowHits())
                .setStatementValue(16, pitPlayer.combatStats().arrowShoots())
                .setStatementValue(17, pitPlayer.combatStats().damageDealt())
                .setStatementValue(18, pitPlayer.combatStats().meleeDamageDealt())
                .setStatementValue(19, pitPlayer.combatStats().arrowDamageDealt())
                .setStatementValue(20, pitPlayer.combatStats().damageTaken())
                .setStatementValue(21, pitPlayer.combatStats().meleeDamageTaken())
                .setStatementValue(22, pitPlayer.combatStats().arrowDamageTaken())
                .setStatementValue(23, pitPlayer.combatStats().blockPlaced())
                .setStatementValue(24, pitPlayer.combatStats().lavaBucketPlaced())
                .setStatementValue(25, String.valueOf(pitPlayer.uniqueId())));
    }

    public void updatePlayerSync(PitPlayer pitPlayer) {

        database.runQuery(Query.query("UPDATE thepit_info SET gold = ?, level = ?, xp = ?, prestige = ?, unlocked_perks = ?, selected_perks = ?, completed_missions = ?, " +
                        "ender_chest_items = ?, bounty = ?, kills = ?, deaths = ?, assists = ?, highest_kill_streak = ?, sword_hits = ?, arrow_hits = ?, arrow_shoots = ?, damage_dealt = ?, " +
                        "melee_damage_dealt = ?, arrow_damage_dealt = ?, damage_taken = ?, melee_damage_taken = ?, arrow_damage_taken = ?, block_placed = ?, lava_bucket_placed = ? WHERE UUID = ?;")
                .setStatementValue(1, pitPlayer.mainStats().gold())
                .setStatementValue(2, pitPlayer.mainStats().level())
                .setStatementValue(3, pitPlayer.mainStats().xp())
                .setStatementValue(4, pitPlayer.mainStats().prestige())
                .setStatementValue(5, pitPlayer.mainStats().unlockedPerks().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().unlockedPerks().stream().map(Perk::id).toList()))
                .setStatementValue(6, pitPlayer.mainStats().selectedPerks().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().selectedPerks().stream().map(Perk::id).toList()))
                .setStatementValue(7, pitPlayer.mainStats().completedMissions().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().completedMissions().stream().map(Mission::id).toList()))
                .setStatementValue(8, pitPlayer.mainStats().enderChestItems().isEmpty() ? "" : String.join(SPLITTER, pitPlayer.mainStats().enderChestItems().stream().map(ItemStackUtils::toString).toList()))
                .setStatementValue(9, pitPlayer.combatStats().bounty())
                .setStatementValue(10, pitPlayer.combatStats().kills())
                .setStatementValue(11, pitPlayer.combatStats().deaths())
                .setStatementValue(12, pitPlayer.combatStats().assists())
                .setStatementValue(13, pitPlayer.combatStats().highestKillSteak())
                .setStatementValue(14, pitPlayer.combatStats().swordHits())
                .setStatementValue(15, pitPlayer.combatStats().arrowHits())
                .setStatementValue(16, pitPlayer.combatStats().arrowShoots())
                .setStatementValue(17, pitPlayer.combatStats().damageDealt())
                .setStatementValue(18, pitPlayer.combatStats().meleeDamageDealt())
                .setStatementValue(19, pitPlayer.combatStats().arrowDamageDealt())
                .setStatementValue(20, pitPlayer.combatStats().damageTaken())
                .setStatementValue(21, pitPlayer.combatStats().meleeDamageTaken())
                .setStatementValue(22, pitPlayer.combatStats().arrowDamageTaken())
                .setStatementValue(23, pitPlayer.combatStats().blockPlaced())
                .setStatementValue(24, pitPlayer.combatStats().lavaBucketPlaced())
                .setStatementValue(25, String.valueOf(pitPlayer.uniqueId())));
    }


    public PitPlayer getPlayer(UUID uniqueId) {
        ResultSet result = database.runQuery(Query.query("SELECT * FROM thepit_info WHERE UUID = ?;").setStatementValue(1, String.valueOf(uniqueId))).getResult();
        if (result == null) {
            return null;
        }

        try {
            if (!result.next()) return null;

            LinkedList<ItemStack> enderChestItems = new LinkedList<>();
            int counter = 0;
            for (String item : result.getString("ender_chest_items").split(SPLITTER_REGEX)) {
                if (counter == 27) break;
                if (ItemStackUtils.toItemStack(item) == null) {
                    enderChestItems.add(counter, new ItemStack(Material.AIR));
                    continue;
                }
                enderChestItems.add(counter, ItemStackUtils.toItemStack(item));
                counter++;
            }

            MainStats mainStats = new MainStats(result.getDouble("gold"),
                    result.getLong("level"),
                    result.getDouble("xp"),
                    result.getLong("prestige"),
                    result.getString("unlocked_perks").isBlank() ? new ArrayList<>() : Arrays.stream(result.getString("unlocked_perks").split(SPLITTER_REGEX)).map(PerkManager::perk).toList(),
                    result.getString("selected_perks").isBlank() ? new LinkedList<>() : new LinkedList<>(Arrays.stream(result.getString("selected_perks").split(SPLITTER_REGEX)).map(PerkManager::perk).toList()),
                    result.getString("completed_missions").isBlank() ? new ArrayList<>() : Arrays.stream(result.getString("completed_missions").split(SPLITTER_REGEX)).map(MissionManager::mission).toList(),
                    result.getString("ender_chest_items").isBlank() ? new LinkedList<>() : enderChestItems
            );

            CombatStats combatStats = new CombatStats(result.getDouble("bounty"), result.getLong("kills"), result.getLong("deaths"),
                    result.getLong("assists"), 0, result.getLong("highest_kill_streak"), result.getLong("sword_hits"),
                    result.getLong("arrow_hits"), result.getLong("arrow_shoots"), result.getDouble("damage_dealt"),
                    result.getDouble("melee_damage_dealt"), result.getDouble("arrow_damage_dealt"), result.getDouble("damage_taken"),
                    result.getDouble("melee_damage_taken"), result.getDouble("arrow_damage_taken"), result.getLong("block_placed"),
                    result.getLong("lava_bucket_placed"));

            PitPlayer pitPlayer = new PitPlayer(Bukkit.getPlayer(uniqueId), mainStats, combatStats);
            result.close();
            return pitPlayer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void connect() {
        database.connect();
    }

    public void disconnect() {
        database.shutdown();
    }

}
