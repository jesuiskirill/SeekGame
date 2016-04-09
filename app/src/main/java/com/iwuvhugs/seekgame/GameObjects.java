package com.iwuvhugs.seekgame;

/**
 * Created by iwuvhugs on 2016-04-09.
 */
public class GameObjects {


    private String[] objects;
    private Boolean[] isObjectsFound;

    public GameObjects(String[] objects) {
        this.objects = new String[objects.length];
        this.isObjectsFound = new Boolean[objects.length];
        System.arraycopy(objects, 0, this.objects, 0, this.objects.length);
    }

    public String[] getObjects() {
        return objects;
    }

    public String getObjectArPosition(int position) {
        return objects[position];

    }

//    public void setObjects(String[] objects) {
//        this.objects = objects;
//    }
}
