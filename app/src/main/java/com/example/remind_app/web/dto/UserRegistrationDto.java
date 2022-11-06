package com.example.remind_app.web.dto;

public class UserRegistrationDto {
    private String correo;
    private String nombreUsuario;

    public UserRegistrationDto() {
    }

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

    public UserRegistrationDto(String nombreUsuario, String correo) {
        super();
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
    }
}
