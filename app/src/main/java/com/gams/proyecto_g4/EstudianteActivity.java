package com.gams.proyecto_g4;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gams.proyecto_g4.adapter.EstudianteAdapter;
import com.gams.proyecto_g4.dao.EstudianteDAO;
import com.gams.proyecto_g4.model.Estudiante;

import java.util.ArrayList;
import java.util.List;

public class EstudianteActivity extends AppCompatActivity {

    private RecyclerView rvEstudiantes;
    private TextView txtSinEstudiantes;
    private EditText edtBuscarEstudiante;
    private Button btnNuevoEstudiante;

    private EstudianteDAO estudianteDAO;
    private EstudianteAdapter estudianteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);

        inicializarComponentes();
        configurarRecyclerView();
        configurarBuscador();

        btnNuevoEstudiante.setOnClickListener(view -> {
            Intent intent = new Intent(
                    EstudianteActivity.this,
                    FormularioEstudianteActivity.class
            );

            startActivity(intent);
        });
    }

    private void inicializarComponentes() {
        rvEstudiantes = findViewById(
                R.id.rvEstudiantes
        );

        txtSinEstudiantes = findViewById(
                R.id.txtSinEstudiantes
        );

        edtBuscarEstudiante = findViewById(
                R.id.edtBuscarEstudiante
        );

        btnNuevoEstudiante = findViewById(
                R.id.btnNuevoEstudiante
        );

        estudianteDAO = new EstudianteDAO(this);
    }

    private void configurarRecyclerView() {
        rvEstudiantes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        estudianteAdapter = new EstudianteAdapter(
                new ArrayList<>(),
                new EstudianteAdapter.OnEstudianteAccionListener() {
                    @Override
                    public void onEditar(Estudiante estudiante) {
                        abrirEdicion(estudiante);
                    }

                    @Override
                    public void onCambiarEstado(Estudiante estudiante) {
                        confirmarCambioEstado(estudiante);
                    }
                }
        );

        rvEstudiantes.setAdapter(estudianteAdapter);
    }

    private void configurarBuscador() {
        edtBuscarEstudiante.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after
                    ) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count
                    ) {
                        buscarEstudiantes(
                                s.toString().trim()
                        );
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s
                    ) {
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        String textoBusqueda =
                edtBuscarEstudiante
                        .getText()
                        .toString()
                        .trim();

        buscarEstudiantes(textoBusqueda);
    }

    private void buscarEstudiantes(String texto) {
        List<Estudiante> lista;

        if (texto.isEmpty()) {
            lista = estudianteDAO.listar();
        } else {
            lista = estudianteDAO.buscar(texto);
        }

        mostrarLista(lista);
    }

    private void mostrarLista(
            List<Estudiante> lista
    ) {
        estudianteAdapter.actualizarLista(lista);

        if (lista.isEmpty()) {
            txtSinEstudiantes.setVisibility(
                    View.VISIBLE
            );

            rvEstudiantes.setVisibility(
                    View.GONE
            );
        } else {
            txtSinEstudiantes.setVisibility(
                    View.GONE
            );

            rvEstudiantes.setVisibility(
                    View.VISIBLE
            );
        }
    }

    private void abrirEdicion(
            Estudiante estudiante
    ) {
        Intent intent = new Intent(
                EstudianteActivity.this,
                FormularioEstudianteActivity.class
        );

        intent.putExtra(
                "id_estudiante",
                estudiante.getIdEstudiante()
        );

        startActivity(intent);
    }

    private void confirmarCambioEstado(
            Estudiante estudiante
    ) {
        boolean estaActivo =
                "ACTIVO".equals(
                        estudiante.getEstadoAcademico()
                );

        String accion = estaActivo
                ? "desactivar"
                : "activar";

        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage(
                        "¿Desea "
                                + accion
                                + " al estudiante "
                                + estudiante.getNombreCompleto()
                                + "?"
                )
                .setPositiveButton(
                        "Sí",
                        (dialog, which) ->
                                cambiarEstado(
                                        estudiante,
                                        estaActivo
                                )
                )
                .setNegativeButton("No", null)
                .show();
    }

    private void cambiarEstado(
            Estudiante estudiante,
            boolean estabaActivo
    ) {
        String nuevoEstado = estabaActivo
                ? "INACTIVO"
                : "ACTIVO";

        boolean resultado =
                estudianteDAO.cambiarEstadoAcademico(
                        estudiante.getIdEstudiante(),
                        nuevoEstado
                );

        if (resultado) {
            Toast.makeText(
                    this,
                    "Estado actualizado correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            buscarEstudiantes(
                    edtBuscarEstudiante
                            .getText()
                            .toString()
                            .trim()
            );
        } else {
            Toast.makeText(
                    this,
                    "No se pudo actualizar el estado",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}