package org;

import FurnitureInteract.CustomFishingTrapsInteract;
import dev.lone.itemsadder.api.ItemsAdder;
import eu.decentsoftware.holograms.api.DecentHologramsAPI;
import net.momirealms.customfishing.api.BukkitCustomFishingPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomFishingTraps extends JavaPlugin {


    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Test!");
        // Call other class
        getServer().getPluginManager().registerEvents(new CustomFishingTrapsInteract(), this);
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
            BukkitCustomFishingPlugin customFishingApi = BukkitCustomFishingPlugin.getInstance();
        if (customFishingApi == null)
            getLogger().info("Custom Fishing api hasn't loaded!");
        else
            getLogger().info("Custom Fishing api has loaded!");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("CustomFishingTraps has been disabled!");
    }
}
