package gffx.gui.comp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gffx.game.entity.Player;
import gffx.game.world.Field2D;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.Parent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Field2DPane extends GridPane {
    private ObservableList<Player> grid;
    private List<FieldCell> cells;
    private RowConstraints rconts;
    private ColumnConstraints cconts;
    private int gridwidth, gridheight;

    public Field2DPane(Field2D field) {
        cells = new ArrayList<>(field.width()*field.height());
        cconts = new ColumnConstraints();
        rconts = new RowConstraints();
        grid = FXCollections.observableArrayList(field.grid());
        gridwidth = field.width();
        gridheight = field.height();

        for(int i = 0; i < gridheight*gridheight; ++i) {
            FieldCell newcell = new FieldCell(field);
            add(newcell, i%gridwidth, i/gridwidth); // TODO (slow)
            cells.add(newcell);

            if(i < field.width())
                getColumnConstraints().add(cconts);

            if(i < field.height())
                getRowConstraints().add(rconts);
        }

        //field.deleteObservers(); // bad move // TODO delete observers on close request of parent window
        field.addObserver((obs, arg) -> {
            int j = ((Integer)arg).intValue();
            Player p = field.get(j); //grid.get(j);
            FieldCell cell = cells.get(j);

            cell.pop();
            if(p != null)
                cell.put(p.getSymbol());

            // delete observer when parent stage closes
            /* auch schlecht -> überschreibt ggf. parent close request
            Parent pa = getParent();
            while(pa != null) {
                try {
                    ((Stage)pa).setOnCloseRequest(e -> field.deleteObserver(Observable.this));
                    break;
                } catch(ClassCastException e) {
                    e.printStackTrace();
                }

                pa = pa.getParent();
            }
            */
            
            System.out.println("cell " + j + " changed to " + p);
        });
    }

    public Collection<FieldCell> getCells() {
        return cells;
    }

    public FieldCell getCell(int x, int y) {
        try {
            return cells.get(y*gridwidth + x);
        } catch(IndexOutOfBoundsException e) {
            // e.printStacktrace();
        }

        return null;
    }

    public int indexOf(FieldCell cell) {
        for(int i = 0; i < cells.size(); ++i)
            if(cell == cells.get(i)) return i;

        return -1;
    }

    public int xPosOf(FieldCell cell) {
        int i = indexOf(cell);
        if(i < 0) return i;

        return i%gridwidth;
    }

    public int yPosOf(FieldCell cell) {
        int i = indexOf(cell);
        if(i < 0) return i;

        return i/gridwidth;
    }

    public RowConstraints getVConstraints() {
        return rconts;
    }

    public ColumnConstraints getHConstraints() {
        return cconts;
    }
}