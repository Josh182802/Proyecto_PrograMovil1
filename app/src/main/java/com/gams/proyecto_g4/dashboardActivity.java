package com.example.examenp2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class DashboardActivity extends Activity {

    TextView txtUsuario, txtBienvenida, txtContenido, txtEstadisticas;
    Button btnMenu, btnInicio, btnEstudiantes, btnDocentes, btnAsignaturas, btnCarreras;
    Button btnMatriculas, btnCalificaciones, btnReportes, btnCerrarSesion;
    LinearLayout menuHamburguesa;

    String usuario, nivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        txtUsuario.setText("Usuario: " + usuario + " | Nivel: " + nivel);
        txtBienvenida.setText("Bienvenido al Sistema Académico Universitario");

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarOcultarMenu();
            }
        });

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarInicio();
            }
        });

        btnEstudiantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarEstudiantes();
            }
        });

        btnDocentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDocentes();
            }
        });

        btnAsignaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarAsignaturas();
            }
        });

        btnCarreras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarCarreras();
            }
        });

        btnMatriculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarMatriculas();
            }
        });

        btnCalificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarCalificaciones();
            }
        });

        btnReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarReportes();
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });
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
                        "Bienvenido al panel principal del Sistema Móvil para el Control Académico Universitario.\n\n" +
                        "Desde este menú puede acceder a los módulos de estudiantes, docentes, asignaturas, carreras, matrículas, calificaciones y reportes."
        );

        txtEstadisticas.setText(
                "Estudiantes registrados: 120\n" +
                        "Docentes registrados: 18\n" +
                        "Asignaturas activas: 35\n" +
                        "Carreras disponibles: 7\n" +
                        "Matrículas realizadas: 95"
        );

        Toast.makeText(this, "Inicio seleccionado", Toast.LENGTH_SHORT).show();
        cerrarMenu();
    }

    private void mostrarEstudiantes() {
        txtContenido.setText(
                "Módulo de Estudiantes\n\n" +
                        "Este módulo permite administrar la información de los estudiantes registrados en la institución.\n\n" +
                        "Funciones disponibles:\n" +
                        "- Registrar estudiante\n" +
                        "- Consultar estudiante\n" +
                        "- Actualizar estudiante\n" +
                        "- Eliminar estudiante"
        );

        txtEstadisticas.setText(
                "Campos sugeridos:\n" +
                        "Código de estudiante\n" +
                        "Nombre completo\n" +
                        "Identidad\n" +
                        "Teléfono\n" +
                        "Correo\n" +
                        "Carrera\n" +
                        "Estado"
        );

        Toast.makeText(this, "Módulo Estudiantes seleccionado", Toast.LENGTH_SHORT).show();
        cerrarMenu();
    }

    private void mostrarDocentes() {
        txtContenido.setText(
                "Módulo de Docentes\n\n" +
                        "Este módulo permite administrar la información de los docentes de la institución.\n\n" +
                        "Funciones disponibles:\n" +
                        "- Registrar docente\n" +
                        "- Consultar docente\n" +
                        "- Actualizar docente\n" +
                        "- Eliminar docente"
        );

        txtEstadisticas.setText(
                "Campos sugeridos:\n" +
                        "Código de docente\n" +
                        "Nombre completo\n" +
                        "Correo\n" +
                        "Teléfono\n" +
                        "Especialidad\n" +
                        "Estado"
        );

        Toast.makeText(this, "Módulo Docentes seleccionado", Toast.LENGTH_SHORT).show();
        cerrarMenu();
    }

    private void mostrarAsignaturas() {
        txtContenido.setText(
                "Módulo de Asignaturas\n\n" +
                        "Este módulo permite gestionar las asignaturas disponibles dentro del sistema académico.\n\n" +
                        "Funciones disponibles:\n" +
                        "- Registrar asignatura\n" +
                        "- Consultar asignatura\n" +
                        "- Actualizar asignatura\n" +
                        "- Eliminar asignatura"
        );

        txtEstadisticas.setText(
                "Campos sugeridos:\n" +
                        "Código de asignatura\n" +
                        "Nombre de asignatura\n" +
                        "Créditos\n" +
                        "Carrera\n" +
                        "Docente asignado"
        );

        Toast.makeText(this, "Módulo Asignaturas seleccionado", Toast.LENGTH_SHORT).show();
        cerrarMenu();
    }

    private void mostrarCarreras() {
        txtContenido.setText(
                "Módulo de Carreras\n\n" +
                        "Este módulo permite registrar y administrar las carreras universitarias disponibles.\n\n" +
                        "Funciones disponibles:\n" +
                        "- Registrar carrera\n" +
                        "- Consultar carrera\n" +
                        "- Actualizar carrera\n" +
                        "- Eliminar carrera"
        );

        txtEstadisticas.setText(
                "Campos sugeridos:\n" +
                        "Código de carrera\n" +
                        "Nombre de carrera\n" +
                        "Facultad\n" +
                        "Duración\n" +
                        "Estado"
        );

        Toast.makeText(this, "Módulo Carreras seleccionado", Toast.LENGTH_SHORT).show();
        cerrarMenu();
    }

    private void mostrarMatriculas() {
        txtContenido.setText(
                "Módulo de Matrículas\n\n" +
                        "Este módulo permite registrar las asignaturas inscritas por cada estudiante.\n\n" +
                        "Funciones disponibles:\n" +
                        "- Registrar matrícula\n" +
                        "- Consultar matrícula\n" +
                        "- Actualizar matrícula\n" +
                        "- Eliminar matrícula"
        );

        txtEstadisticas.setText(
                "Campos sugeridos:\n" +
                        "Código de matrícula\n" +
                        "Estudiante\n" +
                        "Carrera\n" +
                        "Asignatura\n" +
                        "Periodo académico\n" +
                        "Fecha de matrícula\n" +
                        "Estado"
        );

        Toast.makeText(this, "Módulo Matrículas seleccionado", Toast.LENGTH_SHORT).show();
        cerrarMenu();
    }

    private void mostrarCalificaciones() {
        txtContenido.setText(
                "Módulo de Calificaciones\n\n" +
                        "Este módulo permite registrar, consultar y calcular las calificaciones de los estudiantes.\n\n" +
                        "Funciones disponibles:\n" +
                        "- Registrar calificación\n" +
                        "- Consultar calificación\n" +
                        "- Actualizar calificación\n" +
                        "- Calcular promedio"
        );

        txtEstadisticas.setText(
                "Campos sugeridos:\n" +
                        "Estudiante\n" +
                        "Asignatura\n" +
                        "Primer parcial\n" +
                        "Segundo parcial\n" +
                        "Tercer parcial\n" +
                        "Nota final\n" +
                        "Estado: Aprobado / Reprobado"
        );

        Toast.makeText(this, "Módulo Calificaciones seleccionado", Toast.LENGTH_SHORT).show();
        cerrarMenu();
    }

    private void mostrarReportes() {
        txtContenido.setText(
                "Módulo de Reportes Académicos\n\n" +
                        "Este módulo permite visualizar información general del rendimiento académico.\n\n" +
                        "Reportes disponibles:\n" +
                        "- Rendimiento estudiantil\n" +
                        "- Asignaturas inscritas\n" +
                        "- Promedio por estudiante\n" +
                        "- Estudiantes aprobados y reprobados\n" +
                        "- Estadísticas generales"
        );

        txtEstadisticas.setText(
                "Estadísticas generales:\n" +
                        "Promedio institucional: 82%\n" +
                        "Estudiantes aprobados: 86\n" +
                        "Estudiantes reprobados: 12\n" +
                        "Asignatura con mayor matrícula: Programación Móvil\n" +
                        "Carrera con más estudiantes: Ingeniería en Sistemas"
        );

        Toast.makeText(this, "Módulo Reportes seleccionado", Toast.LENGTH_SHORT).show();
        cerrarMenu();
    }

    private void cerrarSesion() {
        Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}