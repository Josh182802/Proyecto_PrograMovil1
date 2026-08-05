package com.gams.proyecto_g4.model;

public class Carrera {

    private int idCarrera;
    private String codigoCarrera;
    private String nombre;
    private String descripcion;
    private int duracionAnios;
    private boolean estado;

    public Carrera() {
    }

    public Carrera(
            String codigoCarrera,
            String nombre,
            String descripcion,
            int duracionAnios
    ) {
        this.codigoCarrera = codigoCarrera;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionAnios = duracionAnios;
        this.estado = true;
    }

    public Carrera(
            int idCarrera,
            String codigoCarrera,
            String nombre,
            String descripcion,
            int duracionAnios,
            boolean estado
    ) {
        this.idCarrera = idCarrera;
        this.codigoCarrera = codigoCarrera;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionAnios = duracionAnios;
        this.estado = estado;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getCodigoCarrera() {
        return codigoCarrera;
    }

    public void setCodigoCarrera(String codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionAnios() {
        return duracionAnios;
    }

    public void setDuracionAnios(int duracionAnios) {
        this.duracionAnios = duracionAnios;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}