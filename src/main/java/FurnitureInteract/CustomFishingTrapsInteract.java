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
        // Variables
        Player player = event.getPlayer();
        CustomFurniture clickedFurniture = event.getFurniture();
        String fishingTrap = "bmc:fishing_block";
        // Event
        String Id = clickedFurniture.getNamespacedID();
        if(Id.equals(fishingTrap))
           Bukkit.getLogger().info(player +"\nClicked fishing trap! Id =\n" + Id);
        else
            Bukkit.getLogger().info(player +"\nDidn't click fishing trap! Id =\n" + Id);

        }
    }




