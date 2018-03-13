package gffx.game.entity;

import gffx.game.Game;
import gffx.game.rule.AIOperation;
import gffx.game.world.Symbol;

public abstract class AI extends Player {
    public AI() {
        super();
    }

    public AI(Symbol symbol) {
        super(symbol);
    }

    public abstract void move(Game game, AIOperation op);
}