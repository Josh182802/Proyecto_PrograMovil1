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

import com.gams.proyecto_g4.adapter.DocenteAdapter;
import com.gams.proyecto_g4.dao.DocenteDAO;
import com.gams.proyecto_g4.model.Docente;

import java.util.ArrayList;
import java.util.List;

public class DocenteActivity extends AppCompatActivity {

    private RecyclerView rvDocentes;
    private TextView txtSinDocentes;
    private Button btnNuevoDocente;

    private DocenteDAO docenteDAO;
    private DocenteAdapter docenteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);

        rvDocentes = findViewById(R.id.rvDocentes);
        txtSinDocentes = findViewById(R.id.txtSinDocentes);
        btnNuevoDocente = findViewById(R.id.btnNuevoDocente);

        docenteDAO = new DocenteDAO(this);

        configurarRecyclerView();

        btnNuevoDocente.setOnClickListener(view -> {
            Intent intent = new Intent(
                    DocenteActivity.this,
                    FormularioDocenteActivity.class
            );

            startActivity(intent);
        });
    }

    private void configurarRecyclerView() {
        rvDocentes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        docenteAdapter = new DocenteAdapter(
                new ArrayList<>(),
                new DocenteAdapter.OnDocenteAccionListener() {
                    @Override
                    public void onEditar(Docente docente) {
                        abrirEdicion(docente);
                    }

                    @Override
                    public void onCambiarEstado(Docente docente) {
                        confirmarCambioEstado(docente);
                    }
                }
        );

        rvDocentes.setAdapter(docenteAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDocentes();
    }

    private void cargarDocentes() {
        List<Docente> lista = docenteDAO.listar();

        docenteAdapter.actualizarLista(lista);

        if (lista.isEmpty()) {
            txtSinDocentes.setVisibility(View.VISIBLE);
            rvDocentes.setVisibility(View.GONE);
        } else {
            txtSinDocentes.setVisibility(View.GONE);
            rvDocentes.setVisibility(View.VISIBLE);
        }
    }

    private void abrirEdicion(Docente docente) {
        Intent intent = new Intent(
                DocenteActivity.this,
                FormularioDocenteActivity.class
        );

        intent.putExtra(
                "id_docente",
                docente.getIdDocente()
        );

        startActivity(intent);
    }

    private void confirmarCambioEstado(Docente docente) {
        boolean estaActivo =
                "ACTIVO".equals(docente.getEstadoLaboral());

        String accion = estaActivo
                ? "desactivar"
                : "activar";

        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage(
                        "¿Desea "
                                + accion
                                + " al docente "
                                + docente.getNombreCompleto()
                                + "?"
                )
                .setPositiveButton(
                        "Sí",
                        (dialog, which) ->
                                cambiarEstado(docente, estaActivo)
                )
                .setNegativeButton("No", null)
                .show();
    }

    private void cambiarEstado(
            Docente docente,
            boolean estabaActivo
    ) {
        String nuevoEstado = estabaActivo
                ? "INACTIVO"
                : "ACTIVO";

        boolean resultado =
                docenteDAO.cambiarEstadoLaboral(
                        docente.getIdDocente(),
                        nuevoEstado
                );

        if (resultado) {
            Toast.makeText(
                    this,
                    "Estado actualizado correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            cargarDocentes();
        } else {
            Toast.makeText(
                    this,
                    "No se pudo actualizar el estado",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}