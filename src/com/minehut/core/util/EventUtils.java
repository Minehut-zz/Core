package com.minehut.core.util;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by luke on 7/2/15.
 */
public class EventUtils {

    public static boolean isItemClickWithDisplayName(PlayerInteractEvent event) {
        if(event.getAction() == Action.LEFT_CLICK_AIR
                || event.getAction() == Action.LEFT_CLICK_BLOCK
                || event.getAction() == Action.RIGHT_CLICK_AIR
                || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {
                if (event.getItem() != null) {
                    if (event.getItem().getItemMeta() != null) {
                        if (event.getItem().getItemMeta().getDisplayName() != null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String getDisplayName(ItemStack itemStack) {
        if (itemStack != null) {
            if (itemStack.getItemMeta() != null) {
                if (itemStack.getItemMeta().getDisplayName() != null) {
                    return itemStack.getItemMeta().getDisplayName();
                }
            }
        }
        return null;
    }
}
