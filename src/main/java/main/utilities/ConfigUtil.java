package main.utilities;

import main.CustomFishingTraps;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigUtil {
    public static YamlConfiguration getConfig(String configName) {
        File file = new File(CustomFishingTraps.plugin.getDataFolder(), configName);
        if (!file.exists()) CustomFishingTraps.plugin.saveResource(configName, false);
        return YamlConfiguration.loadConfiguration(file);
    }
    public static void reload() {
        // @TODO add methods to load and unload features
    }

}
