package com.minehut.core.util.common.particles;

import org.bukkit.Location;

/**
 * Created by luke on 7/27/15.
 */
public class ParticleUtils {
    public static void circle(Location location, ParticleEffect particleEffect, float radius, int particles) {
        Location location1 = location;
        Location location2 = location;
        Location location3 = location;
        for (int i = 0; i < particles; i++) {
            double angle, x, z;
            angle = 2 * Math.PI * i / particles;
            x = Math.cos(angle) * radius;
            z = Math.sin(angle) * radius;
            location1.add(x, 0, z);
            location2.add(x, -0.66, z);
            location3.add(x, -1.33, z);
            particleEffect.display(0, 0, 0, 0, 1, location1, 20);
            particleEffect.display(0, 0, 0, 0, 1, location2, 20);
            particleEffect.display(0, 0, 0, 0, 1, location3, 20);
            location1.subtract(x, 0, z);
            location2.subtract(x, -0.66, z);
            location3.subtract(x, -1.33, z);
        }
    }
}
