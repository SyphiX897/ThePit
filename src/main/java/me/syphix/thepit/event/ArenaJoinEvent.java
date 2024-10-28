package me.syphix.thepit.event;

import me.syphix.thepit.core.arena.Arena;
import me.syphix.thepit.core.player.PitPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ArenaJoinEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private PitPlayer pitPlayer;
    private Arena arena;

    public ArenaJoinEvent(PitPlayer pitPlayer, Arena arena) {
        this.pitPlayer = pitPlayer;
        this.arena = arena;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public PitPlayer pitPlayer() {
        return pitPlayer;
    }

    public Arena arena() {
        return arena;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
