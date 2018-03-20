package tictactoe.entity;

import gffx.game.Game;
import gffx.game.Game2D;
import gffx.game.entity.Player;
import gffx.game.entity.PlayerAI;
import gffx.game.ressource.Sprite;
import gffx.game.rule.AIOperation;
import gffx.game.rule.WinCondition;
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
        TicTacToe tictactoe = (TicTacToe)game;

        // EXPERIMENTAL // rowcondition > millcondition > sidecondition > rowcondition+1  > ...
        // the arrays are not necessary but they dont hurt either
        WinCondition[] rowConditions = new WinCondition[tictactoe.getWinPoints()];      // xxxxo
        WinCondition[] millConditions = new WinCondition[tictactoe.getWinPoints()];     // oxxxo
        WinCondition[] sideConditions = new WinCondition[tictactoe.getWinPoints()];     // xxxoo

        // the smaller i the higher the priority
        // no need to check for winPoints == 1 (2 neither but we keep it)
        for(int i = 0; i < tictactoe.getWinPoints()-1; ++i) {
            final int i_final = i;

            // row conditions //
            TicTacToe ttt_copy = new TicTacToe(tictactoe);
            ttt_copy.setWinPoints(ttt_copy.getWinPoints()-i);
            rowConditions[i] = ttt_copy.winCondition();

            // mill conditions //
            millConditions[i] = (p) -> {
                if(p != null) {
                    int wdt = tictactoe.getField().width(), hgt = tictactoe.getField().height(), dgt = wdt+hgt-1;
                    int[] hpoints = new int[hgt];
                    int[] vpoints = new int[wdt];
                    int[] d1points = new int[dgt];
                    int[] d2points = new int[dgt];
                    int winPts = tictactoe.getWinPoints()+1-i_final;
                    Player p_check;

                    for(int d1, d2, y = 0; y < hgt; ++y) {
                        for(int x = 0; x < wdt; ++x) {
                            d1 = (hgt-1) + (x-y);
                            d2 = x+y;

                            p_check = tictactoe.getField().get(x, y);
                            hpoints[y] = (p_check == p && hpoints[y] > 0 || p_check == null && hpoints[y] == (winPts-1)) ? (hpoints[y]+1) : p_check == null ? 1 : 0;
                            vpoints[x] = (p_check == p && vpoints[x] > 0 || p_check == null && vpoints[x] == (winPts-1)) ? (vpoints[x]+1) : p_check == null ? 1 : 0;
                            d1points[d1] = (p_check == p && d1points[d1] > 0 || p_check == null && d1points[d1] == (winPts-1)) ? (d1points[d1]+1) : p_check == null ? 1 : 0;
                            d2points[d2] = (p_check == p && d1points[d2] > 0 || p_check == null && d2points[d2] == (winPts-1)) ? (d2points[d2]+1) : p_check == null ? 1 : 0;

                            if(hpoints[y] >= winPts || vpoints[x] >= winPts
                            || d1points[d1] >= winPts || d2points[d2] >= winPts)
                                return true;
                        }
                    }
                }

                return false;
            };

            // side conditions //
            sideConditions[i] = (p) -> {
                if(p != null) {
                    int wdt = tictactoe.getField().width(), hgt = tictactoe.getField().height(), dgt = wdt+hgt-1;
                    int[] hpoints = new int[hgt];
                    int[] vpoints = new int[wdt];
                    int[] d1points = new int[dgt];
                    int[] d2points = new int[dgt];
                    int winPts = tictactoe.getWinPoints()-i_final;
                    Player p_check;

                    for(int d1, d2, y = 0; y < hgt; ++y) {
                        for(int x = 0; x < wdt; ++x) {
                            d1 = (hgt-1) + (x-y);
                            d2 = x+y;

                            p_check = tictactoe.getField().get(x, y);
                            hpoints[y] = (p_check == null && (hpoints[y] == 0 || hpoints[y] == (winPts-1)) || p_check == p) ? (hpoints[y]+1) : 0;
                            vpoints[x] = (p_check == null && (vpoints[x] == 0 || vpoints[x] == (winPts-1)) || p_check == p) ? (vpoints[x]+1) : 0;
                            d1points[d1] = (p_check == null && (d1points[d1] == 0 || d1points[d1] == (winPts-1)) || p_check == p) ? (d1points[d1]+1) : 0;
                            d2points[d2] = (p_check == null && (d2points[d2] == 0 || d2points[d2] == (winPts-1)) || p_check == p) ? (d2points[d2]+1) : 0;

                            if(hpoints[y] >= winPts || vpoints[x] >= winPts
                            || d1points[d1] >= winPts || d2points[d2] >= winPts)
                                return true;
                        }
                    }
                }

                return false;
            };

            int poins_at_level = 3*(tictactoe.getWinPoints()-i);
            for(Player p : tictactoe.getPlayers()) {
                if(rowConditions[i].check(p))
                    return p == this ? poins_at_level : -poins_at_level;
                else if(millConditions[i].check(p))
                    return p == this ? poins_at_level-1 : -(poins_at_level-1);
                else if(sideConditions[i].check(p))
                    return p == this ? poins_at_level-2 : -(poins_at_level-2);
            }
        }
        /////////////////

        return 0;
    }
}