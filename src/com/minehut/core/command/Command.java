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
import java.util.List;

/**
 * Created by luke on 5/31/15.
 */
public abstract class Command implements Listener {
    private String name;
    private Rank rank;
    private List<String> aliases;

    public Command(JavaPlugin plugin, String name, Rank rank) {
        this.name = name;
        this.rank = rank;
        this.aliases = new ArrayList<>();

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Command(JavaPlugin plugin, String name, List<String> aliases, Rank rank) {
        this.name = name;
        this.rank = rank;
        this.aliases = aliases;

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

        if (cmd.equalsIgnoreCase("/" + name)) {
            //This is our command, make sure help msg doesn't show
            event.setCancelled(true);

            if (!Core.getInstance().getPlayerInfo(player).getRank().has(player, rank, true)) return;

            if (!call(event.getPlayer(), args)) {

            }
            return;
        } else if (!this.aliases.isEmpty()) {
            for (String alias : this.aliases) {
                if (cmd.equalsIgnoreCase("/" + alias)) {
                    event.setCancelled(true);

                    if (!Core.getInstance().getPlayerInfo(player).getRank().has(player, rank, true)) return;

                    if (!call(event.getPlayer(), args)) {

                    }
                    return;
                }
            }
        }
    }


    public abstract boolean call(Player player, ArrayList<String> args);
}
