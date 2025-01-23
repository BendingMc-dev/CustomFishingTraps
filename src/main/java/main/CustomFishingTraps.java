package main;

import dev.lone.itemsadder.api.ItemsAdder;
import eu.decentsoftware.holograms.api.DecentHologramsAPI;
import main.listeners.CustomFishingTrapsInteract;
import main.listeners.TownyNewDayListener;
import main.utilities.ConfigUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.momirealms.customfishing.api.BukkitCustomFishingPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import main.listeners.MenuListener;
import main.sqlite.Database;
import main.sqlite.SQLite;

public final class CustomFishingTraps extends JavaPlugin {

    public static CustomFishingTraps plugin;
    public static BukkitAudiences adventure;
    private static Database db;
    private static BukkitCustomFishingPlugin customFishingApi;


    @Override
    public void onEnable() {
        this.plugin = this;
        adventure = BukkitAudiences.create(this);




        Bukkit.getLogger().info("Test!");
        // Call other class
        getServer().getPluginManager().registerEvents(new CustomFishingTrapsInteract(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new TownyNewDayListener(), this);
        // Plugin start up logic
        getLogger().info("CustomFishingTraps has loaded! Testing reload.");
        // Decent Holograms API loaded, no idea why I couldn't use else???
        if (DecentHologramsAPI.isRunning()) {
            getLogger().info("Decent Holograms API has loaded for CustomFishingTraps!");
        if (!DecentHologramsAPI.isRunning()) {
            getLogger().info("Decent Holograms API hasn't loaded for CustomFishingTraps!");
        // Items adder API Loaded, apparently areItemsLoaded is deprecated, but everything else I tried was broken.
        if (ItemsAdder.areItemsLoaded())
            getLogger().info("Items adder API is loaded for Custom Fishing Traps!");
        else
            getLogger().info("Items adder API isn't loaded for Custom Fishing Traps!"); }
        // Custom Fishing API Loaded
            this.customFishingApi = BukkitCustomFishingPlugin.getInstance();
        if (customFishingApi == null)
            getLogger().info("Custom Fishing api hasn't loaded!");
        else
            getLogger().info("Custom Fishing api has loaded!");
        }

        // Reload the plugin configuration

        // Load the database for storing fishing traps
        this.db = new SQLite(this);
        this.db.load();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Save the database
        // Unload methods
        getLogger().info("CustomFishingTraps has been disabled!");
    }

    // Add reloadConfig method so we can handle reloading ourselves
    @Override
    public void reloadConfig() {
        ConfigUtil.reload();
    }
    public static CustomFishingTraps getInstance() {
        return plugin;
    }
    public static Database getDatabase() {
        return db;
    }
    public static BukkitCustomFishingPlugin getCustomFishingApi() {
        return customFishingApi;
    }
}
