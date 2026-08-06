package com.gams.proyecto_g4.model;

public class Asignatura {

    private int idAsignatura;
    private int idCarrera;
    private Integer idDocente;

    private String codigoAsignatura;
    private String nombre;
    private String descripcion;
    private int unidadesValorativas;
    private boolean estado;

    // Campos auxiliares para mostrar en el RecyclerView.
    private String nombreCarrera;
    private String nombreDocente;

    public Asignatura() {
    }

    public Asignatura(
            int idCarrera,
            Integer idDocente,
            String codigoAsignatura,
            String nombre,
            String descripcion,
            int unidadesValorativas
    ) {
        this.idCarrera = idCarrera;
        this.idDocente = idDocente;
        this.codigoAsignatura = codigoAsignatura;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidadesValorativas = unidadesValorativas;
        this.estado = true;
    }

    public Asignatura(
            int idAsignatura,
            int idCarrera,
            Integer idDocente,
            String codigoAsignatura,
            String nombre,
            String descripcion,
            int unidadesValorativas,
            boolean estado
    ) {
        this.idAsignatura = idAsignatura;
        this.idCarrera = idCarrera;
        this.idDocente = idDocente;
        this.codigoAsignatura = codigoAsignatura;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidadesValorativas = unidadesValorativas;
        this.estado = estado;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public Integer getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Integer idDocente) {
        this.idDocente = idDocente;
    }

    public String getCodigoAsignatura() {
        return codigoAsignatura;
    }

    public void setCodigoAsignatura(String codigoAsignatura) {
        this.codigoAsignatura = codigoAsignatura;
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

    public int getUnidadesValorativas() {
        return unidadesValorativas;
    }

    public void setUnidadesValorativas(int unidadesValorativas) {
        this.unidadesValorativas = unidadesValorativas;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }
}