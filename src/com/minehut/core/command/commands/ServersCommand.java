package com.minehut.core.command.commands;

import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.chat.F;
import com.minehut.core.util.common.sound.S;
import com.minehut.core.Core;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by luke on 6/1/15.
 */
public class ServersCommand extends Command {

    public ServersCommand(JavaPlugin plugin) {
        super(plugin, "j", Rank.regular);
    }

    @Override
    public boolean call(Player player, ArrayList<String> args) {

        S.playSound(player, Sound.ENDERDRAGON_WINGS);
        Core.getInstance().getStatusManager().getServerMenuManager().display(player);
        F.message(player, "Opening Server Menu");

        return false;
    }
}
