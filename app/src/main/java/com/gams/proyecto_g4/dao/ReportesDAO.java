package com.gams.proyecto_g4.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.gams.proyecto_g4.database.DatabaseHelper;

public class ReportesDAO {

    private final DatabaseHelper databaseHelper;

    public ReportesDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public int obtenerTotalEstudiantes() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM estudiante " +
                        "WHERE estado_academico = 'ACTIVO'",
                null
        );

        int total = 0;

        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }

        cursor.close();
        return total;
    }

    public int obtenerTotalAsignaturas() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM asignatura WHERE estado = 1",
                null
        );

        int total = 0;

        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }

        cursor.close();
        return total;
    }

    public int obtenerTotalMatriculas() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM matricula WHERE estado = 'ACTIVA'",
                null
        );

        int total = 0;

        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }

        cursor.close();
        return total;
    }

    public Cursor obtenerRendimientoEstudiantil() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String consulta =
                "SELECT " +
                        "e.numero_cuenta, " +
                        "e.nombres, " +
                        "e.apellidos, " +
                        "a.nombre AS asignatura, " +
                        "c.nota_final, " +
                        "c.resultado " +
                        "FROM calificacion c " +
                        "INNER JOIN detalle_matricula dm " +
                        "ON c.id_detalle_matricula = dm.id_detalle_matricula " +
                        "INNER JOIN matricula m " +
                        "ON dm.id_matricula = m.id_matricula " +
                        "INNER JOIN estudiante e " +
                        "ON m.id_estudiante = e.id_estudiante " +
                        "INNER JOIN asignatura a " +
                        "ON dm.id_asignatura = a.id_asignatura " +
                        "ORDER BY e.apellidos, e.nombres";

        return db.rawQuery(consulta, null);
    }

    public Cursor obtenerAsignaturasInscritas() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String consulta =
                "SELECT " +
                        "e.numero_cuenta, " +
                        "e.nombres, " +
                        "e.apellidos, " +
                        "a.codigo_asignatura, " +
                        "a.nombre AS asignatura, " +
                        "dm.estado " +
                        "FROM detalle_matricula dm " +
                        "INNER JOIN matricula m " +
                        "ON dm.id_matricula = m.id_matricula " +
                        "INNER JOIN estudiante e " +
                        "ON m.id_estudiante = e.id_estudiante " +
                        "INNER JOIN asignatura a " +
                        "ON dm.id_asignatura = a.id_asignatura " +
                        "ORDER BY e.apellidos, e.nombres";

        return db.rawQuery(consulta, null);
    }

    public Cursor obtenerMatriculasPorPeriodo() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String consulta =
                "SELECT " +
                        "p.nombre AS periodo, " +
                        "p.anio_academico, " +
                        "COUNT(m.id_matricula) AS total_matriculas " +
                        "FROM periodo_academico p " +
                        "LEFT JOIN matricula m " +
                        "ON p.id_periodo = m.id_periodo " +
                        "GROUP BY p.id_periodo, p.nombre, p.anio_academico " +
                        "ORDER BY p.anio_academico DESC";

        return db.rawQuery(consulta, null);
    }

    public double obtenerPromedioGeneral() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT AVG(nota_final) FROM calificacion " +
                        "WHERE nota_final IS NOT NULL",
                null
        );

        double promedio = 0;

        if (cursor.moveToFirst() && !cursor.isNull(0)) {
            promedio = cursor.getDouble(0);
        }

        cursor.close();
        return promedio;
    }

    public int obtenerTotalAprobados() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM calificacion " +
                        "WHERE resultado = 'APROBADO'",
                null
        );

        int total = 0;

        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }

        cursor.close();
        return total;
    }

    public int obtenerTotalReprobados() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM calificacion " +
                        "WHERE resultado = 'REPROBADO'",
                null
        );

        int total = 0;

        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }

        cursor.close();
        return total;
    }
}