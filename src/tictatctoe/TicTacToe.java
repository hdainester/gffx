package tictactoe;

import gffx.game.Game2D;
import gffx.game.entity.AI;
import gffx.game.entity.Player;
import gffx.game.rule.AIOperation;
import gffx.game.world.Field2D;

public class TicTacToe extends Game2D {
    private int next = 0;

    public TicTacToe(Player[] players, Field2D field) throws Exception {
        super(players, field);
        init();
    }

    public TicTacToe(TicTacToe other) {
        super(other.getPlayers(), new Field2D(other.getField()));

        try {
            init();
            next = other.next;
        } catch(Exception e) { // this should'nt happen
            e.printStackTrace();
        }
    }

    private void init() throws Exception {
        setTurnCondition(() -> {
            if(getField().get(getCursorX(), getCursorY()) == null) {
                int last = next;
                getField().set(getCursorX(), getCursorY(), getPlayers()[next]);
                next = (next+1)%getPlayers().length;
                return getPlayers()[last];
            }

            return null;
        });

        setWinCondition((p) -> {
            if(p != null) {
                final int WIN_PTS = getField().height();
                int wdt = getField().width(), hgt = getField().height(), dgt = wdt+hgt;
                int[] hpoints = new int[hgt];
                int[] vpoints = new int[wdt];
                int[] d1points = new int[dgt];
                int[] d2points = new int[dgt];

                for(int d1, d2, y = 0; y < hgt; ++y) {
                    for(int x = 0; x < wdt; ++x) {
                        d1 = (dgt/2) + (x-y);
                        d2 = x+y;

                        if(getField().get(x, y) == p) {
                            hpoints[y]++;
                            vpoints[x]++;
                            d1points[d1]++;
                            d2points[d2]++;


                            if(hpoints[y] >= WIN_PTS || vpoints[x] >= WIN_PTS
                            || d1points[d1] >= WIN_PTS || d2points[d2] >= WIN_PTS)
                                return true;
                        } else {
                            hpoints[y] = 0;
                            vpoints[x] = 0;
                            d1points[d1] = 0;
                            d2points[d2] = 0;
                        }
                    }
                }
            }

            return false;
        });

        for(int j, i = 0; i < getPlayers().length-1; ++i) {
            if(getPlayers()[i] == null)
                throw new Exception("player symbol must not be null");

            for(j = i+1; j < getPlayers().length; ++j)
                if(getPlayers()[i].getSprite() == getPlayers()[j].getSprite())
                    throw new Exception("only one player per symbol allowed");
        }
    }

    public Player getNextPlayer() {
        return getPlayers()[next];
    }


    @Override
    public void reset() {
        next = 0;
        getField().clear();
    }

    @Override
    public void aiMove(AIOperation op) {
        try {
            ((AI)getPlayers()[next]).move(this, op);
        } catch(ClassCastException e) { /* no AI */ }
    }

    @Override
    public boolean isRunning() {
        for(Player p : getPlayers())
            if(winCondition().check(p))
                return false;

        for(int y = 0; y < getField().height(); ++y)
            for(int x = 0; x < getField().width(); ++x)
                if(getField().get(x, y) == null)
                    return true;

        return false;
    }
    
    @Override
    public String getTitle() {
        return "TicTacToe";
    }
}