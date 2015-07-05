package com.minehut.core.command.commands;

import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.chat.F;
import com.minehut.core.Core;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.mongodb.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by luke on 6/3/15.
 */
public class UpdateCommand extends Command {
    private Connection connection;
    private Core core;

    /* Database */
    private MongoClient mongo;
    private DB db;
    private DBCollection gamePlayers;

    public UpdateCommand(Core core) {
        super(core, "update", Rank.Admin);
        this.core = core;

        connection = new MySQL("localhost", "root", "raycub22", "minehut").getConnection();
    }

    @Override
    public boolean call(Player player, ArrayList<String> args) {

        this.removeNull();
        return false;
    }

    public void removeNull() {
        DBCursor curs = core.getPlayersCollection().find();

        int i = 1;
        while (curs.hasNext()) {
            DBObject found = curs.next();

            if (found.get("credits") == null) {
                core.getPlayersCollection().remove(found);
                F.debug("removed " + i);
                i++;
            }
        }
    }

    public void transferLevels() {
        try {

            this.connect();

            PreparedStatement select = connection.prepareStatement("select * from testglobal");
            ResultSet result = select.executeQuery();

            int i = 1;
            while (result.next()) {
                String uuid = result.getString("uuid");
                String name = result.getString("uuid");
                long xp = result.getInt("xp");

                DBObject query = new BasicDBObject("uuid", uuid);
                DBObject updated = gamePlayers.findOne(query);

                if (updated == null) {
                    DBObject obj = new BasicDBObject("uuid", uuid);
                    obj.put("name", name);

                    List<String> kits = new ArrayList<>();
                    kits.add("Warrior");

                    obj.put("kits", kits);
                    obj.put("xp", (long) xp);

                    core.getPlayersCollection().insert(obj);
                } else {
                    updated.put("xp", (long) xp);
                    gamePlayers.findAndModify(query, updated);
                }

                i++;
                Bukkit.getServer().broadcastMessage("Updated " + i);
            }
        } catch (Exception e) {

        }
    }

    private void connect() {
        try {
            this.mongo = new MongoClient("localhost", 27017);
            this.db = mongo.getDB("minehut");
            db.addOption(Bytes.QUERYOPTION_NOTIMEOUT);

            this.gamePlayers = this.db.getCollection("gamePlayers");

            if (this.db == null) {
                F.log("Failed to connect to Database.");
                return;
            }

        } catch (Exception e) {
            F.log("Failed to connect to Database.");
        }
    }

    public void updateCreditsAndXP() {


        DBCursor curs = core.getPlayersCollection().find();

        int i = 1;
        while (curs.hasNext()) {
            DBObject found = curs.next();
            String uuid = (String) found.get("uuid");
            String name = (String) found.get("name");

            DBObject query = new BasicDBObject("uuid", uuid);
            DBObject updated = core.getPlayersCollection().findOne(query);

            long updatedXP = 0;

            try {
                PreparedStatement select = connection.prepareStatement("select * from testglobal where uuid='" + uuid + "';");
                ResultSet result = select.executeQuery();

                if(result.next()) {
                    updatedXP = result.getInt("xp");
                }

                updated.put("credits", (long) updatedXP + 200);

                core.getPlayersCollection().findAndModify(query, updated);
                Bukkit.getServer().broadcastMessage("Updated " + name + " [" + i + "] Credits = " + updatedXP);
                i++;

                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    private void updateRank(String s) {
        try {
            PreparedStatement select = connection.prepareStatement("select * from testglobal where rank='" + s + "';");
            ResultSet result = select.executeQuery();

            while (result.next()) {
                String uuid = result.getString("uuid");
                DBObject obj = new BasicDBObject("uuid", uuid);
                obj.put("name", result.getString("name"));
                obj.put("rank", result.getString("rank"));
                obj.put("credits", (long) result.getInt("credits"));

                Date now = new Date();
                obj.put("first_joined", now);
                obj.put("last_online", now);

                obj.put("first_ip", null);
                obj.put("recent_ip", null);

                Core.getInstance().getPlayersCollection().insert(obj);

                Bukkit.getServer().broadcastMessage("Updated player " + Rank.getRank(result.getString("rank")) + result.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MySQL {
        //test
        private Connection connection;
        String ip;
        String userName;
        String password;
        String db;

        public MySQL(String ip, String userName, String password, String db) {
            this.ip = ip;
            this.userName = userName;
            this.password = password;
            this.db = db;
            this.renewConnection();
        }

        private void renewConnection() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db + "?user=" + userName + "&password=" + password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Connection getConnection() {
            return connection;
        }
    }
}
