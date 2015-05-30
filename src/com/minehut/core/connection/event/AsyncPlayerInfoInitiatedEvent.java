package com.minehut.core.connection.event;

import com.minehut.core.player.PlayerInfo;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by luke on 5/29/15.
 */
public class AsyncPlayerInfoInitiatedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public PlayerInfo playerInfo;
    public boolean isNewPlayer;

    public AsyncPlayerInfoInitiatedEvent(PlayerInfo playerInfo, boolean isNewPlayer) {
        this.playerInfo = playerInfo;
        this.isNewPlayer = isNewPlayer;
    }


    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


}
