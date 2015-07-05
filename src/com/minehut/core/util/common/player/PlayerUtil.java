package com.minehut.core.util.common.player;

import com.minehut.core.util.common.items.ArmorType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * Created by Luke on 10/18/14.
 */
public class PlayerUtil {

	public static void ejectAll() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getVehicle() != null) {
				player.eject();
			}
		}
	}


    public static void clearAll(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setHealth(player.getMaxHealth()); //For different health level support
        player.setFallDistance(0);
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.setExp(0);

		player.setAllowFlight(false);
		player.setFlying(false);
	}

    public static void heal(Player player) {
        player.setHealthScale(player.getMaxHealth());
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
        player.setFireTicks(0);
    }

    public static boolean has(Player player, Material material) {
        org.bukkit.inventory.PlayerInventory inventory = player.getInventory();

        if (inventory.contains(material)) {
            return true;
        }

        for (ItemStack itemStack : inventory.getArmorContents()) {
            if (itemStack.getType() == material) {
                return true;
            }
        }
        return false;
    }

    public static void equipArmor(Player player, ItemStack itemStack) {
        ArmorType armorType = ArmorType.getArmorType(itemStack);

        if (armorType == ArmorType.helmet) {
            player.getInventory().setHelmet(itemStack);
        } else if (armorType == ArmorType.chestplate) {
            player.getInventory().setChestplate(itemStack);
        } else if (armorType == ArmorType.leggings) {
            player.getInventory().setLeggings(itemStack);
        } else if (armorType == ArmorType.boots) {
            player.getInventory().setBoots(itemStack);
        }
    }
}
