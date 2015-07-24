package com.minehut.core.command.commands;

import com.minehut.core.Core;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.core.util.common.chat.C;
import com.minehut.core.util.common.chat.F;
import com.minehut.core.util.common.location.LocationUtils;
import com.minehut.core.util.common.particles.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by luke on 6/1/15.
 */
public class ChunkCommand extends Command {
    public ArrayList<Player> viewingChunkBorders;

    public ChunkCommand(JavaPlugin plugin) {
        super(plugin, "chunk", Arrays.asList("chunks"), Rank.Mod);
        this.viewingChunkBorders = new ArrayList<>();
        this.viewChunkEdges();
    }

    public int viewChunkEdges() {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(!viewingChunkBorders.isEmpty()) {
                    for (Player player : viewingChunkBorders) {
                        if(player != null) {
                            Chunk chunk = player.getLocation().getChunk();

                            int y = player.getLocation().getBlockY();
                            for (int x = 0; x < 15; x++) {

                                ArrayList<Location> locs = new ArrayList<>();

                                locs.add(chunk.getBlock(x, y, 0).getLocation());
                                locs.add(chunk.getBlock(15, y, x).getLocation());
                                locs.add(chunk.getBlock(15 - x, y, 15).getLocation());
                                locs.add(chunk.getBlock(0, y, 15 - x).getLocation());

                                for (Location location : locs) {

                                    location.setX(center(location.getBlockX()));
                                    location.setZ(center(location.getBlockZ()));

                                    ParticleEffect.SMOKE_NORMAL.display((float) 0, (float) 0, (float) 0, 0, 20, LocationUtils.modifyForAir(location), Arrays.asList(player));
                                }
                            }
                        }
                    }
                }
            }
        }, 10L, 10L);
    }

    private double center(int i) {
        double d = i;
        d += .5;

        return d;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.viewingChunkBorders.contains(event.getPlayer())) {
            this.viewingChunkBorders.remove(event.getPlayer());
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (this.viewingChunkBorders.contains(event.getPlayer())) {
            this.viewingChunkBorders.remove(event.getPlayer());
        }
    }

    @Override
    public boolean call(Player player, ArrayList<String> args) {

        if (this.viewingChunkBorders.contains(player)) {
            this.viewingChunkBorders.remove(player);
            F.message(player, "Disabled Chunk Borders");
        } else {
            this.viewingChunkBorders.add(player);
            F.message(player, "Enabling Chunk Borders");
        }

        return false;
    }
}
