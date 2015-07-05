package com.minehut.core.util.common.region;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Created by Luke on 3/19/15.
 */
public class Zone {
	public enum ZoneType {
		cube,
		cyl;
	}

	private ZoneType zoneType;
	private String id;

	/* Cuboid */
	private Location loc1;
	private Location loc2;

	/* Cylinder */
	private Location center;
	private int radius;

	public Zone(String id, Location loc1, Location loc2) {
		this.id = id;
		this.loc1 = loc1;
		this.loc2 = loc2;

		this.zoneType = ZoneType.cube;
	}

	public Zone(String id, Location center, int radius) {
		this.id = id;
		this.center = center;
		this.radius = radius;

		this.zoneType = ZoneType.cyl;
	}

	public boolean insideRegion(Location loc) {
		/* Cuboid */
		if(this.zoneType == ZoneType.cube) {
			if (loc.getWorld() == loc1.getWorld()) {

				double minX = Math.min(loc1.getX(), loc2.getX());
				double maxX = Math.max(loc1.getX(), loc2.getX());

				double minY = Math.min(loc1.getY(), loc2.getY());
				double maxY = Math.max(loc1.getY(), loc2.getY());

				double minZ = Math.min(loc1.getZ(), loc2.getZ());
				double maxZ = Math.max(loc1.getZ(), loc2.getZ());

				if (loc.getX() >= minX && loc.getX() <= maxX &&
						loc.getY() >= minY && loc.getY() <= maxY &&
						loc.getZ() >= minZ && loc.getZ() <= maxZ) {
					return true;
				}
			} else {
				return false;
			}
		} else if (this.zoneType == ZoneType.cyl) {
			if (distanceHorizontal(center, loc) <= this.radius) {
				return true;
			} else {
				return false;
			}
		}

		/* Unknown type */
		return false;
	}

	public boolean insideRegionAllowBelow(Location loc) {
		if (loc.getWorld() == loc1.getWorld()) {

			double minX = Math.min(loc1.getX(), loc2.getX());
			double maxX = Math.max(loc1.getX(), loc2.getX());

			double maxY = Math.max(loc1.getY(), loc2.getY());

			double minZ = Math.min(loc1.getZ(), loc2.getZ());
			double maxZ = Math.max(loc1.getZ(), loc2.getZ());

			if (loc.getX() >= minX && loc.getX() <= maxX &&
					loc.getY() <= maxY &&
					loc.getZ() >= minZ && loc.getZ() <= maxZ ) {
				return true;
			}
		}
		return false;
	}

	public void remove(Material material) {
		double minX = Math.min(loc1.getX(), loc2.getX());
		double maxX = Math.max(loc1.getX(), loc2.getX());

		double minY = Math.min(loc1.getY(), loc2.getY());
		double maxY = Math.max(loc1.getY(), loc2.getY());

		double minZ = Math.min(loc1.getZ(), loc2.getZ());
		double maxZ = Math.max(loc1.getZ(), loc2.getZ());

		World world = loc1.getWorld();

		for (int x = (int) minX; x <= maxX; x++) {
			for (int y = (int) minY; y <= maxY; y++) {
				for (int z = (int) minZ; z <= maxZ; z++) {
					Block block = world.getBlockAt(x, y, z);
					if (block.getType() == material) {
						block.setType(Material.AIR);
					}
				}
			}
		}
	}

	public static double distanceHorizontal(Location p1, Location p2) {
		double x1 = Math.max(p1.getX(), p2.getX());
		double x2 = Math.min(p1.getX(), p2.getX());

		double z1 = Math.max(p1.getZ(), p2.getZ());
		double z2 = Math.min(p1.getZ(), p2.getZ());

		double distanceX = Math.abs(x1 - x2);
		double distanceZ = Math.abs(z1 - z2);

		return Math.max(distanceX, distanceZ);
	}

	public ZoneType getZoneType() {
		return zoneType;
	}

	public String getId() {
		return id;
	}
}
