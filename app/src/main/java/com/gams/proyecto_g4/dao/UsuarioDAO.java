package com.gams.proyecto_g4.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import com.gams.proyecto_g4.database.DatabaseHelper;

public class UsuarioDAO {

    private DatabaseHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean validarLogin(String usuario, String contrasena) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM usuario WHERE nombre_usuario=? AND contrasena_hash=?",
                new String[]{usuario, contrasena}
        );

        boolean existe = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return existe;
    }

    public int obtenerIdUsuario(String usuario) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_usuario FROM usuario WHERE nombre_usuario=?",
                new String[]{usuario}
        );

        int idUsuario = -1;

        if (cursor.moveToFirst()) {
            idUsuario = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return idUsuario;
    }

    public String obtenerRol(String usuario) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT rol.nombre " +
                        "FROM usuario " +
                        "INNER JOIN rol ON usuario.id_rol = rol.id_rol " +
                        "WHERE usuario.nombre_usuario=?",
                new String[]{usuario}
        );

        String rol = "";

        if (cursor.moveToFirst()) {
            rol = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return rol;
    }

    public long insertarUsuario(int idRol, String nombreUsuario, String correo, String contrasena) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("id_rol", idRol);
        valores.put("nombre_usuario", nombreUsuario);
        valores.put("correo", correo);
        valores.put("contrasena_hash", contrasena);
        valores.put("estado", 1);

        long idUsuario = db.insert(
                "usuario",
                null,
                valores
        );

        db.close();

        return idUsuario;
    }

    public boolean existeNombreUsuario(String nombreUsuario) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_usuario " +
                        "FROM usuario " +
                        "WHERE nombre_usuario = ?",
                new String[]{nombreUsuario}
        );

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }

    public boolean existeCorreo(String correo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_usuario " +
                        "FROM usuario " +
                        "WHERE correo = ?",
                new String[]{correo}
        );

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }
}