package com.gams.proyecto_g4.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.gams.proyecto_g4.database.DatabaseHelper;
import com.gams.proyecto_g4.model.Calificacion;

import java.util.ArrayList;
import java.util.List;

public class CalificacionDAO {
    private final DatabaseHelper dbHelper;

    public CalificacionDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<Calificacion> listarCalificaciones() {
        List<Calificacion> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT c.id_calificacion, c.id_detalle_matricula, c.nota_parcial_1, c.nota_parcial_2, " +
                "c.nota_parcial_3, c.nota_reposicion, c.nota_final, c.resultado, c.observaciones, " +
                "(e.nombres || ' ' || e.apellidos) AS estudiante, e.numero_cuenta, a.nombre AS asignatura " +
                "FROM calificacion c " +
                "INNER JOIN detalle_matricula dm ON c.id_detalle_matricula = dm.id_detalle_matricula " +
                "INNER JOIN matricula m ON dm.id_matricula = m.id_matricula " +
                "INNER JOIN estudiante e ON m.id_estudiante = e.id_estudiante " +
                "INNER JOIN asignatura a ON dm.id_asignatura = a.id_asignatura";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Calificacion calif = new Calificacion();
                calif.setIdCalificacion(cursor.getInt(cursor.getColumnIndexOrThrow("id_calificacion")));
                calif.setIdDetalleMatricula(cursor.getInt(cursor.getColumnIndexOrThrow("id_detalle_matricula")));

                calif.setNotaParcial1(cursor.isNull(cursor.getColumnIndexOrThrow("nota_parcial_1")) ? null : cursor.getDouble(cursor.getColumnIndexOrThrow("nota_parcial_1")));
                calif.setNotaParcial2(cursor.isNull(cursor.getColumnIndexOrThrow("nota_parcial_2")) ? null : cursor.getDouble(cursor.getColumnIndexOrThrow("nota_parcial_2")));
                calif.setNotaParcial3(cursor.isNull(cursor.getColumnIndexOrThrow("nota_parcial_3")) ? null : cursor.getDouble(cursor.getColumnIndexOrThrow("nota_parcial_3")));
                calif.setNotaReposicion(cursor.isNull(cursor.getColumnIndexOrThrow("nota_reposicion")) ? null : cursor.getDouble(cursor.getColumnIndexOrThrow("nota_reposicion")));
                calif.setNotaFinal(cursor.isNull(cursor.getColumnIndexOrThrow("nota_final")) ? null : cursor.getDouble(cursor.getColumnIndexOrThrow("nota_final")));

                calif.setResultado(cursor.getString(cursor.getColumnIndexOrThrow("resultado")));
                calif.setObservaciones(cursor.getString(cursor.getColumnIndexOrThrow("observaciones")));
                calif.setEstudianteNombre(cursor.getString(cursor.getColumnIndexOrThrow("estudiante")));
                calif.setNumeroCuenta(cursor.getString(cursor.getColumnIndexOrThrow("numero_cuenta")));
                calif.setAsignaturaNombre(cursor.getString(cursor.getColumnIndexOrThrow("asignatura")));
                lista.add(calif);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public boolean guardarOActualizarCalificacion(Calificacion calif) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();

        // Lógica de cálculo de promedios y sustitución de nota por reposición
        double p1 = calif.getNotaParcial1() != null ? calif.getNotaParcial1() : 0.0;
        double p2 = calif.getNotaParcial2() != null ? calif.getNotaParcial2() : 0.0;
        double p3 = calif.getNotaParcial3() != null ? calif.getNotaParcial3() : 0.0;

        if (calif.getNotaReposicion() != null) {
            double repo = calif.getNotaReposicion();
            // Reemplaza la nota más baja
            if (p1 <= p2 && p1 <= p3) p1 = Math.max(p1, repo);
            else if (p2 <= p1 && p2 <= p3) p2 = Math.max(p2, repo);
            else p3 = Math.max(p3, repo);
        }

        double notaFinal = (p1 + p2 + p3) / 3.0;
        String resultado = (notaFinal >= 65.0) ? "APROBADO" : "REPROBADO";

        valores.put("nota_parcial_1", calif.getNotaParcial1());
        valores.put("nota_parcial_2", calif.getNotaParcial2());
        valores.put("nota_parcial_3", calif.getNotaParcial3());
        valores.put("nota_reposicion", calif.getNotaReposicion());
        valores.put("nota_final", notaFinal);
        valores.put("resultado", resultado);
        valores.put("observaciones", calif.getObservaciones());

        int filas = db.update("calificacion", valores, "id_calificacion = ?", new String[]{String.valueOf(calif.getIdCalificacion())});

        // Actualizar el estado en el detalle de la matrícula
        if (filas > 0) {
            ContentValues detValores = new ContentValues();
            detValores.put("estado", resultado.equals("APROBADO") ? "APROBADA" : "REPROBADA");
            db.update("detalle_matricula", detValores, "id_detalle_matricula = ?", new String[]{String.valueOf(calif.getIdDetalleMatricula())});
        }

        db.close();
        return filas > 0;
    }
}