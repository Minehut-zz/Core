package com.minehut.core.status;

import com.minehut.core.player.Rank;
import org.bukkit.Bukkit;

/**
 * Created by luke on 6/19/15.
 */
public class ServerInfo {
    private String name;
    private String type;
    private String bungee;
    private String ip;
    private int port;
    private int playersOnline;
    private int maxPlayers;
    private long lastOnline;

    private boolean kingdom;
    private Rank rank;
    private String motd;

    public ServerInfo(String name, String type, String bungee, String ip, int port, int players, int maxPlayers, long lastOnline) {
        this.name = name;
        this.type = type;
        this.bungee = bungee;
        this.ip = ip;
        this.port = port;
        this.lastOnline = lastOnline;

        this.kingdom = false;
        this.rank = null;
        this.motd = "";

        this.playersOnline = players;
        this.maxPlayers = maxPlayers;
    }

    public ServerInfo(String name, String type, String bungee, String ip, int port, int players, int maxPlayers, long lastOnline, boolean kingdom, String rank, String motd) {
        this.name = name;
        this.type = type;
        this.bungee = bungee;
        this.ip = ip;
        this.port = port;
        this.lastOnline = lastOnline;

        this.kingdom = kingdom;
        this.rank = Rank.getRank(rank);
        this.motd = motd;

        this.playersOnline = players;
        this.maxPlayers = maxPlayers;
    }

    public String getName() {
        return name;
    }

    public String getBungee() {
        return bungee;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getType() {
        return type;
    }

    public int getPlayersOnline() {
        return playersOnline;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isKingdom() {
        return kingdom;
    }

    public Rank getRank() {
        return rank;
    }

    public String getMotd() {
        return motd;
    }

    public long getLastOnline() {
        return lastOnline;
    }
}
