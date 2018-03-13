package gffx.game.rule;

import gffx.game.entity.Player;

public interface TurnCondition {
    /**
     * Checks if the next player turn is valid and executes it.
     * Returns the player on success else returns null. 
     */
    public Player check();
}