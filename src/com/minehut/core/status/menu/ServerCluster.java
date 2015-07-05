package com.minehut.core.status.menu;

import com.minehut.core.util.common.bungee.Bungee;
import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.chat.F;
import com.minehut.core.util.common.items.EnchantGlow;
import com.minehut.core.util.common.items.ItemStackFactory;
import com.minehut.core.util.common.sound.S;
import com.minehut.core.Core;
import com.minehut.core.player.Rank;
import com.minehut.core.status.ServerInfo;
import com.minehut.core.status.download.StatusDownloader;
import org.apache.logging.log4j.core.jmx.Server;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
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
    private ServerMenuManager serverMenuManager;

    public String serverType;
    public String name;
    public List<String> desc;
    public Material material;
    public ArrayList<ServerInfo> servers;
    public boolean kingdoms;
    public int slot;

    public boolean comingSoon;
    public boolean showName;

    private Inventory inv;
    private int runnableID = -1;

    public ServerCluster(ServerMenuManager serverMenuManager, String serverType, Material material, String name, List<String> desc, boolean kingdoms, int slot) {
        this.servers = new ArrayList<>();
        this.serverMenuManager = serverMenuManager;

        this.serverType = serverType;
        this.material = material;
        this.name = name;
        this.desc = desc;
        this.kingdoms = kingdoms;
        this.slot = slot;
        this.comingSoon = false;

        if(this.kingdoms) {
            this.inv = Bukkit.getServer().createInventory(null, 54, this.name);
        } else {
            this.inv = Bukkit.getServer().createInventory(null, 18, this.name);
        }
        this.runnableID = this.continuousRefresh();


        Bukkit.getServer().getPluginManager().registerEvents(this, Core.getInstance());
    }

    public ServerCluster(ServerMenuManager serverMenuManager, String serverType, Material material, String name, List<String> desc, boolean kingdoms, int slot, boolean comingSoon, boolean showName) {
        this.servers = new ArrayList<>();
        this.serverMenuManager = serverMenuManager;

        this.serverType = serverType;
        this.material = material;
        this.name = name;
        this.desc = desc;
        this.kingdoms = kingdoms;
        this.slot = slot;
        this.comingSoon = comingSoon;
        this.showName = showName;

        if(!comingSoon) {
            if (this.kingdoms) {
                this.inv = Bukkit.getServer().createInventory(null, 54, this.name);
            } else {
                this.inv = Bukkit.getServer().createInventory(null, 18, this.name);
            }
            this.runnableID = this.continuousRefresh();


            Bukkit.getServer().getPluginManager().registerEvents(this, Core.getInstance());
        }
    }

    public void display(Player player) {
        player.openInventory(this.inv);
    }

    public int continuousRefresh() {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                update();
            }
        }, 40L, 30L);
    }

    public void update() {
        this.sortServers(this.serverMenuManager.getStatusDownloader().getServers());
        this.refreshInventory();
    }

    private void refreshInventory() {
        this.inv.clear();

        if(!this.servers.isEmpty()) {
            int i = 0;
            for (ServerInfo serverInfo : this.servers) {
                this.inv.setItem(i, this.getIcon(serverInfo));
                i++;
            }
        } else {
            if(this.kingdoms) {
                for (int i = 0; i < 45; i++) {
                    this.inv.setItem(i, ItemStackFactory.createItem(Material.BARRIER,
                            C.red + "No Servers",
                            Arrays.asList("", C.red + "There are currently no servers online.", "")));
                }
            } else {
                for (int i = 0; i < 9; i++) {
                    this.inv.setItem(i, ItemStackFactory.createItem(Material.BARRIER,
                            C.red + "No Servers",
                            Arrays.asList("", C.red + "There are currently no servers online.", "")));
                }
            }
        }

        if (this.kingdoms) {
            this.inv.setItem(49, ItemStackFactory.createItem(Material.ARROW, C.red + "Back to Home Page"));
        } else {
            this.inv.setItem(13, ItemStackFactory.createItem(Material.ARROW, C.red + "Back to Home Page"));
        }

    }

    public ItemStack getHomeIcon() {
        ItemStack itemStack = ItemStackFactory.createItem(this.material, this.name, this.desc);
        return itemStack;
    }

    private ItemStack getIcon(ServerInfo serverInfo) {
        if (!serverInfo.isKingdom()) {
            ItemStack itemStack = ItemStackFactory.createItem(Material.EMERALD_BLOCK,
                    serverInfo.getName(),
                    Arrays.asList(
                            "",
                            C.gray + "Name: " + C.yellow + serverInfo.getName(),
                            C.gray + "Type: " + C.yellow + serverInfo.getType().toUpperCase(),
                            "",
                            C.gray + "Players: " + C.yellow + serverInfo.getPlayersOnline() + "/" + serverInfo.getMaxPlayers(),
                            ""
                    ));
            return itemStack;
        } else {
            ItemStack itemStack = ItemStackFactory.createItem(Material.EMERALD_BLOCK,
                    serverInfo.getName(),
                    Arrays.asList(
                            "",
                            C.gray + "Name: " + C.yellow + serverInfo.getName(),
                            C.gray + "Players: " + C.yellow + serverInfo.getPlayersOnline() + "/" + serverInfo.getMaxPlayers(),
                            "",
                            C.gray + "MOTD: " + C.yellow + serverInfo.getMotd(),
                            ""
                    ));
            if (serverInfo.getRank().has(null, Rank.Mega, false)) {
                EnchantGlow.addGlow(itemStack);
            }
            return itemStack;
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
        if (event.getInventory().getName().equalsIgnoreCase(this.getName())) {
            event.setCancelled(true);
            S.click((Player) event.getWhoClicked());
            if(event.getCurrentItem() != null) {
                if (event.getCurrentItem().getItemMeta() != null) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName() != null) {
                        ServerInfo serverInfo = this.getServerInfo(event.getCurrentItem().getItemMeta().getDisplayName());
                        if (serverInfo != null) {
                            F.message((Player) event.getWhoClicked(), "Sending you to " + C.green + serverInfo.getName());
                            Bungee.sendToServer(Core.getInstance(), (Player) event.getWhoClicked(), serverInfo.getBungee());
                        }

                        else if (event.getCurrentItem().getType() == Material.ARROW) {
                            this.serverMenuManager.display((Player) event.getWhoClicked());
                        }
                    }
                }
            }
        }
    }

    public ServerInfo getServerInfo(String name) {
        for (ServerInfo serverInfo : this.servers) {
            if (serverInfo.getName().equalsIgnoreCase(name)) {
                return serverInfo;
            }
        }
        return null;
    }

    private int getInventorySize(int amountOfServers) {
        if (amountOfServers <= 9) {
            return 18;
        } else if (amountOfServers <= 18) {
            return 27;
        } else if (amountOfServers <= 27) {
            return 36;
        } else if (amountOfServers <= 36) {
            return 45;
        } else {
            return 45;
        }
    }

    public String getServerType() {
        return serverType;
    }

    public String getName() {
        return name;
    }

    public List<String> getDesc() {
        return desc;
    }

    public Material getMaterial() {
        return material;
    }

    public ArrayList<ServerInfo> getServers() {
        return servers;
    }

    public boolean isKingdoms() {
        return kingdoms;
    }

    public Inventory getInv() {
        return inv;
    }

    public int getSlot() {
        return slot;
    }

    public boolean isComingSoon() {
        return comingSoon;
    }

    public boolean isShowName() {
        return showName;
    }
}
