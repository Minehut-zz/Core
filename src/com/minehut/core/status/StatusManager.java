package com.minehut.core.status;

import com.minehut.core.Core;
import com.minehut.core.status.download.StatusDownloader;
import com.minehut.core.status.menu.ServerMenuManager;
import com.minehut.core.status.upload.StatusUploader;
import com.minehut.core.util.common.bungee.Bungee;
import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.chat.F;
import org.bukkit.entity.Player;

/**
 * Created by luke on 6/19/15.
 */
public class StatusManager {
    private Core core;
    private StatusUploader statusUploader;
    private StatusDownloader statusDownloader;
    private ServerMenuManager serverMenuManager;

    public StatusManager(Core core) {
        this.core = core;
        this.statusUploader = new StatusUploader();
        this.statusDownloader = new StatusDownloader(core);
        this.serverMenuManager = new ServerMenuManager(this.statusDownloader);
    }

    public StatusUploader getStatusUploader() {
        return statusUploader;
    }

    public StatusDownloader getStatusDownloader() {
        return statusDownloader;
    }

    public ServerMenuManager getServerMenuManager() {
        return serverMenuManager;
    }

    public void sendToKingdom(Player player, String kingdomName) {
        String bungeeID = null;

        for (ServerInfo serverInfo : this.statusDownloader.getServers()) {
            if (serverInfo.isKingdom() && serverInfo.getKingdomName() != null) {
                if (serverInfo.getKingdomName().equalsIgnoreCase(kingdomName)) {
                    bungeeID = serverInfo.getBungee();
                }
            }
        }

        if (bungeeID == null) {
            F.message(player, C.red + "Could not join " + C.aqua + kingdomName);
        } else {
            Bungee.sendToServer(Core.getInstance(), player, bungeeID);
            F.message(player, "Sending you to " + C.aqua + kingdomName);
        }
    }
}
