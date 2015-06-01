package com.minehut.core.command;

import com.minehut.core.Core;
import com.minehut.core.player.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by luke on 5/31/15.
 */
public abstract class Command implements Listener {
    private String name;
    private Rank rank;

    public Command(JavaPlugin plugin, String name, Rank rank) {
        this.name = name;
        this.rank = rank;

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        ArrayList<String> args = new ArrayList<String>();

        //Define args
        for (String s : event.getMessage().split(" ")) {
            args.add(s);
        }

        //Remove command from args
        String cmd = args.get(0);
        args.remove(0);

        if (!cmd.equalsIgnoreCase("/" + name)) return;

        //This is our command, make sure help msg doesn't show
        event.setCancelled(true);

        if (!Core.getInstance().getPlayerInfo(player).getRank().has(player, rank, true)) return;

        if (!call(event.getPlayer(), args)) {

        }
    }


    public abstract boolean call(Player player, ArrayList<String> args);
}
