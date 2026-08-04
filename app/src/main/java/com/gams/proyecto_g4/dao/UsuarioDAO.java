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
}