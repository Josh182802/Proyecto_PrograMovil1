package com.gams.proyecto_g4.model;

public class Estudiante {

    private int idEstudiante;
    private int idUsuario;
    private int idCarrera;

    private String numeroCuenta;
    private String nombres;
    private String apellidos;
    private String numeroIdentidad;
    private String fechaNacimiento;
    private String direccion;
    private String telefono;
    private String fechaIngreso;
    private String estadoAcademico;

    // Campo auxiliar para mostrar la carrera.
    private String nombreCarrera;

    public Estudiante() {
    }

    public Estudiante(
            int idCarrera,
            String numeroCuenta,
            String nombres,
            String apellidos,
            String numeroIdentidad,
            String fechaNacimiento,
            String direccion,
            String telefono,
            String estadoAcademico
    ) {
        this.idCarrera = idCarrera;
        this.numeroCuenta = numeroCuenta;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numeroIdentidad = numeroIdentidad;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estadoAcademico = estadoAcademico;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getEstadoAcademico() {
        return estadoAcademico;
    }

    public void setEstadoAcademico(String estadoAcademico) {
        this.estadoAcademico = estadoAcademico;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}