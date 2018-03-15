package gffx.game.ressource;

import javafx.scene.image.Image;

// TODO
public class Texture extends Image implements Ressource {
    private String url;
    
    public Texture(String url) {
        super(url);
        this.url = url;
    }

    @Override
    public String getURL() {
        return url;
    }
}