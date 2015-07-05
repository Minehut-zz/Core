package com.minehut.core.util.common.velocity;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

/**
 * Created by Luke on 2/21/15.
 */
public class UtilAction {
	public static void velocity(Entity ent, double str, double yAdd, double yMax, boolean groundBoost)
	{
		velocity(ent, ent.getLocation().getDirection(), str, false, 0.0D, yAdd, yMax, groundBoost);
	}

	public static void velocity(Entity ent, Vector vec, double str, boolean ySet, double yBase, double yAdd, double yMax, boolean groundBoost)
	{
		if ((Double.isNaN(vec.getX())) || (Double.isNaN(vec.getY())) || (Double.isNaN(vec.getZ())) || (vec.length() == 0.0D)) {
			return;
		}

		if (ySet) {
			vec.setY(yBase);
		}

		vec.normalize();
		vec.multiply(str);

		vec.setY(vec.getY() + yAdd);

		if (vec.getY() > yMax) {
			vec.setY(yMax);
		}
		if ((groundBoost) &&
				(ent.isOnGround())) {
			vec.setY(vec.getY() + 0.2D);
		}

		ent.setFallDistance(0.0F);
		ent.setVelocity(vec);
	}
}
