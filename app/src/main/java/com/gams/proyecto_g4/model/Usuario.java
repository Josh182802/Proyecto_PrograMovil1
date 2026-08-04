package com.gams.proyecto_g4.model;

public class Usuario {

    private int idUsuario;
    private String nombreUsuario;
    private int idRol;
    private String nombreRol;

    public Usuario(int idUsuario, String nombreUsuario, int idRol, String nombreRol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }
}