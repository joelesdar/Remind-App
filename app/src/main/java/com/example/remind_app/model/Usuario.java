package com.example.remind_app.model;

import javax.persistence.*;

@Entity
@Table(name = "USUARIO", uniqueConstraints = @UniqueConstraint(columnNames = "CORREO"))
public class Usuario {
    @Id
    @Column(name = "correo", nullable = false)
    private String correo;
    @Column(name = "nombreUsuario", unique = true, nullable = false)
    private String nombreUsuario;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario(String correo, String nombreUsuario) {
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario(){
    }
}
