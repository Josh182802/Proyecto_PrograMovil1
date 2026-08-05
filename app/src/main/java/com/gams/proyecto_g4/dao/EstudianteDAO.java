package com.gams.proyecto_g4.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gams.proyecto_g4.database.DatabaseHelper;
import com.gams.proyecto_g4.model.Estudiante;

import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    private final DatabaseHelper dbHelper;

    public EstudianteDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertar(
            Estudiante estudiante,
            String nombreUsuario,
            String correo,
            String contrasena
    ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long idEstudiante = -1;

        db.beginTransaction();

        try {
            ContentValues valoresUsuario = new ContentValues();

            // Rol 3 = ESTUDIANTE.
            valoresUsuario.put("id_rol", 3);
            valoresUsuario.put(
                    "nombre_usuario",
                    nombreUsuario
            );
            valoresUsuario.put("correo", correo);
            valoresUsuario.put(
                    "contrasena_hash",
                    contrasena
            );
            valoresUsuario.put("estado", 1);

            long idUsuario = db.insert(
                    "usuario",
                    null,
                    valoresUsuario
            );

            if (idUsuario == -1) {
                return -1;
            }

            ContentValues valoresEstudiante =
                    new ContentValues();

            valoresEstudiante.put(
                    "id_usuario",
                    idUsuario
            );

            valoresEstudiante.put(
                    "id_carrera",
                    estudiante.getIdCarrera()
            );

            valoresEstudiante.put(
                    "numero_cuenta",
                    estudiante.getNumeroCuenta()
            );

            valoresEstudiante.put(
                    "nombres",
                    estudiante.getNombres()
            );

            valoresEstudiante.put(
                    "apellidos",
                    estudiante.getApellidos()
            );

            valoresEstudiante.put(
                    "numero_identidad",
                    estudiante.getNumeroIdentidad()
            );

            if (
                    estudiante.getFechaNacimiento() == null
                            || estudiante.getFechaNacimiento()
                            .trim()
                            .isEmpty()
            ) {
                valoresEstudiante.putNull(
                        "fecha_nacimiento"
                );
            } else {
                valoresEstudiante.put(
                        "fecha_nacimiento",
                        estudiante.getFechaNacimiento()
                );
            }

            valoresEstudiante.put(
                    "direccion",
                    estudiante.getDireccion()
            );

            valoresEstudiante.put(
                    "telefono",
                    estudiante.getTelefono()
            );

            valoresEstudiante.put(
                    "estado_academico",
                    estudiante.getEstadoAcademico()
            );

            idEstudiante = db.insert(
                    "estudiante",
                    null,
                    valoresEstudiante
            );

            if (idEstudiante == -1) {
                return -1;
            }

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
            db.close();
        }

        return idEstudiante;
    }

    public List<Estudiante> listar() {
        List<Estudiante> lista = new ArrayList<>();

        SQLiteDatabase db =
                dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "e.id_estudiante, " +
                        "e.id_usuario, " +
                        "e.id_carrera, " +
                        "e.numero_cuenta, " +
                        "e.nombres, " +
                        "e.apellidos, " +
                        "e.numero_identidad, " +
                        "e.fecha_nacimiento, " +
                        "e.direccion, " +
                        "e.telefono, " +
                        "e.fecha_ingreso, " +
                        "e.estado_academico, " +
                        "c.nombre AS nombre_carrera " +
                        "FROM estudiante e " +
                        "INNER JOIN carrera c " +
                        "ON e.id_carrera = c.id_carrera " +
                        "ORDER BY e.apellidos, e.nombres",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                lista.add(
                        convertirCursorAEstudiante(cursor)
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lista;
    }

    public Estudiante obtenerPorId(
            int idEstudiante
    ) {
        SQLiteDatabase db =
                dbHelper.getReadableDatabase();

        Estudiante estudiante = null;

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "e.id_estudiante, " +
                        "e.id_usuario, " +
                        "e.id_carrera, " +
                        "e.numero_cuenta, " +
                        "e.nombres, " +
                        "e.apellidos, " +
                        "e.numero_identidad, " +
                        "e.fecha_nacimiento, " +
                        "e.direccion, " +
                        "e.telefono, " +
                        "e.fecha_ingreso, " +
                        "e.estado_academico, " +
                        "c.nombre AS nombre_carrera " +
                        "FROM estudiante e " +
                        "INNER JOIN carrera c " +
                        "ON e.id_carrera = c.id_carrera " +
                        "WHERE e.id_estudiante = ?",
                new String[]{
                        String.valueOf(idEstudiante)
                }
        );

        if (cursor.moveToFirst()) {
            estudiante =
                    convertirCursorAEstudiante(cursor);
        }

        cursor.close();
        db.close();

        return estudiante;
    }

    public boolean actualizar(
            Estudiante estudiante
    ) {
        SQLiteDatabase db =
                dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put(
                "id_carrera",
                estudiante.getIdCarrera()
        );

        valores.put(
                "numero_cuenta",
                estudiante.getNumeroCuenta()
        );

        valores.put(
                "nombres",
                estudiante.getNombres()
        );

        valores.put(
                "apellidos",
                estudiante.getApellidos()
        );

        valores.put(
                "numero_identidad",
                estudiante.getNumeroIdentidad()
        );

        if (
                estudiante.getFechaNacimiento() == null
                        || estudiante.getFechaNacimiento()
                        .trim()
                        .isEmpty()
        ) {
            valores.putNull("fecha_nacimiento");
        } else {
            valores.put(
                    "fecha_nacimiento",
                    estudiante.getFechaNacimiento()
            );
        }

        valores.put(
                "direccion",
                estudiante.getDireccion()
        );

        valores.put(
                "telefono",
                estudiante.getTelefono()
        );

        valores.put(
                "estado_academico",
                estudiante.getEstadoAcademico()
        );

        int filas = db.update(
                "estudiante",
                valores,
                "id_estudiante = ?",
                new String[]{
                        String.valueOf(
                                estudiante.getIdEstudiante()
                        )
                }
        );

        db.close();

        return filas > 0;
    }

    public boolean cambiarEstadoAcademico(
            int idEstudiante,
            String nuevoEstado
    ) {
        SQLiteDatabase db =
                dbHelper.getWritableDatabase();

        ContentValues valores =
                new ContentValues();

        valores.put(
                "estado_academico",
                nuevoEstado
        );

        int filas = db.update(
                "estudiante",
                valores,
                "id_estudiante = ?",
                new String[]{
                        String.valueOf(idEstudiante)
                }
        );

        db.close();

        return filas > 0;
    }

    public boolean existeNumeroCuenta(
            String numeroCuenta
    ) {
        SQLiteDatabase db =
                dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_estudiante " +
                        "FROM estudiante " +
                        "WHERE numero_cuenta = ?",
                new String[]{numeroCuenta}
        );

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }

    public boolean existeIdentidad(
            String numeroIdentidad
    ) {
        SQLiteDatabase db =
                dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_estudiante " +
                        "FROM estudiante " +
                        "WHERE numero_identidad = ?",
                new String[]{numeroIdentidad}
        );

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }

    public List<Estudiante> buscar(
            String texto
    ) {
        List<Estudiante> lista =
                new ArrayList<>();

        SQLiteDatabase db =
                dbHelper.getReadableDatabase();

        String busqueda = "%" + texto + "%";

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "e.id_estudiante, " +
                        "e.id_usuario, " +
                        "e.id_carrera, " +
                        "e.numero_cuenta, " +
                        "e.nombres, " +
                        "e.apellidos, " +
                        "e.numero_identidad, " +
                        "e.fecha_nacimiento, " +
                        "e.direccion, " +
                        "e.telefono, " +
                        "e.fecha_ingreso, " +
                        "e.estado_academico, " +
                        "c.nombre AS nombre_carrera " +
                        "FROM estudiante e " +
                        "INNER JOIN carrera c " +
                        "ON e.id_carrera = c.id_carrera " +
                        "WHERE e.numero_cuenta LIKE ? " +
                        "OR e.nombres LIKE ? " +
                        "OR e.apellidos LIKE ? " +
                        "OR e.numero_identidad LIKE ? " +
                        "ORDER BY e.apellidos, e.nombres",
                new String[]{
                        busqueda,
                        busqueda,
                        busqueda,
                        busqueda
                }
        );

        if (cursor.moveToFirst()) {
            do {
                lista.add(
                        convertirCursorAEstudiante(cursor)
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lista;
    }

    private Estudiante convertirCursorAEstudiante(
            Cursor cursor
    ) {
        Estudiante estudiante = new Estudiante();

        estudiante.setIdEstudiante(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "id_estudiante"
                        )
                )
        );

        estudiante.setIdUsuario(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "id_usuario"
                        )
                )
        );

        estudiante.setIdCarrera(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "id_carrera"
                        )
                )
        );

        estudiante.setNumeroCuenta(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "numero_cuenta"
                        )
                )
        );

        estudiante.setNombres(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "nombres"
                        )
                )
        );

        estudiante.setApellidos(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "apellidos"
                        )
                )
        );

        estudiante.setNumeroIdentidad(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "numero_identidad"
                        )
                )
        );

        estudiante.setFechaNacimiento(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "fecha_nacimiento"
                        )
                )
        );

        estudiante.setDireccion(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "direccion"
                        )
                )
        );

        estudiante.setTelefono(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "telefono"
                        )
                )
        );

        estudiante.setFechaIngreso(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "fecha_ingreso"
                        )
                )
        );

        estudiante.setEstadoAcademico(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "estado_academico"
                        )
                )
        );

        estudiante.setNombreCarrera(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "nombre_carrera"
                        )
                )
        );

        return estudiante;
    }
}