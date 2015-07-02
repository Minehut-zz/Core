package com.minehut.core.status.menu;

import com.minehut.commons.common.bungee.Bungee;
import com.minehut.commons.common.chat.C;
import com.minehut.commons.common.chat.F;
import com.minehut.commons.common.items.ItemStackFactory;
import com.minehut.core.Core;
import com.minehut.core.status.ServerInfo;
import com.minehut.core.status.download.StatusDownloader;
import org.apache.logging.log4j.core.jmx.Server;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by luke on 7/2/15.
 */
public class ServerCluster implements Listener {
    private StatusDownloader statusDownloader;

    public String serverType;
    public String name;
    public List<String> desc;
    public ItemStack icon;
    public ArrayList<ServerInfo> servers;
    public boolean kingdoms;

    private Inventory inv;

    public ServerCluster(StatusDownloader statusDownloader, String serverType, String name, List<String> desc, ItemStack icon, boolean kingdoms) {
        this.servers = new ArrayList<>();
        this.statusDownloader = statusDownloader;

        this.serverType = serverType;
        this.name = name;
        this.desc = desc;
        this.icon = icon;
        this.kingdoms = kingdoms;

        Bukkit.getServer().getPluginManager().registerEvents(this, Core.getInstance());
    }

    public int continuousRefresh() {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.getInstance(), new Runnable() {
            @Override
            public void run() {

            }
        }, 40L, 20L);
    }

    public void update() {

    }

    private void refreshInventory() {
        this.inv = Bukkit.getServer().createInventory(null, getInventorySize(this.servers.size()), C.underline + this.name);

        int i = 0;
        for (ServerInfo serverInfo : this.servers) {
            this.inv.setItem(i, this.getIcon(serverInfo));
            i++;
        }
    }

    public ItemStack getIcon(ServerInfo serverInfo) {
        if (!serverInfo.isKingdom()) {
            ItemStack itemStack = ItemStackFactory.createItem(Material.EMERALD_BLOCK,
                    serverInfo.getName(),
                    Arrays.asList(
                            "",
                            C.gray + "Type: " + C.yellow + serverInfo.getType(),
                            C.gray + "Players: " + C.yellow + serverInfo.getPlayersOnline() + "/" + serverInfo.getMaxPlayers(),
                            ""
                    ));
            return itemStack;
        } else {
            //todo
            return null;
        }
    }

    private void sortServers(ArrayList<ServerInfo> unsortedServers) {
        this.servers = new ArrayList<>();
        for (ServerInfo serverInfo : unsortedServers) {
            if (serverInfo.getType().equalsIgnoreCase(this.serverType)) {
                this.servers.add(serverInfo);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() == this.inv) {
            if(event.getCurrentItem() != null) {
                if (event.getCurrentItem().getItemMeta() != null) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName() != null) {
                        ServerInfo serverInfo = this.getServerInfo(event.getCurrentItem().getItemMeta().getDisplayName());
                        if (serverInfo != null) {
                            F.message((Player) event.getWhoClicked(), "Sending you to " + C.green + serverInfo.getName());
                            Bungee.sendToServer(Core.getInstance(), (Player) event.getWhoClicked(), serverInfo.getBungee());
                        }
                    }
                }
            }
        }
    }

    public ServerInfo getServerInfo(String name) {
        for (ServerInfo serverInfo : this.servers) {
            if (serverInfo.getName().equalsIgnoreCase(this.name)) {
                return serverInfo;
            }
        }
        return null;
    }

    private int getInventorySize(int amountOfServers) {
        if (amountOfServers <= 9) {
            return 9;
        } else if (amountOfServers <= 18) {
            return 18;
        } else if (amountOfServers <= 27) {
            return 27;
        } else if (amountOfServers <= 36) {
            return 36;
        } else {
            return 45;
        }
    }
}
