package com.minehut.core.status.menu;

import com.minehut.core.Core;
import com.minehut.core.status.ServerInfo;
import com.minehut.core.status.download.StatusDownloader;
import com.minehut.core.util.EventUtils;
import com.minehut.core.util.common.bungee.Bungee;
import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.chat.F;
import com.minehut.core.util.common.items.ItemStackFactory;
import com.minehut.core.util.common.sound.S;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by luke on 6/22/15.
 */
public class ServerMenuManager implements Listener {
    private StatusDownloader statusDownloader;
    private ItemStack selectorItem;

    private ArrayList<ServerCluster> clusters;

    public Inventory home;

    public ServerMenuManager(StatusDownloader statusDownloader) {
        this.clusters = new ArrayList<>();
        this.statusDownloader = statusDownloader;
        this.selectorItem = getServerSelectorItem();

        Bukkit.getServer().getPluginManager().registerEvents(this, Core.getInstance());

        this.clusters.add(new ServerCluster(this, "kingdom",
                Material.CHEST,
                C.yellow + C.bold + "Kingdom Servers",
                Arrays.asList(
                        "",
                        C.gray + "Collection of Free Servers",
                        C.gray + "hosted on Minehut!",
                        C.gray + "Create your own server",
                        C.gray + "with /create (name)",
                        ""
                ),
                true,
                false,
                4
        ));

//        this.clusters.add(new ServerCluster(this, "kingdom",
//                Material.ENDER_CHEST,
//                C.aqua + C.bold + "FEATURED " + C.yellow + C.bold + "Kingdom Servers",
//                Arrays.asList(
//                        "",
//                        C.gray + "Staff picked Kingdoms",
//                        C.gray + "that go above and beyond",
//                        C.gray + "to create fun, original",
//                        C.gray + "gameplay here on Minehut.",
//                        ""
//                ),
//                true,
//                true,
//                5
//        ));

        this.clusters.add(new ServerCluster(this, "tnt",
                Material.TNT,
                C.yellow    + "TNT Wars",
                Arrays.asList(
                        "",
                        C.gray + "Create cannons and ",
                        C.gray + "blast your enemies",
                        C.gray + "until their lives run out.",
                        ""
                ),
                false,
                false,
                25
        ));

//        this.clusters.add(new ServerCluster(this, "tribes",
//                Material.LEATHER_CHESTPLATE,
//                C.yellow + "Tribes",
//                Arrays.asList(
//                        "",
//                        C.gray + "Clash of Clans in Minecraft",
//                        ""
//                ),
//                false,
//                false,
//                22,
//                true,
//                true
//        ));

        this.clusters.add(new ServerCluster(this, "vanilla",
                Material.GRASS,
                C.yellow + "Minehut Vanilla Server",
                Arrays.asList(
                        "",
                        C.gray + "Kick back and chill",
                        C.gray + "on Minehut's vanilla server.",
                        ""
                ),
                false,
                false,
                22
        ));

        this.clusters.add(new ServerCluster(this, "warzone",
                Material.BOW,
                C.yellow + "Warzone",
                Arrays.asList(
                        "",
                        C.gray + "Fight with your team to",
                        C.gray + "destroy the wool core located",
                        C.gray + "at the enemy base.",
                        C.gray + "Features a full set of",
                        C.gray + "custom kits to unlock.",
                        ""
                ),
                false,
                false,
                19
        ));

        this.clusters.add(new ServerCluster(this, "dev",
                Material.BONE,
                C.red + "Dev Servers",
                Arrays.asList(
                        "",
                        C.gray + "Servers that are still",
                        C.gray + "in their development phase",
                        C.gray + "and are prone to many bugs.",
                        ""
                ),
                false,
                false,
                40,
                false,
                false
        ));



        this.createMenu();
    }

    public void createMenu() {
        this.home = Bukkit.getServer().createInventory(null, 45, C.underline + "Server Selector");

        for (ServerCluster serverCluster : this.clusters) {
            this.home.setItem(serverCluster.getSlot(), serverCluster.getHomeIcon());
        }
    }

    public ServerCluster getServerClustor(String name) {
        for (ServerCluster serverCluster : this.clusters) {
            if (serverCluster.getName().equalsIgnoreCase(name)) {
                return serverCluster;
            }
        }
        return null;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase(this.home.getName())) {
            event.setCancelled(true);
            S.click((Player) event.getWhoClicked());

            if (event.getCurrentItem() != null) {
                if (EventUtils.getDisplayName(event.getCurrentItem()) != null) {
                    ServerCluster serverCluster = this.getServerClustor(EventUtils.getDisplayName(event.getCurrentItem()));
                    if (serverCluster != null) {
                        if(serverCluster.isComingSoon()) {
                            if(serverCluster.isComingSoon()) {
                                if(serverCluster.isShowName()) {
                                    F.message((Player) event.getWhoClicked(), C.green + serverCluster.getName() + C.yellow + " is coming soon!");
                                } else {
                                    F.message((Player) event.getWhoClicked(), "That server is coming soon!");
                                }
                            }
                        } else {
                            serverCluster.display((Player) event.getWhoClicked());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (EventUtils.isItemClickWithDisplayName(event)) {
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(this.selectorItem.getItemMeta().getDisplayName())) {
                event.setCancelled(true);
                S.playSound(event.getPlayer(), Sound.ENDERDRAGON_WINGS);
                this.display(event.getPlayer());
            }
        }
    }

    public void display(Player player) {
        player.openInventory(this.home);
    }

    public static ItemStack getServerSelectorItem() {
        ItemStack itemStack = ItemStackFactory.createItem(Material.COMPASS, C.aqua + C.bold + "Server Selector");
        return itemStack;
    }

    public StatusDownloader getStatusDownloader() {
        return statusDownloader;
    }
}
