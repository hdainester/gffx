package gffx.game.ressource;

// TODO
public class Sound implements Ressource {
    private String url;

    public Sound(String url) {
        this.url = url;
    }

    @Override
    public String getURL() {
        return url;
    }
}