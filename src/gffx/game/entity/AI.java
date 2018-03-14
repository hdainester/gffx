package gffx.game.entity;

import gffx.game.Game;
import gffx.game.ressource.Sprite;
import gffx.game.rule.AIOperation;

public abstract class AI extends Player {
    public AI() {
        super();
    }

    public AI(Sprite sprite) {
        super(sprite);
    }

    public abstract void move(Game game, AIOperation op);
}