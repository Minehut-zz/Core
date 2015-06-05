package com.minehut.core.command.commands;

import com.minehut.commons.common.chat.C;
import com.minehut.commons.common.chat.F;
import com.minehut.commons.common.sound.S;
import com.minehut.core.Core;
import com.minehut.core.command.Command;
import com.minehut.core.player.PlayerInfo;
import com.minehut.core.player.Rank;
import com.minehut.core.util.UUIDFetcher;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created by luke on 6/1/15.
 */
public class RankSetCommand extends Command {

    public RankSetCommand(JavaPlugin plugin) {
        super(plugin, "setrank", Rank.Admin);
    }

    @Override
    public boolean call(Player player, ArrayList<String> args) {

        if (args.size() != 2) {
            player.sendMessage(C.red + "/setrank (name) (rank)");
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
            Rank rank = Rank.getRank(args.get(1));

            if (rank.compareTo(playerInfo.getRank()) < 0) {
                player.sendMessage(C.red + "You can not promote someone higher than your rank.");
                return true;
            }

            DBObject r = new BasicDBObject("uuid", uuid.toString());
            DBObject found = Core.getInstance().getPlayersCollection().findOne(r);

            DBObject updated = new BasicDBObject("uuid", uuid.toString());
            updated.put("name", found.get("name"));
            updated.put("rank", rank.name);
            updated.put("credits", found.get("credits"));
            updated.put("first_joined", found.get("first_joined"));
            updated.put("last_online", found.get("last_online"));
            updated.put("first_ip", found.get("first_ip"));
            updated.put("recent_ip", player.getAddress().toString());

            Core.getInstance().getPlayersCollection().update(found, updated);

            /* update player if on server */
            Player updatedPlayer = Bukkit.getServer().getPlayer(args.get(0));
            if (updatedPlayer != null) {
                Core.getInstance().getPlayerInfo(updatedPlayer).setRank(rank);
                updatedPlayer.sendMessage("");
                updatedPlayer.sendMessage(C.green + "Your rank has been set to " + rank.getTag());
                updatedPlayer.sendMessage("");
                S.playSound(updatedPlayer, Sound.LEVEL_UP);
            }

            player.sendMessage(C.green + "Successfully set " + C.aqua + args.get(0) + C.green + "'s rank to " + rank.getTag());
        } else {
            player.sendMessage(C.red + "Couldn't find UUID for " + C.aqua + args.get(0));
        }


        return false;
    }
}
