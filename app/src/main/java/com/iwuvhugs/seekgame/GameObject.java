package com.iwuvhugs.seekgame;


public class GameObject {

    private String object;
    private Boolean isFound = false;

    public GameObject() {
        this.isFound = false;
    }

    public GameObject(String object) {
        this.object = object;
        this.isFound = false;
    }

    public GameObject(String object, Boolean isFound) {
        this.object = object;
        this.isFound = isFound;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Boolean getFound() {
        return isFound;
    }

    public void setFound(Boolean found) {
        isFound = found;
    }
}
