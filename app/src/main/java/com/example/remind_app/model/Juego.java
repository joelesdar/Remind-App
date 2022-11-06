package com.example.remind_app.model;

import javax.persistence.*;

@Entity
@Table(name = "JUEGO", uniqueConstraints = @UniqueConstraint(columnNames = "nombreJuego"))
public class Juego {
    @Id
    @Column(name = "nombreJuego", nullable = false)
    private String nombreJuego;

    @ManyToOne
    private Usuario usuario;

    public Juego() {

    }

    public String getNombreJuego() {
        return nombreJuego;
    }

    public void setNombreJuego(String nombreJuego) {
        this.nombreJuego = nombreJuego;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Juego(String nombreJuego, Usuario usuario) {
        this.nombreJuego = nombreJuego;
        this.usuario = usuario;
    }
}
