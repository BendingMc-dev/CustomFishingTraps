package org.listeners;

import org.FishingTrapInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // We're getting the clicked inventory to avoid situations where the player
        // already has a stone in their inventory and clicks that one.
        Inventory inventory = event.getClickedInventory();
        // Add a null check in case the player clicked outside the window.
        if (inventory == null || !(inventory.getHolder(false) instanceof FishingTrapInventory myInventory)) {
            return;
        }

        event.setCancelled(true);

        if (!event.getClick().isLeftClick()) return;

        ItemStack clicked = event.getCurrentItem();
        if (clicked != null && clicked.getType() == Material.COD) { //@ TODO check if the item is a fish
            myInventory.takeItem((Player) event.getWhoClicked(), clicked);
            myInventory.reloadInventory();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getHolder() instanceof FishingTrapInventory myInventory) {
            myInventory.updateFishingTrap();
        }
    }

}
