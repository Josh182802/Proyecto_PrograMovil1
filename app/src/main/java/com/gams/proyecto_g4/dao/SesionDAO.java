package com.gams.proyecto_g4.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gams.proyecto_g4.database.DatabaseHelper;

import java.util.UUID;

public class SesionDAO {

    private DatabaseHelper dbHelper;

    public SesionDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    public void crearSesion(int idUsuario) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String token = UUID.randomUUID().toString();

        db.execSQL(
                "INSERT INTO sesion " +
                        "(id_usuario, token_sesion) VALUES (?,?)",
                new Object[]{
                        idUsuario,
                        token
                }
        );

        db.close();
    }


    public void cerrarSesion(int idUsuario) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL(
                "UPDATE sesion SET estado=0, fecha_finalizacion=CURRENT_TIMESTAMP " +
                        "WHERE id_usuario=? AND estado=1",
                new Object[]{
                        idUsuario
                }
        );

        db.close();
    }


    public boolean existeSesionActiva() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM sesion WHERE estado=1",
                null
        );

        boolean existe = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return existe;
    }
}