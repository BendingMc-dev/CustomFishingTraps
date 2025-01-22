package main.sqlite;



import main.CustomFishingTraps;

import java.util.logging.Level;

public class Error {
    public static void execute(CustomFishingTraps plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }
    public static void close(CustomFishingTraps plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}