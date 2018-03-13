package gffx.game.rule;

import gffx.game.entity.Player;

public interface WinCondition {
    /**
     * Checks if the current situation is a win situation for the
     * player and returns true if this is the case. Returns false instead.
     */
    public boolean check(Player p);
}