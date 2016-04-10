package com.iwuvhugs.seekgame;

import java.util.ArrayList;


public class GameObjects {

    private int currentScore = 0;
    private int scoresToWin = 0;
    private ArrayList<GameObject> list = new ArrayList<>();

    public GameObjects() {
    }

    public GameObjects(ArrayList<GameObject> list) {
        this.list = list;
    }

    public ArrayList<GameObject> getList() {
        return list;
    }

    public void setList(ArrayList<GameObject> list) {
        this.list = list;
    }

    public GameObject getGameObjectAtPosition(int position) {
        return list.get(position);
    }

    public int getSize() {
        return list.size();
    }

    public void add(GameObject go) {
        list.add(go);
        scoresToWin++;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void score() {
        currentScore++;
    }

    public Boolean win() {
        return currentScore == scoresToWin;
    }
}
