//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.sqllite;

import org.CustomFishingTraps;
import org.FishingTrap;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import static java.lang.Double.parseDouble;

public abstract class Database {
    static CustomFishingTraps plugin;
    Connection connection;
    public String table = "fishing_traps";
    public int tokens = 0;

    public Database(CustomFishingTraps instance) {
        plugin = CustomFishingTraps.getInstance();
    }

    public Connection getSQLConnection() {
        return null;
    }

    public abstract void load();

    public void initialize() {
        this.connection = this.getSQLConnection();

        try {
            PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM " + this.table + " WHERE player = ?");
            ResultSet rs = ps.executeQuery();
            this.close(ps, rs);
        } catch (SQLException var3) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", var3);
        }

    }

    public FishingTrap getFishingTrapById(String id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM fishing_traps WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UUID owner = UUID.fromString(rs.getString("owner"));
                String key = rs.getString("key");
                Location location = deserializeLocation(rs.getString("location"));
                boolean active = rs.getInt("active") == 1;
                List<ItemStack> items = deserializeItems(rs.getString("items"));
                int maxItems = rs.getInt("maxItems");
                List<ItemStack> bait = deserializeItems(rs.getString("bait"));
                int maxBait = rs.getInt("maxBait");

                return new FishingTrap(key, owner, location, active, items, maxItems, bait, maxBait);
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return null;
    }

    private Location deserializeLocation(String locationString) {
        String[] location = locationString.split(",");
        return new Location(CustomFishingTraps.getInstance().getServer().getWorld(location[0]), parseDouble(location[1]), parseDouble(location[2]), parseDouble(location[3]));
    }

    private List<ItemStack> deserializeItems(String itemsString) {
        String[] items = itemsString.split(",");
        List<ItemStack> itemStacks = new ArrayList<>();
        for (String item : items) {
            // Deserialize the item string into an ItemStack
        }
        return itemStacks;
    }

    public void saveFishingTrap(FishingTrap trap) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO fishing_traps(id, owner, key, location, active, items, maxItems, bait, maxBait) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, trap.getId().toString());
            ps.setString(2, trap.getOwner().toString());
            ps.setString(3, trap.getKey());
            ps.setString(4, serializeLocation(trap.getLocation()));
            ps.setInt(5, trap.isActive() ? 1 : 0);
            ps.setString(6, serializeItems(trap.getItems()));
            ps.setInt(7, trap.getMaxItems());
            ps.setString(8, serializeItems(trap.getBait()));
            ps.setInt(9, trap.getMaxBait());

            ps.executeUpdate();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
    }

    private String serializeLocation(Location location) {
        return location.getWorld().toString() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ();
    }

    private String serializeItems(List<ItemStack> items) {
        // Implement the logic to serialize the items list into a string
        return null;
    }
    public void close(PreparedStatement ps, ResultSet rs) {
        try {
            if (ps != null) {
                ps.close();
            }

            if (rs != null) {
                rs.close();
            }
        } catch (SQLException var4) {
            Error.close(plugin, var4);
        }

    }
}
