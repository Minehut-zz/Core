package com.minehut.core.util.common.player;

import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.sound.S;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * Created by Luke on 10/19/14.
 */
public class ChatUtil {
    public static void gameAnnounce(String msg) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(C.mHead + "Game > " + ChatColor.YELLOW + msg);
        }
    }

    public static String joinAnnounce(String name) {
        return C.gray + "Join > " + name;
    }

    public static void dividerTop(Player player) {
        player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "ᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔ");
        player.sendMessage("");
    }

    public static void dividerBot(Player player) {
        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "ᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔᚔ");
    }

    public static String prefix() {
        return ChatColor.BLUE + "Game > " + ChatColor.GOLD;
    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static void announce(String message) {
        Bukkit.broadcastMessage(prefix() + ChatColor.YELLOW + message);
    }

    public static void announceAlert(String message) {
        Bukkit.broadcastMessage(prefix() + ChatColor.YELLOW + message);
		S.clickAll();
    }


    public static void plingHighAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 10);
        }
    }

    public static void excludeMessage(Player player, String message) {
        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (!otherPlayer.getName().equals(player.getName())) {
                otherPlayer.sendMessage(message);
            }
        }
    }

    public static String getMidMessage(String s) {
        int le = (62 - s.length()) / 2;
        String newS = "";
        for (int i = 0; i < le; i++) {
            newS += ChatColor.GOLD + "*";
        }
        newS += s;
        for (int i = 0; i < le; i++) {
            newS += ChatColor.GOLD + "*";
        }
        return newS;
    }

    public static String rainbowChatColor(String string) {
        int lastColor = 0;
        int currColor = 0;
        String newMessage = "";
        String colors = "123456789abcde";
        for (int i = 0; i < string.length(); i++) {
            do {
                currColor = new Random().nextInt(colors.length() - 1) + 1;
            } while (currColor == lastColor);

            newMessage += ChatColor.RESET.toString() + ChatColor.getByChar(colors.charAt(currColor)) + "" + string.charAt(i);

        }
        return newMessage;
    }

    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 10, 1);
    }
}
