package com.gams.proyecto_g4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gams.proyecto_g4.adapter.MatriculaAdapter;
import com.gams.proyecto_g4.dao.MatriculaDAO;
import com.gams.proyecto_g4.model.Matricula;
import java.util.ArrayList;
import java.util.List;

public class MatriculaActivity extends AppCompatActivity {
    private RecyclerView rvMatriculas;
    private TextView txtSinMatriculas;
    private Button btnNuevaMatricula;
    private MatriculaDAO matriculaDAO;
    private MatriculaAdapter matriculaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);

        rvMatriculas = findViewById(R.id.rvMatriculas);
        txtSinMatriculas = findViewById(R.id.txtSinMatriculas);
        btnNuevaMatricula = findViewById(R.id.btnNuevaMatricula);
        matriculaDAO = new MatriculaDAO(this);

        rvMatriculas.setLayoutManager(new LinearLayoutManager(this));
        matriculaAdapter = new MatriculaAdapter(new ArrayList<>());
        rvMatriculas.setAdapter(matriculaAdapter);

        btnNuevaMatricula.setOnClickListener(view -> {
            Intent intent = new Intent(MatriculaActivity.this, FormularioMatriculaActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarMatriculas();
    }

    private void cargarMatriculas() {
        List<Matricula> lista = matriculaDAO.listarMatriculas();
        matriculaAdapter.actualizarLista(lista);
        if (lista.isEmpty()) {
            txtSinMatriculas.setVisibility(View.VISIBLE);
            rvMatriculas.setVisibility(View.GONE);
        } else {
            txtSinMatriculas.setVisibility(View.GONE);
            rvMatriculas.setVisibility(View.VISIBLE);
        }
    }
}