package com.iwuvhugs.seekgame;

/**
 * Created by iwuvhugs on 2016-04-09.
 */
public class GameObjects {

    private String[] objects;

    public GameObjects(String[] objects) {
        this.objects = new String[objects.length];
        System.arraycopy(objects, 0, this.objects, 0, this.objects.length);
    }
}
