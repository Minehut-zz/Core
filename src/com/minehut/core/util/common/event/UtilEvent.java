package com.minehut.core.util.common.event;

import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Luke on 2/6/15.
 */
public class UtilEvent {
	public static String getCause(EntityDamageEvent.DamageCause d) {
		if (d == EntityDamageEvent.DamageCause.VOID) {
			return "void";
		} else if (d == EntityDamageEvent.DamageCause.PROJECTILE) {
			return "projectile";
		} else if (d == EntityDamageEvent.DamageCause.DROWNING) {
			return "water";
		} else if (d == EntityDamageEvent.DamageCause.FIRE) {
			return "fire";
		} else if (d == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
			return "slain";
		} else if (d == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
			return "explosion";
		} else {
			return "null";
		}
	}
}
