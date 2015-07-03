package com.minehut.core;

import com.minehut.commons.common.chat.C;
import com.minehut.commons.common.chat.F;
import com.minehut.commons.common.sound.S;
import com.minehut.core.command.commands.*;
import com.minehut.core.connection.ConnectionListener;
import com.minehut.core.player.PlayerInfo;
import com.minehut.core.player.Rank;
import com.minehut.core.status.StatusManager;
import com.minehut.core.status.menu.ServerMenuManager;
import com.mongodb.*;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by luke on 5/28/15.
 */
public class Core extends JavaPlugin {
    private static Core instance;
    private ConnectionListener connectionListener;
    private StatusManager statusManager;

    /* Perms */
    public final HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();

    /* Database */
    private MongoClient mongo;
    private DB db;
    private DBCollection playersCollection;

    private boolean onlineMode;

    @Override
    public void onEnable() {
        this.instance = this;
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.connect();

        this.connectionListener = new ConnectionListener(this);
        this.statusManager = new StatusManager(this);

        /* Commands */
        new RankSetCommand(this);
        new CreditsCommand(this);
        new SetCreditsCommand(this);
        new UpdateCommand(this);
        new ServersCommand(this);

    }

    public static void registerListener(Listener listener) {
        getInstance().getServer().getPluginManager().registerEvents(listener, getInstance());
    }

    public static Core getInstance() {
        return instance;
    }

    public PlayerInfo getPlayerInfo(Player player) {
        return this.connectionListener.getPlayerInfo(player);
    }

    public ArrayList<PlayerInfo> getPlayersInfos() {
        return this.connectionListener.getPlayerInfos();
    }

    public long addCredits(Player player, long amount) {
        PlayerInfo playerInfo = getPlayerInfo(player);

        DBObject query = new BasicDBObject("uuid", player.getUniqueId().toString());
        DBObject found = playersCollection.findOne(query);

        long oldCredits = (long) found.get("credits");
        long updatedCredits = oldCredits + amount;

        found.put("credits", updatedCredits);
        playersCollection.findAndModify(query, found);

        playerInfo.setCredits(updatedCredits);

        return updatedCredits;
    }

    public long addCredits(Player player, long amount, String reason) {
        if (amount > 0) {
            player.sendMessage(C.green + "+" + amount + " credits" + C.gold + " | " + C.aqua + reason);
            S.playSound(player, Sound.LEVEL_UP);
        } else {
            player.sendMessage(C.red + amount + " credits" + C.gold + " | " + C.aqua + reason);
            S.playSound(player, Sound.LEVEL_UP);
        }

        return this.addCredits(player, amount);
    }

    public Rank getRank(UUID uuid) {
        if(onlineMode) {
            DBObject r = new BasicDBObject("uuid", uuid.toString());
            DBObject found = playersCollection.findOne(r);

            if (found != null) {
                String rankName = (String) found.get("rank");
                return Rank.getRank(rankName);
            } else {
                /* Player not found, return default */
                return Rank.regular;
            }
        } else {
            return Rank.regular;
        }
    }

    private void connect() {
        try {
            this.mongo = new MongoClient("localhost", 27017);
            this.db = mongo.getDB("minehut");
            this.playersCollection = db.getCollection("players");

            if (this.db == null) {
                F.log("Couldn't connect to database, enabling offline mode.");
                this.onlineMode = false;
                return;
            }

            this.onlineMode = true;
        } catch (Exception e) {
            F.log("Couldn't connect to database, enabling offline mode.");
            this.onlineMode = false;
        }
    }

    public boolean isOnlineMode() {
        return onlineMode;
    }

    public MongoClient getMongo() {
        return mongo;
    }

    public DB getDb() {
        return db;
    }

    public DBCollection getPlayersCollection() {
        return playersCollection;
    }

    public HashMap<UUID, PermissionAttachment> getPerms() {
        return perms;
    }

    public StatusManager getStatusManager() {
        return statusManager;
    }
}
