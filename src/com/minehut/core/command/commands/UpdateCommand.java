package com.minehut.core.command.commands;

import com.minehut.commons.common.chat.C;
import com.minehut.core.Core;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by luke on 6/3/15.
 */
public class UpdateCommand extends Command {
    private Connection connection;

    public UpdateCommand(JavaPlugin plugin) {
        super(plugin, "update", Rank.Owner);

        connection = new MySQL("localhost", "root", "raycub22", "minehut").getConnection();
    }

    @Override
    public boolean call(Player player, ArrayList<String> args) {

        updateRank("mega");
        updateRank("super");
        updateRank("legend");
        updateRank("champ");
        updateRank("mod");
        updateRank("admin");
        updateRank("famous");

        return false;
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
