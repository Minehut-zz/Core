package com.minehut.core.player;

import com.minehut.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

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

    public void addPerm(Player player, String perm) {
        PermissionAttachment pa = player.addAttachment(Core.getInstance());
        pa.setPermission(perm, true);
        Core.getInstance().getPerms().put(player.getUniqueId(), pa);
    }

    public boolean hasEnoughForCreditsPurchase(long amount) {
        return (this.credits - amount) >= 0;
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

        if (rank.has(null, Rank.Mod, false)) {
            Player player = Bukkit.getServer().getPlayer(this.name);

            this.addPerm(player, "nocheatplus.admin");
        }
    }

    public long getCredits() {
        return credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }
}
