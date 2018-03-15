package gffx.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public List<Player> getPlayers() {
        // TODO this is a temporary solution until all other class are compatible with Collection<Player>
        List<Player> list = new ArrayList<>();
        for(Player p : players) list.add(p);
        return Collections.unmodifiableList(list);
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

    public Game copy() {
        return this;
    }

    public String getTitle() {
        return "Game";
    }
    
    public abstract void reset();
    public abstract void aiMove(AIOperation op);
    public abstract boolean isRunning();
}