package com.iwuvhugs.seekgame;

import java.util.ArrayList;
import java.util.List;


public class GameLevels {

    private List<GameObjects> games = new ArrayList<>();

    public GameLevels() {
    }

    public GameLevels(Boolean populate) {
        if (populate) {
            fillWithPredefinedLevels();
        }
    }


    public List<GameObjects> getGames() {
        return games;
    }

    public void setGames(List<GameObjects> games) {
        this.games = games;
    }


    private void fillWithPredefinedLevels() {

        GameObject go1 = new GameObject("Phone");
        GameObject go2 = new GameObject("Laptop");
        GameObject go3 = new GameObject("Car");
        GameObject go4 = new GameObject("Headphones");
        GameObject go5 = new GameObject("Book");
        GameObject go6 = new GameObject("Cat");

        GameObjects game1 = new GameObjects();
        game1.add(go1);
        game1.add(go2);
        game1.add(go3);
        game1.add(go4);
        game1.add(go5);
        game1.add(go6);

        games.add(game1);


    }

}
