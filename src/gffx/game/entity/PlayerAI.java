package gffx.game.entity;

import gffx.game.Game;
import gffx.game.Game2D;
import gffx.game.ressource.Sprite;
import gffx.game.rule.AIOperation;

public abstract class PlayerAI extends Player {
    public PlayerAI() {
        super();
    }

    public PlayerAI(Sprite sprite) {
        super(sprite);
    }

    protected Game2D minimax(Game2D game, int depth) {
        return minimax(game, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    }

    protected Game2D minimax(Game2D game, int depth, int alpha, int beta, boolean ismax) {
        if(depth <= 0 || !game.isRunning())
            return game;
        
        int rating;
        Game2D gamecopy, bestgame = game;
        
        for(int y = 0; y < game.getField().height(); ++y) {
            for(int x = 0; x < game.getField().width(); ++x) {
                gamecopy = (Game2D)game.copy();
                gamecopy.setCursor(x, y);

                if(gamecopy.turnCondition().check() != null) {
                    System.out.println("PlayerAI::minimax: [" + depth + "]: try turn at " + x + ", " + y + ", max: " + ismax + ", alpha: " + alpha + ", beta: " + beta);

                    if(alpha < beta) {
                        rating = rate(minimax(gamecopy, depth-1, alpha, beta, !ismax));

                        if(ismax ? (rating > alpha) : (rating < beta)) {
                            bestgame = gamecopy;

                            if(ismax) alpha = rating;
                            else beta = rating;
                        }
                    } else
                        System.out.println("PlayerAI::minimax: [" + depth + "]: pruning at " + x + ", " + y);
                }
            }
        }

        return bestgame;
    }

    public void move(Game game) {
        move(game, () -> {});
    }

    protected abstract int rate(Game game);
    public abstract void move(Game game, AIOperation op);
}