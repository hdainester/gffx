package gffx.game.entity;

import gffx.game.ressource.Sprite;

public class Player {
    private static int playerCount = 1;

    private final int id;
    private Sprite sprite;

    public Player() {
        this(null);
    }

    public Player(Sprite sprite) {
        id = playerCount++;
        this.sprite = sprite;
    }

    public int getID() {
        return id;
    }

    public Sprite getSprite() {
        return sprite;
    }
}