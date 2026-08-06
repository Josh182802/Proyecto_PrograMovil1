package com.gams.proyecto_g4.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gams.proyecto_g4.database.DatabaseHelper;
import com.gams.proyecto_g4.model.Asignatura;

import java.util.ArrayList;
import java.util.List;

public class AsignaturaDAO {

    private final DatabaseHelper dbHelper;

    public AsignaturaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertar(Asignatura asignatura) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put(
                "id_carrera",
                asignatura.getIdCarrera()
        );

        if (asignatura.getIdDocente() == null) {
            valores.putNull("id_docente");
        } else {
            valores.put(
                    "id_docente",
                    asignatura.getIdDocente()
            );
        }

        valores.put(
                "codigo_asignatura",
                asignatura.getCodigoAsignatura()
        );

        valores.put(
                "nombre",
                asignatura.getNombre()
        );

        valores.put(
                "descripcion",
                asignatura.getDescripcion()
        );

        valores.put(
                "unidades_valorativas",
                asignatura.getUnidadesValorativas()
        );

        valores.put(
                "estado",
                asignatura.isEstado() ? 1 : 0
        );

        long resultado = db.insert(
                "asignatura",
                null,
                valores
        );

        db.close();

        return resultado;
    }

    public List<Asignatura> listar() {
        List<Asignatura> lista = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "a.id_asignatura, " +
                        "a.id_carrera, " +
                        "a.id_docente, " +
                        "a.codigo_asignatura, " +
                        "a.nombre, " +
                        "a.descripcion, " +
                        "a.unidades_valorativas, " +
                        "a.estado, " +
                        "c.nombre AS nombre_carrera, " +
                        "CASE " +
                        "WHEN d.id_docente IS NULL THEN 'Sin docente' " +
                        "ELSE d.nombres || ' ' || d.apellidos " +
                        "END AS nombre_docente " +
                        "FROM asignatura a " +
                        "INNER JOIN carrera c " +
                        "ON a.id_carrera = c.id_carrera " +
                        "LEFT JOIN docente d " +
                        "ON a.id_docente = d.id_docente " +
                        "ORDER BY a.nombre ASC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Asignatura asignatura =
                        convertirCursorAAsignatura(cursor);

                lista.add(asignatura);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lista;
    }

    public Asignatura obtenerPorId(int idAsignatura) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Asignatura asignatura = null;

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "a.id_asignatura, " +
                        "a.id_carrera, " +
                        "a.id_docente, " +
                        "a.codigo_asignatura, " +
                        "a.nombre, " +
                        "a.descripcion, " +
                        "a.unidades_valorativas, " +
                        "a.estado, " +
                        "c.nombre AS nombre_carrera, " +
                        "CASE " +
                        "WHEN d.id_docente IS NULL THEN 'Sin docente' " +
                        "ELSE d.nombres || ' ' || d.apellidos " +
                        "END AS nombre_docente " +
                        "FROM asignatura a " +
                        "INNER JOIN carrera c " +
                        "ON a.id_carrera = c.id_carrera " +
                        "LEFT JOIN docente d " +
                        "ON a.id_docente = d.id_docente " +
                        "WHERE a.id_asignatura = ?",
                new String[]{
                        String.valueOf(idAsignatura)
                }
        );

        if (cursor.moveToFirst()) {
            asignatura =
                    convertirCursorAAsignatura(cursor);
        }

        cursor.close();
        db.close();

        return asignatura;
    }

    public boolean actualizar(Asignatura asignatura) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put(
                "id_carrera",
                asignatura.getIdCarrera()
        );

        if (asignatura.getIdDocente() == null) {
            valores.putNull("id_docente");
        } else {
            valores.put(
                    "id_docente",
                    asignatura.getIdDocente()
            );
        }

        valores.put(
                "codigo_asignatura",
                asignatura.getCodigoAsignatura()
        );

        valores.put(
                "nombre",
                asignatura.getNombre()
        );

        valores.put(
                "descripcion",
                asignatura.getDescripcion()
        );

        valores.put(
                "unidades_valorativas",
                asignatura.getUnidadesValorativas()
        );

        valores.put(
                "estado",
                asignatura.isEstado() ? 1 : 0
        );

        int filas = db.update(
                "asignatura",
                valores,
                "id_asignatura = ?",
                new String[]{
                        String.valueOf(
                                asignatura.getIdAsignatura()
                        )
                }
        );

        db.close();

        return filas > 0;
    }

    public boolean cambiarEstado(
            int idAsignatura,
            boolean nuevoEstado
    ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(
                "estado",
                nuevoEstado ? 1 : 0
        );

        int filas = db.update(
                "asignatura",
                valores,
                "id_asignatura = ?",
                new String[]{
                        String.valueOf(idAsignatura)
                }
        );

        db.close();

        return filas > 0;
    }

    public boolean existeCodigo(String codigoAsignatura) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_asignatura " +
                        "FROM asignatura " +
                        "WHERE codigo_asignatura = ?",
                new String[]{codigoAsignatura}
        );

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }

    public boolean existeNombreEnCarrera(
            String nombre,
            int idCarrera
    ) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_asignatura " +
                        "FROM asignatura " +
                        "WHERE nombre = ? " +
                        "AND id_carrera = ?",
                new String[]{
                        nombre,
                        String.valueOf(idCarrera)
                }
        );

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }

    public List<Asignatura> listarPorCarrera(int idCarrera) {
        List<Asignatura> lista = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "a.id_asignatura, " +
                        "a.id_carrera, " +
                        "a.id_docente, " +
                        "a.codigo_asignatura, " +
                        "a.nombre, " +
                        "a.descripcion, " +
                        "a.unidades_valorativas, " +
                        "a.estado, " +
                        "c.nombre AS nombre_carrera, " +
                        "CASE " +
                        "WHEN d.id_docente IS NULL THEN 'Sin docente' " +
                        "ELSE d.nombres || ' ' || d.apellidos " +
                        "END AS nombre_docente " +
                        "FROM asignatura a " +
                        "INNER JOIN carrera c " +
                        "ON a.id_carrera = c.id_carrera " +
                        "LEFT JOIN docente d " +
                        "ON a.id_docente = d.id_docente " +
                        "WHERE a.id_carrera = ? " +
                        "ORDER BY a.nombre ASC",
                new String[]{
                        String.valueOf(idCarrera)
                }
        );

        if (cursor.moveToFirst()) {
            do {
                lista.add(
                        convertirCursorAAsignatura(cursor)
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lista;
    }

    private Asignatura convertirCursorAAsignatura(
            Cursor cursor
    ) {
        Asignatura asignatura = new Asignatura();

        asignatura.setIdAsignatura(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "id_asignatura"
                        )
                )
        );

        asignatura.setIdCarrera(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "id_carrera"
                        )
                )
        );

        int indiceDocente =
                cursor.getColumnIndexOrThrow(
                        "id_docente"
                );

        if (cursor.isNull(indiceDocente)) {
            asignatura.setIdDocente(null);
        } else {
            asignatura.setIdDocente(
                    cursor.getInt(indiceDocente)
            );
        }

        asignatura.setCodigoAsignatura(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "codigo_asignatura"
                        )
                )
        );

        asignatura.setNombre(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "nombre"
                        )
                )
        );

        asignatura.setDescripcion(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "descripcion"
                        )
                )
        );

        asignatura.setUnidadesValorativas(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "unidades_valorativas"
                        )
                )
        );

        asignatura.setEstado(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "estado"
                        )
                ) == 1
        );

        asignatura.setNombreCarrera(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "nombre_carrera"
                        )
                )
        );

        asignatura.setNombreDocente(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "nombre_docente"
                        )
                )
        );

        return asignatura;
    }
}