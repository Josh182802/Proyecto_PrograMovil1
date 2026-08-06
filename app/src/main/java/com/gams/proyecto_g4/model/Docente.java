package com.gams.proyecto_g4.model;

public class Docente {

    private int idDocente;
    private int idUsuario;
    private String codigoDocente;
    private String nombres;
    private String apellidos;
    private String numeroIdentidad;
    private String telefono;
    private String especialidad;
    private String estadoLaboral;

    public Docente() {
    }

    public Docente(
            int idUsuario,
            String codigoDocente,
            String nombres,
            String apellidos,
            String numeroIdentidad,
            String telefono,
            String especialidad,
            String estadoLaboral
    ) {
        this.idUsuario = idUsuario;
        this.codigoDocente = codigoDocente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numeroIdentidad = numeroIdentidad;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.estadoLaboral = estadoLaboral;
    }

    public Docente(
            int idDocente,
            int idUsuario,
            String codigoDocente,
            String nombres,
            String apellidos,
            String numeroIdentidad,
            String telefono,
            String especialidad,
            String estadoLaboral
    ) {
        this.idDocente = idDocente;
        this.idUsuario = idUsuario;
        this.codigoDocente = codigoDocente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numeroIdentidad = numeroIdentidad;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.estadoLaboral = estadoLaboral;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCodigoDocente() {
        return codigoDocente;
    }

    public void setCodigoDocente(String codigoDocente) {
        this.codigoDocente = codigoDocente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumeroIdentidad() {
        return numeroIdentidad;
    }

    public void setNumeroIdentidad(String numeroIdentidad) {
        this.numeroIdentidad = numeroIdentidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEstadoLaboral() {
        return estadoLaboral;
    }

    public void setEstadoLaboral(String estadoLaboral) {
        this.estadoLaboral = estadoLaboral;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}