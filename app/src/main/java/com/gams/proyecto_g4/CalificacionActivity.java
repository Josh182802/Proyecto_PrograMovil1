package com.gams.proyecto_g4;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gams.proyecto_g4.adapter.CalificacionAdapter;
import com.gams.proyecto_g4.dao.CalificacionDAO;
import com.gams.proyecto_g4.model.Calificacion;
import java.util.ArrayList;
import java.util.List;

public class CalificacionActivity extends AppCompatActivity {
    private RecyclerView rvCalificaciones;
    private TextView txtSinCalificaciones;
    private CalificacionDAO calificacionDAO;
    private CalificacionAdapter calificacionAdapter;
    private Button btnVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificacion);

        rvCalificaciones = findViewById(R.id.rvCalificaciones);
        txtSinCalificaciones = findViewById(R.id.txtSinCalificaciones);
        calificacionDAO = new CalificacionDAO(this);

        btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(v -> finish());

        rvCalificaciones.setLayoutManager(new LinearLayoutManager(this));
        calificacionAdapter = new CalificacionAdapter(new ArrayList<>(), this::mostrarDialogoEdicion);
        rvCalificaciones.setAdapter(calificacionAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarCalificaciones();
    }

    private void cargarCalificaciones() {
        List<Calificacion> lista = calificacionDAO.listarCalificaciones();
        calificacionAdapter.actualizarLista(lista);
        if (lista.isEmpty()) {
            txtSinCalificaciones.setVisibility(View.VISIBLE);
            rvCalificaciones.setVisibility(View.GONE);
        } else {
            txtSinCalificaciones.setVisibility(View.GONE);
            rvCalificaciones.setVisibility(View.VISIBLE);
        }
    }

    private void mostrarDialogoEdicion(Calificacion calif) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_editar_calificacion, null);
        builder.setView(dialogView);

        EditText edtP1 = dialogView.findViewById(R.id.edtParcial1);
        EditText edtP2 = dialogView.findViewById(R.id.edtParcial2);
        EditText edtP3 = dialogView.findViewById(R.id.edtParcial3);
        EditText edtRepo = dialogView.findViewById(R.id.edtReposicion);
        EditText edtObs = dialogView.findViewById(R.id.edtObservacionesCalificacion);

        if (calif.getNotaParcial1() != null) edtP1.setText(String.valueOf(calif.getNotaParcial1()));
        if (calif.getNotaParcial2() != null) edtP2.setText(String.valueOf(calif.getNotaParcial2()));
        if (calif.getNotaParcial3() != null) edtP3.setText(String.valueOf(calif.getNotaParcial3()));
        if (calif.getNotaReposicion() != null) edtRepo.setText(String.valueOf(calif.getNotaReposicion()));
        edtObs.setText(calif.getObservaciones());

        builder.setTitle("Editar Notas - " + calif.getAsignaturaNombre());
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            try {
                calif.setNotaParcial1(edtP1.getText().toString().isEmpty() ? null : Double.parseDouble(edtP1.getText().toString()));
                calif.setNotaParcial2(edtP2.getText().toString().isEmpty() ? null : Double.parseDouble(edtP2.getText().toString()));
                calif.setNotaParcial3(edtP3.getText().toString().isEmpty() ? null : Double.parseDouble(edtP3.getText().toString()));
                calif.setNotaReposicion(edtRepo.getText().toString().isEmpty() ? null : Double.parseDouble(edtRepo.getText().toString()));
                calif.setObservaciones(edtObs.getText().toString().trim());

                if (calificacionDAO.guardarOActualizarCalificacion(calif)) {
                    Toast.makeText(this, "Calificación guardada y promedio actualizado", Toast.LENGTH_SHORT).show();
                    cargarCalificaciones();
                } else {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Asegúrese de ingresar calificaciones numéricas válidas", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }
}