package com.minehut.core.util.common.location;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Created by luke on 7/21/15.
 */
public class LocationUtils {
    public static Location modifyForAir(Location location) {
        while (location.getBlock().getType() != Material.AIR) {
            location.setY(location.getY() + 1);
        }
        return location;
    }
}
