package com.example.remind_app.model;

import javax.persistence.*;

@Entity
@Table(name = "PUNTUACION", uniqueConstraints = @UniqueConstraint(columnNames = "hora"))
public class Puntuacion {
    @Id
    @Column(name = "hora", nullable = false)
    private String Hora;

    @Column
    private int Puntuacion;

    @ManyToOne
    private Juego juego;

    public Puntuacion() {
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public int getPuntuacion() {
        return Puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        Puntuacion = puntuacion;
    }

    public Puntuacion(String hora, int puntuacion) {
        Hora = hora;
        Puntuacion = puntuacion;
    }
}
