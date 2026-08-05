package com.gams.proyecto_g4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;

import com.gams.proyecto_g4.dao.SesionDAO;
import com.gams.proyecto_g4.dao.UsuarioDAO;

public class DashboardActivity extends Activity {

    TextView txtUsuario;
    TextView txtBienvenida;
    TextView txtContenido;
    TextView txtEstadisticas;

    Button btnMenu;
    Button btnInicio;
    Button btnEstudiantes;
    Button btnDocentes;
    Button btnAsignaturas;
    Button btnCarreras;
    Button btnMatriculas;
    Button btnCalificaciones;
    Button btnReportes;
    Button btnCerrarSesion;

    LinearLayout menuHamburguesa;

    String usuario;
    String nivel;
    int idUsuario;

    SesionDAO sesionDAO;
    UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(
                getWindow(),
                true
        );

        setContentView(R.layout.activity_dashboard);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtBienvenida = findViewById(R.id.txtBienvenida);
        txtContenido = findViewById(R.id.txtContenido);
        txtEstadisticas = findViewById(R.id.txtEstadisticas);

        btnMenu = findViewById(R.id.btnMenu);
        btnInicio = findViewById(R.id.btnInicio);
        btnEstudiantes = findViewById(R.id.btnEstudiantes);
        btnDocentes = findViewById(R.id.btnDocentes);
        btnAsignaturas = findViewById(R.id.btnAsignaturas);
        btnCarreras = findViewById(R.id.btnCarreras);
        btnMatriculas = findViewById(R.id.btnMatriculas);
        btnCalificaciones = findViewById(R.id.btnCalificaciones);
        btnReportes = findViewById(R.id.btnReportes);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        menuHamburguesa = findViewById(R.id.menuHamburguesa);

        usuarioDAO = new UsuarioDAO(this);
        sesionDAO = new SesionDAO(this);

        usuario = getIntent().getStringExtra("usuario");
        nivel = getIntent().getStringExtra("nivel");

        if (usuario == null || usuario.trim().isEmpty()) {

            Toast.makeText(
                    this,
                    "No se recibió el usuario. Inicie sesión nuevamente.",
                    Toast.LENGTH_LONG
            ).show();

            regresarLogin();
            return;
        }

        if (nivel == null || nivel.trim().isEmpty()) {

            Toast.makeText(
                    this,
                    "El usuario no tiene un nivel válido.",
                    Toast.LENGTH_LONG
            ).show();

            regresarLogin();
            return;
        }

        idUsuario = usuarioDAO.obtenerIdUsuario(usuario);

        if (idUsuario == -1) {

            Toast.makeText(
                    this,
                    "No se pudo obtener la información del usuario.",
                    Toast.LENGTH_LONG
            ).show();

            regresarLogin();
            return;
        }

        txtUsuario.setText(
                "Usuario: " + usuario +
                        " | Nivel: " + nivel
        );

        txtBienvenida.setText(
                "Bienvenido al Sistema Académico Universitario"
        );

        configurarMenuSegunNivel();

        // Eventos
        btnMenu.setOnClickListener(view ->
                mostrarOcultarMenu()
        );

        btnCerrarSesion.setOnClickListener(view ->
                cerrarSesion()
        );

        btnInicio.setOnClickListener(view ->
                mostrarInicio()
        );

        btnEstudiantes.setOnClickListener(view ->
                mostrarEstudiantes()
        );

        btnDocentes.setOnClickListener(view ->
                mostrarDocentes()
        );

        btnAsignaturas.setOnClickListener(view ->
                mostrarAsignaturas()
        );

        btnCarreras.setOnClickListener(view ->
                mostrarCarreras()
        );

        btnMatriculas.setOnClickListener(view ->
                mostrarMatriculas()
        );

        btnCalificaciones.setOnClickListener(view ->
                mostrarCalificaciones()
        );

