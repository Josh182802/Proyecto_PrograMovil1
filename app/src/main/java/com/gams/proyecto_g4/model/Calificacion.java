package com.gams.proyecto_g4.model;

public class Calificacion {
    private int idCalificacion;
    private int idDetalleMatricula;
    private Double notaParcial1;
    private Double notaParcial2;
    private Double notaParcial3;
    private Double notaReposicion;
    private Double notaFinal;
    private String resultado;
    private String observaciones;

    // Campos auxiliares
    private String estudianteNombre;
    private String numeroCuenta;
    private String asignaturaNombre;

    public Calificacion() {
        this.resultado = "PENDIENTE";
    }

    public int getIdCalificacion() { return idCalificacion; }
    public void setIdCalificacion(int idCalificacion) { this.idCalificacion = idCalificacion; }

    public int getIdDetalleMatricula() { return idDetalleMatricula; }
    public void setIdDetalleMatricula(int idDetalleMatricula) { this.idDetalleMatricula = idDetalleMatricula; }

    public Double getNotaParcial1() { return notaParcial1; }
    public void setNotaParcial1(Double notaParcial1) { this.notaParcial1 = notaParcial1; }

    public Double getNotaParcial2() { return notaParcial2; }
    public void setNotaParcial2(Double notaParcial2) { this.notaParcial2 = notaParcial2; }

    public Double getNotaParcial3() { return notaParcial3; }
    public void setNotaParcial3(Double notaParcial3) { this.notaParcial3 = notaParcial3; }

    public Double getNotaReposicion() { return notaReposicion; }
    public void setNotaReposicion(Double notaReposicion) { this.notaReposicion = notaReposicion; }

    public Double getNotaFinal() { return notaFinal; }
    public void setNotaFinal(Double notaFinal) { this.notaFinal = notaFinal; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getEstudianteNombre() { return estudianteNombre; }
    public void setEstudianteNombre(String estudianteNombre) { this.estudianteNombre = estudianteNombre; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public String getAsignaturaNombre() { return asignaturaNombre; }
    public void setAsignaturaNombre(String asignaturaNombre) { this.asignaturaNombre = asignaturaNombre; }
}