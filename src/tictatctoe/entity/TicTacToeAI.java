package tictactoe.entity;

import gffx.game.Game;
import gffx.game.entity.Player;
import gffx.game.entity.PlayerAI;
import gffx.game.ressource.Sprite;
import gffx.game.rule.AIOperation;
import tictactoe.TicTacToe;

public class TicTacToeAI extends PlayerAI {
    private int level;

    public TicTacToeAI(Sprite sprite, int level) {
        super(sprite);
        this.level = level;
    }

    @Override
    public void move(Game game, AIOperation op) {
        try {
            TicTacToe tictactoe = (TicTacToe)game;
            TicTacToe best = (TicTacToe)minimax(tictactoe, level);
            tictactoe.setCursor(best.getCursorX(), best.getCursorY());

            op.run();
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected int rate(Game game) { // Cost-Function
        for(Player p : game.getPlayers())
            if(game.winCondition().check(p))
                return p == this ? 1 : -1;

        return 0;
    }
}