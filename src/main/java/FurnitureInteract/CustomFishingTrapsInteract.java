package FurnitureInteract;


import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.Events.FurnitureInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;



public class CustomFishingTrapsInteract implements Listener {
    public CustomFishingTrapsInteract() {
    }
    @EventHandler
    public void onInteract(FurnitureInteractEvent event) {
        Player player = event.getPlayer();
        CustomFurniture clickedFurniture = event.getFurniture();
        String fishingTrap = "bmc:fishing_block";
        if(clickedFurniture.equals(fishingTrap))
           Bukkit.getLogger().info(player +"Clicked fishing trap!");
        else
            Bukkit.getLogger().info(player +"Didn't click fishing trap!");
        }
    }

