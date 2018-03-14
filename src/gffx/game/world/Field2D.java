package gffx.game.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Observable;

import gffx.game.entity.Player;

public class Field2D extends Observable {
    private int width, height;
    private ArrayList<Player> grid;

    public Field2D(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new ArrayList<>(width*height);
        while(grid.size() < width*height) grid.add(null);
    }

    public Field2D(Field2D other) {
        this(other.width(), other.height());
        
        for(int i = 0; i < grid.size(); ++i)
            grid.set(i, other.get(i));
    }

    public void set(int x, int y, Player p) {
        set(y*width + x, p, false);
    }

    public void set(int x, int y, Player p, boolean override) {
        set(y*width + x, p, override);
    }

    public void set(int i, Player p) {
        set(i, p, false);
    }

    public void set(int i, Player p, boolean override) {
        try {
            if(override || grid.get(i) == null) {
                grid.set(i, p);
                setChanged();
                notifyObservers(new Integer(i));
            }
        } catch(IndexOutOfBoundsException e) {
            // e.printStackTrace();
        }
    }

    public Player get(int x, int y) {
        return get(y*width + x);
    }

    public Player get(int i) {
        try {
            return grid.get(i);
        } catch(IndexOutOfBoundsException e) {
            // e.printStacktrac();
        }

        return null;
    }

    public int indexOf(Player p) {
        return grid.indexOf(p);
    }

    public Collection<Player> grid() {
        return grid;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
    
    public void clear() {
        for(int i = 0; i < grid.size(); ++i)
            set(i, null, true);
    }
}