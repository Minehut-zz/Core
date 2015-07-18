package com.minehut.core.util.common.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by luke on 7/13/15.
 */
public class InvUtils {

    public static int getAmountOfItems(Inventory inventory) {
        int i = 0;

        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && itemStack.getType() != null && itemStack.getType() != Material.AIR) {
                i++;
            }
        }

        return i;
    }
}
