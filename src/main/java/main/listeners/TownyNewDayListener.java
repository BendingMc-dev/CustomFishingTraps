package main.listeners;

import com.palmergames.bukkit.towny.event.NewDayEvent;
import main.DataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownyNewDayListener implements Listener {
    // @TODO add listener for new day event

    @EventHandler
    public void onNewDay(NewDayEvent event) {
        DataManager.updateFishingTraps();
        // @TODO add logic for new day event
    }
}
