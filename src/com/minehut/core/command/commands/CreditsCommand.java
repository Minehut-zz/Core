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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

/**
 * Created by luke on 6/1/15.
 */
public class CreditsCommand extends Command {

    public CreditsCommand(JavaPlugin plugin) {
        super(plugin, "credits", Rank.regular);
    }

    @Override
    public boolean call(Player player, ArrayList<String> args) {

        long credits = Core.getInstance().getPlayerInfo(player).getCredits();

        F.message(player, "You have " + C.green + credits + C.white + " credits");

        return false;
    }
}
