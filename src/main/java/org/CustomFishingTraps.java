package org;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public final class CustomFishingTraps extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("CustomFishingTraps has loaded!");
        // We need to find the instance. Get the instance, if the instance isn't null, then we can run the getLogger that the plugin has been initialised.

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("CustomFishingTraps has been disabled!");

    }
}
