package main.sqlite;



import main.CustomFishingTraps;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SQLite extends Database {
    String dbname;
    public SQLite(CustomFishingTraps instance){
        super(instance);
        this.plugin = instance;
        dbname = "fishing_traps";
    }

    public String SQLiteCreateTrapsTable = "CREATE TABLE IF NOT EXISTS fishing_traps (" +
            "`id` TEXT NOT NULL," +
            "`owner` TEXT NOT NULL," +
            "`key` TEXT NOT NULL," +
            "`location` TEXT NOT NULL," +
            "`active` INTEGER NOT NULL," +
            "`items` TEXT," +
            "`maxItems` INTEGER NOT NULL," +
            "`bait` TEXT," +
            "PRIMARY KEY (`id`)" +
            ");";

    public Connection getSQLConnection() {
        Connection result = null;
        File dataFolder = new File(plugin.getDataFolder(), dbname + ".db");
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: " + dbname + ".db");
            }
        }
        try {
            if (connection != null && !connection.isClosed()) {
                result = connection;
            } else {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
                result = connection;
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return result;
    }

    public void load() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreateTrapsTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
}