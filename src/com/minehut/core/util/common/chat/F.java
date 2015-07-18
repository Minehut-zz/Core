package com.minehut.core.util.common.chat;

import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.sound.S;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Luke on 2/4/15.
 */
public class F {

	public enum BroadcastType {
		FULL_BORDER,
		FULL,
		MINIMAL_BORDER,
		MINIMAL
	}

	public static void log(String head, String msg) {
		System.out.println(head + " > " + msg);
	}

	public static void warning(Player player, String message) {
		S.playSound(player, Sound.NOTE_BASS);
		player.sendMessage(C.warning + C.gray + " " + message);
	}

	public static void broadcast(String message, BroadcastType broadcastType) {

		if (broadcastType == BroadcastType.FULL_BORDER) {
			Bukkit.getServer().broadcastMessage(C.blue + " > ----------------------------------");
			Bukkit.getServer().broadcastMessage(C.blue + " > " + C.yellow + message);
			Bukkit.getServer().broadcastMessage(C.blue + " > ----------------------------------");
		} else if (broadcastType == BroadcastType.FULL) {
			Bukkit.getServer().broadcastMessage(C.blue + " > " + C.yellow + message);
		} else if (broadcastType == BroadcastType.MINIMAL_BORDER) {
			Bukkit.getServer().broadcastMessage(C.gray + " *");
			Bukkit.getServer().broadcastMessage(C.gray + " * " + C.yellow + message);
			Bukkit.getServer().broadcastMessage(C.gray + " *");
		} else if (broadcastType == BroadcastType.MINIMAL) {
			Bukkit.getServer().broadcastMessage(C.gray + " * " + C.yellow + message);
		}
	}

	public static void broadcast(List<String> messages, BroadcastType broadcastType) {

		if (broadcastType == BroadcastType.FULL_BORDER) {
			Bukkit.getServer().broadcastMessage(C.blue + " > ----------------------------------");
			for (String s : messages) {
				Bukkit.getServer().broadcastMessage(C.blue + C.yellow + " > " + s);
			}
			Bukkit.getServer().broadcastMessage(C.blue + " > ----------------------------------");
		} else if (broadcastType == BroadcastType.FULL) {
			for (String s : messages) {
				Bukkit.getServer().broadcastMessage(C.blue + " > " + C.yellow + s);
			}
		} else if (broadcastType == BroadcastType.MINIMAL_BORDER) {
			Bukkit.getServer().broadcastMessage(C.gray + " *");
			for (String s : messages) {
				Bukkit.getServer().broadcastMessage(C.gray + " * " + C.yellow + s);
			}
			Bukkit.getServer().broadcastMessage(C.gray + " *");
		} else if (broadcastType == BroadcastType.MINIMAL) {
			for (String s : messages) {
				Bukkit.getServer().broadcastMessage(C.gray + " * " + C.yellow + s);
			}
		}
	}

	public static void broadcast(String message) {
		Bukkit.getServer().broadcastMessage(C.blue + " > " + C.yellow + message);
	}

	public static void broadcast(List<String> messages) {
		for (String s : messages) {
			Bukkit.getServer().broadcastMessage(C.blue + " > " + C.yellow + s);
		}
	}

	public static void message(Player player, String message, BroadcastType broadcastType) {

		if (broadcastType == BroadcastType.FULL_BORDER) {
			player.sendMessage(C.blue + " > ----------------------------------");
			player.sendMessage(C.blue + " > " + C.yellow + message);
			player.sendMessage(C.blue + " > ----------------------------------");
		} else if (broadcastType == BroadcastType.FULL) {
			player.sendMessage(C.blue + " > " + C.yellow + message);
		} else if (broadcastType == BroadcastType.MINIMAL_BORDER) {
			player.sendMessage(C.gray + " *");
			player.sendMessage(C.gray + " * " + C.yellow + message);
			player.sendMessage(C.gray + " *");
		} else if (broadcastType == BroadcastType.MINIMAL) {
			player.sendMessage(C.gray + " * " + C.yellow + message);
		}
	}

	public static void message(Player player, String message) {
		player.sendMessage(C.blue + " > " + C.yellow + message);
	}

	public static void log(String message) {
		System.out.println("[Log] MGM » " + message);
	}

	public static void noPerm(Player player, String message) {
		S.playSound(player, Sound.NOTE_BASS);
		player.sendMessage(C.warning + C.gray + " " + message);
	}

	public static void alert(String head, String msg) {
		Bukkit.broadcastMessage(C.dgray + "[" + C.green + head + C.dgray + "]: " + C.white + msg);
	}

	public static void alert(Player player, String head, String msg) {
		player.sendMessage(C.dgray + "[" + C.green + head + C.dgray + "]: " + C.white + msg);
	}

	public static void alert(Player player, String s) {
		player.sendMessage("");
		player.sendMessage(s);
		player.sendMessage("");
	}

	public static void info(Player player, List<String> msg) {
		player.sendMessage(C.divider);
		for (String s : msg) {
			player.sendMessage(C.white + "          " + s);
		}
		player.sendMessage(C.divider);
	}

	public static void debug(String msg) {
		Bukkit.broadcastMessage(C.green + C.bold + "Debug" + " > " + C.yellow + msg);
	}
}
