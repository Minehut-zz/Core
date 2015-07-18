package com.minehut.core.rules;

import com.minehut.core.Core;
import com.minehut.core.util.EventUtils;
import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.chat.F;
import com.minehut.core.util.common.inventory.InvUtils;
import com.minehut.core.util.common.items.ItemStackFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by luke on 7/12/15.
 */
public class RuleManager implements Listener {
    private Inventory inv;

    public RuleManager(Core core) {
        this.inv = Bukkit.getServer().createInventory(null, 36, C.red + C.bold + "Minehut Rules");

        this.addRule(C.red + C.bold + "No Foul Language",
                Arrays.asList(
                        "Please keep our chat waves acceptable",
                        "for children of all ages."));

        this.addRule(C.red + C.bold + "No Spamming",
                Arrays.asList(
                        "If somebody didn't answer you",
                        "the first time, try rephrasing.",
                        "Repeating the same message is only",
                        "irritating to those around you."));

        this.addRule(C.red + C.bold + "No Caps Abuse",
                Arrays.asList(
                        "We understand you like to SHOUT",
                        "by using CAPS, but it gets annoying."));

        this.addRule(C.red + C.bold + "No Team Griefing",
                Arrays.asList(
                        "Don't do anything to cause harm/grief",
                        "to a player on your team."));

        this.addRule(C.red + C.bold + "No Advertising",
                Arrays.asList(
                        "Please do not advertise any servers",
                        "not owned by Minehut. You are allowed to",
                        "advertise your kingdom in a non-spam manor."));

        this.addRule(C.red + C.bold + "No Hacked Clients",
                Arrays.asList(
                        "Use of hacked clients are stricly forbidden."));

        this.addRule(C.red + C.bold + "No Advantage Granting Mods ",
                Arrays.asList(
                        "We do not permit any modifications to minecraft",
                        "that grant an edge when it comes to PvP.",
                        "Optifine, 5zig PvP Mod, Forge and bspkrs'",
                        "are all examples of acceptable mods."));

        this.addRule(C.red + C.bold + "No Inappropriate skins/capes",
                Arrays.asList(
                        "Sexual or offensive skins are",
                        "both bannable offenses."));

        this.addRule(C.red + C.bold + "No Harassment or Racism",
                Arrays.asList(
                        "Any type of harassment/racism",
                        "will result in immediate",
                        "punishment, along with",
                        "losing all chances of being promoted",
                        "to the staff team."));

        Bukkit.getServer().getPluginManager().registerEvents(this, core);

        new RulesCommand(core, this);
    }

    public void openInv(Player player) {
        player.openInventory(this.inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        try {
            if (event.getInventory().getName().equalsIgnoreCase(this.inv.getName())) {
                event.setCancelled(true);
            }
        } catch (Exception e) {

        }
    }

    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent event) {
        try {
            if (event.getDestination().getName().equalsIgnoreCase(this.inv.getName())) {
                event.setCancelled(true);
            }
        } catch (Exception e) {

        }
    }

    @EventHandler
    public void onRulesItemClick(PlayerInteractEvent event) {
        if (Core.getInstance().getStatusManager().getStatusUploader().isUploading()) {
            if (EventUtils.isItemClickWithDisplayName(event)) {
                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(RuleManager.getRulesItem().getItemMeta().getDisplayName())) {
                    event.setCancelled(true);
                    this.openInv(event.getPlayer());
                }
            }
        }
    }

    public void addRule(String header, List<String> desc) {

        ArrayList<String> betterDesc = new ArrayList<>();

        for (String s : desc) {
            betterDesc.add(C.yellow + s);
        }

        ItemStack itemStack = ItemStackFactory.createItem(Material.SIGN, header, betterDesc);

        /* Index of next slot + row skip */
        int i = InvUtils.getAmountOfItems(inv) + 9 + 1;

        if (i == 0 || i == 9 || i == 18 || i == 25) {
            i++;
        }
        else if (i == 8 || i == 17 || i == 24) {
            i += 2;
        }

        F.log("creating rule in slot " + i);
        inv.setItem(i, itemStack);

        i++;
    }

    public static ItemStack getRulesItem() {
        return ItemStackFactory.createItem(Material.BOOK, C.red + C.bold + "Rules");
    }
}
