package com.minehut.core.command.bannedCommandManager;

import com.minehut.core.Core;
import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.chat.F;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;

/**
 * Created by luke on 7/12/15.
 */
public class BannedCommandManager implements Listener {
    private ArrayList<String> bannedCommands;

    public BannedCommandManager(Core core) {
        this.bannedCommands = new ArrayList<>();

        this.bannedCommands.add("/me");
        this.bannedCommands.add("/bukkit:me");
        this.bannedCommands.add("/minecraft:me");

        Bukkit.getServer().getPluginManager().registerEvents(this, core);
    }

    @EventHandler
    public void onPreCommand(PlayerCommandPreprocessEvent event) {
        /* Clears up interference with our custom Command class */
        if(event.isCancelled()) return;

        for (String s : bannedCommands) {
            if (event.getMessage().startsWith(s)) {
                event.setCancelled(true);
                F.message(event.getPlayer(), C.aqua + s + C.red + " is not allowed on this server");
                break;
            }
        }
    }
}
