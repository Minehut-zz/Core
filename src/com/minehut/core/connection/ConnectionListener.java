package com.minehut.core.connection;

import com.minehut.core.Core;
import com.minehut.core.connection.event.AsyncPlayerInfoInitiatedEvent;
import com.minehut.core.player.PlayerInfo;
import com.minehut.core.player.Rank;
import com.mongodb.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by luke on 5/28/15.
 */
public class ConnectionListener implements Listener {
    private Core core;
    private ArrayList<PlayerInfo> playerInfos;

    public ConnectionListener(Core core) {
        this.core = core;
        this.playerInfos = new ArrayList<>();
        Core.registerListener(this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PlayerInfo playerInfo = new PlayerInfo(event.getPlayer());
        this.playerInfos.add(playerInfo);

        if(core.isOnlineMode()) {
            Core.getInstance().getServer().getScheduler().runTaskAsynchronously(Core.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Bukkit.getServer().getPluginManager().callEvent(new AsyncPlayerInfoInitiatedEvent(playerInfo, initiate(event.getPlayer(), playerInfo)));
                }
            });
        }
    }

    /* Returns true if player is new */
    private boolean initiate(Player player, PlayerInfo playerInfo) {
        DBObject r = new BasicDBObject("uuid", player.getUniqueId().toString());
        DBObject found = core.getPlayersCollection().findOne(r);

        if (found == null) {
            /* New Player */
            storePlayer(player, player.getName(), "regular", 0);

            playerInfo.setRank(Rank.regular);
            playerInfo.setCredits(0);

            return true;
        } else {
            /* Returning Player */
            String rank = (String) found.get("rank");
            long credits = (long) found.get("credits");

            playerInfo.setRank(Rank.valueOf(rank));
            playerInfo.setCredits(credits);

            /* Update latest played and player name */
            DBObject updated = new BasicDBObject("uuid", player.getUniqueId().toString());
            updated.put("name", player.getName());
            updated.put("rank", rank);
            updated.put("credits", credits);
            updated.put("first_joined", found.get("first_joined"));
            updated.put("last_online", new Date());
            updated.put("first_ip", found.get("first_ip"));
            updated.put("recent_ip", player.getAddress());

            core.getPlayersCollection().update(found, updated);

            return false;
        }
    }

    private void storePlayer(Player player, String name, String rank, long credits) {
        UUID uuid = player.getUniqueId();
        DBObject obj = new BasicDBObject("uuid", uuid.toString());
        obj.put("name", name);
        obj.put("rank", rank);
        obj.put("credits", credits);

        Date now = new Date();
        obj.put("first_joined", now);
        obj.put("last_online", now);

        obj.put("first_ip", player.getAddress());
        obj.put("recent_ip", player.getAddress());

        core.getPlayersCollection().insert(obj);
    }

    public PlayerInfo getPlayerInfo(Player player) {
        for (PlayerInfo playerInfo : playerInfos) {
            if (playerInfo.getUuid().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
                return playerInfo;
            }
        }
        return null;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.playerInfos.remove(this.getPlayerInfo(event.getPlayer()));
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        this.playerInfos.remove(this.getPlayerInfo(event.getPlayer()));
    }
}
