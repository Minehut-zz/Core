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
import net.md_5.bungee.api.ChatColor;
import org.apache.logging.log4j.core.jmx.Server;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
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
    public boolean featuredKingdom;

    public int slot;

    public String invName;

    public boolean comingSoon;
    public boolean showName;

    private Inventory inv;
    private int runnableID = -1;

    public ServerCluster(ServerMenuManager serverMenuManager, String serverType, Material material, String name, List<String> desc, boolean kingdoms, boolean featuredKingdom, int slot) {
        this.servers = new ArrayList<>();
        this.serverMenuManager = serverMenuManager;

        this.serverType = serverType;
        this.material = material;

        this.name = name;
        this.invName = C.underline + ChatColor.stripColor(name);

        this.desc = desc;
        this.kingdoms = kingdoms;
        this.slot = slot;
        this.comingSoon = false;
        this.featuredKingdom = featuredKingdom;

        if(this.kingdoms) {

            F.debug(C.logDivider);
            F.debug(C.logDivider);
            if(featuredKingdom) {
                F.log("Created FEATURED kingdom cluster. name: " + name);
            } else {
                F.log("Created non-featured kingdom cluster. name: " + name);
            }
            F.debug(C.logDivider);
            F.debug(C.logDivider);

            this.inv = Bukkit.getServer().createInventory(null, 54, this.invName);
        } else {
            this.inv = Bukkit.getServer().createInventory(null, 18, this.invName);
        }
        this.runnableID = this.continuousRefresh();


        Bukkit.getServer().getPluginManager().registerEvents(this, Core.getInstance());
    }

    public ServerCluster(ServerMenuManager serverMenuManager, String serverType, Material material, String name, List<String> desc, boolean kingdoms, boolean featuredKingdom, int slot, boolean comingSoon, boolean showName) {
        this.servers = new ArrayList<>();
        this.serverMenuManager = serverMenuManager;

        this.serverType = serverType;
        this.material = material;


        this.name = name;
        this.invName = C.underline + ChatColor.stripColor(name);

        this.desc = desc;
        this.kingdoms = kingdoms;
        this.slot = slot;
        this.comingSoon = comingSoon;
        this.showName = showName;
        this.featuredKingdom = featuredKingdom;

        if(!comingSoon) {
            if (this.kingdoms) {
                this.inv = Bukkit.getServer().createInventory(null, 54, this.invName);
            } else {
                this.inv = Bukkit.getServer().createInventory(null, 18, this.invName);
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

            if(this.kingdoms) {

                for (int i = 0; i < 54; i++) {

                    if (this.servers.size() - 1 < i) {
                        break;
                    }

                    this.inv.setItem(i, this.getIcon(this.servers.get(i)));
                }
            } else {
                /* Game Server */
                for (int i = 0; i < this.servers.size(); i++) {
                    this.inv.setItem(i, this.getIcon(this.servers.get(i)));
                }
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
            /* Game Server */
            ItemStack itemStack = ItemStackFactory.createDye(serverInfo.getName(),
                    10, //10 = green
                    Arrays.asList(
                            "",
                            C.gray + "Name: " + C.aqua + serverInfo.getName(),
                            "",
                            C.gray + "Players: " + C.yellow + serverInfo.getPlayersOnline() + "/" + serverInfo.getMaxPlayers(),
                            ""
                    ));
            return itemStack;
        } else {
            /* Kingdom Server */
            ItemStack itemStack;
            if(serverInfo.getRank().has(null, Rank.Mega, false)) {
                itemStack = ItemStackFactory.createItem(Material.SIGN,
                        ChatColor.stripColor(serverInfo.getKingdomName()),
                        Arrays.asList(
                                "",
                                C.gray + "Name: " + C.yellow + serverInfo.getKingdomName(),
                                C.gray + "Players: " + C.yellow + serverInfo.getPlayersOnline() + "/" + serverInfo.getMaxPlayers(),
                                "",
                                C.gray + "MOTD: " + C.yellow + serverInfo.getMotd(),
                                ""
                        ));
            } else {
                itemStack = ItemStackFactory.createItem(Material.SIGN,
                        ChatColor.stripColor(serverInfo.getKingdomName()),
                        Arrays.asList(
                                "",
                                C.gray + "Name: " + C.yellow + serverInfo.getKingdomName(),
                                C.gray + "Players: " + C.yellow + serverInfo.getPlayersOnline() + "/" + serverInfo.getMaxPlayers(),
                                ""
                        ));
            }
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

                if (this.kingdoms) {
                    if (this.featuredKingdom) {
                        if (serverInfo.isFeatured()) {
//                            F.log("Added Featured Kingdom to cluster");
                            this.servers.add(serverInfo);
                        } else {
//                            F.log("Cluster says Kingdom " + serverInfo.getKingdomName() + " is not featured");
                        }
                    } else {
//                        F.log("In sort servers, cluster wasn't featured: " + name);
                        if (!serverInfo.isFeatured()) {
                            this.servers.add(serverInfo);
                        }
                    }
                } else {
                    this.servers.add(serverInfo);
                }

            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase(this.invName)) {
            event.setCancelled(true);
            S.click((Player) event.getWhoClicked());
            if(event.getCurrentItem() != null) {
                if (event.getCurrentItem().getItemMeta() != null) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName() != null) {
                        ServerInfo serverInfo = this.getServerInfo(event.getCurrentItem().getItemMeta().getDisplayName());
                        if (serverInfo != null) {
                            if (serverInfo.isKingdom()) {
                                F.message((Player) event.getWhoClicked(), "Sending you to " + C.green + serverInfo.getKingdomName());
                                Bungee.sendToServer(Core.getInstance(), (Player) event.getWhoClicked(), serverInfo.getBungee());
                            } else {
                                F.message((Player) event.getWhoClicked(), "Sending you to " + C.green + serverInfo.getName());
                                Bungee.sendToServer(Core.getInstance(), (Player) event.getWhoClicked(), serverInfo.getBungee());
                            }
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
            if (this.kingdoms) {
                if (serverInfo.getKingdomName().equalsIgnoreCase(name)) {
                    return serverInfo;
                }
            } else {
                if (serverInfo.getName().equalsIgnoreCase(name)) {
                    return serverInfo;
                }
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
