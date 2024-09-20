package ir.syphix.thepit.database;

import ir.syphix.thepit.file.FileManager;
import org.bukkit.configuration.ConfigurationSection;
import org.sayandev.stickynote.bukkit.StickyNote;
import org.sayandev.stickynote.core.database.Database;
import org.sayandev.stickynote.core.database.Query;
import org.sayandev.stickynote.core.database.mysql.MySQLCredentials;
import org.sayandev.stickynote.core.database.mysql.MySQLDatabase;
import org.sayandev.stickynote.core.database.sqlite.SQLiteDatabase;

import java.io.File;

public class PitDatabase {

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
                ConfigurationSection databaseSection = FileManager.DatabaseFile.get().getConfigurationSection("database");

                if (databaseSection == null) {
                    throw new NullPointerException("Can't find database section in database.yml!");
                }

                database = new MySQLDatabase(
                        MySQLCredentials.mySQLCredentials(
                                databaseSection.getString("address"),
                                databaseSection.getInt("port"),
                                databaseSection.getString("database"),
                                databaseSection.getBoolean("ssl"),
                                databaseSection.getString("username"),
                                databaseSection.getString("password")
                        ),
                        databaseSection.getInt("pooling_size"),
                        false, driverClass, 0L, 5000L, 10, 1800000L, true
                );
            }

            case SQLITE -> database = new SQLiteDatabase(new File(StickyNote.pluginDirectory(), "storage.db"), StickyNote.getLogger());
        }

        connect();
        database.queueQuery(Query.query("CREATE TABLE IF NOT EXIST thepit_info (UUID VARCHAR(64), gold VARCHAR(24), level VARCHAR(16)," +
                " xp VARCHAR(24), prestige VARCHAR(12), unlocked_perks TEXT, selected_perks TEXT, completed_missions TEXT, ender_chest_items MEDIUMTEXT," +
                " bounty VARCHAR(16), kills VARCHAR(16), deaths VARCHAR(16), assists VARCHAR(16), highest_kill_streak VARCHAR(16), sword_hits VARCHAR(24)," +
                " arrow_hits VARCHAR(24), arrow_shoots VARCHAR(24), damage_dealt VARCHAR(32), melee_damage_dealt VARCHAR(32), arrow_damage_dealt VARCHAR(32)," +
                " damage_taken VARCHAR(32), melee_damage_taken VARCHAR(32), arrow_damage_taken VARCHAR(32), block_placed VARCHAR(32), lava_bucket_placed VARCHAR(16)," +
                " PRIMARY KEY (UUID));"));

//        database.queueQuery(Query.query("mammad")).getCompletableFuture().whenComplete(((resultSet, throwable) -> {
//            if (throwable != null) {
//                throwable.printStackTrace();
//                return;
//            }
//        }));
    }


    public static



    public void connect() {
        database.connect();
    }

    public void disconnect() {
        database.shutdown();
    }

}
