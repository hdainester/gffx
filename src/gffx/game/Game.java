package gffx.game;

import gffx.game.entity.Player;
import gffx.game.rule.AIOperation;
import gffx.game.rule.TurnCondition;
import gffx.game.rule.WinCondition;

public abstract class Game {
    private Player[] players;
    private WinCondition winCondition;
    private TurnCondition turnCondition;

    public Game(Player[] players) {
        this.players = players;
        this.winCondition = null;
        this.turnCondition = null;
    }

    public Player[] getPlayers() {
        return players;
    }

    public WinCondition winCondition() {
        return winCondition;
    }

    public TurnCondition turnCondition() {
        return turnCondition;
    }

    public void setWinCondition(WinCondition wc) {
        winCondition = wc;
    }

    public void setTurnCondition(TurnCondition tc) {
        turnCondition = tc;
    }

    public String getTitle() {
        return "Game";
    }
    
    public abstract void reset();
    public abstract void aiMove(AIOperation op);
    public abstract boolean isRunning();
}