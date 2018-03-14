package gffx.gui.comp;

import java.util.Stack;

import gffx.game.ressource.Sprite;
import gffx.game.ressource.Texture;
import gffx.game.world.Field2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class FieldCell extends StackPane {
    private boolean hasTexture;

    public FieldCell() {
        this(null);
    }

    public FieldCell(Field2D field) {
        this(field, null);
    }

    public FieldCell(Field2D field, Texture background) {
        hasTexture = false;
        setTexture(background);
        setMinSize(0, 0);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        init();
    }

    public void setTexture(Texture background) {
        if(background != null) {
            if(hasTexture) {
                if(hasTexture = background != null)
                    getChildren().set(0, prepareView(background, false));
                else
                    getChildren().remove(0);
            } else if(background != null) {
                getChildren().add(0, prepareView(background, false));
                hasTexture = true;
            }
        }
    }

    public void set(int i, Sprite sprite) {
        if(i >= 0) {
            i += hasTexture ? 1 : 0;
            System.out.println("set at " + i + ", " + sprite + ", " + prepareView(sprite, true));
            
            if(sprite != null) {
                if(i < getChildren().size()) // TODO test if it works
                    getChildren().set(1, prepareView(sprite, true));
                else
                    getChildren().add(prepareView(sprite, true));
            } else
                getChildren().remove(i);
        }
    }

    public void push(Sprite sprite) {
        if(sprite != null)
            getChildren().add(prepareView(sprite, true));
    }

    public Sprite pop() {
        if(getChildren().size() > (hasTexture ? 1 : 0)) {
            try {
                return (Sprite)((ImageView)getChildren().remove(getChildren().size()-1)).getImage();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Sprite get(int i) {
        i += hasTexture ? 1 : 0;

        if(i > (hasTexture ? 0 : -1)) {
            try {
                return (Sprite)((ImageView)(getChildren().get(i))).getImage();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Texture getTexture() {
        if(hasTexture)
            return (Texture)((ImageView)getChildren().get(0)).getImage();

        return null;
    }

    private ImageView prepareView(Image img, boolean ratio) {
        ImageView imv = new ImageView(img);
        imv.setPreserveRatio(ratio);
        imv.fitWidthProperty().bind(widthProperty());
        imv.fitHeightProperty().bind(heightProperty());
        return imv;
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
}