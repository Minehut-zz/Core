package com.minehut.core.util.common.items;

import org.bukkit.enchantments.Enchantment;

public class GameItemStackEnchantment {
	public Enchantment enchantment;
	public int level;
	public GameItemStackEnchantment(Enchantment ench, int lvl) {
		enchantment = ench;
		level = lvl;
	}
}