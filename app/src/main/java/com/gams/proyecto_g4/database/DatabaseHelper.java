package com.gams.proyecto_g4.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "control_academico.db";

    //Si se modifica una tabla se tiene que aumentar el numero de la version de la base de datos
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);

        // Activa las llaves foráneas en SQLite.
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // 1. TABLA ROL
        db.execSQL(
                "CREATE TABLE rol (" +
                        "id_rol INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT NOT NULL UNIQUE, " +
                        "descripcion TEXT, " +
                        "estado INTEGER NOT NULL DEFAULT 1 " +
                        "CHECK (estado IN (0, 1))" +
                        ")"
        );

        // 2. TABLA USUARIO
        db.execSQL(
                "CREATE TABLE usuario (" +
                        "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id_rol INTEGER NOT NULL, " +
                        "nombre_usuario TEXT NOT NULL UNIQUE, " +
                        "correo TEXT NOT NULL UNIQUE, " +
                        "contrasena_hash TEXT NOT NULL, " +
                        "estado INTEGER NOT NULL DEFAULT 1 " +
                        "CHECK (estado IN (0, 1)), " +
                        "fecha_creacion TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +

                        "FOREIGN KEY (id_rol) " +
                        "REFERENCES rol(id_rol) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE RESTRICT" +
                        ")"
        );

        // 3. TABLA CARRERA
        db.execSQL(
                "CREATE TABLE carrera (" +
                        "id_carrera INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "codigo_carrera TEXT NOT NULL UNIQUE, " +
                        "nombre TEXT NOT NULL UNIQUE, " +
                        "descripcion TEXT, " +
                        "duracion_anios INTEGER, " +
                        "estado INTEGER NOT NULL DEFAULT 1 " +
                        "CHECK (estado IN (0, 1)), " +

                        "CHECK (" +
                        "duracion_anios IS NULL " +
                        "OR duracion_anios > 0" +
                        ")" +
                        ")"
        );

        // 4. TABLA ESTUDIANTE
        db.execSQL(
                "CREATE TABLE estudiante (" +
                        "id_estudiante INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id_usuario INTEGER NOT NULL UNIQUE, " +
                        "id_carrera INTEGER NOT NULL, " +
                        "numero_cuenta TEXT NOT NULL UNIQUE, " +
                        "nombres TEXT NOT NULL, " +
                        "apellidos TEXT NOT NULL, " +
                        "numero_identidad TEXT NOT NULL UNIQUE, " +
                        "fecha_nacimiento TEXT, " +
                        "direccion TEXT, " +
                        "telefono TEXT, " +
                        "fecha_ingreso TEXT NOT NULL DEFAULT CURRENT_DATE, " +

                        "estado_academico TEXT NOT NULL DEFAULT 'ACTIVO' " +
                        "CHECK (estado_academico IN (" +
                        "'ACTIVO', " +
                        "'INACTIVO', " +
                        "'GRADUADO', " +
                        "'SUSPENDIDO', " +
                        "'RETIRADO'" +
                        ")), " +

                        "FOREIGN KEY (id_usuario) " +
                        "REFERENCES usuario(id_usuario) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE RESTRICT, " +

                        "FOREIGN KEY (id_carrera) " +
                        "REFERENCES carrera(id_carrera) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE RESTRICT" +
                        ")"
        );

        // 5. TABLA DOCENTE
        db.execSQL(
                "CREATE TABLE docente (" +
                        "id_docente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id_usuario INTEGER NOT NULL UNIQUE, " +
                        "codigo_docente TEXT NOT NULL UNIQUE, " +
                        "nombres TEXT NOT NULL, " +
                        "apellidos TEXT NOT NULL, " +
                        "numero_identidad TEXT NOT NULL UNIQUE, " +
                        "telefono TEXT, " +
                        "especialidad TEXT, " +

                        "estado_laboral TEXT NOT NULL DEFAULT 'ACTIVO' " +
                        "CHECK (estado_laboral IN (" +
                        "'ACTIVO', " +
                        "'INACTIVO', " +
                        "'SUSPENDIDO', " +
                        "'RETIRADO'" +
                        ")), " +

                        "FOREIGN KEY (id_usuario) " +
                        "REFERENCES usuario(id_usuario) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE RESTRICT" +
                        ")"
        );

        // 6. TABLA PERIODO ACADÉMICO
        db.execSQL(
                "CREATE TABLE periodo_academico (" +
                        "id_periodo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT NOT NULL, " +
                        "anio_academico INTEGER NOT NULL, " +
                        "fecha_inicio TEXT NOT NULL, " +
                        "fecha_finalizacion TEXT NOT NULL, " +
                        "estado INTEGER NOT NULL DEFAULT 1 " +
                        "CHECK (estado IN (0, 1)), " +

                        "UNIQUE (nombre, anio_academico), " +

                        "CHECK (anio_academico >= 2000), " +

                        "CHECK (fecha_finalizacion >= fecha_inicio)" +
                        ")"
        );

        // 7. TABLA ASIGNATURA
        db.execSQL(
                "CREATE TABLE asignatura (" +
                        "id_asignatura INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id_carrera INTEGER NOT NULL, " +
                        "id_docente INTEGER, " +
                        "codigo_asignatura TEXT NOT NULL UNIQUE, " +
                        "nombre TEXT NOT NULL, " +
                        "descripcion TEXT, " +
                        "unidades_valorativas INTEGER NOT NULL, " +
                        "estado INTEGER NOT NULL DEFAULT 1 " +
                        "CHECK (estado IN (0, 1)), " +

                        "FOREIGN KEY (id_carrera) " +
                        "REFERENCES carrera(id_carrera) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE RESTRICT, " +

                        "FOREIGN KEY (id_docente) " +
                        "REFERENCES docente(id_docente) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE SET NULL, " +

                        "UNIQUE (nombre, id_carrera), " +

                        "CHECK (unidades_valorativas > 0)" +
                        ")"
        );

        // 8. TABLA MATRÍCULA
        db.execSQL(
                "CREATE TABLE matricula (" +
                        "id_matricula INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id_estudiante INTEGER NOT NULL, " +
                        "id_periodo INTEGER NOT NULL, " +
                        "fecha_matricula TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +

                        "estado TEXT NOT NULL DEFAULT 'ACTIVA' " +
                        "CHECK (estado IN (" +
                        "'ACTIVA', " +
                        "'PENDIENTE', " +
                        "'CANCELADA', " +
                        "'FINALIZADA'" +
                        ")), " +

                        "observaciones TEXT, " +

                        "FOREIGN KEY (id_estudiante) " +
                        "REFERENCES estudiante(id_estudiante) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE RESTRICT, " +

                        "FOREIGN KEY (id_periodo) " +
                        "REFERENCES periodo_academico(id_periodo) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE RESTRICT, " +

                        "UNIQUE (id_estudiante, id_periodo)" +
                        ")"
        );

        // 9. TABLA DETALLE MATRÍCULA
        db.execSQL(
                "CREATE TABLE detalle_matricula (" +
                        "id_detalle_matricula INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id_matricula INTEGER NOT NULL, " +
                        "id_asignatura INTEGER NOT NULL, " +
                        "fecha_inscripcion TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +

                        "estado TEXT NOT NULL DEFAULT 'INSCRITA' " +
                        "CHECK (estado IN (" +
                        "'INSCRITA', " +
                        "'CANCELADA', " +
                        "'APROBADA', " +
                        "'REPROBADA', " +
                        "'RETIRADA'" +
                        ")), " +

                        "FOREIGN KEY (id_matricula) " +
                        "REFERENCES matricula(id_matricula) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE CASCADE, " +

                        "FOREIGN KEY (id_asignatura) " +
                        "REFERENCES asignatura(id_asignatura) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE RESTRICT, " +

                        "UNIQUE (id_matricula, id_asignatura)" +
                        ")"
        );

        // 10. TABLA CALIFICACIÓN
        db.execSQL(
                "CREATE TABLE calificacion (" +
                        "id_calificacion INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id_detalle_matricula INTEGER NOT NULL UNIQUE, " +
                        "nota_parcial_1 REAL, " +
                        "nota_parcial_2 REAL, " +
                        "nota_parcial_3 REAL, " +
                        "nota_reposicion REAL, " +
                        "nota_final REAL, " +

                        "resultado TEXT NOT NULL DEFAULT 'PENDIENTE' " +
                        "CHECK (resultado IN (" +
                        "'APROBADO', " +
                        "'REPROBADO', " +
                        "'PENDIENTE'" +
                        ")), " +

                        "observaciones TEXT, " +
                        "fecha_registro TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +

                        "FOREIGN KEY (id_detalle_matricula) " +
                        "REFERENCES detalle_matricula(id_detalle_matricula) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE CASCADE, " +

                        "CHECK (" +
                        "nota_parcial_1 IS NULL " +
                        "OR nota_parcial_1 BETWEEN 0 AND 100" +
                        "), " +

                        "CHECK (" +
                        "nota_parcial_2 IS NULL " +
                        "OR nota_parcial_2 BETWEEN 0 AND 100" +
                        "), " +

                        "CHECK (" +
                        "nota_parcial_3 IS NULL " +
                        "OR nota_parcial_3 BETWEEN 0 AND 100" +
                        "), " +

                        "CHECK (" +
                        "nota_reposicion IS NULL " +
                        "OR nota_reposicion BETWEEN 0 AND 100" +
                        "), " +

                        "CHECK (" +
                        "nota_final IS NULL " +
                        "OR nota_final BETWEEN 0 AND 100" +
                        ")" +
                        ")"
        );

        // 11. TABLA SESIÓN
        db.execSQL(
                "CREATE TABLE sesion (" +
                        "id_sesion INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id_usuario INTEGER NOT NULL, " +
                        "token_sesion TEXT NOT NULL UNIQUE, " +
                        "fecha_inicio TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                        "fecha_finalizacion TEXT, " +
                        "estado INTEGER NOT NULL DEFAULT 1 " +
                        "CHECK (estado IN (0, 1)), " +

                        "FOREIGN KEY (id_usuario) " +
                        "REFERENCES usuario(id_usuario) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE CASCADE" +
                        ")"
        );

        // 12. TABLA RECUPERACIÓN DE CONTRASEÑA
        db.execSQL(
                "CREATE TABLE recuperacion_contrasena (" +
                        "id_recuperacion INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id_usuario INTEGER NOT NULL, " +
                        "token_recuperacion TEXT NOT NULL UNIQUE, " +
                        "fecha_solicitud TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                        "fecha_vencimiento TEXT NOT NULL, " +

                        "estado TEXT NOT NULL DEFAULT 'ACTIVO' " +
                        "CHECK (estado IN (" +
                        "'ACTIVO', " +
                        "'UTILIZADO', " +
                        "'VENCIDO', " +
                        "'CANCELADO'" +
                        ")), " +

                        "fecha_utilizacion TEXT, " +

                        "FOREIGN KEY (id_usuario) " +
                        "REFERENCES usuario(id_usuario) " +
                        "ON UPDATE CASCADE " +
                        "ON DELETE CASCADE" +
                        ")"
        );

        // Índices
        db.execSQL(
                "CREATE INDEX idx_usuario_rol " +
                        "ON usuario(id_rol)"
        );

        db.execSQL(
                "CREATE INDEX idx_estudiante_carrera " +
                        "ON estudiante(id_carrera)"
        );

        db.execSQL(
                "CREATE INDEX idx_asignatura_carrera " +
                        "ON asignatura(id_carrera)"
        );

        db.execSQL(
                "CREATE INDEX idx_asignatura_docente " +
                        "ON asignatura(id_docente)"
        );

        db.execSQL(
                "CREATE INDEX idx_matricula_estudiante " +
                        "ON matricula(id_estudiante)"
        );

        db.execSQL(
                "CREATE INDEX idx_matricula_periodo " +
                        "ON matricula(id_periodo)"
        );

        db.execSQL(
                "CREATE INDEX idx_detalle_matricula " +
                        "ON detalle_matricula(id_matricula)"
        );

        db.execSQL(
                "CREATE INDEX idx_detalle_asignatura " +
                        "ON detalle_matricula(id_asignatura)"
        );

        db.execSQL(
                "CREATE INDEX idx_sesion_usuario " +
                        "ON sesion(id_usuario)"
        );

        db.execSQL(
                "CREATE INDEX idx_recuperacion_usuario " +
                        "ON recuperacion_contrasena(id_usuario)"
        );

        // ROLES INICIALES
        db.execSQL(
                "INSERT INTO rol (nombre, descripcion) VALUES " +
                        "('ADMINISTRADOR', " +
                        "'Gestiona toda la información del sistema'), " +

                        "('DOCENTE', " +
                        "'Gestiona asignaturas y calificaciones'), " +

                        "('ESTUDIANTE', " +
                        "'Consulta información académica y realiza matrículas')"
        );

        db.execSQL(
                "INSERT INTO usuario " +
                        "(id_rol,nombre_usuario,correo,contrasena_hash) VALUES " +

                        "(1,'admin','admin@gmail.com','1234')," +

                        "(2,'docente','docente@gmail.com','1234')," +

                        "(3,'estudiante','estudiante@gmail.com','1234')"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Para actualizar la estructura de la base de datos cuando o si es que se modifican las tablas
        db.execSQL("DROP TABLE IF EXISTS recuperacion_contrasena");
        db.execSQL("DROP TABLE IF EXISTS sesion");
        db.execSQL("DROP TABLE IF EXISTS calificacion");
        db.execSQL("DROP TABLE IF EXISTS detalle_matricula");
        db.execSQL("DROP TABLE IF EXISTS matricula");
        db.execSQL("DROP TABLE IF EXISTS asignatura");
        db.execSQL("DROP TABLE IF EXISTS periodo_academico");
        db.execSQL("DROP TABLE IF EXISTS docente");
        db.execSQL("DROP TABLE IF EXISTS estudiante");
        db.execSQL("DROP TABLE IF EXISTS carrera");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS rol");

        onCreate(db);
    }


}
