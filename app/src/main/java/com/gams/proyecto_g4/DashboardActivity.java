package com.gams.proyecto_g4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.core.view.WindowCompat;

import com.gams.proyecto_g4.dao.SesionDAO;
import com.gams.proyecto_g4.dao.UsuarioDAO;

public class DashboardActivity extends Activity {

    TextView txtUsuario, txtBienvenida, txtContenido, txtEstadisticas;
    Button btnMenu, btnInicio, btnEstudiantes, btnDocentes, btnAsignaturas, btnCarreras;
    Button btnMatriculas, btnCalificaciones, btnReportes, btnCerrarSesion;
    LinearLayout menuHamburguesa;

    String usuario, nivel;
    int idUsuario;

    SesionDAO sesionDAO;
    UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(),true);

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

        usuario = getIntent().getStringExtra("usuario");
        nivel = getIntent().getStringExtra("nivel");

        usuarioDAO = new UsuarioDAO(this);
        sesionDAO = new SesionDAO(this);

        if (usuario == null || usuario.trim().isEmpty()) {
            Toast.makeText(
                    this,
                    "No se recibió el usuario. Inicie sesión nuevamente.",
                    Toast.LENGTH_LONG
            ).show();

            Intent intent = new Intent(
                    DashboardActivity.this,
                    MainActivity.class
            );

            startActivity(intent);
            finish();
            return;
        }

        idUsuario = usuarioDAO.obtenerIdUsuario(usuario);

        txtUsuario.setText("Usuario: " + usuario + " | Nivel: " + nivel);
        txtBienvenida.setText("Bienvenido al Sistema Académico Universitario");


        btnMenu.setOnClickListener(view -> mostrarOcultarMenu());

        btnCerrarSesion.setOnClickListener(view -> cerrarSesion());

        btnInicio.setOnClickListener(view -> mostrarInicio());
        btnEstudiantes.setOnClickListener(view -> mostrarEstudiantes());
        btnDocentes.setOnClickListener(view -> mostrarDocentes());
        btnAsignaturas.setOnClickListener(view -> mostrarAsignaturas());
        btnCarreras.setOnClickListener(view -> mostrarCarreras());
        btnMatriculas.setOnClickListener(view -> mostrarMatriculas());
        btnCalificaciones.setOnClickListener(view -> mostrarCalificaciones());
        btnReportes.setOnClickListener(view -> mostrarReportes());
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


    private void cerrarSesion() {

        if (idUsuario != -1) {

            sesionDAO.cerrarSesion(idUsuario);

        }

        Toast.makeText(
                this,
                "Sesión cerrada correctamente",
                Toast.LENGTH_SHORT
        ).show();


        Intent intent = new Intent(
                DashboardActivity.this,
                MainActivity.class
        );

        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);

    }


    private void mostrarInicio() {
        txtContenido.setText(
                "Inicio\n\n" +
                        "Bienvenido al panel principal del Sistema Móvil para el Control Académico Universitario."
        );

        cerrarMenu();
    }


    private void mostrarEstudiantes() {
        txtContenido.setText(
                "Módulo de Estudiantes\n\nAdministración de estudiantes registrados."
        );

        cerrarMenu();
    }


    private void mostrarDocentes() {
        cerrarMenu();

        Intent intent = new Intent(
                DashboardActivity.this,
                DocenteActivity.class
        );

        startActivity(intent);
    }


    private void mostrarAsignaturas() {
        cerrarMenu();

        Intent intent = new Intent(
                DashboardActivity.this,
                AsignaturaActivity.class
        );

        startActivity(intent);
    }


    private void mostrarCarreras() {
        cerrarMenu();

        Intent intent = new Intent(
                DashboardActivity.this,
                CarreraActivity.class
        );

        startActivity(intent);
    }


    private void mostrarMatriculas() {
        txtContenido.setText(
                "Módulo de Matrículas\n\nGestión de matrículas."
        );

        cerrarMenu();
    }


    private void mostrarCalificaciones() {
        txtContenido.setText(
                "Módulo de Calificaciones\n\nRegistro de notas."
        );

        cerrarMenu();
    }


    private void mostrarReportes() {
        txtContenido.setText(
                "Módulo de Reportes\n\nInformación académica general."
        );

        cerrarMenu();
    }
}