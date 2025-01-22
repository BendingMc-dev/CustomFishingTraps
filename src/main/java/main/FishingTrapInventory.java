package main;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import main.objects.FishingTrap;

import java.util.List;

public class FishingTrapInventory implements InventoryHolder {

    private final Inventory inventory;
    private final FishingTrap trap;
    private static List<ItemStack> items;

    public FishingTrapInventory(FishingTrap trap, CustomFishingTraps plugin) {
        this.inventory = plugin.getServer().createInventory(this, 36, "Fishing Trap");
        this.trap = trap;
        items = trap.getItems();

        // Add items to the inventory
        for (ItemStack item : items) {
            inventory.addItem(item);
        }
    }

    public void takeItem(Player player, ItemStack itemStack) {
        items.remove(itemStack);
        player.getInventory().addItem(itemStack);
        // @TODO Method to take an item from the inventory
    }

    public void reloadInventory() {
        inventory.clear();
        for (ItemStack item : items) {
            inventory.addItem(item);
        }
        // @TODO Method to reload the inventory
    }

    public void updateFishingTrap() {
        trap.setItems(items);
        CustomFishingTraps.getDatabase().saveFishingTrap(trap);
        // @TODO Method to update the fishing trap
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
