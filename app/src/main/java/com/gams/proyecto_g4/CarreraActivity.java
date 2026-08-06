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

import com.gams.proyecto_g4.adapter.CarreraAdapter;
import com.gams.proyecto_g4.dao.CarreraDAO;
import com.gams.proyecto_g4.model.Carrera;

import java.util.ArrayList;
import java.util.List;

public class CarreraActivity extends AppCompatActivity {

    private RecyclerView rvCarreras;
    private TextView txtSinCarreras;
    private Button btnNuevaCarrera;
    private Button btnVolver;
    private CarreraDAO carreraDAO;
    private CarreraAdapter carreraAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrera);

        rvCarreras = findViewById(R.id.rvCarreras);
        txtSinCarreras = findViewById(R.id.txtSinCarreras);
        btnNuevaCarrera = findViewById(R.id.btnNuevaCarrera);
        btnVolver = findViewById(R.id.btnVolver);

        configurarRecyclerView();

        btnNuevaCarrera.setOnClickListener(view -> {
            Intent intent = new Intent(
                    CarreraActivity.this,
                    FormularioCarreraActivity.class
            );

            startActivity(intent);
        });

        btnVolver.setOnClickListener(v -> finish());
        carreraDAO = new CarreraDAO(this);
    }

    private void configurarRecyclerView() {
        rvCarreras.setLayoutManager(
                new LinearLayoutManager(this)
        );

        carreraAdapter = new CarreraAdapter(
                new ArrayList<>(),
                new CarreraAdapter.OnCarreraAccionListener() {
                    @Override
                    public void onEditar(Carrera carrera) {
                        abrirEdicion(carrera);
                    }

                    @Override
                    public void onCambiarEstado(Carrera carrera) {
                        confirmarCambioEstado(carrera);
                    }
                }
        );

        rvCarreras.setAdapter(carreraAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarCarreras();
    }

    private void cargarCarreras() {
        List<Carrera> lista = carreraDAO.listar();

        carreraAdapter.actualizarLista(lista);

        if (lista.isEmpty()) {
            txtSinCarreras.setVisibility(View.VISIBLE);
            rvCarreras.setVisibility(View.GONE);
        } else {
            txtSinCarreras.setVisibility(View.GONE);
            rvCarreras.setVisibility(View.VISIBLE);
        }
    }

    private void abrirEdicion(Carrera carrera) {
        Intent intent = new Intent(
                CarreraActivity.this,
                FormularioCarreraActivity.class
        );

        intent.putExtra(
                "id_carrera",
                carrera.getIdCarrera()
        );

        startActivity(intent);
    }

    private void confirmarCambioEstado(Carrera carrera) {
        String accion = carrera.isEstado()
                ? "desactivar"
                : "activar";

        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage(
                        "¿Desea "
                                + accion
                                + " la carrera "
                                + carrera.getNombre()
                                + "?"
                )
                .setPositiveButton(
                        "Sí",
                        (dialog, which) -> cambiarEstado(carrera)
                )
                .setNegativeButton("No", null)
                .show();
    }

    private void cambiarEstado(Carrera carrera) {
        boolean resultado = carreraDAO.cambiarEstado(
                carrera.getIdCarrera(),
                !carrera.isEstado()
        );

        if (resultado) {
            Toast.makeText(
                    this,
                    "Estado actualizado correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            cargarCarreras();
        } else {
            Toast.makeText(
                    this,
                    "No se pudo actualizar el estado",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}