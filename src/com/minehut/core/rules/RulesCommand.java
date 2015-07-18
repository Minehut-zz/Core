package com.minehut.core.rules;

import com.minehut.core.Core;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.chat.F;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by luke on 6/1/15.
 */
public class RulesCommand extends Command {
    private RuleManager ruleManager;

    public RulesCommand(JavaPlugin plugin, RuleManager ruleManager) {
        super(plugin, "rules", Arrays.asList("rule"), Rank.regular);

        this.ruleManager = ruleManager;
    }

    @Override
    public boolean call(Player player, ArrayList<String> args) {

        ruleManager.openInv(player);

        return false;
    }
}
