package gffx.game.entity;

import gffx.game.world.Symbol;

public class Player {
    private static int playerCount = 1;

    private final int id;
    private final Symbol symbol;

    public Player() {
        this(null);
    }

    public Player(Symbol symbol) {
        id = playerCount++;
        this.symbol = symbol;
    }

    public int getID() {
        return id;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}