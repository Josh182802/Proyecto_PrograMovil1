package com.gams.proyecto_g4.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gams.proyecto_g4.database.DatabaseHelper;

import java.util.UUID;

public class RecuperacionDAO {

    private DatabaseHelper dbHelper;

    public RecuperacionDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean solicitarRecuperacion(String correo) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id_usuario FROM usuario WHERE correo=?",
                new String[]{correo}
        );

        if (!cursor.moveToFirst()) {

            cursor.close();
            db.close();

            return false;
        }

        int idUsuario = cursor.getInt(0);

        cursor.close();

        String token = UUID.randomUUID().toString();

        try {

            db.execSQL(
                    "INSERT INTO recuperacion_contrasena " +
                            "(id_usuario, token_recuperacion, fecha_vencimiento) " +
                            "VALUES (?,?,datetime('now','+1 day'))",
                    new Object[]{
                            idUsuario,
                            token
                    }
            );

        } catch (Exception e) {

            db.close();

            return false;
        }

        db.close();

        return true;
    }
}