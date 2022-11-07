package com.example.twins;

public class Imagen {
    int id;
    //Figura figura;
    boolean color;

    public Imagen(int id,/*Figura figura*/ boolean color) {
        this.id = id;
        //this.figura = figura;
        this.color = color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
