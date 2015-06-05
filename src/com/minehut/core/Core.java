package com.minehut.core;

import com.minehut.commons.common.chat.F;
import com.minehut.core.command.commands.RankSetCommand;
import com.minehut.core.command.commands.UpdateCommand;
import com.minehut.core.connection.ConnectionListener;
import com.minehut.core.player.PlayerInfo;
import com.minehut.core.player.Rank;
import com.mongodb.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

/**
 * Created by luke on 5/28/15.
 */
public class Core extends JavaPlugin {
    private static Core instance;
    private ConnectionListener connectionListener;

    /* Database */
    private MongoClient mongo;
    private DB db;
    private DBCollection playersCollection;

    private boolean onlineMode;

    @Override
    public void onEnable() {
        this.instance = this;

        this.connect();

        this.connectionListener = new ConnectionListener(this);

        /* Commands */
        new RankSetCommand(this);
//        new UpdateCommand(this);
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
}
