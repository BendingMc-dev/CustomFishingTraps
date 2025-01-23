//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package main.sqlite;

import main.CustomFishingTraps;
import main.objects.FishingTrap;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import static java.lang.Double.parseDouble;

public abstract class Database {
    static CustomFishingTraps plugin;
    Connection connection;
    public String table = "fishing_traps";

    public Database(CustomFishingTraps instance) {
        plugin = instance;
    }

    public Connection getSQLConnection() {
        return null;
    }

    public abstract void load();

    public void initialize() {
        this.connection = this.getSQLConnection();

        try {
            PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM " + this.table + " WHERE id = ?");
            ResultSet rs = ps.executeQuery();
            this.close(ps, rs);
        } catch (SQLException var3) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", var3);
        }

    }

    public List<FishingTrap> getActiveFishingTraps() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            List<FishingTrap> fishingTraps = new ArrayList<>();
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM fishing_traps WHERE active = 1");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                UUID owner = UUID.fromString(rs.getString("owner"));
                String key = rs.getString("key");
                Location location = deserializeLocation(rs.getString("location"));
                boolean active = rs.getInt("active") == 1;
                List<ItemStack> items = deserializeItems(rs.getString("items"));
                int maxItems = rs.getInt("maxItems");
                ItemStack bait = deserializeItems(rs.getString("bait")).get(0);

                fishingTraps.add(new FishingTrap(key, id, owner, location, active, items, maxItems, bait));
            }
            return fishingTraps;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to deserialize items", e);
            e.printStackTrace();
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
                ItemStack bait = deserializeItems(rs.getString("bait")).get(0);

                return new FishingTrap(key, UUID.fromString(id), owner, location, active, items, maxItems, bait);
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to deserialize items", e);
            e.printStackTrace();
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
        String[] location = locationString.split(":");
        return new Location(plugin.getServer().getWorld(location[0]), parseDouble(location[1]), parseDouble(location[2]), parseDouble(location[3]));
    }

    private List<ItemStack> deserializeItems(String itemsString) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(itemsString));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            int size = dataInput.readInt();
            List<ItemStack> itemStacks = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                itemStacks.add((ItemStack) dataInput.readObject());
            }
            dataInput.close();

            if (itemStacks.size() == 0) {
                itemStacks.add(ItemStack.empty());
            }

            return itemStacks;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to load item stacks.", e);
        }
    }

    public void saveFishingTrap(FishingTrap trap) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO fishing_traps(id, owner, key, location, active, items, maxItems, bait) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, trap.getId().toString());
            ps.setString(2, trap.getOwner().toString());
            ps.setString(3, trap.getKey());
            ps.setString(4, serializeLocation(trap.getLocation()));
            ps.setInt(5, trap.isActive() ? 1 : 0);
            ps.setString(6, serializeItems(trap.getItems()));
            ps.setInt(7, trap.getMaxItems());
            ps.setString(8, serializeItems(Collections.singletonList(trap.getBait())));

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
        return "world" + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ();
    }

    private String serializeItems(List<ItemStack> items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(items.size());
            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public void deleteFishingTrapById(String id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("DELETE FROM fishing_traps WHERE id = ?");
            ps.setString(1, id);
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
