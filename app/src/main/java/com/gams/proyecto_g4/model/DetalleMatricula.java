package com.gams.proyecto_g4.model;

public class DetalleMatricula {
    private int idDetalleMatricula;
    private int idMatricula;
    private int idAsignatura;
    private String fechaInscripcion;
    private String estado;

    // Campos auxiliares
    private String codigoAsignatura;
    private String nombreAsignatura;

    public DetalleMatricula() {}

    public int getIdDetalleMatricula() { return idDetalleMatricula; }
    public void setIdDetalleMatricula(int idDetalleMatricula) { this.idDetalleMatricula = idDetalleMatricula; }

    public int getIdMatricula() { return idMatricula; }
    public void setIdMatricula(int idMatricula) { this.idMatricula = idMatricula; }

    public int getIdAsignatura() { return idAsignatura; }
    public void setIdAsignatura(int idAsignatura) { this.idAsignatura = idAsignatura; }

    public String getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(String fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCodigoAsignatura() { return codigoAsignatura; }
    public void setCodigoAsignatura(String codigoAsignatura) { this.codigoAsignatura = codigoAsignatura; }

    public String getNombreAsignatura() { return nombreAsignatura; }
    public void setNombreAsignatura(String nombreAsignatura) { this.nombreAsignatura = nombreAsignatura; }
}