        btnReportes.setOnClickListener(view ->
                mostrarReportes()
        );
    }

    private void configurarMenuSegunNivel() {

        if (nivel.equalsIgnoreCase("ADMINISTRADOR")) {

            btnEstudiantes.setVisibility(View.VISIBLE);
            btnDocentes.setVisibility(View.VISIBLE);
            btnAsignaturas.setVisibility(View.VISIBLE);
            btnCarreras.setVisibility(View.VISIBLE);
            btnMatriculas.setVisibility(View.VISIBLE);
            btnCalificaciones.setVisibility(View.VISIBLE);
            btnReportes.setVisibility(View.VISIBLE);

        } else if (nivel.equalsIgnoreCase("DOCENTE")) {

            btnEstudiantes.setVisibility(View.VISIBLE);
            btnDocentes.setVisibility(View.GONE);
            btnAsignaturas.setVisibility(View.VISIBLE);
            btnCarreras.setVisibility(View.GONE);
            btnMatriculas.setVisibility(View.GONE);
            btnCalificaciones.setVisibility(View.VISIBLE);
            btnReportes.setVisibility(View.VISIBLE);

        } else if (nivel.equalsIgnoreCase("ESTUDIANTE")) {

            btnEstudiantes.setVisibility(View.GONE);
            btnDocentes.setVisibility(View.GONE);
            btnAsignaturas.setVisibility(View.GONE);
            btnCarreras.setVisibility(View.GONE);
            btnMatriculas.setVisibility(View.VISIBLE);
            btnCalificaciones.setVisibility(View.VISIBLE);
            btnReportes.setVisibility(View.VISIBLE);

        } else {

            Toast.makeText(
                    this,
                    "Nivel de usuario no reconocido.",
                    Toast.LENGTH_LONG
            ).show();

            regresarLogin();
        }
    }

    private void mostrarOcultarMenu() {

        if (menuHamburguesa.getVisibility() == View.GONE) {

            menuHamburguesa.setVisibility(View.VISIBLE);

        } else {

            menuHamburguesa.setVisibility(View.GONE);
        }
    }

    private void cerrarMenu() {

        menuHamburguesa.setVisibility(View.GONE);
    }

    private void mostrarInicio() {

        txtContenido.setText(
                "Inicio\n\n" +
                        "Bienvenido al panel principal del Sistema Móvil " +
                        "para el Control Académico Universitario."
        );

        cerrarMenu();
    }

    private void mostrarEstudiantes() {

        if (nivel.equalsIgnoreCase("ESTUDIANTE")) {

            mostrarSinPermiso(
                    "No tiene permiso para acceder al módulo de estudiantes."
            );

            return;
        }

        cerrarMenu();

        Intent intent = new Intent(
                DashboardActivity.this,
                EstudianteActivity.class
        );

        enviarDatosUsuario(intent);

        startActivity(intent);
    }

    private void mostrarDocentes() {

        if (!nivel.equalsIgnoreCase("ADMINISTRADOR")) {

            mostrarSinPermiso(
                    "Solo el administrador puede acceder al módulo de docentes."
            );

            return;
        }

        cerrarMenu();

        Intent intent = new Intent(
                DashboardActivity.this,
                DocenteActivity.class
        );

        enviarDatosUsuario(intent);

        startActivity(intent);
    }

    private void mostrarAsignaturas() {

        if (nivel.equalsIgnoreCase("ESTUDIANTE")) {

            mostrarSinPermiso(
                    "No tiene permiso para administrar asignaturas."
            );

            return;
        }

        cerrarMenu();

        Intent intent = new Intent(
                DashboardActivity.this,
                AsignaturaActivity.class
        );

        enviarDatosUsuario(intent);

        startActivity(intent);
    }

    private void mostrarCarreras() {

        if (!nivel.equalsIgnoreCase("ADMINISTRADOR")) {

            mostrarSinPermiso(
                    "Solo el administrador puede acceder al módulo de carreras."
            );

            return;
        }

        cerrarMenu();

        Intent intent = new Intent(
                DashboardActivity.this,
                CarreraActivity.class
        );

        enviarDatosUsuario(intent);

        startActivity(intent);
    }

    private void mostrarMatriculas() {

        cerrarMenu();

        Intent intent = new Intent(
                DashboardActivity.this,
                MatriculaActivity.class
        );

        enviarDatosUsuario(intent);

        startActivity(intent);
    }

    private void mostrarCalificaciones() {

        cerrarMenu();

        Intent intent = new Intent(
                DashboardActivity.this,
                CalificacionActivity.class
        );

        enviarDatosUsuario(intent);

        startActivity(intent);
    }

    private void mostrarReportes() {

        cerrarMenu();

        Intent intent = new Intent(
                DashboardActivity.this,
                Reportes.class
        );

        enviarDatosUsuario(intent);

        startActivity(intent);
    }

    private void enviarDatosUsuario(Intent intent) {

        intent.putExtra("id_usuario", idUsuario);
        intent.putExtra("usuario", usuario);
        intent.putExtra("nivel", nivel);
    }

    private void mostrarSinPermiso(String mensaje) {

        Toast.makeText(
                this,
                mensaje,
                Toast.LENGTH_SHORT
        ).show();

        cerrarMenu();
    }

    private void cerrarSesion() {

        if (idUsuario != -1) {

            sesionDAO.cerrarSesion(idUsuario);
        }

        Toast.makeText(
                this,
                "Sesión cerrada correctamente.",
                Toast.LENGTH_SHORT
        ).show();

        regresarLogin();
    }

    private void regresarLogin() {

        Intent intent = new Intent(
                DashboardActivity.this,
                MainActivity.class
        );

        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);
        finish();
    }
}