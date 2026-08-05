package com.gams.proyecto_g4.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gams.proyecto_g4.database.DatabaseHelper;
import com.gams.proyecto_g4.model.Carrera;

import java.util.ArrayList;
import java.util.List;

public class CarreraDAO {

    private final DatabaseHelper dbHelper;

    public CarreraDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertar(Carrera carrera) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("codigo_carrera", carrera.getCodigoCarrera());
        valores.put("nombre", carrera.getNombre());
        valores.put("descripcion", carrera.getDescripcion());
        valores.put("duracion_anios", carrera.getDuracionAnios());
        valores.put("estado", carrera.isEstado() ? 1 : 0);

        long resultado = db.insert(
                "carrera",
                null,
                valores
        );

        db.close();

        return resultado;
    }

    public List<Carrera> listar() {
        List<Carrera> listaCarreras = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_carrera, codigo_carrera, nombre, " +
                        "descripcion, duracion_anios, estado " +
                        "FROM carrera " +
                        "ORDER BY nombre ASC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Carrera carrera = new Carrera();

                carrera.setIdCarrera(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow("id_carrera")
                        )
                );

                carrera.setCodigoCarrera(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("codigo_carrera")
                        )
                );

                carrera.setNombre(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("nombre")
                        )
                );

                carrera.setDescripcion(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("descripcion")
                        )
                );

                carrera.setDuracionAnios(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow("duracion_anios")
                        )
                );

                carrera.setEstado(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow("estado")
                        ) == 1
                );

                listaCarreras.add(carrera);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return listaCarreras;
    }

    public Carrera obtenerPorId(int idCarrera) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Carrera carrera = null;

        Cursor cursor = db.rawQuery(
                "SELECT id_carrera, codigo_carrera, nombre, " +
                        "descripcion, duracion_anios, estado " +
                        "FROM carrera " +
                        "WHERE id_carrera = ?",
                new String[]{
                        String.valueOf(idCarrera)
                }
        );

        if (cursor.moveToFirst()) {
            carrera = new Carrera();

            carrera.setIdCarrera(
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow("id_carrera")
                    )
            );

            carrera.setCodigoCarrera(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("codigo_carrera")
                    )
            );

            carrera.setNombre(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("nombre")
                    )
            );

            carrera.setDescripcion(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("descripcion")
                    )
            );

            carrera.setDuracionAnios(
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow("duracion_anios")
                    )
            );

            carrera.setEstado(
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow("estado")
                    ) == 1
            );
        }

        cursor.close();
        db.close();

        return carrera;
    }

    public boolean actualizar(Carrera carrera) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("codigo_carrera", carrera.getCodigoCarrera());
        valores.put("nombre", carrera.getNombre());
        valores.put("descripcion", carrera.getDescripcion());
        valores.put("duracion_anios", carrera.getDuracionAnios());
        valores.put("estado", carrera.isEstado() ? 1 : 0);

        int filasAfectadas = db.update(
                "carrera",
                valores,
                "id_carrera = ?",
                new String[]{
                        String.valueOf(carrera.getIdCarrera())
                }
        );

        db.close();

        return filasAfectadas > 0;
    }

    public boolean cambiarEstado(
            int idCarrera,
            boolean nuevoEstado
    ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("estado", nuevoEstado ? 1 : 0);

        int filasAfectadas = db.update(
                "carrera",
                valores,
                "id_carrera = ?",
                new String[]{
                        String.valueOf(idCarrera)
                }
        );

        db.close();

        return filasAfectadas > 0;
    }

    public boolean existeCodigo(String codigoCarrera) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_carrera " +
                        "FROM carrera " +
                        "WHERE codigo_carrera = ?",
                new String[]{codigoCarrera}
        );

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }

    public boolean existeNombre(String nombre) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_carrera " +
                        "FROM carrera " +
                        "WHERE nombre = ?",
                new String[]{nombre}
        );

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }
}