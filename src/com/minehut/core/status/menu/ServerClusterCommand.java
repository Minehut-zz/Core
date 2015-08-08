package com.minehut.core.status.menu;

import com.minehut.core.Core;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.chat.F;
import com.minehut.core.util.common.sound.S;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by luke on 6/1/15.
 */
public class ServerClusterCommand extends Command {
    public ServerCluster serverCluster;

    public ServerClusterCommand(JavaPlugin plugin, ServerCluster serverCluster) {
        super(plugin, "gs-" + serverCluster.serverType, Rank.regular);
        this.serverCluster = serverCluster;
    }

    @Override
    public boolean call(Player player, ArrayList<String> args) {

        S.playSound(player, Sound.ENDERDRAGON_WINGS);
        serverCluster.display(player);

        return false;
    }
}
