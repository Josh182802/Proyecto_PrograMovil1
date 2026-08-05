package com.gams.proyecto_g4.model;

public class Matricula {
    private int idMatricula;
    private int idEstudiante;
    private int idPeriodo;
    private String fechaMatricula;
    private String estado;
    private String observaciones;

    // Campos auxiliares para vistas
    private String nombreEstudiante;
    private String numeroCuenta;
    private String nombrePeriodo;

    public Matricula() {}

    public Matricula(int idEstudiante, int idPeriodo, String observaciones) {
        this.idEstudiante = idEstudiante;
        this.idPeriodo = idPeriodo;
        this.observaciones = observaciones;
        this.estado = "ACTIVA";
    }

    public int getIdMatricula() { return idMatricula; }
    public void setIdMatricula(int idMatricula) { this.idMatricula = idMatricula; }

    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    public int getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(int idPeriodo) { this.idPeriodo = idPeriodo; }

    public String getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(String fechaMatricula) { this.fechaMatricula = fechaMatricula; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public String getNombrePeriodo() { return nombrePeriodo; }
    public void setNombrePeriodo(String nombrePeriodo) { this.nombrePeriodo = nombrePeriodo; }
}