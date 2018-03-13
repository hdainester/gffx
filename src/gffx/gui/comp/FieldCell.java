package gffx.gui.comp;

import java.util.Stack;

import gffx.game.world.Field2D;
import gffx.game.world.Symbol;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class FieldCell extends StackPane {
    private Symbol symbol;

    public FieldCell() {
        this(null);
    }

    public FieldCell(Field2D field) {
        this(field, null);
    }

    public FieldCell(Field2D field, Image background) {
        if(background != null)
            putImage(background, false);

        symbol = null;
        setMinSize(0, 0);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        init();
    }

    public void setBackground(String url) {
        setBackground(new Image(url));
    }
    
    public void setBackground(Image background) {
        Symbol tmp = pop();

        if(getChildren().size() == 1)
            getChildren().remove(0);

        putImage(background, false);
        put(tmp);

    }

    public void put(Symbol symbol) {
        if(this.symbol != null)
            getChildren().remove(getChildren().size()-1);

        if(symbol != null)
            putImage(new Image(symbol.toString()), true);

        this.symbol = symbol;
    }

    public Symbol pop() {
        if(this.symbol != null)
            getChildren().remove(getChildren().size()-1);
        
        Symbol symbol = this.symbol;
        this.symbol = null;
        return symbol;
    }

    public Symbol peek() {
        return symbol;
    }

    private void init() {
        setOnMouseClicked(e -> {
            // TODO
        });

        setOnMouseEntered(e -> {
            // TODO
        });

        setOnMouseExited(e -> {
            // TODO
        });
    }

    private ImageView putImage(Image img, boolean ratio) {
        ImageView imv = new ImageView(img);
        imv.setPreserveRatio(ratio);
        imv.fitWidthProperty().bind(widthProperty());
        imv.fitHeightProperty().bind(heightProperty());
        getChildren().add(imv);
        
        return imv;
    }
}