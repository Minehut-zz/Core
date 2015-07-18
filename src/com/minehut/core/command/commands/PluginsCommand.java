package com.minehut.core.command.commands;

import com.minehut.core.Core;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.chat.F;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by luke on 6/1/15.
 */
public class PluginsCommand extends Command {

    public PluginsCommand(JavaPlugin plugin) {
        super(plugin, "plugins", Arrays.asList("pl", "bukkit:pl", "bukkit:?", "bukkit:help", "bukkit:plugins"), Rank.regular);
    }

    @Override
    public boolean call(Player player, ArrayList<String> args) {

        Random rn = new Random();
        int n = 948 - 623 + 1;
        int i = rn.nextInt() % n;
        int randomNum =  623 + i;

        F.message(player, C.yellow + "Running the " + C.green + "Minehut Minecraft Interface");
        F.message(player, "There are currently " + C.aqua + randomNum + " Operations" + C.yellow + " in requisition");

        return false;
    }
}
