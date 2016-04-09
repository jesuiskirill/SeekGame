package com.iwuvhugs.seekgame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iwuvhugs on 2016-04-09.
 */
public class GameLevels {

    private List<GameObjects> games = new ArrayList<>();

    public GameLevels() {

        String[] gameObjects1 = {"phone", "laptop", "car", "headphones"};
        GameObjects game1 = new GameObjects(gameObjects1);

        games.add(game1);

    }

    public GameObjects getGame(int position){
        return games.get(position);
    }
}
