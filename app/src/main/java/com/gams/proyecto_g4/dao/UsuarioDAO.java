package com.gams.proyecto_g4.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
}