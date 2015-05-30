package com.minehut.core;

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

    @Override
    public void onEnable() {
        this.instance = this;

        this.connect();

        this.connectionListener = new ConnectionListener(this);
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
        DBObject r = new BasicDBObject("uuid", uuid.toString());
        DBObject found = playersCollection.findOne(r);

        if (found != null) {
            String rankName = (String) found.get("rank");
            return Rank.valueOf(rankName);
        } else {
            /* Player not found, return default */
            return Rank.regular;
        }
    }

    private void connect() {
        try {
            this.mongo = new MongoClient("localhost", 27017);
            this.db = mongo.getDB("minehut");
            this.playersCollection = db.getCollection("players");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
