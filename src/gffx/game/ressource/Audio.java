package gffx.game.ressource;

// TODO
public class Audio implements Ressource {
    private String url;
    
    public Audio(String url) {
        this.url = url;
    }

    @Override
    public String getURL() {
        return url;
    }
}