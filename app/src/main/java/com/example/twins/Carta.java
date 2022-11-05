package com.example.twins;

public class Carta {
    int id;
    Imagen imagen;
    boolean estado;

    public Carta(int id, Imagen imagen, boolean estado){
        this.id = id;
        this.imagen = imagen;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}