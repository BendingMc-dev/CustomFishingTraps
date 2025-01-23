package main;

import main.utilities.AdventureUtil;
import net.momirealms.customfishing.api.BukkitCustomFishingPlugin;
import net.momirealms.customfishing.api.mechanic.context.Context;
import net.momirealms.customfishing.api.mechanic.context.ContextKeys;
import net.momirealms.customfishing.api.mechanic.effect.Effect;
import net.momirealms.customfishing.api.mechanic.loot.Loot;
import net.momirealms.customfishing.api.mechanic.loot.LootType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import main.objects.FishingTrap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class DataManager {
    public static void handleFishingTrapInteract(Player player, Entity entity) {
        if (CustomFishingTraps.getDatabase().getFishingTrapById(entity.getUniqueId().toString()) != null) {
            // Method to handle interacting with a fishing trap
            UUID playerID = player.getUniqueId();
            FishingTrap fishingTrap = CustomFishingTraps.getDatabase().getFishingTrapById(entity.getUniqueId().toString());
            if (fishingTrap.getOwner().equals(playerID)) {
                // @TODO Method to handle interacting with your own fishing trap
                AdventureUtil.consoleMessage("Opened fishing trap with id:" + fishingTrap.getId());
                player.openInventory(new FishingTrapInventory(fishingTrap, CustomFishingTraps.getInstance()).getInventory());
            } else {
                // @TODO Method to handle interacting with someone else's fishing trap
            }
        } else {
            // Method to handle interacting with a fishing trap that does not have stored data
            FishingTrap fishingTrap = handleCreateFishingTrap(player, entity);
            AdventureUtil.consoleMessage("Created a new fishing trap! with id:" + fishingTrap.getId());
            player.openInventory(new FishingTrapInventory(fishingTrap, CustomFishingTraps.getInstance()).getInventory());
        }
    }

    public static FishingTrap handleCreateFishingTrap(Player player, Entity entity) {
        // Method to handle creating a fishing trap
        FishingTrap fishingTrap = new FishingTrap("fishing_trap", // New fishing trap
                entity.getUniqueId(),
                player.getUniqueId(),
                entity.getLocation(),
                false,
                new ArrayList<>(),
                64, // Get from Config eventually
                null); // Get from Config eventually
        fishingTrap.checkActive(); // Check if the trap is active
        CustomFishingTraps.getDatabase().saveFishingTrap(fishingTrap); // Save the trap to the database!
        return fishingTrap;
    }

    public static void updateFishingTraps() {
        BukkitCustomFishingPlugin api = CustomFishingTraps.getCustomFishingApi();
        Context<Player> context = Context.player(null);
        context.arg(ContextKeys.SURROUNDING, "water");
        for (FishingTrap trap : CustomFishingTraps.getDatabase().getActiveFishingTraps()) {
            context.arg(ContextKeys.LOCATION, trap.getLocation());
            context.arg(ContextKeys.OTHER_LOCATION, trap.getLocation());
            Loot loot = api.getLootManager().getNextLoot(Effect.newInstance(), context);
            if (loot.type() == LootType.ITEM) {
                ItemStack itemStack = api.getItemManager().buildInternal(context, loot.id());
                trap.addItem(itemStack);
                CustomFishingTraps.getDatabase().saveFishingTrap(trap);
            }
        }
    }
}
