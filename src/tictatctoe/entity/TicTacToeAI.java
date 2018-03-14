package tictactoe.entity;

import gffx.game.Game;
import gffx.game.entity.AI;
import gffx.game.entity.Player;
import gffx.game.ressource.Sprite;
import gffx.game.rule.AIOperation;
import tictactoe.TicTacToe;

public class TicTacToeAI extends AI {
    private int level;

    public TicTacToeAI(Sprite sprite, int level) {
        super(sprite);
        this.level = level;
    }

    @Override
    public void move(Game game, AIOperation op) {
        try {
            TicTacToe tictactoe = (TicTacToe)game;
            TicTacToe best = minimax(tictactoe, level);
            tictactoe.setCursor(best.getCursorX(), best.getCursorY());

            System.out.println("----- AI Move -----");
            tictactoe.getField().grid().forEach(System.out::println);

            op.run();
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
    }

    private TicTacToe minimax(TicTacToe game, int depth) {
        return minimax(game, depth, true);
    }

    private TicTacToe minimax(TicTacToe game, int depth, boolean ismax) {
        if(depth <= 0 || !game.isRunning())
            return game;

        Player curplayer;
        TicTacToe gamecopy, bestgame = game;
        int rating, bestrating = ismax ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for(int y = 0; y < game.getField().height(); ++y) {
            for(int x = 0; x < game.getField().width(); ++x) {
                gamecopy = new TicTacToe(game);
                gamecopy.setCursor(x, y);

                if((curplayer = gamecopy.turnCondition().check()) != null) {
                    System.out.print(depth + ": try turn at " + x + ", " + y + ", player: " + curplayer);
                    if(depth > 1) System.out.println("");
                    // TODO prune check here
                    rating = rate(minimax(gamecopy, depth-1, !ismax));
                    if(depth == 1)
                        System.out.println(" rating: " + rating + "/" + bestrating);

                    if(this == curplayer ? (rating > bestrating) : (rating < bestrating)) { // 'bestrating' relative to minimax algorithm
                        System.out.println(depth + ": best turn at " + x + ", " + y + " rating: " + rating);
                        bestrating = rating;
                        bestgame = gamecopy;
                    }
                }
            }
        }

        return bestgame;
    }

    private int rate(TicTacToe game) { // Cost-Function
        for(Player p : game.getPlayers())
            if(game.winCondition().check(p))
                return p == this ? 1 : -1;

        return 0;
    }
}