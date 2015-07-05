package com.minehut.core.util.common.color;

import org.bukkit.ChatColor;
import org.bukkit.Color;

/**
 * Created by Luke on 3/17/15.
 */
public class ColorConverter {
	public static Color getColor(String name) {
		if (name.equalsIgnoreCase("red")) {
			return Color.RED;
		} else if (name.equalsIgnoreCase("blue")) {
			return Color.BLUE;
		} else if (name.equalsIgnoreCase("purple")) {
			return Color.PURPLE;
		} else if (name.equalsIgnoreCase("green")) {
			return Color.GREEN;
		} else if (name.equalsIgnoreCase("yellow")) {
			return Color.YELLOW;
		} else {
			return Color.WHITE;
		}
	}

	public static ChatColor getChatColor(Color color) {
		if (color == Color.RED) {
			return ChatColor.RED;
		} else if (color == Color.BLUE) {
			return ChatColor.BLUE;
		} else if (color == Color.PURPLE) {
			return ChatColor.DARK_PURPLE;
		} else if (color == Color.GREEN) {
			return ChatColor.GREEN;
		} else if (color == Color.YELLOW) {
			return ChatColor.YELLOW;
		} else {
			return ChatColor.WHITE;
		}
	}

}
