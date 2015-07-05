package com.minehut.core.status.upload;

import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.chat.F;
import com.minehut.core.Core;
import com.minehut.core.status.ServerInfo;
import com.minehut.core.util.StatusUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by luke on 6/19/15.
 */
public class StatusUploader {
    private ServerInfo serverInfo;
    private DBCollection serverCollection;
    private int runnableID = -1;
    private boolean uploading;

    public StatusUploader() {
        this.attemptUploadFromFile();
    }

    public void attemptUploadFromFile() {
        FileConfiguration config = StatusUtils.getStatusConfig();

        if (config != null) {
            String name = config.getString("name");
            String type = config.getString("type");
            String bungee = config.getString("bungee");

            F.log(C.logDivider);
            F.log("Starting Status Upload!");
            F.log("Name: " + name);
            F.log("Type: " + type);
            F.log("Bungee ID: " + bungee);
            F.log(C.logDivider);

            this.serverInfo = new ServerInfo(name, type, bungee, Bukkit.getServer().getIp(), Bukkit.getServer().getPort(), 0, 0, 0);

            this.startLocalUploading();
            this.uploading = true;
        } else {
            F.log(C.logDivider);
            F.log("status.yml was null, cancelling status upload.");
            F.log(C.logDivider);
            this.uploading = false;
        }
    }

    public int continuousUpload() {
        return Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                update();
            }
        }, 0L, 30L);
    }

    public void update() {
        DBObject query = new BasicDBObject("name", this.serverInfo.getName());

        this.serverCollection.findAndModify(query, createObject());
    }

    public void startLocalUploading() {
        this.serverCollection = Core.getInstance().getDb().getCollection("servers");

        DBObject query = new BasicDBObject("name", this.serverInfo.getName());
        DBObject found = this.serverCollection.findOne(query);

        if (found == null) {
            this.serverCollection.insert(this.createObject());
            F.log(C.logDivider);
            F.log("Uploading Status for the first time");
            F.log(C.logDivider);
        } else {
            this.serverCollection.findAndModify(query, this.createObject());
            F.log(C.logDivider);
            F.log("Found existing server, re-uploading Status");
            F.log(C.logDivider);
        }

        this.runnableID = continuousUpload();
    }

    public DBObject createObject() {
        DBObject obj = new BasicDBObject("name", this.serverInfo.getName());
        obj.put("type", this.serverInfo.getType());
        obj.put("bungee", this.serverInfo.getBungee());
        obj.put("ip", this.serverInfo.getIp());
        obj.put("port", this.serverInfo.getPort());

        obj.put("playersOnline", Bukkit.getServer().getOnlinePlayers().size());
        obj.put("maxPlayers", Bukkit.getServer().getMaxPlayers());

        obj.put("lastOnline", System.currentTimeMillis());

        return obj;
    }

    public boolean isUploading() {
        return uploading;
    }

    public void setUploading(boolean uploading) {
        this.uploading = uploading;
    }
}
