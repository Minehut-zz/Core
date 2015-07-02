package com.minehut.core.status.menu;

import com.minehut.core.Core;
import com.minehut.core.status.download.StatusDownloader;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

/**
 * Created by luke on 6/22/15.
 */
public class ServerMenuManager implements Listener {
    private StatusDownloader statusDownloader;

    public Inventory home;

    public ServerMenuManager(StatusDownloader statusDownloader) {
        this.statusDownloader = statusDownloader;

        Bukkit.getServer().getPluginManager().registerEvents(this, Core.getInstance());
    }


}
