package com.minehut.core.util;

import com.minehut.commons.common.chat.F;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by luke on 6/19/15.
 */
public class StatusUtils {
    public static FileConfiguration getStatusConfig() {
        File statusConfigFile = new File("status.yml");

        if (!statusConfigFile.exists()) {
            /* Kingdoms won't be using this */
            F.log("Couldn't find status.yml");
            return null;
        }

        return YamlConfiguration.loadConfiguration(statusConfigFile);
    }
}
