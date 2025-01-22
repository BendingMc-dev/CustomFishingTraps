package main.listeners;


import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.Events.FurnitureBreakEvent;
import dev.lone.itemsadder.api.Events.FurnitureInteractEvent;
import main.CustomFishingTraps;
import main.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class CustomFishingTrapsInteract implements Listener {
    public final NamespacedKey KEY;
    public CustomFishingTrapsInteract (CustomFishingTraps customFishingTraps) {
        this.KEY = new NamespacedKey(customFishingTraps, "trapId");

    }
    @EventHandler
    public void onInteract(FurnitureInteractEvent event) {
        // Variables
        Player player = event.getPlayer();
        CustomFurniture clickedFurniture = event.getFurniture();
        String fishingTrap = "bmc:fishing_block";
        // Event
        String Id = clickedFurniture.getNamespacedID();
        if (Id.equals(fishingTrap)) {
            Bukkit.getLogger().info(player + "Clicked fishing trap! Id =" + Id);
            DataManager.handleFishingTrapInteract(player, clickedFurniture.getEntity());
        } else {
            Bukkit.getLogger().info(player + "Didn't click fishing trap! Id =" + Id);
        }
    }

    @EventHandler
    public void onBreak(FurnitureBreakEvent event) {
        CustomFurniture clickedFurniture = event.getFurniture();
        String fishingTrap = "bmc:fishing_block";
        if (clickedFurniture.getNamespacedID().equals(fishingTrap)) {
            CustomFishingTraps.getDatabase().deleteFishingTrapById(clickedFurniture.getEntity().getUniqueId().toString());
        }
        return;
    }
}