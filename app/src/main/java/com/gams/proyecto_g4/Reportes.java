package com.gams.proyecto_g4;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.gams.proyecto_g4.dao.ReportesDAO;
import com.gams.proyecto_g4.database.DatabaseHelper;

import java.util.Locale;

public class Reportes extends AppCompatActivity {

    private Button btnRendimiento;
    private Button btnAsignaturas;
    private Button btnMatriculas;
    private Button btnEstadisticas;
    private Button btnRegresar;

    private ReportesDAO reportesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportes);

        reportesDAO = new ReportesDAO(this);

        btnRendimiento = findViewById(R.id.btnRendimiento);
        btnAsignaturas = findViewById(R.id.btnAsignaturas);
        btnMatriculas = findViewById(R.id.btnMatriculas);
        btnEstadisticas = findViewById(R.id.btnEstadisticas);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnRendimiento.setOnClickListener(v ->
                mostrarRendimientoEstudiantil()
        );

        btnAsignaturas.setOnClickListener(v ->
                mostrarAsignaturasInscritas()
        );

        btnMatriculas.setOnClickListener(v ->
                mostrarMatriculasPorPeriodo()
        );

        btnEstadisticas.setOnClickListener(v ->
                mostrarEstadisticasGenerales()
        );

        btnRegresar.setOnClickListener(v -> finish());
    }

    private void mostrarRendimientoEstudiantil() {
        Cursor cursor = null;
        StringBuilder resultado = new StringBuilder();

        try {
            cursor = reportesDAO.obtenerRendimientoEstudiantil();

            while (cursor.moveToNext()) {
                String cuenta = cursor.getString(
                        cursor.getColumnIndexOrThrow("numero_cuenta")
                );

                String nombres = cursor.getString(
                        cursor.getColumnIndexOrThrow("nombres")
                );

                String apellidos = cursor.getString(
                        cursor.getColumnIndexOrThrow("apellidos")
                );

                String asignatura = cursor.getString(
                        cursor.getColumnIndexOrThrow("asignatura")
                );

                int indiceNota = cursor.getColumnIndexOrThrow("nota_final");

                String nota = cursor.isNull(indiceNota)
                        ? "Pendiente"
                        : String.format(Locale.getDefault(), "%.2f", cursor.getDouble(indiceNota));

                String estado = cursor.getString(
                        cursor.getColumnIndexOrThrow("resultado")
                );

                resultado.append("Cuenta: ")
                        .append(cuenta)
                        .append("\nEstudiante: ")
                        .append(nombres)
                        .append(" ")
                        .append(apellidos)
                        .append("\nAsignatura: ")
                        .append(asignatura)
                        .append("\nNota final: ")
                        .append(nota)
                        .append("\nResultado: ")
                        .append(estado)
                        .append("\n\n");
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        mostrarResultado(
                "Rendimiento estudiantil",
                resultado.toString()
        );
    }

    private void mostrarAsignaturasInscritas() {
        Cursor cursor = null;
        StringBuilder resultado = new StringBuilder();

        try {
            cursor = reportesDAO.obtenerAsignaturasInscritas();

            while (cursor.moveToNext()) {
                String cuenta = cursor.getString(
                        cursor.getColumnIndexOrThrow("numero_cuenta")
                );

                String nombres = cursor.getString(
                        cursor.getColumnIndexOrThrow("nombres")
                );

                String apellidos = cursor.getString(
                        cursor.getColumnIndexOrThrow("apellidos")
                );

                String codigo = cursor.getString(
                        cursor.getColumnIndexOrThrow("codigo_asignatura")
                );

                String asignatura = cursor.getString(
                        cursor.getColumnIndexOrThrow("asignatura")
                );

                String estado = cursor.getString(
                        cursor.getColumnIndexOrThrow("estado")
                );

                resultado.append("Cuenta: ")
                        .append(cuenta)
                        .append("\nEstudiante: ")
                        .append(nombres)
                        .append(" ")
                        .append(apellidos)
                        .append("\nAsignatura: ")
                        .append(codigo)
                        .append(" - ")
                        .append(asignatura)
                        .append("\nEstado: ")
                        .append(estado)
                        .append("\n\n");
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        mostrarResultado(
                "Asignaturas inscritas",
                resultado.toString()
        );
    }

    private void mostrarMatriculasPorPeriodo() {
        Cursor cursor = null;
        StringBuilder resultado = new StringBuilder();

        try {
            cursor = reportesDAO.obtenerMatriculasPorPeriodo();

            while (cursor.moveToNext()) {
                String periodo = cursor.getString(
                        cursor.getColumnIndexOrThrow("periodo")
                );

                int anio = cursor.getInt(
                        cursor.getColumnIndexOrThrow("anio_academico")
                );

                int total = cursor.getInt(
                        cursor.getColumnIndexOrThrow("total_matriculas")
                );

                resultado.append("Período: ")
                        .append(periodo)
                        .append("\nAño académico: ")
                        .append(anio)
                        .append("\nTotal de matrículas: ")
                        .append(total)
                        .append("\n\n");
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        mostrarResultado(
                "Matrículas por período",
                resultado.toString()
        );
    }

    private void mostrarEstadisticasGenerales() {
        int estudiantes = reportesDAO.obtenerTotalEstudiantes();
        int asignaturas = reportesDAO.obtenerTotalAsignaturas();
        int matriculas = reportesDAO.obtenerTotalMatriculas();
        int aprobados = reportesDAO.obtenerTotalAprobados();
        int reprobados = reportesDAO.obtenerTotalReprobados();
        double promedio = reportesDAO.obtenerPromedioGeneral();

        String resultado =
                "Estudiantes activos: " + estudiantes +
                        "\nAsignaturas activas: " + asignaturas +
                        "\nMatrículas activas: " + matriculas +
                        "\nAsignaturas aprobadas: " + aprobados +
                        "\nAsignaturas reprobadas: " + reprobados +
                        "\nPromedio general: " +
                        String.format(Locale.getDefault(), "%.2f", promedio);

        mostrarResultado(
                "Estadísticas generales",
                resultado
        );
    }

    private void mostrarResultado(String titulo, String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            mensaje = "No hay datos disponibles.";
        }

        new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }
}