package com.minehut.core.util.common.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Luke on 11/26/14.
 */
public enum ArmorType {
    helmet, chestplate, leggings, boots;

    public static com.minehut.core.util.common.items.ArmorType getArmorType(ItemStack itemStack) {
        Material material = itemStack.getType();

        if (material == Material.LEATHER_HELMET || material == Material.CHAINMAIL_HELMET || material == Material.IRON_HELMET || material == Material.GOLD_HELMET || material == Material.DIAMOND_HELMET) {
            return com.minehut.core.util.common.items.ArmorType.helmet;
        } else if (material == Material.LEATHER_CHESTPLATE || material == Material.CHAINMAIL_CHESTPLATE || material == Material.IRON_CHESTPLATE || material == Material.GOLD_CHESTPLATE || material == Material.DIAMOND_CHESTPLATE) {
            return com.minehut.core.util.common.items.ArmorType.chestplate;
        } else if (material == Material.LEATHER_LEGGINGS || material == Material.CHAINMAIL_LEGGINGS || material == Material.IRON_LEGGINGS || material == Material.GOLD_LEGGINGS || material == Material.DIAMOND_LEGGINGS) {
            return com.minehut.core.util.common.items.ArmorType.leggings;
        } else if (material == Material.LEATHER_BOOTS || material == Material.CHAINMAIL_BOOTS || material == Material.IRON_BOOTS || material == Material.GOLD_BOOTS || material == Material.DIAMOND_BOOTS) {
            return com.minehut.core.util.common.items.ArmorType.boots;
        } else {
            return null;
        }
    }

    public static com.minehut.core.util.common.items.ArmorType getArmorType(Material material) {
        if (material == Material.LEATHER_HELMET || material == Material.CHAINMAIL_HELMET || material == Material.IRON_HELMET || material == Material.GOLD_HELMET || material == Material.DIAMOND_HELMET) {
            return com.minehut.core.util.common.items.ArmorType.helmet;
        } else if (material == Material.LEATHER_CHESTPLATE || material == Material.CHAINMAIL_CHESTPLATE || material == Material.IRON_CHESTPLATE || material == Material.GOLD_CHESTPLATE || material == Material.DIAMOND_CHESTPLATE) {
            return com.minehut.core.util.common.items.ArmorType.chestplate;
        } else if (material == Material.LEATHER_LEGGINGS || material == Material.CHAINMAIL_LEGGINGS || material == Material.IRON_LEGGINGS || material == Material.GOLD_LEGGINGS || material == Material.DIAMOND_LEGGINGS) {
            return com.minehut.core.util.common.items.ArmorType.leggings;
        } else if (material == Material.LEATHER_BOOTS || material == Material.CHAINMAIL_BOOTS || material == Material.IRON_BOOTS || material == Material.GOLD_BOOTS || material == Material.DIAMOND_BOOTS) {
            return com.minehut.core.util.common.items.ArmorType.boots;
        } else {
            return null;
        }
    }
}
