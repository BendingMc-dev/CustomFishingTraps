package FishingTrapData;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FishingTrap {

    public static FishingTrap EMPTY = new FishingTrap("null",null, null, false, null, 0, null, 0);

    protected UUID id;
    protected UUID owner;
    protected String key;
    protected @NotNull Map<String, Object> location;
    protected boolean active;
    protected List<ItemStack> items;
    protected int maxItems;
    protected List<ItemStack> bait;
    protected int maxBait;

    public FishingTrap(String key, UUID owner, Location location, boolean active, List<ItemStack> items, int maxItems, List<ItemStack> bait, int maxDDDBait) {
        this.key = key;
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.location = location.serialize();
        this.active = active;
        this.items = items;
        this.maxItems = maxItems;
        this.bait = bait;
        this.maxBait = maxBait;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getKey() {
        return key;
    }

    public @NotNull Map<String, Object> getLocation() {
        return location;
    }

    public boolean isActive() {
        return active;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public int getMaxItems() {
        return maxItems;
    }

    public List<ItemStack> getBait() {
        return bait;
    }

    public int getMaxBait() {
        return maxBait;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLocation(@NotNull Map<String, Object> location) {
        this.location = location;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }

    public void setBait(List<ItemStack> bait) {
        this.bait = bait;
    }

    public void setMaxBait(int maxBait) {
        this.maxBait = maxBait;
    }

    public boolean isFull() {
        return items.size() >= maxItems;
    }

    public boolean isBaitFull() {
        return bait.size() >= maxBait;
    }

    public void addItem(ItemStack item) {
        items.add(item);
    }

    public void addBait(ItemStack item) {
        bait.add(item);
    }
}