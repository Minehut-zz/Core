package com.minehut.core.player;

import com.minehut.commons.common.chat.C;
import com.minehut.commons.common.chat.F;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by luke on 5/28/15.
 */
public enum Rank {
    Owner("Owner", ChatColor.RED),
    Dev("Dev", ChatColor.RED),
    Manager("Manager", ChatColor.RED),
    Admin("Admin", ChatColor.RED),
    Ref("Ref", ChatColor.BLUE),
    Mod("Mod", ChatColor.YELLOW),
    dank("Dank", ChatColor.GREEN),
    noob("Noob", ChatColor.GREEN),
    Royal("Royal", ChatColor.LIGHT_PURPLE),
    Youtube("Youtube", ChatColor.AQUA),
    Twitch("Twitch", ChatColor.AQUA),
    Famous("Famous", ChatColor.DARK_PURPLE),
    Champ("Champ", ChatColor.GOLD),
    Legend("Legend", ChatColor.AQUA),
    Super("Super", ChatColor.GREEN),
    Mega("Mega",ChatColor.LIGHT_PURPLE),
    regular("", ChatColor.WHITE);

    public String name;
    private ChatColor tagColor;

    private Rank(String name, ChatColor tagColor) {
        this.name = name;
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

        if(!this.name.equalsIgnoreCase(""))
            return this.tagColor + "[" + this.name + "] ";
        return C.white + "";
    }

    public ChatColor getTagColor() {
        return tagColor;
    }

    public static Rank getRank(String s) {
        for (Rank rank : values()) {
            if (rank.name.equalsIgnoreCase(s)) {
                return rank;
            }
        }
        F.log("Couldn't find rank: " + s);
        return Rank.regular;
    }
}

