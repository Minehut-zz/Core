package com.minehut.core.command.commands;

import com.minehut.commons.common.chat.C;
import com.minehut.commons.common.chat.F;
import com.minehut.core.Core;
import com.minehut.core.command.Command;
import com.minehut.core.player.PlayerInfo;
import com.minehut.core.player.Rank;
import com.minehut.core.util.UUIDFetcher;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created by luke on 6/1/15.
 */
public class RankSetCommand extends Command {

    public RankSetCommand(JavaPlugin plugin) {
        super(plugin, "rankset", Rank.Admin);
    }

    @Override
    public boolean call(Player player, ArrayList<String> args) {

        if (args.size() != 2) {
            player.sendMessage(C.red + "/rankset (name) (rank)");
            return true;
        }

        UUIDFetcher fetcher = new UUIDFetcher(Arrays.asList(args.get(0)));

        Map<String, UUID> response = null;
        try {
            response = fetcher.call();
        } catch (Exception e) {
            F.log("Exception while running UUIDFetcher");
            e.printStackTrace();
        }

        if (response != null) {
            PlayerInfo playerInfo = Core.getInstance().getPlayerInfo(player);
            UUID uuid = response.get(args.get(0));
            Rank rank = Rank.valueOf(args.get(1));

            if (rank.compareTo(playerInfo.getRank()) > 0) {
                player.sendMessage(C.red + "You can not promote someone higher than your rank.");
                return true;
            }

            DBObject r = new BasicDBObject("uuid", player.getUniqueId().toString());
            DBObject found = Core.getInstance().getPlayersCollection().findOne(r);

            DBObject updated = new BasicDBObject("uuid", uuid.toString());
            updated.put("name", found.get("name"));
            updated.put("rank", rank.toString());
            updated.put("credits", found.get("credits"));
            updated.put("first_joined", found.get("first_joined"));
            updated.put("last_online", found.get("last_online"));

            Core.getInstance().getPlayersCollection().update(found, updated);

            player.sendMessage(C.green + "Successfully set " + C.aqua + args.get(0) + C.green + " rank to " + rank.getTag());
        } else {
            player.sendMessage(C.red + "Couldn't find UUID for " + C.aqua + args.get(0));
        }


        return false;
    }
}
