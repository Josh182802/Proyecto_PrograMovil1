package com.gams.proyecto_g4.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.gams.proyecto_g4.database.DatabaseHelper;
import com.gams.proyecto_g4.model.Matricula;
import com.gams.proyecto_g4.model.DetalleMatricula;

import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {
    private final DatabaseHelper dbHelper;

    public MatriculaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long registrarMatriculaConAsignaturas(Matricula matricula, List<Integer> idAsignaturas) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long idMatricula = -1;
        db.beginTransaction();
        try {
            ContentValues valores = new ContentValues();
            valores.put("id_estudiante", matricula.getIdEstudiante());
            valores.put("id_periodo", matricula.getIdPeriodo());
            valores.put("observaciones", matricula.getObservaciones());

            idMatricula = db.insert("matricula", null, valores);

            if (idMatricula != -1) {
                for (int idAsignatura : idAsignaturas) {
                    ContentValues detalle = new ContentValues();
                    detalle.put("id_matricula", idMatricula);
                    detalle.put("id_asignatura", idAsignatura);

                    long idDetalle = db.insert("detalle_matricula", null, detalle);

                    // Inicializar calificación en blanco asociada al detalle
                    if (idDetalle != -1) {
                        ContentValues calif = new ContentValues();
                        calif.put("id_detalle_matricula", idDetalle);
                        calif.put("resultado", "PENDIENTE");
                        db.insert("calificacion", null, calif);
                    }
                }
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
            db.close();
        }
        return idMatricula;
    }

    public List<Matricula> listarMatriculas() {
        List<Matricula> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT m.id_matricula, m.id_estudiante, m.id_periodo, m.fecha_matricula, m.estado, m.observaciones, " +
                "(e.nombres || ' ' || e.apellidos) AS nombre_estudiante, e.numero_cuenta, p.nombre AS periodo " +
                "FROM matricula m " +
                "INNER JOIN estudiante e ON m.id_estudiante = e.id_estudiante " +
                "INNER JOIN periodo_academico p ON m.id_periodo = p.id_periodo " +
                "ORDER BY m.id_matricula DESC";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Matricula m = new Matricula();
                m.setIdMatricula(cursor.getInt(cursor.getColumnIndexOrThrow("id_matricula")));
                m.setIdEstudiante(cursor.getInt(cursor.getColumnIndexOrThrow("id_estudiante")));
                m.setIdPeriodo(cursor.getInt(cursor.getColumnIndexOrThrow("id_periodo")));
                m.setFechaMatricula(cursor.getString(cursor.getColumnIndexOrThrow("fecha_matricula")));
                m.setEstado(cursor.getString(cursor.getColumnIndexOrThrow("estado")));
                m.setObservaciones(cursor.getString(cursor.getColumnIndexOrThrow("observaciones")));
                m.setNombreEstudiante(cursor.getString(cursor.getColumnIndexOrThrow("nombre_estudiante")));
                m.setNumeroCuenta(cursor.getString(cursor.getColumnIndexOrThrow("numero_cuenta")));
                m.setNombrePeriodo(cursor.getString(cursor.getColumnIndexOrThrow("periodo")));
                lista.add(m);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public boolean existeMatriculaEnPeriodo(int idEstudiante, int idPeriodo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_matricula FROM matricula WHERE id_estudiante = ? AND id_periodo = ?",
                new String[]{String.valueOf(idEstudiante), String.valueOf(idPeriodo)});
        boolean existe = cursor.moveToFirst();
        cursor.close();
        db.close();
        return existe;
    }
}