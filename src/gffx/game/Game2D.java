package gffx.game;

import gffx.game.entity.Player;
import gffx.game.world.Field2D;

public abstract class Game2D extends Game {
    private int cursorX, cursorY;
    private Field2D field;

    public Game2D(Player[] players, Field2D field) {
        super(players);
        this.field = field;
        cursorX = cursorY = 0;
    }

    // TODO copy ctor for Player, Turn- and WinCondition
    public Game2D(Game2D other) {
        this(other.getPlayers(), new Field2D(other.getField()));
        setTurnCondition(other.turnCondition()); // TODO : problem here -> the methods of the Conditions may
        setWinCondition(other.winCondition());   // still refer to the instances they were initialized in
        setCursor(other.cursorX, other.cursorY);
    }

    public void setCursor(int x, int y) {
        cursorX = x < 0 ? 0 : x >= field.width() ? field.width()-1 : x;
        cursorY = y < 0 ? 0 : y >= field.height() ? field.height()-1 : y;
    }
    
    public int getCursorX() {
        return cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public Field2D getField() {
        return field;
    }
}