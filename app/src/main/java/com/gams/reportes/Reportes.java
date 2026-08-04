package com.gams.reportes;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.gams.reportes.database.DatabaseHelper;
import android.widget.Button;
import android.widget.Toast;


public class Reportes extends AppCompatActivity {

    private Button btnRendimiento;
    private Button btnAsignaturas;
    private Button btnMatriculas;
    private Button btnEstadisticas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.reportes);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnRendimiento = findViewById(R.id.btnRendimiento);
        btnAsignaturas = findViewById(R.id.btnAsignaturas);
        btnMatriculas = findViewById(R.id.btnMatriculas);
        btnEstadisticas = findViewById(R.id.btnEstadisticas);

        btnRendimiento.setOnClickListener(v ->
                Toast.makeText(this, "Reporte de rendimiento estudiantil", Toast.LENGTH_SHORT).show()
        );

        btnAsignaturas.setOnClickListener(v ->
                Toast.makeText(this, "Reporte de asignaturas inscritas", Toast.LENGTH_SHORT).show()
        );

        btnMatriculas.setOnClickListener(v ->
                Toast.makeText(this, "Reporte de matrículas", Toast.LENGTH_SHORT).show()
        );

        btnEstadisticas.setOnClickListener(v ->
                Toast.makeText(this, "Reporte de estadísticas generales", Toast.LENGTH_SHORT).show()
        );
    }
}