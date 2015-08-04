package com.minehut.core.util.common.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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

    public static void removeMaterialAmount(PlayerInventory inv, Material type, int amount) {
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type) {
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                } else {
                    inv.remove(is);
                    amount = -newamount;
                    if (amount == 0) break;
                }
                return;
            }
        }
    }


}
