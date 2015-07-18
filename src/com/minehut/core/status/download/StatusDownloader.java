package com.minehut.core.status.download;

import com.minehut.core.util.common.chat.F;
import com.minehut.core.Core;
import com.minehut.core.player.Rank;
import com.minehut.core.status.ServerInfo;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;

import java.util.ArrayList;

/**
 * Created by luke on 6/22/15.
 */
public class StatusDownloader {
    public ArrayList<ServerInfo> servers;
    public DBCollection serverCollection;

    private int runnableID = -1;

    public StatusDownloader(Core core) {
        this.servers = new ArrayList<>();
        this.serverCollection = core.getDb().getCollection("servers");

        this.continuousDownload();
    }

    public int continuousDownload() {
        return Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                pull();
            }
        }, 0L, 30L);
    }

    public void pull() {
        this.servers.clear();

        DBCursor cursor = serverCollection.find();
        while (cursor.hasNext()) {
            DBObject found = cursor.next();

            long lastOnline = (long) found.get("lastOnline");
            if(isOnline(lastOnline)) {

                String name = (String) found.get("name");
                String type = (String) found.get("type");
                String bungee = (String) found.get("bungee");
                String ip = (String) found.get("ip");
                int port = (int) found.get("port");
                int playersOnline = (int) found.get("playersOnline");
                int maxPlayers = (int) found.get("maxPlayers");

                if (type.equalsIgnoreCase("kingdom")) {
                    String rank = (String) found.get("rank");
                    String motd = (String) found.get("motd");
                    String kingdomName = (String) found.get("kingdomName");
                    Boolean featured = (Boolean) found.get("featured");

                    ServerInfo serverInfo = new ServerInfo(name, type, bungee, ip, port, playersOnline, maxPlayers, lastOnline, kingdomName, rank, motd, featured);
                    this.servers.add(serverInfo);
                } else {
                    ServerInfo serverInfo = new ServerInfo(name, type, bungee, ip, port, playersOnline, maxPlayers, lastOnline);
                    this.servers.add(serverInfo);
                }
            }
        }
    }

    public boolean isOnline(long lastOnline) {
        long calculated = (System.currentTimeMillis() - lastOnline) / 1000;
        if (calculated <= 5) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<ServerInfo> getServers() {
        return servers;
    }
}
