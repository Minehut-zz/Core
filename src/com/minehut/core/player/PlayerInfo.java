package com.minehut.core.player;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by luke on 5/28/15.
 */
public class PlayerInfo {
    private String name;
    private UUID uuid;
    private Rank rank;
    private long credits;

    public PlayerInfo(Player player) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.rank = Rank.regular;
        this.credits = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public long getCredits() {
        return credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }
}
