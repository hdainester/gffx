package gffx.game.world;

// TODO get rid of this e.g. get a proper Ressource System going
public enum Symbol {
    CROSS("res/ttt/image/symbol/cross_cracked_blue.png"),
    CIRCLE("res/ttt/image/circle_cracked_red.png");

    private final String path;

    Symbol(final String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}