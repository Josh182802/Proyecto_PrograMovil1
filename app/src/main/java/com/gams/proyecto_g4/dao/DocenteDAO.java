package com.gams.proyecto_g4.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gams.proyecto_g4.database.DatabaseHelper;
import com.gams.proyecto_g4.model.Docente;

import java.util.ArrayList;
import java.util.List;

public class DocenteDAO {

    private final DatabaseHelper dbHelper;

    public DocenteDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertar(
            Docente docente,
            String nombreUsuario,
            String correo,
            String contrasena
    ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long idDocente = -1;

        db.beginTransaction();

        try {
            ContentValues valoresUsuario = new ContentValues();
            valoresUsuario.put("id_rol", 2);
            valoresUsuario.put("nombre_usuario", nombreUsuario);
            valoresUsuario.put("correo", correo);
            valoresUsuario.put("contrasena_hash", contrasena);
            valoresUsuario.put("estado", 1);

            long idUsuario = db.insert(
                    "usuario",
                    null,
                    valoresUsuario
            );

            if (idUsuario == -1) {
                return -1;
            }

            ContentValues valoresDocente = new ContentValues();
            valoresDocente.put("id_usuario", idUsuario);
            valoresDocente.put(
                    "codigo_docente",
                    docente.getCodigoDocente()
            );
            valoresDocente.put(
                    "nombres",
                    docente.getNombres()
            );
            valoresDocente.put(
                    "apellidos",
                    docente.getApellidos()
            );
            valoresDocente.put(
                    "numero_identidad",
                    docente.getNumeroIdentidad()
            );
            valoresDocente.put(
                    "telefono",
                    docente.getTelefono()
            );
            valoresDocente.put(
                    "especialidad",
                    docente.getEspecialidad()
            );
            valoresDocente.put(
                    "estado_laboral",
                    docente.getEstadoLaboral()
            );

            idDocente = db.insert(
                    "docente",
                    null,
                    valoresDocente
            );

            if (idDocente == -1) {
                return -1;
            }

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
            db.close();
        }

        return idDocente;
    }

    public List<Docente> listar() {
        List<Docente> lista = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "id_docente, " +
                        "id_usuario, " +
                        "codigo_docente, " +
                        "nombres, " +
                        "apellidos, " +
                        "numero_identidad, " +
                        "telefono, " +
                        "especialidad, " +
                        "estado_laboral " +
                        "FROM docente " +
                        "ORDER BY apellidos, nombres",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Docente docente = convertirCursorADocente(cursor);
                lista.add(docente);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lista;
    }

    public Docente obtenerPorId(int idDocente) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Docente docente = null;

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "id_docente, " +
                        "id_usuario, " +
                        "codigo_docente, " +
                        "nombres, " +
                        "apellidos, " +
                        "numero_identidad, " +
                        "telefono, " +
                        "especialidad, " +
                        "estado_laboral " +
                        "FROM docente " +
                        "WHERE id_docente = ?",
                new String[]{
                        String.valueOf(idDocente)
                }
        );

        if (cursor.moveToFirst()) {
            docente = convertirCursorADocente(cursor);
        }

        cursor.close();
        db.close();

        return docente;
    }

    public boolean actualizar(Docente docente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(
                "codigo_docente",
                docente.getCodigoDocente()
        );
        valores.put(
                "nombres",
                docente.getNombres()
        );
        valores.put(
                "apellidos",
                docente.getApellidos()
        );
        valores.put(
                "numero_identidad",
                docente.getNumeroIdentidad()
        );
        valores.put(
                "telefono",
                docente.getTelefono()
        );
        valores.put(
                "especialidad",
                docente.getEspecialidad()
        );
        valores.put(
                "estado_laboral",
                docente.getEstadoLaboral()
        );

        int filas = db.update(
                "docente",
                valores,
                "id_docente = ?",
                new String[]{
                        String.valueOf(
                                docente.getIdDocente()
                        )
                }
        );

        db.close();

        return filas > 0;
    }

    public boolean cambiarEstadoLaboral(
            int idDocente,
            String nuevoEstado
    ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("estado_laboral", nuevoEstado);

        int filas = db.update(
                "docente",
                valores,
                "id_docente = ?",
                new String[]{
                        String.valueOf(idDocente)
                }
        );

        db.close();

        return filas > 0;
    }

    public boolean existeCodigo(String codigoDocente) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_docente " +
                        "FROM docente " +
                        "WHERE codigo_docente = ?",
                new String[]{codigoDocente}
        );

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }

    public boolean existeIdentidad(String numeroIdentidad) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_docente " +
                        "FROM docente " +
                        "WHERE numero_identidad = ?",
                new String[]{numeroIdentidad}
        );

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }

    private Docente convertirCursorADocente(Cursor cursor) {
        Docente docente = new Docente();

        docente.setIdDocente(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "id_docente"
                        )
                )
        );

        docente.setIdUsuario(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "id_usuario"
                        )
                )
        );

        docente.setCodigoDocente(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "codigo_docente"
                        )
                )
        );

        docente.setNombres(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "nombres"
                        )
                )
        );

        docente.setApellidos(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "apellidos"
                        )
                )
        );

        docente.setNumeroIdentidad(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "numero_identidad"
                        )
                )
        );

        docente.setTelefono(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "telefono"
                        )
                )
        );

        docente.setEspecialidad(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "especialidad"
                        )
                )
        );

        docente.setEstadoLaboral(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "estado_laboral"
                        )
                )
        );

        return docente;
    }
}