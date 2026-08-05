package com.gams.proyecto_g4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gams.proyecto_g4.adapter.AsignaturaAdapter;
import com.gams.proyecto_g4.dao.AsignaturaDAO;
import com.gams.proyecto_g4.model.Asignatura;

import java.util.ArrayList;
import java.util.List;

public class AsignaturaActivity extends AppCompatActivity {

    private RecyclerView rvAsignaturas;
    private TextView txtSinAsignaturas;
    private Button btnNuevaAsignatura;

    private Button btnVolver;

    private AsignaturaDAO asignaturaDAO;
    private AsignaturaAdapter asignaturaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura);

        rvAsignaturas = findViewById(R.id.rvAsignaturas);
        txtSinAsignaturas = findViewById(R.id.txtSinAsignaturas);
        btnNuevaAsignatura = findViewById(R.id.btnNuevaAsignatura);
        btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(v -> finish());

        asignaturaDAO = new AsignaturaDAO(this);

        configurarRecyclerView();

        btnNuevaAsignatura.setOnClickListener(view -> {
            Intent intent = new Intent(
                    AsignaturaActivity.this,
                    FormularioAsignaturaActivity.class
            );

            startActivity(intent);
        });
    }

    private void configurarRecyclerView() {
        rvAsignaturas.setLayoutManager(
                new LinearLayoutManager(this)
        );

        asignaturaAdapter = new AsignaturaAdapter(
                new ArrayList<>(),
                new AsignaturaAdapter.OnAsignaturaAccionListener() {
                    @Override
                    public void onEditar(Asignatura asignatura) {
                        abrirEdicion(asignatura);
                    }

                    @Override
                    public void onCambiarEstado(Asignatura asignatura) {
                        confirmarCambioEstado(asignatura);
                    }
                }
        );

        rvAsignaturas.setAdapter(asignaturaAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarAsignaturas();
    }

    private void cargarAsignaturas() {
        List<Asignatura> lista = asignaturaDAO.listar();

        asignaturaAdapter.actualizarLista(lista);

        if (lista.isEmpty()) {
            txtSinAsignaturas.setVisibility(View.VISIBLE);
            rvAsignaturas.setVisibility(View.GONE);
        } else {
            txtSinAsignaturas.setVisibility(View.GONE);
            rvAsignaturas.setVisibility(View.VISIBLE);
        }
    }

    private void abrirEdicion(Asignatura asignatura) {
        Intent intent = new Intent(
                AsignaturaActivity.this,
                FormularioAsignaturaActivity.class
        );

        intent.putExtra(
                "id_asignatura",
                asignatura.getIdAsignatura()
        );

        startActivity(intent);
    }

    private void confirmarCambioEstado(Asignatura asignatura) {
        String accion = asignatura.isEstado()
                ? "desactivar"
                : "activar";

        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage(
                        "¿Desea "
                                + accion
                                + " la asignatura "
                                + asignatura.getNombre()
                                + "?"
                )
                .setPositiveButton(
                        "Sí",
                        (dialog, which) ->
                                cambiarEstado(asignatura)
                )
                .setNegativeButton("No", null)
                .show();
    }

    private void cambiarEstado(Asignatura asignatura) {
        boolean resultado = asignaturaDAO.cambiarEstado(
                asignatura.getIdAsignatura(),
                !asignatura.isEstado()
        );

        if (resultado) {
            Toast.makeText(
                    this,
                    "Estado actualizado correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            cargarAsignaturas();
        } else {
            Toast.makeText(
                    this,
                    "No se pudo actualizar el estado",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}