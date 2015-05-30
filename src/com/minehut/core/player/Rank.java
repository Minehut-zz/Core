package com.minehut.core.player;

import com.minehut.commons.common.chat.C;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by luke on 5/28/15.
 */
public enum Rank {
    Owner("Owner", ChatColor.RED),
    Dev("Dev", ChatColor.RED),
    Admin("Manager", ChatColor.RED),
    Ref("Ref", ChatColor.BLUE),
    Mod("Mod", ChatColor.YELLOW),
    dank("Dank", ChatColor.GREEN),
    noob("Noob", ChatColor.GREEN),
    Famous("Famous", ChatColor.DARK_PURPLE),
    Champ("Champ", ChatColor.GOLD),
    Legend("Legend", ChatColor.AQUA),
    Super("Super", ChatColor.GREEN),
    Mega("Mega",ChatColor.LIGHT_PURPLE),
    regular("", ChatColor.WHITE);

    public String tagName;
    private ChatColor tagColor;

    private Rank(String tagName, ChatColor tagColor) {
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    public boolean has(Player player, Rank rank, boolean inform) {
        if (compareTo(rank) <= 0)
            return true;

        if (inform)
            player.sendMessage(C.mHead + "Permissions > " + C.mBody + "This requires Permission Rank " + rank.getTag());

        return false;
    }

    public String getChatColor() {
        return C.white;
    }

    public String getTag() {

        if(!this.tagName.equalsIgnoreCase(""))
            return this.tagColor + "[" + this.tagName + "] ";
        return C.white + "";
    }

    public ChatColor getTagColor() {
        return tagColor;
    }
}

