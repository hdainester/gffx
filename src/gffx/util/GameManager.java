package gffx.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import gffx.game.Game;
import gffx.game.GameConfigurator;
import gffx.game.ressource.Ressource;
import gffx.gui.view.Game2DView;

public class GameManager {
    private static Map<String, Game> gameMap;
    private static Map<String, GameConfigurator> configMap;
    private static Ressource[] ressources; // TODO

    static {
        gameMap = new HashMap<>();
        configMap = new HashMap<>();
    }

    public static void add(String title, GameConfigurator configurator) {
        gameMap.put(title, null);
        configMap.put(title, configurator);
    }

    public static void init(String gametitle) {
        if(gameMap.containsKey(gametitle))
            gameMap.put(gametitle, configMap.get(gametitle).setup());
    }

    public static Game get(String title) {
        return gameMap.get(title);
    }

    public static Collection<String> getAllTitles() {
        return gameMap.keySet();
    }
}